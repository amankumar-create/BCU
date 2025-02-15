package com.loduone.damn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResponse {
    private String bookingId;
    private String orderId;
    private String status;
    private String message;
}