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
		new Thread("��һ���߳�  ") {

			@Override
			public void run() {
				test.doSomething(Thread.currentThread());
			}
		}.start();

		new Thread("�ڶ����߳�  ") {

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
				System.out.println(thread.getName() + "�õ�����.");
				for (int i = 0; i < 10; i++) {
					list.add(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println(thread.getName() + "�ͷ�����.");
				lock.unlock();
			}
		} else {
			System.out.println(thread.getName() + "��ȡ��ʧ��.");
		}
	}

	// LOCK.lock(): �˷�ʽ��ʼ�մ��ڵȴ��У���ʹ����B.interrupt()Ҳ�����жϣ������߳�A����LOCK.unlock()�ͷ�����
	//
	//
	// LOCK.lockInterruptibly():
	// �˷�ʽ��ȴ�����������B.interrupt()�ᱻ�жϵȴ������׳�InterruptedException�쳣���������lock()һ��ʼ�մ��ڵȴ��У�ֱ���߳�A�ͷ�����
	//
	//
	// LOCK.tryLock(): �ô�����ȴ�����ȡ��������ֱ�ӷ���false��ȥִ��������߼���
	//
	//
	// LOCK.tryLock(10,
	// TimeUnit.SECONDS)���ô�����10��ʱ���ڴ��ڵȴ��У���������B.interrupt()�ᱻ�жϵȴ������׳�InterruptedException��10��ʱ��������߳�A�ͷ��������ȡ����������true������10�������ȡ������������false��ȥִ��������߼���

}
