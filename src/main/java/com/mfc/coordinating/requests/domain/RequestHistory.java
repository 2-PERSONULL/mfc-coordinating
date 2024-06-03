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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "request_history")
public class RequestHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long requestId;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String partnerId;

	@Column(nullable = false)
	private LocalDate deadline;

	@Column(nullable = false)
	private RequestsStates status;

	@Column(nullable = false)
	private String title;

	@Builder
	public RequestHistory(Long requestId, String userId, String partnerId, LocalDate deadline, RequestsStates status, String title) {
		this.requestId = requestId;
		this.userId = userId;
		this.partnerId = partnerId;
		this.deadline = deadline;
		this.status = status;
		this.title = title;
	}

	public void updateStatus(RequestsStates status) {
		this.status = status;
	}
}
