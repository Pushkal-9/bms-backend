package com.bms.backend.Repository;

import com.bms.backend.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    public List<Theatre> findAllByCityId(long cityId);
}
