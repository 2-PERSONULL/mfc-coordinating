package com.mfc.coordinating.requests.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfc.coordinating.requests.domain.Requests;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {

	@Query("{'userId': ?0}")
	Page<Requests> findByUserId(String userId, Pageable pageable);

	Optional<Requests> findByRequestId(String requestId);

	Optional<Requests> findByRequestIdAndUserId(String requestId, String userId);

	@Query("{'partner.partnerId': ?0}")
	Page<Requests> findByPartnerId(String partnerId, Pageable pageable);
}