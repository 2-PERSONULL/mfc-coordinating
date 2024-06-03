package com.mfc.coordinating.requests.vo.req;

import java.util.List;

import lombok.Getter;

@Getter
public class RequestsUpdateReqVo {
	String title;
	String description;
	String situation;
	List<String> referenceImages;
	List<String> myImages;
	Long budget;
	String brand;
	String otherRequirements;
}
