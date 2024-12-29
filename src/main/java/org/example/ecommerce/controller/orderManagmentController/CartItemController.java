package org.example.ecommerce.controller.orderManagmentController;

import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.productModel.SavedProduct;
import org.example.ecommerce.service.orderService.CartItemService;
import org.example.ecommerce.service.orderService.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;
    private final SessionService sessionService;

    @Autowired
    public CartItemController(CartItemService cartItemService, SessionService sessionService) {
        this.cartItemService = cartItemService;
        this.sessionService = sessionService;
    }


    @PostMapping("add/{userId}/{productId}")
    public ResponseEntity<ShoppingSession> addProductToCart(@PathVariable("userId") Long id, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(cartItemService.addProductToCart(id, productId));

    }


    @PostMapping("save/item/later/{user_id}/{cartItem_id}")
    public ResponseEntity<SavedProduct> saveItemForLater(@PathVariable(name = "user_id") Long userId, @PathVariable(name = "cartItem_id") Long cartItemId) {
        return ResponseEntity.ok().body(cartItemService.saveItemForLater(userId, cartItemId));
    }

    @PostMapping("saveItem/to/cart/{user_id}/{saveItem_id}")
    public ResponseEntity<Void> saveItemToCart(@PathVariable(name = "user_id") Long userId, @PathVariable(name = "saveItem_id") Long SavedItemId) {
        cartItemService.saveItemToCart(userId, SavedItemId);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("delete/{session_id}/{product_id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable(name = "session_id") Long sessionId, @PathVariable(name = "product_id") Long productId) {
        cartItemService.deleteFromCartItem(sessionId, productId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



}
