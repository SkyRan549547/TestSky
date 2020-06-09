package comThread;



public class ThreadA extends Thread {
	
	private test test = new test();
	public ThreadA(test test){
		super();
		this.test=test;
	}
	
	public void run(){
		test.await();
	}
}
