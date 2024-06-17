package com.mfc.coordinating.requests.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashDeductionRequestDto {
	private String userId;
	private String amount;
}