package org.example.ecommerce.reopsotries.userRepo;

import org.example.ecommerce.model.usersModel.UserAddress;
import org.springframework.data.repository.CrudRepository;

public interface UserAddressRepositories extends CrudRepository<UserAddress, Long> {
}
