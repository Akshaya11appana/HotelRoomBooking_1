package com.ey.config;

import com.ey.service.AvailabilityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

  private final AvailabilityService availabilityService;

  public SchedulingConfig(AvailabilityService availabilityService) {
    this.availabilityService = availabilityService;
  }


  @Scheduled(cron = "0 * * * * *")
  public void purgeHolds() {
    availabilityService.purgeExpiredHolds();
  }
}
