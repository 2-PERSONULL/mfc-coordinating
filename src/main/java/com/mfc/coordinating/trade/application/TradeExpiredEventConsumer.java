package com.mfc.coordinating.trade.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.requests.dto.kafka.PaymentCompletedEvent;
import com.mfc.coordinating.trade.domain.Trade;
import com.mfc.coordinating.trade.infrastructure.TradeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeExpiredEventConsumer {

	private final TradeService tradeService;
	private final TradeRepository tradeRepository;

	@KafkaListener(topics = "expired-requests", groupId = "trade-expired-group", containerFactory = "longKafkaListenerContainerFactory")
	public void consumeTradeExpiredEvent(Long tradeId) {
		log.info("Received expired trade ID: {}", tradeId);
		tradeService.handleTradeExpired(tradeId);
	}

	@KafkaListener(topics = "payment-completed", containerFactory = "kafkaListenerContainerFactory")
	public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerId();

		Trade trade = tradeRepository.findByRequestIdAndPartnerId(requestId, partnerId)
			.orElseThrow(() -> new IllegalStateException("Trade not found"));
		trade.tradeSettled();
		tradeRepository.save(trade);
		log.info("Trade settled for requestId: {} and partnerId: {}", requestId, partnerId);
	}
}