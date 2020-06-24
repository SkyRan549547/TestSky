package comThread.TestThread;



import comThread.javaBean.UserInfo;

public class SynchProduct implements Runnable {

	private UserInfo info ;
		
	
	public SynchProduct(UserInfo info){
		this.info = info;
	}
	
	public void run() {
		while(true){
			if(info.getIndex()<0){
				break;
			}
			if(info.getIndex()%2==0){
				System.out.println("...."+Thread.currentThread().getName()+":"+info.getNextIndex()+".....");
				this.info.set("����", "��ѧ��");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.info.get();
			}else{
				System.out.println("...."+Thread.currentThread().getName()+":"+info.getNextIndex()+".....");
				this.info.set("����", "������");
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
