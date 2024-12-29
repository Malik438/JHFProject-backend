package org.example.ecommerce.service.supplier;

import lombok.RequiredArgsConstructor;
//import org.example.ecommerce.model.Dto.OrderDto;
import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.enums.OrderStatus;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.Supplier;
import org.example.ecommerce.reopsotries.projections.IOrderProjection;
import org.example.ecommerce.reopsotries.orderRepo.OrderDetailsRepository;
import org.example.ecommerce.reopsotries.productRepo.ProductRepository;
import org.example.ecommerce.reopsotries.productRepo.SupplierRepository;
import org.example.ecommerce.reopsotries.userRepo.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {


    private  final SupplierRepository supplierRepository;
    private  final ProductRepository productRepository;
    private  final UserRepository userRepository;
    private  final OrderDetailsRepository orderDetailsRepository;







    public Optional<List<Product>> getProducts(long supplierId) {

        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        return supplier.map(Supplier::getProducts);

    }
    public Optional<OrderDetails> getOrder(long orderId) {
        return orderDetailsRepository.findById(orderId);

    }

    public Optional<List<IOrderProjection>> getOrderDetails(long supplierId) {

        return supplierRepository.findOrderDetailsBySupplierId(supplierId);

    }

//    public Optional<List<Product>> getProduct(long orderDetailId) {
//        Optional<OrderDetails> orderDetails = orderDetailsRepositories.findById(orderDetailId);
//        return orderDetails.get().getOrderItems();
//
//
//    }>

    public void updateOrderStatus(long orderDetailId , OrderStatus orderStatus) {
              OrderDetails orderDetails = orderDetailsRepository.findById(orderDetailId).get();
              orderDetails.setOrderStatus(orderStatus);
              orderDetails.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
              orderDetailsRepository.save(orderDetails);



    }

    public  Optional<Double> getProfits(long supplierId) {
      return    supplierRepository.findTotalPriceBySupplierIdForLastWeek(supplierId);

    }

    public  Optional<List<OrderDetails>> shippedProducts(long supplierId) {
        return supplierRepository.findShippedProductsBySupplierId(supplierId);
    }





}
