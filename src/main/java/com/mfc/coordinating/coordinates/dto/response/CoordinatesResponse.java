package com.mfc.coordinating.coordinates.dto.response;

import java.util.List;

import com.mfc.coordinating.coordinates.domain.Coordinates;
import com.mfc.coordinating.coordinates.domain.CoordinatesImage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoordinatesResponse {
	private Long id;
	private String partnerId;
	private String userId;
	private String category;
	private String brand;
	private Integer budget;
	private String url;
	private String comment;
	private List<String> images;
	private Long requestId;

	public static CoordinatesResponse from(Coordinates coordinates) {
		return CoordinatesResponse.builder()
			.id(coordinates.getId())
			.partnerId(coordinates.getPartnerId())
			.userId(coordinates.getUserId())
			.category(coordinates.getCategory())
			.brand(coordinates.getBrand())
			.budget(coordinates.getBudget())
			.url(coordinates.getUrl())
			.comment(coordinates.getComment())
			.images(coordinates.getImages().stream()
				.map(CoordinatesImage::getImageUrl)
				.toList())
			.requestId(coordinates.getRequestId())
			.build();
	}
}
