package org.example.ecommerce.service.orderService;

import org.example.ecommerce.model.orderModel.*;
import org.example.ecommerce.model.orderModel.enums.OrderStatus;
import org.example.ecommerce.model.orderModel.enums.SessionStatus;
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
    private  final SessionService sessionService;

    @Autowired
    public CheckoutService(ShoppingSessionRepositories shoppingSessionRepositories, CartItemRepositories cartItemRepositories, OrderDetailsRepositories orderDetailsRepositories, OrderItemRepositories orderItemRepositories, PaymentDetailsRepositories paymentDetailsRepositories, ProductRepositories productRepositories, UserRepositories userRepositories, UserPaymentRepositories userPaymentRepositories, SessionService sessionService) {
        this.shoppingSessionRepositories = shoppingSessionRepositories;
        this.cartItemRepositories = cartItemRepositories;
        this.orderDetailsRepositories = orderDetailsRepositories;
        this.orderItemRepositories = orderItemRepositories;
        this.paymentDetailsRepositories = paymentDetailsRepositories;
        this.productRepositories = productRepositories;
        this.userRepositories = userRepositories;
        this.userPaymentRepositories = userPaymentRepositories;
        this.sessionService = sessionService;
    }

          // read the value of transactional 
        @Transactional
        public OrderDetails processCheckout(Long userId ,Long sessionId , Long userPaymentId) throws Exception {





            ShoppingSession session = shoppingSessionRepositories.findById(sessionId)
                    .orElseThrow(() -> new Exception("Session not found"));

           UserPayment userPayment = userPaymentRepositories.findById(userPaymentId).orElseThrow(() -> new Exception("Payment details not found"));

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setStatus(false);
            paymentDetails.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));



            OrderDetails order = new OrderDetails();



            order.setUser(session.getUser());
            order.setTotalPrice(session.getTotalPrice());
            order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            order.setOrderItems(new ArrayList<>());
            order.setOrderStatus(OrderStatus.PENDING);

            paymentDetails.setAmount(order.getTotalPrice());
            paymentDetails.setProvider(userPayment.getPaymentType());
            paymentDetails.setStatus(true);


            System.out.println("the id is " + order.getId());

            OrderItem orderItem ;
            for (CartItem cartItem : session.getCartItems()) {
                 orderItem = new OrderItem();
                orderItem.setOrderDetails(order);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                System.out.println("saving OrderItem: " + orderItem.getProduct().getName());
                order.getOrderItems().add(orderItem);
                orderItemRepositories.save(orderItem);
                cartItem.getProduct().setQuantity(cartItem.getQuantity()-1);

            }
            session.getCartItems().clear();
            orderDetailsRepositories.save(order);



            if(paymentDetails.getAmount() < userPayment.getAmount()) {
                paymentDetails.setStatus(true);
                userPayment.setAmount(userPayment.getAmount() - paymentDetails.getAmount());
                //delete(sessionId);



            }else {

                paymentDetails.setStatus(false);

            }

            paymentDetailsRepositories.save(paymentDetails);
            order.setPaymentDetails(paymentDetails);
            session.setSessionStatus(SessionStatus.COMPLETED);

            sessionService.createShoppingSession(userId);


            return order;
        }


      @Transactional
      public  void delete(long sessionId){
          shoppingSessionRepositories.deleteById(sessionId);

      }



}
