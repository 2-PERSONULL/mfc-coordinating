package com.mfc.coordinating.requests.application;

import java.util.List;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.vo.req.RequestsUpdateReqVo;

public interface RequestsService {

	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);

	List<RequestsListResDto> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid);

	RequestsDetailResDto getRequestsDetail(Long requestId, String uuid);

	void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid);
}
