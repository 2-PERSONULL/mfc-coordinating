package com.mfc.coordinating.requests.application;

import java.time.Instant;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.enums.RequestsStates;

public interface RequestsService {

	/**
	 * 코디 요청서 생성
	 *
	 * @param requestsCreateReqDto 코디 요청서 생성 DTO
	 * @param userId 사용자 ID
	 */
	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String userId);

	/**
	 * 사용자가 작성한 코디 요청서 목록 조회
	 *
	 * @param page 페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sortType 정렬 기준
	 * @param userId 사용자 ID
	 * @return 코디 요청서 목록
	 */
	List<MyRequestListResponse> getMyRequestsList(int page, int pageSize, RequestsListSortType sortType, String userId);

	/**
	 * 파트너별 코디 요청서 목록 조회
	 *
	 * @param page 페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sortType 정렬 기준
	 * @param partnerId 파트너 ID
	 * @return 파트너별 코디 요청서 목록
	 */
	List<RequestsListResDto> getPartnerRequestsList(int page, int pageSize, RequestsListSortType sortType, String partnerId);

	/**
	 * 사용자별 코디 요청서 목록 조회
	 *
	 * @param page 페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sortType 정렬 기준
	 * @param userId 사용자 ID
	 * @return 사용자별 코디 요청서 목록
	 */
	List<RequestsListResDto> getUserRequestsList(int page, int pageSize, RequestsListSortType sortType, String userId);

	/**
	 * 코디 요청서 상세 정보 조회
	 *
	 * @param requestId 요청서 ID
	 * @return 코디 요청서 상세 정보
	 */
	RequestsDetailResDto getRequestsDetail(String requestId);

	/**
	 * 코디 요청서 수정
	 *
	 * @param requestsUpdateReqDto 코디 요청서 수정 DTO
	 * @param requestId 요청서 ID
	 * @param userId 사용자 ID
	 */
	void updateRequests(RequestsUpdateReqDto requestsUpdateReqDto, String requestId, String userId);

	/**
	 * 코디 요청서 삭제
	 *
	 * @param requestId 요청서 ID
	 * @param userId 사용자 ID
	 */
	void deleteRequests(String requestId, String userId);

	/**
	 * 코디 요청서 제안
	 *
	 * @param requestId 요청서 ID
	 * @param partnerId 파트너 ID
	 * @param userId 사용자 ID
	 * @param deadline 제안 마감일
	 */
	void updateProposal(String requestId, String partnerId, String userId, Instant deadline);

	/**
	 * 파트너 확정 제안
	 *
	 * @param requestId 요청서 ID
	 * @param partnerId 파트너 ID
	 * @param price 제안 가격
	 */
	@Transactional
	void confirmProposal(String requestId, String partnerId, Double price, Instant confirmDate);

	/**
	 * 파트너 응답 업데이트
	 *
	 * @param requestId 요청서 ID
	 * @param userId 사용자 ID
	 * @param status 응답 상태
	 */
	void updatePartnerResponse(String requestId, String userId, RequestsStates status);
}