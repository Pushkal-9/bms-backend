package com.bms.backend.dto;

import com.bms.backend.entity.Movie;
import com.bms.backend.entity.Screen;
import com.bms.backend.entity.Seat;
import com.bms.backend.entity.Theatre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShowCreateRequest {

    private LocalDate date;

    private String startTime;

    private Long movieId;

    private Long screenId;

    private Long theatreId;
}
