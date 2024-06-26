package com.mfc.coordinating.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.mfc.coordinating.coordinates.dto.kafka.CoordinatesSubmittedEventDto;
import com.mfc.coordinating.requests.dto.kafka.PaymentCompletedEvent;
import com.mfc.coordinating.reviews.dto.kafka.ReviewSummaryDto;
import com.mfc.coordinating.trade.dto.kafka.TradeDueDateEventDto;
import com.mfc.coordinating.trade.dto.kafka.TradeSettledEventDto;

@EnableKafka
@Configuration
public class KafkaConfig {

	private static final String TRUSTED_PACKAGES = "*";
	private static final String EARLIEST = "earliest";

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String consumerGroupId;

	// Common configuration methods

	private Map<String, Object> getCommonProducerConfig() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return configProps;
	}

	private Map<String, Object> getCommonConsumerConfig() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, TRUSTED_PACKAGES);
		return configProps;
	}

	// Generic producer factory method

	private <T> ProducerFactory<String, T> createProducerFactory() {
		return new DefaultKafkaProducerFactory<>(getCommonProducerConfig());
	}

	// Producer configurations

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return createProducerFactory();
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public KafkaTemplate<String, TradeSettledEventDto> tradeSettledKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}

	@Bean
	public KafkaTemplate<String, ReviewSummaryDto> reviewSummaryDtoKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}

	@Bean
	public KafkaTemplate<String, CoordinatesSubmittedEventDto> coordinatesSubmittedKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}

	@Bean
	public KafkaTemplate<String, TradeDueDateEventDto> tradeDueDateKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}

	// Consumer configurations

	@Bean
	public ConsumerFactory<String, Long> longConsumerFactory() {
		Map<String, Object> config = getCommonConsumerConfig();
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Long> longKafkaListenerContainerFactory() {
		return createListenerContainerFactory(longConsumerFactory());
	}

	@Bean
	public ConsumerFactory<String, PaymentCompletedEvent> paymentCompletedConsumerFactory() {
		Map<String, Object> config = getCommonConsumerConfig();
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);
		return new DefaultKafkaConsumerFactory<>(
			config,
			new StringDeserializer(),
			new JsonDeserializer<>(PaymentCompletedEvent.class, false)
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent> factory = createListenerContainerFactory(paymentCompletedConsumerFactory());
		factory.setConcurrency(2);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, CoordinatesSubmittedEventDto> coordinatesSubmittedConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
			getCommonConsumerConfig(),
			new StringDeserializer(),
			new JsonDeserializer<>(CoordinatesSubmittedEventDto.class, false)
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CoordinatesSubmittedEventDto> coordinatesSubmittedKafkaListenerContainerFactory() {
		return createListenerContainerFactory(coordinatesSubmittedConsumerFactory());
	}

	@Bean
	public ConsumerFactory<String, TradeSettledEventDto> TradeSettledConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
			getCommonConsumerConfig(),
			new StringDeserializer(),
			new JsonDeserializer<>(TradeSettledEventDto.class, false)
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, TradeSettledEventDto> tradeSettledKafkaListenerContainerFactory() {
		return createListenerContainerFactory(TradeSettledConsumerFactory());
	}

	@Bean
	public ConsumerFactory<String, TradeDueDateEventDto> tradeDueDateConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
			getCommonConsumerConfig(),
			new StringDeserializer(),
			new JsonDeserializer<>(TradeDueDateEventDto.class, false)
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, TradeDueDateEventDto> tradeDueDateEventKafkaListenerContainerFactory() {
		return createListenerContainerFactory(tradeDueDateConsumerFactory());
	}

	private <T> ConcurrentKafkaListenerContainerFactory<String, T> createListenerContainerFactory(ConsumerFactory<String, T> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
}