/**
 * 
 */
package SimpleFactory;

/**
 * @author Administrator
 *
 */
public class CollegeStudent implements Student {

	/* (non-Javadoc)
	 * @see SimpleFactory.student#getAgeInfo()
	 */
	public String getAgeInfo() {
		System.out.print("年龄是18岁\n");
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see SimpleFactory.student#getNameInfo()
	 */
	public String getNameInfo() {
		System.out.print("名字是张三\n");
		// TODO Auto-generated method stub
		return null;
	}

}
