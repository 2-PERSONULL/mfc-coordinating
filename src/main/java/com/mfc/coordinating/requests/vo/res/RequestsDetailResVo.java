package com.mfc.coordinating.requests.vo.res;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestsDetailResVo {
	private Long requestId;
	private String userId;
	private String title;
	private String description;
	private Long options;
	private Long totalPrice;
	private String situation;
	private String referenceImages;
	private String myImages;
	private Long budget;
	private String brand;
	private String otherRequirements;
	private LocalDate deadline;
	private RequestsStates state;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
