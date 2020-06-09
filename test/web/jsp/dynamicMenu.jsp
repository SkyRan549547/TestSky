<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="baosight.baosteel.uup.client.model.UserSummary"%>
<%@ page import="com.baosight.baosteel.cas.client.Constants"%>
<%@ page
	import="com.baosight.baosteel.cas.common.util.PropertiesValueHelper"%>
<%@ page
	import="com.baosight.baosteel.cas.client.sso.cache.CasUserSummaryCache"%>
<%@ page import="com.baosight.baosteel.bli.tpl.common.SystemConstants"%>
<%@ page import="baosight.baosteel.uup.client.menu.MenuModel"%>
<%@ page import="java.util.*"%>
<%@ include file="/inc/constants.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link type="text/css" rel="stylesheet"
	href="<%=SERVELET_PATH%>/tree/xtree.css">
<script src="<%=SERVELET_PATH%>/tree/xtree.js"></script>
<script src="<%=SERVELET_PATH%>/tree/xloadtree.js"></script>
<script src="<%=SERVELET_PATH%>/tree/xmlextras.js"></script>
<script language="JavaScript">

	var http = null;
	<%
		if(session.getAttribute("SYS_INNER_FUN_HOMEPAGE")==null){
			out.println("	menuClick(94,'SYS_INNER_FUN_HOMEPAGE');  ");
			session.setAttribute("SYS_INNER_FUN_HOMEPAGE","1");  
		} 
	%>
	function createXMLHttpRequest() {
         var xmlreq = false;  
         if (window.XMLHttpRequest) {  
             xmlreq = new XMLHttpRequest();
          } else if (window.ActiveXObject) {
            try { 
              xmlreq = new ActiveXObject("Msxml2.XMLHTTP"); 
            } catch (e1) {
              try {
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
              } catch (e2) {}
            }
          }
  		return xmlreq;
	}

	
	function menuClick(domainID,menuCode){
		http = createXMLHttpRequest();
		//var http = window.ActiveXObject ? new ActiveXObject('MSXML2.XMLHTTP') : new XMLHttpRequest;
		http.open("POST", "/baosteel_3pl/maintain/touchMenu.do?method=menuCount&domainId="+domainID+"&menuCode="+menuCode, true);
		http.onreadystatechange = handlestatechange;
		http.send(null);
	}
	
	function handlestatechange(){
         if(http.readyState == 4){
            if(http.status == 200){ 
		
            }
         }
	}
	
//-->
</script>
<body bgcolor="#ffffff">


	<%
				UserSummary summary = (UserSummary) session
				.getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
	%>
	<%--<b>²Ëµ¥ÑÝÊ¾</b><br>--%>
	<%
				class Temp implements
				baosight.baosteel.uup.client.menu.ITreeGenerator {

			public String getIcon(MenuModel item) {
				// TODO Auto-generated method stub
				if (item.getType() == null)
			return null;

				if (item.getType().longValue() == 1)
			return "tree/images/folder1.gif";
				else
			return "tree/images/report.gif";
			}

			public String getIsOpen(MenuModel item) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getOpenIcon(MenuModel item) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getTarget(MenuModel item) {
				// TODO Auto-generated method stub
				if (item.getUrl() == null || item.getUrl().startsWith("#"))
			return "_self";
				else
			return "main";
			}

			public String getUrl(MenuModel item) {
				// TODO Auto-generated method stub
				if (item.isRootNode())
			return "#";
				else
			return item.getUrl();
			}

			public String getClickEvent(MenuModel item) {
				return "menuClick('" + item.getDomainId() + "','"
				+ item.getCode() + "')";
				//return "alert('"+item.getDomainId()+"','"+item.getCode()+"')";
			}
		}
	%>
	<%
	//=baosight.baosteel.uup.client.model.util.common.MenuUtils.parseMenu(summary,new Temp())
	%>
	<%
		Map map = new HashMap();
		//map.put("DefaultLanguage", userSummary.getDefaultLanguage());//ÕZÑÔîÐÍ
		map.put("style", "grey");//ïL¸ñ
		out
				.println(baosight.baosteel.uup.client.model.util.common.MenuUtils
				.parseMenu(summary, new Temp(), map));
	%>
</body>
	





</body>
</html>