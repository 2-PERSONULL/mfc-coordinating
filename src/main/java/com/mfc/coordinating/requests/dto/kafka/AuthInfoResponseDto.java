package com.mfc.coordinating.requests.dto.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthInfoResponseDto {
	private Long userId;
	private Short userGender;
	private int userAge;
}
