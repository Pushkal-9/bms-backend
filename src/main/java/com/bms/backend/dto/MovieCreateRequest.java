package com.bms.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieCreateRequest {

    private String name;

    private String language;

    private String rating;

    private String description;

    private String duration;

    private String country;

    private String genre;

    private LocalDate releaseDate;

    private String imageLink;
}
