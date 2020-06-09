package comThread.WriteRead;

public class Test1 {

	
	public static void main(String[] args) {
		final Demo de = new Demo();
//		de.set("key1", "value1");
//		de.set("key2", "value2");
//		de.set("key3", "value3");

		new Thread(new Runnable() {
			public void run() {
				de.set("key1", "value1");
			}
		}).start();
//		
////		new Thread(new Runnable() {
////			public void run() {
////				de.set("key2", "value2");
////			}
////		}).start();
//		
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName()+ "...."+de.get("key1"));;
//			}
//		}).start();
//		
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName()+ "...."+de.get("key1"));;
//			}
//		}).start();
//		
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName()+ "...."+de.get("key1"));;
//			}
//		}).start();
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName()+ "...."+de.get("key1"));;
//			}
//		}).start();
//		
//		
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName()+ "...."+de.get("key2"));;
//			}
//		}).start();
//		
//		new Thread(new Runnable() {
//			public void run() {
//				System.out.println(Thread.currentThread().getName()+ "...."+de.get("key3"));;
//			}
//		}).start();
		
		
		
		new Thread(new Runnable() {
			public void run() {
				de.set("key2", "value2");
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				de.set("key3", "value3");
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				de.set("key4", "value4");
			}
		}).start();
		
		
		
	}
}
