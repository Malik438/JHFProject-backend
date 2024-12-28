package org.example.ecommerce.service.supplier;

import lombok.RequiredArgsConstructor;
//import org.example.ecommerce.model.Dto.OrderDto;
import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.model.orderModel.enums.OrderStatus;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.Supplier;
import org.example.ecommerce.model.projections.IOrderProjection;
import org.example.ecommerce.reopsotries.orderRepo.OrderDetailsRepositories;
import org.example.ecommerce.reopsotries.productRepo.ProductRepositories;
import org.example.ecommerce.reopsotries.productRepo.SupplierRepositories;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {


    private  final SupplierRepositories supplierRepositories;
    private  final ProductRepositories productRepositories;
    private  final UserRepositories userRepositories;
    private  final OrderDetailsRepositories orderDetailsRepositories;







    public Optional<List<Product>> getProducts(long supplierId) {

        Optional<Supplier> supplier = supplierRepositories.findById(supplierId);
        return supplier.map(Supplier::getProducts);

    }
    public Optional<OrderDetails> getOrder(long orderId) {
        return orderDetailsRepositories.findById(orderId);

    }

    public Optional<List<IOrderProjection>> getOrderDetails(long supplierId) {

        return supplierRepositories.findOrderDetailsBySupplierId(supplierId);

    }

//    public Optional<List<Product>> getProduct(long orderDetailId) {
//        Optional<OrderDetails> orderDetails = orderDetailsRepositories.findById(orderDetailId);
//        return orderDetails.get().getOrderItems();
//
//
//    }>

    public void updateOrderStatus(long orderDetailId , OrderStatus orderStatus) {
              OrderDetails orderDetails = orderDetailsRepositories.findById(orderDetailId).get();
              orderDetails.setOrderStatus(orderStatus);
              orderDetails.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
              orderDetailsRepositories.save(orderDetails);



    }

    public  Optional<Double> getProfits(long supplierId) {
      return    supplierRepositories.findTotalPriceBySupplierIdForLastWeek(supplierId);

    }

    public  Optional<List<OrderDetails>> shippedProducts(long supplierId) {
        return supplierRepositories.findShippedProductsBySupplierId(supplierId);
    }





}
