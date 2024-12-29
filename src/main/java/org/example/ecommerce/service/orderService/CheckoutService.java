package org.example.ecommerce.service.orderService;

import org.example.ecommerce.model.orderModel.*;
import org.example.ecommerce.enums.OrderStatus;
import org.example.ecommerce.enums.SessionStatus;
import org.example.ecommerce.model.usersModel.UserPayment;
import org.example.ecommerce.reopsotries.orderRepo.*;
import org.example.ecommerce.reopsotries.productRepo.ProductRepository;
import org.example.ecommerce.reopsotries.userRepo.UserPaymentRepository;
import org.example.ecommerce.reopsotries.userRepo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CheckoutService {

    private  final ShoppingSessionRepository shoppingSessionRepository;
    private final CartItemRepository cartItemRepositories;
    private  final OrderDetailsRepository orderDetailsRepository;
    private  final OrderItemRepository orderItemRepository;
    private  final PaymentDetailsRepository paymentDetailsRepository;
    private  final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserPaymentRepository userPaymentRepository;
    private  final SessionService sessionService;

    @Autowired
    public CheckoutService(ShoppingSessionRepository shoppingSessionRepository, CartItemRepository cartItemRepositories, OrderDetailsRepository orderDetailsRepository, OrderItemRepository orderItemRepository, PaymentDetailsRepository paymentDetailsRepository, ProductRepository productRepository, UserRepository userRepository, UserPaymentRepository userPaymentRepository, SessionService sessionService) {
        this.shoppingSessionRepository = shoppingSessionRepository;
        this.cartItemRepositories = cartItemRepositories;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userPaymentRepository = userPaymentRepository;
        this.sessionService = sessionService;
    }

          // read the value of transactional 
        @Transactional
        public OrderDetails processCheckout(Long userId ,Long sessionId , Long userPaymentId) throws Exception {





            ShoppingSession session = shoppingSessionRepository.findById(sessionId)
                    .orElseThrow(() -> new Exception("Session not found"));

           UserPayment userPayment = userPaymentRepository.findById(userPaymentId).orElseThrow(() -> new Exception("Payment details not found"));

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
                orderItemRepository.save(orderItem);
                cartItem.getProduct().setQuantity(cartItem.getQuantity()-1);

            }
            session.getCartItems().clear();
            orderDetailsRepository.save(order);



            if(paymentDetails.getAmount() < userPayment.getAmount()) {
                paymentDetails.setStatus(true);
                userPayment.setAmount(userPayment.getAmount() - paymentDetails.getAmount());
                //delete(sessionId);



            }else {

                paymentDetails.setStatus(false);

            }

            paymentDetailsRepository.save(paymentDetails);
            order.setPaymentDetails(paymentDetails);
            session.setSessionStatus(SessionStatus.COMPLETED);

            sessionService.createShoppingSession(userId);


            return order;
        }


      @Transactional
      public  void delete(long sessionId){
          shoppingSessionRepository.deleteById(sessionId);

      }



}
