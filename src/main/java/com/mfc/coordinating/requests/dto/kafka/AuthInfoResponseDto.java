package com.mfc.coordinating.requests.dto.kafka;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthInfoResponseDto {
	private String userId;
	private Short userGender;
	private LocalDate userBirth;
}
