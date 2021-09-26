package com.rekha.usecase.ordermgmt;

import com.rekha.usecase.ordermgmt.dto.OrderDetails;
import com.rekha.usecase.ordermgmt.model.ProductDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderManagementControllerTest extends AbstractTest {
    @Override
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getOrderDetailsList() throws Exception {
        String uri = "/orders";
        this.mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createOrderDetails() throws Exception {
        String uri = "/orders";
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId("123");
        productDetails.setCategory("decor");
        productDetails.setPrice(2010.50);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setContactNo("9876543210");
        orderDetails.setAddress("TamilNadu");
        orderDetails.setCustomerName("Rekha");
        orderDetails.setProductDetails(Collections.singletonList(productDetails));
        String inputJson = super.mapToJson(orderDetails);
        this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andDo(print()).andExpect(status().isCreated());
    }
}