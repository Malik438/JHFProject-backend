package org.example.ecommerce.service.userService;

import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.userRepo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {





    private  final UserRepository userRepository;
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }




    public User getUserById(Long id){
        return  userRepository.findById(id).orElse(null);

    }




}
