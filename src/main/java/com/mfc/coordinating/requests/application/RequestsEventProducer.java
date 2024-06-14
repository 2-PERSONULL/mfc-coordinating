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

	public CompletableFuture<SendResult<String, String>> requestUserInfo(String uuid) {
		try {
			RequestUserInfoDto dto = RequestUserInfoDto.builder().userId(uuid).build();
			String message = objectMapper.writeValueAsString(dto);
			return kafkaTemplate.send("user-info-request", message);
		} catch (Exception e) {
			log.error("Failed to send user info request event", e);
			CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
			future.completeExceptionally(e);
			return future;
		}
	}

	public CompletableFuture<SendResult<String, String>> requestAuthInfo(String uuid) {
		try {
			RequestUserInfoDto dto = RequestUserInfoDto.builder().userId(uuid).build();
			String message = objectMapper.writeValueAsString(dto);
			return kafkaTemplate.send("auth-info-request", message);
		} catch (Exception e) {
			log.error("Failed to send auth info request event", e);
			CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
			future.completeExceptionally(e);
			return future;
		}
	}

	// public CompletableFuture<SendResult<String, String>> sendPartnerNotificationEvent(Long requestId, String partnerId) {
	// 	RequestsUserInfoDto dto = RequestsUserInfoDto.builder()
	// 		.requestId(requestId)
	// 		.partnerId(partnerId)
	// 		.build();
	//
	// 	try {
	// 		String message = objectMapper.writeValueAsString(dto);
	// 		return kafkaTemplate.send("partner-notification", message);
	// 	} catch (Exception e) {
	// 		log.error("Failed to send partner notification event", e);
	// 		CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
	// 		future.completeExceptionally(e);
	// 		return future;
	// 	}
	// }
}
