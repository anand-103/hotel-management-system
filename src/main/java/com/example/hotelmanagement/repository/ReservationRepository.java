package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByUsername(String username);
	
}
