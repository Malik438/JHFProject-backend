package org.example.ecommerce.reopsotries.projections;

import org.springframework.data.domain.Pageable;

public interface IProductForm  {

      String getProductId();
      String getName();
      String getPrice();
      String getDescription();
      String getImageUrl();
      String getSupplierId();




}
