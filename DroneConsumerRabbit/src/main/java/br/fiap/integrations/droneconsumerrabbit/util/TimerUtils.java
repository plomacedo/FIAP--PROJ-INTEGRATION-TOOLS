package br.fiap.integrations.droneconsumerrabbit.util;

import br.fiap.integrations.droneconsumerrabbit.models.TimeDetails;
import org.quartz.*;
import java.util.Date;

public final class TimerUtils {

    private TimerUtils() {}
    public static JobDetail buildJobDetail(final Class jobClass, final TimeDetails info) {

        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);
        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final TimeDetails info) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMs());

        if (info.isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1);
        }
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
                .build();
    }
}