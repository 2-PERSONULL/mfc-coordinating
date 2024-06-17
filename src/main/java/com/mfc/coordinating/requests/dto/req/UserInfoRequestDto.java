package com.mfc.coordinating.requests.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoRequestDto {
	private String userImageUrl;
	private String userNickName;
}
