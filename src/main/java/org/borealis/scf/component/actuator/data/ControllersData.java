package org.borealis.scf.component.actuator.data;

import lombok.Getter;
import org.borealis.scf.controller.AbstractController;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergey Kastalski
 */
@Component
@Getter
public class ControllersData {

	private final String name = "all controllers";

	private final List<String> controllers;

	public ControllersData(final List<AbstractController> controllers) {
		this.controllers = controllers.stream().map(c -> c.getClass().getSimpleName()).collect(Collectors.toList());
	}

}
