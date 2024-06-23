package com.mfc.coordinating.coordinates.application;

import java.util.List;

import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;

public interface CoordinatesService {
	List<Long> createCoordinates(List<CoordinatesRequest> requests);
	CoordinatesResponse getCoordinatesById(String id);
	void updateCoordinates(String id, CoordinatesRequest request);
	void deleteCoordinates(String id);
}
