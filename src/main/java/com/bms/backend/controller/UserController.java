package com.bms.backend.controller;

import com.bms.backend.dto.UserDetailsResponse;
import com.bms.backend.dto.UserDetailsUpdateRequest;
import com.bms.backend.dto.UserDto;
import com.bms.backend.dto.UserPaymentMethodDetailsResponse;
import com.bms.backend.entity.User;
import com.bms.backend.mapper.UserMapper;
import com.bms.backend.security.CustomUserDetails;
import com.bms.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bms.backend.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        return userMapper.toUserDto(user);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{username}")
    public UserDto deleteUser(@PathVariable String username) {
        User user = userService.validateAndGetUserByUsername(username);
        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }

    @PostMapping("/update")
    public UserDetailsResponse updateUser(@RequestBody UserDetailsUpdateRequest userDetailsUpdateRequest) {
        return userService.updateUserDetails(userDetailsUpdateRequest);
    }

    @GetMapping("/{userId}/details")
    public UserDetailsResponse getUserDetails(@PathVariable Long userId) {
        return userService.getUserDetails(userId);
    }

    @GetMapping("email/{email}/details")
    public UserDetailsResponse getUserDetailsByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional.map(user -> userService.getUserDetails(user.getId())).orElse(null);
    }

    @GetMapping("/{userId}/payment-details")
    public UserPaymentMethodDetailsResponse getSavedPaymentDetails(@PathVariable Long userId) {
        return userService.getPaymentMethodDetails(userId);
    }

}
