package com.loduone.damn.service;

import com.loduone.damn.core.ProcessingException;
import com.loduone.damn.dto.ExpertProfileDTO;
import com.loduone.damn.model.Booking;
import com.loduone.damn.model.ExpertProfile;
import com.loduone.damn.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.loduone.damn.core.StatusCode.FORBIDDEN;

@Service
public class BookingValidationService {
    @Autowired
    ExpertProfileService expertProfileService;

    public void validateBooking(Booking booking, ExpertProfileDTO expertProfile){
        String expertId = booking.getExpertId();
        ExpertProfileDTO expertProfileDTO = expertProfileService.getProfileById(expertId);
        if(expertProfileDTO==null){
            String message = "Expert not found";
            throw new ProcessingException(FORBIDDEN, message);
        }
        Boolean isServiceAvailable = false;
        for(ExpertProfileDTO.ServiceDTO serviceDTO: expertProfileDTO.getServicesOffered()){
            if(serviceDTO.getName().equals(booking.getService())) isServiceAvailable = true;
        }
        if(!isServiceAvailable){
            String message = "Service not availabe";
            throw new ProcessingException(FORBIDDEN, message);
        }

        if(!isTimeSlotAvailable(booking.getTimeSlot(), expertProfileDTO.getAvailability())){
            String message = "Time Slot not availabe";
            throw new ProcessingException(FORBIDDEN, message);
        }
    }

    public Boolean isTimeSlotAvailable(TimeSlot bookingSlot, List<TimeSlot> availability){
        System.out.println(bookingSlot);
        return availability.stream()
                .anyMatch(slot -> slot.getDate().equals(bookingSlot.getDate())
                        && slot.getStartTime().equals(bookingSlot.getStartTime()));
    }
}


