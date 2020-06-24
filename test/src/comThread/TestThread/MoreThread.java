package comThread.TestThread;

import comThread.javaBean.UserInfo;

public class MoreThread {

	private int value;

	public synchronized int getNext() {
		return value++;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Synchron thread = new Synchron();
		// new Thread(thread,"Æ±··×ÓA").start();
		// new Thread(thread,"Æ±··×ÓB").start();
		// new Thread(thread,"Æ±··×ÓC").start();
		// new Thread(thread,"Æ±··×ÓD").start();

		// UserInfo info = new UserInfo();
		// SynchProduct syncp= new SynchProduct(info);
		// Thread t1 = new Thread(syncp);
		// Thread t2 = new Thread(syncp);
		// t1.start();
		// t2.start();
		UserInfo info = new UserInfo();

		for(int i =0 ;i<6; i++){
			Thread t1 = new Thread(new SynchProduct(info));
			t1.start();
		}
		
//		Thread t2 = new Thread(new SynchCustomer(info));
		// t1.setPriority(Thread.MIN_PRIORITY);
		// t2.setPriority(Thread.MAX_PRIORITY);
		// t1.setDaemon(false);
//		t2.start();

		// final MoreThread s = new MoreThread();
		// new Thread(new Runnable(){
		//
		// public void run() {
		// int i =0;
		// while(i<100){
		// System.out.println(Thread.currentThread().getName()+
		// ".."+s.getNext()); i++;
		// }
		// }
		//			
		// }).start();
		//		
		// new Thread(new Runnable(){
		//
		// public void run() {
		// int i =0;
		// while(i<100){
		// System.out.println(Thread.currentThread().getName()+
		// ".."+s.getNext());
		// i++;
		// }
		// }
		//			
		// }).start();
		//		
		// new Thread(new Runnable(){
		//
		// public void run() {
		// int i =0;
		// while(i<100){
		// System.out.println(Thread.currentThread().getName()+
		// ".."+s.getNext()); i++;
		// }
		// }
		//			
		// }).start();

	}

}
