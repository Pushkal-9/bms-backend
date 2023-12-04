package com.bms.backend.dto;

import com.bms.backend.commons.Genre;
import com.bms.backend.commons.MembershipType;
import com.bms.backend.commons.UserType;
import com.bms.backend.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String favoriteGenres;
    private UserType userType;
    private MembershipType membershipType;
    private String imageUrl;

}

