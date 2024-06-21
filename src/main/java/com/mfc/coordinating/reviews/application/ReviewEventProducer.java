package com.mfc.coordinating.reviews.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.coordinating.reviews.dto.kafka.ReviewSummaryDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewEventProducer {

	private final KafkaTemplate<String, ReviewSummaryDto> kafkaTemplate;

	public void publishCreateReviewEvent(String partnerId, Short rating) {
		try {
			ReviewSummaryDto reviewSummaryDto = ReviewSummaryDto.builder()
				.partnerId(partnerId)
				.rating(rating)
				.build();
			kafkaTemplate.send("create-review", reviewSummaryDto);
		} catch (Exception e) {
			log.error("Failed to publish create review event", e);
		}
	}

}
