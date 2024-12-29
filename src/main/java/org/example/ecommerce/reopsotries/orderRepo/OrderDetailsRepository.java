package org.example.ecommerce.reopsotries.orderRepo;

import org.example.ecommerce.model.orderModel.OrderDetails;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails,  Long> {
}
