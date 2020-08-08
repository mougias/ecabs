package com.stefanosdemetriou.ecabs.web.dto;

import java.time.Instant;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TripWaypointDto {
	private Boolean lastStop;
	private String locality;
	private Double lat;
	private Double lng;
	private Instant tripWayPointTimestamp;
}