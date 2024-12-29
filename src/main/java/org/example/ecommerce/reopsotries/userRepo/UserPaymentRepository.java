package org.example.ecommerce.reopsotries.userRepo;

import org.example.ecommerce.model.usersModel.UserPayment;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {
}
