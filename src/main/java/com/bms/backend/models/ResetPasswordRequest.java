package com.bms.backend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ResetPasswordRequest {
    private String token;
    private String password;
}
