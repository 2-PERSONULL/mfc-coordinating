package com.mfc.coordinating.requests.dto.res;

import java.util.List;

import com.mfc.coordinating.requests.domain.Requests;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestsDetailResDto {
	private Long requestId;
	private String userId;
	private String title;
	private String description;
	private String situation;
	private Long budget;
	private String brand;
	private String otherRequirements;
	private List<String> referenceImages;
	private List<String> myImages;

	public static RequestsDetailResDto toBuild(Requests requests, List<String> referenceImageUrls, List<String> myImageUrls) {
		return RequestsDetailResDto.builder()
			.requestId(requests.getRequestId())
			.userId(requests.getUserId())
			.title(requests.getTitle())
			.description(requests.getDescription())
			.situation(requests.getSituation())
			.budget(requests.getBudget())
			.brand(requests.getBrand())
			.otherRequirements(requests.getOtherRequirements())
			.referenceImages(referenceImageUrls)
			.myImages(myImageUrls)
			.build();
	}
}