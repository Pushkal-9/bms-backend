package com.bms.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
    @NoArgsConstructor
    public class BookingRequest {
        private Long amount;
        private List<Long> seatIds;
        private String email;
        private String seatData;
    }

