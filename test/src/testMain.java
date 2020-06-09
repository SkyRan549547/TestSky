import java.lang.reflect.Field;


public class testMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * @param args
		 */
		Student stu = new Student();
//		stu.setAge(18);
//		stu.setGrade(95.2);
//		stu.setGroupNum("3");
//		stu.setHeight(175);
//		stu.setName("张三");
//		stu.setRunking(12);
//		Teacher tea = new Teacher();
//		BeanUtils.Copy(stu, tea);
//		System.out.println("==名字=="+tea.getName());
//		
		
		Class<?> clas = stu.getClass();
		Field[] fie = clas.getDeclaredFields();
		for(int i =0; i<fie.length; i++){
			System.out.println("==属性类型=="+fie[i].getGenericType());
			System.out.println("==属性类型=="+fie[i].getName());
		}
	}

}
