package comThread.Study;

import java.util.LinkedList;

public class Demo2 {

	public volatile static int result = 1;

	public void test() {
		LinkedList<String> link = new LinkedList<String>();

	}

	// public static int getResult() {
	// return result;
	// }
	//
	//
	//
	// public static void setResult(int result) {
	// Demo2.result = result;
	// }

	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("执行了" + i + "次");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					result = 2;
					System.out.println("执行中......" + result);
				}
				// System.out.println("执行中......"+result);
				// result =2;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				System.out.println("执行完成......" + result);
				// while(!result){
				// System.out.println("执行完成......"+result);
				// }
				System.out.println("执行完成......");
			}
		}).start();
	}

}
