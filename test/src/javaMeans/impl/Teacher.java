package javaMeans.impl;

import javaMeans.interfaces.person;

public class Teacher implements person {

	public int get() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAge(int age) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getGrade() {
		String name = setName();
		System.out.println("==========" + name + "============");
	}

	public String setName() {
		// TODO Auto-generated method stub
		return "hello world!!";
	}

}
