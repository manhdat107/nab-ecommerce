package com.vdc.ecommerce.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignInRequest {
    @NotBlank
    @Size(min=3, max = 60, message = "Username length must be more than 3 and less than 60 character")
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    @NotNull
    private String password;
}
