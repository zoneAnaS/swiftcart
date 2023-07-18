package com.swiftcart.service;

import com.swiftcart.dto.CartResponse;
import com.swiftcart.dto.OrderBillResponse;
import com.swiftcart.dto.OrderResponse;
import com.swiftcart.dto.ProductResponse;
import com.swiftcart.exception.OrderException;
import com.swiftcart.exception.OrderNotFoundException;
import com.swiftcart.model.Order;
import com.swiftcart.model.OrderBill;
import com.swiftcart.model.OrderedProduct;
import com.swiftcart.model.Status;
import com.swiftcart.proxy.CartProxy;
import com.swiftcart.proxy.ProductProxy;
import com.swiftcart.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private CartProxy cartProxy;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductProxy productProxy;
    private final Double discount=10.0;
    private final Double deliveryCharge=60.0;


    public OrderResponse orderToOrderResponse(Order order){
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setOrderTotal(order.getOrderTotal());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setProductList(order.getProductList());
        orderResponse.setOrderId(order.getOrderId());
        return orderResponse;
    }
    public OrderBillResponse orderToOrderBillResponse(Order order){
        OrderBill bill = order.getOrderBill();
        OrderBillResponse responseBill=new OrderBillResponse();
        responseBill.setBillDate(bill.getBillDate());
        responseBill.setBillTotal(bill.getBillTotal());
        responseBill.setDiscount(bill.getDiscount());
        responseBill.setOrderedProductList(bill.getOrderedProductList());
        responseBill.setDeliveryCharge(bill.getDeliveryCharge());

        return responseBill;
    }





    @Override
    public String addOrder(String userId) throws OrderException {
        Order order = new Order();
        try{
            CartResponse cartResponse=cartProxy.getCartByUserId(userId);
            cartProxy.emptyCart(userId);
            order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
            order.setOrderTotal(cartResponse.getCartTotal());
            order.setProductList(cartResponse.getProductList());
            order.setUserId(cartResponse.getUserId());
            order.setStatus(Status.generated);
        }catch (Exception exception){
            throw new OrderException("Something went wrong");
        }
        if(order.getProductList()==null || order.getProductList().isEmpty())throw new OrderException("Cart is empty! please add to cart");
        order.setOrderBill(null);
        Order order1=orderRepository.save(order);
        return order1.getOrderId();
    }

    @Override
    public OrderResponse getOrder(String userId, String orderId) throws OrderNotFoundException, OrderException {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));
        if(!order.getUserId().equals(userId))throw new OrderException("Order does not belong to user");
        return orderToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrdersOfUser(String userId) throws OrderException {
        try {
            cartProxy.getCartByUserId(userId);
        }catch (Exception exception){throw new OrderException("Invalid user id");}
        return orderRepository.findByUserId(userId).stream().map(this::orderToOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::orderToOrderResponse).toList();
    }

    @Override
    public String generateBill(String userId, String orderId) throws OrderNotFoundException, OrderException {
        getOrder(userId,orderId);

        Order order = orderRepository.findById(orderId).get();
//        if(order.getStatus()==Status.cancelled)throw new OrderException("Order is cancelled cannot generate bill");
        OrderBill orderBill=new OrderBill();
        orderBill.setBillDate(LocalDateTime.now());
        if(order.getOrderBill()!=null && order.getOrderBill().getBillDate()!=null){
            orderBill.setBillDate(order.getOrderBill().getBillDate());
        }
        orderBill.setOrderedProductList(new ArrayList<>());
        for(String productId:order.getProductList().keySet()){
            try{
                ProductResponse product=productProxy.getProductById(productId);
                Integer quantity=order.getProductList().get(productId);
                OrderedProduct orderedProduct=new OrderedProduct(productId,quantity,product.getPrice());
                orderBill.getOrderedProductList().add(orderedProduct);
            }catch (Exception exception){

            }
        }
        Double total=0.0;
        for(OrderedProduct orderedProduct:orderBill.getOrderedProductList()){
            total+=orderedProduct.getPrice()*orderedProduct.getQuantity();
        }
        orderBill.setDiscount(discount);
        Double discountAmount = (discount / 100) * total;
        total=total - discountAmount;

        if(total<1000){
            total+=deliveryCharge;
            orderBill.setDeliveryCharge(deliveryCharge);
        }else orderBill.setDeliveryCharge(0.0);
        orderBill.setBillTotal(total);
        order.setOrderBill(orderBill);
        orderRepository.save(order);
        return "Bill added successfully";
    }

    @Override
    public OrderBillResponse getBillOfUser(String userId, String orderId) throws OrderNotFoundException, OrderException {
        List<Order> orders=orderRepository.findByUserId(userId);
        Order order=orders.stream().filter(order1 -> order1.getOrderId().equals(orderId)).toList().get(0);
        return orderToOrderBillResponse(order);
    }

    @Override
    public String updateOrder(String userId, String orderId, Status status) throws OrderNotFoundException, OrderException {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));
        if(!order.getUserId().equals(userId))throw new OrderException("Order doest not belong to user");
        order.setStatus(status);
        orderRepository.save(order);
        try{
            this.generateBill(userId,orderId);
        }catch(Exception exception){

        }
        return "Order updated successfully";
    }

    @Override
    public List<OrderResponse> filter(LocalDate orderDate, Status status, Double orderTotal) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery=criteriaBuilder.createQuery(Order.class);
        Root<Order> root=criteriaQuery.from(Order.class);
        List<Predicate> predicates=new ArrayList<>();

        if(orderDate!=null){
            Expression<LocalDate> orderDateExpression = criteriaBuilder.function(
                    "DATE", LocalDate.class, root.get("orderDate")
            );
            Predicate datePredicate=criteriaBuilder.equal(orderDateExpression,orderDate);
            predicates.add(datePredicate);
        }
        if(status!=null){
            Predicate countPredicate=criteriaBuilder.equal(root.get("status"),status);
            predicates.add(countPredicate);
        }
        if(orderTotal!=null){
            Predicate pricePredicate=criteriaBuilder.equal(root.get("orderTotal"),orderTotal);
            predicates.add(pricePredicate);
        }



        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Order> query=entityManager.createQuery(criteriaQuery);
        List<OrderResponse> filteredOrder= query.getResultList().stream().map(this::orderToOrderResponse).toList();
        return filteredOrder;

    }
}
