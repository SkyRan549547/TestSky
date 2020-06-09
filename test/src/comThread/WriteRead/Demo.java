package comThread.WriteRead;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo {
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	private Lock rl = rwl.readLock();
	private Lock wl = rwl.writeLock();
	private Map<String, Object> map = new HashMap<String, Object>();

	public Object get(String key) {
		rl.lock();
		System.out.println(Thread.currentThread().getName() + "���������ڽ��С�����");
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return map.get(key);
		} finally {
			rl.unlock();
			System.out.println(Thread.currentThread().getName() + "�������Ѿ���ϡ�����");
		}
	}

	public void set(String key, Object value) {
		wl.lock();
		System.out.println(Thread.currentThread().getName() + "д�������ڽ��С�����");
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put(key, value);
		} finally {
			wl.unlock();
			System.out.println(Thread.currentThread().getName() + "д�����Ѿ���ϡ�����");
		}
	}

	public void set1(String key, Object value) {
		wl.lock();
		System.out.println(Thread.currentThread().getName() + "д�������ڽ���1������");
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put(key, value);
		} finally {
			wl.unlock();
			System.out
					.println(Thread.currentThread().getName() + "д�����Ѿ����1������");
		}
	}

}
