package com.mfc.coordinating.confirms.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ConfirmsUpdateRequest {
	private LocalDate dueDate;
	private Integer totalPrice;
	private Long options;
}
