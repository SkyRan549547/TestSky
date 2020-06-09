package comThread.RewriteLock;

public class ReInto {

	private MyLock lock = new MyLock();

	public void a() {
		lock.lock();
		System.out.println("..a..");
		b();
		lock.unlock();
	}

	public void b() {
		lock.lock();
		System.out.println("..b..");
		c();
		lock.unlock();
	}

	public void c() {
		lock.lock();
		System.out.println("..c..");
		lock.unlock();
	}

	public static void main(String[] args) {
		final ReInto s = new ReInto();
		new Thread(new Runnable() {
			public void run() {
				s.a();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				s.b();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				s.c();
			}
		}).start();
	}

}
