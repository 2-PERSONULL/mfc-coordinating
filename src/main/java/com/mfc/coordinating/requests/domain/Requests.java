package com.mfc.coordinating.requests.domain;

import com.mfc.coordinating.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	private String situation;

	@Column(nullable = false)
	private Long budget;

	@Column(name = "other_requirements")
	private String otherRequirements;

	@Builder
	public Requests(Long requestId, String userId, String title, String description, String situation,
		Long budget, String otherRequirements) {
		this.requestId = requestId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.situation = situation;
		this.budget = budget;
		this.otherRequirements = otherRequirements;
	}

	public void updateRequests(String title, String description, String situation, Long budget,
		String otherRequirements) {
		this.title = title;
		this.description = description;
		this.situation = situation;
		this.budget = budget;
		this.otherRequirements = otherRequirements;
	}
}