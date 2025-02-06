package com.loduone.damn.dto;

import com.loduone.damn.model.TimeSlot;
import lombok.Data;

@Data
public class BookingRequest {
    private String clientId;
    private String expertId;
    private TimeSlot timeSlot;
    private String service;
}
