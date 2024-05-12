package com.bukup.vetclinic.dto;

import com.bukup.vetclinic.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String password;

    public UserRequest() {}

    public UserRequest(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.phone = user.getPhone();
    }
}
