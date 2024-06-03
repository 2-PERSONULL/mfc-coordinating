package com.mfc.coordinating.coordinates.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesRequest {
	private String category;
	private String brand;
	private Double budget;
	private String url;
	private String comment;
	private List<String> images;
	private Long requestId;
}