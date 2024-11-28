package org.example.ecommerce.model.usersModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name =  "user_id")
    @JsonIgnore
    private User user;

    private String paymentType ;

    private int accountNumber ;

    private LocalDate expiryDate ;

    private  Double amount ;


    private Timestamp createdAt ;
    private  Timestamp updatedAt ;








}
