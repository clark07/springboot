package com.cs.test.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/12/5.
 */
@Component
public class CsHealth implements HealthIndicator {
	@Override
	public Health health() {
		return Health.status("200").withDetail("detail", "healthDetail").withDetail("detail2", "healthDetail2").build();
	}
}
