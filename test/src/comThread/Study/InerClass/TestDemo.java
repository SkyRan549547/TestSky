package comThread.Study.InerClass;

public class TestDemo {
	public static void main(String[] args) {
		OutClass out = new OutClass();
		out.run();
	}

}

class OutClass {
	private String msg = "Hello world !";

	class InClass {
		private String name;
		private String id;

		public void print() {
			System.out.println(OutClass.this.msg);
		}
	}

	public void run() {
		new InClass().print();

	}

	public void say() {

	}

}
