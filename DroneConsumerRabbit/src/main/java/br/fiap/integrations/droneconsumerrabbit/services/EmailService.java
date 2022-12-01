package br.fiap.integrations.droneconsumerrabbit.services;

import br.fiap.integrations.droneconsumerrabbit.enums.StatusEmail;
import br.fiap.integrations.droneconsumerrabbit.models.EmailModel;
import br.fiap.integrations.droneconsumerrabbit.repositories.EmailRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail( LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);
            emailModel.setStatusEmail( StatusEmail.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }
        finally {
            return emailRepository.save(emailModel);
        }
    }
    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }
    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }

    public String createEmailMessage(List<JSONObject> riskDrones){
        String message = "\n---------------------------------------------"
                + "\nDrones in risk: " + riskDrones.size();

        for (int i = 0; i < riskDrones.size(); i++) {
            message = message + "\n---------------------------------------------"
                    + "\n Drone " +i+ ": "
                    + "\n Drone id: " + riskDrones.get(0).getInt("droneId")
                    + "\n Temperature: " + riskDrones.get(0).getInt("temperature")
                    + "\n Latitude: " + riskDrones.get(0).getBigDecimal("latitude")
                    + "\n Longitude: " + riskDrones.get(0).getBigDecimal("longitude")
                    + "\n Humidity: " + riskDrones.get(0).getDouble("humidity")
                    + "\n Tracker Enable: " + riskDrones.get(0).getBoolean("tracker");
        }
        return message;
    }
    public static EmailModel emailSettings(String message){

        EmailModel emailModel = new EmailModel();
        emailModel.setEmailFrom("rafaellross@gmail.com");
        emailModel.setEmailTo("rafaellross@gmail.com");
        emailModel.setSubject("Drones in risk");
        emailModel.setOwnerRef("FIAP INTEGRATIONS");
        emailModel.setText(message);

        return emailModel;

    }

}

