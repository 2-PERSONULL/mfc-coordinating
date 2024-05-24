package com.mfc.coordinating.coordinates.application;

import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;

public interface CoordinatesService {
	Long createCoordinates(CoordinatesRequest request);
	CoordinatesResponse getCoordinatesById(Long id);
	void updateCoordinates(Long id, CoordinatesRequest request);
	void deleteCoordinates(Long id);
}
