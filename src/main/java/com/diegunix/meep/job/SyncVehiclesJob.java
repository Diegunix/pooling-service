package com.diegunix.meep.job;

import java.time.ZonedDateTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import com.diegunix.meep.config.quartz.SchedulerConfiguration;
import com.diegunix.meep.service.IntegrationService;

import lombok.extern.slf4j.Slf4j;

@Component
@DisallowConcurrentExecution
@Slf4j
public class SyncVehiclesJob implements Job {

  @Value("${application.cron.syncmeep}")
  private String cronExpression;

  @Autowired
  @Lazy
  private IntegrationService integrationService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.info("Executing job SyncVehiclesJob at {} ...", ZonedDateTime.now());
    integrationService.poolingMeep();
    log.info("Execution job SyncVehiclesJob finished in {} ms.", ZonedDateTime.now());
  }

  @Bean(name = "jobDetailSyncVehiclesJob")
  public JobDetailFactoryBean jobDetail() {
    return SchedulerConfiguration.createJobDetail(this.getClass());
  }

  @Bean(name = "cronTriggerSyncVehiclesJob")
  public CronTriggerFactoryBean jobTrigger(@Lazy @Qualifier("jobDetailSyncVehiclesJob") JobDetail jobDetail) {
    return SchedulerConfiguration.createCronTrigger(jobDetail, cronExpression);
  }
}
