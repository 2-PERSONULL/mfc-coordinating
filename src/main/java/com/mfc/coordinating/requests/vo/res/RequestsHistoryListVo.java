package com.mfc.coordinating.requests.vo.res;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class RequestsHistoryListVo {
	private Long requestId;
	private String userId;
	private String partnerId;
	private LocalDate deadline;
	private String status;
	private String title;
	private LocalDate createdDate;
}
