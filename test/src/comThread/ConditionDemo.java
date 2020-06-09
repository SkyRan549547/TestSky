package comThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

	Object[] queue = new Object[3];//����
	Object[] queue2 = new Object[3];//����2
	String [] strs = new String[3];
    int readIndex = 0;//read����λ��
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

    //����
    public void write(Object obj) {
    	
        	lock.lock();
            try {
    		 if (dataLen >= queue.length) {//����д����
    			 System.out.println("��������,�޷�д��,���Ѷ���reading.....11111");
    			 fullCondition.await();
    		 }
    		 System.out.println("�����п�λ�ˣ�д��writing..fullCondition...1111111");
    		 queue[writeIndex] = obj;
//    		 queue2[writeIndex]= obj;
    		 System.out.println("queue= "+queue[writeIndex]);
    		 writeIndex++;
    		 dataLen++;
    		 if (writeIndex >= queue.length) {
    			 writeIndex = 0;
    		 }
    		 emptyCondition.signal();
    		 System.out.println("...��ȡ...emptyCondition...111111");
            } catch (InterruptedException e) {
            	System.out.print("writing is stop");
    			 e.printStackTrace();
            } finally {
    			 lock.unlock();
            }
    	
    }

    //����
    public Object read() {
        lock.lock();
        try {
		 if (dataLen <= 0) {
			 System.out.println("���п���,�޷���ȡ,�ȴ�����.....222222");
			 emptyCondition.await();
		 }
		 System.out.println("������������,����.��ȡ.emptyCondition...22222");
		 Object obj = queue[readIndex];
		 readIndex++;
		 dataLen--;
		 if (readIndex >= queue.length) {
			 readIndex = 0;
		 }
		 fullCondition.signal();
		 System.out.println("...д��...fullCondition...2222222");
		 return obj;
	    } catch (InterruptedException e) {
        	System.out.print("reading is stop");
	    	e.printStackTrace();
        } finally {
        	lock.unlock();
        }
        return null;
    }
    
    

//    //����
//    public void write(Object obj) {
//    	if(dataLen>=0){
//        	lock.lock();
//            try {
//    		 if (dataLen == queue.length) {//����д����
//    			 System.out.println("��������,�޷�д��,���Ѷ���reading.....11111");
//    			 fullCondition.await();
//    		 }
//    		 System.out.println("�����п�λ�ˣ�д��writing..fullCondition...1111111");
//    		 queue[writeIndex] = obj;
////    		 queue2[writeIndex]= obj;
//    		 System.out.println("queue= "+queue[writeIndex]);
//    		 writeIndex++;
//    		 dataLen++;
//    		 if (writeIndex >= queue.length) {
//    			 writeIndex = 0;
//    		 }
//    		 emptyCondition.signal();
//    		 System.out.println("...д��...emptyCondition...111111");
//            } catch (InterruptedException e) {
//            	System.out.print("writing is stop");
//    			 e.printStackTrace();
//            } finally {
//            	System.out.print("writing ��Դ�ͷţ�");
//    			 lock.unlock();
//            }
//    	}
//    }
//
//    //����
//    public Object read() {
//    	if(dataLen>0){
//            lock.lock();
//            try {
//	    		 if (dataLen == 0) {
//	    			 System.out.println("���п���,�޷���ȡ,�ȴ�����.....222222");
//	    			 emptyCondition.await();
//	    		 }
//	    		 System.out.println("������������,����.��ȡ.emptyCondition...22222");
//	    		 Object obj = queue[readIndex];
//	    		 readIndex++;
//	    		 dataLen--;
//	    		 if (readIndex >= queue.length) {
//	    			 readIndex = 0;
//	    		 }
//	    		 fullCondition.signal();
//	    		 System.out.println("...д��...fullCondition...2222222");
//	    		 return obj;
//    	    } catch (InterruptedException e) {
//            	System.out.print("reading is stop");
//    	    	e.printStackTrace();
//            } finally {
//            	lock.unlock();
//            	System.out.print("reading ��Դ�ͷţ�");
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
    //�� ����await(),����ִ����Ӧcondition��signal()֮ʱ�����ѡ�
    //������Դ�����������ִ����Ӧ��signal()֮��Ĵ������Դ���Թ���
    //��.Lock()��unlock()��������Ӧ�ɶԵģ�����ͬһ��Lock��Ķ�����á�
    //��await�����ǵȴ������û�ж�Ӧ��signal������֮�������һֱ�ȴ���
    //�壬����signal��������ʱ���Ƚ��ֳ���ִ�������ִ�ж�Ӧ��await����֮��ĳ���
}
