package nl.oscar.dpi.donutrater.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ComponentScan("nl.oscar.dpi.donutrater.gateway")
public class GatewayApplication {

    public static void main(String... args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
