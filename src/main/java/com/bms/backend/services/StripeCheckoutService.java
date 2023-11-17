package com.bms.backend.services;
import com.bms.backend.Repository.SeatRepository;
import com.bms.backend.entity.Booking;
import com.bms.backend.entity.Seat;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StripeCheckoutService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${app.baseurl:http://localhost:3000}")
    private String clientBaseURL;

    private final SeatRepository seatRepository;

    public String createCheckoutSession(Booking booking) {
        try {
            Seat seat = seatRepository.findAllById(booking.getSeats().get(0));
            long showId = seat.getShows().getId();
            Stripe.apiKey = stripeSecretKey;

            SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
            builder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
            builder.setClientReferenceId(String.valueOf(booking.getId()));
            builder.setMode(SessionCreateParams.Mode.PAYMENT);
            builder.setSuccessUrl(getRedirectionUrl(showId,booking,0));
            builder.setCancelUrl(getRedirectionUrl(showId,booking,1));

            SessionCreateParams.LineItem.Builder lineItemBuilder = new SessionCreateParams.LineItem.Builder();
            lineItemBuilder.setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(booking.getAmount() * 100)
                            .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Booking")
                                            .build()
                            )
                            .build()
            );
            lineItemBuilder.setQuantity(1L);
            builder.addLineItem(lineItemBuilder.build());

            SessionCreateParams params = builder.build();
            Session session = Session.create(params);

            return session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getRedirectionUrl(long showId, Booking booking, int status){
        return clientBaseURL +"/show/" + showId + "/booking/" + booking.getId()+ "/status/" + status;
    }
}

