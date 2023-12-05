package com.bms.backend.services;

import com.bms.backend.Repository.ScreenRepository;
import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.dto.ScreenCreateRequest;
import com.bms.backend.entity.Screen;
import com.bms.backend.entity.Theatre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    public Screen addScreen(ScreenCreateRequest screenCreateRequest){
        Optional<Theatre> theatre = theatreRepository.findById(screenCreateRequest.getTheatreId());
        Screen screen = new Screen();
        screen.setName(screenCreateRequest.getName());
        screen.setTheatre(theatre.orElse(null));
        screen.setCreatedAt(LocalDateTime.now());
        screen.setUpdatedAt(LocalDateTime.now());
        return screenRepository.save(screen);
    }

    public List<Screen> findScreensByTheatre(Long theatreId){
        Optional<Theatre> theatre = theatreRepository.findById(theatreId);
        return screenRepository.findByTheatre(theatre.orElse(null));
    }
}
