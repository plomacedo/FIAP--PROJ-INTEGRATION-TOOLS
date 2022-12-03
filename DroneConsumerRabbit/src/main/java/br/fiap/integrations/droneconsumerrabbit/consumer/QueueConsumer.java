package br.fiap.integrations.droneconsumerrabbit.consumer;

import br.fiap.integrations.droneconsumerrabbit.models.DroneRisk;
import br.fiap.integrations.droneconsumerrabbit.services.DroneRiskService;
import br.fiap.integrations.droneconsumerrabbit.services.EmailService;
import br.fiap.integrations.droneconsumerrabbit.util.Utils;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QueueConsumer {

    @Autowired
    EmailService emailService;

    @Autowired
    DroneRiskService droneRiskService;

    public QueueConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload String fileBody) {
        JSONObject mqMessage = Utils.messageConverter(fileBody);

        List<DroneRisk> riskList = DroneRiskService.checkDrones(mqMessage);

        for (DroneRisk drone: riskList) {
                droneRiskService.save(drone);
        }
    }

}