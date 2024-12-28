package org.example.ecommerce.controller;


import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.service.orderService.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/checkout")
public class CheckOutController {

    private  final CheckoutService checkoutService;
    public CheckOutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("add/{user_id}/{session_id}/{paymentDetailsId}")
    public ResponseEntity<OrderDetails>  processCheckout(@PathVariable(name = "user_id")  Long userId  ,   @PathVariable(name = "session_id") Long  session_id , @PathVariable(name = "paymentDetailsId") Long  paymentDetailsId) throws Exception {
        return ResponseEntity.ok().body(checkoutService.processCheckout(userId,session_id,paymentDetailsId));


    }


}
