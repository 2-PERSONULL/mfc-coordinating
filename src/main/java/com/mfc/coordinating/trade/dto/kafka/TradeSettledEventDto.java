package com.mfc.coordinating.trade.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeSettledEventDto {
	private String userUuid;
	private String partnerUuid;
	private Double amount;
	private Long tradeId;
}