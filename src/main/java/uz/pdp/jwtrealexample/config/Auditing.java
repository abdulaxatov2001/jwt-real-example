package uz.pdp.jwtrealexample.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.jwtrealexample.entity.User;

import java.util.Optional;
import java.util.UUID;

public class Auditing implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null &&
            authentication.isAuthenticated() &&
            !authentication.getPrincipal().equals("anonymousUser")){
            User currentUser = (User) authentication.getPrincipal();
            return Optional.of(currentUser.getId());
        }
        return Optional.empty();
    }
}
