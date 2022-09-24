package org.borealis.scf.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Kastalski
 */
@Configuration
public class CityVersion1_0Configuration {

	@Bean
	public GroupedOpenApi cityApi_v1_0() {
		return GroupedOpenApi.builder().group("cities v1.0").pathsToMatch("/v1.0/city/**").build();
	}

}
