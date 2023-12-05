package com.bms.backend.services;

import com.bms.backend.Repository.SeatRepository;
import com.bms.backend.entity.Seat;
import com.bms.backend.entity.Shows;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatPopulatorService {

    private final SeatRepository seatRepository;


    public void populateSeatsForShow(Shows shows) {
        List<Seat> seats = new ArrayList<>();
        int vId = 1;

        for (int vRowNumber = 0; vRowNumber < 12; vRowNumber++) {
            for (int vColumnNumber = 0; vColumnNumber < 15; vColumnNumber++) {
                Seat seat = new Seat();
                seat.setRate(15);
                seat.setRowNumber(String.valueOf(vRowNumber));
                seat.setColumnNumber(String.valueOf(vColumnNumber));
                seat.setShows(shows);
                seat.setAvailable(true);
                seat.setCreatedAt(LocalDateTime.now());
                seat.setUpdatedAt(LocalDateTime.now());
                seats.add(seat);
                vId++;
            }
        }

        seatRepository.saveAll(seats);
    }
}
