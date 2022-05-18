package uz.pdp.jwtrealexample.payload;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Random;

@Data
public class RegisterDto {


    @NotNull
    private  String phoneNumber;

    private String username;


}
