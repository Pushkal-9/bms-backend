package com.bms.backend.dto;

import com.bms.backend.commons.Genre;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsUpdateRequest {
    private Long id;
    private String name;
    private String favoriteGenres;
}
