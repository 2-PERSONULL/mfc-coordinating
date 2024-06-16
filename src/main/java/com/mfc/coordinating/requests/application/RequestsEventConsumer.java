package com.mfc.coordinating.requests.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfc.coordinating.requests.dto.kafka.AuthInfoResponseDto;
import com.mfc.coordinating.requests.dto.kafka.UserInfoResponseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestsEventConsumer {

	private final ObjectMapper objectMapper;

	@Getter
	private UserInfoResponseDto userInfoResponse;
	@Getter
	private AuthInfoResponseDto authInfoResponse;

	@KafkaListener(topics = "user-info-response")
	public void consumeUserInfoResponse(String message) {
		try {
			userInfoResponse = objectMapper.readValue(message, UserInfoResponseDto.class);
		} catch (Exception e) {
			log.error("Failed to consume user info response", e);
		}
	}

	@KafkaListener(topics = "auth-info-response")
	public void consumeAuthInfoResponse(String message) {
		try {
			authInfoResponse = objectMapper.readValue(message, AuthInfoResponseDto.class);
		} catch (Exception e) {
			log.error("Failed to consume auth info response", e);
		}
	}

}
