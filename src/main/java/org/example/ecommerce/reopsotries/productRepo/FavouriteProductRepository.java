package org.example.ecommerce.reopsotries.productRepo;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.FavouriteProduct;
import org.example.ecommerce.model.usersModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteProductRepository extends JpaRepository<FavouriteProduct, Long> {




    @Query
    boolean existsByUserAndProduct(User user , Product product);

    @Modifying
    @Transactional
    @Query
    int deleteByUserAndProduct(User user , Product product);



    @Query("select p.product.productId from FavouriteProduct  p where  p.user.userId = :userId")
    List<Long> findAllProductIdsByUser_Id(Long userId);
}
