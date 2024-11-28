package org.example.ecommerce.model.productModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {

    private Product product;
    private List<ProductAttributes> attributes;
    private ProductCategory productCategory;
}

