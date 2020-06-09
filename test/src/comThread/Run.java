package comThread;

public class Run {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		test test = new test();
        ThreadA a = new ThreadA(test);
        a.start();
        Thread.sleep(3000);
        test.signal();
        
        Run run = new Run();
        run.a();
		// TODO Auto-generated method stub
	}
	
	
	public synchronized void  a(){
		int i=0;
        for(;;){
        	System.out.println(".............");
        	i++;
        	if(i>10){
        		try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        }
	}
}
