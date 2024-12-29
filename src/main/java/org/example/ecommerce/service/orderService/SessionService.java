package org.example.ecommerce.service.orderService;

import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.enums.SessionStatus;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.orderRepo.CartItemRepository;
import org.example.ecommerce.reopsotries.orderRepo.ShoppingSessionRepository;
import org.example.ecommerce.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {


   private final ShoppingSessionRepository shoppingSessionRepository;

   private final CartItemRepository cartItemRepositories;
   private final UserService userService;

   @Autowired
   public SessionService(ShoppingSessionRepository shoppingSessionRepository, CartItemRepository cartItemRepositories, UserService userService) {
       this.shoppingSessionRepository = shoppingSessionRepository;

       this.cartItemRepositories = cartItemRepositories;
       this.userService = userService;
   }


   public  ShoppingSession createShoppingSession(Long userid) {

       User user = userService.getUserById(userid);


       ShoppingSession shoppingSession = new ShoppingSession();
       shoppingSession.setUser(user);
       shoppingSession.setTotalPrice(0.0);
       shoppingSession.setSessionStatus(SessionStatus.ACTIVE);
       shoppingSession.setCartItems(new ArrayList<CartItem>());
       shoppingSession.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));


       return   saveShoppingSession(shoppingSession);
   }

   public  Optional<ShoppingSession> getActiveShoppingSessionByUserId(Long userid) {
       return  shoppingSessionRepository.findActiveShoppingSessionByUserId(userid,SessionStatus.ACTIVE);
   }

   public ShoppingSession saveShoppingSession(ShoppingSession shoppingSession) {
        return  shoppingSessionRepository.save(shoppingSession);
   }


   public Optional <ShoppingSession> getShoppingSessionByUserId(Long userId) {


       return shoppingSessionRepository.findShoppingSessionByUserId(userId);
   }



  public  ShoppingSession updateShoppingSessionStatus(Long shoppingSessionId , SessionStatus sessionStatus) {


      ShoppingSession session = shoppingSessionRepository.findById(shoppingSessionId).orElseThrow(() -> new RuntimeException("Shopping session not found"));

      session.setSessionStatus(sessionStatus);

      shoppingSessionRepository.save(session);

       if(sessionStatus != SessionStatus.ACTIVE) {
           createShoppingSession(session.getUser().getUserId());
       }


       return session;

  }

//  public  void saveCartItemForLater(Long sessionId ) {
//       ShoppingSession session = shoppingSessionRepositories.findById(sessionId).orElseThrow(() -> new RuntimeException("Shopping session not found"));
//       session.setSessionStatus(SessionStatus.SAVED);
//       shoppingSessionRepositories.save(session);
//
//
//  }

  public List<List<CartItem>> getCartItemsByUserId(Long userId) {

       List<List<CartItem>> cartItems = new ArrayList<>();

      List <ShoppingSession> sessions = shoppingSessionRepository.findSavedShoppingSessionByUserId(userId ,SessionStatus.SAVED).get();


      for(ShoppingSession session: sessions) {
          cartItems.add(session.getCartItems());
      }



        return  cartItems;

  }





}



