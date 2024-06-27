package com.mfc.coordinating.requests.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.enums.RequestsStates;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {

	@Query("{'userId': ?0}")
	Page<Requests> findByUserId(String userId, Pageable pageable);

	Optional<Requests> findByRequestId(String requestId);

	Optional<Requests> findByRequestIdAndUserId(String requestId, String userId);

	@Query("{'partner': {$elemMatch: {'partnerId': ?0, $or: [{'status': ?1}, {$expr: {$eq: [?1, null]}}]}}}")
	Page<Requests> findByPartnerId(String partnerId, RequestsStates status, Pageable pageable);

	@Query("{'userId': ?0, 'partner': {$elemMatch: {$or: [{'status': ?1}, {$expr: {$eq: [?1, null]}}]}}}")
	Page<Requests> findByUserIdAndStatus(String userId, RequestsStates status, Pageable pageable);
}