package com.mfc.coordinating.requests.dto.req;

import java.time.Instant;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmProposalRequest {
	private Double price;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Instant confirmDate;
}
