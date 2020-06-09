package comThread.javaBean;

public class UserInfo {

	private String name;
	private String project;
	private boolean flag = true;
	//flag为true表示可以生产，但不可以取走
	//flag为false表示可以取走，但不可以生产
	public synchronized void set(String name, String project){
		if(this.flag ==false){
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.name = name;
		this.project = project;
		this.flag =false;//修改生产标记
		super.notify();//唤醒其他等待进程
	}
	
	public synchronized void get(){
		if(this.flag ==true){
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(this.name+"--"+this.project+"数据");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.flag =true;//修改生产标记
		super.notify();//唤醒其他等待进程
	}
	
	
//	public synchronized void set(String name, String project){
//		this.name = name;
//		this.project = project;
//	}
//	
//	public synchronized void get(){
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println(this.name+"--"+this.project+"数据");
//	}
	
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "UserInfo [getName()=" + getName() + ", getProject()="
				+ getProject() + "]";
	}

	public String getProject() {
		return project;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
}
