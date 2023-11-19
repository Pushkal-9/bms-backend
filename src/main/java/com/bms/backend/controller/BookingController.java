package com.bms.backend.controller;

import com.bms.backend.entity.Booking;
import com.bms.backend.models.BookingDetailsResponse;
import com.bms.backend.models.BookingRequest;
import com.bms.backend.models.BookingResponse;
import com.bms.backend.models.BookingStatusUpdateRequest;
import com.bms.backend.services.BookingService;
import com.bms.backend.services.BookingUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final BookingUpdateService bookingUpdateService;

    @PostMapping("/initiate")
    public BookingResponse initiateBooking(@RequestBody BookingRequest bookingRequest) {
        log.info(bookingRequest.toString());
        return bookingService.initiateBooking(bookingRequest);
    }

    @PostMapping("/status")
    public void updateStatus(@RequestBody BookingStatusUpdateRequest bookingStatusUpdateRequest) {
        bookingUpdateService.updateStatus(bookingStatusUpdateRequest);
    }

    @GetMapping("/details")
    public BookingDetailsResponse getBookingDetails(@RequestParam("booking_id") Long bookingId) {
        return bookingService.getBookingDetails(bookingId);
    }
}
