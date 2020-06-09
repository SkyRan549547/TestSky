package javaMeans.main;

import javaMeans.abstractJava.People;
import javaMeans.extens.HighSchoolStudent;
import javaMeans.extens.Student;
import javaMeans.impl.MiddleSchoolTeacher;
import javaMeans.impl.Teacher;
import javaMeans.interfaces.Doctor;
import javaMeans.interfaces.person;

public class main {

	public main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Student st = new Student();
////		st.printf();
//		HighSchoolStudent stu = new HighSchoolStudent();
////		stu.printf();
//		st.getStudent();
//		stu.getStudent();
//		person per1= new MiddleSchoolTeacher();
//		person per2= new Teacher();

		
		
		
//		Teacher tea = new Teacher();
//		MiddleSchoolTeacher t = new MiddleSchoolTeacher();
//		tea.getGrade();
//		t.getGrade();
		
		person per1 = new Teacher();
		person per2 = new MiddleSchoolTeacher();
		per1.getGrade();
		per2.getGrade();
		
		
	}

}
