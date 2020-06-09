package Treads;

/**
 * 调度任务具体执行类
 * 
 * @author Administrator
 * 
 */
public class TransformJob implements Job {

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		// 获取当前待转换视频文件队列
		Vector<ResourceInfo> infos = TransformTaskQueue.getInstance().taskQueue;
		System.out.println("size:" + infos.size());

		// 如果任务队列中存在待转换对象，则进行转换
		if (infos.size() > 0) {
			for (int i = 0; i < infos.size(); i++) {
				// status为0，表示不是正在转换中的
				if (infos.get(i).getStatus() == 0) {
					infos.get(i).setStatus(1);
					// 新开线程，执行转换操作
					TransExecutorService.getInstance().execute(infos.get(i));
				}
			}
		}
	}
}
