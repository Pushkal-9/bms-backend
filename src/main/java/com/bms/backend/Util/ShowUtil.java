package com.bms.backend.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.bms.backend.entity.Shows;
import org.springframework.data.jpa.domain.Specification;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ShowUtil {

    public static Specification<Shows> createSpecification(String movieName, String city, LocalDate showDate, LocalDateTime startTime) {

        List<Specification<Shows>> specifications = new ArrayList<>();

        specifications.add(getCurrentAndFutureShowSpec());
//
//        if (StringUtils.isNotBlank(movieName)) {
//            specifications.add(getShowByMovieNameSpec(movieName));
//        }
//
//        if (StringUtils.isNotBlank(city)) {
//            specifications.add(getShowByCitySpec(city));
//        }

        if (Objects.nonNull(showDate)) {
            specifications.add(getShowByDateSpec(showDate));
        }

        if (Objects.nonNull(startTime)) {
            specifications.add(getShowByTimeSpec(startTime));
        }

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
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("start_time"), LocalDate.now());
    }

//    private static Specification<Show> getShowByMovieNameSpec(String movieName) {
//        return (root, query, criteriaBuilder) -> {
//            Join<Show, Movie> movieJoin = (Join<Show, Movie>) root.join("movie");
//            return criteriaBuilder.equal((Expression<?>) movieJoin.get("name"), movieName);
//        };
//    }
//
//    private static Specification<Show> getShowByCitySpec(String city) {
//        return (root, query, criteriaBuilder) -> {
//            Join<Show, Theatre> theaterJoin = root.join("theatre");
//            return criteriaBuilder.equal(theaterJoin.get("city"), city);
//        };
//    }

    private static Specification<Shows> getShowByDateSpec(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date);
    }

    private static Specification<Shows> getShowByTimeSpec(LocalDateTime startTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("start_time"), startTime);
    }

}
