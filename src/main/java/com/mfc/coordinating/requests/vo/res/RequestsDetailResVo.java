package com.mfc.coordinating.requests.vo.res;

import java.util.List;

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
	private String situation;
	private Long budget;
	private String brand;
	private String otherRequirements;
	private List<String> referenceImages;
	private List<String> myImages;
}
