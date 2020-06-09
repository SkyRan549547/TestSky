package comThread.AQS;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {

	private Lock lock1 = new ReentrantLock();

	public void a() {
		lock1.lock();
		System.out.println("..a..");
		lock1.unlock();
	}

	public static void main(String[] args) {

		final Test1 t = new Test1();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 2; i++) {
					t.a();
					System.out.println(Thread.currentThread().getName()
							+ ".....");
				}
			}
		}).start();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 2; i++) {
					t.a();
					System.out.println(Thread.currentThread().getName()
							+ ".....");
				}
			}
		}).start();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 2; i++) {
					t.a();
					System.out.println(Thread.currentThread().getName()
							+ ".....");
				}
			}
		}).start();

	}
}
