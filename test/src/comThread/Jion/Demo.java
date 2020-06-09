package comThread.Jion;

public class Demo {
	
	
	public void a(Thread jionThread){
		System.out.println("a方法执行了。。。。。");
		jionThread.start();
		try {
			jionThread.join();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

		System.out.println("a方法执行完毕了。。。。。");
	}
	
	
	public void b(){
		System.out.println("加塞线程执行了。。。。。");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("加塞线程执行完毕了。。。");
	}
	
	public static void main(String[] args) {
		
		final Demo de = new Demo();
		final Thread joinThread = new Thread(new Runnable() {
			public void run() {
				de.b();
			}
		});
		
		new Thread(new Runnable() {
			
			public void run() {
				de.a(joinThread);
				
			}
		}).start();
	}
}
