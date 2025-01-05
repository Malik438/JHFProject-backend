package org.example.ecommerce.reopsotries.projections;

import org.example.ecommerce.model.productModel.Image;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductForm  {

      String getProductId();
      String getName();
      String getPrice();
      String getDescription();
      String getMainImage();
      String getSupplierId();




}
