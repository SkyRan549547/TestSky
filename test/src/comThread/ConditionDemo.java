package comThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

	Object[] queue = new Object[3];//队列
	Object[] queue2 = new Object[3];//队列2
	String [] strs = new String[3];
    int readIndex = 0;//read索引位置
    int writeIndex = 0;
    int dataLen = 0;
    final Lock lock = new ReentrantLock();
    final Condition fullCondition = lock.newCondition();
    final Condition emptyCondition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        final ConditionDemo demo = new ConditionDemo();
//        Thread.sleep(0);
//        new MyThread() {
//			 @Override
//			 public void run() {
//				 for (int i = 0; i < 3; i++) {
//					 demo.write(i);
//					 System.out.println("iiiiiiiiii = " + i);
//				 }
//			 }
//        }.start();
//		
//        new MyThread() {
//			 @Override
//			 public void run() {
//				boolean flag =true;
//				 while (flag) {
//					 Object obj = demo.read();
//					 if (obj != null) {
//							 System.out.println((Integer) obj+" obj is not null");
//				 	}else{
//				 		flag = false;
//						 System.out.println("obj is null");
//				 	}
//				 }
//		 }
//        }.start();
//        demo.iterateInfo();
        
//        new MyThread() {
//			 @Override
//			 public void run() {
//				 demo.iterateInfo();
//			 }
//       }.start();
        
        
        Thread.sleep(0);
        new MyThread() {
			 @Override
			 public void run() {
				 for(int i=0; i<9; i++){
					 demo.test2();
				 }
			 }
        }.start();
		
        new MyThread() {
			 @Override
			 public void run() {
				 for(int i=0; i<9; i++){
					 demo.test3();
				 }
			 }
        }.start();
    }

    //生产
    public void write(Object obj) {
    	
        	lock.lock();
            try {
    		 if (dataLen >= queue.length) {//队列写满了
    			 System.out.println("队列满了,无法写入,唤醒读入reading.....11111");
    			 fullCondition.await();
    		 }
    		 System.out.println("队列有空位了，写入writing..fullCondition...1111111");
    		 queue[writeIndex] = obj;
//    		 queue2[writeIndex]= obj;
    		 System.out.println("queue= "+queue[writeIndex]);
    		 writeIndex++;
    		 dataLen++;
    		 if (writeIndex >= queue.length) {
    			 writeIndex = 0;
    		 }
    		 emptyCondition.signal();
    		 System.out.println("...读取...emptyCondition...111111");
            } catch (InterruptedException e) {
            	System.out.print("writing is stop");
    			 e.printStackTrace();
            } finally {
    			 lock.unlock();
            }
    	
    }

    //消费
    public Object read() {
        lock.lock();
        try {
		 if (dataLen <= 0) {
			 System.out.println("队列空了,无法读取,等待数据.....222222");
			 emptyCondition.await();
		 }
		 System.out.println("队列有数据了,唤醒.读取.emptyCondition...22222");
		 Object obj = queue[readIndex];
		 readIndex++;
		 dataLen--;
		 if (readIndex >= queue.length) {
			 readIndex = 0;
		 }
		 fullCondition.signal();
		 System.out.println("...写入...fullCondition...2222222");
		 return obj;
	    } catch (InterruptedException e) {
        	System.out.print("reading is stop");
	    	e.printStackTrace();
        } finally {
        	lock.unlock();
        }
        return null;
    }
    
    

//    //生产
//    public void write(Object obj) {
//    	if(dataLen>=0){
//        	lock.lock();
//            try {
//    		 if (dataLen == queue.length) {//队列写满了
//    			 System.out.println("队列满了,无法写入,唤醒读入reading.....11111");
//    			 fullCondition.await();
//    		 }
//    		 System.out.println("队列有空位了，写入writing..fullCondition...1111111");
//    		 queue[writeIndex] = obj;
////    		 queue2[writeIndex]= obj;
//    		 System.out.println("queue= "+queue[writeIndex]);
//    		 writeIndex++;
//    		 dataLen++;
//    		 if (writeIndex >= queue.length) {
//    			 writeIndex = 0;
//    		 }
//    		 emptyCondition.signal();
//    		 System.out.println("...写入...emptyCondition...111111");
//            } catch (InterruptedException e) {
//            	System.out.print("writing is stop");
//    			 e.printStackTrace();
//            } finally {
//            	System.out.print("writing 资源释放！");
//    			 lock.unlock();
//            }
//    	}
//    }
//
//    //消费
//    public Object read() {
//    	if(dataLen>0){
//            lock.lock();
//            try {
//	    		 if (dataLen == 0) {
//	    			 System.out.println("队列空了,无法读取,等待数据.....222222");
//	    			 emptyCondition.await();
//	    		 }
//	    		 System.out.println("队列有数据了,唤醒.读取.emptyCondition...22222");
//	    		 Object obj = queue[readIndex];
//	    		 readIndex++;
//	    		 dataLen--;
//	    		 if (readIndex >= queue.length) {
//	    			 readIndex = 0;
//	    		 }
//	    		 fullCondition.signal();
//	    		 System.out.println("...写入...fullCondition...2222222");
//	    		 return obj;
//    	    } catch (InterruptedException e) {
//            	System.out.print("reading is stop");
//    	    	e.printStackTrace();
//            } finally {
//            	lock.unlock();
//            	System.out.print("reading 资源释放！");
//            }
//    	}
//        return null;
//    }
//    
    
//    
//    public void iterateInfo(){
//    	for(int k=1; k<queue.length; k++){
//			 System.out.println("queue1= "+queue[k]);
//			 System.out.println("length2= "+queue2[k]);
//		 }
//    }
//    
//
//    public void readMyWork(){
//    	lock.lock();
//    	try{
//        	emptyCondition.await();
//			 System.out.println("length= "+queue.length);
//			 System.out.println("length2= "+queue2.length);
//			 
//    	}
//    	catch (InterruptedException e){
//	    	e.printStackTrace();
//    	}
//    	finally{
//    		lock.unlock();
//    	}
//    	
//    }
//    
//    public void test1() throws InterruptedException{
//    	Lock mylock =new ReentrantLock();
//    	Condition con =mylock.newCondition();
//    	con.await();
//    	con.signal();
//    }
    
    
    public void test2(){
    	lock.lock();
    	try{
        	System.out.println("test2 starting 111111 ");
        	if(dataLen>=3){
        		System.out.println("test2 ............ ");
        		emptyCondition.await();
        		System.out.println("test2 await 1111111 ");
        	}
        	dataLen++;
        	fullCondition.signal();
        	System.out.println("test2 signal 1111111 ");
    	}catch(InterruptedException e){
    		e.printStackTrace();
    	}finally{
    		lock.unlock();
    	}
    }
    
    public void test3(){
    	lock.lock();
    	try{
        	System.out.println("test3 starting 2222222 ");
        	if(dataLen==0){
        		fullCondition.await();
            	System.out.println("test3 await 222222 ");
        	}
        	dataLen--;
        	emptyCondition.signal();
        	System.out.println("test3 signal 2222222 ");
    	}catch(InterruptedException e){
    		e.printStackTrace();
    	}finally{
    		lock.unlock();
    	}
    }
    
    
    ///************Multithreading Notes**************//
    //① 先有await(),再在执行相应condition的signal()之时被唤醒。
    //②在资源上锁的情况下执行相应的signal()之后的代码的资源可以共享。
    //三.Lock()与unlock()方法是相应成对的，且是同一个Lock类的对象调用。
    //④await方法是等待，如果没有对应的signal方法将之唤醒则会一直等待。
    //五，在有signal方法唤醒时，先将现程序执行完后再执行对应的await方法之后的程序
}
