package com.loduone.damn.controller;


import com.loduone.damn.dto.BookingRequest;
import com.loduone.damn.dto.UpdateBookingStatusRequest;
import com.loduone.damn.model.Booking;
import com.loduone.damn.service.ExpertBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
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
