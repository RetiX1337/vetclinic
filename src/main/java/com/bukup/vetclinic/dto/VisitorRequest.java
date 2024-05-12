package com.bukup.vetclinic.dto;

import com.bukup.vetclinic.model.Visitor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorRequest {
    @Valid
    @NotNull
    private UserRequest userRequest;

    public VisitorRequest() {}

    public VisitorRequest(final Visitor visitor) {
        this.userRequest = new UserRequest(visitor.getUser());
    }
}
