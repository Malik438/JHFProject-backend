package org.example.ecommerce.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FavProductDto {

     private  Long id;
    private   Long user_id;
    private   Long productId;

}
