package com.loduone.damn.service;

import com.loduone.damn.dto.BookingRequest;
import com.loduone.damn.enums.BookingStatus;
import com.loduone.damn.model.Booking;

import java.util.List;

public interface ExpertBookingService {
    Booking createBooking(BookingRequest request);
    List<Booking> getAllBookings(String userId, Boolean isClient);
    Booking updateBookingStatus(String bookingId, BookingStatus status);
}
