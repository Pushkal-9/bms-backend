package com.bms.backend.models;

import com.bms.backend.entity.BookingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDetailsResponse {
    private Long id;

    private Long amount;

    private BookingStatus status;

    private LocalDateTime bookedAt;

    private String email;

    private String QRImageUrl;

    private String showTime;

    private String showDate;

    private String seatData;

    private String movieName;

    private String theatre;

    private String city;

    private String screen;
}
