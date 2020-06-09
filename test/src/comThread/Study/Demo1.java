package comThread.Study;

import java.util.Random;

public class Demo1 {
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"线程执行中。。。。。。。");
				try {
					Thread.sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程执行完毕。。。。。。");
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"线程执行中。。。。。。。");
				try {
					Thread.sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程执行完毕。。。。。。");
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"线程执行中。。。。。。。");
				try {
					Thread.sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程执行完毕。。。。。。");
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"线程执行中。。。。。。。");
				try {
					Thread.sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程执行完毕。。。。。。");
			}
		}).start();
		
		while(Thread.activeCount()!=1){
//			System.out.println("。。。Thread.activeCount。。");
		}
		System.out.println("执行完毕了。。。");
//		if(Thread.activeCount()==1){
//			System.out.println("执行完毕了。。。");
//		}
	}
}
