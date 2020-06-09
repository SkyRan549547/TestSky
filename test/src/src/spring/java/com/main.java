package src.spring.java.com;

import java.util.HashMap;
import java.util.Map;

import comThread.MyThread;
import comThread.Resource;
import comThread.myLockTest;

public class main {

	public main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Student stud = new StudentImpl();
		// System.out.println("Key1 = " + stud);
		// stud.getAge("18");
		// System.out.println("Key = " + "123456");
		// TODO Auto-generated method stub

		// Student stud = new StudentImpl();
		System.out.print("this is a test !");
		MyThread thread = new MyThread();
		Thread.sleep(0);
		thread.start();
		thread.run();
		// try{
		// for(int i =0; i<5; i++){
		// Thread.sleep((int)(Math.random() * 1000));
		// System.out.println("sleep = " + (int)(Math.random() * 1000));
		// System.out.println("run = " + Thread.currentThread().getName());
		// }
		// }
		// catch(InterruptedException e)
		// {
		// e.printStackTrace();
		// }
		//		
		//		
		// MyThread thread = new MyThread();
		// Resource re = new Resource();
		// myLockTest mLock = new myLockTest(re,thread);
		// mLock.run();

	}

	public void getInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("张三", "16");
		map.put("李四", "17");
		map.put("王五", "18");
		map.put("马六", "19");
		map.put("牛七", "20");
		for (String key : map.keySet()) {
			System.out.println("Key = " + key);
		}
		for (String value : map.values()) {
			System.out.println("value = " + value);
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", value = "
					+ entry.getValue());
		}
		System.out.println("test = " + map);
	}

	public static boolean isFinish = false;
	public static Object object = new Object();

	public void test() {

		final Thread download = new Thread() {
			public void run() {
				System.out.println("download:开始下载图片");
				for (int i = 0; i <= 100; i++) {
					System.out.println("download:已完成" + i + "%");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				System.out.println("download:图片下载完毕");
				isFinish = true;// 表示图片下载完毕了

				// 当图片下载完毕后，就可以通知showImg开始显示图片了
				synchronized (object) {
					// 通知在object身上等待的线程解除等待阻塞
					object.notify();
				}

				System.out.println("download:开始下载附件");
				for (int i = 0; i <= 100; i++) {
					System.out.println("download:已完成" + i + "%");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				System.out.println("download:附件下载完毕");
			}

		};

		// 用于显示图片的线程
		Thread showImg = new Thread() {
			public void run() {
				System.out.println("show:准备显示图片");
				// 等待下载线程将图片下载结束后，再执行下面的代码
				try {
					// wait()阻塞会在以下两种情况被解除,1:当download线程结束.
					// 2:当调用了download的notify()
					synchronized (object) {
						object.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!isFinish) {
					throw new RuntimeException("图片没有下载完毕");
				}
				System.out.println("show:图片已经显示了!");
			}
		};

		download.start();
		showImg.start();
	}

	public void readInfo() {
		System.out.print("this is a test !");
	}
}
