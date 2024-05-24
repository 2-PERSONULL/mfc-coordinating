package com.mfc.coordinating.reviews.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.reviews.dto.request.ReviewsRequest;
import com.mfc.coordinating.reviews.entity.ReviewImage;
import com.mfc.coordinating.reviews.entity.Reviews;
import com.mfc.coordinating.reviews.infrastructure.ReviewsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewsServiceImpl implements ReviewsService {
	private final ReviewsRepository reviewsRepository;

	@Override
	@Transactional
	public void createReview(ReviewsRequest request) {
		Reviews review = Reviews.builder()
			.requestId(request.getRequestId())
			.userId(request.getUserId())
			.partnerId(request.getPartnerId())
			.rating(request.getRating())
			.comment(request.getComment())
			.build();

		List<ReviewImage> reviewImages = request.getReviewImage().stream()
			.map(ReviewImage::new)
			.toList();

		review.updateReviewImage(reviewImages);

		// 리뷰 저장
		reviewsRepository.save(review);
	}

	@Override
	@Transactional
	public void updateReview(Long reviewId, String comment, List<String> reviewImageUrls) {
		Reviews review = getReviewById(reviewId);
		review.updateComment(comment);

		List<ReviewImage> reviewImages = reviewImageUrls.stream()
			.map(ReviewImage::new)
			.toList();

		review.updateReviewImage(reviewImages);
	}
	@Override
	@Transactional(readOnly = true)
	public Page<Reviews> getReviewsByPartner(String partnerId, int page, int pageSize) {
		return reviewsRepository.findByPartnerId(partnerId, Pageable.ofSize(pageSize).withPage(page));
	}

	@Override
	@Transactional(readOnly = true)
	public Reviews getReviewById(Long reviewId) {
		return reviewsRepository.findById(reviewId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.REVIEW_NOT_FOUND));
	}

	@Override
	@Transactional
	public void deleteReview(Long reviewId) {
		Reviews review = getReviewById(reviewId);
		reviewsRepository.delete(review);
	}

	@Override
	@Transactional
	public Integer getReviewCount(String partnerId) {
		return reviewsRepository.getReviewCountByPartnerId(partnerId);
	}
}