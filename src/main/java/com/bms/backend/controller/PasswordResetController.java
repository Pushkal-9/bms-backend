package com.bms.backend.controller;
import com.bms.backend.Repository.PasswordResetTokenRepository;
import com.bms.backend.entity.PasswordResetTokenModel;
import com.bms.backend.models.PasswordResetResponse;
import com.bms.backend.models.ResetPasswordRequest;
import com.bms.backend.services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PasswordResetController {


    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordResetService passwordResetService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/passwordResetRequest")
    public PasswordResetResponse handlePasswordResetRequest(@RequestParam String email) {
        boolean status= passwordResetService.sendPasswordResetLink(email);
        String message = status?"A link to reset password is sent to your email":"Invalid email";

        return new PasswordResetResponse(status,message,null);
    }

    @GetMapping("/resetPassword")
    public PasswordResetResponse resetPasswordForm(@RequestParam("token") String token) {
        boolean isValid = passwordResetService.validateToken(token);

        if (isValid) {
            PasswordResetTokenModel tokenModel = passwordResetTokenRepository.findByToken(token).orElse(null);
            if (tokenModel != null) {
                return new PasswordResetResponse(true,tokenModel.getEmail(),token);
            }
        }
            return new PasswordResetResponse(false,"",token);
    }



    @PostMapping("/doReset")
    public boolean resetPassword(@RequestBody ResetPasswordRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return passwordResetService.resetPassword(request.getToken(), encodedPassword);
    }
}