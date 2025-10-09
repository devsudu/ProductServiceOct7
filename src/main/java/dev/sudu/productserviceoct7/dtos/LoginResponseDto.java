package dev.sudu.productserviceoct7.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private String email;
    private Long expiryAt;
    private Boolean isLoggedIn;
}
