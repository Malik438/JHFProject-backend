package org.example.ecommerce.service.authService;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.example.ecommerce.Dto.AuthenticationRequest;
import org.example.ecommerce.Dto.AuthenticationResponse;
import org.example.ecommerce.Dto.SupplierRegisterRequest;
import org.example.ecommerce.Dto.UserRegisterRequest;

import org.example.ecommerce.model.productModel.Supplier;
import org.example.ecommerce.model.token.Token;
import org.example.ecommerce.model.token.TokenRepository;
import org.example.ecommerce.model.token.TokenType;
import org.example.ecommerce.enums.Role;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.productRepo.SupplierRepository;
import org.example.ecommerce.reopsotries.userRepo.UserAddressRepository;
import org.example.ecommerce.reopsotries.userRepo.UserRepository;
import org.example.ecommerce.service.JwtService.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder ;
    private final JwtService  jwtService ;
    private  final AuthenticationManager authenticationManager ;
    private  final UserAddressRepository userAddressRepository;
    private  final TokenRepository tokenRepository ;
    private  final SupplierRepository supplierRepository;

    public User userRegister(UserRegisterRequest userRegisterRequest) {
        User user = new User();

        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode( userRegisterRequest.getPassword()) );
        user.setEmail(userRegisterRequest.getEmail());
        user.setAddress(userRegisterRequest.getAddresses());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setPhone(userRegisterRequest.getPhoneNumber());
        user.setRole(Role.USER);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));


        userRepository.save(user);

        //userAddressRepositories.saveAll(registerRequest.getAddresses());


        return  user ;


    }

    public Supplier supplierRegister(SupplierRegisterRequest supplierRegisterRequest) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierRegisterRequest.getSupplierName());
        supplier.setGmail(supplierRegisterRequest.getSupplierGmail());
        supplier.setUsername(supplierRegisterRequest.getSupplierUsername());
        supplier.setPassword(passwordEncoder.encode(supplierRegisterRequest.getSupplierPassword()));
        supplier.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        supplier.setRole(Role.SUPPLIER);
       return   supplierRepository.save(supplier);


    }

    public AuthenticationResponse supplierAuthenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        Supplier supplier = supplierRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);

            var token = jwtService.generateToken(supplier);
            var refreshToken = jwtService.generateRefreshToken(supplier);
            revokeAllUserTokens(supplier);  // Revoke any previous tokens for the Supplier
            saveUserToken(supplier, token); // Save new token for Supplier

            return AuthenticationResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .build();
        }




    public AuthenticationResponse userAuthenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);


        // If found in User repository, generate token for User
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);  // Revoke any previous tokens for the User
        saveUserToken(user, token); // Save new token for User

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    // Helper methods to revoke tokens and save new ones
    private void saveUserToken(UserDetails user, String jwtToken) {
        var token = Token.builder()
                .user(user instanceof  User ? (User) user : null)
                .supplier(user instanceof  Supplier ? (Supplier) user : null)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }



    private void revokeAllUserTokens(UserDetails user) {
         List<Token> validUserTokens = new ArrayList<>();
         if(user instanceof User) {

             validUserTokens = tokenRepository.findAllValidTokenByUser(((User) user).getUserId());
         }else if(user instanceof Supplier) {
             validUserTokens = tokenRepository.findAllValidTokenBySupplier(((Supplier) user).getSupplierId());
         }

        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }




    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, java.io.IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUserName(refreshToken);
        if (userName != null) {
            var user = this.userRepository.findByUsername(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
