package com.bms.backend.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "rating", nullable = false)
    private String rating;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "release_date", columnDefinition = "DATE", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "image_link")
    private String imageLink;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Shows> shows = new ArrayList<>();
}
