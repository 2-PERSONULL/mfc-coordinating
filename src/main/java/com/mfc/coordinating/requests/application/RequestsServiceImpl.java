package com.mfc.coordinating.requests.application;

import org.springframework.stereotype.Service;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
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
}
