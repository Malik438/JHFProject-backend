package org.example.ecommerce.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.ProductAttributes;
import org.example.ecommerce.model.productModel.ProductCategory;

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

