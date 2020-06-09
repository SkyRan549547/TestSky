

import comThread.javaBean.UserInfo;

public class SynchCustomer implements Runnable {

	private UserInfo info ;
	public SynchCustomer(UserInfo info){
		this.info =info;
	}
	public void run() {
		for(int i=0; i<50;i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(info.getName()+"--"+this.info.getProject()+"Êý¾Ý"+i);
//			this.info.get();
			}
		
	}

}
