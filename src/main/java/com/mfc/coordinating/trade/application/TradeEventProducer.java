package com.mfc.coordinating.trade.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendTradeSettledEvent(String userUuid, String partnerUuid, Double amount, Long tradeId) {
		String message = String.format("UserUuid: %s, PartnerUuid: %s, Amount: %.2f, TradeId: %d", userUuid, partnerUuid, amount, tradeId);
		kafkaTemplate.send("partner-completion", message);
	}
}
