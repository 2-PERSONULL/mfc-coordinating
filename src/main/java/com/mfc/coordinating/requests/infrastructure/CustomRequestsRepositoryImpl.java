package com.mfc.coordinating.requests.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.enums.RequestsStates;

public class CustomRequestsRepositoryImpl implements CustomRequestsRepository {

	private final MongoTemplate mongoTemplate;

	public CustomRequestsRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Page<Requests> findByUserIdAndStatus(String userId, RequestsStates status, Pageable pageable) {
		Criteria criteria = Criteria.where("userId").is(userId);

		AggregationOperation match = Aggregation.match(criteria);
		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(match);

		if (status != null) {
			AggregationOperation filter = Aggregation.project("userId", "requestId", "title", "description")
				.and(ArrayOperators.Filter.filter("partner")
					.as("item")
					.by(ComparisonOperators.Eq.valueOf("item.status").equalToValue(status)))
				.as("partner");
			operations.add(filter);
		}

		operations.add(Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()));
		operations.add(Aggregation.limit(pageable.getPageSize()));

		Aggregation aggregation = Aggregation.newAggregation(operations);

		AggregationResults<Requests> results = mongoTemplate.aggregate(aggregation, "requests", Requests.class);
		List<Requests> requests = results.getMappedResults();

		long count = mongoTemplate.count(Query.query(criteria), Requests.class);

		return PageableExecutionUtils.getPage(
			requests,
			pageable,
			() -> count
		);
	}
	@Override
	public Page<Requests> findByPartnerIdAndStatus(String partnerId, RequestsStates status, Pageable pageable) {
		Criteria criteria = Criteria.where("partner.partnerId").is(partnerId);
		if (status != null) {
			criteria = criteria.and("partner.status").is(status);
		}

		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(Aggregation.match(criteria));

		AggregationOperation filterPartners = Aggregation.project("userId", "requestId", "title", "description",
				"userImageUrl", "userNickName", "userGender", "userAge", "createdDate", "situation", "budget")
			.and(ArrayOperators.Filter.filter("partner")
				.as("item")
				.by(ComparisonOperators.Eq.valueOf("item.partnerId").equalToValue(partnerId)))
			.as("partner");
		operations.add(filterPartners);

		// 정렬 적용
		if (pageable.getSort().isSorted()) {
			operations.add(Aggregation.sort(pageable.getSort()));
		}

		operations.add(Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()));
		operations.add(Aggregation.limit(pageable.getPageSize()));

		Aggregation aggregation = Aggregation.newAggregation(operations);

		AggregationResults<Requests> results = mongoTemplate.aggregate(aggregation, "requests", Requests.class);
		List<Requests> requests = results.getMappedResults();

		// 필터링된 총 문서 수 계산
		long count = mongoTemplate.count(Query.query(criteria), Requests.class);

		return PageableExecutionUtils.getPage(
			requests,
			pageable,
			() -> count
		);
	}
}
