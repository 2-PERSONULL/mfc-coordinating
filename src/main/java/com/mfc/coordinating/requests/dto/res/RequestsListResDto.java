package com.mfc.coordinating.requests.dto.res;

import java.time.Instant;

import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestsListResDto {
	private String requestId;
	private String userId;
	private String userImageUrl;
	private String userNickName;
	private Short userGender;
	private int userAge;
	private String title;
	private Instant createdDate;
	private String partnerId;
	private RequestsStates status;
	private Instant deadline;
}