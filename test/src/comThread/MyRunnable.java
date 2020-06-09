package comThread;

import SimpleFactory.Student;

public class MyRunnable implements Runnable {

	public MyRunnable(Student stu) {
		System.out.print("name = " + stu.getNameInfo() + " age = "
				+ stu.getAgeInfo() + "\n");

	}

	public void run() {
		System.out.print("hello word£¡£¡!");
		// TODO Auto-generated method stub

	}

}
