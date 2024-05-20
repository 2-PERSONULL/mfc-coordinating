package com.mfc.coordinating.requests.domain;

import java.time.LocalDate;

import com.mfc.coordinating.common.entity.BaseEntity;
import com.mfc.coordinating.requests.enums.RequestsStates;

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
@Table(name = "requests")
public class Requests extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id")
	private Long requestId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Long options;

	@Column(name = "total_price", nullable = false)
	private Long totalPrice;

	@Column(nullable = false)
	private String situation;

	@Column(name = "reference_images")
	private String referenceImages;

	@Column(name = "my_images")
	private String myImages;

	@Column(nullable = false)
	private Long budget;

	private String brand;

	@Column(name = "other_requirements")
	private String otherRequirements;

	@Column(nullable = false)
	private LocalDate deadline;

	@Column(nullable = false)
	private RequestsStates state;
}
