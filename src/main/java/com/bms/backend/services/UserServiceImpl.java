package com.bms.backend.services;

import com.bms.backend.Repository.UserPaymentMethodRepository;
import com.bms.backend.Repository.UserRepository;
import com.bms.backend.dto.UserDetailsResponse;
import com.bms.backend.dto.UserDetailsUpdateRequest;
import com.bms.backend.dto.UserPaymentMethodDetailsResponse;
import com.bms.backend.entity.User;
import com.bms.backend.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPaymentMethodRepository userPaymentMethodRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetailsResponse getUserDetails(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            userDetailsResponse.setUserType(user.getUserType());
            userDetailsResponse.setEmail(user.getEmail());
            userDetailsResponse.setId(user.getId());
            userDetailsResponse.setName(user.getName());
            userDetailsResponse.setImageUrl(user.getImageUrl());
            userDetailsResponse.setFavoriteGenres(user.getFavoriteGenres());
            userDetailsResponse.setMembershipType(user.getMembershipType());
            userDetailsResponse.setUsername(user.getUsername());

            return userDetailsResponse;

        }

        return null;
    }

    @Override
    public UserDetailsResponse updateUserDetails(UserDetailsUpdateRequest updateRequest) {
        User existingUser = userRepository.findById(updateRequest.getId()).orElse(null);

        if (existingUser != null) {
            if (updateRequest.getName() != null) {
                existingUser.setName(updateRequest.getName());
            }
            if (updateRequest.getFavoriteGenres() != null) {
                existingUser.setFavoriteGenres(updateRequest.getFavoriteGenres());
            }
            userRepository.save(existingUser);
        }
        assert existingUser != null;
        return getUserDetails(existingUser.getId());
    }

    @Override
    public UserPaymentMethodDetailsResponse getPaymentMethodDetails(Long userId){
        return new UserPaymentMethodDetailsResponse(userPaymentMethodRepository.findUserPaymentMethodsByUserId(userId));
    }


    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
