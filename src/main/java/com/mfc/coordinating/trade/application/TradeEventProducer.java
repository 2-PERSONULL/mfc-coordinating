package com.mfc.coordinating.trade.application;

import java.time.LocalDate;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.trade.dto.kafka.TradeSettledEventDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeEventProducer {

	private final KafkaTemplate<String, TradeSettledEventDto> tradeSettledKafkaTemplate;

	public void sendTradeSettledEvent(String userUuid, String partnerUuid, Double amount,
		Long tradeId, LocalDate dueDate, String requestId) {
		TradeSettledEventDto eventDto = new TradeSettledEventDto(userUuid, partnerUuid, dueDate, amount, tradeId, requestId);
		tradeSettledKafkaTemplate.send("partner-completion", eventDto);
	}
}