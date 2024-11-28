package org.example.ecommerce.controller;


import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.service.orderService.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("session")
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


    @DeleteMapping("/delete/{shoppingSessionId}/{userId}")
    public ResponseEntity<String> deleteShoppingSession(@PathVariable("shoppingSessionId") long shoppingSessionId , @PathVariable("userId") long userId) {

       return ResponseEntity.ok().body(sessionService.deleteShoppingSession(shoppingSessionId,userId));
    }


    @GetMapping("get/currentSession/cartItems/{user_id}")
    public  ResponseEntity<Optional<ShoppingSession>> getCurrentCartItem(@PathVariable("user_id") Long userId) {
             return  ResponseEntity.ok().body(sessionService.getShoppingSessionByUserId(userId)) ;
     }












}
