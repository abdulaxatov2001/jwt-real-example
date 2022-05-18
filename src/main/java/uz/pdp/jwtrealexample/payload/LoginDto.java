package uz.pdp.jwtrealexample.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uz.pdp.jwtrealexample.config.SecurityConfig;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class LoginDto {


    @NotNull
    private String phoneNumber;


    private Integer password;

    public LoginDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
