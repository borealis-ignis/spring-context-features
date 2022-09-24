package org.borealis.scf.component.listener;

/**
 * @author Sergey Kastalski
 */
@FunctionalInterface
interface SpecificListenerProcessor {

	void process(String originalBeanClassName, Object beanObject) throws Exception;

}
