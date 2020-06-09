package com.baosight.baosteel.bli.tpl.fee.service;

import com.baosight.core.spring.Service;
import com.baosight.baosteel.bli.tpl.model.TplInsuranceBill;
import com.baosight.baosteel.bli.tpl.model.TplTransPlan;
import com.baosight.baosteel.bli.tpl.transport.searchmodel.TransPlanSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.InsuranceBillSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.PlanFeeSearchModel;
import com.baosight.web.struts.BaseResultModel;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.*;

/**
 * <b>文件名</b>：InsuranceBillService.java
 * <p>
 * <b>创建日期</b>: 2007-06-07
 * </p>
 * <p>
 * <b>功能描述</b>：保单管理Service接口
 * </p>
 * <p>
 * <b>修改历史</b>:
 * </p>
 * 
 * @author 付强
 * @version 1.0
 */

public interface InsuranceBillService extends Service {

	/**
	 * 向保单表中加入一条记录
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void insertTplInsuranceBill(TplInsuranceBill model) throws Exception;

	/**
	 * 从保单表中删除一条记录
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public String deleteTplInsuranceBill(String[] ids) throws Exception;

	/**
	 * 铁运保单删除保费记录
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public String deleteTplInsuranceBillTrain(String[] ids) throws Exception;

	/**
	 * 更新保单表中的一条记录
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void updateTplInsuranceBill(TplInsuranceBill model) throws Exception;

	/**
	 * 取出保单表中的一条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TplInsuranceBill queryTplInsuranceBillById(Long id) throws Exception;

	/**
	 * 带条件无分页查询多条保单记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public BaseResultModel queryTplInsuranceBillWithArgs(
			InsuranceBillSearchModel searchModel) throws Exception;

	/**
	 * 带条件分页查询多条保单记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public BaseResultModel queryTplInsuranceBillWithArgsByPage(
			InsuranceBillSearchModel searchModel) throws Exception;

	/**
	 * 查询运输计划主表中可用于生成保单的计划
	 * 
	 * @param searchModel
	 *            查找作业计划的SearchModel
	 * @return
	 * @throws Exception
	 */
	public BaseResultModel queryTransPlanForMakeInsuranceBill(
			TransPlanSearchModel searchModel) throws Exception;

	/**
	 * 水运保单预览:分组汇总出计划下的所有合同，用于生成保单。
	 * 
	 * @param ids
	 *            运输作业计划ID
	 * @return
	 * @throws Exception
	 */
	public Map getPreviewInsuranceBill(String[] ids, String[] shipIds)
			throws Exception;

	/**
	 * 对指定的计划生成保单
	 * 
	 * @param transPlanIds
	 *            运输作业计划号数组
	 * @param orderNums
	 *            合同号数组
	 * @throws Exception
	 */
	public String insertInsuranceBill(String[] planIds, String[] orderNums)
			throws Exception;

	/**
	 * 回写保单号码
	 * 
	 * @param ids
	 *            保单ID数组
	 * @param startInsurBillCode
	 *            保单起始编号
	 * @throws Exception
	 */
	public void updateInsurBillCode(String[] ids, String startInsurBillCode)
			throws Exception;

	/**
	 * 加入保单时,给运输作业实绩添加保费单价和保险费用,更改应付费用=保险费用+不含保险的总金额,以及其它各种状态。
	 * 
	 * @param insuranceBill
	 * @throws Exception
	 */
	public void updateRealFeeWhileAddInsurance(TplInsuranceBill insuranceBill)
			throws Exception;

	/**
	 * 发送保单电文
	 * 
	 * @param id
	 *            保单id
	 * @param operate
	 *            控制字符
	 * @param unitId
	 *            货主代码
	 * @throws Exception
	 */

	public void sendMessageInsuranceBill(Long id, String operate, String unitId)
			throws Exception;

	/**
	 * 取出保单信息,放入Excel表格;
	 * 
	 * @param ids
	 *            保单ID数组
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook getDownLoadInsurances(
			InsuranceBillSearchModel searchModel) throws Exception;

	/**
	 * 生成要下载的保单文件,压缩到一个.zip文件当中;
	 * 
	 * @param planIds
	 * @return
	 * @throws Exception
	 */
	public String queryDownloadTransInsurance(String[] ids, String providerId)
			throws Exception;

	/**
	 * 查询保单信息;
	 * 
	 * @param seqId
	 * @param transPlanId
	 * @param orderNum
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	public TplInsuranceBill queryInsuranceBill(Long seqId, String transPlanId,
			String orderNum, String unit) throws Exception;

	/**
	 * 判断保单编号是否已被使用;
	 * 
	 * @param providerId
	 * @param startCode
	 * @param endCode
	 * @return
	 * @throws Exception
	 */
	public boolean getOnlyInsuranceCode(String providerId, String startCode,
			String endCode) throws Exception;

	/**
	 * 汇总保单毛重/净重/件数信息;
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public Map queryTotalInsuranceInfo(InsuranceBillSearchModel searchModel)
			throws Exception;

	/**
	 * 
	 * @param transPlanId
	 * @return
	 * @throws Exception
	 */
	public TplTransPlan queryTplTransPlan(String transPlanId) throws Exception;

	/**
	 * 费用计划 列表
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List queryPlanFeeListWithArgs(PlanFeeSearchModel searchModel)
			throws Exception;

	/**
	 * 
	 * @param manuId
	 * @param productType
	 * @param planInDate
	 * @param consineeCode
	 * @return
	 * @throws Exception
	 */
	public double queryBasePriceList(String manuId, String productType,
			Date planInDate, String consineeCode, String packOrderType)
			throws Exception;

	public List queryProductInfoOfOrder(Long planId, String orderNum,
			String packStatus) throws Exception;

	/**
	 * 
	 * @param transPlanId
	 * @param orderNum
	 * @return
	 * @throws Exception
	 */
	public String queryPackOrderType(String transPlanId, String orderNum)
			throws Exception;

	public Map queryTransPlanWithArgsByPageTotal(
			TransPlanSearchModel searchModel) throws Exception;

	/** ranqiguang add start 20180913 **/
	/**
	 * 修改保险费
	 * 
	 * @param id
	 * @param insuranceAmount
	 * @return
	 * @throws Exception
	 */
	public String updateTplInsuranceBillTrain(String[] idsels,
			String[] insuranceAmount, String[] ids) throws Exception;

	/** ranqiguang add start 20180913 **/

	/** ranqiguang add start 20181107 **/
	/***
	 * 保单合并
	 * 
	 * @param idsels
	 * @return
	 * @throws Exception
	 */
	public String mergeInsuranceBillTrain(String[] idsels, String creatId)
			throws Exception;

	/**
	 * 获取子保单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List getSubInsuranceBill(String id) throws Exception;
	/** ranqiguang add end 20181107 **/
}
