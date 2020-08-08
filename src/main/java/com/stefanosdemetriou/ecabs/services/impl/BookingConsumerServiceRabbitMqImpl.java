package com.stefanosdemetriou.ecabs.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.stefanosdemetriou.ecabs.config.RabbitMqConfig;
import com.stefanosdemetriou.ecabs.domain.models.Booking;
import com.stefanosdemetriou.ecabs.domain.repositories.BookingRepository;
import com.stefanosdemetriou.ecabs.services.BookingConsumerService;
import com.stefanosdemetriou.ecabs.services.BookingMapper;
import com.stefanosdemetriou.ecabs.services.BookingMapperImpl;
import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class BookingConsumerServiceRabbitMqImpl implements BookingConsumerService {

	private final BookingRepository repository;

	private final BookingMapper mapper = new BookingMapperImpl();

	@Override
	@RabbitListener(queues = RabbitMqConfig.BOOKING_ADD_QUEUE_NAME)
	public void addBooking(BookingDto bookingDto) {
		Booking booking = mapper.bookingDtoToBooking(bookingDto);
		booking.setBookingId(UUID.randomUUID());
		booking.getTripWayPoints().stream().forEach(e -> {
			e.setTripWayPointId(UUID.randomUUID());
			e.setBooking(booking);
		});
		repository.save(booking);
	}

	@Override
	@RabbitListener(queues = RabbitMqConfig.BOOKING_EDIT_QUEUE_NAME)
	public void editBooking(@Header("bookingId") UUID bookingId, BookingDto bookingDto) {
		Optional<Booking> bookingOpt = repository.findById(bookingId);
		if (bookingOpt.isEmpty()) {
			log.info("Received edit booking request for non-existing booking id {}", bookingId);
			return;
		}

		Booking booking = bookingOpt.get();

		if (booking.isDeleted()) {
			log.info("Received edit booking request for deleted booking with id {}", bookingId);
		}

		Booking newBooking = mapper.bookingDtoToBooking(bookingDto);
		// just in case for some reason we added id in dto in the future
		newBooking.setBookingId(bookingId);

		// create UUIDs for each trip that doesn't have one
		newBooking.getTripWayPoints().forEach(e -> {
			e.setTripWayPointId(e.getTripWayPointId() == null ? UUID.randomUUID() : e.getTripWayPointId());
			e.setBooking(booking);
		});
		repository.save(newBooking);
	}

	@Override
	@RabbitListener(queues = RabbitMqConfig.BOOKING_DELETE_QUEUE_NAME)
	public void deleteBooking(@Header("bookingId") UUID bookingId) {
		Optional<Booking> bookingOpt = repository.findById(bookingId);
		if (bookingOpt.isEmpty()) {
			log.info("Received delete booking request for non-existing booking id {}", bookingId);
			return;
		}

		Booking booking = bookingOpt.get();

		if (booking.isDeleted()) {
			log.info("Received delete booking request for deleted booking with id {}", bookingId);
		}

		// soft delete: leave record in db but mark as deleted
		booking.setDeleted(true);
		repository.save(booking);
	}

}
