package org.example.ecommerce.model.orderModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.model.orderModel.enums.SessionStatus;
import org.example.ecommerce.model.usersModel.User;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Shopping_Session")
public class ShoppingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "session",cascade = CascadeType.ALL)
    private List<CartItem> cartItems;



    @Enumerated(EnumType.STRING)
   private SessionStatus sessionStatus;
    



    private  double  totalPrice   ;


    private Timestamp createdAt ;
    private  Timestamp updatedAt ;





}
