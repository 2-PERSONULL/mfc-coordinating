package com.mfc.coordinating.requests.presentation;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.vo.req.RequestsCreateReqVo;
import com.mfc.coordinating.requests.vo.req.RequestsUpdateReqVo;
import com.mfc.coordinating.requests.vo.res.RequestsDetailResVo;
import com.mfc.coordinating.requests.vo.res.RequestsHistoryListVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
@Tag(name = "Requests", description = "코디 요청서 API document")
public class RequestsController {
	private final RequestsService requestsService;
	private final ModelMapper modelMapper;

	@PostMapping("")
	@Operation(summary = "코디 요청서 생성", description = "유저가 작성한 코디 요청서를 저장합니다.")
	public BaseResponse<Void> createRequests(
		@RequestHeader String uuid,
		@RequestBody RequestsCreateReqVo vo
	){
		requestsService.createRequests(modelMapper.map(vo, RequestsCreateReqDto.class), uuid);

		return new BaseResponse<>();
	}

	@GetMapping("/userlist")
	@Operation(summary = "유저 코디 요청서 목록 조회", description = "유저가 작성한 코디 요청서 목록을 조회합니다.")
	public BaseResponse<?> getRequestsHistoryListUser(
		@RequestHeader String uuid,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize,
		@RequestParam(defaultValue = "LATEST") RequestsListSortType sortType
	){
		List<RequestsListResDto> requestsList = requestsService.getRequestsListByUser(page, pageSize, sortType, uuid);
		List<RequestsHistoryListVo> requestsHistoryList = requestsList.stream()
			.map(dto -> modelMapper.map(dto, RequestsHistoryListVo.class))
			.toList();

		return new BaseResponse<>(requestsHistoryList);
	}

	@GetMapping("/{requestId}")
	@Operation(summary = "코디 요청서 세부내용 조회", description = "유저가 작성한 코디 요청서 세부 내용을 조회합니다.")
	public BaseResponse<?> getRequestsDetail(
		@PathVariable Long requestId
	){
		return new BaseResponse<>(modelMapper.map(requestsService.getRequestsDetail(requestId), RequestsDetailResVo.class));
	}

	@PutMapping("/{requestId}")
	@Operation(summary = "코디 요청서 수정", description = "유저가 작성한 코디 요청서 내용을 수정합니다.")
	public BaseResponse<?> updateRequests(
		@RequestHeader String uuid,
		@PathVariable Long requestId,
		@RequestBody RequestsUpdateReqVo vo
	){
		requestsService.updateRequests(modelMapper.map(vo, RequestsUpdateReqDto.class), requestId, uuid);

		return new BaseResponse<>();
	}

	@DeleteMapping("/{requestId}")
	@Operation(summary = "코디 요청서 삭제", description = "유저가 작성한 코디 요청서를 삭제합니다.")
	public BaseResponse<?> deleteRequests(
		@RequestHeader String uuid,
		@PathVariable Long requestId
	){
		requestsService.deleteRequests(requestId, uuid);
		return new BaseResponse<>();
	}

	@PutMapping("/proposal/{requestId}/{partnerId}")
	@Operation(summary = "코디 요청서 요청", description = "유저가 작성한 코디 요청서를 파트너에게 요청합니다.")
	public BaseResponse<?> updateProposal(
		@RequestHeader String uuid,
		@PathVariable Long requestId,
		@PathVariable String partnerId,
		@RequestParam LocalDate deadline
	){
		requestsService.updateProposal(requestId, partnerId, uuid, deadline);
		return new BaseResponse<>();
	}

	@GetMapping("/partnerlist")
	@Operation(summary = "파트너 코디 요청서 목록 조회", description = "유저가 요청한 코디 요청 목록을 조회합니다.")
	public BaseResponse<?> getRequestsListPartner(
		@RequestHeader String uuid,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize,
		@RequestParam(defaultValue = "LATEST") RequestsListSortType sortType
	){
		List<RequestsListResDto> requestsList = requestsService.getRequestsListPartner(page, pageSize, sortType, uuid);

		return new BaseResponse<>(requestsList);
	}

	@PutMapping("/accept/{historyId}")
	@Operation(summary = "파트너 코디 요청 수락", description = "유저가 요청한 코디 요청을 수락합니다.")
	public BaseResponse<?> updateAcceptRequests(
		@RequestHeader String uuid,
		@PathVariable Long historyId
	){
		requestsService.updateAcceptRequests(historyId, uuid);

		return new BaseResponse<>();
	}

	@PutMapping("/reject/{historyId}")
	@Operation(summary = "파트너 코디 요청 거절", description = "유저가 요청한 코디 요청을 거절합니다.")
	public BaseResponse<?> updateRejectRequests(
		@RequestHeader String uuid,
		@PathVariable Long historyId
	){
		requestsService.updateRejectRequests(historyId, uuid);

		return new BaseResponse<>();
	}
}