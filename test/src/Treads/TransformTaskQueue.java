package Treads;

import java.util.Vector;
	/**
	 * ��ʽת���������
	 * �����зŵ���ResourceInfo���Ͷ���
	 * @author Administrator
	 *
	 */
public class TransformTaskQueue {
	

	    private static TransformTaskQueue instance = null;

	    //ʵ�ʴ��ת��������Ϣ�Ķ��У������̰߳�ȫ��Vercor����
	    public static Vector<ResourceInfo> taskQueue = new Vector<ResourceInfo>();

	    public static TransformTaskQueue getInstance() {
	        if (instance == null) {
	            instance = new TransformTaskQueue();
	        }
	        return instance;
	    }

	    /**
	     * ���������Ӷ���
	     * @param info
	     */
	    public static void add(ResourceInfo info) {
	        taskQueue.add(info);
	    }

	    /**
	     * �Ӷ�����ɾ������
	     * @param info
	     */
	    public static void remove(ResourceInfo info) {
	        if(taskQueue.size()>0 && taskQueue.contains(info)){
	            taskQueue.remove(info);
	        }
	    }

	 // ���Դ��Ƶ�ļ����ڣ��������Ӧ��ת��,ת��ΪFLV�ļ�
//        if (new File(TransConfig.VIDEO_SOURCE_ROOT + path + fileName).exists()) {
//
//            ResourceInfo info = new ResourceInfo();
//            info.setResourceId(resourceId);
//            info.setPath(path);
//            info.setFileName(fileName);
//            info.setStatus(0);
//            // ��ӵ�ת������
//            TransformTaskQueue.add(info);
//
//        } else {
//            System.out.println("Դ�ļ������ڣ�");
//        }
	    public void queueTest(){
	    	LinkedList ;
	    	Map;
	    	ArrayList;
	    }
	    
}
