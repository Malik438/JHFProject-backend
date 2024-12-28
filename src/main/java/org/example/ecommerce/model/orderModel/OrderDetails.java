package org.example.ecommerce.model.orderModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.model.orderModel.enums.OrderStatus;
import org.example.ecommerce.model.usersModel.User;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Order_Details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private  double totalPrice;

    @ManyToOne
    @JoinColumn(name = "payment_id" )
    private  PaymentDetails paymentDetails;

    @OneToMany(mappedBy = "orderDetails" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

//    @OneToOne(cascade = CascadeType.ALL ,  mappedBy = "orderDetails_1")
//    private PaymentDetails PaymentDetails;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Timestamp createdAt ;
    private  Timestamp updatedAt ;











}
