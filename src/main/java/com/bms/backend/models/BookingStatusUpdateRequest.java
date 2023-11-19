package com.bms.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingStatusUpdateRequest {
    private int status;
    private Long bookingId;
}
