package comThread.Study.LinkList;

import comThread.javaBean.UserInfo;

public class LinkDemo {
	public static void main(String[] args) {
		Link link = new Link();
		link.add("hello world !");
		link.add("my test!");
		link.add("ni hao !");
		link.add("zhang san");
		link.add("li si");
		link.remove("zhang san");
		String [] results = link.toStringList();
		link.getStringList(results);
		
		
		System.out.println("大小为："+link.size());
		System.out.println("结果为："+ link.contain("li si"));
		System.out.println("结果为："+ link.contain("wang wu"));
		
		
		
		
//		Integer age =0;
//		String name = "Ran";
//		link.changeAge(age);
//		link.changeName(name);
//		UserInfo info = new UserInfo();
//		info.setName("cat");
//		info.setProject("jsp");
//		
//		link.changeUserInfo(info);
//		System.out.println("age:" +age);
//		System.out.println("name:" +name);
//		
//		System.out.println(info.toString());
	}
	
}




class Link{
	public Integer age =0;
	public Integer changeAge(Integer age){
		age =10;
		return age;
	}
	public String changeName(String name){
		name ="Sky";
		return name;
	}
	public UserInfo changeUserInfo(UserInfo userInfo){
		userInfo.setName("Davi");
		userInfo.setProject("java");
		return userInfo;
	}
	private class Node{
		private Node next;
		private String data;
		/**
		 * 构造方法
		 * @param data
		 */
		public Node(String data){
			this.data = data;
		}
		
		
		/**
		 * 向Node添加数据
		 * @param newNode
		 */
		public void addNode(Node newNode){
			if(null ==this.next){
				this.next = newNode;
			}else{
				this.next.addNode(newNode);
			}
		}
		
		
		/**
		 * 将Node转换为数组
		 * @return
		 */
		public String[] nodeToString(){
			strList[foot++] =this.data;
			if(null != this.next){
				this.next.nodeToString();
			}
			return strList;
		}
		
		/***
		 * 删除Node元素
		 * @param proNode
		 * @param data
		 */
		public void removeNode(Node proNode ,String data){
			if(data.equals(this.data)){
				System.out.println("-----proNode  "+proNode.next+"----this   "+this);
				proNode.next = this.next;
			}else{
				this.next.removeNode(this, data);
			}
		}
		
		/**
		 * 查询Node元素
		 * @param data
		 * @return
		 */
		public boolean containNode(String data){
			if(data.equals(this.data)){
				return true;
			}else{
				if(null ==this.next){
					return false;
				}
				return this.next.containNode(data);
			}
		} 
		
	}
	
	private Node root;
	private String [] strList;
	private int count =0;
	private int foot =0;
	
	/**
	 * 添加
	 * @param data
	 */
	public void add(String data){
		if(null==data){
			return;
		}
		Node newNode = new Node(data);
		if(null== this.root){
			this.root = newNode;
		}else{
			this.root.addNode(newNode);
		}
		count++;
	}

	
	/**
	 * 将链表转换为数组
	 * @return
	 */
	public String[] toStringList(){
		if(null ==root){
			return null;
		}else{
			strList = new String[this.count];
			this.foot =0;
//			if(null ==root.next){
//				strList[count]=root.data;
//				return strList;
//			}else{
//				return root.next.nodeToString();
//			}
			return root.nodeToString();

		}
	}
	
	public void remove(String data){
		if(this.root ==null){
			return ;
		}else{
			if(data.equals(this.root.data)){
				this.root = this.root.next;
			}else{
				this.root.next.removeNode(this.root ,data);
			}
			this.count--;
		}
	}
	
	
	public boolean isEmpty(){
		if(root==null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean contain(String data){
		if(null ==root){
			return false;
		}else{
			return root.containNode(data);
//			if(null == root.next){
//				if(data.equals(root.data)){
//					return true;
//				}else{
//					return false;
//				}
//			}else{
//				return root.next.containNode(data);
//			}
		}
	}
	
	/**
	 * 获取链表大小
	 * @return
	 */
	public int size(){
		return count;
	}

	
	/**
	 * 将数组一个输出
	 * @param strs
	 */
	public void getStringList(String[] strs){
		for(int i=0; i<strs.length; i++){
			System.out.println(strs[i]);
		}
	}
}