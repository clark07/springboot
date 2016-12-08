package com.cs.test.tasks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by admin on 2016/12/6.
 */
@EnableScheduling
@EnableAsync
@Configuration
@ImportResource(locations = {"classpath:spring/application-schedule.xml"})
public class ScheduleConfig {
}
