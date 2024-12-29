package org.example.ecommerce.Dto;

import lombok.*;
import org.example.ecommerce.model.usersModel.UserAddress;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private List<UserAddress> addresses;

}


