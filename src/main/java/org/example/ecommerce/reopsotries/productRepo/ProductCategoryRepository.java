package org.example.ecommerce.reopsotries.productRepo;

import org.example.ecommerce.model.productModel.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
