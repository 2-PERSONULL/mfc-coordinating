package com.mfc.coordinating.requests.dto.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
	private Long userId;
	private String userImageUrl;
	private String userNickName;
}
