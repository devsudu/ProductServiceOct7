package dev.sudu.productserviceoct7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductServiceOct7Application {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceOct7Application.class, args);
    }

}
