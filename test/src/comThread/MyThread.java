package comThread;

import java.util.HashMap;
import java.util.Map;

import src.spring.java.com.StudentImpl;

public class MyThread extends Thread {

	public void run() {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("张三", "16");
		// map.put("李四", "17");
		// map.put("王五", "18");
		// map.put("马六", "19");
		// map.put("牛七", "20");
		// for(String key: map.keySet())
		// {
		// System.out.println("Key = " + key);
		// }
		// for(String value : map.values())
		// {
		// System.out.println("value = " + value);
		// }
		//		
		// for(Map.Entry<String, String> entry : map.entrySet())
		// {
		// System.out.println("Key = " + entry.getKey()+", value = " +
		// entry.getValue());
		// }
		// System.out.println("test = " + map); try
		try {
			for (int i = 0; i < 3; i++) {
				Thread.sleep((int) (Math.random() * 1000));
				System.out.println("run = " + Thread.currentThread().getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run(StudentImpl stu) {
		System.out.println("runStu = " + Thread.currentThread().getName());
		System.out.println("name = " + stu.getName() + "age = " + stu.getAge());

	}
}
