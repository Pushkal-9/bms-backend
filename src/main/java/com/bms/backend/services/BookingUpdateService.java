package com.bms.backend.services;

import com.bms.backend.Repository.BookingRepository;
import com.bms.backend.entity.Booking;
import com.bms.backend.entity.BookingStatus;
import com.bms.backend.models.BookingDetailsResponse;
import com.bms.backend.models.BookingStatusUpdateRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingUpdateService {
    private final BookingRepository bookingRepository;
    private final S3Service s3Service;
    private final SeatService seatService;
    private final BookingConfirmationEmailService bookingConfirmationEmailService;
    private final BookingService bookingService;


    public void updateStatus(BookingStatusUpdateRequest bookingStatusUpdateRequest){
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingStatusUpdateRequest.getBookingId());
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            if(bookingStatusUpdateRequest.getStatus() == 0){
                booking.setStatus(BookingStatus.SUCCESS);
                seatService.bookSeats(booking.getSeats());
                s3Service.uploadQRCodeToS3(booking.getId());
                try {
                    bookingConfirmationEmailService.sendBookingConfirmation(bookingService.getBookingDetails(booking.getId()));
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(bookingStatusUpdateRequest.getStatus() == 1) {
                booking.setStatus(BookingStatus.CANCELLED);
            }
            bookingRepository.save(booking);
        }
    }
}
