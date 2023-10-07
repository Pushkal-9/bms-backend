package com.bms.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;



import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", columnDefinition = "TIME", nullable = false)
    private LocalDateTime startTime;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="screen_id", nullable=false)
    private Screen screen;

    @ManyToOne
    @JoinColumn(name="theatre_id", nullable=false)
    private Theatre theatre;

    @OneToMany(mappedBy = "shows", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Seat> seats;

}
