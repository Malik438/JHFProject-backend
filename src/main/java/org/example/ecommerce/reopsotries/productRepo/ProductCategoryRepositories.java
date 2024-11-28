package org.example.ecommerce.reopsotries.productRepo;

import org.example.ecommerce.model.productModel.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepositories extends JpaRepository<ProductCategory, Long> {
}
