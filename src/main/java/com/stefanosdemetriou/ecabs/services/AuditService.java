package com.stefanosdemetriou.ecabs.services;

import org.springframework.amqp.core.Message;

import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

public interface AuditService {

	public void getBookingUpdate(Message message, BookingDto bookingDto);
}
