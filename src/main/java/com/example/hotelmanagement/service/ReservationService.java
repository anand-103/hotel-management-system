package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public void saveReservation(Reservation reservation) {
        // Room prices per person per night
        int pricePerPerson = switch (reservation.getRoomType()) {
            case "Single Room" -> 1000;
            case "Double Room" -> 2000;
            case "Family Room" -> 3000;
            case "Honeymoon" -> 5000;
            default -> 0;
        };

        // Calculate total price
        double totalAmount = pricePerPerson * reservation.getNumPersons() * reservation.getNumRooms() * reservation.getPeriodOfStay();
        reservation.setTotalAmount(totalAmount);

        reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(String username) {
        return reservationRepository.findByUsername(username);
    }

    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }
    

    
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID: " + id));
    }

    public void updateReservation(Reservation updatedReservation) {
        Reservation existingReservation = getReservationById(updatedReservation.getId());

        existingReservation.setRoomType(updatedReservation.getRoomType());
        existingReservation.setNumRooms(updatedReservation.getNumRooms());
        existingReservation.setNumPersons(updatedReservation.getNumPersons());
        existingReservation.setNumChildren(updatedReservation.getNumChildren());
        existingReservation.setBuffet(updatedReservation.getBuffet() != null ? "yes" : "no");
        existingReservation.setPeriodOfStay(updatedReservation.getPeriodOfStay());
        existingReservation.setArrivalDate(updatedReservation.getArrivalDate());

        // Recalculate total amount
        int pricePerPerson = switch (existingReservation.getRoomType()) {
            case "Single Room" -> 1000;
            case "Double Room" -> 2000;
            case "Family Room" -> 3000;
            case "Honeymoon" -> 5000;
            default -> 0;
        };
        double totalAmount = pricePerPerson * existingReservation.getNumPersons() * existingReservation.getNumRooms() * existingReservation.getPeriodOfStay();
        existingReservation.setTotalAmount(totalAmount);

        reservationRepository.save(existingReservation);
    }
}

