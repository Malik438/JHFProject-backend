package org.example.ecommerce.service.orderService;


import jakarta.transaction.Transactional;
import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.SavedProduct;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.orderRepo.CartItemRepositories;
import org.example.ecommerce.reopsotries.orderRepo.ShoppingSessionRepositories;
import org.example.ecommerce.reopsotries.productRepo.ProductRepositories;
import org.example.ecommerce.reopsotries.productRepo.SavedProductRepository;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.example.ecommerce.service.userService.UserService;
import org.example.ecommerce.service.productService.ProductService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepositories cartItemRepositories;

    private final SessionService sessionService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepositories productRepositories;
    private final SavedProductRepository savedProductRepository;
    private final UserRepositories userRepositories;


    @Autowired
    public CartItemService(CartItemRepositories cartItemRepositories, ShoppingSessionRepositories shoppingSessionRepositories, SessionService sessionService, UserService userService, ProductService productService, ProductRepositories productRepositories, SavedProductRepository savedProductRepository, UserRepositories userRepositories) {
        this.cartItemRepositories = cartItemRepositories;
        this.sessionService = sessionService;
        this.userService = userService;
        this.productService = productService;
        this.productRepositories = productRepositories;
        this.savedProductRepository = savedProductRepository;
        this.userRepositories = userRepositories;
    }




    public ShoppingSession addProductToCart(Long userId, Long productId) {

        Optional<Product> product = productService.findProductById(productId);
        ShoppingSession session = null;


        if (product.isPresent()) {


            Optional<ShoppingSession> shoppingSession = sessionService.getShoppingSessionByUserId(userId);


            if (shoppingSession.isPresent()) {
                session = shoppingSession.get();
                session.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            } else {
                session = sessionService.createShoppingSession(userId);

            }



            CartItem cartItem = existInCartItem(session.getId() ,productId);


            if(cartItem !=null){
                cartItem.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemRepositories.save(cartItem);

            }else {
                cartItem = new CartItem();
                cartItem.setSession(session);
                cartItem.setProduct(product.get());
                cartItem.setQuantity(product.map(Product::getQuantity).orElse(0));
                cartItem.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                cartItemRepositories.save(cartItem);
            }




            session.getCartItems().add(cartItem);
            session.setTotalPrice(session.getTotalPrice() + product.get().getPrice() * cartItem.getQuantity());

            sessionService.saveShoppingSession(session);

        }

        return session;


    }

    private  CartItem existInCartItem(Long sessionId, Long productId) {
       Optional<CartItem> cartItem =   cartItemRepositories.findBySessionIdAndProductId(sessionId, productId);
        return cartItem.orElse(null);
    }


    public void deleteFromCartItem(long sessionId, Long productId) {
        CartItem cartItem = existInCartItem(sessionId, productId);
        if (cartItem != null) {
            cartItemRepositories.deleteById(cartItem.getId());
        }
    }
    //todo
    // after that you should make delete from cart
    // then  test checkout api


    @Transactional
    public SavedProduct saveItemForLater(Long userId, Long id) {

        SavedProduct savedProduct = new SavedProduct();


        CartItem cartItem = cartItemRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("cart item not found"));

        User user = userRepositories.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));


        savedProduct.setUser(user);
        savedProduct.setProduct(cartItem.getProduct());
        savedProduct.setSaveAt(Timestamp.valueOf(LocalDateTime.now()));

        savedProductRepository.save(savedProduct);

        cartItemRepositories.deleteById(id);


        return savedProduct;


    }

    @Transactional
    public void saveItemToCart(Long userId, Long SavedItemId) {

        CartItem cartItem = new CartItem();
        Optional<SavedProduct> savedProduct = savedProductRepository.findById(SavedItemId);
        Optional<ShoppingSession> shoppingSession = sessionService.getShoppingSessionByUserId(userId);


        if (savedProduct.isPresent() && shoppingSession.isPresent()) {

            cartItem.setProduct(savedProduct.get().getProduct());
            cartItem.setQuantity(savedProduct.get().getQuantity());
            cartItem.setSession(shoppingSession.get());
            cartItem.setCreatedAt(savedProduct.get().getSaveAt());

            cartItemRepositories.save(cartItem);
            shoppingSession.get().getCartItems().add(cartItem);
            savedProductRepository.deleteById(SavedItemId);


        }


    }

}
