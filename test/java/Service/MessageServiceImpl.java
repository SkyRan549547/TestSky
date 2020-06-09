package com.baosight.baosteel.bli.tpl.interfaces.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baosight.baosteel.bli.tpl.common.AcquireAgencyName;
import com.baosight.baosteel.bli.tpl.common.BeanConvertUtils;
import com.baosight.baosteel.bli.tpl.common.DateUtils;
import com.baosight.baosteel.bli.tpl.common.JpushUtil;
import com.baosight.baosteel.bli.tpl.common.StringUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConfigUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConstants;
import com.baosight.baosteel.bli.tpl.interfaces.dao.MessageDao;
import com.baosight.baosteel.bli.tpl.interfaces.service.MessageService;
import com.baosight.baosteel.bli.tpl.interfaces.service.SendMsgService;
import com.baosight.baosteel.bli.tpl.main.SendLgsPickData;
import com.baosight.baosteel.bli.tpl.main.SendMessageStackOut;
import com.baosight.baosteel.bli.tpl.model.*;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.CarStoreSeqenceSearchModel;
import com.baosight.baosteel.bli.tpl.webservice.client.spotGoods.service.SpotGoodsClientService;
import com.bsteel.uecp.sms.client.SendSMS;
import com.baosight.baosteel.bli.tpl.main.SendTransCarDynWebServicce;
import com.baosight.baosteel.bli.tpl.main.SendTransPlanWebServicce;
import com.baosight.baosteel.bli.tpl.main.SendTransResultWebServicce;
import com.baosight.baosteel.bli.tpl.main.SendTransSeqWebServicce;
public class MessageServiceImpl implements MessageService {
	
	private static final Log logger = LogFactory
		.getLog(MessageServiceImpl.class);

	MessageDao messageDao;

	SendMsgService sendMsgService;
	
	SpotGoodsClientService spotGoodsClientService;

	public SpotGoodsClientService getSpotGoodsClientService() {
		return spotGoodsClientService;
	}
	
	public void setSpotGoodsClientService(
			SpotGoodsClientService spotGoodsClientService) {
		this.spotGoodsClientService = spotGoodsClientService;
	}
	
	SendLgsPickData sendLgsPickData;

	public SendLgsPickData getSendLgsPickData() {
		return sendLgsPickData;
	}

	public void setSendLgsPickData(SendLgsPickData sendLgsPickData) {
		this.sendLgsPickData = sendLgsPickData;
	}
	
	SendTransPlanWebServicce sendTransPlanWebServicce;

	SendTransCarDynWebServicce sendTransCarDynWebServicce;
	
	
	SendTransShipDynImpl sendTransShipDynImpl;

	
	SendTransResultWebServicce sendTransResultWebServicce;
	
	SendTransSeqWebServicce sendTransSeqWebServicce;
	
	public SendTransPlanWebServicce getSendTransPlanWebServicce() {
		return sendTransPlanWebServicce;
	}

	public void setSendTransPlanWebServicce(
			SendTransPlanWebServicce sendTransPlanWebServicce) {
		this.sendTransPlanWebServicce = sendTransPlanWebServicce;
	}

	public SendTransCarDynWebServicce getSendTransCarDynWebServicce() {
		return sendTransCarDynWebServicce;
	}

	public void setSendTransCarDynWebServicce(
			SendTransCarDynWebServicce sendTransCarDynWebServicce) {
		this.sendTransCarDynWebServicce = sendTransCarDynWebServicce;
	}
	
	public SendTransShipDynImpl getSendTransShipDynImpl() {
		return sendTransShipDynImpl;
	}

	public SendTransResultWebServicce getSendTransResultWebServicce() {
		return sendTransResultWebServicce;
	}

	public SendTransSeqWebServicce getSendTransSeqWebServicce() {
		return sendTransSeqWebServicce;
	}

	public void setSendTransShipDynImpl(SendTransShipDynImpl sendTransShipDynImpl) {
		this.sendTransShipDynImpl = sendTransShipDynImpl;
	}

	public void setSendTransResultWebServicce(
			SendTransResultWebServicce sendTransResultWebServicce) {
		this.sendTransResultWebServicce = sendTransResultWebServicce;
	}

	public void setSendTransSeqWebServicce(
			SendTransSeqWebServicce sendTransSeqWebServicce) {
		this.sendTransSeqWebServicce = sendTransSeqWebServicce;
	}

	private static final double INSURANCE_RATE = 0.00035;

	private static final double INSURANCE_RATE_MEI = 0.00035;

	private static final String OPERATION_TYPE_NEW = "10";

	private static final String OPERATION_TYPE_UPDATE = "20";

	private static final String OPERATION_TYPE_RED = "00";

	private static final String OPERATION_FORCE_OVER = "90";
	
	private static final String OPERATION_TYPE_APPLEND = "30";
	
	private static final int CIRCULAR_TPL_CAR_PLAN_ITEM = 40;
	
	private static final String APPLEND_FLAG="1";

	private static final String CLASS_TRANS_PACK_MTM = "com.baosight.baosteel.bli.tpl.model.TplMessageTransPackMtm";
	private static final String CLASS_STOCK_IN_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageStockInItem";
	private static final String CLASS_CONTRACT_REPLACE_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMsgContractItem";

	private static final String CLASS_STOCK_OUT_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageStockOutItem";

	private static final String CLASS_STOCK_IN_REAL_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageStockinRealItem";

	private static final String CLASS_STOCK_OUT_REAL_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageStockoutRealItem";

	private static final String CLASS_STOCK_ADJUST_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageStockAdjustItem";

	private static final String CLASS_STOCK_CHECK_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageStockCheckItem";

	private static final String CLASS_BILL_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageBillItem";

	private static final String CLASS_TRANS_REAL_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageTransRealItem";

	private static final String CLASS_TRANS_LOADING_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageTransOnItem";

	private static final String CLASS_INVOICE_FEE = "com.baosight.baosteel.bli.tpl.model.TplMessageInvoiceFee";

	private static final String CLASS_RESOURCE_MODIFY_APPLY = "com.baosight.baosteel.bli.tpl.model.TplRModifyApplyItem";
	
	private static final String CLASS_MESSAGE_TRANS_LOADING_ITEM="com.baosight.baosteel.bli.tpl.model.TplMessageTransLoadingItem";
	
	private static final String CLASS_MESSAGE_TRANS_ARRIVAL_ITEM="com.baosight.baosteel.bli.tpl.model.TplMessageTransArrivalItem";
	
	private static final String CLASS_TRANS_PACK = "com.baosight.baosteel.bli.tpl.model.TplMessageTransPack";
	
	private static final String CLASS_STACK_OUT_PACK = "com.baosight.baosteel.bli.tpl.model.TplMessageStackOutPack";
	
	private static final String CLASS_MESSAGE_READY_MATERIAL = "com.baosight.baosteel.bli.tpl.model.LgsMessageReadyMaterial";

	private static final String CLASS_MESSAGE_PICK_MATERIAL = "com.baosight.baosteel.bli.tpl.model.LgsMessagePickMaterial";
	
	private static final String CLASS_TRANS_SEQ = "com.baosight.baosteel.bli.tpl.model.TplTransSeq";
	
	private static final String CLASS_MESSAGE_TRAINS_LOADING = "com.baosight.baosteel.bli.tpl.model.TplMessageTrainsLoading";
	
	private static final String CLASS_MESSAGE_TRAINS_ON_LOAD = "com.baosight.baosteel.bli.tpl.model.TplMessageTrainsOnLoad";
	
	private static final String CLASS_MESSAGE_ENTRY_PERMIT_ZJ_S = "com.baosight.baosteel.bli.tpl.model.TplMessageEntryPermitZjS";
	
	private static final String CLASS_MESSAGE_TPL_MESSAGE_CAR_BIND_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageCarBindItem";
	  
	private static final String CLASS_SINCE_CAR_PLAN_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageCarPlanItem";
	
	private static final String CLASS_SINCE_TPL_MESSAGE_CAR_LEAVE_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageCarLeaveItem";
	  
    private static final String CLASS_SINCE_TPL_MESSAGE_KQ_LIST_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMessageKqListItem";
	 
    private static final String CLASS_SINCE_TPL_M_DELIVERY_PLAN_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMDeliveryPlanItem";
    
    private static final String CLASS_SINCE_TPL_M_OUT_LIST_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMOutListItem";
    
	private static final String CLASS_SINCE_TPL_M_SINCE_MATERIAL_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMSinceMaterialItem";
    
   private static final String CLASS_TPL_M_TRANS_LOAD_PLAN_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMTransLoadPlanItem";
	
	private static final String CLASS_TPL_M_ACT_LOAD_PLAN_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMActLoadPlanItem";
	
	private static final String CLASS_TPL_M_PROVIDER_CAR_LIST_ITEM = "com.baosight.baosteel.bli.tpl.model.TplMProviderCarListItem";
	
	private static final String CLASS_TPL_M_EXTRACTION_TRAIN_ITEM ="com.baosight.baosteel.bli.tpl.model.TplMExtractionTrainItem";
	
    private static final String FEE_OVER_FLAG = "0000000000";
	
	private static final String FEE_DEL_FLAG = "DDDDDDDDDD";

	private static final int CIRCULAR_STOCK_IN_ITEM_TIMES = 20;
	
	private static final int CIRCULAR_TRANS_PACK_MTM_TIMES = 20;
	
	private static final int CIRCULAR_STOCK_OUT_ITEM_TIMES = 20;

	private static final int CIRCULAR_TRANS_ITEM_TIMES = 20;
	
	private static final int CIRCULAR_TRUCK_LOADING_TIMES = 20;
	
	private static final int CIRCULAR_TRUCK_ARRIVAL_TIMES = 20;

	private static final int CIRCULAR_TRANS_LOADING_TIMES = 52;

	private static final int CIRCULAR_STOCK_CHECK_ITEM_TIMES = 30;

	private static final int CIRCULAR_STOCK_ADJUST_ITEM_TIMES = 30;

	private static final int CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES = 4;

	private static final int CIRCULAR_INVOICE_FEE_TIMES = 15;

	/**
	 * 提单类型 10 定金合同发货 20 充当合同发货 30 转库提单 40 外购到货提单 41 互供到货提单 42 加工到货提单 43 采购领用提单
	 * 44 采购转卖提单 45 加工送出提单 46 采购转库提单 49 内供料转库提单 51 贸易单元采购提单 52 贸易单元销售提单 53
	 * 贸易单元转库提单 61 出厂转库提单 62 直发发货段提单 63 厂内转库提单 64 直发短驳段提单
	 */

	// // 接收入库作业计划
	// private static final String MESSAGE_RECEIVE_N0_STOCK_IN = "XL2201";
	//	
	// // 接收出库作业计划
	// private static final String MESSAGE_RECEIVE_N0_STOCK_OUT = "XL2202";
	//	
	// // 接收库存调整作业计划
	// private static final String MESSAGE_RECEIVE_N0_STOCK_ADJUST = "XL7002";
	//	
	// // 接收库存盘点作业计划
	// private static final String MESSAGE_RECEIVE_N0_STOCK_CHECK = "XL7001";
	//	
	// // 接收作业费用信息
	// private static final String MESSAGE_RECEIVE_N0_FEE = "XL4801";
	//	
	// // 接收费用撤销电文
	// private static final String MESSAGE_RECEIVE_N0_FEE_CANCEL = "XL4805";
	//	
	// // 接收发票抬头变更电文
	// private static final String MESSAGE_RECEIVE_N0_INVOICE_TITLE_CHANGE =
	// "XL4804";
	//	
	// // 接收运输作业计划电文
	// private static final String MESSAGE_RECEIVE_N0_TRANS = "XL2203";
	//	
	// // 接收日常公文
	// private static final String MESSAGE_RECEIVE_N0_DOCUMENT_RECEIVE =
	// "XL5205";
	// // 服务商评价电文
	// private static final String MESSAGE_RECEIVE_N0_EVALUATION = "XL5204";
	// 发送入库作业实绩电文
	private static final String MESSAGE_SEND_N0_STOCK_IN = "XZST01";

	// 发送出库作业实绩电文
	private static final String MESSAGE_SEND_N0_STOCK_OUT = "XZST02";

	// 发送库存调整实绩电文
	private static final String MESSAGE_SEND_N0_STOCK_ADJUST = "XZST03";

	// 发送出库电子验单电文
	private static final String MESSAGE_SEND_N0_STOCK_OUT_EPICK = "XZST05";
	
	//发送飞单转授权电文
	private static final String MESSAGE_SEND_N0_TRANS_FLY= "XZTR11";
	
	//发送外高桥船厂收货确认信息
	private static final String MESSAGE_SEND_NO_WAIGAOQIAO_RECEIVED = "XZST06";
	
	//发送装车清单
	private static final String MESSAGE_TRANS_LOADING_MAIN = "XZTR02";
	
	//发送到货清单
	private static final String MESSAGE_TRANS_ARRIVAL_MAIN = "XZTR04";
	
	//发送发车反馈
	private static final String MESSAGE_TRANS_START_RESULT = "XZTR03";
	
	//发送发车反馈
	private static final String MESSAGE_TRANS_RESULT = "XZTR12";
	
	//发送船舶动态反馈
	private static final String MESSAGE_TRANS_SHIP_TRENDS = "XZTR07";
	
	//发送签收反馈
	private static final String MESSAGE_TRANS_SIGN_RESULT = "XZTR05";
	
	//作业计划接收应答
	private static final String MESSAGE_TRANS_PLAN_RESULT = "XZTR06";

	// 上报车辆资料电文
	private static final String MESSAGE_SEND_N0_TRUCK_MANAGE = "XZRC04";

	// 上报船舶资料电文
	private static final String MESSAGE_SEND_N0_SHIP_MANAGE = "XZRC05";

	// 服务商开户申请信息电文
	private static final String MESSAGE_SEND_N0_PROVIDER = "XZRC01";

	// 服务商开户申请仓库信息电文
	private static final String MESSAGE_SEND_N0_PROVIDER_WAREHOUSE = "XZRC02";

	// 服务商开户申请账号信息电文
	private static final String MESSAGE_SEND_N0_PROVIDER_ACCOUNT = "XZRC03";

	// 船舶动态信息电文
	private static final String MESSAGE_SEND_N0_SHIP_TRACE = "XZFE06";

	// 通用信息修改申请电文
	private static final String MESSAGE_SEND_N0_MODIFY_APPLY = "XZRC09";

	// 上报仓储能力电文
	private static final String MESSAGE_SEND_N0_WPROVIDER_ABILITY = "XZRC07";

	// 上报船舶能力电文
	private static final String MESSAGE_SEND_N0_SHIP_ABILITY = "XZRC06";

	// 发送日常公文反馈
	private static final String MESSAGE_SEND_N0_DOCUMENT_RETURN = "XZRC08";

	// 发送发票付款申请
	private static final String MESSAGE_SEND_N0_INVOICE_APPLY = "XZFE01";

	// 发送发票撤销
	private static final String MESSAGE_SEND_N0_INVOICE_CANCEL = "XZFE02";

	// 发送费用撤销回复
	private static final String MESSAGE_SEND_N0_FEE_CANCEL_REPLY = "XZFE03";

	// 发送保单信息
	private static final String MESSAGE_SEND_N0_INSURANCE_BILL = "XZFE04";

	// 发送运输作业实绩;
	private static final String MESSAGE_SEND_N0_TRANS_REAL = "XZTR01";

	// 发送车船装载清单
	private static final String MESSAGE_SEND_N0_TRANS_LOADING = "XZFE05";
	
	// 发送车船装载清单 铁总接口 发送东方钢铁
	private static final String MESSAGE_SEND_N1_TRANS_LOADING = "XZTZ01";

	// 库存盘点实绩
	private static final String MESSAGE_SEND_N0_STOCK_CHECK = "XZST04";

	private static final String MESSAGE_SEND_N0_EPICK_PROMISEE = "XZRC10";
	
	//发送社会仓库库位变更信息电文
	private static final String MESSAGE_SEND_N0_WAREHOUSE_POSCHAN = "XZST07";
	
	//发送进厂证信息电文
	private static final String MESSAGE_SEND_ENTRY_PERMIT = "XZTR08";
	
	//发送物流质量图片电文 add by xty 2015/12/11
	private static final String MESSAGE_SEND_QUALITY_PICTURES = "XZTR13";
	
	//发送手机号注册电文 add by xty 2015/12/11
	private static final String MESSAGE_SEND_PHONE_REGISTERED = "XZTR14";
	
	//铁运车皮 装卸车报告 add wyx  
	private static final String MESSAGE_SEND_TRAINS_LOADING = "XZTR15";
	
	//铁运车皮在途动态 add wyx  
	private static final String MESSAGE_SEND_TRAINS_ON_LOAD = "XZTR16";

	//运输作业计划 add
	private static final String  MESSAGE_SEND_TRAINS_MTM_LOAD="XZXL02";
	
	//调度计划信息
	private static final String  MESSAGE_SEND_TRANS_DIS_PLAN="XZXL03";
	
	//车道信息
	private static final String  MESSAGE_SEND_WAREHOUSE_ROADNUM="XZXL04";
	
	private static final String MESSAGE_SEND_SHIP_INQ_MANAGE = "XZXL19";
	
	private static final String MESSAGE_SEND_TPL_M_SINCE_MATERIAL_WATER="XZXL20";
	
	private static final String MESSAGE_SEND_ENTRY_PERMIT_02 = "XZXL11";
	
	private static final String MESSAGE_TPL_MESSAGE_CAR_BIND_ITEM = "XZXL12";
	
	private static final String MESSAGE_SEND_CAR = "XZXL13";
	
	private static final String MESSAGE_SEND_TPL_M_SINCE_MODELS = "XZXL10";
	private static final String MESSAGE_SEND_TPL_M_CAR_SIGN = "XZXL14";
	private static final String MESSAGE_SEND_TPL_M_CHECK_RESULT = "XZXL15";
    private static final String MESSAGE_SEND_TPL_M_CAR_IN_PLACE = "XZXL16";
	private static final String MESSGAE_SEND_TPL_M_CAR_ARRIVE_USER = "XZXL17";
	private static final String MESSAGE_SEND_TPL_M_SINCE_DRIVER_INFO = "XZXL18";
	private static final String MESSAGE_SEND_TPL_M_FACT_SINCE_CONTRACT="XZXL23";
	
	private static final String MESSAGE_SEND_TPL_M_LOAD_PLAN_RESPONSE ="XZXL21";
	
	private static final String MESSAGE_SEND_TPL_M_PLAN_ALLOCATION_CAR="XZXL22";
	
	private static final String MESSAGE_SEND_TPL_M_QR_CODE_SCAN_RESULT = "XZXL24";
	
	private static final String MESSAGE_SEND_TPL_M_SERVICE_CAR_IN_PLACE = "XZXL25";
	
	private static final String MESSAGE_SEND_TPL_M_PROVIDER_CAR_SIGN ="XZXL26";
	
	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	/**
	 * *********************************仓储业务接口***************************************
	 * 
	 * 1 接收入库作业计划 2 接收出库作业计划 3 接收库存调整作业计划 4 接收库存盘点作业计划
	 * 
	 * 5 发送入库作业实绩 6 发送出库作业实绩 7 发送库存调整作业实绩 8 发送库存盘点作业实绩
	 * 
	 * 9 接收准发信息 10 接收准发红冲信息 11 接收码单红冲信息 12 发送社会仓库库位变更信息
	 * 
	 * **********************************************************************************
	 */
	
	
	// 接收合同替换信息
	public void receiveContractReplace(TplMsgContractReplace tplMsgContractReplace) throws Exception {
		System.out.println(""+tplMsgContractReplace.getResourceSign());
		try {
			if (tplMsgContractReplace.getActReplaceCount().intValue() > 0) {
				tplMsgContractReplace.setMsgContractItemSet(BeanConvertUtils.strToBean(tplMsgContractReplace.getDetail(), CLASS_CONTRACT_REPLACE_ITEM));
			} else {
				tplMsgContractReplace.setMsgContractItemSet(new HashSet());
			}

			// 处理空model
			Iterator it = tplMsgContractReplace.getMsgContractItemSet().iterator();
			while (it.hasNext()) {
				TplMsgContractItem msgContractItem = (TplMsgContractItem) it.next();
				if ("".equals(msgContractItem.getPackId())) {
					it.remove();
				}
			}
			//判断重复
			String checkRepeatSql = "select id from tpl_msg_contract_replace t where t.pre_order_num = '" 
				+ tplMsgContractReplace.getPreOrderNum()
				+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()
				+ "' and t.replace_id = '"+ tplMsgContractReplace.getReplaceId()
				+ "' and STATUS ='0' ";
			if (messageDao.queryIdBySql(checkRepeatSql) != null){
				return;
			}
			// 初始化部分值
			String queryStockInIdSql = "select id from tpl_msg_contract_replace t where t.replace_id = '"
				+ tplMsgContractReplace.getReplaceId() + "' and t.pre_order_num = '" + tplMsgContractReplace.getPreOrderNum()
				+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()
				+ "' and STATUS is null  order by ID DESC";
			Long id = messageDao.queryIdBySql(queryStockInIdSql);
			tplMsgContractReplace.setId(id);
			System.out.println("初始化合同替换tpl_msg_contract_replace--id："+id);
	
			// 查询该计划已经新增的捆包数，来判定本次接收是否结束。
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from tpl_msg_contract_item t where t.main_id = "
					+ tplMsgContractReplace.getId());
			System.out.println("查询该计划（tpl_msg_contract_item）已经新增的捆包数，来判定本次接收是否结束："+itemCount);
			// 判定重复，解决电文同步发送的问题
			if (tplMsgContractReplace.getMsgContractItemSet() != null && tplMsgContractReplace.getMsgContractItemSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = tplMsgContractReplace.getMsgContractItemSet().iterator();
				String packId = "";
				if (itDouble != null) {
					TplMsgContractItem tplMsgContractItem = (TplMsgContractItem) itDouble.next();
					packId = tplMsgContractItem.getPackId();
				}
				// 判断该计划下的捆包号是否已经入库
				Long doublePackCount=null;
				
				doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from tpl_msg_contract_item t "
						+ " where t.pack_id = '" + packId + "'   and t.main_id = " + id);
						
				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			System.out.println("messageDao.insert（tpl_msg_contract_replace）："+tplMsgContractReplace.getPreOrderNum());
			messageDao.insertMsgContractReplace(tplMsgContractReplace);

			// 如果初次新增，需要得到id
			if (id == null) {
				String queryIdSql = "select id from tpl_msg_contract_replace t where t.replace_id = '"
					+ tplMsgContractReplace.getReplaceId() + "' and t.pre_order_num = '" + tplMsgContractReplace.getPreOrderNum()
					+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()
					+ "' and STATUS is null  order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				tplMsgContractReplace.setId(id);
			}
			// 判断接收是否结束，如果结束插入业务表
			if (tplMsgContractReplace.getActReplaceCount().intValue()== tplMsgContractReplace.getMsgContractItemSet().size() + itemCount.intValue()) {
				
				String sqlString = "update tpl_msg_contract_replace t set STATUS = '0' where  t.replace_id = '"
					+ tplMsgContractReplace.getReplaceId() + "' and t.pre_order_num = '" + tplMsgContractReplace.getPreOrderNum()
					+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()+"'";
				System.out.println("sqlString(更新合同替换完成标志):"+sqlString);
				messageDao.updateContractReplaceStatus(sqlString);
				
				//调用宝盈通接口---------------------------------------------------------------------
				//主表数据解析
				TplMessageContractReplace tplMessageContractReplace=initMsgContractReplace(tplMsgContractReplace);
				//子表LIST解析
				List list = initMsgContractReplaceItem(tplMsgContractReplace);
				System.out.println("初始化结束，调用宝盈通接口方法（sendChangeContract）list.size():"+list.size());
				spotGoodsClientService.sendChangeContract(tplMessageContractReplace,list);
				//----------------------------------------------------------------------------------
				
			}
			
		} catch (Exception e) {
			String errTitle = "合同替换充当号:" + tplMsgContractReplace.getReplaceId();
			setLogMessage(e, errTitle);
		} 
	}
	
	private List initMsgContractReplaceItem(
			TplMsgContractReplace tplMsgContractReplace) {
		Set contractReplaceItem= tplMsgContractReplace.getMsgContractItemSet();
		List list =new ArrayList();
	    Iterator itr=contractReplaceItem.iterator();
	    while(itr.hasNext()){
	    	TplMsgContractItem item = (TplMsgContractItem)itr.next();
	    	TplMessageContractItem tplMessageContractItem = new TplMessageContractItem();
	    	tplMessageContractItem.setPackId(item.getPackId());
	    	tplMessageContractItem.setWeightActive(item.getWeightActive());
	    	list.add(tplMessageContractItem);
	    }
		return list;
	}

	private TplMessageContractReplace initMsgContractReplace(
			TplMsgContractReplace tplMsgContractReplace) {
		TplMessageContractReplace tplMessageContractReplace = new TplMessageContractReplace();
		tplMessageContractReplace.setReplaceId(tplMsgContractReplace.getReplaceId());
		tplMessageContractReplace.setPreOrderNum(tplMsgContractReplace.getPreOrderNum());
		tplMessageContractReplace.setPreOrderCompany(tplMsgContractReplace.getPreOrderCompany());
		tplMessageContractReplace.setRepOrderCompany(tplMsgContractReplace.getRepOrderCompany());
		tplMessageContractReplace.setRepOrderNum(tplMsgContractReplace.getRepOrderNum());
		tplMessageContractReplace.setActReplaceCount(tplMsgContractReplace.getActReplaceCount());
		tplMessageContractReplace.setActReplaceWeight(tplMsgContractReplace.getActReplaceWeight());
		tplMessageContractReplace.setMeasureId(tplMsgContractReplace.getMeasureId());
		tplMessageContractReplace.setModiDate(tplMsgContractReplace.getModiDate());
		tplMessageContractReplace.setModiPerson(tplMsgContractReplace.getModiPerson());
		tplMessageContractReplace.setResourceSign(tplMsgContractReplace.getResourceSign());
		return tplMessageContractReplace;
	}

	// 接收入库作业计划
	public void receiveStockInPlan(TplMessageStockInPlan stockInPlan) throws Exception {
		try {
			//yesimin  20150508  湛江提单号截取
			if(stockInPlan.getPickNum()!=null&&(stockInPlan.getPickNum().endsWith("_A")||stockInPlan.getPickNum().endsWith("_B")))
			{
				String[] pickNum=stockInPlan.getPickNum().split("_");
				stockInPlan.setPickNum(pickNum[0]);
			}
			if ("000000".equals(stockInPlan.getProviderId()) || "999999".equals(stockInPlan.getProviderId())
					|| "BGSA".equals(stockInPlan.getProviderId()) || stockInPlan.getProviderId() == null||stockInPlan.getProviderId().length()==4) {
				return;
			}
			
			/**
			 * 处理制造单元特殊红冲：如果件数是0，证明全部要红冲，不需要解析明细。
			 * modified by Forest 20080514
			 */
			if (stockInPlan.getPlanCount().intValue() > 0) {
				stockInPlan.setStockInItemSet(BeanConvertUtils.strToBean(stockInPlan.getDetail(), CLASS_STOCK_IN_ITEM));
			} else {
				stockInPlan.setStockInItemSet(new HashSet());
			}
			// 处理空model
			Iterator it = stockInPlan.getStockInItemSet().iterator();
			while (it.hasNext()) {
				TplMessageStockInItem stockInItem = (TplMessageStockInItem) it.next();
				if ("".equals(stockInItem.getPlanPutinId())) {
					it.remove();
				}
			}

			// 判断重复，如果是红冲，会有多次，不判定重复。
			if (OPERATION_TYPE_NEW.equals(stockInPlan.getOperationType())) {
				String checkRepeatSql = "select id from TPL_MESSAGE_STOCK_IN_PLAN where PLAN_PUTIN_ID = '"
						+ stockInPlan.getPlanPutinId() + "' and OPERATION_TYPE = '" + stockInPlan.getOperationType()
						+ "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}

			// 初始化部分值
			String queryStockInIdSql = "select id from TPL_MESSAGE_STOCK_IN_PLAN where PLAN_PUTIN_ID = '"
					+ stockInPlan.getPlanPutinId() + "' and OPERATION_TYPE = '" + stockInPlan.getOperationType()
					+ "' and STATUS  is null  order by ID DESC";
			Long id = messageDao.queryIdBySql(queryStockInIdSql);
			stockInPlan.setId(id);
			stockInPlan.setActCount(new Long(0));
			stockInPlan.setActNetWeight(new BigDecimal(0.0));
			stockInPlan.setActGrossWeight(new BigDecimal(0.0));
		//	stockInPlan.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
			stockInPlan.setUnitId(stockInPlan.getAuthorizedUnitId());
			//stockInPlan.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(stockInPlan.getUnitId()));

//	 	SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//		"searchTitleService", SearchTitleService.class);
//	      String str1 = searchTitleService.acquireHeadByCode("W001", stockInPlan.getAuthorizedUnitId());
//			stockInPlan.setUnitName(str1);
			stockInPlan.setUnitName(AcquireAgencyName.getAgencyName(stockInPlan.getUnitId()));//获取委托机构名称20110308
			//互供料业务,不包含厂外延伸库,只会在厂外库发生
			String pickType = StringUtil.trimStr(stockInPlan.getPickType());
			if(SystemConfigUtil.TPL_STOCK_PLAN_PICK_TYPE_PURCHASE_IN.contains(pickType)){
				stockInPlan.setExtendWarehouseFlag(null);
				stockInPlan.setExtendWarehouseCode(null);
			}
			
			// 查询该计划已经新增的捆包数，来判定本次接收是否结束。
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_STOCK_IN_ITEM t where t.PLAN_ID = "
					+ stockInPlan.getId());
			//获取这批电文中任一合同号和合同性质用来判断是否发给宝盈通 add by ljh 2015-08-03 23:31:48
			String packOrderType = "";
			String orderNumSub =""; 
			// 判定重复，解决电文同步发送的问题，临时过渡方案 add by zhengfei 20080128
			if (stockInPlan.getStockInItemSet() != null && stockInPlan.getStockInItemSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = stockInPlan.getStockInItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageStockInItem stockInItem = (TplMessageStockInItem) itDouble.next();
					someOnePack = stockInItem.getPackId();
					productClass = stockInItem.getOrderNum().substring(0, 1);
					packOrderType = stockInItem.getPackOrderType();//判断是否发给BYT
					orderNumSub = stockInItem.getOrderNum().substring(2, 3);//判断是否发给BYT
				}
				// 判断该计划下的捆包号是否已经入库
				Long doublePackCount=null;
				if(OPERATION_TYPE_APPLEND.equals(stockInPlan.getOperationType())){
					doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_STOCK_IN_ITEM t "
							+ " where t.plan_putin_id = '" + stockInPlan.getPlanPutinId() + "'   and t.PACK_ID = '" + someOnePack + "'"
							+ " and t.PRODUCT_CLASS = '" + productClass + "'");
				}
				else{
				 doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_STOCK_IN_ITEM t "
						+ " where t.PLAN_ID = " + stockInPlan.getId() + "   and t.PACK_ID = '" + someOnePack + "'"
						+ " and t.PRODUCT_CLASS = '" + productClass + "'");
				}
				
				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			messageDao.insertTplStockInPlan(stockInPlan);

			// 如果初次新增，需要得到id
			if (id == null) {
				String queryIdSql = "select id from TPL_MESSAGE_STOCK_IN_PLAN where PLAN_PUTIN_ID = '" + stockInPlan.getPlanPutinId()
						+ "' and OPERATION_TYPE = '" + stockInPlan.getOperationType() + "'  and STATUS is null order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				stockInPlan.setId(id);
			}

			// 判断计划是否结束，如果结束插入业务表
			if (stockInPlan.getPlanCount().intValue() == stockInPlan.getStockInItemSet().size() + itemCount.intValue()) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_IN_PLAN set STATUS = '0' where  PLAN_PUTIN_ID = '"
						+ stockInPlan.getPlanPutinId() + "'  and OPERATION_TYPE = '" + stockInPlan.getOperationType() + "'");

				
				/**
				 * 新增处理
				 * 属地系统(制造单元)红冲后的计划重新下发处理:如果是制造单元新增的计划，则如果本次新增的计划3PL已经存在，则证明不在本次计划里边的捆包需要红冲的。
				 */
				if (OPERATION_TYPE_NEW.equals(stockInPlan.getOperationType())) {
					String checkPlanExist = "select id from TPL_STOCK_IN_PLAN where PLAN_PUTIN_ID = '" + stockInPlan.getPlanPutinId()
							+ "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doStockInPlanRedOper(stockInPlan, "10");
					} else {
						messageDao.insertTplStockInByBatch(stockInPlan);
						
						//入库线材和管坯-入库端
						messageDao.checkTplStockInPlanPick(stockInPlan);
					}
					//调用宝盈通接口-入库计划-sendInPlan added ljh 2015年7月16日 13:33:30-----------------
					//主表数据解析
					try {
						if(packOrderType.startsWith("QY")||orderNumSub.equals("J")||orderNumSub.equals("W")){
							stockInPlan.setOperationType("10");
							TplMessageBytStockInPlan tplMessageBytStockInPlan=initStockInPlan(stockInPlan);
							//子表LIST解析
							List list = initStockInItem(stockInPlan);
							System.out.println("LIST OVER!");
							spotGoodsClientService.sendInPlan(tplMessageBytStockInPlan,list);
						}
					} catch (Exception e) {
						String errTitle = "调用宝盈通接口-入库计划（新增）:" + stockInPlan.getPlanPutinId();
						setLogMessage(e, errTitle);
					}
					
					//----------------------------------------------------------------------------------
				} else if (OPERATION_TYPE_RED.equals(stockInPlan.getOperationType())) {
					/**
					 * 处理红冲
					 * 1.属地系统的红冲：是计划全部撤销以后，再重新上来正确的计划，不支持单个材料撤销-----提单类型：6开头的。
					 * 2.管控系统的红冲：支持单个材料的红冲-----提单类型：除6开头的提单类型以外的提单
					 * 
					 */
					if ("6".equals(stockInPlan.getPickType().substring(0, 1))||"7".equals(stockInPlan.getPickType().substring(0, 1))) {
						messageDao.doStockInPlanRedOper(stockInPlan, "00");
					} else {
						messageDao.doStockInPlanRedOper(stockInPlan, null);
					}
					try {
						//调用宝盈通接口-入库计划-sendInPlan added ljh 2015年7月16日 13:33:30-----------------
						//主表数据解析
						if(packOrderType.startsWith("QY")||orderNumSub.equals("J")||orderNumSub.equals("W")){
							stockInPlan.setOperationType("00");
							TplMessageBytStockInPlan tplMessageBytStockInPlan=initStockInPlan(stockInPlan);
							//子表LIST解析
							List list = initStockInItem(stockInPlan);
							spotGoodsClientService.sendInPlan(tplMessageBytStockInPlan,list);
						}
					} catch (Exception e) {
						String errTitle = "调用宝盈通接口-入库计划(红冲):" + stockInPlan.getPlanPutinId();
						setLogMessage(e, errTitle);
					}
					
					/**
					 * 强制结案 属地系统的强制结案：强制结案的材料是需要保留的，不在结案内的材料全部撤销，操作类型是90开头。
					 */
				} else if (OPERATION_FORCE_OVER.equals(stockInPlan.getOperationType())) {
					//烟宝按量强制结案入库计划，业务表强制结案
					if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(stockInPlan.getUnitId())
							&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(stockInPlan.getPlanParticle())) {
						messageDao.updatePlanStatus("update TPL_STOCK_IN_PLAN set STATUS = '2' where PLAN_PUTIN_ID ='"
								+ stockInPlan.getPlanPutinId() + "'");
					} else {
						messageDao.doStockInPlanRedOper(stockInPlan, "10");
					}
				} else if (OPERATION_TYPE_APPLEND.equals(stockInPlan.getOperationType())) {
						String checkPlanExist = "select id from TPL_STOCK_IN_PLAN where PLAN_PUTIN_ID = '" + stockInPlan.getPlanPutinId()+ "'";
						Long updateId = messageDao.queryIdBySql(checkPlanExist);
					if (updateId!= null) {
						/**
						 * 追加操作:1.插入捆包数据
						 *         2.更新主表总量
						 */
						messageDao.insertTplStockInByApplend(stockInPlan,"20",updateId);//插入捆包并更新
					} else {
						messageDao.insertTplStockInByApplend(stockInPlan,"10",null);//插入主表子表
					}
				}
				TplMessageTransPlanResult tPlanResult=new TplMessageTransPlanResult();
				tPlanResult.setCreateTime(new Date());
				tPlanResult.setOprationType(stockInPlan.getOperationType());
				tPlanResult.setRecordeId(stockInPlan.getPlanPutinId());
				tPlanResult.setRecordeTime(new Date());
				tPlanResult.setRecordeType("20");
				tPlanResult.setRemark("");
				this.sendTransPlanResult(tPlanResult);//发送计划应答电文 
				System.out.println("入库计划号：======"+stockInPlan.getPlanPutinId());
			}
			
			
		} catch (Exception e) {
			String errTitle = "入库作业计划:" + stockInPlan.getPlanPutinId();
			setLogMessage(e, errTitle);
		} 
	}
	private List initStockInItem(TplMessageStockInPlan stockInPlan) throws Exception {
		//查询该入库计划下所有明细，构成LIST发给BYT
	
		List list = messageDao.queryTplMessageStockInItem(stockInPlan);
		List listTempList = new ArrayList();
		System.out.println("ljhsee-list:"+list.toString());
		if(list!=null&&list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Map item = (Map) list.get(i);
		    	TplMessageBytStockInItem tplMessageBytStockInItem = new TplMessageBytStockInItem();
		    	tplMessageBytStockInItem.setId(getLongValue(item.get("id").toString()));
		    	tplMessageBytStockInItem.setPlanId(getLongValue(item.get("planId").toString()));
		    	tplMessageBytStockInItem.setPackId((String)item.get("packId"));
		    	tplMessageBytStockInItem.setNetWeight(getDoubleValue(item.get("netWeight").toString()));
		    	tplMessageBytStockInItem.setGrossWeight(getDoubleValue(item.get("grossWeight").toString()));
		    	tplMessageBytStockInItem.setUnitCount(getLongValue(item.get("unitCount").toString()));
		    	tplMessageBytStockInItem.setCreateDate(new Date());
		    	tplMessageBytStockInItem.setSpecDesc((String)item.get("specDesc"));
		    	tplMessageBytStockInItem.setOrderNum((String)item.get("orderNum"));
		    	tplMessageBytStockInItem.setReadyNum((String)item.get("readyNum"));
		    	tplMessageBytStockInItem.setProductName((String)item.get("productName"));//品名
		    	tplMessageBytStockInItem.setCompanyCode((String)item.get("companyCode"));
		    	tplMessageBytStockInItem.setProducingAreaName((String)item.get("producingAreaName"));//产地
		    	System.out.println("producingAreaName:::"+(String)item.get("producingAreaName"));
		    	tplMessageBytStockInItem.setShopsignName((String)item.get("shopsignName"));//钢种
		    	System.out.println("shopsignName:::"+(String)item.get("shopsignName"));
		    	listTempList.add(tplMessageBytStockInItem);
		    }
		}
		return listTempList;
	}
	private BigDecimal getBigDecimalValue(Object obj) {
		if (obj == null) {
			return new BigDecimal(0);
		} else {
			return (BigDecimal) obj;
		}
	}
	/*private Double getDoubleValue(BigDecimal value) {
		if (value != null) {
			return Double.valueOf(value.toString());
		} else {
			return new Double("0");
		}
	}*/
	private Double getDoubleValue(String value) {
		if (value != null) {
			return Double.valueOf(value);
		} else {
			return new Double("0");
		}
	}
	public Long getLongValue(String value) {
		if (value != null) {
			return Long.valueOf(value);
		} else {
			return new Long(0);
		}
	}
	private TplMessageBytStockInPlan initStockInPlan(TplMessageStockInPlan stockInPlan) {
		System.out.println("stockInPlan.getWproviderId():"+stockInPlan.getWproviderId());
		TplMessageBytStockInPlan tplMessageBytStockInPlan = new TplMessageBytStockInPlan();
		tplMessageBytStockInPlan.setUnitId(stockInPlan.getUnitId());
		tplMessageBytStockInPlan.setManuId(stockInPlan.getManuId());
		tplMessageBytStockInPlan.setPickNum(stockInPlan.getPlanPutinId());
		tplMessageBytStockInPlan.setCarrierCode(stockInPlan.getCarrierCode());
		tplMessageBytStockInPlan.setCarrierName(stockInPlan.getCarrierName());
		tplMessageBytStockInPlan.setPlanCount(stockInPlan.getPlanCount());
		tplMessageBytStockInPlan.setOperationType(stockInPlan.getOperationType());
		tplMessageBytStockInPlan.setDept(stockInPlan.getWproviderId());
		tplMessageBytStockInPlan.setCreateDate(new Date());
		return tplMessageBytStockInPlan;
	}
	/**
	 * 记录错误日至
	 * 
	 * @param e
	 * @param errTitle
	 * @throws Exception
	 */
	private void setLogMessage(Exception e, String errTitle) throws Exception {
		TplMessageLog messageLog = new TplMessageLog();
		messageLog.setErrTitle(errTitle);
		messageLog.setErrType(MessageDao.LOG_ERR_TYPE_GUFEN);
		messageLog.setCreateDate(new Date());
		messageLog.setOperFlag(MessageDao.LOG_ERR_OPER_UNDO);
		messageLog.setErrContent("");
		StringBuffer err = new StringBuffer("");
		err.append(e.toString() + "\n");
		int total = e.getStackTrace().length;
		for (int i = 0; i < total; i++) {
			if (i < 6) {
				err.append(e.getStackTrace()[i] + "\n");
			} else {
				break;
			}
		}
		messageLog.setErrContent(err.toString());
		messageDao.logError(messageLog);
	}
	private void setLogMessage(String errTitle,String errContent) throws Exception {
		TplMessageLog messageLog = new TplMessageLog();
		messageLog.setErrTitle(errTitle);
		messageLog.setErrType(MessageDao.LOG_ERR_TYPE_GUFEN);
		messageLog.setCreateDate(new Date());
		messageLog.setOperFlag(MessageDao.LOG_ERR_OPER_UNDO);
		messageLog.setErrContent(errContent);
		messageDao.logError(messageLog);
	}

	// 接收出库作业计划主信息
	public void receiveStockOutPlan(TplMessageStockOutPlan stockOutPlan) throws Exception {
		try {
			System.out.println("接收出库作业计划主信息开始"+new Date()+stockOutPlan.getBillId());
			// yesimin  20150508  湛江提单号截取
			if(stockOutPlan.getPickNum()!=null&&(stockOutPlan.getPickNum().endsWith("_A")||stockOutPlan.getPickNum().endsWith("_B")))
			{
				String[] pickNum=stockOutPlan.getPickNum().split("_");
				stockOutPlan.setPickNum(pickNum[0]);
			}

			if ("000000".equals(stockOutPlan.getProviderId()) || "999999".equals(stockOutPlan.getProviderId())
					|| "BGSA".equals(stockOutPlan.getProviderId())|| stockOutPlan.getProviderId() == null||stockOutPlan.getProviderId().length()==4 ) {
				return;
			}
			
			/**
			 * 处理制造单元特殊红冲：如果件数是0，证明全部要红冲，不需要解析明细。
			 * modified by Forest 20080514
			 */
			if (stockOutPlan.getPlanCount().intValue() > 0) {
				stockOutPlan.setStockOutItemSet(BeanConvertUtils.strToBean(stockOutPlan.getDetail(), CLASS_STOCK_OUT_ITEM));
			} else {
				stockOutPlan.setStockOutItemSet(new HashSet());
			}
			
			
			// 处理空model
			Iterator it = stockOutPlan.getStockOutItemSet().iterator();
			while (it.hasNext()) {
				TplMessageStockOutItem stockOutItem = (TplMessageStockOutItem) it.next();
				if ("".equals(stockOutItem.getPlanPutoutId())) {
					it.remove();
				}
			}

			// 判断重复，如果是红冲，会有多次，不判定重复。
			if (OPERATION_TYPE_NEW.equals(stockOutPlan.getOperationType())) {
				String checkRepeatSql = "select id from TPL_MESSAGE_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "' " + " and OPERATION_TYPE = '" + stockOutPlan.getOperationType()
						+ "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}

			// 初始化部分值
			String queryStockOutIdSql = "select id from TPL_MESSAGE_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
					+ stockOutPlan.getPlanPutoutId() + "' and  OPERATION_TYPE = '" + stockOutPlan.getOperationType()
					+ "' and STATUS is null  order by ID DESC";
			Long id = messageDao.queryIdBySql(queryStockOutIdSql);
			stockOutPlan.setId(id);
			stockOutPlan.setActCount(new Long(0));
			stockOutPlan.setActNetWeight(new BigDecimal(0.0));
			stockOutPlan.setActGrossWeight(new BigDecimal(0.0));
			//stockOutPlan.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
			stockOutPlan.setUnitId(stockOutPlan.getAuthorizedUnitId());
			//stockOutPlan.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(stockOutPlan.getUnitId()));
//			SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//					"searchTitleService", SearchTitleService.class);
//				      String str1 = searchTitleService.acquireHeadByCode("W001", stockOutPlan.getAuthorizedUnitId())==null?"":searchTitleService.acquireHeadByCode("W001", stockOutPlan.getAuthorizedUnitId());
//				      stockOutPlan.setUnitName(str1);
			stockOutPlan.setUnitName(AcquireAgencyName.getAgencyName(stockOutPlan.getUnitId()));//获取委托机构名称
			
			// 查询该计划已经新增的捆包数，来判定本次接收是否结束
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_STOCK_OUT_ITEM t where t.PLAN_ID = "
					+ stockOutPlan.getId());
			//互供料业务,不包含厂外延伸库,只会在厂外库发生
			String pickType = stockOutPlan.getPickType();
			if(SystemConfigUtil.TPL_STOCK_PLAN_PICK_TYPE_PURCHASE_OUT.contains(pickType)){
				stockOutPlan.setExtendWarehouseFlag(null);
				stockOutPlan.setExtendWarehouseCode(null);
			}

			// 判定重复，解决电文同步发送的问题，临时过渡方案 add by zhengfei 20080128
			if (stockOutPlan.getStockOutItemSet() != null && stockOutPlan.getStockOutItemSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = stockOutPlan.getStockOutItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageStockOutItem stockOutItem = (TplMessageStockOutItem) itDouble.next();
					someOnePack = stockOutItem.getPackId();
					productClass = stockOutItem.getOrderNum().substring(0, 1);
				}
				// 判断该计划下的捆包号是否已经入库
				Long doublePackCount=null;
				if(OPERATION_TYPE_APPLEND.equals(stockOutPlan.getOperationType())){
					
					doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_STOCK_OUT_ITEM t "
							+ " where t.plan_putout_id = '" + stockOutPlan.getPlanPutoutId() + "'   and t.PACK_ID = '" + someOnePack + "'"
							+ " and t.PRODUCT_CLASS = '" + productClass + "'");
				}
				else{
					 doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_STOCK_OUT_ITEM t "
						+ " where t.PLAN_ID = " + stockOutPlan.getId() + "   and t.PACK_ID = '" + someOnePack + "'"
						+ " and t.PRODUCT_CLASS = '" + productClass + "'");
				}
				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			System.out.println("stockOutPlan:"+stockOutPlan.getBillId());
			messageDao.insertTplStockOutPlan(stockOutPlan);

			// 如果初次新增，需要得到id
			if (id == null) {
				String queryIdSql = "select id from TPL_MESSAGE_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "' and  OPERATION_TYPE = '" + stockOutPlan.getOperationType()
						+ "' and STATUS is null  order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				stockOutPlan.setId(id);
			}

			// 判断计划是否结束，如果结束插入业务表
			if (stockOutPlan.getPlanCount().intValue() == stockOutPlan.getStockOutItemSet().size() + itemCount.intValue()) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_OUT_PLAN set STATUS = '0' where  PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "'  and OPERATION_TYPE = '" + stockOutPlan.getOperationType() + "'");
				
				Long planCount = new Long(0);
				if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(stockOutPlan.getUnitId())) {
					String checkAmountSql = "select count(ID) from TPL_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "' and PLAN_PARTICLE = '" + SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT
						+ "' and ACT_COUNT <> 0 and STATUS <> '" + SystemConstants.STOCK_OUT_PLAN_STATUS_WILLDO + "'";
					planCount = messageDao.queryIdBySql(checkAmountSql);
				}
				
				/**
				 * 新增处理
				 * 属地系统(制造单元)红冲后的计划重新下发处理:如果是制造单元新增的计划，则如果本次新增的计划3PL已经存在，则证明不在本次计划里边的捆包需要红冲的。
				 */
				if (OPERATION_TYPE_NEW.equals(stockOutPlan.getOperationType())) {
					String checkPlanExist = "select id from TPL_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
							+ stockOutPlan.getPlanPutoutId() + "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doStockOutPlanRedOper(stockOutPlan, "10");
					} else {
						//烟宝出厂按量提单回退再次新增按件提单，业务表出库计划已经执行，业务表不收出库计划
						if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(stockOutPlan.getUnitId())
								&& planCount.intValue() > 0
								&& SystemConfigUtil.BAOSTEEL_BGSQ_PIECE.equals(stockOutPlan.getPlanParticle())
								&& "62".equals(stockOutPlan.getPickType())) {
							
						} else {
							
							messageDao.insertTplStockOutByBatch(stockOutPlan);
						}
					}
				} else if (OPERATION_TYPE_RED.equals(stockOutPlan.getOperationType())) {
					/**
					 * 处理红冲
					 * 1.属地系统的红冲：是计划全部撤销以后，再重新上来正确的计划，不支持单个材料撤销-----提单类型：6开头的。
					 * 2.管控系统的红冲：支持单个材料的红冲-----提单类型：除6开头的提单类型以外的提单
					 * 
					 */
					if ("6".equals(stockOutPlan.getPickType().substring(0, 1))||"7".equals(stockOutPlan.getPickType().substring(0, 1))) {
						messageDao.doStockOutPlanRedOper(stockOutPlan, "00");
					} else {
						messageDao.doStockOutPlanRedOper(stockOutPlan, null);
					}
				} else if (OPERATION_FORCE_OVER.equals(stockOutPlan.getOperationType())) {
					/**
					 * 强制结案 属地系统的强制结案：强制结案的材料是需要保留的，不在结案内的材料全部撤销，操作类型是90开头。
					 */
					//烟宝厂内转库提单强制结案或者出厂按量提单回退再次强制结案按件提单，业务表出库计划已经执行，业务表强制结案
					if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(stockOutPlan.getUnitId())
							&& planCount.intValue() > 0
							&& (SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(stockOutPlan.getPlanParticle())
									&& "63".equals(stockOutPlan.getPickType())
									|| SystemConfigUtil.BAOSTEEL_BGSQ_PIECE.equals(stockOutPlan.getPlanParticle())
									&& "62".equals(stockOutPlan.getPickType()))) {
						messageDao.updatePlanStatus("update TPL_STOCK_OUT_PLAN set MEMO = '', STATUS = '2' where PLAN_PUTOUT_ID = '"
								+ stockOutPlan.getPlanPutoutId() + "'");
					} else {
						messageDao.doStockOutPlanRedOper(stockOutPlan, "10");
					}
				}else if (OPERATION_TYPE_APPLEND.equals(stockOutPlan.getOperationType())) {
					String checkPlanExist = "select id from TPL_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "'";
					
					Long updateId = messageDao.queryIdBySql(checkPlanExist);
					
					if (updateId!= null) {
						/**
						 * 追加操作:1.插入捆包数据
						 *         2.更新主表总量
						 */
						//烟宝按量增量出库计划，业务表出库计划已经执行，业务表不收出库计划
						if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(stockOutPlan.getUnitId())
								&& planCount.intValue() > 0
								&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(stockOutPlan.getPlanParticle())) {
							
						} else {
							messageDao.insertTplStockOutByApplend(stockOutPlan,"20",updateId);//插入捆包并更新
						}
					} else {
						messageDao.insertTplStockOutByApplend(stockOutPlan,"10",null);//插入主表子表
					}
				}
				TplMessageTransPlanResult tPlanResult=new TplMessageTransPlanResult();
				tPlanResult.setCreateTime(new Date());
				tPlanResult.setOprationType(stockOutPlan.getOperationType());
				tPlanResult.setRecordeId(stockOutPlan.getPlanPutoutId());
				tPlanResult.setRecordeTime(new Date());
				tPlanResult.setRecordeType("10");
				tPlanResult.setRemark("");
				this.sendTransPlanResult(tPlanResult);

			}
			
			
		} catch (Exception e) {
			String errTitle = "出库作业计划:" + stockOutPlan.getPlanPutoutId();
			setLogMessage(e, errTitle);
			
		}
	}

	// 接收库存调整作业计划
	public void receiveStockAdjust(TplMessageStockAdjust stockAdjust) throws Exception {
		try {
			stockAdjust.setTplStockAdjustItems(BeanConvertUtils.strToBean(stockAdjust.getDetail(), CLASS_STOCK_ADJUST_ITEM));
			// 处理空model
			Iterator it = stockAdjust.getTplStockAdjustItems().iterator();
			while (it.hasNext()) {
				TplMessageStockAdjustItem stockOutItem = (TplMessageStockAdjustItem) it.next();
				if ("".equals(stockOutItem.getAdjustNo())) {
					it.remove();
				}
			}
			// 初始化部分值
			String queryStockAdjustIdBySql = "select ID from TPL_MESSAGE_STOCK_ADJUST where ADJUST_NO = '" + stockAdjust.getAdjustNo()
					+ "'";
			//stockAdjust.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
			stockAdjust.setUnitId(stockAdjust.getAuthorizedUnitId());
			stockAdjust.setId(messageDao.queryIdBySql(queryStockAdjustIdBySql));
			//stockAdjust.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(stockAdjust.getUnitId()));
//			SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//					"searchTitleService", SearchTitleService.class);
//				      String str1 = searchTitleService.acquireHeadByCode("W001", stockAdjust.getAuthorizedUnitId())==null?"":searchTitleService.acquireHeadByCode("W001", stockAdjust.getAuthorizedUnitId());
//				      stockAdjust.setUnitName(str1);
			stockAdjust.setUnitName(AcquireAgencyName.getAgencyName(stockAdjust.getUnitId()));//获取委托机构名称20110308
			
			stockAdjust.setStatus(null);
			messageDao.insertTplStockAdjust(stockAdjust);
			String overCheckSql = "select m.ID from  TPL_MESSAGE_STOCK_ADJUST m where m.ADJUST_NO = '" + stockAdjust.getAdjustNo()
					+ "' and " + " (select count(t.ID) from TPL_MESSAGE_STOCK_ADJUST_ITEM t " + " where t.ADJUST_NO = '"
					+ stockAdjust.getAdjustNo() + "') =  " + stockAdjust.getItemCount();
			// 判断计划是否结束，如果结束插入业务表
			if (messageDao.checkPlanOver(overCheckSql)) {
				messageDao.insertTplStockAdjustByBatch(stockAdjust.getAdjustNo());
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_ADJUST set STATUS = '0' where ADJUST_NO = '"
						+ stockAdjust.getAdjustNo() + "'");
			}
		} catch (Exception e) {
			String errTitle = "库存调整作业计划:" + stockAdjust.getAdjustNo();
			setLogMessage(e, errTitle);
		}
	}

	// 接收库存盘点作业计划
	public void receiveStockCheck(TplMessageStockCheck stockCheck) throws Exception {
		try {
			
			/**
			 * 管控不下发捆包
			 * modified by zhengfei 20080602
			 */
			stockCheck.setTplMessageStockCheckItems(new HashSet());
			
			// 处理空model
			Iterator it = stockCheck.getTplMessageStockCheckItems().iterator();
			while (it.hasNext()) {
				TplMessageStockCheckItem stockCheckItem = (TplMessageStockCheckItem) it.next();
				if ("".equals(stockCheckItem.getCheckNo())) {
					it.remove();
				}
			}
			// 初始化部分值
			String queryStockCheckIdBySql = "select id from TPL_MESSAGE_STOCK_CHECK where CHECK_NO = '" + stockCheck.getCheckNo()
					+ "'";
			//stockCheck.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
			stockCheck.setUnitId(stockCheck.getAuthorizedUnitId());
			stockCheck.setId(messageDao.queryIdBySql(queryStockCheckIdBySql));
			//stockCheck.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(stockCheck.getUnitId()));
			
//			SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//					"searchTitleService", SearchTitleService.class);
//				      String str1 = searchTitleService.acquireHeadByCode("W001", stockCheck.getAuthorizedUnitId())==null?"":searchTitleService.acquireHeadByCode("W001", stockCheck.getAuthorizedUnitId());
//				      stockCheck.setUnitName(str1);
			stockCheck.setUnitName(AcquireAgencyName.getAgencyName(stockCheck.getUnitId()));//获取委托机构名称20110308
			
			stockCheck.setStatus(null);
			messageDao.insertTplStockCheck(stockCheck);
			String overCheckSql = "select m.ID from  TPL_MESSAGE_STOCK_CHECK m where m.CHECK_NO = '" + stockCheck.getCheckNo()
					+ "' and " + " (select count(t.ID) from TPL_MESSAGE_STOCK_CHECK_ITEM t" + " where t.CHECK_NO = '"
					+ stockCheck.getCheckNo() + "') = " + stockCheck.getItemCount();
			// 判断计划是否结束，如果结束插入业务表
			if (messageDao.checkPlanOver(overCheckSql)) {
				messageDao.insertTplStockCheckByBatch(stockCheck.getCheckNo());
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_CHECK set STATUS = '0' where CHECK_NO = '"
						+ stockCheck.getCheckNo() + "'");
			}
		} catch (Exception e) {
			String errTitle = "库存盘点作业计划:" + stockCheck.getCheckNo();
			setLogMessage(e, errTitle);
		}
	}
	
	// 接收厂外延伸库的二次准发电文
	public void receiveMessageReadyData(LgsMessageReadyData messageReadyData) throws Exception {
		try {
			messageReadyData.setMessageReadyMaterialSet(BeanConvertUtils.strToBean(messageReadyData.getDetail(), CLASS_MESSAGE_READY_MATERIAL));

			// 处理空model
			Iterator it = messageReadyData.getMessageReadyMaterialSet().iterator();
			while (it.hasNext()) {
				LgsMessageReadyMaterial messageReadyMaterial = (LgsMessageReadyMaterial) it.next();
				if ("".equals(messageReadyMaterial.getPackNum())) {
					it.remove();
				}
			}

			// 判断准发重复
			String checkRepeatSql = "select id from LGS_MESSAGE_READY_DATA where READY_NUM = '"
					+ messageReadyData.getReadyNum() + "' and READY_STATUS = '0'";
			if (messageDao.queryIdBySql(checkRepeatSql) != null)
				return;
			//充当合同后的二次准发，不接收
			String checkCDSql = "select id from tpl_msg_contract_replace where rep_order_num = '"
				+ messageReadyData.getOrderNum() + "'";
		    if (messageDao.queryIdBySql(checkCDSql) != null)
			    return;
			

			// 初始化部分值
			String queryMessageReadyDataIdSql = "select id from LGS_MESSAGE_READY_DATA where READY_NUM = '"
					+ messageReadyData.getReadyNum() + "' and READY_STATUS is null order by ID DESC";
			Long id = messageDao.queryIdBySql(queryMessageReadyDataIdSql);
			messageReadyData.setId(id);

			// 查询该准发已经新增的材料数，来判定本次接收是否结束。
			Long materialCount = messageDao.queryIdBySql("select count(t.ID) from LGS_MESSAGE_READY_MATERIAL t where t.READY_DATA_ID = "
					+ messageReadyData.getId());

			// 判定重复，解决电文同步发送的问题，临时过渡方案 add by zhengfei 20080128
			if (messageReadyData.getMessageReadyMaterialSet() != null && messageReadyData.getMessageReadyMaterialSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = messageReadyData.getMessageReadyMaterialSet().iterator();
				String someOnePack = "";
				if (itDouble != null) {
					LgsMessageReadyMaterial messageReadyMaterial = (LgsMessageReadyMaterial) itDouble.next();
					someOnePack = messageReadyMaterial.getPackNum();
				}
				// 判断该准发下的捆包号是否已经入库
				Long doublePackCount = null;
				doublePackCount = messageDao.queryIdBySql("select count(t.ID) from LGS_MESSAGE_READY_MATERIAL t "
						+ " where t.READY_DATA_ID = " + messageReadyData.getId() + " and t.PACK_NUM = '" + someOnePack + "'");
				
				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;
			}

			messageDao.insertLgsMessageReadyData(messageReadyData);

			// 如果初次新增，需要得到id
			if (id == null) {
				String queryIdSql = "select id from LGS_MESSAGE_READY_DATA where READY_NUM = '" + messageReadyData.getReadyNum()
						+ "' and READY_STATUS is null order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				messageReadyData.setId(id);
			}

			// 判断准发是否结束，如果结束插入库存表
			if (messageReadyData.getNReadyTot().intValue() == messageReadyData.getMessageReadyMaterialSet().size() + materialCount.intValue()) {
				//接收准发完全后修改准发状态
				messageDao.updatePlanStatus("update LGS_MESSAGE_READY_DATA set READY_STATUS = '0' where READY_NUM = '"
						+ messageReadyData.getReadyNum() + "'");
				
				if(SystemConfigUtil.judgeBaosteelExtend(messageReadyData.getUnitId())){
					//委托机构0001准发是二次准发，不是一次准发，只有存在更新材料，不应该存在新增材料
					Iterator messageReadyMaterialIterator = messageReadyData.getMessageReadyMaterialSet().iterator();
					while (messageReadyMaterialIterator.hasNext()) {
						LgsMessageReadyMaterial messageReadyMaterial = (LgsMessageReadyMaterial) messageReadyMaterialIterator.next();
						SearchStockModel searchStockModel = new SearchStockModel();
						searchStockModel.setPackId(messageReadyMaterial.getPackNum());
						searchStockModel.setProductClass(messageReadyData.getOrderNum().substring(0, 1));
						searchStockModel.setUnitId(messageReadyData.getUnitId());
						searchStockModel.setManuId(messageReadyData.getManuId());
						searchStockModel.setExtendWarehouseFlag("10");
						Long stockId = messageDao.queryStockId(searchStockModel);
						if (stockId != null) {
							messageReadyMaterial.setId(stockId);
						}else{
							return;
						}
					}
				}
				
				//准发入库操作
				messageDao.doReadyTplStockOperation(messageReadyData);
			}
		} catch (Exception e) {
			String errTitle = "准发电文:" + messageReadyData.getReadyNum();
			setLogMessage(e, errTitle);
		} 
	}
	
	// 接收厂外延伸库准发红冲电文和曲向硅钢预合同的厂外库准发红冲电文
	public void receiveMessageReadyRed(LgsMessageReadyRed messageReadyRed) throws Exception {
		try {
			String queryIdSql = "select id from LGS_MESSAGE_READY_RED where PACK_NUM = '"
			+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
			+ messageReadyRed.getReadyNum() + "' and ORDER_NUM = '"
			+ messageReadyRed.getOrderNum() + "' and COMPANY_CODE = '"
			+ messageReadyRed.getCompanyCode() + "' and MANU_ID = '"
			+ messageReadyRed.getManuId() + "' and UNIT_ID = '"
			+ messageReadyRed.getUnitId() + "' order by ID DESC";
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id == null){
				messageDao.insertLgsMessageReadyRed(messageReadyRed);
//				//库存操作 捆包属性产成品变在制品
//				messageDao.updatePlanStatus("update TPL_STOCK set PRODUCT_PROPERTY = '10', PACK_STATUS = '00', CONFIRM_STATUS = '10', MODIFY_DATE=SYSDATE where PACK_ID = '"
//						+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
//						+ messageReadyRed.getReadyNum()	+ "' and PRODUCT_CLASS = '"
//						+ messageReadyRed.getOrderNum().substring(0, 1) + "' and COMPANY_CODE = '"
//						+ messageReadyRed.getCompanyCode() + "' and EXTEND_WAREHOUSE_FLAG = '10' "
//						+ " and STOCK_FLAG = '10' and UNIT_ID = '" + messageReadyRed.getUnitId() + "'");
				if(SystemConfigUtil.judgeBaosteelExtend(messageReadyRed.getUnitId())){
					//库存操作 捆包属性产成品变在制品
					messageDao.updatePlanStatus("update TPL_STOCK set PRODUCT_PROPERTY = '10', PACK_STATUS = '00', CONFIRM_STATUS = '10', MODIFY_DATE=SYSDATE where PACK_ID = '"
							+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
							+ messageReadyRed.getReadyNum()	+ "' and PRODUCT_CLASS = '"
							+ messageReadyRed.getOrderNum().substring(0, 1) + "' and COMPANY_CODE = '"
							+ messageReadyRed.getCompanyCode() + "' and EXTEND_WAREHOUSE_FLAG = '10' "
							+ " and STOCK_FLAG = '10' and UNIT_ID = '" + messageReadyRed.getUnitId() + "'");
				} else if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(messageReadyRed.getUnitId()) || SystemConfigUtil.BAOSTEEL_BGTM.equals(messageReadyRed.getUnitId())) {
					//库存操作 删除材料
					messageDao.updatePlanStatus("delete from TPL_STOCK where PRODUCT_PROPERTY = '20' and PACK_STATUS = '48' and STOCK_FLAG = '10' " 
							+ " and EXTEND_WAREHOUSE_FLAG = '10' and CONFIRM_STATUS = '10' and PACK_ID = '"
							+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
							+ messageReadyRed.getReadyNum()	+ "' and PRODUCT_CLASS = '"
							+ messageReadyRed.getOrderNum().substring(0, 1) + "' and COMPANY_CODE = '"
							+ messageReadyRed.getCompanyCode() + "' and UNIT_ID = '" + messageReadyRed.getUnitId() + "'");
				}
			}
		} catch (Exception e) {
			String errTitle = "准发红冲电文:" + messageReadyRed.getReadyNum();
			setLogMessage(e, errTitle);
		} 
	}
	
	// 接收码单红冲电文
	public void receiveMessageStackRed(LgsMessageStackRed messageStackRed) throws Exception {
		try {
			String queryIdSql = "select id from LGS_MESSAGE_STACK_RED where PICK_NUM = '"
				+ messageStackRed.getPickNum() + "' and PACK_ID = '"
				+ messageStackRed.getPackId() + "' and ORDER_NUM = '"
				+ messageStackRed.getOrderNum() + "' and STACKING_NUM = '"
				+ messageStackRed.getStackingNum() + "' and COMPANY_CODE = '"
				+ messageStackRed.getCompanyCode() + "' and MANU_ID = '"
				+ messageStackRed.getManuId() + "' and UNIT_ID = '"
				+ messageStackRed.getUnitId() + "' order by ID DESC";
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id == null){
				messageDao.insertLgsMessageStackRed(messageStackRed);
//				messageDao.doStockOperation(messageStackRed);
				messageDao.doStackRedOperation(messageStackRed);
			}
			
		} catch (Exception e) {
			String errTitle = "码单红冲电文:" + messageStackRed.getStackingNum();
			setLogMessage(e, errTitle);
		} 
	}

	// 接收虚捆包信息电文
	public void receiveMessagePickData(LgsMessagePickData lgsMessagePickData) throws Exception {
		try {
			lgsMessagePickData.setMessagePickMaterialSet(BeanConvertUtils.strToBean(lgsMessagePickData.getDetail(), CLASS_MESSAGE_PICK_MATERIAL));
	
			// 处理空model
			Iterator it = lgsMessagePickData.getMessagePickMaterialSet().iterator();
			while (it.hasNext()) {
				
				LgsMessagePickMaterial lgsMessagePickMaterial = (LgsMessagePickMaterial) it.next();
				if ("".equals(lgsMessagePickMaterial.getPackNum())||lgsMessagePickMaterial.getWeightActive().floatValue()==0) {
					it.remove();
				}
			}
			
//			//针对线材的情形，借用制造单元字段，制造单元去掉前两位，在后面补上按量按卷标记，10代表按量，20代表按卷，
//			//在元旦之后的9672第二次切换，需要9672恢复制造单元数据，加一个标志字段，下面的条件就去掉
//			if("D".equals(lgsMessagePickData.getOrderNum().substring(0 ,1))){
//				String manuId = lgsMessagePickData.getManuId();
//				lgsMessagePickData.setManuId("BG" + manuId.substring(0, 2));
//				lgsMessagePickData.setDelivyQtyFlag(manuId.substring(2));
//			}
	
			// 初始化部分值
			String queryMessagePickDataIdSql = "select id from LGS_MESSAGE_PICK_DATA where PICK_NUM = '"
					+ lgsMessagePickData.getPickNum() + "' and ORDER_NUM = '"
					+ lgsMessagePickData.getOrderNum() + "' and READY_NUM = '"
					+ lgsMessagePickData.getReadyNum() + "' and VEHICLE_CODE = '"
					+ lgsMessagePickData.getVehicleCode() + "' order by ID DESC";
			Long id = messageDao.queryIdBySql(queryMessagePickDataIdSql);
			lgsMessagePickData.setId(id);
	
			
			
			
			// 判定重复，解决电文同步发送的问题，临时过渡方案 add by zhengfei 20080128
			if (lgsMessagePickData.getMessagePickMaterialSet() != null && lgsMessagePickData.getMessagePickMaterialSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = lgsMessagePickData.getMessagePickMaterialSet().iterator();
				String someOnePack = "";
				if (itDouble != null) {
					LgsMessagePickMaterial lgsMessagePickMaterial = (LgsMessagePickMaterial) itDouble.next();
					someOnePack = lgsMessagePickMaterial.getPackNum();
				}
				
				// 判断该虚捆包下的捆包号是否已经入库
				Long doublePackCount = null;
				doublePackCount = messageDao.queryIdBySql("select count(t.ID) from LGS_MESSAGE_PICK_MATERIAL t "
						+ " where t.PICK_DATA_ID = " + lgsMessagePickData.getId() + " and t.PACK_NUM = '" + someOnePack + "'");
				
				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;
			}
	
			messageDao.insertLgsMessagePickData(lgsMessagePickData);
	
			
			// 如果初次新增，需要得到id
			if (id == null) {
				String queryIdSql = "select id from LGS_MESSAGE_PICK_DATA where PICK_NUM = '" + lgsMessagePickData.getPickNum()
						+ "' and ORDER_NUM = '" + lgsMessagePickData.getOrderNum()
						+ "' and READY_NUM = '" + lgsMessagePickData.getReadyNum()
						+ "' order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				lgsMessagePickData.setId(id);
			}
			
			//运输线材和管坯-9672厂内库出库实绩端
			messageDao.checkLgsMessagePickDataTrans(lgsMessagePickData);
			
			//入库线材和管坯-9672厂内库出库实绩端
			messageDao.checkLgsMessagePickDataStockin(lgsMessagePickData);
			
			//webservise转发暂时不上线
			System.out.println("1");
			String vehicleCode = StringUtil.trimStr(lgsMessagePickData.getVehicleCode());
			if(vehicleCode!=""&&vehicleCode.length()>0){
				System.out.println("2");
				sendLgsPickData.sendPickData(lgsMessagePickData);
			}
		} catch (Exception e) {
			String errTitle = "虚捆包信息:" + lgsMessagePickData.getPickNum();
			setLogMessage(e, errTitle);
		} 
	}
		
	// 接收离港、站实绩信息电文
	public void receiveTransLeaveReal(TplTransLeaveReal transLeaveReal) throws Exception {
		try {
			
			messageDao.updateTplTransShipM(transLeaveReal);
			messageDao.insert(transLeaveReal, transLeaveReal.getClass());
			//处理厂内船批数据 add by llk
			this.insertTplShipMLeaveReal(transLeaveReal);
		} catch (Exception e) {
			String errTitle = "离港、站实绩信息电文:" + transLeaveReal.getShipLotNo();
			setLogMessage(e, errTitle);
		} 
	}
	
	public void insertTplShipMLeaveReal(TplTransLeaveReal transLeaveReal) throws Exception{
		//将数据插入临时表
		//获取母船批号
		List transSeqList = messageDao.queryShipMIdList(transLeaveReal.getShipLotNo());
		String shipMId = "";
		if(transSeqList != null && transSeqList.size()>0){
			System.out.println("获取母船批号");
			Object[] shipIdArr = (Object[])transSeqList.get(0);
			shipMId = (String)shipIdArr[0];
		}
		System.out.println("母船批号：" + shipMId);
		String portTime = StringUtil.trimStr(transLeaveReal.getPortTime());//靠泊时间
		String startTime = StringUtil.trimStr(transLeaveReal.getWorkStartTime());//开工时间
		String endTime = StringUtil.trimStr(transLeaveReal.getWorkEndTime());//完工时间
		String leaveTime = StringUtil.trimStr(transLeaveReal.getLeaveTime());//离港时间
		
		TplShipMLeaveReal tplShipMLeaveReal = new TplShipMLeaveReal();
		tplShipMLeaveReal.setShipLotNo(transLeaveReal.getShipLotNo());
		tplShipMLeaveReal.setShipNo(transLeaveReal.getShipNo());
		tplShipMLeaveReal.setShipName(transLeaveReal.getShipName());
		tplShipMLeaveReal.setCountry(transLeaveReal.getCountry());
		tplShipMLeaveReal.setPortTime(portTime);//靠港时间
		tplShipMLeaveReal.setWorkStartTime(startTime);//开工时间
		tplShipMLeaveReal.setWorkEndTime(endTime);//完工时间
		tplShipMLeaveReal.setLeaveTime(leaveTime);//离港时间
		tplShipMLeaveReal.setShipIdM(shipMId);//母船批号
		
		List shipMList = messageDao.queryTplShipMList(shipMId);
		//船舶电话
		String shipContactNumber = "";
		if(shipMList != null && shipMList.size()>0){
			Object[] shipContactNumberArr = (Object[])shipMList.get(0);
			shipContactNumber = objtoString(shipContactNumberArr[0]);
		}
		if(!"".equals(portTime) && !"".equals(startTime)){
			//发送靠泊电文到物流管控
			TplMessageTransShipTrends tmtst = new TplMessageTransShipTrends();
			tmtst.setShipLotNo(shipMId);
			tmtst.setShipContactNumber(shipContactNumber);
			tmtst.setTimeMoveRqrCmpl(portTime);
			tmtst.setTimeLadeAct(startTime);
			tmtst.setOperationType("10");// 10靠港确认
			sendTransShipTrends(tmtst);
		}
		if(!"".equals(endTime) && !"".equals(leaveTime)){
			//发送离港
			TplMessageTransShipTrends tmtst = new TplMessageTransShipTrends();
			tmtst.setShipLotNo(shipMId);
			tmtst.setTimeEnd(endTime);
			tmtst.setLeaveDate(leaveTime);
			tmtst.setShipContactNumber(shipContactNumber);
			tmtst.setOperationType("20");// 20离港确认
			sendTransShipTrends(tmtst);
		}
		//插入临时表之前判断重复
		List shipMLeaveList =  messageDao.queryTplShipMLeaveRealList(shipMId);
		if(shipMLeaveList == null || shipMLeaveList.size()==0){
			//新增操作
			messageDao.insert(tplShipMLeaveReal, tplShipMLeaveReal.getClass());
		}else{
			//更新操作
			TplShipMLeaveReal real = (TplShipMLeaveReal)shipMLeaveList.get(0);
			real.setShipName(transLeaveReal.getShipName());
			real.setCountry(transLeaveReal.getCountry());
			real.setPortTime(portTime);//靠港时间
			real.setWorkStartTime(startTime);//开工时间
			real.setWorkEndTime(endTime);//完工时间
			real.setLeaveTime(leaveTime);//离港时间
			real.setShipIdM(shipMId);//母船批号
			messageDao.updateTplShipM(real);
		}
	}
	
	private String objtoString(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}

	// 接收船批信息电文
	public void receiveTransShipM(TplTransShipM transShipM) throws Exception {
		try {
			// add by wj 20130411
			if ("000000".equals(transShipM.getCarryCmpaCode()) || "999999".equals(transShipM.getCarryCmpaCode())
					|| "BGSA".equals(transShipM.getCarryCmpaCode())|| transShipM.getCarryCmpaCode() == null) {
				return;
			}
			
			transShipM.setTransSeqSet(BeanConvertUtils.strToBean(transShipM.getDetail(), CLASS_TRANS_SEQ));

			// 处理空model
			Iterator it = transShipM.getTransSeqSet().iterator();
			while (it.hasNext()) {
				TplTransSeq transSeq = (TplTransSeq) it.next();
				if ("".equals(transSeq.getShipId())) {
					it.remove();
				}
			}

			/**
			 * 船批信息处理
			 * 0代表新增，1代表修改，9代表红冲
			 */
			if ("0".equals(transShipM.getOperateFlag())) {
				// 初始化部分值
				String queryShipMIdSql = "select id from TPL_TRANS_SHIP_M where SHIP_ID_M = '"
						+ transShipM.getShipIdM() + "' and OPERATE_FLAG = '" + transShipM.getOperateFlag()
						+ "' order by ID DESC";
				Long id = messageDao.queryIdBySql(queryShipMIdSql);
				transShipM.setId(id);
				if (transShipM.getTransSeqSet() != null && transShipM.getTransSeqSet().size() > 0) {
					// 获取这批电文中任一子船批号
					Iterator itDouble = transShipM.getTransSeqSet().iterator();
					String someOneShip = "";
					while (itDouble.hasNext()) {
						TplTransSeq transSeq = (TplTransSeq) itDouble.next();
						someOneShip = transSeq.getShipId();
						// 判断该船批下的船批号是否存在
						Long doubleShipCount = messageDao.queryIdBySql("select count(t.ID) from TPL_TRANS_SEQ t "
								+ " where t.SHIP_M_ID = " + transShipM.getId() + " and t.SHIP_ID = '" + someOneShip + "'");
						// 如果该船批存在，则不处理该船批
						if (doubleShipCount != null && doubleShipCount.longValue() > 0)
							return;
					}
					
					messageDao.insertTplShipM(transShipM);
				}
			} else if ("1".equals(transShipM.getOperateFlag())) {
				String queryShipMIdSqlAlt = "select id from TPL_TRANS_SHIP_M where SHIP_ID_M = '"
					+ transShipM.getShipIdM() + "' and SHIP_STATUS = '00' order by ID DESC";
				Long id = messageDao.queryIdBySql(queryShipMIdSqlAlt);
				transShipM.setId(id);
				// 主船批信息存在,修改母子船批信息
				if (id != null) {
					// 获取这批电文中的子船批号
					Iterator itDouble = transShipM.getTransSeqSet().iterator();
					String someOneShip = "";
					while (itDouble.hasNext()) {
						TplTransSeq transSeq = (TplTransSeq) itDouble.next();
						someOneShip = transSeq.getShipId();
						// 判断该船批下的船批号是否存在
						Long doubleShipCount = messageDao.queryIdBySql("select count(t.ID) from TPL_TRANS_SEQ t "
								+ " where t.SHIP_M_ID = " + transShipM.getId() + " and t.SHIP_ID = '" + someOneShip + "' and PRO_STATUS = '00'");
						// 如果该船批不存在，则不处理该船批
						if (doubleShipCount == null || (doubleShipCount != null && doubleShipCount.longValue() < 1))
							return;
					}
					
					messageDao.updateTplShipM(transShipM);
				}
			} else if ("9".equals(transShipM.getOperateFlag())) {
				String queryShipMIdSqlDel = "select id from TPL_TRANS_SHIP_M where SHIP_ID_M = '"
					+ transShipM.getShipIdM() + "' and SHIP_STATUS = '00' order by ID DESC";
				Long id = messageDao.queryIdBySql(queryShipMIdSqlDel);
				transShipM.setId(id);
				// 主船批信息存在,删除子船批信息
				if (id != null) {
					// 获取这批电文中的子船批号
					Iterator itDouble = transShipM.getTransSeqSet().iterator();
					String someOneShip = "";
					while (itDouble.hasNext()) {
						TplTransSeq transSeq = (TplTransSeq) itDouble.next();
						someOneShip = transSeq.getShipId();
						// 判断该船批下的船批号是否存在
						Long doubleShipCount = messageDao.queryIdBySql("select count(t.ID) from TPL_TRANS_SEQ t "
								+ " where t.SHIP_M_ID = " + transShipM.getId() + " and t.SHIP_ID = '" + someOneShip + "' and PRO_STATUS = '00'");
						// 如果该船批不存在，则不处理该船批
						if (doubleShipCount == null || (doubleShipCount != null && doubleShipCount.longValue() < 1))
							return;
					}
					
					messageDao.doTransShipMRedOper(transShipM);
				}
			}
		} catch (Exception e) {
			String errTitle = "船批信息:" + transShipM.getShipIdM();
			setLogMessage(e, errTitle);
		} 
	}
	
	// 发送社会仓库库位变更信息
	public void sendMessageWarehousePosChan(TplMessageWarehousePoschan warehousePoschan) throws Exception {
		messageDao.saveWarehousePoschan(warehousePoschan);
		sendMsgService.sendMsg(MESSAGE_SEND_N0_WAREHOUSE_POSCHAN, null, warehousePoschan);
	}

	// 发送入库作业实绩
	public void sendStockInResult(TplMessageStockinRealMain stockIn) throws Exception {
		messageDao.saveStockInResult(stockIn);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockIn.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockIn.getUnitId())) { //wuyang20110309
			// 补充空model,达到指定循环次数
			List list = new ArrayList();
			list.addAll(stockIn.getStockInItemSet());
			int leftCircularTimes = 0;
			if (stockIn.getStockInItemSet().size() % CIRCULAR_STOCK_IN_ITEM_TIMES != 0) {
				leftCircularTimes = CIRCULAR_STOCK_IN_ITEM_TIMES - stockIn.getStockInItemSet().size() % CIRCULAR_STOCK_IN_ITEM_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageStockinRealItem());
			}

			List detailList = getMsgDetail(list, CIRCULAR_STOCK_IN_ITEM_TIMES, CLASS_STOCK_IN_REAL_ITEM);
			for (int i = 0; i < detailList.size(); i++) {
				stockIn.setDetail((String) detailList.get(i));
				sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_IN, null, stockIn);
			}
		}
	}

	// 发送出库作业实绩
	public void sendStockOutResult(TplMessageStockoutRealMain stockOut) throws Exception {
		messageDao.saveStockOutResult(stockOut);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockOut.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockOut.getUnitId())) { //wuyang20110309
			// 补充空model,达到指定循环次数
			List list = new ArrayList();
			list.addAll(stockOut.getStockOutItemSet());

			int leftCircularTimes = 0;
			if (stockOut.getStockOutItemSet().size() % CIRCULAR_STOCK_OUT_ITEM_TIMES != 0) {
				leftCircularTimes = CIRCULAR_STOCK_OUT_ITEM_TIMES - stockOut.getStockOutItemSet().size()
						% CIRCULAR_STOCK_OUT_ITEM_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageStockoutRealItem());
			}

			List detailList = getMsgDetail(list, CIRCULAR_STOCK_OUT_ITEM_TIMES, CLASS_STOCK_OUT_REAL_ITEM);
			for (int i = 0; i < detailList.size(); i++) {
				stockOut.setDetail((String) detailList.get(i));
				sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_OUT, null, stockOut);
			}
		}

	}
	// 发送线上出库作业实绩 added ljh 2015-07-30 13:17:40
	public void sendOnlineStockOutResult(TplMessageStockoutRealMain stockOut) throws Exception {
		
		messageDao.saveStockOutResult(stockOut);
		System.out.println("更新完成");
		int lString=stockOut.getStockOutItemSet()==null ?0:stockOut.getStockOutItemSet().size();
		System.out.println("--------------"+lString);
		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.addAll(stockOut.getStockOutItemSet());

		int leftCircularTimes = 0;
		if (stockOut.getStockOutItemSet().size() % CIRCULAR_STOCK_OUT_ITEM_TIMES != 0) {
			leftCircularTimes = CIRCULAR_STOCK_OUT_ITEM_TIMES - stockOut.getStockOutItemSet().size()
					% CIRCULAR_STOCK_OUT_ITEM_TIMES;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplMessageStockoutRealItem());
		}

		List detailList = getMsgDetail(list, CIRCULAR_STOCK_OUT_ITEM_TIMES, CLASS_STOCK_OUT_REAL_ITEM);
		for (int i = 0; i < detailList.size(); i++) {
			stockOut.setDetail((String) detailList.get(i));
			sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_OUT, null, stockOut);
		}
		

	}
	// 发送电子提单验单电文 add by zhengfei 20080229
	public void sendStockOutEpickResult(TplMessageEpickSend epickSend) throws Exception {
		//messageDao.saveStockOutEpickResult(epickSend);
		sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_OUT_EPICK, null, epickSend);
	}

	// 发送库存调整作业实绩
	public void sendStockAdjustResult(TplMessageStockAdjust stockAdjust) throws Exception {
		messageDao.saveStockAdjustResult(stockAdjust);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockAdjust.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockAdjust.getUnitId())) { //wuyang20110309
			// 补充空model,达到指定循环次数
			List list = new ArrayList();
			list.addAll(stockAdjust.getTplStockAdjustItems());
			int leftCircularTimes = 0;
			if (stockAdjust.getTplStockAdjustItems().size() % CIRCULAR_STOCK_ADJUST_ITEM_TIMES != 0) {
				leftCircularTimes = CIRCULAR_STOCK_ADJUST_ITEM_TIMES - stockAdjust.getTplStockAdjustItems().size()
						% CIRCULAR_STOCK_ADJUST_ITEM_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageStockAdjustItem());
			}

			stockAdjust.setItemCount(Long.valueOf("" + stockAdjust.getTplStockAdjustItems().size()));
			List detailList = getMsgDetail(list, CIRCULAR_STOCK_ADJUST_ITEM_TIMES, CLASS_STOCK_ADJUST_ITEM);
			for (int i = 0; i < detailList.size(); i++) {
				stockAdjust.setDetail((String) detailList.get(i));
				sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_ADJUST, null, stockAdjust);
			}
		}
	}

	private double getFormatDouble(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	// 发送库存盘点作业实绩
	public void sendStockCheckResult(TplMessageStockCheck stockCheck) throws Exception {
		messageDao.saveStockCheckResult(stockCheck);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockCheck.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockCheck.getUnitId())) {//wuyang20110309
			// 补充空model,达到指定循环次数
			List list = new ArrayList();
			list.addAll(stockCheck.getTplMessageStockCheckItems());
			int leftCircularTimes = 0;
			if (stockCheck.getTplMessageStockCheckItems().size() % CIRCULAR_STOCK_CHECK_ITEM_TIMES != 0) {
				leftCircularTimes = CIRCULAR_STOCK_CHECK_ITEM_TIMES - stockCheck.getTplMessageStockCheckItems().size()
						% CIRCULAR_STOCK_CHECK_ITEM_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageStockCheckItem());
			}

			stockCheck.setItemCount(Long.valueOf("" + stockCheck.getTplMessageStockCheckItems().size()));
			List detailList = getMsgDetail(list, CIRCULAR_STOCK_CHECK_ITEM_TIMES, CLASS_STOCK_CHECK_ITEM);
			for (int i = 0; i < detailList.size(); i++) {
				stockCheck.setDetail((String) detailList.get(i));
				sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_CHECK, null, stockCheck);
			}
		}

	}

	/**
	 * *********************************费用结算业务接口*************************************
	 * 
	 * 1 接收作业费用信息 2 发送发票付款申请 3 接收付款实绩 4 接收费用撤销(发送费用撤销回复) 5 发送发票撤销 6 接收发票抬头变更
	 * 
	 * **********************************************************************************
	 */
	// 接收作业费用信息
	public void receiveFee(TplMessageFee messageFee) throws Exception {
		
		System.out.println("********************暂缓标志*********************" +messageFee.getLaterSettleFlag());
		/**
		 * 0101 水路运费 0201 铁路运费 0301 汽运运费 1101 仓储费 1102 出入库费 1103 码头吊装费 1104
		 * 铁路吊装费 3101 代理费 3102 商检费 3103 薰蒸费 3104 产地证费 3105 集装箱费 3201 保险费 3202
		 * 印花税
		 * 
		 */
		try {
			// 计划费用删除电文结束
			String delMsgFee = "delete from TPL_MESSAGE_FEE where PLAN_SEQ = '" + messageFee.getPlanSeq() + "'";
			String delPlanFee = "delete from TPL_PLAN_FEE where PLAN_ID = '" + messageFee.getPlanSeq() + "'";
			String delRealFee = "delete from TPL_REAL_FEE where PLAN_ID = '" + messageFee.getPlanSeq() + "' and invoice_sys_id is null ";
			if (FEE_DEL_FLAG.equals(messageFee.getOrderNum())) {
				/**
				 * modified by Forest 20080425 厂内直发的铁运特殊，费用不做删除。
				 * 如果该计划的实绩已做，不能删除。
				 */				
				//武钢铁路费用撤销删除全部费用计划
				//wuyixuan 2018-10-31 23:43:53
				String checkIfDelSql = "select ID from TPL_MESSAGE_FEE  where UNIT_ID != 'BGBW' and PLAN_SEQ = '" + messageFee.getPlanSeq()
						+ "' and TRANS_TYPE = '" + SystemConstants.TRANS_TYPE_TRAIN + "' and BILL_TYPE = '62'";
				if (messageDao.querySingleValueBySql(checkIfDelSql) != null) {
					return;
				}
				
				Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_FEE t where t.PLAN_SEQ = '" + messageFee.getPlanSeq() + "' and t.FEE_STATUS = '1'");
				if (itemCount.intValue() != 0) {
					// 如果是实绩费用，将费用实绩记录也做相关删除
					messageDao.executeUpdateSql(delRealFee);
				}
				
				// 1 删除tpl_message_fee，tpl_plan_fee费用
				messageDao.executeUpdateSql(delMsgFee);
				messageDao.executeUpdateSql(delPlanFee);
				return;
			}
			
			// add by zhengfei 20080128
			if ("000000".equals(messageFee.getProviderId()) || "999999".equals(messageFee.getProviderId())
					|| "BGSA".equals(messageFee.getProviderId())) {
				return;
			}
			
			if(!"".equals(messageFee.getTransInvoiceRank())) {
				messageFee.setTransInvoiceSequence(messageFee.getTransInvoiceRank());
			}else {
				messageFee.setTransInvoiceSequence("");
			}
           //add by zhanyuchuan
			/*3PL接收费用时，判断提单类型是11，31，66模式为直销业务，
		       将费用表中业务类型（businessType：10：直销）*/
//			if("11".equals(messageFee.getBillType())||"31".equals(messageFee.getBillType())||"66".equals(messageFee.getBillType())){
//				messageFee.setBusinessType("10");
//			}
			//messageFee.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
			messageFee.setUnitId(messageFee.getAuthorizedUnitId());
			//messageFee.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(messageFee.getUnitId()));
			
//			SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//					"searchTitleService", SearchTitleService.class);
//				      String str1 = searchTitleService.acquireHeadByCode("W001", messageFee.getAuthorizedUnitId())==null?"":searchTitleService.acquireHeadByCode("W001", messageFee.getAuthorizedUnitId());
//				      messageFee.setUnitName(str1);
			messageFee.setUnitName(AcquireAgencyName.getAgencyName(messageFee.getUnitId()));//获取委托机构名称20110308
			
			// 可开票标记暂时不用，都置为1：可开票
			messageFee.setInvoiceMadeStatus("1");

			// 毛重为0或小于净重都补净重
			if (new BigDecimal("0").compareTo(messageFee.getGrossWeight()) == 0
					|| messageFee.getGrossWeight().compareTo(messageFee.getNetWeight()) < 0) {
				messageFee.setGrossWeight(messageFee.getNetWeight());
			}
			
			if (new BigDecimal("0").compareTo(messageFee.getGrossWeight()) == 0 && SystemConfigUtil.BAOSTEEL_BGSQ.equals(messageFee.getUnitId())) {
				messageFee.setGrossWeight(new BigDecimal("1"));
				messageFee.setNetWeight(new BigDecimal("1"));
			}

			String checkFeeRepeatSql = "select ID from TPL_MESSAGE_FEE where PLAN_SEQ = '" + messageFee.getPlanSeq()
					+ "' and STATUS = '0'";
			// 检查运费或仓储费是否重复接收
			if (messageDao.queryIdBySql(checkFeeRepeatSql) != null) {
				//鲁宝厂内转库运输业务,采用全增全删的方式追加
				if(APPLEND_FLAG.equals(messageFee.getApplendFlag())&& "0".equals(messageFee.getPlanType())){
					messageDao.executeUpdateSql(delMsgFee);
					messageDao.executeUpdateSql(delPlanFee);
				}
				else{
					return;
				}
				
			}
			
			/**
			 * 费用作业计划是否在3PL开保单(INSURANCE_FLAG)，10：是；00：否；空：历史数据（沿用保单改造之前的方式处理）
			 * modified by tangaj 20111012
			 * 
			 */
			if (null == messageFee.getInsuranceFlag() || "".equals(messageFee.getInsuranceFlag())) {
				// 在3PL开保单标记为null或空时，运输方式(TRANS_TYPE)是汽运(="1")、结算方式(SETTLE_CODE)为2,5,6 不在3PL开保单
				if ("1".equals(messageFee.getTransType())
						|| "2".equals(messageFee.getSettleCode())
						|| "5".equals(messageFee.getSettleCode())
						|| "6".equals(messageFee.getSettleCode())) {
					messageFee.setInsuranceFlag("00");
				}
			}// 其它情况不处理

			// 计划费用新增电文结束
			if (FEE_OVER_FLAG.equals(messageFee.getOrderNum())) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_FEE set STATUS = '0' where PLAN_SEQ = '" + messageFee.getPlanSeq()
						+ "'");
				messageDao.insertTplFeeByBatch(messageFee);
				
				// 20180110 依据材料合同判断是否是现货，抛送给欧冶物流
//				if(StringUtil.trimStr(messageFee.getBusinessType()).equals("99")){
//					String checkSendstatusExistsSql = "select ID from TPL_TRANS_PLAN where TRANS_PLAN_ID = '" + messageFee.getPlanSeq()
//					+ "' and SEND_STATUS is not null ";
//					System.out.println("checkSendstatusExistsSql-->"+checkSendstatusExistsSql);
//					if (messageDao.queryIdBySql(checkSendstatusExistsSql) == null) {
//						List tplTransPlanList = messageDao.queryTplTransPlanById(messageFee.getPlanSeq());
//						if(tplTransPlanList!=null&&tplTransPlanList.size()!=0){
//							TplTransPlan tplTransPlan = (TplTransPlan)tplTransPlanList.get(0);
//							System.out.println("tplTransPlan.getId()-->"+tplTransPlan.getId());
//							tplTransPlan.setSendStatus("10");
//							messageDao.update(tplTransPlan, TplTransPlan.class);
//
//							//判断计划号是否已经插入临时表，避免重复插入
//							//现货代理业务，计划信息导入临时表，由定时任务抛送欧冶
//							Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '1' and SEND_STATUS = '00' and TRANS_PLAN_ID = '" + messageFee.getPlanSeq()+"'");
//							if (tempId==null) {//运输计划存在
//								TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
//								transPlanTemp.setOperationType("1");
//								transPlanTemp.setTransPlanId(messageFee.getPlanSeq());
//								transPlanTemp.setCreateDate(new Date());
//								transPlanTemp.setBillType("10");
//								transPlanTemp.setSendStatus("00");
//								transPlanTemp.setPickNum(tplTransPlan.getPickNum());
//								messageDao.insert(transPlanTemp, TplTransPlanTemp.class);
//							}
//						}
//					}
//				}
			} else {
				// 如果结算方式是代收代付，需要根据合同第三位订货公司别，区分不同的地区公司(相同的发票抬头不同地区公司代码不能开在一张发票上)。
				if (SystemConfigUtil.SETTLE_CODE_DAISHOUDAIZHI.equals(messageFee.getSettleCode())) {
					/**
					 * 000107 广州宝钢 000199 成都宝钢 000424 宝钢商贸 000425 宝钢钢贸 000650
					 * 天津宝钢 001779 浦东国贸 000136 武汉宝钢 007297 宝山公司
					 */
//					String orderNum = messageFee.getOrderNum();
					String orderUserId = messageFee.getManuId();
					String finalName = getAreaCompanyCode(orderUserId);
					messageFee.setOrderUserType(finalName);
					/**
					 * 如果是报支，需要分五种类型开票： 1 外采购，合同性质是P000 2 内供料，合同性质是P100 3
					 * 钢管，合同号是G开头 4 预合同， 合同性质S100 5 其他产成品 S000
					 * 
					 */
				} else if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(messageFee.getSettleCode())) {
					if (SystemConfigUtil.ORDER_TYPE_WAICAIGOU.equals(messageFee.getOrderType())
							|| SystemConfigUtil.ORDER_TYPE_NEIGONG.equals(messageFee.getOrderType())) {
						messageFee.setOrderUserType(messageFee.getOrderType());
						// added by Forest 2008-02-20 R开头合同(ERW管)属于钢管品种
					} else if (messageFee.getOrderNum().substring(0, 1).equals("G")
							|| messageFee.getOrderNum().substring(0, 1).equals("R")) {
						messageFee.setOrderUserType("G");
						// added by Forest 2008-02-25 增加预合同分票
					} else if (SystemConfigUtil.ORDER_TYPE_YUHETONG.equals(messageFee.getOrderType())) {
						messageFee.setOrderUserType(SystemConfigUtil.ORDER_TYPE_YUHETONG);
						// added by Forest 2008-04-22 增加制造单元代码区分不锈钢碳钢品种的报支
					} else if ("BGSY".equals(messageFee.getManuId())) {
						messageFee.setOrderUserType("SY" + SystemConfigUtil.ORDER_TYPE_XIAOSHOU);
					} else {
						messageFee.setOrderUserType(SystemConfigUtil.ORDER_TYPE_XIAOSHOU);
					}
				}
				messageDao.insertTplMessageFee(messageFee);
			}
		} catch (Exception e) {
			String errTitle = "费用接收出错:" + messageFee.getPlanSeq();
			setLogMessage(e, errTitle);
		}
	}

	private String getAreaCompanyCode(String orderM) {
		String finalName = "";
		//2010年4月23日，要求取消对合同号的判断
//		if ("".equals(orderNum)) {
//			orderNum = "0000000000";
//		}
		//物流室
		if ("A".equals(orderM) || "002995".equals(orderM)){
			finalName = "002995";
        // 宝钢钢贸
		}else if ("E".equals(orderM) || "000425".equals(orderM) || "009406".equals(orderM)) {
			finalName = "000425";
		// 宝钢商贸
		}else if ("H".equals(orderM) || "000424".equals(orderM) || "701018".equals(orderM)) {
			finalName = "000424";
	    // 成都宝钢（西部公司）
		}else if ("D".equals(orderM) || "000199".equals(orderM) || "701116".equals(orderM)) {
			finalName = "000199";
		// 武汉宝钢（华中公司）
		}else if ("L".equals(orderM) || "000136".equals(orderM) || "702646".equals(orderM)) {
			finalName = "000136";
		// 浦东国贸
		}else if ("I".equals(orderM) || "001779".equals(orderM)) {
			finalName = "001779";
		// 宝山公司（宝宝公司）
		}else if ("N".equals(orderM) || "007297".equals(orderM)) {
			finalName = "007297";
		// 上海不锈
		}else if ("Q".equals(orderM) || "A05541".equals(orderM)){
			finalName = "A05541";	
		// 广州宝钢（南方公司）
		}else if ("B".equals(orderM) || "000107".equals(orderM) || "702644".equals(orderM)) {
			finalName = "000107";
		// 揭阳宝钢
		}else if ("B01".equals(orderM) || "044399".equals(orderM)){
			finalName = "044399";
		// 天津宝钢（北方公司）
		}else if ("C".equals(orderM) || "000650".equals(orderM) || "700714".equals(orderM)) {
			finalName = "000650";
		// 长春宝钢
		}else if ("C01".equals(orderM) || "035169".equals(orderM)){
			finalName = "035169";
		// 沈阳宝钢
		}else if ("C02".equals(orderM) || "000108".equals(orderM)){
			finalName = "000108";
		//	佛山宝钢
		}else if ("T02".equals(orderM) || "008422".equals(orderM)){
			finalName = "008422";
		//	宁波宝钢
		}else if ("T01".equals(orderM) || "031346".equals(orderM)){
			finalName = "031346";
		}else if ("F".equals(orderM) || "000158".equals(orderM)){
			finalName = "000158";
			//北京宝钢
		}else if ("K".equals(orderM) || "001068".equals(orderM)){
			finalName="001068";
			//宝钢国际东北公司
		}
		else{
			finalName = orderM;
		}
		return finalName;
	}
	
	// 发送发票付款申请
	public void sendInvoice(TplMessageInvoice invoice) throws Exception {
		System.out.println("invoice.getSendStatus():::"+StringUtil.trimStr(invoice.getSendStatus()));
		
		// 处理尾数误差
		double totalMoney = invoice.getTotalAmount().doubleValue();

		double detailTotal = 0;
		
		double detailTotalTax = 0; //处理税额误差
		double invTaxAmount = invoice.getInvTaxAmount().doubleValue(); //手工输入的税额总金额

		Iterator it = invoice.getInvoiceFeeList().iterator();
		while (it.hasNext()) {
			TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) it.next();
			detailTotal = detailTotal + invoiceFee.getTotalAmount().doubleValue();
			detailTotalTax = detailTotalTax + invoiceFee.getTaxAmount().doubleValue(); //汇总明细中的税额
		}
		System.out.println("detailTotal"+detailTotal);
		System.out.println("totalMoney"+totalMoney);
		double diffMoney = this.round(totalMoney - detailTotal, 2);
		
		double diffMoneyTax = this.round(invTaxAmount - detailTotalTax, 2);//录入总税额与明细中税额和相差
		
		boolean flag=true;

		System.out.println("diffMoney"+diffMoney);
		// 如果尾数有偏差，则将偏差汇加到除保险费 印花税 仓储费(这三种费用固定)以外的费用上，保持发票和明细金额一致。
		if (diffMoney != 0) {
			/**
			 * 3201 保险费 3202 印花税 1101 仓储费
			 */
			Iterator itSecond = invoice.getInvoiceFeeList().iterator();
			while (itSecond.hasNext()) {
				TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) itSecond.next();
				double trueMoney = this.round(invoiceFee.getTotalAmount().doubleValue() + diffMoney, 2);
				System.out.println("trueMoney"+trueMoney);
				// 如果是报支需要特殊处理
				if(trueMoney>0){
					if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(invoice.getSettleType())) {
						// 将差异金额分摊到运费上
						if ("0101".equals(invoiceFee.getFeeTypeCode()) || "0301".equals(invoiceFee.getFeeTypeCode())
								|| "0201".equals(invoiceFee.getFeeTypeCode()) || "1102".equals(invoiceFee.getFeeTypeCode())
								|| "1103".equals(invoiceFee.getFeeTypeCode()) || "1104".equals(invoiceFee.getFeeTypeCode())|| "3201".equals(invoiceFee.getFeeTypeCode())) {//add by zhengfei 增加码头吊装费
							invoiceFee.setTotalAmount(new BigDecimal(trueMoney));
							break;
						}
					} else {
						if (invoiceFee.getFeeTypeCode() != "3202"
								&& invoiceFee.getFeeTypeCode() != "1101") {
							invoiceFee.setTotalAmount(new BigDecimal(trueMoney));
							break;
						}
					}
				}
				
				
			}
		}
		
		//计算税额误差20120109 add by wuyang
		if(diffMoneyTax != 0) {
			Iterator itSecondTax = invoice.getInvoiceFeeList().iterator();
			while (itSecondTax.hasNext()) {
				TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) itSecondTax.next();
				double trueMoneyTax = this.round(invoiceFee.getTaxAmount().doubleValue() + diffMoneyTax, 2);
				if(trueMoneyTax>0){
					// 如果是报支需要特殊处理
					if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(invoice.getSettleType())) {
						// 将差异金额分摊到运费上
						if ("0101".equals(invoiceFee.getFeeTypeCode()) || "0301".equals(invoiceFee.getFeeTypeCode())
								|| "0201".equals(invoiceFee.getFeeTypeCode()) || "1102".equals(invoiceFee.getFeeTypeCode())
								|| "1103".equals(invoiceFee.getFeeTypeCode()) || "1104".equals(invoiceFee.getFeeTypeCode())|| "3201".equals(invoiceFee.getFeeTypeCode())) {//add by zhengfei 增加码头吊装费
							flag=false;
							invoiceFee.setTaxAmount(new BigDecimal(trueMoneyTax));
							break;
						}
					} else {
						if (invoiceFee.getFeeTypeCode() != "3202"
								&& invoiceFee.getFeeTypeCode() != "1101") {
							flag=false;
							invoiceFee.setTaxAmount(new BigDecimal(trueMoneyTax));
							break;
						}
					}
				}
				
			}
			if(flag){
				Iterator itSecondTax2 = invoice.getInvoiceFeeList().iterator();
			    if (itSecondTax2.hasNext()) {
				TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) itSecondTax2.next();
				double trueMoneyTax = this.round(invoiceFee.getTaxAmount().doubleValue() + diffMoneyTax, 2);
				if(trueMoneyTax>0){
					// 如果是报支需要特殊处理
					if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(invoice.getSettleType())) {
						// 将差异金额分摊到运费上
						if ("1101".equals(invoiceFee.getFeeTypeCode())) {//add by xutainyu  仓储费发票类型
							invoiceFee.setTaxAmount(new BigDecimal(trueMoneyTax));
							//break;
						}
					} 
				 }
				
			   }
			}
			
		}

		//if (invoice.getInvoiceType() != null && invoice.getInvoiceType().trim().equals("9")) {// 运输增值税，由9改成8，只在电文表里做修改
		//	invoice.setInvoiceType("8");
		//}
		messageDao.saveInvoice(invoice);

		if (SystemConfigUtil.judgeBaosteelOwner(invoice.getUnitId())) { //wuyang20110309
			// 补充空model,达到指定循环次数
			List list = new ArrayList();
			list.addAll(invoice.getInvoiceFeeList());
			int leftCircularTimes = 0;
			if (invoice.getInvoiceFeeList().size() % CIRCULAR_INVOICE_FEE_TIMES != 0) {
				leftCircularTimes = CIRCULAR_INVOICE_FEE_TIMES - invoice.getInvoiceFeeList().size() % CIRCULAR_INVOICE_FEE_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageInvoiceFee());
			}

			// 转换发票类型
			String tempInvoiceType = invoice.getInvoiceType(); 
			System.out.println("========= tempInvoiceType"+tempInvoiceType);
			String convertInvoiceType = (String) SystemConfigUtil.INVOICE_CODE_MATCH_GUFEN.get(tempInvoiceType); 
			System.out.println("========= convertInvoiceType"+convertInvoiceType);
			invoice.setInvoiceType(convertInvoiceType); 
			System.out.println("========= invoice.getInvoiceType()"+invoice.getInvoiceType());
		
			List detailList = getMsgDetail(list, CIRCULAR_INVOICE_FEE_TIMES, CLASS_INVOICE_FEE);
			for (int i = 0; i < detailList.size(); i++) {
				invoice.setDetail((String) detailList.get(i)); 
				sendMsgService.sendMsg(MESSAGE_SEND_N0_INVOICE_APPLY, null, invoice);
				System.out.println("发送电文次数---"+i);
			}
			// 发送结束电文
			String orderNum = invoice.getOrderNum();
			invoice.setInvoiceType(tempInvoiceType);
			invoice.setOrderNum(FEE_OVER_FLAG);
			sendMsgService.sendMsg(MESSAGE_SEND_N0_INVOICE_APPLY, null, invoice);
			invoice.setOrderNum(orderNum);
		}

	}

	// 接收发票付款实绩
	public void receiveInvoicePay(TplMessageInvoicePay invoicePay) throws Exception {
		try {
			messageDao.updateInvoicePay(invoicePay);
		} catch (Exception e) {
			String errTitle = "发票付款实绩: " + invoicePay.getInvoiceId() + "#" + invoicePay.getInvoiceNo();
			setLogMessage(e, errTitle);
		}
	}

	// 接收费用撤销 0 不可撤销；1 可撤销
	public void receiveFeeCancel(TplMessageFeeCancel feeCancel) throws Exception {
		try {
			TplMessageFeeCancel feeCancelReturn = new TplMessageFeeCancel();
			feeCancelReturn.setPlanId(feeCancel.getPlanId());
			feeCancel.setCreateDate(new Date());
			feeCancel.setUpdateStatus("0");
			if (messageDao.receiveFeeCancel(feeCancel)) {
				feeCancelReturn.setCancelStatus("0");
			} else {
				feeCancelReturn.setCancelStatus("1");
			}
			sendMsgService.sendMsg(MESSAGE_SEND_N0_FEE_CANCEL_REPLY, null, feeCancelReturn);
		} catch (Exception e) {
			String errTitle = "费用撤销：" + feeCancel.getPlanId();
			setLogMessage(e, errTitle);
		}
	}

	// 发送发票撤销电文
	public void sendInvoiceCancel(TplMessageInvoice invoice) throws Exception {
		messageDao.cancelInvoice(invoice);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(invoice.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(invoice.getUnitId())) { //wuyang20110309
			sendMsgService.sendMsg(MESSAGE_SEND_N0_INVOICE_CANCEL, null, invoice);
		}
	}

	// 接收发票抬头变更
	public void receiveInvoiceTitleChange(TplInvoiceTitleChange invoiceTitleChange) throws Exception {
		try {
			if (invoiceTitleChange.getPlanId() == null || "".equals(invoiceTitleChange.getPlanId())) {
				return;
			}
			String planId=invoiceTitleChange.getPlanId();
			String orderNum=invoiceTitleChange.getOrderNum();
			//查询realfee，看是否已生成费用信息
			Map map=new HashMap();
			map.put("planId", planId);
			map.put("orderNum", orderNum); 
			List list =messageDao.queryTplRealFee(map);
			//构建消息model，以便跟踪执行情况
			TplMessageLog tplMessageLog=new TplMessageLog();
			tplMessageLog.setErrTitle("更新发票抬头失败");
			tplMessageLog.setCreateDate(new Date());
			//以此遍历realfee数据，查看是否可以更新发票抬头
			for (int j = 0; j < list.size(); j++) {
				Map realFee = (Map) list.get(j);
				BigDecimal invoiceSysId = (BigDecimal) realFee.get("invoice_sys_id");
				String settleCode = (String) realFee.get("settle_code");
				
				if(null != invoiceSysId){ 
					tplMessageLog.setErrContent(" 计划号: "+planId+" 合同号: "+orderNum+" 已开票不能更新发票抬头信息 ");
					messageDao.insert(tplMessageLog, TplMessageLog.class);
					return;
				} 
				if( "3".equals(settleCode)){ 
					tplMessageLog.setErrContent(" 计划号: "+planId+" 合同号: "+orderNum+" 结算方式为报支不能更新发票抬头信息 ");
					messageDao.insert(tplMessageLog, TplMessageLog.class);
					return;
				}
				
			} 
			//记录发票抬头电文更新记录
			Object providerId = messageDao.querySingleValueBySql("select PROVIDER_ID from TPL_TRANS_PLAN  where TRANS_PLAN_ID = '"
					+ invoiceTitleChange.getPlanId() + "'");
			if (providerId != null) {
				invoiceTitleChange.setProviderId((String) providerId);
			}
			invoiceTitleChange.setStatus("1");
			invoiceTitleChange.setCreateDate(new Date());
			invoiceTitleChange.setExcuteDate(new Date());
			invoiceTitleChange.setUnitId(invoiceTitleChange.getAuthorizedUnitId());
			//invoiceTitleChange.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(invoiceTitleChange.getUnitId()));
//			SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//					"searchTitleService", SearchTitleService.class);
//				      String str1 = searchTitleService.acquireHeadByCode("W001", invoiceTitleChange.getAuthorizedUnitId())==null?"":searchTitleService.acquireHeadByCode("W001", invoiceTitleChange.getAuthorizedUnitId());
//	
//				      invoiceTitleChange.setUnitName(str1);
			invoiceTitleChange.setUnitName(AcquireAgencyName.getAgencyName(invoiceTitleChange.getUnitId()));//获取委托机构名称20110308
			
			messageDao.insert(invoiceTitleChange, TplInvoiceTitleChange.class);
			
			
			
			//构建更新铁路杂费之外的费用
			String updatePlanFeeTitleTrans = "update TPL_PLAN_FEE set INVOICE_TITLE_CODE='" + invoiceTitleChange.getInvoiceTitleCodeNew()
			+ "',INVOICE_TITLE_NAME='" + invoiceTitleChange.getInvoiceTitleNameNew() + "',INVOICE_TITLE_TAX_NO='"
			+ invoiceTitleChange.getInvoiceTitleTaxNoNew() + "' where PLAN_ID = '" + invoiceTitleChange.getPlanId()
			+ "' and ORDER_NUM = '" + invoiceTitleChange.getOrderNum() + "' and FEE_TYPE_CODE !=3203 ";
			
			String updateRealFeeTitleTrans = "update TPL_REAL_FEE set INVOICE_TITLE_CODE='" + invoiceTitleChange.getInvoiceTitleCodeNew()
			+ "',INVOICE_TITLE_NAME='" + invoiceTitleChange.getInvoiceTitleNameNew() + "',INVOICE_TITLE_TAX_NO='"
			+ invoiceTitleChange.getInvoiceTitleTaxNoNew() + "' where PLAN_ID = '" + invoiceTitleChange.getPlanId()
			+ "' and ORDER_NUM = '" + invoiceTitleChange.getOrderNum() + "' and INVOICE_SYS_ID is null   and SETTLE_CODE<>'3' AND FEE_TYPE_CODE !=3203 AND FEE_TYPE_CODE IS NOT NULL";
			
			
			//构建更新铁路杂费sql
			String updatePlanFeeTitle3203= "update TPL_PLAN_FEE set INVOICE_TITLE_CODE='" + invoiceTitleChange.getInvoiceTitleCodeNew2()
			+ "',INVOICE_TITLE_NAME='" + invoiceTitleChange.getInvoiceTitleNameNew2() + "',INVOICE_TITLE_TAX_NO='"
			+ invoiceTitleChange.getInvoiceTitleTaxNoNew2() + "' where PLAN_ID = '" + invoiceTitleChange.getPlanId()
			+ "' and ORDER_NUM = '" + invoiceTitleChange.getOrderNum() + "'  AND FEE_TYPE_CODE =3203";
			
			String updateRealFeeTitle3203 = "update TPL_REAL_FEE set INVOICE_TITLE_CODE='" + invoiceTitleChange.getInvoiceTitleCodeNew2()
			+ "',INVOICE_TITLE_NAME='" + invoiceTitleChange.getInvoiceTitleNameNew2() + "',INVOICE_TITLE_TAX_NO='"
			+ invoiceTitleChange.getInvoiceTitleTaxNoNew2() + "' where PLAN_ID = '" + invoiceTitleChange.getPlanId()
			+ "' and ORDER_NUM = '" + invoiceTitleChange.getOrderNum() + "' and INVOICE_SYS_ID is null   and SETTLE_CODE<>'3' AND FEE_TYPE_CODE =3203 AND FEE_TYPE_CODE IS NOT NULL";
			
			String invoiceTitleCodeNew = StringUtil.trimStr(invoiceTitleChange.getInvoiceTitleCodeNew());  
			String invoiceTitleCodeName = StringUtil.trimStr(invoiceTitleChange.getInvoiceTitleNameNew());
			String invoiceTitleTaxNoNew = StringUtil.trimStr(invoiceTitleChange.getInvoiceTitleTaxNoNew());
			
			String invoiceTitleCodeNew2 = StringUtil.trimStr(invoiceTitleChange.getInvoiceTitleCodeNew2());  
			String invoiceTitleCodeName2 = StringUtil.trimStr(invoiceTitleChange.getInvoiceTitleNameNew2());
			String invoiceTitleTaxNoNew2 = StringUtil.trimStr(invoiceTitleChange.getInvoiceTitleTaxNoNew2());    
			System.out.println("invoiceTitleCodeNew=="+invoiceTitleCodeNew+"====invoiceTitleCodeName===="+invoiceTitleCodeName+"----invoiceTitleTaxNoNew====="+invoiceTitleTaxNoNew);
			System.out.println("invoiceTitleCodeNew2=="+invoiceTitleCodeNew2+"====invoiceTitleCodeName2===="+invoiceTitleCodeName2+"----invoiceTitleTaxNoNew2====="+invoiceTitleTaxNoNew2);
			if(!"".equals(invoiceTitleCodeNew) && !"".equals(invoiceTitleCodeName)  && !"".equals(invoiceTitleTaxNoNew)){
				 //更新运费
				 //更新plan_fee数据
				this.getMessageDao().executeUpdateSql(updatePlanFeeTitleTrans);
				 //更新real_fee数据
				if(list!=null && list.size()>0){
					this.getMessageDao().executeUpdateSql(updateRealFeeTitleTrans);
				}
			}
			if(!"".equals(invoiceTitleCodeNew2) && !"".equals(invoiceTitleCodeName2)  && !"".equals(invoiceTitleTaxNoNew2)){
				 //更新杂费
				 //更新plan_fee数据
				this.getMessageDao().executeUpdateSql(updatePlanFeeTitle3203);
				 //更新real_fee数据
				if(list!=null && list.size()>0){
					this.getMessageDao().executeUpdateSql(updateRealFeeTitle3203);
				}
				
			}
		} catch (Exception e) {
			String errTitle = "发票抬头变更：" + invoiceTitleChange.getOrderNum();
			setLogMessage(e, errTitle);
		}
	}   

	/**
	 * *********************************运输业务接口*************************************
	 * 1 接收出厂、转库码单电文    2 接收出厂、转库码单红冲电文
	 * 1 接收运输作业计划 2 发送运输实绩 3 发送保单 4 发送车船装载清单 5 发送船舶动态信息
	 * 
	 * **********************************************************************************
	 */
	// 出厂、转库码单电文接收--20140811 --yesimin
	public void receiveMessageStackOut(TplMessageStackOut stackOut)
			throws Exception {
		try {
			//补数据钱手动清空电文表及业务表
			System.out
					.println("messageService===receiveMessageStackOut：接收出厂、转库码单电文----开始");
			String checkRepeatSql = "select id from TPL_MESSAGE_STACK_OUT where STACKING_REC_NUM = '" + stackOut.getStackingRecNum()
			+ "' and MANU_ID = '"+stackOut.getManuId()+"' and PICK_NUM = '"+stackOut.getPickNum()+"' and STATUS = '0'";
			//电文表判重
			if (messageDao.queryIdBySql(checkRepeatSql) != null){
				return;
			}
			
			checkRepeatSql = "select id from LGS_STACK_DATA where STACKING_REC_NUM = '" + stackOut.getStackingRecNum() + "' and MANU_ID = '"+stackOut.getManuId()+"' and PICK_NUM = '"+stackOut.getPickNum()+"'";
			//业务表判重
			if (messageDao.queryIdBySql(checkRepeatSql) != null){
				return;
			}
			// 1、 处理明细，如果主项件数>0则解析明细
			if (stackOut.getNGrassPack().intValue() > 0) {
				stackOut.setStackItemSet(BeanConvertUtils.strToBean(stackOut
						.getDetail(), CLASS_STACK_OUT_PACK));
			} else {
				stackOut.setStackItemSet(new HashSet());
			}
			//处理空model
			Iterator it = stackOut.getStackItemSet().iterator();
			while (it.hasNext()) {
				TplMessageStackOutPack transPack = (TplMessageStackOutPack) it.next();
				if ("".equals(transPack.getPackNum())) {
					it.remove();
				}
			}
			
			String checkRepeatSql1 = "select id from TPL_MESSAGE_STACK_OUT where STACKING_REC_NUM = '"
					+ stackOut.getStackingRecNum()
					+ "' and MANU_ID = '"
					+ stackOut.getManuId() + "'"
					+ "and PICK_NUM = '"+stackOut.getPickNum()+"' and STATUS is null ";
			
			Long id = messageDao.queryIdBySql(checkRepeatSql1);
//			if (messageDao.queryIdBySql(checkRepeatSql1) != null)
//				return;
			stackOut.setId(id);
			
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_STACK_OUT_PACK t where t.STACKING_REC_NUM = '"
					+ stackOut.getStackingRecNum()+"' and t.pick_num = '"+stackOut.getPickNum()+"' and t.manu_id = '"+stackOut.getManuId()+"'");
			
			
			// 3、 插入电文主子表：TplMessageStackOut、TplMessageStackOutPack
			messageDao.insertTplMessageStackOut(stackOut);
			System.out.println(stackOut.getNStackingRec());
			System.out.println(itemCount.intValue());
			System.out.println(stackOut.getStackItemSet().size());
			if(stackOut.getNStackingRec().intValue() == itemCount.intValue()+stackOut.getStackItemSet().size()){
				
				messageDao.updatePlanStatus("update TPL_MESSAGE_STACK_OUT set STATUS = '0' where STACKING_REC_NUM = '" + stackOut.getStackingRecNum()+"' and MANU_ID = '"+stackOut.getManuId()+"' and PICK_NUM = '"+stackOut.getPickNum()+"'");
				
				messageDao.insertTplStackOut(stackOut);
				System.out
					.println("messageService===receiveMessageStackOut：接收出厂、转库码单电文----插业务表结束");
			}
			
			String trnpModeCode = stackOut.getTrnpModeCode();
			   if(trnpModeCode != null&&trnpModeCode.length()>1){
			    String subTrnp = trnpModeCode.substring(1, 2);
			    System.out.println("receiveMessageStackOut：subTrnp:" +subTrnp);
			    if("1".equals(subTrnp)){
			     sendMessageStackOut.sendStackOut(stackOut);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errTitle = "出厂码单：" + stackOut.getPickNum();
			setLogMessage(e, errTitle);
		}
	}


	//	出厂、转库码单红冲电文接收--20140811  --yesimin
	public void receiveMessageStackOutRed(TplMessageStackOutRed stackOutRed)
			throws Exception {
		try {
			System.out
					.println("messageService===receiveMessageStackOutRed：接收出厂、转库码单红冲电文----开始");
			if (stackOutRed == null)
				return;

			// 1、插入电文红冲表（先判重）
			String checkRepeatSql = "select id from TPL_MESSAGE_STACK_OUT_RED ma "
					+ "where ma.PACK_ID ='" + stackOutRed.getPackId()
					+ "' and ma.STACKING_NUM ='" + stackOutRed.getStackingNum()
					+ "' and ma.MANU_ID = '" + stackOutRed.getManuId() + "'";
			System.out
					.println("messageService===receiveMessageStackOutRed：判业红冲电文表是否有此捆包："
							+ checkRepeatSql);
			if (messageDao.queryIdBySql(checkRepeatSql) != null)
				return;
			messageDao.insertTplMessageStackOutRed(stackOutRed);
			System.out
					.println("messageService===receiveMessageStackOutRed：接收出厂、转库码单红冲电文----插电文表结束");

			// 2、业务处理：插入红冲码单业务表(先判断重复)
			String checkRepeatSql1 = "select id from LGS_STACK_RED ma "
					+ "where ma.PACK_ID ='" + stackOutRed.getPackId()
					+ "' and ma.STACKING_NUM ='" + stackOutRed.getStackingNum()
					+ "' and ma.MANU_ID = '" + stackOutRed.getManuId() + "'";
			System.out
					.println("messageService===receiveMessageStackOutRed：判红冲业务表是否有此捆包："
							+ checkRepeatSql1);
			if (messageDao.queryIdBySql(checkRepeatSql1) != null)
				return;
			messageDao.doTplStackOutRed(stackOutRed);
			System.out
					.println("messageService===receiveMessageStackOutRed：接收出厂、转库码单红冲电文----业务处理结束");
		} catch (Exception e) {
			e.printStackTrace();
			String errTitle = "码单红冲提单：" + stackOutRed.getPickNum();
			setLogMessage(e, errTitle);
		}

	}

	// 接收运输计划执行时间电文
	public void receiveTransDeliveryPlanTime(TplTransDeliveryPlanTime tplTransDeliveryPlanTime) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		
		String billStartTime = StringUtil.trimStr(tplTransDeliveryPlanTime.getBillStartTime());
		String billEndTime = StringUtil.trimStr(tplTransDeliveryPlanTime.getBillEndTime());
		if(!"".equals(billStartTime) && billStartTime.length()==12){
			//将时间转化为日期格式的
			tplTransDeliveryPlanTime.setBillStartDate(format.parse(billStartTime));
		}
		if(!"".equals(billEndTime) && billEndTime.length()==12){
			tplTransDeliveryPlanTime.setBillEndDate(format.parse(billEndTime));
		}
		tplTransDeliveryPlanTime.setCreateDate(new Date());
		messageDao.insert(tplTransDeliveryPlanTime, TplTransDeliveryPlanTime.class);
	}
	
	
	
	
	// 接收运输作业计划
	public void receiveTransPlan(TplMessageTransPlan transPlan) throws Exception {
		try {
			// yesimin  20150508  湛江提单号截取
			if(transPlan.getPickNum()!=null&&(transPlan.getPickNum().endsWith("_A")||transPlan.getPickNum().endsWith("_B")))
			{
				String[] pickNum=transPlan.getPickNum().split("_");
				transPlan.setPickNum(pickNum[0]);
			}
			
			//if ("000000".equals(transPlan.getPr oviderId()) || "999999".equals(transPlan.getProviderId())
			//		|| "BGSA".equals(transPlan.getProviderId()) || transPlan.getProviderId() == null||transPlan.getProviderId().length()==4) {
				
			//}
			
			//判断重复，如果是红冲，会有多次，不判定重复。
			if (SystemConstants.OPERATION_TYPE_INSERT.equals(transPlan.getOperationType())) {
				//transPlan.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
				transPlan.setUnitId(transPlan.getAuthorizedUnitId());
				String checkRepeatSql = "select id from TPL_MESSAGE_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId()
						+ "' and OPERATION_TYPE = '" + transPlan.getOperationType() + "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}
			
			/**
			 * 处理制造单元特殊红冲：如果件数是0，证明全部要红冲，不需要解析明细。
			 * modified by Forest 20080514
			 */
			if (transPlan.getPlanCount().intValue() > 0) {
				transPlan.setTransItemSet(BeanConvertUtils.strToBean(transPlan.getDetail(), CLASS_TRANS_PACK));
			} else {
				transPlan.setTransItemSet(new HashSet());
			}

			
			// 处理空model并获取品种代码
			Map productTypeMap = new HashMap();
			Map orderTypeMap = new HashMap();
			Map productTypePriceMap = new HashMap();
			//List lstpackOrderType = new ArrayList();
			Iterator itPack = transPlan.getTransItemSet().iterator();
			while (itPack.hasNext()) {
				TplMessageTransPack transPack = (TplMessageTransPack) itPack.next();
				if ("".equals(transPack.getTransPlanId())) {
					itPack.remove();
				} else {
					productTypeMap.put(transPack.getProductType()+transPack.getPackOrderType(), transPack.getProductType());
					orderTypeMap.put(transPack.getProductType()+transPack.getPackOrderType(), transPack.getPackOrderType());
					//lstpackOrderType.add(transPack.getPackOrderType());
				}
			}

			String queryTransPlanIdBySql = "select id from TPL_MESSAGE_TRANS_PLAN where TRANS_PLAN_ID = '"
					+ transPlan.getTransPlanId() + "' and OPERATION_TYPE = '" + transPlan.getOperationType()
					+ "'  and  STATUS is null  order by ID DESC";
			Long id = messageDao.queryIdBySql(queryTransPlanIdBySql);
			transPlan.setId(id);
			//transPlan.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
			transPlan.setUnitId(transPlan.getAuthorizedUnitId());
		//	transPlan.setUnitName((String) SystemConfigUtil.ASSOCIATED_AGENCY_LIST.get(transPlan.getUnitId()));
			
//			SearchTitleService searchTitleService = (SearchTitleService)BeanFactory.findService(
//					"searchTitleService", SearchTitleService.class);
//				      String str1 = searchTitleService.acquireHeadByCode("W001", transPlan.getAuthorizedUnitId())==null?"":searchTitleService.acquireHeadByCode("W001", transPlan.getAuthorizedUnitId());
//				      transPlan.setUnitName(str1);
			transPlan.setUnitName(AcquireAgencyName.getAgencyName(transPlan.getUnitId()));//获取委托机构名称20110308
			
			// 转换地区公司别
			transPlan.setOrderUserId(this.getAreaCompanyCode(transPlan.getOrderUserId()));
			if (transPlan.getRainCloth() == null || "".equals(transPlan.getRainCloth().trim())) {
				transPlan.setRainCloth(SystemConstants.TRANS_PLAN_RAIN_CLOTH_NO);
			}
			transPlan.setInsureStatus(SystemConstants.TRANS_PLAN_INSURANCE_UNMAKE);

			// added by Forest 20080417 获取保险费率,将保险费率存入捆包表中。
			double insurRate = 0.0;
			
			if ("BGTM".equals(transPlan.getManuId())) {
					insurRate = INSURANCE_RATE_MEI;
			} else {
					insurRate = INSURANCE_RATE;
			}
		

			// 获取品种基价
			/*int i = 0;
			Set set = productTypeMap.keySet();
			Iterator setIt = set.iterator();
			while (setIt.hasNext()) {
				String key1=(String) setIt.next();
				//String type = (String) setIt.next();
				
				String type = (String) productTypeMap.get(key1);
				String packOrderType=(String) orderTypeMap.get(key1);
				//String packOrderType = (String) lstpackOrderType.get(i);
				logger.info("packOrderType1111111111:" + packOrderType);
				BigDecimal price = new BigDecimal(0);
				if ("BGTM".equals(transPlan.getManuId())) {
					price = messageDao.queryBasePrice(transPlan.getManuId(), type, transPlan.getConsineeCode(), packOrderType);
				} else {
					price = messageDao.queryBasePrice("BGSA", type, transPlan.getConsineeCode(), packOrderType);
				}
				double tmpRate = 0;
				// 物流室控货的计划，将保险费率设置为单价*千分之0.85;梅钢是千分之0.65
				if ("BGTM".equals(transPlan.getManuId())) {
					if ("11".equals(transPlan.getPickType())|| "31".equals(transPlan.getPickType()) || "66".equals(transPlan.getPickType())){
						tmpRate = 0.0;
					}else{
						tmpRate = price.doubleValue() * INSURANCE_RATE_MEI;
						tmpRate = getFormatDouble(tmpRate, 2);
					}
				} else {
					// 4月1日开始执行新的费率0.00043 TODO
					// Calendar cl = Calendar.getInstance();
					// cl.set(2008, 3, 1, 0, 0, 0); // 2008-4-1 0:00:00
					// Date date0401 = cl.getTime();
					// if (today.compareTo(date0401) >= 0)
					// tmpRate = price.doubleValue() * INSURANCE_RATE;
					// else
					// tmpRate = price.doubleValue() * 0.00085;
					if ("11".equals(transPlan.getPickType())|| "31".equals(transPlan.getPickType()) || "66".equals(transPlan.getPickType())){
						tmpRate = 0.0;
					}else{
						tmpRate = price.doubleValue() * INSURANCE_RATE;
						tmpRate = getFormatDouble(tmpRate, 2);
					}
				}
				productTypePriceMap.put(type+packOrderType, new BigDecimal(tmpRate));
				i++;
			}

			Iterator it = transPlan.getTransItemSet().iterator();
			while (it.hasNext()) {
				TplMessageTransPack transPack = (TplMessageTransPack) it.next();
				transPack.setInsuranceRate(new BigDecimal(insurRate));
				transPack.setInsurancePrice((BigDecimal) productTypePriceMap.get(transPack.getProductType()+transPack.getPackOrderType()));
			}*/
			// 如果是水运，船批号不空，将水运的计划实际装船量置为计划量
			if (SystemConstants.TRANS_TYPE_SHIP.equals(transPlan.getTransType()) && transPlan.getShipId() != null) {
				if(transPlan.getPlanGrossWeight().doubleValue()==0){
					transPlan.setActGrossWeight(transPlan.getPlanNetWeight());
				}else{
					transPlan.setActGrossWeight(transPlan.getPlanGrossWeight());
				}
				transPlan.setActCount(transPlan.getPlanCount());
				transPlan.setActNetWeight(transPlan.getPlanNetWeight());
				
			} else {
				transPlan.setActCount(new Long(0));
				transPlan.setActNetWeight(new BigDecimal(0.0));
				transPlan.setActGrossWeight(new BigDecimal(0.0));
			}
			//母船批号为空，则用子船批替代母船批号，并建立相应的母船批by panyouyong 20130305
			if(null == transPlan.getShipIdM() || "".equals(transPlan.getShipIdM())){
				transPlan.setShipIdM(transPlan.getShipId());
			}

			// 查询该计划已经新增的捆包数，来判定本次接收是否结束
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_TRANS_PACK t where t.PLAN_ID = "
					+ transPlan.getId());

			// 判定重复，解决电文同步发送的问题，临时过渡方案 add by zhengfei 20080128
			if (transPlan.getTransItemSet() != null && transPlan.getTransItemSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = transPlan.getTransItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageTransPack transPack = (TplMessageTransPack) itDouble.next();
					someOnePack = transPack.getPackId();
					//需要获得产品大类
					if(transPack.getOrderNum()!= null && !transPack.getOrderNum().trim().equals("")){
					productClass = transPack.getOrderNum().substring(0, 1);
					}
				}
				// 判断该计划下的捆包号是否已经入库
				Long doublePackCount=null;
				if(OPERATION_TYPE_APPLEND.equals(transPlan.getOperationType())){
					 doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_TRANS_PACK t "
							+ " where t.trans_plan_id = '" + transPlan.getTransPlanId()+ "'   and t.PACK_ID = '" + someOnePack + "' and t.PRODUCT_CLASS='" + productClass + "' ");//新增商品大类
				}
				else{  doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_TRANS_PACK t "
						+ " where t.PLAN_ID = " + transPlan.getId() + "   and t.PACK_ID = '" + someOnePack + "' and t.PRODUCT_CLASS='" + productClass + "' " );//新增产品大类
				}
				
				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;
				
			}
			messageDao.insertTplTransPlan(transPlan);
			
			if (id == null) {
				String querySql = "select id from TPL_MESSAGE_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId()
						+ "' and OPERATION_TYPE = '" + transPlan.getOperationType() + "' and  STATUS is null  order by ID DESC";
				id = messageDao.queryIdBySql(querySql);
				transPlan.setId(id);
			}

			// 判断计划是否结束，如果结束插入业务表
			if (transPlan.getPlanCount().intValue() == transPlan.getTransItemSet().size() + itemCount.intValue()) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_TRANS_PLAN set STATUS = '0' where TRANS_PLAN_ID = '"
						+ transPlan.getTransPlanId() + "'   and OPERATION_TYPE = '" + transPlan.getOperationType() + "'");

				if (OPERATION_TYPE_NEW.equals(transPlan.getOperationType())) {
					/**
					 * 新增处理
					 * 属地系统(制造单元)红冲后的计划重新下发处理:如果是制造单元新增的计划，则如果本次新增的计划3PL已经存在，则证明不在本次计划里边的捆包需要红冲的。
					 */
					String checkPlanExist = "select id from TPL_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId() + "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doTransPlanRedOper(transPlan, "10");
					} else {
						Long seqId = null;
						// 如果是水运，船批号不空，则将船批信息新增至船批表
						if (SystemConstants.TRANS_TYPE_SHIP.equals(transPlan.getTransType()) && transPlan.getShipId() != null) {
							seqId = messageDao.insertTplTransShipSeq(transPlan);
							
						}
						messageDao.insertTplTransPlanByBatch(transPlan, seqId);
						
						// 20180110 依据材料合同判断是否是现货，抛送给欧冶物流
						Long checkExistId = messageDao.queryIdBySql("select id from tpl_trans_plan t where t.trans_type = '1' and t.unit_id = '0001' and exists (select 1 from tpl_trans_pack p where p.plan_id = t.id and substr(p.order_num,3,1) = 'W') and t.trans_plan_id = '"+transPlan.getTransPlanId()+"'");
						if (checkExistId != null){
							System.out.println("---收入费用查询代理费用信息更新字段SEND_STATUS = '10'---");
							List tplTransPlanList = messageDao.queryTplTransPlanById(transPlan.getTransPlanId());
							if(tplTransPlanList!=null&&tplTransPlanList.size()!=0){
								TplTransPlan tplTransPlan = (TplTransPlan)tplTransPlanList.get(0);
								System.out.println("tplTransPlan.getId()-->"+tplTransPlan.getId());
								tplTransPlan.setSendStatus("10");
								messageDao.update(tplTransPlan, TplTransPlan.class);
								
								// 现货代理业务，计划信息导入临时表，由定时任务抛送欧冶
								Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '1' and SEND_STATUS = '00' and TRANS_PLAN_ID = '"+transPlan.getTransPlanId()+"'");
								if (tempId == null) {
									TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
									transPlanTemp.setOperationType("1");
									transPlanTemp.setTransPlanId(tplTransPlan.getTransPlanId());
									transPlanTemp.setBillType("10");
									transPlanTemp.setCreateDate(new Date());
									transPlanTemp.setSendStatus("00");
									transPlanTemp.setPickNum(tplTransPlan.getPickNum());
									messageDao.insert(transPlanTemp,TplTransPlanTemp.class);
								}
							}
						}
//						System.out.println("---收入费用查询代理费用信息开始---"+transPlan.getTransPlanId());
//						Long feeId = messageDao.queryIdBySql("select id from TPL_PLAN_FEE fe where fe.BUSINESS_TYPE = '99' and fe.PLAN_ID = '"+transPlan.getTransPlanId()+"'");
//						if(feeId != null){
//							System.out.println("---收入费用查询代理费用信息更新字段SEND_STATUS = '10'---");
//							List tplTransPlanList = messageDao.queryTplTransPlanById(transPlan.getTransPlanId());
//							if(tplTransPlanList!=null&&tplTransPlanList.size()!=0){
//								TplTransPlan tplTransPlan = (TplTransPlan)tplTransPlanList.get(0);
//								System.out.println("tplTransPlan.getId()-->"+tplTransPlan.getId());
//								tplTransPlan.setSendStatus("10");
//								messageDao.update(tplTransPlan, TplTransPlan.class);
//								
//								// 现货代理业务，计划信息导入临时表，由定时任务抛送欧冶
//								Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '1' and SEND_STATUS = '00' and TRANS_PLAN_ID = '"+transPlan.getTransPlanId()+"'");
//								if (tempId == null) {
//									TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
//									transPlanTemp.setOperationType("1");
//									transPlanTemp.setTransPlanId(tplTransPlan.getTransPlanId());
//									transPlanTemp.setBillType("10");
//									transPlanTemp.setCreateDate(new Date());
//									transPlanTemp.setSendStatus("00");
//									transPlanTemp.setPickNum(tplTransPlan.getPickNum());
//									messageDao.insert(transPlanTemp,TplTransPlanTemp.class);
//								}
//							}
//						}
						//运输线材和管坯-运输端
						System.out.println("运输线材和管坯-运输端");
						messageDao.checkTplTransPlanPick(transPlan);
						
						Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '1' and SEND_STATUS = '00' and TRANS_PLAN_ID = '"+transPlan.getTransPlanId()+"'");
						if(tempId == null){
							//地区公司自提业务，计划导入临时表，由定时任务抛送欧冶
							String consineType = transPlan.getConsignType();
							String providerId = transPlan.getProviderId(); 
							String ladingSpot = transPlan.getLadingSpot();
							String transType = transPlan.getTransType();
							String manuId = transPlan.getManuId();
							if ((consineType != null && consineType.equals("20")
									&& providerId.equals("001518") && transType.equals("1"))
									|| (providerId.equals("009477") && transType.equals("1") && (manuId.equals("BGTM") || manuId.equals("BGNA")))
									|| (providerId.equals("155682") && transType.equals("1") && (manuId.equals("BGBW") || manuId.equals("BSKV")))
									|| (providerId.equals("150066") && transType.equals("1") && manuId.equals("BGSA"))) {
								TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
								transPlanTemp.setOperationType("1");
								transPlanTemp.setTransPlanId(transPlan.getTransPlanId());
								transPlanTemp.setBillType("20");
								transPlanTemp.setCreateDate(new Date());
								transPlanTemp.setSendStatus("00");
								transPlanTemp.setPickNum(transPlan.getPickNum());
								messageDao.insert(transPlanTemp, TplTransPlanTemp.class);
							}
						}
					}
				} else if (OPERATION_TYPE_RED.equals(transPlan.getOperationType())) {
					/**
					 * 属地系统(制造单元)的红冲：是计划全部撤销以后，再重新上来正确的计划，不支持单个材料撤销-----提单类型：6开头的。
					 * 管控系统的红冲：支持单个材料的红冲-----提单类型：除6开头的提单类型以外的提单
					 * 
					 */
					if ("6".equals(transPlan.getPickType().substring(0, 1))||"7".equals(transPlan.getPickType().substring(0, 1))) {
						/**
						 * modified by Forest 20080410
						 * 制造单元的红冲按照统一模式：制造单元的红冲下发，如果计划是未执行状态则将计划删除；如果不是未执行状态，则将计划先结案。在接收到新的计划后做后续处理。
						 * =====特殊处理：厂内直发的铁运特殊处理，计划不能删除，要先做结案=====
						 */
						if ("62".equals(transPlan.getPickType()) && SystemConstants.TRANS_TYPE_TRAIN.equals(transPlan.getTransType())) {
							messageDao.doTransPlanRedOper(transPlan, "01");
						} else {
							messageDao.doTransPlanRedOper(transPlan, "00");
						}

					} else {
						messageDao.doTransPlanRedOper(transPlan, null);
					}
					
					System.out.println("---收入费用查询代理费用信息开始---"+transPlan.getTransPlanId());
					Long feeId = messageDao.queryIdBySql("select id from TPL_PLAN_FEE fe where fe.BUSINESS_TYPE = '99' and fe.PLAN_ID = '"+transPlan.getTransPlanId()+"'");
					Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '0' and SEND_STATUS = '00' and TRANS_PLAN_ID = '"+transPlan.getTransPlanId()+"'");
					
					if(tempId == null ){
						if (feeId != null) {
							// 现货代理业务，计划信息导入临时表，由定时任务抛送欧冶
							TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
							transPlanTemp.setOperationType("0");// 红冲
							transPlanTemp.setTransPlanId(transPlan.getTransPlanId());
							transPlanTemp.setBillType("10");
							transPlanTemp.setCreateDate(new Date());
							transPlanTemp.setSendStatus("00");
							transPlanTemp.setPickNum(transPlan.getPickNum());
							messageDao.insert(transPlanTemp,TplTransPlanTemp.class);
						}
						//地区公司自提业务，计划导入临时表，由定时任务抛送欧冶
						String consineType = transPlan.getConsignType();
						String providerId = transPlan.getProviderId(); 
						String ladingSpot = transPlan.getLadingSpot();
						String transType = transPlan.getTransType();
						String manuId = transPlan.getManuId();
						if ((consineType != null && consineType.equals("20")
								&& providerId.equals("001518") && transType.equals("1"))
								|| (providerId.equals("009477") && transType.equals("1") && (manuId.equals("BGTM") || manuId.equals("BGNA")))
								|| (providerId.equals("155682") && transType.equals("1") && (manuId.equals("BGBW") || manuId.equals("BSKV")))
								|| (providerId.equals("150066") && transType.equals("1") && manuId.equals("BGSA"))) {
							TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
							transPlanTemp.setOperationType("0");//红冲
							transPlanTemp.setTransPlanId(transPlan.getTransPlanId());
							transPlanTemp.setBillType("20");
							transPlanTemp.setCreateDate(new Date());
							transPlanTemp.setSendStatus("00");
							transPlanTemp.setPickNum(transPlan.getPickNum());
							messageDao.insert(transPlanTemp, TplTransPlanTemp.class);
						}
					}
				} else if (OPERATION_FORCE_OVER.equals(transPlan.getOperationType())) {
					/**
					 * 强制结案 属地系统的强制结案：强制结案的材料是需要保留的，其余的全部删除，操作类型是90开头。
					 */
					//烟宝按量强制结案汽运计划，业务表强制结案
					if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(transPlan.getUnitId())
							&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(transPlan.getPlanParticle())) {
						messageDao.updatePlanStatus("update TPL_TRANS_PLAN set STATUS = '2' where TRANS_PLAN_ID ='"
								+ transPlan.getTransPlanId() + "'");
					} else {
						messageDao.doTransPlanRedOper(transPlan, "10");
					}
				}else if (OPERATION_TYPE_APPLEND.equals(transPlan.getOperationType())) {
			        
					//获取已入库计划id,若有则进行更新操作,否则进行插入操作
					String checkPlanExist = "select id from TPL_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId() + "'";
					Long updateId = null;
			        updateId = messageDao.queryIdBySql(checkPlanExist);
					
					//若是水运,1.新增时追加水运船批记录 2. 更新时将本次增加量累加到船批上
					Long seqId = null;
					if (SystemConstants.TRANS_TYPE_SHIP.equals(transPlan.getTransType()) && transPlan.getShipId() != null) {
						seqId = messageDao.insertTplTransShipSeq(transPlan);
					}
					//追加操作:1.插入捆包数据;2.更新主表总量
			        if (updateId!= null) {
						messageDao.insertTplTransPlanByApplend(transPlan,"20",updateId,seqId);//插入捆包并更新
						messageDao.doTransPlanFixOper(transPlan, "20");
					} else {
						messageDao.insertTplTransPlanByApplend(transPlan,"10",updateId,seqId);//插入主表子表
					}
			        
			        
				}
				if ("000000".equals(transPlan.getProviderId()) || "999999".equals(transPlan.getProviderId())
						|| "BGSA".equals(transPlan.getProviderId()) || transPlan.getProviderId() == null||transPlan.getProviderId().length()==4) {
					   messageDao.updateTransPlanforStatus(transPlan);
					   messageDao.deleteTransSeqforShipId(transPlan);
				}
				
				TplMessageTransPlanResult tPlanResult=new TplMessageTransPlanResult();
				tPlanResult.setCreateTime(new Date());
				tPlanResult.setOprationType(transPlan.getOperationType());
				tPlanResult.setRecordeId(transPlan.getTransPlanId());
				tPlanResult.setRecordeTime(new Date());
				tPlanResult.setRecordeType("30");
				tPlanResult.setRemark("");
//				this.sendTransPlanResult(tPlanResult);
				System.out.println("====webserviceTransPlan==============");
				Iterator it = transPlan.getTransItemSet().iterator();
				String orderNum;
				String finUserId =null;
				TplMessageTransPack transPack =null;
				boolean flag =true;
				while (it.hasNext()&&flag) {
					TplMessageTransPack transPackIt = (TplMessageTransPack) it.next();
					if(null != transPackIt.getPackId() && !"".equals(transPackIt.getPackId())){
						orderNum= transPackIt.getOrderNum();
						System.out.println("====webserviceTransPlan======getFinUserInfo========");
						finUserId= messageDao.getFinUserInfo(orderNum);
						if(null!=finUserId && !"".equals(finUserId)){
							transPack =transPackIt;
							flag = false;
						}
					}
				}
				System.out.println("====webserviceTransPlan======循环结束========");
				Long seqId ;
				String seqIdStr = messageDao.getSeqIdByTransPackId(transPack);
				if(null!= seqIdStr && !"".equals(seqIdStr)){
					seqId = Long.valueOf(seqIdStr);
				}else{
					seqId =null;
				}
				
				System.out.println("====webserviceTransPlan========ManuId=="+transPlan.getManuId()+"===transType=="+transPlan.getTransType()+"===finUserId==="+finUserId+"====OrderUserId=="+transPlan.getOrderUserId()+"======");
				if(null!=finUserId &&!"".equals(finUserId) && (OPERATION_TYPE_NEW.equals(transPlan.getOperationType())||OPERATION_TYPE_RED.equals(transPlan.getOperationType()))){
					if((("BGSA".equals(transPlan.getManuId())|| "BGTX".equals(transPlan.getManuId()))&& "3".equals(transPlan.getTransType())&& "10".equals(transPlan.getConsignType())
								&&((("000706".equals(finUserId)&&"035169".equals(transPlan.getOrderUserId()))) || ("000706".equals(finUserId)&&"035169".equals(transPlan.getOrderUserId()))))
						||(("BGSA".equals(transPlan.getManuId())|| "BGTX".equals(transPlan.getManuId()))&&"039665".equals(finUserId)&&"037413".equals(transPlan.getOrderUserId()) &&
								"1".equals(transPlan.getTransType())&& "10".equals(transPlan.getConsignType()))
						||("BSZG".equals(transPlan.getManuId())&&"006892".equals(finUserId)&&"063283".equals(transPlan.getOrderUserId()) &&"3".equals(transPlan.getTransType())
								&& "10".equals(transPlan.getConsignType()))
						||(("BGBW".equals(transPlan.getManuId())|| "BSKV".equals(transPlan.getManuId()))&& "004950".equals(finUserId)&&"099240".equals(transPlan.getOrderUserId()) 
								&&"2".equals(transPlan.getTransType())&& "10".equals(transPlan.getConsignType()))
						||("BGTM".equals(transPlan.getManuId())&&"000010".equals(finUserId)&&"000010".equals(transPlan.getOrderUserId()) &&"3".equals(transPlan.getTransType())
								&& "10".equals(transPlan.getConsignType()))){
						
						sendTransPlanWebServicce.sendTransPlan(transPlan, seqId);
					}
				}
			}
				
		} catch (Exception e) {
			String errTitle = "运输作业计划：" + transPlan.getTransPlanId();
			setLogMessage(e, errTitle);
		} 
		
	}

	
	// 发送运输作业实绩
	public void sendTransResult(TplMessageTransRealMain transReal) throws Exception {
		System.out.println("===========sendTransResult=====sendTransResultWebServicce===="+sendTransResultWebServicce);
		messageDao.saveTransResult(transReal);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(transReal.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(transReal.getUnitId())) { //wuyang20110309
			// 补充空model,达到指定循环次数
			List list = new ArrayList();
			list.addAll(transReal.getTransRealItems());
			int leftCircularTimes = 0;
			if (transReal.getTransRealItems().size() % CIRCULAR_TRANS_ITEM_TIMES != 0) {
				leftCircularTimes = CIRCULAR_TRANS_ITEM_TIMES - transReal.getTransRealItems().size() % CIRCULAR_TRANS_ITEM_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageTransRealItem());
			}

			List detailList = getMsgDetail(list, CIRCULAR_TRANS_ITEM_TIMES, CLASS_TRANS_REAL_ITEM);
			System.out.println("===========sendTransResult=====sendTransResultWebServicce===="+sendTransResultWebServicce);
			sendTransResultWebServicce.sendTransResult(transReal);
			for (int j = 0; j < detailList.size(); j++) {
				transReal.setDetail((String) detailList.get(j));
				sendMsgService.sendMsg(MESSAGE_SEND_N0_TRANS_REAL, null, transReal);
			}
		}
	}

	// 发送保单
	public void sendInsurance(TplMessageInsuranceBill insuranceBill) throws Exception {
		messageDao.saveInsurance(insuranceBill);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(insuranceBill.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(insuranceBill.getUnitId())) { //wuyang20110309
			sendMsgService.sendMsg(MESSAGE_SEND_N0_INSURANCE_BILL, null, insuranceBill);
		}
	}

	// 发送船舶动态信息
	public void sendShipTrace(TplTransShipTrace transShipTrace) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_SHIP_TRACE, null, transShipTrace);
	}

	// 发送车船装载清单
	public void sendTransList(TplMessageTransOnMain transOn) throws Exception {
		transOn.setCreateDate(new Date());
		messageDao.insert(transOn, TplMessageTransOnMain.class);

		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.addAll(transOn.getTransOnItems());
		int leftCircularTimes = 0;
		if (transOn.getTransOnItems().size() % CIRCULAR_TRANS_LOADING_TIMES != 0) {
			leftCircularTimes = CIRCULAR_TRANS_LOADING_TIMES - transOn.getTransOnItems().size() % CIRCULAR_TRANS_LOADING_TIMES;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplMessageTransOnItem());
		}

		List detailList = getMsgDetail(list, CIRCULAR_TRANS_LOADING_TIMES, CLASS_TRANS_LOADING_ITEM);
		for (int j = 0; j < detailList.size(); j++) {
			transOn.setDetail((String) detailList.get(j));
			sendMsgService.sendMsg(MESSAGE_SEND_N0_TRANS_LOADING, null, transOn);
		}
	}
	
	// 发送车船装载清单  铁总接口 发送给东方钢铁  add wyx
	public void sendTransList1(TplMessageTransOnMain transOn,TplTransSeq tplTransSeq) throws Exception {
		TplMessageTrainsOnMain tplMessageTrainsOnMain = new TplMessageTrainsOnMain();
		tplMessageTrainsOnMain.setOperationType(transOn.getOperationType());
		tplMessageTrainsOnMain.setProviderId(transOn.getProviderId());
		tplMessageTrainsOnMain.setProviderName(transOn.getProviderName());
		tplMessageTrainsOnMain.setTransPlanId(transOn.getTransPlanId());
		tplMessageTrainsOnMain.setTrainId(transOn.getShipId());
		System.out.println(transOn.getSeqCreateDate());
		tplMessageTrainsOnMain.setCreateDate(transOn.getSeqCreateDate());
		System.out.println(tplMessageTrainsOnMain.getCreateDate());
		tplMessageTrainsOnMain.setGoodBillCode(transOn.getGoodBillCode());
		tplMessageTrainsOnMain.setLeaveDate(transOn.getLeaveDate());
		tplMessageTrainsOnMain.setPlanEndDate(transOn.getPlanEndDate());
		tplMessageTrainsOnMain.setSendDate(new Date());
		
		tplMessageTrainsOnMain.setLadingSpot(tplTransSeq.getStartSpotCode());
		tplMessageTrainsOnMain.setLadingSpotName(tplTransSeq.getStartSpotName());
		tplMessageTrainsOnMain.setDestSpot(tplTransSeq.getDestSpotCode());
		tplMessageTrainsOnMain.setDestSpotName(tplTransSeq.getDestSpotName());
		tplMessageTrainsOnMain.setConsineeCode(tplTransSeq.getConsineeCode());
		tplMessageTrainsOnMain.setConsineeName(tplTransSeq.getConsineeName());
		
		messageDao.insert(tplMessageTrainsOnMain, TplMessageTrainsOnMain.class);

		sendMsgService.sendMsg(MESSAGE_SEND_N1_TRANS_LOADING, null, tplMessageTrainsOnMain);
	}

	// 将子bean转化成String
	private List getMsgDetail(List list, int circularTimes, String detailBeanName) throws Exception {
		List detailList = new ArrayList();
		int circular = list.size() / circularTimes;
		for (int i = 0; i < circular; i++) {
			Object[] tempArray = list.subList(i * circularTimes, (i + 1) * circularTimes).toArray();
			detailList.add(BeanConvertUtils.beanToStr(tempArray, detailBeanName));
		}
		return detailList;
	}

	// 将子bean转化成String
	/**
	 * 暂时不用
	 */
	// private List getMsgDetail(Set set, int circularTimes, String
	// detailBeanName) throws Exception {
	// List list = new ArrayList();
	// list.addAll(set);
	// int circular;
	// if (set.size() % circularTimes != 0) {
	// circular = set.size() / circularTimes + 1;
	// } else {
	// circular = set.size() / circularTimes;
	// }
	// List detailList = new ArrayList();
	// for (int i = 0; i < circular; i++) {
	// int fromIndex = i * circularTimes;
	// int endIndex = (i + 1) * circularTimes < set.size() ? (i + 1) *
	// circularTimes : set.size();
	// Object[] tempArray = list.subList(fromIndex, endIndex).toArray();
	//
	// String endStr = getSpecificLengthStr(" ", (circularTimes -
	// tempArray.length)
	// * BeanConvertUtils.getDetailBeanLength(detailBeanName));
	// detailList.add(BeanConvertUtils.beanToStr(tempArray, detailBeanName) +
	// endStr);
	// detailList.add(BeanConvertUtils.beanToStr(tempArray, detailBeanName));
	// }
	// return detailList;
	// }
	// private static String getSpecificLengthStr(String str, int length) {
	// StringBuffer sb = new StringBuffer("");
	// for (int i = 0; i < length; i++) {
	// sb.append(str);
	// }
	// return sb.toString();
	// }
	/**
	 * *********************************物流服务资源接口*************************************
	 * 
	 * 1 发送服务商开户基本信息 2 发送服务商开户仓库信息 3 发送服务商开户帐号信息
	 * 
	 * 4 接收服务商开户基本信息 5 接收服务商开户仓库信息 6 接收服务商开户帐号信息
	 * 
	 * 7 接收服务商评价 8 发送车船运力预报 9 发送仓储能力预报
	 * 
	 * 10 接收日常公文 11 发送日常公文反馈 12 发送通用信息修改申请 13 接收通用信息修改反馈
	 * 
	 * 14 上报车辆资料 15 上报船舶资料
	 * 
	 * **********************************************************************************
	 */
	// 接收日常公文
	public void receiveDocument(TplRDocument tplRDocument) throws Exception {
		try {
			
			messageDao.insert(tplRDocument, TplRDocument.class);
			String phone=StringUtil.trimStr(tplRDocument.getDcontcatTel());
			String emergencyMark=StringUtil.trimStr(tplRDocument.getEmergencyMark());
			if(!"".equals(phone)&&!"".equals(emergencyMark)){
			   String cd="";
			   if(emergencyMark.equals("1")){
				   cd="非常紧急";
			   }else if(emergencyMark.equals("2")){
				   cd="紧急";
			   }else{
				   cd="一般";
			   }
				
			String message="您在3PL平台有新的电子公文，电子公文号："+tplRDocument.getDocumentId()+"，紧急程度为【"+cd+"】，请及时处理.";
			 SendSMS.sendSMS(phone, message);
			
			}
			
		} catch (Exception e) {
			String errTitle = "接收日常公文错误: " + tplRDocument.getDocumentId();
			setLogMessage(e, errTitle);
		}
	}

	// 接收服务商评价
	public void receiveEvaluation(TplREvaluation tplREvaluation) throws Exception {
		try {
			messageDao.insert(tplREvaluation, TplREvaluation.class);
		} catch (Exception e) {
			String errTitle = "接收服务商评价错误: " + tplREvaluation.getProviderName();
			setLogMessage(e, errTitle);
		}
	}

	// 接收通用信息修改反馈
	public void receiveModifyApplyReturn(TplRModifyApplyRet tplRModifyApplyRet) throws Exception {
		try {
			messageDao.insertModifyApplyReturn(tplRModifyApplyRet);
		} catch (Exception e) {
			String errTitle = "接收通用信息反馈错误: " + tplRModifyApplyRet.getApplyId();
			setLogMessage(e, errTitle);
		}
	}

	// 接收服务商开户基本信息
	public void receiveProviderBase(TplRProvider tplRProvider) throws Exception {
		try {
			// Long pk = messageDao.queryIdBySql("select id from TPL_R_PROVIDER
			// where PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "'");
			// tplRProvider.setId(pk);
			// messageDao.update(tplRProvider, TplRProvider.class);
			// 补充开户仓库信息的服务商代码
			// String updProviderSql = "update TPL_R_PROVIDER set PROVIDER_ID =
			// '" + tplRProvider.getProviderId()
			// + "',APPLY_STATUS = '2' where APPLY_ID = '" +
			// tplRProvider.getApplyId() + "' ";
			// 补充开户仓库信息的服务商代码
			// String updWarehouseSql = "update TPL_R_PROVIDER_WAREHOUSE set
			// PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = '" + tplRProvider.getApplyId() + "' ";
			// 补充开户银行帐号信息的服务商代码
			// String updAccountSql = "update TPL_R_PROVIDER_ACCOUNT set
			// PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = '" + tplRProvider.getApplyId() + "' ";
			// 更新服务商代码
			// messageDao.executeUpdateSql(updProviderSql);
			// messageDao.executeUpdateSql(updWarehouseSql);
			// messageDao.executeUpdateSql(updAccountSql);
			// if (OPERATE_TYPE_NEW.endsWith(tplRProvider.getOperationType())) {
			// messageDao.insert(tplRProvider, TplRProvider.class);
			// } else {
			// Long pk = messageDao.queryIdBySql("select id from
			// TPL_R_PROVIDER
			// where PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "'");
			// tplRProvider.setId(pk);
			// // messageDao.update(tplRProvider, TplRProvider.class);
			// // 补充开户仓库信息的服务商代码
			// String updProviderSql = "update TPL_R_PROVIDER set PROVIDER_ID =
			// '" +
			// tplRProvider.getProviderId()
			// + "',APPLY_STATUS = '2' where APPLY_ID = ? ";
			// // 补充开户仓库信息的服务商代码
			// String updWarehouseSql = "update TPL_R_PROVIDER_WAREHOUSE set
			// PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = ? ";
			// // 补充开户银行帐号信息的服务商代码
			// String updAccountSql = "update TPL_R_PROVIDER_ACCOUNT set
			// PROVIDER_ID
			// = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = ? ";
			// // 更新服务商代码
			// messageDao.executeUpdateSql(updProviderSql);
			// messageDao.executeUpdateSql(updWarehouseSql);
			// messageDao.executeUpdateSql(updAccountSql);
			// }

			// 电文操作类型(10:新增;20:更新;00:红冲)
			String operationType = tplRProvider.getOperationType();
			String providerId = tplRProvider.getProviderId();
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				// 新增服务商基本信息,如果该服务商已存在,就不在新增
				tplRProvider.setCreateDate(new Date());
				tplRProvider.setModifyDate(new Date());
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				if (messageDao.queryIdBySql(queryIdSql) == null)
					messageDao.insert(tplRProvider, TplRProvider.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				// 以providerId为关键字,查出当前服务商基本信息表中服务商记录，用新的数据更新该记录
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if(pk==null){
					String message=providerId+"服务商还没有开户信息，不能更新。";
					setLogMessage(message,queryIdSql);
					return;
				}
				TplRProvider oldTplRProvider = (TplRProvider) messageDao.queryById(pk, TplRProvider.class);
				oldTplRProvider.setProviderName(tplRProvider.getProviderName());
				oldTplRProvider.setProviderShortName(tplRProvider.getProviderShortName());
				oldTplRProvider.setProviderEngName(tplRProvider.getProviderEngName());
				oldTplRProvider.setProviderStatus(tplRProvider.getProviderStatus());
				oldTplRProvider.setProviderType(tplRProvider.getProviderType());
				oldTplRProvider.setTaxNum(tplRProvider.getTaxNum());
				oldTplRProvider.setLocalityId(tplRProvider.getLocalityId());
				oldTplRProvider.setPrivinceId(tplRProvider.getPrivinceId());
				oldTplRProvider.setTownId(tplRProvider.getTownId());
				oldTplRProvider.setValibStarDate(tplRProvider.getValibStarDate());
				oldTplRProvider.setValibEndDate(tplRProvider.getValibEndDate());
				oldTplRProvider.setArtPerson(tplRProvider.getArtPerson());
				oldTplRProvider.setArtPersonPre(tplRProvider.getArtPersonPre());
				oldTplRProvider.setLegalPersonTele(tplRProvider.getLegalPersonTele());
				oldTplRProvider.setLegalPersonFax(tplRProvider.getLegalPersonFax());
				oldTplRProvider.setCompanyBoss(tplRProvider.getCompanyBoss());
				oldTplRProvider.setContactPerson(tplRProvider.getContactPerson());
				oldTplRProvider.setDuty(tplRProvider.getDuty());
				oldTplRProvider.setCompanyAddr(tplRProvider.getCompanyAddr());
				oldTplRProvider.setCompanyTele(tplRProvider.getCompanyTele());
				oldTplRProvider.setCompanyFax(tplRProvider.getCompanyFax());
				oldTplRProvider.setCompanyZip(tplRProvider.getCompanyZip());
				oldTplRProvider.setEmail(tplRProvider.getEmail());
				oldTplRProvider.setWeb(tplRProvider.getWeb());
				oldTplRProvider.setStandardExeInfo(tplRProvider.getStandardExeInfo());
				oldTplRProvider.setTradeRemark(tplRProvider.getTradeRemark());
				// oldTplRProvider.setTradeService(tplRProvider.getTradeService());
				oldTplRProvider.setStorageService(tplRProvider.getStorageService());
				oldTplRProvider.setTransService(tplRProvider.getTransService());
				oldTplRProvider.setMachService(tplRProvider.getMachService());
				// oldTplRProvider.setBgService(tplRProvider.getBgService());
				// oldTplRProvider.setStatus(tplRProvider.getStatus());
				oldTplRProvider.setRegisterAddr(tplRProvider.getRegisterAddr());
				oldTplRProvider.setRegisterFax(tplRProvider.getRegisterFax());
				oldTplRProvider.setRegisterTele(tplRProvider.getRegisterTele());
				oldTplRProvider.setRegisterZip(tplRProvider.getRegisterZip());
				oldTplRProvider.setRegisterId(tplRProvider.getRegisterId());
				oldTplRProvider.setRegisterFund(tplRProvider.getRegisterFund());
				oldTplRProvider.setRegCurrencyId(tplRProvider.getRegCurrencyId());
				// oldTplRProvider.setCompanyType(tplRProvider.getCompanyType());
				oldTplRProvider.setRange(tplRProvider.getRange());
				oldTplRProvider.setServiceExchange(tplRProvider.getServiceExchange());
				oldTplRProvider.setCompanyPoint(tplRProvider.getCompanyPoint());
				// oldTplRProvider.setPurPersNo(tplRProvider.getPurPersNo());
				oldTplRProvider.setInvoiceAddr(tplRProvider.getInvoiceAddr());
				oldTplRProvider.setInvoiceZip(tplRProvider.getInvoiceZip());
				// oldTplRProvider.setInformationSources(tplRProvider.getInformationSources());
				oldTplRProvider.setTransportAbility(tplRProvider.getTransportAbility());
				oldTplRProvider.setTransportWayWater(tplRProvider.getTransportWayWater());
				oldTplRProvider.setTransportWayLand(tplRProvider.getTransportWayLand());
				oldTplRProvider.setOwnerCar(tplRProvider.getOwnerCar());
				oldTplRProvider.setAssistCar(tplRProvider.getAssistCar());
				oldTplRProvider.setOwnerShip(tplRProvider.getOwnerShip());
				oldTplRProvider.setAssistShip(tplRProvider.getAssistShip());
				oldTplRProvider.setTransportAbilityRule(tplRProvider.getTransportAbilityRule());
				// oldTplRProvider.setMisConnect(tplRProvider.getMisConnect());
				// oldTplRProvider.setRemark(tplRProvider.getRemark());
				// oldTplRProvider.setCreateId(tplRProvider.getCreateId());
				// oldTplRProvider.setCreateDate(tplRProvider.getCreateDate());
				// oldTplRProvider.setModifyId(tplRProvider.getModifyId());
				oldTplRProvider.setModifyDate(new Date());
				oldTplRProvider.setRegBeginDate(tplRProvider.getRegBeginDate());
				oldTplRProvider.setRegEndDate(tplRProvider.getRegEndDate());
				oldTplRProvider.setCountryId(tplRProvider.getCountryId());
				oldTplRProvider.setBusinessObjId(tplRProvider.getBusinessObjId());
				oldTplRProvider.setBusinessObjName(tplRProvider.getBusinessObjName());
				oldTplRProvider.setTaxNumAttach(tplRProvider.getTaxNumAttach());
				oldTplRProvider.setWarehouseCount(tplRProvider.getWarehouseCount());
				oldTplRProvider.setAccountCount(tplRProvider.getAccountCount());
				messageDao.update(oldTplRProvider, TplRProvider.class);
			} else if (operationType.equals(OPERATION_TYPE_RED)) {
				// 删除服务商下所有仓库记录;
				String redProWarehouseSql = "delete tpl_r_provider_warehouse t where t.provider_id = '" + providerId + "'";
				messageDao.executeUpdateSql(redProWarehouseSql);
				// 删除服务商下所有银行帐号记录;
				String redProAccountSql = "delete tpl_r_provider_account t where t.provider_id = '" + providerId + "'";
				messageDao.executeUpdateSql(redProAccountSql);
				// 以providerId为关键字删除该服务商记录;
				String redProviderSql = "delete tpl_r_provider t where t.provider_id = '" + providerId + "'";
				messageDao.executeUpdateSql(redProviderSql);
			}
		} catch (Exception e) {
			String errTitle = "接收服务商开户基本信息错误: " + tplRProvider.getProviderId() + ",操作:" + tplRProvider.getOperationType();
			setLogMessage(e, errTitle);
		}
	}

	// 接收服务商开户仓库信息(暂时不用)
	public void receiveProviderWarehouse(TplRProviderWarehouse tplRProviderWarehouse) throws Exception {
		// if
		// (OPERATION_TYPE_NEW.endsWith(tplRProviderWarehouse.getOperationType()))
		// {
		// messageDao.insert(tplRProviderWarehouse, TplRProvider.class);
		// } else {
		// Long pk = messageDao.queryIdBySql("select id from
		// TPL_R_PROVIDER_WAREHOUSE where PROVIDER_ID = '"
		// + tplRProviderWarehouse.getProviderId() + "' and WAREHOUSE_ID = '" +
		// tplRProviderWarehouse.getWarehouseId() + "'");
		// tplRProviderWarehouse.setId(pk);
		// messageDao.update(tplRProviderWarehouse, TplRProvider.class);
		// }
		try {
			// 电文操作类型(10:新增;20:更新;00:红冲)
			String operationType = tplRProviderWarehouse.getOperationType();
			String providerId = tplRProviderWarehouse.getProviderId();
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				// 以providerId为关键字查询服务商基本信息表，取出ID放入服务商仓库信息中(provId),建立仓库和服务商信息的关联,
				// 把仓库信息保存到服务商仓库表中
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				tplRProviderWarehouse.setProvId(pk);
				tplRProviderWarehouse.setCreateDate(new Date());
				tplRProviderWarehouse.setModifyDate(new Date());
				// 查询该服务商仓库是否已存在
				String queryWarehouseIdSql = "select t.id from tpl_r_provider_warehouse t where t.provider_id='" + providerId
						+ "' and t.warehouse_id='" + tplRProviderWarehouse.getWarehouseId() + "'";
				// 如果该服务商下仓库已存在就不再新增;
				if (messageDao.queryIdBySql(queryWarehouseIdSql) == null)
					messageDao.insert(tplRProviderWarehouse, TplRProviderWarehouse.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				// 以providerId(服务商代码)和warehouseId(仓库代码)为关键字,查询仓库信息,用新的仓库信息更新该仓库数据
				String queryIdSql = "select t.id from tpl_r_provider_warehouse t where t.provider_id='" + providerId
						+ "' and t.warehouse_id='" + tplRProviderWarehouse.getWarehouseId() + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if(pk==null){
					String message=tplRProviderWarehouse.getProviderId()+"服务商还没有"+tplRProviderWarehouse.getWarehouseId()+"仓库的开户信息，不能更新。";
					setLogMessage(message,queryIdSql);
					return;
				}
				TplRProviderWarehouse oldTplRProviderWarehouse = (TplRProviderWarehouse) messageDao.queryById(pk,
						TplRProviderWarehouse.class);
				oldTplRProviderWarehouse.setProviderName(tplRProviderWarehouse.getProviderName());
				oldTplRProviderWarehouse.setWarehouseName(tplRProviderWarehouse.getWarehouseName());
				oldTplRProviderWarehouse.setWarehouseAddress(tplRProviderWarehouse.getWarehouseAddress());
				oldTplRProviderWarehouse.setStorageAbility(tplRProviderWarehouse.getStorageAbility());
				oldTplRProviderWarehouse.setStorageAbilityIndoor(tplRProviderWarehouse.getStorageAbilityIndoor());
				oldTplRProviderWarehouse.setStorageAbilityOutdoor(tplRProviderWarehouse.getStorageAbilityOutdoor());
				oldTplRProviderWarehouse.setFiveTen(tplRProviderWarehouse.getFiveTen());
				oldTplRProviderWarehouse.setElevenFifteen(tplRProviderWarehouse.getElevenFifteen());
				oldTplRProviderWarehouse.setTwentyUp(tplRProviderWarehouse.getTwentyUp());
				oldTplRProviderWarehouse.setPrivateLine(tplRProviderWarehouse.getPrivateLine());
				oldTplRProviderWarehouse.setDispatchAbility(tplRProviderWarehouse.getDispatchAbility());
				oldTplRProviderWarehouse.setBerthLength(tplRProviderWarehouse.getBerthLength());
				oldTplRProviderWarehouse.setHandlingAbility(tplRProviderWarehouse.getHandlingAbility());
				oldTplRProviderWarehouse.setStorageAbilityRule(tplRProviderWarehouse.getStorageAbilityRule());
				oldTplRProviderWarehouse.setStorageAlarmRatio(tplRProviderWarehouse.getStorageAlarmRatio());
				// oldTplRProviderWarehouse.setRemark(tplRProviderWarehouse.getRemark());
				// oldTplRProviderWarehouse.setCreateId(tplRProviderWarehouse.getCreateId());
				// oldTplRProviderWarehouse.setCreateDate(tplRProviderWarehouse.getCreateDate());
				// oldTplRProviderWarehouse.setModifyId(tplRProviderWarehouse.getModifyId());
				oldTplRProviderWarehouse.setModifyDate(new Date());
				// oldTplRProviderWarehouse.setOnlyBaosteel(tplRProviderWarehouse.getOnlyBaosteel());
				oldTplRProviderWarehouse.setWarehouseType(tplRProviderWarehouse.getWarehouseType());
//				by panyouyong 20120523 for message
				oldTplRProviderWarehouse.setBgsaCode(tplRProviderWarehouse.getBgsaCode());
				oldTplRProviderWarehouse.setExtendWarehouseFlag(tplRProviderWarehouse.getExtendWarehouseFlag());
//				by panyouyong 20120523 for message
//				by panyouyong 20141121 for message
				oldTplRProviderWarehouse.setBgsqCode(tplRProviderWarehouse.getBgsqCode());
//				by panyouyong 20141121 for message
//				by panyouyong 20150507 for message
				oldTplRProviderWarehouse.setBszgCode(tplRProviderWarehouse.getBszgCode());
//				by panyouyong 20150507 for message
//				by panyouyong 20160216 for message
				oldTplRProviderWarehouse.setBgtmCode(tplRProviderWarehouse.getBgtmCode());
//				by panyouyong 20160216 for message
				oldTplRProviderWarehouse.setBgbwCode(tplRProviderWarehouse.getBgbwCode());
				oldTplRProviderWarehouse.setBskvCode(tplRProviderWarehouse.getBskvCode());
				messageDao.update(oldTplRProviderWarehouse, TplRProviderWarehouse.class);
			} else if (operationType.equals(OPERATION_TYPE_RED)) {
				// 以provider_id(服务商代码)和warehouseId(仓库代码)为关键字删除该仓库记录;
				String redSql = "delete tpl_r_provider_warehouse t where t.provider_id = '" + providerId + "' and t.warehouse_id='"
						+ tplRProviderWarehouse.getWarehouseId() + "'";
				messageDao.executeUpdateSql(redSql);
			}
		} catch (Exception e) {
			String errTitle = "接收服务商开户仓库信息错误: " + tplRProviderWarehouse.getProviderId() + ",操作:"
					+ tplRProviderWarehouse.getOperationType() + ",仓库代码:" + tplRProviderWarehouse.getWarehouseId();
			setLogMessage(e, errTitle);
		}
	}

	// 接收服务商开户帐号信息(暂时不用)
	public void receiveProviderAccount(TplRProviderAccount tplRProviderAccount) throws Exception {
		// if
		// (OPERATION_TYPE_NEW.endsWith(tplRProviderAccount.getOperationType()))
		// {
		// messageDao.insert(tplRProviderAccount, TplRProvider.class);
		// } else {
		// Long pk = messageDao.queryIdBySql("select id from
		// TPL_R_PROVIDER_ACCOUNT where PROVIDER_ID = '"
		// + tplRProviderAccount.getProviderId() + "' and ACCOUNT = '" +
		// tplRProviderAccount.getAccount() + "'");
		// tplRProviderAccount.setId(pk);
		// messageDao.update(tplRProviderAccount, TplRProvider.class);
		// }
		try {
			// 电文操作类型(10:新增;20:更新;00:红冲)
			String operationType = tplRProviderAccount.getOperationType();
			String providerId = tplRProviderAccount.getProviderId();
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				// 以providerId为关键字查询服务商基本信息表，取出ID放入服务商帐号信息中(provId),建立银行帐号和服务商信息的关联,
				// 把银行帐号信息保存到服务商帐号表中
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				tplRProviderAccount.setProvId(pk);
				 tplRProviderAccount.setCreateDate(new Date());
				 tplRProviderAccount.setModifyDate(new Date());
				// 查询该服务商的银行帐号是否已存在;
				String queryAccountIdSql = "select t.id from tpl_r_provider_account t where t.provider_id='" + providerId
						+ "' and t.bank_seq='" + tplRProviderAccount.getBankSeq() + "'";
				// 如果该服务商的银行帐号已存在,就不在新增
				if (messageDao.queryIdBySql(queryAccountIdSql) == null)
					messageDao.insert(tplRProviderAccount, TplRProviderAccount.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				// 以providerId(服务商代码)和account(帐号代码)为关键字,查询银行帐号信息,用新的帐号信息更新该银行帐号数据
				String queryIdSql = "select t.id from tpl_r_provider_account t where t.provider_id='" + providerId
						+ "' and t.bank_seq='" + tplRProviderAccount.getBankSeq() + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if(pk==null){
					String message=providerId+"服务商还没有在"+tplRProviderAccount.getBankSeq()+"(供应商银行序号)开户的信息，不能更新。";
					setLogMessage(message,queryIdSql);
					return;
				}
				TplRProviderAccount oldTplRProviderAccount = (TplRProviderAccount) messageDao.queryById(pk, TplRProviderAccount.class);
				oldTplRProviderAccount.setProviderName(tplRProviderAccount.getProviderName());
				oldTplRProviderAccount.setAccount(tplRProviderAccount.getAccount());
				oldTplRProviderAccount.setBankId(tplRProviderAccount.getBankId());
				oldTplRProviderAccount.setBankName(tplRProviderAccount.getBankName());
				oldTplRProviderAccount.setBankAddress(tplRProviderAccount.getBankAddress());
				oldTplRProviderAccount.setBankAreaCode(tplRProviderAccount.getBankAreaCode());
				oldTplRProviderAccount.setDefaultFlag(tplRProviderAccount.getDefaultFlag());
				oldTplRProviderAccount.setValidFlag(tplRProviderAccount.getValidFlag());
				oldTplRProviderAccount.setRemark("修改");
				oldTplRProviderAccount.setModifyDate(new Date());
				messageDao.update(oldTplRProviderAccount, TplRProviderAccount.class);
			} else if (operationType.equals(OPERATION_TYPE_RED)) {
				// 以provider_id(服务商代码)和account(帐号代码)为关键字删除该帐号记录;
				String redSql = "delete tpl_r_provider_account t where t.provider_id = '" + providerId + "' and t.bank_seq='"
						+ tplRProviderAccount.getBankSeq() + "'";
				messageDao.executeUpdateSql(redSql);
			}
		} catch (Exception e) {
			String errTitle = "接收服务商开户帐号信息错误: " + tplRProviderAccount.getProviderId() + ",操作:"
					+ tplRProviderAccount.getOperationType() + ",帐号:" + tplRProviderAccount.getAccount();
			setLogMessage(e, errTitle);
		}
	}

	// 接收船型资料电文
	public void receiveShipManage(TplMessageShip tplMessageShip) throws Exception {
		tplMessageShip.setMessageNo("XL5207");
		tplMessageShip.setCreateDate(new Date());
		messageDao.insert(tplMessageShip, TplMessageShip.class);
		// 0内销,1外销;
		String inOutFlag = tplMessageShip.getInOutFlag();
		// 00撤消,10新增,20修改;
		String operationType = tplMessageShip.getOperationType();
		if ("0".equals(inOutFlag)) {
			// 内销;
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				TplRShipManage tplRShipManage = this.setShipManageValue(tplMessageShip, operationType);
				if (tplRShipManage != null)
					messageDao.insert(tplRShipManage, TplRShipManage.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				TplRShipManage tplRShipManage = this.setShipManageValue(tplMessageShip, operationType);
				messageDao.update(tplRShipManage, TplRShipManage.class);
			} else if (operationType.equals(OPERATION_TYPE_RED)) {
				String queryIdSql = "select t.ID from TPL_R_SHIP_MANAGE t " + "where t.SHIP_ID='" + tplMessageShip.getShipId() + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				messageDao.delete(new Long[] { pk }, TplRShipManage.class);

			}
		} else if ("1".equals(inOutFlag)) {
			// 外销;
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				LgsShipParticular lgsShipParticular = this.setShipParticularValue(tplMessageShip, operationType);
				if (lgsShipParticular != null)
					messageDao.insert(lgsShipParticular, LgsShipParticular.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				LgsShipParticular lgsShipParticular = this.setShipParticularValue(tplMessageShip, operationType);
				if (lgsShipParticular != null)
					messageDao.update(lgsShipParticular, LgsShipParticular.class);
			} else if (operationType.equals(OPERATION_TYPE_RED)) {
				String queryIdSql = "select t.PARTICULAR_ID from LGS_SHIP_PARTICULAR t " + "where t.PARTICULAR_NUM='"
						+ tplMessageShip.getParticularNum() + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if (pk != null)
					messageDao.delete(new Long[] { pk }, LgsShipParticular.class);
			}
		}
	}

	// 内销船型资料
	private TplRShipManage setShipManageValue(TplMessageShip tplMessageShip, String operationType) throws Exception {
		TplRShipManage tplRShipManage = null;
		if (operationType.equals(OPERATION_TYPE_NEW)) {
			// 判断是否有重复记录
			String queryIdSql = "select t.ID from TPL_R_SHIP_MANAGE t " + "where t.SHIP_ID='" + tplMessageShip.getShipId() + "'";
			Long pk = messageDao.queryIdBySql(queryIdSql);
			if (pk != null)
				return null;

			tplRShipManage = new TplRShipManage();
		} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
			String queryIdSql = "select t.ID from TPL_R_SHIP_MANAGE t " + "where t.SHIP_ID='" + tplMessageShip.getShipId() + "'";
			Long pk = messageDao.queryIdBySql(queryIdSql);
			tplRShipManage = (TplRShipManage) messageDao.queryById(pk, TplRShipManage.class);
		}

		// tplRShipManage.setOperationType("10"); //新增;
		tplRShipManage.setShipId(tplMessageShip.getShipId());
		// 0:内销;1:外销
		// tplRShipManage.setInOutFlag("0"); //内销
		tplRShipManage.setShipName(tplMessageShip.getShipName());
		tplRShipManage.setShipEngName(tplMessageShip.getShipEngName());
		tplRShipManage.setOwnerName(tplMessageShip.getOwnerName());
		tplRShipManage.setTproviderId(tplMessageShip.getTproviderId());
		tplRShipManage.setTproviderName(tplMessageShip.getTproviderName());
		tplRShipManage.setApplyDate(tplMessageShip.getApplyDate());
		tplRShipManage.setShipType(tplMessageShip.getShipType());
		// tplRShipManage.setDockFlag("1"); //0:厂内;1:外码头;
		// tplRShipManage.setParticularNum();
		tplRShipManage.setCarryWeight(bigdecimal2double(tplMessageShip.getCarryWeight()));
		tplRShipManage.setSelfWeight(bigdecimal2double(tplMessageShip.getSelfWeight()));
		tplRShipManage.setShipSize(tplMessageShip.getShipSize());
		tplRShipManage.setDipDepth(bigdecimal2double(tplMessageShip.getDipDepth()));
		tplRShipManage.setLeaveFactoryDate(tplMessageShip.getLeaveFactoryDate());
		tplRShipManage.setShipAge(tplMessageShip.getShipAge());
		tplRShipManage.setRepairStatus(tplMessageShip.getRepairStatus());
		tplRShipManage.setSuitProductType(tplMessageShip.getSuitProductType());
		tplRShipManage.setHarborRequire(tplMessageShip.getHarborRequire());
		tplRShipManage.setShipLength(bigdecimal2double(tplMessageShip.getShipLength()));
		tplRShipManage.setShipWidth(bigdecimal2double(tplMessageShip.getShipWidth()));
		tplRShipManage.setShipHigh(bigdecimal2double(tplMessageShip.getShipHigh()));
		tplRShipManage.setShipDepth(bigdecimal2double(tplMessageShip.getShipDepth()));
		tplRShipManage.setCabinNum(tplMessageShip.getCabinNum());
		tplRShipManage.setSubCabinNum(tplMessageShip.getSubCabinNum());
		tplRShipManage.setCabinClassTwo(tplMessageShip.getCabinClassTwo());

		tplRShipManage.setHatchLengthNum1(bigdecimal2double(tplMessageShip.getHatchLengthNum1()));
		tplRShipManage.setHatchWidthNum1(bigdecimal2double(tplMessageShip.getHatchWidthNum1()));
		tplRShipManage.setCabinLengthNum1(bigdecimal2double(tplMessageShip.getCabinLengthNum1()));
		tplRShipManage.setCabinWidthNum1(bigdecimal2double(tplMessageShip.getCabinWidthNum1()));
		tplRShipManage.setCabinHighNum1(bigdecimal2double(tplMessageShip.getCabinHighNum1()));

		tplRShipManage.setHatchLengthNum2(bigdecimal2double(tplMessageShip.getHatchLengthNum2()));
		tplRShipManage.setHatchWidthNum2(bigdecimal2double(tplMessageShip.getHatchWidthNum2()));
		tplRShipManage.setCabinLengthNum2(bigdecimal2double(tplMessageShip.getCabinLengthNum2()));
		tplRShipManage.setCabinWidthNum2(bigdecimal2double(tplMessageShip.getCabinWidthNum2()));
		tplRShipManage.setCabinHighNum2(bigdecimal2double(tplMessageShip.getCabinHighNum2()));

		tplRShipManage.setHatchLengthNum3(bigdecimal2double(tplMessageShip.getHatchLengthNum3()));
		tplRShipManage.setHatchWidthNum3(bigdecimal2double(tplMessageShip.getHatchWidthNum3()));
		tplRShipManage.setCabinLengthNum3(bigdecimal2double(tplMessageShip.getCabinLengthNum3()));
		tplRShipManage.setCabinWidthNum3(bigdecimal2double(tplMessageShip.getCabinWidthNum3()));
		tplRShipManage.setCabinHighNum3(bigdecimal2double(tplMessageShip.getCabinHighNum3()));

		// tplRShipManage.setShipBottomWeight(shipBottomWeight);
		// tplRShipManage.setShipHoldHeight(shipHoldHeight);
		// tplRShipManage.setShipDerrick(shipDerrick);
		// tplRShipManage.setShipMainEngine(shipMainEngine);
		// tplRShipManage.setShipGeneratorEngine(shipGeneratorEngine);
		// tplRShipManage.setShipSpeed(shipSpeed);
		// tplRShipManage.setShipShanghaiDate(shipShanghaiDate);
		// tplRShipManage.setPortName(portName);
		// tplRShipManage.setBaosteelCabin(baosteelCabin);
		//		
		// tplRShipManage.setAgentName(agentName);
		// tplRShipManage.setAgentTele(agentTele);
		// tplRShipManage.setAgentFax(agentFax);
		// tplRShipManage.setAgentContact();
		// tplRShipManage.setAgentMemberId(agentMemberId);
		// tplRShipManage.setAgentMemberName(agentMemberName);
		//		
		// tplRShipManage.setCharterMemberId(charterMemberId);
		// tplRShipManage.setCharterMemberName(charterMemberName);

		tplRShipManage.setApplyPerson(tplMessageShip.getApplyPerson());
		tplRShipManage.setAuditor(tplMessageShip.getAuditor());
		tplRShipManage.setAuditDate(tplMessageShip.getAuditDate());
		// tplRShipManage.setMemo(memo);
		// tplRShipManage.setMessageNo(null);
		return tplRShipManage;
	}

	// 外销船型资料
	private LgsShipParticular setShipParticularValue(TplMessageShip tplMessageShip, String operationType) throws Exception {
		LgsShipParticular lgsShipParticular = null;
		if (operationType.equals(OPERATION_TYPE_NEW)) {

			// 判断是否有重复记录
			String shipId = tplMessageShip.getShipId(); // 船型代码
			String queryIdSql = "select t.PARTICULAR_ID from LGS_SHIP_PARTICULAR t  where 1=1 ";
			if (shipId != null && !"".equals(shipId))
				queryIdSql += " and t.SHIP_ID = '" + tplMessageShip.getShipId() + "'";
			Long pk = messageDao.queryIdBySql(queryIdSql);
			if (pk != null)
				return null;

			lgsShipParticular = new LgsShipParticular();
			lgsShipParticular.setCreateDate(new Date());
		} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
			String particularNum = tplMessageShip.getParticularNum();// 船型资料号
			String shipId = tplMessageShip.getShipId(); // 船型代码

			String queryIdSql = "select t.PARTICULAR_ID from LGS_SHIP_PARTICULAR t  where 1=1 ";
			if (particularNum != null && !"".equals(particularNum))// 3pl新增，管控更新，关键字船型资料号
				queryIdSql += " and t.PARTICULAR_NUM = '" + tplMessageShip.getParticularNum() + "'";
			else if (shipId != null && !"".equals(shipId))// 管控新增后，管控更新,关键字船代码
				queryIdSql += " and t.SHIP_ID = '" + tplMessageShip.getShipId() + "'";
			else
				throw new Exception("船型资料号和船型代码都为空!");

			Long pk = messageDao.queryIdBySql(queryIdSql);
			lgsShipParticular = (LgsShipParticular) messageDao.queryById(pk, LgsShipParticular.class);
			lgsShipParticular.setModifyDate(new Date());
		}
		// tplMessageShip.setOperationType("10");
		lgsShipParticular.setShipId(tplMessageShip.getShipId());
		lgsShipParticular.setShipName(tplMessageShip.getShipName());
		lgsShipParticular.setShipOwnerName(tplMessageShip.getOwnerName());
		// lgsShipParticular.setTproviderId(shipParticularModel);
		// lgsShipParticular.setTproviderName(String tproviderName);
		lgsShipParticular.setSendDate(tplMessageShip.getApplyDate());
		lgsShipParticular.setShipType(tplMessageShip.getShipType());
		lgsShipParticular.setDockFlag(tplMessageShip.getDockFlag());
		lgsShipParticular.setParticularNum(tplMessageShip.getParticularNum());
		lgsShipParticular.setShipWeight(bigdecimal2double(tplMessageShip.getCarryWeight()));
		lgsShipParticular.setShipNetWeight(bigdecimal2double(tplMessageShip.getSelfWeight()));
		lgsShipParticular.setShipFreshDraft(bigdecimal2double(tplMessageShip.getDipDepth()));
		lgsShipParticular.setShipAge(string2double(tplMessageShip.getShipAge()));
		lgsShipParticular.setShipLength(bigdecimal2double(tplMessageShip.getShipLength()));
		lgsShipParticular.setShipWidth(bigdecimal2double(tplMessageShip.getShipWidth()));
		lgsShipParticular.setShipDepth(bigdecimal2double(tplMessageShip.getShipDepth()));
		lgsShipParticular.setShipCabinNum(string2double(tplMessageShip.getCabinNum()));
		lgsShipParticular.setShipNum(string2double(tplMessageShip.getSubCabinNum()));
		lgsShipParticular.setShipSecond(tplMessageShip.getCabinClassTwo());
		lgsShipParticular.setShipHoldHeight(bigdecimal2double(tplMessageShip.getShipHoldHeight()));
		lgsShipParticular.setShipDerrick(tplMessageShip.getShipDerrick());
		lgsShipParticular.setShipMainEngine(tplMessageShip.getShipMainEngine());
		lgsShipParticular.setShipGeneratorEngine(tplMessageShip.getShipGeneratorEngine());
		lgsShipParticular.setShipSpeed(bigdecimal2double(tplMessageShip.getShipSpeed()));
		lgsShipParticular.setShipShanghaiDate(tplMessageShip.getShipShanghaiDate());
		lgsShipParticular.setPortName(tplMessageShip.getPortName());
		lgsShipParticular.setBaosteelCabin(tplMessageShip.getBaosteelCabin());
		lgsShipParticular.setAgentName(tplMessageShip.getAgentName());
		lgsShipParticular.setAgentTele(tplMessageShip.getAgentTele());
		lgsShipParticular.setAgentFax(tplMessageShip.getAgentFax());
		lgsShipParticular.setAgentContact(tplMessageShip.getAgentContact());
		lgsShipParticular.setAgentMemberId(tplMessageShip.getAgentMemberId());
		lgsShipParticular.setAgentMemberName(tplMessageShip.getAgentMemberName());
		lgsShipParticular.setCharterMemberId(tplMessageShip.getCharterMemberId());
		lgsShipParticular.setCharterMemberName(tplMessageShip.getCharterMemberName());
		lgsShipParticular.setCreateUserId(tplMessageShip.getApplyPerson());
//		lgsShipParticular.setCreateMemberId(tplMessageShip.getTproviderId());
//		lgsShipParticular.setCreateMemberName(tplMessageShip.getTproviderName());
		// if(ShipKeys.TYPE_SALE.equals(type)){ //外销
		// tplMessageShip.setTproviderId("999999");
		// tplMessageShip.setAuditor(shipParticularModel.getReplyUserName());
		// tplMessageShip.setAuditDate(shipParticularModel.getReplyDate()) ;
		// tplMessageShip.setMemo("外销");
		// }else if(ShipKeys.TYPE_BUY.equals(type)){//外购
		// tplMessageShip.setAuditor(shipParticularModel.getSaleReplyUserName());
		// tplMessageShip.setAuditDate(shipParticularModel.getSaleReplyDate()) ;
		// tplMessageShip.setMemo("外购");
		// }
		//		

		return lgsShipParticular;
	}

	// 发送日常公文反馈
	public void sendDocumentReturn(TplRDocument tplRDocument) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_DOCUMENT_RETURN, null, tplRDocument);
	}

	// 发送通用信息修改申请
	public void sendModifyApply(TplRModifyApplyMain tplRModifyApplyMain) throws Exception {

		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.add(tplRModifyApplyMain.getTplRModifyApplyItems());
		int leftCircularTimes = 0;
		if (tplRModifyApplyMain.getTplRModifyApplyItems().size() % CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES != 0) {
			leftCircularTimes = CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES - tplRModifyApplyMain.getTplRModifyApplyItems().size()
					% CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplRModifyApplyItem());
		}

		List detailList = getMsgDetail(list, CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES, CLASS_RESOURCE_MODIFY_APPLY);
		for (int j = 0; j < detailList.size(); j++) {
			tplRModifyApplyMain.setDetail((String) detailList.get(j));
			sendMsgService.sendMsg(MESSAGE_SEND_N0_MODIFY_APPLY, null, tplRModifyApplyMain);
		}
	}

	
public void sendTransTruckLoadingList(TplMessageTransLoadingMain transLoading)
	throws Exception {
logger.info("--发送装车清单：" + new Date());

List list = new ArrayList();
list.addAll(transLoading.getTransLoadingItem());
int leftCircularTimes = 0;
if (transLoading.getTransLoadingItem().size() % CIRCULAR_TRUCK_LOADING_TIMES != 0) {
	leftCircularTimes = CIRCULAR_TRUCK_LOADING_TIMES - transLoading.getTransLoadingItem().size()
			% CIRCULAR_TRUCK_LOADING_TIMES;
}

for (int i = 0; i < leftCircularTimes; i++) {
	list.add(new TplMessageTransLoadingItem());
}

List detailList = getMsgDetail(list, CIRCULAR_TRUCK_LOADING_TIMES, CLASS_MESSAGE_TRANS_LOADING_ITEM);
if(detailList!=null&&detailList.size()>0){
for (int j = 0; j < detailList.size(); j++) {
	transLoading.setDetail((String) detailList.get(j));
	sendMsgService.sendMsg(MESSAGE_TRANS_LOADING_MAIN, null, transLoading);
}
}else{
	sendMsgService.sendMsg(MESSAGE_TRANS_LOADING_MAIN, null, transLoading);
}
messageDao.saveTransLoadingResult(transLoading);
}

public void sendTransTruckArrivalList(TplMessageTransArrivalMain transArrival)
throws Exception {
logger.info("--发送到货清单：" + new Date());
List list = new ArrayList();
list.addAll(transArrival.getTransArrivalItem());
int leftCircularTimes = 0;
if (transArrival.getTransArrivalItem().size() % CIRCULAR_TRUCK_ARRIVAL_TIMES != 0) {
leftCircularTimes = CIRCULAR_TRUCK_ARRIVAL_TIMES - transArrival.getTransArrivalItem().size()
		% CIRCULAR_TRUCK_ARRIVAL_TIMES;
}
for (int i = 0; i < leftCircularTimes; i++) {
list.add(new TplMessageTransArrivalItem());
}

List detailList = getMsgDetail(list, CIRCULAR_TRUCK_ARRIVAL_TIMES, CLASS_MESSAGE_TRANS_ARRIVAL_ITEM);
if(detailList!=null&&detailList.size()>0){
for (int j = 0; j < detailList.size(); j++) {
	transArrival.setDetail((String) detailList.get(j));
sendMsgService.sendMsg(MESSAGE_TRANS_ARRIVAL_MAIN, null, transArrival);
}
}else{
	sendMsgService.sendMsg(MESSAGE_TRANS_ARRIVAL_MAIN, null, transArrival);
}
messageDao.insert(transArrival, TplMessageTransArrivalMain.class);
}
	
	// 发送服务商开户帐号信息
	public void sendProviderAccount(TplRProviderAccount tplRProviderAccount) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_PROVIDER_ACCOUNT, null, tplRProviderAccount);
	}

	// 发送服务商开户基本信息
	public void sendProviderBase(TplRProvider tplRProvider) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_PROVIDER, null, tplRProvider);
	}

	// 发送服务商开户仓库信息
	public void sendProviderWarehouse(TplRProviderWarehouse tplRProviderWarehouse) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_PROVIDER_WAREHOUSE, null, tplRProviderWarehouse);
	}

	// 发送车船运力预报
	public void sendShipAbility(TplShipAbility tplShipAbility) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_SHIP_ABILITY, null, tplShipAbility);
	}

	// 发送仓储能力预报
	public void sendStockAbility(TplWproviderAbility tplWproviderAbility) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_WPROVIDER_ABILITY, null, tplWproviderAbility);
	}

	// 上报船舶资料
	public void sendShipManage(TplMessageShip tplMessageShip) throws Exception {
		tplMessageShip.setMessageNo(MESSAGE_SEND_N0_SHIP_MANAGE);
		tplMessageShip.setCreateDate(new Date());
		messageDao.insert(tplMessageShip, TplMessageShip.class);
		sendMsgService.sendMsg(MESSAGE_SEND_N0_SHIP_MANAGE, null, tplMessageShip);
	}

	// 上报车辆资料
	public void sendTruckManage(TplRTruckManage tplRTruckManage) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_TRUCK_MANAGE, null, tplRTruckManage);
	}

	private double round(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public boolean judgeSendMsg(String unitId) throws Exception {
		return sendMsgService.judgeSendMsg(unitId);
	}

	public SendMsgService getSendMsgService() {
		return sendMsgService;
	}

	public void setSendMsgService(SendMsgService sendMsgService) {
		this.sendMsgService = sendMsgService;
	}

	private BigDecimal double2bigdecimal(Double double_var) {
		if (double_var == null)
			return new BigDecimal("0");
		return new BigDecimal(double_var.toString());
	}

	private Double bigdecimal2double(BigDecimal bigdecimal_var) {
		if (bigdecimal_var == null)
			return new Double("0");
		return new Double(bigdecimal_var.toString());
	}

	private Double string2double(String str) {
		if (str == null)
			return new Double("0");
		try {
			return Double.valueOf(str);
		} catch (Exception e) {
			return new Double("0");
		}
	}
	
	/********************************************************************************************
	 * 1. 发送电子提单承诺函电文  2. 发送电子提单仓储协议电文   3.接受管控验单通过信息   
	 * 4. 发送电子提单打印             5. 接收提单计划
	 *******************************************************************************************/

	// 1. 发送电子提单承诺函电文
	public void sendEpickPromisee(
			TplMessageEpickPromisee tplMessageEpickPromisee) throws Exception {
		// 储存备案电文信息
		logger.info("3PL_MESSAGE_sendEpickPromisee: EPICK PROMISEE SENT" + tplMessageEpickPromisee.getOperationType());
		logger.info("++++++ tplMessageEpickPromisee.getClass().getName()="+tplMessageEpickPromisee.getClass().getName());
		logger.info("++++++ TplMessageEpickPromisee.class.getName()="+TplMessageEpickPromisee.class.getName());
		messageDao.insert(tplMessageEpickPromisee,
				TplMessageEpickPromisee.class);
		
		logger.info("------ 3PL_MESSAGE_sendEpickPromisee: EPICK PROMISEE SENT" + tplMessageEpickPromisee.getOperationType());
		sendMsgService.sendMsg(MESSAGE_SEND_N0_EPICK_PROMISEE, null, tplMessageEpickPromisee);

	}


//5. 发送电子提单打印
	public void sendEpickPrint(TplMessageEpickSend tplMessageEpickSend) throws Exception{
		//备案发送信息--以下一行代码交移3PL引用处理
		//messageDao.insert(tplMessageEpickSend, TplMessageEpickSend.class);
		logger.info("sendEpickPrint Type:" + tplMessageEpickSend.getFeedbackType());
		//发送电文
		sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_OUT_EPICK, null, tplMessageEpickSend);
		
	}

	public void sendWGQtoGK(TplMessageWgqGkSend tplMessageWgqGkSend) throws Exception{

		logger.info("---发送外高桥船厂收货信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_NO_WAIGAOQIAO_RECEIVED, null, tplMessageWgqGkSend);
	}
	
	/*
	 *  接收提单计划(non-Javadoc)
	 * @see com.baosight.baosteel.bli.tpl.interfaces.service.MessageService#receiveBill(com.baosight.baosteel.bli.tpl.model.TplMessageBillMain)
	 */
	
	public void receiveBill(TplMessageBillMain bill) throws Exception {
//		 yesimin  20150508  湛江提单号截取
		if(bill.getPickNum()!=null&&(bill.getPickNum().endsWith("_A")||bill.getPickNum().endsWith("_B")))
		{
			String[] pickNum=bill.getPickNum().split("_");
			bill.setPickNum(pickNum[0]);
		}
		logger.info("receiveBill Type:" + bill.getOperationType());
		try {

			if ("000000".equals(bill.getTproviderId())
					|| "999999".equals(bill.getTproviderId())
					|| "BGSA".equals(bill.getTproviderId())
					//|| bill.getTproviderId() == null
					|| bill.getAuthorizedUnitId() == null) {
				
			}
			
			// 判断重复，如果是红冲，会有多次，不判定重复。
			if (SystemConstants.OPERATION_TYPE_INSERT.equals(bill
					.getOperationType())) {
				String checkRepeatSql = "select id from TPL_MESSAGE_BILL_MAIN where BILL_ID = '"
						+ bill.getBillId()// 根据提单号??
						+ "' and OPERATION_TYPE = '"
						+ bill.getOperationType()
						+ "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}

			/**
			 * 处理制造单元特殊红冲：如果件数是0，证明全部要红冲，不需要解析明细。 modified by Forest 20080514
			 */
			if (bill.getPlanCount().intValue() > 0) {
				bill.setBillItemSet(BeanConvertUtils.strToBean(
						bill.getDetail(), CLASS_BILL_ITEM));
			} else {
				bill.setBillItemSet(new HashSet());
			}

			// 处理空model

			Iterator itBill = bill.getBillItemSet().iterator();
			while (itBill.hasNext()) {
				TplMessageBillItem billitem = (TplMessageBillItem) itBill
						.next();
				if ("".equals(billitem.getOrderNum())) {
					itBill.remove();
				}
				if("NULL".equals(billitem.getProductType())){
					billitem.setProductType("");
				}
			}

			String queryBillIdBySql = "select id from TPL_MESSAGE_BILL_MAIN where BILL_ID = '"
					+ bill.getBillId()
					+ "' and OPERATION_TYPE = '"
					+ bill.getOperationType()
					+ "'  and  STATUS is null  order by ID DESC";
			Long id = messageDao.queryIdBySql(queryBillIdBySql);
			bill.setId(id);
			bill.setUnitId(bill.getAuthorizedUnitId());
			bill.setUnitName(AcquireAgencyName.getAgencyName(bill.getAuthorizedUnitId()));// 获取委托机构名称

			// 转换地区公司别
			bill.setOrderUserId(this.getAreaCompanyCode(bill.getOrderUserId()));
		
			// 查询该计划已经新增的捆包数，来判定本次接收是否结束
			Long itemCount = messageDao
					.queryIdBySql("select count(t.ID) from TPL_MESSAGE_BILL_ITEM t where t.BILL_NO = "
							+ bill.getId());

			// 判定重复，解决电文同步发送的问题，临时过渡方案 add by zhengfei 20080128
			if (bill.getBillItemSet() != null
					&& bill.getBillItemSet().size() > 0) {
				// 获取这批电文中任一捆包号
				Iterator itDouble = bill.getBillItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageBillItem billitem = (TplMessageBillItem) itDouble
							.next();
					someOnePack = billitem.getPackId();
					//需要获得产品大类
					if(billitem.getOrderNum()!= null && !billitem.getOrderNum().trim().equals("")){
					 productClass = billitem.getOrderNum().substring(0, 1);
					}
				}
				// 判断该计划下的捆包号是否已经入库
				Long doublePackCount = null;
				if (OPERATION_TYPE_APPLEND.equals(bill.getOperationType())) {
					doublePackCount = messageDao
							.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_BILL_ITEM t "
									+ " where t.BILL_ID = '"
									+ bill.getBillId()
									+ "'   and t.PACK_ID = '"
									+ someOnePack
									+ "' and t.PRODUCT_CLASS='" + productClass + "' ");
				} else {
					doublePackCount = messageDao
							.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_BILL_ITEM t "
									+ " where t.BILL_NO = "
									+ bill.getId()
									+ "   and t.PACK_ID = '"
									+ someOnePack
									+ "' and t.PRODUCT_CLASS='" + productClass + "' ");
				}

				// 如果该捆包已经入库，则不处理这批捆包
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			messageDao.insertTplBillMain(bill);

			if (id == null) {
				String querySql = "select id from TPL_MESSAGE_BILL_MAIN where BILL_ID = '"
						+ bill.getBillId()
						+ "' and OPERATION_TYPE = '"
						+ bill.getOperationType()
						+ "' and  STATUS is null  order by ID DESC";
				id = messageDao.queryIdBySql(querySql);
				bill.setId(id);
			}

			// 判断计划是否结束，如果结束插入业务表
			if (bill.getPlanCount().intValue() == bill.getBillItemSet().size()
					+ itemCount.intValue()) {
				messageDao.updatePlanStatus("update TPL_MESSAGE_BILL_MAIN set STATUS = '0' where BILL_ID = '"
								+ bill.getBillId()
								+ "'   and OPERATION_TYPE = '"
								+ bill.getOperationType() + "'");

				if (OPERATION_TYPE_NEW.equals(bill.getOperationType())) {
					/**
					 * 新增处理
					 * 属地系统(制造单元)红冲后的计划重新下发处理:如果是制造单元新增的计划，则如果本次新增的计划3PL已经存在
					 * ，则证明不在本次计划里边的捆包需要红冲的。
					 */
					String checkPlanExist = "select id from TPL_BILL where BILL_ID = '"
							+ bill.getBillId() + "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doBillRedOper(bill, "10");
					} else {
						messageDao.insertTplBillByBatch(bill);
					}
				} else if (OPERATION_TYPE_RED.equals(bill.getOperationType())) { 
					/**
					 * 属地系统(制造单元)的红冲：是计划全部撤销以后，再重新上来正确的计划，不支持单个材料撤销-----提单类型：6
					 * 开头的。 管控系统的红冲：支持单个材料的红冲-----提单类型：除6开头的提单类型以外的提单
					 * 
					 */
					if ("6".equals(bill.getPickType().substring(0, 1))||"7".equals(bill.getPickType().substring(0, 1))) {
						/**
						 * modified by Forest 20080410
						 * 制造单元的红冲按照统一模式：制造单元的红冲下发，如果计划是未执行状态则将计划删除
						 * ；如果不是未执行状态，则将计划先结案。在接收到新的计划后做后续处理。
						 * =====特殊处理：厂内直发的铁运特殊处理，计划不能删除，要先做结案=====
						 */
						if ("62".equals(bill.getPickType())
								&& SystemConstants.TRANS_TYPE_TRAIN.equals(bill
										.getTransType())) {
							messageDao.doBillRedOper(bill, "01");
						} else {
							messageDao.doBillRedOper(bill, "00");
						}

					} else {
						messageDao.doBillRedOper(bill, null);
					}
				} else if (OPERATION_FORCE_OVER.equals(bill.getOperationType())) {
					/**
					 * 强制结案 属地系统的强制结案：强制结案的材料是需要保留的，其余的全部删除，操作类型是90开头。
					 */
					//烟宝按量强制结案提单，业务表强制结案
					if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(bill.getUnitId())
							&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(bill.getPlanParticle())) {
						
					} else {
						messageDao.doBillRedOper(bill, "10");
					}
				} else if (OPERATION_TYPE_APPLEND.equals(bill
						.getOperationType())) {

					// 获取已入库计划id,若有则进行更新操作,否则进行插入操作
					String checkPlanExist = "select id from TPL_BILL where BILL_ID  = '"
							+ bill.getBillId() + "'";
					Long updateId = null;
					updateId = messageDao.queryIdBySql(checkPlanExist);
					// 追加操作:1.插入捆包数据;2.更新主表总量
					if (updateId != null) {
						messageDao.insertTplBillByApplend(bill, "20",
								updateId);// 插入捆包并更新
					//	messageDao.doBillFixRedOper(bill, "20");
					} else {
						messageDao.insertTplBillByApplend(bill, "10",
								updateId);// 插入主表子表
					}

				}
			}
			
		
		} catch (Exception e) {
			String errTitle = "提单计划：" + bill.getBillId();
			setLogMessage(e, errTitle);
			e.printStackTrace();
		}

	}
	
	/*
	 *  add by tangwei
	 *  接收暂缓验证信息(non-Javadoc)
	 * @see com.baosight.baosteel.bli.tpl.interfaces.service.MessageService#receiveBill(com.baosight.baosteel.bli.tpl.model.TplMessageBillMain)
	 */
	
	public void receiveAcceptInfo(TplMessageAcceptInfo messageAcceptInfo) throws Exception {
		logger.info("messageAcceptInfo start");
		try {
			messageDao.insertTplAcceptInfo(messageAcceptInfo);
		} catch (Exception e) {
			String errTitle = "计划号：" + messageAcceptInfo.getTransInstructionId() +"合同号：" +messageAcceptInfo.getOrderNo();
			setLogMessage(e, errTitle);
			e.printStackTrace();
		}
		logger.info("messageAcceptInfo end");

	}



	public void sendTransPlanResult(TplMessageTransPlanResult transPlanResult)
			throws Exception {
		logger.info("sendTransPlanResult Type:" );
		messageDao.insert(transPlanResult, TplMessageTransPlanResult.class);
		//发送电文
		sendMsgService.sendMsg(MESSAGE_TRANS_PLAN_RESULT, null, transPlanResult);
		
	}


	public void sendTransSignResult(TplMessageTransSignResult transSignResult)
			throws Exception {
		logger.info("sendEpickPrint Type:" );
		messageDao.insert(transSignResult, TplMessageTransSignResult.class);
		//发送电文
		sendMsgService.sendMsg(MESSAGE_TRANS_SIGN_RESULT, null, transSignResult);
	}


	public void sendTransTransStartResult(
			TplMessageTransStartResult transStartResult) throws Exception {
		logger.info("sendEpickPrint Type:" );
		System.out.println("发车反馈："+transStartResult.getTruckNo());
		messageDao.insert(transStartResult, TplMessageTransStartResult.class);
		//发送电文
		sendMsgService.sendMsg(MESSAGE_TRANS_START_RESULT, null, transStartResult);
		
	}
	
	//发送提货计划反馈电文
	public void sendTransTransResult( TplTransDeliveryPlanReturn tplTransDeliveryPlanReturn) throws Exception{
		logger.info("sendEpickPrint Type:" );
		messageDao.insert(tplTransDeliveryPlanReturn, TplTransDeliveryPlanReturn.class);
		//发送电文
		sendMsgService.sendMsg(MESSAGE_TRANS_RESULT, null, tplTransDeliveryPlanReturn);
		//发送汽运动态跟踪
		sendTransCarDynWebServicce.sendCarDynWebservice(tplTransDeliveryPlanReturn);
	}

	// 接收线材发货完成标志电文
	public void receiveMessagePickDataEnd(LgsMessagePickDataEnd pickDataEnd) throws Exception {
		try {
			//9672委托机构搞错了，并且少了制造单元，在元旦之后的9672第二次切换，需要9672恢复委托和制造单元，下面去掉
//			pickDataEnd.setUnitId("0001");
//			pickDataEnd.setManuId("BGSA");
			
			//判断重复
			String checkPickDataEndExist = "select id from LGS_MESSAGE_PICK_DATA_END t where 1=1 "
				    + " and t.ORDER_NUM='"  + pickDataEnd.getOrderNum() +  "'"
			        + " and t.READY_NUM = '" + pickDataEnd.getReadyNum() + "' "
					+ " and t.PICK_NUM = '" + pickDataEnd.getPickNum() + "' ";
			System.out.println("-------------------1----------------"+checkPickDataEndExist);
			if (messageDao.queryIdBySql(checkPickDataEndExist) != null){
				System.out.println("-------------------2----------------");
				return;
			}else{
				pickDataEnd.setCreateDate(new Date());
				pickDataEnd.setModifyDate(new Date());

				System.out.println("-------------------3----------------");
				messageDao.insert(pickDataEnd, LgsMessagePickDataEnd.class);

				System.out.println("-------------------4----------------");
				messageDao.doPickDataEndStockInOperation(pickDataEnd);

				System.out.println("-------------------5----------------");
				messageDao.doPickDataEndTransOperation(pickDataEnd);
			}
				
		} catch (Exception e) {
			String errTitle = "接收线材发货完成标志电文: 提单号" + pickDataEnd.getPickNum() + ",合同号:" + pickDataEnd.getOrderNum() + ",准发号:" + pickDataEnd.getReadyNum();
			setLogMessage(e, errTitle);
		}
	}

	public void sendTransShipTrends(TplMessageTransShipTrends transShipTrends)
			throws Exception {
		logger.info("sendEpickPrint Type: 船舶动态信息反馈" );
		if(transShipTrends!=null){
			transShipTrends.setCreateDate(new Date());
		messageDao.insert(transShipTrends, TplMessageTransShipTrends.class);
		logger.info("=====webservice===调用====getShipIdFromWebTransPlan===");
		String shipId = messageDao.getShipIdFromWebTransPlan(transShipTrends);
		logger.info("=====webservice=======shipId==="+shipId);
		logger.info("=====webservice=======sendTransShipDynImpl==="+sendTransShipDynImpl);
		if(null!= shipId && !"".equals(shipId)){
			sendTransShipDynImpl.sendTransShipDyn(transShipTrends);
		}
		//发送电文
		sendMsgService.sendMsg(MESSAGE_TRANS_SHIP_TRENDS, null, transShipTrends);
		}
	}

	// 接收线材发货完成标志电文
	public void receiveMessageReturnPack(LgsMessageReturnPack messageReturnPack) throws Exception {
		try {
			System.out.println("....................新增硅钢材料电文............开始");
			//硅钢返厂捆包处理
			//判断重复
			String checkPickDataEndExist = "select id from LGS_MESSAGE_RETURN_PACK t where 1=1 "
				    + " and t.ORDER_NUM='"  + messageReturnPack.getOrderNum() +  "'"
					+ " and t.PACK_ID = '" + messageReturnPack.getPackId() + "' "
					+ " and t.EXTEND_WAREHOUSE_CODE = '" + messageReturnPack.getExtendWarehouseCode() + "' ";
			if (messageDao.queryIdBySql(checkPickDataEndExist) != null){
				return;
			}else{
				messageReturnPack.setCreateDate(new Date());
				messageReturnPack.setModifyDate(new Date());
				messageReturnPack.setUnitId("0001");
				messageReturnPack.setProductClass((messageReturnPack.getOrderNum()).substring(0, 1));
				System.out.println("....................新增硅钢材料电文............");
				messageDao.insertLgsMessageReturnPack(messageReturnPack);
				//硅钢材料返厂
				this.doReturnPack(messageReturnPack);
				System.out.println("....................新增硅钢材料电文............结束");
			}
		} catch (Exception e) {
			String errTitle = "接收硅钢返厂电文: 合同号" + messageReturnPack.getOrderNum() + ",材料号:" + messageReturnPack.getPackId();
			setLogMessage(e, errTitle);
		}
	}
	
	// 接收武钢硅钢返厂电文
	public void receiveMessageReturnPackForBGBW(LgsMessageReturnPack messageReturnPack) throws Exception {
		try {
			System.out.println("....................新增硅钢材料电文............开始");
			//硅钢返厂捆包处理
			//判断重复
			String checkPickDataEndExist = "select id from LGS_MESSAGE_RETURN_PACK t where 1=1 "
				    + " and t.ORDER_NUM='"  + messageReturnPack.getOrderNum() +  "'"
					+ " and t.PACK_ID = '" + messageReturnPack.getPackId() + "' "
					+ " and t.EXTEND_WAREHOUSE_CODE = '" + messageReturnPack.getExtendWarehouseCode() + "' ";
			if (messageDao.queryIdBySql(checkPickDataEndExist) != null){
				return;
			}else{
				messageReturnPack.setCreateDate(new Date());
				messageReturnPack.setModifyDate(new Date());
				messageReturnPack.setUnitId("BGBW");
				messageReturnPack.setProductClass((messageReturnPack.getOrderNum()).substring(0, 1));
				System.out.println("....................新增硅钢材料电文............");
				messageDao.insertLgsMessageReturnPack(messageReturnPack);
				//硅钢材料返厂
				this.doReturnPack(messageReturnPack);
				System.out.println("....................新增硅钢材料电文............结束");
			}
		} catch (Exception e) {
			String errTitle = "接收硅钢返厂电文: 合同号" + messageReturnPack.getOrderNum() + ",材料号:" + messageReturnPack.getPackId();
			setLogMessage(e, errTitle);
		}
	}
	
	//9672 硅钢材料,厂外库材料返厂
	private void doReturnPack(LgsMessageReturnPack messageReturnPack)throws Exception{
		try {
			String queryIdSql = " select tsii.id from TPL_STOCK_IN_PLAN tsip,TPL_STOCK_IN_ITEM tsii,LGS_MESSAGE_RETURN_PACK lmrp " 
								+ " where tsip.pick_type = '61' "
								+ " and tsip.id = tsii.plan_id "
								+ " and tsip.unit_id = lmrp.unit_id "
								+ " and tsii.product_class = lmrp.product_class "
								+ " and tsii.order_num = lmrp.order_num "
								+ " and tsii.pack_id = lmrp.pack_id "
								+ " and lmrp.order_num='" + messageReturnPack.getOrderNum() + "'"
								+ " and lmrp.product_class='" + messageReturnPack.getProductClass() + "'"
								+ " and lmrp.pack_id='" + messageReturnPack.getPackId() + "'"
								+ "and tsii.putin_id is not null";
			System.out.println("查询语句..." + queryIdSql + "");
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id != null){
				//硅钢厂外库做入库操作
				String updateSql = "update TPL_STOCK set EXTEND_WAREHOUSE_FLAG = '10', EXTEND_WAREHOUSE_CODE = '" + messageReturnPack.getExtendWarehouseCode() + "', PRODUCT_PROPERTY = '10', STOCK_FLAG = '10', CONFIRM_STATUS = '10', MODIFY_DATE=SYSDATE where PACK_ID = '"
				+ messageReturnPack.getPackId() + "' and PRODUCT_CLASS = '"
				+ messageReturnPack.getOrderNum().substring(0, 1) + "' and UNIT_ID = '"
				+ messageReturnPack.getUnitId() + "' and EXTEND_WAREHOUSE_FLAG is null and PACK_STATUS = '00' and STOCK_FLAG = '20'";
				messageDao.updateStockStatus(updateSql);
				
				System.out.println("updateSql................更新语句..." + updateSql);
			}
		} catch (Exception e) {
			String errTitle = "接收硅钢返厂电文:" + messageReturnPack.getOrderNum();
			setLogMessage(e, errTitle);
		} 
	}
	
	//接收司机基础信息应答电文
	public void receiveTplRDriverReturn(TplRDriverReturn tplRDriverReturn) throws Exception{
		tplRDriverReturn.setCreateDate(new Date());
		messageDao.insert(tplRDriverReturn, tplRDriverReturn.getClass());
	}
	//接收车辆基础信息应答电文
	public void receiveTplRHeadstockReturn(TplRHeadstockReturn tplRHeadstockReturn) throws Exception{
		tplRHeadstockReturn.setCreateDate(new Date());
		messageDao.insert(tplRHeadstockReturn, tplRHeadstockReturn.getClass());
	}

	public void receiveLgsStackStorageYard(
			LgsStackStorageYard lgsStackStorageYard) throws Exception{
		try {
			logger.info("启动==========================================" );
			String str="select p.pack_num from LGS_STACK_STORAGE_YARD p " +
					"where p.pack_num= '"+lgsStackStorageYard.getPackNum()+"'";
			Long id   = messageDao.queryIdBySql(str);
			if(id!=null){
				return;
			}else{
				lgsStackStorageYard.setCreateDate(new Date());
				String actputinDate = lgsStackStorageYard.getActputinDate();
				String actputinDateFinal = dateToString(actputinDate);
				lgsStackStorageYard.setActputinDate(actputinDateFinal);
				messageDao.insertLgsStackStorageYard(lgsStackStorageYard);	
			}
		} catch (Exception e) {
			String errTitle = "接入库码单电文:" + lgsStackStorageYard.getPackNum();
			setLogMessage(e, errTitle);
		}
			
	}
	private String dateToString(String actputinDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		Date dateString = df.parse(actputinDate);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//时间格式
		String sysDatetime = fmt.format(dateString); 
		return sysDatetime;
	}

	public void sendFlyTransPlanResult(TplMessageFlySend flySend)
			throws Exception {
		try {
			sendMsgService.sendMsg(MESSAGE_SEND_N0_TRANS_FLY, null, flySend);
			messageDao.insert(flySend,flySend.getClass());
			
		} catch (Exception e) {
			String errTitle = "飞单转授权电文:" + flySend.getBillId();
			setLogMessage(e, errTitle);
		}
			
		
	}

	public void sendEntryPermit(TplREntryPermitZj tplREntryPermitZj, String no) throws Exception {
		System.out.println("发送进厂车辆");
		TplMessageEntryPermit tplMessageEntryPermit = new TplMessageEntryPermit();
		String billCode = null;
		billCode = "IXZ";
		Date t = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat ("yyMMdd");
		billCode = billCode+fmt.format(t)+no;
		
		tplMessageEntryPermit.setBillCode(billCode);
		tplMessageEntryPermit.setSysTarget("");
		tplMessageEntryPermit.setInoutType(tplREntryPermitZj.getInoutType());
		tplMessageEntryPermit.setPlateNum(tplREntryPermitZj.getPlateNum());
		tplMessageEntryPermit.setVehicleType(tplREntryPermitZj.getVehicleType());
		tplMessageEntryPermit.setStartDate(parseDateToString(tplREntryPermitZj.getStartDate()));
		tplMessageEntryPermit.setEndDate(parseDateToString(tplREntryPermitZj.getEndDate()));
		tplMessageEntryPermit.setIsWeigh(tplREntryPermitZj.getIsWeigh());
		tplMessageEntryPermit.setBcName(tplREntryPermitZj.getBcName());
		tplMessageEntryPermit.setBcCode(tplREntryPermitZj.getBcCode());
		tplMessageEntryPermit.setPplDept(tplREntryPermitZj.getPplDept());
		tplMessageEntryPermit.setPplBill(tplREntryPermitZj.getPplBill());
		tplMessageEntryPermit.setPplReason(tplREntryPermitZj.getPplReason());
		tplMessageEntryPermit.setPplNuim(tplREntryPermitZj.getPplNuim());
		tplMessageEntryPermit.setDoorName(tplREntryPermitZj.getDoorName());
		tplMessageEntryPermit.setMaterialNum(tplREntryPermitZj.getMaterialNum());
		tplMessageEntryPermit.setMcName(tplREntryPermitZj.getMcName());
		tplMessageEntryPermit.setMcCode(tplREntryPermitZj.getMcCode());
		tplMessageEntryPermit.setGysName(tplREntryPermitZj.getGysName());
		tplMessageEntryPermit.setGysCode(tplREntryPermitZj.getGysCode());
		tplMessageEntryPermit.setMatName(tplREntryPermitZj.getMatName());
		tplMessageEntryPermit.setMatCode(tplREntryPermitZj.getMatCode());
		tplMessageEntryPermit.setMatNorms(tplREntryPermitZj.getMatNorms());
		tplMessageEntryPermit.setMatNum(tplREntryPermitZj.getMatNum());
		tplMessageEntryPermit.setMatWeight(tplREntryPermitZj.getMatWeight());
		tplMessageEntryPermit.setMatUnit(tplREntryPermitZj.getMatUnit());
		

		logger.info("---发送进厂证信息：" + new Date());
		messageDao.insert(tplMessageEntryPermit, TplMessageEntryPermit.class);
		sendMsgService.sendMsg(MESSAGE_SEND_ENTRY_PERMIT, null, tplMessageEntryPermit);
		System.out.println("结束");
	}

	private String parseDateToString(Date time) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");//时间格式
		return fmt.format(time);
	}

	
	// 接收质量缺陷电文 add by xty 2015/12/11
	// 单表接受电文信息，根据传进来的 OperationType 字段判断，
	// 值为10新增到表中，值为20做修改操作，值为30做删除操作
	public void receiveTplQualityDefectConfig(TplQualityDefectConfig qualityDefectConfig) throws Exception {
		try {
			System.out.println("新增质量缺陷信息：：：");
			System.out.println("qualityDefectConfig:::"+qualityDefectConfig.toString());
			if(qualityDefectConfig.getOperationType().equals("10")){//新增
				String queryIdSql = "select id from TPL_QUALITY_DEFECT_CONFIG where 1=1 "
					+ " and TRANS_TYPE = '"
					+ qualityDefectConfig.getTransType() + "' and DEFECT_CODE = '"
					+ qualityDefectConfig.getDefectCode() + "'";
				System.out.println("queryQualityDefectConfig:::"+queryIdSql);
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id == null){
					messageDao.insertTplQualityDefectConfig(qualityDefectConfig);
				}
			}else if(qualityDefectConfig.getOperationType().equals("20")){//修改
				System.out.println("更新质量缺陷信息：：：");
				String queryQualityDefectConfigHql = " from TplQualityDefectConfig as t where 1=1 and t.transType = '"+qualityDefectConfig.getTransType()+"' and t.defectCode = '"+qualityDefectConfig.getDefectCode()+"'";
				TplQualityDefectConfig tplQualityDefectConfig = null;
				tplQualityDefectConfig = (TplQualityDefectConfig)messageDao.queryTplQualityDefectConfigByHql(queryQualityDefectConfigHql).get(0);
				if(tplQualityDefectConfig!=null){
					tplQualityDefectConfig.setDefectName(qualityDefectConfig.getDefectName());
					tplQualityDefectConfig.setOperationPerson(qualityDefectConfig.getOperationPerson());
					tplQualityDefectConfig.setOperationTime(qualityDefectConfig.getOperationTime());
					tplQualityDefectConfig.setModifyDate(new Date());
					messageDao.update(tplQualityDefectConfig, TplQualityDefectConfig.class);
				}
			}else{//删除
				System.out.println("删除质量缺陷信息：：：");
				String queryIdSql = "select id from TPL_QUALITY_DEFECT_CONFIG where TRANS_TYPE = '"
					+qualityDefectConfig.getTransType()+"' and DEFECT_CODE = '"
					+qualityDefectConfig.getDefectCode()+"' and OPERATION_PERSON = '"
					+qualityDefectConfig.getOperationPerson()+"' order by ID DESC";
				System.out.println("queryQualityDefectConfig:::"+queryIdSql);
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id != null){
					messageDao.deleteTplQualityDefectConfig(id);
				}
			}
			
		} catch (Exception e) {
			String errTitle = "质量缺陷电文:id为" + qualityDefectConfig.getId() + ",缺陷名称为" + qualityDefectConfig.getDefectName();
			setLogMessage(e, errTitle);
		} 
	}
	
	// 接收拍摄类型电文 add by xty 2015/12/11
	// 单表接受电文信息，根据传进来的 OperationType 字段判断，
	// 值为10新增到表中，值为20做修改操作，值为30做删除操作
	public void receiveTplShooteTypeConfig(TplShooteTypeConfig shooteTypeConfig) throws Exception {
		try {
			if(shooteTypeConfig.getOperationType().equals("10")){//新增
				System.out.println("新增拍摄类型信息：：：");
				String queryIdSql = "select id from TPL_SHOOTE_TYPE_CONFIG where TRANS_TYPE = '"
					+shooteTypeConfig.getTransType()+"' and CAMERA_CODE = '"
					+shooteTypeConfig.getCameraCode()+"' and CAMERA_NUM = '"
					+shooteTypeConfig.getCameraNum()+"' ";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id == null){
					messageDao.insertTplShooteTypeConfig(shooteTypeConfig);
				}
			}else if(shooteTypeConfig.getOperationType().equals("20")){//修改
				System.out.println("更新拍摄类型信息：：：");
				String queryShooteTypeConfigHql = " from tplShooteTypeConfig as t where 1=1 and t.transType = '"+shooteTypeConfig.getTransType()+"' and t.cameraCode = '"+shooteTypeConfig.getCameraCode()+"'";
				TplShooteTypeConfig tplShooteTypeConfig = null;
				tplShooteTypeConfig = (TplShooteTypeConfig)messageDao.queryTplShooteTypeConfigByHql(queryShooteTypeConfigHql).get(0);
				if(tplShooteTypeConfig!=null){
					tplShooteTypeConfig.setCameraName(shooteTypeConfig.getCameraName());
					tplShooteTypeConfig.setCameraNum(shooteTypeConfig.getCameraNum());
					tplShooteTypeConfig.setOperationPerson(shooteTypeConfig.getOperationPerson());
					tplShooteTypeConfig.setOperationTime(shooteTypeConfig.getOperationTime());
					tplShooteTypeConfig.setModifyDate(new Date());
					messageDao.update(tplShooteTypeConfig, TplShooteTypeConfig.class);
				}
			}else{
				//删除
				System.out.println("删除拍摄类型信息：：：");
				String queryIdSql = "select id from TPL_SHOOTE_TYPE_CONFIG where TRANS_TYPE = '"
					+shooteTypeConfig.getTransType()+"' and CAMERA_CODE = '"
					+shooteTypeConfig.getCameraCode()+"' and CAMERA_NUM = '"
					+shooteTypeConfig.getCameraNum()+"' ";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id != null){
					messageDao.deleteTplShooteTypeConfig(id);
				}
			}
			
		} catch (Exception e) {
			String errTitle = "拍摄类型电文:id为" + shooteTypeConfig.getId() + ",拍摄名称为" + shooteTypeConfig.getCameraName();
			setLogMessage(e, errTitle);
		} 
	}
	
	// 接收船批号分配手机号录入信息电文 add by xty 2015/12/11
	// 单表接受电文信息，根据传进来的 OperationType 字段判断，
	// 值为10新增到表中，值为20做删除操作
	public void receiveTplShipMobileQuality(TplShipMobileQuality shipMobileQuality) throws Exception {
		try {
			if(shipMobileQuality.getOperationType().equals("10")){//新增
				String queryIdSql = "select id from TPL_SHIP_MOBILE_QUALITY where TRADING_ID = '"
					+shipMobileQuality.getTradingId()+"' and TPROVIDER_ID = '"
					+shipMobileQuality.getTproviderId()+"'";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id == null){
					//获取订货用户代码
					String tradingId = shipMobileQuality.getTradingId();
					// 转换地区公司别
					shipMobileQuality.setOrderUserId(this.getAreaCompanyCode(tradingId));
					System.out.println("shipMobileQuality:OrderUserId----"+shipMobileQuality.getOrderUserId());
					messageDao.insertTplShipMobileQuality(shipMobileQuality);
				}
			}else{
				//删除
				String queryIdSql = "select id from TPL_SHIP_MOBILE_QUALITY where TRADING_ID = '"
					+shipMobileQuality.getTradingId()+"' and TPROVIDER_ID = '"
					+shipMobileQuality.getTproviderId()+"'";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id != null){
					messageDao.deleteTplShipMobileQuality(id);
				}
			}
			
		} catch (Exception e) {
			String errTitle = "船批号分配手机号电文:id为" + shipMobileQuality.getId() + "船批号为" + shipMobileQuality.getShipId() + "手机号为" + shipMobileQuality.getMobileNum();
			setLogMessage(e, errTitle);
		} 
	}
	//发送物流质量图片电文 add by xty 2015/12/11
	public void sendTplMessageQualityPictures(TplMessageQualityPictures messageQualityPictures) throws Exception{
		System.out.println("发送物流质量图片...");
		//List lists = messageDao.queryTplQualityPictures();
		/*TplQualityPictures tplQualityPictures = new TplQualityPictures();
		for (int i = 0 ; i<lists.size() ; i++){
			tplQualityPictures = (TplQualityPictures)lists.get(i);
			messageQualityPictures.setOperationType(tplQualityPictures.getOperationType());
			messageQualityPictures.setTransType(tplQualityPictures.getTransType());
			messageQualityPictures.setBusinessType(tplQualityPictures.getBusinessType());
			messageQualityPictures.setBillId(tplQualityPictures.getBillId());
			messageQualityPictures.setOrderNum(tplQualityPictures.getOrderNum());
			messageQualityPictures.setCompanyCode(tplQualityPictures.getCompanyCode());
			messageQualityPictures.setPackId(tplQualityPictures.getPackId());
			messageQualityPictures.setDefectCode(tplQualityPictures.getDefectCode());
			messageQualityPictures.setCameraCode(tplQualityPictures.getCameraCode());
			messageQualityPictures.setUuid(tplQualityPictures.getUuid());
			messageQualityPictures.setRemark(tplQualityPictures.getRemark());
			messageQualityPictures.setPictureSize(tplQualityPictures.getPictureSize());
			messageQualityPictures.setPictureTime(tplQualityPictures.getPictureTime());
			messageQualityPictures.setPictureFormat(tplQualityPictures.getPictureFormat());
			messageQualityPictures.setOperationPerson(tplQualityPictures.getOperationPerson());
			messageQualityPictures.setOperationTime(tplQualityPictures.getOperationTime());
			messageQualityPictures.setCreateDate(new Date());
			messageQualityPictures.setCreateId(tplQualityPictures.getCreateId());
			messageQualityPictures.setModifyDate(tplQualityPictures.getModifyDate());
			messageQualityPictures.setModifyId(tplQualityPictures.getModifyId());
			messageQualityPictures.setProviderId(tplQualityPictures.getProviderId());*/
		if(messageQualityPictures!=null){
			messageQualityPictures.setCreateDate(new Date());
			messageDao.insert(messageQualityPictures,TplMessageQualityPictures.class);
			sendMsgService.sendMsg(MESSAGE_SEND_QUALITY_PICTURES, null, messageQualityPictures);
		}
		System.out.println("success");
	}
	
	// 发送手机号注册电文 add by xty 2015/12/11
	public void sendTplPhoneRegistered(TplMessagePhoneRegistered tplMessagePhoneRegistered) throws Exception{
		System.out.println("发送手机号注册...");
		/*List lists = messageDao.queryTplPhoneRegistered();
		TplPhoneRegistered tplPhoneRegistered = new TplPhoneRegistered();
		for (int i = 0 ; i<lists.size() ; i++){
			tplMessagePhoneRegistered = new TplMessagePhoneRegistered();
			tplPhoneRegistered = (TplPhoneRegistered)lists.get(i);
			
			tplMessagePhoneRegistered.setOperationType(tplPhoneRegistered.getOperationType());
			tplMessagePhoneRegistered.setMobileNum(tplPhoneRegistered.getMobileNum());
			tplMessagePhoneRegistered.setWechatNum(tplPhoneRegistered.getWechatNum());
			tplMessagePhoneRegistered.setMobileName(tplPhoneRegistered.getMobileName());
			tplMessagePhoneRegistered.setOperationPerson(tplPhoneRegistered.getOperationPerson());
			tplMessagePhoneRegistered.setOperationTime(tplPhoneRegistered.getOperationTime());
			tplMessagePhoneRegistered.setCreateDate(new Date());
			tplMessagePhoneRegistered.setCreateId(tplPhoneRegistered.getCreateId());
			tplMessagePhoneRegistered.setModifyDate(tplPhoneRegistered.getModifyDate());
			tplMessagePhoneRegistered.setModifyId(tplPhoneRegistered.getModifyId());*/
		if(tplMessagePhoneRegistered!=null){
			tplMessagePhoneRegistered.setCreateDate(new Date());
			messageDao.insert(tplMessagePhoneRegistered,TplMessagePhoneRegistered.class);
			sendMsgService.sendMsg(MESSAGE_SEND_PHONE_REGISTERED, null, tplMessagePhoneRegistered);
		}
		System.out.println("success");
	}
	
	// 铁总 车皮在途动态 add wyx 
	public void receiveTrainsOnLoad(TplMessageSetModel tplMessageSetModel){
		System.out.println("--------------------1");
		
		TplMessageLog tplMessageLog = new TplMessageLog();
		tplMessageLog.setCreateDate(new Date());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//时间格式
		
		TplMessageTrainsOnLoad tplMessageTrainsOnLoad = new TplMessageTrainsOnLoad();
		try {
			Iterator it = BeanConvertUtils.strToBean(tplMessageSetModel.getDetail(), CLASS_MESSAGE_TRAINS_ON_LOAD).iterator();
			while (it.hasNext()) {
				tplMessageTrainsOnLoad = (TplMessageTrainsOnLoad) it.next();
			}
			
			if (tplMessageTrainsOnLoad != null) {
				// 判重
				List lists = messageDao
						.queryTplTrainsOnLoad(tplMessageTrainsOnLoad);
				if (lists != null && lists.size() > 0) {
					Map maps = (Map) lists.get(0);
					Long id = Long.valueOf(maps.get("id").toString());
					String temp = maps.get("insertDate").toString();
					temp = temp.substring(0,19).replaceAll("-", "/");
					System.out.println(temp);
					Date insertDate = fmt.parse(temp);
					System.out.println(insertDate);
					tplMessageTrainsOnLoad.setId(id);
					tplMessageTrainsOnLoad.setInsertDate(insertDate);
					
					tplMessageTrainsOnLoad.setModifyDate(new Date());
					messageDao.update(tplMessageTrainsOnLoad,
							TplMessageTrainsOnLoad.class);
				} else {
					tplMessageTrainsOnLoad.setInsertDate(new Date());
					messageDao.insert(tplMessageTrainsOnLoad,
							TplMessageTrainsOnLoad.class);
				}
			}
			System.out.println("1-------------------------");
			System.out.println("detail++++++++++"+tplMessageSetModel.getDetail());
			System.out.println("send");
			sendMsgService.sendMsg(MESSAGE_SEND_TRAINS_ON_LOAD, null,
					tplMessageSetModel);
			System.out.println("success");
		} catch (Exception e) {
			if(e.getMessage().length()>2000){
				tplMessageLog.setErrContent(e.getMessage().substring(0,2000));
			}else{
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("车皮在途动态接收异常:"+tplMessageTrainsOnLoad.getCarNo());
			messageDao.insert(tplMessageLog,TplMessageLog.class);
		}
	}

	// 铁总 车皮装卸车报告 add wyx
	public void receiveTrainsLoading(TplMessageSetModel tplMessageSetModel){
		System.out.println("1-------------------------");
		TplMessageLog tplMessageLog = new TplMessageLog();
		tplMessageLog.setCreateDate(new Date());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//时间格式
		
		TplMessageTrainsLoading tplMessageTrainsLoading = new TplMessageTrainsLoading();
		try {
			Iterator it = BeanConvertUtils.strToBean(tplMessageSetModel.getDetail(), CLASS_MESSAGE_TRAINS_LOADING).iterator();
			while (it.hasNext()) {
				tplMessageTrainsLoading = (TplMessageTrainsLoading) it.next();
			}
			
			if (tplMessageTrainsLoading != null) {

				// 判重
				List lists = messageDao
						.queryTplTrainsLoading(tplMessageTrainsLoading);
				if (lists != null && lists.size() > 0) {
					System.out.println("update");
					Map maps = (Map) lists.get(0);
					System.out.println("id=========="+maps.get("id"));
					Long id = Long.valueOf(maps.get("id").toString());
					String temp = maps.get("insertDate").toString();
					temp = temp.substring(0,19).replaceAll("-", "/");
					System.out.println(temp);
					Date insertDate = fmt.parse(temp);
					System.out.println(insertDate);
					
					tplMessageTrainsLoading.setId(id);
					tplMessageTrainsLoading.setInsertDate(insertDate);
					
					tplMessageTrainsLoading.setModifyDate(new Date());
					messageDao.update(tplMessageTrainsLoading,
							TplMessageTrainsLoading.class);
				} else {
					System.out.println("insert");
					tplMessageTrainsLoading.setInsertDate(new Date());
					messageDao.insert(tplMessageTrainsLoading,
							TplMessageTrainsLoading.class);
				}
			}
			System.out.println("detail++++++++++"+tplMessageSetModel.getDetail());
			System.out.println("send");
			sendMsgService.sendMsg(MESSAGE_SEND_TRAINS_LOADING, null,
					tplMessageSetModel);
			System.out.println("success");
		} catch (Exception e) {
			if(e.getMessage().length()>2000){
				tplMessageLog.setErrContent(e.getMessage().substring(0,2000));
			}else{
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("车皮装卸车报告接收异常:"+tplMessageTrainsLoading.getCarNo());
			messageDao.insert(tplMessageLog,TplMessageLog.class);
		}
		
		
	}
 
	public void receiveBlackZj(TplRBlackZj tplRBlackZj) throws Exception {
		System.out.println("1++++++++++");
		List lists = messageDao.queryTplBlackZj(tplRBlackZj);
		
		if(lists != null && lists.size() > 0){
			Map maps = (Map) lists.get(0);
			System.out.println("id=========="+maps.get("id"));
			tplRBlackZj.setId(Long.valueOf(maps.get("id").toString()));
			tplRBlackZj.setCreateDate(new Date());
			messageDao.update(tplRBlackZj,TplRBlackZj.class);
		}else{
			tplRBlackZj.setCreateDate(new Date());
			messageDao.insert(tplRBlackZj,TplRBlackZj.class);
		}
		System.out.println("2++++++++++");
	}
	 
	// 服务商运输作业计划信息
	public void sendTplMessageTransPlanMtm(
			TplMessageTransPlanMtm tplMessageTransPlanMtm) throws Exception {
		System.out.println("0");
		System.out.println(tplMessageTransPlanMtm);
		System.out.println("----");
		System.out.println(tplMessageTransPlanMtm.getTransPackItemSet());
		System.out.println("----");
		System.out.println(tplMessageTransPlanMtm.getTransPackItemSet().size());
		messageDao.saveTplMessageTransPlanMtm(tplMessageTransPlanMtm);
		System.out.println("1");
		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.addAll(tplMessageTransPlanMtm.getTransPackItemSet());
		int leftCircularTimes = 0;
		if (tplMessageTransPlanMtm.getTransPackItemSet().size() % CIRCULAR_TRANS_PACK_MTM_TIMES != 0) {
			leftCircularTimes = CIRCULAR_TRANS_PACK_MTM_TIMES
					- tplMessageTransPlanMtm.getTransPackItemSet().size()
					% CIRCULAR_TRANS_PACK_MTM_TIMES;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplMessageTransPackMtm());
		}
		System.out.println(list.size());
		List detailList = getMsgDetail(list, CIRCULAR_TRANS_PACK_MTM_TIMES,CLASS_TRANS_PACK_MTM);
		System.out.println(detailList.size());
		for (int i = 0; i < detailList.size(); i++) {
			tplMessageTransPlanMtm.setDetail((String) detailList.get(i));
			sendMsgService.sendMsg(MESSAGE_SEND_TRAINS_MTM_LOAD, null,tplMessageTransPlanMtm);

		}

	}

	public void sendTplMessageWarehouseRoadnum(
			TplMessageWarehouseRoadnum tplMessageWarehouseRoadnum)
			throws Exception {
		// TODO Auto-generated method stub
		messageDao.insert(tplMessageWarehouseRoadnum,TplMessageWarehouseRoadnum.class);
		sendMsgService.sendMsg(MESSAGE_SEND_WAREHOUSE_ROADNUM, null, tplMessageWarehouseRoadnum);
	}

	public void sendTplMessageTransDisPlan(String flag, 
			TplMessageTransDisPlan tplMessageTransDisPlan) throws Exception {
		tplMessageTransDisPlan.setSendFlag(flag);
		// TODO Auto-generated method stub
		messageDao.insert(tplMessageTransDisPlan,TplMessageTransDisPlan.class);
		sendMsgService.sendMsg(MESSAGE_SEND_TRANS_DIS_PLAN, null, tplMessageTransDisPlan);
		
	}
	
	

	/**
	 * *************************************************************************
	 * 2018/3
	 * *************************************************************************
	 */
	//湛江临时进场证电文发送[XZXL11]
	public void sendEntryPermit02(TplMessageEntryPermitZj tplMessageEntryPermitZj)
			throws Exception {
		Date date = new Date();
		System.out.println("发送进厂车辆");
		if(tplMessageEntryPermitZj!=null){
			tplMessageEntryPermitZj.setCreateDate(date);
			messageDao.insert(tplMessageEntryPermitZj, TplMessageEntryPermitZj.class);
			Set TplMessageEntryPermitZjSSet = tplMessageEntryPermitZj.getTplMessageEntryPermitZjS();
			for (Iterator it = TplMessageEntryPermitZjSSet.iterator(); it.hasNext();) {
				TplMessageEntryPermitZjS tplMessageEntryPermitZjS = (TplMessageEntryPermitZjS) it.next();
				if(tplMessageEntryPermitZjS.getMaterialNum()!=null && !tplMessageEntryPermitZjS.getMaterialNum().equals("")){
					tplMessageEntryPermitZjS.setMessageId(messageDao.queryMessageEntryPermitZjId());
					messageDao.insert(tplMessageEntryPermitZjS, TplMessageEntryPermitZjS.class);
				}
		}
		}
		System.out.println(tplMessageEntryPermitZj);
		System.out.println("----");
		System.out.println(tplMessageEntryPermitZj.getTplMessageEntryPermitZjS());
		System.out.println("----");
		System.out.println(tplMessageEntryPermitZj.getTplMessageEntryPermitZjS().size());
		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.addAll(tplMessageEntryPermitZj.getTplMessageEntryPermitZjS());
		int leftCircularTimes = 0;
		if (tplMessageEntryPermitZj.getTplMessageEntryPermitZjS().size() % CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES != 0) {
			leftCircularTimes = CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES - tplMessageEntryPermitZj.getTplMessageEntryPermitZjS().size()
					% CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplMessageEntryPermitZjS());
		}
		List detailList = getMsgDetail(list, CIRCULAR_RESOURCE_MODIFY_APLLY_TIMES, CLASS_MESSAGE_ENTRY_PERMIT_ZJ_S);
		System.out.println("进场证详细信息");
		for (int i = 0; i < detailList.size(); i++) {
			tplMessageEntryPermitZj.setDetail((String) detailList.get(i));
			logger.info("---发送进厂证信息：" + new Date());
			sendMsgService.sendMsg(MESSAGE_SEND_ENTRY_PERMIT_02, null, tplMessageEntryPermitZj);
		}
		System.out.println("结束");
	}

	//船舶到港信息发送[XZXL19]
	public void sendTplShipInqManage(TplShipReacfManage tplShipReacfManage,String procType)
			throws Exception {
		TplMessageShipReacfManage shipReacfManage = new TplMessageShipReacfManage();
		shipReacfManage.setProcType(procType);
		if(tplShipReacfManage.getProviderId()!=null && !tplShipReacfManage.getProviderId().trim().equals("")){
			shipReacfManage.setProviderId(tplShipReacfManage.getProviderId());
		}else{
			shipReacfManage.setProviderId("测试");
		}
		if(tplShipReacfManage.getProviderName()!=null && !tplShipReacfManage.getProviderName().trim().equals("")){
			shipReacfManage.setProviderName(tplShipReacfManage.getProviderName());
		}else{
			shipReacfManage.setProviderName("测试");
		}
		shipReacfManage.setShipName(tplShipReacfManage.getShipName());
		shipReacfManage.setShipArriveTime(DateUtils.toString(tplShipReacfManage.getShipArriveTime(),SystemConstants.dateformat6));
		shipReacfManage.setCarryWeight(tplShipReacfManage.getCarryWeight());
		shipReacfManage.setRegion(tplShipReacfManage.getRegion());
		shipReacfManage.setPlanPort(tplShipReacfManage.getPlanPort());
		shipReacfManage.setShipType(tplShipReacfManage.getShipType());
		shipReacfManage.setRemarks(tplShipReacfManage.getRemarks());
		shipReacfManage.setCreateDate(new Date());
		System.out.println("新增船舶到港电文信息");
		messageDao.insert(shipReacfManage, TplMessageShipReacfManage.class);
		logger.info("---发送船舶到港信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_SHIP_INQ_MANAGE, null, shipReacfManage);
		System.out.println("结束");
	}
	
	//司机车辆绑定信息发送[XZXL12]
	public void sendCarMessageBind(TplMessageCarBind tplMessageCarBind)
			throws Exception {
		System.out.println("司机车辆绑定信息发送");
		if (tplMessageCarBind != null) {
			messageDao.insert(tplMessageCarBind, TplMessageCarBind.class);
			Set tplMessageCarBindItemSet = tplMessageCarBind.getTplMessageCarBindItem();
			for (Iterator it = tplMessageCarBindItemSet.iterator(); it.hasNext();) {
				TplMessageCarBindItem tplMessageCarBindItem = (TplMessageCarBindItem) it.next();
				tplMessageCarBindItem.setCarBindId(tplMessageCarBind.getId());
				messageDao.insert(tplMessageCarBindItem,TplMessageCarBindItem.class);
				System.out.println("提单明细："+tplMessageCarBindItem.getPickNo()+"*****"+tplMessageCarBindItem.getStoreSystem()+"提单ID:"+tplMessageCarBind.getId());
			}
		}
		System.out.println(tplMessageCarBind);
		System.out.println("----");
		System.out.println(tplMessageCarBind.getTplMessageCarBindItem());
		System.out.println("----");
		System.out.println(tplMessageCarBind.getTplMessageCarBindItem().size());
		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.addAll(tplMessageCarBind.getTplMessageCarBindItem());
		int leftCircularTimes = 0;
		if (tplMessageCarBind.getTplMessageCarBindItem().size() % CIRCULAR_TRANS_ITEM_TIMES != 0) {
			leftCircularTimes = CIRCULAR_TRANS_ITEM_TIMES -  tplMessageCarBind.getTplMessageCarBindItem().size()
					% CIRCULAR_TRANS_ITEM_TIMES;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplMessageCarBindItem());
		}
		List detailList = getMsgDetail(list, CIRCULAR_TRANS_ITEM_TIMES, CLASS_MESSAGE_TPL_MESSAGE_CAR_BIND_ITEM);
		System.out.println("司机车辆绑定详细信息");
		for (int i = 0; i < detailList.size(); i++) {
			tplMessageCarBind.setDetail((String) detailList.get(i));
			logger.info("---司机车辆绑定信息：" + new Date());
			sendMsgService.sendMsg(MESSAGE_TPL_MESSAGE_CAR_BIND_ITEM, null, tplMessageCarBind);
		}
		System.out.println("结束");
		
	}
	
	/**
	 * 自提车签到[XZXL13]发送
	 */
	public void sendCarMessage(TplMSinceMaterial tplMSinceMaterial) throws Exception {
		if (tplMSinceMaterial != null) {
			tplMSinceMaterial.setCreateDate(new Date());
			messageDao.insert(tplMSinceMaterial, TplMSinceMaterial.class);
			Set tplMSinceMaterialItemSet = tplMSinceMaterial.getTplMSinceMaterialItemSet();
			for (Iterator it = tplMSinceMaterialItemSet.iterator(); it.hasNext();) {
				TplMSinceMaterialItem tplMSinceMaterialItem = (TplMSinceMaterialItem) it.next();
				tplMSinceMaterialItem.setMateriaPlanId(tplMSinceMaterial.getId());
				messageDao.insert(tplMSinceMaterialItem,TplMSinceMaterialItem.class);
			}
		}
		
		System.out.println(tplMSinceMaterial);
		System.out.println("----");
		System.out.println(tplMSinceMaterial.getTplMSinceMaterialItemSet());
		System.out.println("----");
		System.out.println(tplMSinceMaterial.getTplMSinceMaterialItemSet().size());
		// 补充空model,达到指定循环次数
		List list = new ArrayList();
		list.addAll(tplMSinceMaterial.getTplMSinceMaterialItemSet());
		int leftCircularTimes = 0;
		if (tplMSinceMaterial.getTplMSinceMaterialItemSet().size() % CIRCULAR_TPL_CAR_PLAN_ITEM != 0) {
			leftCircularTimes = CIRCULAR_TPL_CAR_PLAN_ITEM -  tplMSinceMaterial.getTplMSinceMaterialItemSet().size()
					% CIRCULAR_TPL_CAR_PLAN_ITEM;
		}
		for (int i = 0; i < leftCircularTimes; i++) {
			list.add(new TplMessageCarPlanItem());
		}
		List detailList = getMsgDetail(list, CIRCULAR_TPL_CAR_PLAN_ITEM, CLASS_SINCE_TPL_M_SINCE_MATERIAL_ITEM);
		System.out.println("自提详细信息");
		for (int i = 0; i < detailList.size(); i++) {
			tplMSinceMaterial.setDetail((String) detailList.get(i));
			logger.info("---司机车辆绑定信息：" + new Date());
			sendMsgService.sendMsg(MESSAGE_SEND_CAR, null, tplMSinceMaterial);
		}
		System.out.println("结束");
	}

	//自提车辆准入申请应答[XLXZ48]
	public void receiveTplPermitMessageZj(TplPermitMessageZj tplPermitMessageZj)
			throws Exception {
		System.out.println("自提车辆准入申请应答");
		System.out.println("1++++++++++");
		tplPermitMessageZj.setCreateDate(new Date());
		messageDao.insert(tplPermitMessageZj,TplPermitMessageZj.class);
		//执行进场证申请撤销
		if(tplPermitMessageZj.getResultFlag()!=null && tplPermitMessageZj.getResultFlag().equals("Y")){
			String  queryHql = " from TplREntryPermitZj as t where 1=1 and t.billCode = '"+tplPermitMessageZj.getBillCode()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				TplREntryPermitZj tplREntryPermitZj= (TplREntryPermitZj) list.get(0);
				tplREntryPermitZj.setSendStatus("0");//未发送
				tplREntryPermitZj.setRequestResult("");//红冲成功置为未审核
				messageDao.update(tplREntryPermitZj, TplREntryPermitZj.class);
			}
		}else if(tplPermitMessageZj.getRequestResult()!=null && tplPermitMessageZj.getRequestResult().equals("Y")){//准入申请通过
			String  queryHql = " from TplREntryPermitZj as t where 1=1 and t.billCode = '"+tplPermitMessageZj.getBillCode()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				TplREntryPermitZj tplREntryPermitZj= (TplREntryPermitZj) list.get(0);
				tplREntryPermitZj.setRequestResult("1");//有进场证资格
				messageDao.update(tplREntryPermitZj, TplREntryPermitZj.class);
			}
		}else if(tplPermitMessageZj.getRequestResult()!=null && tplPermitMessageZj.getRequestResult().equals("N")){//准入申请通过
			String  queryHql = " from TplREntryPermitZj as t where 1=1 and t.billCode = '"+tplPermitMessageZj.getBillCode()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				TplREntryPermitZj tplREntryPermitZj= (TplREntryPermitZj) list.get(0);
				tplREntryPermitZj.setRequestResult("0");//无进场证资格
				messageDao.update(tplREntryPermitZj, TplREntryPermitZj.class);
			}
		}
		System.out.println("2++++++++++");
	}
	
	/**
	 * 自提车辆配载计划[XLXZ43]
	 */
	public void receiveTplSinceCarPlan(TplMessageCarPlan tplMessageCarPlan)
			throws Exception {
		Date date = new Date();
		System.out.println("自提车辆配载信息接收开始");
		tplMessageCarPlan.setTplMessageCarPlanItemSet(BeanConvertUtils.strToBean(tplMessageCarPlan.getDetail(), CLASS_SINCE_CAR_PLAN_ITEM));
		// 处理空model
		Iterator it = tplMessageCarPlan.getTplMessageCarPlanItemSet().iterator();
		while (it.hasNext()) {
			TplMessageCarPlanItem tplMessageCarPlanItem = (TplMessageCarPlanItem) it.next();
			if ("".equals(tplMessageCarPlanItem.getPackNo())) {
				it.remove();
			}
		}
		//如果是新增判断重
		if(tplMessageCarPlan.getProcType().equals("I")){
			String  queryHql = " from TplCarPlan as t where 1=1 and t.carPickNo = '"+tplMessageCarPlan.getCarPickNo()+"' and t.carNum = '"+tplMessageCarPlan.getCarNum()+"' and t.seqId = '"+tplMessageCarPlan.getSeqId()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				return;
			}
		}
		
		//接收电文表新增
		tplMessageCarPlan.setCreateDate(date);
		messageDao.insert(tplMessageCarPlan, TplMessageCarPlan.class);
		if(tplMessageCarPlan.getTplMessageCarPlanItemSet()!=null && tplMessageCarPlan.getTplMessageCarPlanItemSet().size()>0){
			Iterator itDouble = tplMessageCarPlan.getTplMessageCarPlanItemSet().iterator();
			if(itDouble != null ){
				while(itDouble.hasNext()){
					TplMessageCarPlanItem tplMessageCarPlanItem = (TplMessageCarPlanItem) itDouble.next();
					tplMessageCarPlanItem.setCarPlanId(tplMessageCarPlan.getId());
					messageDao.insert(tplMessageCarPlanItem, TplMessageCarPlanItem.class);
				}
			}
		}
		//已解绑的车次不接受配载计划
		String queryTplCarBindHql = " from TplCarBind as t where 1=1 and t.seqId = '" + tplMessageCarPlan.getSeqId()+"'";
		List carBindList = messageDao.queryBySql(queryTplCarBindHql);
		if(carBindList!=null){
			TplCarBind tplCarBind = (TplCarBind)carBindList.get(0);
			String revokeStruts = tplCarBind.getRevokeStruts();
			if(revokeStruts!=null && revokeStruts.equals("1")){
				TplMessageLog tplMessageLog = new TplMessageLog();
				tplMessageLog.setErrTitle("配载计划接收失败");
				tplMessageLog.setErrContent(tplMessageCarPlan.getCarPickNo()+"配载计划接收失败:车次号:"+tplMessageCarPlan.getSeqId()+"已被解绑");
				tplMessageLog.setOperMemo("XLXZ43");
				tplMessageLog.setCreateDate(new Date());
				messageDao.insert(tplMessageLog, TplMessageLog.class);
				return;
			}
		}
		
		String queryTplTransSeqHql = " from TplTransSeq as t where 1=1 and t.id = "+tplMessageCarPlan.getSeqId();
		List listTplTransSeq = messageDao.queryTplQualityDefectConfigByHql(queryTplTransSeqHql);
		TplTransSeq tplTransSeq = new TplTransSeq();
		if(null !=listTplTransSeq && listTplTransSeq.size()>0){
			tplTransSeq = (TplTransSeq) listTplTransSeq.get(0);
		}else{
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setErrTitle("配载计划接收失败");
			tplMessageLog.setErrContent(tplMessageCarPlan.getCarPickNo()+"配载计划接收失败:车次号:"+tplMessageCarPlan.getSeqId()+"不存在");
			tplMessageLog.setOperMemo("XLXZ43");
			tplMessageLog.setCreateDate(new Date());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
			return;
		}
		
		if(tplMessageCarPlan.getProcType().equals("I")){//新增
			//新增自提车辆配载计划业务主信息
			TplCarPlan tplCarPlan = new TplCarPlan();
			tplCarPlan.setCarNum(tplMessageCarPlan.getCarNum());
			tplCarPlan.setCarPickNo(tplMessageCarPlan.getCarPickNo());
			tplCarPlan.setCount(tplMessageCarPlan.getCount());
			tplCarPlan.setDriverId(tplMessageCarPlan.getDriverId());
			tplCarPlan.setGrossWeight(null !=tplMessageCarPlan.getGrossWeight()? (tplMessageCarPlan.getGrossWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));//kg化成吨，余六位小数
			tplCarPlan.setNetWeight(null !=tplMessageCarPlan.getNetWeight()? (tplMessageCarPlan.getNetWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));
			tplCarPlan.setSeqId(tplMessageCarPlan.getSeqId());
			tplCarPlan.setTrailerNum(tplMessageCarPlan.getTrailerNum());
			tplCarPlan.setCreateDate(date);
			tplCarPlan.setProviderId(tplMessageCarPlan.getProviderId());
			tplCarPlan.setProviderName(tplMessageCarPlan.getProviderName());
			tplCarPlan.settStatus("0");
			messageDao.insert(tplCarPlan, TplCarPlan.class);
			String seqId = tplMessageCarPlan.getSeqId();
			if(tplMessageCarPlan.getTplMessageCarPlanItemSet()!=null && tplMessageCarPlan.getTplMessageCarPlanItemSet().size()>0){
				Iterator itDouble = tplMessageCarPlan.getTplMessageCarPlanItemSet().iterator();
				if(itDouble != null ){
					while (itDouble.hasNext()) {
						TplMessageCarPlanItem tplMessageCarPlanItem = (TplMessageCarPlanItem)itDouble.next();
						//自提车辆配载计划业务子信息
						TplCarPlanItem tplCarPlanItem = new TplCarPlanItem();
						tplCarPlanItem.setPackNo(tplMessageCarPlanItem.getPackNo());
						tplCarPlanItem.setPickNo(tplMessageCarPlanItem.getPickNo());
						tplCarPlanItem.setOrderNum(tplMessageCarPlanItem.getOrderNum());
						tplCarPlanItem.setGrossWeight(null !=tplMessageCarPlanItem.getGrossWeight()? (tplMessageCarPlanItem.getGrossWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));
						tplCarPlanItem.setNetWeight(null !=tplMessageCarPlanItem.getNetWeight()? (tplMessageCarPlanItem.getNetWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));
						tplCarPlanItem.setCount(tplMessageCarPlanItem.getCount());
						tplCarPlanItem.setProductName(tplMessageCarPlanItem.getProductName());
						tplCarPlanItem.setLadingSpotId(tplMessageCarPlanItem.getLadingSpotId());
						System.out.println(StringUtil.trimStr(tplMessageCarPlanItem.getLadingSpotName()).equals(""));
						System.out.println(StringUtil.trimStr(tplMessageCarPlanItem.getLadingSpotName())=="");
						System.out.println(StringUtil.trimStr(tplMessageCarPlanItem.getLadingSpotName())==null);
						tplCarPlanItem.setLadingSpotName(StringUtil.trimStr(tplMessageCarPlanItem.getLadingSpotName()).equals("") ? SystemConfigUtil.ZG_WAREHOUSE_LIST.get(tplMessageCarPlanItem.getLadingSpotId())+"":tplMessageCarPlanItem.getLadingSpotName());
						tplCarPlanItem.setDestSpotId(tplMessageCarPlanItem.getDestSpotId());
						tplCarPlanItem.setDestSpotName(tplMessageCarPlanItem.getDestSpotName());
						tplCarPlanItem.setCarPlanId(tplCarPlan.getId());
						tplCarPlanItem.setStruts("0");
						tplCarPlanItem.setStrutsFlag("0");
						tplCarPlanItem.setValidateFlag("00");
						messageDao.insert(tplCarPlanItem, TplCarPlanItem.class);
						
						// 更新派单为执行中状态
//						String updateSql = "update tpl_trans_truck_dis_plan t set t.status = '10' where t.pick_num = '"+tplMessageCarPlanItem.getPickNo()+"'";
//						messageDao.updateBySql(updateSql);
						String packId = tplMessageCarPlanItem.getPackNo();
						String pickNum = tplMessageCarPlanItem.getPickNo();
						TplTransPack transPack = messageDao.searchTransPackForCarPlan(packId, pickNum);
						if(transPack!=null){
							transPack.setSeqId(new Long(seqId));
							transPack.setPackStatus("1");//已装载
							transPack.setProStatus("30");//已装车
							messageDao.update(transPack, TplTransPack.class);
						}
					}
				}
			}
			//刷新车次信息毛重，净重，pro_status
			if(tplTransSeq.getId()!=null){
				String grossWeight = tplMessageCarPlan.getGrossWeight().divide(new BigDecimal("1000"),6, BigDecimal.ROUND_UP).toString();
				String netWeight = tplMessageCarPlan.getNetWeight().divide(new BigDecimal("1000"),6, BigDecimal.ROUND_UP).toString();
				tplTransSeq.setPlanGrossWeight(Double.valueOf(grossWeight));
				tplTransSeq.setActGrossWeight(Double.valueOf(grossWeight));
				tplTransSeq.setPlanNetWeight(Double.valueOf(netWeight));
				tplTransSeq.setActNetWeight(Double.valueOf(netWeight));
				tplTransSeq.setPlanCount(tplMessageCarPlan.getCount());
				tplTransSeq.setActCount(tplMessageCarPlan.getCount());
				tplTransSeq.setProStatus("03");
				tplTransSeq.setUploadTime(date);//装车时间
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
			
			List planIdList = messageDao.queryPlanIdBySeqId(seqId);
			if(planIdList.size()>0){
				for(int i=0 ;i<planIdList.size();i++){
					String transPlanId = (String)planIdList.get(i);
					
					List list03 = messageDao.tplTransPlanCount(transPlanId);
					BigDecimal actGrossWeight = new BigDecimal("0");
					BigDecimal actNetWeight = new BigDecimal("0");
					Long actCount = new Long("0");
					if(null!=list03 && list03.size()>0){
						Object [] obj = (Object[]) list03.get(0);
						actGrossWeight = null==obj[0]? new BigDecimal("0"):new BigDecimal(obj[0].toString());
						actNetWeight = null==obj[1]? new BigDecimal("0"):new BigDecimal(obj[1].toString());
						actCount = null==obj[2]? new Long(0): new Long(obj[2].toString());
					}
					String sql = "from TplTransPlan t where t.transPlanId = '"+transPlanId+"'";
					TplTransPlan tplTransPlan = (TplTransPlan)messageDao.queryTplQualityDefectConfigByHql(sql).get(0);
					//更新计划
					tplTransPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransPlan.setActCount(actCount);
					tplTransPlan.setStatus("1");
					messageDao.update(tplTransPlan,TplTransPlan.class);
				
					//更新汽运调度计划
					TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
					tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransTruckDisPlan.setActCount(actCount);
					tplTransTruckDisPlan.setStatus("10");
					tplTransTruckDisPlan.setTransPlanId(transPlanId);
					//根据所指定的计划号去更新所有的汽运调度计划
					messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);
				}
			}
		}else if(tplMessageCarPlan.getProcType().equals("D")){//删除
			//业务表
			String queryIdSql = "select id from TPL_CAR_PLAN where CAR_PICK_NO = '"
				+tplMessageCarPlan.getCarPickNo()+"' and CAR_NUM = '"
				+tplMessageCarPlan.getCarNum()+"' and SEQ_ID = '"
				+tplMessageCarPlan.getSeqId()+"'";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id!=null){
				Long [] ids = {id};
				messageDao.delete(ids, TplCarPlan.class);
				String deleteTplCarPlanItem = "delete from TPL_CAR_PLAN_ITEM t where t.CAR_PLAN_ID = " + id ;
				messageDao.deleteSql(deleteTplCarPlanItem);
			}
		}else if(tplMessageCarPlan.getProcType().equals("U")){//修改
			String  queryHql = " from TplCarPlan as t where 1=1 and t.carPickNo = '"+tplMessageCarPlan.getCarPickNo()+"' and t.carNum = '"+tplMessageCarPlan.getCarNum()+"' and t.seqId = '"+tplMessageCarPlan.getSeqId()+"'";
			System.out.println("***queryHql:"+queryHql);
			TplCarPlan tplCarPlan;
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				tplCarPlan = (TplCarPlan)list.get(0);
				if(tplCarPlan!=null){
					tplCarPlan.setCarNum(tplMessageCarPlan.getCarNum());
					tplCarPlan.setCarPickNo(tplMessageCarPlan.getCarPickNo());
					tplCarPlan.setCount(tplMessageCarPlan.getCount());
					tplCarPlan.setDriverId(tplMessageCarPlan.getDriverId());
					tplCarPlan.setGrossWeight(null !=tplMessageCarPlan.getGrossWeight()? (tplMessageCarPlan.getGrossWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));//kg化成吨，余六位小数
					tplCarPlan.setNetWeight(null !=tplMessageCarPlan.getNetWeight()? (tplMessageCarPlan.getNetWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));
					tplCarPlan.setSeqId(tplMessageCarPlan.getSeqId());
					tplCarPlan.setTrailerNum(tplMessageCarPlan.getTrailerNum());
					tplCarPlan.setProviderId(tplMessageCarPlan.getProviderId());
					tplCarPlan.setProviderName(tplMessageCarPlan.getProviderName());
					tplCarPlan.settStatus("0");
					messageDao.update(tplCarPlan, TplCarPlan.class);
				}
				//删除子信息，重新添加
				if(tplCarPlan!=null && tplCarPlan.getId()!=null){
					String deleteTplCarPlanItem = "delete from TPL_CAR_PLAN_ITEM t where t.CAR_PLAN_ID = " + tplCarPlan.getId();
					messageDao.deleteSql(deleteTplCarPlanItem);
					if(tplMessageCarPlan.getTplMessageCarPlanItemSet()!=null && tplMessageCarPlan.getTplMessageCarPlanItemSet().size()>0){
						Iterator itDouble = tplMessageCarPlan.getTplMessageCarPlanItemSet().iterator();
						if(itDouble != null ){
							while (itDouble.hasNext()) {
								TplMessageCarPlanItem tplMessageCarPlanItem = (TplMessageCarPlanItem)itDouble.next();
								//自提车辆配载计划业务子信息
								TplCarPlanItem tplCarPlanItem = new TplCarPlanItem();
								tplCarPlanItem.setPackNo(tplMessageCarPlanItem.getPackNo());
								tplCarPlanItem.setPickNo(tplMessageCarPlanItem.getPickNo());
								tplCarPlanItem.setOrderNum(tplMessageCarPlanItem.getOrderNum());
								tplCarPlanItem.setGrossWeight(null !=tplMessageCarPlanItem.getGrossWeight()? (tplMessageCarPlanItem.getGrossWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)): new BigDecimal("0"));
								tplCarPlanItem.setNetWeight(null !=tplMessageCarPlanItem.getNetWeight()? (tplMessageCarPlanItem.getNetWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)): new BigDecimal("0"));
								tplCarPlanItem.setCount(tplMessageCarPlanItem.getCount());
								tplCarPlanItem.setProductName(tplMessageCarPlanItem.getProductName());
								tplCarPlanItem.setLadingSpotId(tplMessageCarPlanItem.getLadingSpotId());
								tplCarPlanItem.setLadingSpotName(tplMessageCarPlanItem.getLadingSpotName());
								tplCarPlanItem.setDestSpotId(tplMessageCarPlanItem.getDestSpotId());
								tplCarPlanItem.setDestSpotName(tplMessageCarPlanItem.getDestSpotName());
								tplCarPlanItem.setCarPlanId(tplCarPlan.getId());
								tplCarPlanItem.setStruts("0");
								tplCarPlanItem.setStrutsFlag("0");
								tplCarPlanItem.setValidateFlag("00");
								messageDao.insert(tplCarPlanItem, TplCarPlanItem.class);
							}
						}
					}
				}
			}
			//刷新车次信息毛重，净重，pro_status
			if(tplTransSeq.getId()!=null){
				String grossWeight = tplMessageCarPlan.getGrossWeight().divide(new BigDecimal("1000"),6, BigDecimal.ROUND_UP).toString();
				String netWeight = tplMessageCarPlan.getNetWeight().divide(new BigDecimal("1000"),6, BigDecimal.ROUND_UP).toString();
				tplTransSeq.setPlanGrossWeight(Double.valueOf(grossWeight));
				tplTransSeq.setActGrossWeight(Double.valueOf(grossWeight));
				tplTransSeq.setPlanNetWeight(Double.valueOf(netWeight));
				tplTransSeq.setActNetWeight(Double.valueOf(netWeight));
				tplTransSeq.setPlanCount(tplMessageCarPlan.getCount());
				tplTransSeq.setActCount(tplMessageCarPlan.getCount());
				tplTransSeq.setProStatus("03");
				tplTransSeq.setUploadTime(date);//装车时间
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
		}
		System.out.println("结束");
	}
	
	//车道车位分配[XLXZ45]
	//根据tplMessageCarPosition的procType字段判断是新增还是修改'I','D','U'
	public void receiveTplCarPosition(TplMessageCarPosition tplMessageCarPosition) throws Exception {
		System.out.println("车道车位分配");
		Date date = new Date();
		//如果是新增判断重复
		if(tplMessageCarPosition.getProcType().equals("I")){
			String  queryHql = " from TplCarPosition as t where 1=1 and t.storeCode = '"+tplMessageCarPosition.getStoreCode()+"' and t.parkingCode = '"+tplMessageCarPosition.getParkingCode()+"' and carPickNo ='"+tplMessageCarPosition.getCarPickNo()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				return;
			}
		}
		//接收电文表
		tplMessageCarPosition.setCreateDate(date);
		messageDao.insert(tplMessageCarPosition, TplMessageCarPosition.class);
		
		if(tplMessageCarPosition.getProcType().equals("I")){//新增
			System.out.println("新增业务表信息：：：");
			TplCarPosition  tplCarPosition = new TplCarPosition();
			tplCarPosition.setCarPickNo(tplMessageCarPosition.getCarPickNo());
			tplCarPosition.setCarNum(tplMessageCarPosition.getCarNum());
			tplCarPosition.setTrailerNum(tplMessageCarPosition.getTrailerNum());
			tplCarPosition.setSeqId(tplMessageCarPosition.getSeqId());
			tplCarPosition.setStoreCode(tplMessageCarPosition.getStoreCode());
			tplCarPosition.setParkingCode(tplMessageCarPosition.getParkingCode());
			tplCarPosition.setDistributionTime(DateUtils.toDate(tplMessageCarPosition.getDistributionTime(), SystemConstants.dateformat6));
			tplCarPosition.setCreateDate(date);
			tplCarPosition.setFlag("00");
			messageDao.insert(tplCarPosition, TplCarPosition.class);
		}else if(tplMessageCarPosition.getProcType().equals("D")){//撤销车位分配
			System.out.println("删除业务表信息：：：");
			String seqId = tplMessageCarPosition.getSeqId();
			String storeCode = tplMessageCarPosition.getStoreCode();
			
			//删除车位分配信息
			String queryIdSql02 = "select id from TPL_CAR_POSITION where STORE_CODE = '"
				+tplMessageCarPosition.getStoreCode()+"' and PARKING_CODE = '"
				+tplMessageCarPosition.getParkingCode()
				+"' and SEQ_ID ='"+tplMessageCarPosition.getSeqId()
				+"' order by ID DESC";
			System.out.println("queryCarPosition:::"+queryIdSql02);
			Long id02 = messageDao.queryIdBySql(queryIdSql02);
			if(id02 != null){
				Long [] ids = {id02};
				messageDao.delete(ids, TplCarPosition.class);
			}
			//库区重新签到
			String querySeqSignHql = " from TplTransTruckSignIn as t where 1=1 and t.seqId = "+seqId+" and substr(t.ladingSpot,-3,3) = '"+storeCode+"' and t.status = '10'";
			List list = messageDao.queryTplQualityDefectConfigByHql(querySeqSignHql);
			if(list.size()>0){
				TplTransTruckSignIn singIn = (TplTransTruckSignIn)list.get(0);
				singIn.setStatus("00");//无效
				messageDao.update(singIn, TplTransTruckSignIn.class);
			}
			//还原车次状态到签到前的状态 ---配载状态
//			if(seqId!=null){
//				TplTransSeq transSeq = (TplTransSeq)messageDao.queryById(new Long(seqId), TplTransSeq.class);
//				if(transSeq!=null){
//					transSeq.setProStatus("03");
//					messageDao.update(transSeq, TplTransSeq.class);
//				}
//			}
			
		}else if(tplMessageCarPosition.getProcType().equals("U")){//修改
			System.out.println("更新业务表信息：：：");
			String  queryTplCarPositionHql = " from TplCarPosition as t where 1=1 and t.storeCode = '"+tplMessageCarPosition.getStoreCode()+"' and t.carNum = '"+tplMessageCarPosition.getCarNum()+"' and t.carPickNo ='"+tplMessageCarPosition.getCarPickNo()+"'";
			TplCarPosition  tplCarPosition;
			List list = messageDao.queryTplQualityDefectConfigByHql(queryTplCarPositionHql);
			if(list!=null && list.size()>0){
				tplCarPosition = (TplCarPosition)list.get(0);
				if(tplCarPosition!=null && tplCarPosition.getId()!=null){
					tplCarPosition.setCarPickNo(tplMessageCarPosition.getCarPickNo());
					tplCarPosition.setCarNum(tplMessageCarPosition.getCarNum());
					tplCarPosition.setTrailerNum(tplMessageCarPosition.getTrailerNum());
					tplCarPosition.setSeqId(tplMessageCarPosition.getSeqId());
					tplCarPosition.setStoreCode(tplMessageCarPosition.getStoreCode());
					tplCarPosition.setParkingCode(tplMessageCarPosition.getParkingCode());
					tplCarPosition.setDistributionTime(DateUtils.toDate(tplMessageCarPosition.getDistributionTime(), SystemConstants.dateformat6));
					tplCarPosition.setFlag("00");
					messageDao.update(tplCarPosition, TplCarPosition.class);
				}
			}
		}
		
		//执行消息推送
		if (tplMessageCarPosition.getProcType().equals("I")
				|| tplMessageCarPosition.getProcType().equals("D")
				|| tplMessageCarPosition.getProcType().equals("U")) {
			System.out.println("消息推送信息封装");
			// 查询车次信息
			String queryTplTranSeq = " from TplTransSeq t where t.id = "
					+ Long.valueOf(tplMessageCarPosition.getSeqId());
			List listSeq = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
			if (listSeq != null && listSeq.size() > 0) {
				TplTransSeq tplTransSeq = (TplTransSeq) listSeq.get(0);
				HashMap bspMap = new HashMap();
				String alias = tplTransSeq.getDriver();// 发送别名
				String storeCode = tplMessageCarPosition.getStoreCode();// 库区id
				bspMap.put("ALIAS", alias);// 别名
				bspMap.put("WAREHOUSE_ID", storeCode);// 仓库id

				// 根据库区号和车次号查询出库区名称
				List listTplCarPlanItem = messageDao.queryTplCarPlanItem(
						storeCode, String.valueOf(tplTransSeq.getId()));
				System.out.println("listTplCarPlanItem::::::"+listTplCarPlanItem.size());
				if (listTplCarPlanItem != null && listTplCarPlanItem.size() > 0) {
					Object[] items = (Object[]) listTplCarPlanItem.get(0);
					bspMap.put("WAREHOUSE_NAME", items[1].toString());// 仓库名称
				}else{
					bspMap.put("WAREHOUSE_NAME", "");// 仓库名称
				}
				CarStoreSeqenceSearchModel searchModel = new CarStoreSeqenceSearchModel();
				searchModel.setCarPlanId(tplMessageCarPosition.getCarPickNo());
				searchModel.setStoreCode(storeCode);
				searchModel.setCarId(tplTransSeq.getCarId());
				List listTplKq = messageDao.queryTplKq(searchModel);
				System.out.println("listTplKq::::::::"+listTplKq.size());
				if (listTplKq != null && listTplKq.size() > 0) {
					Object[] items = (Object[]) listTplKq.get(0);
					bspMap.put("LIST_NUM", "车道"+items[0].toString()+"-序号"+items[1].toString());
				} else {
					bspMap.put("LIST_NUM", "");
				}
				bspMap.put("SYSTEM_TYPE", "20");// 系统标志
				bspMap.put("CONFIRM_FLAG", "00");// 是否到位
				bspMap.put("PROC_TYPE", tplMessageCarPosition.getProcType());
				bspMap.put("DRI_U_CODE",alias);
				/*if(tplMessageCarPosition.getProcType().equals("I")){
					String alter = tplMessageCarPosition.getCarNum() + "在"+ storeCode + "库已分配车位为"+ tplMessageCarPosition.getParkingCode() + "!";// 发送消息内容
					bspMap.put("ALTER", alter);// 推送内容
					bspMap.put("PARKING_CODE", tplMessageCarPosition.getParkingCode());// 车位代码
				}else if(tplMessageCarPosition.getProcType().equals("D")){
					String alter = tplMessageCarPosition.getCarNum() + "在"+ storeCode + "库分配的车位已撤销!";// 发送消息内容
					bspMap.put("ALTER", alter);
					bspMap.put("PARKING_CODE", "");
				}else if(tplMessageCarPosition.getProcType().equals("U")){
					String alter = tplMessageCarPosition.getCarNum() + "在"+ storeCode + "库分配的车位已修改为"+ tplMessageCarPosition.getParkingCode() + "!";// 发送消息内容
					bspMap.put("ALTER", alter);
					bspMap.put("PARKING_CODE", tplMessageCarPosition.getParkingCode());// 车位代码
				}*/
				if(tplMessageCarPosition.getProcType().equals("I") || tplMessageCarPosition.getProcType().equals("U")){
					bspMap.put("PARKING_CODE", tplMessageCarPosition.getParkingCode());// 车位代码
				}else if(tplMessageCarPosition.getProcType().equals("D")){
					bspMap.put("PARKING_CODE", "");
				}
				System.out.println("执行消息推送");
				String result = JpushUtil.push(bspMap);
				System.out.println(result);
				
//				JSONObject resData = JSONObject.fromObject(result);
//				System.out.println("resData:"+resData);
//				System.out.println("极光推送返回结果：" + resData);
//				if (resData.containsKey("error")) {
//					System.out.println("针对别名为" + alias + "的信息推送失败！");
//					JSONObject error = JSONObject.fromObject(resData
//							.get("error"));
//					System.out.println("错误信息为："
//							+ error.get("message").toString());
//				} else {
//					System.out.println("针对别名为" + alias + "的信息推送成功！");
//				}
			}
		}
		System.out.println("结束");
	}
	
	//自提车离库实绩[XLXZ46]==>>离库时更新车次计划等信息
	public void receiveTplCarLeave(TplMessageCarLeave tplMessageCarLeave) throws Exception {
		System.out.println("自提车离库实绩");
		String message = "";
		Date date = new Date();
		tplMessageCarLeave.setTplMessageCarLeaveItemSet(BeanConvertUtils.strToBean(tplMessageCarLeave.getDetail(), CLASS_SINCE_TPL_MESSAGE_CAR_LEAVE_ITEM));
		// 处理空model
		Iterator it = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
		while (it.hasNext()) {
			TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) it.next();
			if ("".equals(tplMessageCarLeaveItem.getPackNo())) {
				it.remove();
			}
		}
		
		//接收电文表新增
		tplMessageCarLeave.setCreateDate(date);
		messageDao.insert(tplMessageCarLeave, TplMessageCarLeave.class);
		if(tplMessageCarLeave.getTplMessageCarLeaveItemSet()!=null &&tplMessageCarLeave.getTplMessageCarLeaveItemSet().size()>0){
			Iterator itDouble = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
			if(itDouble != null){
				while(itDouble.hasNext()){
					TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) itDouble.next();
					tplMessageCarLeaveItem.setSinceCarLeaveId(tplMessageCarLeave.getId());
					messageDao.insert(tplMessageCarLeaveItem, TplMessageCarLeaveItem.class);
				}
			}
		}
		
		//如果是新增判断重复
		if(tplMessageCarLeave.getProcType().equals("I")){
			it = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
			while (it.hasNext()) {
				TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) it.next();
				if(!"".equals(tplMessageCarLeaveItem.getPackNo())){
					String  queryHql = "select tt from TplCarLeave as t,TplCarLeaveItem as tt where 1=1 and t.id =tt.sinceCarLeaveId and t.storeCode = '"+tplMessageCarLeave.getStoreCode()+"' and t.seqId = '"+tplMessageCarLeave.getSeqId()+"' and tt.packNo='"+tplMessageCarLeaveItem.getPackNo()+"'";
					List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
					if (list != null && list.size()>0){
						message+=tplMessageCarLeaveItem.getPackNo()+"、";
					}
				}
			}
			if(null !=message && !message.trim().equals("")){
				TplMessageLog tplMessageLog= new TplMessageLog();
				tplMessageLog.setErrTitle("自提车离库实绩");
				tplMessageLog.setErrContent("存在重复离库的材料:"+message.trim()+",车次号为:"+tplMessageCarLeave.getSeqId()+" 仓库号为: "+tplMessageCarLeave.getStoreCode());
				tplMessageLog.setCreateDate(date);
				messageDao.insert(tplMessageLog, TplMessageLog.class);
				return;
			}
		}
		
		
		TplTransSeq tplTransSeq = new TplTransSeq();//车次信息
		List listTplTransPack = new ArrayList();//捆包材料信息
		List listIds = new ArrayList();//离库实绩捆包材料id
		Long [] ids = new Long[tplMessageCarLeave.getTplMessageCarLeaveItemSet().size()];
		//查询出运输此车TplTransSeq
		String queryTplTranSeq = " from TplTransSeq t where t.id = "+Long.valueOf(tplMessageCarLeave.getSeqId());
		List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
		if(tplTransSeqList!=null && tplTransSeqList.size()>0){
			tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
			//根据出库实绩的材料号查询查询出运输作业计划捆包信息
			Iterator itDouble = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
			if(itDouble != null ){
				while(itDouble.hasNext()){
					TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) itDouble.next();
					String queryTplTransPack = "select t3 from TplTransPlan t,TplTransTruckDisPlan t2,TplTransPack t3 where t.billId = t2.billId and t.id=t3.planId and t2.trackNo='"+tplTransSeq.getId()+"' and t3.packId ='"+tplMessageCarLeaveItem.getPackNo()+"'";
					System.out.println("queryTplTransPack:::"+queryTplTransPack);
					List list =messageDao.queryTplQualityDefectConfigByHql(queryTplTransPack);
					if(null!=list && list.size()>0){
						listTplTransPack.add(list.get(0));
						TplTransPack tplTransPack = (TplTransPack) list.get(0);
						listIds.add(tplTransPack.getId());
					}
				}
				//获取离库实际 pack捆包信息Id
				for(int i=0;i<listIds.size();i++){
					ids[i] = Long.valueOf((listIds.get(i).toString()));
				}
			}
		}
		
		if(tplMessageCarLeave.getProcType().equals("I")){//新增
			//新增自提车离库实绩业务主信息
			TplCarLeave tplCarLeave = new TplCarLeave();
			tplCarLeave.setCarPickNo(tplMessageCarLeave.getCarPickNo());
			tplCarLeave.setCarNum(tplMessageCarLeave.getCarNum());
			tplCarLeave.setTrailerNum(tplMessageCarLeave.getTrailerNum());
			tplCarLeave.setSeqId(tplMessageCarLeave.getSeqId());
			tplCarLeave.setStoreCode(tplMessageCarLeave.getStoreCode());
			tplCarLeave.setParkingCode(tplMessageCarLeave.getParkingCode());
			tplCarLeave.setLeaveTime(DateUtils.toDate(tplMessageCarLeave.getLeaveTime(), SystemConstants.dateformat6));
			tplCarLeave.setCreateDate(date);
			messageDao.insert(tplCarLeave,TplCarLeave.class);
			//新增自提车离库实绩业务子信息
			if(tplMessageCarLeave.getTplMessageCarLeaveItemSet()!=null && tplMessageCarLeave.getTplMessageCarLeaveItemSet().size()>0){
				Iterator itDouble = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
				if(itDouble != null ){
					while (itDouble.hasNext()) {
						TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) itDouble.next();
						TplCarLeaveItem  tplCarLeaveItem = new TplCarLeaveItem();
						tplCarLeaveItem.setPackNo(tplMessageCarLeaveItem.getPackNo());
						tplCarLeaveItem.setSinceCarLeaveId(tplCarLeave.getId());
						messageDao.insert(tplCarLeaveItem,TplCarLeaveItem.class);
					}
				}
			}
			//更新配置材料失效
			messageDao.updateTplCarPlan(tplMessageCarLeave.getSeqId(),tplMessageCarLeave.getStoreCode());
		}/*else if(tplMessageCarLeave.getProcType().equals("D")){//删除
			//删除自提车离库实绩业务主信息
			//业务表
			String queryIdSql = "select id from TPL_CAR_LEAVE where SEQ_ID = '"
				+tplMessageCarLeave.getSeqId()+"' and STORE_CODE = '"
				+tplMessageCarLeave.getStoreCode()+"' order by ID DESC";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id!=null){
				Long [] ids02 = {id};
				messageDao.delete(ids02, TplCarLeave.class);
				//删除自提车离库实绩业务子信息
				String deleteTplCarPlanItem = "delete from TPL_CAR_LEAVE_ITEM t where t.SINCE_CAR_LEAVE_ID = " + id ;
				messageDao.deleteSql(deleteTplCarPlanItem);
			}
			
			//修改入库实际的车次的状态
			if(tplTransSeq.getId()!=null){
				tplTransSeq.setStatus("0");
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
			//修改捆包状态
			for(int i =0;i<listTplTransPack.size();i++){
				TplTransPack tplTransPack = (TplTransPack) listTplTransPack.get(i);
				tplTransPack.setPackStatus("0");
				tplTransPack.setSeqId(null);
				tplTransPack.setControlId(null);
				tplTransPack.setProStatus("20");
				messageDao.update(tplTransPack, TplTransPack.class);
			}
			List listTransPlanId = messageDao.queryTransPlanId(ids);
			Double actGrossWeight = new Double("0");
			Double actNetWeight = new Double("0");
			Long actCount = new Long("0");
			for(int i=0;i<listTransPlanId.size();i++){
				Object []  object = (Object[]) listTransPlanId.get(0);
				String transPlanId = object[0].toString();
				List list03 = messageDao.tplTransPlanCount(transPlanId);
				if(null!=list03 && list03.size()>0){
					Object [] obj = (Object[]) list03.get(0);
					actGrossWeight =new Double(obj[0].toString());
					actNetWeight = new Double(obj[1].toString());
					actCount =  new Long(obj[2].toString());
				}
				//修改运输作业计划主表
				TplTransPlan tplTransPlan = new TplTransPlan();
				tplTransPlan.setStatus("0");
				tplTransPlan.setSeqId(null);
				tplTransPlan.setTransPlanId(transPlanId);
				tplTransPlan.setActGrossWeight(actGrossWeight);
				tplTransPlan.setActNetWeight(actNetWeight);
				tplTransPlan.setActCount(actCount);
				messageDao.updateTplTransPlan(tplTransPlan);
				//修改汽运调度计划表状态
				TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
				tplTransTruckDisPlan.setStatus("00");
				tplTransTruckDisPlan.setActGrossWeight(actGrossWeight);
				tplTransTruckDisPlan.setActNetWeight(actNetWeight);
				tplTransTruckDisPlan.setActCount(actCount);
				tplTransTruckDisPlan.setTransPlanId(transPlanId);
				messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);
			}
		}*/
		else if(tplMessageCarLeave.getProcType().equals("U")){//修改
			//修改自提车离库实绩业务主信息
			System.out.println("更新业务表信息：：：");
			String queryTplCarPositionHql = " from TplCarLeave as t where 1=1 and t.seqId = '"+tplMessageCarLeave.getSeqId()+"' and t.storeCode = '"+tplMessageCarLeave.getStoreCode()+"'";
			System.out.println("queryTplCarPositionHql:::"+queryTplCarPositionHql);
			TplCarLeave  tplCarLeave;
			List list = messageDao.queryTplQualityDefectConfigByHql(queryTplCarPositionHql);
			if(list!=null && list.size()>0){
				tplCarLeave = (TplCarLeave)list.get(0);
				if(tplCarLeave!=null && tplCarLeave.getId()!=null){
					tplCarLeave.setCarNum(tplMessageCarLeave.getCarNum());
					tplCarLeave.setCarPickNo(tplMessageCarLeave.getCarPickNo());
					tplCarLeave.setTrailerNum(tplMessageCarLeave.getTrailerNum());
					tplCarLeave.setSeqId(tplMessageCarLeave.getSeqId());
					tplCarLeave.setStoreCode(tplMessageCarLeave.getStoreCode());
					tplCarLeave.setParkingCode(tplMessageCarLeave.getParkingCode());
					tplCarLeave.setLeaveTime(DateUtils.toDate(tplMessageCarLeave.getLeaveTime(), SystemConstants.dateformat6));
					messageDao.update(tplCarLeave, TplCarLeave.class);
				}
				//修改自提车离库实绩业务子信息
				if(tplMessageCarLeave.getTplMessageCarLeaveItemSet()!=null && tplMessageCarLeave.getTplMessageCarLeaveItemSet().size()>0){
					//删除自提车离库实绩业务子信息
					String deleteTplCarPlanItem = "delete from TPL_CAR_LEAVE_ITEM t where t.SINCE_CAR_LEAVE_ID = " + tplCarLeave.getId() ;
					System.out.println("deleteTplCarPlanItem:::"+deleteTplCarPlanItem);
					messageDao.deleteSql(deleteTplCarPlanItem);
					//新增子业务
					Iterator itDouble = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
					if(itDouble != null ){
						while (itDouble.hasNext()) {
							TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) itDouble.next();
							TplCarLeaveItem  tplCarLeaveItem = new TplCarLeaveItem();
							tplCarLeaveItem.setPackNo(tplMessageCarLeaveItem.getPackNo());
							tplCarLeaveItem.setSinceCarLeaveId(tplCarLeave.getId());
							messageDao.insert(tplCarLeaveItem,TplCarLeaveItem.class);
						}
					}
				}
			}
		}
		
		if(tplMessageCarLeave.getProcType().equals("I") || tplMessageCarLeave.getProcType().equals("U")){
			DecimalFormat df  = new DecimalFormat("0.000000");
			BigDecimal actGrossWeight = new BigDecimal("0");//实际装车毛重
			BigDecimal actNetWeight = new BigDecimal("0");//实际装车净重
			Long actCount = new Long(0);//实际装车件数
			if (tplTransSeq.getId() != null) {

				Long seqId = tplTransSeq.getId();
				//先拿到planId
				List list1 = new ArrayList();
				list1 = messageDao.queryPackSumBySeqId(seqId);
				// 移除接收、修改配载计划时的装车数据
				messageDao.updateTransPackForLoadBack(seqId);

				// 根据所选择的材料号查询出汽运调度计划信息，更新捆包信息的调度计划id，及其捆包状态
				for (int i = 0; i < listTplTransPack.size(); i++) {
					TplTransPack tplTransPack = (TplTransPack) listTplTransPack
							.get(i);
					String queryTplTransPlan = "select t from TplTransTruckDisPlan t,TplTransPack t2 where t.transPlanId = t2.transPlanId and t2.id = '"
							+ tplTransPack.getId() + "' and t.trackNo = "+seqId;
					System.out.println("queryTplTransPlan:::" + queryTplTransPlan);
					List listTruckDisPlan = messageDao
							.queryTplQualityDefectConfigByHql(queryTplTransPlan);
					if (null != listTruckDisPlan && listTruckDisPlan.size() > 0) {
						TplTransTruckDisPlan tplTransTruckDisPlan = (TplTransTruckDisPlan) listTruckDisPlan.get(0);
						tplTransPack.setControlId(new Double(tplTransTruckDisPlan.getId().toString()));
					}
					tplTransPack.setPackStatus("1");
					tplTransPack.setProStatus("40");
					tplTransPack.setSeqId(tplTransSeq.getId());
					messageDao.update(tplTransPack, TplTransPack.class);
				}

				// 根据捆包查询出计划号,再根据计划号去更新计划和汽运调度计划信息
//				List listTransPlanId = messageDao.queryTransPlanId(ids);
				List listTransPlanId = messageDao.queryPackSumBySeqId(seqId);
				listTransPlanId.addAll(list1);
				System.out.println("查询出来的计划条数：" + listTransPlanId.size());
				if (listTransPlanId != null && listTransPlanId.size() > 0) {
					for (int i = 0; i < listTransPlanId.size(); i++) {
						TplTransPlan tplTransPlan = new TplTransPlan();
						String transPlanId = (String) listTransPlanId.get(i);
						System.out.println("计划号：：：：：：：：" + transPlanId);
						List list03 = messageDao.tplTransPlanCount(transPlanId);
						if (null != list03 && list03.size() > 0) {
							Object[] obj = (Object[]) list03.get(0);
							actGrossWeight = null == obj[0] ? new BigDecimal("0") : new BigDecimal(obj[0].toString());
							actNetWeight = null == obj[1] ? new BigDecimal("0") : new BigDecimal(obj[1].toString());
							actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
						}
						// 更新计划
						tplTransPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
						tplTransPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
						tplTransPlan.setActCount(actCount);
						tplTransPlan.setStatus("1");
						tplTransPlan.setSeqId(tplTransSeq.getId());
						tplTransPlan.setTransPlanId(transPlanId);
						messageDao.updateTplTransPlan(tplTransPlan);

						// 更新汽运调度计划
						TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
						tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
						tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
						tplTransTruckDisPlan.setActCount(actCount);
						tplTransTruckDisPlan.setStatus("10");
						tplTransTruckDisPlan.setTransPlanId(transPlanId);
						// 根据所指定的计划号去更新所有的汽运调度计划执行量
						messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);
					}
				}
				// 根据出库实绩材料信息查询出库的实绩毛重、净重、件数，然后更新到此运输车次中，且修改此车次为运输中
				List list = messageDao.tplTransSeqLeaveCount(tplTransSeq
						.getId().toString());
				Object[] obj = (Object[]) list.get(0);
				actGrossWeight = null == obj[0] ? new BigDecimal("0") : new BigDecimal(df.format(obj[0]));
				actNetWeight = null == obj[1] ? new BigDecimal("0") : new BigDecimal(df.format(obj[1]));
				actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
				tplTransSeq.setStatus("1");
				tplTransSeq.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
				tplTransSeq.setActNetWeight(Double.valueOf(actNetWeight.toString()));
				tplTransSeq.setActCount(actCount);// 实际件数
				tplTransSeq.setPlanGrossWeight(Double.valueOf(actGrossWeight.toString()));
				tplTransSeq.setPlanNetWeight(Double.valueOf(actNetWeight.toString()));
				tplTransSeq.setPlanCount(actCount);// 计划件数
				tplTransSeq.setProStatus("10");// 发车状态
				tplTransSeq.setRealStartDate(date);//发车时间
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
		}
		System.out.println("结束");
	}
	
	//自提车进出厂信息[XLXZ47]==>>进场时不可以进行材料挑选
	public void receiveTplCarInout(TplMessageCarInout tplMessageCarInout) throws Exception {
		Date date = new Date();
		Long carPlanId = null;
		System.out.println("自提车进出厂");
		//接收电文表
		System.out.println("新增电文表信息：：");
		tplMessageCarInout.setCreateDate(date);
		messageDao.insert(tplMessageCarInout, TplMessageCarInout.class);
		
		String queryTplCarPlanHql = " from TplCarPlan as t where 1=1 and t.seqId = '"+tplMessageCarInout.getSeqId()+"'";
		String queryTplTransSeqHql = " from TplTransSeq as t where 1=1 and t.id = "+tplMessageCarInout.getSeqId();
		System.out.println("queryTplCarPlanHql:::"+queryTplCarPlanHql);
		System.out.println("queryTplTransSeqHql:::"+queryTplTransSeqHql);
		List tplCarPlanList = messageDao.queryTplQualityDefectConfigByHql(queryTplCarPlanHql);
		List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTransSeqHql);
		TplCarPlan tplCarPlan = new TplCarPlan();
		TplTransSeq tplTransSeq = new TplTransSeq();
		if(null !=tplCarPlanList && tplCarPlanList.size()>0){
			tplCarPlan = (TplCarPlan) tplCarPlanList.get(0);
		}
		if(null!=tplTransSeqList && tplTransSeqList.size()>0){
			tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
		}
		if(tplTransSeq.getStatus().equals("2")){
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setErrTitle("进出厂电文接收失败");
			tplMessageLog.setErrContent("车次号:"+tplTransSeq.getId()+"已结案");
			tplMessageLog.setOperMemo("XLXZ47");
			tplMessageLog.setCreateDate(new Date());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
			return;
		}
		if(tplMessageCarInout.getProcType().equals("I")){//新增
			System.out.println("新增业务表信息：：：");
			TplCarInout  tplCarInout = new TplCarInout();
			tplCarInout.setCarNum(tplMessageCarInout.getCarNum());
			tplCarInout.setSeqId(tplMessageCarInout.getSeqId());
			tplCarInout.setInoutFlag(tplMessageCarInout.getInoutFlag());
			tplCarInout.setDoorName(tplMessageCarInout.getDoorName());
			tplCarInout.setExecuteTime(tplMessageCarInout.getExecuteTime());
			tplCarInout.setCreateDate(date);
			messageDao.insert(tplCarInout, TplCarInout.class);
			if(tplMessageCarInout.getInoutFlag().equals("0")){//进场
				//修改材料挑选的状态
				if(tplCarPlan.getId()!=null){
					tplCarPlan.settStatus("1");
					messageDao.update(tplCarPlan, TplCarPlan.class);
				}
				//更新车次表Pro_struts
				if(tplTransSeq.getId()!=null){
					tplTransSeq.setProStatus("05");
					messageDao.update(tplTransSeq, TplTransSeq.class);
				}
			}else if(tplMessageCarInout.getInoutFlag().equals("1")){
				//更新车次表Pro_struts
				if(tplTransSeq.getId()!=null){
					tplTransSeq.setProStatus("07");
					messageDao.update(tplTransSeq, TplTransSeq.class);
				}
				
				if(tplCarPlan.getId()!=null){
					carPlanId = tplCarPlan.getId();
					String updateSql = " update tpl_car_plan_item t set t.struts = '1' where t.car_plan_id = "+carPlanId;
					messageDao.executeUpdateSql(updateSql);
				}
			}
		}else if(tplMessageCarInout.getProcType().equals("D")){//删除
			System.out.println("删除业务表信息：：：");
			String queryIdSql = "select id from TPL_CAR_INOUT where CAR_NUM = '"
				+tplMessageCarInout.getCarNum()+"' order by ID DESC";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id != null){
				Long [] ids = {id};
				messageDao.delete(ids, TplCarInout.class);
			}
			if(tplMessageCarInout.getInoutFlag().equals("0")){//进场删除
				//修改材料挑选的状态
				if(tplCarPlan.getId()!=null){
					tplCarPlan.settStatus("0");
					messageDao.update(tplCarPlan, TplCarPlan.class);
				}
				//更新车次表Pro_struts
				if(tplTransSeq.getId()!=null){
					tplTransSeq.setProStatus("");
					messageDao.update(tplTransSeq, TplTransSeq.class);
				}
			}
		}else if(tplMessageCarInout.getProcType().equals("U")){//修改
			System.out.println("更新业务表信息：：：");
			String queryTplCarPositionHql = " from TplCarInout as t where 1=1 and t.carNum = '"+tplMessageCarInout.getCarNum()+"'";
			System.out.println("queryTplCarPositionHql:::"+queryTplCarPositionHql);
			TplCarInout  tplCarInout;
			List list = messageDao.queryTplQualityDefectConfigByHql(queryTplCarPositionHql);
			if(list!=null && list.size()>0){
				tplCarInout = (TplCarInout)list.get(0);
				if(tplCarInout!=null){
					tplCarInout.setCarNum(tplMessageCarInout.getCarNum());
					tplCarInout.setSeqId(tplMessageCarInout.getSeqId());
					tplCarInout.setInoutFlag(tplMessageCarInout.getInoutFlag());
					tplCarInout.setDoorName(tplMessageCarInout.getDoorName());
					tplCarInout.setExecuteTime(tplMessageCarInout.getExecuteTime());
					messageDao.update(tplCarInout, TplCarInout.class);
				}
			}
		}
		System.out.println("结束");
	}
	
	//库区排队信息[XLXZ49]
	public void receiveTplKqList(TplMessageKqList tplMessageKqList) throws Exception {
		Date date = new Date();
		System.out.println("库区排队");
		if(tplMessageKqList.getDetail().startsWith("000")){
			//业务上会发循环为空的电文
			//发现这样的电文取出detail的时候会把前后的空格处理掉，导致解析报错
			tplMessageKqList.setDetail("          "+tplMessageKqList.getDetail());
		}
		tplMessageKqList.setTplMessageKqListItemSet(BeanConvertUtils.strToBean(tplMessageKqList.getDetail(), CLASS_SINCE_TPL_MESSAGE_KQ_LIST_ITEM));
		// 处理空model
		Iterator it = tplMessageKqList.getTplMessageKqListItemSet().iterator();
		if(it!=null){
			while (it.hasNext()) {
				TplMessageKqListItem tplMessageKqListItem = (TplMessageKqListItem) it.next();
				if ("".equals(tplMessageKqListItem.getCarNum())) {
					it.remove();
				}
			}
		}
		//接收电文表新增
		tplMessageKqList.setCreateDate(date);
		messageDao.insert(tplMessageKqList, TplMessageKqList.class);
		
		if(tplMessageKqList.getTplMessageKqListItemSet()!=null && tplMessageKqList.getTplMessageKqListItemSet().size()>0){
			Iterator itDouble = tplMessageKqList.getTplMessageKqListItemSet().iterator();
			if(itDouble != null ){
				while(itDouble.hasNext()){
					TplMessageKqListItem tplMessageKqListItem = (TplMessageKqListItem) itDouble.next();
					tplMessageKqListItem.setKqListId(tplMessageKqList.getId());
					messageDao.insert(tplMessageKqListItem, TplMessageKqListItem.class);
				}
			}
		}
		String queryHql = " from TplKqList where storeCode = '"+tplMessageKqList.getStoreCode()+"' and laneNo = '"+tplMessageKqList.getLaneNo()+"'";
		System.out.println("queryHql:::"+queryHql);
		List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
		TplKqList tplKqList = new TplKqList();
		if(list==null || list.size()==0){//主信息不存在，新增业务表主信息
			tplKqList.setStoreCode(tplMessageKqList.getStoreCode());
			tplKqList.setLaneNo(tplMessageKqList.getLaneNo());
			tplKqList.setCreateDate(date);
			messageDao.insert(tplKqList, TplKqList.class);
		}else{//主信息已经存在，删除原有子信息
			tplKqList = (TplKqList) list.get(0);
			String deleteTplKqListItem = "delete from Tpl_Kq_List_item t where KQ_LIST_ID = " +tplKqList.getId();
			System.out.println("deleteTplKqListItem:::"+deleteTplKqListItem);
			messageDao.deleteSql(deleteTplKqListItem);
		}
		
		//新增业务表子信息
		if(tplMessageKqList.getTplMessageKqListItemSet()!=null && tplMessageKqList.getTplMessageKqListItemSet().size()>0){
			Iterator itDouble = tplMessageKqList.getTplMessageKqListItemSet().iterator();
			if(itDouble != null ){
				while(itDouble.hasNext()){
					TplMessageKqListItem tplMessageKqListItem = (TplMessageKqListItem) itDouble.next();
					TplKqListItem tplKqListItem = new TplKqListItem();
					tplKqListItem.setCarNum(tplMessageKqListItem.getCarNum());
					tplKqListItem.setSeqId(tplMessageKqListItem.getSeqId());
					tplKqListItem.setCarPickNo(tplMessageKqListItem.getCarPickNo());
					tplKqListItem.setKqListId(tplKqList.getId());
					messageDao.insert(tplKqListItem, TplKqListItem.class);
				}
			}
		}
		System.out.println("结束");
	}

	/**
	 *要货计划（自提转水运）[XLXZ40]
	 */
	public void receiveTplMDeliveryPlan(TplMDeliveryPlan tplMDeliveryPlan)
			throws Exception {
		//判断重复
		if(tplMDeliveryPlan.getProcType().equals("I")){
			System.out.println("要货计划（自提转水运）业务表信息是否已经存在,存在不执行新增");
			String  queryHql = " from TplDeliveryPlan as t where 1=1 and t.billOfLadingNo = '"+tplMDeliveryPlan.getBillOfLadingNo()+"' and t.custMatNo = '"+tplMDeliveryPlan.getCustMatNo()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				return;
			}
		}
		//接收电文表新增
		Date date = new Date();
		System.out.println("要货计划（自提转水运）电文表新增");
		tplMDeliveryPlan.setCreateDate(date);
		messageDao.insert(tplMDeliveryPlan, TplMDeliveryPlan.class);
		//操作业务表
		if(tplMDeliveryPlan.getProcType().equals("I")){
			TplDeliveryPlan tplDeliveryPlan = new TplDeliveryPlan();
			tplDeliveryPlan.setCreateDate(date);
			tplDeliveryPlan.setBillOfLadingNo(tplMDeliveryPlan.getBillOfLadingNo());
			tplDeliveryPlan.setCustMatNo(tplMDeliveryPlan.getCustMatNo());
			System.out.println("要货计划（自提转水运）业务文表新增");
			messageDao.insert(tplDeliveryPlan, TplDeliveryPlan.class);
		}else if(tplMDeliveryPlan.getProcType().equals("D")){
			//业务表
			String queryIdSql = "select id from TPL_DELIVERY_PLAN where BILL_OF_LADING_NO = '"
				+tplMDeliveryPlan.getBillOfLadingNo()+"' and CUST_MAT_NO = '"+tplMDeliveryPlan.getCustMatNo()+"' order by ID DESC";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id!=null){
				Long [] ids = {id};
				messageDao.delete(ids, TplCarPlan.class);
			}
		}
	}

	/**
	 * 出库队列（装载计划）[XLXZ41]
	 */
	public void receiveTplMOutList(TplMOutList tplMOutList) throws Exception {
		Date date = new Date();
		System.out.println("出库队列（装载计划）,创建时间：");
		tplMOutList.setTplMOutListItemSet(BeanConvertUtils.strToBean(tplMOutList.getDetail(), CLASS_SINCE_TPL_M_OUT_LIST_ITEM));
		// 处理空model
		Iterator it = tplMOutList.getTplMOutListItemSet().iterator();
		while (it.hasNext()) {
			TplMOutListItem tplMOutListItem = (TplMOutListItem) it.next();
			if ("".equals(tplMOutListItem.getCustMatNo())) {
				it.remove();
			}
		}
		System.out.println("操作标记:"+tplMOutList.getProcType());
		//如果是新增判断重复
		if(tplMOutList.getProcType().trim().equals("I")){
		/*	Iterator itDouble = tplMOutList.getTplMOutListItemSet().iterator();
			//判断队列任务号和材料号信息是否存在，如果存在就结束程序
			if(itDouble != null ){
			while(itDouble.hasNext()){
				TplMOutListItem tplMOutListItem = (TplMOutListItem) itDouble.next();
				String  queryHql = " from TplOutList as t,TplMOutListItem as tt where 1=1 and t.id = tt.outListId and t.queueTaskNo = '"+tplMOutList.getQueueTaskNo()+"' and tt.custMatNo='"+tplMOutListItem.getCustMatNo()+"'";
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size()>0){
					return;
				}
				}
			}*/
		
			//如果是新增判断重
			if(tplMOutList.getProcType().equals("I")){
				String  queryHql = " from TplOutList as t where 1=1 and t.queueTaskNo = '"+tplMOutList.getQueueTaskNo()+"'";
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size()>0){
						return;
				}
			}
		}
		
		//接收电文表新增
		tplMOutList.setCreateDate(date);
		messageDao.insert(tplMOutList, TplMOutList.class);
		if(tplMOutList.getTplMOutListItemSet()!=null && tplMOutList.getTplMOutListItemSet().size()>0){
			Iterator itDouble = tplMOutList.getTplMOutListItemSet().iterator();
			if(itDouble != null ){
				while(itDouble.hasNext()){
					TplMOutListItem tplMOutListItem = (TplMOutListItem) itDouble.next();
					tplMOutListItem.setOutListId(tplMOutList.getId());
					messageDao.insert(tplMOutListItem, TplMOutListItem.class);
				}
			}
		}
		
		//操作业务表
		if(tplMOutList.getProcType().equals("I")){
			TplOutList tplOutList = new TplOutList();
			tplOutList.setQueueNo(tplMOutList.getQueueNo());
			tplOutList.setQueueTaskNo(tplMOutList.getQueueTaskNo());
			tplOutList.setTaskSeqNo(tplMOutList.getTaskSeqNo());
			tplOutList.setMatScopeNo(tplMOutList.getMatScopeNo());
			tplOutList.setNum(tplMOutList.getNum());
			tplOutList.setSourceStoreCode(tplMOutList.getSourceStoreCode());
			tplOutList.setDestAddr(tplMOutList.getDestAddr());
			tplOutList.setStartTime(DateUtils.toDate(tplMOutList.getStartTime(), SystemConstants.dateformat6));
			tplOutList.setEndTime(DateUtils.toDate(tplMOutList.getEndTime(), SystemConstants.dateformat6));
			tplOutList.setFrameNeedTime(DateUtils.toDate(tplMOutList.getFrameNeedTime(), SystemConstants.dateformat6));
			tplOutList.setPlanOutTime(DateUtils.toDate(tplMOutList.getPlanOutTime(), SystemConstants.dateformat6));
			tplOutList.setRequirePlanNo(tplMOutList.getRequirePlanNo());
			tplOutList.setLotNo(tplMOutList.getLotNo());
			tplOutList.setCreateDate(date);
			messageDao.insert(tplOutList, TplOutList.class);
			if(tplMOutList.getTplMOutListItemSet()!=null && tplMOutList.getTplMOutListItemSet().size()>0){
				Iterator itDouble = tplMOutList.getTplMOutListItemSet().iterator();
				if(itDouble != null ){
					while(itDouble.hasNext()){
						TplMOutListItem tplMOutListItem = (TplMOutListItem) itDouble.next();
						TplOutListItem tplOutListItem = new TplOutListItem();
						tplOutListItem.setCustMatNo(tplMOutListItem.getCustMatNo());
						tplOutListItem.setBillOfLadingNo(tplMOutListItem.getBillOfLadingNo());
						tplOutListItem.setPickLotNo(tplMOutListItem.getPickLotNo());
						tplOutListItem.setOrderNo(tplMOutListItem.getOrderNo());
						tplOutListItem.setOutListId(tplOutList.getId());
						messageDao.insert(tplOutListItem, TplOutListItem.class);
					}
				}
			}
		}if(tplMOutList.getProcType().equals("U")){
			String  queryHql = " from TplOutList as t where 1=1 and t.queueTaskNo = '"+tplMOutList.getQueueTaskNo()+"'";
			System.out.println("***queryHql:"+queryHql);
			TplOutList tplOutList;
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				tplOutList = (TplOutList) list.get(0);
				if(tplOutList!=null){
					tplOutList.setQueueNo(tplMOutList.getQueueNo());
					tplOutList.setQueueTaskNo(tplMOutList.getQueueTaskNo());
					tplOutList.setTaskSeqNo(tplMOutList.getTaskSeqNo());
					tplOutList.setMatScopeNo(tplMOutList.getMatScopeNo());
					tplOutList.setNum(tplMOutList.getNum());
					tplOutList.setSourceStoreCode(tplMOutList.getSourceStoreCode());
					tplOutList.setDestAddr(tplMOutList.getDestAddr());
					tplOutList.setStartTime(DateUtils.toDate(tplMOutList.getStartTime(), SystemConstants.dateformat6));
					tplOutList.setEndTime(DateUtils.toDate(tplMOutList.getEndTime(), SystemConstants.dateformat6));
					tplOutList.setFrameNeedTime(DateUtils.toDate(tplMOutList.getFrameNeedTime(), SystemConstants.dateformat6));
					tplOutList.setPlanOutTime(DateUtils.toDate(tplMOutList.getPlanOutTime(), SystemConstants.dateformat6));
					tplOutList.setRequirePlanNo(tplMOutList.getRequirePlanNo());
					tplOutList.setLotNo(tplMOutList.getLotNo());
					messageDao.update(tplOutList, TplOutList.class);
				}
				if(tplMOutList.getTplMOutListItemSet()!=null && tplMOutList.getTplMOutListItemSet().size()>0&&tplOutList!=null){
					Iterator itDouble = tplMOutList.getTplMOutListItemSet().iterator();
					if(itDouble != null ){
						while(itDouble.hasNext()){
							TplMOutListItem tplMOutListItem = (TplMOutListItem) itDouble.next();
							String  queryItemHql = " from TplOutListItem as t where 1=1 and t.custMatNo = '"+tplMOutListItem.getCustMatNo()
							+" '  and t.outListId = '"+tplOutList.getId();
							List listItem = messageDao.queryTplQualityDefectConfigByHql(queryItemHql);
							//存在就更新，否则新增
							if(listItem!=null && listItem.size()>0){
								TplOutListItem tplOutListItem = (TplOutListItem) listItem.get(0);
								tplOutListItem.setCustMatNo(tplMOutListItem.getCustMatNo());
								tplOutListItem.setBillOfLadingNo(tplMOutListItem.getBillOfLadingNo());
								tplOutListItem.setPickLotNo(tplMOutListItem.getPickLotNo());
								tplOutListItem.setOrderNo(tplMOutListItem.getOrderNo());
								messageDao.update(tplOutListItem, TplOutListItem.class);
							}else{
								TplOutListItem tplOutListItem = new TplOutListItem();
								tplOutListItem.setCustMatNo(tplMOutListItem.getCustMatNo());
								tplOutListItem.setBillOfLadingNo(tplMOutListItem.getBillOfLadingNo());
								tplOutListItem.setPickLotNo(tplMOutListItem.getPickLotNo());
								tplOutListItem.setOrderNo(tplMOutListItem.getOrderNo());
								tplOutListItem.setOutListId(tplOutList.getId());
								messageDao.insert(tplOutListItem, TplOutListItem.class);
							}
						}
					}
				}
			}
		}if(tplMOutList.getProcType().equals("D")){
			String queryIdSql = "select id from TPL_OUT_LIST where QUEUE_TASK_NO = '"
				+tplMOutList.getQueueNo()+"' order by ID DESC";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id!=null){
				Long [] ids = {id};
				messageDao.delete(ids, TplCarPlan.class);
				String deleteTplOutList = "delete from TPL_OUT_LIST_ITEM t where OUT_LIST_ID = " +id;
				System.out.println("queryIdSql:::"+deleteTplOutList);
				messageDao.deleteSql(deleteTplOutList);
			}
		}
		System.out.println("结束");
	}

	/**
	 *行车检修信息[XLXZ42]
	 */
	public void receiveTplMOverhaulInfo(TplMOverhaulInfo tplMOverhaulInfo)
			throws Exception {
		System.out.println("行车检修");
		Date date  = new Date();
		//如果是新增判断重复
		if(tplMOverhaulInfo.getProcType().equals("I")){
			System.out.println(DateUtils.toDate(tplMOverhaulInfo.getOvhPlanStartTime(),SystemConstants.dateformat6));
			String  queryHql = " from TplOverhaulInfo as t where 1=1 and t.stockCode = '"+tplMOverhaulInfo.getStockCode()+"' and t.craneId = '"+tplMOverhaulInfo.getCraneId()
						+"' and t.ovhPlanStartTime <= to_date('" + tplMOverhaulInfo.getOvhPlanStartTime() +"','yyyy/MM/dd HH24:MI:SS')"
						+" and t.ovhPlanEndTime >=  to_date('" + tplMOverhaulInfo.getOvhPlanEndTime() +"','yyyy/MM/dd HH24:MI:SS')";
			List list =messageDao.queryTplQualityDefectConfigByHql(queryHql) ;
			if (list != null && list.size()>0){
				return;
			}
		}
		//接收电文表
		tplMOverhaulInfo.setCreateDate(date);
		messageDao.insert(tplMOverhaulInfo, TplMOverhaulInfo.class);
		if(tplMOverhaulInfo.getProcType().equals("I")){//新增
			System.out.println("新增业务表信息：：：");
			TplOverhaulInfo  tplOverhaulInfo = new TplOverhaulInfo();
			tplOverhaulInfo.setStockCode(tplMOverhaulInfo.getStockCode());
			tplOverhaulInfo.setStockName(tplMOverhaulInfo.getStockName());
			tplOverhaulInfo.setCraneId(tplMOverhaulInfo.getCraneId());
			tplOverhaulInfo.setOvhType(tplMOverhaulInfo.getOvhType());
			tplOverhaulInfo.setOvhStartTime(DateUtils.toDate(tplMOverhaulInfo.getOvhStartTime(), SystemConstants.dateformat6));
			tplOverhaulInfo.setOvhEndTime(DateUtils.toDate(tplMOverhaulInfo.getOvhEndTime(), SystemConstants.dateformat6));
			tplOverhaulInfo.setOvhPlanStartTime(DateUtils.toDate(tplMOverhaulInfo.getOvhPlanStartTime(), SystemConstants.dateformat6));
			tplOverhaulInfo.setOvhPlanEndTime(DateUtils.toDate(tplMOverhaulInfo.getOvhPlanEndTime(), SystemConstants.dateformat6));
			tplOverhaulInfo.setOvhDesc(tplMOverhaulInfo.getOvhDesc());
			tplOverhaulInfo.setCreateDate(date);
			messageDao.insert(tplOverhaulInfo, TplOverhaulInfo.class);
		}else if(tplMOverhaulInfo.getProcType().equals("D")){//删除
			System.out.println("删除业务表信息：：：");
			String  queryHql = " from TplOverhaulInfo as t where 1=1 and t.stockCode = '"+tplMOverhaulInfo.getStockCode()+"' and t.craneId = '"+tplMOverhaulInfo.getCraneId()
				+"' and t.ovhPlanStartTime <= to_date('" + tplMOverhaulInfo.getOvhPlanStartTime() +"','yyyy/MM/dd HH24:MI:SS')"
				+" and t.ovhPlanEndTime >=  to_date('" + tplMOverhaulInfo.getOvhPlanEndTime() +"','yyyy/MM/dd HH24:MI:SS')";
			List list =messageDao.queryTplQualityDefectConfigByHql(queryHql) ;
			System.out.println("queryIdSql:::"+queryHql);
			if(list != null && list.size()>0){
				TplOverhaulInfo  tplOverhaulInfo = (TplOverhaulInfo)list.get(0);
				Long [] ids = {tplOverhaulInfo.getId()};
				messageDao.delete(ids, TplOverhaulInfo.class);
			}
		}else if(tplMOverhaulInfo.getProcType().equals("U")){//修改
			System.out.println("更新业务表信息：：：");
			String  queryTplCarPositionHql = " from TplOverhaulInfo as t where 1=1 and t.stockCode = '"+tplMOverhaulInfo.getStockCode()+"' and t.craneId = '"+tplMOverhaulInfo.getCraneId()
				+"' and t.ovhPlanStartTime <= to_date('" + tplMOverhaulInfo.getOvhPlanStartTime() +"','yyyy/MM/dd HH24:MI:SS')"
				+" and t.ovhPlanEndTime >=  to_date('" + tplMOverhaulInfo.getOvhPlanEndTime() +"','yyyy/MM/dd HH24:MI:SS')";
			TplOverhaulInfo  tplOverhaulInfo ;
			List list = messageDao.queryTplQualityDefectConfigByHql(queryTplCarPositionHql);
			if(list!=null && list.size()>0){
				tplOverhaulInfo = (TplOverhaulInfo)list.get(0);
				if(tplOverhaulInfo!=null){
					tplOverhaulInfo.setStockCode(tplMOverhaulInfo.getStockCode());
					tplOverhaulInfo.setStockName(tplMOverhaulInfo.getStockName());
					tplOverhaulInfo.setCraneId(tplMOverhaulInfo.getCraneId());
					tplOverhaulInfo.setOvhType(tplMOverhaulInfo.getOvhType());
					tplOverhaulInfo.setOvhStartTime(DateUtils.toDate(tplMOverhaulInfo.getOvhStartTime(), SystemConstants.dateformat6));
					tplOverhaulInfo.setOvhEndTime(DateUtils.toDate(tplMOverhaulInfo.getOvhEndTime(), SystemConstants.dateformat6));
					tplOverhaulInfo.setOvhPlanStartTime(DateUtils.toDate(tplMOverhaulInfo.getOvhPlanStartTime(), SystemConstants.dateformat6));
					tplOverhaulInfo.setOvhPlanEndTime(DateUtils.toDate(tplMOverhaulInfo.getOvhPlanEndTime(), SystemConstants.dateformat6));
					tplOverhaulInfo.setOvhDesc(tplMOverhaulInfo.getOvhDesc());
					messageDao.update(tplOverhaulInfo, TplOverhaulInfo.class);
				}
			}
		}
		System.out.println("结束");
	}

	/**
	 * 自提车车型信息[XZXL10]
	 */
	public void sendTplMSinceModels(TplMSinceModels tplMSinceModels)
			throws Exception {
		//若是车头整车新增，查询挂车号是否已经发送，若已经发送不执行
		System.out.println("操作标志："+tplMSinceModels.getProcType().trim());
		/*if(tplMSinceModels.getProcType().trim().equals("U")&&
				tplMSinceModels.getCarNum()!=null  && !tplMSinceModels.getCarNum().trim().equals("")
				&&tplMSinceModels.getTrailerNum()!=null && !tplMSinceModels.getTrailerNum().trim().equals("")){
			String  queryHql = " from TplMSinceModels as t where 1=1 and t.trailerNum = '"+tplMSinceModels.getTrailerNum().trim()+"' and t.carNum='"+tplMSinceModels.getCarNum()+"'";
			List list =messageDao.queryTplQualityDefectConfigByHql(queryHql) ;
			System.out.println("**************:"+list.size());
			if (list == null || list.size()==0){
				tplMSinceModels.setProcType("I");
			}
		}*/
		tplMSinceModels.setCreateDate(new Date());
		messageDao.insert(tplMSinceModels, TplMSinceModels.class);
		logger.info("---发送 自提车车型信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SINCE_MODELS, null, tplMSinceModels);
		System.out.println("结束");
	}

	/**
	 * 自提车签到[XZXL14]
	 */
	public void sendTplMCarSign(TplMCarSign tplMCarSign) throws Exception {
		tplMCarSign.setProcType("I");
		tplMCarSign.setCreateDate(new Date());
		messageDao.insert(tplMCarSign, TplMCarSign.class);
		logger.info("---发送 自提车签到信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_CAR_SIGN, null, tplMCarSign);
		System.out.println("结束");
	}

	/**
	 *  验单结果[XZXL15]
	 */
	public void sendTplMCheckResult(TplMCheckResult tplMCheckResult)
			throws Exception {
		tplMCheckResult.setCreateDate(new Date());
		tplMCheckResult.setProcType("I");
		messageDao.insert(tplMCheckResult, TplMCheckResult.class);
		logger.info("---发送 自提车签到信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_CHECK_RESULT, null, tplMCheckResult);
		System.out.println("结束");
	}

	/**
	 * 自提车到位[XZXL16]
	 */
	public void sendTplMCarInPlace(TplMCarInPlace TplMCarInPlace)
			throws Exception {
		TplMCarInPlace.setProcType("I");
		TplMCarInPlace.setCreateDate(new Date());
		messageDao.insert(TplMCarInPlace, TplMCarInPlace.class);
		logger.info("---发送 自提车签到信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_CAR_IN_PLACE, null, TplMCarInPlace);
		System.out.println("结束");
	}

	/**
	 * 自提车到达用户[XZXL17]
	 */
	public void sendTplMCarArriveUser(TplMCarArriveUser tplMCarArriveUser)
			throws Exception {
		tplMCarArriveUser.setProcType("I");
		tplMCarArriveUser.setCreateDate(new Date());
		messageDao.insert(tplMCarArriveUser, TplMCarArriveUser.class);
		logger.info("---发送 自提车到达用信息：" + new Date());
		sendMsgService.sendMsg(MESSGAE_SEND_TPL_M_CAR_ARRIVE_USER, null, tplMCarArriveUser);
		System.out.println("结束");
	}

	/***
	 *自提司机信息[XZXL18]
	 */
	public void sendTplMSinceDriverInfo(TplRDriver driver,String procType)
			throws Exception {
		TplMSinceDriverInfo tplMSinceDriverInfo = new TplMSinceDriverInfo();
		tplMSinceDriverInfo.setProcType(procType);
		tplMSinceDriverInfo.setProviderId(driver.getProviderId());
		tplMSinceDriverInfo.setDriverName(driver.getUsername());
		tplMSinceDriverInfo.setDriverId(driver.getDriUCode());
		tplMSinceDriverInfo.setDriverBirth(DateUtils.toString(driver.getBirthday(), SystemConstants.dateformat7));
		tplMSinceDriverInfo.setDriverGender(driver.getSex());
		tplMSinceDriverInfo.setDriverLicenseNum(driver.getDriverLicense());
		tplMSinceDriverInfo.setDriverLicenseDate(driver.getValidityTime()==null ? "":driver.getValidityTime().trim().replaceAll("-", ""));
		tplMSinceDriverInfo.setRemarks(driver.getMemo());
		tplMSinceDriverInfo.setCreateDate(new Date());
		tplMSinceDriverInfo.setDriverPhonenum(driver.getDriPhone());
		messageDao.insert(tplMSinceDriverInfo, TplMSinceDriverInfo.class);
		logger.info("---发送 自提司机信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SINCE_DRIVER_INFO, null, tplMSinceDriverInfo);
		System.out.println("结束");
	}

	/**
	 * 自提计划转水运
	 */
	public void senTPLMSinceMaterialWATER(
			TplMSinceMaterialWater tplMSinceMaterialWater) throws Exception {
		System.out.println("自提计划转水运信息新增");
		messageDao.insert(tplMSinceMaterialWater, TplMSinceMaterialWater.class);
		logger.info("---发送自提计划转水运信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SINCE_MATERIAL_WATER, null, tplMSinceMaterialWater);
		System.out.println("结束");
	}
	
	// ******************************宝山基地钢制品物流效率提升start********************************************************
	/**
	 * 服务商自提合同信息[XLXZ50]
	 */
	public void receiveTplMSinceContract(TplMSinceContract tplMSinceContract)
			throws Exception {
		Date date = new Date();
		System.out.println("接收服务商自提合同信息，时间：" + date);
		try {
			String queryTplSinceContractHql = " from TplSinceContract t where t.providerId='"
					+ tplMSinceContract.getProviderId()
					+ "' and t.orderNum = '"
					+ tplMSinceContract.getOrderNum()
					+ "'";
			// 新增判重
			List list = messageDao.queryTplQualityDefectConfigByHql(queryTplSinceContractHql);
			if (null != list && list.size() > 0) {
				return;
			}
			// 新增电文信息
			tplMSinceContract.setCreateDate(date);
			messageDao.insert(tplMSinceContract, TplMSinceContract.class);

			// 新增业务信息
			System.out.println("新增服务商自提合同信息");
			TplSinceContract tplSinceContract = new TplSinceContract();
			tplSinceContract.setOrderNum(tplMSinceContract.getOrderNum());
			tplSinceContract.setProviderId(tplMSinceContract.getProviderId());
			tplSinceContract.setProviderName(tplMSinceContract.getProviderName());
			tplSinceContract.setDestSpotCode(tplMSinceContract.getDestSpotCode());
			tplSinceContract.setDestSpotName(tplMSinceContract.getDestSpotName());
			tplSinceContract.setCreateDate(date);
			tplSinceContract.setSendStatus("00");// 未发送00,10已发送
			tplSinceContract.setMaintainStatus("00");// 00未维护，10已维护
			messageDao.insert(tplSinceContract, TplSinceContract.class);
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收服务商自提合同信息电文异常,承运商为："
					+ tplMSinceContract.getProviderId() + "合同号为："
					+ tplMSinceContract.getOrderNum());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}

	// 装载计划队列信息[XLXZ57]
	public void receiveTplMTransLoadPlan(TplMTransLoadPlan tplMTransLoadPlan)
			throws Exception {
		Date date = new Date();
		System.out.println("接收装载计划队列信息，时间为：" + date);
		System.out.println("++++++++++++++++++++++++"+tplMTransLoadPlan.getDetail()+"----------------------");
		if(tplMTransLoadPlan.getDetail().startsWith("000")){
			//业务上会发循环为空的电文
			//发现这样的电文取出detail的时候会把前后的空格处理掉，导致解析报错  补30个空格
			tplMTransLoadPlan.setDetail("                              "+tplMTransLoadPlan.getDetail());
		}
		tplMTransLoadPlan.setTplMTransLoadPlanItemSet(BeanConvertUtils
				.strToBean(tplMTransLoadPlan.getDetail(),CLASS_TPL_M_TRANS_LOAD_PLAN_ITEM));
		try {
			System.out.println("装载计划队列号："+ tplMTransLoadPlan.getLoadingPlanNo());
			String hqlQuery = " from TplTransLoadPlan t where 1=1 and t.loadingPlanNo = '"
					           + tplMTransLoadPlan.getLoadingPlanNo() + "'";
			System.out.println("hqlQuery:::" + hqlQuery);
			
			// 处理空的model
			Iterator it = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
			while (it.hasNext()) {
				TplMTransLoadPlanItem tplMTransLoadPlanItem = (TplMTransLoadPlanItem) it.next();
				if (tplMTransLoadPlanItem.getBillId() == null
						|| tplMTransLoadPlanItem.getBillId().trim().equals("")) {
					it.remove();
				}
			}

			//查询电文信息是否存在，存在就删掉重新插入
			String hqlQueryTplMTransLoadPlan = " from TplMTransLoadPlan t where 1=1 and t.loadingPlanNo = '"
		           + tplMTransLoadPlan.getLoadingPlanNo() + "' and t.operateFlag = '"+tplMTransLoadPlan.getOperateFlag()+"'";
			System.out.println("hqlQueryTplMTransLoadPlan:::" + hqlQueryTplMTransLoadPlan);
			List listTplMTransLoadPlan = messageDao.queryTplQualityDefectConfigByHql(hqlQueryTplMTransLoadPlan);
			if(null != listTplMTransLoadPlan && listTplMTransLoadPlan.size()>0){
				for(int i=0;i<listTplMTransLoadPlan.size();i++){
					TplMTransLoadPlan tplMTransLoad=(TplMTransLoadPlan) listTplMTransLoadPlan.get(i);
					//删除装载计划子信息sql
					String deleteTplMTransLoadPlanItem = "delete from TPL_M_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
						+ tplMTransLoad.getId();
					Long[] ids = { tplMTransLoad.getId() };
					messageDao.delete(ids, TplMTransLoadPlan.class);

					System.out.println("删除电文子信息，deleteTplMTransLoadPlanItem:::"+ deleteTplMTransLoadPlanItem);
					messageDao.deleteSql(deleteTplMTransLoadPlanItem);
				}
			}
			
			tplMTransLoadPlan.setCreateDate(date);
			// 电文表新增
			messageDao.insert(tplMTransLoadPlan, TplMTransLoadPlan.class);
			// 新增电文子信息
			if (tplMTransLoadPlan.getTplMTransLoadPlanItemSet() != null
					&& tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size() > 0) {
				Iterator iterator = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
				while (iterator.hasNext()) {
					TplMTransLoadPlanItem tplMTransLoadPlanItem = (TplMTransLoadPlanItem) iterator.next();
					tplMTransLoadPlanItem.setTransLoadPlanId(tplMTransLoadPlan.getId());
					messageDao.insert(tplMTransLoadPlanItem,TplMTransLoadPlanItem.class);
				}
			}
			
			//判断新增去重，若此装载计划号存在，执行完毕
			if (tplMTransLoadPlan.getOperateFlag().trim().equals("I")) {
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (list != null && list.size() > 0) {
					return;
				}
			}
			// 业务表操作
			if (null !=tplMTransLoadPlan.getOperateFlag() && tplMTransLoadPlan.getOperateFlag().trim().equals("I")) {
				System.out.println("新增业务信息");
				TplTransLoadPlan tplTransLoadPlan = new TplTransLoadPlan();
				tplTransLoadPlan.setLoadingPlanNo(tplMTransLoadPlan.getLoadingPlanNo());
				tplTransLoadPlan.setPlanTaskSeqNo(tplMTransLoadPlan.getPlanTaskSeqNo());
				tplTransLoadPlan.setPlanNetWeight(tplMTransLoadPlan.getPlanWeight());
				tplTransLoadPlan.setPlanGrossWeight(tplMTransLoadPlan.getPlanWeight());
				tplTransLoadPlan.setPlanCount(tplMTransLoadPlan.getPlanCount());
				tplTransLoadPlan.setActGrossWeight(new BigDecimal(0));
				tplTransLoadPlan.setActNetWeight(new BigDecimal(0));
				tplTransLoadPlan.setActCount(new Long(0));
				tplTransLoadPlan.setStartTime(DateUtils.toDate(tplMTransLoadPlan.getStartTime(),SystemConstants.dateformat6));
				tplTransLoadPlan.setEndTime(DateUtils.toDate(tplMTransLoadPlan.getEndTime(), SystemConstants.dateformat6));
				tplTransLoadPlan.setRequirePlanNo(tplMTransLoadPlan.getRequirePlanNo());
				tplTransLoadPlan.setRemark(tplMTransLoadPlan.getRemark());
				tplTransLoadPlan.setProviderId(tplMTransLoadPlan.getProviderId());
				tplTransLoadPlan.setLadingSpotCode(tplMTransLoadPlan.getLadingSpotCode());
				tplTransLoadPlan.setLadingSpotName(tplMTransLoadPlan.getLadingSpotName());
				tplTransLoadPlan.setDestSpotCode(tplMTransLoadPlan.getDestSpotCode());
				tplTransLoadPlan.setDestSpotName(tplMTransLoadPlan.getDestSpotName());
				tplTransLoadPlan.setCreateDate(date);
				tplTransLoadPlan.setStatus("0");// 0未执行，1已执行，2已结案
				tplTransLoadPlan.setProStatus("00");// 00未派单，10已派单
				tplTransLoadPlan.setValidateFlag("00");//未验单
				tplTransLoadPlan.setSignInStatus("00");//未签到
				tplTransLoadPlan.setFinalUseId(tplMTransLoadPlan.getFinalUseId());
				tplTransLoadPlan.setFinalUseName(tplMTransLoadPlan.getFinalUseName());
				messageDao.insert(tplTransLoadPlan, TplTransLoadPlan.class);
				if (tplMTransLoadPlan.getTplMTransLoadPlanItemSet() != null
						&& tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size() > 0) {
					System.out.println("新增业务子信息:"+tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size());
					Iterator iterator = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
					while (iterator.hasNext()) {
						System.out.println("子信息新增");
						TplMTransLoadPlanItem tplMTransLoadPlanItem = (TplMTransLoadPlanItem) iterator.next();
						TplTransLoadPlanItem tplTransLoadPlanItem = new TplTransLoadPlanItem();
						tplTransLoadPlanItem.setPickNum(tplMTransLoadPlanItem.getBillId());
						tplTransLoadPlanItem.setOrderNum(tplMTransLoadPlanItem.getOrderNum());
						tplTransLoadPlanItem.setPlanWeight(tplMTransLoadPlanItem.getPlanWeight());
						tplTransLoadPlanItem.setPlanCount(tplMTransLoadPlanItem.getPlanCount());
						tplTransLoadPlanItem.setTransLoadPlanId(tplTransLoadPlan.getId());
						messageDao.insert(tplTransLoadPlanItem,TplTransLoadPlanItem.class);
					}
				}
			} else if (null !=tplMTransLoadPlan.getOperateFlag() && tplMTransLoadPlan.getOperateFlag().trim().equals("U")) {
				System.out.println("修改业务信息");
				List list = messageDao
						.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplTransLoadPlan tplTransLoadPlan = (TplTransLoadPlan) list
							.get(0);
					// 已经派单的装载计划不能修改
					if (null == tplTransLoadPlan.getProStatus()
							|| tplTransLoadPlan.getProStatus().trim().equals("00")) {
						tplTransLoadPlan.setPlanTaskSeqNo(tplMTransLoadPlan.getPlanTaskSeqNo());
						tplTransLoadPlan.setPlanNetWeight(tplMTransLoadPlan.getPlanWeight());
						tplTransLoadPlan.setPlanGrossWeight(tplMTransLoadPlan.getPlanWeight());
						tplTransLoadPlan.setPlanCount(tplMTransLoadPlan.getPlanCount());
						tplTransLoadPlan.setActGrossWeight(new BigDecimal(0));
						tplTransLoadPlan.setActNetWeight(new BigDecimal(0));
						tplTransLoadPlan.setActCount(new Long(0));
						tplTransLoadPlan.setStartTime(DateUtils.toDate(tplMTransLoadPlan.getStartTime(),SystemConstants.dateformat6));
						tplTransLoadPlan.setEndTime(DateUtils.toDate(tplMTransLoadPlan.getEndTime(),SystemConstants.dateformat6));
						tplTransLoadPlan.setRequirePlanNo(tplMTransLoadPlan.getRequirePlanNo());
						tplTransLoadPlan.setRemark(tplMTransLoadPlan.getRemark());
						tplTransLoadPlan.setProviderId(tplMTransLoadPlan.getProviderId());
						tplTransLoadPlan.setLadingSpotCode(tplMTransLoadPlan.getLadingSpotCode());
						tplTransLoadPlan.setLadingSpotName(tplMTransLoadPlan.getLadingSpotName());
						tplTransLoadPlan.setDestSpotCode(tplMTransLoadPlan.getDestSpotCode());
						tplTransLoadPlan.setDestSpotName(tplMTransLoadPlan.getDestSpotName());
						tplTransLoadPlan.setUpdateDate(date);
						tplTransLoadPlan.setFinalUseId(tplMTransLoadPlan.getFinalUseId());
						tplTransLoadPlan.setFinalUseName(tplMTransLoadPlan.getFinalUseName());
						messageDao.update(tplTransLoadPlan,TplTransLoadPlan.class);
						System.out.println("修改子信息");
						// 删除子信息，再次新增
						String deleteTplMTransLoadPlanItem = "delete from TPL_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
								+ tplTransLoadPlan.getId();
						System.out.println("deleteTplMTransLoadPlanItem:::"
								+ deleteTplMTransLoadPlanItem);
						messageDao.deleteSql(deleteTplMTransLoadPlanItem);
						if (tplMTransLoadPlan.getTplMTransLoadPlanItemSet() != null
								&& tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size() > 0) {
							Iterator iterator = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
							while (iterator.hasNext()) {
								TplMTransLoadPlanItem tplMTransLoadPlanItem = (TplMTransLoadPlanItem) iterator.next();
								TplTransLoadPlanItem tplTransLoadPlanItem = new TplTransLoadPlanItem();
								tplTransLoadPlanItem.setPickNum(tplMTransLoadPlanItem.getBillId());
								tplTransLoadPlanItem.setOrderNum(tplMTransLoadPlanItem.getOrderNum());
								tplTransLoadPlanItem.setPlanWeight(tplMTransLoadPlanItem.getPlanWeight());
								tplTransLoadPlanItem.setPlanCount(tplMTransLoadPlanItem.getPlanCount());
								tplTransLoadPlanItem.setTransLoadPlanId(tplTransLoadPlan.getId());
								messageDao.insert(tplTransLoadPlanItem,TplTransLoadPlanItem.class);
							}
						}
					}
				}
			} else if (null !=tplMTransLoadPlan.getOperateFlag() && tplMTransLoadPlan.getOperateFlag().trim().equals("D")) {
				System.out.println("删除业务信息");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplTransLoadPlan tplTransLoadPlan = (TplTransLoadPlan) list.get(0);
					//装载计划号
					String loadingPlanNo = tplTransLoadPlan.getLoadingPlanNo();
					Long getSeqId = tplTransLoadPlan.getSeqId();//车次号
					if(null == getSeqId){
						//删除装载计划子信息sql
						String deleteTplTransLoadPlanItem = "delete from TPL_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
							+ tplTransLoadPlan.getId();
						
						//删除根据计划号调度计划sql
						String deleteTplDisplan = "delete from tpl_trans_truck_dis_plan t where t.TRANS_PLAN_ID = '"+tplTransLoadPlan.getLoadingPlanNo()+"'";
						
						// 删除主信息
						Long[] ids = { tplTransLoadPlan.getId() };
						messageDao.delete(ids, TplTransLoadPlan.class);

						System.out.println("删除子信息，deleteTplTransLoadPlanItem:::"+ deleteTplTransLoadPlanItem);
						messageDao.deleteSql(deleteTplTransLoadPlanItem);

						// 若已分配了调度计划，就根据计划号删除调度计划
						System.out.println("删除根据计划号调度计划，deleteTplDisplan:::"+ deleteTplDisplan);
						messageDao.deleteSql(deleteTplDisplan);
					}else if (null != getSeqId) {//车次号不为空说明已经配车
						//查询出车次信息
						TplTransSeq tplTransSeq = (TplTransSeq) messageDao.queryById(tplTransLoadPlan.getSeqId(),TplTransSeq.class);
						
						//查询此装载计划是否已经存在离库实绩，已经存在离库实绩不予许撤销
						String hqlQueryTplActLoadPlan = " from TplActLoadPlan t where t.loadingPlanNo = '"
								+ loadingPlanNo
								+ "' and t.seqId='" + tplTransSeq.getId() + "'";
						System.out.println("装载计划查询：：：：：：：：：：："+hqlQueryTplActLoadPlan);
						List listTplActLoadPlan = messageDao.queryTplQualityDefectConfigByHql(hqlQueryTplActLoadPlan);
						if (null == listTplActLoadPlan || listTplActLoadPlan.size() <=0) {
							//删除装载计划子信息sql
							String deleteTplTransLoadPlanItem = "delete from TPL_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
								+ tplTransLoadPlan.getId();
							
							//删除根据计划号调度计划sql
							String deleteTplDisplan = "delete from tpl_trans_truck_dis_plan t where t.TRANS_PLAN_ID = '"+tplTransLoadPlan.getLoadingPlanNo()+"'";
							
							// 删除主信息
							Long[] ids = { tplTransLoadPlan.getId() };
							messageDao.delete(ids, TplTransLoadPlan.class);

							System.out.println("删除子信息，deleteTplTransLoadPlanItem:::"+ deleteTplTransLoadPlanItem);
							messageDao.deleteSql(deleteTplTransLoadPlanItem);

							// 若已分配了调度计划，就根据计划号删除调度计划
							System.out.println("删除根据计划号调度计划，deleteTplDisplan:::"+ deleteTplDisplan);
							messageDao.deleteSql(deleteTplDisplan);
							
							//更新车位车位分配信息失效
							String updateTplProviderCarAllot = "UPDATE TPL_PROVIDER_CAR_ALLOT t set t.FLAG = '20' where t.LOADING_PLAN_NO= '"
									+ loadingPlanNo + "'";
							System.out.println("更新车位车位分配信息失效，updateTplProviderCarAllot:::"+ updateTplProviderCarAllot);
							messageDao.updateBySql(updateTplProviderCarAllot);

							//更新排队信息为失效
							String updateTplProviderCarList = "UPDATE TPL_PROVIDER_CAR_LIST_ITEM t set t.STATUS ='0' where t.LOADING_PLAN_NO ='"+ loadingPlanNo + "')";
							System.out.println("更新排队信息为失效,updateTplProviderCarList:::::"+ updateTplProviderCarList);
							messageDao.updateBySql(updateTplProviderCarList);

							//更新服务商装载计划配车业务信息为失效
							String updateTplPlanAllocationCar = "UPDATE TPL_PLAN_ALLOCATION_CAR t SET T.REVOKE_STATUS = '2' where t.LOADING_PLAN_NO = '"+ loadingPlanNo + "'";
							System.out.println("更新服务商装载计划配车业务信息为失效，updateTplPlanAllocationCar:::"+ updateTplPlanAllocationCar);
							messageDao.updateBySql(updateTplPlanAllocationCar);
							
							//更新签到sql，根据装载计划号更新为失效
							String updateTplTransTruckSignIn = "UPDATE TPL_TRANS_TRUCK_SIGN_IN t set t.STATUS ='00' where t.LOADING_PLAN_NO = '"+ loadingPlanNo + "'";
							System.out.println("更新签到信息，deleteTplTransTruckSignIn:::"+ updateTplTransTruckSignIn);
							messageDao.updateBySql(updateTplTransTruckSignIn);

							if (null != tplTransSeq) {
								Long seqId = tplTransSeq.getId();
								//判断此车次是否还存在其他的装载计划
								String hqlQueryTplTransLoadPlanSeq = " from TplTransLoadPlan t where 1=1 and t.seqId = "+ seqId;
								List listTplTransLoadPlanSeq = messageDao.queryTplQualityDefectConfigByHql(hqlQueryTplTransLoadPlanSeq);
								if (null == listTplTransLoadPlanSeq || listTplTransLoadPlanSeq.size() <= 0) {
									System.out.println("车次：" + seqId+ "已不存在装载计划，更新车次为未配置车！");
									tplTransSeq.setProStatus("01");//更新状态为绑定状态
									messageDao.update(tplTransSeq, TplTransSeq.class);
									
									/*//根据司机U代码查询出司机信息
									TplRDriver tplRDriver = messageDao.queryTplRDriver(tplTransSeq.getDriver());

									//初始化部分值
									CarsInfoSearchModel carsInfo = new CarsInfoSearchModel();
									carsInfo.setDriUCode(tplTransSeq.getDriver());
									carsInfo.setBusNumber(tplTransSeq.getCarId());
									carsInfo.setSeqId(seqId);
									carsInfo.setProviderId(tplTransSeq.getProviderId());
									carsInfo.setUsername(tplRDriver.getUsername());
									String updateFontStatus = "0";
									messageDao.updateFontStatusBySearchModel(carsInfo, updateFontStatus);

									//删除车次信息
									Long[] id = { seqId };
									messageDao.delete(id, TplTransSeq.class);*/
									
									TplMessageLog tplMessageLog = new TplMessageLog();
									tplMessageLog.setCreateDate(date);
									tplMessageLog.setErrContent("此车次已不存在配置的装载计划，车次已经刷新为挂车绑定状态");
									tplMessageLog.setErrTitle("接收装载计划队列信息,装载计划号为："+ tplTransLoadPlan );
									messageDao.insert(tplMessageLog, TplMessageLog.class);
								}
							}
						}else{
							TplMessageLog tplMessageLog = new TplMessageLog();
							tplMessageLog.setCreateDate(date);
							tplMessageLog.setErrContent("装载计划号为："+tplTransLoadPlan+"撤销失败，因为存在此装载计划已接收离库实绩！");
							tplMessageLog.setErrTitle("接收装载计划队列信息,装载计划号为："+ tplTransLoadPlan );
							messageDao.insert(tplMessageLog, TplMessageLog.class);
						}
					}
				}
			}else if (null !=tplMTransLoadPlan.getOperateFlag() && tplMTransLoadPlan.getOperateFlag().trim().equals("E")) {//强制结案
				System.out.println("强制结案装载计划");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					//更新装载计划为结案
					TplTransLoadPlan tplTransLoadPlan = (TplTransLoadPlan) list.get(0);
					String loadingPlanNo = tplTransLoadPlan.getLoadingPlanNo();
					tplTransLoadPlan.setStatus("2");//已结案
					tplTransLoadPlan.setSignInStatus("10");//已签到
					tplTransLoadPlan.setValidateFlag("10");//已验单
					messageDao.update(tplTransLoadPlan, TplTransLoadPlan.class);
					
					//如果已经派单更新调度计划已结案
					String updateTplDisplan = "UPDATE tpl_trans_truck_dis_plan t set t.STATUS='20',t.VALIDATE_FLAG='10' where t.TRANS_PLAN_ID = '"+loadingPlanNo+"'";
					System.out.println("更新调度计划为执行完毕：：：：：："+updateTplDisplan);
					messageDao.updateBySql(updateTplDisplan);
					
					//更新车位车位分配信息已到位
					String updateTplProviderCarAllot = "UPDATE TPL_PROVIDER_CAR_ALLOT t set t.FLAG = '10' where t.LOADING_PLAN_NO= '"
							+ loadingPlanNo + "'";
					System.out.println("更新车位车位分配信息已经到位，updateTplProviderCarAllot:::"+ updateTplProviderCarAllot);
					messageDao.updateBySql(updateTplProviderCarAllot);
				}
			}
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收装载计划队列信息,装载计划号为："
					+ tplMTransLoadPlan.getLoadingPlanNo() + "，任务顺序号为："
					+ tplMTransLoadPlan.getPlanTaskSeqNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}

	// 社会服务商车辆排队信息[XLXZ52]
	public void receiveTplMProviderCarList(
			TplMProviderCarList tplMProviderCarList) throws Exception {
		Date date = new Date();
		System.out.println("接收社会服务商车辆排队信息，时间：：：：" + date);
		try {
			tplMProviderCarList.setTplMProviderCarListItemSet(BeanConvertUtils
					.strToBean(tplMProviderCarList.getDetail(),CLASS_TPL_M_PROVIDER_CAR_LIST_ITEM));
			// 处理null的model
			Iterator it = tplMProviderCarList.getTplMProviderCarListItemSet().iterator();
			if(null != it){
				while (it.hasNext()) {
					TplMProviderCarListItem tplMProviderCarListItem = (TplMProviderCarListItem) it.next();
					if (tplMProviderCarListItem.getLoadingPlanNo() == null
							|| "".equals(tplMProviderCarListItem.getLoadingPlanNo().trim())) {
						it.remove();
					}
				}
			}
			// 接收新的电文
			tplMProviderCarList.setCreateDate(date);
			messageDao.insert(tplMProviderCarList, TplMProviderCarList.class);
			if (tplMProviderCarList.getTplMProviderCarListItemSet() != null
					&& tplMProviderCarList.getTplMProviderCarListItemSet().size() > 0) {
				Iterator iterator = tplMProviderCarList.getTplMProviderCarListItemSet().iterator();
				if (iterator != null) {
					while (iterator.hasNext()) {
						TplMProviderCarListItem tplMProviderCarListItem = (TplMProviderCarListItem) iterator.next();
						tplMProviderCarListItem.setCarListId(tplMProviderCarList.getId());
						messageDao.insert(tplMProviderCarListItem,TplMProviderCarListItem.class);
					}
				}
			}
			
			// 查询进来排队库区是否存在
			String queryHql = " from TplProviderCarList t where and t.stockNo='"+ tplMProviderCarList.getStockNo() + "'";
			System.out.println("queryHql::::" + queryHql);
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			TplProviderCarList tplProviderCarList = new TplProviderCarList();
			
			if (null == list || list.size() == 0) {//主信息不存在，新增业务主信息
				tplProviderCarList.setStockNo(tplMProviderCarList.getStockNo());
				tplProviderCarList.setCreateDate(date);
				messageDao.insert(tplProviderCarList, TplProviderCarList.class);
			} else {//主信息已经存在，删除原有子信息 
				tplProviderCarList = (TplProviderCarList) list.get(0);
				String deleteTplProviderCarList = "delete from TPL_PROVIDER_CAR_LIST_ITEM t where t.CAR_LIST_ID = "
						+ tplProviderCarList.getId();
				System.out.println("deleteTplProviderCarList:::"+ deleteTplProviderCarList);
				messageDao.deleteSql(deleteTplProviderCarList);
			}
			//新增子业务信息
			if (tplMProviderCarList.getTplMProviderCarListItemSet() != null
					&& tplMProviderCarList.getTplMProviderCarListItemSet().size() > 0) {
				Iterator iterator = tplMProviderCarList.getTplMProviderCarListItemSet().iterator();
				if (iterator != null) {
					while (iterator.hasNext()) {
						TplProviderCarListItem tplProviderCarListItem = new TplProviderCarListItem();
						TplMProviderCarListItem tplMProviderCarListItem = (TplMProviderCarListItem) iterator.next();
						tplProviderCarListItem.setLoadingPlanNo(tplMProviderCarListItem.getLoadingPlanNo());
						tplProviderCarListItem.setTruckNo(tplMProviderCarListItem.getTruckNo());
						tplProviderCarListItem.setSeq(tplMProviderCarListItem.getSeq());
						tplProviderCarListItem.setTimeWait(tplMProviderCarListItem.getTimeWait());
						tplProviderCarListItem.setCarListId(tplProviderCarList.getId());
						tplProviderCarListItem.setStatus("1");
						messageDao.insert(tplProviderCarListItem,TplProviderCarListItem.class);
					}
				}
			}
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收社会服务商车辆排队信息电文异常,库区为："+ tplMProviderCarList.getStockNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}

	// 社会服务商车辆车位分配信息 [XLXZ53]
	public void receiveTplMProviderCarAllot(
			TplMProviderCarAllot tplMProviderCarAllot) throws Exception {
		Date date = new Date();
		System.out.println("接收社会服务商车辆车位分配信息，时间：：：：" + date);
		try {
			// 新增电文信息
			System.out.println("新增电文信息");
			tplMProviderCarAllot.setCreateDate(date);
			messageDao.insert(tplMProviderCarAllot, TplMProviderCarAllot.class);

			//判断装载计划、车次号是否已经存在车位分配，有就不执行新增
			List listTplProviderCarAllot = messageDao.queryTplProviderCarAllot(tplMProviderCarAllot);
			if(null == listTplProviderCarAllot || listTplProviderCarAllot.size()<=0){
				// 新增业务信息
				System.out.println("新增业务信息");
				TplProviderCarAllot tplProviderCarAllot = new TplProviderCarAllot();
				tplProviderCarAllot.setTruckNo(tplMProviderCarAllot.getTruckNo());
				tplProviderCarAllot.setTrailerNum(tplMProviderCarAllot.getTrailerNum());
				tplProviderCarAllot.setParkingLot(tplMProviderCarAllot.getParkingLot());
				tplProviderCarAllot.setLoadingPlanNo(tplMProviderCarAllot.getLoadingPlanNo());
				System.out.println(DateUtils.toDate(tplMProviderCarAllot.getInOffTime(),
						SystemConstants.dateformat6));
				tplProviderCarAllot.setInOffTime(DateUtils.toDate(tplMProviderCarAllot.getInOffTime(),
						SystemConstants.dateformat6));
				tplProviderCarAllot.setCreateDate(date);
				tplProviderCarAllot.setSeqId(tplMProviderCarAllot.getSeqId());
				tplProviderCarAllot.setFlag("00");//未到位确认
				messageDao.insert(tplProviderCarAllot, TplProviderCarAllot.class);
				
				//根据司机的U代码推送消息给司机
				String queryTplTranSeq = " from TplTransSeq t where t.id = " + Long.valueOf(tplMProviderCarAllot.getSeqId());
				List list = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
				if(null != list && list.size()>0){
					TplTransSeq tplTransSeq= (TplTransSeq) list.get(0);
					String alias = tplTransSeq.getDriver();//发送别名
					HashMap bspMap = new HashMap();//传递参数
					//根据库区代码简称查询出库区代码全称以及库区名称
					String wareHouseCode= tplProviderCarAllot.getParkingLot().substring(0,tplProviderCarAllot.getParkingLot().indexOf("-"));//库区id
					List listHous = messageDao.queryWareHouseName(wareHouseCode, new Long(tplProviderCarAllot.getSeqId()));
					if(null != list && list.size()>0){
						TplTransTruckSignIn tplTransTruckSignIn = (TplTransTruckSignIn) listHous.get(0);
						bspMap.put("WAREHOUSE_NAME", tplTransTruckSignIn.getLadingSpotName());//仓库名称
						bspMap.put("WAREHOUSE_ID", tplTransTruckSignIn.getLadingSpot());//仓库id
					}else{
						bspMap.put("WAREHOUSE_NAME", "");
						bspMap.put("WAREHOUSE_ID", "");
					}
					List listProviderCar=messageDao.queryProviderCar(wareHouseCode, tplProviderCarAllot.getLoadingPlanNo());
					if(null !=listProviderCar && listProviderCar.size()>0){
						Object [] items = (Object[]) listProviderCar.get(0);
						bspMap.put("LIST_NUM", "库区"+ items[0].toString()+ "-序号"+ items[1].toString());
					}else{
						bspMap.put("LIST_NUM", "");
					}
					bspMap.put("PARKING_CODE", tplProviderCarAllot.getParkingLot());
					bspMap.put("CONFIRM_FLAG", "00");// 是否到位
					bspMap.put("SYSTEM_TYPE", "30");// 系统标志
					bspMap.put("ALIAS", alias);//别名
					bspMap.put("PROC_TYPE", "I");//操作标志
				    bspMap.put("DRI_U_CODE",alias);
				    
				    System.out.println("执行消息推送");
					String result = JpushUtil.push(bspMap);
					System.out.println("消息推送结果："+result);
				}
			}
			
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收社会服务商车辆车位分配信息电文异常,车头号为："
					+ tplMProviderCarAllot.getTruckNo() + "挂车号为："
					+ tplMProviderCarAllot.getTrailerNum());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}

	// 二维码扫描验单通过信息[XLXZ54]
	/*public void receiveTplMQrCodeScanPass(TplMQrCodeScanPass tplMQrCodeScanPass)
			throws Exception {
		Date date = new Date();
		System.out.println("二维码扫描验单通过信息电文接收，时间：：：：" + date);
		try {
			// 新增电文信息
			tplMQrCodeScanPass.setCreateDate(date);
			messageDao.insert(tplMQrCodeScanPass, TplMQrCodeScanPass.class);

			// 新增业务信息
			TplQrCodeScanPass tplQrCodeScanPass = new TplQrCodeScanPass();
			tplQrCodeScanPass.setLoadingPlanNo(tplMQrCodeScanPass.getLoadingPlanNo());
			tplQrCodeScanPass.setVerifyDate(DateUtils.toDate(tplMQrCodeScanPass.getVerifyDate(), SystemConstants.dateformat6));
			tplQrCodeScanPass.setVerifyMan(tplMQrCodeScanPass.getVerifyMan());
			tplQrCodeScanPass.setCreateDate(date);
			messageDao.insert(tplQrCodeScanPass, TplQrCodeScanPass.class);
			
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收二维码扫描验单通过信息电文异常,装载计划号为："
					+ tplMQrCodeScanPass.getLoadingPlanNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}*/

	// 装载计划装载实绩 [XLXZ55]
	public void receiveTplMActLoadPlan(TplMActLoadPlan tplMActLoadPlan)
			throws Exception {
		Date date = new Date();
		System.out.println("装载计划装载实绩电文信息接收，时间：：：：" + date);
		tplMActLoadPlan.setTplMActLoadPlanItemSet(BeanConvertUtils.strToBean(tplMActLoadPlan.getDetail(), CLASS_TPL_M_ACT_LOAD_PLAN_ITEM));
		try {

			String hqlQuery = " from TplActLoadPlan t where t.loadingPlanNo = '"
					+ tplMActLoadPlan.getLoadingPlanNo()
					+ "' and t.seqId='"
					+ tplMActLoadPlan.getSeqId() + "'";
			

			// 处理null的model
			Iterator it = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
			while (it.hasNext()) {
				TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) it.next();
				if (null ==tplMActLoadPlanItem.getPackId() || tplMActLoadPlanItem.getPackId().trim().equals("")) {
					it.remove();
				}
			}

			// 新增电文信息
			tplMActLoadPlan.setCreateDate(date);
			messageDao.insert(tplMActLoadPlan, TplMActLoadPlan.class);
			if (tplMActLoadPlan.getTplMActLoadPlanItemSet() != null
					&& tplMActLoadPlan.getTplMActLoadPlanItemSet().size() > 0) {
				Iterator iterator = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
				while (iterator.hasNext()) {
					TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) iterator.next();
					tplMActLoadPlanItem.setPlanActId(tplMActLoadPlan.getId());
					messageDao.insert(tplMActLoadPlanItem,TplMActLoadPlanItem.class);
				}
			}
			
			//I操作如果存在离库的装载实绩，先把之前的离库实绩还原掉
			if (null != tplMActLoadPlan.getOperFlag() && tplMActLoadPlan.getOperFlag().trim().equals("I")) {
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {//判断装载计划是否存在
					List listOld = new ArrayList();//原材料信息
					String queryTplTranSeq = " from TplTransSeq t where t.id = " + Long.valueOf(tplMActLoadPlan.getSeqId());
					List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
					if(null != tplTransSeqList && tplTransSeqList.size()>0){
						TplTransSeq tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
						if (tplTransSeq.getId() != null) {
						//根据装载计划号、车号查询出原材料信息
						listOld = messageDao.queryTplTransPackY(tplMActLoadPlan.getLoadingPlanNo(),tplMActLoadPlan.getSeqId());
						//原来离库实绩的信息还原
						this.restoreXZXL55(tplTransSeq,listOld,tplMActLoadPlan);
						//删除原装载计划的离库实绩信息
						TplActLoadPlan tplActLoadPlan = (TplActLoadPlan) list.get(0);
						Long[] ids = { tplActLoadPlan.getId() };
						messageDao.delete(ids, TplActLoadPlan.class);
						// 删除原装载离库实绩的子信息
						String deleteTplActLoadPlanItem = "delete from TPL_ACT_LOAD_PLAN_ITEM t where t.PLAN_ACT_ID = "
								+ tplActLoadPlan.getId();
						System.out.println("deleteTplActLoadPlanItem:::"+ deleteTplActLoadPlanItem);
						messageDao.deleteSql(deleteTplActLoadPlanItem);
						}
					}
				}
			}

			//判断离库材料seq_id字段是否不等于null，若有不等于null说明已经被装载
			if (tplMActLoadPlan.getOperFlag().trim().equals("I") || tplMActLoadPlan.getOperFlag().trim().equals("U")) {
				if (tplMActLoadPlan.getTplMActLoadPlanItemSet() != null && tplMActLoadPlan.getTplMActLoadPlanItemSet().size() > 0) {
					Iterator iterator = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
					String message ="";
					while (iterator.hasNext()) {
						TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) iterator.next();
						String queryPackSeqId = "select t from TplTransPack t,TplTransPlan tt where t.transPlanId = tt.transPlanId and tt.transType = '1'"
								+ " and t.packId = '"
								+tplMActLoadPlanItem.getPackId()
								+ "' and tt.pickNum = '"
								+ tplMActLoadPlanItem.getPlanNo()
								+ "' and t.seqId is not null";
						List list = messageDao.queryTplQualityDefectConfigByHql(queryPackSeqId);
						if (list != null && list.size() > 0) {
							message +=tplMActLoadPlanItem.getPackId()+"、";
						}
					}
					if(null !=message && !message.trim().equals("")){
						TplMessageLog tplMessageLog= new TplMessageLog();
						tplMessageLog.setErrTitle("装载计划装载实绩");
						tplMessageLog.setErrContent("存在已装载的材料，材料号为："+message+"，车次号为:"+tplMActLoadPlan.getSeqId()+" ，装载计划号为: "+tplMActLoadPlan.getLoadingPlanNo());
						tplMessageLog.setCreateDate(date);
						messageDao.insert(tplMessageLog, TplMessageLog.class);
						return;
					}
				}
			}

			//查询出车次信息
			TplTransSeq tplTransSeq = new TplTransSeq();// 车次信息
			List listTplTransPack = new ArrayList();// 捆包材料信息
			List listIds = new ArrayList();// 离库实绩捆包材料id
			System.out.println("查询出车次信息，车次为：" + tplMActLoadPlan.getSeqId());
			Long[] packIds = new Long[tplMActLoadPlan.getTplMActLoadPlanItemSet().size()];
			String queryTplTranSeq = " from TplTransSeq t where t.id = " + Long.valueOf(tplMActLoadPlan.getSeqId());
			List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
			if (tplTransSeqList != null && tplTransSeqList.size() > 0) {
				tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
				Iterator iterator = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
				if (iterator != null) {
					while (iterator.hasNext()) {
						TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) iterator.next();
						// 根据捆包id、制造单元提单号查询出捆包信息
						String queryTplTransPack = "select t from TplTransPack t,TplTransPlan tt where t.transPlanId = tt.transPlanId and tt.transType = '1'"
								+" and t.packId ='"
								+ tplMActLoadPlanItem.getPackId()
								+ "' and tt.pickNum = '"
								+ tplMActLoadPlanItem.getPlanNo() + "'";
						System.out.println("queryTplTransPack:::"+ queryTplTransPack);
						List list = messageDao.queryTplQualityDefectConfigByHql(queryTplTransPack);
						if (null != list && list.size() > 0) {
							listTplTransPack.add(list.get(0));
							TplTransPack tplTransPack = (TplTransPack) list.get(0);
							listIds.add(tplTransPack.getId());
						}
					}
					packIds = new Long[listIds.size()];
					listIds.toArray(packIds);// 获取离库实际 pack捆包信息Id
				}
			}
			
			List listOld = new ArrayList();//操作标记为'D' or 'U'的原装载计划下的材料
			if(tplMActLoadPlan.getOperFlag().trim().equals("U") || tplMActLoadPlan.getOperFlag().trim().equals("D")){
				if (tplTransSeq.getId() != null) {
					// 根据装载计划号、车号查询出原材料信息
					listOld = messageDao.queryTplTransPackY(tplMActLoadPlan.getLoadingPlanNo(),tplMActLoadPlan.getSeqId());
				}
			}

			// 业务操作
			if (tplMActLoadPlan.getOperFlag().trim().equals("I")) {
				System.out.println("新增业务信息");
				TplActLoadPlan tplActLoadPlan = new TplActLoadPlan();
				tplActLoadPlan.setLoadingPlanNo(tplMActLoadPlan.getLoadingPlanNo());
				tplActLoadPlan.setTruckNo(tplMActLoadPlan.getTruckNo());
				tplActLoadPlan.setTrailerNum(tplMActLoadPlan.getTrailerNum());
				tplActLoadPlan.setParkingLot(tplMActLoadPlan.getParkingLot());
				tplActLoadPlan.setCreateDate(date);
				tplActLoadPlan.setSeqId(tplMActLoadPlan.getSeqId());
				messageDao.insert(tplActLoadPlan, TplActLoadPlan.class);// 主信息新增
				// 子信息新增
				if (tplMActLoadPlan.getTplMActLoadPlanItemSet() != null && tplMActLoadPlan.getTplMActLoadPlanItemSet().size() > 0) {
					Iterator iterator = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
					while (iterator.hasNext()) {
						TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) iterator.next();
						TplActLoadPlanItem tplActLoadPlanItem = new TplActLoadPlanItem();
						tplActLoadPlanItem.setPackId(tplMActLoadPlanItem.getPackId());
						tplActLoadPlanItem.setPlanNo(tplMActLoadPlanItem.getPlanNo());
						tplActLoadPlanItem.setLayerNo(tplMActLoadPlanItem.getLayerNo());
						tplActLoadPlanItem.setPlaceNo(tplMActLoadPlanItem.getPlaceNo());
						tplActLoadPlanItem.setPlanActId(tplActLoadPlan.getId());
						messageDao.insert(tplActLoadPlanItem,TplActLoadPlanItem.class);
					}
				}
			} else if (tplMActLoadPlan.getOperFlag().trim().equals("U")) {
				System.out.println("修改业务信息");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplActLoadPlan tplActLoadPlan = (TplActLoadPlan) list.get(0);
					tplActLoadPlan.setTruckNo(tplMActLoadPlan.getTruckNo());
					tplActLoadPlan.setTrailerNum(tplMActLoadPlan.getTrailerNum());
					tplActLoadPlan.setParkingLot(tplMActLoadPlan.getParkingLot());
					messageDao.update(tplActLoadPlan, TplActLoadPlan.class);
					// 修改子信息
					// 删除子信息，再次新增
					String deleteTplActLoadPlanItem = "delete from TPL_ACT_LOAD_PLAN_ITEM t where t.PLAN_ACT_ID = "
							                           + tplActLoadPlan.getId();
					System.out.println("deleteTplActLoadPlanItem:::"+ deleteTplActLoadPlanItem);
					messageDao.deleteSql(deleteTplActLoadPlanItem);
					if (tplMActLoadPlan.getTplMActLoadPlanItemSet() != null && tplMActLoadPlan.getTplMActLoadPlanItemSet().size() > 0) {
						Iterator iterator = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
						while (iterator.hasNext()) {
							TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) iterator.next();
							TplActLoadPlanItem tplActLoadPlanItem = new TplActLoadPlanItem();
							tplActLoadPlanItem.setPackId(tplMActLoadPlanItem.getPackId());
							tplActLoadPlanItem.setPlanNo(tplMActLoadPlanItem.getPlanNo());
							tplActLoadPlanItem.setLayerNo(tplMActLoadPlanItem.getLayerNo());
							tplActLoadPlanItem.setPlaceNo(tplMActLoadPlanItem.getPlaceNo());
							tplActLoadPlanItem.setPlanActId(tplActLoadPlan.getId());
							messageDao.insert(tplActLoadPlanItem,TplActLoadPlanItem.class);
						}
					}
				}
			} else if (tplMActLoadPlan.getOperFlag().trim().equals("D")) {
				System.out.println("删除业务信息");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplActLoadPlan tplActLoadPlan = (TplActLoadPlan) list.get(0);
					// 删主信息
					Long[] ids = { tplActLoadPlan.getId() };
					messageDao.delete(ids, TplActLoadPlan.class);
					// 删除子信息
					String deleteTplActLoadPlanItem = "delete from TPL_ACT_LOAD_PLAN_ITEM t where t.PLAN_ACT_ID = "
							+ tplActLoadPlan.getId();
					System.out.println("deleteTplActLoadPlanItem:::"+ deleteTplActLoadPlanItem);
					messageDao.deleteSql(deleteTplActLoadPlanItem);
				}
			}

			//U或者D操作先还原掉原装载计划离库实绩的信息
			if (tplMActLoadPlan.getOperFlag().trim().equals("U") || tplMActLoadPlan.getOperFlag().trim().equals("D")) {
				this.restoreXZXL55(tplTransSeq,listOld,tplMActLoadPlan);
			}
			
			//入库实际新增刷新材料信息，计划信息，调度计划信息，车次信息
			if (tplMActLoadPlan.getOperFlag().trim().equals("I") || tplMActLoadPlan.getOperFlag().trim().equals("U")) {
				DecimalFormat df = new DecimalFormat("0.000000");
				BigDecimal actGrossWeight = new BigDecimal("0");// 实际装车毛重
				BigDecimal actNetWeight = new BigDecimal("0");// 实际装车净重
				Long actCount = new Long(0);// 实际装车件数
				if (tplTransSeq.getId() != null) {
					// 根据所选择的材料号查询出汽运调度计划信息，更新捆包信息的调度计划id，及其捆包状态
					String queryTplTransPlan = "select t from TplTransTruckDisPlan t where t.flag='10' and t.billId ='"
		                 + tplMActLoadPlan.getLoadingPlanNo() + "' and t.trackNo = "+tplTransSeq.getId();
					System.out.println("queryTplTransPlan:::"+ queryTplTransPlan);
					List listTruckDisPlan = messageDao.queryTplQualityDefectConfigByHql(queryTplTransPlan);
					for (int i = 0; i < listTplTransPack.size(); i++) {
						TplTransPack tplTransPack = (TplTransPack) listTplTransPack.get(i);
						if (null != listTruckDisPlan && listTruckDisPlan.size() > 0) {
							TplTransTruckDisPlan tplTransTruckDisPlan = (TplTransTruckDisPlan) listTruckDisPlan.get(0);
							tplTransPack.setControlId(new Double(tplTransTruckDisPlan.getId().toString()));
						}
						tplTransPack.setPackStatus("1");
						tplTransPack.setProStatus("40");
						tplTransPack.setSeqId(tplTransSeq.getId());
						messageDao.update(tplTransPack, TplTransPack.class);
					}

					//根据捆包查询出计划号,再根据计划号去更新计划
					if (packIds != null && packIds.length > 0) {
						List listTransPlanId = messageDao.queryTransPlanId(packIds);
						System.out.println("查询出来的计划条	数：" + listTransPlanId.size());
						if (listTransPlanId != null && listTransPlanId.size() > 0) {
							for (int i = 0; i < listTransPlanId.size(); i++) {
								TplTransPlan tplTransPlan = new TplTransPlan();
								String transPlanId = (String) listTransPlanId.get(i);
								System.out.println("计划号：：：：：：：：" + transPlanId);
								List list = messageDao.tplTransPlanCount(transPlanId);
								if (null != list && list.size() > 0) {
									Object[] obj = (Object[]) list.get(0);
									actGrossWeight = null == obj[0] ? new BigDecimal("0"): new BigDecimal(obj[0].toString());
									actNetWeight = null == obj[1] ? new BigDecimal("0"): new BigDecimal(obj[1].toString());
									actCount = null == obj[2] ? new Long(0): new Long(obj[2].toString());
								}
								// 更新计划
								tplTransPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
								tplTransPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
								tplTransPlan.setActCount(actCount);
								tplTransPlan.setStatus("1");
								tplTransPlan.setSeqId(tplTransSeq.getId());
								tplTransPlan.setTransPlanId(transPlanId);
								messageDao.updateTplTransPlan(tplTransPlan);
							}
						}
					}

					List list = messageDao.tplTransSeqLeaveCount(tplTransSeq.getId().toString());
					Object[] obj = (Object[]) list.get(0);
					actGrossWeight = null == obj[0] ? new BigDecimal("0"): new BigDecimal(df.format(obj[0]));
					actNetWeight = null == obj[1] ? new BigDecimal("0"): new BigDecimal(df.format(obj[1]));
					actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
					//根据出库实绩材料信息查询出库的实绩毛重、净重、件数，然后更新到此运输车次中，且修改此车次为运输中
					tplTransSeq.setStatus("1");
					tplTransSeq.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransSeq.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransSeq.setActCount(actCount);// 实际件数
					tplTransSeq.setPlanGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransSeq.setPlanNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransSeq.setPlanCount(actCount);// 计划件数
					tplTransSeq.setProStatus("10");// 已发车
					tplTransSeq.setUploadTime(date);//修改时间
					tplTransSeq.setRealStartDate(date);//实际发车时间
					messageDao.update(tplTransSeq, TplTransSeq.class);
					
					//根据所指定的计划号更新汽运调度计划
					//根据车次号、装计划号查询出此装计划下的实绩毛重、净重、件数
					List loadPlanSumList = messageDao.tplTransLoanPlanCount(tplMActLoadPlan);
					Object[] objSum = (Object[]) loadPlanSumList.get(0);
					actGrossWeight = null == objSum[0] ? new BigDecimal("0"): new BigDecimal(df.format(objSum[0]));
					actNetWeight = null == objSum[1] ? new BigDecimal("0"): new BigDecimal(df.format(objSum[1]));
					actCount = null == objSum[2] ? new Long(0) : new Long(objSum[2].toString());
					TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
					tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransTruckDisPlan.setActCount(actCount);
					tplTransTruckDisPlan.setStatus("20");//将接收到装载计划离库实绩的信息更新为已结案
					tplTransTruckDisPlan.setTransPlanId(tplMActLoadPlan.getLoadingPlanNo());
					messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);

					//更新装载计划
					TplTransLoadPlan tplTransLoadPlan = new TplTransLoadPlan();
					tplTransLoadPlan.setLoadingPlanNo(tplMActLoadPlan.getLoadingPlanNo());
					tplTransLoadPlan.setActGrossWeight(actGrossWeight);
					tplTransLoadPlan.setActNetWeight(actNetWeight);
					tplTransLoadPlan.setActCount(actCount);
					tplTransLoadPlan.setSeqId(tplTransSeq.getId());
					tplTransLoadPlan.setStatus("2");//将接受到装载计划离库实绩信息更新为结案
					messageDao.updateTplTransLoadPlan(tplTransLoadPlan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收装载计划装载实绩电文信息电文异常,转载计划号为："
					+ tplMActLoadPlan.getLoadingPlanNo() + "，车号为："
					+ tplMActLoadPlan.getTruckNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}

	// 仓库定检修异常反馈信息(XLXZ56）-定检修计划确认或异常反馈时
	public void receiveTplMDepotException(TplMDepotException tplMDepotException)
			throws Exception {
		Date date = new Date();
		System.out.println("仓库定检修异常反馈信息接收，时间：：：：" + date);
		// 电文信息新增
		try {
			String queryHql = " from TplDepotException as t where 1=1 and t.stockCode = '"
					+ tplMDepotException.getStockCode()
					+ "' and t.devNo = '"
					+ tplMDepotException.getDevNo()
					+ "' and t.planTimeBegin <= to_date('"
					+ tplMDepotException.getPlanTimeBegin()
					+ "','yyyy/MM/dd HH24:MI:SS')"
					+ " and t.planTimeEnd >=  to_date('"
					+ tplMDepotException.getPlanTimeEnd()
					+ "','yyyy/MM/dd HH24:MI:SS')";
			System.out.println("queryHql:::" + queryHql);
			// 判重
			if (tplMDepotException.getOperFlag().trim().equals("I")) {
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size() > 0) {
					return;
				}
			}
			messageDao.insert(tplMDepotException, TplMDepotException.class);
			// 业务操作
			if (tplMDepotException.getOperFlag().trim().equals("I")) {
				System.out.println("新增业务信息");
				TplDepotException tplDepotException = new TplDepotException();
				tplDepotException.setCheckType(tplMDepotException.getCheckType());
				tplDepotException.setDeviceType(tplMDepotException.getDeviceType());
				tplDepotException.setStockCode(tplMDepotException.getStockCode());
				tplDepotException.setDevNo(tplMDepotException.getDevNo());
				tplDepotException.setDevName(tplMDepotException.getDevName());
				tplDepotException.setPlanTimeBegin(DateUtils.toDate(tplMDepotException.getPlanTimeBegin(),
						SystemConstants.dateformat6));
				tplDepotException.setPlanTimeEnd(DateUtils.toDate(tplMDepotException.getPlanTimeEnd(),
						SystemConstants.dateformat6));
				tplDepotException.setRemark(tplMDepotException.getRemark());
				tplDepotException.setCreateDate(date);
				messageDao.insert(tplDepotException, TplDepotException.class);
			} else if (tplMDepotException.getOperFlag().trim().equals("U")) {
				System.out.println("修改业务信息");
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size() > 0) {
					TplDepotException tplDepotException = (TplDepotException) list.get(0);
					tplDepotException.setCheckType(tplMDepotException.getCheckType());
					tplDepotException.setDeviceType(tplMDepotException.getDeviceType());
					tplDepotException.setDevName(tplMDepotException.getDevName());
					tplDepotException.setRemark(tplMDepotException.getRemark());
					messageDao.update(tplDepotException,TplDepotException.class);
				}
			} else if (tplMDepotException.getOperFlag().trim().equals("D")) {
				System.out.println("删除业务信息");
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size() > 0) {
					TplDepotException tplDepotException = (TplDepotException) list.get(0);
					Long[] ids = { tplDepotException.getId() };
					messageDao.delete(ids, TplDepotException.class);
				}
			}
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("接收定检修计划确认或异常反馈时信息电文异常,库区编号为："
					+ tplMDepotException.getStockCode() + "，检修类型为："
					+ tplMDepotException.getCheckType() + "，设备类型为："
					+ tplMDepotException.getDeviceType());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("结束");
	}

	// 装载计划任务包接收反馈[XZXL21]
	public void sendTplMLoadPlanResponse(
			TplMLoadPlanResponse tplMLoadPlanResponse) throws Exception {
		Date date = new Date();
		tplMLoadPlanResponse.setCreateDate(date);
		System.out.println("新增装载计划任务包接收反馈电文信息");
		messageDao.insert(tplMLoadPlanResponse, TplMLoadPlanResponse.class);
		logger.info("---发送装载计划任务包接收反馈信息：" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_LOAD_PLAN_RESPONSE, null,
				tplMLoadPlanResponse);
		System.out.println("结束");
	}

	// 社会服务商装载计划配车信息[XZXL22]
	public void sendTplMPlanAllocationCar(
			TplMPlanAllocationCar tplMPlanAllocationCar) throws Exception {
		Date date = new Date();
		tplMPlanAllocationCar.setCreateDate(date);
		System.out.println("新增社会服务商装载计划配车信息");
		messageDao.insert(tplMPlanAllocationCar, TplMPlanAllocationCar.class);
		logger.info("---发送社会服务商装载计划配车信息：" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_PLAN_ALLOCATION_CAR, null,
				tplMPlanAllocationCar);
		System.out.println("结束");
	}

	// 自提合同实际交货地信息[XZXL23]
	public void sendTplMFactSinceContract(
			TplMFactSinceContract tplMFactSinceContract) throws Exception {
		System.out.println("新增自提合同实际交货地信息电文信息");
		messageDao.insert(tplMFactSinceContract, TplMFactSinceContract.class);
		logger.info("---发送自提合同实际交货地信息：" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_FACT_SINCE_CONTRACT, null,
				tplMFactSinceContract);
		System.out.println("结束");
	}

	// 二维码扫描结果信息[XZXL24]
	public void sendTplMQrCodeScanResult(
			TplMQrCodeScanResult tplMQrCodeScanResult) throws Exception {
		Date date = new Date();
		tplMQrCodeScanResult.setCreateDate(date);
		System.out.println("新增二维码扫描结果信息");
		messageDao.insert(tplMQrCodeScanResult, TplMQrCodeScanResult.class);
		logger.info("---发送二维码扫描结果信息信息：" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_QR_CODE_SCAN_RESULT, null,
				tplMQrCodeScanResult);
		System.out.println("结束");
	}

	// 社会服务商车辆签到信息[XZXL26]
	public void sendTplMProviderCarSign(TplMProviderCarSign tplMProviderCarSign)
			throws Exception {
		Date date = new Date();
		tplMProviderCarSign.setCreateDate(date);
		System.out.println("新增社会服务商车辆签到信息");
		messageDao.insert(tplMProviderCarSign, TplMProviderCarSign.class);
		
		tplMProviderCarSign.setSignInNo(String.valueOf(tplMProviderCarSign.getId()));//签到序号
		messageDao.update(tplMProviderCarSign, TplMProviderCarSign.class);
		logger.info("---发送社会服务商车辆签到信息：" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_PROVIDER_CAR_SIGN, null,
				tplMProviderCarSign);
		System.out.println("结束");
	}

	// 社会服务商自提车车道到位[XZXL25]
	public void sendTplMServiceCarInPlace(
			TplMServiceCarInPlace tplMServiceCarInPlace) throws Exception {
		Date date = new Date();
		tplMServiceCarInPlace.setCreateDate(date);
		System.out.println("社会服务商自提车车道到位信息");
		messageDao.insert(tplMServiceCarInPlace, TplMServiceCarInPlace.class);
		logger.info("---社会服务商自提车车道到位 信息：" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SERVICE_CAR_IN_PLACE, null,
				tplMServiceCarInPlace);
		System.out.println("结束");
	}
	
	/**
	 * 大院还原离库实绩原信息（捆包、计划、车次、调度计划、装载计划）
	 * @param tplTransSeq
	 * @param listOld
	 * @param tplMActLoadPlan
	 * @throws Exception
	 */
	private void restoreXZXL55(TplTransSeq tplTransSeq,List listOld,TplMActLoadPlan tplMActLoadPlan) throws Exception {
		Date date = new Date();
		DecimalFormat df = new DecimalFormat("0.000000");
		BigDecimal actGrossWeight = new BigDecimal("0");// 实际装车毛重
		BigDecimal actNetWeight = new BigDecimal("0");// 实际装车净重
		Long actCount = new Long(0);// 实际装车件数
		if (tplTransSeq.getId() != null) {
			if (listOld != null && listOld.size() > 0) {
				Long[] itemIds = new Long[listOld.size()];
				for (int i = 0; i < listOld.size(); i++) {
					TplTransPack tplTransPack = (TplTransPack) listOld.get(i);
					itemIds[i] = tplTransPack.getId();
					
					tplTransPack.setPackStatus("0");
					tplTransPack.setProStatus("10");
					tplTransPack.setSeqId(null);
					tplTransPack.setControlId(null);
					// 更新原装载的实绩捆包状态（0：未装载，10：待派单）
					messageDao.update(tplTransPack, TplTransPack.class);
				}
			/*	// 更新原装载的实绩捆包状态（0：未装载，10：待派单）
				for (int j = 0; j < itemIds.length; j++) {
					String updateSql = "update TPL_TRANS_PACK t SET t.PACK_STATUS='0',t.PRO_STATUS='10',t.SEQ_ID=NULL,t.CONTROL_ID =null WHERE t.ID="
							+ itemIds[j]
							+ " and t.SEQ_ID = "
							+ tplTransSeq.getId();
					messageDao.updateContractReplaceStatus(updateSql);
				}*/
				// 根据材料信息查询出所对应的装载计划
				List listTransPlanId = messageDao.queryTransPlanId(itemIds);
				if (listTransPlanId != null && listTransPlanId.size() > 0) {
					for (int i = 0; i < listTransPlanId.size(); i++) {
						String transPlanId = (String) listTransPlanId.get(i);

						TplTransPlan tplTransPlan = new TplTransPlan();
						System.out.println("计划号：：：：：：：：" + transPlanId);
						List listCount = messageDao.tplTransPlanCount(transPlanId);
						if (null != listCount && listCount.size() > 0) {
							Object[] obj = (Object[]) listCount.get(0);
							actGrossWeight = null == obj[0] ? new BigDecimal("0") : new BigDecimal(obj[0].toString());
							actNetWeight = null == obj[1] ? new BigDecimal("0") : new BigDecimal(obj[1].toString());
							actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
						}
						tplTransPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
						tplTransPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
						tplTransPlan.setActCount(actCount);
						tplTransPlan.setStatus("0");
						tplTransPlan.setSeqId(null);
						tplTransPlan.setTransPlanId(transPlanId);
						messageDao.updateTplTransPlan(tplTransPlan);
					}
				}
			}

			List listCount = messageDao.tplTransSeqLeaveCount(tplTransSeq.getId().toString());
			Object[] obj = (Object[]) listCount.get(0);
			actGrossWeight = null == obj[0] ? new BigDecimal("0") : new BigDecimal(df.format(obj[0]));
			actNetWeight = null == obj[1] ? new BigDecimal("0") : new BigDecimal(df.format(obj[1]));
			actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
			// 更新车次状态
			tplTransSeq.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
			tplTransSeq.setActNetWeight(Double.valueOf(actNetWeight.toString()));
			tplTransSeq.setActCount(actCount);// 实际件数
			tplTransSeq.setPlanGrossWeight(Double.valueOf(actGrossWeight.toString()));
			tplTransSeq.setPlanNetWeight(Double.valueOf(actNetWeight.toString()));
			tplTransSeq.setPlanCount(actCount);// 计划件数
			tplTransSeq.setStatus("1");// 执行状态
			tplTransSeq.setProStatus("06");// 到位
			tplTransSeq.setUploadTime(date);//修改时间
			messageDao.update(tplTransSeq, TplTransSeq.class);

			// 更新汽运调度计划
			// 根据车次号、装计划号查询出此装计划下的实绩毛重、净重、件数
			List loadPlanSumList = messageDao.tplTransLoanPlanCount(tplMActLoadPlan);
			Object[] objSum = (Object[]) loadPlanSumList.get(0);
			actGrossWeight = null == objSum[0] ? new BigDecimal("0")
					: new BigDecimal(df.format(objSum[0]));
			actNetWeight = null == objSum[1] ? new BigDecimal("0")
					: new BigDecimal(df.format(objSum[1]));
			actCount = null == objSum[2] ? new Long(0) : new Long(objSum[2].toString());
			TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
			tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
			tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
			tplTransTruckDisPlan.setActCount(actCount);
			tplTransTruckDisPlan.setStatus("10");
			tplTransTruckDisPlan.setTransPlanId(tplMActLoadPlan.getLoadingPlanNo());
			messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);

			// 更新装载计划
			TplTransLoadPlan tplTransLoadPlan = new TplTransLoadPlan();
			tplTransLoadPlan.setLoadingPlanNo(tplMActLoadPlan.getLoadingPlanNo());
			tplTransLoadPlan.setActGrossWeight(actGrossWeight);
			tplTransLoadPlan.setActNetWeight(actNetWeight);
			tplTransLoadPlan.setActCount(actCount);
			tplTransLoadPlan.setSeqId(tplTransSeq.getId());
			tplTransLoadPlan.setStatus("1");//执行中
			messageDao.updateTplTransLoadPlan(tplTransLoadPlan);
		}
	}
	// ******************************宝山基地钢制品物流效率提升end********************************************************
	/**
	 * 接收运输站点电文[XLXZ64]
	 */
	public void receiveMessageTransStation(TplMessageTransStation messageTransStation) throws Exception {
		System.out.println("StationSeqId==== "+messageTransStation.getStationSeqId());
		if(null==messageTransStation.getStationSeqId() || "".equals(messageTransStation.getStationSeqId())){
			System.out.println("==================XLXZ64==StationSeqId is null====================== ");
			return ;
		}
		System.out.println("=========================插入电文XLXZ64=============== ");
		TplMessageTransStation message =messageDao.insertMessageTransStation(messageTransStation);
		System.out.println("OperationType==== "+message.getOperationType());
		if("10".equals(message.getOperationType())){
			String  queryHql = " from TplTransStation as t where 1=1 and t.stationSeqId = '"+messageTransStation.getStationSeqId()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (null !=list  && list.size()>0){
				System.out.println("=========================业务表已有该信息不能插入=============== ");
				return;
			}
			System.out.println("=========================插入电文XLXZ64业务表=============== ");
			messageDao.insertTransStation(messageTransStation);
		}else if("20".equals(message.getOperationType())){
			String  queryHql = " from TplTransStation as t where 1=1 and t.stationSeqId = '"+messageTransStation.getStationSeqId()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (null == list || list.size()==0){
				System.out.println("=========================业务表没有该信息不能修改=============== ");
				return;
			}
			TplTransStation transStation = new TplTransStation();
			TplTransStation info = (TplTransStation)list.get(0);
			transStation.setId(info.getId());
			transStation.setStationSeqId(messageTransStation.getStationSeqId());
			transStation.setStationId(messageTransStation.getStationId());
			transStation.setStationName(messageTransStation.getStationName());
			transStation.setStationStatus(messageTransStation.getStationStatus());
			transStation.setTransType(messageTransStation.getTransType());
			transStation.setPrivateLineId(messageTransStation.getPrivateLineId());
			transStation.setPrivateLineName(messageTransStation.getPrivateLineName());
			transStation.setVehicleStationName(messageTransStation.getVehicleStationName());
			transStation.setShipPortName(messageTransStation.getShipPortName());
			transStation.setStationAddr(messageTransStation.getStationAddr());
			transStation.setArea(messageTransStation.getArea());
			transStation.setProvince(messageTransStation.getProvince());
			transStation.setCity(messageTransStation.getCity());
			transStation.setModifyUserId(messageTransStation.getModifyUserId());
			String modifyDateStr = messageTransStation.getModifyDate();
			Date modifyDate =null;
			if(null != modifyDateStr && !"".equals(modifyDateStr)){
				modifyDate=DateUtils.toDate(modifyDateStr, SystemConstants.dateformat7);
			}
			transStation.setModifyDate(modifyDate);
			transStation.setRemark(messageTransStation.getRemark());
			transStation.setPrivateLineFullname(messageTransStation.getPrivateLineFullname());
			System.out.println("=========================修改电文XLXZ64业务表=============== ");
			messageDao.updateTransStation(transStation);
		}
	}
	
	/**
	 * 自提转铁运 XLXZ62
	 * @param tplMExtractionTrainPlan
	 * @throws Exception
	 */
	public void receiveTplExtractionTrainPlan (TplMExtractionTrainPlan tplMExtractionTrainPlan) throws Exception {
		
		if (tplMExtractionTrainPlan.getReqNum().intValue() > 0) {
			tplMExtractionTrainPlan.setExtractionTrainItemSet(BeanConvertUtils.strToBean(tplMExtractionTrainPlan.getDetail(), CLASS_TPL_M_EXTRACTION_TRAIN_ITEM));
		} else {
			tplMExtractionTrainPlan.setExtractionTrainItemSet(new HashSet());
		}
		Iterator itPack = tplMExtractionTrainPlan.getExtractionTrainItemSet().iterator();
		while (itPack.hasNext()) {
			TplMExtractionTrainItem extractionTrainItem = (TplMExtractionTrainItem) itPack.next();
			if (null ==extractionTrainItem.getPackNo() || "".equals(extractionTrainItem.getPackNo())) {
				itPack.remove();
			}
		}
		System.out.println("=======电文明细长度======："+tplMExtractionTrainPlan.getExtractionTrainItemSet().size());
		String opFlag = tplMExtractionTrainPlan.getProcType();
		String  queryHql = " from TplExtractionTrainPlan as t where 1=1 and t.reqPlanNo = '"+tplMExtractionTrainPlan.getReqPlanNo()+"' and t.reqLotNo = '"+tplMExtractionTrainPlan.getReqLotNo()
		+"')";
		System.out.println("================接收自提转铁运电文表XLXZ62====================");
		TplMExtractionTrainPlan msgPlan = messageDao.insertMessageExtractionTrainPlan(tplMExtractionTrainPlan);
		tplMExtractionTrainPlan.setId(msgPlan.getId());
		System.out.println("================自提转铁运业务表插入====================");
		if("I".equals(opFlag)){
			List list =messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				System.out.println("================更新业务表====================");
				messageDao.insertExtractionTrainPlan(tplMExtractionTrainPlan, "1");
			}else{
				System.out.println("================新增业务表====================");
				messageDao.insertExtractionTrainPlan(tplMExtractionTrainPlan, "0");
			}
		}else if("D".equals(opFlag)){
			messageDao.deleteExtractionTrainPlan(tplMExtractionTrainPlan);
		}
		System.out.println("================电文XLXZ62接收成功====================");
	}
}
