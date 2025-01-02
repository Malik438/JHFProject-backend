package org.example.ecommerce.model.productModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.enums.ProductCatalog;
import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.OrderItem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long productId;

    private  String name;
    private  String description;
    private  int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @JsonIgnore
    private Supplier supplier;



    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FavouriteProduct> favouriteProducts;

    @OneToMany(mappedBy = "product")
    private   List<ProductAttributes> list = new ArrayList<ProductAttributes>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id")
    private ProductCategory productCat;

    // one to many
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CartItem> cartItem ;


    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private SavedProduct savedProduct ;



    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<OrderItem> orderItem;

    @Enumerated(EnumType.STRING)
    private ProductCatalog productCatalog;

    @JsonProperty("supplierId") // Includes this field in the JSON response
    public Long getSupplierId() {
        return supplier != null ? supplier.getSupplierId() : null;
    }



    @Lob
    private  String imageUrl;



     private  Timestamp createdAt ;
     private  Timestamp updatedAt ;





}
