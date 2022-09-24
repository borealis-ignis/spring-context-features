package org.borealis.scf.component.actuator;

import org.borealis.scf.component.actuator.data.ControllersData;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Kastalski
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

	private final ControllersData controllersData;

	public CustomHealthIndicator(final ControllersData controllersData) {
		this.controllersData = controllersData;
	}

	@Override
	public Health health() {
		return new Health.Builder().up().withDetail("custom-item", controllersData).build();
	}

}
