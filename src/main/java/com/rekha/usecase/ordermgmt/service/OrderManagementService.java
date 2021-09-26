package com.rekha.usecase.ordermgmt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekha.usecase.ordermgmt.dao.OrderDetailRepository;
import com.rekha.usecase.ordermgmt.dto.OrderDetails;
import com.rekha.usecase.ordermgmt.kafka.ProducerService;
import com.rekha.usecase.ordermgmt.model.ProductDetails;
import com.rekha.usecase.ordermgmt.util.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class OrderManagementService {
    private static final Logger logger = LoggerFactory.getLogger(OrderManagementService.class);

    private final OrderDetailRepository orderDetailRepository;
    private final ProducerService producerService;
    private final ExecutorService executorService;

    @Value("${app.kafka.topic_name}")
    private String orderTopic;


    @Autowired
    public OrderManagementService(OrderDetailRepository orderDetailRepository,
                                  ProducerService producerService,
                                  @Qualifier("threadPoolTaskExecutor") ExecutorService executorService) {
        this.orderDetailRepository = orderDetailRepository;
        this.producerService = producerService;
        this.executorService = executorService;
    }

    public OrderDetails createOrder(OrderDetails orderDetails) {
        OrderDetails orderData = new OrderDetails();
        orderData.setOrderId(UUID.randomUUID().toString());
        orderData.setCustomerName(orderDetails.getCustomerName());
        orderData.setAddress(orderDetails.getAddress());
        orderData.setContactNo(orderDetails.getContactNo());
        orderData.setOrderStatus(OrderStatus.PLACED.toString());
        orderData.setProductDetails(orderDetails.getProductDetails());
        orderData.setCreatedTimestamp(new Date());
        orderData.setUpdatedTimestamp(new Date());
        List<ProductDetails> productDetails = orderDetails.getProductDetails();
        orderData.setTotalPrice(productDetails.stream().mapToDouble(order -> order.getPrice()).sum());
        orderDetailRepository.save(orderData);
        CompletableFuture.runAsync(() -> sendKafkaMessage(orderData), executorService); // Async process to post message to kafka
        return orderData;
    }

    public void updateOrderStatus(String orderDetails) {
        OrderDetails orderData = null;
        try {
            orderData = new ObjectMapper().readValue(orderDetails, OrderDetails.class);
            Optional<OrderDetails> optionalOrderDetails = orderDetailRepository.findById(orderData.getOrderId()); // Verify if the order id exists.
            optionalOrderDetails.ifPresent(
                    order -> {
                        order.setOrderStatus(OrderStatus.PROCESSED.toString()); // Update order status
                        order.setUpdatedTimestamp(new Date()); //update timestamp
                        orderDetailRepository.save(order);
                    });
        } catch (JsonProcessingException e) {
            logger.error("Unable to parse order details", e);
        }
    }

    public List<OrderDetails> getAllOrders() {
        return orderDetailRepository.findAll();
    }

    public OrderDetails getOrderById(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public void sendKafkaMessage(OrderDetails orderData) {
        try {
            producerService.sendOrder(
                    orderTopic,
                    UUID.randomUUID().toString(),
                    new ObjectMapper().writeValueAsString(orderData));
        } catch (JsonProcessingException e) {
            logger.error(
                    String.format("invalid request body for order id : %s, due to error %s", orderData.getOrderId(),
                            e.getMessage()));
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("invalid request body for order id : %s, due to error %s", orderData.getOrderId()));
        }
    }
}
