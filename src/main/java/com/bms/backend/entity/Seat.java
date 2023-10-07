package com.bms.backend.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;



import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table(name = "seat")
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "row_number")
    private String rowNumber;

    @Column(name = "column_number")
    private String columnNumber;

    @Column(name = "rate")
    private int rate;

    @Column(name = "available")
    private boolean available;

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="show_id")
    private Shows shows;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
