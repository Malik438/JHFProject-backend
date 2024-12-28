package org.example.ecommerce.model.projections;

import java.time.LocalDateTime;

public interface IOrderProjection    {
        Long getOrderId();
        String getCustomerFullName();
        String getProductNameList();
        LocalDateTime getCreatedAt();
        String getOrderStatus();


}
