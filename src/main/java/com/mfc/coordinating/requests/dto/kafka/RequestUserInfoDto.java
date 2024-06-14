package com.mfc.coordinating.requests.dto.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestUserInfoDto {
	private String userId;
}
