package com.loduone.damn.controller;


import com.loduone.damn.dto.BookingRequest;
import com.loduone.damn.dto.UpdateBookingStatusRequest;
import com.loduone.damn.model.Booking;
import com.loduone.damn.service.ExpertBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private ExpertBookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/verify-booking")
    public ResponseEntity<Map<String, Object>> verifyBooking(@RequestBody Map<String, Object> data) {
        try {
            boolean isValid = bookingService.verifyBooking(data);
            System.out.println("isValid : " + isValid);
            if (isValid) {
                Map<String, Object> response = Collections.singletonMap("status", "VERIFIED");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = Collections.singletonMap("error", "Invalid signature");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(@RequestParam String userId, @RequestParam boolean isClient) {
        List<Booking> bookings = bookingService.getAllBookings(userId, isClient);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable String bookingId, @RequestBody UpdateBookingStatusRequest statusRequest) {
        Booking booking = bookingService.updateBookingStatus(bookingId, statusRequest.getStatus());
        return ResponseEntity.ok(booking);
    }
}
