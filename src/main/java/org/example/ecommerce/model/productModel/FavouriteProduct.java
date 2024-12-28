package org.example.ecommerce.model.productModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.model.usersModel.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Favourite_Product")
public class FavouriteProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;











}
