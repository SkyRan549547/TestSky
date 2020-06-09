package comThread.javaBean;

public class UserInfo {

	private String name;
	private String project;
	private boolean flag = true;
	//flagΪtrue��ʾ������������������ȡ��
	//flagΪfalse��ʾ����ȡ�ߣ�������������
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
		this.flag =false;//�޸��������
		super.notify();//���������ȴ�����
	}
	
	public synchronized void get(){
		if(this.flag ==true){
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(this.name+"--"+this.project+"����");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.flag =true;//�޸��������
		super.notify();//���������ȴ�����
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
//		System.out.println(this.name+"--"+this.project+"����");
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
