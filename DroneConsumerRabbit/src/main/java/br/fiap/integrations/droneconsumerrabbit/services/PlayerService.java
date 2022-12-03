package br.fiap.integrations.droneconsumerrabbit.services;

import br.fiap.integrations.droneconsumerrabbit.models.TimeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final SchedulerService scheduler;

    @Autowired
    public PlayerService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runTimer() {
        final TimeDetails info = new TimeDetails();
        info.setTotalFireCount(1000);
        info.setRepeatIntervalMs(60000);
        info.setInitialOffsetMs(1000);

        scheduler.schedule( EmailScheduler.class, info);
    }
}