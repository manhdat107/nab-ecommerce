package com.vdc.ecommerce.testController;

import com.google.gson.Gson;
import com.vdc.ecommerce.model.request.SignInRequest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthControllerTest {

    private static final Gson GSON = new Gson();

    @Autowired
    private MockMvc mvc;

    private static final String LOGIN_URL = "/auth/signin";

    @Test
    public void inValidUsername() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("asd");
        signInRequest.setPassword("aaaaaaaaaaaaa");
        String request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(LOGIN_URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("User Not Found")));


        // username is empty
        signInRequest.setUsername("");
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(LOGIN_URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("User Not Found")));


        // username is null
        signInRequest.setUsername(null);
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(LOGIN_URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("User Not Found")));

    }


    @Test
    public void invalidPassword() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("admin");
        signInRequest.setPassword("asd");
        String request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(LOGIN_URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // password is empty
        signInRequest.setPassword("");
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(LOGIN_URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // password is null
        signInRequest.setPassword(null);
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(LOGIN_URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    public void loginValid() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("admin");
        signInRequest.setPassword("admin");

        String request = GSON.toJson(signInRequest);

        mvc.perform(MockMvcRequestBuilders.post("/auth/signin").content(request).contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token", Matchers.is(Matchers.not(Matchers.nullValue()))));
    }


    private String toJson(SignInRequest signInRequest) {
        return GSON.toJson(signInRequest);
    }
}
