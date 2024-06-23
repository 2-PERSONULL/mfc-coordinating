package com.mfc.coordinating.coordinates.dto.request;

import java.util.List;

import com.mfc.coordinating.coordinates.enums.CoordinateCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesRequest {
	private CoordinateCategory category;
	private String brand;
	private Double budget;
	private String url;
	private String comment;
	private List<String> images;
	private String requestId;
}