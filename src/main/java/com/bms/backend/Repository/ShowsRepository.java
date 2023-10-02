package com.bms.backend.Repository;

import com.bms.backend.entity.Screen;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowsRepository extends JpaRepository<Shows, Long>, JpaSpecificationExecutor<Shows> {
    List<Shows> findAllByMovieId(long movieId);
    List<Shows> findAllByMovieIdAndTheatreId(long movieId,long theatreId);

}
