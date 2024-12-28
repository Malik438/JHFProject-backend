package org.example.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.model.orderModel.enums.OrderStatus;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.ProductDto;
import org.example.ecommerce.model.projections.IOrderProjection;
import org.example.ecommerce.service.authService.AuthenticationService;
import org.example.ecommerce.service.productService.ProductService;
import org.example.ecommerce.service.supplier.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/supplier")
public class SupplierController {

    private final SupplierService supplierService;
    private final AuthenticationService authenticationService;
    private  final ProductService productService;





    @PostMapping("product/create/{supplierId}")
    public ResponseEntity<String> addProduct(@RequestPart(name = "productDto") String productDtoJson , @RequestPart(name = "multipartFile") MultipartFile multipartFile , @PathVariable(name = "supplierId") long id ) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDto productDto = objectMapper.readValue(productDtoJson, ProductDto.class);
            productService.createProduct(productDto ,multipartFile , id);
            return ResponseEntity.ok("Product added successfully");

        }catch (Exception e){

            return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @GetMapping("products/{supplier_id}")
    public ResponseEntity<Optional<List<Product>>> getSupplierProducts(@PathVariable(name ="supplier_id") Long supplier_id) {
        return  ResponseEntity.ok().body(supplierService.getProducts(supplier_id));
    }

    @GetMapping("orderDetails/{supplier_id}")
    public  ResponseEntity<Optional<List<IOrderProjection>>> getSupplierOrderDetails(@PathVariable(name ="supplier_id") Long supplier_id) {
        return  ResponseEntity.ok().body(supplierService.getOrderDetails(supplier_id));
    }


    @PatchMapping("orderDetails/update/status/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {

        String status = request.get("status");
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            supplierService.updateOrderStatus(id, newStatus);

            return ResponseEntity.ok("Order status updated successfully to: " + newStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid order status: " + status);
        }
    }

    @GetMapping("profits/{supplier_id}")
    public ResponseEntity<Optional<Double>> getSupplierProfits(@PathVariable(name ="supplier_id") Long id) {
        return      ResponseEntity.ok().body(supplierService.getProfits(id));
    }


    @GetMapping("shipped/order/{supplier_id}")
    public ResponseEntity<Optional<List<OrderDetails>>> getSupplierShippedOrders(@PathVariable(name ="supplier_id") Long id) {
        return  ResponseEntity.ok().body(supplierService.shippedProducts(id)) ;
    }

    @GetMapping("view/order/{id}")
    public ResponseEntity<Optional<OrderDetails>> viewOrder(@PathVariable(name ="id") Long id) {
        return  ResponseEntity.ok().body(supplierService.getOrder(id));
    }













}






//api/supplier/products

// api/supplier/product/add

 //  products should have status [shipped , delivered]

//api/supplier/today/profit

// table of orders for his supplier that appear the product and and user info

//api/supplier/product/status/shipped {productId}



