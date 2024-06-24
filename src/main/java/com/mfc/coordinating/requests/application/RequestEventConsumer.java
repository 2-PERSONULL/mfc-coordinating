package com.mfc.coordinating.requests.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mfc.coordinating.coordinates.dto.kafka.CoordinatesSubmittedEventDto;
import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.kafka.PaymentCompletedEvent;
import com.mfc.coordinating.requests.enums.RequestsStates;
import com.mfc.coordinating.requests.infrastructure.RequestsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestEventConsumer {

	private final RequestsRepository requestsRepository;

	@KafkaListener(topics = "payment-completed", groupId = "request-expired-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerId();

		Requests request = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

		request.updatePartnerStatus(partnerId, RequestsStates.CONFIRMED);
		log.info("Request confirmed: {}", request);
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
}