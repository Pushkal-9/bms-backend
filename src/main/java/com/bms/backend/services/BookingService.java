package com.bms.backend.services;

import com.bms.backend.Repository.BookingRepository;
import com.bms.backend.Repository.CityRepository;
import com.bms.backend.Repository.SeatRepository;
import com.bms.backend.Repository.UserRepository;
import com.bms.backend.entity.*;
import com.bms.backend.models.BookingDetailsResponse;
import com.bms.backend.models.BookingRequest;

import com.bms.backend.models.BookingResponse;
import com.bms.backend.models.BookingStatusUpdateRequest;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Value("${app.baseurl:https://movie-radar-frontend.vercel.app}")
    private String clientBaseURL;
    private final BookingRepository bookingRepository;
    private final StripeCheckoutService stripeCheckoutService;
    private final SeatService seatService;
    private final UserServiceImpl userService;
    private final SeatRepository seatRepository;
    private final CityRepository cityRepository;
    @Value("${app.s3.path:https://movie-radar.s3.us-east-2.amazonaws.com/}")
    private String S3Path;
    public BookingResponse initiateBooking(BookingRequest bookingRequest){
        boolean isBlocked = seatService.blockSeats(bookingRequest.getSeatIds());
        if(isBlocked){
            Booking booking = createBooking(bookingRequest);
            return new BookingResponse(stripeCheckoutService.createCheckoutSession(booking));
        }
        return null;
    }

    private Booking createBooking(BookingRequest bookingRequest){
        Booking booking = new Booking();
        booking.setBookedAt(LocalDateTime.now());
        booking.setEmail(bookingRequest.getEmail());
        booking.setAmount(bookingRequest.getAmount());
        booking.setCustomerId(getUserId(bookingRequest.getEmail()));
        booking.setStatus(BookingStatus.PENDING);
        booking.setSeats(bookingRequest.getSeatIds());
        booking.setSeatData(bookingRequest.getSeatData());
        return bookingRepository.save(booking);
    }

    private Long getUserId(String email){
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional.map(User::getId).orElse(null);
    }

    public Booking getBooking(Long bookingId){
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        return bookingOptional.orElse(null);
    }

    public ByteArrayOutputStream generateQRCodeForBooking(Long bookingId) throws IOException, WriterException {
        String bookingDetails = clientBaseURL + "/booking/" + bookingId + "/details";

        int size = 250;
        String fileType = "png";

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(bookingDetails, com.google.zxing.BarcodeFormat.QR_CODE, size, size, hintMap);

        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        bufferedImage.createGraphics();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, fileType, outputStream);

        return outputStream;
    }

    public void updateQRCodeUrl(Long bookingId, String key){
        Booking booking = getBooking(bookingId);
        booking.setQRImageUrl(S3Path + key);
        bookingRepository.save(booking);
    }

    private City getCity(Long cityId){
        Optional<City> cityOptional = cityRepository.findById(cityId);
        return cityOptional.orElse(null);
    }

    public BookingDetailsResponse getBookingDetails(Long bookingId){
        Booking booking = getBooking(bookingId);
        Seat seat = seatRepository.findAllById(booking.getSeats().get(0));
        City city = getCity(seat.getShows().getTheatre().getCityId());
        BookingDetailsResponse bookingDetailsResponse = new BookingDetailsResponse();
        bookingDetailsResponse.setId(booking.getId());
        bookingDetailsResponse.setAmount(booking.getAmount());
        bookingDetailsResponse.setSeatData(booking.getSeatData());
        bookingDetailsResponse.setBookedAt(booking.getBookedAt());
        bookingDetailsResponse.setEmail(booking.getEmail());
        bookingDetailsResponse.setMovieName(seat.getShows().getMovie().getName());
        bookingDetailsResponse.setStatus(booking.getStatus());
        bookingDetailsResponse.setShowTime(seat.getShows().getStartTime().toString());
        bookingDetailsResponse.setShowDate(seat.getShows().getDate().toString());
        bookingDetailsResponse.setQRImageUrl(booking.getQRImageUrl());
        bookingDetailsResponse.setTheatre(seat.getShows().getTheatre().getName());
        bookingDetailsResponse.setCity(city.getName());
        bookingDetailsResponse.setScreen(seat.getShows().getScreen().getName());
        return bookingDetailsResponse;
    }

}
