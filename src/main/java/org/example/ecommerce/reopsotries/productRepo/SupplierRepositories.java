package org.example.ecommerce.reopsotries.productRepo;

//import org.example.ecommerce.model.Dto.OrderDto;
import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.model.productModel.Supplier;
import org.example.ecommerce.model.projections.IOrderProjection;
import org.example.ecommerce.model.usersModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SupplierRepositories extends JpaRepository<Supplier, Long> {




    @Query(value = "SELECT od.id AS orderId, " +
            "CONCAT(u.first_name, ' ', u.last_name) AS customerFullName, " +
            "GROUP_CONCAT(p.name ORDER BY p.name SEPARATOR ', ') AS productNameList, " +
            "od.created_at AS createdAt, " +
            "od.order_status AS orderStatus " +
            "FROM order_details od " +
            "JOIN user u ON u.user_id = od.user_id " +
            "JOIN order_item oi ON od.id = oi.order_id " +
            "JOIN product p ON oi.product_id = p.product_id " +
            "WHERE p.supplier_id = :supplierId " +
            "GROUP BY od.id, od.created_at, od.order_status, u.first_name, u.last_name",
            nativeQuery = true)
    Optional<List<IOrderProjection>> findOrderDetailsBySupplierId(@Param("supplierId") Long supplierId);



    @Query(value = "SELECT SUM(od.total_price) AS profits " +
            "FROM order_details od " +
            "JOIN order_item oi ON od.id = oi.order_id " +
            "JOIN product p ON oi.product_id = p.product_id " +
            "WHERE p.supplier_id = :id " +
            "AND od.created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY p.supplier_id " +
            "ORDER BY profits DESC", nativeQuery = true)
    Optional<Double> findTotalPriceBySupplierIdForLastWeek(@Param("id") Long supplierId);


    @Query(value = "SELECT od.* " +
            "FROM order_details od " +
            "JOIN order_item oi ON od.id = oi.order_id " +
            "JOIN product p ON oi.product_id = p.product_id " +
            "WHERE od.order_status = 'SHIPPED' " +
            "AND p.supplier_id = :supplierId "
             , nativeQuery = true)
    Optional<List<OrderDetails>> findShippedProductsBySupplierId(@Param("supplierId") Long supplierId);


     Optional<Supplier> findByUsername(String username);


}
