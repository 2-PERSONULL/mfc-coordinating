package com.mfc.coordinating.requests.dto.res;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestsListResDto {
	// private Long id;
	private Long requestId;
	private String title;
	private String description;
	private LocalDate deadline;
}
