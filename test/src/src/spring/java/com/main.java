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
		map.put("����", "16");
		map.put("����", "17");
		map.put("����", "18");
		map.put("����", "19");
		map.put("ţ��", "20");
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
				System.out.println("download:��ʼ����ͼƬ");
				for (int i = 0; i <= 100; i++) {
					System.out.println("download:�����" + i + "%");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				System.out.println("download:ͼƬ�������");
				isFinish = true;// ��ʾͼƬ���������

				// ��ͼƬ������Ϻ󣬾Ϳ���֪ͨshowImg��ʼ��ʾͼƬ��
				synchronized (object) {
					// ֪ͨ��object���ϵȴ����߳̽���ȴ�����
					object.notify();
				}

				System.out.println("download:��ʼ���ظ���");
				for (int i = 0; i <= 100; i++) {
					System.out.println("download:�����" + i + "%");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				System.out.println("download:�����������");
			}

		};

		// ������ʾͼƬ���߳�
		Thread showImg = new Thread() {
			public void run() {
				System.out.println("show:׼����ʾͼƬ");
				// �ȴ������߳̽�ͼƬ���ؽ�������ִ������Ĵ���
				try {
					// wait()������������������������,1:��download�߳̽���.
					// 2:��������download��notify()
					synchronized (object) {
						object.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!isFinish) {
					throw new RuntimeException("ͼƬû���������");
				}
				System.out.println("show:ͼƬ�Ѿ���ʾ��!");
			}
		};

		download.start();
		showImg.start();
	}

	public void readInfo() {
		System.out.print("this is a test !");
	}
}
