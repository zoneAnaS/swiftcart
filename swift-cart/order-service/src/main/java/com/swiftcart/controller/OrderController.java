package com.swiftcart.controller;

import com.swiftcart.dto.MessageResponse;
import com.swiftcart.dto.OrderBillResponse;
import com.swiftcart.dto.OrderResponse;
import com.swiftcart.dto.StatusRequest;
import com.swiftcart.exception.OrderException;
import com.swiftcart.exception.OrderNotFoundException;
import com.swiftcart.model.Order;
import com.swiftcart.model.Status;
import com.swiftcart.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/swiftcart/orders")
public class OrderController{
    @Autowired
    private OrderService orderService;




    @PostMapping("/user/{userId}")
    public ResponseEntity<MessageResponse> addOrder(@PathVariable String userId) throws OrderException {
        return new ResponseEntity<>(new MessageResponse(orderService.addOrder(userId)), HttpStatus.CREATED);
    }

    @GetMapping("{orderId}/user/{userId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String userId,@PathVariable String orderId) throws OrderNotFoundException, OrderException {
        return new ResponseEntity<>(orderService.getOrder(userId,orderId),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersOfUser(@PathVariable String userId) throws OrderException {
        return new ResponseEntity<>(orderService.getAllOrdersOfUser(userId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
    }

    @PostMapping("/{orderId}/user/{userId}/bill")
    public ResponseEntity<MessageResponse> generateBill(@PathVariable String userId,@PathVariable String orderId) throws OrderNotFoundException, OrderException {
        return new ResponseEntity<>(new MessageResponse(orderService.generateBill(userId,orderId)),HttpStatus.OK);
    }

    @GetMapping("/{orderId}/user/{userId}/bill")
    public ResponseEntity<OrderBillResponse> getBillOfUser(@PathVariable String userId,@PathVariable String orderId) throws OrderNotFoundException, OrderException {
        return new ResponseEntity<>(orderService.getBillOfUser(userId,orderId),HttpStatus.OK);
    }

    @PutMapping("/{orderId}/user/{userId}")
    public ResponseEntity<MessageResponse> updateOrder(@PathVariable String userId,@PathVariable String orderId,@RequestBody StatusRequest statusRequest) throws OrderNotFoundException, OrderException {
        return new ResponseEntity<>(new MessageResponse(orderService.updateOrder(userId,orderId,statusRequest.getStatus())),HttpStatus.ACCEPTED);
    }
    @GetMapping("/filtered")
    public ResponseEntity<List<OrderResponse>> getFilteredOrders(@RequestParam(required = false) LocalDate orderDate,@RequestParam(required = false) Status status, @RequestParam(required = false) Double orderTotal){
        List<OrderResponse> orders=orderService.filter(orderDate,status,orderTotal);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
