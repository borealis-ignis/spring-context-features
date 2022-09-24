package org.borealis.scf.component.listener;

import lombok.SneakyThrows;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.borealis.scf.component.annotation.PostContextInitializator;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

/**
 * @author Sergey Kastalski
 */
@Component
public class PostInitializationContextAnnotationListener {

	private final AbstractContextListenerProcessor abstractContextListener;

	public PostInitializationContextAnnotationListener(final AbstractContextListenerProcessor abstractContextListener) {
		this.abstractContextListener = abstractContextListener;
	}

	@SneakyThrows
	@EventListener
	public void handleContextRefreshEvent(final ContextRefreshedEvent event) {
		final SpecificListenerProcessor specificListenerProcessor = (originalBeanClassName, beanObject) ->
				Arrays.stream(beanObject.getClass().getMethods())
						.filter(method -> method.isAnnotationPresent(PostContextInitializator.class))
						.forEach(method -> ReflectionUtils.invokeMethod(method, beanObject));

		abstractContextListener.processEvent(event, specificListenerProcessor);
	}

}
