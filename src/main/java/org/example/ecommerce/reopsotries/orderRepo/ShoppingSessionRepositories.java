package org.example.ecommerce.reopsotries.orderRepo;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.usersModel.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShoppingSessionRepositories  extends CrudRepository<ShoppingSession, Long> {



    @Query("select s from  ShoppingSession s where  s.user.userId = :userId ")
    public Optional<ShoppingSession> findShoppingSessionByUserId(Long userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM ShoppingSession s WHERE s.user.userId = :userId")
    void deleteShoppingSessionByUser_UserId(@Param("userId") Long userId);
}
