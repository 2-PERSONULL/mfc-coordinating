package com.mfc.coordinating.requests.application;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;

public interface RequestsService {

	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);

	List<RequestsListResDto> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid);

	RequestsDetailResDto getRequestsDetail(Long requestId, String uuid);
}
