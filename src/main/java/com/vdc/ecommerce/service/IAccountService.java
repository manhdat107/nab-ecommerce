package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.request.SignInRequest;
import com.vdc.ecommerce.model.request.SignUpRequest;
import com.vdc.ecommerce.model.response.ResponseModel;

public interface IAccountService {

    ResponseModel<?> signIn(SignInRequest signInRequest);

    ResponseModel<?> signUp(SignUpRequest signUpRequest);

}
