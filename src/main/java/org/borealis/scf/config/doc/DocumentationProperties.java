package org.borealis.scf.config.doc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Sergey Kastalski
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "documentation")
public class DocumentationProperties {

	private String title;

	private String summary;

	private String description;

	private Contact contact;

	private List<Server> servers;

}

