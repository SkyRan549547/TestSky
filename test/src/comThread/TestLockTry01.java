package comThread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sun.glass.ui.Timer;
import com.sun.jmx.snmp.Timestamp;

public class TestLockTry01 {

	private List<Object> list = new ArrayList<Object>();
	private Lock lock = new ReentrantLock();
	int num =0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final TestLockTry01 test = new TestLockTry01();
		test.test();
//		test.funTest();
		
		
		
//		new Thread("第一个线程  ") {
//
//			@Override
//			public void run() {
//				test.doSomething(Thread.currentThread());
//			}
//		}.start();
//
//		new Thread("第二个线程  ") {
//
//			@Override
//			public void run() {
//				test.doSomething(Thread.currentThread());
//			}
//		}.start();
//		// TODO Auto-generated method stub

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

	
	public void test(){
		
		
//		 ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("fengzheng" + "-%d").setDaemon(true).build();
//		ExecutorService executorService1 = Executors.newFixedThreadPool(10);
		ExecutorService executorService = new ThreadPoolExecutor(5, 10, 200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy() );
		ThreadPoolExecutor executorPool = (ThreadPoolExecutor)executorService;
		Date preDate = new Date(System.currentTimeMillis());
		System.out.println("first_time: "+preDate);
		executorService.submit(new Runnable() {
			public void run() {
				
				System.out.println("555555555");
			}
		});
		
		int a=15;
		for(int i=0; i<500; i++){
			executorService.submit(new Runnable() {
				public void run() {
					System.out.println("66666666: "+num++);
				}
			});
		}
//		executorService.execute(command);
		executorService.shutdown();
		Date curDate =null;
		while(Thread.activeCount()>1){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("=========activeCount======");
		}
		
		//线程池的线程数量。如果线程池不销毁的话，池里的线程不会自动销毁，
		//所以这个大小只增不+ getActiveCount：获取活动的线程数
		System.out.println("====getPoolSize()===="+executorPool.getPoolSize());
		//线程池需要执行的任务数量
		System.out.println("====getTaskCount()===="+executorPool.getTaskCount());
		//线程池在运行过程中已完成的任务数量。小于或等于taskCount
		System.out.println("====getCompletedTaskCount()===="+executorPool.getCompletedTaskCount());
		//线程池曾经创建过的最大线程数量。通过这个数据可以知道线程池是否满过。
		//如等于线程池的最大大小，则表示线程池曾经满了。
		System.out.println("====getLargestPoolSize()===="+executorPool.getLargestPoolSize());

		System.out.println("====getActiveCount()===="+executorPool.getActiveCount());
		
		
		
//		curDate= new Date(System.currentTimeMillis());
//		System.out.println("preDate: "+preDate);
//		System.out.println("curDate: "+curDate);
//		System.out.println("times: "+(curDate.getTime() -preDate.getTime()));
	}
	
	
	public void funTest(){
		
		for(int i=0 ;i <50; i++){
			new Thread(new Runnable() {
				
				public void run() {
					System.out.println("=====99999999999999999=====");
					
				}
			}).start();
		}
		
		while(Thread.activeCount()>1){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("=========activeCount======");
		}
		System.out.println("======777777777777777777777777777777777===");
		
	}
}
