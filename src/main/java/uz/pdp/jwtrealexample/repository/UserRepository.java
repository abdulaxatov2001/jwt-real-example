package uz.pdp.jwtrealexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.jwtrealexample.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);



    Optional<User> findByEmail(String email);

}
