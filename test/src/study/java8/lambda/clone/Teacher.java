package study.java8.lambda.clone;

import java.util.Vector;

import study.java8.lambda.apple.Apple;

public class Teacher implements Cloneable{

	private String id;
	private String name;
	private Vector course;
	private Apple apple;
	
	public Teacher(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public Vector getCourse() {
		return course;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCourse(Vector course) {
		this.course = course;
	}
	
	
	
	
	/**
	 * @return the apple
	 */
	public Apple getApple() {
		return apple;
	}



	/**
	 * @param apple the apple to set
	 */
	public void setApple(Apple apple) {
		this.apple = apple;
	}



	public Teacher newInstance(){
		try {
			return (Teacher)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
