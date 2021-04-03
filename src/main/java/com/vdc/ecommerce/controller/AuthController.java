package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.request.SignInRequest;
import com.vdc.ecommerce.model.request.SignUpRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.IAccountService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Account controller")
@RequestMapping(ApiConstant.AUTH)
public class AuthController {
    private final IAccountService accountService;

    public AuthController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(ApiConstant.SIGN_IN)
    public ResponseModel<?> signIn(@RequestBody SignInRequest signInRequest) {
        return accountService.signIn(signInRequest);
    }

    @PostMapping(ApiConstant.SIGN_UP)
    public ResponseModel<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return accountService.signUp(signUpRequest);
    }
}