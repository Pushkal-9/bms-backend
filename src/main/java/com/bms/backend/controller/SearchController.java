package com.bms.backend.controller;

import com.bms.backend.Repository.SeatRepository;
import com.bms.backend.Repository.ShowsRepository;
import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.entity.*;
import com.bms.backend.models.ShowFilterRequest;
import com.bms.backend.services.CityService;
import com.bms.backend.services.MovieService;
import com.bms.backend.services.ScreenService;
import com.bms.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final CityService cityService;
    private final MovieService movieService;
    private final ShowsRepository showsRepository;
    private final TheatreRepository theatreRepository;
    private final ShowService showService;
    private final SeatRepository seatRepository;
    private final ScreenService screenService;

    @GetMapping("/search/city")
    public List<City> getCityList(){
        return cityService.getAll();
    }

    @GetMapping("/search/movies")
    public List<Movie> getMovieListByCity(@RequestParam("city_id") long cityId){
        return movieService.getAllMoviesByCity(cityId);
    }


    @GetMapping("/search/all/movies")
    public List<Movie> getMovieList(){
        return movieService.getAllMovies();
    }

    @GetMapping("/search/screen")
    public List<Screen> getScreensByTheatre(@RequestParam("theatre_id") long theatreId){
        return screenService.findScreensByTheatre(theatreId);
    }

    @GetMapping("/search/shows")
    public List<Shows> getShowList(@RequestParam("city_id") long cityId,@RequestParam("movie_id") long movieId){
        log.info("city : {}", cityId);
        List<Theatre> theatres = theatreRepository.findAllByCityId(cityId);
        List<Shows> shows = new ArrayList<>();
        for(Theatre theatre : theatres){
            shows.addAll(showsRepository.findAllByMovieIdAndTheatreId(movieId,theatre.getId()));
        }
        return shows;
    }


    @GetMapping("/search/theatre")
    public List<Theatre> getShowList(@RequestParam("city_id") long cityId){
        return theatreRepository.findAllByCityId(cityId);
    }

    @PostMapping("/search/show")
    public List<Shows> getShowCList(@RequestBody ShowFilterRequest showFilterRequest){

        return showService.searchShows(showFilterRequest);
    }

    @GetMapping("/search/seats")
    public List<Seat> getShowCList(@RequestParam("show_id") Long showId){
        Optional<Shows> shows = showsRepository.findById(showId);
        return seatRepository.findAllByShows(shows);
    }

    @GetMapping("/search/seat/")
    public Seat getSeat(@RequestParam("seat_id") Long seatId){
        return seatRepository.findAllById(seatId);
    }


}
