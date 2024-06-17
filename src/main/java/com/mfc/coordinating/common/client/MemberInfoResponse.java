package com.mfc.coordinating.common.client;

import com.mfc.coordinating.requests.dto.req.UserInfoRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private UserInfoRequestDto result;
}

	// 생성자, 화면, 세터 등 필요한 메서드 추가}
