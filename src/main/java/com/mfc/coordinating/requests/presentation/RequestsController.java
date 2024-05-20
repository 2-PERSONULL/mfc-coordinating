package com.mfc.coordinating.requests.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.requests.application.RequestsService;
import com.mfc.coordinating.requests.dto.req.RequestsCreateReqDto;
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
		// @RequestHeader("Authorization") String token  // uuid
		@RequestBody RequestsCreateReqVo vo
	){
		// String uuid = jwtProvider.getUuid(token);	//uuid
		String uuid = "userUuidTest";

		requestsService.createRequests(modelMapper.map(vo, RequestsCreateReqDto.class), uuid);

		return new BaseResponse<>();
	}
}
