package Treads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 转换执行器服务类
 * 
 * @author Administrator
 * 
 */
public class TransExecutorService {

	private final ExecutorService pool;

	private static TransExecutorService instance;
	// 线程池大小,即每次最多允许开启几个线程执行转换操作
	private static final int THREAD_SIZE = 3;

	public static TransExecutorService getInstance() {
		if (instance == null) {
			instance = new TransExecutorService();
		}
		return instance;
	}

	private TransExecutorService() {
		// pool = Executors.newCachedThreadPool();
		pool = Executors.newFixedThreadPool(THREAD_SIZE);// 线程池的创建长度为3
	}

	/**
	 * 开启新线程，执行转换操作
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
