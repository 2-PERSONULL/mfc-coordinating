package com.mfc.coordinating.trade.domain;

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
@ToString
@Getter
@Table(name = "trade")
public class Trade extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tradeId;

	@Column(name = "partner_id", nullable = false)
	private String partnerId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(nullable = false)
	private Long options;

	@Column(name = "total_price", nullable = false)
	private Integer totalPrice;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;
	
	@Column(name = "request_id", nullable = false)
	private Long requestId;

	@Column(name = "status", nullable = false)
	private Short status;

	@Builder
	public Trade(String partnerId, String userId, Long options, Integer totalPrice, LocalDate dueDate, Long requestId,
		Short status) {
		this.partnerId = partnerId;
		this.userId = userId;
		this.options = options;
		this.totalPrice = totalPrice;
		this.dueDate = dueDate;
		this.requestId = requestId;
		this.status = 0;
	}

	public void updateTrade(LocalDate dueDate, Integer totalPrice, Long options) {
		this.dueDate = dueDate;
		this.totalPrice = totalPrice;
		this.options = options;
	}

	public void updateStatus() {
		this.status = 1;
	}
}
