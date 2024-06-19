package com.mfc.coordinating.trade.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.trade.dto.kafka.TradeSettledEventDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendTradeSettledEvent(String userUuid, String partnerUuid, Double amount, Long tradeId) {
		TradeSettledEventDto eventDto = new TradeSettledEventDto(userUuid, partnerUuid, amount, tradeId);
		kafkaTemplate.send("partner-completion", eventDto);
	}
}
