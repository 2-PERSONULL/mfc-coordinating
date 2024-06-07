package com.mfc.coordinating.requests.dto.res;

import java.time.LocalDate;

import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RequestsListResDto {
	private Long id;
	private Long requestId;
	private String userId;
	private String userImageUrl;
	private String userNickName;
	private String userGender;
	private int userAge;
	private String partnerId;
	private String title;
	private LocalDate deadline;
	private LocalDate createdDate;
	private RequestsStates status;

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public void setStatus(RequestsStates status) {
		this.status = status;
	}
}
