package com.stefanosdemetriou.ecabs.services;

import java.util.UUID;

import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

public interface BookingProducerService {

	public void addBooking(BookingDto bookingDto);

	public void editBooking(UUID bookingId, BookingDto bookingDto);

	public void deleteBooking(UUID bookingId);
}
