package readXml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(asyncSupported = true, urlPatterns = { "/MyServlet" })
public class MyServlet  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*ClassLoader cl = this.getClass().getClassLoader();
		InputStream is = cl.getResourceAsStream("/com/hncj/edu/config.properties");
		if(is!=null) {
			Properties pr=new Properties();
			pr.load(is);
			System.out.println(pr.getProperty("username"));
			System.out.println(pr.getProperty("password"));
		}else {
			System.out.println("����ʧ�ܣ�");
		}*/
            //������ʹ��servletContext���������Դ�ļ�
		ServletContext servletContext = this.getServletContext();
		InputStream is = servletContext.getResourceAsStream("/WEB-INF/config.properties");
		Properties pr=new Properties();
		pr.load(is);
		System.out.println(pr.getProperty("username"));
		System.out.println(pr.getProperty("password"));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
