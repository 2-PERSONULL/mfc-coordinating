package com.mfc.coordinating.coordinates.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false)
	private String category; // list로 만들기

	@Column(nullable = false)
	private String brand; // list로 만들기

	@Column(nullable = false)
	private Double budget;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private Long requestId; // 히스토리 아이디로 변경

	@Builder
	public Coordinates(String category, String brand,
		Double budget, String url, String comment, Long requestId) {
		this.category = category;
		this.brand = brand;
		this.budget = budget;
		this.url = url;
		this.comment = comment;
		this.requestId = requestId;
	}

	public void update(String category, String brand,
		Double budget, String url, String comment) {
		this.category = category;
		this.brand = brand;
		this.budget = budget;
		this.url = url;
		this.comment = comment;
	}
}