package org.example.ecommerce.service.productService;

import org.example.ecommerce.Dto.FavProductDto;
import org.example.ecommerce.Dto.ProductDto;
import org.example.ecommerce.model.productModel.*;
import org.example.ecommerce.model.productModel.FavouriteProduct;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.orderRepo.CartItemRepository;
import org.example.ecommerce.reopsotries.productRepo.*;
import org.example.ecommerce.reopsotries.projections.IProductForm;
import org.example.ecommerce.reopsotries.userRepo.UserRepository;
import org.example.ecommerce.service.orderService.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private  final ProductCategoryRepository productCategoryRepository;
    private  final ProductAttributesRepository productAttributesRepository;
    private final UserRepository userRepository;
    private  final FavouriteProductRepository favouriteProductRepository;
    private  final CartItemRepository cartItemRepositories;
    private  final SavedProductRepository savedProductRepository;
    private  final SessionService sessionService;
    private  final SupplierRepository supplierRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ProductAttributesRepository productAttributesRepository, UserRepository userRepository, FavouriteProductRepository favouriteProductRepository, CartItemRepository cartItemRepositories, SavedProductRepository savedProductRepository, SessionService sessionService, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productAttributesRepository = productAttributesRepository;
        this.userRepository = userRepository;
        this.favouriteProductRepository = favouriteProductRepository;
        this.cartItemRepositories = cartItemRepositories;
        this.savedProductRepository = savedProductRepository;
        this.sessionService = sessionService;
        this.supplierRepository = supplierRepository;
    }


  // crud  start here


    public Optional<Product> findProductById(Long id) {
       Optional<Product> product =   productRepository.findById(id) ;

        product.ifPresent(value -> System.out.println(value.getList()));
        return  product;
    }

    public Page<Product> getProducts(int page,int size){

        Pageable pageable = PageRequest.of(page,size);
        return  productRepository.findAll(pageable) ;

    }



    public void  createProduct(ProductDto productDto , MultipartFile file, Long  supplierId  ) throws IOException {



        Product product =  productDto.getProduct() ;
        product.setImageUrl(Base64.getEncoder().encodeToString(file.getBytes()));

        ProductCategory productCategory = productDto.getProductCategory() ;
        List<ProductAttributes> productAttributes = productDto.getAttributes();
        Optional<Supplier> supplier = supplierRepository.findById(supplierId);



        if(supplier.isPresent()){
            product.setSupplier(supplier.get());
            supplier.get().getProducts().add(product);

        }else {
            throw new RuntimeException("Supplier not found");
        }


        product.setProductCat(productCategory);
        productCategoryRepository.save(productCategory);

        product =   productRepository.save(product);


        for (ProductAttributes Attributes : productAttributes) {
            Attributes.setProduct(product);
            productAttributesRepository.save(Attributes);
        }




        //product.setList(productAttributes);


    }
    public void  deleteProductById(Long id){
           productAttributesRepository.deleteProductAttributesByProductId(id);
           productRepository.deleteById(id);

    }

    public Product updateProduct(Product updatedProduct) throws Exception {

        //ResourceNotFoundException todo


        updatedProduct.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

     return  productRepository.save(updatedProduct);


    }


// crud end here


    public  Page<IProductForm> viewProducts(int page ,int size ){

        Pageable pageable = PageRequest.of(page, size) ;

        return  productRepository.findAllIProductFrom(pageable) ;
    }


    public  List<ProductCategory> getProductCategories(){
        return   productCategoryRepository.findAll();
    }


    public  List<IProductForm> getProductFormsByCategory(String productCategoryName){

        return productRepository.findAllIProductFromByCategory(productCategoryName);

    }


        public  Optional<FavProductDto> addFavProduct(Long userId , Long productId){


         User user =  userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
         Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("product not found"));


            boolean isFav = favouriteProductRepository.existsByUserAndProduct(user,product);

            if(isFav){
                   throw  new IllegalArgumentException("product is already favourite");
            }

            FavouriteProduct favouriteProduct = new FavouriteProduct();
            favouriteProduct.setProduct(product);
            favouriteProduct.setUser(user);


            user.getFavouriteProducts().add(favouriteProduct);
            userRepository.save(user);

            FavProductDto FavProductDto = new FavProductDto();
            FavProductDto.setId(favouriteProduct.getId());
            FavProductDto.setProductId(favouriteProduct.getProduct().getProductId());
            FavProductDto.setUser_id(favouriteProduct.getUser().getUserId());

            return Optional.of(FavProductDto);



        }

        public  Optional<List<IProductForm>> getFavouriteProducts(Long userId){

             return  productRepository.findAllFavProductById(userId);



        }

        public  Optional<Boolean> removeFavProduct(Long userId , Long productId){



            int  rowAffected = 0 ;
            User user =  userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("product not found"));


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
