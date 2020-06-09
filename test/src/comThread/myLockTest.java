package comThread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class myLockTest implements Runnable {

	private Lock lock;
	private Resource resource;
	private MyThread thread;

	public myLockTest(Resource r, MyThread thred) {
		lock = new ReentrantLock();
		resource = r;
		thread = thred;
	}

	public void run() {

		try {
			if (lock.tryLock(10, TimeUnit.SECONDS)) {
				resource.dosomething();
				thread.start();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		// TODO Auto-generated method stub
	}

}
