package com.loduone.damn.service.impl;

import com.loduone.damn.dto.BookingRequest;
import com.loduone.damn.dto.ExpertProfileDTO;
import com.loduone.damn.enums.BookingStatus;
import com.loduone.damn.model.Booking;
import com.loduone.damn.model.ExpertProfile;
import com.loduone.damn.model.TimeSlot;
import com.loduone.damn.repository.BookingRepository;
import com.loduone.damn.service.BookingValidationService;
import com.loduone.damn.service.ExpertBookingService;
import com.loduone.damn.service.ExpertProfileService;
import com.loduone.damn.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.loduone.damn.enums.BookingStatus.PAYMENT_PENDING;
import static com.loduone.damn.enums.BookingStatus.SUCCESS;

@Service
public class ExpertBookingServiceImpl implements ExpertBookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingValidationService bookingValidationService;
    @Autowired
    private ExpertProfileService expertProfileService;
    @Autowired
    private PaymentService paymentService;

//    @Autowired
//    private NotificationService notificationService;

    public Booking createBooking(BookingRequest request) {
        try {
            Booking booking = new Booking();
            ExpertProfileDTO expertProfile = expertProfileService.getProfileById(request.getExpertId());
            booking.setClientId(request.getClientId());
            booking.setExpertId(request.getExpertId());
            booking.setService(request.getService());
            booking.setTimeSlot(request.getTimeSlot());
            booking.setStatus(PAYMENT_PENDING);
            booking.setCreatedAt(Instant.now());
            booking.setUpdatedAt(Instant.now());
            bookingValidationService.validateBooking(booking, expertProfile);
            // Notify expert about the new booking
//        notificationService.notifyExpert(savedBooking.getExpertId(), "New booking request");
            Map<String, String> paymentOrder = paymentService.createOrder(request.getTotalFee());
            String orderId = paymentOrder.get("orderId");
            booking.setOrderId(orderId);
            Booking savedBooking =  bookingRepository.save(booking);
            updateExpertAvailability(savedBooking, expertProfile);
            return savedBooking;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean verifyBooking(Map<String, Object> data) throws Exception{

        String paymentId = data.get("razorpay_payment_id").toString();
        String orderId = data.get("razorpay_order_id").toString();
        String signature = data.get("razorpay_signature").toString();

        System.out.println("paymentID" + paymentId);
        System.out.println("orderID" + orderId);
        System.out.println("signature" + signature);

        Boolean isVerified = paymentService.verifyPayment(paymentId, orderId, signature);
        if(!isVerified) return false;
        Booking booking = bookingRepository.findByOrderId(orderId);
        updateBookingStatus(booking.getId(), SUCCESS);
        return isVerified;
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
    private void updateExpertAvailability(Booking booking, ExpertProfileDTO expertProfile) {

        List<TimeSlot> updatedAvailability = expertProfile.getAvailability().stream()
                .filter(slot -> !(slot.getDate().equals(booking.getTimeSlot().getDate()) &&
                        slot.getStartTime().equals(booking.getTimeSlot().getStartTime())))
                .collect(Collectors.toList());

        ExpertProfileDTO updatedExpertProfile = expertProfile.toBuilder()
                .availability(updatedAvailability).build();
        expertProfileService.updateProfile(expertProfile.getId(), updatedExpertProfile);
    }
}
