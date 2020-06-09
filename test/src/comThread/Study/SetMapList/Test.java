package comThread.Study.SetMapList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List list = new ArrayList();
		List vec = new Vector<Object>();
		Vector<Object> vecs = new Vector<Object>();
		Collections cons;
		Set<String> set = new HashSet<String>();
		set.add("zhangsan");
		set.add("Lisi");
		set.add("Wangwu");
		set.add("Wangwu");
		set.add("Xiaoming");
		set.add("Xiaozhang");
		System.out.println(set);

		Set<User> setUser = new HashSet<User>();

		User user1 = new User();
		user1.setName("zhangsan");
		user1.setAge(18);
		User user2 = new User();
		user2.setName("zhangsan");
		user2.setAge(18);
		User user3 = new User();
		user3.setName("zhangsan");
		user3.setAge(18);
		User user4 = new User();
		user4.setName("zhangsan");
		user4.setAge(18);
		setUser.add(user1);
		setUser.add(user2);
		setUser.add(user3);
		setUser.add(user4);
		System.out.println("userSet: " + setUser);

		// Iterator<Object> itVec=vec.iterator();
		// Enumeration<Object> enu = vecs.elements();
		// while(enu.hasMoreElements()){
		// enu.nextElement();
		// }
		//		
		System.out.println("===========================");
		Map map = new HashMap();
		map.put("111", "Xiaoming");
		map.put("222", "Xiaohong");
		map.put("333", "Zhangsan");
		Iterator<Object> it = map.keySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

	}

}
