package org.example.ecommerce.reopsotries.productRepo;

//import org.example.ecommerce.Dto.ProductDto;
import org.example.ecommerce.enums.ProductCatalog;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.reopsotries.projections.IProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long>  {



    @Query("SELECT p.productId as productId ,  p.name AS name, p.price AS price, p.description  AS description , p.supplier.supplierId as supplierId FROM Product p WHERE p.createdAt >= :threeDaysAgo")
    Page<IProductForm> findNewAddedProductsWithPagination(@Param("threeDaysAgo") LocalDateTime threeDaysAgo, Pageable pageable);


    @Query("SELECT  distinct p.productId as productId ,  p.name AS name, p.price AS price, p.mainImage as mainImage , p.description  AS description , p.supplier.supplierId as supplierId FROM Order_Item oi " +
            "JOIN oi.product p " +
            "GROUP BY p.productId " +
            "ORDER BY SUM(oi.quantity) DESC")
    Page<IProductForm> findBestSellerProducts(Pageable pageable);



    @Query("select    p.productId as productId ,  p.name AS name, p.price AS price, p.description  AS description , p.supplier.supplierId as supplierId  , p.mainImage as mainImage   from Product p " +
            "where p.productCatalog = :productCatalog")
    Page<IProductForm> findByCatalog(@Param("productCatalog") ProductCatalog productCatalog, Pageable pageable);




    Page<Product> findAll(Pageable pageable);

    @Query("SELECT    p.productId as productId ,  p.name AS name, p.price AS price, p.description  AS description  , p.mainImage as mainImage   , p.supplier.supplierId as supplierId FROM Product p")
    Page<IProductForm> findAllIProductFrom(Pageable pageable);


    @Query("SELECT    p.productId ,  p.name AS name, p.price AS price, p.description AS description  ,  p.supplier.supplierId as supplierId , p.mainImage as mainImage FROM Product p where  p.productCat.categoryName = :id")
    List<IProductForm> findAllIProductFromByCategory(String id);



    @Query("SELECT   p.productId AS productId, p.name AS name, p.price AS price, p.description AS description ,p.mainImage as mainImage  , p.supplier.supplierId as supplierId   " +
            "FROM Product p INNER JOIN Favourite_Product f ON p.productId = f.product.productId " +
            "WHERE f.user.userId = :id")
    Optional<List<IProductForm>> findAllFavProductById(Long id);













}
