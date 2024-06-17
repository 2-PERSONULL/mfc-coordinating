package com.mfc.coordinating.requests.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.kafka.PaymentCompletedEvent;
import com.mfc.coordinating.requests.enums.RequestsStates;
import com.mfc.coordinating.requests.infrastructure.RequestsRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestEventConsumer {

	private final RequestsRepository requestsRepository;

	@KafkaListener(topics = "payment-completed", groupId = "request-service")
	public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
		String requestId = event.getRequestId();
		String partnerId = event.getPartnerId();

		Requests request = requestsRepository.findByRequestId(requestId)
			.orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

		request.updatePartnerStatus(partnerId, RequestsStates.CONFIRMED);
		requestsRepository.save(request);
	}
}