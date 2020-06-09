package Singleton;

public class Singleton {

	// //单利模式 方式一
	// private static Singleton singleton;
	//
	// private Singleton() {}
	//
	// public static synchronized Singleton getInstance() {
	// if (singleton == null) {
	// singleton = new Singleton();
	// }
	// return singleton;
	// }

	// //单例模式二
	// private static Singleton singleton;
	//
	// private Singleton() {}
	//
	// public static Singleton getInstance() {
	// if (singleton == null) {
	// synchronized (Singleton.class) {
	// singleton = new Singleton();
	// }
	// }
	// return singleton;
	// }

	// //单利模式三
	// private static volatile Singleton singleton;
	//
	// private Singleton() {}
	//
	// public static Singleton getInstance() {
	// if (singleton == null) {
	// synchronized (Singleton.class) {
	// if (singleton == null) {
	// singleton = new Singleton();
	// }
	// }
	// }
	// return singleton;
	// }

	// //单利模式四（静态内部类方式，只有类被实例化调用getInstance()时才会装载静态类）
	//	
	// private Singleton() {}
	//	
	// private static class SingletonInstance {
	// private static final Singleton INSTANCE = new Singleton();
	// }
	//	
	// public static Singleton getInstance() {
	// return SingletonInstance.INSTANCE;
	// }

	// 单例模式五（饿汉模式）
	private static Singleton singleton = new Singleton();

	public static Singleton getInstance() {
		return singleton;
	}

	// 单例模式六（饿汉模式 静态代码块）
	private static Singleton instance;

	static {
		instance = new Singleton();
	}

	private Singleton() {
	}

	public static Singleton getInstance() {
		return instance;
	}
}
