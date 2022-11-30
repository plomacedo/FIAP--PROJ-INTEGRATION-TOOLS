package br.fiap.integrations.droneproducer.services;

import br.fiap.integrations.droneproducer.entities.ScheduledJob;
import br.fiap.integrations.droneproducer.entities.TimeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlayerService {

    private final SchedulerService scheduler;

    @Autowired
    public PlayerService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runTimer() {
        final TimeDetails info = new TimeDetails();
        info.setTotalFireCount(6);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");

        scheduler.schedule( ScheduledJob.class, info);
    }

    public Boolean deleteTimer(final String timerId) {
        return scheduler.deleteTimer(timerId);
    }

    public List<TimeDetails> getAllRunningTimers() {
        return scheduler.getAllRunningTimers();
    }

    public TimeDetails getRunningTimer(final String timerId) {
        return scheduler.getRunningTimer(timerId);
    }
}