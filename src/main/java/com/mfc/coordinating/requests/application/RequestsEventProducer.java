package com.mfc.coordinating.requests.application;

import java.time.LocalDate;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfc.coordinating.requests.dto.RequestHistoryCreateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestsEventProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void sendRequestsHistoryCreateEvent(Long requestId, String userId, String partnerId, LocalDate deadline) {
		RequestHistoryCreateDto dto = RequestHistoryCreateDto.builder()
			.requestId(requestId)
			.userId(userId)
			.partnerId(partnerId)
			.deadline(deadline)
			.build();

		try {
			String message = objectMapper.writeValueAsString(dto);
			kafkaTemplate.send("request-history-create", message);
		} catch (Exception e) {
			// 예외 처리 로직 추가
			e.printStackTrace();
		}
	}
}