package com.mfc.coordinating.reviews.application;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mfc.coordinating.reviews.dto.request.ReviewsRequest;
import com.mfc.coordinating.reviews.entity.Reviews;

public interface ReviewsService {
	void createReview(ReviewsRequest request);
	Page<Reviews> getReviewsByPartner(String partnerId, int page, int pageSize);
	Reviews getReviewById(Long reviewId);
	void updateReview(Long reviewId, String comment, List<String> reviewImage);
	void deleteReview(Long reviewId);
}
