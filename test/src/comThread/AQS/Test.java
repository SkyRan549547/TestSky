package comThread.AQS;

public class Test  extends Object{
	private MyLock lock =new MyLock();
	private MyLock lock1 =new MyLock();
	private int value =0;
	public int next(){
		lock.lock();
		try {
			Thread.sleep(300);
			return value++;
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}finally{
			lock.unlock();
		}
	}
	public void a(){
		lock.lock();
		System.out.println("..a..");
		b();
		lock.unlock();
	}
	
	
	public void b(){
		lock.lock();
		System.out.println("..b..");
		c();
		lock.unlock();
	}
	
	
	public void c(){
		lock.lock();
		System.out.println("..c..");
		lock.unlock();
	}
	
	
	public void a1(){
		lock1.lock();
		System.out.println("..........................");
		System.out.println("..a1..");
		b1();
		lock1.unlock();
	}
	
	
	public void b1(){
		lock1.lock();
		System.out.println("..b1..");
		b();
		c1();
		lock1.unlock();
	}
	
	public void c1(){
		lock1.lock();
		System.out.println("..c1..");
		c();
		lock1.unlock();
	}
	
	
	public void read(){
//		lock.lock();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print("......read......");
//		lock1.unlock();
	}
	
	
	public void write(){
		lock1.lock();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("......write......");
		lock.unlock();
	}
	
	public static void main(String[] args) {
		final Test t =new Test();
//		new Thread(new Runnable() {
//			public void run() {
//				t.a();
//			}
//		}).start();
//		
//		
//		new Thread(new Runnable() {
//			public void run() {
//				t.a1();
//			}
//		}).start();
		
		
//		try {
//			t.wait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		t.notify();
		
		
		new Thread(new Runnable() {
			public void run() {
				t.read();
			}
		}).start();
		System.out.print("1231..");
		
//		new Thread(new Runnable() {
//			public void run() {
//				t.write();
//			}
//		}).start();
//		


//		new Thread(new Runnable() {
//			public void run() {
//				for(int a=0;a <20;a++){
//					System.out.println(Thread.currentThread().getName()+"..."+t.next());
//				}
//			}
//		}).start();
//		
//		new Thread(new Runnable() {
//			public void run() {
//				for(int a=0;a <20;a++){
//					System.out.println(Thread.currentThread().getName()+"..."+t.next());
//				}
//			}
//		}).start();
//		
//		new Thread(new Runnable() {
//			public void run() {
//				for(int a=0;a <20;a++){
//					System.out.println(Thread.currentThread().getName()+"..."+t.next());
//				}
//			}
//		}).start();
		
		
	}
}
