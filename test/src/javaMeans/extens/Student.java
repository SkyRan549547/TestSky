package javaMeans.extens;

import javaMeans.abstractJava.People;

public class Student extends People {

	@Override
	public String getName() {
		// TODO Auto-generated method stub

		System.out.println("我是学生类实现的！！！！！！");
		return null;
	}

	public void getStudent() {
		String name = setStudent("多态的体现！");
		System.out.println("====" + name + "======");
	}

	public String setStudent(String name) {
		String rName = "my name is WangWu";
		name = "WangWu";
		return rName;
	}
}
