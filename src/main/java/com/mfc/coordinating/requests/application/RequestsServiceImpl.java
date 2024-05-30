package com.mfc.coordinating.requests.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.requests.domain.MyImage;
import com.mfc.coordinating.requests.domain.ReferenceImage;
import com.mfc.coordinating.requests.domain.RequestHistory;
import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.enums.RequestsStates;
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
public class RequestsServiceImpl implements RequestsService{
	private final RequestsRepository requestsRepository;
	private final RequestHistoryRepository requestHistoryRepository;
	private final MyImageRepository myImageRepository;
	private final ReferenceImageRepository referenceImageRepository;

	@Override
	public void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid) {
		Requests requests = Requests.builder()
			.userId(uuid)
			.title(requestsCreateReqDto.getTitle())
			.description(requestsCreateReqDto.getDescription())
			.situation(requestsCreateReqDto.getSituation())
			.budget(requestsCreateReqDto.getBudget())
			.brand(requestsCreateReqDto.getBrand())
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
	}

	@Override
	public List<RequestsListResDto> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable;
		String userId = uuid;

		if (sortType == RequestsListSortType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == RequestsListSortType.DEADLINE_ASC) {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").ascending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").descending());
		}

		Page<RequestHistory> requestHistoryPage = requestHistoryRepository.findByUserId(userId, pageable);

		return requestHistoryPage.getContent().stream()
			.map(history -> {
				Requests request = requestsRepository.findById(history.getRequestId())
					.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

				return RequestsListResDto.builder()
					.requestId(request.getRequestId())
					.title(request.getTitle())
					.description(request.getDescription())
					.deadline(history.getDeadline())
					.build();
			})
			.toList();
	}

	@Override
	public List<RequestsListResDto> getRequestsListByUser(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable;
		String userId = uuid;

		if (sortType == RequestsListSortType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == RequestsListSortType.DEADLINE_ASC) {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").ascending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").descending());
		}

		Page<Requests> requestsPage = requestsRepository.findByUserId(userId, pageable);

		return requestsPage.getContent().stream()
			.map(request -> RequestsListResDto.builder()
				.requestId(request.getRequestId())
				.title(request.getTitle())
				.description(request.getDescription())
				.build())
			.toList();
	}

	@Override
	public RequestsDetailResDto getRequestsDetail(Long requestId) {
		Requests requests = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		List<String> referenceImageUrls = referenceImageRepository.findByRequestId(requestId)
			.stream()
			.map(ReferenceImage::getUrl)
			.toList();

		List<String> myImageUrls = myImageRepository.findByRequestId(requestId)
			.stream()
			.map(MyImage::getUrl)
			.toList();

		return RequestsDetailResDto.toBuild(requests, referenceImageUrls, myImageUrls);
	}
	@Override
	public void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid) {
		String userId = uuid;
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.updateRequests(dto.getTitle(), dto.getDescription(), dto.getSituation(), dto.getBudget(),
			dto.getBrand(), dto.getOtherRequirements());

		referenceImageRepository.deleteByRequestId(requestId);
		List<ReferenceImage> referenceImages = dto.getReferenceImages().stream()
			.map(url -> ReferenceImage.builder().url(url).requests(requests).build())
			.toList();
		referenceImageRepository.saveAll(referenceImages);

		myImageRepository.deleteByRequestId(requestId);
		List<MyImage> myImages = dto.getMyImages().stream()
			.map(url -> MyImage.builder().url(url).requests(requests).build())
			.toList();
	}

	@Override
	public void deleteRequests(Long requestId, String uuid) {
		String userId = uuid;
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		referenceImageRepository.deleteByRequestId(requestId);
		myImageRepository.deleteByRequestId(requestId);
		requestsRepository.delete(requests);

		System.out.println("RequestsServiceImpl.deleteRequests");
	}

	@Override
	public void updateProposal(Long requestId, String partnerId, String uuid, LocalDate deadline) {
		String userId = uuid;
		RequestsStates states = RequestsStates.NONERESPONSE;
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		RequestHistory requestHistory = RequestHistory.builder()
			.requestId(requestId)
			.userId(Long.parseLong(userId))
			.partnerId(partnerId)
			.deadline(deadline)
			.status(states)
			.build();

		requestHistoryRepository.save(requestHistory);
	}

	@Override
	public List<RequestsListResDto>  getRequestsListPartner(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable;
		String partnerId = uuid;

		if (sortType == RequestsListSortType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == RequestsListSortType.DEADLINE_ASC) {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").ascending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").descending());
		}
		List<RequestsListResDto> requestsList = new ArrayList<>();

		Page<Object[]> requestPage = requestHistoryRepository.findByPartnerId(partnerId, pageable);

		int index = 0;
		int returnListSize = requestPage.getContent().size();
		if (returnListSize < pageSize){
			pageSize = returnListSize;
		}
		for (int i = 0; i < pageSize; i++) {
			requestsList.add(new RequestsListResDto((long)i, null, null, null, null));
		}
		for (Object[] result : requestPage.getContent()) {
			Long requestsId = (Long)result[0];
			String title  = (String)result[1];
			String description = (String)result[2];
			LocalDate deadline = (LocalDate)result[3];
			requestsList.get(index).setRequestId(requestsId);
			requestsList.get(index).setTitle(title);
			requestsList.get(index).setDescription(description);
			requestsList.get(index).setDeadline(deadline);
			index++;
		}
		return requestsList;
	}

	@Override
	public void updateAcceptRequests(Long historyId, String uuid) {
		String partnerId = uuid;
		RequestsStates states = RequestsStates.RESPONSEACCEPT;
		RequestHistory requestHistory = requestHistoryRepository.findById(historyId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestHistory.updateStatus(states);

	}

	@Override
	public void updateRejectRequests(Long historyId, String uuid) {
		String partnerId = uuid;
		RequestsStates states = RequestsStates.RESPONSEREJECT;
		RequestHistory requestHistory = requestHistoryRepository.findById(historyId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestHistory.updateStatus(states);

	}
}