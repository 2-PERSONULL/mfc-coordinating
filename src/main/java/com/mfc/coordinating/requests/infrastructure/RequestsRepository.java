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

	Page<Requests> findByUserId(String userId, Pageable pageable);

	Optional<Requests> findByRequestId(Long requestId);

	Optional<Requests> findByRequestIdAndUserId(Long requestId, String userId);

	@Query("{'partner.partnerId': ?0}")
	Page<Requests> findByPartnerId(String partnerId, Pageable pageable);

	@Query("{ '$or': [ { 'userId': ?0 }, { 'partners.partnerId': ?0 } ] }")
	Page<Requests> findByUserOrPartnerId(String id, Pageable pageable);
}