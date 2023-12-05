package com.bms.backend.dto;

import lombok.Data;

@Data
public class TheatreCreateRequest {

    private String name;

    private long cityId;

    private String address;
}
