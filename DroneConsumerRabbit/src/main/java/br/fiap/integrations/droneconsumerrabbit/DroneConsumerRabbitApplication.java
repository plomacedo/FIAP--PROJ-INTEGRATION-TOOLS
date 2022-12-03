package br.fiap.integrations.droneconsumerrabbit;

import br.fiap.integrations.droneconsumerrabbit.models.DroneRisk;
import br.fiap.integrations.droneconsumerrabbit.services.DroneRiskService;
import br.fiap.integrations.droneconsumerrabbit.services.PlayerService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@EnableRabbit
public class DroneConsumerRabbitApplication {

    private static PlayerService service = null;

    @Autowired
    static
    DroneRiskService droneRiskService;

    public DroneConsumerRabbitApplication(PlayerService service) {
        this.service = service;
    }

    public static void main(String[] args) {

        SpringApplication.run( DroneConsumerRabbitApplication.class, args );
        service.runTimer();

    }

}
