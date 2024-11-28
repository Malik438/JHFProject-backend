package org.example.ecommerce.reopsotries.orderRepo;

import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.productModel.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartItemRepositories extends JpaRepository<CartItem, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.session.id = :sessionId")
    void deleteCartItemBySessionId(Long sessionId);


   //Optional <CartItem> findBySessionAndProduct(ShoppingSession session, Product product);

    @Query(value = "SELECT * FROM cart_item WHERE session_id = :sessionId AND product_id = :productId", nativeQuery = true)
    Optional<CartItem> findBySessionIdAndProductId(@Param("sessionId") Long sessionId, @Param("productId") Long productId);

}
