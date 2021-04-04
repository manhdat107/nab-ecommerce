package com.vdc.ecommerce.testController;

import com.google.gson.Gson;
import com.vdc.ecommerce.common.RoleConstant;
import com.vdc.ecommerce.model.request.SignInRequest;
import com.vdc.ecommerce.model.request.SignUpRequest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTest {

    private static final Gson GSON = new Gson();

    @Autowired
    private MockMvc mvc;

    private static final String SIGN_IN = "/auth/signin";
    private static final String SIGN_UP = "/auth/signup";

    @Test
    public void inValidUsername() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("asd");
        signInRequest.setPassword("aaaaaaaaaaaaa");
        String request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("User Not Found")));


        // username is empty
        signInRequest.setUsername("");
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("User Not Found")));


        // username is null
        signInRequest.setUsername(null);
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))
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
        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // password is empty
        signInRequest.setPassword("");
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // password is null
        signInRequest.setPassword(null);
        request = toJson(signInRequest);
        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    public void loginValid() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("admin");
        signInRequest.setPassword("admin");

        String request = toJson(signInRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_IN).content(request).contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token", Matchers.is(Matchers.not(Matchers.nullValue()))));
    }

    @Test
    public void signUpSuccess() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setUsername("aaaaaaa");
        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.statusCode", Matchers.is(200)));
    }

    @Test
    public void signUpInvalidEmail() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setEmail("email");
        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Email Invalid, please try again."))
                .andReturn();
    }

    @Test
    public void signUpNotMatchPassword() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setPassword("password");
        signUpRequest.setConfirmPassword("password-confirm");
        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.statusCode", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Those passwords did not match.")))
                .andReturn();
    }

    @Test
    public void signUpPasswordNull() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setPassword(null);
        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void signUpPasswordSpace() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setPassword("while space");
        signUpRequest.setConfirmPassword("while space");
        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Password can not contain while space")))
                .andReturn();
    }

    @Test
    public void signUpNotValidFullName() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setFullName("");
        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Full name must not be empty"))
                .andReturn();

        signUpRequest = objSignUpTest();
        signUpRequest.setFullName(null);
        request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Full name must not be empty"))
                .andReturn();
    }

    @Test
    public void signUpInvalidUsernameNull() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setUsername(null);

        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Username must not be null"))
                .andReturn();

    }

    @Test
    public void signUpInvalidUsernameEmpty() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setUsername("");

        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Username is mandatory, cannot contain space"))
                .andReturn();
    }

    @Test
    public void signUpInvalidUsernameBlank() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setUsername(" ");

        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Username is mandatory, cannot contain space"))
                .andReturn();
    }

    @Test
    public void signUpInvalidUsernameSpace() throws Exception {
        SignUpRequest signUpRequest = objSignUpTest();
        signUpRequest.setUsername("test space");

        String request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Username is mandatory, cannot contain space"))
                .andReturn();
    }

    @Test
    public void signUpInvalidUsernameLength() throws Exception {
        SignUpRequest signUpRequest;
        String request;
        signUpRequest = objSignUpTest();

        signUpRequest.setUsername("a");
        request = toJson(signUpRequest);

        mvc.perform(MockMvcRequestBuilders.post(SIGN_UP).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("Username must be at least 6 characters"))
                .andReturn();
    }

    private String toJson(Object o) {
        return GSON.toJson(o);
    }

    private SignUpRequest objSignUpTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testUsername");
        signUpRequest.setAddress("Lao Cai");
        signUpRequest.setEmail("email@test");
        signUpRequest.setFullName("full name");
        signUpRequest.setPassword("password");
        signUpRequest.setConfirmPassword("password");
        signUpRequest.setPhoneNumber(133323232L);
        Set<String> roleSet = new HashSet<>();
        roleSet.add(RoleConstant.ROLE_ADMIN.name());
        signUpRequest.setRoles(roleSet);
        return signUpRequest;
    }
}
