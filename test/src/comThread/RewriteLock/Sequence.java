package comThread.RewriteLock;

public class Sequence {
	private MyLock lock = new MyLock();
	private int value;

	public int getNext() {
		lock.lock();
		value++;
		lock.unlock();
		return value;
	}

	public static void main(String[] args) {

		final Sequence sq = new Sequence();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					System.out.println("...结果为：..." + sq.getNext());
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					System.out.println("...结果为：..." + sq.getNext());
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					System.out.println("...结果为：..." + sq.getNext());
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					System.out.println("...结果为：..." + sq.getNext());
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					System.out.println("...结果为：..." + sq.getNext());
				}
			}
		}).start();
	}
}
