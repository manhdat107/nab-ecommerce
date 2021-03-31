package com.vdc.ecommerce.model.security;

import com.vdc.ecommerce.model.CommonEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse extends CommonEntity {
    private Long id;
    private String token;
    private String type = "Bearer";

    public JwtResponse(Long id, String token) {
        this.id = id;
        this.token = token;
    }

}
