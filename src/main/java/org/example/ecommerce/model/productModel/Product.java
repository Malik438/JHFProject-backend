package org.example.ecommerce.model.productModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.OrderItem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long productId;

    private  String name;
    private  String description;
    private  int price;
    private int quantity;
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "supplier_id")
    //    private Supplier supplier;



    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FavouriteProduct> favouriteProducts;

    @OneToMany(mappedBy = "product")
    private   List<ProductAttributes> list = new ArrayList<ProductAttributes>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id")
    private ProductCategory productCat;

    @OneToOne(mappedBy = "product")
    @JsonIgnore
    private CartItem cartItem ;


    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private SavedProduct savedProduct ;



    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "product")
    private OrderItem orderItem;



     private  Timestamp createdAt ;
     private  Timestamp updatedAt ;





}
