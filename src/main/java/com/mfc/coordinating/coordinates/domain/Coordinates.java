package com.mfc.coordinating.coordinates.domain;

import com.mfc.coordinating.coordinates.enums.CoordinateCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinates {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CoordinateCategory category;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private Double budget;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private String requestId;

	@Column(nullable = false)
	private String partnerId;
	@Builder
	public Coordinates(CoordinateCategory category, String brand,
		Double budget, String url, String comment, String requestId, String partnerId) {
		this.category = category;
		this.brand = brand;
		this.budget = budget;
		this.url = url;
		this.comment = comment;
		this.requestId = requestId;
		this.partnerId = partnerId;
	}

	public void update(CoordinateCategory category, String brand,
		Double budget, String url, String comment) {
		this.category = category;
		this.brand = brand;
		this.budget = budget;
		this.url = url;
		this.comment = comment;
	}

}