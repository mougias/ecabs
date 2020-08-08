package com.stefanosdemetriou.ecabs.services.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.stefanosdemetriou.ecabs.config.RabbitMqConfig;
import com.stefanosdemetriou.ecabs.services.AuditService;
import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditServiceRabbitMqImpl implements AuditService {

	@Override
	@RabbitListener(queues = RabbitMqConfig.AUDIT_QUEUE_NAME)
	public void getBookingUpdate(Message message, BookingDto bookingDto) {
		UUID bookingId = null;
		String bookingIdStr = (String) message.getMessageProperties().getHeader("bookingId");
		if (StringUtils.isNoneEmpty(bookingIdStr)) {
			bookingId = UUID.fromString(bookingIdStr);
		}
		String routingKey = message.getMessageProperties().getReceivedRoutingKey();

		switch (routingKey) {
		case RabbitMqConfig.BOOKING_ADD_ROUTING_KEY:
			log.info("Added new booking {}", bookingDto);
			return;
		case RabbitMqConfig.BOOKING_EDIT_ROUTING_KEY:
			log.info("Updated booking with id {}: {}", bookingId, bookingDto);
			return;
		case RabbitMqConfig.BOOKING_DELETE_ROUTING_KEY:
			log.info("Deleted booking with id {}", bookingId);
			return;
		default:
			log.warn("Received message with unknown routing key {}", routingKey);
		}
	}

}
