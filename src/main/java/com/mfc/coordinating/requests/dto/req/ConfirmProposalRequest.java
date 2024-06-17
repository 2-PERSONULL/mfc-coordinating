package com.mfc.coordinating.requests.dto.req;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmProposalRequest {
	private Double price;
	private Instant confirmDate;
}
