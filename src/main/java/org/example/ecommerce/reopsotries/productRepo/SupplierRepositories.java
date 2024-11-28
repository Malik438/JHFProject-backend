package org.example.ecommerce.reopsotries.productRepo;

import org.example.ecommerce.model.productModel.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepositories extends JpaRepository<Supplier, Long> {
}
