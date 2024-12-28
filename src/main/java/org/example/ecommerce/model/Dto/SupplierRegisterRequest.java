package org.example.ecommerce.model.Dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRegisterRequest {

    private String supplierName;
    private String supplierGmail;
    private String supplierUsername;
    private String supplierPassword;

}
