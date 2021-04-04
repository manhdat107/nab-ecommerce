package com.vdc.ecommerce.testController;

import com.google.gson.Gson;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Color;
import com.vdc.ecommerce.model.dto.OrderDTO;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.model.response.ResponsePageableModel;
import com.vdc.ecommerce.service.OrderService;
import com.vdc.ecommerce.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class AdminControllerTest {

    private static final Gson GSON = new Gson();

    public static final String ADD_PRODUCT = ApiConstant.ADMIN + ApiConstant.PRODUCT + ApiConstant.ADD;
    public static final String UPDATE_PRODUCT = ApiConstant.ADMIN + ApiConstant.PRODUCT + ApiConstant.UPDATE;
    public static final String DELETE = ApiConstant.ADMIN + ApiConstant.PRODUCT + ApiConstant.DELETE;
    public static final String UPDATE_QUANTITY = ApiConstant.ADMIN + ApiConstant.PRODUCT + ApiConstant.UPDATE + ApiConstant.QUANTITY;
    public static final String ORDER_LIST = ApiConstant.ADMIN + ApiConstant.ORDER + ApiConstant.LIST;

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void getListOrder() throws Exception {
        ProductDTO productDTO = updateObj();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS.add(productDTO);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProducts(productDTOS);
        orderDTO.setFullname("Vu Manh Dat");
        orderDTO.setAddress("Lang Son");
        orderDTO.setPhoneNumber(999999999L);
        orderDTO.setEmail("dat@gmail.com");
        orderDTO.setTotalPrice(new BigDecimal("1000"));

        ResponsePageableModel<OrderDTO> dResponsePageableModel =
                new ResponsePageableModel(Arrays.asList(orderDTO), AppUtils.getPageable(0, 10, null, false), 1);

        OrderService orderService = Mockito.mock(OrderService.class);
        Mockito.when(orderService.findByPredicate(null, null)).thenReturn(
                ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), dResponsePageableModel));

        String json = "{ \"dest\": true, \"field\": \"\", \"metricFilters\": [{ \"condition\": \"string\",\"field\": \"name\",\"value\": \"ni\"}],\"page\": 0,\"pageSize\": 0}\"";


        mvc.perform(MockMvcRequestBuilders.post(ORDER_LIST).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void addProductNonAuth() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateProductNonAuth() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(UPDATE_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void updateProductSuccess() throws Exception {
        ProductDTO productDTO = updateObj();

        ProductService productService = Mockito.mock(ProductService.class);
        Mockito.when(productService.update(productDTO)).thenReturn(ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), productDTO));

        String request = toJson(productDTO);

        mvc.perform(MockMvcRequestBuilders.put(UPDATE_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void addProductSuccess() throws Exception {

        ProductDTO productDTO = productDTORequest();
        ProductService productService = Mockito.mock(ProductService.class);
        Mockito.when(productService.addProduct(productDTO)).thenReturn(ResponseModel.successful(ResponseMessage.SUCCESS.getMessage()));

        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addProductNotFoundBranch() throws Exception {
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
    public void addProductNameNull() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.setName(null);
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addProductNameEmpty() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.setName("");
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addProductQuantityZero() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.getQuantityDTO().setQuantity(0L);
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Quantity must be more than 0")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addProductQuantityNull() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.setQuantityDTO(null);
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Quantity can not null")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addProductBranchNull() throws Exception {
        ProductDTO productDTO = productDTORequest();
        productDTO.setBranch(null);
        String request = toJson(productDTO);
        mvc.perform(MockMvcRequestBuilders.post(ADD_PRODUCT).content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Branch can not null")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteSuccess() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete(DELETE + "/{productId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteNotFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete(DELETE + "/{productId}", 999999))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.NOT_FOUND.message)))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateQuantitySuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(UPDATE_QUANTITY).param("productId", "1").param("quantity", "50"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.SUCCESS.message)))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateQuantityNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(UPDATE_QUANTITY).param("productId", "19999").param("quantity", "50"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.NOT_FOUND.message)))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateQuantityNotValidQuantity() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(UPDATE_QUANTITY).param("productId", "19999").param("quantity", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is("Quantity can not less than 0")))
                .andReturn();
    }


    public ProductDTO productDTORequest() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setColor(Color.BLUE);
        productDTO.setName("product");
        productDTO.setPrice(new BigDecimal("50"));

        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setQuantity(10L);
        productDTO.setQuantityDTO(quantityDTO);

        Branch branch = new Branch();
        branch.setId(1L);
        productDTO.setBranch(branch);
        return productDTO;
    }

    public ProductDTO updateObj() {
        ProductDTO productDTO = productDTORequest();
        productDTO.setId(1L);
        return productDTO;
    }


    private String toJson(Object o) {
        return GSON.toJson(o);
    }

}
