package DataStruture;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class myLinkedList {

	public void scanfLinkedList() {
		LinkedList<String> linkList = new LinkedList<String>();
		for (int i = 0; i < 100000; i++) {
			linkList.add("1111111");
			linkList.addFirst("0000000");
			linkList.addLast("99999999");
			linkList.addFirst("2222222");
			linkList.addLast("888888888");
			linkList.add("5555555");
			linkList.add("666666666");
		}
		Iterator<String> its = linkList.iterator();
		System.out.println("**********time1************"
				+ (new Date()).getTime());
		while (its.hasNext()) {
			Object ob = its.next();
			// System.out.println(ob+"=======");
		}
		System.out.println("***********time2***********"
				+ (new Date()).getTime());
		for (String str : linkList) {
			// System.out.println("**********************");
			// System.out.println(str+"=======");
			String reStr = str;
		}
		System.out.println("***********time3***********"
				+ (new Date()).getTime());
	}

	public void scanfMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 100000; i++) {
			map.put("stock_" + i, "1");
			map.put("trans_" + i, "1");
			map.put("pack_" + i, "1");
			map.put("item_" + i, "1");
			map.put("plan_" + i, "1");
			map.put("fee_" + i, "1");
			map.put("invoice_" + i, "1");
		}

		Iterator it = map.entrySet().iterator();
		Entry result;
		String value;
		System.out.println("**********time7************"
				+ (new Date()).getTime());

		while (it.hasNext()) {
			result = (Entry) it.next();
		}
		System.out.println("**********time8************"
				+ (new Date()).getTime());

		for (Entry entry : map.entrySet()) {
			value = (String) entry.getValue();
		}
		System.out.println("**********time9************"
				+ (new Date()).getTime());
	}

	public void scanfHashtable() {
		Hashtable<String, String> table = new Hashtable<String, String>();
		for (int i = 0; i < 100000; i++) {
			table.put("stock_" + i, "1");
			table.put("trans_" + i, "1");
			table.put("pack_" + i, "1");
			table.put("item_" + i, "1");
			table.put("plan_" + i, "1");
			table.put("fee_" + i, "1");
			table.put("invoice_" + i, "1");
		}

		Iterator it = table.entrySet().iterator();
		System.out.println("**********time4************"
				+ (new Date()).getTime());

		while (it.hasNext()) {
			Entry<String, Object> result = (Entry<String, Object>) it.next();
		}
		System.out.println("**********time5************"
				+ (new Date()).getTime());

		for (Entry<String, String> entry : table.entrySet()) {
			String value = (String) entry.getValue();
		}
		System.out.println("**********time6************"
				+ (new Date()).getTime());

	}
}
