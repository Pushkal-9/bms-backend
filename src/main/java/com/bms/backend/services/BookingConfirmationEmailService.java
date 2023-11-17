package com.bms.backend.services;


import com.bms.backend.models.BookingDetailsResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BookingConfirmationEmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private static final String BOOKING_CONFIRMATION_SUBJECT = "Booking Confirmation";
    private static final String BOOKING_CONFIRMATION_TEMPLATE = "bookingConfirmationEmailTemplate";

    @Async
    public void sendBookingConfirmation(BookingDetailsResponse bookingDetailsResponse) throws MessagingException {
        sendEmail(bookingDetailsResponse.getEmail(), formatBookingDetails(bookingDetailsResponse));
    }

    private void sendEmail(String recipientEmail, BookingDetailsResponse bookingDetails) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(BOOKING_CONFIRMATION_SUBJECT);
        helper.setTo(recipientEmail);
        helper.setFrom("your-email@example.com");
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("bookingDetails", bookingDetails);
        String content = templateEngine.process(BOOKING_CONFIRMATION_TEMPLATE, thymeleafContext);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    private BookingDetailsResponse formatBookingDetails(BookingDetailsResponse bookingDetailsResponse){
        LocalDateTime dateTime = LocalDateTime.parse(bookingDetailsResponse.getShowTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedDate = dateTime.minusDays(1).format(DateTimeFormatter.ofPattern("d, MMM, yyyy", Locale.ENGLISH));
        String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));
        bookingDetailsResponse.setShowDate(formattedDate);
        bookingDetailsResponse.setShowTime(formattedTime);
        return bookingDetailsResponse;
    }
}

