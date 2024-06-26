package com.mfc.coordinating.trade.application;

import java.time.LocalDate;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.trade.dto.kafka.PartnerSummaryDto;
import com.mfc.coordinating.trade.dto.kafka.TradeDueDateEventDto;
import com.mfc.coordinating.trade.dto.kafka.TradeSettledEventDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeEventProducer {

	private final KafkaTemplate<String, TradeSettledEventDto> tradeSettledKafkaTemplate;
	private final KafkaTemplate<String, TradeDueDateEventDto> tradeDueDateKafkaTemplate;
	private final KafkaTemplate<String, PartnerSummaryDto> partnerSummaryKafkaTemplate;

	private static final String TRADE_SETTLED_TOPIC = "partner-completion";
	private static final String TRADE_DUE_DATE_TOPIC = "trade-due-date";
	private static final String PARTNER_SUMMARY_TOPIC = "close-request";

	public void sendTradeSettledEvent(String userUuid, String partnerUuid, Double amount,
		Long tradeId, LocalDate dueDate, String requestId) {
		TradeSettledEventDto eventDto = new TradeSettledEventDto(userUuid, partnerUuid, dueDate, amount, tradeId, requestId);
		tradeSettledKafkaTemplate.send(TRADE_SETTLED_TOPIC, eventDto);
	}

	public void sendTradeDueDateEvent(String requestId, LocalDate dueDate, String partnerUuid) {
		TradeDueDateEventDto eventDto = new TradeDueDateEventDto(requestId, dueDate, partnerUuid);
		tradeDueDateKafkaTemplate.send(TRADE_DUE_DATE_TOPIC, eventDto);
	}

	public void closeRequest(PartnerSummaryDto dto) {
		partnerSummaryKafkaTemplate.send(PARTNER_SUMMARY_TOPIC, dto);
	}
}