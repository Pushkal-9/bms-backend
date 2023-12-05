package com.bms.backend.services;

import com.bms.backend.Repository.MovieRepository;
import com.bms.backend.Repository.ScreenRepository;
import com.bms.backend.Repository.ShowsRepository;
import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.Util.ShowUtil;
import com.bms.backend.dto.ShowCreateRequest;
import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Screen;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import com.bms.backend.models.PageResponse;
import com.bms.backend.models.ShowFilterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowService {


    private final ShowsRepository showsRepository;
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final MovieRepository movieRepository;

    public static LocalDateTime convertToDateTime(String startTime, LocalDate currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(startTime, formatter);
        return LocalDateTime.of(currentDate, localTime);
    }
    public List<Shows> searchShows(ShowFilterRequest showFilterRequest) {

        Specification<Shows> specifications = ShowUtil.createSpecification(showFilterRequest);

        return showsRepository.findAll(specifications);

    }

    public Shows addShow(ShowCreateRequest showCreateRequest){
        Optional<Screen> screen =screenRepository.findById(showCreateRequest.getScreenId());
        Optional<Theatre> theatre = theatreRepository.findById(showCreateRequest.getTheatreId());
        Optional<Movie> movie = movieRepository.findById(showCreateRequest.getMovieId());
        Shows shows = new Shows();
        shows.setDate(showCreateRequest.getDate());
        shows.setScreen(screen.orElse(null));
        shows.setMovie(movie.orElse(null));
        shows.setTheatre(theatre.orElse(null));
        shows.setStartTime(convertToDateTime(showCreateRequest.getStartTime(),showCreateRequest.getDate()));

        return showsRepository.save(shows);
    }

}
