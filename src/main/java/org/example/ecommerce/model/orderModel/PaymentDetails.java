package org.example.ecommerce.model.orderModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PaymentDetails")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "paymentDetails")
    @JsonIgnore
    private List<OrderDetails> orderDetails;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private OrderDetails orderDetails_1 ;

    private  Double amount;

    private String provider ;
    private  boolean status = false;



    private Timestamp createdAt ;
    private  Timestamp updatedAt ;


}
