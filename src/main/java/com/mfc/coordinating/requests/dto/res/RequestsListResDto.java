package com.mfc.coordinating.requests.dto.res;

import java.time.LocalDate;

import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestsListResDto {
	private String id;
	private Long requestId;
	private String userId;
	private String userImageUrl;
	private String userNickName;
	private Short userGender;
	private int userAge;
	private String title;
	private LocalDate createdDate;
	private String partnerId;
	private RequestsStates status;
	private LocalDate deadline;
}