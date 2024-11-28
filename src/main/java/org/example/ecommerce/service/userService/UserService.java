package org.example.ecommerce.service.userService;

import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final UserRepositories userRepositories;
    public UserService(UserRepositories userRepositories) {

        this.userRepositories = userRepositories;
    }

    public User getUserById(Long id){
        return  userRepositories.findById(id).orElse(null);

    }
}
