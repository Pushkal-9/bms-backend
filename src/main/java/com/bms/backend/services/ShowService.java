package com.bms.backend.services;

import com.bms.backend.Repository.ShowsRepository;
import com.bms.backend.Util.ShowUtil;
import com.bms.backend.entity.Shows;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowService {


    private final ShowsRepository showsRepository;

    public List<Shows> searchShows(ShowFilterRequest showFilterRequest) {

        Specification<Shows> specifications = ShowUtil.createSpecification(showFilterRequest);

        return showsRepository.findAll(specifications);

    }

}
