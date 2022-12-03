package br.fiap.integrations.droneconsumerrabbit.controllers;


import br.fiap.integrations.droneconsumerrabbit.dtos.EmailDto;
import br.fiap.integrations.droneconsumerrabbit.models.EmailModel;
import br.fiap.integrations.droneconsumerrabbit.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
}
