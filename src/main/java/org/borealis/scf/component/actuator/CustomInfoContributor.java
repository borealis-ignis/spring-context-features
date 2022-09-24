package org.borealis.scf.component.actuator;

import org.borealis.scf.component.actuator.data.ControllersData;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Kastalski
 */
@Component
public class CustomInfoContributor implements InfoContributor {

	private final ControllersData controllersData;

	public CustomInfoContributor(final ControllersData controllersData) {
		this.controllersData = controllersData;
	}

	@Override
	public void contribute(final Info.Builder builder) {
		builder.withDetail("controllers", controllersData);
	}

}
