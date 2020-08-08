package com.stefanosdemetriou.ecabs.web.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stefanosdemetriou.ecabs.services.BookingProducerService;
import com.stefanosdemetriou.ecabs.web.dto.BookingDto;

import lombok.AllArgsConstructor;

@RestController("/bookings")
@AllArgsConstructor
public class BookingController {

	private final BookingProducerService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addBooking(@RequestBody BookingDto booking) {
		service.addBooking(booking);
	}

	@PutMapping("/{id}")
	public void editBooking(@PathVariable("id") UUID bookingId, @RequestBody BookingDto booking) {
		service.editBooking(bookingId, booking);
	}

	@DeleteMapping("/{id}")
	public void deleteBooking(@PathVariable("id") UUID bookingId) {
		service.deleteBooking(bookingId);
	}
}
