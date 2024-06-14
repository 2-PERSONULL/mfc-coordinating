package com.mfc.coordinating.requests.application;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfc.coordinating.requests.dto.kafka.RequestUserInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestsEventProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void requestUserInfo(String uuid) {
		try {
			RequestUserInfoDto dto = RequestUserInfoDto.builder().userId(uuid).build();
			String message = objectMapper.writeValueAsString(dto);
			kafkaTemplate.send("user-info-request", message);
		} catch (Exception e) {
			log.error("Failed to send user info request event", e);
		}
	}

	public void requestAuthInfo(String uuid) {
		try {
			RequestUserInfoDto dto = RequestUserInfoDto.builder().userId(uuid).build();
			String message = objectMapper.writeValueAsString(dto);
			kafkaTemplate.send("auth-info-request", message);
		} catch (Exception e) {
			log.error("Failed to send auth info request event", e);
		}
	}
}
