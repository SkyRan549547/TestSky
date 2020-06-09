package com.baosight.baosteel.bli.tpl.fee.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import baosight.baosteel.uup.client.model.UserSummary;

import com.baosight.baosteel.bli.tpl.base.service.ProcFlagService;
import com.baosight.baosteel.bli.tpl.common.DateUtils;
import com.baosight.baosteel.bli.tpl.common.FileReaderUtil;
import com.baosight.baosteel.bli.tpl.common.StringUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConfigUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConstants;
import com.baosight.baosteel.bli.tpl.fee.dao.InsuranceBillDao;
import com.baosight.baosteel.bli.tpl.fee.dao.PlanFeeDao;
import com.baosight.baosteel.bli.tpl.fee.dao.RealFeeDao;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.InsuranceBillPreviewInfo;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.InsuranceBillSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.PlanFeeSearchModel;
import com.baosight.baosteel.bli.tpl.fee.service.InsuranceBillService;
import com.baosight.baosteel.bli.tpl.interfaces.service.MessageService;
import com.baosight.baosteel.bli.tpl.messagelog.dao.MessageLogDao;
import com.baosight.baosteel.bli.tpl.model.TplInsuranceBill;
import com.baosight.baosteel.bli.tpl.model.TplMessageInsuranceBill;
import com.baosight.baosteel.bli.tpl.model.TplMessageLog;
import com.baosight.baosteel.bli.tpl.model.TplPlanFee;
import com.baosight.baosteel.bli.tpl.model.TplRealFee;
import com.baosight.baosteel.bli.tpl.model.TplTransPlan;
import com.baosight.baosteel.bli.tpl.model.TplTransSeq;
import com.baosight.baosteel.bli.tpl.transport.dao.TransPackDao;
import com.baosight.baosteel.bli.tpl.transport.dao.TransPlanDao;
import com.baosight.baosteel.bli.tpl.transport.dao.TransSeqDao;
import com.baosight.baosteel.bli.tpl.transport.searchmodel.TransPlanSearchModel;
import com.baosight.web.struts.BaseResultModel;

/**
 * <b>�ļ���</b>��InsuranceBillServiceImpl.java
 * <p>
 * <b>��������</b>: 2007-06-07
 * </p>
 * <p>
 * <b>��������</b>����������Serviceʵ����
 * </p>
 * <p>
 * <b>�޸���ʷ</b>:
 * </p>
 * 
 * @author ��ǿ
 * @version 1.0
 */

public class InsuranceBillServiceImpl implements InsuranceBillService {

	private TransPlanDao transPlanDao;

	private TransPackDao transPackDao;

	private TransSeqDao transSeqDao;

	private RealFeeDao realFeeDao;

	private InsuranceBillDao insuranceBillDao;

	private MessageService messageService;

	private MessageLogDao messageLogDao;
	
	private ProcFlagService procFlagService;
	
	private PlanFeeDao planfeedao;
	
	private static final Log logger = LogFactory.getLog(InsuranceBillServiceImpl.class);

	/**
	 * {@inheritDoc}
	 */
	public String deleteTplInsuranceBill(String[] ids) throws Exception {
		Long[] idArray = new Long[ids.length];
		System.out.println("ids.length:" + ids.length);
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.valueOf(ids[i]);
			idArray[i] = id;
			TplInsuranceBill insuranceBill = this.queryTplInsuranceBillById(id);
			TplTransSeq transSeq = (TplTransSeq) transSeqDao.queryById(
					insuranceBill.getShipSeqId(), TplTransSeq.class);
			if (transSeq != null&& transSeq.getTransType().equals(SystemConstants.TRANS_TYPE_SHIP)){
				if (SystemConstants.FEE_STOCK_INVOICE_STATUS_YES.equals(transSeq.getInvoiceStatus())					) {
					return new String("�üƻ����Ҵ����Ѿ���Ʊ������ɾ������!");
				}else if (SystemConstants.TRANS_SEQ_INVOICE_STATUS_FEE.equals(transSeq.getInvoiceStatus())) {
					return new String("�üƻ����Ҵ����Ѿ����ɷ��ã�����ɾ������!");
				}
			}else {
				return new String("�üƻ����Ҵ�������ˮ�˴���!");
			}				
			/*if(transSeq != null && "1".equals(transSeq.getStatus())){
				return new String("�üƻ����Ҵ����Ѿ���ۣ�����ɾ������!");
			}
			if(transSeq != null && "2".equals(transSeq.getStatus())){
				return new String("�üƻ����Ҵ����Ѿ���ۣ�����ɾ������!");
			}*/
			TplTransPlan tplTransPlan = transPlanDao
					.queryTransPlanByTransPlanId(
							insuranceBill.getTransPlanId(), insuranceBill
									.getUnitId());
			
			if(tplTransPlan==null){
				TplMessageLog tplMessageLog = new TplMessageLog();
				tplMessageLog.setErrTitle("ɾ��������ҵ�ƻ��Ѻ��ı���");
				tplMessageLog.setErrContent("������ҵ�ƻ��ţ�"+insuranceBill.getTransPlanId());
				tplMessageLog.setCreateDate(new Date());
				tplMessageLog.setOperMemo("ɾ����������");
				messageLogDao.insertMessageLog(tplMessageLog);

			}
			else{
			TplRealFee realFee = realFeeDao.queryRealFee(transSeq.getId(),
					tplTransPlan.getTransPlanId(), insuranceBill.getOrderNum(),
					insuranceBill.getUnitId());

			// ����üƻ��º�ͬ�ѿ�Ʊ���Ͳ�������ɾ������
			if (realFee != null
					&& realFee.getInvoiceStatus().equals(
							SystemConstants.FEE_STOCK_INVOICE_STATUS_YES)) {
				return new String("�üƻ��Ѿ���Ʊ,����ɾ������!");
			}


			// ����ҵ�ƻ��޸�Ϊδ������״̬;
			if (tplTransPlan != null) {
				tplTransPlan
						.setInsureStatus(SystemConstants.TRANS_PLAN_INSURANCE_UNMAKE);
				transPlanDao.update(tplTransPlan, TplTransPlan.class);
			}
			

			// �ȸ���������ҵ���ñ��кͱ�����صķ��ú�״ֵ̬;
			this.updateRealFeeWhileDelInsurance(insuranceBill.getShipSeqId(),
					insuranceBill.getTransPlanId(),
					insuranceBill.getOrderNum(), insuranceBill.getUnitId());
			

			// ������ɾ����������;
			// this.sendMessageInsuranceBill(Long.valueOf(ids[i]),
			// SystemConstants.FEE_INSUR_OPERATE_DEL, tplTransPlan
			// .getUnitId());
			}
		}
		// �����ɾ��������¼;
		insuranceBillDao.delete(idArray, TplInsuranceBill.class);
		return "ɾ���ɹ���";
	}
	
	
/**
 * ���˱���ɾ��
 * notes��
 *      1.���Ҫɾ��������¼
 *      2.��Ʊ�Ѿ����͸������룬����ɾ������
 *      3.�������ɷ�Ʊ�ķ���ʵ�����γ�ÿ����Ʊ��Ӧ�ķ����Ƕ��ٵ�hashmap
 *		3.���ݳ�Ƥ�ţ��ƻ��ţ���ͬ�Ÿ��³�����ʵ�����������£�
 *		  a.������id��Ϊ��
 *        b.����״̬��Ϊδ������
 *        c.����������
 *        d.�����ܽ�� = �����ܽ�� -�������
 *		4.���ݷ�Ʊid���·�Ʊ��Ϣ���������£�
 *		  a.�����ѽ������
 *		  b.������= ������-���ѽ��
 * @author zf
 */
	public String deleteTplInsuranceBillTrain(String[] ids) throws Exception {
		Long[] idArray = new Long[ids.length];
		for (int i = 0; i < ids.length; i++) {

			Long id = Long.valueOf(ids[i]);
			idArray[i] = id;//��¼ÿ�α�ɾ������id
			//1.���Ҫɾ��������¼
			TplInsuranceBill insuranceBill = this.queryTplInsuranceBillById(id);
			//2.�����Ʊ�Ѿ����͸������룬����ɾ������
			if(insuranceBillDao.getInvoicePayStatusIsSended(insuranceBill))
				return new String("�ñ�����Ӧ��Ʊ�Ѿ����͸������룬����ɾ��!");
			//3.���������һһ��Ӧ����Ϊ���ɷ��õ�ʱ�򣬿�Ʊ��ֻ��һ��
			TplRealFee realFee = realFeeDao.queryRealFee(insuranceBill.getShipSeqId(), insuranceBill.getTransPlanId(), insuranceBill.getOrderNum(),insuranceBill.getUnitId());
			Long invoiceSysId = realFee.getInvoiceSysId();//��¼��Ʊid
			Double totalInsurMoney = realFee.getTotalInsurMoney();//��¼���ñ���
			if(totalInsurMoney==null)  totalInsurMoney = new Double(0);
			
			//3.���·���ʵ��
			insuranceBillDao.updateRealFeeInsurSetZero(insuranceBill,totalInsurMoney);
            //4.���·�Ʊ
			insuranceBillDao.updateInvoiceInsurSetZero(invoiceSysId,totalInsurMoney);
		}
		// �����ɾ��������¼;
		insuranceBillDao.delete(idArray, TplInsuranceBill.class);
		return null;
	}
		
	

	/**
	 * ��ɾ����������Ҫ������ҵʵ����ı��ѵ��ۡ�Ӧ�����ú����״̬;
	 * 
	 * @param transPlanId
	 * @param orderNum
	 * @throws Exception
	 */
	private void updateRealFeeWhileDelInsurance(Long seqId, String transPlanId,
			String orderNum, String unitId) throws Exception {
		TplRealFee tplRealFee = realFeeDao.queryRealFee(seqId, transPlanId,
				orderNum, unitId);
		if (null == tplRealFee) {
			return;
		}
		// ɾ������:��ʵ�������м�ȥ�������ã��޸�INSUR_STATUSΪδ���ɱ���״̬��
		tplRealFee.setInsurId("");
		tplRealFee.setTotalInsurMoney(new Double(0));
		Double totalMoney = tplRealFee.getTotalMoney();
		// �������������ˮ�ˣ���Ҫ����ӡ��˰;
		// if
		// (tplRealFee.getTransType().equals(SystemConstants.TRANS_TYPE_SHIP)) {
		// tplRealFee.setPayMoney(this.addStampTax(totalMoney));
		// } else {
		tplRealFee.setPayMoney(totalMoney);
		// }
		tplRealFee.setInsurStatus(SystemConstants.FEE_INSUR_STATUS_UNMAKE);
		realFeeDao.update(tplRealFee, TplRealFee.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public void insertTplInsuranceBill(TplInsuranceBill model) throws Exception {
		insuranceBillDao.insert(model, TplInsuranceBill.class);

	}

	/**
	 * {@inheritDoc}
	 */
	public TplInsuranceBill queryTplInsuranceBillById(Long id) throws Exception {
		return (TplInsuranceBill) insuranceBillDao.queryById(id,
				TplInsuranceBill.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public BaseResultModel queryTplInsuranceBillWithArgs(
			InsuranceBillSearchModel searchModel) throws Exception {
		BaseResultModel brm = new BaseResultModel();
		brm
				.setResults(insuranceBillDao
						.queryInsuranceBillWithArgs(searchModel));
		return brm;
	}

	/**
	 * {@inheritDoc}
	 */
	public BaseResultModel queryTplInsuranceBillWithArgsByPage(
			InsuranceBillSearchModel searchModel) throws Exception {
		BaseResultModel brm = new BaseResultModel();
		brm.setResults(insuranceBillDao
				.queryInsuranceBillWithArgsByPage(searchModel));
		brm.setPage(searchModel.getPage());
		return brm;
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateTplInsuranceBill(TplInsuranceBill model) throws Exception {
		insuranceBillDao.update(model, TplInsuranceBill.class);

	}

	/**
	 * {@inheritDoc}
	 */
	public BaseResultModel queryTransPlanForMakeInsuranceBill(
			TransPlanSearchModel searchModel) throws Exception {
		BaseResultModel brm = new BaseResultModel();
		brm.setResults(transPlanDao.queryTransPlanWithArgsByPage(searchModel));
		brm.setPage(searchModel.getPage());
		return brm;
	}

	/**
	 * {@inheritDoc}
	 */
	public String insertInsuranceBill(String[] planIds, String[] orderNums)
			throws Exception {
		TplTransPlan tplTransPlan = null;
		String msgInfo = null;
		if (orderNums != null && orderNums.length > 0) {
			for (int i = 0; i < orderNums.length; i++) {
				Long planId = Long.valueOf(planIds[i]);
				String orderNum = orderNums[i];

				List orderItem = transPackDao.queryOrderInfo(planId, orderNum,
						SystemConstants.TRANS_PACK_STATUS_LOADING);
				Object[] order = (Object[]) orderItem.get(0);
				tplTransPlan = (TplTransPlan) transPlanDao.queryById(
						(Long) order[1], TplTransPlan.class);
				Long seqId = (Long) order[0];

				TplInsuranceBill insurance = this.queryInsuranceBill(seqId,
						tplTransPlan.getTransPlanId(), orderNum, tplTransPlan
								.getUnitId());
				if (insurance != null) {
					return new String("�ú�ͬ�����Ѿ�����,�����ظ�����!");
				}

				TplTransSeq transSeq = (TplTransSeq) transSeqDao.queryById(
						seqId, TplTransSeq.class);
				if (null == transSeq) {
					return new String("�üƻ������Ѿ���ɾ��!");
				}
				if (transSeq != null
						&& transSeq.getInvoiceStatus().equals(
								SystemConstants.FEE_STOCK_INVOICE_STATUS_YES)) {
					return new String("�ƻ�"+tplTransPlan.getTransPlanId()+"���Ҵ����Ѿ���Ʊ�����������ɱ���!");
				}

				TplRealFee realFee = realFeeDao.queryRealFee(transSeq.getId(),
						tplTransPlan.getTransPlanId(), orderNum, tplTransPlan
								.getUnitId());
				// ����üƻ��º�ͬ�ѿ�Ʊ���Ͳ����������ɱ���;
				if (realFee != null
						&& realFee.getInvoiceStatus().equals(
								SystemConstants.FEE_STOCK_INVOICE_STATUS_YES)) {
					return new String("�ƻ�"+tplTransPlan.getTransPlanId()+"�Ѿ���Ʊ,�������ɱ���!");
				}
				
				//������ҵ�ƻ�,��ͬ�Ų�ѯplanfee, ������id ����planFee
				List planFeeList = realFeeDao.queryPlanFee(tplTransPlan.getTransPlanId(), orderNum, tplTransPlan.getProviderId());
				TplInsuranceBill insuranceBill = new TplInsuranceBill();
				{   
					insuranceBill.setInsurId("");
					//����������Ҫ���ķ�Ʊ����,add by llk 2012-3-19,
					if(planFeeList != null && planFeeList.size()>0){
						TplPlanFee planFee = (TplPlanFee)planFeeList.get(0);
						insuranceBill.setInvoiceType(planFee.getInvoiceType());
						insuranceBill.setPolicyHolder(planFee.getInvoiceTitleName());// �����ˣ���Ʊ̧ͷ��λ����
					}
					insuranceBill.setTransType(SystemConstants.TRANS_TYPE_SHIP);
					insuranceBill.setShipSeqId(tplTransPlan.getSeqId());
					insuranceBill.setUnitId(tplTransPlan.getUnitId());
					insuranceBill.setUnitName(tplTransPlan.getUnitName());
					insuranceBill.setManuId(tplTransPlan.getManuId());
					insuranceBill.setShipId(tplTransPlan.getShipId());
					insuranceBill.setShipName(tplTransPlan.getShipName());
					insuranceBill.setTransPlanId(tplTransPlan.getTransPlanId());
					insuranceBill.setOrderNum(orderNum);
					insuranceBill.setCompanyCode((String) order[5]);
					// insuranceBill.setInvoiceSysId(invoiceSysId);
					// insuranceBill.setInvoiceId(invoiceId);
					// insuranceBill.setInvoiceNo(invoiceNo);
					//insuranceBill.setPolicyHolder(SystemConstants.POLICY_HOLDER);
					insuranceBill.setInsuredMan(tplTransPlan.getConsineeName());
					insuranceBill.setConsigneeCode(tplTransPlan.getConsineeCode());
					insuranceBill.setConsigneeName(tplTransPlan.getConsineeName());
					insuranceBill.setLadingSpot(tplTransPlan.getLadingSpot());
					insuranceBill.setLadingSpotName(tplTransPlan
							.getLadingSpotName());
					insuranceBill.setDestSpot(tplTransPlan.getDestSpot());
					insuranceBill.setDestSpotName(tplTransPlan
							.getDestSpotName());
					insuranceBill.setProductType((String) order[6]);
					insuranceBill.setProductTypeName((String) order[7]);
					insuranceBill.setTotalCount((Long) order[10]);
					insuranceBill.setTotalGrossWeight((Double) order[11]);
					insuranceBill.setTotalNetWeight((Double) order[12]);
					insuranceBill.setBeginTransDate(tplTransPlan
							.getExcuteDate());
					insuranceBill.setUnitPrice(this.filterDouble((Double) order[9]));
					
					double unitPrice = filterDouble((Double) order[9])
							.doubleValue();
					//����һ�ɰ����ؼ���;
					double weight = filterDouble((Double) order[12])
							.doubleValue();
					double totalInsurMoney = unitPrice * weight; // �����ܽ��=���ѵ���*�ܾ���;
					if (totalInsurMoney <= 0) {
						if (msgInfo != null) {
							msgInfo += "," + order[4];
						} else {
							msgInfo = "��ͬ" + order[4];
						}
						continue;
					}
					insuranceBill.setIntegrateInsurance(new Double(0));
					insuranceBill.setBasisInsurance(new Double(0));
					totalInsurMoney=StringUtil.round(totalInsurMoney,2);
					insuranceBill.setInsuranceTotalNum(new Double(totalInsurMoney));
					
				    //�����ɱ�����ʱ��,Ĭ�ϵĽ�����У��ֵ���ó�ϵͳ����ֵ,֮����ֵ˰�Ŀ��Խ��л�д modify by llk 2012/3/20
					insuranceBill.setInsuranceTotalNumAdjust(new Double(totalInsurMoney));

					insuranceBill.setInsuranceTotalCn("");
					insuranceBill.setProviderId(tplTransPlan.getProviderId());
					insuranceBill.setOperateDate(new Date());
					insuranceBill
							.setRewriteStatus(SystemConstants.FEE_INSURANCE_STATUS_UNREWRITE);
					insuranceBill.setCreateId(tplTransPlan.getCreateId());
					insuranceBill.setCreateDate(new Date());
				}
				this.insertTplInsuranceBill(insuranceBill);

				// ���뱣����Ҫ�������ҵʵ�������ط��ú͸���״̬;
				this.updateRealFeeWhileAddInsurance(insuranceBill);
				// �����ͱ�����Ϣ����;
				// �ָ�Ϊ�ڷ��ͷ�Ʊ����ʱһͬ����,�ڴ˲��ٷ���.
				// this.sendMessageInsuranceBill(insuranceBill.getId(),
				// SystemConstants.FEE_INSUR_OPERATE_ADD, tplTransPlan
				// .getUnitId());
				// �ƻ������ɱ���;
				tplTransPlan
						.setInsureStatus(SystemConstants.TRANS_PLAN_INSURANCE_MAKED);
				transPlanDao.update(tplTransPlan, TplTransPlan.class);
			}
			if (msgInfo != null) {
				msgInfo += "��������Ϊ0,���ܿ��߱���!";
				return msgInfo;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateRealFeeWhileAddInsurance(TplInsuranceBill insuranceBill)
			throws Exception {

		TplRealFee tplRealFee = realFeeDao.queryRealFee(insuranceBill
				.getShipSeqId(), insuranceBill.getTransPlanId(), insuranceBill
				.getOrderNum(), insuranceBill.getUnitId());
		if (null == tplRealFee) {
			return;
		}
		double totalInsurMoney = this.filterDouble(
				insuranceBill.getInsuranceTotalNum()).doubleValue();// �ܵı��շ���;
		double totalMoney = filterDouble(tplRealFee.getTotalMoney())
				.doubleValue(); // ���������շѵ��ܽ��;
		tplRealFee.setTotalInsurMoney(new Double(totalInsurMoney));

		// �������������ˮ�ˣ���Ҫ����ӡ��˰;
		// if
		// (tplRealFee.getTransType().equals(SystemConstants.TRANS_TYPE_SHIP)) {
		// tplRealFee.setPayMoney(new Double(this.addStampTax(
		// new Double(totalMoney)).doubleValue()
		// + totalInsurMoney));
		// } else {
		double payMoney = totalMoney + totalInsurMoney; // Ӧ�����=�������շѵ��ܽ��;
		payMoney=StringUtil.round(payMoney,2);
		tplRealFee.setPayMoney(new Double(payMoney));
		// }

		tplRealFee.setInsurStatus(SystemConstants.FEE_INSUR_STATUS_MAKEED); // �����ɱ���;
		realFeeDao.update(tplRealFee, TplRealFee.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Map getPreviewInsuranceBill(String[] ids,String[] shipIds) throws Exception {
		Map resultSet = new HashMap();
		BaseResultModel brm = new BaseResultModel();
		List list = new ArrayList();
		long sumOfCount = 0; // ��������;
		double sumOfGrossWeight = 0.0; // ��ë��;
		double sumOfNetWeight = 0.0; // �ܾ���;
		double sumOfInsurMoney = 0.0; // ���ս��֮��;
		
		if (ids != null && ids.length > 0) {
			
			//��֤�����Ƿ�ˢ�¹���������
			if(shipIds != null && shipIds.length > 0){
				for (int i = 0; i < shipIds.length; i++) {
					if(shipIds[i] != null && shipIds[i] != ""){
						Long seqId = Long.valueOf(shipIds[i]);
						boolean virtualFlag = transPackDao.getVirtualPackFlag(seqId);
		//				boolean InnerWarehouseFlag = transPackDao.getCheckInnerWarehouse(seqId);
						if (virtualFlag) {
							throw new Exception("msg:�üƻ���Ӧ���뵥δ���ɣ����Ժ��������ϵ����Ա! ��ʾ��ϵͳ����ÿ��7:00��12:30�Զ�ˢ��");
						}
						boolean tubeFlag=procFlagService.isTubeProcFlag(seqId,"transType");
						if(tubeFlag){
							throw new Exception("msg:�ó���װ����δ��ˢ�µĹ������ͬ��������ˢ������Ժ��ٲ���! ��ʾ��ϵͳ����ÿ��7:00��12:30�Զ�ˢ��");
						}
					}
				}
			}
			
			for (int i = 0; i < ids.length; i++) {
				Long planId = Long.valueOf(ids[i]); // ������ҵ�ƻ�ID;
				TplTransPlan tplTransPlan = (TplTransPlan) transPlanDao
						.queryById(planId, TplTransPlan.class);
				List result = transPackDao.queryOrderInfoByPlanId(planId,
						SystemConstants.TRANS_PACK_STATUS_LOADING); // ��ѯ��һ����ҵ�ƻ������ĺ�ͬ��Ϣ�б�;
				if (result != null && result.size() > 0) {

					// �ƻ��������ɱ����ĺ�ͬ���б�;
					List orderList = insuranceBillDao
							.queryOrderNumList(tplTransPlan.getSeqId(),
									tplTransPlan.getTransPlanId(), tplTransPlan
											.getUnitId());

					for (int j = 0; j < result.size(); j++) {
						Object[] items = (Object[]) result.get(j); // ȡ��һ����ͬ;
						// ��־λ:�жϼƻ��µĺ�ͬ�Ƿ������ɱ���;false:δ����;true:������
						boolean flag = false;
						if (orderList != null) {
							for (int k = 0; k < orderList.size(); k++) {
								if (((String) items[4])
										.equals((String) orderList.get(k))) {
									flag = true;
									break;
								}
							}
						}
						// ����ú�ͬ�����Ѿ�����,�ͰѸú�ͬ�Ӻ�ͬԤ����ȥ��;
						if (flag) {
							continue;
						}
						
						/** ���ݼƻ���ͬ���ҷ��üƻ���
					      *	 ���ޣ���������ȱ��ĳ�ƻ�ĳ��ͬ�ƻ�����
					      *	 ���У����ݼƻ���ͬ��Ϊ���������б�����Ʊ�����ж�
						 **/
						//add by wq 20110523 begin 
						PlanFeeSearchModel searchModel = new PlanFeeSearchModel();
						searchModel.setPlanId(tplTransPlan.getTransPlanId());
						searchModel.setUnitId(tplTransPlan.getUnitId());
						searchModel.setOrderNum((String)items[4]);
						List planFeeList = planfeedao.queryPlanFeeListWithArgs(searchModel);
						if (null == planFeeList || planFeeList.size()<1) {
							logger.info("msg:������ҵ�ƻ�: " + tplTransPlan.getTransPlanId() + "ȱ�ٷ�����Ϣ,�������ɷ���!");
							throw new Exception("msg:������ҵ�ƻ�ȱ�ٷ�����Ϣ,�������ɷ���!");
						}
						TplPlanFee planFee = (TplPlanFee)planFeeList.get(0);
						
						if("00".equalsIgnoreCase(planFee.getInsuranceFlag()))
							continue;							
						
						//add by wq 20110523 end
						
						InsuranceBillPreviewInfo bean = new InsuranceBillPreviewInfo();
						bean.setPlanId(tplTransPlan.getId());
						bean.setTransPlanId(tplTransPlan.getTransPlanId());
						bean.setUnitId(tplTransPlan.getUnitId());
						bean.setOrderNum((String) items[4]);
						bean.setConsineeName(tplTransPlan.getConsineeName());
						bean
								.setLadingSpotName(tplTransPlan
										.getLadingSpotName());
						bean.setDestSpotName(tplTransPlan.getDestSpotName());
						bean.setShipName(tplTransPlan.getShipName());
						bean.setProductName((String) items[7]);
						
						double grossWeight = this.filterDouble(
								(Double) items[11]).doubleValue();
						double netWeight = this
								.filterDouble((Double) items[12]).doubleValue();
						long actCount = this.filterLong((Long) items[10])
						.longValue();
												
						bean.setGrossWeight(new Double(grossWeight));
						bean.setNetWeight(new Double(netWeight));
						bean.setActCount(new Long(actCount));
//						if (grossWeight < netWeight) {
//							grossWeight = netWeight;
//						}
						double insurancePrice = this.filterDouble(
								(Double) items[9]).doubleValue();
	

						//����һ�ɰ����ؼ���;
						double totalInsurMoney = insurancePrice * netWeight;
						totalInsurMoney = StringUtil.round(totalInsurMoney,2);

						bean.setTotalInsurMoney(new Double(totalInsurMoney));
						
//						if(tplTransPlan.getManuId()!=null && tplTransPlan.getManuId().equalsIgnoreCase("BGTM")){
//							insurancePrice = insurancePrice / 0.0003486;
//						}else{
//							insurancePrice = insurancePrice / 0.0003486;
//						}						
						insurancePrice = StringUtil.round(insurancePrice,2);
						bean.setInsurancePrice(new Double(insurancePrice));
						
						
						if (null == planFee.getInsuranceFlag() || "".equals(planFee.getInsuranceFlag())) {// ��������ǰģʽ�������ʷ���ݲ���������
							//��ȡ��ͬ����
//							String orderNum = null == items[4]?"":(String)items[4];
//							String transPlanId = null == tplTransPlan.getTransPlanId()?"":(String)tplTransPlan.getTransPlanId();
//							String packOrderType = insuranceBillDao.queryPackOrderType(transPlanId,orderNum);
							
							//��Ʒ����
//							String productType = null == items[6]? "":(String)items[6];
//							Date planInDate = tplTransPlan.getModifyDate();
//							String manuId = tplTransPlan.getManuId();
//							String consineeCode = tplTransPlan.getConsineeCode();
							
							//String manuId, String productType, Date planInDate, String consineeCode
//							double insurBasePrice =  insuranceBillDao.queryBasePriceList(manuId,productType, planInDate, consineeCode,packOrderType);
							
							/* 2011-07-21 16:55-- ����Ϊ0.035% 2011-07-07 17:48---2011-07-21 16:55 ����Ϊ0.03486% 2011-07-07 17:48��ǰ ��÷�ַ���Ϊ 0.00065 �⣬�������ʾ�Ϊ 0.00042**/
							double insuranceRate = 0.00035;
							Date planInDate = tplTransPlan.getModifyDate();
							if (planInDate != null) {
								if (planInDate.before(DateUtils.toDate("2011-07-07 17:48", "yyyy-MM-dd HH:mm"))) {
									if(tplTransPlan.getManuId()!=null && tplTransPlan.getManuId().equalsIgnoreCase("BGTM")){
										insuranceRate = 0.00065;
									} else {
										insuranceRate = 0.00042;
									}
								} else if (planInDate.after(DateUtils.toDate("2011-07-07 17:48", "yyyy-MM-dd HH:mm"))
										&& planInDate.before(DateUtils.toDate("2011-07-21 16:55", "yyyy-MM-dd HH:mm"))) {
									insuranceRate = 0.0003486;
								} else if (planInDate.after(DateUtils.toDate("2011-07-07 17:48", "yyyy-MM-dd HH:mm"))) {
									insuranceRate = 0.00035;
								}
							}
							
							double insurBasePrice = insurancePrice / insuranceRate;
							bean.setInsurBasePrice(new Double(StringUtil.round(insurBasePrice,2)));
						} else if("10".equalsIgnoreCase(planFee.getInsuranceFlag())) {// ��������������µ�ģʽ
							bean.setInsurBasePrice((Double) items[13]);
						}

						list.add(bean);
						sumOfCount += actCount;
						sumOfGrossWeight += grossWeight;
						sumOfGrossWeight=StringUtil.round(sumOfGrossWeight, 3);
						sumOfNetWeight += netWeight;
						sumOfNetWeight=StringUtil.round(sumOfNetWeight, 3);
						sumOfInsurMoney += totalInsurMoney;
						sumOfInsurMoney=StringUtil.round(sumOfInsurMoney,2);
					}
				}
			}
		}

		brm.setResults(list);
		resultSet.put("insurList", brm);
		resultSet.put("totalActCount", new Long(sumOfCount));
		resultSet.put("totalGrossWeight", new Double(sumOfGrossWeight));
		resultSet.put("totalNetWeight", new Double(sumOfNetWeight));
		resultSet
				.put("totalInsurMoney", new Double(sumOfInsurMoney));
		return resultSet;
	}


	/**
	 * {@inheritDoc}
	 */
	public void updateInsurBillCode(String[] ids, String startInsurBillCode)
			throws Exception {
		TplInsuranceBill tplInsuranceBill = null;
		long startCode = Long.valueOf(startInsurBillCode).longValue();
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.valueOf(ids[i]);

			String insurBillCode = new Long(startCode).toString();
			//���startInsurBillCode�Ŀ��>insurBillCode�Ŀ��
			int zeroNum=startInsurBillCode.length()-insurBillCode.length();
			if(zeroNum>0){				
				char[] headChar=new char[zeroNum];
				Arrays.fill(headChar, (char)'0');				
				insurBillCode=new String(headChar)+insurBillCode;
			}
			tplInsuranceBill = this.queryTplInsuranceBillById(id);
			tplInsuranceBill.setInsurId(insurBillCode);
			tplInsuranceBill
					.setRewriteStatus(SystemConstants.FEE_INSURANCE_STATUS_REWRITED);
			tplInsuranceBill.setRewriteDate(new Date());
			this.updateTplInsuranceBill(tplInsuranceBill);
			startCode++;
			/****rangiguang add start 20181011 *********/
			// ������ҵʵ����ı����,��������;
//			TplRealFee tplRealFee = realFeeDao.queryRealFee(tplInsuranceBill
//					.getShipSeqId(), tplInsuranceBill.getTransPlanId(),
//					tplInsuranceBill.getOrderNum(), tplInsuranceBill
//							.getUnitId());
			List tplRealFeeList = realFeeDao.insuranceQueryRealFeeList(tplInsuranceBill
					.getShipSeqId(), tplInsuranceBill.getTransPlanId(),
					tplInsuranceBill.getOrderNum(), tplInsuranceBill
							.getUnitId());
			if (tplRealFeeList != null && tplRealFeeList.size()>0) {
				for(int j=0; j<tplRealFeeList.size(); j++){
					TplRealFee tplRealFee = (TplRealFee)tplRealFeeList.get(j);
					tplRealFee.setInsurId(insurBillCode);
					realFeeDao.update(tplRealFee, TplRealFee.class);
				}
			}
			/****rangiguang add end 20181011 *********/
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public HSSFWorkbook getDownLoadInsurances(InsuranceBillSearchModel searchModel) throws Exception {
		String [] ids=searchModel.getIds();
		if (ids==null || ids.length<=0){
			throw new Exception("msg:����ѡ����Ҫ���صı������ݡ�");
		}
		if (ids!=null && ids.length>30){
			throw new Exception("msg:�����ֻ������30���������ݡ�");
		}
		List insurList=new ArrayList();
		HSSFWorkbook wb = new HSSFWorkbook(); // ������HSSFWorkbook����;
		HSSFSheet sheet = wb.createSheet("InsuranceSheet1"); // �����µ�sheet����
		HSSFRow titleRow = sheet.createRow(0); // ����������
		String[] headers = { "�������", "��������", "��֯��������", "��֯��������", "������", "����",
				"��Ʊ���", "��Ƥ��", "��ҵ�ƻ���", "��ͬ��", "Ͷ����", "��������", "ʼ��վ����",
				"�յ�վ����", "Ʒ������", "����", "ë��","����", "��������", "���ս��", "���շ�(����ֵ)","���շ�(Ʊ��ֵ)","������������", };
		for (int i = 0; i < headers.length; i++) {
			// ����һ����¼
			HSSFCell csCell = titleRow.createCell((short) i);
			// ����cell���������ĸ�λ�ֽڽض�
			csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
			// �������
			csCell.setCellValue(headers[i]);
		}
		if(ids!=null && ids.length>0){
			for(int i=0;i<ids.length;i++){
				List result = insuranceBillDao.queryDownLoadInsuranceInfo(Long.valueOf(ids[i]));
				Object[] item = (Object[]) result.get(0);
				insurList.add(item);
			}
//		}else{
//			insurList=insuranceBillDao.queryDownLoadInsuranceInfo(searchModel);
		}
		if(insurList!=null && insurList.size()>0){
		for (int i = 0; i < insurList.size(); i++) {
			
			Object[] item = (Object[]) insurList.get(i);
			int size=item.length;
			double unitPrice=this.filterDouble((Double)item[19]).doubleValue();
			String transPlanId = (String) item[8];
			String orderNum = (String) item[9];
			TplTransPlan transPlan = insuranceBillDao.queryTplTransPlan(transPlanId);// ��ҵ�ƻ�
			// ȡ�÷��üƻ�
			PlanFeeSearchModel planFeeSearchModel = new PlanFeeSearchModel();
			planFeeSearchModel.setPlanId(transPlan.getTransPlanId());
			planFeeSearchModel.setUnitId(transPlan.getUnitId());
			planFeeSearchModel.setOrderNum(orderNum);
			List planFeeList = planfeedao.queryPlanFeeListWithArgs(planFeeSearchModel);
			TplPlanFee planFee = null;
			if (null != planFeeList && planFeeList.size() > 0) {
				planFee = (TplPlanFee)planFeeList.get(0);
			}
			System.out.println("11111111111111................................");
			if (null == planFee.getInsuranceFlag() || "".equals(planFee.getInsuranceFlag())) {// ��������ǰģʽ�������ʷ���ݲ���������
				System.out.println("22222222222222222222222222................................");
				String manuId=(String)item[size-1];
				
				/* 2011-07-21 16:55-- ����Ϊ0.035% 2011-07-07 17:48---2011-07-21 16:55 ����Ϊ0.03486% 2011-07-07 17:48��ǰ ��÷�ַ���Ϊ 0.00065 �⣬�������ʾ�Ϊ 0.00042**/
				double insuranceRate = 0.00035;
				Date planInDate = transPlan.getModifyDate();
				if (planInDate != null) {
					if (planInDate.before(DateUtils.toDate("2011-07-07 17:48", "yyyy-MM-dd HH:mm"))) {
						if(transPlan.getManuId()!=null && transPlan.getManuId().equalsIgnoreCase("BGTM")){
							insuranceRate = 0.00065;
						} else {
							insuranceRate = 0.00042;
						}
					} else if (planInDate.after(DateUtils.toDate("2011-07-07 17:48", "yyyy-MM-dd HH:mm"))
							&& planInDate.before(DateUtils.toDate("2011-07-21 16:55", "yyyy-MM-dd HH:mm"))) {
						insuranceRate = 0.0003486;
					} else if (planInDate.after(DateUtils.toDate("2011-07-07 17:48", "yyyy-MM-dd HH:mm"))) {
						insuranceRate = 0.00035;
					}
				}
				unitPrice = unitPrice / insuranceRate;
//				if(manuId!=null && manuId.equalsIgnoreCase("BGTM")){
//					unitPrice = unitPrice / 0.00065;
//				}else{
//					unitPrice = unitPrice / 0.00042;
//				}	
			} else if("10".equalsIgnoreCase(planFee.getInsuranceFlag())) {// ��������������µ�ģʽ
				System.out.println("33333333333333333333333................................");
				List list = transPackDao.queryProductInfoOfOrder(transPlan.getId(), orderNum, SystemConstants.TRANS_PACK_STATUS_LOADING);
				if (list != null && list.size() > 0) {
					Object[] items = (Object[]) list.get(0);
					System.out.println("4444444444444444444444444444................................" + items[13]);
					unitPrice = ((Double) items[13]).doubleValue();
				}
			}
			
			item[19]=new Double(StringUtil.round(unitPrice,2));
			HSSFRow dataRow = sheet.createRow(i + 1);
			for (int j = 0; j < size-1; j++) {
				HSSFCell csCell = dataRow.createCell((short) j);
				// ����cell���������ĸ�λ�ֽڽض�
				csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				if (null != item[j]) {
					csCell.setCellValue(String.valueOf(item[j]));
				}
			}
		}}
		return wb;
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryDownloadTransInsurance(String[] ids, String providerId)
			throws Exception {

		String FTP_LOCAL_FILE_PATH = null;
		if (System.getProperty("os.name").startsWith("Windows")) {

			FTP_LOCAL_FILE_PATH = SystemConstants.FTP_LOCAL_FILE_PATH_WINDOWS;
		} else {

			FTP_LOCAL_FILE_PATH = SystemConstants.FTP_LOCAL_FILE_PATH_LINUX;
		}
		String ZIP_FILE_NAME = "InsuranceBill.zip";
		String TEMP_FILE_PATH = FTP_LOCAL_FILE_PATH;
		FileWriter mainFileWriter = null;
		BufferedWriter mainBuffWriter = null;
		TplInsuranceBill insuranceBill = null;
		try {
			File file = null;
			for (int i = 0; i < ids.length; i++) {
				insuranceBill = (TplInsuranceBill) insuranceBillDao.queryById(
						Long.valueOf(ids[i]), TplInsuranceBill.class);
				if (null == providerId) {
					providerId = insuranceBill.getProviderId();
				}
				TEMP_FILE_PATH = FTP_LOCAL_FILE_PATH + providerId
						+ "TplInsurance" + "//";
				if (null == file) {
					file = new File(TEMP_FILE_PATH);
					file.mkdirs();
				}
				if (null == mainFileWriter) {
					mainFileWriter = new FileWriter(TEMP_FILE_PATH
							+ "//TRANS_INSURANCE.txt");
					mainBuffWriter = new BufferedWriter(mainFileWriter);
				}
				mainBuffWriter.write("10"
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);

				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getInsurId())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getTransType())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getUnitId())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getUnitName())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getShipId())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getShipName())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getGoodBillCode())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getTrainNo())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);

				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getTransPlanId())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getOrderNum())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getCompanyCode())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getPolicyHolder())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getInsuredMan())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);

				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getLadingSpotName())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getDestSpotName()
								+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE));
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getProductTypeName())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil.longToString(insuranceBill
						.getTotalCount())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.doubleToString(insuranceBill.getTotalGrossWeight())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.doubleToString(insuranceBill.getTotalNetWeight())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);

				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(DateUtils.toString(insuranceBill
								.getBeginTransDate(), "yyyyMMdd"))
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.doubleToString(insuranceBill.getInsuranceTotalNum())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getCheckMan())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getSignMan())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil
						.formatNullToBlank(insuranceBill.getAgency())
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);
				mainBuffWriter.write(FileReaderUtil.formatNullToBlank(DateUtils
						.toString(insuranceBill.getCreateDate(), "yyyyMMdd"))
						+ FileReaderUtil.FILE_FORMAT_TOKEN_WAVE);

				mainBuffWriter.newLine();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (mainBuffWriter != null) {
				mainBuffWriter.flush();
				mainBuffWriter.close();
			}
			if (mainFileWriter != null) {
				mainFileWriter.close();
			}
		}

		/**
		 * ѹ���ļ���Ϊ.zip�ļ�;
		 */
		String zipPath = FTP_LOCAL_FILE_PATH + ZIP_FILE_NAME;
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(zipPath));
		ZipOutputStream out = new ZipOutputStream(bos);

		File f = new File(TEMP_FILE_PATH);
		put(f, out, "");
		out.close();

		return zipPath;
	}

	/**
	 * ѹ���ļ����ļ���;
	 * 
	 * @param f
	 * @param out
	 * @param dir
	 * @throws IOException
	 */
	private void put(File f, ZipOutputStream out, String dir)
			throws IOException {
		int BUFFER = 2048;
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			dir = dir + (dir.length() == 0 ? "" : "/") + f.getName();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				put(file, out, dir);
			}
		} else {
			byte[] data = new byte[BUFFER];
			FileInputStream fi = new FileInputStream(f);
			BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
			dir = dir.length() == 0 ? "" : dir + "/" + f.getName();
			ZipEntry entry = new ZipEntry(dir);
			out.putNextEntry(entry);
			int count = 0;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
				out.flush();
			}
			origin.close();
		}

	}

	/**
	 * ���ͱ�������;
	 * 
	 * @param id
	 * @param operate
	 * @param unitId
	 * @throws Exception
	 */
	public void sendMessageInsuranceBill(Long id, String operate, String unitId)
			throws Exception {
		boolean status = messageService.judgeSendMsg(unitId);
		if (status) {
			TplInsuranceBill insuranceBill = (TplInsuranceBill) insuranceBillDao
					.queryById(id, TplInsuranceBill.class);
			if (null == insuranceBill) {
				throw new Exception("Ҫ���͵ĳ�����ļ�¼Ϊ�� id="
						+ insuranceBill.getId().toString());
			}
			insuranceBill.setOpFlag(operate);
			TplMessageInsuranceBill tplMessageInsuranceBill = new TplMessageInsuranceBill();
			BeanUtils.copyProperties(tplMessageInsuranceBill, insuranceBill);
			{
				tplMessageInsuranceBill.setOpFlag(insuranceBill.getOpFlag());
				tplMessageInsuranceBill.setId(insuranceBill.getId());
				tplMessageInsuranceBill.setInvoiceSysId(insuranceBill.getInvoiceSysId());
				tplMessageInsuranceBill.setTotalCount(insuranceBill
						.getTotalCount());
				tplMessageInsuranceBill.setTotalGrossWeight(StringUtil
						.getBigDecimalNumber(insuranceBill
								.getTotalGrossWeight()));
				tplMessageInsuranceBill
						.setTotalNetWeight(StringUtil
								.getBigDecimalNumber(insuranceBill
										.getTotalNetWeight()));
				tplMessageInsuranceBill.setUnitPrice(StringUtil
						.getBigDecimalNumber(insuranceBill.getUnitPrice()));
				tplMessageInsuranceBill.setIntegrateInsurance(StringUtil
						.getBigDecimalNumber(insuranceBill
								.getIntegrateInsurance()));
				tplMessageInsuranceBill
						.setBasisInsurance(StringUtil
								.getBigDecimalNumber(insuranceBill
										.getBasisInsurance()));
				tplMessageInsuranceBill.setInsuranceTotalNum(StringUtil
						.getBigDecimalNumber(insuranceBill
								.getInsuranceTotalNum()));
				tplMessageInsuranceBill.setManuId(insuranceBill.getManuId());
				tplMessageInsuranceBill.setGoodBillId(insuranceBill.getGoodBillId());
				if(SystemConfigUtil.BAOSTEEL_BGSQ.equals(unitId)){
					TplTransPlan tplTransPlan = transPlanDao.queryTransPlanByTransPlanId(tplMessageInsuranceBill.getTransPlanId(), unitId);
					tplMessageInsuranceBill.setTransPlanId(tplTransPlan.getPickNum());
				}
			}
			// ���͵���
			messageService.sendInsurance(tplMessageInsuranceBill);
		}
	}

	/**
	 * ����ӡ��˰;
	 * 
	 * @param totalMoney
	 * @return
	 * @throws Exception
	 */
	// private Double addStampTax(Double totalMoney) throws Exception {
	// // �������˷�(�����˷ѵ���*����)*0.0005(ӡ��˰)
	// double money = this.filterDouble(totalMoney).doubleValue();
	// totalMoney = new Double(money * (1 + 0.0005));
	// return totalMoney;
	// }
	private Double filterDouble(Double arg) throws Exception {
		if (arg != null)
			return arg;
		return new Double(0);
	}

	private Long filterLong(Long arg) throws Exception {
		if (arg != null)
			return arg;
		return new Long(0);
	}

	public TransPlanDao getTransPlanDao() {
		return transPlanDao;
	}

	public void setTransPlanDao(TransPlanDao transPlanDao) {
		this.transPlanDao = transPlanDao;
	}

	public TransPackDao getTransPackDao() {
		return transPackDao;
	}

	public void setTransPackDao(TransPackDao transPackDao) {
		this.transPackDao = transPackDao;
	}

	public RealFeeDao getRealFeeDao() {
		return realFeeDao;
	}

	public void setRealFeeDao(RealFeeDao realFeeDao) {
		this.realFeeDao = realFeeDao;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public InsuranceBillDao getInsuranceBillDao() {
		return insuranceBillDao;
	}

	public void setInsuranceBillDao(InsuranceBillDao insuranceBillDao) {
		this.insuranceBillDao = insuranceBillDao;
	}

	public TransSeqDao getTransSeqDao() {
		return transSeqDao;
	}

	public void setTransSeqDao(TransSeqDao transSeqDao) {
		this.transSeqDao = transSeqDao;
	}

	public MessageLogDao getMessageLogDao() {
		return messageLogDao;
	}


	public void setMessageLogDao(MessageLogDao messageLogDao) {
		this.messageLogDao = messageLogDao;
	}


	/**
	 * {@inheritDoc}
	 */
	public TplInsuranceBill queryInsuranceBill(Long seqId, String transPlanId,
			String orderNum, String unitId) throws Exception {
		return insuranceBillDao.queryInsuranceBill(seqId, transPlanId,
				orderNum, unitId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean getOnlyInsuranceCode(String providerId,String startCode,String endCode) throws Exception{
		return insuranceBillDao.getOnlyInsuranceCode(providerId, startCode, endCode);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Map queryTotalInsuranceInfo(InsuranceBillSearchModel searchModel) throws Exception{
		return insuranceBillDao.queryTotalInsuranceInfo(searchModel);
	}
	public Map queryTransPlanWithArgsByPageTotal(TransPlanSearchModel searchModel)
	throws Exception{
		return transPlanDao.queryTransPlanWithArgsByPageTotal(searchModel);
	};


	public TplTransPlan queryTplTransPlan(String transPlanId) throws Exception {
		// TODO Auto-generated method stub
		return insuranceBillDao.queryTplTransPlan(transPlanId);
	}

	public List queryPlanFeeListWithArgs(PlanFeeSearchModel searchModel)throws Exception {
		return planfeedao.queryPlanFeeListWithArgsByPage(searchModel);
	}

	public double queryBasePriceList(String manuId, String productType, Date planInDate, String consineeCode,String packOrderType) throws Exception {
		// TODO Auto-generated method stub
		return insuranceBillDao.queryBasePriceList(manuId,productType, planInDate, consineeCode,packOrderType);
	}

	public List queryProductInfoOfOrder(Long planId, String orderNum, String packStatus) throws Exception {
		return transPackDao.queryProductInfoOfOrder(planId, orderNum, packStatus);
	}

	public String queryPackOrderType(String transPlanId, String orderNum) throws Exception {
		// TODO Auto-generated method stub
		return insuranceBillDao.queryPackOrderType(transPlanId, orderNum);
	}


	public ProcFlagService getProcFlagService() {
		return procFlagService;
	}


	public void setProcFlagService(ProcFlagService procFlagService) {
		this.procFlagService = procFlagService;
	}


	public PlanFeeDao getPlanfeedao() {
		return planfeedao;
	}


	public void setPlanfeedao(PlanFeeDao planfeedao) {
		this.planfeedao = planfeedao;
	}

	/**ranqiguang add start 20180913**/
	/****
	 * �޸ı��շ�
	 */
	public String updateTplInsuranceBillTrain(String[] idsels, String[] insuranceAmounts, String[] ids)
			throws Exception {
		String message ="";
		for(int i=0; i<idsels.length; i++){
			for(int j=0; j<ids.length; j++){
				if(ids[j].equals(idsels[i])){
					
					if(null==idsels[i] || "".equals(idsels[i])){
						message = "���շ�idΪ�գ����ܽ����޸ģ�";
						return message;
					}
					String idsel=idsels[i];
					TplInsuranceBill insuranceBill = (TplInsuranceBill)insuranceBillDao.queryById(Long.valueOf(idsel),TplInsuranceBill.class);
					if(insuranceBillDao.getInvoicePayStatusIsSended(insuranceBill)){
						message = "�ñ�����Ӧ��Ʊ�Ѿ����͸������룬�����޸�!";
						return message;
					}
					
					String insuranceAmount=insuranceAmounts[j];
					Double insuranceAmountUp;
					if(insuranceAmount==null || "".equals(insuranceAmount)){
						insuranceAmountUp = new Double(0);
					}else{
						insuranceAmountUp = Double.valueOf(insuranceAmount);
					}
					List listRel = realFeeDao.insuranceQueryRealFeeList(insuranceBill.getShipSeqId(), insuranceBill.getTransPlanId(), insuranceBill.getOrderNum(),insuranceBill.getUnitId());
					if(listRel != null && listRel.size()>0){
						
						for(int m=0; m<listRel.size(); m++){
							TplRealFee realFee = (TplRealFee)listRel.get(m);
							Long invoiceSysId = realFee.getInvoiceSysId();//��¼��Ʊid
							Double totalInsurMoney = realFee.getTotalInsurMoney();//��¼���ñ���
							if(totalInsurMoney==null)  totalInsurMoney = new Double(0);
							//3.���·���ʵ��
							insuranceBillDao.updateRealFeeInsurSetUpd(insuranceBill, totalInsurMoney, insuranceAmountUp);
					        //4.���·�Ʊ
							insuranceBillDao.updateInvoiceInsurSetUpd(invoiceSysId, totalInsurMoney, insuranceAmountUp);
						}
					}
					insuranceBillDao.updateTplInsuranceBillUpd(insuranceBill, insuranceAmountUp);
				}
			}
		}
		return message;
		
	}
	/**ranqiguang add end 20180913**/
	
	/**ranqiguang add start 20181107**/
	/***
	 * �����ϲ�
	 */
	public String mergeInsuranceBillTrain(String[] idsels, String creatId) throws Exception {
		String message="";
		List results;
		Long invoceSysId;
		if(null !=idsels && idsels.length>0){
			results=insuranceBillDao.checkMergeInsurance(idsels);
			if(null!=results && results.size()>0){
				TplInsuranceBill insurBill = (TplInsuranceBill)results.get(0);
				if(null!=insurBill){
					invoceSysId = insurBill.getInvoiceSysId();
					if(null ==invoceSysId){
						message="��ѡ����û���ɷ�Ʊ��";
						return message;
					}
					for(int a=0; a<results.size(); a++){
						TplInsuranceBill perInsurBill = (TplInsuranceBill)results.get(a);
						if(!invoceSysId.equals(perInsurBill.getInvoiceSysId())){
							message="��ѡ��������ͬһ�ŷ�Ʊ��";
							return message;
						}
						if("1".equals(perInsurBill.getIsInsuranceM())){
							message="��ѡ���������Ѻϲ�������";
							return message;
						}
					}
					if(results.size()<=1){
						message="��ѡ�������ںϲ���Χ�ڣ�";
						return message;
					}
					List argRes = insuranceBillDao.getSumInsuranceAmount(insurBill.getInvoiceSysId());
					TplInsuranceBill argInsurBill = (TplInsuranceBill)argRes.get(0);
					Double sumInsurAmount = argInsurBill.getInsuranceTotalNumAdjust();
					Double sumAmountRes = this.filterDouble(sumInsurAmount);
					TplInsuranceBill mergeInsurBill = new TplInsuranceBill();
					{
					mergeInsurBill.setInsuranceTotalNum(sumAmountRes);
					mergeInsurBill.setInsuranceTotalNumAdjust(sumAmountRes);
					mergeInsurBill.setOrderNum(insurBill.getOrderNum());
					mergeInsurBill.setTransPlanId(null);
					mergeInsurBill.setTotalGrossWeight(argInsurBill.getTotalGrossWeight());
					mergeInsurBill.setTotalNetWeight(argInsurBill.getTotalNetWeight());
					mergeInsurBill.setTotalCount(argInsurBill.getTotalCount());
					mergeInsurBill.setIsInsuranceM(SystemConstants.FEE_STOCK_SETTLEMENT_STATUS_YES);
					mergeInsurBill.setCreateDate(new Date());
					mergeInsurBill.setOperateDate(new Date());
					mergeInsurBill.setTransType(insurBill.getTransType());
					mergeInsurBill.setUnitId(insurBill.getUnitId());
					mergeInsurBill.setUnitName(insurBill.getUnitName());
					mergeInsurBill.setGoodBillId(insurBill.getGoodBillId());
					mergeInsurBill.setGoodBillCode(insurBill.getGoodBillCode());
					mergeInsurBill.setTrainNo(insurBill.getTrainNo());
					mergeInsurBill.setCompanyCode(insurBill.getCompanyCode());
					mergeInsurBill.setPolicyHolder(insurBill.getPolicyHolder());
					mergeInsurBill.setInsuredMan(insurBill.getInsuredMan());
					mergeInsurBill.setConsigneeCode(insurBill.getConsigneeCode());
					mergeInsurBill.setConsigneeName(insurBill.getConsigneeName());
					mergeInsurBill.setLadingSpot(insurBill.getLadingSpot());
					mergeInsurBill.setLadingSpotName(insurBill.getLadingSpotName());
					mergeInsurBill.setDestSpot(insurBill.getDestSpot());
					mergeInsurBill.setDestSpotName(insurBill.getDestSpotName());
					mergeInsurBill.setProductType(insurBill.getProductType());
					mergeInsurBill.setProductTypeName(insurBill.getProductTypeName());
					mergeInsurBill.setBeginTransDate(insurBill.getBeginTransDate());
					mergeInsurBill.setUnitPrice(insurBill.getUnitPrice());
					mergeInsurBill.setIntegrateInsurance(insurBill.getIntegrateInsurance());
					mergeInsurBill.setBasisInsurance(insurBill.getBasisInsurance());
					mergeInsurBill.setProviderId(insurBill.getProviderId());
					mergeInsurBill.setRewriteStatus(insurBill.getRewriteStatus());
					mergeInsurBill.setCreateId(creatId);
					mergeInsurBill.setInvoiceSysId(insurBill.getInvoiceSysId());
					System.out.print("�ϲ��ɹ���"+mergeInsurBill);
					this.insertTplInsuranceBill(mergeInsurBill);
					System.out.print("�ϲ��ɹ���");
					}
				}
			}
		}
		return message;
	}
	

	/***
	 * ajax ��ȡ�ӱ���
	 */
	public List getSubInsuranceBill(String id) throws Exception {
		TplInsuranceBill bill =  (TplInsuranceBill)insuranceBillDao.queryById(Long.valueOf(id), TplInsuranceBill.class);
		List result =insuranceBillDao.getSubInsuranceBillsByInvoiceId(bill.getInvoiceSysId());
		return result;
	}
	/**ranqiguang add end 20181107**/
}
