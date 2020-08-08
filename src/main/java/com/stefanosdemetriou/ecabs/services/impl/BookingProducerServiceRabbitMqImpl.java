package com.stefanosdemetriou.ecabs.services.impl;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.stefanosdemetriou.ecabs.config.RabbitMqConfig;
import com.stefanosdemetriou.ecabs.services.BookingProducerService;
import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingProducerServiceRabbitMqImpl implements BookingProducerService {

	private final RabbitTemplate rabbitTemplate;

	@Override
	public void addBooking(BookingDto bookingDto) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE_NAME, RabbitMqConfig.BOOKING_ADD_ROUTING_KEY,
				bookingDto);
	}

	@Override
	public void editBooking(UUID bookingId, BookingDto bookingDto) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE_NAME, RabbitMqConfig.BOOKING_EDIT_ROUTING_KEY,
				bookingDto, m -> {
					m.getMessageProperties().getHeaders().put("bookingId", bookingId);
					return m;
				});
	}

	@Override
	public void deleteBooking(UUID bookingId) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE_NAME, RabbitMqConfig.BOOKING_DELETE_ROUTING_KEY,
				new BookingDto(), m -> {
					m.getMessageProperties().getHeaders().put("bookingId", bookingId);
					return m;
				});
	}

}
