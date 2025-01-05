package org.example.ecommerce.reopsotries.orderRepo;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.enums.SessionStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingSessionRepository extends CrudRepository<ShoppingSession, Long> {



    @Query("select s from shopping_session s where s.user.userId = :userId and s.sessionStatus = :status")
    Optional<ShoppingSession> findActiveShoppingSessionByUserId(@Param("userId") Long userId, @Param("status") SessionStatus status);

    @Query("select s from  shopping_session s where  s.user.userId = :userId ")
    public Optional<ShoppingSession> findShoppingSessionByUserId(Long userId);


    @Query("select s from shopping_session s where s.user.userId = :userId and s.sessionStatus = :status")
    Optional<List<ShoppingSession>> findSavedShoppingSessionByUserId(@Param("userId") Long userId, @Param("status") SessionStatus status);



    @Modifying
    @Transactional
    @Query("DELETE FROM shopping_session s WHERE s.user.userId = :userId")
    void deleteShoppingSessionByUser_UserId(@Param("userId") Long userId);



}
