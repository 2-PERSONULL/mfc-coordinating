package com.mfc.coordinating.requests.dto.req;

import java.time.LocalDate;

import com.mfc.coordinating.requests.enums.RequestsStates;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestsUpdateReqDto {
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
	RequestsStates state;
}
