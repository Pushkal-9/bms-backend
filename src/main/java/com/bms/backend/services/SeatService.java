package com.bms.backend.services;

import com.bms.backend.Repository.SeatRepository;
import com.bms.backend.entity.Seat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {
    private final SeatRepository seatRepository;

    @Transactional
    public boolean blockSeats(List<Long> seatIds) {
        log.info("blocking seats : {}", seatIds);
        List<Seat> seats = seatRepository.findAllByIdInAndAvailableIsTrueAndBlockedIsFalse(seatIds);

        if (seats.size() == seatIds.size()) {
            LocalDateTime blockedAt = LocalDateTime.now();
            seatRepository.blockSeatsByIds(seatIds, blockedAt);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean bookSeats(List<Long> seatIds) {
        log.info("booking seats : {}", seatIds);
        List<Seat> seats = seatRepository.findAllByIdInAndAvailableIsTrue(seatIds);

        if (seats.size() == seatIds.size()) {
            seatRepository.bookSeatsByIds(seatIds);
            return true;
        } else {
            return false;
        }
    }



    @Scheduled(fixedRate = 60000)
    public void unblockExpiredSeats() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Seat> expiredSeats = seatRepository.findAllByBlockedIsTrueAndBlockedAtBefore(currentDateTime.minusMinutes(15));
        expiredSeats.forEach(seat -> {
            seat.setBlocked(false);
            seatRepository.save(seat);
        });
    }
}
