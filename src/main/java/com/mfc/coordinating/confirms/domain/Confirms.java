package com.mfc.coordinating.confirms.domain;

import java.time.LocalDate;

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
@Table(name = "confirms")
public class Confirms extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "confirm_id", nullable = false)
	private Long confirmId;

	@Column(name = "partner_id", nullable = false)
	private String partnerId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(nullable = false)
	private Long options;

	@Column(name = "total_price", nullable = false)
	private Long totalPrice;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;
	
	@Column(name = "request_id", nullable = false)
	private Long requestId;
}
