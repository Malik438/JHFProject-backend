package org.example.ecommerce.controller.orderManagmentController;


import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.enums.SessionStatus;
import org.example.ecommerce.service.orderService.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/session")
public class ShoppingSessionController {

    private  final SessionService sessionService;

    public ShoppingSessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<ShoppingSession> createShoppingSession(@PathVariable("id") Long userId) {

        ShoppingSession shoppingSession = sessionService.createShoppingSession(userId);
        return ResponseEntity.ok(shoppingSession);


    }

    @PatchMapping ("/update/{shoppingSessionId}")
    public ResponseEntity<ShoppingSession> updateShoppingSession(@PathVariable("shoppingSessionId") long shoppingSessionId , @RequestBody SessionStatus sessionStatus) {

       return ResponseEntity.ok().body(sessionService.updateShoppingSessionStatus(shoppingSessionId, sessionStatus));
    }


    @GetMapping("get/currentSession/cartItems/{user_id}")
    public  ResponseEntity<Optional<ShoppingSession>> getCurrentCartItem(@PathVariable("user_id") Long userId) {
             return  ResponseEntity.ok().body(sessionService.getActiveShoppingSessionByUserId(userId)) ;
     }

//     @PatchMapping("save/session/cartItem/later/{session_id}")
//    public ResponseEntity<String> saveCartItemForLater(@PathVariable(name ="session_id") long sessionId ) {
//         sessionService.saveCartItemForLater(sessionId);
//        return  ResponseEntity.ok("Cart item saved for later successfully.");
//     }

     @GetMapping("saved/cartItems/{user_id}")
    public  ResponseEntity<List<List<CartItem>>> getSavedCartItem(@PathVariable("user_id") Long userId) {
        return   ResponseEntity.ok().body(sessionService.getCartItemsByUserId(userId)) ;
     }












}
