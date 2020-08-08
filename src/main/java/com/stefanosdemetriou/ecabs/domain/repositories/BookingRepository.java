package com.stefanosdemetriou.ecabs.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefanosdemetriou.ecabs.domain.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
