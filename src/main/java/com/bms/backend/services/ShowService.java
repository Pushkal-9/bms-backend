package com.bms.backend.services;

import com.bms.backend.Repository.ShowsRepository;
import com.bms.backend.Util.ShowUtil;
import com.bms.backend.entity.Shows;
import com.bms.backend.models.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowService {


    private final ShowsRepository showsRepository;

    public PageResponse<Shows> searchShows(String movieName, String city, LocalDate showDate, LocalDateTime startTime, int pageNo, int limit) {

        log.info("Searching Shows by Params: [showName: " + movieName + ", city: " + city + ", showDate: " + showDate + ", showTime: " + startTime + "]");

        Specification<Shows> specifications = ShowUtil.createSpecification(movieName, city, showDate, startTime);

        Page<Shows> showsPage = showsRepository.findAll(specifications, PageRequest.of(pageNo - 1, limit));

        log.info("Found " + showsPage.getNumberOfElements() + " Shows on Page: " + showsPage.getNumber());

        PageResponse<Shows> pageResponse = new PageResponse<>();

        if (showsPage.hasContent()) {
            pageResponse.setNumber(pageNo);
            pageResponse.setRecords(showsPage.getNumberOfElements());

            pageResponse.setTotalPages(showsPage.getTotalPages());
            pageResponse.setTotalRecords(showsPage.getTotalElements());

            pageResponse.setData(showsPage.getContent());
        }

        return pageResponse;
    }

}
