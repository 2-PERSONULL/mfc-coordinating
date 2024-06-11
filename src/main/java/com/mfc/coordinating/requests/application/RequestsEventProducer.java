package com.mfc.coordinating.requests.application;

import java.time.LocalDate;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestsEventProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendRequestsHistoryCreateEvent(Long requestId, String userId, String partnerId, LocalDate deadline) {
		String message = String.format("RequestId: %d, UserId: %s, PartnerId: %s, Deadline: %s",
			requestId, userId, partnerId, deadline);
		kafkaTemplate.send("request-history-create", message);
	}
}