package com.vdc.ecommerce.testController;

import com.google.gson.Gson;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Color;
import com.vdc.ecommerce.model.dto.ProductDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminControllerTest {

    private static final Gson GSON = new Gson();

    public static final String ADD_PRODUCT = ApiConstant.ADMIN + ApiConstant.PRODUCT + ApiConstant.ADD;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAddProductNonAuth() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddProductSuccess() throws Exception {
        ProductDTO productDTO = productDTORequest();
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddProductNotFoundBranch() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.getBranch().setId(10L);
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Not Found")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.statusCode", Matchers.is(400)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddProductNameNull() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.setName(null);
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddProductNameEmpty() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.setName("");
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }


    public ProductDTO productDTORequest() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setColor(Color.BLUE);
        productDTO.setName("product");
        productDTO.setPrice(new BigDecimal("50"));
        Branch branch = new Branch();
        branch.setId(1L);
        productDTO.setBranch(branch);
        return productDTO;
    }


    private String toJson(Object o) {
        return GSON.toJson(o);
    }

}
