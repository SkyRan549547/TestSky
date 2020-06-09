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
	 * �ᵥ���� 10 �����ͬ���� 20 �䵱��ͬ���� 30 ת���ᵥ 40 �⹺�����ᵥ 41 ���������ᵥ 42 �ӹ������ᵥ 43 �ɹ������ᵥ
	 * 44 �ɹ�ת���ᵥ 45 �ӹ��ͳ��ᵥ 46 �ɹ�ת���ᵥ 49 �ڹ���ת���ᵥ 51 ó�׵�Ԫ�ɹ��ᵥ 52 ó�׵�Ԫ�����ᵥ 53
	 * ó�׵�Ԫת���ᵥ 61 ����ת���ᵥ 62 ֱ���������ᵥ 63 ����ת���ᵥ 64 ֱ���̲����ᵥ
	 */

	// // ���������ҵ�ƻ�
	// private static final String MESSAGE_RECEIVE_N0_STOCK_IN = "XL2201";
	//	
	// // ���ճ�����ҵ�ƻ�
	// private static final String MESSAGE_RECEIVE_N0_STOCK_OUT = "XL2202";
	//	
	// // ���տ�������ҵ�ƻ�
	// private static final String MESSAGE_RECEIVE_N0_STOCK_ADJUST = "XL7002";
	//	
	// // ���տ���̵���ҵ�ƻ�
	// private static final String MESSAGE_RECEIVE_N0_STOCK_CHECK = "XL7001";
	//	
	// // ������ҵ������Ϣ
	// private static final String MESSAGE_RECEIVE_N0_FEE = "XL4801";
	//	
	// // ���շ��ó�������
	// private static final String MESSAGE_RECEIVE_N0_FEE_CANCEL = "XL4805";
	//	
	// // ���շ�Ʊ̧ͷ�������
	// private static final String MESSAGE_RECEIVE_N0_INVOICE_TITLE_CHANGE =
	// "XL4804";
	//	
	// // ����������ҵ�ƻ�����
	// private static final String MESSAGE_RECEIVE_N0_TRANS = "XL2203";
	//	
	// // �����ճ�����
	// private static final String MESSAGE_RECEIVE_N0_DOCUMENT_RECEIVE =
	// "XL5205";
	// // ���������۵���
	// private static final String MESSAGE_RECEIVE_N0_EVALUATION = "XL5204";
	// ���������ҵʵ������
	private static final String MESSAGE_SEND_N0_STOCK_IN = "XZST01";

	// ���ͳ�����ҵʵ������
	private static final String MESSAGE_SEND_N0_STOCK_OUT = "XZST02";

	// ���Ϳ�����ʵ������
	private static final String MESSAGE_SEND_N0_STOCK_ADJUST = "XZST03";

	// ���ͳ�������鵥����
	private static final String MESSAGE_SEND_N0_STOCK_OUT_EPICK = "XZST05";
	
	//���ͷɵ�ת��Ȩ����
	private static final String MESSAGE_SEND_N0_TRANS_FLY= "XZTR11";
	
	//��������Ŵ����ջ�ȷ����Ϣ
	private static final String MESSAGE_SEND_NO_WAIGAOQIAO_RECEIVED = "XZST06";
	
	//����װ���嵥
	private static final String MESSAGE_TRANS_LOADING_MAIN = "XZTR02";
	
	//���͵����嵥
	private static final String MESSAGE_TRANS_ARRIVAL_MAIN = "XZTR04";
	
	//���ͷ�������
	private static final String MESSAGE_TRANS_START_RESULT = "XZTR03";
	
	//���ͷ�������
	private static final String MESSAGE_TRANS_RESULT = "XZTR12";
	
	//���ʹ�����̬����
	private static final String MESSAGE_TRANS_SHIP_TRENDS = "XZTR07";
	
	//����ǩ�շ���
	private static final String MESSAGE_TRANS_SIGN_RESULT = "XZTR05";
	
	//��ҵ�ƻ�����Ӧ��
	private static final String MESSAGE_TRANS_PLAN_RESULT = "XZTR06";

	// �ϱ��������ϵ���
	private static final String MESSAGE_SEND_N0_TRUCK_MANAGE = "XZRC04";

	// �ϱ��������ϵ���
	private static final String MESSAGE_SEND_N0_SHIP_MANAGE = "XZRC05";

	// �����̿���������Ϣ����
	private static final String MESSAGE_SEND_N0_PROVIDER = "XZRC01";

	// �����̿�������ֿ���Ϣ����
	private static final String MESSAGE_SEND_N0_PROVIDER_WAREHOUSE = "XZRC02";

	// �����̿��������˺���Ϣ����
	private static final String MESSAGE_SEND_N0_PROVIDER_ACCOUNT = "XZRC03";

	// ������̬��Ϣ����
	private static final String MESSAGE_SEND_N0_SHIP_TRACE = "XZFE06";

	// ͨ����Ϣ�޸��������
	private static final String MESSAGE_SEND_N0_MODIFY_APPLY = "XZRC09";

	// �ϱ��ִ���������
	private static final String MESSAGE_SEND_N0_WPROVIDER_ABILITY = "XZRC07";

	// �ϱ�������������
	private static final String MESSAGE_SEND_N0_SHIP_ABILITY = "XZRC06";

	// �����ճ����ķ���
	private static final String MESSAGE_SEND_N0_DOCUMENT_RETURN = "XZRC08";

	// ���ͷ�Ʊ��������
	private static final String MESSAGE_SEND_N0_INVOICE_APPLY = "XZFE01";

	// ���ͷ�Ʊ����
	private static final String MESSAGE_SEND_N0_INVOICE_CANCEL = "XZFE02";

	// ���ͷ��ó����ظ�
	private static final String MESSAGE_SEND_N0_FEE_CANCEL_REPLY = "XZFE03";

	// ���ͱ�����Ϣ
	private static final String MESSAGE_SEND_N0_INSURANCE_BILL = "XZFE04";

	// ����������ҵʵ��;
	private static final String MESSAGE_SEND_N0_TRANS_REAL = "XZTR01";

	// ���ͳ���װ���嵥
	private static final String MESSAGE_SEND_N0_TRANS_LOADING = "XZFE05";
	
	// ���ͳ���װ���嵥 ���ܽӿ� ���Ͷ�������
	private static final String MESSAGE_SEND_N1_TRANS_LOADING = "XZTZ01";

	// ����̵�ʵ��
	private static final String MESSAGE_SEND_N0_STOCK_CHECK = "XZST04";

	private static final String MESSAGE_SEND_N0_EPICK_PROMISEE = "XZRC10";
	
	//�������ֿ��λ�����Ϣ����
	private static final String MESSAGE_SEND_N0_WAREHOUSE_POSCHAN = "XZST07";
	
	//���ͽ���֤��Ϣ����
	private static final String MESSAGE_SEND_ENTRY_PERMIT = "XZTR08";
	
	//������������ͼƬ���� add by xty 2015/12/11
	private static final String MESSAGE_SEND_QUALITY_PICTURES = "XZTR13";
	
	//�����ֻ���ע����� add by xty 2015/12/11
	private static final String MESSAGE_SEND_PHONE_REGISTERED = "XZTR14";
	
	//���˳�Ƥ װж������ add wyx  
	private static final String MESSAGE_SEND_TRAINS_LOADING = "XZTR15";
	
	//���˳�Ƥ��;��̬ add wyx  
	private static final String MESSAGE_SEND_TRAINS_ON_LOAD = "XZTR16";

	//������ҵ�ƻ� add
	private static final String  MESSAGE_SEND_TRAINS_MTM_LOAD="XZXL02";
	
	//���ȼƻ���Ϣ
	private static final String  MESSAGE_SEND_TRANS_DIS_PLAN="XZXL03";
	
	//������Ϣ
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
	 * *********************************�ִ�ҵ��ӿ�***************************************
	 * 
	 * 1 ���������ҵ�ƻ� 2 ���ճ�����ҵ�ƻ� 3 ���տ�������ҵ�ƻ� 4 ���տ���̵���ҵ�ƻ�
	 * 
	 * 5 ���������ҵʵ�� 6 ���ͳ�����ҵʵ�� 7 ���Ϳ�������ҵʵ�� 8 ���Ϳ���̵���ҵʵ��
	 * 
	 * 9 ����׼����Ϣ 10 ����׼�������Ϣ 11 �����뵥�����Ϣ 12 �������ֿ��λ�����Ϣ
	 * 
	 * **********************************************************************************
	 */
	
	
	// ���պ�ͬ�滻��Ϣ
	public void receiveContractReplace(TplMsgContractReplace tplMsgContractReplace) throws Exception {
		System.out.println(""+tplMsgContractReplace.getResourceSign());
		try {
			if (tplMsgContractReplace.getActReplaceCount().intValue() > 0) {
				tplMsgContractReplace.setMsgContractItemSet(BeanConvertUtils.strToBean(tplMsgContractReplace.getDetail(), CLASS_CONTRACT_REPLACE_ITEM));
			} else {
				tplMsgContractReplace.setMsgContractItemSet(new HashSet());
			}

			// �����model
			Iterator it = tplMsgContractReplace.getMsgContractItemSet().iterator();
			while (it.hasNext()) {
				TplMsgContractItem msgContractItem = (TplMsgContractItem) it.next();
				if ("".equals(msgContractItem.getPackId())) {
					it.remove();
				}
			}
			//�ж��ظ�
			String checkRepeatSql = "select id from tpl_msg_contract_replace t where t.pre_order_num = '" 
				+ tplMsgContractReplace.getPreOrderNum()
				+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()
				+ "' and t.replace_id = '"+ tplMsgContractReplace.getReplaceId()
				+ "' and STATUS ='0' ";
			if (messageDao.queryIdBySql(checkRepeatSql) != null){
				return;
			}
			// ��ʼ������ֵ
			String queryStockInIdSql = "select id from tpl_msg_contract_replace t where t.replace_id = '"
				+ tplMsgContractReplace.getReplaceId() + "' and t.pre_order_num = '" + tplMsgContractReplace.getPreOrderNum()
				+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()
				+ "' and STATUS is null  order by ID DESC";
			Long id = messageDao.queryIdBySql(queryStockInIdSql);
			tplMsgContractReplace.setId(id);
			System.out.println("��ʼ����ͬ�滻tpl_msg_contract_replace--id��"+id);
	
			// ��ѯ�üƻ��Ѿ������������������ж����ν����Ƿ������
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from tpl_msg_contract_item t where t.main_id = "
					+ tplMsgContractReplace.getId());
			System.out.println("��ѯ�üƻ���tpl_msg_contract_item���Ѿ������������������ж����ν����Ƿ������"+itemCount);
			// �ж��ظ����������ͬ�����͵�����
			if (tplMsgContractReplace.getMsgContractItemSet() != null && tplMsgContractReplace.getMsgContractItemSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = tplMsgContractReplace.getMsgContractItemSet().iterator();
				String packId = "";
				if (itDouble != null) {
					TplMsgContractItem tplMsgContractItem = (TplMsgContractItem) itDouble.next();
					packId = tplMsgContractItem.getPackId();
				}
				// �жϸüƻ��µ��������Ƿ��Ѿ����
				Long doublePackCount=null;
				
				doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from tpl_msg_contract_item t "
						+ " where t.pack_id = '" + packId + "'   and t.main_id = " + id);
						
				// ����������Ѿ���⣬�򲻴�����������
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			System.out.println("messageDao.insert��tpl_msg_contract_replace����"+tplMsgContractReplace.getPreOrderNum());
			messageDao.insertMsgContractReplace(tplMsgContractReplace);

			// ���������������Ҫ�õ�id
			if (id == null) {
				String queryIdSql = "select id from tpl_msg_contract_replace t where t.replace_id = '"
					+ tplMsgContractReplace.getReplaceId() + "' and t.pre_order_num = '" + tplMsgContractReplace.getPreOrderNum()
					+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()
					+ "' and STATUS is null  order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				tplMsgContractReplace.setId(id);
			}
			// �жϽ����Ƿ�����������������ҵ���
			if (tplMsgContractReplace.getActReplaceCount().intValue()== tplMsgContractReplace.getMsgContractItemSet().size() + itemCount.intValue()) {
				
				String sqlString = "update tpl_msg_contract_replace t set STATUS = '0' where  t.replace_id = '"
					+ tplMsgContractReplace.getReplaceId() + "' and t.pre_order_num = '" + tplMsgContractReplace.getPreOrderNum()
					+ "' and t.rep_order_num = '" + tplMsgContractReplace.getRepOrderNum()+"'";
				System.out.println("sqlString(���º�ͬ�滻��ɱ�־):"+sqlString);
				messageDao.updateContractReplaceStatus(sqlString);
				
				//���ñ�ӯͨ�ӿ�---------------------------------------------------------------------
				//�������ݽ���
				TplMessageContractReplace tplMessageContractReplace=initMsgContractReplace(tplMsgContractReplace);
				//�ӱ�LIST����
				List list = initMsgContractReplaceItem(tplMsgContractReplace);
				System.out.println("��ʼ�����������ñ�ӯͨ�ӿڷ�����sendChangeContract��list.size():"+list.size());
				spotGoodsClientService.sendChangeContract(tplMessageContractReplace,list);
				//----------------------------------------------------------------------------------
				
			}
			
		} catch (Exception e) {
			String errTitle = "��ͬ�滻�䵱��:" + tplMsgContractReplace.getReplaceId();
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

	// ���������ҵ�ƻ�
	public void receiveStockInPlan(TplMessageStockInPlan stockInPlan) throws Exception {
		try {
			//yesimin  20150508  տ���ᵥ�Ž�ȡ
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
			 * �������쵥Ԫ�����壺���������0��֤��ȫ��Ҫ��壬����Ҫ������ϸ��
			 * modified by Forest 20080514
			 */
			if (stockInPlan.getPlanCount().intValue() > 0) {
				stockInPlan.setStockInItemSet(BeanConvertUtils.strToBean(stockInPlan.getDetail(), CLASS_STOCK_IN_ITEM));
			} else {
				stockInPlan.setStockInItemSet(new HashSet());
			}
			// �����model
			Iterator it = stockInPlan.getStockInItemSet().iterator();
			while (it.hasNext()) {
				TplMessageStockInItem stockInItem = (TplMessageStockInItem) it.next();
				if ("".equals(stockInItem.getPlanPutinId())) {
					it.remove();
				}
			}

			// �ж��ظ�������Ǻ�壬���ж�Σ����ж��ظ���
			if (OPERATION_TYPE_NEW.equals(stockInPlan.getOperationType())) {
				String checkRepeatSql = "select id from TPL_MESSAGE_STOCK_IN_PLAN where PLAN_PUTIN_ID = '"
						+ stockInPlan.getPlanPutinId() + "' and OPERATION_TYPE = '" + stockInPlan.getOperationType()
						+ "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}

			// ��ʼ������ֵ
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
			stockInPlan.setUnitName(AcquireAgencyName.getAgencyName(stockInPlan.getUnitId()));//��ȡί�л�������20110308
			//������ҵ��,���������������,ֻ���ڳ���ⷢ��
			String pickType = StringUtil.trimStr(stockInPlan.getPickType());
			if(SystemConfigUtil.TPL_STOCK_PLAN_PICK_TYPE_PURCHASE_IN.contains(pickType)){
				stockInPlan.setExtendWarehouseFlag(null);
				stockInPlan.setExtendWarehouseCode(null);
			}
			
			// ��ѯ�üƻ��Ѿ������������������ж����ν����Ƿ������
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_STOCK_IN_ITEM t where t.PLAN_ID = "
					+ stockInPlan.getId());
			//��ȡ������������һ��ͬ�źͺ�ͬ���������ж��Ƿ񷢸���ӯͨ add by ljh 2015-08-03 23:31:48
			String packOrderType = "";
			String orderNumSub =""; 
			// �ж��ظ����������ͬ�����͵����⣬��ʱ���ɷ��� add by zhengfei 20080128
			if (stockInPlan.getStockInItemSet() != null && stockInPlan.getStockInItemSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = stockInPlan.getStockInItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageStockInItem stockInItem = (TplMessageStockInItem) itDouble.next();
					someOnePack = stockInItem.getPackId();
					productClass = stockInItem.getOrderNum().substring(0, 1);
					packOrderType = stockInItem.getPackOrderType();//�ж��Ƿ񷢸�BYT
					orderNumSub = stockInItem.getOrderNum().substring(2, 3);//�ж��Ƿ񷢸�BYT
				}
				// �жϸüƻ��µ��������Ƿ��Ѿ����
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
				
				// ����������Ѿ���⣬�򲻴�����������
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			messageDao.insertTplStockInPlan(stockInPlan);

			// ���������������Ҫ�õ�id
			if (id == null) {
				String queryIdSql = "select id from TPL_MESSAGE_STOCK_IN_PLAN where PLAN_PUTIN_ID = '" + stockInPlan.getPlanPutinId()
						+ "' and OPERATION_TYPE = '" + stockInPlan.getOperationType() + "'  and STATUS is null order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				stockInPlan.setId(id);
			}

			// �жϼƻ��Ƿ�����������������ҵ���
			if (stockInPlan.getPlanCount().intValue() == stockInPlan.getStockInItemSet().size() + itemCount.intValue()) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_IN_PLAN set STATUS = '0' where  PLAN_PUTIN_ID = '"
						+ stockInPlan.getPlanPutinId() + "'  and OPERATION_TYPE = '" + stockInPlan.getOperationType() + "'");

				
				/**
				 * ��������
				 * ����ϵͳ(���쵥Ԫ)����ļƻ������·�����:��������쵥Ԫ�����ļƻ�����������������ļƻ�3PL�Ѿ����ڣ���֤�����ڱ��μƻ���ߵ�������Ҫ���ġ�
				 */
				if (OPERATION_TYPE_NEW.equals(stockInPlan.getOperationType())) {
					String checkPlanExist = "select id from TPL_STOCK_IN_PLAN where PLAN_PUTIN_ID = '" + stockInPlan.getPlanPutinId()
							+ "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doStockInPlanRedOper(stockInPlan, "10");
					} else {
						messageDao.insertTplStockInByBatch(stockInPlan);
						
						//����߲ĺ͹���-����
						messageDao.checkTplStockInPlanPick(stockInPlan);
					}
					//���ñ�ӯͨ�ӿ�-���ƻ�-sendInPlan added ljh 2015��7��16�� 13:33:30-----------------
					//�������ݽ���
					try {
						if(packOrderType.startsWith("QY")||orderNumSub.equals("J")||orderNumSub.equals("W")){
							stockInPlan.setOperationType("10");
							TplMessageBytStockInPlan tplMessageBytStockInPlan=initStockInPlan(stockInPlan);
							//�ӱ�LIST����
							List list = initStockInItem(stockInPlan);
							System.out.println("LIST OVER!");
							spotGoodsClientService.sendInPlan(tplMessageBytStockInPlan,list);
						}
					} catch (Exception e) {
						String errTitle = "���ñ�ӯͨ�ӿ�-���ƻ���������:" + stockInPlan.getPlanPutinId();
						setLogMessage(e, errTitle);
					}
					
					//----------------------------------------------------------------------------------
				} else if (OPERATION_TYPE_RED.equals(stockInPlan.getOperationType())) {
					/**
					 * ������
					 * 1.����ϵͳ�ĺ�壺�Ǽƻ�ȫ�������Ժ�������������ȷ�ļƻ�����֧�ֵ������ϳ���-----�ᵥ���ͣ�6��ͷ�ġ�
					 * 2.�ܿ�ϵͳ�ĺ�壺֧�ֵ������ϵĺ��-----�ᵥ���ͣ���6��ͷ���ᵥ����������ᵥ
					 * 
					 */
					if ("6".equals(stockInPlan.getPickType().substring(0, 1))||"7".equals(stockInPlan.getPickType().substring(0, 1))) {
						messageDao.doStockInPlanRedOper(stockInPlan, "00");
					} else {
						messageDao.doStockInPlanRedOper(stockInPlan, null);
					}
					try {
						//���ñ�ӯͨ�ӿ�-���ƻ�-sendInPlan added ljh 2015��7��16�� 13:33:30-----------------
						//�������ݽ���
						if(packOrderType.startsWith("QY")||orderNumSub.equals("J")||orderNumSub.equals("W")){
							stockInPlan.setOperationType("00");
							TplMessageBytStockInPlan tplMessageBytStockInPlan=initStockInPlan(stockInPlan);
							//�ӱ�LIST����
							List list = initStockInItem(stockInPlan);
							spotGoodsClientService.sendInPlan(tplMessageBytStockInPlan,list);
						}
					} catch (Exception e) {
						String errTitle = "���ñ�ӯͨ�ӿ�-���ƻ�(���):" + stockInPlan.getPlanPutinId();
						setLogMessage(e, errTitle);
					}
					
					/**
					 * ǿ�ƽ᰸ ����ϵͳ��ǿ�ƽ᰸��ǿ�ƽ᰸�Ĳ�������Ҫ�����ģ����ڽ᰸�ڵĲ���ȫ������������������90��ͷ��
					 */
				} else if (OPERATION_FORCE_OVER.equals(stockInPlan.getOperationType())) {
					//�̱�����ǿ�ƽ᰸���ƻ���ҵ���ǿ�ƽ᰸
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
						 * ׷�Ӳ���:1.������������
						 *         2.������������
						 */
						messageDao.insertTplStockInByApplend(stockInPlan,"20",updateId);//��������������
					} else {
						messageDao.insertTplStockInByApplend(stockInPlan,"10",null);//���������ӱ�
					}
				}
				TplMessageTransPlanResult tPlanResult=new TplMessageTransPlanResult();
				tPlanResult.setCreateTime(new Date());
				tPlanResult.setOprationType(stockInPlan.getOperationType());
				tPlanResult.setRecordeId(stockInPlan.getPlanPutinId());
				tPlanResult.setRecordeTime(new Date());
				tPlanResult.setRecordeType("20");
				tPlanResult.setRemark("");
				this.sendTransPlanResult(tPlanResult);//���ͼƻ�Ӧ����� 
				System.out.println("���ƻ��ţ�======"+stockInPlan.getPlanPutinId());
			}
			
			
		} catch (Exception e) {
			String errTitle = "�����ҵ�ƻ�:" + stockInPlan.getPlanPutinId();
			setLogMessage(e, errTitle);
		} 
	}
	private List initStockInItem(TplMessageStockInPlan stockInPlan) throws Exception {
		//��ѯ�����ƻ���������ϸ������LIST����BYT
	
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
		    	tplMessageBytStockInItem.setProductName((String)item.get("productName"));//Ʒ��
		    	tplMessageBytStockInItem.setCompanyCode((String)item.get("companyCode"));
		    	tplMessageBytStockInItem.setProducingAreaName((String)item.get("producingAreaName"));//����
		    	System.out.println("producingAreaName:::"+(String)item.get("producingAreaName"));
		    	tplMessageBytStockInItem.setShopsignName((String)item.get("shopsignName"));//����
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
	 * ��¼��������
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

	// ���ճ�����ҵ�ƻ�����Ϣ
	public void receiveStockOutPlan(TplMessageStockOutPlan stockOutPlan) throws Exception {
		try {
			System.out.println("���ճ�����ҵ�ƻ�����Ϣ��ʼ"+new Date()+stockOutPlan.getBillId());
			// yesimin  20150508  տ���ᵥ�Ž�ȡ
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
			 * �������쵥Ԫ�����壺���������0��֤��ȫ��Ҫ��壬����Ҫ������ϸ��
			 * modified by Forest 20080514
			 */
			if (stockOutPlan.getPlanCount().intValue() > 0) {
				stockOutPlan.setStockOutItemSet(BeanConvertUtils.strToBean(stockOutPlan.getDetail(), CLASS_STOCK_OUT_ITEM));
			} else {
				stockOutPlan.setStockOutItemSet(new HashSet());
			}
			
			
			// �����model
			Iterator it = stockOutPlan.getStockOutItemSet().iterator();
			while (it.hasNext()) {
				TplMessageStockOutItem stockOutItem = (TplMessageStockOutItem) it.next();
				if ("".equals(stockOutItem.getPlanPutoutId())) {
					it.remove();
				}
			}

			// �ж��ظ�������Ǻ�壬���ж�Σ����ж��ظ���
			if (OPERATION_TYPE_NEW.equals(stockOutPlan.getOperationType())) {
				String checkRepeatSql = "select id from TPL_MESSAGE_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "' " + " and OPERATION_TYPE = '" + stockOutPlan.getOperationType()
						+ "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}

			// ��ʼ������ֵ
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
			stockOutPlan.setUnitName(AcquireAgencyName.getAgencyName(stockOutPlan.getUnitId()));//��ȡί�л�������
			
			// ��ѯ�üƻ��Ѿ������������������ж����ν����Ƿ����
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_STOCK_OUT_ITEM t where t.PLAN_ID = "
					+ stockOutPlan.getId());
			//������ҵ��,���������������,ֻ���ڳ���ⷢ��
			String pickType = stockOutPlan.getPickType();
			if(SystemConfigUtil.TPL_STOCK_PLAN_PICK_TYPE_PURCHASE_OUT.contains(pickType)){
				stockOutPlan.setExtendWarehouseFlag(null);
				stockOutPlan.setExtendWarehouseCode(null);
			}

			// �ж��ظ����������ͬ�����͵����⣬��ʱ���ɷ��� add by zhengfei 20080128
			if (stockOutPlan.getStockOutItemSet() != null && stockOutPlan.getStockOutItemSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = stockOutPlan.getStockOutItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageStockOutItem stockOutItem = (TplMessageStockOutItem) itDouble.next();
					someOnePack = stockOutItem.getPackId();
					productClass = stockOutItem.getOrderNum().substring(0, 1);
				}
				// �жϸüƻ��µ��������Ƿ��Ѿ����
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
				// ����������Ѿ���⣬�򲻴�����������
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;

			}
			System.out.println("stockOutPlan:"+stockOutPlan.getBillId());
			messageDao.insertTplStockOutPlan(stockOutPlan);

			// ���������������Ҫ�õ�id
			if (id == null) {
				String queryIdSql = "select id from TPL_MESSAGE_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
						+ stockOutPlan.getPlanPutoutId() + "' and  OPERATION_TYPE = '" + stockOutPlan.getOperationType()
						+ "' and STATUS is null  order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				stockOutPlan.setId(id);
			}

			// �жϼƻ��Ƿ�����������������ҵ���
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
				 * ��������
				 * ����ϵͳ(���쵥Ԫ)����ļƻ������·�����:��������쵥Ԫ�����ļƻ�����������������ļƻ�3PL�Ѿ����ڣ���֤�����ڱ��μƻ���ߵ�������Ҫ���ġ�
				 */
				if (OPERATION_TYPE_NEW.equals(stockOutPlan.getOperationType())) {
					String checkPlanExist = "select id from TPL_STOCK_OUT_PLAN where PLAN_PUTOUT_ID = '"
							+ stockOutPlan.getPlanPutoutId() + "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doStockOutPlanRedOper(stockOutPlan, "10");
					} else {
						//�̱����������ᵥ�����ٴ����������ᵥ��ҵ������ƻ��Ѿ�ִ�У�ҵ����ճ���ƻ�
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
					 * ������
					 * 1.����ϵͳ�ĺ�壺�Ǽƻ�ȫ�������Ժ�������������ȷ�ļƻ�����֧�ֵ������ϳ���-----�ᵥ���ͣ�6��ͷ�ġ�
					 * 2.�ܿ�ϵͳ�ĺ�壺֧�ֵ������ϵĺ��-----�ᵥ���ͣ���6��ͷ���ᵥ����������ᵥ
					 * 
					 */
					if ("6".equals(stockOutPlan.getPickType().substring(0, 1))||"7".equals(stockOutPlan.getPickType().substring(0, 1))) {
						messageDao.doStockOutPlanRedOper(stockOutPlan, "00");
					} else {
						messageDao.doStockOutPlanRedOper(stockOutPlan, null);
					}
				} else if (OPERATION_FORCE_OVER.equals(stockOutPlan.getOperationType())) {
					/**
					 * ǿ�ƽ᰸ ����ϵͳ��ǿ�ƽ᰸��ǿ�ƽ᰸�Ĳ�������Ҫ�����ģ����ڽ᰸�ڵĲ���ȫ������������������90��ͷ��
					 */
					//�̱�����ת���ᵥǿ�ƽ᰸���߳��������ᵥ�����ٴ�ǿ�ƽ᰸�����ᵥ��ҵ������ƻ��Ѿ�ִ�У�ҵ���ǿ�ƽ᰸
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
						 * ׷�Ӳ���:1.������������
						 *         2.������������
						 */
						//�̱�������������ƻ���ҵ������ƻ��Ѿ�ִ�У�ҵ����ճ���ƻ�
						if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(stockOutPlan.getUnitId())
								&& planCount.intValue() > 0
								&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(stockOutPlan.getPlanParticle())) {
							
						} else {
							messageDao.insertTplStockOutByApplend(stockOutPlan,"20",updateId);//��������������
						}
					} else {
						messageDao.insertTplStockOutByApplend(stockOutPlan,"10",null);//���������ӱ�
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
			String errTitle = "������ҵ�ƻ�:" + stockOutPlan.getPlanPutoutId();
			setLogMessage(e, errTitle);
			
		}
	}

	// ���տ�������ҵ�ƻ�
	public void receiveStockAdjust(TplMessageStockAdjust stockAdjust) throws Exception {
		try {
			stockAdjust.setTplStockAdjustItems(BeanConvertUtils.strToBean(stockAdjust.getDetail(), CLASS_STOCK_ADJUST_ITEM));
			// �����model
			Iterator it = stockAdjust.getTplStockAdjustItems().iterator();
			while (it.hasNext()) {
				TplMessageStockAdjustItem stockOutItem = (TplMessageStockAdjustItem) it.next();
				if ("".equals(stockOutItem.getAdjustNo())) {
					it.remove();
				}
			}
			// ��ʼ������ֵ
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
			stockAdjust.setUnitName(AcquireAgencyName.getAgencyName(stockAdjust.getUnitId()));//��ȡί�л�������20110308
			
			stockAdjust.setStatus(null);
			messageDao.insertTplStockAdjust(stockAdjust);
			String overCheckSql = "select m.ID from  TPL_MESSAGE_STOCK_ADJUST m where m.ADJUST_NO = '" + stockAdjust.getAdjustNo()
					+ "' and " + " (select count(t.ID) from TPL_MESSAGE_STOCK_ADJUST_ITEM t " + " where t.ADJUST_NO = '"
					+ stockAdjust.getAdjustNo() + "') =  " + stockAdjust.getItemCount();
			// �жϼƻ��Ƿ�����������������ҵ���
			if (messageDao.checkPlanOver(overCheckSql)) {
				messageDao.insertTplStockAdjustByBatch(stockAdjust.getAdjustNo());
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_ADJUST set STATUS = '0' where ADJUST_NO = '"
						+ stockAdjust.getAdjustNo() + "'");
			}
		} catch (Exception e) {
			String errTitle = "��������ҵ�ƻ�:" + stockAdjust.getAdjustNo();
			setLogMessage(e, errTitle);
		}
	}

	// ���տ���̵���ҵ�ƻ�
	public void receiveStockCheck(TplMessageStockCheck stockCheck) throws Exception {
		try {
			
			/**
			 * �ܿز��·�����
			 * modified by zhengfei 20080602
			 */
			stockCheck.setTplMessageStockCheckItems(new HashSet());
			
			// �����model
			Iterator it = stockCheck.getTplMessageStockCheckItems().iterator();
			while (it.hasNext()) {
				TplMessageStockCheckItem stockCheckItem = (TplMessageStockCheckItem) it.next();
				if ("".equals(stockCheckItem.getCheckNo())) {
					it.remove();
				}
			}
			// ��ʼ������ֵ
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
			stockCheck.setUnitName(AcquireAgencyName.getAgencyName(stockCheck.getUnitId()));//��ȡί�л�������20110308
			
			stockCheck.setStatus(null);
			messageDao.insertTplStockCheck(stockCheck);
			String overCheckSql = "select m.ID from  TPL_MESSAGE_STOCK_CHECK m where m.CHECK_NO = '" + stockCheck.getCheckNo()
					+ "' and " + " (select count(t.ID) from TPL_MESSAGE_STOCK_CHECK_ITEM t" + " where t.CHECK_NO = '"
					+ stockCheck.getCheckNo() + "') = " + stockCheck.getItemCount();
			// �жϼƻ��Ƿ�����������������ҵ���
			if (messageDao.checkPlanOver(overCheckSql)) {
				messageDao.insertTplStockCheckByBatch(stockCheck.getCheckNo());
				messageDao.updatePlanStatus("update TPL_MESSAGE_STOCK_CHECK set STATUS = '0' where CHECK_NO = '"
						+ stockCheck.getCheckNo() + "'");
			}
		} catch (Exception e) {
			String errTitle = "����̵���ҵ�ƻ�:" + stockCheck.getCheckNo();
			setLogMessage(e, errTitle);
		}
	}
	
	// ���ճ��������Ķ���׼������
	public void receiveMessageReadyData(LgsMessageReadyData messageReadyData) throws Exception {
		try {
			messageReadyData.setMessageReadyMaterialSet(BeanConvertUtils.strToBean(messageReadyData.getDetail(), CLASS_MESSAGE_READY_MATERIAL));

			// �����model
			Iterator it = messageReadyData.getMessageReadyMaterialSet().iterator();
			while (it.hasNext()) {
				LgsMessageReadyMaterial messageReadyMaterial = (LgsMessageReadyMaterial) it.next();
				if ("".equals(messageReadyMaterial.getPackNum())) {
					it.remove();
				}
			}

			// �ж�׼���ظ�
			String checkRepeatSql = "select id from LGS_MESSAGE_READY_DATA where READY_NUM = '"
					+ messageReadyData.getReadyNum() + "' and READY_STATUS = '0'";
			if (messageDao.queryIdBySql(checkRepeatSql) != null)
				return;
			//�䵱��ͬ��Ķ���׼����������
			String checkCDSql = "select id from tpl_msg_contract_replace where rep_order_num = '"
				+ messageReadyData.getOrderNum() + "'";
		    if (messageDao.queryIdBySql(checkCDSql) != null)
			    return;
			

			// ��ʼ������ֵ
			String queryMessageReadyDataIdSql = "select id from LGS_MESSAGE_READY_DATA where READY_NUM = '"
					+ messageReadyData.getReadyNum() + "' and READY_STATUS is null order by ID DESC";
			Long id = messageDao.queryIdBySql(queryMessageReadyDataIdSql);
			messageReadyData.setId(id);

			// ��ѯ��׼���Ѿ������Ĳ����������ж����ν����Ƿ������
			Long materialCount = messageDao.queryIdBySql("select count(t.ID) from LGS_MESSAGE_READY_MATERIAL t where t.READY_DATA_ID = "
					+ messageReadyData.getId());

			// �ж��ظ����������ͬ�����͵����⣬��ʱ���ɷ��� add by zhengfei 20080128
			if (messageReadyData.getMessageReadyMaterialSet() != null && messageReadyData.getMessageReadyMaterialSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = messageReadyData.getMessageReadyMaterialSet().iterator();
				String someOnePack = "";
				if (itDouble != null) {
					LgsMessageReadyMaterial messageReadyMaterial = (LgsMessageReadyMaterial) itDouble.next();
					someOnePack = messageReadyMaterial.getPackNum();
				}
				// �жϸ�׼���µ��������Ƿ��Ѿ����
				Long doublePackCount = null;
				doublePackCount = messageDao.queryIdBySql("select count(t.ID) from LGS_MESSAGE_READY_MATERIAL t "
						+ " where t.READY_DATA_ID = " + messageReadyData.getId() + " and t.PACK_NUM = '" + someOnePack + "'");
				
				// ����������Ѿ���⣬�򲻴�����������
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;
			}

			messageDao.insertLgsMessageReadyData(messageReadyData);

			// ���������������Ҫ�õ�id
			if (id == null) {
				String queryIdSql = "select id from LGS_MESSAGE_READY_DATA where READY_NUM = '" + messageReadyData.getReadyNum()
						+ "' and READY_STATUS is null order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				messageReadyData.setId(id);
			}

			// �ж�׼���Ƿ��������������������
			if (messageReadyData.getNReadyTot().intValue() == messageReadyData.getMessageReadyMaterialSet().size() + materialCount.intValue()) {
				//����׼����ȫ���޸�׼��״̬
				messageDao.updatePlanStatus("update LGS_MESSAGE_READY_DATA set READY_STATUS = '0' where READY_NUM = '"
						+ messageReadyData.getReadyNum() + "'");
				
				if(SystemConfigUtil.judgeBaosteelExtend(messageReadyData.getUnitId())){
					//ί�л���0001׼���Ƕ���׼��������һ��׼����ֻ�д��ڸ��²��ϣ���Ӧ�ô�����������
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
				
				//׼��������
				messageDao.doReadyTplStockOperation(messageReadyData);
			}
		} catch (Exception e) {
			String errTitle = "׼������:" + messageReadyData.getReadyNum();
			setLogMessage(e, errTitle);
		} 
	}
	
	// ���ճ��������׼�������ĺ�������Ԥ��ͬ�ĳ����׼��������
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
//				//������ �������Բ���Ʒ������Ʒ
//				messageDao.updatePlanStatus("update TPL_STOCK set PRODUCT_PROPERTY = '10', PACK_STATUS = '00', CONFIRM_STATUS = '10', MODIFY_DATE=SYSDATE where PACK_ID = '"
//						+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
//						+ messageReadyRed.getReadyNum()	+ "' and PRODUCT_CLASS = '"
//						+ messageReadyRed.getOrderNum().substring(0, 1) + "' and COMPANY_CODE = '"
//						+ messageReadyRed.getCompanyCode() + "' and EXTEND_WAREHOUSE_FLAG = '10' "
//						+ " and STOCK_FLAG = '10' and UNIT_ID = '" + messageReadyRed.getUnitId() + "'");
				if(SystemConfigUtil.judgeBaosteelExtend(messageReadyRed.getUnitId())){
					//������ �������Բ���Ʒ������Ʒ
					messageDao.updatePlanStatus("update TPL_STOCK set PRODUCT_PROPERTY = '10', PACK_STATUS = '00', CONFIRM_STATUS = '10', MODIFY_DATE=SYSDATE where PACK_ID = '"
							+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
							+ messageReadyRed.getReadyNum()	+ "' and PRODUCT_CLASS = '"
							+ messageReadyRed.getOrderNum().substring(0, 1) + "' and COMPANY_CODE = '"
							+ messageReadyRed.getCompanyCode() + "' and EXTEND_WAREHOUSE_FLAG = '10' "
							+ " and STOCK_FLAG = '10' and UNIT_ID = '" + messageReadyRed.getUnitId() + "'");
				} else if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(messageReadyRed.getUnitId()) || SystemConfigUtil.BAOSTEEL_BGTM.equals(messageReadyRed.getUnitId())) {
					//������ ɾ������
					messageDao.updatePlanStatus("delete from TPL_STOCK where PRODUCT_PROPERTY = '20' and PACK_STATUS = '48' and STOCK_FLAG = '10' " 
							+ " and EXTEND_WAREHOUSE_FLAG = '10' and CONFIRM_STATUS = '10' and PACK_ID = '"
							+ messageReadyRed.getPackNum() + "' and READY_NUM = '"
							+ messageReadyRed.getReadyNum()	+ "' and PRODUCT_CLASS = '"
							+ messageReadyRed.getOrderNum().substring(0, 1) + "' and COMPANY_CODE = '"
							+ messageReadyRed.getCompanyCode() + "' and UNIT_ID = '" + messageReadyRed.getUnitId() + "'");
				}
			}
		} catch (Exception e) {
			String errTitle = "׼��������:" + messageReadyRed.getReadyNum();
			setLogMessage(e, errTitle);
		} 
	}
	
	// �����뵥������
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
			String errTitle = "�뵥������:" + messageStackRed.getStackingNum();
			setLogMessage(e, errTitle);
		} 
	}

	// ������������Ϣ����
	public void receiveMessagePickData(LgsMessagePickData lgsMessagePickData) throws Exception {
		try {
			lgsMessagePickData.setMessagePickMaterialSet(BeanConvertUtils.strToBean(lgsMessagePickData.getDetail(), CLASS_MESSAGE_PICK_MATERIAL));
	
			// �����model
			Iterator it = lgsMessagePickData.getMessagePickMaterialSet().iterator();
			while (it.hasNext()) {
				
				LgsMessagePickMaterial lgsMessagePickMaterial = (LgsMessagePickMaterial) it.next();
				if ("".equals(lgsMessagePickMaterial.getPackNum())||lgsMessagePickMaterial.getWeightActive().floatValue()==0) {
					it.remove();
				}
			}
			
//			//����߲ĵ����Σ��������쵥Ԫ�ֶΣ����쵥Ԫȥ��ǰ��λ���ں��油�ϰ��������ǣ�10��������20������
//			//��Ԫ��֮���9672�ڶ����л�����Ҫ9672�ָ����쵥Ԫ���ݣ���һ����־�ֶΣ������������ȥ��
//			if("D".equals(lgsMessagePickData.getOrderNum().substring(0 ,1))){
//				String manuId = lgsMessagePickData.getManuId();
//				lgsMessagePickData.setManuId("BG" + manuId.substring(0, 2));
//				lgsMessagePickData.setDelivyQtyFlag(manuId.substring(2));
//			}
	
			// ��ʼ������ֵ
			String queryMessagePickDataIdSql = "select id from LGS_MESSAGE_PICK_DATA where PICK_NUM = '"
					+ lgsMessagePickData.getPickNum() + "' and ORDER_NUM = '"
					+ lgsMessagePickData.getOrderNum() + "' and READY_NUM = '"
					+ lgsMessagePickData.getReadyNum() + "' and VEHICLE_CODE = '"
					+ lgsMessagePickData.getVehicleCode() + "' order by ID DESC";
			Long id = messageDao.queryIdBySql(queryMessagePickDataIdSql);
			lgsMessagePickData.setId(id);
	
			
			
			
			// �ж��ظ����������ͬ�����͵����⣬��ʱ���ɷ��� add by zhengfei 20080128
			if (lgsMessagePickData.getMessagePickMaterialSet() != null && lgsMessagePickData.getMessagePickMaterialSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = lgsMessagePickData.getMessagePickMaterialSet().iterator();
				String someOnePack = "";
				if (itDouble != null) {
					LgsMessagePickMaterial lgsMessagePickMaterial = (LgsMessagePickMaterial) itDouble.next();
					someOnePack = lgsMessagePickMaterial.getPackNum();
				}
				
				// �жϸ��������µ��������Ƿ��Ѿ����
				Long doublePackCount = null;
				doublePackCount = messageDao.queryIdBySql("select count(t.ID) from LGS_MESSAGE_PICK_MATERIAL t "
						+ " where t.PICK_DATA_ID = " + lgsMessagePickData.getId() + " and t.PACK_NUM = '" + someOnePack + "'");
				
				// ����������Ѿ���⣬�򲻴�����������
				if (doublePackCount != null && doublePackCount.longValue() > 0)
					return;
			}
	
			messageDao.insertLgsMessagePickData(lgsMessagePickData);
	
			
			// ���������������Ҫ�õ�id
			if (id == null) {
				String queryIdSql = "select id from LGS_MESSAGE_PICK_DATA where PICK_NUM = '" + lgsMessagePickData.getPickNum()
						+ "' and ORDER_NUM = '" + lgsMessagePickData.getOrderNum()
						+ "' and READY_NUM = '" + lgsMessagePickData.getReadyNum()
						+ "' order by ID DESC";
				id = messageDao.queryIdBySql(queryIdSql);
				lgsMessagePickData.setId(id);
			}
			
			//�����߲ĺ͹���-9672���ڿ����ʵ����
			messageDao.checkLgsMessagePickDataTrans(lgsMessagePickData);
			
			//����߲ĺ͹���-9672���ڿ����ʵ����
			messageDao.checkLgsMessagePickDataStockin(lgsMessagePickData);
			
			//webserviseת����ʱ������
			System.out.println("1");
			String vehicleCode = StringUtil.trimStr(lgsMessagePickData.getVehicleCode());
			if(vehicleCode!=""&&vehicleCode.length()>0){
				System.out.println("2");
				sendLgsPickData.sendPickData(lgsMessagePickData);
			}
		} catch (Exception e) {
			String errTitle = "��������Ϣ:" + lgsMessagePickData.getPickNum();
			setLogMessage(e, errTitle);
		} 
	}
		
	// ������ۡ�վʵ����Ϣ����
	public void receiveTransLeaveReal(TplTransLeaveReal transLeaveReal) throws Exception {
		try {
			
			messageDao.updateTplTransShipM(transLeaveReal);
			messageDao.insert(transLeaveReal, transLeaveReal.getClass());
			//�����ڴ������� add by llk
			this.insertTplShipMLeaveReal(transLeaveReal);
		} catch (Exception e) {
			String errTitle = "��ۡ�վʵ����Ϣ����:" + transLeaveReal.getShipLotNo();
			setLogMessage(e, errTitle);
		} 
	}
	
	public void insertTplShipMLeaveReal(TplTransLeaveReal transLeaveReal) throws Exception{
		//�����ݲ�����ʱ��
		//��ȡĸ������
		List transSeqList = messageDao.queryShipMIdList(transLeaveReal.getShipLotNo());
		String shipMId = "";
		if(transSeqList != null && transSeqList.size()>0){
			System.out.println("��ȡĸ������");
			Object[] shipIdArr = (Object[])transSeqList.get(0);
			shipMId = (String)shipIdArr[0];
		}
		System.out.println("ĸ�����ţ�" + shipMId);
		String portTime = StringUtil.trimStr(transLeaveReal.getPortTime());//����ʱ��
		String startTime = StringUtil.trimStr(transLeaveReal.getWorkStartTime());//����ʱ��
		String endTime = StringUtil.trimStr(transLeaveReal.getWorkEndTime());//�깤ʱ��
		String leaveTime = StringUtil.trimStr(transLeaveReal.getLeaveTime());//���ʱ��
		
		TplShipMLeaveReal tplShipMLeaveReal = new TplShipMLeaveReal();
		tplShipMLeaveReal.setShipLotNo(transLeaveReal.getShipLotNo());
		tplShipMLeaveReal.setShipNo(transLeaveReal.getShipNo());
		tplShipMLeaveReal.setShipName(transLeaveReal.getShipName());
		tplShipMLeaveReal.setCountry(transLeaveReal.getCountry());
		tplShipMLeaveReal.setPortTime(portTime);//����ʱ��
		tplShipMLeaveReal.setWorkStartTime(startTime);//����ʱ��
		tplShipMLeaveReal.setWorkEndTime(endTime);//�깤ʱ��
		tplShipMLeaveReal.setLeaveTime(leaveTime);//���ʱ��
		tplShipMLeaveReal.setShipIdM(shipMId);//ĸ������
		
		List shipMList = messageDao.queryTplShipMList(shipMId);
		//�����绰
		String shipContactNumber = "";
		if(shipMList != null && shipMList.size()>0){
			Object[] shipContactNumberArr = (Object[])shipMList.get(0);
			shipContactNumber = objtoString(shipContactNumberArr[0]);
		}
		if(!"".equals(portTime) && !"".equals(startTime)){
			//���Ϳ������ĵ������ܿ�
			TplMessageTransShipTrends tmtst = new TplMessageTransShipTrends();
			tmtst.setShipLotNo(shipMId);
			tmtst.setShipContactNumber(shipContactNumber);
			tmtst.setTimeMoveRqrCmpl(portTime);
			tmtst.setTimeLadeAct(startTime);
			tmtst.setOperationType("10");// 10����ȷ��
			sendTransShipTrends(tmtst);
		}
		if(!"".equals(endTime) && !"".equals(leaveTime)){
			//�������
			TplMessageTransShipTrends tmtst = new TplMessageTransShipTrends();
			tmtst.setShipLotNo(shipMId);
			tmtst.setTimeEnd(endTime);
			tmtst.setLeaveDate(leaveTime);
			tmtst.setShipContactNumber(shipContactNumber);
			tmtst.setOperationType("20");// 20���ȷ��
			sendTransShipTrends(tmtst);
		}
		//������ʱ��֮ǰ�ж��ظ�
		List shipMLeaveList =  messageDao.queryTplShipMLeaveRealList(shipMId);
		if(shipMLeaveList == null || shipMLeaveList.size()==0){
			//��������
			messageDao.insert(tplShipMLeaveReal, tplShipMLeaveReal.getClass());
		}else{
			//���²���
			TplShipMLeaveReal real = (TplShipMLeaveReal)shipMLeaveList.get(0);
			real.setShipName(transLeaveReal.getShipName());
			real.setCountry(transLeaveReal.getCountry());
			real.setPortTime(portTime);//����ʱ��
			real.setWorkStartTime(startTime);//����ʱ��
			real.setWorkEndTime(endTime);//�깤ʱ��
			real.setLeaveTime(leaveTime);//���ʱ��
			real.setShipIdM(shipMId);//ĸ������
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

	// ���մ�����Ϣ����
	public void receiveTransShipM(TplTransShipM transShipM) throws Exception {
		try {
			// add by wj 20130411
			if ("000000".equals(transShipM.getCarryCmpaCode()) || "999999".equals(transShipM.getCarryCmpaCode())
					|| "BGSA".equals(transShipM.getCarryCmpaCode())|| transShipM.getCarryCmpaCode() == null) {
				return;
			}
			
			transShipM.setTransSeqSet(BeanConvertUtils.strToBean(transShipM.getDetail(), CLASS_TRANS_SEQ));

			// �����model
			Iterator it = transShipM.getTransSeqSet().iterator();
			while (it.hasNext()) {
				TplTransSeq transSeq = (TplTransSeq) it.next();
				if ("".equals(transSeq.getShipId())) {
					it.remove();
				}
			}

			/**
			 * ������Ϣ����
			 * 0����������1�����޸ģ�9������
			 */
			if ("0".equals(transShipM.getOperateFlag())) {
				// ��ʼ������ֵ
				String queryShipMIdSql = "select id from TPL_TRANS_SHIP_M where SHIP_ID_M = '"
						+ transShipM.getShipIdM() + "' and OPERATE_FLAG = '" + transShipM.getOperateFlag()
						+ "' order by ID DESC";
				Long id = messageDao.queryIdBySql(queryShipMIdSql);
				transShipM.setId(id);
				if (transShipM.getTransSeqSet() != null && transShipM.getTransSeqSet().size() > 0) {
					// ��ȡ������������һ�Ӵ�����
					Iterator itDouble = transShipM.getTransSeqSet().iterator();
					String someOneShip = "";
					while (itDouble.hasNext()) {
						TplTransSeq transSeq = (TplTransSeq) itDouble.next();
						someOneShip = transSeq.getShipId();
						// �жϸô����µĴ������Ƿ����
						Long doubleShipCount = messageDao.queryIdBySql("select count(t.ID) from TPL_TRANS_SEQ t "
								+ " where t.SHIP_M_ID = " + transShipM.getId() + " and t.SHIP_ID = '" + someOneShip + "'");
						// ����ô������ڣ��򲻴���ô���
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
				// ��������Ϣ����,�޸�ĸ�Ӵ�����Ϣ
				if (id != null) {
					// ��ȡ���������е��Ӵ�����
					Iterator itDouble = transShipM.getTransSeqSet().iterator();
					String someOneShip = "";
					while (itDouble.hasNext()) {
						TplTransSeq transSeq = (TplTransSeq) itDouble.next();
						someOneShip = transSeq.getShipId();
						// �жϸô����µĴ������Ƿ����
						Long doubleShipCount = messageDao.queryIdBySql("select count(t.ID) from TPL_TRANS_SEQ t "
								+ " where t.SHIP_M_ID = " + transShipM.getId() + " and t.SHIP_ID = '" + someOneShip + "' and PRO_STATUS = '00'");
						// ����ô��������ڣ��򲻴���ô���
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
				// ��������Ϣ����,ɾ���Ӵ�����Ϣ
				if (id != null) {
					// ��ȡ���������е��Ӵ�����
					Iterator itDouble = transShipM.getTransSeqSet().iterator();
					String someOneShip = "";
					while (itDouble.hasNext()) {
						TplTransSeq transSeq = (TplTransSeq) itDouble.next();
						someOneShip = transSeq.getShipId();
						// �жϸô����µĴ������Ƿ����
						Long doubleShipCount = messageDao.queryIdBySql("select count(t.ID) from TPL_TRANS_SEQ t "
								+ " where t.SHIP_M_ID = " + transShipM.getId() + " and t.SHIP_ID = '" + someOneShip + "' and PRO_STATUS = '00'");
						// ����ô��������ڣ��򲻴���ô���
						if (doubleShipCount == null || (doubleShipCount != null && doubleShipCount.longValue() < 1))
							return;
					}
					
					messageDao.doTransShipMRedOper(transShipM);
				}
			}
		} catch (Exception e) {
			String errTitle = "������Ϣ:" + transShipM.getShipIdM();
			setLogMessage(e, errTitle);
		} 
	}
	
	// �������ֿ��λ�����Ϣ
	public void sendMessageWarehousePosChan(TplMessageWarehousePoschan warehousePoschan) throws Exception {
		messageDao.saveWarehousePoschan(warehousePoschan);
		sendMsgService.sendMsg(MESSAGE_SEND_N0_WAREHOUSE_POSCHAN, null, warehousePoschan);
	}

	// ���������ҵʵ��
	public void sendStockInResult(TplMessageStockinRealMain stockIn) throws Exception {
		messageDao.saveStockInResult(stockIn);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockIn.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockIn.getUnitId())) { //wuyang20110309
			// �����model,�ﵽָ��ѭ������
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

	// ���ͳ�����ҵʵ��
	public void sendStockOutResult(TplMessageStockoutRealMain stockOut) throws Exception {
		messageDao.saveStockOutResult(stockOut);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockOut.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockOut.getUnitId())) { //wuyang20110309
			// �����model,�ﵽָ��ѭ������
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
	// �������ϳ�����ҵʵ�� added ljh 2015-07-30 13:17:40
	public void sendOnlineStockOutResult(TplMessageStockoutRealMain stockOut) throws Exception {
		
		messageDao.saveStockOutResult(stockOut);
		System.out.println("�������");
		int lString=stockOut.getStockOutItemSet()==null ?0:stockOut.getStockOutItemSet().size();
		System.out.println("--------------"+lString);
		// �����model,�ﵽָ��ѭ������
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
	// ���͵����ᵥ�鵥���� add by zhengfei 20080229
	public void sendStockOutEpickResult(TplMessageEpickSend epickSend) throws Exception {
		//messageDao.saveStockOutEpickResult(epickSend);
		sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_OUT_EPICK, null, epickSend);
	}

	// ���Ϳ�������ҵʵ��
	public void sendStockAdjustResult(TplMessageStockAdjust stockAdjust) throws Exception {
		messageDao.saveStockAdjustResult(stockAdjust);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockAdjust.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockAdjust.getUnitId())) { //wuyang20110309
			// �����model,�ﵽָ��ѭ������
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

	// ���Ϳ���̵���ҵʵ��
	public void sendStockCheckResult(TplMessageStockCheck stockCheck) throws Exception {
		messageDao.saveStockCheckResult(stockCheck);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(stockCheck.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(stockCheck.getUnitId())) {//wuyang20110309
			// �����model,�ﵽָ��ѭ������
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
	 * *********************************���ý���ҵ��ӿ�*************************************
	 * 
	 * 1 ������ҵ������Ϣ 2 ���ͷ�Ʊ�������� 3 ���ո���ʵ�� 4 ���շ��ó���(���ͷ��ó����ظ�) 5 ���ͷ�Ʊ���� 6 ���շ�Ʊ̧ͷ���
	 * 
	 * **********************************************************************************
	 */
	// ������ҵ������Ϣ
	public void receiveFee(TplMessageFee messageFee) throws Exception {
		
		System.out.println("********************�ݻ���־*********************" +messageFee.getLaterSettleFlag());
		/**
		 * 0101 ˮ·�˷� 0201 ��·�˷� 0301 �����˷� 1101 �ִ��� 1102 ������ 1103 ��ͷ��װ�� 1104
		 * ��·��װ�� 3101 ����� 3102 �̼�� 3103 ޹���� 3104 ����֤�� 3105 ��װ��� 3201 ���շ� 3202
		 * ӡ��˰
		 * 
		 */
		try {
			// �ƻ�����ɾ�����Ľ���
			String delMsgFee = "delete from TPL_MESSAGE_FEE where PLAN_SEQ = '" + messageFee.getPlanSeq() + "'";
			String delPlanFee = "delete from TPL_PLAN_FEE where PLAN_ID = '" + messageFee.getPlanSeq() + "'";
			String delRealFee = "delete from TPL_REAL_FEE where PLAN_ID = '" + messageFee.getPlanSeq() + "' and invoice_sys_id is null ";
			if (FEE_DEL_FLAG.equals(messageFee.getOrderNum())) {
				/**
				 * modified by Forest 20080425 ����ֱ�����������⣬���ò���ɾ����
				 * ����üƻ���ʵ������������ɾ����
				 */				
				//�����·���ó���ɾ��ȫ�����üƻ�
				//wuyixuan 2018-10-31 23:43:53
				String checkIfDelSql = "select ID from TPL_MESSAGE_FEE  where UNIT_ID != 'BGBW' and PLAN_SEQ = '" + messageFee.getPlanSeq()
						+ "' and TRANS_TYPE = '" + SystemConstants.TRANS_TYPE_TRAIN + "' and BILL_TYPE = '62'";
				if (messageDao.querySingleValueBySql(checkIfDelSql) != null) {
					return;
				}
				
				Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_FEE t where t.PLAN_SEQ = '" + messageFee.getPlanSeq() + "' and t.FEE_STATUS = '1'");
				if (itemCount.intValue() != 0) {
					// �����ʵ�����ã�������ʵ����¼Ҳ�����ɾ��
					messageDao.executeUpdateSql(delRealFee);
				}
				
				// 1 ɾ��tpl_message_fee��tpl_plan_fee����
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
			/*3PL���շ���ʱ���ж��ᵥ������11��31��66ģʽΪֱ��ҵ��
		       �����ñ���ҵ�����ͣ�businessType��10��ֱ����*/
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
			messageFee.setUnitName(AcquireAgencyName.getAgencyName(messageFee.getUnitId()));//��ȡί�л�������20110308
			
			// �ɿ�Ʊ�����ʱ���ã�����Ϊ1���ɿ�Ʊ
			messageFee.setInvoiceMadeStatus("1");

			// ë��Ϊ0��С�ھ��ض�������
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
			// ����˷ѻ�ִ����Ƿ��ظ�����
			if (messageDao.queryIdBySql(checkFeeRepeatSql) != null) {
				//³������ת������ҵ��,����ȫ��ȫɾ�ķ�ʽ׷��
				if(APPLEND_FLAG.equals(messageFee.getApplendFlag())&& "0".equals(messageFee.getPlanType())){
					messageDao.executeUpdateSql(delMsgFee);
					messageDao.executeUpdateSql(delPlanFee);
				}
				else{
					return;
				}
				
			}
			
			/**
			 * ������ҵ�ƻ��Ƿ���3PL������(INSURANCE_FLAG)��10���ǣ�00���񣻿գ���ʷ���ݣ����ñ�������֮ǰ�ķ�ʽ����
			 * modified by tangaj 20111012
			 * 
			 */
			if (null == messageFee.getInsuranceFlag() || "".equals(messageFee.getInsuranceFlag())) {
				// ��3PL���������Ϊnull���ʱ�����䷽ʽ(TRANS_TYPE)������(="1")�����㷽ʽ(SETTLE_CODE)Ϊ2,5,6 ����3PL������
				if ("1".equals(messageFee.getTransType())
						|| "2".equals(messageFee.getSettleCode())
						|| "5".equals(messageFee.getSettleCode())
						|| "6".equals(messageFee.getSettleCode())) {
					messageFee.setInsuranceFlag("00");
				}
			}// �������������

			// �ƻ������������Ľ���
			if (FEE_OVER_FLAG.equals(messageFee.getOrderNum())) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_FEE set STATUS = '0' where PLAN_SEQ = '" + messageFee.getPlanSeq()
						+ "'");
				messageDao.insertTplFeeByBatch(messageFee);
				
				// 20180110 ���ݲ��Ϻ�ͬ�ж��Ƿ����ֻ������͸�ŷұ����
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
//							//�жϼƻ����Ƿ��Ѿ�������ʱ�������ظ�����
//							//�ֻ�����ҵ�񣬼ƻ���Ϣ������ʱ���ɶ�ʱ��������ŷұ
//							Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '1' and SEND_STATUS = '00' and TRANS_PLAN_ID = '" + messageFee.getPlanSeq()+"'");
//							if (tempId==null) {//����ƻ�����
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
				// ������㷽ʽ�Ǵ��մ�������Ҫ���ݺ�ͬ����λ������˾�����ֲ�ͬ�ĵ�����˾(��ͬ�ķ�Ʊ̧ͷ��ͬ������˾���벻�ܿ���һ�ŷ�Ʊ��)��
				if (SystemConfigUtil.SETTLE_CODE_DAISHOUDAIZHI.equals(messageFee.getSettleCode())) {
					/**
					 * 000107 ���ݱ��� 000199 �ɶ����� 000424 ������ó 000425 ���ָ�ó 000650
					 * ��򱦸� 001779 �ֶ���ó 000136 �人���� 007297 ��ɽ��˾
					 */
//					String orderNum = messageFee.getOrderNum();
					String orderUserId = messageFee.getManuId();
					String finalName = getAreaCompanyCode(orderUserId);
					messageFee.setOrderUserType(finalName);
					/**
					 * ����Ǳ�֧����Ҫ���������Ϳ�Ʊ�� 1 ��ɹ�����ͬ������P000 2 �ڹ��ϣ���ͬ������P100 3
					 * �ֹܣ���ͬ����G��ͷ 4 Ԥ��ͬ�� ��ͬ����S100 5 ��������Ʒ S000
					 * 
					 */
				} else if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(messageFee.getSettleCode())) {
					if (SystemConfigUtil.ORDER_TYPE_WAICAIGOU.equals(messageFee.getOrderType())
							|| SystemConfigUtil.ORDER_TYPE_NEIGONG.equals(messageFee.getOrderType())) {
						messageFee.setOrderUserType(messageFee.getOrderType());
						// added by Forest 2008-02-20 R��ͷ��ͬ(ERW��)���ڸֹ�Ʒ��
					} else if (messageFee.getOrderNum().substring(0, 1).equals("G")
							|| messageFee.getOrderNum().substring(0, 1).equals("R")) {
						messageFee.setOrderUserType("G");
						// added by Forest 2008-02-25 ����Ԥ��ͬ��Ʊ
					} else if (SystemConfigUtil.ORDER_TYPE_YUHETONG.equals(messageFee.getOrderType())) {
						messageFee.setOrderUserType(SystemConfigUtil.ORDER_TYPE_YUHETONG);
						// added by Forest 2008-04-22 �������쵥Ԫ�������ֲ����̼��Ʒ�ֵı�֧
					} else if ("BGSY".equals(messageFee.getManuId())) {
						messageFee.setOrderUserType("SY" + SystemConfigUtil.ORDER_TYPE_XIAOSHOU);
					} else {
						messageFee.setOrderUserType(SystemConfigUtil.ORDER_TYPE_XIAOSHOU);
					}
				}
				messageDao.insertTplMessageFee(messageFee);
			}
		} catch (Exception e) {
			String errTitle = "���ý��ճ���:" + messageFee.getPlanSeq();
			setLogMessage(e, errTitle);
		}
	}

	private String getAreaCompanyCode(String orderM) {
		String finalName = "";
		//2010��4��23�գ�Ҫ��ȡ���Ժ�ͬ�ŵ��ж�
//		if ("".equals(orderNum)) {
//			orderNum = "0000000000";
//		}
		//������
		if ("A".equals(orderM) || "002995".equals(orderM)){
			finalName = "002995";
        // ���ָ�ó
		}else if ("E".equals(orderM) || "000425".equals(orderM) || "009406".equals(orderM)) {
			finalName = "000425";
		// ������ó
		}else if ("H".equals(orderM) || "000424".equals(orderM) || "701018".equals(orderM)) {
			finalName = "000424";
	    // �ɶ����֣�������˾��
		}else if ("D".equals(orderM) || "000199".equals(orderM) || "701116".equals(orderM)) {
			finalName = "000199";
		// �人���֣����й�˾��
		}else if ("L".equals(orderM) || "000136".equals(orderM) || "702646".equals(orderM)) {
			finalName = "000136";
		// �ֶ���ó
		}else if ("I".equals(orderM) || "001779".equals(orderM)) {
			finalName = "001779";
		// ��ɽ��˾��������˾��
		}else if ("N".equals(orderM) || "007297".equals(orderM)) {
			finalName = "007297";
		// �Ϻ�����
		}else if ("Q".equals(orderM) || "A05541".equals(orderM)){
			finalName = "A05541";	
		// ���ݱ��֣��Ϸ���˾��
		}else if ("B".equals(orderM) || "000107".equals(orderM) || "702644".equals(orderM)) {
			finalName = "000107";
		// ��������
		}else if ("B01".equals(orderM) || "044399".equals(orderM)){
			finalName = "044399";
		// ��򱦸֣�������˾��
		}else if ("C".equals(orderM) || "000650".equals(orderM) || "700714".equals(orderM)) {
			finalName = "000650";
		// ��������
		}else if ("C01".equals(orderM) || "035169".equals(orderM)){
			finalName = "035169";
		// ��������
		}else if ("C02".equals(orderM) || "000108".equals(orderM)){
			finalName = "000108";
		//	��ɽ����
		}else if ("T02".equals(orderM) || "008422".equals(orderM)){
			finalName = "008422";
		//	��������
		}else if ("T01".equals(orderM) || "031346".equals(orderM)){
			finalName = "031346";
		}else if ("F".equals(orderM) || "000158".equals(orderM)){
			finalName = "000158";
			//��������
		}else if ("K".equals(orderM) || "001068".equals(orderM)){
			finalName="001068";
			//���ֹ��ʶ�����˾
		}
		else{
			finalName = orderM;
		}
		return finalName;
	}
	
	// ���ͷ�Ʊ��������
	public void sendInvoice(TplMessageInvoice invoice) throws Exception {
		System.out.println("invoice.getSendStatus():::"+StringUtil.trimStr(invoice.getSendStatus()));
		
		// ����β�����
		double totalMoney = invoice.getTotalAmount().doubleValue();

		double detailTotal = 0;
		
		double detailTotalTax = 0; //����˰�����
		double invTaxAmount = invoice.getInvTaxAmount().doubleValue(); //�ֹ������˰���ܽ��

		Iterator it = invoice.getInvoiceFeeList().iterator();
		while (it.hasNext()) {
			TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) it.next();
			detailTotal = detailTotal + invoiceFee.getTotalAmount().doubleValue();
			detailTotalTax = detailTotalTax + invoiceFee.getTaxAmount().doubleValue(); //������ϸ�е�˰��
		}
		System.out.println("detailTotal"+detailTotal);
		System.out.println("totalMoney"+totalMoney);
		double diffMoney = this.round(totalMoney - detailTotal, 2);
		
		double diffMoneyTax = this.round(invTaxAmount - detailTotalTax, 2);//¼����˰������ϸ��˰������
		
		boolean flag=true;

		System.out.println("diffMoney"+diffMoney);
		// ���β����ƫ���ƫ���ӵ������շ� ӡ��˰ �ִ���(�����ַ��ù̶�)����ķ����ϣ����ַ�Ʊ����ϸ���һ�¡�
		if (diffMoney != 0) {
			/**
			 * 3201 ���շ� 3202 ӡ��˰ 1101 �ִ���
			 */
			Iterator itSecond = invoice.getInvoiceFeeList().iterator();
			while (itSecond.hasNext()) {
				TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) itSecond.next();
				double trueMoney = this.round(invoiceFee.getTotalAmount().doubleValue() + diffMoney, 2);
				System.out.println("trueMoney"+trueMoney);
				// ����Ǳ�֧��Ҫ���⴦��
				if(trueMoney>0){
					if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(invoice.getSettleType())) {
						// ���������̯���˷���
						if ("0101".equals(invoiceFee.getFeeTypeCode()) || "0301".equals(invoiceFee.getFeeTypeCode())
								|| "0201".equals(invoiceFee.getFeeTypeCode()) || "1102".equals(invoiceFee.getFeeTypeCode())
								|| "1103".equals(invoiceFee.getFeeTypeCode()) || "1104".equals(invoiceFee.getFeeTypeCode())|| "3201".equals(invoiceFee.getFeeTypeCode())) {//add by zhengfei ������ͷ��װ��
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
		
		//����˰�����20120109 add by wuyang
		if(diffMoneyTax != 0) {
			Iterator itSecondTax = invoice.getInvoiceFeeList().iterator();
			while (itSecondTax.hasNext()) {
				TplMessageInvoiceFee invoiceFee = (TplMessageInvoiceFee) itSecondTax.next();
				double trueMoneyTax = this.round(invoiceFee.getTaxAmount().doubleValue() + diffMoneyTax, 2);
				if(trueMoneyTax>0){
					// ����Ǳ�֧��Ҫ���⴦��
					if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(invoice.getSettleType())) {
						// ���������̯���˷���
						if ("0101".equals(invoiceFee.getFeeTypeCode()) || "0301".equals(invoiceFee.getFeeTypeCode())
								|| "0201".equals(invoiceFee.getFeeTypeCode()) || "1102".equals(invoiceFee.getFeeTypeCode())
								|| "1103".equals(invoiceFee.getFeeTypeCode()) || "1104".equals(invoiceFee.getFeeTypeCode())|| "3201".equals(invoiceFee.getFeeTypeCode())) {//add by zhengfei ������ͷ��װ��
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
					// ����Ǳ�֧��Ҫ���⴦��
					if (SystemConfigUtil.SETTLE_CODE_BAOZHI.equals(invoice.getSettleType())) {
						// ���������̯���˷���
						if ("1101".equals(invoiceFee.getFeeTypeCode())) {//add by xutainyu  �ִ��ѷ�Ʊ����
							invoiceFee.setTaxAmount(new BigDecimal(trueMoneyTax));
							//break;
						}
					} 
				 }
				
			   }
			}
			
		}

		//if (invoice.getInvoiceType() != null && invoice.getInvoiceType().trim().equals("9")) {// ������ֵ˰����9�ĳ�8��ֻ�ڵ��ı������޸�
		//	invoice.setInvoiceType("8");
		//}
		messageDao.saveInvoice(invoice);

		if (SystemConfigUtil.judgeBaosteelOwner(invoice.getUnitId())) { //wuyang20110309
			// �����model,�ﵽָ��ѭ������
			List list = new ArrayList();
			list.addAll(invoice.getInvoiceFeeList());
			int leftCircularTimes = 0;
			if (invoice.getInvoiceFeeList().size() % CIRCULAR_INVOICE_FEE_TIMES != 0) {
				leftCircularTimes = CIRCULAR_INVOICE_FEE_TIMES - invoice.getInvoiceFeeList().size() % CIRCULAR_INVOICE_FEE_TIMES;
			}
			for (int i = 0; i < leftCircularTimes; i++) {
				list.add(new TplMessageInvoiceFee());
			}

			// ת����Ʊ����
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
				System.out.println("���͵��Ĵ���---"+i);
			}
			// ���ͽ�������
			String orderNum = invoice.getOrderNum();
			invoice.setInvoiceType(tempInvoiceType);
			invoice.setOrderNum(FEE_OVER_FLAG);
			sendMsgService.sendMsg(MESSAGE_SEND_N0_INVOICE_APPLY, null, invoice);
			invoice.setOrderNum(orderNum);
		}

	}

	// ���շ�Ʊ����ʵ��
	public void receiveInvoicePay(TplMessageInvoicePay invoicePay) throws Exception {
		try {
			messageDao.updateInvoicePay(invoicePay);
		} catch (Exception e) {
			String errTitle = "��Ʊ����ʵ��: " + invoicePay.getInvoiceId() + "#" + invoicePay.getInvoiceNo();
			setLogMessage(e, errTitle);
		}
	}

	// ���շ��ó��� 0 ���ɳ�����1 �ɳ���
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
			String errTitle = "���ó�����" + feeCancel.getPlanId();
			setLogMessage(e, errTitle);
		}
	}

	// ���ͷ�Ʊ��������
	public void sendInvoiceCancel(TplMessageInvoice invoice) throws Exception {
		messageDao.cancelInvoice(invoice);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(invoice.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(invoice.getUnitId())) { //wuyang20110309
			sendMsgService.sendMsg(MESSAGE_SEND_N0_INVOICE_CANCEL, null, invoice);
		}
	}

	// ���շ�Ʊ̧ͷ���
	public void receiveInvoiceTitleChange(TplInvoiceTitleChange invoiceTitleChange) throws Exception {
		try {
			if (invoiceTitleChange.getPlanId() == null || "".equals(invoiceTitleChange.getPlanId())) {
				return;
			}
			String planId=invoiceTitleChange.getPlanId();
			String orderNum=invoiceTitleChange.getOrderNum();
			//��ѯrealfee�����Ƿ������ɷ�����Ϣ
			Map map=new HashMap();
			map.put("planId", planId);
			map.put("orderNum", orderNum); 
			List list =messageDao.queryTplRealFee(map);
			//������Ϣmodel���Ա����ִ�����
			TplMessageLog tplMessageLog=new TplMessageLog();
			tplMessageLog.setErrTitle("���·�Ʊ̧ͷʧ��");
			tplMessageLog.setCreateDate(new Date());
			//�Դ˱���realfee���ݣ��鿴�Ƿ���Ը��·�Ʊ̧ͷ
			for (int j = 0; j < list.size(); j++) {
				Map realFee = (Map) list.get(j);
				BigDecimal invoiceSysId = (BigDecimal) realFee.get("invoice_sys_id");
				String settleCode = (String) realFee.get("settle_code");
				
				if(null != invoiceSysId){ 
					tplMessageLog.setErrContent(" �ƻ���: "+planId+" ��ͬ��: "+orderNum+" �ѿ�Ʊ���ܸ��·�Ʊ̧ͷ��Ϣ ");
					messageDao.insert(tplMessageLog, TplMessageLog.class);
					return;
				} 
				if( "3".equals(settleCode)){ 
					tplMessageLog.setErrContent(" �ƻ���: "+planId+" ��ͬ��: "+orderNum+" ���㷽ʽΪ��֧���ܸ��·�Ʊ̧ͷ��Ϣ ");
					messageDao.insert(tplMessageLog, TplMessageLog.class);
					return;
				}
				
			} 
			//��¼��Ʊ̧ͷ���ĸ��¼�¼
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
			invoiceTitleChange.setUnitName(AcquireAgencyName.getAgencyName(invoiceTitleChange.getUnitId()));//��ȡί�л�������20110308
			
			messageDao.insert(invoiceTitleChange, TplInvoiceTitleChange.class);
			
			
			
			//����������·�ӷ�֮��ķ���
			String updatePlanFeeTitleTrans = "update TPL_PLAN_FEE set INVOICE_TITLE_CODE='" + invoiceTitleChange.getInvoiceTitleCodeNew()
			+ "',INVOICE_TITLE_NAME='" + invoiceTitleChange.getInvoiceTitleNameNew() + "',INVOICE_TITLE_TAX_NO='"
			+ invoiceTitleChange.getInvoiceTitleTaxNoNew() + "' where PLAN_ID = '" + invoiceTitleChange.getPlanId()
			+ "' and ORDER_NUM = '" + invoiceTitleChange.getOrderNum() + "' and FEE_TYPE_CODE !=3203 ";
			
			String updateRealFeeTitleTrans = "update TPL_REAL_FEE set INVOICE_TITLE_CODE='" + invoiceTitleChange.getInvoiceTitleCodeNew()
			+ "',INVOICE_TITLE_NAME='" + invoiceTitleChange.getInvoiceTitleNameNew() + "',INVOICE_TITLE_TAX_NO='"
			+ invoiceTitleChange.getInvoiceTitleTaxNoNew() + "' where PLAN_ID = '" + invoiceTitleChange.getPlanId()
			+ "' and ORDER_NUM = '" + invoiceTitleChange.getOrderNum() + "' and INVOICE_SYS_ID is null   and SETTLE_CODE<>'3' AND FEE_TYPE_CODE !=3203 AND FEE_TYPE_CODE IS NOT NULL";
			
			
			//����������·�ӷ�sql
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
				 //�����˷�
				 //����plan_fee����
				this.getMessageDao().executeUpdateSql(updatePlanFeeTitleTrans);
				 //����real_fee����
				if(list!=null && list.size()>0){
					this.getMessageDao().executeUpdateSql(updateRealFeeTitleTrans);
				}
			}
			if(!"".equals(invoiceTitleCodeNew2) && !"".equals(invoiceTitleCodeName2)  && !"".equals(invoiceTitleTaxNoNew2)){
				 //�����ӷ�
				 //����plan_fee����
				this.getMessageDao().executeUpdateSql(updatePlanFeeTitle3203);
				 //����real_fee����
				if(list!=null && list.size()>0){
					this.getMessageDao().executeUpdateSql(updateRealFeeTitle3203);
				}
				
			}
		} catch (Exception e) {
			String errTitle = "��Ʊ̧ͷ�����" + invoiceTitleChange.getOrderNum();
			setLogMessage(e, errTitle);
		}
	}   

	/**
	 * *********************************����ҵ��ӿ�*************************************
	 * 1 ���ճ�����ת���뵥����    2 ���ճ�����ת���뵥������
	 * 1 ����������ҵ�ƻ� 2 ��������ʵ�� 3 ���ͱ��� 4 ���ͳ���װ���嵥 5 ���ʹ�����̬��Ϣ
	 * 
	 * **********************************************************************************
	 */
	// ������ת���뵥���Ľ���--20140811 --yesimin
	public void receiveMessageStackOut(TplMessageStackOut stackOut)
			throws Exception {
		try {
			//������Ǯ�ֶ���յ��ı�ҵ���
			System.out
					.println("messageService===receiveMessageStackOut�����ճ�����ת���뵥����----��ʼ");
			String checkRepeatSql = "select id from TPL_MESSAGE_STACK_OUT where STACKING_REC_NUM = '" + stackOut.getStackingRecNum()
			+ "' and MANU_ID = '"+stackOut.getManuId()+"' and PICK_NUM = '"+stackOut.getPickNum()+"' and STATUS = '0'";
			//���ı�����
			if (messageDao.queryIdBySql(checkRepeatSql) != null){
				return;
			}
			
			checkRepeatSql = "select id from LGS_STACK_DATA where STACKING_REC_NUM = '" + stackOut.getStackingRecNum() + "' and MANU_ID = '"+stackOut.getManuId()+"' and PICK_NUM = '"+stackOut.getPickNum()+"'";
			//ҵ�������
			if (messageDao.queryIdBySql(checkRepeatSql) != null){
				return;
			}
			// 1�� ������ϸ������������>0�������ϸ
			if (stackOut.getNGrassPack().intValue() > 0) {
				stackOut.setStackItemSet(BeanConvertUtils.strToBean(stackOut
						.getDetail(), CLASS_STACK_OUT_PACK));
			} else {
				stackOut.setStackItemSet(new HashSet());
			}
			//�����model
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
			
			
			// 3�� ����������ӱ�TplMessageStackOut��TplMessageStackOutPack
			messageDao.insertTplMessageStackOut(stackOut);
			System.out.println(stackOut.getNStackingRec());
			System.out.println(itemCount.intValue());
			System.out.println(stackOut.getStackItemSet().size());
			if(stackOut.getNStackingRec().intValue() == itemCount.intValue()+stackOut.getStackItemSet().size()){
				
				messageDao.updatePlanStatus("update TPL_MESSAGE_STACK_OUT set STATUS = '0' where STACKING_REC_NUM = '" + stackOut.getStackingRecNum()+"' and MANU_ID = '"+stackOut.getManuId()+"' and PICK_NUM = '"+stackOut.getPickNum()+"'");
				
				messageDao.insertTplStackOut(stackOut);
				System.out
					.println("messageService===receiveMessageStackOut�����ճ�����ת���뵥����----��ҵ������");
			}
			
			String trnpModeCode = stackOut.getTrnpModeCode();
			   if(trnpModeCode != null&&trnpModeCode.length()>1){
			    String subTrnp = trnpModeCode.substring(1, 2);
			    System.out.println("receiveMessageStackOut��subTrnp:" +subTrnp);
			    if("1".equals(subTrnp)){
			     sendMessageStackOut.sendStackOut(stackOut);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errTitle = "�����뵥��" + stackOut.getPickNum();
			setLogMessage(e, errTitle);
		}
	}


	//	������ת���뵥�����Ľ���--20140811  --yesimin
	public void receiveMessageStackOutRed(TplMessageStackOutRed stackOutRed)
			throws Exception {
		try {
			System.out
					.println("messageService===receiveMessageStackOutRed�����ճ�����ת���뵥������----��ʼ");
			if (stackOutRed == null)
				return;

			// 1��������ĺ��������أ�
			String checkRepeatSql = "select id from TPL_MESSAGE_STACK_OUT_RED ma "
					+ "where ma.PACK_ID ='" + stackOutRed.getPackId()
					+ "' and ma.STACKING_NUM ='" + stackOutRed.getStackingNum()
					+ "' and ma.MANU_ID = '" + stackOutRed.getManuId() + "'";
			System.out
					.println("messageService===receiveMessageStackOutRed����ҵ�����ı��Ƿ��д�������"
							+ checkRepeatSql);
			if (messageDao.queryIdBySql(checkRepeatSql) != null)
				return;
			messageDao.insertTplMessageStackOutRed(stackOutRed);
			System.out
					.println("messageService===receiveMessageStackOutRed�����ճ�����ת���뵥������----����ı����");

			// 2��ҵ�����������뵥ҵ���(���ж��ظ�)
			String checkRepeatSql1 = "select id from LGS_STACK_RED ma "
					+ "where ma.PACK_ID ='" + stackOutRed.getPackId()
					+ "' and ma.STACKING_NUM ='" + stackOutRed.getStackingNum()
					+ "' and ma.MANU_ID = '" + stackOutRed.getManuId() + "'";
			System.out
					.println("messageService===receiveMessageStackOutRed���к��ҵ����Ƿ��д�������"
							+ checkRepeatSql1);
			if (messageDao.queryIdBySql(checkRepeatSql1) != null)
				return;
			messageDao.doTplStackOutRed(stackOutRed);
			System.out
					.println("messageService===receiveMessageStackOutRed�����ճ�����ת���뵥������----ҵ�������");
		} catch (Exception e) {
			e.printStackTrace();
			String errTitle = "�뵥����ᵥ��" + stackOutRed.getPickNum();
			setLogMessage(e, errTitle);
		}

	}

	// ��������ƻ�ִ��ʱ�����
	public void receiveTransDeliveryPlanTime(TplTransDeliveryPlanTime tplTransDeliveryPlanTime) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		
		String billStartTime = StringUtil.trimStr(tplTransDeliveryPlanTime.getBillStartTime());
		String billEndTime = StringUtil.trimStr(tplTransDeliveryPlanTime.getBillEndTime());
		if(!"".equals(billStartTime) && billStartTime.length()==12){
			//��ʱ��ת��Ϊ���ڸ�ʽ��
			tplTransDeliveryPlanTime.setBillStartDate(format.parse(billStartTime));
		}
		if(!"".equals(billEndTime) && billEndTime.length()==12){
			tplTransDeliveryPlanTime.setBillEndDate(format.parse(billEndTime));
		}
		tplTransDeliveryPlanTime.setCreateDate(new Date());
		messageDao.insert(tplTransDeliveryPlanTime, TplTransDeliveryPlanTime.class);
	}
	
	
	
	
	// ����������ҵ�ƻ�
	public void receiveTransPlan(TplMessageTransPlan transPlan) throws Exception {
		try {
			// yesimin  20150508  տ���ᵥ�Ž�ȡ
			if(transPlan.getPickNum()!=null&&(transPlan.getPickNum().endsWith("_A")||transPlan.getPickNum().endsWith("_B")))
			{
				String[] pickNum=transPlan.getPickNum().split("_");
				transPlan.setPickNum(pickNum[0]);
			}
			
			//if ("000000".equals(transPlan.getPr oviderId()) || "999999".equals(transPlan.getProviderId())
			//		|| "BGSA".equals(transPlan.getProviderId()) || transPlan.getProviderId() == null||transPlan.getProviderId().length()==4) {
				
			//}
			
			//�ж��ظ�������Ǻ�壬���ж�Σ����ж��ظ���
			if (SystemConstants.OPERATION_TYPE_INSERT.equals(transPlan.getOperationType())) {
				//transPlan.setUnitId(SystemConfigUtil.BAOSTEEL_GUFEN);
				transPlan.setUnitId(transPlan.getAuthorizedUnitId());
				String checkRepeatSql = "select id from TPL_MESSAGE_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId()
						+ "' and OPERATION_TYPE = '" + transPlan.getOperationType() + "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}
			
			/**
			 * �������쵥Ԫ�����壺���������0��֤��ȫ��Ҫ��壬����Ҫ������ϸ��
			 * modified by Forest 20080514
			 */
			if (transPlan.getPlanCount().intValue() > 0) {
				transPlan.setTransItemSet(BeanConvertUtils.strToBean(transPlan.getDetail(), CLASS_TRANS_PACK));
			} else {
				transPlan.setTransItemSet(new HashSet());
			}

			
			// �����model����ȡƷ�ִ���
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
			transPlan.setUnitName(AcquireAgencyName.getAgencyName(transPlan.getUnitId()));//��ȡί�л�������20110308
			
			// ת��������˾��
			transPlan.setOrderUserId(this.getAreaCompanyCode(transPlan.getOrderUserId()));
			if (transPlan.getRainCloth() == null || "".equals(transPlan.getRainCloth().trim())) {
				transPlan.setRainCloth(SystemConstants.TRANS_PLAN_RAIN_CLOTH_NO);
			}
			transPlan.setInsureStatus(SystemConstants.TRANS_PLAN_INSURANCE_UNMAKE);

			// added by Forest 20080417 ��ȡ���շ���,�����շ��ʴ����������С�
			double insurRate = 0.0;
			
			if ("BGTM".equals(transPlan.getManuId())) {
					insurRate = INSURANCE_RATE_MEI;
			} else {
					insurRate = INSURANCE_RATE;
			}
		

			// ��ȡƷ�ֻ���
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
				// �����ҿػ��ļƻ��������շ�������Ϊ����*ǧ��֮0.85;÷����ǧ��֮0.65
				if ("BGTM".equals(transPlan.getManuId())) {
					if ("11".equals(transPlan.getPickType())|| "31".equals(transPlan.getPickType()) || "66".equals(transPlan.getPickType())){
						tmpRate = 0.0;
					}else{
						tmpRate = price.doubleValue() * INSURANCE_RATE_MEI;
						tmpRate = getFormatDouble(tmpRate, 2);
					}
				} else {
					// 4��1�տ�ʼִ���µķ���0.00043 TODO
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
			// �����ˮ�ˣ������Ų��գ���ˮ�˵ļƻ�ʵ��װ������Ϊ�ƻ���
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
			//ĸ������Ϊ�գ������Ӵ������ĸ�����ţ���������Ӧ��ĸ����by panyouyong 20130305
			if(null == transPlan.getShipIdM() || "".equals(transPlan.getShipIdM())){
				transPlan.setShipIdM(transPlan.getShipId());
			}

			// ��ѯ�üƻ��Ѿ������������������ж����ν����Ƿ����
			Long itemCount = messageDao.queryIdBySql("select count(t.ID) from TPL_MESSAGE_TRANS_PACK t where t.PLAN_ID = "
					+ transPlan.getId());

			// �ж��ظ����������ͬ�����͵����⣬��ʱ���ɷ��� add by zhengfei 20080128
			if (transPlan.getTransItemSet() != null && transPlan.getTransItemSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = transPlan.getTransItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageTransPack transPack = (TplMessageTransPack) itDouble.next();
					someOnePack = transPack.getPackId();
					//��Ҫ��ò�Ʒ����
					if(transPack.getOrderNum()!= null && !transPack.getOrderNum().trim().equals("")){
					productClass = transPack.getOrderNum().substring(0, 1);
					}
				}
				// �жϸüƻ��µ��������Ƿ��Ѿ����
				Long doublePackCount=null;
				if(OPERATION_TYPE_APPLEND.equals(transPlan.getOperationType())){
					 doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_TRANS_PACK t "
							+ " where t.trans_plan_id = '" + transPlan.getTransPlanId()+ "'   and t.PACK_ID = '" + someOnePack + "' and t.PRODUCT_CLASS='" + productClass + "' ");//������Ʒ����
				}
				else{  doublePackCount = messageDao.queryIdBySql(" select count(t.ID) from TPL_MESSAGE_TRANS_PACK t "
						+ " where t.PLAN_ID = " + transPlan.getId() + "   and t.PACK_ID = '" + someOnePack + "' and t.PRODUCT_CLASS='" + productClass + "' " );//������Ʒ����
				}
				
				// ����������Ѿ���⣬�򲻴�����������
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

			// �жϼƻ��Ƿ�����������������ҵ���
			if (transPlan.getPlanCount().intValue() == transPlan.getTransItemSet().size() + itemCount.intValue()) {
				// modified by zhengfei 20080125
				messageDao.updatePlanStatus("update TPL_MESSAGE_TRANS_PLAN set STATUS = '0' where TRANS_PLAN_ID = '"
						+ transPlan.getTransPlanId() + "'   and OPERATION_TYPE = '" + transPlan.getOperationType() + "'");

				if (OPERATION_TYPE_NEW.equals(transPlan.getOperationType())) {
					/**
					 * ��������
					 * ����ϵͳ(���쵥Ԫ)����ļƻ������·�����:��������쵥Ԫ�����ļƻ�����������������ļƻ�3PL�Ѿ����ڣ���֤�����ڱ��μƻ���ߵ�������Ҫ���ġ�
					 */
					String checkPlanExist = "select id from TPL_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId() + "'";
					if (messageDao.queryIdBySql(checkPlanExist) != null) {
						messageDao.doTransPlanRedOper(transPlan, "10");
					} else {
						Long seqId = null;
						// �����ˮ�ˣ������Ų��գ��򽫴�����Ϣ������������
						if (SystemConstants.TRANS_TYPE_SHIP.equals(transPlan.getTransType()) && transPlan.getShipId() != null) {
							seqId = messageDao.insertTplTransShipSeq(transPlan);
							
						}
						messageDao.insertTplTransPlanByBatch(transPlan, seqId);
						
						// 20180110 ���ݲ��Ϻ�ͬ�ж��Ƿ����ֻ������͸�ŷұ����
						Long checkExistId = messageDao.queryIdBySql("select id from tpl_trans_plan t where t.trans_type = '1' and t.unit_id = '0001' and exists (select 1 from tpl_trans_pack p where p.plan_id = t.id and substr(p.order_num,3,1) = 'W') and t.trans_plan_id = '"+transPlan.getTransPlanId()+"'");
						if (checkExistId != null){
							System.out.println("---������ò�ѯ���������Ϣ�����ֶ�SEND_STATUS = '10'---");
							List tplTransPlanList = messageDao.queryTplTransPlanById(transPlan.getTransPlanId());
							if(tplTransPlanList!=null&&tplTransPlanList.size()!=0){
								TplTransPlan tplTransPlan = (TplTransPlan)tplTransPlanList.get(0);
								System.out.println("tplTransPlan.getId()-->"+tplTransPlan.getId());
								tplTransPlan.setSendStatus("10");
								messageDao.update(tplTransPlan, TplTransPlan.class);
								
								// �ֻ�����ҵ�񣬼ƻ���Ϣ������ʱ���ɶ�ʱ��������ŷұ
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
//						System.out.println("---������ò�ѯ���������Ϣ��ʼ---"+transPlan.getTransPlanId());
//						Long feeId = messageDao.queryIdBySql("select id from TPL_PLAN_FEE fe where fe.BUSINESS_TYPE = '99' and fe.PLAN_ID = '"+transPlan.getTransPlanId()+"'");
//						if(feeId != null){
//							System.out.println("---������ò�ѯ���������Ϣ�����ֶ�SEND_STATUS = '10'---");
//							List tplTransPlanList = messageDao.queryTplTransPlanById(transPlan.getTransPlanId());
//							if(tplTransPlanList!=null&&tplTransPlanList.size()!=0){
//								TplTransPlan tplTransPlan = (TplTransPlan)tplTransPlanList.get(0);
//								System.out.println("tplTransPlan.getId()-->"+tplTransPlan.getId());
//								tplTransPlan.setSendStatus("10");
//								messageDao.update(tplTransPlan, TplTransPlan.class);
//								
//								// �ֻ�����ҵ�񣬼ƻ���Ϣ������ʱ���ɶ�ʱ��������ŷұ
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
						//�����߲ĺ͹���-�����
						System.out.println("�����߲ĺ͹���-�����");
						messageDao.checkTplTransPlanPick(transPlan);
						
						Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '1' and SEND_STATUS = '00' and TRANS_PLAN_ID = '"+transPlan.getTransPlanId()+"'");
						if(tempId == null){
							//������˾����ҵ�񣬼ƻ�������ʱ���ɶ�ʱ��������ŷұ
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
					 * ����ϵͳ(���쵥Ԫ)�ĺ�壺�Ǽƻ�ȫ�������Ժ�������������ȷ�ļƻ�����֧�ֵ������ϳ���-----�ᵥ���ͣ�6��ͷ�ġ�
					 * �ܿ�ϵͳ�ĺ�壺֧�ֵ������ϵĺ��-----�ᵥ���ͣ���6��ͷ���ᵥ����������ᵥ
					 * 
					 */
					if ("6".equals(transPlan.getPickType().substring(0, 1))||"7".equals(transPlan.getPickType().substring(0, 1))) {
						/**
						 * modified by Forest 20080410
						 * ���쵥Ԫ�ĺ�尴��ͳһģʽ�����쵥Ԫ�ĺ���·�������ƻ���δִ��״̬�򽫼ƻ�ɾ�����������δִ��״̬���򽫼ƻ��Ƚ᰸���ڽ��յ��µļƻ�������������
						 * =====���⴦������ֱ�����������⴦���ƻ�����ɾ����Ҫ�����᰸=====
						 */
						if ("62".equals(transPlan.getPickType()) && SystemConstants.TRANS_TYPE_TRAIN.equals(transPlan.getTransType())) {
							messageDao.doTransPlanRedOper(transPlan, "01");
						} else {
							messageDao.doTransPlanRedOper(transPlan, "00");
						}

					} else {
						messageDao.doTransPlanRedOper(transPlan, null);
					}
					
					System.out.println("---������ò�ѯ���������Ϣ��ʼ---"+transPlan.getTransPlanId());
					Long feeId = messageDao.queryIdBySql("select id from TPL_PLAN_FEE fe where fe.BUSINESS_TYPE = '99' and fe.PLAN_ID = '"+transPlan.getTransPlanId()+"'");
					Long tempId = messageDao.queryIdBySql("select ID from TPL_TRANS_PLAN_TEMP where OPERATION_TYPE = '0' and SEND_STATUS = '00' and TRANS_PLAN_ID = '"+transPlan.getTransPlanId()+"'");
					
					if(tempId == null ){
						if (feeId != null) {
							// �ֻ�����ҵ�񣬼ƻ���Ϣ������ʱ���ɶ�ʱ��������ŷұ
							TplTransPlanTemp transPlanTemp = new TplTransPlanTemp();
							transPlanTemp.setOperationType("0");// ���
							transPlanTemp.setTransPlanId(transPlan.getTransPlanId());
							transPlanTemp.setBillType("10");
							transPlanTemp.setCreateDate(new Date());
							transPlanTemp.setSendStatus("00");
							transPlanTemp.setPickNum(transPlan.getPickNum());
							messageDao.insert(transPlanTemp,TplTransPlanTemp.class);
						}
						//������˾����ҵ�񣬼ƻ�������ʱ���ɶ�ʱ��������ŷұ
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
							transPlanTemp.setOperationType("0");//���
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
					 * ǿ�ƽ᰸ ����ϵͳ��ǿ�ƽ᰸��ǿ�ƽ᰸�Ĳ�������Ҫ�����ģ������ȫ��ɾ��������������90��ͷ��
					 */
					//�̱�����ǿ�ƽ᰸���˼ƻ���ҵ���ǿ�ƽ᰸
					if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(transPlan.getUnitId())
							&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(transPlan.getPlanParticle())) {
						messageDao.updatePlanStatus("update TPL_TRANS_PLAN set STATUS = '2' where TRANS_PLAN_ID ='"
								+ transPlan.getTransPlanId() + "'");
					} else {
						messageDao.doTransPlanRedOper(transPlan, "10");
					}
				}else if (OPERATION_TYPE_APPLEND.equals(transPlan.getOperationType())) {
			        
					//��ȡ�����ƻ�id,��������и��²���,������в������
					String checkPlanExist = "select id from TPL_TRANS_PLAN where TRANS_PLAN_ID = '" + transPlan.getTransPlanId() + "'";
					Long updateId = null;
			        updateId = messageDao.queryIdBySql(checkPlanExist);
					
					//����ˮ��,1.����ʱ׷��ˮ�˴�����¼ 2. ����ʱ�������������ۼӵ�������
					Long seqId = null;
					if (SystemConstants.TRANS_TYPE_SHIP.equals(transPlan.getTransType()) && transPlan.getShipId() != null) {
						seqId = messageDao.insertTplTransShipSeq(transPlan);
					}
					//׷�Ӳ���:1.������������;2.������������
			        if (updateId!= null) {
						messageDao.insertTplTransPlanByApplend(transPlan,"20",updateId,seqId);//��������������
						messageDao.doTransPlanFixOper(transPlan, "20");
					} else {
						messageDao.insertTplTransPlanByApplend(transPlan,"10",updateId,seqId);//���������ӱ�
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
				System.out.println("====webserviceTransPlan======ѭ������========");
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
			String errTitle = "������ҵ�ƻ���" + transPlan.getTransPlanId();
			setLogMessage(e, errTitle);
		} 
		
	}

	
	// ����������ҵʵ��
	public void sendTransResult(TplMessageTransRealMain transReal) throws Exception {
		System.out.println("===========sendTransResult=====sendTransResultWebServicce===="+sendTransResultWebServicce);
		messageDao.saveTransResult(transReal);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(transReal.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(transReal.getUnitId())) { //wuyang20110309
			// �����model,�ﵽָ��ѭ������
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

	// ���ͱ���
	public void sendInsurance(TplMessageInsuranceBill insuranceBill) throws Exception {
		messageDao.saveInsurance(insuranceBill);
//		if (SystemConfigUtil.BAOSTEEL_GUFEN.equals(insuranceBill.getUnitId())) {
		if (SystemConfigUtil.judgeBaosteelOwner(insuranceBill.getUnitId())) { //wuyang20110309
			sendMsgService.sendMsg(MESSAGE_SEND_N0_INSURANCE_BILL, null, insuranceBill);
		}
	}

	// ���ʹ�����̬��Ϣ
	public void sendShipTrace(TplTransShipTrace transShipTrace) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_SHIP_TRACE, null, transShipTrace);
	}

	// ���ͳ���װ���嵥
	public void sendTransList(TplMessageTransOnMain transOn) throws Exception {
		transOn.setCreateDate(new Date());
		messageDao.insert(transOn, TplMessageTransOnMain.class);

		// �����model,�ﵽָ��ѭ������
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
	
	// ���ͳ���װ���嵥  ���ܽӿ� ���͸���������  add wyx
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

	// ����beanת����String
	private List getMsgDetail(List list, int circularTimes, String detailBeanName) throws Exception {
		List detailList = new ArrayList();
		int circular = list.size() / circularTimes;
		for (int i = 0; i < circular; i++) {
			Object[] tempArray = list.subList(i * circularTimes, (i + 1) * circularTimes).toArray();
			detailList.add(BeanConvertUtils.beanToStr(tempArray, detailBeanName));
		}
		return detailList;
	}

	// ����beanת����String
	/**
	 * ��ʱ����
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
	 * *********************************����������Դ�ӿ�*************************************
	 * 
	 * 1 ���ͷ����̿���������Ϣ 2 ���ͷ����̿����ֿ���Ϣ 3 ���ͷ����̿����ʺ���Ϣ
	 * 
	 * 4 ���շ����̿���������Ϣ 5 ���շ����̿����ֿ���Ϣ 6 ���շ����̿����ʺ���Ϣ
	 * 
	 * 7 ���շ��������� 8 ���ͳ�������Ԥ�� 9 ���Ͳִ�����Ԥ��
	 * 
	 * 10 �����ճ����� 11 �����ճ����ķ��� 12 ����ͨ����Ϣ�޸����� 13 ����ͨ����Ϣ�޸ķ���
	 * 
	 * 14 �ϱ��������� 15 �ϱ���������
	 * 
	 * **********************************************************************************
	 */
	// �����ճ�����
	public void receiveDocument(TplRDocument tplRDocument) throws Exception {
		try {
			
			messageDao.insert(tplRDocument, TplRDocument.class);
			String phone=StringUtil.trimStr(tplRDocument.getDcontcatTel());
			String emergencyMark=StringUtil.trimStr(tplRDocument.getEmergencyMark());
			if(!"".equals(phone)&&!"".equals(emergencyMark)){
			   String cd="";
			   if(emergencyMark.equals("1")){
				   cd="�ǳ�����";
			   }else if(emergencyMark.equals("2")){
				   cd="����";
			   }else{
				   cd="һ��";
			   }
				
			String message="����3PLƽ̨���µĵ��ӹ��ģ����ӹ��ĺţ�"+tplRDocument.getDocumentId()+"�������̶�Ϊ��"+cd+"�����뼰ʱ����.";
			 SendSMS.sendSMS(phone, message);
			
			}
			
		} catch (Exception e) {
			String errTitle = "�����ճ����Ĵ���: " + tplRDocument.getDocumentId();
			setLogMessage(e, errTitle);
		}
	}

	// ���շ���������
	public void receiveEvaluation(TplREvaluation tplREvaluation) throws Exception {
		try {
			messageDao.insert(tplREvaluation, TplREvaluation.class);
		} catch (Exception e) {
			String errTitle = "���շ��������۴���: " + tplREvaluation.getProviderName();
			setLogMessage(e, errTitle);
		}
	}

	// ����ͨ����Ϣ�޸ķ���
	public void receiveModifyApplyReturn(TplRModifyApplyRet tplRModifyApplyRet) throws Exception {
		try {
			messageDao.insertModifyApplyReturn(tplRModifyApplyRet);
		} catch (Exception e) {
			String errTitle = "����ͨ����Ϣ��������: " + tplRModifyApplyRet.getApplyId();
			setLogMessage(e, errTitle);
		}
	}

	// ���շ����̿���������Ϣ
	public void receiveProviderBase(TplRProvider tplRProvider) throws Exception {
		try {
			// Long pk = messageDao.queryIdBySql("select id from TPL_R_PROVIDER
			// where PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "'");
			// tplRProvider.setId(pk);
			// messageDao.update(tplRProvider, TplRProvider.class);
			// ���俪���ֿ���Ϣ�ķ����̴���
			// String updProviderSql = "update TPL_R_PROVIDER set PROVIDER_ID =
			// '" + tplRProvider.getProviderId()
			// + "',APPLY_STATUS = '2' where APPLY_ID = '" +
			// tplRProvider.getApplyId() + "' ";
			// ���俪���ֿ���Ϣ�ķ����̴���
			// String updWarehouseSql = "update TPL_R_PROVIDER_WAREHOUSE set
			// PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = '" + tplRProvider.getApplyId() + "' ";
			// ���俪�������ʺ���Ϣ�ķ����̴���
			// String updAccountSql = "update TPL_R_PROVIDER_ACCOUNT set
			// PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = '" + tplRProvider.getApplyId() + "' ";
			// ���·����̴���
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
			// // ���俪���ֿ���Ϣ�ķ����̴���
			// String updProviderSql = "update TPL_R_PROVIDER set PROVIDER_ID =
			// '" +
			// tplRProvider.getProviderId()
			// + "',APPLY_STATUS = '2' where APPLY_ID = ? ";
			// // ���俪���ֿ���Ϣ�ķ����̴���
			// String updWarehouseSql = "update TPL_R_PROVIDER_WAREHOUSE set
			// PROVIDER_ID = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = ? ";
			// // ���俪�������ʺ���Ϣ�ķ����̴���
			// String updAccountSql = "update TPL_R_PROVIDER_ACCOUNT set
			// PROVIDER_ID
			// = '" + tplRProvider.getProviderId()
			// + "' where APPLY_ID = ? ";
			// // ���·����̴���
			// messageDao.executeUpdateSql(updProviderSql);
			// messageDao.executeUpdateSql(updWarehouseSql);
			// messageDao.executeUpdateSql(updAccountSql);
			// }

			// ���Ĳ�������(10:����;20:����;00:���)
			String operationType = tplRProvider.getOperationType();
			String providerId = tplRProvider.getProviderId();
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				// ���������̻�����Ϣ,����÷������Ѵ���,�Ͳ�������
				tplRProvider.setCreateDate(new Date());
				tplRProvider.setModifyDate(new Date());
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				if (messageDao.queryIdBySql(queryIdSql) == null)
					messageDao.insert(tplRProvider, TplRProvider.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				// ��providerIdΪ�ؼ���,�����ǰ�����̻�����Ϣ���з����̼�¼�����µ����ݸ��¸ü�¼
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if(pk==null){
					String message=providerId+"�����̻�û�п�����Ϣ�����ܸ��¡�";
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
				// ɾ�������������вֿ��¼;
				String redProWarehouseSql = "delete tpl_r_provider_warehouse t where t.provider_id = '" + providerId + "'";
				messageDao.executeUpdateSql(redProWarehouseSql);
				// ɾ�������������������ʺż�¼;
				String redProAccountSql = "delete tpl_r_provider_account t where t.provider_id = '" + providerId + "'";
				messageDao.executeUpdateSql(redProAccountSql);
				// ��providerIdΪ�ؼ���ɾ���÷����̼�¼;
				String redProviderSql = "delete tpl_r_provider t where t.provider_id = '" + providerId + "'";
				messageDao.executeUpdateSql(redProviderSql);
			}
		} catch (Exception e) {
			String errTitle = "���շ����̿���������Ϣ����: " + tplRProvider.getProviderId() + ",����:" + tplRProvider.getOperationType();
			setLogMessage(e, errTitle);
		}
	}

	// ���շ����̿����ֿ���Ϣ(��ʱ����)
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
			// ���Ĳ�������(10:����;20:����;00:���)
			String operationType = tplRProviderWarehouse.getOperationType();
			String providerId = tplRProviderWarehouse.getProviderId();
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				// ��providerIdΪ�ؼ��ֲ�ѯ�����̻�����Ϣ��ȡ��ID��������ֿ̲���Ϣ��(provId),�����ֿ�ͷ�������Ϣ�Ĺ���,
				// �Ѳֿ���Ϣ���浽�����ֿ̲����
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				tplRProviderWarehouse.setProvId(pk);
				tplRProviderWarehouse.setCreateDate(new Date());
				tplRProviderWarehouse.setModifyDate(new Date());
				// ��ѯ�÷����ֿ̲��Ƿ��Ѵ���
				String queryWarehouseIdSql = "select t.id from tpl_r_provider_warehouse t where t.provider_id='" + providerId
						+ "' and t.warehouse_id='" + tplRProviderWarehouse.getWarehouseId() + "'";
				// ����÷������²ֿ��Ѵ��ھͲ�������;
				if (messageDao.queryIdBySql(queryWarehouseIdSql) == null)
					messageDao.insert(tplRProviderWarehouse, TplRProviderWarehouse.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				// ��providerId(�����̴���)��warehouseId(�ֿ����)Ϊ�ؼ���,��ѯ�ֿ���Ϣ,���µĲֿ���Ϣ���¸òֿ�����
				String queryIdSql = "select t.id from tpl_r_provider_warehouse t where t.provider_id='" + providerId
						+ "' and t.warehouse_id='" + tplRProviderWarehouse.getWarehouseId() + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if(pk==null){
					String message=tplRProviderWarehouse.getProviderId()+"�����̻�û��"+tplRProviderWarehouse.getWarehouseId()+"�ֿ�Ŀ�����Ϣ�����ܸ��¡�";
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
				// ��provider_id(�����̴���)��warehouseId(�ֿ����)Ϊ�ؼ���ɾ���òֿ��¼;
				String redSql = "delete tpl_r_provider_warehouse t where t.provider_id = '" + providerId + "' and t.warehouse_id='"
						+ tplRProviderWarehouse.getWarehouseId() + "'";
				messageDao.executeUpdateSql(redSql);
			}
		} catch (Exception e) {
			String errTitle = "���շ����̿����ֿ���Ϣ����: " + tplRProviderWarehouse.getProviderId() + ",����:"
					+ tplRProviderWarehouse.getOperationType() + ",�ֿ����:" + tplRProviderWarehouse.getWarehouseId();
			setLogMessage(e, errTitle);
		}
	}

	// ���շ����̿����ʺ���Ϣ(��ʱ����)
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
			// ���Ĳ�������(10:����;20:����;00:���)
			String operationType = tplRProviderAccount.getOperationType();
			String providerId = tplRProviderAccount.getProviderId();
			if (operationType.equals(OPERATION_TYPE_NEW)) {
				// ��providerIdΪ�ؼ��ֲ�ѯ�����̻�����Ϣ��ȡ��ID����������ʺ���Ϣ��(provId),���������ʺźͷ�������Ϣ�Ĺ���,
				// �������ʺ���Ϣ���浽�������ʺű���
				String queryIdSql = "select t.id from tpl_r_provider t where t.provider_id='" + providerId + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				tplRProviderAccount.setProvId(pk);
				 tplRProviderAccount.setCreateDate(new Date());
				 tplRProviderAccount.setModifyDate(new Date());
				// ��ѯ�÷����̵������ʺ��Ƿ��Ѵ���;
				String queryAccountIdSql = "select t.id from tpl_r_provider_account t where t.provider_id='" + providerId
						+ "' and t.bank_seq='" + tplRProviderAccount.getBankSeq() + "'";
				// ����÷����̵������ʺ��Ѵ���,�Ͳ�������
				if (messageDao.queryIdBySql(queryAccountIdSql) == null)
					messageDao.insert(tplRProviderAccount, TplRProviderAccount.class);
			} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
				// ��providerId(�����̴���)��account(�ʺŴ���)Ϊ�ؼ���,��ѯ�����ʺ���Ϣ,���µ��ʺ���Ϣ���¸������ʺ�����
				String queryIdSql = "select t.id from tpl_r_provider_account t where t.provider_id='" + providerId
						+ "' and t.bank_seq='" + tplRProviderAccount.getBankSeq() + "'";
				Long pk = messageDao.queryIdBySql(queryIdSql);
				if(pk==null){
					String message=providerId+"�����̻�û����"+tplRProviderAccount.getBankSeq()+"(��Ӧ���������)��������Ϣ�����ܸ��¡�";
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
				oldTplRProviderAccount.setRemark("�޸�");
				oldTplRProviderAccount.setModifyDate(new Date());
				messageDao.update(oldTplRProviderAccount, TplRProviderAccount.class);
			} else if (operationType.equals(OPERATION_TYPE_RED)) {
				// ��provider_id(�����̴���)��account(�ʺŴ���)Ϊ�ؼ���ɾ�����ʺż�¼;
				String redSql = "delete tpl_r_provider_account t where t.provider_id = '" + providerId + "' and t.bank_seq='"
						+ tplRProviderAccount.getBankSeq() + "'";
				messageDao.executeUpdateSql(redSql);
			}
		} catch (Exception e) {
			String errTitle = "���շ����̿����ʺ���Ϣ����: " + tplRProviderAccount.getProviderId() + ",����:"
					+ tplRProviderAccount.getOperationType() + ",�ʺ�:" + tplRProviderAccount.getAccount();
			setLogMessage(e, errTitle);
		}
	}

	// ���մ������ϵ���
	public void receiveShipManage(TplMessageShip tplMessageShip) throws Exception {
		tplMessageShip.setMessageNo("XL5207");
		tplMessageShip.setCreateDate(new Date());
		messageDao.insert(tplMessageShip, TplMessageShip.class);
		// 0����,1����;
		String inOutFlag = tplMessageShip.getInOutFlag();
		// 00����,10����,20�޸�;
		String operationType = tplMessageShip.getOperationType();
		if ("0".equals(inOutFlag)) {
			// ����;
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
			// ����;
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

	// ������������
	private TplRShipManage setShipManageValue(TplMessageShip tplMessageShip, String operationType) throws Exception {
		TplRShipManage tplRShipManage = null;
		if (operationType.equals(OPERATION_TYPE_NEW)) {
			// �ж��Ƿ����ظ���¼
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

		// tplRShipManage.setOperationType("10"); //����;
		tplRShipManage.setShipId(tplMessageShip.getShipId());
		// 0:����;1:����
		// tplRShipManage.setInOutFlag("0"); //����
		tplRShipManage.setShipName(tplMessageShip.getShipName());
		tplRShipManage.setShipEngName(tplMessageShip.getShipEngName());
		tplRShipManage.setOwnerName(tplMessageShip.getOwnerName());
		tplRShipManage.setTproviderId(tplMessageShip.getTproviderId());
		tplRShipManage.setTproviderName(tplMessageShip.getTproviderName());
		tplRShipManage.setApplyDate(tplMessageShip.getApplyDate());
		tplRShipManage.setShipType(tplMessageShip.getShipType());
		// tplRShipManage.setDockFlag("1"); //0:����;1:����ͷ;
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

	// ������������
	private LgsShipParticular setShipParticularValue(TplMessageShip tplMessageShip, String operationType) throws Exception {
		LgsShipParticular lgsShipParticular = null;
		if (operationType.equals(OPERATION_TYPE_NEW)) {

			// �ж��Ƿ����ظ���¼
			String shipId = tplMessageShip.getShipId(); // ���ʹ���
			String queryIdSql = "select t.PARTICULAR_ID from LGS_SHIP_PARTICULAR t  where 1=1 ";
			if (shipId != null && !"".equals(shipId))
				queryIdSql += " and t.SHIP_ID = '" + tplMessageShip.getShipId() + "'";
			Long pk = messageDao.queryIdBySql(queryIdSql);
			if (pk != null)
				return null;

			lgsShipParticular = new LgsShipParticular();
			lgsShipParticular.setCreateDate(new Date());
		} else if (operationType.equals(OPERATION_TYPE_UPDATE)) {
			String particularNum = tplMessageShip.getParticularNum();// �������Ϻ�
			String shipId = tplMessageShip.getShipId(); // ���ʹ���

			String queryIdSql = "select t.PARTICULAR_ID from LGS_SHIP_PARTICULAR t  where 1=1 ";
			if (particularNum != null && !"".equals(particularNum))// 3pl�������ܿظ��£��ؼ��ִ������Ϻ�
				queryIdSql += " and t.PARTICULAR_NUM = '" + tplMessageShip.getParticularNum() + "'";
			else if (shipId != null && !"".equals(shipId))// �ܿ������󣬹ܿظ���,�ؼ��ִ�����
				queryIdSql += " and t.SHIP_ID = '" + tplMessageShip.getShipId() + "'";
			else
				throw new Exception("�������Ϻźʹ��ʹ��붼Ϊ��!");

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
		// if(ShipKeys.TYPE_SALE.equals(type)){ //����
		// tplMessageShip.setTproviderId("999999");
		// tplMessageShip.setAuditor(shipParticularModel.getReplyUserName());
		// tplMessageShip.setAuditDate(shipParticularModel.getReplyDate()) ;
		// tplMessageShip.setMemo("����");
		// }else if(ShipKeys.TYPE_BUY.equals(type)){//�⹺
		// tplMessageShip.setAuditor(shipParticularModel.getSaleReplyUserName());
		// tplMessageShip.setAuditDate(shipParticularModel.getSaleReplyDate()) ;
		// tplMessageShip.setMemo("�⹺");
		// }
		//		

		return lgsShipParticular;
	}

	// �����ճ����ķ���
	public void sendDocumentReturn(TplRDocument tplRDocument) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_DOCUMENT_RETURN, null, tplRDocument);
	}

	// ����ͨ����Ϣ�޸�����
	public void sendModifyApply(TplRModifyApplyMain tplRModifyApplyMain) throws Exception {

		// �����model,�ﵽָ��ѭ������
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
logger.info("--����װ���嵥��" + new Date());

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
logger.info("--���͵����嵥��" + new Date());
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
	
	// ���ͷ����̿����ʺ���Ϣ
	public void sendProviderAccount(TplRProviderAccount tplRProviderAccount) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_PROVIDER_ACCOUNT, null, tplRProviderAccount);
	}

	// ���ͷ����̿���������Ϣ
	public void sendProviderBase(TplRProvider tplRProvider) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_PROVIDER, null, tplRProvider);
	}

	// ���ͷ����̿����ֿ���Ϣ
	public void sendProviderWarehouse(TplRProviderWarehouse tplRProviderWarehouse) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_PROVIDER_WAREHOUSE, null, tplRProviderWarehouse);
	}

	// ���ͳ�������Ԥ��
	public void sendShipAbility(TplShipAbility tplShipAbility) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_SHIP_ABILITY, null, tplShipAbility);
	}

	// ���Ͳִ�����Ԥ��
	public void sendStockAbility(TplWproviderAbility tplWproviderAbility) throws Exception {
		sendMsgService.sendMsg(MESSAGE_SEND_N0_WPROVIDER_ABILITY, null, tplWproviderAbility);
	}

	// �ϱ���������
	public void sendShipManage(TplMessageShip tplMessageShip) throws Exception {
		tplMessageShip.setMessageNo(MESSAGE_SEND_N0_SHIP_MANAGE);
		tplMessageShip.setCreateDate(new Date());
		messageDao.insert(tplMessageShip, TplMessageShip.class);
		sendMsgService.sendMsg(MESSAGE_SEND_N0_SHIP_MANAGE, null, tplMessageShip);
	}

	// �ϱ���������
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
	 * 1. ���͵����ᵥ��ŵ������  2. ���͵����ᵥ�ִ�Э�����   3.���ܹܿ��鵥ͨ����Ϣ   
	 * 4. ���͵����ᵥ��ӡ             5. �����ᵥ�ƻ�
	 *******************************************************************************************/

	// 1. ���͵����ᵥ��ŵ������
	public void sendEpickPromisee(
			TplMessageEpickPromisee tplMessageEpickPromisee) throws Exception {
		// ���汸��������Ϣ
		logger.info("3PL_MESSAGE_sendEpickPromisee: EPICK PROMISEE SENT" + tplMessageEpickPromisee.getOperationType());
		logger.info("++++++ tplMessageEpickPromisee.getClass().getName()="+tplMessageEpickPromisee.getClass().getName());
		logger.info("++++++ TplMessageEpickPromisee.class.getName()="+TplMessageEpickPromisee.class.getName());
		messageDao.insert(tplMessageEpickPromisee,
				TplMessageEpickPromisee.class);
		
		logger.info("------ 3PL_MESSAGE_sendEpickPromisee: EPICK PROMISEE SENT" + tplMessageEpickPromisee.getOperationType());
		sendMsgService.sendMsg(MESSAGE_SEND_N0_EPICK_PROMISEE, null, tplMessageEpickPromisee);

	}


//5. ���͵����ᵥ��ӡ
	public void sendEpickPrint(TplMessageEpickSend tplMessageEpickSend) throws Exception{
		//����������Ϣ--����һ�д��뽻��3PL���ô���
		//messageDao.insert(tplMessageEpickSend, TplMessageEpickSend.class);
		logger.info("sendEpickPrint Type:" + tplMessageEpickSend.getFeedbackType());
		//���͵���
		sendMsgService.sendMsg(MESSAGE_SEND_N0_STOCK_OUT_EPICK, null, tplMessageEpickSend);
		
	}

	public void sendWGQtoGK(TplMessageWgqGkSend tplMessageWgqGkSend) throws Exception{

		logger.info("---��������Ŵ����ջ���Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_NO_WAIGAOQIAO_RECEIVED, null, tplMessageWgqGkSend);
	}
	
	/*
	 *  �����ᵥ�ƻ�(non-Javadoc)
	 * @see com.baosight.baosteel.bli.tpl.interfaces.service.MessageService#receiveBill(com.baosight.baosteel.bli.tpl.model.TplMessageBillMain)
	 */
	
	public void receiveBill(TplMessageBillMain bill) throws Exception {
//		 yesimin  20150508  տ���ᵥ�Ž�ȡ
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
			
			// �ж��ظ�������Ǻ�壬���ж�Σ����ж��ظ���
			if (SystemConstants.OPERATION_TYPE_INSERT.equals(bill
					.getOperationType())) {
				String checkRepeatSql = "select id from TPL_MESSAGE_BILL_MAIN where BILL_ID = '"
						+ bill.getBillId()// �����ᵥ��??
						+ "' and OPERATION_TYPE = '"
						+ bill.getOperationType()
						+ "' and STATUS = '0'";
				if (messageDao.queryIdBySql(checkRepeatSql) != null)
					return;
			}

			/**
			 * �������쵥Ԫ�����壺���������0��֤��ȫ��Ҫ��壬����Ҫ������ϸ�� modified by Forest 20080514
			 */
			if (bill.getPlanCount().intValue() > 0) {
				bill.setBillItemSet(BeanConvertUtils.strToBean(
						bill.getDetail(), CLASS_BILL_ITEM));
			} else {
				bill.setBillItemSet(new HashSet());
			}

			// �����model

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
			bill.setUnitName(AcquireAgencyName.getAgencyName(bill.getAuthorizedUnitId()));// ��ȡί�л�������

			// ת��������˾��
			bill.setOrderUserId(this.getAreaCompanyCode(bill.getOrderUserId()));
		
			// ��ѯ�üƻ��Ѿ������������������ж����ν����Ƿ����
			Long itemCount = messageDao
					.queryIdBySql("select count(t.ID) from TPL_MESSAGE_BILL_ITEM t where t.BILL_NO = "
							+ bill.getId());

			// �ж��ظ����������ͬ�����͵����⣬��ʱ���ɷ��� add by zhengfei 20080128
			if (bill.getBillItemSet() != null
					&& bill.getBillItemSet().size() > 0) {
				// ��ȡ������������һ������
				Iterator itDouble = bill.getBillItemSet().iterator();
				String someOnePack = "";
				String productClass = "";
				if (itDouble != null) {
					TplMessageBillItem billitem = (TplMessageBillItem) itDouble
							.next();
					someOnePack = billitem.getPackId();
					//��Ҫ��ò�Ʒ����
					if(billitem.getOrderNum()!= null && !billitem.getOrderNum().trim().equals("")){
					 productClass = billitem.getOrderNum().substring(0, 1);
					}
				}
				// �жϸüƻ��µ��������Ƿ��Ѿ����
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

				// ����������Ѿ���⣬�򲻴�����������
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

			// �жϼƻ��Ƿ�����������������ҵ���
			if (bill.getPlanCount().intValue() == bill.getBillItemSet().size()
					+ itemCount.intValue()) {
				messageDao.updatePlanStatus("update TPL_MESSAGE_BILL_MAIN set STATUS = '0' where BILL_ID = '"
								+ bill.getBillId()
								+ "'   and OPERATION_TYPE = '"
								+ bill.getOperationType() + "'");

				if (OPERATION_TYPE_NEW.equals(bill.getOperationType())) {
					/**
					 * ��������
					 * ����ϵͳ(���쵥Ԫ)����ļƻ������·�����:��������쵥Ԫ�����ļƻ�����������������ļƻ�3PL�Ѿ�����
					 * ����֤�����ڱ��μƻ���ߵ�������Ҫ���ġ�
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
					 * ����ϵͳ(���쵥Ԫ)�ĺ�壺�Ǽƻ�ȫ�������Ժ�������������ȷ�ļƻ�����֧�ֵ������ϳ���-----�ᵥ���ͣ�6
					 * ��ͷ�ġ� �ܿ�ϵͳ�ĺ�壺֧�ֵ������ϵĺ��-----�ᵥ���ͣ���6��ͷ���ᵥ����������ᵥ
					 * 
					 */
					if ("6".equals(bill.getPickType().substring(0, 1))||"7".equals(bill.getPickType().substring(0, 1))) {
						/**
						 * modified by Forest 20080410
						 * ���쵥Ԫ�ĺ�尴��ͳһģʽ�����쵥Ԫ�ĺ���·�������ƻ���δִ��״̬�򽫼ƻ�ɾ��
						 * ���������δִ��״̬���򽫼ƻ��Ƚ᰸���ڽ��յ��µļƻ�������������
						 * =====���⴦������ֱ�����������⴦���ƻ�����ɾ����Ҫ�����᰸=====
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
					 * ǿ�ƽ᰸ ����ϵͳ��ǿ�ƽ᰸��ǿ�ƽ᰸�Ĳ�������Ҫ�����ģ������ȫ��ɾ��������������90��ͷ��
					 */
					//�̱�����ǿ�ƽ᰸�ᵥ��ҵ���ǿ�ƽ᰸
					if (SystemConfigUtil.BAOSTEEL_BGSQ.equals(bill.getUnitId())
							&& SystemConfigUtil.BAOSTEEL_BGSQ_AMOUNT.equals(bill.getPlanParticle())) {
						
					} else {
						messageDao.doBillRedOper(bill, "10");
					}
				} else if (OPERATION_TYPE_APPLEND.equals(bill
						.getOperationType())) {

					// ��ȡ�����ƻ�id,��������и��²���,������в������
					String checkPlanExist = "select id from TPL_BILL where BILL_ID  = '"
							+ bill.getBillId() + "'";
					Long updateId = null;
					updateId = messageDao.queryIdBySql(checkPlanExist);
					// ׷�Ӳ���:1.������������;2.������������
					if (updateId != null) {
						messageDao.insertTplBillByApplend(bill, "20",
								updateId);// ��������������
					//	messageDao.doBillFixRedOper(bill, "20");
					} else {
						messageDao.insertTplBillByApplend(bill, "10",
								updateId);// ���������ӱ�
					}

				}
			}
			
		
		} catch (Exception e) {
			String errTitle = "�ᵥ�ƻ���" + bill.getBillId();
			setLogMessage(e, errTitle);
			e.printStackTrace();
		}

	}
	
	/*
	 *  add by tangwei
	 *  �����ݻ���֤��Ϣ(non-Javadoc)
	 * @see com.baosight.baosteel.bli.tpl.interfaces.service.MessageService#receiveBill(com.baosight.baosteel.bli.tpl.model.TplMessageBillMain)
	 */
	
	public void receiveAcceptInfo(TplMessageAcceptInfo messageAcceptInfo) throws Exception {
		logger.info("messageAcceptInfo start");
		try {
			messageDao.insertTplAcceptInfo(messageAcceptInfo);
		} catch (Exception e) {
			String errTitle = "�ƻ��ţ�" + messageAcceptInfo.getTransInstructionId() +"��ͬ�ţ�" +messageAcceptInfo.getOrderNo();
			setLogMessage(e, errTitle);
			e.printStackTrace();
		}
		logger.info("messageAcceptInfo end");

	}



	public void sendTransPlanResult(TplMessageTransPlanResult transPlanResult)
			throws Exception {
		logger.info("sendTransPlanResult Type:" );
		messageDao.insert(transPlanResult, TplMessageTransPlanResult.class);
		//���͵���
		sendMsgService.sendMsg(MESSAGE_TRANS_PLAN_RESULT, null, transPlanResult);
		
	}


	public void sendTransSignResult(TplMessageTransSignResult transSignResult)
			throws Exception {
		logger.info("sendEpickPrint Type:" );
		messageDao.insert(transSignResult, TplMessageTransSignResult.class);
		//���͵���
		sendMsgService.sendMsg(MESSAGE_TRANS_SIGN_RESULT, null, transSignResult);
	}


	public void sendTransTransStartResult(
			TplMessageTransStartResult transStartResult) throws Exception {
		logger.info("sendEpickPrint Type:" );
		System.out.println("����������"+transStartResult.getTruckNo());
		messageDao.insert(transStartResult, TplMessageTransStartResult.class);
		//���͵���
		sendMsgService.sendMsg(MESSAGE_TRANS_START_RESULT, null, transStartResult);
		
	}
	
	//��������ƻ���������
	public void sendTransTransResult( TplTransDeliveryPlanReturn tplTransDeliveryPlanReturn) throws Exception{
		logger.info("sendEpickPrint Type:" );
		messageDao.insert(tplTransDeliveryPlanReturn, TplTransDeliveryPlanReturn.class);
		//���͵���
		sendMsgService.sendMsg(MESSAGE_TRANS_RESULT, null, tplTransDeliveryPlanReturn);
		//�������˶�̬����
		sendTransCarDynWebServicce.sendCarDynWebservice(tplTransDeliveryPlanReturn);
	}

	// �����߲ķ�����ɱ�־����
	public void receiveMessagePickDataEnd(LgsMessagePickDataEnd pickDataEnd) throws Exception {
		try {
			//9672ί�л�������ˣ������������쵥Ԫ����Ԫ��֮���9672�ڶ����л�����Ҫ9672�ָ�ί�к����쵥Ԫ������ȥ��
//			pickDataEnd.setUnitId("0001");
//			pickDataEnd.setManuId("BGSA");
			
			//�ж��ظ�
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
			String errTitle = "�����߲ķ�����ɱ�־����: �ᵥ��" + pickDataEnd.getPickNum() + ",��ͬ��:" + pickDataEnd.getOrderNum() + ",׼����:" + pickDataEnd.getReadyNum();
			setLogMessage(e, errTitle);
		}
	}

	public void sendTransShipTrends(TplMessageTransShipTrends transShipTrends)
			throws Exception {
		logger.info("sendEpickPrint Type: ������̬��Ϣ����" );
		if(transShipTrends!=null){
			transShipTrends.setCreateDate(new Date());
		messageDao.insert(transShipTrends, TplMessageTransShipTrends.class);
		logger.info("=====webservice===����====getShipIdFromWebTransPlan===");
		String shipId = messageDao.getShipIdFromWebTransPlan(transShipTrends);
		logger.info("=====webservice=======shipId==="+shipId);
		logger.info("=====webservice=======sendTransShipDynImpl==="+sendTransShipDynImpl);
		if(null!= shipId && !"".equals(shipId)){
			sendTransShipDynImpl.sendTransShipDyn(transShipTrends);
		}
		//���͵���
		sendMsgService.sendMsg(MESSAGE_TRANS_SHIP_TRENDS, null, transShipTrends);
		}
	}

	// �����߲ķ�����ɱ�־����
	public void receiveMessageReturnPack(LgsMessageReturnPack messageReturnPack) throws Exception {
		try {
			System.out.println("....................������ֲ��ϵ���............��ʼ");
			//��ַ�����������
			//�ж��ظ�
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
				System.out.println("....................������ֲ��ϵ���............");
				messageDao.insertLgsMessageReturnPack(messageReturnPack);
				//��ֲ��Ϸ���
				this.doReturnPack(messageReturnPack);
				System.out.println("....................������ֲ��ϵ���............����");
			}
		} catch (Exception e) {
			String errTitle = "���չ�ַ�������: ��ͬ��" + messageReturnPack.getOrderNum() + ",���Ϻ�:" + messageReturnPack.getPackId();
			setLogMessage(e, errTitle);
		}
	}
	
	// ������ֹ�ַ�������
	public void receiveMessageReturnPackForBGBW(LgsMessageReturnPack messageReturnPack) throws Exception {
		try {
			System.out.println("....................������ֲ��ϵ���............��ʼ");
			//��ַ�����������
			//�ж��ظ�
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
				System.out.println("....................������ֲ��ϵ���............");
				messageDao.insertLgsMessageReturnPack(messageReturnPack);
				//��ֲ��Ϸ���
				this.doReturnPack(messageReturnPack);
				System.out.println("....................������ֲ��ϵ���............����");
			}
		} catch (Exception e) {
			String errTitle = "���չ�ַ�������: ��ͬ��" + messageReturnPack.getOrderNum() + ",���Ϻ�:" + messageReturnPack.getPackId();
			setLogMessage(e, errTitle);
		}
	}
	
	//9672 ��ֲ���,�������Ϸ���
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
			System.out.println("��ѯ���..." + queryIdSql + "");
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id != null){
				//��ֳ������������
				String updateSql = "update TPL_STOCK set EXTEND_WAREHOUSE_FLAG = '10', EXTEND_WAREHOUSE_CODE = '" + messageReturnPack.getExtendWarehouseCode() + "', PRODUCT_PROPERTY = '10', STOCK_FLAG = '10', CONFIRM_STATUS = '10', MODIFY_DATE=SYSDATE where PACK_ID = '"
				+ messageReturnPack.getPackId() + "' and PRODUCT_CLASS = '"
				+ messageReturnPack.getOrderNum().substring(0, 1) + "' and UNIT_ID = '"
				+ messageReturnPack.getUnitId() + "' and EXTEND_WAREHOUSE_FLAG is null and PACK_STATUS = '00' and STOCK_FLAG = '20'";
				messageDao.updateStockStatus(updateSql);
				
				System.out.println("updateSql................�������..." + updateSql);
			}
		} catch (Exception e) {
			String errTitle = "���չ�ַ�������:" + messageReturnPack.getOrderNum();
			setLogMessage(e, errTitle);
		} 
	}
	
	//����˾��������ϢӦ�����
	public void receiveTplRDriverReturn(TplRDriverReturn tplRDriverReturn) throws Exception{
		tplRDriverReturn.setCreateDate(new Date());
		messageDao.insert(tplRDriverReturn, tplRDriverReturn.getClass());
	}
	//���ճ���������ϢӦ�����
	public void receiveTplRHeadstockReturn(TplRHeadstockReturn tplRHeadstockReturn) throws Exception{
		tplRHeadstockReturn.setCreateDate(new Date());
		messageDao.insert(tplRHeadstockReturn, tplRHeadstockReturn.getClass());
	}

	public void receiveLgsStackStorageYard(
			LgsStackStorageYard lgsStackStorageYard) throws Exception{
		try {
			logger.info("����==========================================" );
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
			String errTitle = "������뵥����:" + lgsStackStorageYard.getPackNum();
			setLogMessage(e, errTitle);
		}
			
	}
	private String dateToString(String actputinDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		Date dateString = df.parse(actputinDate);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//ʱ���ʽ
		String sysDatetime = fmt.format(dateString); 
		return sysDatetime;
	}

	public void sendFlyTransPlanResult(TplMessageFlySend flySend)
			throws Exception {
		try {
			sendMsgService.sendMsg(MESSAGE_SEND_N0_TRANS_FLY, null, flySend);
			messageDao.insert(flySend,flySend.getClass());
			
		} catch (Exception e) {
			String errTitle = "�ɵ�ת��Ȩ����:" + flySend.getBillId();
			setLogMessage(e, errTitle);
		}
			
		
	}

	public void sendEntryPermit(TplREntryPermitZj tplREntryPermitZj, String no) throws Exception {
		System.out.println("���ͽ�������");
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
		

		logger.info("---���ͽ���֤��Ϣ��" + new Date());
		messageDao.insert(tplMessageEntryPermit, TplMessageEntryPermit.class);
		sendMsgService.sendMsg(MESSAGE_SEND_ENTRY_PERMIT, null, tplMessageEntryPermit);
		System.out.println("����");
	}

	private String parseDateToString(Date time) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");//ʱ���ʽ
		return fmt.format(time);
	}

	
	// ��������ȱ�ݵ��� add by xty 2015/12/11
	// ������ܵ�����Ϣ�����ݴ������� OperationType �ֶ��жϣ�
	// ֵΪ10���������У�ֵΪ20���޸Ĳ�����ֵΪ30��ɾ������
	public void receiveTplQualityDefectConfig(TplQualityDefectConfig qualityDefectConfig) throws Exception {
		try {
			System.out.println("��������ȱ����Ϣ������");
			System.out.println("qualityDefectConfig:::"+qualityDefectConfig.toString());
			if(qualityDefectConfig.getOperationType().equals("10")){//����
				String queryIdSql = "select id from TPL_QUALITY_DEFECT_CONFIG where 1=1 "
					+ " and TRANS_TYPE = '"
					+ qualityDefectConfig.getTransType() + "' and DEFECT_CODE = '"
					+ qualityDefectConfig.getDefectCode() + "'";
				System.out.println("queryQualityDefectConfig:::"+queryIdSql);
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id == null){
					messageDao.insertTplQualityDefectConfig(qualityDefectConfig);
				}
			}else if(qualityDefectConfig.getOperationType().equals("20")){//�޸�
				System.out.println("��������ȱ����Ϣ������");
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
			}else{//ɾ��
				System.out.println("ɾ������ȱ����Ϣ������");
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
			String errTitle = "����ȱ�ݵ���:idΪ" + qualityDefectConfig.getId() + ",ȱ������Ϊ" + qualityDefectConfig.getDefectName();
			setLogMessage(e, errTitle);
		} 
	}
	
	// �����������͵��� add by xty 2015/12/11
	// ������ܵ�����Ϣ�����ݴ������� OperationType �ֶ��жϣ�
	// ֵΪ10���������У�ֵΪ20���޸Ĳ�����ֵΪ30��ɾ������
	public void receiveTplShooteTypeConfig(TplShooteTypeConfig shooteTypeConfig) throws Exception {
		try {
			if(shooteTypeConfig.getOperationType().equals("10")){//����
				System.out.println("��������������Ϣ������");
				String queryIdSql = "select id from TPL_SHOOTE_TYPE_CONFIG where TRANS_TYPE = '"
					+shooteTypeConfig.getTransType()+"' and CAMERA_CODE = '"
					+shooteTypeConfig.getCameraCode()+"' and CAMERA_NUM = '"
					+shooteTypeConfig.getCameraNum()+"' ";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id == null){
					messageDao.insertTplShooteTypeConfig(shooteTypeConfig);
				}
			}else if(shooteTypeConfig.getOperationType().equals("20")){//�޸�
				System.out.println("��������������Ϣ������");
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
				//ɾ��
				System.out.println("ɾ������������Ϣ������");
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
			String errTitle = "�������͵���:idΪ" + shooteTypeConfig.getId() + ",��������Ϊ" + shooteTypeConfig.getCameraName();
			setLogMessage(e, errTitle);
		} 
	}
	
	// ���մ����ŷ����ֻ���¼����Ϣ���� add by xty 2015/12/11
	// ������ܵ�����Ϣ�����ݴ������� OperationType �ֶ��жϣ�
	// ֵΪ10���������У�ֵΪ20��ɾ������
	public void receiveTplShipMobileQuality(TplShipMobileQuality shipMobileQuality) throws Exception {
		try {
			if(shipMobileQuality.getOperationType().equals("10")){//����
				String queryIdSql = "select id from TPL_SHIP_MOBILE_QUALITY where TRADING_ID = '"
					+shipMobileQuality.getTradingId()+"' and TPROVIDER_ID = '"
					+shipMobileQuality.getTproviderId()+"'";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id == null){
					//��ȡ�����û�����
					String tradingId = shipMobileQuality.getTradingId();
					// ת��������˾��
					shipMobileQuality.setOrderUserId(this.getAreaCompanyCode(tradingId));
					System.out.println("shipMobileQuality:OrderUserId----"+shipMobileQuality.getOrderUserId());
					messageDao.insertTplShipMobileQuality(shipMobileQuality);
				}
			}else{
				//ɾ��
				String queryIdSql = "select id from TPL_SHIP_MOBILE_QUALITY where TRADING_ID = '"
					+shipMobileQuality.getTradingId()+"' and TPROVIDER_ID = '"
					+shipMobileQuality.getTproviderId()+"'";
				Long id = messageDao.queryIdBySql(queryIdSql);
				if(id != null){
					messageDao.deleteTplShipMobileQuality(id);
				}
			}
			
		} catch (Exception e) {
			String errTitle = "�����ŷ����ֻ��ŵ���:idΪ" + shipMobileQuality.getId() + "������Ϊ" + shipMobileQuality.getShipId() + "�ֻ���Ϊ" + shipMobileQuality.getMobileNum();
			setLogMessage(e, errTitle);
		} 
	}
	//������������ͼƬ���� add by xty 2015/12/11
	public void sendTplMessageQualityPictures(TplMessageQualityPictures messageQualityPictures) throws Exception{
		System.out.println("������������ͼƬ...");
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
	
	// �����ֻ���ע����� add by xty 2015/12/11
	public void sendTplPhoneRegistered(TplMessagePhoneRegistered tplMessagePhoneRegistered) throws Exception{
		System.out.println("�����ֻ���ע��...");
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
	
	// ���� ��Ƥ��;��̬ add wyx 
	public void receiveTrainsOnLoad(TplMessageSetModel tplMessageSetModel){
		System.out.println("--------------------1");
		
		TplMessageLog tplMessageLog = new TplMessageLog();
		tplMessageLog.setCreateDate(new Date());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//ʱ���ʽ
		
		TplMessageTrainsOnLoad tplMessageTrainsOnLoad = new TplMessageTrainsOnLoad();
		try {
			Iterator it = BeanConvertUtils.strToBean(tplMessageSetModel.getDetail(), CLASS_MESSAGE_TRAINS_ON_LOAD).iterator();
			while (it.hasNext()) {
				tplMessageTrainsOnLoad = (TplMessageTrainsOnLoad) it.next();
			}
			
			if (tplMessageTrainsOnLoad != null) {
				// ����
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
			tplMessageLog.setErrTitle("��Ƥ��;��̬�����쳣:"+tplMessageTrainsOnLoad.getCarNo());
			messageDao.insert(tplMessageLog,TplMessageLog.class);
		}
	}

	// ���� ��Ƥװж������ add wyx
	public void receiveTrainsLoading(TplMessageSetModel tplMessageSetModel){
		System.out.println("1-------------------------");
		TplMessageLog tplMessageLog = new TplMessageLog();
		tplMessageLog.setCreateDate(new Date());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//ʱ���ʽ
		
		TplMessageTrainsLoading tplMessageTrainsLoading = new TplMessageTrainsLoading();
		try {
			Iterator it = BeanConvertUtils.strToBean(tplMessageSetModel.getDetail(), CLASS_MESSAGE_TRAINS_LOADING).iterator();
			while (it.hasNext()) {
				tplMessageTrainsLoading = (TplMessageTrainsLoading) it.next();
			}
			
			if (tplMessageTrainsLoading != null) {

				// ����
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
			tplMessageLog.setErrTitle("��Ƥװж����������쳣:"+tplMessageTrainsLoading.getCarNo());
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
	 
	// ������������ҵ�ƻ���Ϣ
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
		// �����model,�ﵽָ��ѭ������
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
	//տ����ʱ����֤���ķ���[XZXL11]
	public void sendEntryPermit02(TplMessageEntryPermitZj tplMessageEntryPermitZj)
			throws Exception {
		Date date = new Date();
		System.out.println("���ͽ�������");
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
		// �����model,�ﵽָ��ѭ������
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
		System.out.println("����֤��ϸ��Ϣ");
		for (int i = 0; i < detailList.size(); i++) {
			tplMessageEntryPermitZj.setDetail((String) detailList.get(i));
			logger.info("---���ͽ���֤��Ϣ��" + new Date());
			sendMsgService.sendMsg(MESSAGE_SEND_ENTRY_PERMIT_02, null, tplMessageEntryPermitZj);
		}
		System.out.println("����");
	}

	//����������Ϣ����[XZXL19]
	public void sendTplShipInqManage(TplShipReacfManage tplShipReacfManage,String procType)
			throws Exception {
		TplMessageShipReacfManage shipReacfManage = new TplMessageShipReacfManage();
		shipReacfManage.setProcType(procType);
		if(tplShipReacfManage.getProviderId()!=null && !tplShipReacfManage.getProviderId().trim().equals("")){
			shipReacfManage.setProviderId(tplShipReacfManage.getProviderId());
		}else{
			shipReacfManage.setProviderId("����");
		}
		if(tplShipReacfManage.getProviderName()!=null && !tplShipReacfManage.getProviderName().trim().equals("")){
			shipReacfManage.setProviderName(tplShipReacfManage.getProviderName());
		}else{
			shipReacfManage.setProviderName("����");
		}
		shipReacfManage.setShipName(tplShipReacfManage.getShipName());
		shipReacfManage.setShipArriveTime(DateUtils.toString(tplShipReacfManage.getShipArriveTime(),SystemConstants.dateformat6));
		shipReacfManage.setCarryWeight(tplShipReacfManage.getCarryWeight());
		shipReacfManage.setRegion(tplShipReacfManage.getRegion());
		shipReacfManage.setPlanPort(tplShipReacfManage.getPlanPort());
		shipReacfManage.setShipType(tplShipReacfManage.getShipType());
		shipReacfManage.setRemarks(tplShipReacfManage.getRemarks());
		shipReacfManage.setCreateDate(new Date());
		System.out.println("�����������۵�����Ϣ");
		messageDao.insert(shipReacfManage, TplMessageShipReacfManage.class);
		logger.info("---���ʹ���������Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_SHIP_INQ_MANAGE, null, shipReacfManage);
		System.out.println("����");
	}
	
	//˾����������Ϣ����[XZXL12]
	public void sendCarMessageBind(TplMessageCarBind tplMessageCarBind)
			throws Exception {
		System.out.println("˾����������Ϣ����");
		if (tplMessageCarBind != null) {
			messageDao.insert(tplMessageCarBind, TplMessageCarBind.class);
			Set tplMessageCarBindItemSet = tplMessageCarBind.getTplMessageCarBindItem();
			for (Iterator it = tplMessageCarBindItemSet.iterator(); it.hasNext();) {
				TplMessageCarBindItem tplMessageCarBindItem = (TplMessageCarBindItem) it.next();
				tplMessageCarBindItem.setCarBindId(tplMessageCarBind.getId());
				messageDao.insert(tplMessageCarBindItem,TplMessageCarBindItem.class);
				System.out.println("�ᵥ��ϸ��"+tplMessageCarBindItem.getPickNo()+"*****"+tplMessageCarBindItem.getStoreSystem()+"�ᵥID:"+tplMessageCarBind.getId());
			}
		}
		System.out.println(tplMessageCarBind);
		System.out.println("----");
		System.out.println(tplMessageCarBind.getTplMessageCarBindItem());
		System.out.println("----");
		System.out.println(tplMessageCarBind.getTplMessageCarBindItem().size());
		// �����model,�ﵽָ��ѭ������
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
		System.out.println("˾����������ϸ��Ϣ");
		for (int i = 0; i < detailList.size(); i++) {
			tplMessageCarBind.setDetail((String) detailList.get(i));
			logger.info("---˾����������Ϣ��" + new Date());
			sendMsgService.sendMsg(MESSAGE_TPL_MESSAGE_CAR_BIND_ITEM, null, tplMessageCarBind);
		}
		System.out.println("����");
		
	}
	
	/**
	 * ���ᳵǩ��[XZXL13]����
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
		// �����model,�ﵽָ��ѭ������
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
		System.out.println("������ϸ��Ϣ");
		for (int i = 0; i < detailList.size(); i++) {
			tplMSinceMaterial.setDetail((String) detailList.get(i));
			logger.info("---˾����������Ϣ��" + new Date());
			sendMsgService.sendMsg(MESSAGE_SEND_CAR, null, tplMSinceMaterial);
		}
		System.out.println("����");
	}

	//���ᳵ��׼������Ӧ��[XLXZ48]
	public void receiveTplPermitMessageZj(TplPermitMessageZj tplPermitMessageZj)
			throws Exception {
		System.out.println("���ᳵ��׼������Ӧ��");
		System.out.println("1++++++++++");
		tplPermitMessageZj.setCreateDate(new Date());
		messageDao.insert(tplPermitMessageZj,TplPermitMessageZj.class);
		//ִ�н���֤���볷��
		if(tplPermitMessageZj.getResultFlag()!=null && tplPermitMessageZj.getResultFlag().equals("Y")){
			String  queryHql = " from TplREntryPermitZj as t where 1=1 and t.billCode = '"+tplPermitMessageZj.getBillCode()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				TplREntryPermitZj tplREntryPermitZj= (TplREntryPermitZj) list.get(0);
				tplREntryPermitZj.setSendStatus("0");//δ����
				tplREntryPermitZj.setRequestResult("");//���ɹ���Ϊδ���
				messageDao.update(tplREntryPermitZj, TplREntryPermitZj.class);
			}
		}else if(tplPermitMessageZj.getRequestResult()!=null && tplPermitMessageZj.getRequestResult().equals("Y")){//׼������ͨ��
			String  queryHql = " from TplREntryPermitZj as t where 1=1 and t.billCode = '"+tplPermitMessageZj.getBillCode()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				TplREntryPermitZj tplREntryPermitZj= (TplREntryPermitZj) list.get(0);
				tplREntryPermitZj.setRequestResult("1");//�н���֤�ʸ�
				messageDao.update(tplREntryPermitZj, TplREntryPermitZj.class);
			}
		}else if(tplPermitMessageZj.getRequestResult()!=null && tplPermitMessageZj.getRequestResult().equals("N")){//׼������ͨ��
			String  queryHql = " from TplREntryPermitZj as t where 1=1 and t.billCode = '"+tplPermitMessageZj.getBillCode()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if(list!=null && list.size()>0){
				TplREntryPermitZj tplREntryPermitZj= (TplREntryPermitZj) list.get(0);
				tplREntryPermitZj.setRequestResult("0");//�޽���֤�ʸ�
				messageDao.update(tplREntryPermitZj, TplREntryPermitZj.class);
			}
		}
		System.out.println("2++++++++++");
	}
	
	/**
	 * ���ᳵ�����ؼƻ�[XLXZ43]
	 */
	public void receiveTplSinceCarPlan(TplMessageCarPlan tplMessageCarPlan)
			throws Exception {
		Date date = new Date();
		System.out.println("���ᳵ��������Ϣ���տ�ʼ");
		tplMessageCarPlan.setTplMessageCarPlanItemSet(BeanConvertUtils.strToBean(tplMessageCarPlan.getDetail(), CLASS_SINCE_CAR_PLAN_ITEM));
		// �����model
		Iterator it = tplMessageCarPlan.getTplMessageCarPlanItemSet().iterator();
		while (it.hasNext()) {
			TplMessageCarPlanItem tplMessageCarPlanItem = (TplMessageCarPlanItem) it.next();
			if ("".equals(tplMessageCarPlanItem.getPackNo())) {
				it.remove();
			}
		}
		//����������ж���
		if(tplMessageCarPlan.getProcType().equals("I")){
			String  queryHql = " from TplCarPlan as t where 1=1 and t.carPickNo = '"+tplMessageCarPlan.getCarPickNo()+"' and t.carNum = '"+tplMessageCarPlan.getCarNum()+"' and t.seqId = '"+tplMessageCarPlan.getSeqId()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				return;
			}
		}
		
		//���յ��ı�����
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
		//�ѽ��ĳ��β��������ؼƻ�
		String queryTplCarBindHql = " from TplCarBind as t where 1=1 and t.seqId = '" + tplMessageCarPlan.getSeqId()+"'";
		List carBindList = messageDao.queryBySql(queryTplCarBindHql);
		if(carBindList!=null){
			TplCarBind tplCarBind = (TplCarBind)carBindList.get(0);
			String revokeStruts = tplCarBind.getRevokeStruts();
			if(revokeStruts!=null && revokeStruts.equals("1")){
				TplMessageLog tplMessageLog = new TplMessageLog();
				tplMessageLog.setErrTitle("���ؼƻ�����ʧ��");
				tplMessageLog.setErrContent(tplMessageCarPlan.getCarPickNo()+"���ؼƻ�����ʧ��:���κ�:"+tplMessageCarPlan.getSeqId()+"�ѱ����");
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
			tplMessageLog.setErrTitle("���ؼƻ�����ʧ��");
			tplMessageLog.setErrContent(tplMessageCarPlan.getCarPickNo()+"���ؼƻ�����ʧ��:���κ�:"+tplMessageCarPlan.getSeqId()+"������");
			tplMessageLog.setOperMemo("XLXZ43");
			tplMessageLog.setCreateDate(new Date());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
			return;
		}
		
		if(tplMessageCarPlan.getProcType().equals("I")){//����
			//�������ᳵ�����ؼƻ�ҵ������Ϣ
			TplCarPlan tplCarPlan = new TplCarPlan();
			tplCarPlan.setCarNum(tplMessageCarPlan.getCarNum());
			tplCarPlan.setCarPickNo(tplMessageCarPlan.getCarPickNo());
			tplCarPlan.setCount(tplMessageCarPlan.getCount());
			tplCarPlan.setDriverId(tplMessageCarPlan.getDriverId());
			tplCarPlan.setGrossWeight(null !=tplMessageCarPlan.getGrossWeight()? (tplMessageCarPlan.getGrossWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));//kg���ɶ֣�����λС��
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
						//���ᳵ�����ؼƻ�ҵ������Ϣ
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
						
						// �����ɵ�Ϊִ����״̬
//						String updateSql = "update tpl_trans_truck_dis_plan t set t.status = '10' where t.pick_num = '"+tplMessageCarPlanItem.getPickNo()+"'";
//						messageDao.updateBySql(updateSql);
						String packId = tplMessageCarPlanItem.getPackNo();
						String pickNum = tplMessageCarPlanItem.getPickNo();
						TplTransPack transPack = messageDao.searchTransPackForCarPlan(packId, pickNum);
						if(transPack!=null){
							transPack.setSeqId(new Long(seqId));
							transPack.setPackStatus("1");//��װ��
							transPack.setProStatus("30");//��װ��
							messageDao.update(transPack, TplTransPack.class);
						}
					}
				}
			}
			//ˢ�³�����Ϣë�أ����أ�pro_status
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
				tplTransSeq.setUploadTime(date);//װ��ʱ��
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
					//���¼ƻ�
					tplTransPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransPlan.setActCount(actCount);
					tplTransPlan.setStatus("1");
					messageDao.update(tplTransPlan,TplTransPlan.class);
				
					//�������˵��ȼƻ�
					TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
					tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransTruckDisPlan.setActCount(actCount);
					tplTransTruckDisPlan.setStatus("10");
					tplTransTruckDisPlan.setTransPlanId(transPlanId);
					//������ָ���ļƻ���ȥ�������е����˵��ȼƻ�
					messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);
				}
			}
		}else if(tplMessageCarPlan.getProcType().equals("D")){//ɾ��
			//ҵ���
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
		}else if(tplMessageCarPlan.getProcType().equals("U")){//�޸�
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
					tplCarPlan.setGrossWeight(null !=tplMessageCarPlan.getGrossWeight()? (tplMessageCarPlan.getGrossWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));//kg���ɶ֣�����λС��
					tplCarPlan.setNetWeight(null !=tplMessageCarPlan.getNetWeight()? (tplMessageCarPlan.getNetWeight().divide(new BigDecimal("1000"), 6, BigDecimal.ROUND_UP)):new BigDecimal("0"));
					tplCarPlan.setSeqId(tplMessageCarPlan.getSeqId());
					tplCarPlan.setTrailerNum(tplMessageCarPlan.getTrailerNum());
					tplCarPlan.setProviderId(tplMessageCarPlan.getProviderId());
					tplCarPlan.setProviderName(tplMessageCarPlan.getProviderName());
					tplCarPlan.settStatus("0");
					messageDao.update(tplCarPlan, TplCarPlan.class);
				}
				//ɾ������Ϣ���������
				if(tplCarPlan!=null && tplCarPlan.getId()!=null){
					String deleteTplCarPlanItem = "delete from TPL_CAR_PLAN_ITEM t where t.CAR_PLAN_ID = " + tplCarPlan.getId();
					messageDao.deleteSql(deleteTplCarPlanItem);
					if(tplMessageCarPlan.getTplMessageCarPlanItemSet()!=null && tplMessageCarPlan.getTplMessageCarPlanItemSet().size()>0){
						Iterator itDouble = tplMessageCarPlan.getTplMessageCarPlanItemSet().iterator();
						if(itDouble != null ){
							while (itDouble.hasNext()) {
								TplMessageCarPlanItem tplMessageCarPlanItem = (TplMessageCarPlanItem)itDouble.next();
								//���ᳵ�����ؼƻ�ҵ������Ϣ
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
			//ˢ�³�����Ϣë�أ����أ�pro_status
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
				tplTransSeq.setUploadTime(date);//װ��ʱ��
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
		}
		System.out.println("����");
	}
	
	//������λ����[XLXZ45]
	//����tplMessageCarPosition��procType�ֶ��ж������������޸�'I','D','U'
	public void receiveTplCarPosition(TplMessageCarPosition tplMessageCarPosition) throws Exception {
		System.out.println("������λ����");
		Date date = new Date();
		//����������ж��ظ�
		if(tplMessageCarPosition.getProcType().equals("I")){
			String  queryHql = " from TplCarPosition as t where 1=1 and t.storeCode = '"+tplMessageCarPosition.getStoreCode()+"' and t.parkingCode = '"+tplMessageCarPosition.getParkingCode()+"' and carPickNo ='"+tplMessageCarPosition.getCarPickNo()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				return;
			}
		}
		//���յ��ı�
		tplMessageCarPosition.setCreateDate(date);
		messageDao.insert(tplMessageCarPosition, TplMessageCarPosition.class);
		
		if(tplMessageCarPosition.getProcType().equals("I")){//����
			System.out.println("����ҵ�����Ϣ������");
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
		}else if(tplMessageCarPosition.getProcType().equals("D")){//������λ����
			System.out.println("ɾ��ҵ�����Ϣ������");
			String seqId = tplMessageCarPosition.getSeqId();
			String storeCode = tplMessageCarPosition.getStoreCode();
			
			//ɾ����λ������Ϣ
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
			//��������ǩ��
			String querySeqSignHql = " from TplTransTruckSignIn as t where 1=1 and t.seqId = "+seqId+" and substr(t.ladingSpot,-3,3) = '"+storeCode+"' and t.status = '10'";
			List list = messageDao.queryTplQualityDefectConfigByHql(querySeqSignHql);
			if(list.size()>0){
				TplTransTruckSignIn singIn = (TplTransTruckSignIn)list.get(0);
				singIn.setStatus("00");//��Ч
				messageDao.update(singIn, TplTransTruckSignIn.class);
			}
			//��ԭ����״̬��ǩ��ǰ��״̬ ---����״̬
//			if(seqId!=null){
//				TplTransSeq transSeq = (TplTransSeq)messageDao.queryById(new Long(seqId), TplTransSeq.class);
//				if(transSeq!=null){
//					transSeq.setProStatus("03");
//					messageDao.update(transSeq, TplTransSeq.class);
//				}
//			}
			
		}else if(tplMessageCarPosition.getProcType().equals("U")){//�޸�
			System.out.println("����ҵ�����Ϣ������");
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
		
		//ִ����Ϣ����
		if (tplMessageCarPosition.getProcType().equals("I")
				|| tplMessageCarPosition.getProcType().equals("D")
				|| tplMessageCarPosition.getProcType().equals("U")) {
			System.out.println("��Ϣ������Ϣ��װ");
			// ��ѯ������Ϣ
			String queryTplTranSeq = " from TplTransSeq t where t.id = "
					+ Long.valueOf(tplMessageCarPosition.getSeqId());
			List listSeq = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
			if (listSeq != null && listSeq.size() > 0) {
				TplTransSeq tplTransSeq = (TplTransSeq) listSeq.get(0);
				HashMap bspMap = new HashMap();
				String alias = tplTransSeq.getDriver();// ���ͱ���
				String storeCode = tplMessageCarPosition.getStoreCode();// ����id
				bspMap.put("ALIAS", alias);// ����
				bspMap.put("WAREHOUSE_ID", storeCode);// �ֿ�id

				// ���ݿ����źͳ��κŲ�ѯ����������
				List listTplCarPlanItem = messageDao.queryTplCarPlanItem(
						storeCode, String.valueOf(tplTransSeq.getId()));
				System.out.println("listTplCarPlanItem::::::"+listTplCarPlanItem.size());
				if (listTplCarPlanItem != null && listTplCarPlanItem.size() > 0) {
					Object[] items = (Object[]) listTplCarPlanItem.get(0);
					bspMap.put("WAREHOUSE_NAME", items[1].toString());// �ֿ�����
				}else{
					bspMap.put("WAREHOUSE_NAME", "");// �ֿ�����
				}
				CarStoreSeqenceSearchModel searchModel = new CarStoreSeqenceSearchModel();
				searchModel.setCarPlanId(tplMessageCarPosition.getCarPickNo());
				searchModel.setStoreCode(storeCode);
				searchModel.setCarId(tplTransSeq.getCarId());
				List listTplKq = messageDao.queryTplKq(searchModel);
				System.out.println("listTplKq::::::::"+listTplKq.size());
				if (listTplKq != null && listTplKq.size() > 0) {
					Object[] items = (Object[]) listTplKq.get(0);
					bspMap.put("LIST_NUM", "����"+items[0].toString()+"-���"+items[1].toString());
				} else {
					bspMap.put("LIST_NUM", "");
				}
				bspMap.put("SYSTEM_TYPE", "20");// ϵͳ��־
				bspMap.put("CONFIRM_FLAG", "00");// �Ƿ�λ
				bspMap.put("PROC_TYPE", tplMessageCarPosition.getProcType());
				bspMap.put("DRI_U_CODE",alias);
				/*if(tplMessageCarPosition.getProcType().equals("I")){
					String alter = tplMessageCarPosition.getCarNum() + "��"+ storeCode + "���ѷ��䳵λΪ"+ tplMessageCarPosition.getParkingCode() + "!";// ������Ϣ����
					bspMap.put("ALTER", alter);// ��������
					bspMap.put("PARKING_CODE", tplMessageCarPosition.getParkingCode());// ��λ����
				}else if(tplMessageCarPosition.getProcType().equals("D")){
					String alter = tplMessageCarPosition.getCarNum() + "��"+ storeCode + "�����ĳ�λ�ѳ���!";// ������Ϣ����
					bspMap.put("ALTER", alter);
					bspMap.put("PARKING_CODE", "");
				}else if(tplMessageCarPosition.getProcType().equals("U")){
					String alter = tplMessageCarPosition.getCarNum() + "��"+ storeCode + "�����ĳ�λ���޸�Ϊ"+ tplMessageCarPosition.getParkingCode() + "!";// ������Ϣ����
					bspMap.put("ALTER", alter);
					bspMap.put("PARKING_CODE", tplMessageCarPosition.getParkingCode());// ��λ����
				}*/
				if(tplMessageCarPosition.getProcType().equals("I") || tplMessageCarPosition.getProcType().equals("U")){
					bspMap.put("PARKING_CODE", tplMessageCarPosition.getParkingCode());// ��λ����
				}else if(tplMessageCarPosition.getProcType().equals("D")){
					bspMap.put("PARKING_CODE", "");
				}
				System.out.println("ִ����Ϣ����");
				String result = JpushUtil.push(bspMap);
				System.out.println(result);
				
//				JSONObject resData = JSONObject.fromObject(result);
//				System.out.println("resData:"+resData);
//				System.out.println("�������ͷ��ؽ����" + resData);
//				if (resData.containsKey("error")) {
//					System.out.println("��Ա���Ϊ" + alias + "����Ϣ����ʧ�ܣ�");
//					JSONObject error = JSONObject.fromObject(resData
//							.get("error"));
//					System.out.println("������ϢΪ��"
//							+ error.get("message").toString());
//				} else {
//					System.out.println("��Ա���Ϊ" + alias + "����Ϣ���ͳɹ���");
//				}
			}
		}
		System.out.println("����");
	}
	
	//���ᳵ���ʵ��[XLXZ46]==>>���ʱ���³��μƻ�����Ϣ
	public void receiveTplCarLeave(TplMessageCarLeave tplMessageCarLeave) throws Exception {
		System.out.println("���ᳵ���ʵ��");
		String message = "";
		Date date = new Date();
		tplMessageCarLeave.setTplMessageCarLeaveItemSet(BeanConvertUtils.strToBean(tplMessageCarLeave.getDetail(), CLASS_SINCE_TPL_MESSAGE_CAR_LEAVE_ITEM));
		// �����model
		Iterator it = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
		while (it.hasNext()) {
			TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) it.next();
			if ("".equals(tplMessageCarLeaveItem.getPackNo())) {
				it.remove();
			}
		}
		
		//���յ��ı�����
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
		
		//����������ж��ظ�
		if(tplMessageCarLeave.getProcType().equals("I")){
			it = tplMessageCarLeave.getTplMessageCarLeaveItemSet().iterator();
			while (it.hasNext()) {
				TplMessageCarLeaveItem tplMessageCarLeaveItem = (TplMessageCarLeaveItem) it.next();
				if(!"".equals(tplMessageCarLeaveItem.getPackNo())){
					String  queryHql = "select tt from TplCarLeave as t,TplCarLeaveItem as tt where 1=1 and t.id =tt.sinceCarLeaveId and t.storeCode = '"+tplMessageCarLeave.getStoreCode()+"' and t.seqId = '"+tplMessageCarLeave.getSeqId()+"' and tt.packNo='"+tplMessageCarLeaveItem.getPackNo()+"'";
					List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
					if (list != null && list.size()>0){
						message+=tplMessageCarLeaveItem.getPackNo()+"��";
					}
				}
			}
			if(null !=message && !message.trim().equals("")){
				TplMessageLog tplMessageLog= new TplMessageLog();
				tplMessageLog.setErrTitle("���ᳵ���ʵ��");
				tplMessageLog.setErrContent("�����ظ����Ĳ���:"+message.trim()+",���κ�Ϊ:"+tplMessageCarLeave.getSeqId()+" �ֿ��Ϊ: "+tplMessageCarLeave.getStoreCode());
				tplMessageLog.setCreateDate(date);
				messageDao.insert(tplMessageLog, TplMessageLog.class);
				return;
			}
		}
		
		
		TplTransSeq tplTransSeq = new TplTransSeq();//������Ϣ
		List listTplTransPack = new ArrayList();//����������Ϣ
		List listIds = new ArrayList();//���ʵ����������id
		Long [] ids = new Long[tplMessageCarLeave.getTplMessageCarLeaveItemSet().size()];
		//��ѯ������˳�TplTransSeq
		String queryTplTranSeq = " from TplTransSeq t where t.id = "+Long.valueOf(tplMessageCarLeave.getSeqId());
		List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
		if(tplTransSeqList!=null && tplTransSeqList.size()>0){
			tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
			//���ݳ���ʵ���Ĳ��ϺŲ�ѯ��ѯ��������ҵ�ƻ�������Ϣ
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
				//��ȡ���ʵ�� pack������ϢId
				for(int i=0;i<listIds.size();i++){
					ids[i] = Long.valueOf((listIds.get(i).toString()));
				}
			}
		}
		
		if(tplMessageCarLeave.getProcType().equals("I")){//����
			//�������ᳵ���ʵ��ҵ������Ϣ
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
			//�������ᳵ���ʵ��ҵ������Ϣ
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
			//�������ò���ʧЧ
			messageDao.updateTplCarPlan(tplMessageCarLeave.getSeqId(),tplMessageCarLeave.getStoreCode());
		}/*else if(tplMessageCarLeave.getProcType().equals("D")){//ɾ��
			//ɾ�����ᳵ���ʵ��ҵ������Ϣ
			//ҵ���
			String queryIdSql = "select id from TPL_CAR_LEAVE where SEQ_ID = '"
				+tplMessageCarLeave.getSeqId()+"' and STORE_CODE = '"
				+tplMessageCarLeave.getStoreCode()+"' order by ID DESC";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id!=null){
				Long [] ids02 = {id};
				messageDao.delete(ids02, TplCarLeave.class);
				//ɾ�����ᳵ���ʵ��ҵ������Ϣ
				String deleteTplCarPlanItem = "delete from TPL_CAR_LEAVE_ITEM t where t.SINCE_CAR_LEAVE_ID = " + id ;
				messageDao.deleteSql(deleteTplCarPlanItem);
			}
			
			//�޸����ʵ�ʵĳ��ε�״̬
			if(tplTransSeq.getId()!=null){
				tplTransSeq.setStatus("0");
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
			//�޸�����״̬
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
				//�޸�������ҵ�ƻ�����
				TplTransPlan tplTransPlan = new TplTransPlan();
				tplTransPlan.setStatus("0");
				tplTransPlan.setSeqId(null);
				tplTransPlan.setTransPlanId(transPlanId);
				tplTransPlan.setActGrossWeight(actGrossWeight);
				tplTransPlan.setActNetWeight(actNetWeight);
				tplTransPlan.setActCount(actCount);
				messageDao.updateTplTransPlan(tplTransPlan);
				//�޸����˵��ȼƻ���״̬
				TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
				tplTransTruckDisPlan.setStatus("00");
				tplTransTruckDisPlan.setActGrossWeight(actGrossWeight);
				tplTransTruckDisPlan.setActNetWeight(actNetWeight);
				tplTransTruckDisPlan.setActCount(actCount);
				tplTransTruckDisPlan.setTransPlanId(transPlanId);
				messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);
			}
		}*/
		else if(tplMessageCarLeave.getProcType().equals("U")){//�޸�
			//�޸����ᳵ���ʵ��ҵ������Ϣ
			System.out.println("����ҵ�����Ϣ������");
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
				//�޸����ᳵ���ʵ��ҵ������Ϣ
				if(tplMessageCarLeave.getTplMessageCarLeaveItemSet()!=null && tplMessageCarLeave.getTplMessageCarLeaveItemSet().size()>0){
					//ɾ�����ᳵ���ʵ��ҵ������Ϣ
					String deleteTplCarPlanItem = "delete from TPL_CAR_LEAVE_ITEM t where t.SINCE_CAR_LEAVE_ID = " + tplCarLeave.getId() ;
					System.out.println("deleteTplCarPlanItem:::"+deleteTplCarPlanItem);
					messageDao.deleteSql(deleteTplCarPlanItem);
					//������ҵ��
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
			BigDecimal actGrossWeight = new BigDecimal("0");//ʵ��װ��ë��
			BigDecimal actNetWeight = new BigDecimal("0");//ʵ��װ������
			Long actCount = new Long(0);//ʵ��װ������
			if (tplTransSeq.getId() != null) {

				Long seqId = tplTransSeq.getId();
				//���õ�planId
				List list1 = new ArrayList();
				list1 = messageDao.queryPackSumBySeqId(seqId);
				// �Ƴ����ա��޸����ؼƻ�ʱ��װ������
				messageDao.updateTransPackForLoadBack(seqId);

				// ������ѡ��Ĳ��ϺŲ�ѯ�����˵��ȼƻ���Ϣ������������Ϣ�ĵ��ȼƻ�id����������״̬
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

				// ����������ѯ���ƻ���,�ٸ��ݼƻ���ȥ���¼ƻ������˵��ȼƻ���Ϣ
//				List listTransPlanId = messageDao.queryTransPlanId(ids);
				List listTransPlanId = messageDao.queryPackSumBySeqId(seqId);
				listTransPlanId.addAll(list1);
				System.out.println("��ѯ�����ļƻ�������" + listTransPlanId.size());
				if (listTransPlanId != null && listTransPlanId.size() > 0) {
					for (int i = 0; i < listTransPlanId.size(); i++) {
						TplTransPlan tplTransPlan = new TplTransPlan();
						String transPlanId = (String) listTransPlanId.get(i);
						System.out.println("�ƻ��ţ���������������" + transPlanId);
						List list03 = messageDao.tplTransPlanCount(transPlanId);
						if (null != list03 && list03.size() > 0) {
							Object[] obj = (Object[]) list03.get(0);
							actGrossWeight = null == obj[0] ? new BigDecimal("0") : new BigDecimal(obj[0].toString());
							actNetWeight = null == obj[1] ? new BigDecimal("0") : new BigDecimal(obj[1].toString());
							actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
						}
						// ���¼ƻ�
						tplTransPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
						tplTransPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
						tplTransPlan.setActCount(actCount);
						tplTransPlan.setStatus("1");
						tplTransPlan.setSeqId(tplTransSeq.getId());
						tplTransPlan.setTransPlanId(transPlanId);
						messageDao.updateTplTransPlan(tplTransPlan);

						// �������˵��ȼƻ�
						TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
						tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
						tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
						tplTransTruckDisPlan.setActCount(actCount);
						tplTransTruckDisPlan.setStatus("10");
						tplTransTruckDisPlan.setTransPlanId(transPlanId);
						// ������ָ���ļƻ���ȥ�������е����˵��ȼƻ�ִ����
						messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);
					}
				}
				// ���ݳ���ʵ��������Ϣ��ѯ�����ʵ��ë�ء����ء�������Ȼ����µ������䳵���У����޸Ĵ˳���Ϊ������
				List list = messageDao.tplTransSeqLeaveCount(tplTransSeq
						.getId().toString());
				Object[] obj = (Object[]) list.get(0);
				actGrossWeight = null == obj[0] ? new BigDecimal("0") : new BigDecimal(df.format(obj[0]));
				actNetWeight = null == obj[1] ? new BigDecimal("0") : new BigDecimal(df.format(obj[1]));
				actCount = null == obj[2] ? new Long(0) : new Long(obj[2].toString());
				tplTransSeq.setStatus("1");
				tplTransSeq.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
				tplTransSeq.setActNetWeight(Double.valueOf(actNetWeight.toString()));
				tplTransSeq.setActCount(actCount);// ʵ�ʼ���
				tplTransSeq.setPlanGrossWeight(Double.valueOf(actGrossWeight.toString()));
				tplTransSeq.setPlanNetWeight(Double.valueOf(actNetWeight.toString()));
				tplTransSeq.setPlanCount(actCount);// �ƻ�����
				tplTransSeq.setProStatus("10");// ����״̬
				tplTransSeq.setRealStartDate(date);//����ʱ��
				messageDao.update(tplTransSeq, TplTransSeq.class);
			}
		}
		System.out.println("����");
	}
	
	//���ᳵ��������Ϣ[XLXZ47]==>>����ʱ�����Խ��в�����ѡ
	public void receiveTplCarInout(TplMessageCarInout tplMessageCarInout) throws Exception {
		Date date = new Date();
		Long carPlanId = null;
		System.out.println("���ᳵ������");
		//���յ��ı�
		System.out.println("�������ı���Ϣ����");
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
			tplMessageLog.setErrTitle("���������Ľ���ʧ��");
			tplMessageLog.setErrContent("���κ�:"+tplTransSeq.getId()+"�ѽ᰸");
			tplMessageLog.setOperMemo("XLXZ47");
			tplMessageLog.setCreateDate(new Date());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
			return;
		}
		if(tplMessageCarInout.getProcType().equals("I")){//����
			System.out.println("����ҵ�����Ϣ������");
			TplCarInout  tplCarInout = new TplCarInout();
			tplCarInout.setCarNum(tplMessageCarInout.getCarNum());
			tplCarInout.setSeqId(tplMessageCarInout.getSeqId());
			tplCarInout.setInoutFlag(tplMessageCarInout.getInoutFlag());
			tplCarInout.setDoorName(tplMessageCarInout.getDoorName());
			tplCarInout.setExecuteTime(tplMessageCarInout.getExecuteTime());
			tplCarInout.setCreateDate(date);
			messageDao.insert(tplCarInout, TplCarInout.class);
			if(tplMessageCarInout.getInoutFlag().equals("0")){//����
				//�޸Ĳ�����ѡ��״̬
				if(tplCarPlan.getId()!=null){
					tplCarPlan.settStatus("1");
					messageDao.update(tplCarPlan, TplCarPlan.class);
				}
				//���³��α�Pro_struts
				if(tplTransSeq.getId()!=null){
					tplTransSeq.setProStatus("05");
					messageDao.update(tplTransSeq, TplTransSeq.class);
				}
			}else if(tplMessageCarInout.getInoutFlag().equals("1")){
				//���³��α�Pro_struts
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
		}else if(tplMessageCarInout.getProcType().equals("D")){//ɾ��
			System.out.println("ɾ��ҵ�����Ϣ������");
			String queryIdSql = "select id from TPL_CAR_INOUT where CAR_NUM = '"
				+tplMessageCarInout.getCarNum()+"' order by ID DESC";
			System.out.println("queryIdSql:::"+queryIdSql);
			Long id = messageDao.queryIdBySql(queryIdSql);
			if(id != null){
				Long [] ids = {id};
				messageDao.delete(ids, TplCarInout.class);
			}
			if(tplMessageCarInout.getInoutFlag().equals("0")){//����ɾ��
				//�޸Ĳ�����ѡ��״̬
				if(tplCarPlan.getId()!=null){
					tplCarPlan.settStatus("0");
					messageDao.update(tplCarPlan, TplCarPlan.class);
				}
				//���³��α�Pro_struts
				if(tplTransSeq.getId()!=null){
					tplTransSeq.setProStatus("");
					messageDao.update(tplTransSeq, TplTransSeq.class);
				}
			}
		}else if(tplMessageCarInout.getProcType().equals("U")){//�޸�
			System.out.println("����ҵ�����Ϣ������");
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
		System.out.println("����");
	}
	
	//�����Ŷ���Ϣ[XLXZ49]
	public void receiveTplKqList(TplMessageKqList tplMessageKqList) throws Exception {
		Date date = new Date();
		System.out.println("�����Ŷ�");
		if(tplMessageKqList.getDetail().startsWith("000")){
			//ҵ���ϻᷢѭ��Ϊ�յĵ���
			//���������ĵ���ȡ��detail��ʱ����ǰ��Ŀո���������½�������
			tplMessageKqList.setDetail("          "+tplMessageKqList.getDetail());
		}
		tplMessageKqList.setTplMessageKqListItemSet(BeanConvertUtils.strToBean(tplMessageKqList.getDetail(), CLASS_SINCE_TPL_MESSAGE_KQ_LIST_ITEM));
		// �����model
		Iterator it = tplMessageKqList.getTplMessageKqListItemSet().iterator();
		if(it!=null){
			while (it.hasNext()) {
				TplMessageKqListItem tplMessageKqListItem = (TplMessageKqListItem) it.next();
				if ("".equals(tplMessageKqListItem.getCarNum())) {
					it.remove();
				}
			}
		}
		//���յ��ı�����
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
		if(list==null || list.size()==0){//����Ϣ�����ڣ�����ҵ�������Ϣ
			tplKqList.setStoreCode(tplMessageKqList.getStoreCode());
			tplKqList.setLaneNo(tplMessageKqList.getLaneNo());
			tplKqList.setCreateDate(date);
			messageDao.insert(tplKqList, TplKqList.class);
		}else{//����Ϣ�Ѿ����ڣ�ɾ��ԭ������Ϣ
			tplKqList = (TplKqList) list.get(0);
			String deleteTplKqListItem = "delete from Tpl_Kq_List_item t where KQ_LIST_ID = " +tplKqList.getId();
			System.out.println("deleteTplKqListItem:::"+deleteTplKqListItem);
			messageDao.deleteSql(deleteTplKqListItem);
		}
		
		//����ҵ�������Ϣ
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
		System.out.println("����");
	}

	/**
	 *Ҫ���ƻ�������תˮ�ˣ�[XLXZ40]
	 */
	public void receiveTplMDeliveryPlan(TplMDeliveryPlan tplMDeliveryPlan)
			throws Exception {
		//�ж��ظ�
		if(tplMDeliveryPlan.getProcType().equals("I")){
			System.out.println("Ҫ���ƻ�������תˮ�ˣ�ҵ�����Ϣ�Ƿ��Ѿ�����,���ڲ�ִ������");
			String  queryHql = " from TplDeliveryPlan as t where 1=1 and t.billOfLadingNo = '"+tplMDeliveryPlan.getBillOfLadingNo()+"' and t.custMatNo = '"+tplMDeliveryPlan.getCustMatNo()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				return;
			}
		}
		//���յ��ı�����
		Date date = new Date();
		System.out.println("Ҫ���ƻ�������תˮ�ˣ����ı�����");
		tplMDeliveryPlan.setCreateDate(date);
		messageDao.insert(tplMDeliveryPlan, TplMDeliveryPlan.class);
		//����ҵ���
		if(tplMDeliveryPlan.getProcType().equals("I")){
			TplDeliveryPlan tplDeliveryPlan = new TplDeliveryPlan();
			tplDeliveryPlan.setCreateDate(date);
			tplDeliveryPlan.setBillOfLadingNo(tplMDeliveryPlan.getBillOfLadingNo());
			tplDeliveryPlan.setCustMatNo(tplMDeliveryPlan.getCustMatNo());
			System.out.println("Ҫ���ƻ�������תˮ�ˣ�ҵ���ı�����");
			messageDao.insert(tplDeliveryPlan, TplDeliveryPlan.class);
		}else if(tplMDeliveryPlan.getProcType().equals("D")){
			//ҵ���
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
	 * ������У�װ�ؼƻ���[XLXZ41]
	 */
	public void receiveTplMOutList(TplMOutList tplMOutList) throws Exception {
		Date date = new Date();
		System.out.println("������У�װ�ؼƻ���,����ʱ�䣺");
		tplMOutList.setTplMOutListItemSet(BeanConvertUtils.strToBean(tplMOutList.getDetail(), CLASS_SINCE_TPL_M_OUT_LIST_ITEM));
		// �����model
		Iterator it = tplMOutList.getTplMOutListItemSet().iterator();
		while (it.hasNext()) {
			TplMOutListItem tplMOutListItem = (TplMOutListItem) it.next();
			if ("".equals(tplMOutListItem.getCustMatNo())) {
				it.remove();
			}
		}
		System.out.println("�������:"+tplMOutList.getProcType());
		//����������ж��ظ�
		if(tplMOutList.getProcType().trim().equals("I")){
		/*	Iterator itDouble = tplMOutList.getTplMOutListItemSet().iterator();
			//�ж϶�������źͲ��Ϻ���Ϣ�Ƿ���ڣ�������ھͽ�������
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
		
			//����������ж���
			if(tplMOutList.getProcType().equals("I")){
				String  queryHql = " from TplOutList as t where 1=1 and t.queueTaskNo = '"+tplMOutList.getQueueTaskNo()+"'";
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size()>0){
						return;
				}
			}
		}
		
		//���յ��ı�����
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
		
		//����ҵ���
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
							//���ھ͸��£���������
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
		System.out.println("����");
	}

	/**
	 *�г�������Ϣ[XLXZ42]
	 */
	public void receiveTplMOverhaulInfo(TplMOverhaulInfo tplMOverhaulInfo)
			throws Exception {
		System.out.println("�г�����");
		Date date  = new Date();
		//����������ж��ظ�
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
		//���յ��ı�
		tplMOverhaulInfo.setCreateDate(date);
		messageDao.insert(tplMOverhaulInfo, TplMOverhaulInfo.class);
		if(tplMOverhaulInfo.getProcType().equals("I")){//����
			System.out.println("����ҵ�����Ϣ������");
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
		}else if(tplMOverhaulInfo.getProcType().equals("D")){//ɾ��
			System.out.println("ɾ��ҵ�����Ϣ������");
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
		}else if(tplMOverhaulInfo.getProcType().equals("U")){//�޸�
			System.out.println("����ҵ�����Ϣ������");
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
		System.out.println("����");
	}

	/**
	 * ���ᳵ������Ϣ[XZXL10]
	 */
	public void sendTplMSinceModels(TplMSinceModels tplMSinceModels)
			throws Exception {
		//���ǳ�ͷ������������ѯ�ҳ����Ƿ��Ѿ����ͣ����Ѿ����Ͳ�ִ��
		System.out.println("������־��"+tplMSinceModels.getProcType().trim());
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
		logger.info("---���� ���ᳵ������Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SINCE_MODELS, null, tplMSinceModels);
		System.out.println("����");
	}

	/**
	 * ���ᳵǩ��[XZXL14]
	 */
	public void sendTplMCarSign(TplMCarSign tplMCarSign) throws Exception {
		tplMCarSign.setProcType("I");
		tplMCarSign.setCreateDate(new Date());
		messageDao.insert(tplMCarSign, TplMCarSign.class);
		logger.info("---���� ���ᳵǩ����Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_CAR_SIGN, null, tplMCarSign);
		System.out.println("����");
	}

	/**
	 *  �鵥���[XZXL15]
	 */
	public void sendTplMCheckResult(TplMCheckResult tplMCheckResult)
			throws Exception {
		tplMCheckResult.setCreateDate(new Date());
		tplMCheckResult.setProcType("I");
		messageDao.insert(tplMCheckResult, TplMCheckResult.class);
		logger.info("---���� ���ᳵǩ����Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_CHECK_RESULT, null, tplMCheckResult);
		System.out.println("����");
	}

	/**
	 * ���ᳵ��λ[XZXL16]
	 */
	public void sendTplMCarInPlace(TplMCarInPlace TplMCarInPlace)
			throws Exception {
		TplMCarInPlace.setProcType("I");
		TplMCarInPlace.setCreateDate(new Date());
		messageDao.insert(TplMCarInPlace, TplMCarInPlace.class);
		logger.info("---���� ���ᳵǩ����Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_CAR_IN_PLACE, null, TplMCarInPlace);
		System.out.println("����");
	}

	/**
	 * ���ᳵ�����û�[XZXL17]
	 */
	public void sendTplMCarArriveUser(TplMCarArriveUser tplMCarArriveUser)
			throws Exception {
		tplMCarArriveUser.setProcType("I");
		tplMCarArriveUser.setCreateDate(new Date());
		messageDao.insert(tplMCarArriveUser, TplMCarArriveUser.class);
		logger.info("---���� ���ᳵ��������Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSGAE_SEND_TPL_M_CAR_ARRIVE_USER, null, tplMCarArriveUser);
		System.out.println("����");
	}

	/***
	 *����˾����Ϣ[XZXL18]
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
		logger.info("---���� ����˾����Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SINCE_DRIVER_INFO, null, tplMSinceDriverInfo);
		System.out.println("����");
	}

	/**
	 * ����ƻ�תˮ��
	 */
	public void senTPLMSinceMaterialWATER(
			TplMSinceMaterialWater tplMSinceMaterialWater) throws Exception {
		System.out.println("����ƻ�תˮ����Ϣ����");
		messageDao.insert(tplMSinceMaterialWater, TplMSinceMaterialWater.class);
		logger.info("---��������ƻ�תˮ����Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SINCE_MATERIAL_WATER, null, tplMSinceMaterialWater);
		System.out.println("����");
	}
	
	// ******************************��ɽ���ظ���Ʒ����Ч������start********************************************************
	/**
	 * �����������ͬ��Ϣ[XLXZ50]
	 */
	public void receiveTplMSinceContract(TplMSinceContract tplMSinceContract)
			throws Exception {
		Date date = new Date();
		System.out.println("���շ����������ͬ��Ϣ��ʱ�䣺" + date);
		try {
			String queryTplSinceContractHql = " from TplSinceContract t where t.providerId='"
					+ tplMSinceContract.getProviderId()
					+ "' and t.orderNum = '"
					+ tplMSinceContract.getOrderNum()
					+ "'";
			// ��������
			List list = messageDao.queryTplQualityDefectConfigByHql(queryTplSinceContractHql);
			if (null != list && list.size() > 0) {
				return;
			}
			// ����������Ϣ
			tplMSinceContract.setCreateDate(date);
			messageDao.insert(tplMSinceContract, TplMSinceContract.class);

			// ����ҵ����Ϣ
			System.out.println("���������������ͬ��Ϣ");
			TplSinceContract tplSinceContract = new TplSinceContract();
			tplSinceContract.setOrderNum(tplMSinceContract.getOrderNum());
			tplSinceContract.setProviderId(tplMSinceContract.getProviderId());
			tplSinceContract.setProviderName(tplMSinceContract.getProviderName());
			tplSinceContract.setDestSpotCode(tplMSinceContract.getDestSpotCode());
			tplSinceContract.setDestSpotName(tplMSinceContract.getDestSpotName());
			tplSinceContract.setCreateDate(date);
			tplSinceContract.setSendStatus("00");// δ����00,10�ѷ���
			tplSinceContract.setMaintainStatus("00");// 00δά����10��ά��
			messageDao.insert(tplSinceContract, TplSinceContract.class);
		} catch (Exception e) {
			TplMessageLog tplMessageLog = new TplMessageLog();
			tplMessageLog.setCreateDate(date);
			if (e.getMessage().length() > 2000) {
				tplMessageLog.setErrContent(e.getMessage().substring(0, 2000));
			} else {
				tplMessageLog.setErrContent(e.getMessage());
			}
			tplMessageLog.setErrTitle("���շ����������ͬ��Ϣ�����쳣,������Ϊ��"
					+ tplMSinceContract.getProviderId() + "��ͬ��Ϊ��"
					+ tplMSinceContract.getOrderNum());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}

	// װ�ؼƻ�������Ϣ[XLXZ57]
	public void receiveTplMTransLoadPlan(TplMTransLoadPlan tplMTransLoadPlan)
			throws Exception {
		Date date = new Date();
		System.out.println("����װ�ؼƻ�������Ϣ��ʱ��Ϊ��" + date);
		System.out.println("++++++++++++++++++++++++"+tplMTransLoadPlan.getDetail()+"----------------------");
		if(tplMTransLoadPlan.getDetail().startsWith("000")){
			//ҵ���ϻᷢѭ��Ϊ�յĵ���
			//���������ĵ���ȡ��detail��ʱ����ǰ��Ŀո���������½�������  ��30���ո�
			tplMTransLoadPlan.setDetail("                              "+tplMTransLoadPlan.getDetail());
		}
		tplMTransLoadPlan.setTplMTransLoadPlanItemSet(BeanConvertUtils
				.strToBean(tplMTransLoadPlan.getDetail(),CLASS_TPL_M_TRANS_LOAD_PLAN_ITEM));
		try {
			System.out.println("װ�ؼƻ����кţ�"+ tplMTransLoadPlan.getLoadingPlanNo());
			String hqlQuery = " from TplTransLoadPlan t where 1=1 and t.loadingPlanNo = '"
					           + tplMTransLoadPlan.getLoadingPlanNo() + "'";
			System.out.println("hqlQuery:::" + hqlQuery);
			
			// ����յ�model
			Iterator it = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
			while (it.hasNext()) {
				TplMTransLoadPlanItem tplMTransLoadPlanItem = (TplMTransLoadPlanItem) it.next();
				if (tplMTransLoadPlanItem.getBillId() == null
						|| tplMTransLoadPlanItem.getBillId().trim().equals("")) {
					it.remove();
				}
			}

			//��ѯ������Ϣ�Ƿ���ڣ����ھ�ɾ�����²���
			String hqlQueryTplMTransLoadPlan = " from TplMTransLoadPlan t where 1=1 and t.loadingPlanNo = '"
		           + tplMTransLoadPlan.getLoadingPlanNo() + "' and t.operateFlag = '"+tplMTransLoadPlan.getOperateFlag()+"'";
			System.out.println("hqlQueryTplMTransLoadPlan:::" + hqlQueryTplMTransLoadPlan);
			List listTplMTransLoadPlan = messageDao.queryTplQualityDefectConfigByHql(hqlQueryTplMTransLoadPlan);
			if(null != listTplMTransLoadPlan && listTplMTransLoadPlan.size()>0){
				for(int i=0;i<listTplMTransLoadPlan.size();i++){
					TplMTransLoadPlan tplMTransLoad=(TplMTransLoadPlan) listTplMTransLoadPlan.get(i);
					//ɾ��װ�ؼƻ�����Ϣsql
					String deleteTplMTransLoadPlanItem = "delete from TPL_M_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
						+ tplMTransLoad.getId();
					Long[] ids = { tplMTransLoad.getId() };
					messageDao.delete(ids, TplMTransLoadPlan.class);

					System.out.println("ɾ����������Ϣ��deleteTplMTransLoadPlanItem:::"+ deleteTplMTransLoadPlanItem);
					messageDao.deleteSql(deleteTplMTransLoadPlanItem);
				}
			}
			
			tplMTransLoadPlan.setCreateDate(date);
			// ���ı�����
			messageDao.insert(tplMTransLoadPlan, TplMTransLoadPlan.class);
			// ������������Ϣ
			if (tplMTransLoadPlan.getTplMTransLoadPlanItemSet() != null
					&& tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size() > 0) {
				Iterator iterator = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
				while (iterator.hasNext()) {
					TplMTransLoadPlanItem tplMTransLoadPlanItem = (TplMTransLoadPlanItem) iterator.next();
					tplMTransLoadPlanItem.setTransLoadPlanId(tplMTransLoadPlan.getId());
					messageDao.insert(tplMTransLoadPlanItem,TplMTransLoadPlanItem.class);
				}
			}
			
			//�ж�����ȥ�أ�����װ�ؼƻ��Ŵ��ڣ�ִ�����
			if (tplMTransLoadPlan.getOperateFlag().trim().equals("I")) {
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (list != null && list.size() > 0) {
					return;
				}
			}
			// ҵ������
			if (null !=tplMTransLoadPlan.getOperateFlag() && tplMTransLoadPlan.getOperateFlag().trim().equals("I")) {
				System.out.println("����ҵ����Ϣ");
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
				tplTransLoadPlan.setStatus("0");// 0δִ�У�1��ִ�У�2�ѽ᰸
				tplTransLoadPlan.setProStatus("00");// 00δ�ɵ���10���ɵ�
				tplTransLoadPlan.setValidateFlag("00");//δ�鵥
				tplTransLoadPlan.setSignInStatus("00");//δǩ��
				tplTransLoadPlan.setFinalUseId(tplMTransLoadPlan.getFinalUseId());
				tplTransLoadPlan.setFinalUseName(tplMTransLoadPlan.getFinalUseName());
				messageDao.insert(tplTransLoadPlan, TplTransLoadPlan.class);
				if (tplMTransLoadPlan.getTplMTransLoadPlanItemSet() != null
						&& tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size() > 0) {
					System.out.println("����ҵ������Ϣ:"+tplMTransLoadPlan.getTplMTransLoadPlanItemSet().size());
					Iterator iterator = tplMTransLoadPlan.getTplMTransLoadPlanItemSet().iterator();
					while (iterator.hasNext()) {
						System.out.println("����Ϣ����");
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
				System.out.println("�޸�ҵ����Ϣ");
				List list = messageDao
						.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplTransLoadPlan tplTransLoadPlan = (TplTransLoadPlan) list
							.get(0);
					// �Ѿ��ɵ���װ�ؼƻ������޸�
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
						System.out.println("�޸�����Ϣ");
						// ɾ������Ϣ���ٴ�����
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
				System.out.println("ɾ��ҵ����Ϣ");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplTransLoadPlan tplTransLoadPlan = (TplTransLoadPlan) list.get(0);
					//װ�ؼƻ���
					String loadingPlanNo = tplTransLoadPlan.getLoadingPlanNo();
					Long getSeqId = tplTransLoadPlan.getSeqId();//���κ�
					if(null == getSeqId){
						//ɾ��װ�ؼƻ�����Ϣsql
						String deleteTplTransLoadPlanItem = "delete from TPL_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
							+ tplTransLoadPlan.getId();
						
						//ɾ�����ݼƻ��ŵ��ȼƻ�sql
						String deleteTplDisplan = "delete from tpl_trans_truck_dis_plan t where t.TRANS_PLAN_ID = '"+tplTransLoadPlan.getLoadingPlanNo()+"'";
						
						// ɾ������Ϣ
						Long[] ids = { tplTransLoadPlan.getId() };
						messageDao.delete(ids, TplTransLoadPlan.class);

						System.out.println("ɾ������Ϣ��deleteTplTransLoadPlanItem:::"+ deleteTplTransLoadPlanItem);
						messageDao.deleteSql(deleteTplTransLoadPlanItem);

						// ���ѷ����˵��ȼƻ����͸��ݼƻ���ɾ�����ȼƻ�
						System.out.println("ɾ�����ݼƻ��ŵ��ȼƻ���deleteTplDisplan:::"+ deleteTplDisplan);
						messageDao.deleteSql(deleteTplDisplan);
					}else if (null != getSeqId) {//���κŲ�Ϊ��˵���Ѿ��䳵
						//��ѯ��������Ϣ
						TplTransSeq tplTransSeq = (TplTransSeq) messageDao.queryById(tplTransLoadPlan.getSeqId(),TplTransSeq.class);
						
						//��ѯ��װ�ؼƻ��Ƿ��Ѿ��������ʵ�����Ѿ��������ʵ����������
						String hqlQueryTplActLoadPlan = " from TplActLoadPlan t where t.loadingPlanNo = '"
								+ loadingPlanNo
								+ "' and t.seqId='" + tplTransSeq.getId() + "'";
						System.out.println("װ�ؼƻ���ѯ����������������������"+hqlQueryTplActLoadPlan);
						List listTplActLoadPlan = messageDao.queryTplQualityDefectConfigByHql(hqlQueryTplActLoadPlan);
						if (null == listTplActLoadPlan || listTplActLoadPlan.size() <=0) {
							//ɾ��װ�ؼƻ�����Ϣsql
							String deleteTplTransLoadPlanItem = "delete from TPL_TRANS_LOAD_PLAN_ITEM t where t.TRANS_LOAD_PLAN_ID = "
								+ tplTransLoadPlan.getId();
							
							//ɾ�����ݼƻ��ŵ��ȼƻ�sql
							String deleteTplDisplan = "delete from tpl_trans_truck_dis_plan t where t.TRANS_PLAN_ID = '"+tplTransLoadPlan.getLoadingPlanNo()+"'";
							
							// ɾ������Ϣ
							Long[] ids = { tplTransLoadPlan.getId() };
							messageDao.delete(ids, TplTransLoadPlan.class);

							System.out.println("ɾ������Ϣ��deleteTplTransLoadPlanItem:::"+ deleteTplTransLoadPlanItem);
							messageDao.deleteSql(deleteTplTransLoadPlanItem);

							// ���ѷ����˵��ȼƻ����͸��ݼƻ���ɾ�����ȼƻ�
							System.out.println("ɾ�����ݼƻ��ŵ��ȼƻ���deleteTplDisplan:::"+ deleteTplDisplan);
							messageDao.deleteSql(deleteTplDisplan);
							
							//���³�λ��λ������ϢʧЧ
							String updateTplProviderCarAllot = "UPDATE TPL_PROVIDER_CAR_ALLOT t set t.FLAG = '20' where t.LOADING_PLAN_NO= '"
									+ loadingPlanNo + "'";
							System.out.println("���³�λ��λ������ϢʧЧ��updateTplProviderCarAllot:::"+ updateTplProviderCarAllot);
							messageDao.updateBySql(updateTplProviderCarAllot);

							//�����Ŷ���ϢΪʧЧ
							String updateTplProviderCarList = "UPDATE TPL_PROVIDER_CAR_LIST_ITEM t set t.STATUS ='0' where t.LOADING_PLAN_NO ='"+ loadingPlanNo + "')";
							System.out.println("�����Ŷ���ϢΪʧЧ,updateTplProviderCarList:::::"+ updateTplProviderCarList);
							messageDao.updateBySql(updateTplProviderCarList);

							//���·�����װ�ؼƻ��䳵ҵ����ϢΪʧЧ
							String updateTplPlanAllocationCar = "UPDATE TPL_PLAN_ALLOCATION_CAR t SET T.REVOKE_STATUS = '2' where t.LOADING_PLAN_NO = '"+ loadingPlanNo + "'";
							System.out.println("���·�����װ�ؼƻ��䳵ҵ����ϢΪʧЧ��updateTplPlanAllocationCar:::"+ updateTplPlanAllocationCar);
							messageDao.updateBySql(updateTplPlanAllocationCar);
							
							//����ǩ��sql������װ�ؼƻ��Ÿ���ΪʧЧ
							String updateTplTransTruckSignIn = "UPDATE TPL_TRANS_TRUCK_SIGN_IN t set t.STATUS ='00' where t.LOADING_PLAN_NO = '"+ loadingPlanNo + "'";
							System.out.println("����ǩ����Ϣ��deleteTplTransTruckSignIn:::"+ updateTplTransTruckSignIn);
							messageDao.updateBySql(updateTplTransTruckSignIn);

							if (null != tplTransSeq) {
								Long seqId = tplTransSeq.getId();
								//�жϴ˳����Ƿ񻹴���������װ�ؼƻ�
								String hqlQueryTplTransLoadPlanSeq = " from TplTransLoadPlan t where 1=1 and t.seqId = "+ seqId;
								List listTplTransLoadPlanSeq = messageDao.queryTplQualityDefectConfigByHql(hqlQueryTplTransLoadPlanSeq);
								if (null == listTplTransLoadPlanSeq || listTplTransLoadPlanSeq.size() <= 0) {
									System.out.println("���Σ�" + seqId+ "�Ѳ�����װ�ؼƻ������³���Ϊδ���ó���");
									tplTransSeq.setProStatus("01");//����״̬Ϊ��״̬
									messageDao.update(tplTransSeq, TplTransSeq.class);
									
									/*//����˾��U�����ѯ��˾����Ϣ
									TplRDriver tplRDriver = messageDao.queryTplRDriver(tplTransSeq.getDriver());

									//��ʼ������ֵ
									CarsInfoSearchModel carsInfo = new CarsInfoSearchModel();
									carsInfo.setDriUCode(tplTransSeq.getDriver());
									carsInfo.setBusNumber(tplTransSeq.getCarId());
									carsInfo.setSeqId(seqId);
									carsInfo.setProviderId(tplTransSeq.getProviderId());
									carsInfo.setUsername(tplRDriver.getUsername());
									String updateFontStatus = "0";
									messageDao.updateFontStatusBySearchModel(carsInfo, updateFontStatus);

									//ɾ��������Ϣ
									Long[] id = { seqId };
									messageDao.delete(id, TplTransSeq.class);*/
									
									TplMessageLog tplMessageLog = new TplMessageLog();
									tplMessageLog.setCreateDate(date);
									tplMessageLog.setErrContent("�˳����Ѳ��������õ�װ�ؼƻ��������Ѿ�ˢ��Ϊ�ҳ���״̬");
									tplMessageLog.setErrTitle("����װ�ؼƻ�������Ϣ,װ�ؼƻ���Ϊ��"+ tplTransLoadPlan );
									messageDao.insert(tplMessageLog, TplMessageLog.class);
								}
							}
						}else{
							TplMessageLog tplMessageLog = new TplMessageLog();
							tplMessageLog.setCreateDate(date);
							tplMessageLog.setErrContent("װ�ؼƻ���Ϊ��"+tplTransLoadPlan+"����ʧ�ܣ���Ϊ���ڴ�װ�ؼƻ��ѽ������ʵ����");
							tplMessageLog.setErrTitle("����װ�ؼƻ�������Ϣ,װ�ؼƻ���Ϊ��"+ tplTransLoadPlan );
							messageDao.insert(tplMessageLog, TplMessageLog.class);
						}
					}
				}
			}else if (null !=tplMTransLoadPlan.getOperateFlag() && tplMTransLoadPlan.getOperateFlag().trim().equals("E")) {//ǿ�ƽ᰸
				System.out.println("ǿ�ƽ᰸װ�ؼƻ�");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					//����װ�ؼƻ�Ϊ�᰸
					TplTransLoadPlan tplTransLoadPlan = (TplTransLoadPlan) list.get(0);
					String loadingPlanNo = tplTransLoadPlan.getLoadingPlanNo();
					tplTransLoadPlan.setStatus("2");//�ѽ᰸
					tplTransLoadPlan.setSignInStatus("10");//��ǩ��
					tplTransLoadPlan.setValidateFlag("10");//���鵥
					messageDao.update(tplTransLoadPlan, TplTransLoadPlan.class);
					
					//����Ѿ��ɵ����µ��ȼƻ��ѽ᰸
					String updateTplDisplan = "UPDATE tpl_trans_truck_dis_plan t set t.STATUS='20',t.VALIDATE_FLAG='10' where t.TRANS_PLAN_ID = '"+loadingPlanNo+"'";
					System.out.println("���µ��ȼƻ�Ϊִ����ϣ�����������"+updateTplDisplan);
					messageDao.updateBySql(updateTplDisplan);
					
					//���³�λ��λ������Ϣ�ѵ�λ
					String updateTplProviderCarAllot = "UPDATE TPL_PROVIDER_CAR_ALLOT t set t.FLAG = '10' where t.LOADING_PLAN_NO= '"
							+ loadingPlanNo + "'";
					System.out.println("���³�λ��λ������Ϣ�Ѿ���λ��updateTplProviderCarAllot:::"+ updateTplProviderCarAllot);
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
			tplMessageLog.setErrTitle("����װ�ؼƻ�������Ϣ,װ�ؼƻ���Ϊ��"
					+ tplMTransLoadPlan.getLoadingPlanNo() + "������˳���Ϊ��"
					+ tplMTransLoadPlan.getPlanTaskSeqNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}

	// �������̳����Ŷ���Ϣ[XLXZ52]
	public void receiveTplMProviderCarList(
			TplMProviderCarList tplMProviderCarList) throws Exception {
		Date date = new Date();
		System.out.println("�����������̳����Ŷ���Ϣ��ʱ�䣺������" + date);
		try {
			tplMProviderCarList.setTplMProviderCarListItemSet(BeanConvertUtils
					.strToBean(tplMProviderCarList.getDetail(),CLASS_TPL_M_PROVIDER_CAR_LIST_ITEM));
			// ����null��model
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
			// �����µĵ���
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
			
			// ��ѯ�����Ŷӿ����Ƿ����
			String queryHql = " from TplProviderCarList t where and t.stockNo='"+ tplMProviderCarList.getStockNo() + "'";
			System.out.println("queryHql::::" + queryHql);
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			TplProviderCarList tplProviderCarList = new TplProviderCarList();
			
			if (null == list || list.size() == 0) {//����Ϣ�����ڣ�����ҵ������Ϣ
				tplProviderCarList.setStockNo(tplMProviderCarList.getStockNo());
				tplProviderCarList.setCreateDate(date);
				messageDao.insert(tplProviderCarList, TplProviderCarList.class);
			} else {//����Ϣ�Ѿ����ڣ�ɾ��ԭ������Ϣ 
				tplProviderCarList = (TplProviderCarList) list.get(0);
				String deleteTplProviderCarList = "delete from TPL_PROVIDER_CAR_LIST_ITEM t where t.CAR_LIST_ID = "
						+ tplProviderCarList.getId();
				System.out.println("deleteTplProviderCarList:::"+ deleteTplProviderCarList);
				messageDao.deleteSql(deleteTplProviderCarList);
			}
			//������ҵ����Ϣ
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
			tplMessageLog.setErrTitle("�����������̳����Ŷ���Ϣ�����쳣,����Ϊ��"+ tplMProviderCarList.getStockNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}

	// �������̳�����λ������Ϣ [XLXZ53]
	public void receiveTplMProviderCarAllot(
			TplMProviderCarAllot tplMProviderCarAllot) throws Exception {
		Date date = new Date();
		System.out.println("�����������̳�����λ������Ϣ��ʱ�䣺������" + date);
		try {
			// ����������Ϣ
			System.out.println("����������Ϣ");
			tplMProviderCarAllot.setCreateDate(date);
			messageDao.insert(tplMProviderCarAllot, TplMProviderCarAllot.class);

			//�ж�װ�ؼƻ������κ��Ƿ��Ѿ����ڳ�λ���䣬�оͲ�ִ������
			List listTplProviderCarAllot = messageDao.queryTplProviderCarAllot(tplMProviderCarAllot);
			if(null == listTplProviderCarAllot || listTplProviderCarAllot.size()<=0){
				// ����ҵ����Ϣ
				System.out.println("����ҵ����Ϣ");
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
				tplProviderCarAllot.setFlag("00");//δ��λȷ��
				messageDao.insert(tplProviderCarAllot, TplProviderCarAllot.class);
				
				//����˾����U����������Ϣ��˾��
				String queryTplTranSeq = " from TplTransSeq t where t.id = " + Long.valueOf(tplMProviderCarAllot.getSeqId());
				List list = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
				if(null != list && list.size()>0){
					TplTransSeq tplTransSeq= (TplTransSeq) list.get(0);
					String alias = tplTransSeq.getDriver();//���ͱ���
					HashMap bspMap = new HashMap();//���ݲ���
					//���ݿ��������Ʋ�ѯ����������ȫ���Լ���������
					String wareHouseCode= tplProviderCarAllot.getParkingLot().substring(0,tplProviderCarAllot.getParkingLot().indexOf("-"));//����id
					List listHous = messageDao.queryWareHouseName(wareHouseCode, new Long(tplProviderCarAllot.getSeqId()));
					if(null != list && list.size()>0){
						TplTransTruckSignIn tplTransTruckSignIn = (TplTransTruckSignIn) listHous.get(0);
						bspMap.put("WAREHOUSE_NAME", tplTransTruckSignIn.getLadingSpotName());//�ֿ�����
						bspMap.put("WAREHOUSE_ID", tplTransTruckSignIn.getLadingSpot());//�ֿ�id
					}else{
						bspMap.put("WAREHOUSE_NAME", "");
						bspMap.put("WAREHOUSE_ID", "");
					}
					List listProviderCar=messageDao.queryProviderCar(wareHouseCode, tplProviderCarAllot.getLoadingPlanNo());
					if(null !=listProviderCar && listProviderCar.size()>0){
						Object [] items = (Object[]) listProviderCar.get(0);
						bspMap.put("LIST_NUM", "����"+ items[0].toString()+ "-���"+ items[1].toString());
					}else{
						bspMap.put("LIST_NUM", "");
					}
					bspMap.put("PARKING_CODE", tplProviderCarAllot.getParkingLot());
					bspMap.put("CONFIRM_FLAG", "00");// �Ƿ�λ
					bspMap.put("SYSTEM_TYPE", "30");// ϵͳ��־
					bspMap.put("ALIAS", alias);//����
					bspMap.put("PROC_TYPE", "I");//������־
				    bspMap.put("DRI_U_CODE",alias);
				    
				    System.out.println("ִ����Ϣ����");
					String result = JpushUtil.push(bspMap);
					System.out.println("��Ϣ���ͽ����"+result);
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
			tplMessageLog.setErrTitle("�����������̳�����λ������Ϣ�����쳣,��ͷ��Ϊ��"
					+ tplMProviderCarAllot.getTruckNo() + "�ҳ���Ϊ��"
					+ tplMProviderCarAllot.getTrailerNum());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}

	// ��ά��ɨ���鵥ͨ����Ϣ[XLXZ54]
	/*public void receiveTplMQrCodeScanPass(TplMQrCodeScanPass tplMQrCodeScanPass)
			throws Exception {
		Date date = new Date();
		System.out.println("��ά��ɨ���鵥ͨ����Ϣ���Ľ��գ�ʱ�䣺������" + date);
		try {
			// ����������Ϣ
			tplMQrCodeScanPass.setCreateDate(date);
			messageDao.insert(tplMQrCodeScanPass, TplMQrCodeScanPass.class);

			// ����ҵ����Ϣ
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
			tplMessageLog.setErrTitle("���ն�ά��ɨ���鵥ͨ����Ϣ�����쳣,װ�ؼƻ���Ϊ��"
					+ tplMQrCodeScanPass.getLoadingPlanNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}*/

	// װ�ؼƻ�װ��ʵ�� [XLXZ55]
	public void receiveTplMActLoadPlan(TplMActLoadPlan tplMActLoadPlan)
			throws Exception {
		Date date = new Date();
		System.out.println("װ�ؼƻ�װ��ʵ��������Ϣ���գ�ʱ�䣺������" + date);
		tplMActLoadPlan.setTplMActLoadPlanItemSet(BeanConvertUtils.strToBean(tplMActLoadPlan.getDetail(), CLASS_TPL_M_ACT_LOAD_PLAN_ITEM));
		try {

			String hqlQuery = " from TplActLoadPlan t where t.loadingPlanNo = '"
					+ tplMActLoadPlan.getLoadingPlanNo()
					+ "' and t.seqId='"
					+ tplMActLoadPlan.getSeqId() + "'";
			

			// ����null��model
			Iterator it = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
			while (it.hasNext()) {
				TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) it.next();
				if (null ==tplMActLoadPlanItem.getPackId() || tplMActLoadPlanItem.getPackId().trim().equals("")) {
					it.remove();
				}
			}

			// ����������Ϣ
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
			
			//I���������������װ��ʵ�����Ȱ�֮ǰ�����ʵ����ԭ��
			if (null != tplMActLoadPlan.getOperFlag() && tplMActLoadPlan.getOperFlag().trim().equals("I")) {
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {//�ж�װ�ؼƻ��Ƿ����
					List listOld = new ArrayList();//ԭ������Ϣ
					String queryTplTranSeq = " from TplTransSeq t where t.id = " + Long.valueOf(tplMActLoadPlan.getSeqId());
					List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
					if(null != tplTransSeqList && tplTransSeqList.size()>0){
						TplTransSeq tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
						if (tplTransSeq.getId() != null) {
						//����װ�ؼƻ��š����Ų�ѯ��ԭ������Ϣ
						listOld = messageDao.queryTplTransPackY(tplMActLoadPlan.getLoadingPlanNo(),tplMActLoadPlan.getSeqId());
						//ԭ�����ʵ������Ϣ��ԭ
						this.restoreXZXL55(tplTransSeq,listOld,tplMActLoadPlan);
						//ɾ��ԭװ�ؼƻ������ʵ����Ϣ
						TplActLoadPlan tplActLoadPlan = (TplActLoadPlan) list.get(0);
						Long[] ids = { tplActLoadPlan.getId() };
						messageDao.delete(ids, TplActLoadPlan.class);
						// ɾ��ԭװ�����ʵ��������Ϣ
						String deleteTplActLoadPlanItem = "delete from TPL_ACT_LOAD_PLAN_ITEM t where t.PLAN_ACT_ID = "
								+ tplActLoadPlan.getId();
						System.out.println("deleteTplActLoadPlanItem:::"+ deleteTplActLoadPlanItem);
						messageDao.deleteSql(deleteTplActLoadPlanItem);
						}
					}
				}
			}

			//�ж�������seq_id�ֶ��Ƿ񲻵���null�����в�����null˵���Ѿ���װ��
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
							message +=tplMActLoadPlanItem.getPackId()+"��";
						}
					}
					if(null !=message && !message.trim().equals("")){
						TplMessageLog tplMessageLog= new TplMessageLog();
						tplMessageLog.setErrTitle("װ�ؼƻ�װ��ʵ��");
						tplMessageLog.setErrContent("������װ�صĲ��ϣ����Ϻ�Ϊ��"+message+"�����κ�Ϊ:"+tplMActLoadPlan.getSeqId()+" ��װ�ؼƻ���Ϊ: "+tplMActLoadPlan.getLoadingPlanNo());
						tplMessageLog.setCreateDate(date);
						messageDao.insert(tplMessageLog, TplMessageLog.class);
						return;
					}
				}
			}

			//��ѯ��������Ϣ
			TplTransSeq tplTransSeq = new TplTransSeq();// ������Ϣ
			List listTplTransPack = new ArrayList();// ����������Ϣ
			List listIds = new ArrayList();// ���ʵ����������id
			System.out.println("��ѯ��������Ϣ������Ϊ��" + tplMActLoadPlan.getSeqId());
			Long[] packIds = new Long[tplMActLoadPlan.getTplMActLoadPlanItemSet().size()];
			String queryTplTranSeq = " from TplTransSeq t where t.id = " + Long.valueOf(tplMActLoadPlan.getSeqId());
			List tplTransSeqList = messageDao.queryTplQualityDefectConfigByHql(queryTplTranSeq);
			if (tplTransSeqList != null && tplTransSeqList.size() > 0) {
				tplTransSeq = (TplTransSeq) tplTransSeqList.get(0);
				Iterator iterator = tplMActLoadPlan.getTplMActLoadPlanItemSet().iterator();
				if (iterator != null) {
					while (iterator.hasNext()) {
						TplMActLoadPlanItem tplMActLoadPlanItem = (TplMActLoadPlanItem) iterator.next();
						// ��������id�����쵥Ԫ�ᵥ�Ų�ѯ��������Ϣ
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
					listIds.toArray(packIds);// ��ȡ���ʵ�� pack������ϢId
				}
			}
			
			List listOld = new ArrayList();//�������Ϊ'D' or 'U'��ԭװ�ؼƻ��µĲ���
			if(tplMActLoadPlan.getOperFlag().trim().equals("U") || tplMActLoadPlan.getOperFlag().trim().equals("D")){
				if (tplTransSeq.getId() != null) {
					// ����װ�ؼƻ��š����Ų�ѯ��ԭ������Ϣ
					listOld = messageDao.queryTplTransPackY(tplMActLoadPlan.getLoadingPlanNo(),tplMActLoadPlan.getSeqId());
				}
			}

			// ҵ�����
			if (tplMActLoadPlan.getOperFlag().trim().equals("I")) {
				System.out.println("����ҵ����Ϣ");
				TplActLoadPlan tplActLoadPlan = new TplActLoadPlan();
				tplActLoadPlan.setLoadingPlanNo(tplMActLoadPlan.getLoadingPlanNo());
				tplActLoadPlan.setTruckNo(tplMActLoadPlan.getTruckNo());
				tplActLoadPlan.setTrailerNum(tplMActLoadPlan.getTrailerNum());
				tplActLoadPlan.setParkingLot(tplMActLoadPlan.getParkingLot());
				tplActLoadPlan.setCreateDate(date);
				tplActLoadPlan.setSeqId(tplMActLoadPlan.getSeqId());
				messageDao.insert(tplActLoadPlan, TplActLoadPlan.class);// ����Ϣ����
				// ����Ϣ����
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
				System.out.println("�޸�ҵ����Ϣ");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplActLoadPlan tplActLoadPlan = (TplActLoadPlan) list.get(0);
					tplActLoadPlan.setTruckNo(tplMActLoadPlan.getTruckNo());
					tplActLoadPlan.setTrailerNum(tplMActLoadPlan.getTrailerNum());
					tplActLoadPlan.setParkingLot(tplMActLoadPlan.getParkingLot());
					messageDao.update(tplActLoadPlan, TplActLoadPlan.class);
					// �޸�����Ϣ
					// ɾ������Ϣ���ٴ�����
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
				System.out.println("ɾ��ҵ����Ϣ");
				List list = messageDao.queryTplQualityDefectConfigByHql(hqlQuery);
				if (null != list && list.size() > 0) {
					TplActLoadPlan tplActLoadPlan = (TplActLoadPlan) list.get(0);
					// ɾ����Ϣ
					Long[] ids = { tplActLoadPlan.getId() };
					messageDao.delete(ids, TplActLoadPlan.class);
					// ɾ������Ϣ
					String deleteTplActLoadPlanItem = "delete from TPL_ACT_LOAD_PLAN_ITEM t where t.PLAN_ACT_ID = "
							+ tplActLoadPlan.getId();
					System.out.println("deleteTplActLoadPlanItem:::"+ deleteTplActLoadPlanItem);
					messageDao.deleteSql(deleteTplActLoadPlanItem);
				}
			}

			//U����D�����Ȼ�ԭ��ԭװ�ؼƻ����ʵ������Ϣ
			if (tplMActLoadPlan.getOperFlag().trim().equals("U") || tplMActLoadPlan.getOperFlag().trim().equals("D")) {
				this.restoreXZXL55(tplTransSeq,listOld,tplMActLoadPlan);
			}
			
			//���ʵ������ˢ�²�����Ϣ���ƻ���Ϣ�����ȼƻ���Ϣ��������Ϣ
			if (tplMActLoadPlan.getOperFlag().trim().equals("I") || tplMActLoadPlan.getOperFlag().trim().equals("U")) {
				DecimalFormat df = new DecimalFormat("0.000000");
				BigDecimal actGrossWeight = new BigDecimal("0");// ʵ��װ��ë��
				BigDecimal actNetWeight = new BigDecimal("0");// ʵ��װ������
				Long actCount = new Long(0);// ʵ��װ������
				if (tplTransSeq.getId() != null) {
					// ������ѡ��Ĳ��ϺŲ�ѯ�����˵��ȼƻ���Ϣ������������Ϣ�ĵ��ȼƻ�id����������״̬
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

					//����������ѯ���ƻ���,�ٸ��ݼƻ���ȥ���¼ƻ�
					if (packIds != null && packIds.length > 0) {
						List listTransPlanId = messageDao.queryTransPlanId(packIds);
						System.out.println("��ѯ�����ļƻ���	����" + listTransPlanId.size());
						if (listTransPlanId != null && listTransPlanId.size() > 0) {
							for (int i = 0; i < listTransPlanId.size(); i++) {
								TplTransPlan tplTransPlan = new TplTransPlan();
								String transPlanId = (String) listTransPlanId.get(i);
								System.out.println("�ƻ��ţ���������������" + transPlanId);
								List list = messageDao.tplTransPlanCount(transPlanId);
								if (null != list && list.size() > 0) {
									Object[] obj = (Object[]) list.get(0);
									actGrossWeight = null == obj[0] ? new BigDecimal("0"): new BigDecimal(obj[0].toString());
									actNetWeight = null == obj[1] ? new BigDecimal("0"): new BigDecimal(obj[1].toString());
									actCount = null == obj[2] ? new Long(0): new Long(obj[2].toString());
								}
								// ���¼ƻ�
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
					//���ݳ���ʵ��������Ϣ��ѯ�����ʵ��ë�ء����ء�������Ȼ����µ������䳵���У����޸Ĵ˳���Ϊ������
					tplTransSeq.setStatus("1");
					tplTransSeq.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransSeq.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransSeq.setActCount(actCount);// ʵ�ʼ���
					tplTransSeq.setPlanGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransSeq.setPlanNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransSeq.setPlanCount(actCount);// �ƻ�����
					tplTransSeq.setProStatus("10");// �ѷ���
					tplTransSeq.setUploadTime(date);//�޸�ʱ��
					tplTransSeq.setRealStartDate(date);//ʵ�ʷ���ʱ��
					messageDao.update(tplTransSeq, TplTransSeq.class);
					
					//������ָ���ļƻ��Ÿ������˵��ȼƻ�
					//���ݳ��κš�װ�ƻ��Ų�ѯ����װ�ƻ��µ�ʵ��ë�ء����ء�����
					List loadPlanSumList = messageDao.tplTransLoanPlanCount(tplMActLoadPlan);
					Object[] objSum = (Object[]) loadPlanSumList.get(0);
					actGrossWeight = null == objSum[0] ? new BigDecimal("0"): new BigDecimal(df.format(objSum[0]));
					actNetWeight = null == objSum[1] ? new BigDecimal("0"): new BigDecimal(df.format(objSum[1]));
					actCount = null == objSum[2] ? new Long(0) : new Long(objSum[2].toString());
					TplTransTruckDisPlan tplTransTruckDisPlan = new TplTransTruckDisPlan();
					tplTransTruckDisPlan.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
					tplTransTruckDisPlan.setActNetWeight(Double.valueOf(actNetWeight.toString()));
					tplTransTruckDisPlan.setActCount(actCount);
					tplTransTruckDisPlan.setStatus("20");//�����յ�װ�ؼƻ����ʵ������Ϣ����Ϊ�ѽ᰸
					tplTransTruckDisPlan.setTransPlanId(tplMActLoadPlan.getLoadingPlanNo());
					messageDao.updateTplTransTruckDisPlan(tplTransTruckDisPlan);

					//����װ�ؼƻ�
					TplTransLoadPlan tplTransLoadPlan = new TplTransLoadPlan();
					tplTransLoadPlan.setLoadingPlanNo(tplMActLoadPlan.getLoadingPlanNo());
					tplTransLoadPlan.setActGrossWeight(actGrossWeight);
					tplTransLoadPlan.setActNetWeight(actNetWeight);
					tplTransLoadPlan.setActCount(actCount);
					tplTransLoadPlan.setSeqId(tplTransSeq.getId());
					tplTransLoadPlan.setStatus("2");//�����ܵ�װ�ؼƻ����ʵ����Ϣ����Ϊ�᰸
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
			tplMessageLog.setErrTitle("����װ�ؼƻ�װ��ʵ��������Ϣ�����쳣,ת�ؼƻ���Ϊ��"
					+ tplMActLoadPlan.getLoadingPlanNo() + "������Ϊ��"
					+ tplMActLoadPlan.getTruckNo());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}

	// �ֿⶨ�����쳣������Ϣ(XLXZ56��-�����޼ƻ�ȷ�ϻ��쳣����ʱ
	public void receiveTplMDepotException(TplMDepotException tplMDepotException)
			throws Exception {
		Date date = new Date();
		System.out.println("�ֿⶨ�����쳣������Ϣ���գ�ʱ�䣺������" + date);
		// ������Ϣ����
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
			// ����
			if (tplMDepotException.getOperFlag().trim().equals("I")) {
				List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
				if (list != null && list.size() > 0) {
					return;
				}
			}
			messageDao.insert(tplMDepotException, TplMDepotException.class);
			// ҵ�����
			if (tplMDepotException.getOperFlag().trim().equals("I")) {
				System.out.println("����ҵ����Ϣ");
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
				System.out.println("�޸�ҵ����Ϣ");
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
				System.out.println("ɾ��ҵ����Ϣ");
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
			tplMessageLog.setErrTitle("���ն����޼ƻ�ȷ�ϻ��쳣����ʱ��Ϣ�����쳣,�������Ϊ��"
					+ tplMDepotException.getStockCode() + "����������Ϊ��"
					+ tplMDepotException.getCheckType() + "���豸����Ϊ��"
					+ tplMDepotException.getDeviceType());
			messageDao.insert(tplMessageLog, TplMessageLog.class);
		}
		System.out.println("����");
	}

	// װ�ؼƻ���������շ���[XZXL21]
	public void sendTplMLoadPlanResponse(
			TplMLoadPlanResponse tplMLoadPlanResponse) throws Exception {
		Date date = new Date();
		tplMLoadPlanResponse.setCreateDate(date);
		System.out.println("����װ�ؼƻ���������շ���������Ϣ");
		messageDao.insert(tplMLoadPlanResponse, TplMLoadPlanResponse.class);
		logger.info("---����װ�ؼƻ���������շ�����Ϣ��" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_LOAD_PLAN_RESPONSE, null,
				tplMLoadPlanResponse);
		System.out.println("����");
	}

	// ��������װ�ؼƻ��䳵��Ϣ[XZXL22]
	public void sendTplMPlanAllocationCar(
			TplMPlanAllocationCar tplMPlanAllocationCar) throws Exception {
		Date date = new Date();
		tplMPlanAllocationCar.setCreateDate(date);
		System.out.println("������������װ�ؼƻ��䳵��Ϣ");
		messageDao.insert(tplMPlanAllocationCar, TplMPlanAllocationCar.class);
		logger.info("---������������װ�ؼƻ��䳵��Ϣ��" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_PLAN_ALLOCATION_CAR, null,
				tplMPlanAllocationCar);
		System.out.println("����");
	}

	// �����ͬʵ�ʽ�������Ϣ[XZXL23]
	public void sendTplMFactSinceContract(
			TplMFactSinceContract tplMFactSinceContract) throws Exception {
		System.out.println("���������ͬʵ�ʽ�������Ϣ������Ϣ");
		messageDao.insert(tplMFactSinceContract, TplMFactSinceContract.class);
		logger.info("---���������ͬʵ�ʽ�������Ϣ��" + new Date());
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_FACT_SINCE_CONTRACT, null,
				tplMFactSinceContract);
		System.out.println("����");
	}

	// ��ά��ɨ������Ϣ[XZXL24]
	public void sendTplMQrCodeScanResult(
			TplMQrCodeScanResult tplMQrCodeScanResult) throws Exception {
		Date date = new Date();
		tplMQrCodeScanResult.setCreateDate(date);
		System.out.println("������ά��ɨ������Ϣ");
		messageDao.insert(tplMQrCodeScanResult, TplMQrCodeScanResult.class);
		logger.info("---���Ͷ�ά��ɨ������Ϣ��Ϣ��" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_QR_CODE_SCAN_RESULT, null,
				tplMQrCodeScanResult);
		System.out.println("����");
	}

	// �������̳���ǩ����Ϣ[XZXL26]
	public void sendTplMProviderCarSign(TplMProviderCarSign tplMProviderCarSign)
			throws Exception {
		Date date = new Date();
		tplMProviderCarSign.setCreateDate(date);
		System.out.println("�����������̳���ǩ����Ϣ");
		messageDao.insert(tplMProviderCarSign, TplMProviderCarSign.class);
		
		tplMProviderCarSign.setSignInNo(String.valueOf(tplMProviderCarSign.getId()));//ǩ�����
		messageDao.update(tplMProviderCarSign, TplMProviderCarSign.class);
		logger.info("---�����������̳���ǩ����Ϣ��" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_PROVIDER_CAR_SIGN, null,
				tplMProviderCarSign);
		System.out.println("����");
	}

	// �����������ᳵ������λ[XZXL25]
	public void sendTplMServiceCarInPlace(
			TplMServiceCarInPlace tplMServiceCarInPlace) throws Exception {
		Date date = new Date();
		tplMServiceCarInPlace.setCreateDate(date);
		System.out.println("�����������ᳵ������λ��Ϣ");
		messageDao.insert(tplMServiceCarInPlace, TplMServiceCarInPlace.class);
		logger.info("---�����������ᳵ������λ ��Ϣ��" + date);
		sendMsgService.sendMsg(MESSAGE_SEND_TPL_M_SERVICE_CAR_IN_PLACE, null,
				tplMServiceCarInPlace);
		System.out.println("����");
	}
	
	/**
	 * ��Ժ��ԭ���ʵ��ԭ��Ϣ���������ƻ������Ρ����ȼƻ���װ�ؼƻ���
	 * @param tplTransSeq
	 * @param listOld
	 * @param tplMActLoadPlan
	 * @throws Exception
	 */
	private void restoreXZXL55(TplTransSeq tplTransSeq,List listOld,TplMActLoadPlan tplMActLoadPlan) throws Exception {
		Date date = new Date();
		DecimalFormat df = new DecimalFormat("0.000000");
		BigDecimal actGrossWeight = new BigDecimal("0");// ʵ��װ��ë��
		BigDecimal actNetWeight = new BigDecimal("0");// ʵ��װ������
		Long actCount = new Long(0);// ʵ��װ������
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
					// ����ԭװ�ص�ʵ������״̬��0��δװ�أ�10�����ɵ���
					messageDao.update(tplTransPack, TplTransPack.class);
				}
			/*	// ����ԭװ�ص�ʵ������״̬��0��δװ�أ�10�����ɵ���
				for (int j = 0; j < itemIds.length; j++) {
					String updateSql = "update TPL_TRANS_PACK t SET t.PACK_STATUS='0',t.PRO_STATUS='10',t.SEQ_ID=NULL,t.CONTROL_ID =null WHERE t.ID="
							+ itemIds[j]
							+ " and t.SEQ_ID = "
							+ tplTransSeq.getId();
					messageDao.updateContractReplaceStatus(updateSql);
				}*/
				// ���ݲ�����Ϣ��ѯ������Ӧ��װ�ؼƻ�
				List listTransPlanId = messageDao.queryTransPlanId(itemIds);
				if (listTransPlanId != null && listTransPlanId.size() > 0) {
					for (int i = 0; i < listTransPlanId.size(); i++) {
						String transPlanId = (String) listTransPlanId.get(i);

						TplTransPlan tplTransPlan = new TplTransPlan();
						System.out.println("�ƻ��ţ���������������" + transPlanId);
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
			// ���³���״̬
			tplTransSeq.setActGrossWeight(Double.valueOf(actGrossWeight.toString()));
			tplTransSeq.setActNetWeight(Double.valueOf(actNetWeight.toString()));
			tplTransSeq.setActCount(actCount);// ʵ�ʼ���
			tplTransSeq.setPlanGrossWeight(Double.valueOf(actGrossWeight.toString()));
			tplTransSeq.setPlanNetWeight(Double.valueOf(actNetWeight.toString()));
			tplTransSeq.setPlanCount(actCount);// �ƻ�����
			tplTransSeq.setStatus("1");// ִ��״̬
			tplTransSeq.setProStatus("06");// ��λ
			tplTransSeq.setUploadTime(date);//�޸�ʱ��
			messageDao.update(tplTransSeq, TplTransSeq.class);

			// �������˵��ȼƻ�
			// ���ݳ��κš�װ�ƻ��Ų�ѯ����װ�ƻ��µ�ʵ��ë�ء����ء�����
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

			// ����װ�ؼƻ�
			TplTransLoadPlan tplTransLoadPlan = new TplTransLoadPlan();
			tplTransLoadPlan.setLoadingPlanNo(tplMActLoadPlan.getLoadingPlanNo());
			tplTransLoadPlan.setActGrossWeight(actGrossWeight);
			tplTransLoadPlan.setActNetWeight(actNetWeight);
			tplTransLoadPlan.setActCount(actCount);
			tplTransLoadPlan.setSeqId(tplTransSeq.getId());
			tplTransLoadPlan.setStatus("1");//ִ����
			messageDao.updateTplTransLoadPlan(tplTransLoadPlan);
		}
	}
	// ******************************��ɽ���ظ���Ʒ����Ч������end********************************************************
	/**
	 * ��������վ�����[XLXZ64]
	 */
	public void receiveMessageTransStation(TplMessageTransStation messageTransStation) throws Exception {
		System.out.println("StationSeqId==== "+messageTransStation.getStationSeqId());
		if(null==messageTransStation.getStationSeqId() || "".equals(messageTransStation.getStationSeqId())){
			System.out.println("==================XLXZ64==StationSeqId is null====================== ");
			return ;
		}
		System.out.println("=========================�������XLXZ64=============== ");
		TplMessageTransStation message =messageDao.insertMessageTransStation(messageTransStation);
		System.out.println("OperationType==== "+message.getOperationType());
		if("10".equals(message.getOperationType())){
			String  queryHql = " from TplTransStation as t where 1=1 and t.stationSeqId = '"+messageTransStation.getStationSeqId()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (null !=list  && list.size()>0){
				System.out.println("=========================ҵ������и���Ϣ���ܲ���=============== ");
				return;
			}
			System.out.println("=========================�������XLXZ64ҵ���=============== ");
			messageDao.insertTransStation(messageTransStation);
		}else if("20".equals(message.getOperationType())){
			String  queryHql = " from TplTransStation as t where 1=1 and t.stationSeqId = '"+messageTransStation.getStationSeqId()+"'";
			List list = messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (null == list || list.size()==0){
				System.out.println("=========================ҵ���û�и���Ϣ�����޸�=============== ");
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
			System.out.println("=========================�޸ĵ���XLXZ64ҵ���=============== ");
			messageDao.updateTransStation(transStation);
		}
	}
	
	/**
	 * ����ת���� XLXZ62
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
		System.out.println("=======������ϸ����======��"+tplMExtractionTrainPlan.getExtractionTrainItemSet().size());
		String opFlag = tplMExtractionTrainPlan.getProcType();
		String  queryHql = " from TplExtractionTrainPlan as t where 1=1 and t.reqPlanNo = '"+tplMExtractionTrainPlan.getReqPlanNo()+"' and t.reqLotNo = '"+tplMExtractionTrainPlan.getReqLotNo()
		+"')";
		System.out.println("================��������ת���˵��ı�XLXZ62====================");
		TplMExtractionTrainPlan msgPlan = messageDao.insertMessageExtractionTrainPlan(tplMExtractionTrainPlan);
		tplMExtractionTrainPlan.setId(msgPlan.getId());
		System.out.println("================����ת����ҵ������====================");
		if("I".equals(opFlag)){
			List list =messageDao.queryTplQualityDefectConfigByHql(queryHql);
			if (list != null && list.size()>0){
				System.out.println("================����ҵ���====================");
				messageDao.insertExtractionTrainPlan(tplMExtractionTrainPlan, "1");
			}else{
				System.out.println("================����ҵ���====================");
				messageDao.insertExtractionTrainPlan(tplMExtractionTrainPlan, "0");
			}
		}else if("D".equals(opFlag)){
			messageDao.deleteExtractionTrainPlan(tplMExtractionTrainPlan);
		}
		System.out.println("================����XLXZ62���ճɹ�====================");
	}
}
