package org.borealis.scf.component.mbean;

import org.springframework.stereotype.Component;

/**
 * @author Sergey Kastalski
 */
@Component
public class ProfilingController implements ProfilingControllerMBean {

	private boolean enabled;


	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
