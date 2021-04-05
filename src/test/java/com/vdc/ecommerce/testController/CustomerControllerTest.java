package com.vdc.ecommerce.testController;

import com.google.gson.Gson;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Color;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import com.vdc.ecommerce.model.request.OrderRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.OrderService;
import com.vdc.ecommerce.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CustomerControllerTest {


    private static final Gson GSON = new Gson();

    @Autowired
    private MockMvc mvc;

    private String toJson(Object o) {
        return GSON.toJson(o);
    }

    private static final String PRODUCT_INFO = ApiConstant.CUSTOMER + ApiConstant.PRODUCT;
    private static final String DO_ORDER = ApiConstant.CUSTOMER + ApiConstant.ORDER + ApiConstant.ADD;


    @Test
    @Transactional
    public void productInfo() throws Exception {
        ProductDTO productDTO = updateObj();

        ProductService productService = Mockito.mock(ProductService.class);
        Mockito.when(productService.getById(ArgumentMatchers.any(Long.class))).thenReturn(ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), productDTO));

        mvc.perform(MockMvcRequestBuilders.get(PRODUCT_INFO)
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(Matchers.notNullValue())))
                .andReturn();

    }


    @Test
    @Transactional
    public void productInfoNotFound() throws Exception {
        ProductService productService = Mockito.mock(ProductService.class);
        Mockito.when(productService.getById(ArgumentMatchers.any(Long.class))).thenReturn(ResponseModel.successful(ResponseMessage.NOT_FOUND.getMessage()));

        mvc.perform(MockMvcRequestBuilders.get(PRODUCT_INFO)
                .param("id", "1111"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.NOT_FOUND.getMessage())))
                .andReturn();

    }

    @Test
    @Transactional
    public void doOrderTest() throws Exception {
        OrderRequest orderRequest = orderRequest();

        OrderService orderService = Mockito.mock(OrderService.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(orderService.order(orderRequest, authentication)).thenReturn(ResponseModel.successful(ResponseMessage.SUCCESS.getMessage()));

        mvc.perform(MockMvcRequestBuilders.post(DO_ORDER).content(toJson(orderRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.containsString(ResponseMessage.SUCCESS.getMessage())))
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

    public OrderRequest orderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setEmail("DatVm@gmail.com");
        orderRequest.setFullname("Le van Luyen");
        orderRequest.setPhoneNumber(234234234L);
        orderRequest.setAddress("address");

        OrderRequest.ProductOrder productOrder = new OrderRequest.ProductOrder();
        productOrder.setProductId(1L);
        productOrder.setQuantity(12);

        List<OrderRequest.ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);
        orderRequest.setProductOrders(productOrderList);
        return orderRequest;
    }

}
