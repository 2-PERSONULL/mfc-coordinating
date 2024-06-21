package com.mfc.coordinating.reviews.dto.response;

import java.util.List;

import com.mfc.coordinating.reviews.entity.ReviewImage;
import com.mfc.coordinating.reviews.entity.Reviews;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewsResponse {
	private Long reviewId;
	private Long requestId;
	private String userId;
	private String partnerId;
	private Short rating;
	private String comment;
	private List<String> reviewImage;

	public static ReviewsResponse from(Reviews review) {
		return ReviewsResponse.builder()
			.reviewId(review.getReviewId())
			.requestId(review.getRequestId())
			.userId(review.getUserId())
			.partnerId(review.getPartnerId())
			.rating(review.getRating())
			.comment(review.getComment())
			.reviewImage(review.getReviewImages().stream()
				.map(ReviewImage::getImageUrl)
				.toList())
			.build();
	}
}