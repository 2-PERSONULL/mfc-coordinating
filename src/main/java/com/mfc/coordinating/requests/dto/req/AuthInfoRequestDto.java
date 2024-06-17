package com.mfc.coordinating.requests.dto.req;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfoRequestDto {
	private Short userGender;
	private LocalDate userBirth;
}
