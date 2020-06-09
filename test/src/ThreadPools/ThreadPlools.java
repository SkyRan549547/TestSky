package ThreadPools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

public class ThreadPlools {

	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20, 10, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(10), new DiscardOldestPolicy());
		for(int i=0; i<100; i++){
				threadPool.execute(new Runnable() {
					public void run() {
						System.out.println("...."+ Thread.currentThread().getName());
					}
				}); 
			}
		threadPool.shutdown();
	}
}
