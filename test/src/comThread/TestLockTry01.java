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
		
		
		
//		new Thread("��һ���߳�  ") {
//
//			@Override
//			public void run() {
//				test.doSomething(Thread.currentThread());
//			}
//		}.start();
//
//		new Thread("�ڶ����߳�  ") {
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
		
		//�̳߳ص��߳�����������̳߳ز����ٵĻ���������̲߳����Զ����٣�
		//���������Сֻ����+ getActiveCount����ȡ����߳���
		System.out.println("====getPoolSize()===="+executorPool.getPoolSize());
		//�̳߳���Ҫִ�е���������
		System.out.println("====getTaskCount()===="+executorPool.getTaskCount());
		//�̳߳������й���������ɵ�����������С�ڻ����taskCount
		System.out.println("====getCompletedTaskCount()===="+executorPool.getCompletedTaskCount());
		//�̳߳�����������������߳�������ͨ��������ݿ���֪���̳߳��Ƿ�������
		//������̳߳ص�����С�����ʾ�̳߳��������ˡ�
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
