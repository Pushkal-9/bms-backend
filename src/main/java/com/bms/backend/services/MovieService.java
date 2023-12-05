package com.bms.backend.services;

import com.bms.backend.Repository.MovieRepository;
import com.bms.backend.Repository.ScreenRepository;
import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.dto.MovieCreateRequest;
import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final TheatreRepository theatreRepository;
    private final MovieRepository movieRepository;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }


    public List<Movie> getAllMoviesByCity(long cityId){
        List<Theatre> theatres = theatreRepository.findAllByCityId(cityId);
        HashSet<Movie> resList = new HashSet<>();

        for(Theatre theatre : theatres){
            for(Shows show : theatre.getShows()){
                resList.add(show.getMovie());
            }
        }

        return resList.stream().toList();
    }

    public Movie addMovie(MovieCreateRequest movieCreateRequest){
        Movie movie = new Movie();
        movie.setCountry(movieCreateRequest.getCountry());
        movie.setDescription(movieCreateRequest.getDescription());
        movie.setGenre(movieCreateRequest.getGenre());
        movie.setName(movieCreateRequest.getName());
        movie.setDuration(movieCreateRequest.getDuration());
        movie.setLanguage(movieCreateRequest.getLanguage());
        movie.setRating(movieCreateRequest.getRating());
        movie.setImageLink(movieCreateRequest.getImageLink());
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setReleaseDate(movieCreateRequest.getReleaseDate());
        return movieRepository.save(movie);
    }
}
