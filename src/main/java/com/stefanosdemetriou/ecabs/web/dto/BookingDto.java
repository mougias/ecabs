package com.stefanosdemetriou.ecabs.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookingDto {

	private String passengerName;
	private String passengerContactNumber;
	private OffsetDateTime pickupTime;
	private Boolean asap = true;
	private Integer waitingTime;
	private Integer noOfPassengers;
	private BigDecimal price;
	private Integer rating;
	private Instant createdOn;
	private Instant lastModifiedOn;

	private List<TripWaypointDto> tripWayPoints;
}