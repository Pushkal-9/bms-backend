package com.login.service;

import com.login.model.PasswordResetTokenModel;
import com.login.model.UsersModel;
import com.login.repository.PasswordResetTokenRepository;
import com.login.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender emailSender;

    public boolean sendPasswordResetLink(String email) {
        email = email.toLowerCase().trim(); // Convert email to lowercase and trim whitespace
        UsersModel user = usersRepository.findByEmail(email).orElse(null);
        if (user != null) {
            String token = UUID.randomUUID().toString();

            PasswordResetTokenModel resetToken = new PasswordResetTokenModel();
            // resetToken.setLogin(user.getLogin()); // added
            resetToken.setEmail(email);
            resetToken.setToken(token);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            passwordResetTokenRepository.save(resetToken);

            String resetLink = "http://localhost:8080/resetPassword?token=" + token;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click on the following link to reset your password: " + resetLink);

            emailSender.send(message);
            return true;
        }
        return false;
    }

    public boolean validateToken(String token) {
        PasswordResetTokenModel resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        return resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now());
    }

    public boolean resetPassword(String token, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            LOGGER.warn("New password and confirm password do not match.");
            return false;
        }

        PasswordResetTokenModel resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        if (resetToken != null) {
            UsersModel user = usersRepository.findByEmail(resetToken.getEmail()).orElse(null);
            if (user != null) {
                user.setPassword(newPassword);
                usersRepository.save(user);

                // Delete the token after successful password reset
                passwordResetTokenRepository.delete(resetToken);
                return true;
            } else {
                LOGGER.warn("User not found for the given email from reset token.");
            }
        } else {
            LOGGER.warn("Invalid reset token.");
        }
        return false;
    }
}
