package uz.pdp.jwtrealexample.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.jwtrealexample.entity.User;
import uz.pdp.jwtrealexample.entity.enums.RoleName;
import uz.pdp.jwtrealexample.payload.ApiResponse;
import uz.pdp.jwtrealexample.payload.LoginDto;
import uz.pdp.jwtrealexample.payload.RegisterDto;
import uz.pdp.jwtrealexample.repository.RoleRepository;
import uz.pdp.jwtrealexample.repository.UserRepository;
import uz.pdp.jwtrealexample.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService  implements UserDetailsService {

    private final  UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    private final RoleRepository roleRepository;

    private final JavaMailSender javaMailSender;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       @Lazy JavaMailSender javaMailSender, @Lazy AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    public ApiResponse register(RegisterDto registerDto) {

        boolean existsByEmail = userRepository.existsByPhoneNumber(registerDto.getPhoneNumber());
        if (existsByEmail) {
            return new ApiResponse("This user already exist",false);
        }
        User user = new User();

        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));

        userRepository.save(user);


        return new ApiResponse("Successfully registered. Please verify",true);
    }

    public Boolean sendEmail(String sendingEmail, String emailCode){
        try {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("pdp@gmail.com");
        mailMessage.setTo(sendingEmail);
        mailMessage.setSubject("Verification Code");
        mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode="+emailCode+"&email="+sendingEmail+"'>Verify</a>");

        javaMailSender.send(mailMessage);
        return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse verifyEmail( String email) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(email);
        if (optionalUser.isEmpty()) {
            return new ApiResponse("Error",false);
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        userRepository.save(user);
        return new ApiResponse("Account confirmed",true);
    }

    public ApiResponse login(LoginDto loginDto) {

        try {
            if (userRepository.findByPhoneNumber(loginDto.getPhoneNumber()).isEmpty())
                return new ApiResponse("yoq token",false);
                Authentication authenticate = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken
                    (loginDto.getPhoneNumber(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getPhoneNumber());
            return new ApiResponse("Mana token",true,token);
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Username or password is wrong",false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }throw new UsernameNotFoundException(username+" not found");*/
        return userRepository.findByPhoneNumber(username).orElseThrow(() ->
                new UsernameNotFoundException(username+" not found"));
    }

}
