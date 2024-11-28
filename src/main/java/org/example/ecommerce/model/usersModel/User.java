package org.example.ecommerce.model.usersModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.productModel.FavouriteProduct;
import org.example.ecommerce.model.productModel.SavedProduct;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FavouriteProduct> favouriteProducts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL )
    private List<UserPayment> payments;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private ShoppingSession shoppingSession;


    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
    private OrderDetails orderDetails ;

    @OneToMany(mappedBy = "user")
    private List<SavedProduct> savedProductList ;



    private Timestamp createdAt ;
    private  Timestamp updatedAt ;

















}
