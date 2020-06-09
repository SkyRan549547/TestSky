package Treads;

public class EventTransformStartupListener implements ServletContextListener {

	    @Override
	    public void contextDestroyed(ServletContextEvent arg0) {
	    }

	    @Override
	    public void contextInitialized(ServletContextEvent arg0) {
	        
	        System.out.println("init...");
	        
	        SchedulManager sm = SchedulManager.getInstance();
	        //sm.start(info);
	        sm.init();
	    }
	
}
