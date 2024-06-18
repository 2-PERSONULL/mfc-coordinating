package com.mfc.coordinating.requests.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.requests.dto.kafka.CashDeductionRequestDto;
import com.mfc.coordinating.requests.dto.kafka.CreateChatRoomDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestsEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void publishDeductCashEvent(String userId, String amount) {
		try {
			CashDeductionRequestDto dto = CashDeductionRequestDto.builder()
				.userId(userId)
				.amount(amount)
				.build();
			kafkaTemplate.send("cash-deduction-request", dto);
		} catch (Exception e) {
			log.error("Failed to publish cash deduction event", e);
		}
	}

	public void createChatRoom(CreateChatRoomDto dto) {
		kafkaTemplate.send("create-chatroom", dto);
	}
}
