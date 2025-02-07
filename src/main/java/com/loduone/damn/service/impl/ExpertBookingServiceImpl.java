package com.loduone.damn.service.impl;

import com.loduone.damn.dto.BookingRequest;
import com.loduone.damn.enums.BookingStatus;
import com.loduone.damn.model.Booking;
import com.loduone.damn.repository.BookingRepository;
import com.loduone.damn.service.BookingValidationService;
import com.loduone.damn.service.ExpertBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.loduone.damn.enums.BookingStatus.PENDING;

@Service
public class ExpertBookingServiceImpl implements ExpertBookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingValidationService bookingValidationService;
//    @Autowired
//    private NotificationService notificationService;

    public Booking createBooking(BookingRequest request) {
        Booking booking = new Booking();
        booking.setClientId(request.getClientId());
        booking.setExpertId(request.getExpertId());
        booking.setService(request.getService());
        booking.setTimeSlot(request.getTimeSlot());
        booking.setStatus(PENDING);
        booking.setCreatedAt(Instant.now());
        booking.setUpdatedAt(Instant.now());
        bookingValidationService.validateBooking(booking);
        // Notify expert about the new booking
//        notificationService.notifyExpert(savedBooking.getExpertId(), "New booking request");

        return bookingRepository.save(booking);
    }


    public List<Booking> getAllBookings(String userId, Boolean isClient) {
        if(isClient){
            return bookingRepository.findByClientId(userId);
        }
        return bookingRepository.findByExpertId(userId);
    }

    public Booking updateBookingStatus(String bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        booking.setUpdatedAt(Instant.now());
        Booking updatedBooking = bookingRepository.save(booking);

        // Notify client about the status update
        String notificationMessage = status.equals("ACCEPTED") ?
                "Your booking request has been accepted" : "Your booking request has been rejected";
//        notificationService.notifyClient(booking.getClientId(), notificationMessage);

        return updatedBooking;
    }
}
