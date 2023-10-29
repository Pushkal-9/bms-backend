package com.bms.backend.Repository;

import com.bms.backend.entity.Seat;
import com.bms.backend.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {
    List<Seat> findAllByShows(Optional<Shows> show);

    Seat findAllById(Long Id);

}