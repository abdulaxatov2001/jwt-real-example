package uz.pdp.jwtrealexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.jwtrealexample.entity.Role;
import uz.pdp.jwtrealexample.entity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleName(RoleName roleName);
}
