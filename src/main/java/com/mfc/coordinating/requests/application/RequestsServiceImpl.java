// package com.mfc.coordinating.requests.application;
//
// import java.time.LocalDate;
// import java.time.Period;
// import java.util.List;
// import java.util.Optional;
//
// import org.bson.types.ObjectId;
// import org.modelmapper.ModelMapper;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;
//
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.mfc.coordinating.common.exception.BaseException;
// import com.mfc.coordinating.common.response.BaseResponseStatus;
// import com.mfc.coordinating.requests.domain.Brand;
// import com.mfc.coordinating.requests.domain.Category;
// import com.mfc.coordinating.requests.domain.MyImage;
// import com.mfc.coordinating.requests.domain.ReferenceImage;
// import com.mfc.coordinating.requests.domain.RequestHistory;
// import com.mfc.coordinating.requests.domain.Requests;
// import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
// import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
// import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
// import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
// import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
// import com.mfc.coordinating.requests.enums.RequestsListSortType;
// import com.mfc.coordinating.requests.enums.RequestsStates;
// import com.mfc.coordinating.requests.infrastructure.BrandRepository;
// import com.mfc.coordinating.requests.infrastructure.CategoryRepository;
// import com.mfc.coordinating.requests.infrastructure.MyImageRepository;
// import com.mfc.coordinating.requests.infrastructure.ReferenceImageRepository;
// import com.mfc.coordinating.requests.infrastructure.RequestHistoryRepository;
// import com.mfc.coordinating.requests.infrastructure.RequestsRepository;
//
// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Service
// @Slf4j
// @RequiredArgsConstructor
// @Transactional
// public class RequestsServiceImpl implements RequestsService {
// 	private final RequestsRepository requestsRepository;
// 	private final RequestHistoryRepository requestHistoryRepository;
// 	private final MyImageRepository myImageRepository;
// 	private final ReferenceImageRepository referenceImageRepository;
// 	private final CategoryRepository categoryRepository;
// 	private final BrandRepository brandRepository;
// 	private final RequestsEventProducer requestsEventProducer;
// 	private final ModelMapper modelMapper;
//
//
// 	@Override
// 	public void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid) {
// 		Requests requests = Requests.builder()
// 			.userId(uuid)
// 			.title(requestsCreateReqDto.getTitle())
// 			.description(requestsCreateReqDto.getDescription())
// 			.situation(requestsCreateReqDto.getSituation())
// 			.budget(requestsCreateReqDto.getBudget())
// 			.otherRequirements(requestsCreateReqDto.getOtherRequirements())
// 			.build();
//
// 		Requests savedRequests = requestsRepository.save(requests);
//
// 		List<ReferenceImage> referenceImages = requestsCreateReqDto.getReferenceImages().stream()
// 			.map(url -> ReferenceImage.builder().url(url).requests(savedRequests).build())
// 			.toList();
// 		referenceImageRepository.saveAll(referenceImages);
//
// 		List<MyImage> myImages = requestsCreateReqDto.getMyImages().stream()
// 			.map(url -> MyImage.builder().url(url).requests(savedRequests).build())
// 			.toList();
// 		myImageRepository.saveAll(myImages);
//
// 		List<Category> categories = requestsCreateReqDto.getCategory().stream()
// 			.map(name -> Category.builder().name(name).requests(savedRequests).build())
// 			.toList();
// 		categoryRepository.saveAll(categories);
//
// 		List<Brand> brands = requestsCreateReqDto.getBrand().stream()
// 			.map(name -> Brand.builder().name(name).requests(savedRequests).build())
// 			.toList();
// 		brandRepository.saveAll(brands);
// 	}
//
// 	@Override
// 	public List<RequestsListResDto> getRequestsListByUser(int page, int pageSize, RequestsListSortType sortType, String uuid) {
// 		Pageable pageable = getPageable(page, pageSize, sortType);
//
// 		Page<RequestHistory> requestHistoryPage = requestHistoryRepository.findByUserId(uuid, pageable);
//
// 		return requestHistoryPage.getContent().stream()
// 			.map(RequestHistory::toDto)
// 			.toList();
// 	}
//
// 	public RequestsListResDto getRequestHistoryById(ObjectId id) {
// 		return requestHistoryRepository.findById(id)
// 			.map(history -> modelMapper.map(history, RequestsListResDto.class))
// 			.orElseThrow(() -> new IllegalArgumentException("Invalid id"));
// 	}
//
// 	@Override
// 	public List<MyRequestListResponse> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid) {
// 		Pageable pageable = getPageable(page, pageSize, sortType);
// 		return requestsRepository.findByUserId(uuid, pageable).getContent();
// 	}
//
// 	@Override
// 	public RequestsDetailResDto getRequestsDetail(Long requestId) {
// 		Requests requests = requestsRepository.findByRequestId(requestId)
// 			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 		List<String> categoryNames = categoryRepository.findByRequestId(requestId)
// 			.stream()
// 			.map(Category::getName)
// 			.toList();
//
// 		List<String> brandNames = brandRepository.findByRequestId(requestId)
// 			.stream()
// 			.map(Brand::getName)
// 			.toList();
//
// 		List<String> referenceImageUrls = referenceImageRepository.findByRequestId(requestId)
// 			.stream()
// 			.map(ReferenceImage::getUrl)
// 			.toList();
//
// 		List<String> myImageUrls = myImageRepository.findByRequestId(requestId)
// 			.stream()
// 			.map(MyImage::getUrl)
// 			.toList();
//
// 		return RequestsDetailResDto.toBuild(requests, referenceImageUrls, myImageUrls, categoryNames, brandNames);
// 	}
//
// 	@Override
// 	public void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid) {
// 		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
// 			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 		requests.updateRequests(dto.getTitle(), dto.getDescription(), dto.getSituation(), dto.getBudget(),
// 			dto.getOtherRequirements());
//
// 		referenceImageRepository.deleteByRequestId(requestId);
// 		List<ReferenceImage> referenceImages = dto.getReferenceImages().stream()
// 			.map(url -> ReferenceImage.builder().url(url).requests(requests).build())
// 			.toList();
// 		referenceImageRepository.saveAll(referenceImages);
//
// 		myImageRepository.deleteByRequestId(requestId);
// 		List<MyImage> myImages = dto.getMyImages().stream()
// 			.map(url -> MyImage.builder().url(url).requests(requests).build())
// 			.toList();
// 		myImageRepository.saveAll(myImages);
//
// 		categoryRepository.deleteByRequestId(requestId);
// 		List<Category> categories = dto.getCategory().stream()
// 			.map(name -> Category.builder().name(name).requests(requests).build())
// 			.toList();
// 		categoryRepository.saveAll(categories);
//
// 		brandRepository.deleteByRequestId(requestId);
// 		List<Brand> brands = dto.getBrand().stream()
// 			.map(name -> Brand.builder().name(name).requests(requests).build())
// 			.toList();
// 		brandRepository.saveAll(brands);
// 	}
//
// 	@Override
// 	public void deleteRequests(Long requestId, String uuid) {
// 		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
// 			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 		referenceImageRepository.deleteByRequestId(requestId);
// 		myImageRepository.deleteByRequestId(requestId);
// 		requestsRepository.delete(requests);
// 		categoryRepository.deleteByRequestId(requestId);
// 		brandRepository.deleteByRequestId(requestId);
// 	}
//
// 	@Override
// 	public void updateProposal(Long requestId, String partnerId, String uuid, LocalDate deadline) {
// 		RequestsStates states = RequestsStates.NONERESPONSE;
// 		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
// 			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 		if (partnerId.equals(uuid)) {
// 			throw new BaseException(BaseResponseStatus.NON_SELF_MEMBERS);
// 		}
//
// 		// Kafka를 통해 유저 정보 요청 이벤트 발행
// 		requestsEventProducer.sendRequestsHistoryCreateEvent(requestId, uuid, partnerId, deadline);
// 	}
//
// 	@KafkaListener(topics = "user-info-response", containerFactory = "kafkaListenerContainerFactory")
// 	public void handleUserInfoResponse(String message) {
// 		try {
// 			// ObjectMapper를 사용하여 JSON 메시지 파싱
// 			ObjectMapper objectMapper = new ObjectMapper();
// 			JsonNode jsonNode = objectMapper.readTree(message);
//
// 			Long requestId = jsonNode.get("requestId").asLong();
// 			String userId = jsonNode.get("userId").asText();
// 			String partnerId = jsonNode.get("partnerId").asText();
// 			LocalDate deadline = LocalDate.parse(jsonNode.get("deadline").asText());
// 			String userImageUrl = jsonNode.get("userImageUrl").asText();
// 			String userNickName = jsonNode.get("userNickName").asText();
// 			Short userGender = jsonNode.get("userGender").shortValue();
// 			LocalDate userBirth = LocalDate.parse(jsonNode.get("userBirth").asText());
//
// 			// requestId를 기반으로 Requests 엔티티 조회
// 			Requests requests = requestsRepository.findByRequestId(requestId)
// 				.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 			// RequestHistory 엔티티 생성
// 			RequestHistory requestHistory = RequestHistory.builder()
// 				.requestId(requestId)
// 				.userId(userId)
// 				.partnerId(partnerId)
// 				.deadline(deadline)
// 				.status(RequestsStates.NONERESPONSE)
// 				.title(requests.getTitle())
// 				.userImageUrl(userImageUrl)
// 				.userNickName(userNickName)
// 				.userGender(userGender)
// 				.userAge(calculateAge(userBirth))
// 				.build();
//
// 			// RequestHistory 엔티티 저장
// 			try {
// 				requestHistoryRepository.save(requestHistory);
// 			} catch (Exception e) {
// 				// 예외 처리 로직 추가
// 				e.printStackTrace();
// 				// 필요한 경우 예외를 throw하거나 다른 방식으로 처리할 수 있습니다.
// 				throw new RuntimeException("Failed to save RequestHistory", e);
// 			}
// 		} catch (Exception e) {
// 			// 예외 처리 로직 추가
// 			e.printStackTrace();
// 			// 필요한 경우 예외를 throw하거나 다른 방식으로 처리할 수 있습니다.
// 			throw new RuntimeException("Failed to handle user info response", e);
// 		}
// 	}
//
// 	private int calculateAge(LocalDate birthDate) {
// 		LocalDate currentDate = LocalDate.now();
// 		return Period.between(birthDate, currentDate).getYears();
// 	}
//
// 	@Override
// 	public List<RequestsListResDto> getRequestsListPartner(int page, int pageSize, RequestsListSortType sortType, String uuid) {
// 		Pageable pageable = getPageable(page, pageSize, sortType);
//
// 		Page<RequestHistory> requestHistoryPage = requestHistoryRepository.findByPartnerId(uuid, pageable);
//
// 		return requestHistoryPage.getContent().stream()
// 			.map(history -> RequestsListResDto.builder()
// 				.requestId(history.getRequestId())
// 				.userId(history.getUserId())
// 				.title(history.getTitle())
// 				.partnerId(history.getPartnerId())
// 				.deadline(history.getDeadline())
// 				.status(history.getStatus())
// 				.build())
// 			.toList();
// 	}
//
// 	@Override
// 	public void updateAcceptRequests(ObjectId historyId, String uuid) {
// 		RequestsStates states = RequestsStates.RESPONSEACCEPT;
// 		Optional<RequestHistory> optionalRequestHistory = requestHistoryRepository.findById(historyId);
// 		RequestHistory requestHistory = optionalRequestHistory
// 			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 		requestHistory.updateStatus(states);
// 	}
//
// 	@Override
// 	public void updateRejectRequests(ObjectId historyId, String uuid) {
// 		RequestsStates states = RequestsStates.RESPONSEREJECT;
// 		Optional<RequestHistory> optionalRequestHistory = requestHistoryRepository.findById(historyId);
// 		RequestHistory requestHistory = optionalRequestHistory
// 			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));
//
// 		requestHistory.updateStatus(states);
// 	}
//
// 	private Pageable getPageable(int page, int pageSize, RequestsListSortType sortType) {
// 		if (sortType == RequestsListSortType.LATEST) {
// 			return PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
// 		} else if (sortType == RequestsListSortType.DEADLINE_ASC) {
// 			return PageRequest.of(page, pageSize, Sort.by("deadline").ascending());
// 		} else {
// 			return PageRequest.of(page, pageSize, Sort.by("deadline").descending());
// 		}
// 	}
// }
package com.mfc.coordinating.requests.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.kafka.AuthInfoResponseDto;
import com.mfc.coordinating.requests.dto.kafka.UserInfoResponseDto;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.enums.RequestsStates;
import com.mfc.coordinating.requests.infrastructure.RequestsRepository;
import com.mfc.coordinating.requests.mapper.RequestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RequestsServiceImpl implements RequestsService {
	private final RequestsRepository requestsRepository;
	private final RequestsEventProducer requestsEventProducer;
	private final RequestsEventConsumer requestsEventConsumer;
	private final ObjectMapper objectMapper;
	private final RequestMapper requestMapper;

	@Override
	public void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid) {
		// 유저 정보 요청 이벤트 발생
		requestsEventProducer.requestUserInfo(uuid);

		// Auth 정보 요청 이벤트 발생
		requestsEventProducer.requestAuthInfo(uuid);

		// 유저 정보와 Auth 정보 추출
		UserInfoResponseDto userInfoResponse = requestsEventConsumer.getUserInfoResponse();
		AuthInfoResponseDto authInfoResponse = requestsEventConsumer.getAuthInfoResponse();

		String userImageUrl = userInfoResponse.getUserImageUrl();
		String userNickName = userInfoResponse.getUserNickName();
		Short userGender = authInfoResponse.getUserGender();
		LocalDate userBirth = authInfoResponse.getUserBirth();

		int userAge = LocalDate.now().getYear() - userBirth.getYear();

		// Requests 엔티티 생성 및 저장
		Requests requests = getRequests(requestsCreateReqDto, uuid, userImageUrl,
			userNickName, userGender, userAge);

		requestsRepository.save(requests);
	}

	@Override
	public List<MyRequestListResponse> getMyRequestsList(int page, int pageSize, RequestsListSortType sortType,
		String userId) {
		Pageable pageable = getPageable(page, pageSize, sortType);
		Page<Requests> requestsPage = requestsRepository.findByUserId(userId, pageable);

		return requestsPage.getContent()
			.stream()
			.map(requestMapper::toMyRequestListResponse)
			.toList();
	}

	@Override
	public List<RequestsListResDto> getPartnerRequestsList(int page, int pageSize, RequestsListSortType sortType, String partnerId) {
		Pageable pageable = getPageable(page, pageSize, sortType);
		Page<Requests> requestsPage = requestsRepository.findByPartnerId(partnerId, pageable);

		return requestsPage.getContent().stream()
			.map(request -> requestMapper.toRequestsListResDto(request, partnerId))
			.filter(Objects::nonNull)
			.toList();
	}
	@Override
	public List<RequestsListResDto> getUserRequestsList(int page, int pageSize, RequestsListSortType sortType,
		String userId) {
		Pageable pageable = getPageable(page, pageSize, sortType);
		Page<Requests> requestsPage = requestsRepository.findByUserId(userId, pageable);

		return getRequestsListResDtos(requestsPage);
	}

	@Override
	public RequestsDetailResDto getRequestsDetail(String requestId) {
		Requests requests = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		return getRequestsDetailResDto(requests);
	}

	@Transactional
	@Override
	public void updateRequests(RequestsUpdateReqDto dto, String requestId, String uuid) {
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.updateRequests(
			dto.getTitle(),
			dto.getDescription(),
			dto.getSituation(),
			dto.getBudget(),
			dto.getOtherRequirements(),
			dto.getBrandIds(),
			dto.getCategoryIds(),
			dto.getReferenceImageUrls(),
			dto.getMyImageUrls()
		);
	}

	@Transactional
	@Override
	public void deleteRequests(String requestId, String uuid) {
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestsRepository.delete(requests);
	}

	@Transactional
	@Override
	public void updateProposal(String requestId, String partnerId, String uuid, LocalDate deadline) {
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, uuid)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		if (partnerId.equals(uuid)) {
			throw new BaseException(BaseResponseStatus.NON_SELF_MEMBERS);
		}

		requests.addPartnerInfo(partnerId, RequestsStates.NONERESPONSE, deadline);
		requestsRepository.save(requests);

		// Kafka를 통해 파트너에게 알림 이벤트 발행
		//requestsEventProducer.sendPartnerNotificationEvent(requestId, partnerId);
	}

	@Transactional
	@Override
	public void updatePartnerResponse(String requestId, String partnerId, String uuid, RequestsStates status) {
		Requests requests = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.updatePartnerStatus(partnerId, status);
		requestsRepository.save(requests);
	}

	private Pageable getPageable(int page, int pageSize, RequestsListSortType sortType) {
		Sort sort = switch (sortType) {
			case LATEST -> Sort.by("createdDate").descending();
			case DEADLINE_ASC -> Sort.by("partner.deadline").ascending();
			case DEADLINE_DESC -> Sort.by("partner.deadline").descending();
		};

		return PageRequest.of(page, pageSize, sort);
	}

	private String generateRequestId() {
		return String.valueOf(System.currentTimeMillis());
	}

	private static RequestsDetailResDto getRequestsDetailResDto(Requests requests) {
		return RequestsDetailResDto.builder()
			.requestId(requests.getRequestId())
			.userId(requests.getUserId())
			.title(requests.getTitle())
			.description(requests.getDescription())
			.situation(requests.getSituation())
			.budget(requests.getBudget())
			.otherRequirements(requests.getOtherRequirements())
			.userImageUrl(requests.getUserImageUrl())
			.userNickName(requests.getUserNickName())
			.userGender(requests.getUserGender())
			.userAge(requests.getUserAge())
			.createdDate(requests.getCreatedDate())
			.partner(requests.getPartner())
			.brandIds(requests.getBrandIds())
			.categoryIds(requests.getCategoryIds())
			.referenceImageUrls(requests.getReferenceImageUrls())
			.myImageUrls(requests.getMyImageUrls())
			.build();
	}

	private static List<RequestsListResDto> getRequestsListResDtos(Page<Requests> requestsPage) {
		return requestsPage.getContent().stream()
			.flatMap(request -> request.getPartner().stream()
				.map(partner -> RequestsListResDto.builder()
					.requestId(request.getRequestId())
					.userId(request.getUserId())
					.userImageUrl(request.getUserImageUrl())
					.userNickName(request.getUserNickName())
					.userGender(request.getUserGender())
					.userAge(request.getUserAge())
					.title(request.getTitle())
					.createdDate(request.getCreatedDate())
					.partnerId(partner.getPartnerId())
					.status(partner.getStatus())
					.deadline(partner.getDeadline())
					.build()))
			.toList();
	}

	private Requests getRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid, String userImageUrl,
		String userNickName, Short userGender, int userAge) {
		return Requests.builder()
			.requestId(generateRequestId())
			.userId(uuid)
			.title(requestsCreateReqDto.getTitle())
			.description(requestsCreateReqDto.getDescription())
			.situation(requestsCreateReqDto.getSituation())
			.budget(requestsCreateReqDto.getBudget())
			.otherRequirements(requestsCreateReqDto.getOtherRequirements())
			.userImageUrl(userImageUrl)
			.userNickName(userNickName)
			.userGender(userGender)
			.userAge(userAge)
			.brandIds(requestsCreateReqDto.getBrandIds())
			.categoryIds(requestsCreateReqDto.getCategoryIds())
			.referenceImageUrls(requestsCreateReqDto.getReferenceImageUrls())
			.myImageUrls(requestsCreateReqDto.getMyImageUrls())
			.build();
	}

}