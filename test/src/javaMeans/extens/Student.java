package javaMeans.extens;

import javaMeans.abstractJava.People;

public class Student extends People {

	@Override
	public String getName() {
		// TODO Auto-generated method stub

		System.out.println("����ѧ����ʵ�ֵģ�����������");
		return null;
	}

	public void getStudent() {
		String name = setStudent("��̬�����֣�");
		System.out.println("====" + name + "======");
	}

	public String setStudent(String name) {
		String rName = "my name is WangWu";
		name = "WangWu";
		return rName;
	}
}
