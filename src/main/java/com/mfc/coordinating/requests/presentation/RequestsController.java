package com.mfc.coordinating.requests.presentation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.requests.application.RequestsService;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.req.RequestsUpdateReqDto;
import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;
import com.mfc.coordinating.requests.dto.res.RequestsDetailResDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.enums.RequestsStates;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
@Tag(name = "Requests", description = "코디 요청서 API document")
public class RequestsController {
	private final RequestsService requestsService;

	@PostMapping("")
	@Operation(summary = "코디 요청서 생성", description = "유저가 작성한 코디 요청서를 저장합니다.")
	public BaseResponse<Void> createRequests(
		@RequestHeader String uuid,
		@Valid @RequestBody RequestsCreateReqDto requestsCreateReqDto
	) {
		requestsService.createRequests(requestsCreateReqDto, uuid);
		return new BaseResponse<>();
	}

	@GetMapping("/my-requests")
	@Operation(summary = "작성한 요청서 목록 조회", description = "유저가 작성한 코디 요청서 목록을 조회합니다.")
	public BaseResponse<List<MyRequestListResponse>> getMyRequestsList(
		@RequestHeader String uuid,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize,
		@RequestParam(defaultValue = "LATEST") RequestsListSortType sortType) {

		List<MyRequestListResponse> requestsList = requestsService.getMyRequestsList(page, pageSize, sortType, uuid);
		return new BaseResponse<>(requestsList);
	}

	@GetMapping("/partner-requests")
	@Operation(summary = "파트너별 코디 요청서 목록 조회", description = "파트너별 코디 요청서 목록을 조회합니다.")
	public BaseResponse<List<RequestsListResDto>> getPartnerRequestsList(
		@RequestHeader String uuid,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int pageSize,
		@RequestParam(defaultValue = "LATEST") RequestsListSortType sortType,
		@RequestParam(required = false) RequestsStates status
	) {
		List<RequestsListResDto> requestsList = requestsService.getPartnerRequestsList(page, pageSize, sortType, uuid, status);
		return new BaseResponse<>(requestsList);
	}

	@GetMapping("/user-requests")
	@Operation(summary = "유저별 코디 요청서 목록 조회", description = "유저별 코디 요청서 목록을 조회합니다.")
	public BaseResponse<List<RequestsListResDto>> getUserRequestsList(
		@RequestHeader String uuid,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize,
		@RequestParam(defaultValue = "LATEST") RequestsListSortType sortType,
		@RequestParam(required = false) RequestsStates status
	) {
		List<RequestsListResDto> requestsList = requestsService.getUserRequestsList(page, pageSize, sortType, uuid, status);
		return new BaseResponse<>(requestsList);
	}

	@GetMapping("/{requestId}")
	@Operation(summary = "코디 요청서 세부내용 조회", description = "유저가 작성한 코디 요청서 세부 내용을 조회합니다.")
	public BaseResponse<RequestsDetailResDto> getRequestsDetail(
		@PathVariable String requestId
	) {
		return new BaseResponse<>(requestsService.getRequestsDetail(requestId));
	}

	@PutMapping("/{requestId}")
	@Operation(summary = "코디 요청서 수정", description = "유저가 작성한 코디 요청서 내용을 수정합니다.")
	public BaseResponse<Void> updateRequests(
		@RequestHeader String uuid,
		@PathVariable String requestId,
		@Valid @RequestBody RequestsUpdateReqDto requestsUpdateReqDto
	) {
		requestsService.updateRequests(requestsUpdateReqDto, requestId, uuid);
		return new BaseResponse<>();
	}

	@DeleteMapping("/{requestId}")
	@Operation(summary = "코디 요청서 삭제", description = "유저가 작성한 코디 요청서를 삭제합니다.")
	public BaseResponse<Void> deleteRequests(
		@RequestHeader String uuid,
		@PathVariable String requestId
	) {
		requestsService.deleteRequests(requestId, uuid);
		return new BaseResponse<>();
	}

	@PutMapping("/proposal/{requestId}/{partnerId}")
	@Operation(summary = "코디 요청서 요청", description = "유저가 작성한 코디 요청서를 파트너에게 요청합니다.")
	public BaseResponse<Void> updateProposal(
		@RequestHeader String uuid,
		@PathVariable String requestId,
		@PathVariable String partnerId,
		@RequestParam LocalDate deadline
	) {
		Instant date = deadline.atStartOfDay(ZoneOffset.UTC).toInstant();
		requestsService.updateProposal(requestId, partnerId, uuid, date);
		return new BaseResponse<>();
	}

	@PutMapping("/response/{requestId}")
	@Operation(summary = "파트너 응답 업데이트", description = "파트너가 코디 요청서에 대한 응답을 업데이트합니다.")
	public BaseResponse<Void> updatePartnerResponse(
		@RequestHeader String uuid,
		@PathVariable String requestId,
		@RequestParam RequestsStates status
	) {
		requestsService.updatePartnerResponse(requestId, uuid, status);
		return new BaseResponse<>();
	}

	@GetMapping("/{requestId}/status/{partnerId}")
	@Operation(summary = "요청 상태", description = "요청 상태를 가져옵니다.")
	public BaseResponse<RequestsStates> getRequestStatusForPartner(
		@PathVariable String requestId,
		@PathVariable String partnerId) {
		RequestsStates status = requestsService.getRequestStatusForPartner(requestId, partnerId);
		return new BaseResponse<>(status);
	}
}