package br.fiap.integrations.droneconsumerrabbit.services;

import br.fiap.integrations.droneconsumerrabbit.enums.StatusEmail;
import br.fiap.integrations.droneconsumerrabbit.models.DroneRisk;
import br.fiap.integrations.droneconsumerrabbit.models.EmailModel;
import br.fiap.integrations.droneconsumerrabbit.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${email.to}")
    private String emailTo;
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

    public String createEmailMessage(List<DroneRisk> riskList){

        String message = "\n--------------------------------"
                       + "\n           DRONES ALERT           "
                       + "\n--------------------------------" ;;

        for (DroneRisk drone: riskList) {
            message = message + drone.toString();
        }
        return message;
    }
    public EmailModel emailSettings(String message){

        EmailModel emailModel = new EmailModel();
        emailModel.setEmailFrom(emailFrom);
        emailModel.setEmailTo(emailTo);
        emailModel.setSubject("DRONES ALERT!");
        emailModel.setOwnerRef("FIAP INTEGRATIONS");
        emailModel.setText(message);

        return emailModel;

    }

}