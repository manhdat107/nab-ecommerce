package com.vdc.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank
    @NotEmpty(message = "Please input your name")
    @Size(max = 50)
    private String name;

    @NotBlank
    @NotEmpty(message = "Username can not empty")
    @Size(min = 6, max = 50)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email(message = "Email invalid")
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40, message = "Minimum password length is 6 chars")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotNull
    private String fullName;

    private String address;

    private Long phoneNumber;

}

