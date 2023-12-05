package com.bms.backend.Repository;

import com.bms.backend.entity.Screen;
import com.bms.backend.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByTheatre(Theatre theatre);
}
