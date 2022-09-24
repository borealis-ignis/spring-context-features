package org.borealis.scf.component.listener;

import lombok.SneakyThrows;
import org.borealis.scf.component.PostContextInitializer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Sergey Kastalski
 */
@Component
public class PostInitializationContextInterfaceListener {

	private final AbstractContextListenerProcessor abstractContextListener;

	public PostInitializationContextInterfaceListener(final AbstractContextListenerProcessor abstractContextListener) {
		this.abstractContextListener = abstractContextListener;
	}

	@SneakyThrows
	@EventListener
	public void handleContextRefreshEvent(final ContextRefreshedEvent event) {
		final SpecificListenerProcessor specificListenerProcessor = (originalBeanClassName, beanObject) -> {
			final Class<?> originalClass = ClassUtils.resolveClassName(originalBeanClassName, beanObject.getClass().getClassLoader());
			if (Arrays.stream(originalClass.getInterfaces()).anyMatch(clazz -> clazz == PostContextInitializer.class)) {
				final Method interfaceMethod = Arrays.stream(PostContextInitializer.class.getMethods()).findFirst().orElseThrow();
				final Method beanMethod = ReflectionUtils.findRequiredMethod(beanObject.getClass(), interfaceMethod.getName());
				beanMethod.invoke(beanObject);
			}
		};

		abstractContextListener.processEvent(event, specificListenerProcessor);
	}

}
