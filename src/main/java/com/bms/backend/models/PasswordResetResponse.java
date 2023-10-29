package com.bms.backend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class PasswordResetResponse {

    private boolean success;
    private String message;
    private String token;
}
