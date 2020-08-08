package com.stefanosdemetriou.ecabs.domain.models;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table
@Entity
public class TripWaypoint {

	@Id
	private UUID tripWayPointId;

	@ManyToOne
	private Booking booking;

	private Boolean lastStop;
	private String locality;
	private Double lat;
	private Double lng;
	private Instant tripWayPointTimestamp;
}