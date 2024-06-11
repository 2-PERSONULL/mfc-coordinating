package com.mfc.coordinating.requests.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mfc.coordinating.requests.dto.res.RequestsListResDto;
import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "request_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestHistory {
	@Id
	private Long id;

	private Long requestId;

	private String userId;

	private String partnerId;

	private LocalDate deadline;

	private LocalDate createdDate;

	private RequestsStates status;

	private String title;

	private String userImageUrl;

	private String userNickName;

	private Short userGender;

	private int userAge;

	@Builder
	public RequestHistory(Long requestId, String userId, String partnerId, LocalDate deadline, RequestsStates status, String title,
		String userImageUrl, String userNickName, Short userGender, int userAge) {
		this.requestId = requestId;
		this.userId = userId;
		this.partnerId = partnerId;
		this.deadline = deadline;
		this.status = status;
		this.title = title;
		this.userImageUrl = userImageUrl;
		this.userNickName = userNickName;
		this.userGender = userGender;
		this.userAge = userAge;
		this.createdDate = LocalDate.now();
	}

	public void updateStatus(RequestsStates status) {
		this.status = status;
	}

	public RequestsListResDto toDto() {
		return RequestsListResDto.builder()
			.requestId(this.requestId)
			.userId(this.userId)
			.userImageUrl(this.userImageUrl)
			.userNickName(this.userNickName)
			.userGender(this.userGender)
			.userAge(this.userAge)
			.partnerId(this.partnerId)
			.title(this.title)
			.deadline(this.deadline)
			.createdDate(this.createdDate)
			.status(this.status)
			.build();
	}
}