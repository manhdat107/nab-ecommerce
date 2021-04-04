package com.vdc.ecommerce.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
public class SignUpRequest {

    @Size(min = 6, max = 50)
    @NotBlank(message = "Username can not have blank")
    @NotEmpty(message = "Username can not empty")
    @NotNull(message = "Username must not be null")
    private String username;

    @Size(min = 6, max = 40, message = "Minimum password length is 6 chars")
    @NotBlank(message = "Full name must not have blank")
    @NotNull(message = "Full name must not be null")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotEmpty(message = "Full name must not be empty")
    @Size(max = 50)
    private String fullName;

    @NotBlank
    @Size(max = 60)
    @Email(message = "Email invalid")
    private String email;

    private Set<String> roles;

    private String address;

    @Size(min = 9, max= 12, message = "Phone number is not valid")
    private Long phoneNumber;

}

