package org.example.ecommerce.model.productModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;


}
