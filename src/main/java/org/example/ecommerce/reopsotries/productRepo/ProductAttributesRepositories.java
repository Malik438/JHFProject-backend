package org.example.ecommerce.reopsotries.productRepo;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.productModel.ProductAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductAttributesRepositories extends JpaRepository<ProductAttributes, Long> {


    @Modifying
    @Transactional
    @Query("delete  from  ProductAttributes p where  p.product.productId = :productId")
      void deleteProductAttributesByProductId(Long  productId);
}