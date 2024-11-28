package org.example.ecommerce.model.productModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String name;
    private String gmail;

//    @OneToMany(mappedBy = "supplier")
//    private List<Product> products;


    private Timestamp createdAt ;
    private  Timestamp updatedAt ;


}
