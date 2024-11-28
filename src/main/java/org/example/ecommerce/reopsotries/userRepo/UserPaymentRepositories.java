package org.example.ecommerce.reopsotries.userRepo;

import org.example.ecommerce.model.usersModel.UserPayment;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepositories extends CrudRepository<UserPayment, Long> {
}
