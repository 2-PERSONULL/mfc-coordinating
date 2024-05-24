package com.mfc.coordinating.reviews.presentation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.coordinating.common.response.BaseResponse;
import com.mfc.coordinating.reviews.application.ReviewsService;
import com.mfc.coordinating.reviews.dto.request.ReviewsRequest;
import com.mfc.coordinating.reviews.dto.response.ReviewsResponse;
import com.mfc.coordinating.reviews.entity.Reviews;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewsController {
	private final ReviewsService reviewsService;

	@PutMapping
	@Operation(summary = "리뷰 생성", description = "새로운 리뷰를 생성합니다.")
	public BaseResponse<Void> createReview(@RequestBody ReviewsRequest request) {
		reviewsService.createReview(request);
		return new BaseResponse<>();
	}

	@GetMapping("/partner/{partnerId}")
	@Operation(summary = "파트너 리뷰 목록 조회", description = "특정 파트너에 대한 리뷰 목록을 조회합니다.")
	public BaseResponse<List<ReviewsResponse>> getReviewsByPartner(
		@PathVariable String partnerId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int pageSize
	) {
		Page<Reviews> reviews = reviewsService.getReviewsByPartner(partnerId, page, pageSize);
		List<ReviewsResponse> response = reviews.stream()
			.map(ReviewsResponse::from)
			.toList();
		return new BaseResponse<>(response);
	}

	@GetMapping("/{reviewId}")
	@Operation(summary = "리뷰 상세 조회", description = "리뷰 ID로 리뷰 상세 정보를 조회합니다.")
	public BaseResponse<ReviewsResponse> getReviewById(@PathVariable Long reviewId) {
		Reviews review = reviewsService.getReviewById(reviewId);
		ReviewsResponse response = ReviewsResponse.from(review);
		return new BaseResponse<>(response);
	}

	@PutMapping("/{reviewId}")
	@Operation(summary = "리뷰 수정", description = "리뷰 ID로 리뷰 정보를 수정합니다.")
	public BaseResponse<Void> updateReview(
		@PathVariable Long reviewId,
		@RequestBody ReviewsRequest request
	) {
		reviewsService.updateReview(reviewId, request.getComment(), request.getReviewImage());
		return new BaseResponse<>();
	}

	@DeleteMapping("/{reviewId}")
	@Operation(summary = "리뷰 삭제", description = "리뷰 ID로 리뷰를 삭제합니다.")
	public BaseResponse<Void> deleteReview(@PathVariable Long reviewId) {
		reviewsService.deleteReview(reviewId);
		return new BaseResponse<>();
	}

	@GetMapping("/count/{partnerId}")
	@Operation(summary = "리뷰 개수", description = "리뷰 개수를 조회합니다.")
	public BaseResponse<Integer> getReviewCount(@PathVariable String partnerId) {
		Integer reviewCount = reviewsService.getReviewCount(partnerId);
		return new BaseResponse<>(reviewCount);
	}
}