package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.LoginRequest;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.security.service.AuthService;
import com.bukup.vetclinic.service.RoleService;
import com.bukup.vetclinic.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthController {
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthService authService;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, UserService userService, RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "login-form";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            model.addAttribute("loginRequest", loginRequest);
            model.addAttribute("exceptionMessage", e.getMessage());
            return "login-form";
        }
        return "redirect:/home";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-form";
    }


    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register-form";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Set.of(roleService.readByName("USER"))));
        userService.create(user);
        return "redirect:/login";
    }
}
