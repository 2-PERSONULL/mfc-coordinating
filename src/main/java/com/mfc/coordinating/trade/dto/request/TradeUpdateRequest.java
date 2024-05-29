package com.mfc.coordinating.trade.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class TradeUpdateRequest {
	private LocalDate dueDate;
	private Integer totalPrice;
	private Long options;
}
