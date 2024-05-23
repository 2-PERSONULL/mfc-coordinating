package com.mfc.coordinating.reviews.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.mfc.coordinating.reviews.entity.QReviews;
import com.mfc.coordinating.reviews.entity.Reviews;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ReviewsRepositoryCustomImpl implements ReviewsRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public ReviewsRepositoryCustomImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<Reviews> findByPartnerId(String partnerId, Pageable pageable) {
		QReviews qReviews = QReviews.reviews;

		JPAQuery<Reviews> query = queryFactory
			.selectFrom(qReviews)
			.where(eqPartnerId(partnerId))
			.orderBy(qReviews.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		JPAQuery<Long> countQuery = queryFactory
			.select(qReviews.count())
			.from(qReviews)
			.where(eqPartnerId(partnerId));

		return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchOne);
	}

	private BooleanExpression eqPartnerId(String partnerId) {
		return partnerId != null ? QReviews.reviews.partnerId.eq(partnerId) : null;
	}
}