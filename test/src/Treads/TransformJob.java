package Treads;

/**
 * �����������ִ����
 * 
 * @author Administrator
 * 
 */
public class TransformJob implements Job {

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		// ��ȡ��ǰ��ת����Ƶ�ļ�����
		Vector<ResourceInfo> infos = TransformTaskQueue.getInstance().taskQueue;
		System.out.println("size:" + infos.size());

		// �����������д��ڴ�ת�����������ת��
		if (infos.size() > 0) {
			for (int i = 0; i < infos.size(); i++) {
				// statusΪ0����ʾ��������ת���е�
				if (infos.get(i).getStatus() == 0) {
					infos.get(i).setStatus(1);
					// �¿��̣߳�ִ��ת������
					TransExecutorService.getInstance().execute(infos.get(i));
				}
			}
		}
	}
}
