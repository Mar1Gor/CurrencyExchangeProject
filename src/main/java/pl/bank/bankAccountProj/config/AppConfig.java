package pl.bank.bankAccountProj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"pl.bank.bankAccountProj"})
public class AppConfig {

  @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
