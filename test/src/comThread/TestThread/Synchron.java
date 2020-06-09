package comThread.TestThread;

public class Synchron implements Runnable {

	private int ticket = 50;

	public void run() {
		for (int i = 0; i < 200; i++) {
			synchronized (this) {
				get();
				// if(ticket >0){
				// try {
				// Thread.sleep(100);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// System.out.println(Thread.currentThread().getName()+"ÂòÆ±£¬ticket£º"+ticket--);
				// }
			}
		}
	}

	public synchronized void get() {
		if (ticket > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "ÂòÆ±£¬ticket£º"
					+ ticket--);
		}
	}

}
