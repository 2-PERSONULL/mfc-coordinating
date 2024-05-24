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
import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.enums.RequestsStates;
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

	@Override
	public void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid) {
		Requests requests = Requests.builder()
			.userId(uuid)
			.title(requestsCreateReqDto.getTitle())
			.description(requestsCreateReqDto.getDescription())
			.options(requestsCreateReqDto.getOptions())
			.totalPrice(requestsCreateReqDto.getTotalPrice())
			.situation(requestsCreateReqDto.getSituation())
			.referenceImages(requestsCreateReqDto.getReferenceImages())
			.myImages(requestsCreateReqDto.getMyImages())
			.budget(requestsCreateReqDto.getBudget())
			.brand(requestsCreateReqDto.getBrand())
			.otherRequirements(requestsCreateReqDto.getOtherRequirements())
			.deadline(requestsCreateReqDto.getDeadline())
			.state(requestsCreateReqDto.getState())
			.partnerId(requestsCreateReqDto.getPartnerId())
			.build();

		requestsRepository.save(requests);
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
		List<RequestsListResDto> requestsList = new ArrayList<>();

		Page<Object[]> requestPage = requestsRepository.findByUserId(userId, pageable);

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
	public RequestsDetailResDto getRequestsDetail(Long requestId) {
		Requests requests = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		return RequestsDetailResDto.toBuild(requests);
	}

	@Override
	public void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid) {
		String userId = uuid;
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestsRepository.save(Requests.builder()
				.requestId(requests.getRequestId())
				.userId(requests.getUserId())
				.title(dto.getTitle())
				.description(dto.getDescription())
				.options(dto.getOptions())
				.totalPrice(dto.getTotalPrice())
				.situation(dto.getSituation())
				.referenceImages(dto.getReferenceImages())
				.myImages(dto.getMyImages())
				.budget(dto.getBudget())
				.brand(dto.getBrand())
				.otherRequirements(dto.getOtherRequirements())
				.deadline(dto.getDeadline())
				.state(dto.getState())
				.partnerId(dto.getPartnerId())
			.build());
	}

	@Override
	public void deleteRequests(Long requestId, String uuid) {
		String userId = uuid;
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requestsRepository.deleteByRequestId(requestId);

		System.out.println("RequestsServiceImpl.deleteRequests");
	}

	@Override
	public void updateProposal(Long requestId, String partnerId, String uuid) {
		String userId = uuid;
		RequestsStates states = RequestsStates.valueOf("NONERESPONSE");
		Requests requests = requestsRepository.findByRequestIdAndUserId(requestId, userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.setState(states);
		requests.setPartnerId(partnerId);

		requestsRepository.save(requests);
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

		Page<Object[]> requestPage = requestsRepository.findByPartnerId(partnerId, pageable);

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
	public void updateAcceptRequests(Long requestId, String uuid) {
		String partnerId = uuid;
		RequestsStates states = RequestsStates.valueOf("RESPONSEACCEPT");
		Requests requests = requestsRepository.findByRequestIdAndPartnerId(requestId, partnerId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.setState(states);

		requestsRepository.save(requests);
	}

	@Override
	public void updateRejectRequests(Long requestId, String uuid) {
		String partnerId = uuid;
		RequestsStates states = RequestsStates.valueOf("RESPONSEREJECT");
		Requests requests = requestsRepository.findByRequestIdAndPartnerId(requestId, partnerId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATING_REQUESTS_NOT_FOUND));

		requests.setState(states);

		requestsRepository.save(requests);
	}
}
