package com.mfc.coordinating.requests.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.infrastructure.RequestsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
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
			.build();

		requestsRepository.save(requests);
	}

	@Override
	public Page<RequestsListResDto> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid) {
		Pageable pageable;
		String userId = uuid;

		if (sortType == RequestsListSortType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == RequestsListSortType.DEADLINE_ASC) {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").ascending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("deadline").descending());
		}

		Page<Object[]> requestPage = requestsRepository.findByUserId(userId, pageable);
		return requestPage.map(result -> {
			Long requestsId = (Long)result[0];
			String title  = (String)result[1];
			String description = (String)result[2];
			LocalDate deadline = (LocalDate)result[3];

			return new RequestsListResDto(requestsId, title, description, deadline);
		});


	}
}
