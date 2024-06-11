package com.mfc.coordinating.coordinates.application;

import java.util.List;

import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;

public interface CoordinatesService {
	List<Long> createCoordinates(List<CoordinatesRequest> requests);
	CoordinatesResponse getCoordinatesById(Long id);
	void updateCoordinates(Long id, CoordinatesRequest request);
	void deleteCoordinates(Long id);
}
