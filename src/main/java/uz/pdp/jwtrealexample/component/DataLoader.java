package uz.pdp.jwtrealexample.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.jwtrealexample.entity.User;
import uz.pdp.jwtrealexample.repository.UserRepository;

import javax.persistence.Column;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    private String mode; //always

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always") ) {
            User user = new User();
            user.setPhoneNumber("1234");
            userRepository.save(user);
            User user1 = new User();
            user1.setPhoneNumber("aziz");
            userRepository.save(user1);
        }
    }
}
