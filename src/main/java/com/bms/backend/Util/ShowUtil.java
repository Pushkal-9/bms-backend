package com.bms.backend.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Seat;
import com.bms.backend.entity.Shows;
import com.bms.backend.entity.Theatre;
import com.bms.backend.models.ShowFilterRequest;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ShowUtil {

    public static Specification<Shows> createSpecification(ShowFilterRequest showFilterRequest) {

        List<Specification<Shows>> specifications = new ArrayList<>();

        specifications.add(getCurrentAndFutureShowSpec());

        if (Objects.nonNull(showFilterRequest.getMovieId())) {
            specifications.add(getShowByMovieNameSpec(showFilterRequest.getMovieId()));
        }

        if (Objects.nonNull(showFilterRequest.getCityId())) {
            specifications.add(getShowByCitySpec(showFilterRequest.getCityId()));
        }

        if (Objects.nonNull(showFilterRequest.getShowDate())) {
            specifications.add(getShowByDateSpec(showFilterRequest.getShowDate()));
        }

        if (Objects.nonNull(showFilterRequest.getStartTime())) {
            specifications.add(getShowByAfterTimeSpec(showFilterRequest.getStartTime()));
        }

        if (Objects.nonNull(showFilterRequest.getEndTime())) {
            specifications.add(getShowByBeforeTimeSpec(showFilterRequest.getEndTime()));
        }

        if (Objects.nonNull(showFilterRequest.getMinRate())) {
            specifications.add(getShowByMinRateSpec(showFilterRequest.getMinRate()));
        }

        if (Objects.nonNull(showFilterRequest.getMaxRate())) {
            specifications.add(getShowByMaxRateSpec(showFilterRequest.getMaxRate()));
        }

        return createSpecification(specifications);

    }

    public static Specification<Shows> rate(long min, long max) {

        List<Specification<Shows>> specifications = new ArrayList<>();

        specifications.add(getShowByMinRateSpec(min));
        specifications.add(getShowByMaxRateSpec(max));


        return createSpecification(specifications);

    }

    private static Specification<Shows> createSpecification(List<Specification<Shows>> specs) {

        Specification<Shows> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }

    private static Specification<Shows> getCurrentAndFutureShowSpec() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), LocalDate.now());
    }

    private static Specification<Shows> getShowByMovieNameSpec(long movieId) {
        return (root, query, criteriaBuilder) -> {
            Join<Shows, Movie> movieJoin = root.join("movie");
            return criteriaBuilder.equal( movieJoin.get("id"), movieId);
        };
    }

    private static Specification<Shows> getShowByCitySpec(long cityId) {
        return (root, query, criteriaBuilder) -> {
            Join<Shows, Theatre> theaterJoin = root.join("theatre");
            return criteriaBuilder.equal(theaterJoin.get("cityId"), cityId);
        };
    }

    private static Specification<Shows> getShowByMinRateSpec(long rate) {
        return (root, query, criteriaBuilder) -> {
            Join<Shows, Seat> theaterJoin = root.join("seats");
            return criteriaBuilder.greaterThanOrEqualTo(theaterJoin.get("rate"), rate);
        };
    }

    private static Specification<Shows> getShowByMaxRateSpec(long rate) {
        return (root, query, criteriaBuilder) -> {
            Join<Shows, Seat> theaterJoin = root.join("seats");
            return criteriaBuilder.lessThanOrEqualTo(theaterJoin.get("rate"), rate);
        };
    }

    private static Specification<Shows> getShowByDateSpec(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date);
    }

    private static Specification<Shows> getShowByTimeSpec(LocalDateTime startTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"), startTime);
    }

    private static Specification<Shows> getShowByAfterTimeSpec(LocalDateTime startTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime);
    }

    private static Specification<Shows> getShowByBeforeTimeSpec(LocalDateTime endTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), endTime);
    }



}
