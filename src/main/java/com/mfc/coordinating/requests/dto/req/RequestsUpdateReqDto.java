package com.mfc.coordinating.requests.dto.req;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestsUpdateReqDto {
	String title;
	String description;
	String situation;
	List<String> referenceImages;
	List<String> myImages;
	Long budget;
	List<String> brand;
	List<String> category;
	String otherRequirements;
}
