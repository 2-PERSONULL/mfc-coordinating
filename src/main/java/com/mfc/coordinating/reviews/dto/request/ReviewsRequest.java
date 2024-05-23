package com.mfc.coordinating.reviews.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsRequest {
	private Long requestId;
	private String userId;
	private String partnerId;
	private Byte rating;
	private String comment;
	private String reviewImage;
}
