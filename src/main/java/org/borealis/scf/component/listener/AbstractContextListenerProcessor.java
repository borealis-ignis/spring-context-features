package org.borealis.scf.component.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Kastalski
 */
@Slf4j
@Component
public class AbstractContextListenerProcessor {

	private final ConfigurableListableBeanFactory configurableListableBeanFactory;

	private final String applicationName;

	protected AbstractContextListenerProcessor(
			final ConfigurableListableBeanFactory configurableListableBeanFactory,
			@Value("${spring.application.name}") final String applicationName) {
		this.configurableListableBeanFactory = configurableListableBeanFactory;
		this.applicationName = applicationName;
	}

	public void processEvent(final ContextRefreshedEvent event, final SpecificListenerProcessor processor) throws Exception {
		final ApplicationContext context = event.getApplicationContext();
		if (!applicationName.equals(context.getId())) {
			return;
		}

		try {
			for (final String beanDefinitionName : context.getBeanDefinitionNames()) {
				final BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
				final String originalBeanClassName = beanDefinition.getBeanClassName();
				if (originalBeanClassName == null) {
					continue;
				}
				final Object beanObject = context.getBean(beanDefinitionName);
				processor.process(originalBeanClassName, beanObject);
			}
		} catch (final Exception e) {
			log.error("Post context initializing stage failed", e);
			throw e;
		}
	}

}
