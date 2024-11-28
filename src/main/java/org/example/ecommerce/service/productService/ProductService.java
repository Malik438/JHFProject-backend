package org.example.ecommerce.service.productService;

import jakarta.transaction.Transactional;
import org.example.ecommerce.model.Dto.FavProductDto;
import org.example.ecommerce.model.orderModel.CartItem;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.productModel.*;
import org.example.ecommerce.model.productModel.FavouriteProduct;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.orderRepo.CartItemRepositories;
import org.example.ecommerce.reopsotries.productRepo.*;
import org.example.ecommerce.reopsotries.productRepo.projections.IProductForm;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.example.ecommerce.service.orderService.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepositories productRepositories;
    private  final ProductCategoryRepositories productCategoryRepositories;
    private  final ProductAttributesRepositories productAttributesRepositories;
    private final UserRepositories userRepositories;
    private  final FavouriteProductRepository favouriteProductRepository;
    private  final CartItemRepositories cartItemRepositories;
    private  final SavedProductRepository savedProductRepository;
    private  final SessionService sessionService;

    @Autowired
    public ProductService(ProductRepositories productRepositories, ProductCategoryRepositories productCategoryRepositories, ProductAttributesRepositories productAttributesRepositories, UserRepositories userRepositories, FavouriteProductRepository favouriteProductRepository, CartItemRepositories cartItemRepositories, SavedProductRepository savedProductRepository, SessionService sessionService) {
        this.productRepositories = productRepositories;
        this.productCategoryRepositories = productCategoryRepositories;
        this.productAttributesRepositories = productAttributesRepositories;
        this.userRepositories = userRepositories;
        this.favouriteProductRepository = favouriteProductRepository;
        this.cartItemRepositories = cartItemRepositories;
        this.savedProductRepository = savedProductRepository;
        this.sessionService = sessionService;
    }


  // crud  start here


    public Optional<Product> findProductById(Long id) {
       Optional<Product> product =   productRepositories.findById(id) ;

        product.ifPresent(value -> System.out.println(value.getList()));
        return  product;
    }

    public List<Product> getProducts(){

        return  productRepositories.findAll() ;

    }

    public void  createProduct(ProductDto productDto  ){
        Product product =  productDto.getProduct() ;
        ProductCategory productCategory = productDto.getProductCategory() ;
        List<ProductAttributes> productAttributes = productDto.getAttributes();



        product.setProductCat(productCategory);
        productCategoryRepositories.save(productCategory);

        product =   productRepositories.save(product);
        for (ProductAttributes Attributes : productAttributes) {
            Attributes.setProduct(product);
            productAttributesRepositories.save(Attributes);
        }
        //product.setList(productAttributes);


    }
    public void  deleteProductById(Long id){
           productAttributesRepositories.deleteProductAttributesByProductId(id);
           productRepositories.deleteById(id);

    }

    public Product updateProduct(Product updatedProduct) throws Exception {

        //ResourceNotFoundException todo


        updatedProduct.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

     return  productRepositories.save(updatedProduct);


    }


// crud end here


    public  List<IProductForm> viewProducts(){
        return  productRepositories.findAllIProductFrom() ;
    }


    public  List<ProductCategory> getProductCategories(){
        return   productCategoryRepositories.findAll();
    }


    public  List<IProductForm> getProductFormsByCategory(String productCategoryName){

        return productRepositories.findAllIProductFromByCategory(productCategoryName);

    }


        public  Optional<FavProductDto> addFavProduct(Long userId , Long productId){


         User user =  userRepositories.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
         Product product = productRepositories.findById(productId).orElseThrow(() -> new IllegalArgumentException("product not found"));


            boolean isFav = favouriteProductRepository.existsByUserAndProduct(user,product);

            if(isFav){
                   throw  new IllegalArgumentException("product is already favourite");
            }

            FavouriteProduct favouriteProduct = new FavouriteProduct();
            favouriteProduct.setProduct(product);
            favouriteProduct.setUser(user);


            user.getFavouriteProducts().add(favouriteProduct);
            userRepositories.save(user);

            FavProductDto FavProductDto = new FavProductDto();
            FavProductDto.setId(favouriteProduct.getId());
            FavProductDto.setProductId(favouriteProduct.getProduct().getProductId());
            FavProductDto.setUser_id(favouriteProduct.getUser().getUserId());

            return Optional.of(FavProductDto);



        }

        public  Optional<List<IProductForm>> getFavouriteProducts(Long userId){

             return  productRepositories.findAllFavProductById(userId);



        }

        public  Optional<Boolean> removeFavProduct(Long userId , Long productId){



            int  rowAffected = 0 ;
            User user =  userRepositories.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
            Product product = productRepositories.findById(productId).orElseThrow(() -> new IllegalArgumentException("product not found"));


            boolean isFav = favouriteProductRepository.existsByUserAndProduct(user,product);

            if(isFav){
                rowAffected =  favouriteProductRepository.deleteByUserAndProduct(user,product);


            }

            System.out.println(rowAffected +  "+++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            return  rowAffected > 0  ?  Optional.of(true) : Optional.of(false)   ;

        }


//        @Transactional
//        public  SavedProduct saveItemForLater(Long userId , Long id){
//
//        SavedProduct savedProduct = new SavedProduct();
//
//
//        CartItem cartItem = cartItemRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("cart item not found"));
//
//         User user =  userRepositories.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
//
//
//
//         savedProduct.setUser(user);
//         savedProduct.setProduct(cartItem.getProduct());
//         savedProduct.setSaveAt(Timestamp.valueOf(LocalDateTime.now()));
//
//         savedProductRepository.save(savedProduct);
//
//         cartItemRepositories.deleteById(id);
//
//
//         return  savedProduct ;
//
//
//
//        }
//
//        @Transactional
//        public  void  saveItemToCart(Long userId , Long SavedItemId){
//
//        CartItem cartItem = new CartItem();
//         Optional<SavedProduct> savedProduct = savedProductRepository.findById(SavedItemId);
//         //User user = userRepositories.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
//         Optional<ShoppingSession> shoppingSession =   sessionService.getShoppingSessionByUserId(userId);
//
//
//
//            if(savedProduct.isPresent() && shoppingSession.isPresent()){
//
//                cartItem.setProduct(savedProduct.get().getProduct());
//                cartItem.setQuantity(savedProduct.get().getQuantity());
//                cartItem.setSession(shoppingSession.get());
//                cartItem.setCreatedAt(savedProduct.get().getSaveAt());
//
//                cartItemRepositories.save(cartItem);
//                shoppingSession.get().getCartItems().add(cartItem);
//                savedProductRepository.deleteById(SavedItemId);
//
//
//
//
//         }
//
//
//        }









        // cartItems save for later



















}
