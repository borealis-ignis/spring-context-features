package org.borealis.scf.controller;

import lombok.extern.slf4j.Slf4j;
import org.borealis.scf.service.RandNumberProviderService;

/**
 * @author Sergey Kastalski
 */
@Slf4j
public abstract class AbstractController {

	private final RandNumberProviderService randNumberProviderService;

	protected AbstractController(final RandNumberProviderService randNumberProviderService) {
		this.randNumberProviderService = randNumberProviderService;
	}

	protected void printRandID(final String methodName) {
		log.info("Method " + methodName + "(), Controller rand id: " + randNumberProviderService.getRandNumber());
	}

}
