package Treads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ת��ִ����������
 * 
 * @author Administrator
 * 
 */
public class TransExecutorService {

	private final ExecutorService pool;

	private static TransExecutorService instance;
	// �̳߳ش�С,��ÿ����������������߳�ִ��ת������
	private static final int THREAD_SIZE = 3;

	public static TransExecutorService getInstance() {
		if (instance == null) {
			instance = new TransExecutorService();
		}
		return instance;
	}

	private TransExecutorService() {
		// pool = Executors.newCachedThreadPool();
		pool = Executors.newFixedThreadPool(THREAD_SIZE);// �̳߳صĴ�������Ϊ3
	}

	/**
	 * �������̣߳�ִ��ת������
	 * 
	 * @param info
	 */
	public void execute(ResourceInfo info) {
		try {
			pool.submit(new TransformExecutor(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void shutdown() {
		pool.shutdown();
	}
}
