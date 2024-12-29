package org.example.ecommerce.reopsotries.productRepo;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.productModel.ProductAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductAttributesRepository extends JpaRepository<ProductAttributes, Long> {


    @Modifying
    @Transactional
    @Query("delete  from  Product_Attributes p where  p.product.productId = :productId")
      void deleteProductAttributesByProductId(Long  productId);
}
