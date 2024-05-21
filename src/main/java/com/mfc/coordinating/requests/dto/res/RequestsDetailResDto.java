package com.mfc.coordinating.requests.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestsDetailResDto {
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
	private String partnerId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static RequestsDetailResDto toBuild(Requests requests){
		return RequestsDetailResDto.builder()
			.requestId(requests.getRequestId())
			.userId(requests.getUserId())
			.title(requests.getTitle())
			.description(requests.getDescription())
			.options(requests.getOptions())
			.totalPrice(requests.getTotalPrice())
			.situation(requests.getSituation())
			.referenceImages(requests.getReferenceImages())
			.myImages(requests.getMyImages())
			.budget(requests.getBudget())
			.brand(requests.getBrand())
			.otherRequirements(requests.getOtherRequirements())
			.deadline(requests.getDeadline())
			.state(requests.getState())
			.partnerId(requests.getPartnerId())
			.createdAt(requests.getCreatedAt())
			.updatedAt(requests.getUpdatedAt())
			.build();
	}
}
