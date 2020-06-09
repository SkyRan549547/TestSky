/**
 * 
 */
package SimpleFactory;


/**
 * @author Administrator
 *
 */
public class MyFactory {
	
	private static Student stu ;


	/**
	 * 
	 */
	public MyFactory() {	
		// TODO Auto-generated constructor stub
	}
	
	
	public Student makeStudent(String stuType)
	{
		if(null==stu){
			if("hightSchool".equals(stuType)){
				synchronized(HiSchoolStudent.class){
					if(null==stu){
						stu=(Student)new HiSchoolStudent();
						return stu;
					}else{
						return stu;
					}
				}
			}else if("collegeStudent".equals(stuType)){
				synchronized(CollegeStudent.class){
					if(null == stu){
						stu = (Student) new CollegeStudent();
						return stu;
					}else{
						return stu;
					}
				}
			}else
			{
				System.out.print("无法生成！");
				return null;
			}
		}else{
			return stu;
		}
		
	}
}
