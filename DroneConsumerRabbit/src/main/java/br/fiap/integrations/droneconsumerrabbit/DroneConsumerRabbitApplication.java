package br.fiap.integrations.droneconsumerrabbit;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class DroneConsumerRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run( DroneConsumerRabbitApplication.class, args );
    }

}
