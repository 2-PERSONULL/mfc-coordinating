package com.mfc.coordinating.reviews.application;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mfc.coordinating.reviews.dto.request.ReviewsRequest;
import com.mfc.coordinating.reviews.entity.Reviews;

/**
 * 리뷰 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 */
public interface ReviewsService {

	/**
	 * 새로운 리뷰를 생성합니다.
	 * @param request 리뷰 생성에 필요한 정보를 담은 요청 객체
	 */
	void createReview(ReviewsRequest request);

	/**
	 * 특정 파트너의 리뷰 목록을 페이지네이션하여 조회합니다.
	 * @param partnerId 파트너 ID
	 * @param page 조회할 페이지 번호
	 * @param pageSize 한 페이지당 표시할 리뷰 수
	 * @return 페이지네이션된 리뷰 목록
	 */
	Page<Reviews> getReviewsByPartner(String partnerId, int page, int pageSize);

	/**
	 * 특정 ID의 리뷰를 조회합니다.
	 * @param reviewId 조회할 리뷰의 ID
	 * @return 조회된 리뷰 객체
	 */
	Reviews getReviewById(Long reviewId);

	/**
	 * 기존 리뷰의 내용을 수정합니다.
	 * @param reviewId 수정할 리뷰의 ID
	 * @param comment 수정할 리뷰 내용
	 * @param reviewImage 수정할 리뷰 이미지 URL 목록
	 */
	void updateReview(Long reviewId, String comment, List<String> reviewImage);

	/**
	 * 특정 ID의 리뷰를 삭제합니다.
	 * @param reviewId 삭제할 리뷰의 ID
	 */
	void deleteReview(Long reviewId);
}