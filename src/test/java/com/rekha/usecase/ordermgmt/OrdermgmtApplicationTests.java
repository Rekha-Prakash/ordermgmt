package com.rekha.usecase.ordermgmt;

import com.rekha.usecase.ordermgmt.controller.OrderManagementController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrdermgmtApplicationTests {

    @Autowired
    private OrderManagementController orderManagementController;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test checks the controller is created")
    @Test
    public void contextLoads() throws Exception {
        assertThat(orderManagementController).isNotNull();
    }

    @DisplayName("Test should return 403 if no auth-token present in the header")
    @Test
    public void shouldReturnForbiddenIfNoAuthTokenPresent() throws Exception {
        this.mockMvc.perform(get("/orders")).andDo(print()).andExpect(status().isForbidden());
    }
}
