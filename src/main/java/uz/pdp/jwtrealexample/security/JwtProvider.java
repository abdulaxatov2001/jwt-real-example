package uz.pdp.jwtrealexample.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.jwtrealexample.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    private final long expireTime = 1000 * 86400 * 30;
    private final String secretKey = "asssalomu";

    public String generateToken(String phoneNumber) {
        String token = Jwts
                .builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
