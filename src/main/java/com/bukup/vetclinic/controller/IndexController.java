package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.security.model.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        System.out.println(userDetails.getFirstName());
        System.out.println("HERE IT IS");
        model.addAttribute("userDetails", userDetails);
        return "index";
    }

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        System.out.println(userDetails.getFirstName());
        System.out.println("HERE IT IS 2");
        model.addAttribute("userDetails", userDetails);
        return "index";
    }
}
