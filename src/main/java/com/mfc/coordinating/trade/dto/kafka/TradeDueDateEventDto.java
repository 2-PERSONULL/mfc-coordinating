package com.mfc.coordinating.trade.dto.kafka;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeDueDateEventDto {
	private String requestId;
	private LocalDate dueDate;
	private String partnerUuid;
}