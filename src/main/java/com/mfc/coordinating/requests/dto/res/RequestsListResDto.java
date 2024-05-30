package com.mfc.coordinating.requests.dto.res;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RequestsListResDto {
	private Long id;
	private Long requestId;
	private String title;
	private String description;
	private LocalDate deadline;

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
}
