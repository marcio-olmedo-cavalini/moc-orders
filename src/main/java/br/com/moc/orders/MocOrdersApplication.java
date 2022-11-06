package br.com.moc.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MocOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MocOrdersApplication.class, args);
    }

}
