package com.mfc.coordinating.requests.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestHistoryCreateDto {
	private Long requestId;
	private String userId;
	private String partnerId;
	private LocalDate deadline;
}