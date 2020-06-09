package Treads;
/**
 * 格式转换任务调度管理类
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
     * 开始执行，将加载调度配置并启动每个调度。
     * 
     * @注意: 一般在程序启动时调用该方法。
     */
    public void execute() {
        try {
            // 加载调度配置，并启动每个调度。
            scheduleJobs();
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载调度配置并启动每个调度
     * 
     * @注意: TODO
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
     * 根据ResourceInfo启动一个调度
     * 
     * @注意: 内部方法，外部不能调用
     * @param ResourceInfo
     *            资源信息
     */
    private void start() {

        try {
            // String id = info.getResourceId();
            // 构造方法中 第一个是任务名称 ，第二个是任务组名，第三个是任务执行的类
            JobDetail jobDetail = new JobDetail("video_trans_id",
                    Scheduler.DEFAULT_GROUP, TransformJob.class);
            String cronExpr = "0/10 * * * * ?";
            String triggerName = "video_trans_trigger";
            Trigger trigger = new CronTrigger(triggerName,
                    Scheduler.DEFAULT_GROUP, cronExpr);

            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            System.out.println("出错");
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
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
