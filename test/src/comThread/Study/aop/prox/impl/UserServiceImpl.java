package comThread.Study.aop.prox.impl;

import comThread.Study.aop.prox.inter.UserService;

public class UserServiceImpl implements UserService {

	public String getSinfo(String name) {
		System.out.println("======业务在这里执行========");
		return null;
	}

}
