package dev.sudu.productserviceoct7.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

    @Value(("${user.service.baseUrl}"))
    private String userServiceBaseUrl;
}
