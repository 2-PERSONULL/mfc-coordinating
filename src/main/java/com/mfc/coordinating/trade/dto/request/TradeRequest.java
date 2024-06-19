package com.mfc.coordinating.trade.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class TradeRequest {
	private String partnerId;
	private String userId;
	private Long options;
	private Double totalPrice;
	private LocalDate dueDate;
	private String requestId;
}
