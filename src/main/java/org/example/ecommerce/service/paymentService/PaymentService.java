package org.example.ecommerce.service.paymentService;

import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.model.usersModel.UserPayment;
import org.example.ecommerce.reopsotries.userRepo.UserPaymentRepository;
import org.example.ecommerce.reopsotries.userRepo.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private  final UserPaymentRepository userPaymentRepository;
    private final UserRepository userRepository;

    public PaymentService(UserPaymentRepository userPaymentRepository, UserRepository userRepository) {
        this.userPaymentRepository = userPaymentRepository;
        this.userRepository = userRepository;
    }



    public UserPayment addUserPayment(Long userId) {
        // todo you should make this method take object

        Optional<User> user = userRepository.findById(userId);
        UserPayment userPayment = new UserPayment();

        if(user.isPresent()) {
            userPayment.setUserP(user.get());
            userPayment.setPaymentType("VisaCard");
            userPayment.setAmount(40000.2);
            userPayment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userPayment.setExpiryDate(LocalDate.from(LocalDateTime.of(2026,6,10,2,1)));
            userPayment.setAccountNumber(213);

        }



        return userPaymentRepository.save(userPayment);

    }
}
