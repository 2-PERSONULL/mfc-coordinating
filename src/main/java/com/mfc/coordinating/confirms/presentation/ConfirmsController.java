package com.mfc.coordinating.confirms.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.confirms.application.ConfirmsService;
import com.mfc.coordinating.confirms.dto.request.ConfirmsRequest;
import com.mfc.coordinating.confirms.dto.request.ConfirmsUpdateRequest;
import com.mfc.coordinating.confirms.dto.response.ConfirmsResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirms")
public class ConfirmsController {
	private final ConfirmsService confirmsService;

	@PostMapping
	@Operation(summary = "제안서 생성", description = "새로운 제안서를 생성합니다.")
	public BaseResponse<ConfirmsResponse> createConfirms(@RequestBody ConfirmsRequest request,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		ConfirmsResponse confirmsResponse = confirmsService.createConfirms(request, partnerUuid);
		return new BaseResponse<>(confirmsResponse);
	}

	@GetMapping("/partner/{id}")
	@Operation(summary = "제안서 조회 (파트너)", description = "파트너가 제안서를 조회합니다.")
	public BaseResponse<ConfirmsResponse> getConfirmsByPartner(@PathVariable Long id,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		ConfirmsResponse confirmsResponse = confirmsService.getConfirmsById(id, partnerUuid);
		return new BaseResponse<>(confirmsResponse);
	}

	@GetMapping("/user/{id}")
	@Operation(summary = "제안서 조회 (유저)", description = "유저가 제안서를 조회합니다.")
	public BaseResponse<ConfirmsResponse> getConfirmsByUser(@PathVariable Long id,
		@RequestHeader("User-UUID") String userUuid) {
		ConfirmsResponse confirmsResponse = confirmsService.getConfirmsById(id, userUuid);
		return new BaseResponse<>(confirmsResponse);
	}


	@PutMapping("/{id}")
	@Operation(summary = "제안서 수정", description = "제안서 정보를 수정합니다.")
	public BaseResponse<Void> updateConfirms(@PathVariable Long id,
		@RequestBody ConfirmsUpdateRequest request,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		confirmsService.updateConfirms(id, request, partnerUuid);
		return new BaseResponse<>();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "제안서 삭제", description = "제안서를 삭제합니다.")
	public BaseResponse<Void> deleteConfirms(@PathVariable Long id,
		@RequestHeader("Partner-UUID") String partnerUuid) {
		confirmsService.deleteConfirms(id, partnerUuid);
		return new BaseResponse<>();
	}

	@PutMapping("/user/{id}")
	@Operation(summary = "제안 수락", description = "제안을 수락합니다.")
	public BaseResponse<Void> updateConfirmsStatus(@PathVariable Long id,
		@RequestHeader("User-UUID") String userUuid) {
		confirmsService.updateConfirmsStatus(id, userUuid);
		return new BaseResponse<>();
	}
}