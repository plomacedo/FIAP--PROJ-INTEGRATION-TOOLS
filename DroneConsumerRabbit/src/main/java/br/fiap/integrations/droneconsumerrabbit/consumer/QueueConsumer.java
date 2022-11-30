package br.fiap.integrations.droneconsumerrabbit.consumer;

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

    public QueueConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload String fileBody) {
        JSONObject mqMessage = Utils.messageConverter(fileBody);
        List<JSONObject> riskDrones = Utils.validateDrone(mqMessage);

        if(riskDrones.size()!=0){
            String emailMessage = emailService.createEmailMessage(riskDrones);
            System.out.println(emailMessage);
            emailService.sendEmail(emailService.emailSettings(emailMessage));
        }
    }

}