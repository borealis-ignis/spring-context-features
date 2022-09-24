package org.borealis.scf.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Kastalski
 */
@Configuration
public class CityVersion1_1Configuration {

	@Bean
	public GroupedOpenApi cityApi_v1_1() {
		return GroupedOpenApi.builder().group("cities v1.1").pathsToMatch("/v1.1/city/**").build();
	}

}
