package com.mfc.coordinating.coordinates.application;

import java.util.List;

import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;

/**
 * 코디네이트 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 */
public interface CoordinatesService {

	/**
	 * 새로운 코디네이트 목록을 생성합니다.
	 *
	 * @param requests    생성할 코디네이트 정보를 담은 요청 목록
	 * @param partnerUuid 코디네이트를 생성하는 파트너의 고유 식별자
	 * @return 생성된 코디네이트들의 ID 목록
	 */
	List<Long> createCoordinates(List<CoordinatesRequest> requests, String partnerUuid);

	/**
	 * 특정 요청 ID에 해당하는 모든 코디네이트를 조회합니다.
	 *
	 * @param requestId 조회할 코디네이트들의 요청 ID
	 * @return 조회된 코디네이트 정보 목록
	 */
	List<CoordinatesResponse> getCoordinatesByRequestId(String requestId);

	/**
	 * 특정 코디네이트의 정보를 업데이트합니다.
	 *
	 * @param coordinateId 업데이트할 코디네이트의 ID
	 * @param request      업데이트할 코디네이트 정보
	 */
	void updateCoordinates(Long coordinateId, CoordinatesRequest request);

	/**
	 * 특정 코디네이트를 삭제합니다.
	 *
	 * @param coordinateId 삭제할 코디네이트의 ID
	 */
	void deleteCoordinates(Long coordinateId);
}