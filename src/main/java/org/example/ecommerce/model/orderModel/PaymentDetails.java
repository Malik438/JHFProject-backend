package org.example.ecommerce.model.orderModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "paymentDetails")
    private OrderDetails orderDetails;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private OrderDetails orderDetails_1 ;

    private  Double amount;

    private String provider ;
    private  boolean status = false;



    private Timestamp createdAt ;
    private  Timestamp updatedAt ;


}
