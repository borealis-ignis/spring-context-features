package org.borealis.scf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.borealis.scf.config.doc.DocumentationProperties;
import org.borealis.scf.service.RandNumberProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.http.HttpMethod.*;

/**
 * @author Sergey Kastalski
 */
@Configuration
@Import({DocumentationProperties.class, CityVersion1_0Configuration.class, CityVersion1_1Configuration.class})
public class ScfConfiguration implements WebMvcConfigurer {

	private final String[] cityCrossOrigins;

	public ScfConfiguration(@Value("${controller.city.cors}") final String[] cityCrossOrigins) {
		this.cityCrossOrigins = cityCrossOrigins;
	}

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/*/city/**").allowedOrigins(cityCrossOrigins)
				.allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name());
	}

	@Bean
	public OpenAPI customOpenApi(final DocumentationProperties doc) {
		final List<Server> servers = doc.getServers().stream()
				.map(server -> new Server().url(server.getUrl()).description(server.getDescription()))
				.collect(Collectors.toList());
		final Contact contact = new Contact().name(doc.getContact().getName()).url(doc.getContact().getUrl());

		return new OpenAPI()
				.info(new Info()
						.title(doc.getTitle())
						.summary(doc.getSummary())
						.description(doc.getDescription())
						.license(new License().name("Apache 2.0"))
						.contact(contact))
				.servers(servers);
	}

	@Scope(SCOPE_PROTOTYPE)
	@Bean(initMethod = "init")
	public RandNumberProviderService randNumberProviderService() {
		return new RandNumberProviderService();
	}

}
