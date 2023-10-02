package com.bms.backend.services;

import com.bms.backend.Repository.CityRepository;
import com.bms.backend.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;


    public List<City> getAll(){
        return cityRepository.findAll();
    }

}
