package com.mfc.coordinating.requests.application;

import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;

public interface RequestsService {

	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);

}
