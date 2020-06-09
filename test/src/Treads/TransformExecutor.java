package Treads;

import java.io.Serializable;

/**
 * 执行具体转换操作的类， 采用多线程技术，继承了runnable接口
 * 
 * @author Administrator
 * 
 */
public class TransformExecutor implements Runnable, Serializable {

	private static final long serialVersionUID = 1L;

	private ResourceInfo info = null;

	public TransformExecutor(ResourceInfo info) {
		this.info = info;
	}

	public void run() {

		String resourceId = info.getResourceId();
		String path = info.getPath();
		String fileName = info.getFileName();

		String videoFilename = TransConfig.VIDEO_SOURCE_ROOT + path + fileName;
		String flvFilename = path + FileUtil.getFilePrefix(fileName) + ".flv";

		// 转换成功,修改数据库中的is_transed字段为1
		if (Video2FLVTransfer.transform(videoFilename, flvFilename) == 1) {
			CRUDUtil.update(resourceId, 1);
		}
		// 转换失败，修改数据库中的is_transed字段为2
		else {
			CRUDUtil.update(resourceId, 2);
		}

		// 将resourceInfo从转换队列中去除
		TransformTaskQueue.remove(info);

	}
}
