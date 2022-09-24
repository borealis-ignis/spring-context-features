package org.borealis.scf.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.borealis.scf.component.mbean.ProfilingController;
import org.borealis.scf.exception.MBeanRegisterException;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author Sergey Kastalski
 */
@Slf4j
@Component
@Aspect
public class ProfilingAnnotationAspect {

	private final ProfilingController profilingController;

	public ProfilingAnnotationAspect(final ProfilingController profilingController) {
		this.profilingController = profilingController;
		final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try {
			mBeanServer.registerMBean(profilingController, new ObjectName("profiling", "name", "controller"));
		} catch (final InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e) {
			throw new MBeanRegisterException("Can't register MBean " + profilingController.getClass().getSimpleName(), e);
		}
	}

	@Around("@annotation(org.borealis.scf.component.annotation.Profiling)")
	public Object methodProfiling(final ProceedingJoinPoint joinPoint) throws Throwable {
		if (!profilingController.isEnabled()) {
			return joinPoint.proceed();
		}
		final String methodClassName = joinPoint.getSignature().getDeclaringType().getSimpleName();
		final String methodName = joinPoint.getSignature().getName();
		final long start = System.nanoTime();
		final Object result = joinPoint.proceed();
		final long end = System.nanoTime();
		log.info("Processing time of " + methodClassName + "." + methodName + "(): " + (end - start) / 1000000 + "ms");
		return result;
	}

}
