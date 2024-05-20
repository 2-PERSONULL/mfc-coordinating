package com.mfc.coordinating.requests.presentation;

import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.requests.application.RequestsService;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsListSortType;
import com.mfc.coordinating.requests.vo.req.RequestsCreateReqVo;

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
	// private final JwtProvider jwtProvider;

	@PostMapping("")
	@Operation(summary = "코디 요청서 생성", description = "유저가 작성한 코디 요청서를 저장합니다.")
	public BaseResponse<Void> createRequests(
		// @RequestHeader HttpHeaders header,  // uuid
		@RequestBody RequestsCreateReqVo vo
	){
		// List<String> uuid = header.get("UUID");
		// List<String> role = header.get("Role");

		// if(uuid == null || role == null) {
		// 	throw new BaseException(NO_REQUIRED_HEADER);
		// }

		String uuid = "userUuidTest";

		requestsService.createRequests(modelMapper.map(vo, RequestsCreateReqDto.class), uuid);

		return new BaseResponse<>();
	}
	@GetMapping("")
	public BaseResponse<?> getRequestsList(
		// @RequestHeader("Authorization") String token  // uuid
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize,
		@RequestParam(defaultValue = "LATEST")RequestsListSortType sortType
	){
		// String uuid = jwtProvider.getUuid(token);	//uuid
		String uuid = "userUuidTest";

		List<RequestsListResDto> requestsList= requestsService.getRequestsList(page, pageSize, sortType, uuid);

		return new BaseResponse<>(requestsList); // VO 추후 수정 예정
	}
}
