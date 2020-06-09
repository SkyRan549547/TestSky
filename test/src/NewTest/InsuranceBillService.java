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
 * <b>�ļ���</b>��InsuranceBillService.java
 * <p>
 * <b>��������</b>: 2007-06-07
 * </p>
 * <p>
 * <b>��������</b>����������Service�ӿ�
 * </p>
 * <p>
 * <b>�޸���ʷ</b>:
 * </p>
 * 
 * @author ��ǿ
 * @version 1.0
 */

public interface InsuranceBillService extends Service {

	/**
	 * �򱣵����м���һ����¼
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void insertTplInsuranceBill(TplInsuranceBill model) throws Exception;

	/**
	 * �ӱ�������ɾ��һ����¼
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public String deleteTplInsuranceBill(String[] ids) throws Exception;

	/**
	 * ���˱���ɾ�����Ѽ�¼
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public String deleteTplInsuranceBillTrain(String[] ids) throws Exception;

	/**
	 * ���±������е�һ����¼
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void updateTplInsuranceBill(TplInsuranceBill model) throws Exception;

	/**
	 * ȡ���������е�һ����¼
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TplInsuranceBill queryTplInsuranceBillById(Long id) throws Exception;

	/**
	 * �������޷�ҳ��ѯ����������¼
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public BaseResultModel queryTplInsuranceBillWithArgs(
			InsuranceBillSearchModel searchModel) throws Exception;

	/**
	 * ��������ҳ��ѯ����������¼
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public BaseResultModel queryTplInsuranceBillWithArgsByPage(
			InsuranceBillSearchModel searchModel) throws Exception;

	/**
	 * ��ѯ����ƻ������п��������ɱ����ļƻ�
	 * 
	 * @param searchModel
	 *            ������ҵ�ƻ���SearchModel
	 * @return
	 * @throws Exception
	 */
	public BaseResultModel queryTransPlanForMakeInsuranceBill(
			TransPlanSearchModel searchModel) throws Exception;

	/**
	 * ˮ�˱���Ԥ��:������ܳ��ƻ��µ����к�ͬ���������ɱ�����
	 * 
	 * @param ids
	 *            ������ҵ�ƻ�ID
	 * @return
	 * @throws Exception
	 */
	public Map getPreviewInsuranceBill(String[] ids, String[] shipIds)
			throws Exception;

	/**
	 * ��ָ���ļƻ����ɱ���
	 * 
	 * @param transPlanIds
	 *            ������ҵ�ƻ�������
	 * @param orderNums
	 *            ��ͬ������
	 * @throws Exception
	 */
	public String insertInsuranceBill(String[] planIds, String[] orderNums)
			throws Exception;

	/**
	 * ��д��������
	 * 
	 * @param ids
	 *            ����ID����
	 * @param startInsurBillCode
	 *            ������ʼ���
	 * @throws Exception
	 */
	public void updateInsurBillCode(String[] ids, String startInsurBillCode)
			throws Exception;

	/**
	 * ���뱣��ʱ,��������ҵʵ����ӱ��ѵ��ۺͱ��շ���,����Ӧ������=���շ���+�������յ��ܽ��,�Լ���������״̬��
	 * 
	 * @param insuranceBill
	 * @throws Exception
	 */
	public void updateRealFeeWhileAddInsurance(TplInsuranceBill insuranceBill)
			throws Exception;

	/**
	 * ���ͱ�������
	 * 
	 * @param id
	 *            ����id
	 * @param operate
	 *            �����ַ�
	 * @param unitId
	 *            ��������
	 * @throws Exception
	 */

	public void sendMessageInsuranceBill(Long id, String operate, String unitId)
			throws Exception;

	/**
	 * ȡ��������Ϣ,����Excel���;
	 * 
	 * @param ids
	 *            ����ID����
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook getDownLoadInsurances(
			InsuranceBillSearchModel searchModel) throws Exception;

	/**
	 * ����Ҫ���صı����ļ�,ѹ����һ��.zip�ļ�����;
	 * 
	 * @param planIds
	 * @return
	 * @throws Exception
	 */
	public String queryDownloadTransInsurance(String[] ids, String providerId)
			throws Exception;

	/**
	 * ��ѯ������Ϣ;
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
	 * �жϱ�������Ƿ��ѱ�ʹ��;
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
	 * ���ܱ���ë��/����/������Ϣ;
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
	 * ���üƻ� �б�
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
	 * �޸ı��շ�
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
	 * �����ϲ�
	 * 
	 * @param idsels
	 * @return
	 * @throws Exception
	 */
	public String mergeInsuranceBillTrain(String[] idsels, String creatId)
			throws Exception;

	/**
	 * ��ȡ�ӱ���
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List getSubInsuranceBill(String id) throws Exception;
	/** ranqiguang add end 20181107 **/
}
