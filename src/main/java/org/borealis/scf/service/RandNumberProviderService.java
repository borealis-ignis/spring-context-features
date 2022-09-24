package org.borealis.scf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * @author Sergey Kastalski
 */
//@Service
//@Scope(SCOPE_PROTOTYPE)
@Slf4j
public class RandNumberProviderService implements InitializingBean {

	private final int randNumber;

	public RandNumberProviderService() {
		randNumber = new Random().nextInt(100);
	}

	@PostConstruct
	public void initPostConstruct() {
		log.info("{Prototype} @PostConstruct initializing");
	}

	@Override
	public void afterPropertiesSet() {
		log.info("{Prototype} afterPropertiesSet initializing");
	}

	public void init() {
		log.info("{Prototype} initializing by init-method, generated value: " + randNumber);
	}

	public int getRandNumber() {
		return randNumber;
	}
}
