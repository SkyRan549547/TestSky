package src.spring.java.com;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class StudentImpl extends Object implements Student {

	String name;
	String num;
	int age;

	public String getAge(String name) {
		// Map<String, String> map = new HashMap<String, String>();
		// Iterator<Entry<String, String>> it = map.entrySet().iterator();
		// while(it.hasNext()){
		// // Map.Entry<String, String> entry = it.hasNext();
		// Entry entry = it.next();
		// entry.getValue();
		// entry.getKey();
		// }

		// Map map = new HashMap();
		// Iterator it = map.entrySet().iterator();
		// while(it.hasNext()){
		// // Map.Entry<String, String> entry = it.hasNext();
		// Entry entry = (Entry)it.next();
		// entry.getValue();
		// entry.getKey();
		// }

		// TODO Auto-generated method stub
		return null;
	}

	public String getNum(String name, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void Student() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object ob) {
		StudentImpl obj = (StudentImpl) ob;
		if (obj.getAge() == this.age && obj.getName().equals(this.name)
				&& obj.getNum().equals(this.num)) {

			return true;

		} else {
			return false;
		}
	}

	public int hashCode() {

		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
