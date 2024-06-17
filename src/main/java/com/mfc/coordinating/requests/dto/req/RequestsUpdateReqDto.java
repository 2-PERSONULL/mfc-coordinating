package com.mfc.coordinating.requests.dto.req;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestsUpdateReqDto {
	private String title;
	private String description;
	private String situation;
	private String budget;
	private List<String> referenceImageUrls;
	private List<String> myImageUrls;
	private List<String> brandIds;
	private List<String> categoryIds;

	@Builder
	public RequestsUpdateReqDto(String title, String description, String situation, String budget,
		 List<String> referenceImageUrls, List<String> myImageUrls,
		List<String> brandIds, List<String> categoryIds) {
		this.title = title;
		this.description = description;
		this.situation = situation;
		this.budget = budget;
		this.referenceImageUrls = referenceImageUrls;
		this.myImageUrls = myImageUrls;
		this.brandIds = brandIds;
		this.categoryIds = categoryIds;
	}
}