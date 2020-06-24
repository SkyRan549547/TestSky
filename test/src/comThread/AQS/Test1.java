package comThread.AQS;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {

//	private Lock lock1 = new ReentrantLock();
	
	private MyLock lock1 = new MyLock();


	public void a() {
		lock1.lock();
		System.out.println("..a.."+Thread.currentThread().getName());
		lock1.unlock();
	}

	public static void main(String[] args) {

		final Test1 t = new Test1();
		
		t.a();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 2; i++) {
					t.a();
					t.a();
				}
			}
		}).start();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 2; i++) {
					t.a();
				}
			}
		}).start();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 2; i++) {
					t.a();
				}
			}
		}).start();

	}
}
