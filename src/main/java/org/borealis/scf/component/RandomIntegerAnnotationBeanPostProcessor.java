package org.borealis.scf.component;

import lombok.extern.slf4j.Slf4j;
import org.borealis.scf.component.annotation.RandomInteger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.data.util.Pair;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * @author Sergey Kastalski
 */
@Slf4j
@Component
public class RandomIntegerAnnotationBeanPostProcessor implements BeanPostProcessor {

	private final ConfigurableListableBeanFactory configurableListableBeanFactory;

	public RandomIntegerAnnotationBeanPostProcessor(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
		this.configurableListableBeanFactory = configurableListableBeanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		final Optional<String> originalBeanClassName = getOriginalBeanClassName(configurableListableBeanFactory, beanName);
		if (originalBeanClassName.isEmpty()) {
			return bean;
		}

		final Class<?> originalClass = ClassUtils.resolveClassName(originalBeanClassName.get(), ClassLoader.getSystemClassLoader());
		Arrays.stream(originalClass.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(RandomInteger.class))
				.map(field -> {
					final RandomInteger annotation = field.getAnnotation(RandomInteger.class);
					return Pair.of(field, annotation.min() + (new Random().nextInt(annotation.max() - annotation.min())));
				})
				.forEach(pair -> ReflectionUtils.setField(pair.getFirst(), bean, pair.getSecond()));
		return bean;
	}

	private Optional<String> getOriginalBeanClassName(final ConfigurableListableBeanFactory configurableListableBeanFactory, final String beanName) {
		if (!configurableListableBeanFactory.containsBean(beanName)) {
			return Optional.empty();
		}

		final BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
		final String originalBeanClassName = beanDefinition.getBeanClassName();
		if (originalBeanClassName == null) {
			return Optional.empty();
		}

		return Optional.of(originalBeanClassName);
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

}
