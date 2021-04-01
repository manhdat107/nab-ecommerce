package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.dto.SignInRequest;
import com.vdc.ecommerce.model.dto.SignUpRequest;
import com.vdc.ecommerce.model.response.JsonResponse;

public interface IAccountService {

    JsonResponse<?> signIn(SignInRequest signInRequest);

    JsonResponse<?> signUp(SignUpRequest signUpRequest);

}
