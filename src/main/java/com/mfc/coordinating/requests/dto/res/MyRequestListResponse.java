package com.mfc.coordinating.requests.dto.res;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyRequestListResponse {
	private String requestId;
	private String title;
	private String situation;
	private String description;
	private Instant created_date;
	// my-request 줄 때, situation, description, created_date 주도록 변경
}
