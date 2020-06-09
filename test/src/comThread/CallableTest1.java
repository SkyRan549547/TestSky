package comThread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest1 implements Callable<Integer> {

	
	public static void main(String[] args) throws Exception, Exception {
		CallableTest1 demo = new CallableTest1();
		FutureTask<Integer> task = new FutureTask<Integer>(demo);
		Thread t = new Thread(task);
		t.start();
		Integer result = task.get();
		System.out.println("我先干点别的。.。。。。。。");
		System.out.println("执行结果为："+result);
		
	}
	
	public Integer call() throws Exception {
		System.out.println("正在进行Callable的计算。。。。。");
		Thread.sleep(1000);
		return 1;
	}

}
