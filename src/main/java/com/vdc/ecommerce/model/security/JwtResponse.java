package com.vdc.ecommerce.model.security;

import com.vdc.ecommerce.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse extends BaseEntity<Long> {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }

}
