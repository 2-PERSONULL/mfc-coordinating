package com.mfc.coordinating.confirms.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ConfirmsRequest {
	private String partnerId;
	private String userId;
	private Long options;
	private Integer totalPrice;
	private LocalDate dueDate;
	private Long requestId;
}
