package uz.pdp.jwtrealexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.jwtrealexample.entity.User;
import uz.pdp.jwtrealexample.payload.LoginDto;
import uz.pdp.jwtrealexample.repository.UserRepository;

import java.util.Optional;

@RequestMapping("/api/password")
@RestController
@RequiredArgsConstructor
public class GeneratePasswordForLogin {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity getPassword(@RequestBody LoginDto loginDto){
        Optional<User> optionalUser = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
        if (!optionalUser.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body("You must registired");
        Integer generate = generate();
        User user = optionalUser.get();

        user.setGeneratePassword(passwordEncoder.encode(String.valueOf(generate)));
        userRepository.save(user);
        System.out.println(generate);
        return ResponseEntity.ok().body( "bu sms orqali jonatish kk edi: "+generate);
    }

    public Integer generate(){
        int smsCode = (int) ((Math.random() * 9000) + 900);
        return  smsCode;
    }
}
