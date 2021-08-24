package com.diegunix.meep.config.quartz;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class SchedulerConfiguration {

  @Value("${quartz.properties.path}")
  private String quartzPropertiesPath;

  private final List<Trigger> listOfTrigger;

  public SchedulerConfiguration(List<Trigger> listOfTrigger) {
    super();
    this.listOfTrigger = listOfTrigger;
  }

  @Bean
  public JobFactory jobFactory(ApplicationContext applicationContext) {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setOverwriteExistingJobs(false);
    factory.setAutoStartup(true);
    factory.setJobFactory(jobFactory);
    factory.setQuartzProperties(quartzProperties());
    factory.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));

    return factory;
  }

  @Bean
  public Properties quartzProperties() throws IOException {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource(quartzPropertiesPath));
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

  public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setStartDelay(0L);
    factoryBean.setRepeatInterval(pollFrequencyMs);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
    CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setStartTime(new Date());
    factoryBean.setCronExpression(cronExpression);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
    return factoryBean;
  }

  @SuppressWarnings("unchecked")
  public static JobDetailFactoryBean createJobDetail(Class<?> jobClass) {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass((Class<? extends Job>) jobClass);
    factoryBean.setDurability(true);
    return factoryBean;
  }
}
