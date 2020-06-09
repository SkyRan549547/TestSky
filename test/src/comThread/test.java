package comThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import SimpleFactory.CollegeStudent;

public class test {

	private Lock lock = new ReentrantLock();
	public Condition condition = lock.newCondition();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// CollegeStudent stu =new CollegeStudent();
		// MyRunnable tread =new MyRunnable(stu);
		// new Thread(tread).start();
		// System.out.print("---------------------------\n");
		// MyThread mthread =new MyThread();
		// mthread.run();

		String str = "";
		System.out.println("---------------------------" + str.length());

		Singleton demo = Singleton.getInstance();
		Singleton demo1 = Singleton.getInstance();
		System.out.println("------------demo---------------" + demo);
		System.out.println("------------demo1---------------" + demo1);

	}

	// �ȴ�
	public void await() {
		try {
			lock.lock(); // ����lock.lock()�������߳̾ͳ�����"���������"�������߳�ֻ�еȴ������ͷ�ʱ�ٴ�����
			System.out.println("await()ʱ��Ϊ��" + System.currentTimeMillis());
			condition.await();
			System.out.println("֪ͨ����ʱ��Ϊ��" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	// ֪ͨ
	public void signal() {
		try {
			lock.lock();
			System.out.println("signal()ʱ��Ϊ��" + System.currentTimeMillis());
			condition.signal();
		} finally {
			lock.unlock();
		}
	}
}
