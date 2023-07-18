package com.swiftcart.service;

import com.swiftcart.dto.OrderBillResponse;
import com.swiftcart.dto.OrderResponse;
import com.swiftcart.exception.OrderException;
import com.swiftcart.exception.OrderNotFoundException;
import com.swiftcart.model.Status;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public String addOrder(String userId) throws OrderException;
    public OrderResponse getOrder(String userId, String orderId) throws OrderNotFoundException, OrderException;
    public List<OrderResponse> getAllOrdersOfUser(String userId) throws OrderException;
    public List<OrderResponse> getAllOrders();
    public String generateBill(String userId,String orderId) throws OrderNotFoundException, OrderException;
    public OrderBillResponse getBillOfUser(String userId,String orderId) throws OrderNotFoundException, OrderException;
    public String updateOrder(String userId, String orderId, Status status) throws OrderNotFoundException, OrderException;
    public List<OrderResponse> filter(LocalDate orderDate,Status status, Double orderTotal);

}
