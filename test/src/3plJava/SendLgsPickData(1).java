package com.baosight.baosteel.bli.tpl.main;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.baosight.baosteel.bli.tpl.common.JsonUtil;
import com.baosight.baosteel.bli.tpl.interfaces.dao.MessageDao;
import com.baosight.baosteel.bli.tpl.model.LgsMessagePickData;
import com.baosight.baosteel.bli.tpl.model.LgsMessagePickDataLog;
import com.baosight.baosteel.bli.tpl.model.LgsMessagePickMaterial;
import com.baosight.baosteel.bli.tpl.model.TplMessageLog;

public class SendLgsPickData {
	
	MessageDao messageDao;
	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	//测试
//	private static final String sendAgentGoodsAddress = "http://10.60.146.52:8080/ouyeel-openapi/services/tpl-outPerformanceWS?wsdl";
	//正式
	private static final String sendAgentGoodsAddress = "http://openapi.ouyeel56.com/ouyeel-openapi/services/tpl-outPerformanceWS?wsdl";
	private static final String serviceName = "receiveOutPerformanceInfo";
	
	public void sendPickData(LgsMessagePickData lgsMessagePickData){
		System.out.println("3");
		String returnDetail = "";
		String returnPick = "";
		List pickDetailList = new ArrayList();
		Map transPlanParam = new HashMap();
		String body = "";
		Iterator it = lgsMessagePickData.getMessagePickMaterialSet().iterator();
		List transPlanList;
		try {
			transPlanList = messageDao.queryTransPlan(lgsMessagePickData);
			boolean result = false;
			if (transPlanList != null && transPlanList.size() > 0) {
				Map item = (Map) transPlanList.get(0);
				String tstatusSel = formatNullToBlank((String)item.get("tStatus"));
				String proividerIdSel = "";
				if("10".equals(tstatusSel)){
					proividerIdSel = formatNullToBlank((String)item.get("tproviderId"));
				}else{
					proividerIdSel= formatNullToBlank((String)item.get("providerId"));
				}
				String manuId = lgsMessagePickData.getManuId();
				String transType = formatNullToBlank((String)item.get("transType"));
				if ((proividerIdSel.equals("001518") && transType.equals("1"))
						|| (proividerIdSel.equals("009477") && transType.equals("1") && (manuId.equals("BGTM") || manuId.equals("BGNA")))
						|| (proividerIdSel.equals("155682") && transType.equals("1") && (manuId.equals("BGBW") || manuId.equals("BSKV")))
						|| (proividerIdSel.equals("150066") && transType.equals("1") && manuId.equals("BGSA"))) {
					result= true;
				}
				
				if(result){
					transPlanParam.put("unitId", lgsMessagePickData.getUnitId());
					transPlanParam.put("pickNum", lgsMessagePickData.getPickNum());
					transPlanParam.put("manuId", lgsMessagePickData.getManuId());
					transPlanParam.put("readyNum", lgsMessagePickData.getReadyNum());
					transPlanParam.put("orderNum", lgsMessagePickData.getOrderNum());
					transPlanParam.put("vehicleCode", lgsMessagePickData.getVehicleCode());
					String tStatus = formatNullToBlank((String)item.get("tStatus"));
					if(!tStatus.equals("")){
						if(tStatus.equals("20")){//转授权提交
							transPlanParam.put("tproviderId",formatNullToBlank((String)item.get("providerId")));         
							transPlanParam.put("tproviderName",formatNullToBlank((String)item.get("providerName")));       
							transPlanParam.put("realTproviderId", formatNullToBlank((String)item.get("tproviderId")));         
							transPlanParam.put("realTproviderName", formatNullToBlank((String)item.get("tproviderName")));       
						}else if(tStatus.equals("10")){//转授权
							transPlanParam.put("tproviderId",formatNullToBlank((String)item.get("tproviderId")));         
							transPlanParam.put("tproviderName",formatNullToBlank((String)item.get("tproviderName")));       
							transPlanParam.put("realTproviderId", formatNullToBlank((String)item.get("providerId")));         
							transPlanParam.put("realTproviderName", formatNullToBlank((String)item.get("providerName")));
						}
					}else{//未转授权
						transPlanParam.put("tproviderId",formatNullToBlank((String)item.get("providerId")));         
						transPlanParam.put("tproviderName",formatNullToBlank((String)item.get("providerName")));       
						transPlanParam.put("realTproviderId", "");         
						transPlanParam.put("realTproviderName", "");     
					}
					transPlanParam.put("pickType", "10");
					
					List detailList = new ArrayList();
					while (it.hasNext()) {
						LgsMessagePickMaterial lgsMessagePickMaterial = (LgsMessagePickMaterial) it.next();
						Map detailMap = new HashMap();
						
						detailMap.put("packNum", lgsMessagePickMaterial.getPackNum());
						detailMap.put("weightActive", lgsMessagePickMaterial.getWeightActive().toString());
					
						returnDetail = JsonUtil.getJSONString(detailMap);
						System.out.println(returnDetail);
						
						detailList.add(returnDetail);
					}
//					transPlanParam.put("lines", JsonUtil.getJSONString(detailList));
					transPlanParam.put("lines", detailList);
					returnPick = JsonUtil.getJSONString(transPlanParam);
					pickDetailList.add(returnPick);
					
					body =  JsonUtil.getJSONString(pickDetailList);
					System.out.println(body);
					body = body.toString().replaceAll("\\\\", "");
					System.out.println(body);
//					param.put("body", body);
//					String requestStr = JSONObject.fromObject(body).toString();
					System.out.println("requestStr-----"+body);
					JSONObject returnJson = sendAgentGoods(body);
//					JSONObject returnJson = null;
					System.out.println(returnJson);
					
					String pickNum = lgsMessagePickData.getPickNum();
					String orderNum =lgsMessagePickData.getOrderNum();
					String readyNum = lgsMessagePickData.getReadyNum();
					String vehicleCode= lgsMessagePickData.getVehicleCode();
					LgsMessagePickDataLog lsg = (LgsMessagePickDataLog)JSONObject.toBean(returnJson, LgsMessagePickDataLog.class);
					String respStatus= lsg.getRespStatus();
					System.out.println("====respStatus:"+respStatus+"=========");
					if("1".equals(respStatus)){
						TplMessageLog log = new TplMessageLog();
						log.setErrTitle("发送欧冶出厂实绩信息接口");
						log.setErrContent("发送成功！"+"提单号："+pickNum+" 合同号："+orderNum+"准发号："+readyNum+"车船号："+vehicleCode);
						log.setCreateDate(new Date());
						messageDao.insert(log, TplMessageLog.class);
					}else if("0".equals(respStatus)){
						TplMessageLog log = new TplMessageLog();
						log.setErrTitle("发送欧冶出厂实绩信息接口");
						log.setErrContent(" 发送失败！"+"提单号："+pickNum+" 合同号："+orderNum+"准发号："+readyNum+"车船号："+vehicleCode);
						log.setCreateDate(new Date());
						messageDao.insert(log, TplMessageLog.class);
					}
				}else{
					System.out.println("==========非范围内不发送！==========");
				}
				
			}else{
				System.out.println("===========transPlanList.size is 0===================");
			}
//				log.setErrTitle("智慧物流抛送自提及现货业务接口");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String formatNullToBlank(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
			return "";
		} else {
			return str.trim();
		}
	}
	
	public static final JSONObject sendAgentGoods(String jsonStr) throws Exception{
		JSONObject result = null;
		String resultJson = clientWs(sendAgentGoodsAddress, serviceName, jsonStr);
		result = JSONObject.fromObject(resultJson);
		System.out.println("==========="+resultJson);
		return result;
	}
	
	public static String clientWs(String url, String methodName, String jsonStr) throws Exception{
		System.out.println("开始调用url=======================================" + url);
		String resultStr="";
    	try {
			//		Service service = new Service();
			//		Call call = (Call) service.createCall();	
			//		call.setOperationName(new QName(url));
			//		call.setTargetEndpointAddress(new URL(url));
			//		json = (String) call.invoke(new Object[] { jsonStr });
		    Service service = new Service();
		    Call call = (Call) service.createCall();
			//        call.setTargetEndpointAddress(url);
		    call.setTargetEndpointAddress(new java.net.URL(url));
		    call.setTimeout(new Integer(60000));
		    System.out.println("调用。。。。。0");
		    QName qName = new QName("http://openapi.ouyeel56.com/", methodName);
		    System.out.println("调用。。。。。1");
		    call.setOperationName(qName);
		    call.setUseSOAPAction(true);
		    //call.registerTypeMapping(TJk3plOutPerformParam.class, qName, new BeanSerializerFactory(TJk3plOutPerformParam.class, qName),new BeanDeserializerFactory(TJk3plOutPerformParam.class, qName));
		    //call.registerTypeMapping(TJk3plOutPerformDetailParam.class, qName, new BeanSerializerFactory(TJk3plOutPerformDetailParam.class, qName),new BeanDeserializerFactory(TJk3plOutPerformDetailParam.class, qName));
		    //以下是添加参数，方法名中有参数，就需要在下面添加
		    call.addParameter("outPerformParamList", XMLType.SOAP_STRING, ParameterMode.IN);
		    //设定返回值类型
		    call.setReturnType(XMLType.SOAP_STRING);
			//        List<TJk3plOutPerformParam> list = new ArrayList<>();
			//        TJk3plOutPerformParam plOutPerformParam = new TJk3plOutPerformParam();
			//        List<TJk3plOutPerformDetailParam> list1 = new ArrayList<>();
			//        TJk3plOutPerformDetailParam detailParam = new TJk3plOutPerformDetailParam();
			//        list1.add(detailParam);
			//        plOutPerformParam.setLines(list1);
			//        list.add(plOutPerformParam);
		    //接收返回值
		    System.out.println("调用。。。。。2="+jsonStr);
		    Object result = call.invoke(new Object[] {jsonStr});
		    System.out.println("调用。。。。。3");
			resultStr=String.valueOf(result);
			System.out.println("返回数据=======================================" + resultStr);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return resultStr;
	}
}
