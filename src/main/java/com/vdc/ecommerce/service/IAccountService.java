package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.dto.SignInRequest;
import com.vdc.ecommerce.model.dto.SignUpRequest;
import com.vdc.ecommerce.model.response.JsonResponseEntity;

public interface IAccountService {

    JsonResponseEntity<?> signIn(SignInRequest signInRequest);

    JsonResponseEntity<?> signUp(SignUpRequest signUpRequest);

}
