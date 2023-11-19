package com.bms.backend.Repository;

import com.bms.backend.entity.Seat;
import com.bms.backend.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {
    List<Seat> findAllByShows(Optional<Shows> show);

    Seat findAllById(Long Id);

    List<Seat> findAllByIdInAndAvailableIsTrueAndBlockedIsFalse(List<Long> seatIds);

    List<Seat> findAllByIdInAndAvailableIsTrue(List<Long> seatIds);
    @Modifying
    @Query("UPDATE Seat s SET s.blocked = true, s.blockedAt = :blockedAt WHERE s.id IN :seatIds")
    void blockSeatsByIds(@Param("seatIds") List<Long> seatIds, @Param("blockedAt") LocalDateTime blockedAt);

    @Modifying
    @Query("UPDATE Seat s SET s.available = false WHERE s.id IN :seatIds")
    void bookSeatsByIds(@Param("seatIds") List<Long> seatIds);

    List<Seat> findAllByBlockedIsTrueAndBlockedAtBefore(LocalDateTime dateTime);

}