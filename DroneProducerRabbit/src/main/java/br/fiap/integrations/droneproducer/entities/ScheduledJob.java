package br.fiap.integrations.droneproducer.entities;
import br.fiap.integrations.droneproducer.sender.QueueSender;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger( ScheduledJob.class);

    @Value("${uri}")
    private String uri;

    @Autowired
    private QueueSender sender;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimeDetails info = (TimeDetails) jobDataMap.get( ScheduledJob.class.getSimpleName());
        sender.send( getDroneData());
        LOG.info("Remaining fire count is '{}'", info.getRemainingFireCount());
    }

    public String getDroneData(){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }
}