package com.mfc.coordinating.reviews.application;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mfc.coordinating.reviews.entity.Reviews;

public interface ReviewsService {
	void createReview(Long requestId, String userId, String partnerId, Byte rating, String comment,
		List<String> reviewImageUrls);
	Page<Reviews> getReviewsByPartner(String partnerId, int page, int pageSize);
	Reviews getReviewById(Long reviewId);
	void updateReview(Long reviewId, String comment, List<String> reviewImage);
	void deleteReview(Long reviewId);
	Integer getReviewCount(String partnerId);
}
