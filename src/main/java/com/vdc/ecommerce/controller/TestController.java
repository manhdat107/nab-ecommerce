package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.response.JsonResponseEntity;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Test Api")
@RequestMapping(ApiConstant.API + ApiConstant.ADMIN)
public class TestController<T> {

    @GetMapping("hello")
    public JsonResponseEntity<T> testMethod() {
        return JsonResponseEntity.successful("hello world");
    }
}
