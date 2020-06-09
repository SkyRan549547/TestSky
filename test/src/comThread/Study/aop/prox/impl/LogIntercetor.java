package comThread.Study.aop.prox.impl;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class LogIntercetor implements MethodBeforeAdvice {

	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		System.out.println("==========»’÷æ÷¥––==============");

	}

}
