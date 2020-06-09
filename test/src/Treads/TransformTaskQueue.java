package Treads;

import java.util.Vector;
	/**
	 * 格式转换任务队列
	 * 队列中放的是ResourceInfo类型对象
	 * @author Administrator
	 *
	 */
public class TransformTaskQueue {
	

	    private static TransformTaskQueue instance = null;

	    //实际存放转换对象信息的队列，采用线程安全的Vercor容器
	    public static Vector<ResourceInfo> taskQueue = new Vector<ResourceInfo>();

	    public static TransformTaskQueue getInstance() {
	        if (instance == null) {
	            instance = new TransformTaskQueue();
	        }
	        return instance;
	    }

	    /**
	     * 向队列中添加对象
	     * @param info
	     */
	    public static void add(ResourceInfo info) {
	        taskQueue.add(info);
	    }

	    /**
	     * 从队列中删除对象
	     * @param info
	     */
	    public static void remove(ResourceInfo info) {
	        if(taskQueue.size()>0 && taskQueue.contains(info)){
	            taskQueue.remove(info);
	        }
	    }

	 // 如果源视频文件存在，则进行相应的转换,转换为FLV文件
//        if (new File(TransConfig.VIDEO_SOURCE_ROOT + path + fileName).exists()) {
//
//            ResourceInfo info = new ResourceInfo();
//            info.setResourceId(resourceId);
//            info.setPath(path);
//            info.setFileName(fileName);
//            info.setStatus(0);
//            // 添加到转换队列
//            TransformTaskQueue.add(info);
//
//        } else {
//            System.out.println("源文件不存在！");
//        }
	    public void queueTest(){
	    	LinkedList ;
	    	Map;
	    	ArrayList;
	    }
	    
}
