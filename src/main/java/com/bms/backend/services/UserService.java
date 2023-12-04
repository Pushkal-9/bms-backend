package com.bms.backend.services;


import com.bms.backend.dto.UserDetailsResponse;
import com.bms.backend.dto.UserDetailsUpdateRequest;
import com.bms.backend.dto.UserPaymentMethodDetailsResponse;
import com.bms.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    UserDetailsResponse getUserDetails(Long userId);

    UserDetailsResponse updateUserDetails(UserDetailsUpdateRequest updateRequest);

    UserPaymentMethodDetailsResponse getPaymentMethodDetails(Long userId);


        void deleteUser(User user);
}
