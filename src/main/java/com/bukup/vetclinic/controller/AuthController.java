package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.controller.mapper.UserMapper;
import com.bukup.vetclinic.dto.LoginRequest;
import com.bukup.vetclinic.dto.UserRequest;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.security.service.AuthService;
import com.bukup.vetclinic.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.authService = authService;
        this.userMapper = userMapper;
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
        return "redirect:/";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new UserRequest());
        return "register-form";
    }


    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userRequest);
            return "register-form";
        }
        final User user = userMapper.mapRequestToUser(userRequest);
        Visitor visitor = new Visitor();
        visitor.setUser(user);
        userService.createVisitorUser(visitor);
        return "redirect:/login";
    }
}
