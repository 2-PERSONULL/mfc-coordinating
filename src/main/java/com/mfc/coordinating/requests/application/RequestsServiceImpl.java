package com.mfc.coordinating.requests.application;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.requests.domain.Brand;
import com.mfc.coordinating.requests.domain.Category;
import com.mfc.coordinating.requests.domain.MyImage;
import com.mfc.coordinating.requests.domain.ReferenceImage;
import com.mfc.coordinating.requests.domain.RequestHistory;
import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.enums.RequestsStates;
import com.mfc.coordinating.requests.infrastructure.BrandRepository;
import com.mfc.coordinating.requests.infrastructure.CategoryRepository;
import com.mfc.coordinating.requests.infrastructure.MyImageRepository;
import com.mfc.coordinating.requests.infrastructure.ReferenceImageRepository;
import com.mfc.coordinating.requests.infrastructure.RequestHistoryRepository;
import com.mfc.coordinating.requests.infrastructure.RequestsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RequestsServiceImpl implements RequestsService {
	private final RequestsRepository requestsRepository;
	private final RequestHistoryRepository requestHistoryRepository;
	private final MyImageRepository myImageRepository;
	private final ReferenceImageRepository referenceImageRepository;
	private final CategoryRepository categoryRepository;
	private final BrandRepository brandRepository;
	private final RequestsEventProducer requestsEventProducer;

	@Override
	public void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid) {
		Requests requests = Requests.builder()
			.userId(uuid)
			.title(requestsCreateReqDto.getTitle())
			.description(requestsCreateReqDto.getDescription())
			.situation(requestsCreateReqDto.getSituation())
			.budget(requestsCreateReqDto.getBudget())
			.otherRequirements(requestsCreateReqDto.getOtherRequirements())
			.build();

		Requests savedRequests = requestsRepository.save(requests);

		List<ReferenceImage> referenceImages = requestsCreateReqDto.getReferenceImages().stream()
			.map(url -> ReferenceImage.builder().url(url).requests(savedRequests).build())
			.toList();
		referenceImageRepository.saveAll(referenceImages);

		List<MyImage> myImages = requestsCreateReqDto.getMyImages().stream()
			.map(url -> MyImage.builder().url(url).requests(savedRequests).build())
			.toList();
		myImageRepository.saveAll(myImages);

		List<Category> categories = requestsCreateReqDto.getCategory().stream()
			.map(name -> Category.builder().name(name).requests(savedRequests).build())
			.toList();
		categoryRepository.saveAll(categories);

		List<Brand> brands = requestsCreateReqDto.getBrand().stream()
			.map(name -> Brand.builder().name(name).requests(savedRequests).build())
			.toList();
		brandRepository.saveAll(brands);
	}

	@Override
	public List<RequestsListResDto> getRequestsListByUser(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable = getPageable(page, pageSize, sortType);

		Page<RequestHistory> requestHistoryPage = requestHistoryRepository.findByUserId(uuid, pageable);

		return requestHistoryPage.getContent().stream()
			.map(RequestHistory::toDto)
			.toList();
	}

	@Override
	public List<MyRequestListResponse> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable = getPageable(page, pageSize, sortType);
		return requestsRepository.findByUserId(uuid, pageable).getContent();
	}

	@Override
	public RequestsDetailResDto getRequestsDetail(Long requestId) {
		Requests requests = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		List<String> categoryNames = categoryRepository.findByRequestId(requestId)
			.stream()
			.map(Category::getName)
			.toList();

		List<String> brandNames = brandRepository.findByRequestId(requestId)
			.stream()
			.map(Brand::getName)
			.toList();

		List<String> referenceImageUrls = referenceImageRepository.findByRequestId(requestId)
			.stream()
			.map(ReferenceImage::getUrl)
			.toList();

		List<String> myImageUrls = myImageRepository.findByRequestId(requestId)
			.stream()
			.map(MyImage::getUrl)
			.toList();

		return RequestsDetailResDto.toBuild(requests, referenceImageUrls, myImageUrls, categoryNames, brandNames);
	}

	@Override
	public void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid) {
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.updateRequests(dto.getTitle(), dto.getDescription(), dto.getSituation(), dto.getBudget(),
			dto.getOtherRequirements());

		referenceImageRepository.deleteByRequestId(requestId);
		List<ReferenceImage> referenceImages = dto.getReferenceImages().stream()
			.map(url -> ReferenceImage.builder().url(url).requests(requests).build())
			.toList();
		referenceImageRepository.saveAll(referenceImages);

		myImageRepository.deleteByRequestId(requestId);
		List<MyImage> myImages = dto.getMyImages().stream()
			.map(url -> MyImage.builder().url(url).requests(requests).build())
			.toList();
		myImageRepository.saveAll(myImages);

		categoryRepository.deleteByRequestId(requestId);
		List<Category> categories = dto.getCategory().stream()
			.map(name -> Category.builder().name(name).requests(requests).build())
			.toList();
		categoryRepository.saveAll(categories);

		brandRepository.deleteByRequestId(requestId);
		List<Brand> brands = dto.getBrand().stream()
			.map(name -> Brand.builder().name(name).requests(requests).build())
			.toList();
		brandRepository.saveAll(brands);
	}

	@Override
	public void deleteRequests(Long requestId, String uuid) {
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		referenceImageRepository.deleteByRequestId(requestId);
		myImageRepository.deleteByRequestId(requestId);
		requestsRepository.delete(requests);
		categoryRepository.deleteByRequestId(requestId);
		brandRepository.deleteByRequestId(requestId);
	}

	@Override
	public void updateProposal(Long requestId, String partnerId, String uuid, LocalDate deadline) {
		RequestsStates states = RequestsStates.NONERESPONSE;
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		if (partnerId.equals(uuid)) {
			throw new BaseException(BaseResponseStatus.NON_SELF_MEMBERS);
		}

		// Kafka를 통해 유저 정보 요청 이벤트 발행
		requestsEventProducer.sendRequestsHistoryCreateEvent(requestId, uuid, partnerId, deadline);
	}

	@KafkaListener(topics = "user-info-response", containerFactory = "kafkaListenerContainerFactory")
	public void handleUserInfoResponse(String message) {
		try {
			// ObjectMapper를 사용하여 JSON 메시지 파싱
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(message);

			Long requestId = jsonNode.get("requestId").asLong();
			String userId = jsonNode.get("userId").asText();
			String partnerId = jsonNode.get("partnerId").asText();
			LocalDate deadline = LocalDate.parse(jsonNode.get("deadline").asText());
			String userImageUrl = jsonNode.get("userImageUrl").asText();
			String userNickName = jsonNode.get("userNickName").asText();
			Short userGender = jsonNode.get("userGender").shortValue();
			LocalDate userBirth = LocalDate.parse(jsonNode.get("userBirth").asText());

			// requestId를 기반으로 Requests 엔티티 조회
			Requests requests = requestsRepository.findByRequestId(requestId)
				.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

			// RequestHistory 엔티티 생성
			RequestHistory requestHistory = RequestHistory.builder()
				.requestId(requestId)
				.userId(userId)
				.partnerId(partnerId)
				.deadline(deadline)
				.status(RequestsStates.NONERESPONSE)
				.title(requests.getTitle())
				.userImageUrl(userImageUrl)
				.userNickName(userNickName)
				.userGender(userGender)
				.userAge(calculateAge(userBirth))
				.build();

			// RequestHistory 엔티티 저장
			try {
				requestHistoryRepository.save(requestHistory);
			} catch (Exception e) {
				// 예외 처리 로직 추가
				e.printStackTrace();
				// 필요한 경우 예외를 throw하거나 다른 방식으로 처리할 수 있습니다.
				throw new RuntimeException("Failed to save RequestHistory", e);
			}
		} catch (Exception e) {
			// 예외 처리 로직 추가
			e.printStackTrace();
			// 필요한 경우 예외를 throw하거나 다른 방식으로 처리할 수 있습니다.
			throw new RuntimeException("Failed to handle user info response", e);
		}
	}

	private int calculateAge(LocalDate birthDate) {
		LocalDate currentDate = LocalDate.now();
		return Period.between(birthDate, currentDate).getYears();
	}

	@Override
	public List<RequestsListResDto> getRequestsListPartner(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable = getPageable(page, pageSize, sortType);

		Page<RequestHistory> requestHistoryPage = requestHistoryRepository.findByPartnerId(uuid, pageable);

		return requestHistoryPage.getContent().stream()
			.map(history -> RequestsListResDto.builder()
				.requestId(history.getRequestId())
				.userId(history.getUserId())
				.title(history.getTitle())
				.partnerId(history.getPartnerId())
				.deadline(history.getDeadline())
				.status(history.getStatus())
				.build())
			.toList();
	}

	@Override
	public void updateAcceptRequests(Long historyId, String uuid) {
		RequestsStates states = RequestsStates.RESPONSEACCEPT;
		Optional<RequestHistory> optionalRequestHistory = requestHistoryRepository.findById(historyId);
		RequestHistory requestHistory = optionalRequestHistory
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestHistory.updateStatus(states);
	}

	@Override
	public void updateRejectRequests(Long historyId, String uuid) {
		RequestsStates states = RequestsStates.RESPONSEREJECT;
		Optional<RequestHistory> optionalRequestHistory = requestHistoryRepository.findById(historyId);
		RequestHistory requestHistory = optionalRequestHistory
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestHistory.updateStatus(states);
	}

	private Pageable getPageable(int page, int pageSize, RequestsListSortType sortType) {
		if (sortType == RequestsListSortType.LATEST) {
			return PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == RequestsListSortType.DEADLINE_ASC) {
			return PageRequest.of(page, pageSize, Sort.by("deadline").ascending());
		} else {
			return PageRequest.of(page, pageSize, Sort.by("deadline").descending());
		}
	}
}