package com.mfc.coordinating.requests.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.enums.RequestsStates;

public interface CustomRequestsRepository {
	Page<Requests> findByUserIdAndStatus(String userId, RequestsStates status, Pageable pageable);
	Page<Requests> findByPartnerIdAndStatus(String partnerId, RequestsStates status, Pageable pageable);
}