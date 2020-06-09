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
		System.out.println("���ȸɵ��ġ�.������������");
		System.out.println("ִ�н��Ϊ��"+result);
		
	}
	
	public Integer call() throws Exception {
		System.out.println("���ڽ���Callable�ļ��㡣��������");
		Thread.sleep(1000);
		return 1;
	}

}
