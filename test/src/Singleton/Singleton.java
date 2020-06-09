package Singleton;

public class Singleton {

	// //����ģʽ ��ʽһ
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

	// //����ģʽ��
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

	// //����ģʽ��
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

	// //����ģʽ�ģ���̬�ڲ��෽ʽ��ֻ���౻ʵ��������getInstance()ʱ�Ż�װ�ؾ�̬�ࣩ
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

	// ����ģʽ�壨����ģʽ��
	private static Singleton singleton = new Singleton();

	public static Singleton getInstance() {
		return singleton;
	}

	// ����ģʽ��������ģʽ ��̬����飩
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
