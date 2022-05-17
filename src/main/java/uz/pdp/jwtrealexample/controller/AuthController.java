package uz.pdp.jwtrealexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.jwtrealexample.entity.User;
import uz.pdp.jwtrealexample.payload.ApiResponse;
import uz.pdp.jwtrealexample.payload.LoginDto;
import uz.pdp.jwtrealexample.payload.RegisterDto;
import uz.pdp.jwtrealexample.security.CurrentUser;
import uz.pdp.jwtrealexample.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDto registerDto){
        ApiResponse response = authService.register(registerDto);
        return ResponseEntity.status(response.isSuccess()?201:409).body(response);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail( @RequestParam String email){
        ApiResponse apiResponse = authService.verifyEmail( email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse response = authService.login(loginDto);
        return ResponseEntity.status(response.isSuccess()?200:401).body(response);
    }
    @GetMapping("/me")
    public ResponseEntity current(@CurrentUser User user){

        return ResponseEntity.ok(user);
    }

}
