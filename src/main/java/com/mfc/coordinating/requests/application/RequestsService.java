package com.mfc.coordinating.requests.application;

import org.springframework.data.domain.Page;

import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;

public interface RequestsService {

	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);

	Page<RequestsListResDto> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid);
}
