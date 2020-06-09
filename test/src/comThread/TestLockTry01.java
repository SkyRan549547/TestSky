package comThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockTry01 {

	private List<Object> list = new ArrayList<Object>();
	private Lock lock = new ReentrantLock();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final TestLockTry01 test = new TestLockTry01();
		new Thread("第一个线程  ") {

			@Override
			public void run() {
				test.doSomething(Thread.currentThread());
			}
		}.start();

		new Thread("第二个线程  ") {

			@Override
			public void run() {
				test.doSomething(Thread.currentThread());
			}
		}.start();
		// TODO Auto-generated method stub

	}

	public void doSomething(Thread thread) {
		if (lock.tryLock()) {
			try {
				System.out.println(thread.getName() + "得到了锁.");
				for (int i = 0; i < 10; i++) {
					list.add(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println(thread.getName() + "释放了锁.");
				lock.unlock();
			}
		} else {
			System.out.println(thread.getName() + "获取锁失败.");
		}
	}

	// LOCK.lock(): 此方式会始终处于等待中，即使调用B.interrupt()也不能中断，除非线程A调用LOCK.unlock()释放锁。
	//
	//
	// LOCK.lockInterruptibly():
	// 此方式会等待，但当调用B.interrupt()会被中断等待，并抛出InterruptedException异常，否则会与lock()一样始终处于等待中，直到线程A释放锁。
	//
	//
	// LOCK.tryLock(): 该处不会等待，获取不到锁并直接返回false，去执行下面的逻辑。
	//
	//
	// LOCK.tryLock(10,
	// TimeUnit.SECONDS)：该处会在10秒时间内处于等待中，但当调用B.interrupt()会被中断等待，并抛出InterruptedException。10秒时间内如果线程A释放锁，会获取到锁并返回true，否则10秒过后会获取不到锁并返回false，去执行下面的逻辑。

}
