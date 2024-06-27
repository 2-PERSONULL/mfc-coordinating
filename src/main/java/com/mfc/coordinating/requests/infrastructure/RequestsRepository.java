package com.mfc.coordinating.requests.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.enums.RequestsStates;

@Repository
public interface RequestsRepository extends MongoRepository<Requests, String> {

	Page<Requests> findByUserId(String userId, Pageable pageable);

	Optional<Requests> findByRequestId(String requestId);

	Optional<Requests> findByRequestIdAndUserId(String requestId, String userId);

	@Aggregation(pipeline = {
		"{ $match: { 'partner.partnerId': ?0 } }",
		"{ $addFields: { 'partner': { $filter: { input: '$partner', as: 'p', cond: { $eq: ['$$p.partnerId', ?0] } } } } }",
		"{ $match: { $or: [ { 'partner.status': ?1 }, { $expr: { $eq: [?1, null] } } ] } }"
	})
	Page<Requests> findByPartnerId(String partnerId, RequestsStates status, Pageable pageable);

	@Aggregation(pipeline = {
		"{ $match: { 'userId': ?0 } }",
		"{ $match: { $or: [ { 'partner.status': ?1 }, { $expr: { $eq: [?1, null] } } ] } }"
	})
	Page<Requests> findByUserIdAndStatus(String userId, RequestsStates status, Pageable pageable);
}