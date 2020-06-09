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
 * <b>文件名</b>：InsuranceBillServiceImpl.java
 * <p>
 * <b>创建日期</b>: 2007-06-07
 * </p>
 * <p>
 * <b>功能描述</b>：保单管理Service实现类
 * </p>
 * <p>
 * <b>修改历史</b>:
 * </p>
 * 
 * @author 付强
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
					return new String("该计划所挂船批已经开票，不能删除保单!");
				}else if (SystemConstants.TRANS_SEQ_INVOICE_STATUS_FEE.equals(transSeq.getInvoiceStatus())) {
					return new String("该计划所挂船批已经生成费用，不能删除保单!");
				}
			}else {
				return new String("该计划所挂船批不是水运船批!");
			}				
			/*if(transSeq != null && "1".equals(transSeq.getStatus())){
				return new String("该计划所挂船批已经离港，不能删除保单!");
			}
			if(transSeq != null && "2".equals(transSeq.getStatus())){
				return new String("该计划所挂船批已经离港，不能删除保单!");
			}*/
			TplTransPlan tplTransPlan = transPlanDao
					.queryTransPlanByTransPlanId(
							insuranceBill.getTransPlanId(), insuranceBill
									.getUnitId());
			
			if(tplTransPlan==null){
				TplMessageLog tplMessageLog = new TplMessageLog();
				tplMessageLog.setErrTitle("删除运输作业计划已红冲的保单");
				tplMessageLog.setErrContent("运输作业计划号："+insuranceBill.getTransPlanId());
				tplMessageLog.setCreateDate(new Date());
				tplMessageLog.setOperMemo("删除保单操作");
				messageLogDao.insertMessageLog(tplMessageLog);

			}
			else{
			TplRealFee realFee = realFeeDao.queryRealFee(transSeq.getId(),
					tplTransPlan.getTransPlanId(), insuranceBill.getOrderNum(),
					insuranceBill.getUnitId());

			// 如果该计划下合同已开票，就不允许再删除保单
			if (realFee != null
					&& realFee.getInvoiceStatus().equals(
							SystemConstants.FEE_STOCK_INVOICE_STATUS_YES)) {
				return new String("该计划已经开票,不能删除保单!");
			}


			// 把作业计划修改为未开保单状态;
			if (tplTransPlan != null) {
				tplTransPlan
						.setInsureStatus(SystemConstants.TRANS_PLAN_INSURANCE_UNMAKE);
				transPlanDao.update(tplTransPlan, TplTransPlan.class);
			}
			

			// 先更新运输作业费用表中和保单相关的费用和状态值;
			this.updateRealFeeWhileDelInsurance(insuranceBill.getShipSeqId(),
					insuranceBill.getTransPlanId(),
					insuranceBill.getOrderNum(), insuranceBill.getUnitId());
			

			// 并发送删除保单电文;
			// this.sendMessageInsuranceBill(Long.valueOf(ids[i]),
			// SystemConstants.FEE_INSUR_OPERATE_DEL, tplTransPlan
			// .getUnitId());
			}
		}
		// 最后再删除保单记录;
		insuranceBillDao.delete(idArray, TplInsuranceBill.class);
		return "删除成功！";
	}
	
	
/**
 * 铁运保单删除
 * notes：
 *      1.查出要删除保单记录
 *      2.发票已经发送付款申请，不能删除保单
 *      3.汇总生成发票的费用实绩，形成每个发票对应的费用是多少的hashmap
 *		3.根据车皮号，计划号，合同号更新出费用实绩，具体如下：
 *		  a.将保单id置为空
 *        b.保单状态置为未开保单
 *        c.保单金额清空
 *        d.费用总金额 = 费用总金额 -保单金额
 *		4.根据发票id更新发票信息，具体如下：
 *		  a.将保费金额清零
 *		  b.付款金额= 付款金额-保费金额
 * @author zf
 */
	public String deleteTplInsuranceBillTrain(String[] ids) throws Exception {
		Long[] idArray = new Long[ids.length];
		for (int i = 0; i < ids.length; i++) {

			Long id = Long.valueOf(ids[i]);
			idArray[i] = id;//记录每次被删除保单id
			//1.查出要删除保单记录
			TplInsuranceBill insuranceBill = this.queryTplInsuranceBillById(id);
			//2.如果发票已经发送付款申请，则不能删除保单
			if(insuranceBillDao.getInvoicePayStatusIsSended(insuranceBill))
				return new String("该保单对应发票已经发送付款申请，不能删除!");
			//3.保单与费用一一对应，因为生成费用的时候，开票人只有一个
			TplRealFee realFee = realFeeDao.queryRealFee(insuranceBill.getShipSeqId(), insuranceBill.getTransPlanId(), insuranceBill.getOrderNum(),insuranceBill.getUnitId());
			Long invoiceSysId = realFee.getInvoiceSysId();//记录发票id
			Double totalInsurMoney = realFee.getTotalInsurMoney();//记录费用保费
			if(totalInsurMoney==null)  totalInsurMoney = new Double(0);
			
			//3.更新费用实绩
			insuranceBillDao.updateRealFeeInsurSetZero(insuranceBill,totalInsurMoney);
            //4.更新发票
			insuranceBillDao.updateInvoiceInsurSetZero(invoiceSysId,totalInsurMoney);
		}
		// 最后再删除保单记录;
		insuranceBillDao.delete(idArray, TplInsuranceBill.class);
		return null;
	}
		
	

	/**
	 * 当删除保单后，需要更新作业实绩表的保费单价、应付费用和相关状态;
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
		// 删除保单:从实绩费用中减去保单费用，修改INSUR_STATUS为未生成保单状态。
		tplRealFee.setInsurId("");
		tplRealFee.setTotalInsurMoney(new Double(0));
		Double totalMoney = tplRealFee.getTotalMoney();
		// 如果运输类型是水运，还要加入印花税;
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
					return new String("该合同保单已经生成,不能重复生成!");
				}

				TplTransSeq transSeq = (TplTransSeq) transSeqDao.queryById(
						seqId, TplTransSeq.class);
				if (null == transSeq) {
					return new String("该计划船批已经被删除!");
				}
				if (transSeq != null
						&& transSeq.getInvoiceStatus().equals(
								SystemConstants.FEE_STOCK_INVOICE_STATUS_YES)) {
					return new String("计划"+tplTransPlan.getTransPlanId()+"所挂船批已经开票，不能再生成保单!");
				}

				TplRealFee realFee = realFeeDao.queryRealFee(transSeq.getId(),
						tplTransPlan.getTransPlanId(), orderNum, tplTransPlan
								.getUnitId());
				// 如果该计划下合同已开票，就不允许再生成保单;
				if (realFee != null
						&& realFee.getInvoiceStatus().equals(
								SystemConstants.FEE_STOCK_INVOICE_STATUS_YES)) {
					return new String("计划"+tplTransPlan.getTransPlanId()+"已经开票,不能生成保单!");
				}
				
				//根据作业计划,合同号查询planfee, 承运商id 查找planFee
				List planFeeList = realFeeDao.queryPlanFee(tplTransPlan.getTransPlanId(), orderNum, tplTransPlan.getProviderId());
				TplInsuranceBill insuranceBill = new TplInsuranceBill();
				{   
					insuranceBill.setInsurId("");
					//给保单增加要开的发票类型,add by llk 2012-3-19,
					if(planFeeList != null && planFeeList.size()>0){
						TplPlanFee planFee = (TplPlanFee)planFeeList.get(0);
						insuranceBill.setInvoiceType(planFee.getInvoiceType());
						insuranceBill.setPolicyHolder(planFee.getInvoiceTitleName());// 保单人：发票抬头单位名称
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
					//保单一律按净重计算;
					double weight = filterDouble((Double) order[12])
							.doubleValue();
					double totalInsurMoney = unitPrice * weight; // 保单总金额=保费单价*总净重;
					if (totalInsurMoney <= 0) {
						if (msgInfo != null) {
							msgInfo += "," + order[4];
						} else {
							msgInfo = "合同" + order[4];
						}
						continue;
					}
					insuranceBill.setIntegrateInsurance(new Double(0));
					insuranceBill.setBasisInsurance(new Double(0));
					totalInsurMoney=StringUtil.round(totalInsurMoney,2);
					insuranceBill.setInsuranceTotalNum(new Double(totalInsurMoney));
					
				    //在生成保单的时候,默认的将输入校对值设置成系统计算值,之后增值税的可以进行回写 modify by llk 2012/3/20
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

				// 加入保单后要求更新作业实绩表的相关费用和各项状态;
				this.updateRealFeeWhileAddInsurance(insuranceBill);
				// 并发送保单信息电文;
				// 现改为在发送发票电文时一同发送,在此不再发送.
				// this.sendMessageInsuranceBill(insuranceBill.getId(),
				// SystemConstants.FEE_INSUR_OPERATE_ADD, tplTransPlan
				// .getUnitId());
				// 计划已生成保单;
				tplTransPlan
						.setInsureStatus(SystemConstants.TRANS_PLAN_INSURANCE_MAKED);
				transPlanDao.update(tplTransPlan, TplTransPlan.class);
			}
			if (msgInfo != null) {
				msgInfo += "保单费用为0,不能开具保单!";
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
				insuranceBill.getInsuranceTotalNum()).doubleValue();// 总的保险费用;
		double totalMoney = filterDouble(tplRealFee.getTotalMoney())
				.doubleValue(); // 不包含保险费的总金额;
		tplRealFee.setTotalInsurMoney(new Double(totalInsurMoney));

		// 如果运输类型是水运，还要加入印花税;
		// if
		// (tplRealFee.getTransType().equals(SystemConstants.TRANS_TYPE_SHIP)) {
		// tplRealFee.setPayMoney(new Double(this.addStampTax(
		// new Double(totalMoney)).doubleValue()
		// + totalInsurMoney));
		// } else {
		double payMoney = totalMoney + totalInsurMoney; // 应付金额=包含保险费的总金额;
		payMoney=StringUtil.round(payMoney,2);
		tplRealFee.setPayMoney(new Double(payMoney));
		// }

		tplRealFee.setInsurStatus(SystemConstants.FEE_INSUR_STATUS_MAKEED); // 已生成保单;
		realFeeDao.update(tplRealFee, TplRealFee.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Map getPreviewInsuranceBill(String[] ids,String[] shipIds) throws Exception {
		Map resultSet = new HashMap();
		BaseResultModel brm = new BaseResultModel();
		List list = new ArrayList();
		long sumOfCount = 0; // 捆包个数;
		double sumOfGrossWeight = 0.0; // 总毛重;
		double sumOfNetWeight = 0.0; // 总净重;
		double sumOfInsurMoney = 0.0; // 保险金额之和;
		
		if (ids != null && ids.length > 0) {
			
			//验证船批是否刷新管坯类数据
			if(shipIds != null && shipIds.length > 0){
				for (int i = 0; i < shipIds.length; i++) {
					if(shipIds[i] != null && shipIds[i] != ""){
						Long seqId = Long.valueOf(shipIds[i]);
						boolean virtualFlag = transPackDao.getVirtualPackFlag(seqId);
		//				boolean InnerWarehouseFlag = transPackDao.getCheckInnerWarehouse(seqId);
						if (virtualFlag) {
							throw new Exception("msg:该计划对应的码单未生成，请稍后操作或联系管理员! 提示：系统将在每天7:00，12:30自动刷新");
						}
						boolean tubeFlag=procFlagService.isTubeProcFlag(seqId,"transType");
						if(tubeFlag){
							throw new Exception("msg:该车船装载有未经刷新的管坯类合同捆包，请刷新完成以后再操作! 提示：系统将在每天7:00，12:30自动刷新");
						}
					}
				}
			}
			
			for (int i = 0; i < ids.length; i++) {
				Long planId = Long.valueOf(ids[i]); // 运输作业计划ID;
				TplTransPlan tplTransPlan = (TplTransPlan) transPlanDao
						.queryById(planId, TplTransPlan.class);
				List result = transPackDao.queryOrderInfoByPlanId(planId,
						SystemConstants.TRANS_PACK_STATUS_LOADING); // 查询出一个作业计划包含的合同信息列表;
				if (result != null && result.size() > 0) {

					// 计划下已生成保单的合同号列表;
					List orderList = insuranceBillDao
							.queryOrderNumList(tplTransPlan.getSeqId(),
									tplTransPlan.getTransPlanId(), tplTransPlan
											.getUnitId());

					for (int j = 0; j < result.size(); j++) {
						Object[] items = (Object[]) result.get(j); // 取出一个合同;
						// 标志位:判断计划下的合同是否已生成保单;false:未生成;true:已生成
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
						// 如果该合同保单已经存在,就把该合同从合同预览中去除;
						if (flag) {
							continue;
						}
						
						/** 根据计划合同查找费用计划，
					      *	 若无，报错提醒缺少某计划某合同计划费用
					      *	 若有，根据计划合同作为参数，进行保单开票规则判断
						 **/
						//add by wq 20110523 begin 
						PlanFeeSearchModel searchModel = new PlanFeeSearchModel();
						searchModel.setPlanId(tplTransPlan.getTransPlanId());
						searchModel.setUnitId(tplTransPlan.getUnitId());
						searchModel.setOrderNum((String)items[4]);
						List planFeeList = planfeedao.queryPlanFeeListWithArgs(searchModel);
						if (null == planFeeList || planFeeList.size()<1) {
							logger.info("msg:运输作业计划: " + tplTransPlan.getTransPlanId() + "缺少费率信息,不能生成费用!");
							throw new Exception("msg:运输作业计划缺少费率信息,不能生成费用!");
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
	

						//保单一律按净重计算;
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
						
						
						if (null == planFee.getInsuranceFlag() || "".equals(planFee.getInsuranceFlag())) {// 保单改造前模式，解决历史数据不兼容问题
							//获取合同性质
//							String orderNum = null == items[4]?"":(String)items[4];
//							String transPlanId = null == tplTransPlan.getTransPlanId()?"":(String)tplTransPlan.getTransPlanId();
//							String packOrderType = insuranceBillDao.queryPackOrderType(transPlanId,orderNum);
							
							//产品基价
//							String productType = null == items[6]? "":(String)items[6];
//							Date planInDate = tplTransPlan.getModifyDate();
//							String manuId = tplTransPlan.getManuId();
//							String consineeCode = tplTransPlan.getConsineeCode();
							
							//String manuId, String productType, Date planInDate, String consineeCode
//							double insurBasePrice =  insuranceBillDao.queryBasePriceList(manuId,productType, planInDate, consineeCode,packOrderType);
							
							/* 2011-07-21 16:55-- 调整为0.035% 2011-07-07 17:48---2011-07-21 16:55 调整为0.03486% 2011-07-07 17:48以前 除梅钢费率为 0.00065 外，其他费率均为 0.00042**/
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
						} else if("10".equalsIgnoreCase(planFee.getInsuranceFlag())) {// 保单改造后，沿用新的模式
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
			//如果startInsurBillCode的宽度>insurBillCode的宽度
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
			// 更新作业实绩表的表单编号,建立关联;
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
			throw new Exception("msg:请您选择需要下载的保单数据。");
		}
		if (ids!=null && ids.length>30){
			throw new Exception("msg:您最多只能下载30条保单数据。");
		}
		List insurList=new ArrayList();
		HSSFWorkbook wb = new HSSFWorkbook(); // 建立新HSSFWorkbook对象;
		HSSFSheet sheet = wb.createSheet("InsuranceSheet1"); // 建立新的sheet对象
		HSSFRow titleRow = sheet.createRow(0); // 建立标题行
		String[] headers = { "保单编号", "运输类型", "组织机构代码", "组织机构名称", "船批号", "船名",
				"货票编号", "车皮号", "作业计划号", "合同号", "投保人", "被保险人", "始发站名称",
				"终到站名称", "品种名称", "件数", "毛重","净重", "启运日期", "保险金额", "保险费(计算值)","保险费(票据值)","保单生成日期", };
		for (int i = 0; i < headers.length; i++) {
			// 创建一个记录
			HSSFCell csCell = titleRow.createCell((short) i);
			// 设置cell编码解决中文高位字节截断
			csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
			// 放入标题
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
			TplTransPlan transPlan = insuranceBillDao.queryTplTransPlan(transPlanId);// 作业计划
			// 取得费用计划
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
			if (null == planFee.getInsuranceFlag() || "".equals(planFee.getInsuranceFlag())) {// 保单改造前模式，解决历史数据不兼容问题
				System.out.println("22222222222222222222222222................................");
				String manuId=(String)item[size-1];
				
				/* 2011-07-21 16:55-- 调整为0.035% 2011-07-07 17:48---2011-07-21 16:55 调整为0.03486% 2011-07-07 17:48以前 除梅钢费率为 0.00065 外，其他费率均为 0.00042**/
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
			} else if("10".equalsIgnoreCase(planFee.getInsuranceFlag())) {// 保单改造后，沿用新的模式
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
				// 设置cell编码解决中文高位字节截断
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
		 * 压缩文件夹为.zip文件;
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
	 * 压缩文件或文件夹;
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
	 * 发送保单电文;
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
				throw new Exception("要发送的出库电文记录为空 id="
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
			// 发送电文
			messageService.sendInsurance(tplMessageInsuranceBill);
		}
	}

	/**
	 * 加入印花税;
	 * 
	 * @param totalMoney
	 * @return
	 * @throws Exception
	 */
	// private Double addStampTax(Double totalMoney) throws Exception {
	// // 货物总运费(货物运费单价*重量)*0.0005(印花税)
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
	 * 修改保险费
	 */
	public String updateTplInsuranceBillTrain(String[] idsels, String[] insuranceAmounts, String[] ids)
			throws Exception {
		String message ="";
		for(int i=0; i<idsels.length; i++){
			for(int j=0; j<ids.length; j++){
				if(ids[j].equals(idsels[i])){
					
					if(null==idsels[i] || "".equals(idsels[i])){
						message = "保险费id为空，不能进行修改！";
						return message;
					}
					String idsel=idsels[i];
					TplInsuranceBill insuranceBill = (TplInsuranceBill)insuranceBillDao.queryById(Long.valueOf(idsel),TplInsuranceBill.class);
					if(insuranceBillDao.getInvoicePayStatusIsSended(insuranceBill)){
						message = "该保单对应发票已经发送付款申请，不能修改!";
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
							Long invoiceSysId = realFee.getInvoiceSysId();//记录发票id
							Double totalInsurMoney = realFee.getTotalInsurMoney();//记录费用保费
							if(totalInsurMoney==null)  totalInsurMoney = new Double(0);
							//3.更新费用实绩
							insuranceBillDao.updateRealFeeInsurSetUpd(insuranceBill, totalInsurMoney, insuranceAmountUp);
					        //4.更新发票
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
	 * 保单合并
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
						message="所选保单没生成发票！";
						return message;
					}
					for(int a=0; a<results.size(); a++){
						TplInsuranceBill perInsurBill = (TplInsuranceBill)results.get(a);
						if(!invoceSysId.equals(perInsurBill.getInvoiceSysId())){
							message="所选保单不是同一张发票！";
							return message;
						}
						if("1".equals(perInsurBill.getIsInsuranceM())){
							message="所选保单中有已合并保单！";
							return message;
						}
					}
					if(results.size()<=1){
						message="所选保单不在合并范围内！";
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
					System.out.print("合并成功！"+mergeInsurBill);
					this.insertTplInsuranceBill(mergeInsurBill);
					System.out.print("合并成功！");
					}
				}
			}
		}
		return message;
	}
	

	/***
	 * ajax 获取子保单
	 */
	public List getSubInsuranceBill(String id) throws Exception {
		TplInsuranceBill bill =  (TplInsuranceBill)insuranceBillDao.queryById(Long.valueOf(id), TplInsuranceBill.class);
		List result =insuranceBillDao.getSubInsuranceBillsByInvoiceId(bill.getInvoiceSysId());
		return result;
	}
	/**ranqiguang add end 20181107**/
}
