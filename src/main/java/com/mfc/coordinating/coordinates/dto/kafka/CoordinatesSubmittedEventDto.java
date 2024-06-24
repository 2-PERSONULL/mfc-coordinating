package com.mfc.coordinating.coordinates.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesSubmittedEventDto {
	private String requestId;
	private String partnerId;
}