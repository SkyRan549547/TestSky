/**
 * 
 */
package SimpleFactory;

/**
 * @author Administrator
 * 
 */
public class HiSchoolStudent implements Student {

	/**
	 * 
	 */
	public HiSchoolStudent() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SimpleFactory.student#getAgeInfo()
	 */
	public String getAgeInfo() {
		System.out.print("年龄是15岁\n");
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SimpleFactory.student#getNameInfo()
	 */
	public String getNameInfo() {
		System.out.print("名字是李四\n");
		// TODO Auto-generated method stub
		return null;
	}

}
