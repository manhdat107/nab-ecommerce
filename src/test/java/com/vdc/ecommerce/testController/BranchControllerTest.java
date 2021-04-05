package com.vdc.ecommerce.testController;


import com.google.gson.Gson;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.dto.BranchDTO;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.model.response.ResponsePageableModel;
import com.vdc.ecommerce.service.BranchService;
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

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class BranchControllerTest {


    private static final Gson GSON = new Gson();

    @Autowired
    private MockMvc mvc;

    private String toJson(Object o) {
        return GSON.toJson(o);
    }

    private static final String BRANCH_LIST = ApiConstant.ADMIN + ApiConstant.BRANCH + ApiConstant.LIST;
    private static final String BRANCH_ADD = ApiConstant.ADMIN + ApiConstant.BRANCH + ApiConstant.ADD;
    private static final String BRANCH_DELETE = ApiConstant.ADMIN + ApiConstant.BRANCH + ApiConstant.DELETE;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void getListOrder() throws Exception {

        BranchDTO b1 = new BranchDTO("GUCCI", "GUCCI Branch");
        b1.setId(1L);
        BranchDTO b2 = new BranchDTO("NIKE", "NIKE Branch");
        b2.setId(2L);
        BranchDTO b3 = new BranchDTO("ADIDAS", "ADIDAS Branch");
        b3.setId(3L);
        List<BranchDTO> orderDTO = Arrays.asList(b1, b2, b3);

        ResponsePageableModel<BranchDTO> dResponsePageableModel =
                new ResponsePageableModel(orderDTO, AppUtils.getPageable(0, 10, null, false), 3);

        BranchService branchService = Mockito.mock(BranchService.class);
        Mockito.when(branchService.getAll(0, 10, null, false)).thenReturn(
                ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), dResponsePageableModel));

        mvc.perform(MockMvcRequestBuilders.get(BRANCH_LIST)
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .param("sortBy", "")
                .param("isDesc", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(Matchers.notNullValue())))
                .andReturn();

    }

    @Test
    @Transactional
    public void getListNonAuth() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BRANCH_LIST)
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .param("sortBy", "")
                .param("isDesc", "false"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addBranch() throws Exception {
        BranchDTO b1 = new BranchDTO("Dr.Marten", "Dep Lao Hai Phong");

        BranchService branchService = Mockito.mock(BranchService.class);
        Mockito.when(branchService.add(b1)).thenReturn(ResponseModel.successful(ResponseMessage.SUCCESS.getMessage()));

        mvc.perform(MockMvcRequestBuilders.post(BRANCH_ADD).content(toJson(b1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.SUCCESS.getMessage())))
                .andReturn();
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(BRANCH_DELETE + "/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.SUCCESS.getMessage())))
                .andReturn();
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteNotFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete(BRANCH_DELETE + "/{id}", 9999L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.message", Matchers.is(ResponseMessage.NOT_FOUND.getMessage())))
                .andReturn();
    }


}
