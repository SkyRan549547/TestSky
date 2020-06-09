package readXml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ReadXmlTest {
	static Map httpMap = new LinkedHashMap();
	static Map wsMap = new LinkedHashMap();
	
	private void initData() throws ParserConfigurationException, SAXException, IOException{
		InputStream is = ReadXmlTest.class.getClassLoader()
		.getResourceAsStream("bsteelServer.xml");
		if (is == null) {
			System.out
					.println("在classes目录下没有找到bsteelServer.xml配置文件！！！！！！！！");
			return;
		}
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		df.setValidating(false);
		df.setIgnoringComments(true);
		df.setNamespaceAware(false);
		DocumentBuilder builder = df.newDocumentBuilder();
		Document doc = builder.parse(is);
		Element rootItem = doc.getDocumentElement();
		NodeList lt = rootItem.getElementsByTagName("http");
		for (int i = 0; i < lt.getLength(); i++) {
			Element e = (Element) lt.item(i);
			//
			ServerConfigItem mci = new ServerConfigItem();
			mci.id = e.getAttribute("id");
			mci.type = "http";
			mci.name = getText(e, "name");
			mci.description = getText(e, "description");
			mci.className = getText(e, "class");
			ReadXmlTest.httpMap.put(mci.id, mci);
		}
	}
	
	
	
	
	public void getMyServlet() throws ParserConfigurationException, SAXException, IOException{
		InputStream is = ReadXmlTest.class.getClassLoader()
		.getResourceAsStream("bsteelServer.xml");
		if (is == null) {
			System.out
					.println("在classes目录下没有找到bsteelServer.xml配置文件！！！！！！！！");
			return;
		}
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory df1 = DocumentBuilderFactory.newInstance();
		System.out.println("==df==="+df+"=========");
		System.out.println("==df==="+df1+"=========");
		df.setValidating(false);
		df.setIgnoringComments(true);
		df.setNamespaceAware(false);
		DocumentBuilder builder = df.newDocumentBuilder();
		Document doc = builder.parse(is);
		Element rootItem = doc.getDocumentElement();
		NodeList lt = rootItem.getElementsByTagName("servlet");
		System.out.println("=====servlet个数："+lt.getLength()+"=========");
		for (int i = 0; i < lt.getLength(); i++) {
			Element e = (Element) lt.item(i);
			ServerConfigItem item = new ServerConfigItem();
			item.setServletClass(e.getElementsByTagName("servlet-name").toString());
			item.setServletName(e.getElementsByTagName("servlet-Class").toString());
			System.out.println("====="+e.getElementsByTagName("servlet-name")+"=========");
			System.out.println("====="+e.getElementsByTagName("servlet-Class")+"=========");
		}
		
		NodeList lt1 = rootItem.getElementsByTagName("http");
		System.out.println("=====http个数："+lt1.getLength()+"=========");
		for (int i = 0; i < lt1.getLength(); i++) {
			Element e = (Element) lt1.item(i);
			ServerConfigItem item = new ServerConfigItem();
			item.setId(e.getElementsByTagName("id").toString());
			System.out.println("====="+e.getElementsByTagName("id").item(0)+"=========");
			System.out.println("====="+e.getAttribute("id")+"=========");
		}
		
		
	}
	
	
	
	public void getMyHtml() throws ParserConfigurationException, SAXException, IOException{
		BufferedInputStream is = (BufferedInputStream)ReadXmlTest.class.getClassLoader()
		.getResourceAsStream("myTest.html");
//		FileReader red = new FileReader("myTest.html");
		if (is == null) {
			System.out
					.println("在classes目录下没有找到bsteelServer.xml配置文件！！！！！！！！");
			return;
		}
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = df.newDocumentBuilder();
		df.setValidating(false);
		df.setIgnoringComments(true);
		df.setNamespaceAware(false);
//		System.out.println("======len========"+is.read(new byte[1024], 0, 1024));
		File f = new File("D:\\test");
		f.mkdirs();
		String path ="D:\\test\\text.txt";
		FileOutputStream out = new FileOutputStream(path);
//		FileWriter out = new FileWriter(path);
		int len;
		byte [] data = new byte[1024];
		String str;
//		System.out.println("==============len" +is.read(data, 0, 1024));
		while((len = is.read(data, 0, 1024))!=-1){
			str= new String(data, 0, len);
			System.out.println("==============str" +str);
			Document doc = builder.parse(str);
//			str.contains(s);
			System.out.println("==============2");
			Element em = doc.getDocumentElement();
			System.out.println("==============3");
			System.out.println("====="+em.getAttribute("body")+"=========");
			NodeList list = em.getElementsByTagName("body");
			for(int i = 0; i < list.getLength(); i++){
				Element e = (Element) list.item(i);
				System.out.println("====="+e.getAttribute("h1")+"=========");

			}
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	private String getNodeList(NodeList parmList, Object ob){
		
		for(int i=0; i<parmList.getLength();i++){
			
		}
		return "";
	}
	
	private static String getText(Element e, String tagName) {
		NodeList lt = e.getElementsByTagName(tagName);
		if (lt.getLength() > 0) {
			Element el = (Element) lt.item(0);
			if (el.getFirstChild() != null)
				return el.getFirstChild().getNodeValue();
			else
				return "";
		}
		return null;
	}

	
}
