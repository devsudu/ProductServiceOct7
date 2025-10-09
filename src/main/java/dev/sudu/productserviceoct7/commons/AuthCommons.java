package dev.sudu.productserviceoct7.commons;

import dev.sudu.productserviceoct7.dtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {
    private RestClient restClient;
    @Value("${user.service.baseUrl}")
    private String userServiceBaseUrl;

    public AuthCommons(RestTemplate restTemplate, RestClient restClient) {
        this.restClient = restClient;
    }

    public UserResponseDto validateToken(String token) {
        ResponseEntity<UserResponseDto> responseEntity = restClient.get().uri(userServiceBaseUrl+"/validate/".concat(token)).retrieve().toEntity(UserResponseDto.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        return null;
    }
}
