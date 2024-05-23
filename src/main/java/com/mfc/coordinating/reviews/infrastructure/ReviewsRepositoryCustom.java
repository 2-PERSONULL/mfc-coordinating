package com.mfc.coordinating.reviews.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mfc.coordinating.reviews.entity.Reviews;

public interface ReviewsRepositoryCustom {
	Page<Reviews> findByPartnerId(String partnerId, Pageable pageable);
}
