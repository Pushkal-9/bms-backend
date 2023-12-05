package com.bms.backend.services;

import com.bms.backend.Repository.TheatreRepository;
import com.bms.backend.dto.TheatreCreateRequest;
import com.bms.backend.entity.Theatre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheatreService {

    private final TheatreRepository theatreRepository;

    public Theatre addTheatre(TheatreCreateRequest theatreCreateRequest){
        Theatre theatre = new Theatre();
        theatre.setAddress(theatreCreateRequest.getAddress());
        theatre.setName(theatreCreateRequest.getName());
        theatre.setCityId(theatreCreateRequest.getCityId());
        theatre.setCreatedAt(LocalDateTime.now());
        theatre.setUpdatedAt(LocalDateTime.now());
        return theatreRepository.save(theatre);
    }
}
