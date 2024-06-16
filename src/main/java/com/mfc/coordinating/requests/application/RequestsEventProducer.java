package com.mfc.coordinating.requests.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.requests.dto.kafka.RequestUserInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestsEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;


	public void requestUserInfo(String uuid) {
		try {
			RequestUserInfoDto dto = RequestUserInfoDto.builder().userId(uuid).build();
			kafkaTemplate.send("user-info-request", dto);
		} catch (Exception e) {
			log.error("Failed to send user info request event", e);
		}
	}

	public void requestAuthInfo(String uuid) {
		try {
			RequestUserInfoDto dto = RequestUserInfoDto.builder().userId(uuid).build();
			kafkaTemplate.send("auth-info-request", dto);
		} catch (Exception e) {
			log.error("Failed to send auth info request event", e);
		}
	}
}
