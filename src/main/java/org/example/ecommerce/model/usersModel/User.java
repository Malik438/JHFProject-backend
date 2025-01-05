package org.example.ecommerce.model.usersModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce.enums.Role;
import org.example.ecommerce.model.orderModel.OrderDetails;
import org.example.ecommerce.model.orderModel.ShoppingSession;
import org.example.ecommerce.model.productModel.FavouriteProduct;
import org.example.ecommerce.model.productModel.SavedProduct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true , nullable = false)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true , nullable = false)
    private String email;
    private String phone;


    @Enumerated(EnumType.STRING)
    private Role role ;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FavouriteProduct> favouriteProducts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> address;

    @OneToMany(mappedBy = "userP", cascade = CascadeType.ALL )
    private List<UserPayment> payments;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List <ShoppingSession> shoppingSession;


    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails ;

    @OneToMany(mappedBy = "user")
    private List<SavedProduct> savedProductList ;



    private Timestamp createdAt ;
    private  Timestamp updatedAt ;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }
}
