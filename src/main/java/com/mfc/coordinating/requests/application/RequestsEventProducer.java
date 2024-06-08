package com.mfc.coordinating.requests.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestsEventProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendRequestsHistoryCreateEvent(String userId) {
		String message = String.format("userId: %s", userId);
		kafkaTemplate.send("request-history-create", message);
	}
}

