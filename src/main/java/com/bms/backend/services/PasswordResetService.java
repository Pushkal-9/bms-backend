package com.bms.backend.services;

import com.bms.backend.Repository.PasswordResetTokenRepository;
import com.bms.backend.Repository.UserRepository;
import com.bms.backend.entity.PasswordResetTokenModel;
import com.bms.backend.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${app.client.baseUrl:http://localhost:3000}")
    private String clientBaseUrl;

    public boolean sendPasswordResetLink(String email) {
        email = email.toLowerCase().trim(); // Convert email to lowercase and trim whitespace
        User user = usersRepository.findByEmail(email).orElse(null);
        if (user != null) {
            String token = UUID.randomUUID().toString();

            PasswordResetTokenModel resetToken = new PasswordResetTokenModel();

            resetToken.setEmail(email);
            resetToken.setToken(token);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            passwordResetTokenRepository.save(resetToken);

            String resetLink = clientBaseUrl + "/resetPassword?token=" + token;
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

        if (resetToken == null) {
            LOGGER.warn("Token not found in the database.");
            return false;
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            LOGGER.warn("Token has expired.");
            return false;
        }

        return true;
    }


    public boolean resetPassword(String token, String newPassword) {

        PasswordResetTokenModel resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        if (resetToken != null) {
            User user = usersRepository.findByEmail(resetToken.getEmail()).orElse(null);
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
