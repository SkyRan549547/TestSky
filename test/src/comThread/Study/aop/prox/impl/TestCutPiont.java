package comThread.Study.aop.prox.impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TestCutPiont  implements MethodInterceptor{

	public Object invoke(MethodInvocation arg0) throws Throwable {
		System.out.println("========TestCutPiont=======");
		return arg0.proceed();
	}
}
