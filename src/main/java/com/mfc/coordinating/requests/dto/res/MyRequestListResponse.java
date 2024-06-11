package com.mfc.coordinating.requests.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyRequestListResponse {
	private Long requestId;
	private String title;
}
