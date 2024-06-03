package com.mfc.coordinating.requests.application;

import java.time.LocalDate;
import java.util.List;

import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;

public interface RequestsService {

	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);

	List<RequestsListResDto> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid);

	List<RequestsListResDto> getRequestsListByUser(int page, int pageSize, RequestsListSortType sortType, String uuid);

	RequestsDetailResDto getRequestsDetail(Long requestId);

	void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid);

	void deleteRequests(Long requestId, String uuid);

	void updateProposal(Long requestId, String partnerId, String uuid, LocalDate deadline);

	List<RequestsListResDto> getRequestsListPartner(int page, int pageSize, RequestsListSortType sortType, String uuid);

	void updateAcceptRequests(Long requestId, String uuid);

	void updateRejectRequests(Long requestId, String uuid);

}
