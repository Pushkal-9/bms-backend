package com.bms.backend.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScreenCreateRequest {

    private Long theatreId;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
