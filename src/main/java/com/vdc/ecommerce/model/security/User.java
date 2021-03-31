package com.vdc.ecommerce.model.security;

import com.vdc.ecommerce.model.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends CommonEntity<Long> {
    @NotEmpty(message = "username may be not empty.")
    @NotBlank(message = "username may be not blank.")
    @Size(min = 4, message = "must be at least 4 characters")
    private String username;

    @NotEmpty(message = "password may be not empty.")
    @NotBlank(message = "password may be not blank.")
    @Size(min = 4, message = "must be at least 4 characters")
    private String password;
    @Email(message = "Email Invalid, please try again.")
    private String email;
    private int phoneNumber;
    private String fullName;
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, @Email(message = "Email Invalid, please try again.") String email,
                int phoneNumber, String fullName, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }
}