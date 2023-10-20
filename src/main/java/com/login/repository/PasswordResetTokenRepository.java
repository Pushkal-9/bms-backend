package com.login.repository;

import com.login.model.PasswordResetTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenModel, Integer> {
    Optional<PasswordResetTokenModel> findByToken(String token);
    Optional<PasswordResetTokenModel> findByEmail(String email);
}

