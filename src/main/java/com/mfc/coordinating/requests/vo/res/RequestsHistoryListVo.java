package com.mfc.coordinating.requests.vo.res;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestsHistoryListVo {
	private Long requestId;
	private String userId;
	private String partnerId;
	private LocalDate deadline;
	private String status;
	private String title;
	private String userImageUrl;
	private String userNickName;
	private Short userGender;
	private int userAge;
	private LocalDate createdDate;
}
