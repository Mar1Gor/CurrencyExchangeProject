package pl.bank.bankAccountProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class BankAccountApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BankAccountApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.setSources(new HashSet(Arrays.asList(BankAccountApplication.class)));
        springApplication.run(args);

    }

}
