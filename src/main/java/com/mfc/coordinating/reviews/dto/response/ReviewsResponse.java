package com.mfc.coordinating.reviews.dto.response;

import com.mfc.coordinating.reviews.entity.Reviews;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsResponse {
	private Long reviewId;
	private Long requestId;
	private String userId;
	private String partnerId;
	private Byte rating;
	private String comment;
	private String reviewImage;

	public static ReviewsResponse from(Reviews review) {
		ReviewsResponse response = new ReviewsResponse();
		response.setReviewId(review.getReviewId());
		response.setRequestId(review.getRequestId());
		response.setUserId(review.getUserId());
		response.setPartnerId(review.getPartnerId());
		response.setRating(review.getRating());
		response.setComment(review.getComment());
		response.setReviewImage(review.getReviewImage());
		return response;
	}
}