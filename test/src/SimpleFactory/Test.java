/**
 * 
 */
package SimpleFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Administrator
 * 
 */
public class Test {

	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// MyFactory fac = new MyFactory();
		// Student hightStudent =fac.makeStudent("hightSchool");
		// hightStudent.getNameInfo();
		// hightStudent.getAgeInfo();
		// Student collegeStudent =fac.makeStudent("collegeStudent");
		// collegeStudent.getNameInfo();
		// collegeStudent.getAgeInfo();
		// // TODO Auto-generated method stub
		// Test test = new Test();
		// test.hashMapTest();
		String str = "yyyy-MM-dd HH:mm:ss";
		System.out.println("===³¤¶È====" + str.length());
	}

	public void hashMapTest() {
		Map map = new HashMap();
		map.put("name", "zhangSan");
		map.put("age", "25");
		map.put("school", "FirstMidSchool");
		// Iterator it = map.keySet().iterator();
		Iterator it = map.values().iterator();
		while (it.hasNext()) {
			System.out.println("=====" + it.next());
		}
	}

}
