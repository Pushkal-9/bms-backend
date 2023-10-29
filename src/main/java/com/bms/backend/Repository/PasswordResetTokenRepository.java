package com.bms.backend.Repository;

import com.bms.backend.entity.PasswordResetTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenModel, Integer> {
    Optional<PasswordResetTokenModel> findByToken(String token);
    Optional<PasswordResetTokenModel> findByEmail(String email);
}
