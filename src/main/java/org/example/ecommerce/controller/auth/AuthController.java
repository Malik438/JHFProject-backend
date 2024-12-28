package org.example.ecommerce.controller.auth;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.model.Dto.AuthenticationRequest;
import org.example.ecommerce.model.Dto.AuthenticationResponse;
import org.example.ecommerce.model.Dto.SupplierRegisterRequest;
import org.example.ecommerce.model.Dto.UserRegisterRequest;
import org.example.ecommerce.model.productModel.Supplier;
import org.example.ecommerce.model.usersModel.User;
import org.example.ecommerce.reopsotries.userRepo.UserRepositories;
import org.example.ecommerce.service.JwtService.JwtService;
import org.example.ecommerce.service.authService.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private  final AuthenticationService authenticationService;
    private  final JwtService jwtService;
    private  final UserRepositories userRepositories;




    @PostMapping("/supplier/register")
    public ResponseEntity<Supplier> register(@RequestBody SupplierRegisterRequest request){

        return  ResponseEntity.ok(authenticationService.supplierRegister(request));

    }

    @PostMapping("supplier/authenticate")
    public  ResponseEntity<AuthenticationResponse> supplierAuthenticate(@RequestBody AuthenticationRequest request){
        return  ResponseEntity.ok().body(authenticationService.supplierAuthenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequest request){

        return  ResponseEntity.ok(authenticationService.userRegister(request));

    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return  ResponseEntity.ok(authenticationService.userAuthenticate(request));


    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, java.io.IOException {
        authenticationService.refreshToken(request, response);
    }




}
