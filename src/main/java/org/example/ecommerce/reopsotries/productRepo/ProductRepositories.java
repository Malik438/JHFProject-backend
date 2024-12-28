package org.example.ecommerce.reopsotries.productRepo;

//import org.example.ecommerce.model.productModel.ProductDto;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.reopsotries.productRepo.projections.IProductForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositories extends JpaRepository<Product , Long>  {



    @Query("SELECT p.productId as productId ,  p.name AS name, p.price AS price, p.description  AS description ,p.imageUrl As imageUrl  FROM Product p")
    List<IProductForm> findAllIProductFrom();


    @Query("SELECT p.productId ,  p.name AS name, p.price AS price, p.description AS description FROM Product p where  p.productCat.categoryName = :id")
    List<IProductForm> findAllIProductFromByCategory(String id);



    @Query("SELECT p.productId AS productId, p.name AS name, p.price AS price, p.description AS description " +
            "FROM Product p INNER JOIN Favourite_Product f ON p.productId = f.product.productId " +
            "WHERE f.user.userId = :id")
    Optional<List<IProductForm>> findAllFavProductById(Long id);













}
