package javatest;

public class TestJava {

	
	
	public void changeValues(Car car){
		car.setName("����");
		car.setNum("No123456789");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestJava testJava = new TestJava();
		Person person1 = new Person();
		Car car = new Car();
		car.setId(123456);
		car.setName("����ϣ");
		car.setNum("No99999999999");
		car.setPerson(person1);
		testJava.changeValues(car);
		String name = car.getName();
		Person person2 =car.getPerson();
		System.out.println("===person1===="+car.getPerson());

		person2 = new Person();
		name = "·��";
		System.out.println("===person2===="+car.getPerson());
		System.out.println("======Name====="+car.getName());
	}

}
