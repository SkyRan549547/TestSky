package Treads;
/**
 * ��ʽת��������ȹ�����
 * 
 * @author Administrator
 * 
 */
public class SchedulManager {
	
	
	

    private static SchedulManager instance = new SchedulManager();
    private Scheduler scheduler;
    private volatile boolean start = false;

    private SchedulManager() {
        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static SchedulManager getInstance() {
        return instance;
    }

    /**
     * ��ʼִ�У������ص������ò�����ÿ�����ȡ�
     * 
     * @ע��: һ���ڳ�������ʱ���ø÷�����
     */
    public void execute() {
        try {
            // ���ص������ã�������ÿ�����ȡ�
            scheduleJobs();
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ص������ò�����ÿ������
     * 
     * @ע��: TODO
     */
    @SuppressWarnings("static-access")
    private void scheduleJobs() {

        Vector<ResourceInfo> infos = TransformTaskQueue.getInstance().taskQueue;
        System.out.println("size:" + infos.size());
        if (infos.size() > 0) {
            start();
        }
        start = true;
    }

    /**
     * ����ResourceInfo����һ������
     * 
     * @ע��: �ڲ��������ⲿ���ܵ���
     * @param ResourceInfo
     *            ��Դ��Ϣ
     */
    private void start() {

        try {
            // String id = info.getResourceId();
            // ���췽���� ��һ������������ ���ڶ���������������������������ִ�е���
            JobDetail jobDetail = new JobDetail("video_trans_id",
                    Scheduler.DEFAULT_GROUP, TransformJob.class);
            String cronExpr = "0/10 * * * * ?";
            String triggerName = "video_trans_trigger";
            Trigger trigger = new CronTrigger(triggerName,
                    Scheduler.DEFAULT_GROUP, cronExpr);

            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            System.out.println("����");
            e.printStackTrace();
        }
    }

    public void init() {

        SchedulManager sm = SchedulManager.getInstance();
        // sm.start(info);
        // sm.scheduleJobs();
        sm.start();
        try {
            sm.scheduler.start();
        } catch (SchedulerException e) {
            // TODO �Զ����ɵ� catch ��
            e.printStackTrace();
        }
    }
}
