package comThread.Study.aop.prox.impl;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import comThread.Study.aop.prox.inter.UserService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		UserServiceImpl serviceImpl = new UserServiceImpl();
		ProxyFactory factory = new ProxyFactory(serviceImpl);
		factory.addAdvice(new TestCutPiont());
		factory.addAdvisor(new DefaultPointcutAdvisor(new LogIntercetor()));
		UserService service = (UserService) factory.getProxy();
		service.getSinfo("zhangsan");
	}

}
