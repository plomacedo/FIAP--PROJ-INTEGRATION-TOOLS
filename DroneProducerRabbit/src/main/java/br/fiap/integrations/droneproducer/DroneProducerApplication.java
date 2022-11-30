package br.fiap.integrations.droneproducer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@EnableRabbit
@SpringBootApplication
public class DroneProducerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run( DroneProducerApplication.class, args );

    }
}