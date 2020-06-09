package comThread.Jion;

public class Demo {
	
	
	public void a(Thread jionThread){
		System.out.println("a����ִ���ˡ���������");
		jionThread.start();
		try {
			jionThread.join();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

		System.out.println("a����ִ������ˡ���������");
	}
	
	
	public void b(){
		System.out.println("�����߳�ִ���ˡ���������");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("�����߳�ִ������ˡ�����");
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
