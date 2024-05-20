package com.mfc.coordinating.requests.vo.req;

import java.time.LocalDate;

import com.mfc.coordinating.requests.enums.ReviewStates;

import lombok.Getter;

@Getter
public class RequestsCreateReqVo {
	String title;
	String description;
	Long options;
	Long totalPrice;
	String situation;
	String referenceImages;
	String myImages;
	Long budget;
	String brand;
	String otherRequirements;
	LocalDate deadline;
	ReviewStates state;
}
