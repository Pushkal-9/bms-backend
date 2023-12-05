package com.bms.backend.controller;

import com.bms.backend.dto.MovieCreateRequest;
import com.bms.backend.dto.ScreenCreateRequest;
import com.bms.backend.dto.ShowCreateRequest;
import com.bms.backend.dto.TheatreCreateRequest;
import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Screen;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import com.bms.backend.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MovieService movieService;
    private final TheatreService theatreService;
    private final ScreenService screenService;
    private final ShowService showService;
    private final SeatPopulatorService seatPopulatorService;

    @PutMapping("/add/movie")
    public Movie addMovie(@RequestBody MovieCreateRequest movieCreateRequest){
        return movieService.addMovie(movieCreateRequest);
    }

    @PutMapping("/add/theatre")
    public Theatre addTheatre(@RequestBody TheatreCreateRequest theatreCreateRequest){
        return theatreService.addTheatre(theatreCreateRequest);
    }

    @PutMapping("/add/screen")
    public Screen addTheatre(@RequestBody ScreenCreateRequest screenCreateRequest){
        return screenService.addScreen(screenCreateRequest);
    }

    @PutMapping("/add/shows")
    public Shows addTheatre(@RequestBody ShowCreateRequest showCreateRequest){
        Shows shows = showService.addShow(showCreateRequest);
        seatPopulatorService.populateSeatsForShow(shows);
        return shows;
    }


}
