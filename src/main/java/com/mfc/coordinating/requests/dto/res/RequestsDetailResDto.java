package com.mfc.coordinating.requests.dto.res;

import java.time.LocalDate;
import java.util.List;

import com.mfc.coordinating.requests.domain.Requests.RequestPartner;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestsDetailResDto {
	private String requestId;
	private String userId;
	private String title;
	private String description;
	private String situation;
	private String budget;
	private String otherRequirements;
	private LocalDate createdDate;
	private String userImageUrl;
	private String userNickName;
	private Short userGender;
	private int userAge;
	private List<RequestPartner> partner;
	private List<String> brandIds;
	private List<String> categoryIds;
	private List<String> referenceImageUrls;
	private List<String> myImageUrls;
}