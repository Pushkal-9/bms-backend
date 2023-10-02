package com.bms.backend.controller;

import com.bms.backend.Repository.ShowsRepository;
import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import com.bms.backend.services.CityService;
import com.bms.backend.entity.City;
import com.bms.backend.services.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final CityService cityService;
    private final MovieService movieService;
    private final ShowsRepository showsRepository;
    private final TheatreRepository theatreRepository;

    @GetMapping("/search/city")
    public List<City> getCityList(){
        return cityService.getAll();
    }

    @GetMapping("/search/movies")
    public List<Movie> getMovieListByCity(@RequestParam("city_id") long cityId){
        return movieService.getAllMoviesByCity(cityId);
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


}
