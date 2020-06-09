package study.java8.lambda.clone;

import java.util.List;
import java.util.Vector;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Teacher tea = new Teacher();
		Vector<String> course = new Vector<String>();
		course.add("java");
		tea.setCourse(course);
		tea.setId("1");
		tea.setName("zhangSan");

		Teacher tea1 = tea.newInstance();
		tea1.setId("2");
		tea1.setName("Lisi");
		tea1.getCourse().add("C++");
		System.out.println("old:" + tea + "===new:" + tea1);
		System.out.println("old:" + tea.getId() + "===new:" + tea1.getId());
		System.out.println("old:" + tea.getName() + "===new:" + tea1.getName());
		System.out.println("old:" + tea.getCourse() + "===new:"
				+ tea1.getCourse());

	}

}
