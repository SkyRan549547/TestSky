package comThread.Study.design.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import comThread.Study.design.order.impl.AppleOrder;
import comThread.Study.design.order.inter.Order;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Integer orderNum1 = 123156;
		Integer orderNum2 = 123156;
		String[] strs = new String[6];
		Integer[] orderNums = new Integer[3];
		Order order = new AppleOrder();
		order.valited(orderNum1);
		order.valited(orderNum1, orderNum2);
		order.valited(orderNums);

	}

}
