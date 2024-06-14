// package com.mfc.coordinating.requests.application;
//
// import java.time.LocalDate;
// import java.util.List;
//
// import org.bson.types.ObjectId;
//
// import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
// import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
// import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
// import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
// import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
// import com.mfc.coordinating.requests.enums.RequestsListSortType;
//
// public interface RequestsService {
//
// 	// void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);
// 	//
// 	// List<MyRequestListResponse> getRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid);
// 	//
// 	// List<RequestsListResDto> getRequestsListByUser(int page, int pageSize, RequestsListSortType sortType, String uuid);
// 	//
// 	// RequestsDetailResDto getRequestsDetail(Long requestId);
// 	//
// 	// void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid);
// 	//
// 	// void deleteRequests(Long requestId, String uuid);
// 	//
// 	// void updateProposal(Long requestId, String partnerId, String uuid, LocalDate deadline);
// 	//
// 	// List<RequestsListResDto> getRequestsListPartner(int page, int pageSize, RequestsListSortType sortType, String uuid);
// 	//
// 	// void updateAcceptRequests(ObjectId historyId, String uuid);
// 	//
// 	// void updateRejectRequests(ObjectId historyId, String uuid);
// 	//
// 	// RequestsListResDto getRequestHistoryById(ObjectId id);
//
//
// }
package com.mfc.coordinating.requests.application;

import java.time.LocalDate;
import java.util.List;

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
	 * @param uuid 사용자 UUID
	 */
	void createRequests(RequestsCreateReqDto requestsCreateReqDto, String uuid);

	/**
	 * 사용자가 작성한 코디 요청서 목록 조회
	 *
	 * @param page 페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sortType 정렬 기준
	 * @param uuid 사용자 UUID
	 * @return 코디 요청서 목록
	 */
	List<MyRequestListResponse> getMyRequestsList(int page, int pageSize, RequestsListSortType sortType, String uuid);

	/**
	 * 파트너별 코디 요청서 목록 조회
	 *
	 * @param page 페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sortType 정렬 기준
	 * @param userId 사용자 ID
	 * @return 파트너별 코디 요청서 목록
	 */
	List<RequestsListResDto> getPartnerRequestsList(int page, int pageSize, RequestsListSortType sortType,
		String userId);

	List<RequestsListResDto> getUserRequestsList(int page, int pageSize, RequestsListSortType sortType, String userId);

	/**
	 * 코디 요청서 상세 정보 조회
	 *
	 * @param requestId 요청서 ID
	 * @return 코디 요청서 상세 정보
	 */
	RequestsDetailResDto getRequestsDetail(Long requestId);

	/**
	 * 코디 요청서 수정
	 *
	 * @param dto 코디 요청서 수정 DTO
	 * @param requestId 요청서 ID
	 * @param uuid 사용자 UUID
	 */
	void updateRequests(RequestsUpdateReqDto dto, Long requestId, String uuid);

	/**
	 * 코디 요청서 삭제
	 *
	 * @param requestId 요청서 ID
	 * @param uuid 사용자 UUID
	 */
	void deleteRequests(Long requestId, String uuid);

	/**
	 * 코디 요청서 제안
	 *
	 * @param requestId 요청서 ID
	 * @param partnerId 파트너 ID
	 * @param uuid 사용자 UUID
	 * @param deadline 제안 마감일
	 */
	void updateProposal(Long requestId, String partnerId, String uuid, LocalDate deadline);

	/**
	 * 파트너 응답 업데이트
	 *
	 * @param requestId 요청서 ID
	 * @param partnerId 파트너 ID
	 * @param uuid 사용자 UUID
	 * @param status 응답 상태
	 */
	void updatePartnerResponse(Long requestId, String partnerId, String uuid, RequestsStates status);

}