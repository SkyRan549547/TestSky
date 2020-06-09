package comThread.TestThread;



import comThread.javaBean.UserInfo;

public class SynchProduct implements Runnable {

	private UserInfo info ;
	private volatile Integer  index=50;
	
//	private synchronized Integer getIndex(){
//		return index--;
//	}
	
	
	public SynchProduct(UserInfo info){
		this.info = info;
	}
	
	public void run() {
		while(true){
			if(index<0){
				break;
			}
			if(index%2==0){
				System.out.println("...."+Thread.currentThread().getName()+":"+index--+".....");
				this.info.set("张三", "好学生");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.info.get();
			}else{
				System.out.println("...."+Thread.currentThread().getName()+":"+index--+".....");
				this.info.set("王五", "草泥马");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.info.get();
			}
			
		}
	}

}
