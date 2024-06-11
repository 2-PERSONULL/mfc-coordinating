package com.mfc.coordinating.coordinates.dto.response;

import java.util.List;

import com.mfc.coordinating.coordinates.domain.Coordinates;
import com.mfc.coordinating.coordinates.domain.CoordinatesImage;
import com.mfc.coordinating.coordinates.enums.CoordinateCategory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoordinatesResponse {
	private Long id;
	private CoordinateCategory category;
	private String brand;
	private Double budget;
	private String url;
	private String comment;
	private List<String> images;
	private Long requestHistoryId;

	public static CoordinatesResponse from(Coordinates coordinates, List<CoordinatesImage> coordinatesImages) {
		return CoordinatesResponse.builder()
			.id(coordinates.getId())
			.category(coordinates.getCategory())
			.brand(coordinates.getBrand())
			.budget(coordinates.getBudget())
			.url(coordinates.getUrl())
			.comment(coordinates.getComment())
			.images(coordinatesImages.stream()
				.map(CoordinatesImage::getImageUrl)
				.toList())
			.requestHistoryId(coordinates.getRequestHistoryId())
			.build();
	}
}