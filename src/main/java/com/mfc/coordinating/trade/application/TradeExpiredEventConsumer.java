package com.mfc.coordinating.trade.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeExpiredEventConsumer {

	private final TradeService tradeService;

	@KafkaListener(topics = "expired-requests", groupId = "trade-expired-group")
	public void consumeTradeExpiredEvent(Long tradeId) {
		tradeService.handleTradeExpired(tradeId);
	}
}
