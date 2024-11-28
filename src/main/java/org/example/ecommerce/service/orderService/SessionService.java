package org.example.ecommerce.service.orderService;

import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.orderRepo.CartItemRepositories;
import org.example.ecommerce.reopsotries.orderRepo.ShoppingSessionRepositories;
import org.example.ecommerce.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SessionService {


   private final ShoppingSessionRepositories shoppingSessionRepositories;

   private final CartItemRepositories cartItemRepositories;
   private final UserService userService;

   @Autowired
   public SessionService(ShoppingSessionRepositories shoppingSessionRepositories, CartItemRepositories cartItemRepositories, UserService userService) {
       this.shoppingSessionRepositories = shoppingSessionRepositories;

       this.cartItemRepositories = cartItemRepositories;
       this.userService = userService;
   }


   public  ShoppingSession createShoppingSession(Long userid) {

       User user = userService.getUserById(userid);


       ShoppingSession shoppingSession = new ShoppingSession();
       shoppingSession.setUser(user);
       shoppingSession.setTotalPrice(0.0);
       shoppingSession.setCartItems(new ArrayList<CartItem>());
       shoppingSession.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));


       return   saveShoppingSession(shoppingSession);
   }

   public ShoppingSession saveShoppingSession(ShoppingSession shoppingSession) {
        return  shoppingSessionRepositories.save(shoppingSession);
   }


   public Optional <ShoppingSession> getShoppingSessionByUserId(Long userId) {


       Optional<ShoppingSession> currentSession  =   shoppingSessionRepositories.findShoppingSessionByUserId(userId);
       return  currentSession ;
   }



@Transactional
  public  String deleteShoppingSession(Long shoppingSessionId , Long userId) {


        cartItemRepositories.deleteCartItemBySessionId(shoppingSessionId);


        shoppingSessionRepositories.deleteShoppingSessionByUser_UserId (userId);


       return  "deleted successfully for user :" + userId;

  }
}



