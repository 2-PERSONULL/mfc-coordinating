package com.mfc.coordinating.requests.vo.req;

import java.util.List;

import lombok.Getter;

@Getter
public class RequestsCreateReqVo {
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
