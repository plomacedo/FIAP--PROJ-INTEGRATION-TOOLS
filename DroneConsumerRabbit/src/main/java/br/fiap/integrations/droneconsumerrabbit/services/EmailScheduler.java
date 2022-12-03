package br.fiap.integrations.droneconsumerrabbit.services;
import br.fiap.integrations.droneconsumerrabbit.models.DroneRisk;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailScheduler implements Job {

    @Autowired
    DroneRiskService droneRiskService;

    @Autowired
    EmailService emailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(droneRiskService.findAll()!=null) {
            List<DroneRisk> riskListMessage = droneRiskService.findAll();
                if(riskListMessage.size()!=0){
                    String emailMessage = emailService.createEmailMessage(riskListMessage);
                    emailService.sendEmail( emailService.emailSettings( emailMessage ) );
                }

        }
    }
}
