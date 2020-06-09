package Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
	
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(20);
		for(int i=0; i<20; i++){
			threadPool.execute(new Runnable() {
				
				public void run() {
					
					
				}
			});
		}
	}
}
