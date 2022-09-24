package org.borealis.scf;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author Sergey Kastalski
 */
@SpringBootApplication
public class RestApplicationRunner {

	private static final String PID_FILE_NAME = "shutdown.pid";

	public static void main(String[] args) {
		final SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(RestApplicationRunner.class)
				.web(WebApplicationType.SERVLET);
		applicationBuilder.build().addListeners(new ApplicationPidFileWriter(PID_FILE_NAME));
		applicationBuilder.run(args);
	}

}
