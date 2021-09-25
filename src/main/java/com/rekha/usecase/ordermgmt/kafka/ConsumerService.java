package com.rekha.usecase.ordermgmt.kafka;

import com.rekha.usecase.ordermgmt.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final OrderManagementService orderManagementService;

    @Autowired
    public ConsumerService(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    @KafkaListener(topics = "${app.kafka.topic_name}", groupId = "order_consumer")
    public void listenToOrderDetails(String orderDetails) {
        orderManagementService.updateOrderStatus(orderDetails);
    }
}