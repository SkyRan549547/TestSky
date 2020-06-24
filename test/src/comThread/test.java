package comThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import SimpleFactory.CollegeStudent;

public class test {

	private Lock lock = new ReentrantLock();
	public Condition condition = lock.newCondition();

	
	public static volatile int t = 0;
	
//	public synchronized static int getInt(){
//		return t ++;
//	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Thread[] threads = new Thread[100];
        for(int i = 0; i < 100; i++){
            //每个线程对t进行1000次加1的操作
            threads[i]= new Thread(new Runnable(){
                
                public void run(){
                    for(int j = 0; j < 1000; j++){
                    	t=t+1;
                    }
                }
            });
            threads[i].start();
        }

        //等待所有累加线程都结束
        while(Thread.activeCount() > 1){
            Thread.yield();
        }

        //打印t的值
        System.out.println(t);
		
		

        
        
		// CollegeStudent stu =new CollegeStudent();
		// MyRunnable tread =new MyRunnable(stu);
		// new Thread(tread).start();
		// System.out.print("---------------------------\n");
		// MyThread mthread =new MyThread();
		// mthread.run();

//		String str = "";
//		System.out.println("---------------------------" + str.length());
//
//		Singleton demo = Singleton.getInstance();
//		Singleton demo1 = Singleton.getInstance();
//		System.out.println("------------demo---------------" + demo);
//		System.out.println("------------demo1---------------" + demo1);

	}

	// 等待
	public void await() {
		try {
			lock.lock(); // 调用lock.lock()方法的线程就持有了"对象监视器"，其他线程只有等待锁被释放时再次争抢
			System.out.println("await()时间为：" + System.currentTimeMillis());
			condition.await();
			System.out.println("通知唤醒时间为：" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	// 通知
	public void signal() {
		try {
			lock.lock();
			System.out.println("signal()时间为：" + System.currentTimeMillis());
			condition.signal();
		} finally {
			lock.unlock();
		}
	}
}
