package comThread.Study.design.order.impl;

import comThread.Study.design.order.inter.Order;

public class AppleOrder implements Order {

	public boolean inValited(Integer... integers) {

		return false;
	}

	public boolean valited(Integer... integers) {
		System.out.println(integers.length);
		return false;
	}

	public boolean inValited(String... orderNums) {
		// TODO Auto-generated method stub
		return false;
	}

}
