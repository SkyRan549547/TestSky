package comThread.RewriteLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {

	private boolean islocked = false;

	private Thread lockBy = null;
	private int lockCount = 0;

	public synchronized void lock() {
		Thread currentThread = Thread.currentThread();
		while (islocked && currentThread != lockBy) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// if(islocked && currentThread!= lockBy){
		// try {
		// wait();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		islocked = true;
		lockBy = currentThread;
		lockCount++;
	}

	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized void unlock() {
		Thread currentThread = Thread.currentThread();
		if (currentThread == lockBy) {
			lockCount--;
			if (lockCount == 0) {
				islocked = false;
				notify();
			}
		}
	}

}
