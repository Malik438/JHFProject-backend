package org.example.ecommerce.reopsotries.userRepo;

import org.example.ecommerce.model.usersModel.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositories extends CrudRepository<User, Long> {
}
