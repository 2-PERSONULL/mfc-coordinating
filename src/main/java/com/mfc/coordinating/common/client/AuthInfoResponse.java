package com.mfc.coordinating.common.client;

import com.mfc.coordinating.requests.dto.req.AuthInfoRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthInfoResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private AuthInfoRequestDto result;

	// 생성자, 게터, 세터 등 필요한 메서드 추가
}
