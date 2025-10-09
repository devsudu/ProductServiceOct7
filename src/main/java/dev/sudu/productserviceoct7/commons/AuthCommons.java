package dev.sudu.productserviceoct7.commons;

import dev.sudu.productserviceoct7.dtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AuthCommons {
    private final RestClient restClient;
    @Value("${user.service.baseUrl}")
    private String userServiceBaseUrl;

    @Autowired
    public AuthCommons(RestClient.Builder loadBalancedRestClientBuilder) {
        this.restClient = loadBalancedRestClientBuilder.build();
    }
//    public AuthCommons(RestClient restClient) {
//        this.restClient = restClient;
//    }

    public UserResponseDto validateToken(String token) {
        ResponseEntity<UserResponseDto> responseEntity = restClient.patch().uri("http://USERSERVICEOCT3/users/".concat(token)).retrieve().toEntity(UserResponseDto.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        return null;
    }
}
