package com.stefanosdemetriou.ecabs.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMqConfig {

	public static final String MESSAGE_EXCHANGE_NAME = "MessageExchange";
	public static final String BOOKING_EXCHANGE_NAME = "BookingExchange";

	public static final String AUDIT_QUEUE_NAME = "MessageAuditQueue";
	public static final String BOOKING_ADD_QUEUE_NAME = "BookingAddQueue";
	public static final String BOOKING_EDIT_QUEUE_NAME = "BookingEditQueue";
	public static final String BOOKING_DELETE_QUEUE_NAME = "BookingDeleteQueue";

	public static final String BOOKING_ADD_ROUTING_KEY = "booking.add";
	public static final String BOOKING_EDIT_ROUTING_KEY = "booking.edit";
	public static final String BOOKING_DELETE_ROUTING_KEY = "booking.delete";

	// EXCHANGES
	@Bean
	FanoutExchange messageExchange() {
		return new FanoutExchange(MESSAGE_EXCHANGE_NAME);
	}

	@Bean
	TopicExchange bookingExchange() {
		return new TopicExchange(BOOKING_EXCHANGE_NAME);
	}

	// QUEUES
	@Bean
	Queue auditQueue() {
		// default: durable, non-exclusive, non-auto-delete
		return new Queue(AUDIT_QUEUE_NAME);
	}

	@Bean
	Queue bookingAddQueue() {
		// default: durable, non-exclusive, non-auto-delete
		return new Queue(BOOKING_ADD_QUEUE_NAME);
	}

	@Bean
	Queue bookingEditQueue() {
		// default: durable, non-exclusive, non-auto-delete
		return new Queue(BOOKING_EDIT_QUEUE_NAME);
	}

	@Bean
	Queue bookingDeleteQueue() {
		// default: durable, non-exclusive, non-auto-delete
		return new Queue(BOOKING_DELETE_QUEUE_NAME);
	}

	// BINDINGS
	@Bean
	Binding auditQueueToMessageExchangeBinding(Queue auditQueue, FanoutExchange messageExchange) {
		return BindingBuilder.bind(auditQueue).to(messageExchange);
	}

	@Bean
	Binding bookingExchangeToMessageExchangeBinding(FanoutExchange messageExchange, TopicExchange bookingExchange) {
		return BindingBuilder.bind(bookingExchange).to(messageExchange);
	}

	@Bean
	Binding bookingAddQueueToBookingExchangeBinding(TopicExchange bookingExchange, Queue bookingAddQueue) {
		return BindingBuilder.bind(bookingAddQueue).to(bookingExchange).with(BOOKING_ADD_ROUTING_KEY);
	}

	@Bean
	Binding bookingEditQueueToBookingExchangeBinding(TopicExchange bookingExchange, Queue bookingEditQueue) {
		return BindingBuilder.bind(bookingEditQueue).to(bookingExchange).with(BOOKING_EDIT_ROUTING_KEY);
	}

	@Bean
	Binding bookingDeleteQueueToBookingExchangeBinding(TopicExchange bookingExchange, Queue bookingDeleteQueue) {
		return BindingBuilder.bind(bookingDeleteQueue).to(bookingExchange).with(BOOKING_DELETE_ROUTING_KEY);
	}

	// RabbitTemplate & converter
	@Bean
	public Jackson2JsonMessageConverter converter() {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		return new Jackson2JsonMessageConverter(mapper);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(CachingConnectionFactory cf, Jackson2JsonMessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(cf);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
}
