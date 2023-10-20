package com.login.controller;

import com.login.model.PasswordResetTokenModel;
import com.login.model.UsersModel;
import com.login.repository.PasswordResetTokenRepository;
import com.login.repository.UsersRepository;
import com.login.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/passwordResetRequest")
    public String displayResetPasswordForm() {
        // return your form here or forward to an HTML page
        return "password_reset_request";
    }

    @PostMapping("/passwordResetRequest")
    public String handlePasswordResetRequest(@RequestParam String email) {
        boolean sent = passwordResetService.sendPasswordResetLink(email);
        if (sent) {
            return "We have sent you a password reset link to your email";
        } else {
            return "Error sending reset email";
        }
    }

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/sendResetLink")
    public String sendResetLink(String email) {
        boolean isSent = passwordResetService.sendPasswordResetLink(email);
        return isSent ? "email_sent_page" : "error_page";
    }

    @GetMapping("/resetPassword")
    public String resetPasswordForm(@RequestParam String token, Model model) {
        boolean isValid = passwordResetService.validateToken(token);
        if (isValid) {
            PasswordResetTokenModel tokenModel = passwordResetTokenRepository.findByToken(token).orElse(null);
            if (tokenModel != null) {
                model.addAttribute("email", tokenModel.getEmail());
                UsersModel user = usersRepository.findByEmail(tokenModel.getEmail()).orElse(null);
                if (user != null) {
                    model.addAttribute("login", user.getLogin());
                }
            }
            return "reset_password";
        }
        return "error_page";
    }


    @PostMapping("/doReset")
    public String resetPassword(String token, String password, String confirmPassword) {
        boolean isReset = passwordResetService.resetPassword(token, password, confirmPassword);
        return isReset ? "redirect:/login" : "error_page";
    }
}
