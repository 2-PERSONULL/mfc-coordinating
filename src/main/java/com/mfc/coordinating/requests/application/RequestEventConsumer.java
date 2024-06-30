package com.mfc.coordinating.requests.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mfc.coordinating.coordinates.dto.kafka.CoordinatesSubmittedEventDto;
import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.kafka.PaymentCompletedEvent;
import com.mfc.coordinating.requests.enums.RequestsStates;
import com.mfc.coordinating.requests.infrastructure.RequestsRepository;
import com.mfc.coordinating.trade.application.TradeEventProducer;
import com.mfc.coordinating.trade.dto.kafka.PartnerSummaryDto;
import com.mfc.coordinating.trade.dto.kafka.TradeDueDateEventDto;
import com.mfc.coordinating.trade.dto.kafka.TradeSettledEventDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestEventConsumer {

	private final RequestsRepository requestsRepository;
	private final TradeEventProducer producer;

	@KafkaListener(topics = "payment-completed", containerFactory = "requestPaymentCompletedContainerFactory")
	public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerId();
		log.info("Request confirmed: {}", requestId);
		log.info("Request confirmed: {}", partnerId);
		Requests request = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

		request.updatePartnerStatus(partnerId, RequestsStates.CONFIRMED);
		requestsRepository.save(request);
	}

	@KafkaListener(topics = "trade-due-date", containerFactory = "tradeDueDateEventKafkaListenerContainerFactory")
	public void handleTradeDueDateEvent(TradeDueDateEventDto event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerUuid();

		Requests request = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

		request.updatePartnerStatus(partnerId, RequestsStates.TRADE_CREATED);
		request.updateDeadline(partnerId,event.getDueDate());
		log.info("Trade created for request: {}, Partner: {}, Deadline set to: {}",
			request, partnerId, event.getDueDate());
		requestsRepository.save(request);
	}

	@KafkaListener(topics = "coordinates-submitted-topic", containerFactory = "coordinatesSubmittedKafkaListenerContainerFactory")
	public void handleCoordinatesSubmitted(CoordinatesSubmittedEventDto event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerId();

		Requests request = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

		request.updatePartnerStatus(partnerId, RequestsStates.COORDINATE_RECEIVED);
		log.info("Request coordinate received: {}", request);
		requestsRepository.save(request);
	}

	@KafkaListener(topics = "partner-completion", containerFactory = "tradeSettledKafkaListenerContainerFactory")
	public void handleTradeSettledEvent(TradeSettledEventDto event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerUuid();

		Requests request = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

		request.updatePartnerStatus(partnerId, RequestsStates.CLOSED);
		log.info("Request closed: {}", request);

		requestsRepository.save(request);
		producer.closeRequest(PartnerSummaryDto.builder()
						.partnerId(partnerId)
						.build());

	}
}