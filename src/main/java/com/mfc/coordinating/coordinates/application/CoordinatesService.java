package com.mfc.coordinating.coordinates.application;

import java.util.List;

import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;

public interface CoordinatesService {
	List<Long> createCoordinates(List<CoordinatesRequest> requests);
	List<CoordinatesResponse> getCoordinatesByRequestId(String requestId);
	void updateCoordinates(Long coordinateId, CoordinatesRequest request);
	void deleteCoordinates(Long coordinateId);
}
