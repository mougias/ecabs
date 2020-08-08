package com.stefanosdemetriou.ecabs.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Booking {

	@Id
	private UUID bookingId;

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

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TripWaypoint> tripWayPoints;

	private boolean deleted;
}