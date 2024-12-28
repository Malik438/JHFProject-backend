package org.example.ecommerce.reopsotries.orderRepo;

import org.example.ecommerce.model.orderModel.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepositories  extends CrudRepository<OrderItem, Long> {




}
