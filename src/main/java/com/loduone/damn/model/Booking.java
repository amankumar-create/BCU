package com.loduone.damn.model;

import com.loduone.damn.enums.BookingStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String clientId;
    private String expertId;
    private String service;
    private TimeSlot timeSlot;
    private BookingStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
