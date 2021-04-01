package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.dto.SignInRequest;
import com.vdc.ecommerce.model.dto.SignUpRequest;
import com.vdc.ecommerce.model.response.JsonResponse;
import com.vdc.ecommerce.service.IAccountService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Account controller")
@RequestMapping(ApiConstant.ACCOUNT)
public class WebController {
    private final IAccountService accountService;

    public WebController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(ApiConstant.SIGN_IN)
    public JsonResponse<?> signIn(@RequestBody SignInRequest signInRequest) {
        return accountService.signIn(signInRequest);
    }

    @PostMapping(ApiConstant.SIGN_UP)
    public JsonResponse<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return accountService.signUp(signUpRequest);
    }
}
