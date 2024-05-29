package com.mfc.coordinating.trade.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradeResponse {
	private Long id;
	private String partnerId;
	private String userId;
	private Long options;
	private Integer totalPrice;
	private LocalDate dueDate;
	private Long requestId;
	private Short status;
}
