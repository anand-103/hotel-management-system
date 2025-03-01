package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.service.ReservationService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/booking")
    public String showBookingPage(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "booking"; 
    }

    @PostMapping("/submit-booking")
    public String submitBooking(@ModelAttribute Reservation reservation) {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        reservation.setUsername(loggedInUsername);

        // Save reservation using service
        reservationService.saveReservation(reservation);

        return "redirect:/booking"; 
    }

    @GetMapping("/reservations")
    public String showUserReservations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        List<Reservation> reservations = reservationService.getUserReservations(loggedInUsername);
        model.addAttribute("reservations", reservations);

        return "reservations"; 
    }
    
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservationById(id);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting reservation");
        }
    }
    
    
    @GetMapping("/reservations/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        return "edit-reservation"; // Returns the Thymeleaf template
    }

    @PostMapping("/reservations/update")
    public String updateReservation(@ModelAttribute Reservation reservation) {
        reservationService.updateReservation(reservation);
        return "redirect:/reservations"; // Redirect back to the list
    }
}
    
