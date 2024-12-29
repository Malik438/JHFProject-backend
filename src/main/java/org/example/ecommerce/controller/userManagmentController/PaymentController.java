package org.example.ecommerce.controller.userManagmentController;

import org.example.ecommerce.model.usersModel.UserPayment;
import org.example.ecommerce.service.paymentService.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/payment")
public class PaymentController {




     private  final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("add/{user_id}")
    public ResponseEntity<UserPayment> fillUserPayment(@PathVariable(name = "user_id") long  user_id) {
        return   ResponseEntity.ok().body(paymentService.addUserPayment(user_id))  ;
    }
}
