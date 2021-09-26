package com.rekha.usecase.ordermgmt;

import com.rekha.usecase.ordermgmt.dto.OrderDetails;
import com.rekha.usecase.ordermgmt.kafka.ProducerService;
import com.rekha.usecase.ordermgmt.model.ProductDetails;
import com.rekha.usecase.ordermgmt.service.OrderManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.mockito.Mockito.timeout;

@SpringBootTest
public class OrderManagementServiceTest {

    @MockBean
    ProducerService mockProducerService;
    @Autowired
    OrderManagementService orderManagementService;

    @Mock
    OrderDetails orderDetails;
    @Mock
    ProductDetails productDetails;


    @BeforeEach
    public void setup() {
        Mockito.doNothing()
                .when(mockProducerService)
                .sendOrder(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @DisplayName("Test Create Order and Get Results")
    @Test
    void testCreateOrders() {

        OrderDetails mockOrder = Mockito.mock(OrderDetails.class);
        ProductDetails mockProduct = Mockito.mock(ProductDetails.class);
        Mockito.when(mockProduct.getCategory()).thenReturn("decor");
        Mockito.when(mockProduct.getPrice()).thenReturn(2010.50);
        Mockito.when(mockProduct.getProductId()).thenReturn("123-345");
        Mockito.when(mockOrder.getAddress()).thenReturn("tamilNadu");
        Mockito.when(mockOrder.getContactNo()).thenReturn("9876543210");
        Mockito.when(mockOrder.getCustomerName()).thenReturn("Rekha");
        Mockito.when(mockOrder.getOrderStatus()).thenReturn("PLACED");
        Mockito.when(mockOrder.getProductDetails()).thenReturn(Collections.singletonList(productDetails));
        orderManagementService.createOrder(orderDetails);
        Mockito.verify(mockProducerService, timeout(10000))
                .sendOrder(
                        Mockito.anyString(), Mockito.anyString(), Mockito.contains("\"orderStatus\":\"PLACED\""));
    }
}
