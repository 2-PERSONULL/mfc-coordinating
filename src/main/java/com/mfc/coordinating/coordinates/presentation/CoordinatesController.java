package com.mfc.coordinating.coordinates.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.coordinates.application.CoordinatesService;
import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coordinates")
public class CoordinatesController {
	private final CoordinatesService coordinatesService;

	@PostMapping
	@Operation(summary = "코디 생성", description = "새로운 코디를 생성합니다.")
	public BaseResponse<Long> createCoordinates(@RequestBody CoordinatesRequest request) {
		Long coordinatesId = coordinatesService.createCoordinates(request);
		return new BaseResponse<>(coordinatesId);
	}

	@GetMapping("/{id}")
	@Operation(summary = "코디 조회", description = "코디 조회")
	public BaseResponse<CoordinatesResponse> getCoordinatesById(@PathVariable Long id) {
		CoordinatesResponse coordinatesResponse = coordinatesService.getCoordinatesById(id);
		return new BaseResponse<>(coordinatesResponse);
	}

	@PutMapping("/{id}")
	@Operation(summary = "코디 수정", description = "코디 정보를 수정합니다.")
	public BaseResponse<Void> updateCoordinates(@PathVariable Long id, @RequestBody CoordinatesRequest request) {
		coordinatesService.updateCoordinates(id, request);
		return new BaseResponse<>();
	}
	@DeleteMapping("/{id}")
	@Operation(summary = "코디 삭제", description = "코디를 삭제합니다.")
	public BaseResponse<Void> deleteCoordinates(@PathVariable Long id) {
		coordinatesService.deleteCoordinates(id);
		return new BaseResponse<>();
	}
}