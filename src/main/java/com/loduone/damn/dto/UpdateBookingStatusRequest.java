package com.loduone.damn.dto;

import com.loduone.damn.enums.BookingStatus;
import lombok.Data;

@Data
public class UpdateBookingStatusRequest {
    private BookingStatus status;
}
