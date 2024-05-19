package com.mfc.coordinating.reviews.entity;

import com.mfc.coordinating.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Builder
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

	@Column(name = "review_image", nullable = false)
	private String reviewImage;
}
