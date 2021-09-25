package com.rekha.usecase.ordermgmt.dao;

import com.rekha.usecase.ordermgmt.dto.OrderDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetails, String> {

    List<OrderDetails> findAllByCustomerName(String customerName, Pageable pageable);

    OrderDetails findByOrderId(String orderId);
}