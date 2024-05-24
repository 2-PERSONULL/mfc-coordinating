package com.mfc.coordinating.reviews.entity;

import java.util.ArrayList;
import java.util.List;

import com.mfc.coordinating.common.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Table(name = "reviews")
public class Reviews extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long reviewId;

	@Column(name = "request_id", nullable = false)
	private Long requestId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "partner_id", nullable = false)
	private String partnerId;

	@Column(nullable = false)
	private Byte rating;

	@Column(nullable = false)
	private String comment;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewImage> reviewImages = new ArrayList<>();

	@Builder
	public Reviews(Long requestId, String userId, String partnerId, Byte rating, String comment){
		this.requestId = requestId;
		this.userId = userId;
		this.partnerId = partnerId;
		this.rating = rating;
		this.comment = comment;
	}

	public void updateComment(String comment) {
		this.comment = comment;
	}

	public void updateReviewImage(List<ReviewImage> reviewImages) {
		this.reviewImages.clear();
		this.reviewImages.addAll(reviewImages);
		reviewImages.forEach(reviewImage -> reviewImage.setReview(this));
	}
}
