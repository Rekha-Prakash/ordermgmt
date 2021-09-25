package com.rekha.usecase.ordermgmt.controller;


import com.rekha.usecase.ordermgmt.dto.OrderDetails;
import com.rekha.usecase.ordermgmt.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderManagementController {
    OrderManagementService orderManagementService;

    @Autowired
    public OrderManagementController(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDetails> createApplication(
            @Valid @RequestBody OrderDetails orderDetails) {
        OrderDetails order = orderManagementService.createOrder(orderDetails);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrders() {
        List<OrderDetails> orderDetailsList = orderManagementService.getAllOrders();
        return new ResponseEntity<>(orderDetailsList, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetails> getOrderById(
            @PathVariable(value = "orderId") @NotBlank final String orderId) {
        OrderDetails orderDetails = orderManagementService.getOrderById(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}
