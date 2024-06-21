package com.mfc.coordinating.reviews.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsRequest {
	private Long requestId;
	private String userId;
	private String partnerId;
	private Short rating;
	private String comment;
	private List<String> reviewImage;
}
