package comThread.Study;

import java.io.IOException;

import javax.naming.ReferralException;

public class Test {

	private static final boolean IOException = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Test t = new Test();
		t.save();

		String str1 = new String("asdf");
		String str2 = "as" + new String("df");
		// System.out.println("asdf"=="asdf");
		String str3 = new String("asdf");
		System.out.println(str1);
		System.out.println(str2);
		System.out.println(str3 == str2);
	}

	public void say() {
		try {

		} catch (Exception e) {
			System.out.println("Exception");

			try {
				System.out.println();
			} catch (IOException e1) {
				System.out.println();

			}
			try {

			} catch (ReferralException e2) {
				System.out.println("ReferralException");

			}
		}
	}

	public void save() {
		// Chilldren dh = new Chilldren("zhangsan ..");
		Chilldren dh = new Chilldren();

		// Man man =new Man("1234");
	}

	public class Man {
		public String name;

		public Man() {
			System.out.println("....1...");
		}

		public Man(String name) {
			System.out.println("....2...");
			this.name = name;
		}
	}

	public class Chilldren extends Man {
		public Chilldren() {
			System.out.println("....3...");
		}

		public Chilldren(String name) {
			System.out.println("....4...");
			this.name = name + "childdren";
			new Man("childdren..");
		}
	}
}
