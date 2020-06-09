import Teacher;
public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Student stu = new Student();
		stu.setAge(18);
		stu.setGrade(95.2);
		stu.setGroupNum("3");
		stu.setHeight(175);
		stu.setName("ÕÅÈı");
		stu.setRunking(12);
		Teacher tea = new Teacher();
		BeanUtils.Copy(stu, tea);
		System.out.println("==Ãû×Ö=="+tea.getName());
	}

}
