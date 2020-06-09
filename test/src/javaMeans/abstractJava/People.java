package javaMeans.abstractJava;

public abstract class People {
	private int age;

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public abstract String getName();

	public void printf() {
		getName();
	}
}
