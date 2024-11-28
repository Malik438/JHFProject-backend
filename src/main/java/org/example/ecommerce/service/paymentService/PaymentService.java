package org.example.ecommerce.service.paymentService;

import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.model.usersModel.UserPayment;
import org.example.ecommerce.reopsotries.userRepo.UserPaymentRepositories;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private  final UserPaymentRepositories userPaymentRepositories;
    private final UserRepositories userRepositories;

    public PaymentService(UserPaymentRepositories userPaymentRepositories, UserRepositories userRepositories) {
        this.userPaymentRepositories = userPaymentRepositories;
        this.userRepositories = userRepositories;
    }



    public UserPayment addUserPayment(Long userId) {
        // todo you should make this method take object

        Optional<User> user = userRepositories.findById(userId);
        UserPayment userPayment = new UserPayment();

        if(user.isPresent()) {
            userPayment.setUser(user.get());
            userPayment.setPaymentType("VisaCard");
            userPayment.setAmount(40000.2);
            userPayment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userPayment.setExpiryDate(LocalDate.from(LocalDateTime.of(2026,6,10,2,1)));
            userPayment.setAccountNumber(213);

        }



        return userPaymentRepositories.save(userPayment);

    }
}
