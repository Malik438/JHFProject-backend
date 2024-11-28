package org.example.ecommerce.reopsotries.orderRepo;

import org.example.ecommerce.model.orderModel.PaymentDetails;
import org.springframework.data.repository.CrudRepository;

public interface PaymentDetailsRepositories extends CrudRepository<PaymentDetails, Long> {
}
