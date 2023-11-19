package com.bms.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "seats")
    private String seatsAsString;

    @Column(name = "seat_data")
    private String seatData;

    @Column(name = "qr_image_url")
    private String QRImageUrl;

    @Transient
    private List<Long> seats;



    public List<Long> getSeats() {
        if (seatsAsString != null && !seatsAsString.isEmpty()) {
            seats = Arrays.stream(seatsAsString.split(","))
                    .map(Long::parseLong)
                    .toList();
        }
        return seats;
    }

    public void setSeats(List<Long> seats) {
        this.seats = seats;
        if (seats != null && !seats.isEmpty()) {
            this.seatsAsString = seats.stream()
                    .map(String::valueOf)
                    .reduce((s1, s2) -> s1 + "," + s2)
                    .orElse("");
        } else {
            this.seatsAsString = null;
        }
    }
}

