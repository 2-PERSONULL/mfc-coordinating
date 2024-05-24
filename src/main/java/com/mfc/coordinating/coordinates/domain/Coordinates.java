package com.mfc.coordinating.coordinates.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private String partnerId;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private Integer budget;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private Long requestId;

	@OneToMany(mappedBy = "coordinates", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CoordinatesImage> images = new ArrayList<>();

	@Builder
	public Coordinates(String partnerId, String userId, String category, String brand,
		Integer budget, String url, String comment, Long requestId) {
		this.partnerId = partnerId;
		this.userId = userId;
		this.category = category;
		this.brand = brand;
		this.budget = budget;
		this.url = url;
		this.comment = comment;
		this.requestId = requestId;
	}

	public void update(String partnerId, String userId, String category, String brand,
		Integer budget, String url, String comment) {
		this.partnerId = partnerId;
		this.userId = userId;
		this.category = category;
		this.brand = brand;
		this.budget = budget;
		this.url = url;
		this.comment = comment;
	}
}