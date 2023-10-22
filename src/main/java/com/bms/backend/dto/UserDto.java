package com.bms.backend.dto;

public record UserDto(Long id, String username, String name, String email, String role) {
}