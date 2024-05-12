package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.controller.mapper.UserMapper;
import com.bukup.vetclinic.dto.VisitorRequest;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/visitors")
public class VisitorController {
    private final VisitorService visitorService;
    private final UserMapper userMapper;

    public VisitorController(final VisitorService visitorService, final UserMapper userMapper) {
        this.userMapper = userMapper;
        this.visitorService = visitorService;
    }

    @PreAuthorize("hasAuthority('ADMIN') || authentication.principal.id == #id")
    @GetMapping("/{id}/edit")
    public String editVisitor(@PathVariable Long id, Model model) {
        final Visitor visitor = visitorService.findById(id);
        final VisitorRequest visitorRequest = new VisitorRequest(visitor);
        model.addAttribute("visitor", visitorRequest);
        return "visitors/editVisitor";
    }

    @PreAuthorize("hasAuthority('ADMIN') || authentication.principal.id == #id")
    @PostMapping("/{id}")
    public String updateVisitor(@PathVariable Long id,
                                @Valid @ModelAttribute("visitor") VisitorRequest visitorRequest,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("visitor", visitorRequest);
            return "visitors/editVisitor";
        }

        final Visitor visitor = visitorService.findById(id);
        final User user = userMapper.mapRequestToUserUpdate(visitorRequest.getUserRequest(), visitor.getUser());
        visitor.setUser(user);

        visitorService.update(visitor);
        return "redirect:/profile/" + user.getId();
    }
}
