package org.example.ecommerce.service;

import org.example.ecommerce.model.orderModel.*;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.model.usersModel.UserPayment;
import org.example.ecommerce.reopsotries.orderRepo.*;
import org.example.ecommerce.reopsotries.productRepo.ProductRepositories;
import org.example.ecommerce.reopsotries.userRepo.UserPaymentRepositories;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {

    private  final ShoppingSessionRepositories shoppingSessionRepositories;
    private final CartItemRepositories cartItemRepositories;
    private  final OrderDetailsRepositories orderDetailsRepositories;
    private  final OrderItemRepositories orderItemRepositories;
    private  final PaymentDetailsRepositories paymentDetailsRepositories;
    private  final ProductRepositories productRepositories;
    private final UserRepositories userRepositories;
    private final UserPaymentRepositories userPaymentRepositories;

    @Autowired
    public CheckoutService(ShoppingSessionRepositories shoppingSessionRepositories, CartItemRepositories cartItemRepositories, OrderDetailsRepositories orderDetailsRepositories, OrderItemRepositories orderItemRepositories, PaymentDetailsRepositories paymentDetailsRepositories, ProductRepositories productRepositories, UserRepositories userRepositories, UserPaymentRepositories userPaymentRepositories) {
        this.shoppingSessionRepositories = shoppingSessionRepositories;
        this.cartItemRepositories = cartItemRepositories;
        this.orderDetailsRepositories = orderDetailsRepositories;
        this.orderItemRepositories = orderItemRepositories;
        this.paymentDetailsRepositories = paymentDetailsRepositories;
        this.productRepositories = productRepositories;
        this.userRepositories = userRepositories;
        this.userPaymentRepositories = userPaymentRepositories;
    }


        @Transactional
        public OrderDetails processCheckout(Long userId ,Long sessionId , Long userPaymentId) throws Exception {


            ShoppingSession session = shoppingSessionRepositories.findById(sessionId)
                    .orElseThrow(() -> new Exception("Session not found"));
            User user = userRepositories.findById(userId).orElseThrow(() -> new Exception("User not found"));

           UserPayment userPayment = userPaymentRepositories.findById(userPaymentId).orElseThrow(() -> new Exception("Payment details not found"));

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setStatus(false);
            paymentDetails.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));



            OrderDetails order = new OrderDetails();
            order.setUser(session.getUser());
            order.setTotalPrice(session.getTotalPrice());
            order.setUser(user);
            order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            order.setPaymentDetails(paymentDetails);
            order.setOrderItems(new ArrayList<>());

            paymentDetails.setAmount(order.getTotalPrice());
            paymentDetails.setProvider(userPayment.getPaymentType());
            paymentDetails.setStatus(true);


            for (CartItem cartItem : session.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderDetails(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                System.out.println("saving OrderItem: " + orderItem.getProduct().getName());
                order.getOrderItems().add(orderItem);
                orderItemRepositories.save(orderItem);

            }
            session.getCartItems().clear();
            order = orderDetailsRepositories.save(order);



            if(paymentDetails.getAmount() < userPayment.getAmount()) {
                paymentDetails.setStatus(true);
                userPayment.setAmount(userPayment.getAmount() - paymentDetails.getAmount());
                cartItemRepositories.deleteCartItemBySessionId(sessionId);
                shoppingSessionRepositories.deleteById(sessionId);

            }else {

                paymentDetails.setStatus(false);

            }

            paymentDetailsRepositories.save(paymentDetails);


            return order;
        }

        

}
