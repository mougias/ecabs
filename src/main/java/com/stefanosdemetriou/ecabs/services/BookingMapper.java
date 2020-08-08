package com.stefanosdemetriou.ecabs.services;

import org.mapstruct.Mapper;

import com.stefanosdemetriou.ecabs.domain.models.Booking;
import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

@Mapper
public interface BookingMapper {
	Booking bookingDtoToBooking(BookingDto bookingDto);
}
