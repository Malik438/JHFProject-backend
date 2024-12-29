package org.example.ecommerce.controller.productController;


import org.example.ecommerce.Dto.FavProductDto;
import org.example.ecommerce.model.productModel.Product;
import org.example.ecommerce.model.productModel.ProductCategory;
import org.example.ecommerce.reopsotries.projections.IProductForm;
import org.example.ecommerce.service.productService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    // add new Product ;


    @GetMapping("/get/{product_id}")
    public ResponseEntity< Optional<Product>> getProduct(@PathVariable("product_id") long id) {
        return ResponseEntity.ok().body(productService.findProductById(id));

    }


    // get all Products ;
    @GetMapping("/get")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name ="size" ,defaultValue = "10" ) int size) {

        return ResponseEntity.ok().body(productService.getProducts(page,size));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().body("Product deleted successfully");
    }


    @PutMapping("/put/updateProduct")
    public ResponseEntity<Product> updateProduct( @RequestBody Product product ) throws Exception {
         return  ResponseEntity.ok().body(productService.updateProduct(product)) ;

    }

    @GetMapping("view/products")
    public  ResponseEntity<Page<IProductForm>> viewProducts(@RequestParam(name = "page") int page , @RequestParam(name = "size") int size) {

        Page<IProductForm> products = productService.viewProducts(page,size);



        return ResponseEntity.ok().body(products);
    }

    @GetMapping("get/categories")
    public  ResponseEntity<List<ProductCategory>> getCategories() {

        return ResponseEntity.ok().body(productService.getProductCategories());

    }

    @GetMapping("get/categories/{category_name}")
    public  ResponseEntity<List<IProductForm>> getCategoriesById(@PathVariable("category_name") String id) {

        return ResponseEntity.ok().body(productService.getProductFormsByCategory(id));

    }


    @PostMapping("add/favProduct/user/{user_id}/{product_id}")
    public  ResponseEntity<Optional<FavProductDto>> getFavProduct(@PathVariable("user_id") Long user_id, @PathVariable("product_id") Long product_id) {
        return  ResponseEntity.ok().body(productService.addFavProduct(user_id,product_id));
    }

    @GetMapping("get/favProduct/user/{user_id}")
    public  ResponseEntity<Optional<List<IProductForm>>> getFavProductsByUserId(@PathVariable("user_id") Long user_id) {
        return  ResponseEntity.ok().body(productService.getFavouriteProducts(user_id));
    }
    @DeleteMapping("delete/favPrdouct/user/{user_id}/{product_id}")
    public  ResponseEntity<Optional<Boolean>> deleteFavProduct(@PathVariable("user_id") Long user_id, @PathVariable("product_id") Long product_id) {
       return  ResponseEntity.ok().body(productService.removeFavProduct(user_id,product_id));
    }





















    // get product by ID  it may be useful for  me
//    @GetMapping("get/product/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable("id") long id) {
//
//        Optional<IProductForm> iProductForm = productService.getProductById(id);
//
//        if (iProductForm.isPresent()) {
//            return ResponseEntity.ok().body(iProductForm);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    "Product with ID :" + id + " not found."
//            );
//        }
//
//    }
     //get product by name  it is  for  search   maybe  or filter
//    @GetMapping("get/product/name/{name}")
//    public ResponseEntity<?> getProductByName(@PathVariable("name") String name) {
//        Optional<IProductForm> iProductForm = productService.getProductByName(name);
//
//        if (iProductForm.isPresent()) {
//            return ResponseEntity.ok().body(iProductForm);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("there is no product with matching value " + name +
//                    " please be sure that you are write the name of product correctly.");
//        }
//
//    }


}
