package Treads;

import java.io.Serializable;

/**
 * ִ�о���ת���������࣬ ���ö��̼߳������̳���runnable�ӿ�
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

		// ת���ɹ�,�޸����ݿ��е�is_transed�ֶ�Ϊ1
		if (Video2FLVTransfer.transform(videoFilename, flvFilename) == 1) {
			CRUDUtil.update(resourceId, 1);
		}
		// ת��ʧ�ܣ��޸����ݿ��е�is_transed�ֶ�Ϊ2
		else {
			CRUDUtil.update(resourceId, 2);
		}

		// ��resourceInfo��ת��������ȥ��
		TransformTaskQueue.remove(info);

	}
}
