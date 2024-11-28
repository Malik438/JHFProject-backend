package org.example.ecommerce.reopsotries.productRepo;

import org.example.ecommerce.model.productModel.SavedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedProductRepository extends JpaRepository<SavedProduct, Long> {

}
