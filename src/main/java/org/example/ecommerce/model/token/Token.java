package org.example.ecommerce.model.token;


import jakarta.persistence.*;
import lombok.*;
import org.example.ecommerce.model.productModel.Supplier;
import org.example.ecommerce.model.usersModel.User;
import org.springframework.security.core.userdetails.UserDetails;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}