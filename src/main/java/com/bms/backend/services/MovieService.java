package com.bms.backend.services;

import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final TheatreRepository theatreRepository;

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
}
