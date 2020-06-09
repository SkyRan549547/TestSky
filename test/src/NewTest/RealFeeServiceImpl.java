package com.baosight.baosteel.bli.tpl.fee.service.impl;

import com.baosight.baosteel.bli.tpl.common.CodeUtil;
import com.baosight.baosteel.bli.tpl.common.DateUtils;
import com.baosight.baosteel.bli.tpl.common.StringUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConfigUtil;
import com.baosight.baosteel.bli.tpl.fee.dao.GoodBillArhDao;
import com.baosight.baosteel.bli.tpl.fee.dao.GoodBillDao;
import com.baosight.baosteel.bli.tpl.fee.dao.InvoiceDao;
import com.baosight.baosteel.bli.tpl.fee.dao.InvoiceTitleChangeDao;
import com.baosight.baosteel.bli.tpl.fee.dao.PlanFeeDao;
import com.baosight.baosteel.bli.tpl.fee.dao.RealFeeArhDao;
import com.baosight.baosteel.bli.tpl.fee.dao.RealFeeDao;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.GenInvoiceSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.InvoiceTitleChangeSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.InvoiceTitleUserSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.PlanFeeSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.RealFeeByMonthSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.RealFeeSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.StockInvoiceMgrSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.TplUnitContrastSearchModel;
import com.baosight.baosteel.bli.tpl.fee.service.InsuranceBillService;
import com.baosight.baosteel.bli.tpl.fee.service.RealFeeService;
import com.baosight.baosteel.bli.tpl.interfaces.service.MessageService;
import com.baosight.baosteel.bli.tpl.model.TplGoodBill;
import com.baosight.baosteel.bli.tpl.model.TplGoodBillArh;
import com.baosight.baosteel.bli.tpl.model.TplInsuranceBill;
import com.baosight.baosteel.bli.tpl.model.TplInvoice;
import com.baosight.baosteel.bli.tpl.model.TplInvoiceArh;
import com.baosight.baosteel.bli.tpl.model.TplInvoiceTitleUser;
import com.baosight.baosteel.bli.tpl.model.TplMessageInvoice;
import com.baosight.baosteel.bli.tpl.model.TplMessageInvoiceFee;
import com.baosight.baosteel.bli.tpl.model.TplPlanFee;
import com.baosight.baosteel.bli.tpl.model.TplRProviderWarehouse;
import com.baosight.baosteel.bli.tpl.model.TplRealFee;
import com.baosight.baosteel.bli.tpl.model.TplTransSeq;
import com.baosight.baosteel.bli.tpl.transport.dao.TransSeqDao;
import com.baosight.web.struts.BaseResultModel;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class RealFeeServiceImpl
  implements RealFeeService
{
  private static final Log logger = LogFactory.getLog(RealFeeServiceImpl.class);

  private RealFeeDao realfeedao = null;

  private RealFeeArhDao realfeearhdao = null;

  private InvoiceDao invoicedao = null;

  private InvoiceTitleChangeDao invoicetitlechangedao = null;

  private MessageService messageService = null;

  private CodeUtil codeUtil = null;

  private InsuranceBillService insuranceBillService = null;

  private GoodBillDao goodBillDao = null;

  private GoodBillArhDao goodBillArhDao = null;

  private PlanFeeDao planFeeDao = null;

  private TransSeqDao transSeqDao = null;

  public void deleteTplRealFeeByIds(Long[] ids)
    throws Exception
  {
    this.realfeedao.delete(ids, TplRealFee.class);
  }

  public void deleteTplInvoiceTitleUserByIds(Long[] ids)
    throws Exception
  {
    this.realfeedao.delete(ids, TplInvoiceTitleUser.class);
  }

  public void insertRealFee(TplRealFee tplRealFee)
    throws Exception
  {
    this.realfeedao.insert(tplRealFee, TplRealFee.class);
  }

  public TplRealFee queryTplRealFeeById(Long id)
    throws Exception
  {
    return (TplRealFee)this.realfeedao.queryById(id, TplRealFee.class);
  }

  public List queryTplAcceptInfo(String planId, String orderNo)
    throws Exception
  {
    return this.realfeedao.queryTplAcceptInfo(planId, orderNo);
  }

  public void updateTplRealFeeByBO(TplRealFee tplRealFee)
    throws Exception
  {
    this.realfeedao.update(tplRealFee, TplRealFee.class);
  }

  public BaseResultModel queryTplRealFeeListForPageBySearchModel(RealFeeSearchModel realFeeSearchModel)
    throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.realfeedao.queryTplFeeRateListForPage(realFeeSearchModel);
    baseResultModel.setPage(realFeeSearchModel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public List queryTplRealFeeListBySearchModelNoPage(RealFeeSearchModel realFeeSearchModel)
    throws Exception
  {
    List list = this.realfeedao
      .queryTplFeeRateListBySearchModelNoPage(realFeeSearchModel);

    return list;
  }

  public int NumOfTplRealFeeListBySearchModelNoPage(RealFeeSearchModel realFeeSearchModel) throws Exception
  {
    int resultNum = this.realfeedao
      .NumOfTplFeeRateListBySearchModelNoPage(realFeeSearchModel);

    return resultNum;
  }

  public List queryTplRealFeeListNoPage(RealFeeSearchModel realFeeSearchModel, String[] planIds)
    throws Exception
  {
    List list = this.realfeedao.queryTplFeeRateListNoPage(realFeeSearchModel, 
      planIds);
    return list;
  }

  public BaseResultModel querySettleTrack(RealFeeSearchModel searchModel) throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.realfeedao.querySettleTrack(searchModel);
    baseResultModel.setPage(searchModel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public List querySumRealFeeList(RealFeeSearchModel realFeeSearchModel)
    throws Exception
  {
    return this.realfeedao.querySumRealFee(realFeeSearchModel);
  }

  public List queryInvoiceStutasByIds(List ids)
    throws Exception
  {
    return this.realfeedao.queryInvoiceStutasByIds(ids);
  }

  public BaseResultModel queryStockInvoiceMgrListForPage(StockInvoiceMgrSearchModel stockInvoiceMgrSearchModel)
    throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.invoicedao
      .queryStockInvoiceMgrListForPage(stockInvoiceMgrSearchModel);
    baseResultModel.setPage(stockInvoiceMgrSearchModel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public List querySumInvoiceList(StockInvoiceMgrSearchModel stockInvoiceMgrSearchModel)
    throws Exception
  {
    return this.invoicedao.querySumInvoiceList(stockInvoiceMgrSearchModel);
  }

  public List queryTplRealFeeListByIds(String[] ids)
    throws Exception
  {
    return this.realfeedao.queryTplRealFeeListByIds(ids);
  }

  public void saveGenInvoiceByShipId(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    String transSeq = genInvoiceSearchModel.getTransSeq();
    TplTransSeq transSeqModel = (TplTransSeq)this.transSeqDao.queryById(
      new Long(transSeq), TplTransSeq.class);
    String invoiceStatus = transSeqModel.getInvoiceStatus();
    if ("0".equals(invoiceStatus))
    {
      transSeqModel
        .setInvoiceStatus("1");
      this.transSeqDao.update(transSeqModel, TplTransSeq.class);

      List list = this.realfeedao.queryIdsInOneShip(transSeq);
      if ((list != null) && (list.size() > 0)) {
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          ids[i] = list.get(i).toString();
        }
        genInvoiceSearchModel.setIds(ids);
        saveGenInvoice(genInvoiceSearchModel);
      }
    }
  }

  public void saveDelayGenInvoiceByShipId(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    String transSeq = genInvoiceSearchModel.getTransSeq();
    TplTransSeq transSeqModel = (TplTransSeq)this.transSeqDao.queryById(
      new Long(transSeq), TplTransSeq.class);
    String invoiceStatus = transSeqModel.getInvoiceStatus();

    if (genInvoiceSearchModel.getWaterTransportationInvoiceFlag().equals(
      "2"))
    {
      List list = this.realfeedao.queryDelayIdsInOneShip(transSeq, 
        genInvoiceSearchModel);
      if ((list != null) && (list.size() > 0)) {
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          ids[i] = list.get(i).toString();
        }
        genInvoiceSearchModel.setIds(ids);
        saveGenInvoice(genInvoiceSearchModel);
      }

    }
    else if ("2"
      .equals(invoiceStatus))
    {
      transSeqModel
        .setInvoiceStatus("1");
      this.transSeqDao.update(transSeqModel, TplTransSeq.class);

      List list = this.realfeedao.queryDelayIdsInOneShip(transSeq, 
        genInvoiceSearchModel);
      if ((list != null) && (list.size() > 0)) {
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          ids[i] = list.get(i).toString();
        }
        genInvoiceSearchModel.setIds(ids);
        saveGenInvoice(genInvoiceSearchModel);
      }
    }
  }

  public List queryInvoiceIdsInOneShip(String transSeq)
    throws Exception
  {
    return this.realfeedao.queryInvoiceIdsInOneShip(transSeq);
  }

  public List queryInvoiceIdsInOneShip(String transSeq, Object obj)
    throws Exception
  {
    return this.realfeedao.queryInvoiceIdsInOneShip(transSeq, obj);
  }

  public List queryIdsInOneShip(String transSeq)
    throws Exception
  {
    return this.realfeedao.queryIdsInOneShip(transSeq);
  }

  public int queryIdsInOneShip_refined(String transSeq)
    throws Exception
  {
    return this.realfeedao.queryIdsInOneShip_refined(transSeq);
  }

  public int queryIdsInOneShip_refined(String transSeq, Object o)
    throws Exception
  {
    return this.realfeedao.queryIdsInOneShip_refined(transSeq, o);
  }

  public void saveGenInvoiceByIdsTitleNameForTransFee(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    saveGenInvoice(genInvoiceSearchModel);
  }

  public void updateGenInvoiceShipSelf(String transSeq)
    throws Exception
  {
    boolean setCodFlag = this.realfeedao.checkSettleCode(transSeq, 
      "4");
    if (setCodFlag) {
      throw new Exception("msg:非承运商代收代付的结算方式禁止使用自行结算功能！");
    }

    boolean flag = this.realfeedao.checkPickType(transSeq, "10");
    if (!flag)
    {
      this.realfeedao.updateTransSeqStatusInvoiceByTransSeq(transSeq);

      this.realfeedao.updateInvoiceStatusByTransSeq(transSeq);
    } else {
      throw new Exception("msg:定金发货合同不允许自行结算!");
    }
  }

  public void updateTplRealStatusBySelf(String[] ids)
    throws Exception
  {
    this.realfeedao.updateTplRealStatusBySelf(ids);
  }

  public void updateTransSeqStatusInvoiceByTransSeq(String transSeq)
    throws Exception
  {
    this.realfeedao.updateTransSeqStatusInvoiceByTransSeq(transSeq);
  }

  private void saveGenInvoice(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    String[] ids = genInvoiceSearchModel.getIds();

    List list = new ArrayList();
    for (int i = 0; i < ids.length; i++) {
      list.add(ids[i]);
    }
    genInvoiceSearchModel.setNextIds(list);
    logger.info("所有开票的id个数：" + list.toString());
    esalesDsdfGenInvoice(genInvoiceSearchModel);

    outFacDsdfDjGenInvoice(genInvoiceSearchModel);
    logger.info("地区公司代收代付厂外定金开票以后还剩id个数：" + list.toString());

    logger.info("结算方式是2 代收代付电商开票以后还剩id个数：" + list.toString());
    if (list.size() <= 0) {
      return;
    }

    List listDaishouDaifu = this.realfeedao
      .queryIdsBySttleCodeAndInvoiceTitleCode(ids);
    if ((listDaishouDaifu != null) && (listDaishouDaifu.size() > 0))
    {
      genInvoiceSearchModel.setIdsList(listDaishouDaifu);
      saveGenInvoiceContract(genInvoiceSearchModel);
      list.removeAll(listDaishouDaifu);
    }
    logger.info("结算方式是2 代收代付开票以后还剩id个数：" + list.toString());
    if (list.size() <= 0) {
      return;
    }

    List listWuliu = this.realfeedao.queryIdsByUnitId(ids, false);
    if ((listWuliu != null) && (listWuliu.size() > 0)) {
      genInvoiceSearchModel.setIdsList(listWuliu);

      saveGenInvoiceTitle(genInvoiceSearchModel);
      list.removeAll(listWuliu);
    }
    logger.info("委托机构属于物流室开票以后还剩id个数：" + list.toString());
    if (list.size() <= 0) {
      return;
    }

    esalsSdGenInvoice(genInvoiceSearchModel);
    outFacSdDjGenInvoice(genInvoiceSearchModel);
    logger.info("三单厂外定金开票以后还剩id个数：" + list.toString());

    logger.info("三单电商开票以后还剩id个数：" + list.toString());
    if (list.size() <= 0) {
      return;
    }

    List listThree = this.realfeedao.queryIdsByUnitIdSettleOrder(ids, 
      "1", null);
    if ((listThree != null) && (listThree.size() > 0))
    {
      genInvoiceSearchModel.setIdsList(listThree);
      saveGenInvoiceContract(genInvoiceSearchModel);
      list.removeAll(listThree);
    }
    logger.info("委托机构属于物流室 结算方式是三单结算开票以后还剩id个数：" + list.toString());
    if (list.size() <= 0) {
      return;
    }

    List listAreaDaishouDaifu = this.realfeedao
      .queryIdsBySttleCodeAndInvoiceTitleCodeArea(ids);
    if ((listAreaDaishouDaifu != null) && (listAreaDaishouDaifu.size() > 0))
    {
      genInvoiceSearchModel.setIdsList(listAreaDaishouDaifu);
      saveAreaGenInvoiceTitle(genInvoiceSearchModel);
      list.removeAll(listAreaDaishouDaifu);
    }

    logger.info("结算方式是地区公司代收代付开票以后还剩id个数：" + list.toString());
    if (list.size() <= 0) {
      return;
    }

    genInvoiceSearchModel.setIdsList(list);
    saveGenInvoiceTitle(genInvoiceSearchModel);
  }

  private void saveGenInvoiceTitle(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    List idsList = genInvoiceSearchModel.getIdsList();

    TplTransSeq tplTransSeq = null;
    if (StringUtil.trimStr(genInvoiceSearchModel.getTransSeq()).length() > 0) {
      tplTransSeq = (TplTransSeq)this.realfeedao.queryById(
        Long.valueOf(genInvoiceSearchModel.getTransSeq()), 
        TplTransSeq.class);
    }

    List title = null;
    if (tplTransSeq != null) {
      if ("10".equals(tplTransSeq.getShipCode()))
        title = this.realfeedao.queryTitlesForGenInvoiceByIdsZC(idsList
          .toArray());
      else
        title = this.realfeedao.queryTitlesForGenInvoiceByIds(idsList
          .toArray());
    }
    else {
      title = this.realfeedao.queryTitlesForGenInvoiceByIds(idsList.toArray());
    }
    if ((title == null) || (title.size() <= 0)) {
      throw new Exception("费用管理模块，生成发票错误，发票抬头代码为空");
    }
    for (int i = 0; i < title.size(); i++) {
      Object[] temp = (Object[])title.get(i);
      String unitId = temp[0] == null ? null : temp[0].toString();
      String settleCode = temp[1] == null ? null : temp[1].toString();
      String invoiceTitleCode = temp[2] == null ? null : temp[2]
        .toString();

      String idStr = temp[8] == null ? "0" : temp[8].toString();
      String orderUserType = temp[9] == null ? "" : temp[9].toString();
      String pickType = temp[10] == null ? "" : temp[10].toString();

      String netWeight = temp[3] == null ? "0" : temp[3].toString();
      String grossWeight = temp[4] == null ? "0" : temp[4].toString();
      String actCount = temp[5] == null ? "0" : temp[5].toString();
      String sumAmount = temp[6] == null ? "0" : temp[6].toString();
      String totalInsurMoney = temp[7] == null ? "0" : temp[7].toString();
      String destSpot = temp[11] == null ? "" : temp[11].toString();
      String saleOrgCode = temp[12] == null ? "" : temp[12].toString();
      String procmtOrgCode = temp[13] == null ? "" : temp[13].toString();
      String procmtType = temp[14] == null ? "" : temp[14].toString();
      String businessType = temp[15] == null ? "" : temp[15].toString();
      String transInvoiceSequence = temp[16] == null ? "" : temp[16]
        .toString();
      if ((!transInvoiceSequence.equals("")) && 
        (transInvoiceSequence.equals("AA")) && 
        (!settleCode.equals("")) && (settleCode.equals("3")))
      {
        continue;
      }

      String taxRateTemp = "0";
      if (temp[17] != null) {
        taxRateTemp = temp[17].toString();
      }
      BigDecimal taxRate = new BigDecimal(taxRateTemp);

      String taxAreaCode = temp[18] == null ? "" : temp[18].toString();
      String invoiceType = temp[19] == null ? "" : temp[19].toString();
      String invDrawbackAmount = temp[20] == null ? "0" : temp[20]
        .toString();

      String laterSettleFlag = temp[21] == null ? "" : temp[21]
        .toString();

      Map params_map = new HashMap();
      params_map.put("unitId", unitId);
      params_map.put("settleCode", settleCode);
      params_map.put("invoiceTitleCode", invoiceTitleCode);
      params_map.put("saleOrgCode", saleOrgCode);
      params_map.put("procmtOrgCode", procmtOrgCode);
      params_map.put("procmtType", procmtType);
      params_map.put("businessType", businessType);
      params_map.put("idStr", idStr);
      params_map.put("orderUserType", orderUserType);
      params_map.put("netWeight", netWeight);
      params_map.put("grossWeight", grossWeight);
      params_map.put("actCount", actCount);
      params_map.put("sumAmount", sumAmount);
      params_map.put("totalInsurMoney", totalInsurMoney);
      params_map.put("pickType", pickType);
      params_map.put("destSpot", destSpot);
      params_map.put("taxRate", taxRate);
      params_map.put("invoiceType", invoiceType);
      params_map.put("laterSettleFlag", laterSettleFlag);

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(idStr), TplRealFee.class);
      params_map.put("invDrawbackAmount", invDrawbackAmount);
      params_map.put("transType", tplRealFee.getTransType() == null ? "" : 
        tplRealFee.getTransType().toString());

      TplInvoice tplInvoice = new TplInvoice();

      tplInvoice.setInvoiceClassify(tplRealFee.getInvoiceType());

      TplTransSeq transSeq = null;
      if ((SystemConfigUtil.FACTORY_WG_PROVIDER_ID_MAP.containsKey(tplRealFee.getProviderId())) && 
        (tplRealFee.getTransMSeq() != null)) {
        Long seqId = tplRealFee.getTransMSeq();
        transSeq = (TplTransSeq)this.transSeqDao.queryById(seqId, 
          TplTransSeq.class);
      } else if (tplRealFee.getTransSeq() != null) {
        Long seqId = tplRealFee.getTransSeq();
        transSeq = (TplTransSeq)this.transSeqDao.queryById(seqId, 
          TplTransSeq.class);
      }

      BigDecimal taxRateZc = tplRealFee.getTaxRate() == null ? 
        new BigDecimal("0") : 
        tplRealFee.getTaxRate();
      String shipCode = "";
      DecimalFormat df = new DecimalFormat("#.##");
      if (transSeq != null) {
        shipCode = StringUtil.trimStr(transSeq.getShipCode());

        if (((!shipCode.equals("")) && (shipCode.equals("10")) && (
          (taxRateTemp.equals("0.11")) || (taxRateTemp.equals("0.1")))) || (
          (SystemConfigUtil.FACTORY_WG_PROVIDER_ID_MAP
          .containsKey(transSeq.getProviderId())) && ((taxRateTemp.equals("0.11")) || (taxRateTemp.equals("0.1"))))) {
          String taxYh = this.realfeedao.queryTplGoodBillBySeqId(transSeq.getId());
          if ((taxYh != null) && (taxYh.length() > 0)) {
            tplInvoice.setTaxYh(Double.valueOf(taxYh));
          }
        }
      }
      String transType = StringUtil.trimStr(tplRealFee.getTransType());

      tplInvoice.setSaleOrgCode(saleOrgCode);
      tplInvoice.setProcmtOrgCode(tplRealFee.getProcmtOrgCode());
      tplInvoice.setProcmtType(tplRealFee.getProcmtType());
      tplInvoice.setBusinessType(tplRealFee.getBusinessType());

      insertTplInvoiceTitle(genInvoiceSearchModel, params_map, tplInvoice);

      tplInvoice.setTaxAreaCode(tplRealFee.getTaxAreaCode());
      tplInvoice.setTaxAreaName(tplRealFee.getTaxAreaName());
      tplInvoice
        .setUserBankBranchName(tplRealFee.getUserBankBranchName());
      tplInvoice
        .setUserChineseAddress(tplRealFee.getUserChineseAddress());
      tplInvoice.setUserTelephoneNum(tplRealFee.getUserTelephoneNum());
      tplInvoice.setUserAccountNum(tplRealFee.getUserAccountNum());

      insertTplInvoice(tplRealFee, tplInvoice);
      String invoiceSysId = tplInvoice.getId().toString();

      List list = null;
      if (tplTransSeq != null) {
        if ("10".equals(tplTransSeq.getShipCode()))
          list = this.realfeedao.queryIdsByTitleUitlIdOrder(idsList
            .toArray(), invoiceTitleCode, settleCode, "ZC", 
            orderUserType, pickType, destSpot, saleOrgCode, 
            procmtOrgCode, procmtType, businessType, 
            transInvoiceSequence, taxRate, taxAreaCode, 
            invoiceType);
        else
          list = this.realfeedao.queryIdsByTitleUitlIdOrder(idsList
            .toArray(), invoiceTitleCode, settleCode, unitId, 
            orderUserType, pickType, destSpot, saleOrgCode, 
            procmtOrgCode, procmtType, businessType, 
            transInvoiceSequence, taxRate, taxAreaCode, 
            invoiceType);
      }
      else {
        list = this.realfeedao.queryIdsByTitleUitlIdOrder(idsList.toArray(), 
          invoiceTitleCode, settleCode, unitId, orderUserType, 
          pickType, destSpot, saleOrgCode, procmtOrgCode, 
          procmtType, businessType, transInvoiceSequence, 
          taxRate, taxAreaCode, invoiceType);
      }

      this.realfeedao.updateTplRealStatusInvoicIdByIds(list.toArray(), 
        invoiceSysId);
      String trainNo = tplRealFee.getTrainId();

      String shipId = "";

      this.realfeedao.updateTplInsuranceBillInvoicSysIdByIds(list.toArray(), 
        invoiceSysId, shipId, trainNo);
    }
  }

  private void insertTplInvoiceTitle(GenInvoiceSearchModel genInvoiceSearchModel, Map params_map, TplInvoice tplInvoice)
    throws Exception
  {
    String netWeight = (String)params_map.get("netWeight");
    String grossWeight = (String)params_map.get("grossWeight");
    String actCount = (String)params_map.get("actCount");
    String sumAmount = (String)params_map.get("sumAmount");
    String totalInsurMoney = (String)params_map.get("totalInsurMoney");
    String orderUserType = (String)params_map.get("orderUserType");
    String pickType = (String)params_map.get("pickType");

    String taxAreaCode = (String)params_map.get("taxAreaCode");
    String taxAreaName = (String)params_map.get("taxAreaName");

    String userBankBranchName = (String)params_map
      .get("userBankBranchName");
    String userAccountNum = (String)params_map.get("userAccountNum");
    String userChineseAddress = (String)params_map
      .get("userChineseAddress");
    String userTelephoneNum = (String)params_map.get("userTelephoneNum");
    String invDrawbackAmount = (String)params_map.get("invDrawbackAmount");
    String transType = (String)params_map.get("transType");

    String laterSettleFlag = (String)params_map.get("laterSettleFlag");

    tplInvoice.setTaxAreaCode(taxAreaCode);
    tplInvoice.setTaxAreaName(taxAreaName);
    tplInvoice.setUserBankBranchName(userBankBranchName);
    tplInvoice.setUserAccountNum(userAccountNum);
    tplInvoice.setUserChineseAddress(userChineseAddress);
    tplInvoice.setUserTelephoneNum(userTelephoneNum);

    BigDecimal taxRate = (BigDecimal)params_map.get("taxRate");
    tplInvoice.setTaxRate(taxRate);
    String invoiceTypeR = (String)params_map.get("invoiceType");

    String createId = genInvoiceSearchModel.getCreateId();
    String invoiceType = genInvoiceSearchModel.getInvoiceType();
    if ((invoiceType == null) || ("".equals(invoiceType.trim()))) {
      throw new Exception("错误：发票类型不能为空 invoiceType " + invoiceType);
    }

    tplInvoice.setTotalAmount(new Double(sumAmount));

    tplInvoice.setInsurTotalMoney(new Double(totalInsurMoney));

    DecimalFormat df = new DecimalFormat("#.##");

    tplInvoice.setInvDrawbackAmount(tplInvoice.getTotalAmount());
    tplInvoice.setTaxAmount(new Double(setTaxAmountToTplMessageInvoiceFee(
      tplInvoice.getTotalAmount(), tplInvoice.getTaxRate())));

    tplInvoice.setGrossWeight(new Double(grossWeight));

    tplInvoice.setNetWeight(new Double(netWeight));

    tplInvoice.setActCount(new Long(actCount));

    tplInvoice.setOrderUserType(orderUserType);

    tplInvoice.setPickType(pickType);

    tplInvoice.setCreateId(createId);

    tplInvoice.setCreateDate(new Date());

    System.out.println("------发票类型---------  invoiceType  " + invoiceTypeR);
    System.out.println("------税率---------" + taxRate + "---" + invoiceType + 
      "aa");

    String strRaxRate = "";
    if (taxRate != null) {
      strRaxRate = taxRate.toString();
    }

    Map invoiceTypeM = new HashMap();
    invoiceTypeM.put("invoiceTypeR", invoiceTypeR);
    invoiceTypeM.put("taxRate", strRaxRate);
    invoiceTypeM.put("invoiceType", invoiceType);
    invoiceTypeM.put("transType", invoiceType);
    tplInvoice
      .setInvoiceType(SystemConfigUtil.getInvoiceType(invoiceTypeM));

    tplInvoice.setInvoiceClassify(invoiceTypeR);
    System.out.println("++++++++++++++++++++++1+" + 
      tplInvoice.getInvoiceType() + "+1+++++++2+" + 
      tplInvoice.getInvoiceClassify() + 
      "+2+++++++++++++++++++++++++++++");

    tplInvoice.setCreditorName(genInvoiceSearchModel.getCreditorName());

    tplInvoice.setProviderBank(StringUtil.trimStr(genInvoiceSearchModel
      .getProviderBank()));

    tplInvoice.setProviderAccount(StringUtil.trimStr(genInvoiceSearchModel
      .getProviderAccount()));

    tplInvoice
      .setPayStatus("0");

    tplInvoice.setLaterSettleFlag(laterSettleFlag);
  }

  private void saveGenInvoiceContract(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    List idsList = genInvoiceSearchModel.getIdsList();

    List orderNumList = this.realfeedao.queryContractByIds(idsList.toArray());
    if ((orderNumList == null) || (orderNumList.size() <= 0)) {
      throw new Exception("费用管理模块，生成发票错误，发票合同为空");
    }
    orderNumList.remove(null);
    for (int i = 0; i < orderNumList.size(); i++)
    {
      Object[] obj = (Object[])orderNumList.get(i);
      if ((obj == null) || ("".equals(obj))) {
        continue;
      }
      String orderNum = obj[0] == null ? "" : obj[0].toString();
      if ((orderNum == null) || ("".equals(orderNum))) {
        continue;
      }
      String transInvoiceSequence = obj[1] == null ? "" : obj[1]
        .toString();

      String taxRateTemp = "0";
      if (obj[2] != null) {
        taxRateTemp = obj[2].toString();
      }
      BigDecimal taxRate = new BigDecimal(taxRateTemp);

      String taxAreaCode = obj[3] == null ? "" : obj[3].toString();
      String invoiceType = obj[4] == null ? "" : obj[4].toString();

      String laterSettleFlag = obj[5] == null ? "" : obj[5].toString();

      List list = this.realfeedao.queryIdsByContract(idsList.toArray(), 
        orderNum, transInvoiceSequence, taxRate, taxAreaCode, 
        invoiceType);

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(list.get(0).toString()), TplRealFee.class);
      TplInvoice tplInvoice = new TplInvoice();

      tplInvoice.setInvoiceClassify(tplRealFee.getInvoiceType());
      tplInvoice.setTaxAreaCode(tplRealFee.getTaxAreaCode());
      tplInvoice.setTaxAreaName(tplRealFee.getTaxAreaName());
      tplInvoice
        .setUserBankBranchName(tplRealFee.getUserBankBranchName());
      tplInvoice
        .setUserChineseAddress(tplRealFee.getUserChineseAddress());
      tplInvoice.setUserTelephoneNum(tplRealFee.getUserTelephoneNum());
      tplInvoice.setUserAccountNum(tplRealFee.getUserAccountNum());

      tplInvoice.setOrderNum(tplRealFee.getOrderNum());

      tplInvoice.setOrderUserType(tplRealFee.getOrderUserType());

      tplInvoice.setPickType(tplRealFee.getBillType());

      tplInvoice.setProcmtOrgCode(tplRealFee.getProcmtOrgCode());
      tplInvoice.setProcmtType(tplRealFee.getProcmtType());
      tplInvoice.setSaleOrgCode(tplRealFee.getSaleOrgCode());
      tplInvoice.setBusinessType(tplRealFee.getBusinessType());

      tplInvoice.setLaterSettleFlag(laterSettleFlag);

      TplTransSeq transSeq = null;

      if ((SystemConfigUtil.FACTORY_WG_PROVIDER_ID_MAP.containsKey(tplRealFee.getProviderId())) && 
        (tplRealFee.getTransMSeq() != null)) {
        Long seqId = tplRealFee.getTransMSeq();
        transSeq = (TplTransSeq)this.transSeqDao.queryById(seqId, 
          TplTransSeq.class);
      } else if (tplRealFee.getTransSeq() != null) {
        Long seqId = tplRealFee.getTransSeq();
        transSeq = (TplTransSeq)this.transSeqDao.queryById(seqId, 
          TplTransSeq.class);
      }
      BigDecimal taxRateZc = tplRealFee.getTaxRate() == null ? new BigDecimal("0") : 
        tplRealFee.getTaxRate();
      String shipCode = "";
      DecimalFormat df = new DecimalFormat("#.##");
      if (transSeq != null) {
        shipCode = StringUtil.trimStr(transSeq.getShipCode());

        if (((!shipCode.equals("")) && (shipCode.equals("10")) && (
          (taxRateTemp.equals("0.11")) || (taxRateTemp.equals("0.1")))) || (
          (SystemConfigUtil.FACTORY_WG_PROVIDER_ID_MAP
          .containsKey(transSeq.getProviderId())) && ((taxRateTemp.equals("0.11")) || (taxRateTemp.equals("0.1"))))) {
          String taxYh = this.realfeedao.queryTplGoodBillBySeqId(transSeq.getId());
          if ((taxYh != null) && (taxYh.length() > 0)) {
            tplInvoice.setTaxYh(Double.valueOf(taxYh));
          }
        }
      }

      String transType = StringUtil.trimStr(tplRealFee.getTransType());

      insertTplInvoiceContract(genInvoiceSearchModel, list.toArray(), 
        tplInvoice);

      String strTaxRate = "";
      if (tplRealFee.getTaxRate() != null) {
        strTaxRate = tplRealFee.getTaxRate().toString();
      }

      Map invoiceTypeM = new HashMap();
      invoiceTypeM.put("invoiceTypeR", tplRealFee.getInvoiceType());
      invoiceTypeM.put("taxRate", strTaxRate);
      invoiceTypeM.put("invoiceType", genInvoiceSearchModel
        .getInvoiceType());
      invoiceTypeM.put("transType", tplRealFee.getTransType());

      tplInvoice.setInvoiceType(
        SystemConfigUtil.getInvoiceType(invoiceTypeM));
      insertTplInvoice(tplRealFee, tplInvoice);
      String invoiceSysId = tplInvoice.getId().toString();

      this.realfeedao.updateTplRealStatusInvoicIdByIds(list.toArray(), 
        invoiceSysId);

      String shipId = "";
      String trainNo = tplRealFee.getTrainId();

      this.realfeedao.updateTplInsuranceBillInvoicSysIdByIds(list.toArray(), 
        invoiceSysId, shipId, trainNo);
    }
  }

  private void insertTplInvoice(TplRealFee tplRealFee, TplInvoice tplInvoice)
    throws Exception
  {
    tplInvoice.setUnitId(tplRealFee.getUnitId());
    tplInvoice.setUnitName(tplRealFee.getUnitName());

    tplInvoice.setCompanyCode(tplRealFee.getCompanyCode());
    tplInvoice.setInvoiceTitleCode(tplRealFee.getInvoiceTitleCode());
    tplInvoice.setInvoiceTitleName(tplRealFee.getInvoiceTitleName());
    tplInvoice.setInvoiceTitleTaxNo(tplRealFee.getInvoiceTitleTaxNo());

    tplInvoice.setProviderId(tplRealFee.getProviderId());
    tplInvoice.setCreditorCode(tplRealFee.getCreditorCode());
    tplInvoice.setConsigneeCode(tplRealFee.getConsineeCode());
    tplInvoice.setConsigneeName(tplRealFee.getConsineeName());
    tplInvoice.setConsigneeTaxNo(tplRealFee.getConsineeTaxNo());
    tplInvoice.setSettleType(tplRealFee.getSettleCode());
    tplInvoice.setDestSpot(tplRealFee.getDestSpot());
    tplInvoice.setDestSpotName(tplRealFee.getDestSpotName());
    tplInvoice.setLadingSpot(tplRealFee.getLadingSpot());
    tplInvoice.setLadingSpotName(tplRealFee.getLadingSpotName());
    tplInvoice.setPlanId(tplRealFee.getPlanId());
    tplInvoice.setShipId(tplRealFee.getShipId());
    tplInvoice.setShipName(tplRealFee.getShipName());
    tplInvoice.setGoodBillCode(tplRealFee.getGoodBillCode());
    if (StringUtil.trimStr(tplRealFee.getTrainMId()).length() > 0)
      tplInvoice.setTrainNo(tplRealFee.getTrainMId());
    else {
      tplInvoice.setTrainNo(tplRealFee.getTrainId());
    }
    tplInvoice.setManuId(tplRealFee.getManuId());
    tplInvoice.setTaxAreaCode(tplRealFee.getTaxAreaCode());

    this.realfeedao.insert(tplInvoice, TplInvoice.class);
  }

  public void saveGenInvoiceByIdsInvoice(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    saveGenInvoice(genInvoiceSearchModel);
  }

  public String saveGenInvoiceByIdsInvoiceByMonth(GenInvoiceSearchModel genInvoiceSearchModel, RealFeeByMonthSearchModel searchmodel)
    throws Exception
  {
    String planType = searchmodel.getPlanType();

    if ("0".equals(planType))
    {
      genInvoiceSearchModel
        .setInvoiceType("3");
    }
    else if ("1".equals(planType))
    {
      genInvoiceSearchModel
        .setInvoiceType("0");
    }
    else return null;

    List title = this.realfeedao.queryTitlesForGenInvoiceByMonth(searchmodel);
    if ((title == null) || (title.size() <= 0)) {
      return new String("发票月结：你所选择的实绩在该月份下没有记录");
    }
    for (int i = 0; i < title.size(); i++) {
      Object[] temp = (Object[])title.get(i);
      String unitId = temp[0] == null ? null : temp[0].toString();
      String settleCode = temp[1] == null ? null : temp[1].toString();
      String invoiceTitleCode = temp[2] == null ? null : temp[2]
        .toString();
      String idStr = temp[8] == null ? "0" : temp[8].toString();
      String orderUserType = temp[9] == null ? null : temp[9].toString();

      String saleOrgCode = temp[10] == null ? null : temp[10].toString();
      String procmtOrgCode = temp[11] == null ? null : temp[11]
        .toString();
      String procmtType = temp[12] == null ? null : temp[12].toString();
      String businessType = temp[13] == null ? null : temp[13].toString();

      String netWeight = temp[3] == null ? "0" : temp[3].toString();
      String grossWeight = temp[4] == null ? "0" : temp[4].toString();
      String actCount = temp[5] == null ? "0" : temp[5].toString();
      String sumAmount = temp[6] == null ? "0" : temp[6].toString();
      String totalInsurMoney = temp[7] == null ? "0" : temp[7].toString();

      String transInvoiceSequence = temp[14] == null ? null : temp[14]
        .toString();

      String taxRateTemp = "0";
      if (temp[15] != null) {
        taxRateTemp = temp[15].toString();
      }
      BigDecimal taxRate = new BigDecimal(taxRateTemp);

      String taxAreaCode = temp[17] == null ? "0" : temp[17].toString();
      String taxAreaName = temp[18] == null ? "0" : temp[18].toString();
      String invoiceType = temp[16] == null ? "0" : temp[16].toString();
      String userBankBranchName = temp[19] == null ? "0" : temp[19]
        .toString();
      String userAccountNum = temp[20] == null ? "0" : temp[20]
        .toString();
      String userChineseAddress = temp[21] == null ? "0" : temp[21]
        .toString();
      String userTelephoneNum = temp[22] == null ? "0" : temp[22]
        .toString();
      String invDrawbackAmount = temp[23] == null ? "0" : temp[23]
        .toString();
      System.out
        .println(" ------------1111111111111111111111----------taxAreaCode " + 
        taxAreaCode);
      System.out
        .println(" ------------1111111111111111111111----------taxAreaName " + 
        taxAreaName);
      System.out
        .println(" ------------1111111111111111111111----------invoiceType " + 
        invoiceType);
      System.out
        .println(" ------------1111111111111111111111----------userBankBranchName " + 
        userBankBranchName);
      System.out
        .println(" ------------1111111111111111111111----------userAccountNum " + 
        userAccountNum);
      System.out
        .println(" ------------1111111111111111111111----------userChineseAddress " + 
        userChineseAddress);
      System.out
        .println(" ------------1111111111111111111111----------userTelephoneNum " + 
        userTelephoneNum);

      Map params_map = new HashMap();
      params_map.put("unitId", unitId);
      params_map.put("settleCode", settleCode);
      params_map.put("invoiceTitleCode", invoiceTitleCode);
      params_map.put("idStr", idStr);
      params_map.put("orderUserType", orderUserType);
      params_map.put("saleOrgCode", saleOrgCode);
      params_map.put("procmtOrgCode", procmtOrgCode);
      params_map.put("procmtType", procmtType);
      params_map.put("businessType", businessType);
      params_map.put("netWeight", netWeight);
      params_map.put("grossWeight", grossWeight);
      params_map.put("actCount", actCount);
      params_map.put("sumAmount", sumAmount);
      params_map.put("totalInsurMoney", totalInsurMoney);

      params_map.put("taxRate", taxRate);

      params_map.put("taxAreaCode", taxAreaCode);
      params_map.put("taxAreaName", taxAreaName);
      params_map.put("invoiceType", invoiceType);
      params_map.put("userBankBranchName", userBankBranchName);
      params_map.put("userAccountNum", userAccountNum);
      params_map.put("userChineseAddress", userChineseAddress);
      params_map.put("userTelephoneNum", userTelephoneNum);

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(idStr), TplRealFee.class);
      params_map.put("invDrawbackAmount", invDrawbackAmount);
      params_map.put("transType", tplRealFee.getTransType() == null ? "" : 
        tplRealFee.getTransType().toString());

      TplInvoice tplInvoice = new TplInvoice();
      tplInvoice.setSaleOrgCode(saleOrgCode);
      tplInvoice.setProcmtOrgCode(tplRealFee.getProcmtOrgCode());
      tplInvoice.setProcmtType(tplRealFee.getProcmtType());
      tplInvoice.setBusinessType(tplRealFee.getBusinessType());

      insertTplInvoiceTitle(genInvoiceSearchModel, params_map, tplInvoice);
      insertTplInvoice(tplRealFee, tplInvoice);
      String invoiceSysId = tplInvoice.getId().toString();
      searchmodel.setOwnerAreaPart(unitId);
      searchmodel.setSettleCode(settleCode);
      searchmodel.setInvoiceTitleCode(invoiceTitleCode);
      searchmodel.setOrderUserType(orderUserType);
      searchmodel.setSaleOrgCode(saleOrgCode);
      searchmodel.setProcmtOrgCode(procmtOrgCode);
      searchmodel.setProcmtType(procmtType);
      searchmodel.setBusinessType(businessType);

      searchmodel.setTransInvoiceSequence(transInvoiceSequence);
      searchmodel.setTaxRate(taxRate);

      this.realfeedao.updateTplRealStatusInvoicIdByIdsMonth(searchmodel, 
        invoiceSysId);
    }

    return null;
  }

  public List queryRealFeeIdsByMonth(RealFeeByMonthSearchModel searchmodel)
    throws Exception
  {
    return this.realfeedao.queryRealFeeIdsByMonth(searchmodel);
  }

  private void insertTplInvoiceContract(GenInvoiceSearchModel genInvoiceSearchModel, Object[] ids, TplInvoice tplInvoice)
    throws Exception
  {
    if ((ids == null) || (ids.length <= 0)) {
      throw new Exception("错误：选择的记录不能为空 ids " + ids);
    }
    String createId = genInvoiceSearchModel.getCreateId();

    String invoiceType = genInvoiceSearchModel.getInvoiceType();
    if ((invoiceType == null) || ("".equals(invoiceType.trim()))) {
      throw new Exception("错误：发票类型不能为空 invoiceType " + invoiceType);
    }

    List list = this.realfeedao.sumTplRealFeeContractAmountByIds(ids);
    if ((list == null) || (list.size() <= 0)) {
      throw new Exception("错误：通过选择的记录汇总总金额错误 ids " + ids);
    }

    Object[] temp = (Object[])list.iterator().next();
    String sumAmount = temp[0] == null ? "0" : temp[0].toString();
    String grossWeight = temp[1] == null ? "0" : temp[1].toString();
    String netWeight = temp[2] == null ? "0" : temp[2].toString();
    String actCount = temp[3] == null ? "0" : temp[3].toString();
    String insurTotalMoney = temp[4] == null ? "0" : temp[4].toString();
    String totalAmount = temp[6] == null ? "0" : temp[6].toString();
    String invoiceTypeTmp = temp[7] == null ? "0" : temp[7].toString();
    String invDrawbackAmount = temp[8] == null ? "0" : temp[8].toString();
    String transType = temp[9] == null ? "" : temp[9].toString();

    String taxRate = "0";
    if (temp[5] != null) {
      taxRate = temp[5].toString();
    }
    tplInvoice.setTaxRate(new BigDecimal(taxRate));

    if ((invoiceTypeTmp != null) && ("20".equals(invoiceTypeTmp)))
      tplInvoice.setTotalAmount(new Double(totalAmount));
    else {
      tplInvoice.setTotalAmount(new Double(sumAmount));
    }

    tplInvoice.setInsurTotalMoney(new Double(insurTotalMoney));

    DecimalFormat df = new DecimalFormat("#.##");
    tplInvoice.setInvDrawbackAmount(tplInvoice.getTotalAmount());
    tplInvoice.setTaxAmount(new Double(setTaxAmountToTplMessageInvoiceFee(
      tplInvoice.getTotalAmount(), tplInvoice.getTaxRate())));

    tplInvoice.setGrossWeight(new Double(grossWeight));

    tplInvoice.setNetWeight(new Double(netWeight));

    tplInvoice.setActCount(new Long(actCount));
    tplInvoice.setCreateId(createId);

    tplInvoice.setCreateDate(new Date());

    tplInvoice.setInvoiceType(invoiceType);

    tplInvoice.setCreditorName(genInvoiceSearchModel.getCreditorName());

    tplInvoice.setProviderBank(genInvoiceSearchModel.getProviderBank());

    tplInvoice.setProviderAccount(genInvoiceSearchModel
      .getProviderAccount());

    tplInvoice
      .setPayStatus("0");
  }

  public void deleteInvoiceAndUpdateRealFee(String[] ids)
    throws Exception
  {
    this.realfeedao.updateTplRealStatusAndSetInvoiceNull(ids);

    String batchOperateId = "";
    List invoiceList = new ArrayList();
    List invoiceListNo = new ArrayList();
    List invoiceList2 = new ArrayList();
    List invoiceList3 = new ArrayList();
    invoiceList = this.realfeedao.queryTplInvoice(ids, null, null);
    if ((invoiceList != null) && (invoiceList.size() > 0)) {
      for (int i = 0; i < invoiceList.size(); i++) {
        TplInvoice TplInvoice = (TplInvoice)invoiceList.get(i);

        if ("6".equals(TplInvoice.getInvoiceType())) {
          invoiceListNo.add(TplInvoice.getBatchOperateId());
        }
      }

    }

    sendInvioceCancel(ids);

    this.realfeedao.deleteTplInvoice(ids);

    if ((invoiceListNo != null) && (invoiceListNo.size() > 0))
      for (int j = 0; j < invoiceListNo.size(); j++) {
        batchOperateId = invoiceListNo.get(j).toString();
        invoiceList2 = this.realfeedao.queryTplInvoice(null, batchOperateId, 
          "1");
        invoiceList3 = this.realfeedao.queryTplInvoice(null, batchOperateId, 
          "0");
        if ((invoiceList2 != null) && (invoiceList2.size() > 0)) {
          continue;
        }
        if ((invoiceList3 != null) && (invoiceList3.size() > 0)) {
          this.realfeedao.updateTplTransSeqStatus(batchOperateId, "1");
          this.realfeedao.updateTplProtFeeStatus(batchOperateId, "1");
        } else {
          this.realfeedao.updateTplTransSeqStatus(batchOperateId, "0");
          this.realfeedao.updateTplProtFeeStatus(batchOperateId, "0");
        }
      }
  }

  public void deleteOneShipInvoiceAndUpdateShip(String[] ids, String shipId)
    throws Exception
  {
    TplTransSeq tplTransSeq = (TplTransSeq)this.realfeedao.queryById(
      Long.valueOf(shipId), TplTransSeq.class);

    tplTransSeq
      .setInvoiceStatus("2");
    this.realfeedao.update(Long.valueOf(shipId), TplTransSeq.class);
    deleteInvoiceAndUpdateRealFee(ids);
  }

  public void sendInvioceCancel(String[] ids)
    throws Exception
  {
    for (int i = 0; i < ids.length; i++) {
      TplInvoice tplInvoice = (TplInvoice)this.invoicedao.queryById(
        Long.valueOf(ids[i]), TplInvoice.class);

      String unitId = tplInvoice.getUnitId();
      String payStatus = tplInvoice.getPayStatus();
      String businessType = StringUtil.trimStr(tplInvoice
        .getBusinessType());

      if (!"1"
        .equals(payStatus))
        continue;
      if (!this.messageService.judgeSendMsg(unitId))
        continue;
      TplMessageInvoice tplMessageInvoice = new TplMessageInvoice();

      tplMessageInvoice.setInvoiceSysId(tplInvoice.getId()
        .toString());
      tplMessageInvoice.setUnitId(tplInvoice.getUnitId());
      tplMessageInvoice.setInvoiceId(tplInvoice.getInvoiceId());
      tplMessageInvoice.setInvoiceNo(tplInvoice.getInvoiceNo());

      if (businessType.equals("99")) {
        tplMessageInvoice.setSendStatus("10");
      }

      this.messageService.sendInvoiceCancel(tplMessageInvoice);
    }
  }

  public List queryInsurStatusForCreateInovice(Object[] ids)
    throws Exception
  {
    return this.realfeedao.queryInsurStatusForCreateInovice(ids);
  }

  public int queryInsurStatusForCreateInovice_refinedShipInvoice(String shipIds)
    throws Exception
  {
    return this.realfeedao.queryInsurStatusForCreateInovice_Shiprefined(shipIds);
  }

  public int queryInsurStatusForCreateInovice_refinedShipInvoice(String shipIds, Object o)
    throws Exception
  {
    return this.realfeedao.queryInsurStatusForCreateInovice_Shiprefined(shipIds, 
      o);
  }

  public List queryInsurStatusForCreateInovice_refined(Object[] ids)
    throws Exception
  {
    return this.realfeedao.queryInsurStatusForCreateInovice(ids);
  }

  public void updatePaymentApply(String[] ids)
    throws Exception
  {
    this.realfeedao.updatePaymentApply(ids);

    sendMessageTplInvoicePara(ids);
  }

  public void updateInvoiceIdAndInvoiceNO(String[] ids, String invoiceId, long invoiceNo, int len)
    throws Exception
  {
    if (len < 8) {
      len = 8;
    }
    for (int i = 0; i < ids.length; i++) {
      String id = ids[i];
      long invoiceNoTemp = invoiceNo + i;
      String invoiceNoStr = String.valueOf(invoiceNoTemp);
      int m = invoiceNoStr.length();

      if ((m < 8) || (m != len)) {
        String zero = "";
        for (int t = 0; t < len - m; t++) {
          zero = zero + "0";
        }
        invoiceNoStr = zero + invoiceNoStr;
      }
      this.realfeedao.updateInvoiceIdAndInvoiceNO(id, invoiceId, invoiceNoStr);
    }
  }

  public List queryInvoiceIdAndInvoiceNO(String[] ids, String invoiceId, long invoiceNo, int len)
    throws Exception
  {
    if (len < 8) {
      len = 8;
    }
    for (int i = 0; i < ids.length; i++) {
      long invoiceNoTemp = invoiceNo + i;
      String invoiceNoStr = String.valueOf(invoiceNoTemp);
      int m = invoiceNoStr.length();

      if ((m < 8) || (m != len)) {
        String zero = "";
        for (int t = 0; t < len - m; t++) {
          zero = zero + "0";
        }
        invoiceNoStr = zero + invoiceNoStr;
      }

      List list = this.realfeedao.queryInvoiceIdAndInvoiceNO(invoiceId, 
        invoiceNoStr);
      if ((list != null) && (list.size() > 0)) {
        List temp = new ArrayList();
        temp.add(invoiceId);
        temp.add(invoiceNoStr);
        return temp;
      }
    }
    return null;
  }

  public BaseResultModel queryInvoiceTitleChangeListForPage(InvoiceTitleChangeSearchModel searchmodel)
    throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.invoicetitlechangedao
      .queryInvoiceTitleChangeListForPage(searchmodel);
    baseResultModel.setPage(searchmodel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public String updateInvoiceTitleChangeStatusAndNameByIds(String[] ids)
    throws Exception
  {
    String msg = "";

    for (int i = 0; i < ids.length; i++) {
      List realfeeList = this.invoicetitlechangedao
        .queryTplRealFeeTitleNameByIds(ids[i]);
      List planfeeList = this.invoicetitlechangedao
        .queryTplPlanFeeTitleNameByIds(ids[i]);
      if ((realfeeList != null) && (realfeeList.size() > 0)) {
        msg = "要变更的记录已经生成发票，请撤销后再执行！";
      }
      else if ((planfeeList != null) && (planfeeList.size() > 0))
      {
        this.invoicetitlechangedao.updateTplRealFeeTitleNameByIds(ids[i]);

        this.invoicetitlechangedao.updateTplPlanFeeTitleNameByIds(ids[i]);

        this.invoicetitlechangedao
          .updateInvoiceTitleChangeStatusByIds(new String[] { ids[i] });
      } else {
        msg = "要变更的记录已经生成发票，请撤销后再执行！或着要变更的记录没有费用信息";
      }
    }
    return msg;
  }

  private void sendMessageTplInvoicePara(String[] ids)
    throws Exception
  {
    for (int i = 0; i < ids.length; i++)
    {
      TplInvoice tplInvoice = (TplInvoice)this.invoicedao.queryById(
        Long.valueOf(ids[i]), TplInvoice.class);
      if ((tplInvoice.getTotalAmount() == null) || 
        (tplInvoice.getTotalAmount().doubleValue() == 0.0D)) {
        continue;
      }
      String unitId = tplInvoice.getUnitId();
      String businessType = StringUtil.trimStr(tplInvoice
        .getBusinessType());
      if (this.messageService.judgeSendMsg(unitId))
      {
        TplMessageInvoice tplMessageInvoice = new TplMessageInvoice();

        List tplInsuranceBillList = this.invoicedao
          .queryTplInsuranceBill(ids[i]);
        if ((tplInsuranceBillList != null) && 
          (tplInsuranceBillList.size() > 0))
          tplMessageInvoice.setIsInsuranceBill("1");
        else {
          tplMessageInvoice.setIsInsuranceBill("0");
        }
        System.out.println("======tplInsuranceBillList=====" + 
          tplInsuranceBillList.size() + 
          " tplMessageInvoice.getIsInsuranceBill()" + 
          tplMessageInvoice.getIsInsuranceBill());
        tplMessageInvoice.setUnitId(tplInvoice.getUnitId());
        tplMessageInvoice.setUnitName(tplInvoice.getUnitName());
        tplMessageInvoice.setManuId(tplInvoice.getManuId());
        tplMessageInvoice
          .setInvoiceSysId(tplInvoice.getId().toString());
        tplMessageInvoice.setInvoiceId(tplInvoice.getInvoiceId());
        tplMessageInvoice.setInvoiceNo(tplInvoice.getInvoiceNo());
        tplMessageInvoice.setInvoiceType(tplInvoice.getInvoiceType());
        tplMessageInvoice.setInvoiceDate(tplInvoice.getInvoiceDate());
        tplMessageInvoice.setCreditorCode(tplInvoice.getCreditorCode());
        tplMessageInvoice.setCreditorName(tplInvoice.getCreditorName());
        tplMessageInvoice.setProductTypeName(tplInvoice
          .getProductTypeName());
        tplMessageInvoice.setProviderBank(tplInvoice.getProviderBank());
        tplMessageInvoice.setProviderAccount(tplInvoice
          .getProviderAccount());
        tplMessageInvoice.setInvoiceTitleCode(tplInvoice
          .getInvoiceTitleCode());
        tplMessageInvoice.setInvoiceTitleName(tplInvoice
          .getInvoiceTitleName());
        tplMessageInvoice.setInvoiceTitleTaxNo(tplInvoice
          .getInvoiceTitleTaxNo());
        tplMessageInvoice.setConsigneeCode(tplInvoice
          .getConsigneeCode());
        tplMessageInvoice.setConsigneeName(tplInvoice
          .getConsigneeName());
        tplMessageInvoice.setConsigneeTaxNo(tplInvoice
          .getConsigneeTaxNo());
        tplMessageInvoice.setSettleType(tplInvoice.getSettleType());
        tplMessageInvoice.setTaxYh(
          StringUtil.getBigDecimalNumber(tplInvoice.getTaxYh()));
        tplMessageInvoice.setPlanId(tplInvoice.getPlanId());
        tplMessageInvoice.setCompanyCode(tplInvoice.getCompanyCode());
        tplMessageInvoice.setOrderNum(tplInvoice.getOrderNum());
        tplMessageInvoice.setGoodBillCode(tplInvoice.getGoodBillCode());
        tplMessageInvoice.setTrainNo(tplInvoice.getTrainNo());
        tplMessageInvoice.setLadingSpot(tplInvoice.getLadingSpot());
        tplMessageInvoice.setLadingSpotName(tplInvoice
          .getLadingSpotName());
        tplMessageInvoice.setDestSpot(tplInvoice.getDestSpot());
        tplMessageInvoice.setDestSpotName(tplInvoice.getDestSpotName());
        tplMessageInvoice.setPlanId(tplInvoice.getPlanId());
        tplMessageInvoice.setShipId(tplInvoice.getShipId());
        tplMessageInvoice.setShipName(tplInvoice.getShipName());
        tplMessageInvoice.setGrossWeight(
          StringUtil.getBigDecimalNumber(tplInvoice.getGrossWeight()));
        tplMessageInvoice.setNetWeight(
          StringUtil.getBigDecimalNumber(tplInvoice.getNetWeight()));

        double totalAmount = tplInvoice.getTotalAmount() == null ? 0.0D : 
          tplInvoice.getTotalAmount().doubleValue();

        totalAmount = StringUtil.round(totalAmount, 2);

        String invoiceClassify = tplInvoice.getInvoiceClassify();
        if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("20")))
        {
          double transCharges = tplInvoice.getTransCharges() == null ? 0.0D : 
            tplInvoice.getTransCharges().doubleValue();

          double taxMount = tplInvoice.getTaxAmount() == null ? 0.0D : 
            tplInvoice.getTaxAmount().doubleValue();
          double mistake = StringUtil.round(totalAmount - 
            transCharges - taxMount, 2);
          if (Math.abs(mistake) > 0.01D) {
            throw new Exception(
              "所选增值税发票中,税前金额与税额之和与运费总金额误差大于0.01,请处理");
          }
          totalAmount = StringUtil.round(transCharges + taxMount, 2);
          System.out.println("增值说作业发送给物流管控的总金额..........=" + 
            totalAmount);
        }

        if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("20"))) {
          DecimalFormat df = new DecimalFormat(
            "#.##");
          List shipCodeList = this.realfeedao
            .queryInvoiceIsCanCreateInvoiceB(tplInvoice);
          System.out.println(df.format(tplInvoice.getTaxRate()));
          String shipCode = "";
          if ((shipCodeList != null) && (shipCodeList.size() > 0)) {
            Object[] items = (Object[])shipCodeList.get(0);
            shipCode = StringUtil.trimStr(
              String.valueOf((String)items[1]));
          }
          if ((tplInvoice.getGoodBillCode() != null) && 
            (!"".equals(tplInvoice.getGoodBillCode())))
          {
            if (SystemConfigUtil.FACTORY_PROVIDER_ID_MAP
              .containsKey(tplInvoice.getProviderId()))
            {
              if (!"10".equals(shipCode)) break label1141; if (("0.11".equals(df.format(tplInvoice.getTaxRate()))) || 
                ("0.1".equals(df.format(tplInvoice.getTaxRate())))) {
                tplMessageInvoice.setInvGroupId(
                  String.valueOf(tplInvoice.getId()));

                break label1141;
              }
              if (!"0.06".equals(df.format(tplInvoice
                .getTaxRate()))) break label1141; if (tplInvoice.getInvoicePId() == null) {
                List invoiceIds = this.realfeedao
                  .queryInvoiceId(tplInvoice);
                if ((invoiceIds == null) || 
                  (invoiceIds.size() <= 0)) break label1141; tplMessageInvoice.setInvGroupId(
                  String.valueOf(invoiceIds.get(0)));

                break label1141;
              }

              TplInvoice tplInvoiceP = (TplInvoice)this.realfeedao
                .queryById(tplInvoice.getId(), 
                TplInvoice.class);
              List invoiceIds = this.realfeedao
                .queryInvoiceId(tplInvoiceP);
              if (tplInvoiceP == null) break label1141; tplMessageInvoice.setInvGroupId(
                String.valueOf(invoiceIds.get(0)));

              break label1141;
            }

          }

          if (tplInvoice.getInvoicePId() == null) {
            invoiceChaiFen(tplInvoice, tplMessageInvoice, df);
          } else {
            TplInvoice tplInvoiceP = (TplInvoice)this.realfeedao
              .queryById(tplInvoice.getId(), 
              TplInvoice.class);
            invoiceChaiFen(tplInvoiceP, tplMessageInvoice, df);
          }

        }

        label1141: tplMessageInvoice.setTotalAmount(
          StringUtil.getBigDecimalNumber(new Double(totalAmount)));
        tplMessageInvoice.setPayAmount(
          StringUtil.getBigDecimalNumber(tplInvoice.getPayAmount()));

        tplMessageInvoice.setCreateId(tplInvoice.getCreateId());
        tplMessageInvoice.setInvoiceStatus(tplInvoice
          .getInvoiceStatus());
        tplMessageInvoice.setRewriteStatus(tplInvoice
          .getRewriteStatus());
        tplMessageInvoice.setProviderId(tplInvoice.getProviderId());
        tplMessageInvoice.setCreateDate(tplInvoice.getCreateDate());
        tplMessageInvoice.setModifyDate(tplInvoice.getModifyDate());
        tplMessageInvoice.setModifyId(tplInvoice.getModifyId());
        tplMessageInvoice.setInvoiceType(tplInvoice.getInvoiceType());

        String invTaxAmount = "0";
        if ((tplInvoice.getTaxAmount() != null) && 
          (!"".equals(tplInvoice.getTaxAmount()))) {
          invTaxAmount = Double.toString(tplInvoice.getTaxAmount()
            .doubleValue());
        }
        tplMessageInvoice.setInvTaxAmount(
          StringUtil.getBigDecimalNumber(invTaxAmount));

        Double taxYh = tplInvoice.getTaxYh();
        if (taxYh != null)
        {
          tplMessageInvoice.setTaxYh(
            StringUtil.getBigDecimalNumber(tplInvoice.getTaxYh()));
        }
        else {
          tplMessageInvoice.setTaxYh(new BigDecimal("0"));
        }

        if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("20"))) {
          tplMessageInvoice.setInvDrawbackAmount(
            StringUtil.getBigDecimalNumber(new Double(totalAmount)));
        }

        tplMessageInvoice.setTaxRate(tplInvoice.getTaxRate());
        Set invoicFeeItems = new HashSet();

        if (SystemConfigUtil.BAOSTEEL_OWNER_AREA_PART
          .containsKey(unitId))
        {
          sendInvoiceFeeIsAreaPart(ids, i, tplMessageInvoice, 
            tplInvoice, invoicFeeItems);
        }
        else if (("0".equals(tplInvoice.getInvoiceType())) || 
          ("10".equals(tplInvoice.getInvoiceType())))
        {
          sendInvoiceFeeNoAreaPartCommonType(ids[i], 
            tplMessageInvoice, invoicFeeItems);
        } else if ((tplInvoice.getGoodBillCode() != null) && 
          (!"".equals(tplInvoice.getGoodBillCode())))
        {
          invoicFeeItems = sendInvoiceFeeNoAreaPartRealTypePara(
            ids[i], tplMessageInvoice);
        }
        else if ("6"
          .equals(tplInvoice.getInvoiceType())) {
          System.out
            .println("发票类型是港建费.............................................................");
          tplMessageInvoice.setShipId(tplInvoice
            .getBatchOperateId());

          sendInvoiceFeeForPortFee(ids, i, tplMessageInvoice, 
            invoicFeeItems, tplInvoice);
        }
        else
        {
          sendInvoiceFeeNoAreaPart(ids, i, tplMessageInvoice, 
            invoicFeeItems, tplInvoice);
        }

        tplMessageInvoice.setInvoiceFeeList(invoicFeeItems);
        if (businessType.equals("99")) {
          tplMessageInvoice.setSendStatus("10");
        }
        if ((tplInvoice.getGoodBillCode() != null) && 
          (!"".equals(tplInvoice.getGoodBillCode()))) {
          tplMessageInvoice = getRailTransportMessageInvoiceFee(
            tplMessageInvoice, tplInvoice.getId().toString());
        }

        this.messageService.sendInvoice(tplMessageInvoice);
      }

      List list = this.realfeedao.queryInsuranceBillIdByInvoiceId(tplInvoice
        .getId().toString());
      for (Iterator it = list.iterator(); it.hasNext(); ) {
        String insurId = it.next().toString();
        this.insuranceBillService.sendMessageInsuranceBill(
          Long.valueOf(insurId), 
          "0", unitId);
      }
    }
  }

  private void sendMessageTplInvoice(String[] ids)
    throws Exception
  {
    for (int i = 0; i < ids.length; i++)
    {
      TplInvoice tplInvoice = (TplInvoice)this.invoicedao.queryById(
        Long.valueOf(ids[i]), TplInvoice.class);
      if ((tplInvoice.getTotalAmount() == null) || 
        (tplInvoice.getTotalAmount().doubleValue() == 0.0D)) {
        continue;
      }
      String unitId = tplInvoice.getUnitId();

      if (this.messageService.judgeSendMsg(unitId))
      {
        TplMessageInvoice tplMessageInvoice = new TplMessageInvoice();

        List tplInsuranceBillList = this.invoicedao
          .queryTplInsuranceBill(ids[i]);
        if ((tplInsuranceBillList != null) && 
          (tplInsuranceBillList.size() > 0))
          tplMessageInvoice.setIsInsuranceBill("1");
        else {
          tplMessageInvoice.setIsInsuranceBill("0");
        }
        System.out.println("======tplInsuranceBillList=====" + 
          tplInsuranceBillList.size() + 
          " tplMessageInvoice.getIsInsuranceBill()" + 
          tplMessageInvoice.getIsInsuranceBill());
        tplMessageInvoice.setUnitId(tplInvoice.getUnitId());
        tplMessageInvoice.setUnitName(tplInvoice.getUnitName());
        tplMessageInvoice.setManuId(tplInvoice.getManuId());
        tplMessageInvoice
          .setInvoiceSysId(tplInvoice.getId().toString());
        tplMessageInvoice.setInvoiceId(tplInvoice.getInvoiceId());
        tplMessageInvoice.setInvoiceNo(tplInvoice.getInvoiceNo());
        tplMessageInvoice.setInvoiceType(tplInvoice.getInvoiceType());
        tplMessageInvoice.setInvoiceDate(tplInvoice.getInvoiceDate());
        tplMessageInvoice.setCreditorCode(tplInvoice.getCreditorCode());
        tplMessageInvoice.setCreditorName(tplInvoice.getCreditorName());
        tplMessageInvoice.setProductTypeName(tplInvoice
          .getProductTypeName());
        tplMessageInvoice.setProviderBank(tplInvoice.getProviderBank());
        tplMessageInvoice.setProviderAccount(tplInvoice
          .getProviderAccount());
        tplMessageInvoice.setInvoiceTitleCode(tplInvoice
          .getInvoiceTitleCode());
        tplMessageInvoice.setInvoiceTitleName(tplInvoice
          .getInvoiceTitleName());
        tplMessageInvoice.setInvoiceTitleTaxNo(tplInvoice
          .getInvoiceTitleTaxNo());
        tplMessageInvoice.setConsigneeCode(tplInvoice
          .getConsigneeCode());
        tplMessageInvoice.setConsigneeName(tplInvoice
          .getConsigneeName());
        tplMessageInvoice.setConsigneeTaxNo(tplInvoice
          .getConsigneeTaxNo());
        tplMessageInvoice.setSettleType(tplInvoice.getSettleType());
        tplMessageInvoice.setTaxYh(
          StringUtil.getBigDecimalNumber(tplInvoice.getTaxYh()));
        tplMessageInvoice.setPlanId(tplInvoice.getPlanId());
        tplMessageInvoice.setCompanyCode(tplInvoice.getCompanyCode());
        tplMessageInvoice.setOrderNum(tplInvoice.getOrderNum());
        tplMessageInvoice.setGoodBillCode(tplInvoice.getGoodBillCode());
        tplMessageInvoice.setTrainNo(tplInvoice.getTrainNo());
        tplMessageInvoice.setLadingSpot(tplInvoice.getLadingSpot());
        tplMessageInvoice.setLadingSpotName(tplInvoice
          .getLadingSpotName());
        tplMessageInvoice.setDestSpot(tplInvoice.getDestSpot());
        tplMessageInvoice.setDestSpotName(tplInvoice.getDestSpotName());
        tplMessageInvoice.setPlanId(tplInvoice.getPlanId());
        tplMessageInvoice.setShipId(tplInvoice.getShipId());
        tplMessageInvoice.setShipName(tplInvoice.getShipName());
        tplMessageInvoice.setGrossWeight(
          StringUtil.getBigDecimalNumber(tplInvoice.getGrossWeight()));
        tplMessageInvoice.setNetWeight(
          StringUtil.getBigDecimalNumber(tplInvoice.getNetWeight()));

        double totalAmount = tplInvoice.getTotalAmount() == null ? 0.0D : 
          tplInvoice.getTotalAmount().doubleValue();

        totalAmount = StringUtil.round(totalAmount, 2);

        String invoiceClassify = tplInvoice.getInvoiceClassify();
        if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("20")))
        {
          double transCharges = tplInvoice.getTransCharges() == null ? 0.0D : 
            tplInvoice.getTransCharges().doubleValue();

          double taxMount = tplInvoice.getTaxAmount() == null ? 0.0D : 
            tplInvoice.getTaxAmount().doubleValue();
          double mistake = StringUtil.round(totalAmount - 
            transCharges - taxMount, 2);
          if (Math.abs(mistake) > 0.01D) {
            throw new Exception(
              "所选增值税发票中,税前金额与税额之和与运费总金额误差大于0.01,请处理");
          }
          totalAmount = StringUtil.round(transCharges + taxMount, 2);
          System.out.println("增值说作业发送给物流管控的总金额..........=" + 
            totalAmount);
        }

        if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("20"))) {
          DecimalFormat df = new DecimalFormat(
            "#.##");
          List shipCodeList = this.realfeedao
            .queryInvoiceIsCanCreateInvoiceB(tplInvoice);

          String shipCode = "";
          if ((shipCodeList != null) && (shipCodeList.size() > 0)) {
            Object[] items = (Object[])shipCodeList.get(0);
            shipCode = StringUtil.trimStr(
              String.valueOf((String)items[1]));
          }
          if ((tplInvoice.getGoodBillCode() != null) && 
            (!"".equals(tplInvoice.getGoodBillCode())))
          {
            if (SystemConfigUtil.FACTORY_PROVIDER_ID_MAP
              .containsKey(tplInvoice.getProviderId()))
            {
              if ("20".equals(shipCode)) {
                if (tplInvoice.getInvoicePId() == null) {
                  tplMessageInvoice.setInvGroupId(
                    String.valueOf(tplInvoice.getId()));

                  break label1238;
                }

                TplInvoice tplInvoiceP = (TplInvoice)this.realfeedao
                  .queryById(tplInvoice.getId(), 
                  TplInvoice.class);
                if (tplInvoiceP == null) break label1238; tplMessageInvoice.setInvGroupId(
                  String.valueOf(tplInvoiceP.getId()));

                break label1238;
              }

              if (!"10".equals(shipCode)) break label1238; if ("0.07".equals(df
                .format(tplInvoice.getTaxRate()))) {
                tplMessageInvoice.setInvGroupId(
                  String.valueOf(tplInvoice.getId()));

                break label1238;
              }
              if (!"0.06".equals(df.format(tplInvoice
                .getTaxRate()))) break label1238; if (tplInvoice.getInvoicePId() == null) {
                List invoiceIds = this.realfeedao
                  .queryInvoiceId(tplInvoice);
                if ((invoiceIds == null) || 
                  (invoiceIds.size() <= 0)) break label1238; tplMessageInvoice.setInvGroupId(
                  String.valueOf(invoiceIds.get(0)));

                break label1238;
              }

              TplInvoice tplInvoiceP = (TplInvoice)this.realfeedao
                .queryById(tplInvoice.getId(), 
                TplInvoice.class);
              List invoiceIds = this.realfeedao
                .queryInvoiceId(tplInvoiceP);
              if (tplInvoiceP == null) break label1238; tplMessageInvoice.setInvGroupId(
                String.valueOf(invoiceIds.get(0)));

              break label1238;
            }

          }

          if (tplInvoice.getInvoicePId() == null) {
            invoiceChaiFen(tplInvoice, tplMessageInvoice, df);
          } else {
            TplInvoice tplInvoiceP = (TplInvoice)this.realfeedao
              .queryById(tplInvoice.getId(), 
              TplInvoice.class);
            invoiceChaiFen(tplInvoiceP, tplMessageInvoice, df);
          }
        }
        else if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("10"))) {
          tplMessageInvoice.setInvGroupId(String.valueOf(tplInvoice
            .getId()));
        }

        label1238: tplMessageInvoice.setTotalAmount(
          StringUtil.getBigDecimalNumber(new Double(totalAmount)));
        tplMessageInvoice.setPayAmount(
          StringUtil.getBigDecimalNumber(tplInvoice.getPayAmount()));

        tplMessageInvoice.setCreateId(tplInvoice.getCreateId());
        tplMessageInvoice.setInvoiceStatus(tplInvoice
          .getInvoiceStatus());
        tplMessageInvoice.setRewriteStatus(tplInvoice
          .getRewriteStatus());
        tplMessageInvoice.setProviderId(tplInvoice.getProviderId());
        tplMessageInvoice.setCreateDate(tplInvoice.getCreateDate());
        tplMessageInvoice.setModifyDate(tplInvoice.getModifyDate());
        tplMessageInvoice.setModifyId(tplInvoice.getModifyId());
        tplMessageInvoice.setInvoiceType(tplInvoice.getInvoiceType());

        String invTaxAmount = "0";
        if ((tplInvoice.getTaxAmount() != null) && 
          (!"".equals(tplInvoice.getTaxAmount()))) {
          invTaxAmount = Double.toString(tplInvoice.getTaxAmount()
            .doubleValue());
        }
        tplMessageInvoice.setInvTaxAmount(
          StringUtil.getBigDecimalNumber(invTaxAmount));

        if ((invoiceClassify != null) && 
          (invoiceClassify.trim().equals("20"))) {
          tplMessageInvoice.setInvDrawbackAmount(
            StringUtil.getBigDecimalNumber(new Double(totalAmount)));
        } else {
          double invDrawbackAmount = tplInvoice
            .getInvDrawbackAmount() == null ? 
            0.0D : tplInvoice
            .getInvDrawbackAmount().doubleValue();
          invDrawbackAmount = StringUtil.round(invDrawbackAmount, 2);
          tplMessageInvoice
            .setInvDrawbackAmount(
            StringUtil.getBigDecimalNumber(new Double(
            invDrawbackAmount)));
        }

        tplMessageInvoice.setTaxRate(tplInvoice.getTaxRate());
        Set invoicFeeItems = new HashSet();

        if (SystemConfigUtil.BAOSTEEL_OWNER_AREA_PART
          .containsKey(unitId))
        {
          sendInvoiceFeeIsAreaPart(ids, i, tplMessageInvoice, 
            tplInvoice, invoicFeeItems);
        }
        else if (("0".equals(tplInvoice.getInvoiceType())) || 
          ("10".equals(tplInvoice.getInvoiceType())))
        {
          sendInvoiceFeeNoAreaPartCommonType(ids[i], 
            tplMessageInvoice, invoicFeeItems);
        } else if ((tplInvoice.getGoodBillCode() != null) && 
          (!"".equals(tplInvoice.getGoodBillCode())))
        {
          invoicFeeItems = sendInvoiceFeeNoAreaPartRealType(
            ids[i], tplMessageInvoice);
        }
        else if ("6"
          .equals(tplInvoice.getInvoiceType())) {
          System.out
            .println("发票类型是港建费.............................................................");
          tplMessageInvoice.setShipId(tplInvoice
            .getBatchOperateId());

          sendInvoiceFeeForPortFee(ids, i, tplMessageInvoice, 
            invoicFeeItems, tplInvoice);
        }
        else
        {
          sendInvoiceFeeNoAreaPart(ids, i, tplMessageInvoice, 
            invoicFeeItems, tplInvoice);
        }

        tplMessageInvoice.setInvoiceFeeList(invoicFeeItems);

        if ((tplInvoice.getGoodBillCode() != null) && 
          (!"".equals(tplInvoice.getGoodBillCode()))) {
          tplMessageInvoice = getRailTransportMessageInvoiceFee(
            tplMessageInvoice, tplInvoice.getId().toString());
        }

        this.messageService.sendInvoice(tplMessageInvoice);
      }

      List list = this.realfeedao.queryInsuranceBillIdByInvoiceId(tplInvoice
        .getId().toString());
      for (Iterator it = list.iterator(); it.hasNext(); ) {
        String insurId = it.next().toString();
        this.insuranceBillService.sendMessageInsuranceBill(
          Long.valueOf(insurId), 
          "0", unitId);
      }
    }
  }

  private TplMessageInvoice getRailTransportMessageInvoiceFee(TplMessageInvoice tplMessageInvoice, String id)
    throws Exception
  {
    Set set = tplMessageInvoice.getInvoiceFeeList();
    Set invoicFeeItems = new HashSet();
    Iterator iterator = set.iterator();
    while (iterator.hasNext()) {
      TplMessageInvoiceFee tplMessageInvoiceFee = (TplMessageInvoiceFee)iterator
        .next();
      this.realfeedao.saveTplMessageInoviceFeeTemp(tplMessageInvoiceFee);
    }

    System.out.println("id======" + id);

    List list = this.realfeedao.getTplMessageInoviceFeeTemp(id);
    if (list != null) {
      System.out.println("合成条数=====>" + list.size());
      for (int j = 0; j < list.size(); j++)
      {
        TplMessageInvoiceFee tplMessageInvoiceFee = (TplMessageInvoiceFee)list
          .get(j);
        tplMessageInvoiceFee.setInvoice(tplMessageInvoice);
        invoicFeeItems.add(tplMessageInvoiceFee);
      }
    }
    tplMessageInvoice.setInvoiceFeeList(invoicFeeItems);
    int deleteInt = this.realfeedao.deleteTplMessageInoviceFeeTemp();
    System.out.println("删除条数====》" + deleteInt);

    return tplMessageInvoice;
  }

  private void invoiceChaiFen(TplInvoice tplInvoice, TplMessageInvoice tplMessageInvoice, DecimalFormat df)
    throws Exception
  {
    System.out.println(df.format(tplInvoice.getTaxRate()));
    if (("0.11".equals(df.format(tplInvoice.getTaxRate()))) || 
      ("0.1".equals(df.format(tplInvoice.getTaxRate())))) {
      tplMessageInvoice.setInvGroupId(String.valueOf(tplInvoice.getId()));
    } else if ("0.06".equals(df.format(tplInvoice.getTaxRate()))) {
      List invoiceIds = this.realfeedao.queryInvoiceId(tplInvoice);
      if ((invoiceIds != null) && (invoiceIds.size() > 0)) {
        Object obj = invoiceIds.get(0);
        if (obj != null)
          tplMessageInvoice.setInvGroupId(String.valueOf(invoiceIds
            .get(0)));
        else
          tplMessageInvoice.setInvGroupId("");
      }
    }
  }

  private void sendInvoiceFeeIsAreaPart(String[] ids, int i, TplMessageInvoice tplMessageInvoice, TplInvoice tplInvoice, Set invoicFeeItems)
    throws Exception
  {
    List listSub = this.realfeedao.queryInvoiceForMessageAreaPart(ids[i]);

    for (Iterator it = listSub.iterator(); it.hasNext(); ) {
      Object[] object = (Object[])it.next();
      TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
      tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee.setInvoiceId(tplInvoice.getInvoiceId());

      tplMessageInvoiceFee.setInvoiceNo(tplInvoice.getInvoiceNo());
      if ("BGSQ".equals(tplMessageInvoice
        .getUnitId()))
      {
        tplMessageInvoiceFee.setPlanId(object[3] == null ? null : 
          object[3].toString());
      }
      else {
        tplMessageInvoiceFee.setPlanId(object[0] == null ? null : 
          object[0].toString());
      }

      tplMessageInvoiceFee.setStackId(object[1] == null ? null : 
        object[1].toString());

      TplRealFee tplRealFee = new TplRealFee();

      String taxRate = "0";
      if (object[2] != null) {
        taxRate = object[2].toString();
      }
      tplRealFee.setTaxRate(new BigDecimal(taxRate));

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

      invoicFeeItems.add(tplMessageInvoiceFee);
    }
  }

  private void sendInvoiceFeeNoAreaPartPara(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoice tplInvoice)
    throws Exception
  {
    List listSub = this.realfeedao.queryInvoiceAppleyForMessage(ids[i]);

    HashMap insurRecord = new HashMap();
    for (int m = 0; m < listSub.size(); m++) {
      Object[] object = (Object[])listSub.get(m);
      TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
      tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice.getInvoiceNo());

      tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice.getInvoiceId());

      tplMessageInvoiceFee.setPlanId(object[0] == null ? null : object[0]
        .toString());

      tplMessageInvoiceFee.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      String insurId = object[3] == null ? null : object[3].toString();
      tplMessageInvoiceFee.setInsurId(insurId);

      double totalInsurMoneyFee = object[7] == null ? 0.0D : 
        new Double(object[7].toString()).doubleValue();

      tplMessageInvoiceFee.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee.setFeeTypeCode(object[9] == null ? null : 
        object[9].toString());

      tplMessageInvoiceFee.setFeeTypeName(object[10] == null ? null : 
        object[10].toString());

      double unitPrice = object[11] == null ? 0.0D : 
        new Double(object[11]
        .toString()).doubleValue();
      unitPrice = StringUtil.round(unitPrice, 2);
      tplMessageInvoiceFee.setUnitPrice(
        StringUtil.getBigDecimalNumber(new Double(unitPrice)));

      tplMessageInvoiceFee.setGrossWeight(
        StringUtil.getBigDecimalNumber(object[5]));

      tplMessageInvoiceFee.setTotalAmount(
        StringUtil.getBigDecimalNumber(object[12]));

      String bill_type = object[17] == null ? null : object[17]
        .toString();

      if ((tplMessageInvoiceFee.getGrossWeight().doubleValue() <= 0.0D) || 
        (tplMessageInvoiceFee.getOrderNum().startsWith("A")) || 
        (tplMessageInvoiceFee.getOrderNum().startsWith("B"))) {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[6]));
        tplMessageInvoiceFee.setTotalAmount(
          StringUtil.getBigDecimalNumber(object[13]));
      }

      String orderType = (String)object[15];
      if (orderType != null) {
        orderType.equals("P000");
      }

      String orderNum = tplMessageInvoiceFee.getOrderNum();
      if ((orderNum != null) && (
        (orderNum.startsWith("A")) || (orderNum.startsWith("B"))))
      {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[6]));
        tplMessageInvoiceFee.setTotalAmount(
          StringUtil.getBigDecimalNumber(object[13]));
      }

      if ("10".equals(bill_type))
      {
        if ("1101".equals(tplMessageInvoiceFee
          .getFeeTypeCode())) {
          tplMessageInvoiceFee.setGrossWeight(
            StringUtil.getBigDecimalNumber(object[5]));
          tplMessageInvoiceFee.setTotalAmount(
            StringUtil.getBigDecimalNumber(object[16]));
        }

      }

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(object[20] == null ? "0" : object[20].toString()), 
        TplRealFee.class);

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

      String invoiceType = object[19] == null ? null : object[19]
        .toString();

      invoicFeeItems.add(tplMessageInvoiceFee);

      if ((!"10".equals(invoiceType)) || 
        (totalInsurMoneyFee <= 0.0D) || 
        (!isExistInsurFee(tplMessageInvoiceFee.getPlanId() + 
        tplMessageInvoiceFee.getOrderNum(), insurRecord)))
      {
        continue;
      }

      TplMessageInvoiceFee tplMessageInvoiceFee2 = new TplMessageInvoiceFee();

      tplMessageInvoiceFee2.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee2.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee2.setInvoiceNo(tplMessageInvoice
        .getInvoiceNo());

      tplMessageInvoiceFee2.setInvoiceId(tplMessageInvoice
        .getInvoiceId());

      tplMessageInvoiceFee2.setPlanId(object[0] == null ? null : 
        object[0].toString());

      tplMessageInvoiceFee2.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      tplMessageInvoiceFee2.setInsurId(object[3] == null ? null : 
        object[3].toString());

      tplMessageInvoiceFee2.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee2
        .setFeeTypeCode("3201");

      tplMessageInvoiceFee2
        .setFeeTypeName("保险费");

      totalInsurMoneyFee = StringUtil.round(totalInsurMoneyFee, 2);
      tplMessageInvoiceFee2.setTotalAmount(
        StringUtil.getBigDecimalNumber(new Double(totalInsurMoneyFee)));

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, 
        tplRealFee);

      invoicFeeItems.add(tplMessageInvoiceFee2);

      insurRecord.put(tplMessageInvoiceFee.getPlanId() + 
        tplMessageInvoiceFee.getOrderNum(), "false");
    }
  }

  private void sendInvoiceFeeNoAreaPart(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoice tplInvoice)
    throws Exception
  {
    List listSub = this.realfeedao.queryInvoiceAppleyForMessageForBGSQ(ids[i]);

    double yhscale = 0.0D;

    TplMessageInvoiceFee maxInvoiceFee = null;
    double maxTaxYhMoney = 0.0D;
    HashMap insurRecord = new HashMap();
    for (int m = 0; m < listSub.size(); m++) {
      Object[] object = (Object[])listSub.get(m);
      TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
      tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice.getInvoiceNo());

      tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice.getInvoiceId());

      tplMessageInvoiceFee.setPlanId(object[0] == null ? null : object[0]
        .toString());

      tplMessageInvoiceFee.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      String insurId = object[3] == null ? null : object[3].toString();
      tplMessageInvoiceFee.setInsurId(insurId);

      double totalInsurMoneyFee = object[7] == null ? 0.0D : 
        new Double(object[7].toString()).doubleValue();

      tplMessageInvoiceFee.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee.setFeeTypeCode(object[9] == null ? null : 
        object[9].toString());

      tplMessageInvoiceFee.setFeeTypeName(object[10] == null ? null : 
        object[10].toString());

      double unitPrice = object[11] == null ? 0.0D : 
        new Double(object[11]
        .toString()).doubleValue();
      unitPrice = StringUtil.round(unitPrice, 2);
      tplMessageInvoiceFee.setUnitPrice(
        StringUtil.getBigDecimalNumber(new Double(unitPrice)));

      tplMessageInvoiceFee.setGrossWeight(
        StringUtil.getBigDecimalNumber(object[5]));

      tplMessageInvoiceFee.setTotalAmount(
        StringUtil.getBigDecimalNumber(object[12]));

      String bill_type = object[17] == null ? null : object[17]
        .toString();

      if ((tplMessageInvoiceFee.getGrossWeight().doubleValue() <= 0.0D) || 
        (tplMessageInvoiceFee.getOrderNum().startsWith("A")) || 
        (tplMessageInvoiceFee.getOrderNum().startsWith("B"))) {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[6]));
        tplMessageInvoiceFee.setTotalAmount(
          StringUtil.getBigDecimalNumber(object[13]));
      }

      String orderType = (String)object[15];
      if (orderType != null) {
        orderType.equals("P000");
      }

      String orderNum = tplMessageInvoiceFee.getOrderNum();
      if ((orderNum != null) && (
        (orderNum.startsWith("A")) || (orderNum.startsWith("B"))))
      {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[6]));
        tplMessageInvoiceFee.setTotalAmount(
          StringUtil.getBigDecimalNumber(object[13]));
      }

      if ("10".equals(bill_type))
      {
        if ("1101".equals(tplMessageInvoiceFee
          .getFeeTypeCode())) {
          tplMessageInvoiceFee.setGrossWeight(
            StringUtil.getBigDecimalNumber(object[5]));
          tplMessageInvoiceFee.setTotalAmount(
            StringUtil.getBigDecimalNumber(object[16]));
        }

      }

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(object[20] == null ? "0" : object[20].toString()), 
        TplRealFee.class);

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

      String invoiceType = object[19] == null ? null : object[19]
        .toString();

      invoicFeeItems.add(tplMessageInvoiceFee);

      if ((!"10".equals(invoiceType)) || 
        (totalInsurMoneyFee <= 0.0D) || 
        (!isExistInsurFee(tplMessageInvoiceFee.getPlanId() + 
        tplMessageInvoiceFee.getOrderNum(), insurRecord)))
      {
        continue;
      }

      TplMessageInvoiceFee tplMessageInvoiceFee2 = new TplMessageInvoiceFee();

      tplMessageInvoiceFee2.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee2.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee2.setInvoiceNo(tplMessageInvoice
        .getInvoiceNo());

      tplMessageInvoiceFee2.setInvoiceId(tplMessageInvoice
        .getInvoiceId());

      tplMessageInvoiceFee2.setPlanId(object[0] == null ? null : 
        object[0].toString());

      tplMessageInvoiceFee2.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      tplMessageInvoiceFee2.setInsurId(object[3] == null ? null : 
        object[3].toString());

      tplMessageInvoiceFee2.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee2
        .setFeeTypeCode("3201");

      tplMessageInvoiceFee2
        .setFeeTypeName("保险费");

      totalInsurMoneyFee = StringUtil.round(totalInsurMoneyFee, 2);
      tplMessageInvoiceFee2.setTotalAmount(
        StringUtil.getBigDecimalNumber(new Double(totalInsurMoneyFee)));

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, 
        tplRealFee);

      invoicFeeItems.add(tplMessageInvoiceFee2);

      insurRecord.put(tplMessageInvoiceFee.getPlanId() + 
        tplMessageInvoiceFee.getOrderNum(), "false");
    }
  }

  private void sendInvoiceFeeNoAreaPart(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoiceArh tplInvoice)
    throws Exception
  {
    List listSub = this.realfeearhdao.queryInvoiceAppleyForMessage(ids[i]);

    double yhscale = 0.0D;

    TplMessageInvoiceFee maxInvoiceFee = null;
    double maxTaxYhMoney = 0.0D;
    HashMap insurRecord = new HashMap();
    for (int m = 0; m < listSub.size(); m++) {
      Object[] object = (Object[])listSub.get(m);
      TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
      tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice.getInvoiceNo());

      tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice.getInvoiceId());

      tplMessageInvoiceFee.setPlanId(object[0] == null ? null : object[0]
        .toString());

      tplMessageInvoiceFee.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      String insurId = object[3] == null ? null : object[3].toString();
      tplMessageInvoiceFee.setInsurId(insurId);

      double totalInsurMoneyFee = object[7] == null ? 0.0D : 
        new Double(object[7].toString()).doubleValue();

      tplMessageInvoiceFee.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee.setFeeTypeCode(object[9] == null ? null : 
        object[9].toString());

      tplMessageInvoiceFee.setFeeTypeName(object[10] == null ? null : 
        object[10].toString());

      double unitPrice = object[11] == null ? 0.0D : 
        new Double(object[11]
        .toString()).doubleValue();
      unitPrice = StringUtil.round(unitPrice, 2);
      tplMessageInvoiceFee.setUnitPrice(
        StringUtil.getBigDecimalNumber(new Double(unitPrice)));

      tplMessageInvoiceFee.setGrossWeight(
        StringUtil.getBigDecimalNumber(object[5]));

      tplMessageInvoiceFee.setTotalAmount(
        StringUtil.getBigDecimalNumber(object[12]));

      String bill_type = object[17] == null ? null : object[17]
        .toString();

      if (tplMessageInvoiceFee.getGrossWeight().doubleValue() <= 0.0D) {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[6]));
        tplMessageInvoiceFee.setTotalAmount(
          StringUtil.getBigDecimalNumber(object[13]));
      }

      String orderType = (String)object[15];
      if (orderType != null) {
        orderType.equals("P000");
      }

      String orderNum = tplMessageInvoiceFee.getOrderNum();
      if ((orderNum != null) && (
        (orderNum.startsWith("A")) || (orderNum.startsWith("B"))))
      {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[6]));
        tplMessageInvoiceFee.setTotalAmount(
          StringUtil.getBigDecimalNumber(object[13]));
      }

      if ("10".equals(bill_type))
      {
        if ("1101".equals(tplMessageInvoiceFee
          .getFeeTypeCode())) {
          tplMessageInvoiceFee.setGrossWeight(
            StringUtil.getBigDecimalNumber(object[5]));
          tplMessageInvoiceFee.setTotalAmount(
            StringUtil.getBigDecimalNumber(object[16]));
        }
      }

      invoicFeeItems.add(tplMessageInvoiceFee);

      if ((totalInsurMoneyFee <= 0.0D) || 
        (!isExistInsurFee(tplMessageInvoiceFee.getPlanId() + 
        tplMessageInvoiceFee.getOrderNum(), insurRecord)))
      {
        continue;
      }

      TplMessageInvoiceFee tplMessageInvoiceFee2 = new TplMessageInvoiceFee();

      tplMessageInvoiceFee2.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee2.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee2.setInvoiceNo(tplMessageInvoice
        .getInvoiceNo());

      tplMessageInvoiceFee2.setInvoiceId(tplMessageInvoice
        .getInvoiceId());

      tplMessageInvoiceFee2.setPlanId(object[0] == null ? null : 
        object[0].toString());

      tplMessageInvoiceFee2.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      tplMessageInvoiceFee2.setInsurId(object[3] == null ? null : 
        object[3].toString());

      tplMessageInvoiceFee2.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee2
        .setFeeTypeCode("3201");

      tplMessageInvoiceFee2
        .setFeeTypeName("保险费");

      totalInsurMoneyFee = StringUtil.round(totalInsurMoneyFee, 2);
      tplMessageInvoiceFee2.setTotalAmount(
        StringUtil.getBigDecimalNumber(new Double(totalInsurMoneyFee)));
      invoicFeeItems.add(tplMessageInvoiceFee2);

      insurRecord.put(tplMessageInvoiceFee.getPlanId() + 
        tplMessageInvoiceFee.getOrderNum(), "false");
    }
  }

  private boolean isExistInsurFee(String PlanId_OrderNum, HashMap insurRecord)
  {
    return insurRecord.get(PlanId_OrderNum) == null;
  }

  private void sendInvoiceFeeNoAreaPartCommonType(String id, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems)
    throws Exception
  {
    List listSub = this.realfeedao.queryInvoiceAppleyForMessage(id);

    for (int m = 0; m < listSub.size(); m++) {
      Object[] object = (Object[])listSub.get(m);
      TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
      tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice.getInvoiceNo());

      tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice.getInvoiceId());

      tplMessageInvoiceFee.setPlanId(object[0] == null ? null : object[0]
        .toString());

      tplMessageInvoiceFee.setOrderNum(object[1] == null ? null : 
        object[1].toString());

      tplMessageInvoiceFee.setInsurId(object[3] == null ? null : 
        object[3].toString());

      tplMessageInvoiceFee.setCompanyCode(object[8] == null ? null : 
        object[8].toString());

      tplMessageInvoiceFee.setFeeTypeCode(object[9] == null ? null : 
        object[9].toString());

      tplMessageInvoiceFee.setFeeTypeName(object[10] == null ? null : 
        object[10].toString());

      double unitPrice = object[11] == null ? 0.0D : 
        new Double(object[11]
        .toString()).doubleValue();
      unitPrice = StringUtil.round(unitPrice, 2);
      tplMessageInvoiceFee.setUnitPrice(
        StringUtil.getBigDecimalNumber(new Double(unitPrice)));

      String orderType = (String)object[15];
      if ((orderType != null) && 
        (orderType.equals("P000")))
      {
        tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[5]));
      }
      else tplMessageInvoiceFee.setGrossWeight(
          StringUtil.getBigDecimalNumber(object[5]));

      double unitTotalMoney = object[14] == null ? 0.0D : 
        new Double(object[14].toString()).doubleValue();

      tplMessageInvoiceFee.setTotalAmount(
        StringUtil.getBigDecimalNumber(new Double(unitTotalMoney)));

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(object[20] == null ? "0" : object[20].toString()), 
        TplRealFee.class);

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

      invoicFeeItems.add(tplMessageInvoiceFee);
    }
  }

  private Set sendInvoiceFeeNoAreaPartRealTypePara(String id, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    Set invoicFeeItems = new HashSet();

    List feeList = this.realfeedao.queryRealFeeByInvoiceId(id);
    if ((feeList == null) || (feeList.size() <= 0)) {
      throw new Exception("发票信息错误：发票下没有实绩记录，请从试或联系管理员");
    }

    for (Iterator it = feeList.iterator(); it.hasNext(); ) {
      TplRealFee tplRealFee = (TplRealFee)it.next();

      DecimalFormat df = new DecimalFormat("#.##");
      if (!"20".equals(tplRealFee.getInvoiceType()))
        continue;
      if ("0.06".equals(df.format(tplRealFee.getTaxRate())))
      {
        TplMessageInvoiceFee mixedFee = forRailWayMixedFeeTax(
          tplRealFee, tplMessageInvoice);
        invoicFeeItems.add(mixedFee);
        System.out.println("-----铁路杂费------" + 
          mixedFee.getTotalAmount()); } else {
        if ((!"0.11".equals(df.format(tplRealFee.getTaxRate()))) && 
          (!"0.1".equals(df.format(tplRealFee.getTaxRate()))))
          continue;
        forRailWayFee2(tplMessageInvoice, invoicFeeItems, 
          tplRealFee);
      }

    }

    return invoicFeeItems;
  }

  private Set sendInvoiceFeeNoAreaPartRealType(String id, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    Set invoicFeeItems = new HashSet();

    List feeList = this.realfeedao.queryRealFeeByInvoiceId(id);
    if ((feeList == null) || (feeList.size() <= 0)) {
      throw new Exception("发票信息错误：发票下没有实绩记录，请从试或联系管理员");
    }

    for (Iterator it = feeList.iterator(); it.hasNext(); ) {
      TplRealFee tplRealFee = (TplRealFee)it.next();

      String transInvoiceSequence = tplRealFee.getTransInvoiceSequence();
      String unitId = tplRealFee.getUnitId();
      String providerId = tplRealFee.getProviderId();
      String orderNum = tplRealFee.getOrderNum();
      String planId = tplRealFee.getPlanId();
      String feeTypeCode = "3203";
      Long seqId = tplRealFee.getTransSeq();
      List planFeeList = new ArrayList();

      DecimalFormat df = new DecimalFormat("#.##");
      System.out.println(df.format(tplRealFee.getTaxRate()));
      if ("20".equals(tplRealFee.getInvoiceType())) {
        TplTransSeq transSeq = (TplTransSeq)this.transSeqDao.queryById(
          seqId, TplTransSeq.class);
        String shipCode = "";
        if (transSeq != null) {
          shipCode = StringUtil.trimStr(transSeq.getShipCode());
        }
        if (("0.06".equals(df.format(tplRealFee.getTaxRate()))) && 
          ("20".equals(shipCode)))
        {
          if ("0201".equals(tplRealFee.getFeeTypeCode()))
          {
            forRailWayFeePc(tplMessageInvoice, invoicFeeItems, 
              tplRealFee);
          } else if ("3203".equals(tplRealFee.getFeeTypeCode()))
          {
            TplMessageInvoiceFee mixedFee = forRailWayMixedFeeTax(
              tplRealFee, tplMessageInvoice);
            invoicFeeItems.add(mixedFee);
            System.out.println("-----铁路杂费------" + 
              mixedFee.getTotalAmount());
          }
          else {
            TplMessageInvoiceFee mixedFee = forRailWayMixedFeeTax(
              tplRealFee, tplMessageInvoice);
            invoicFeeItems.add(mixedFee);
            System.out.println("-----铁路杂费------" + 
              mixedFee.getTotalAmount());

            forRailWayFeePc(tplMessageInvoice, invoicFeeItems, 
              tplRealFee);
          }
        } else if ("0.06".equals(df.format(tplRealFee.getTaxRate())))
        {
          TplMessageInvoiceFee mixedFee = forRailWayMixedFeeTax(
            tplRealFee, tplMessageInvoice);
          invoicFeeItems.add(mixedFee);
          System.out.println("-----铁路杂费------" + 
            mixedFee.getTotalAmount()); } else {
          if ((!"0.07".equals(df.format(tplRealFee.getTaxRate()))) && 
            (!"0.11".equals(df.format(tplRealFee.getTaxRate()))) && 
            (!"0.1".equals(df.format(tplRealFee.getTaxRate()))))
            continue;
          forRailWayFee2(tplMessageInvoice, invoicFeeItems, 
            tplRealFee);
        }
      } else {
        if (!"10".equals(tplRealFee.getInvoiceType()))
          continue;
        if (("20".equals(transInvoiceSequence)) || 
          ("0".equals(df.format(tplRealFee.getTaxRate()))) || 
          ("0.00".equals(df.format(tplRealFee.getTaxRate()))))
        {
          TplMessageInvoiceFee mixedFee = forRailWayMixedFee1(
            tplRealFee, tplMessageInvoice);
          invoicFeeItems.add(mixedFee);
          System.out.println("-----铁路杂费------" + 
            mixedFee.getTotalAmount());
        }
        else {
          forRailWayFee(tplMessageInvoice, invoicFeeItems, 
            tplRealFee, unitId, providerId, orderNum, planId, 
            feeTypeCode);
        }
      }
    }

    return invoicFeeItems;
  }

  private void forRailWayFee(TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplRealFee tplRealFee, String unitId, String providerId, String orderNum, String planId, String feeTypeCode)
    throws Exception
  {
    List planFeeList = this.realfeedao.queryPlanFeeList(providerId, orderNum, unitId, 
      planId, feeTypeCode);
    if ((planFeeList != null) && (planFeeList.size() > 0))
    {
      TplMessageInvoiceFee RailWayFee = forRailWayFee(tplRealFee, 
        tplMessageInvoice);
      invoicFeeItems.add(RailWayFee);
      System.out.println("-----铁路运费------" + RailWayFee.getTotalAmount());
    }
    else {
      TplMessageInvoiceFee RailWayFee = forRailWayFee(tplRealFee, 
        tplMessageInvoice);
      invoicFeeItems.add(RailWayFee);

      TplMessageInvoiceFee mixedFee = forRailWayMixedFee(tplRealFee, 
        tplMessageInvoice);
      invoicFeeItems.add(mixedFee);

      double totalInsurMoney = tplRealFee.getTotalInsurMoney() == null ? 0.0D : 
        tplRealFee.getTotalInsurMoney().doubleValue();
      if ((totalInsurMoney > 0.0D) && ("10".equals(tplRealFee.getInvoiceType())))
      {
        TplMessageInvoiceFee insureFee = forRailWayInsureFee(
          tplRealFee, tplMessageInvoice);
        invoicFeeItems.add(insureFee);
      }

      if ("10".equals(tplRealFee
        .getBillType())) {
        List otherlist = forRailWayEarnestFee(tplRealFee, 
          tplMessageInvoice);
        for (Iterator othersIt = otherlist.iterator(); othersIt
          .hasNext(); )
        {
          TplMessageInvoiceFee earnestFee = (TplMessageInvoiceFee)othersIt
            .next();
          invoicFeeItems.add(earnestFee);
        }
      }
      System.out.println("-----铁路运费(不拆分)------" + 
        RailWayFee.getTotalAmount());
      System.out.println("-----铁路杂费(不拆分)------" + 
        mixedFee.getTotalAmount());
    }
  }

  private void forRailWayFee2(TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplRealFee tplRealFee)
    throws Exception
  {
    TplMessageInvoiceFee RailWayFee = forRailWayFeeTax(tplRealFee, 
      tplMessageInvoice);
    invoicFeeItems.add(RailWayFee);

    if ("10".equals(tplRealFee.getBillType())) {
      List otherlist = forRailWayEarnestFee(tplRealFee, tplMessageInvoice);
      for (Iterator othersIt = otherlist.iterator(); othersIt.hasNext(); ) {
        TplMessageInvoiceFee earnestFee = (TplMessageInvoiceFee)othersIt
          .next();
        invoicFeeItems.add(earnestFee);
      }
    }
  }

  private void forRailWayFeePc(TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplRealFee tplRealFee)
    throws Exception
  {
    TplMessageInvoiceFee RailWayFee = forRailWayFeeTaxPc(tplRealFee, 
      tplMessageInvoice);
    invoicFeeItems.add(RailWayFee);

    if ("10".equals(tplRealFee.getBillType())) {
      List otherlist = forRailWayEarnestFee(tplRealFee, tplMessageInvoice);
      for (Iterator othersIt = otherlist.iterator(); othersIt.hasNext(); ) {
        TplMessageInvoiceFee earnestFee = (TplMessageInvoiceFee)othersIt
          .next();
        invoicFeeItems.add(earnestFee);
      }
    }
  }

  private TplMessageInvoiceFee forRailWayFeeTax(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);
    String feeTypeCode = tplRealFee.getFeeTypeCode();
    if ((feeTypeCode != null) && (feeTypeCode.equals("3203"))) {
      tplMessageInvoiceFee
        .setFeeTypeCode("3203");

      tplMessageInvoiceFee
        .setFeeTypeName("铁路运杂费");
    }
    else
    {
      tplMessageInvoiceFee
        .setFeeTypeCode("0201");

      tplMessageInvoiceFee
        .setFeeTypeName("铁路运费");
    }

    double grossWeight = getWeightByCondition(tplRealFee);

    tplMessageInvoiceFee.setGrossWeight(
      StringUtil.getBigDecimalNumber(new Double(grossWeight)));

    double totalMoney = tplRealFee.getTotalMoney() == null ? 0.0D : tplRealFee
      .getTotalMoney().doubleValue();
    tplMessageInvoiceFee.setTotalAmount(
      StringUtil.getBigDecimalNumber(new Double(totalMoney)));

    setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

    return tplMessageInvoiceFee;
  }

  private TplMessageInvoiceFee forRailWayFeeTaxPc(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice) throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);

    tplMessageInvoiceFee
      .setFeeTypeCode("0201");

    tplMessageInvoiceFee
      .setFeeTypeName("铁路运费");

    double grossWeight = getWeightByCondition(tplRealFee);

    tplMessageInvoiceFee.setGrossWeight(
      StringUtil.getBigDecimalNumber(new Double(grossWeight)));

    double totalMoney = tplRealFee.getTransportFee() == null ? 0.0D : 
      tplRealFee.getTransportFee().doubleValue();
    tplMessageInvoiceFee.setTotalAmount(
      StringUtil.getBigDecimalNumber(new Double(totalMoney)));

    setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

    return tplMessageInvoiceFee;
  }

  public double getWeightByCondition(TplRealFee tplRealFee)
    throws Exception
  {
    double grossWeight = tplRealFee.getGrossWeight() == null ? 0.0D : 
      tplRealFee.getGrossWeight().doubleValue();

    double netWeight = tplRealFee.getNetWeight() == null ? 0.0D : tplRealFee
      .getNetWeight().doubleValue();

    if (grossWeight <= 0.0D) {
      return netWeight;
    }

    String orderNum = tplRealFee.getOrderNum();
    if (orderNum != null)
    {
      if ((orderNum.startsWith("A")) || (orderNum.startsWith("B")))
      {
        return netWeight;
      }
    }
    return grossWeight;
  }

  public TplMessageInvoiceFee forRailWayFee(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);

    tplMessageInvoiceFee
      .setFeeTypeCode("0201");

    tplMessageInvoiceFee
      .setFeeTypeName("铁路运费");

    double grossWeight = getWeightByCondition(tplRealFee);
    tplMessageInvoiceFee.setGrossWeight(
      StringUtil.getBigDecimalNumber(new Double(grossWeight)));

    double totalMoney = tplRealFee.getTotalMoney() == null ? 0.0D : tplRealFee
      .getTotalMoney().doubleValue();
    totalMoney = StringUtil.round(totalMoney, 2);
    tplMessageInvoiceFee.setTotalAmount(
      StringUtil.getBigDecimalNumber(new Double(totalMoney)));

    setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

    return tplMessageInvoiceFee;
  }

  public TplMessageInvoiceFee forRailWayMixedFee(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);

    tplMessageInvoiceFee
      .setFeeTypeCode("3203");

    tplMessageInvoiceFee
      .setFeeTypeName("铁路运杂费");

    double grossWeight = getWeightByCondition(tplRealFee);
    tplMessageInvoiceFee.setGrossWeight(
      StringUtil.getBigDecimalNumber(new Double(grossWeight)));

    double totalAmount = tplRealFee.getPayMoney() == null ? 0.0D : tplRealFee
      .getPayMoney().doubleValue();

    double billTransFee = tplRealFee.getBillTransFee() == null ? 0.0D : 
      tplRealFee.getBillTransFee().doubleValue();

    double conFee = tplRealFee.getConFee() == null ? 0.0D : tplRealFee
      .getConFee().doubleValue();

    double contractFee = billTransFee + conFee;

    double totalInsurMoney = tplRealFee.getTotalInsurMoney() == null ? 0.0D : 
      tplRealFee.getTotalInsurMoney().doubleValue();

    double other_fee1 = tplRealFee.getOtherFee1() == null ? 0.0D : tplRealFee
      .getOtherFee1().doubleValue();
    double carry_fee = totalAmount - totalInsurMoney - contractFee - 
      other_fee1;

    carry_fee = StringUtil.round(carry_fee, 2);
    tplMessageInvoiceFee.setTotalAmount(
      StringUtil.getBigDecimalNumber(new Double(carry_fee)));

    setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

    return tplMessageInvoiceFee;
  }

  public TplMessageInvoiceFee forRailWayInsureFee(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee insureFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);

    insureFee.setFeeTypeCode("3201");

    insureFee.setFeeTypeName("保险费");

    double grossWeight = getWeightByCondition(tplRealFee);
    insureFee.setGrossWeight(StringUtil.getBigDecimalNumber(
      new Double(grossWeight)));

    double totalInsurMoney = tplRealFee.getTotalInsurMoney() == null ? 0.0D : 
      tplRealFee.getTotalInsurMoney().doubleValue();
    totalInsurMoney = StringUtil.round(totalInsurMoney, 2);
    insureFee.setTotalAmount(StringUtil.getBigDecimalNumber(
      new Double(totalInsurMoney)));

    return insureFee;
  }

  public List forRailWayEarnestFee(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    List resultList = new ArrayList();

    PlanFeeSearchModel planFeeSearchModel = new PlanFeeSearchModel();
    planFeeSearchModel.setPlanId(tplRealFee.getPlanId());
    planFeeSearchModel.setOrderNum(tplRealFee.getOrderNum());
    String feeCode = tplRealFee.getFeeTypeCode();
    if ((feeCode != null) && (feeCode.equals("3203"))) {
      planFeeSearchModel.setFeeTypeCode(tplRealFee.getFeeTypeCode());
    }
    List planFeeList = this.planFeeDao
      .queryPlanFeeListWithArgs(planFeeSearchModel);
    for (Iterator it = planFeeList.iterator(); it.hasNext(); ) {
      TplPlanFee tplPlanFee = (TplPlanFee)it.next();

      if ((tplPlanFee.getUnitPrice() == null) || 
        (tplPlanFee.getUnitPrice().doubleValue() <= 0.0D))
        continue;
      TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
        tplRealFee, tplMessageInvoice);

      tplMessageInvoiceFee.setFeeTypeCode(tplPlanFee
        .getFeeTypeCode());

      tplMessageInvoiceFee.setFeeTypeName(tplPlanFee
        .getFeeTypeName());

      double grossWeight = getWeightByCondition(tplRealFee);
      tplMessageInvoiceFee.setGrossWeight(
        StringUtil.getBigDecimalNumber(new Double(grossWeight)));

      double unitPrice = tplPlanFee.getUnitPrice().doubleValue();

      double avgPrice = tplPlanFee.getUnitTotalMoney()
        .doubleValue() / 
        tplPlanFee.getGrossWeight().doubleValue();

      double contractFee = unitPrice * grossWeight;

      if ("1101".equals(tplPlanFee
        .getFeeTypeCode())) {
        contractFee = getStockFeeTotalMoney(avgPrice, 
          grossWeight, contractFee);
      }

      contractFee = StringUtil.round(contractFee, 2);
      tplMessageInvoiceFee.setTotalAmount(
        StringUtil.getBigDecimalNumber(new Double(contractFee)));

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, 
        tplRealFee);

      resultList.add(tplMessageInvoiceFee);
    }

    return resultList;
  }

  public double getStockFeeTotalMoney(double avgPrice, double grossWeight, double totalMoney)
  {
    totalMoney = avgPrice * grossWeight;
    totalMoney = StringUtil.round(totalMoney, 2);
    return totalMoney;
  }

  public TplMessageInvoiceFee generateTplMessageInvoiceFee(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
    tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

    tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
      .getInvoiceSysId());

    tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice.getInvoiceNo());

    tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice.getInvoiceId());

    if ("BGSQ"
      .equals(tplMessageInvoice.getUnitId()))
    {
      tplMessageInvoiceFee.setPlanId(tplRealFee.getPickNum());
    }
    else {
      tplMessageInvoiceFee.setPlanId(tplRealFee.getPlanId());
    }

    tplMessageInvoiceFee.setCompanyCode(tplRealFee.getCompanyCode());

    tplMessageInvoiceFee.setOrderNum(tplRealFee.getOrderNum());

    tplMessageInvoiceFee.setInsurId(tplRealFee.getInsurId());

    return tplMessageInvoiceFee;
  }

  public HSSFWorkbook queryDownInvoiceExcel(StockInvoiceMgrSearchModel searchmodel)
    throws Exception
  {
    List invoiceList = new ArrayList();

    HSSFWorkbook wb = new HSSFWorkbook();

    String[] headers = { "发票代号", "发票号", "发票类型", "发票抬头", 
      "发票抬头代码", "发票抬头单位税号", "发票抬头单位地址", "发票抬头单位联系方式", "发票抬头单位开户行", 
      "发票抬头单位银行账号", "合同号", "毛重", "净重", "件数", "税前金额", "税额", "价税合计", 
      "保险费", "印花税", "费用合计", "所属地区公司代码", "所属地区公司", "所属地区公司税号", "开票日期", 
      "收货单位名称", "收货单位代码", "收货单位税号", "船批号", "船名", "车皮号", "货票编号", 
      "始发站名称", "终到站名称", "发票系统ID" };

    HSSFSheet sheet = wb.createSheet("invoice");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }

    searchmodel.setPage(null);
    List list = this.invoicedao.queryStockInvoiceMgrListForPage(searchmodel);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }
    int rowNum = 1;
    for (Iterator it = list.iterator(); it.hasNext(); )
    {
      HSSFRow row2 = sheet.createRow(rowNum);

      TplInvoice tplInvoice = (TplInvoice)it.next();

      int k = 0;

      String invoiceId = tplInvoice.getInvoiceId();
      if (invoiceId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceId);
      }
      k++;

      String invoiceNo = tplInvoice.getInvoiceNo();
      if (invoiceNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceNo);
      }
      k++;

      String invoiceType = tplInvoice.getInvoiceType();
      if (invoiceType != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        if (invoiceType.equals("4")) {
          csCell.setCellValue("水运");
        }
        else if (invoiceType
          .equals("0")) {
          csCell.setCellValue("仓储");
        }
        else if (invoiceType
          .equals("1")) {
          csCell.setCellValue("铁运");
        }
        else if (invoiceType
          .equals("3")) {
          csCell.setCellValue("汽运");
        }
        else if (invoiceType
          .equals("2")) {
          csCell.setCellValue("海洋");
        }
        else if (invoiceType
          .equals("5"))
          csCell.setCellValue("联运");
        else {
          csCell.setCellValue(invoiceType);
        }
      }
      k++;

      String invoiceTitleName = tplInvoice.getInvoiceTitleName();
      if (invoiceTitleName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleName);
      }
      k++;

      String invoiceTitleCode = tplInvoice.getInvoiceTitleCode();
      if (invoiceTitleCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleCode);
      }
      k++;

      String invoiceTitleTaxNo = tplInvoice.getInvoiceTitleTaxNo();
      if (invoiceTitleTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleTaxNo);
      }

      k++;
      String userChineseAddress = tplInvoice.getUserChineseAddress();
      if (userChineseAddress != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userChineseAddress);
      }

      k++;
      String userTelephoneNum = tplInvoice.getUserTelephoneNum();
      if (userTelephoneNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userTelephoneNum);
      }

      k++;
      String userBankBranchName = tplInvoice.getUserBankBranchName();
      if (userBankBranchName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userBankBranchName);
      }

      k++;
      String userAccountNum = tplInvoice.getUserAccountNum();
      if (userAccountNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userAccountNum);
      }

      k++;

      String orderNum = tplInvoice.getOrderNum();
      if (orderNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderNum);
      }
      k++;

      Double grossWeight = tplInvoice.getGrossWeight();
      if (grossWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(grossWeight.doubleValue());
      }
      k++;

      Double netWeight = tplInvoice.getNetWeight();
      if (netWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(netWeight.doubleValue());
      }
      k++;

      Long actcount = tplInvoice.getActCount();
      if (actcount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(actcount.longValue());
      }

      k++;
      Double transCharges = tplInvoice.getTransCharges();
      if (transCharges != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(transCharges.doubleValue());
      }
      k++;

      Double taxAmount = tplInvoice.getTaxAmount();
      if (taxAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxAmount.doubleValue());
      }

      Double insurTotalMoney = tplInvoice.getInsurTotalMoney();
      BigDecimal insurTotalMoneyTemp = 
        StringUtil.getBigDecimalNumber(insurTotalMoney);
      Double taxYh = tplInvoice.getTaxYh();
      BigDecimal taxYhTemp = StringUtil.getBigDecimalNumber(taxYh);
      Double totalAmount = tplInvoice.getTotalAmount();
      BigDecimal totalAmountTemp = 
        StringUtil.getBigDecimalNumber(totalAmount);
      k++;

      double transMoney = 0.0D;
      if ("4".equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .subtract(taxYhTemp).doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if ("1"
        .equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if (!"0"
        .equals(invoiceType))
      {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmountTemp.doubleValue());
      }
      k++;

      if (insurTotalMoney != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(insurTotalMoney.doubleValue());
      }

      k++;

      if (taxYh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxYh.doubleValue());
      }

      k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      }

      k++;

      String ssdqgsdm = "";
      if (ssdqgsdm != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(ssdqgsdm);
      }

      k++;

      String orderUserType = tplInvoice.getOrderUserType();
      String orderUserName = "";
      if (orderUserType != null) {
        if (SystemConfigUtil.ORDER_USER_TYPE.containsKey(orderUserType))
          orderUserName = (String)SystemConfigUtil.ORDER_USER_TYPE
            .get(orderUserType);
        else {
          orderUserName = "销售中心";
        }
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      } else {
        orderUserName = "销售中心";

        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      }

      k++;

      String sh = "";
      if (sh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(sh);
      }

      k++;

      String createDate = DateUtils.toString(tplInvoice.getInvoiceDate(), 
        "yyyy-MM-dd");
      if (createDate != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(createDate);
      }
      k++;

      String consigneeName = tplInvoice.getConsigneeName();
      if (consigneeName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeName);
      }
      k++;

      String consigneeCode = tplInvoice.getConsigneeCode();
      if (consigneeCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeCode);
      }
      k++;

      String consigneeTaxNo = tplInvoice.getConsigneeTaxNo();
      if (consigneeTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeTaxNo);
      }
      k++;

      String shipId = tplInvoice.getShipId();
      if (shipId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipId);
      }
      k++;

      String shipName = tplInvoice.getShipName();
      if (shipName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipName);
      }
      k++;

      String trainNo = tplInvoice.getTrainNo();
      if (trainNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(trainNo);
      }
      k++;

      String goodBillCode = tplInvoice.getGoodBillCode();
      if (goodBillCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(goodBillCode);
      }
      k++;

      String ladingSpotName = tplInvoice.getLadingSpotName();
      if (ladingSpotName != null) {
        HSSFCell csCell0 = row2.createCell((short)k);
        csCell0.setEncoding(1);

        csCell0.setCellValue(ladingSpotName);
      }
      k++;

      String destSpotName = tplInvoice.getDestSpotName();
      if (destSpotName != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);

        csCell1.setCellValue(destSpotName);
      }
      k++;

      Long id = tplInvoice.getId();
      if (id != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);
        csCell1.setCellValue(id.toString());
      }

      k++;

      rowNum++;
    }

    HSSFSheet sheet2 = queryDownInvoiceDetailExcel(wb, list);

    return wb;
  }

  public HSSFWorkbook queryDownTnsuranceInvoiceExcel(StockInvoiceMgrSearchModel searchmodel) throws Exception
  {
    List invoiceList = new ArrayList();

    HSSFWorkbook wb = new HSSFWorkbook();

    String[] headers = { "发票代号", "发票号", "发票类型", "发票抬头", 
      "发票抬头代码", "发票抬头单位税号", "发票抬头单位地址", "发票抬头单位联系方式", "发票抬头单位开户行", 
      "发票抬头单位银行账号", "合同号", "毛重", "净重", "件数", "税前金额", "税额", "价税合计", 
      "保险费", "印花税", "费用合计", "所属地区公司代码", "所属地区公司", "所属地区公司税号", "开票日期", 
      "收货单位名称", "收货单位代码", "收货单位税号", "船批号", "船名", "车皮号", "货票编号", 
      "始发站名称", "终到站名称", "发票系统ID" };

    HSSFSheet sheet = wb.createSheet("insuranceInvoice");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }

    searchmodel.setPage(null);
    searchmodel.setFunctionCode("insur");
    List list = this.invoicedao.queryStockInvoiceMgrListForPage(searchmodel);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }
    int rowNum = 1;
    for (Iterator it = list.iterator(); it.hasNext(); )
    {
      HSSFRow row2 = sheet.createRow(rowNum);

      TplInvoice tplInvoice = (TplInvoice)it.next();

      int k = 0;

      String invoiceId = tplInvoice.getInvoiceId();
      if (invoiceId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceId);
      }
      k++;

      String invoiceNo = tplInvoice.getInvoiceNo();
      if (invoiceNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceNo);
      }
      k++;

      String invoiceType = tplInvoice.getInvoiceType();
      if (invoiceType != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        if (invoiceType
          .equals("8")) {
          csCell.setCellValue("增值税专用发票");
        }
        else if (invoiceType
          .equals("9")) {
          csCell.setCellValue("运输业增值税专用发票");
        }
      }
      k++;

      String invoiceTitleName = tplInvoice.getInvoiceTitleName();
      if (invoiceTitleName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleName);
      }
      k++;

      String invoiceTitleCode = tplInvoice.getInvoiceTitleCode();
      if (invoiceTitleCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleCode);
      }
      k++;

      String invoiceTitleTaxNo = tplInvoice.getInvoiceTitleTaxNo();
      if (invoiceTitleTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleTaxNo);
      }

      k++;
      String userChineseAddress = tplInvoice.getUserChineseAddress();
      if (userChineseAddress != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userChineseAddress);
      }

      k++;
      String userTelephoneNum = tplInvoice.getUserTelephoneNum();
      if (userTelephoneNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userTelephoneNum);
      }

      k++;
      String userBankBranchName = tplInvoice.getUserBankBranchName();
      if (userBankBranchName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userBankBranchName);
      }

      k++;
      String userAccountNum = tplInvoice.getUserAccountNum();
      if (userAccountNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userAccountNum);
      }

      k++;

      String orderNum = tplInvoice.getOrderNum();
      if (orderNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderNum);
      }
      k++;

      Double grossWeight = tplInvoice.getGrossWeight();
      if (grossWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(grossWeight.doubleValue());
      }
      k++;

      Double netWeight = tplInvoice.getNetWeight();
      if (netWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(netWeight.doubleValue());
      }
      k++;

      Long actcount = tplInvoice.getActCount();
      if (actcount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(actcount.longValue());
      }

      k++;
      Double transCharges = tplInvoice.getTransCharges();
      if (transCharges != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(transCharges.doubleValue());
      }
      k++;

      Double taxAmount = tplInvoice.getTaxAmount();
      if (taxAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxAmount.doubleValue());
      }

      Double insurTotalMoney = tplInvoice.getInsurTotalMoney();
      BigDecimal insurTotalMoneyTemp = 
        StringUtil.getBigDecimalNumber(insurTotalMoney);
      Double taxYh = tplInvoice.getTaxYh();
      BigDecimal taxYhTemp = StringUtil.getBigDecimalNumber(taxYh);
      Double totalAmount = tplInvoice.getTotalAmount();
      BigDecimal totalAmountTemp = 
        StringUtil.getBigDecimalNumber(totalAmount);
      k++;

      double transMoney = 0.0D;
      if ("4".equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .subtract(taxYhTemp).doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if ("1"
        .equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if (!"0"
        .equals(invoiceType))
      {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmountTemp.doubleValue());
      }
      k++;

      if (insurTotalMoney != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(insurTotalMoney.doubleValue());
      }

      k++;

      if (taxYh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxYh.doubleValue());
      }

      k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      }

      k++;

      String ssdqgsdm = "";
      if (ssdqgsdm != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(ssdqgsdm);
      }

      k++;

      String orderUserType = tplInvoice.getOrderUserType();
      String orderUserName = "";
      if (orderUserType != null) {
        if (SystemConfigUtil.ORDER_USER_TYPE.containsKey(orderUserType))
          orderUserName = (String)SystemConfigUtil.ORDER_USER_TYPE
            .get(orderUserType);
        else {
          orderUserName = "销售中心";
        }
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      } else {
        orderUserName = "销售中心";

        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      }

      k++;

      String sh = "";
      if (sh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(sh);
      }

      k++;

      String createDate = DateUtils.toString(tplInvoice.getInvoiceDate(), 
        "yyyy-MM-dd");
      if (createDate != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(createDate);
      }
      k++;

      String consigneeName = tplInvoice.getConsigneeName();
      if (consigneeName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeName);
      }
      k++;

      String consigneeCode = tplInvoice.getConsigneeCode();
      if (consigneeCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeCode);
      }
      k++;

      String consigneeTaxNo = tplInvoice.getConsigneeTaxNo();
      if (consigneeTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeTaxNo);
      }
      k++;

      String shipId = tplInvoice.getShipId();
      if (shipId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipId);
      }
      k++;

      String shipName = tplInvoice.getShipName();
      if (shipName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipName);
      }
      k++;

      String trainNo = tplInvoice.getTrainNo();
      if (trainNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(trainNo);
      }
      k++;

      String goodBillCode = tplInvoice.getGoodBillCode();
      if (goodBillCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(goodBillCode);
      }
      k++;

      String ladingSpotName = tplInvoice.getLadingSpotName();
      if (ladingSpotName != null) {
        HSSFCell csCell0 = row2.createCell((short)k);
        csCell0.setEncoding(1);

        csCell0.setCellValue(ladingSpotName);
      }
      k++;

      String destSpotName = tplInvoice.getDestSpotName();
      if (destSpotName != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);

        csCell1.setCellValue(destSpotName);
      }
      k++;

      Long id = tplInvoice.getId();
      if (id != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);
        csCell1.setCellValue(id.toString());
      }
      k++;

      rowNum++;
    }

    String id = searchmodel.getIds()[0];
    List list1 = this.realfeedao.queryFeeDetialByInvoiceBill(id, null);
    HSSFSheet sheet2 = queryDownInsuranceInvoiceDetailExcel(wb, list1);

    return wb;
  }

  public HSSFWorkbook queryDownInvoiceExcelForRail6(StockInvoiceMgrSearchModel searchmodel) throws Exception
  {
    List invoiceList = new ArrayList();

    HSSFWorkbook wb = new HSSFWorkbook();

    String[] headers = { "发票代号", "发票号", "发票类型", "发票抬头", 
      "发票抬头代码", "发票抬头单位税号", "发票抬头单位地址", "发票抬头单位联系方式", "发票抬头单位开户行", 
      "发票抬头单位银行账号", "合同号", "毛重", "净重", "件数", "税前金额", "税额", "价税合计", 
      "保险费", "印花税", "费用合计", "所属地区公司代码", "所属地区公司", "所属地区公司税号", "开票日期", 
      "收货单位名称", "收货单位代码", "收货单位税号", "船批号", "船名", "车皮号", "货票编号", 
      "始发站名称", "终到站名称", "发票系统ID", "货票运费总计", "保价费", "货票运费金额", 
      "货票铁建基金", "服务费", "加固费", "绳网费", "铁路护路联防费", "其它费用", "发票总金额", 
      "码单号/合同号/净重" };

    HSSFSheet sheet = wb.createSheet("invoice");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }

    searchmodel.setPage(null);
    List list = this.invoicedao.queryStockInvoiceMgrListForPage(searchmodel);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }
    int rowNum = 1;
    for (Iterator it = list.iterator(); it.hasNext(); )
    {
      HSSFRow row2 = sheet.createRow(rowNum);

      TplInvoice tplInvoice = (TplInvoice)it.next();

      int k = 0;

      String invoiceId = tplInvoice.getInvoiceId();
      if (invoiceId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceId);
      }
      k++;

      String invoiceNo = tplInvoice.getInvoiceNo();
      if (invoiceNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceNo);
      }
      k++;

      String invoiceType = tplInvoice.getInvoiceType();
      if (invoiceType != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        if (invoiceType.equals("4")) {
          csCell.setCellValue("水运");
        }
        else if (invoiceType
          .equals("0")) {
          csCell.setCellValue("仓储");
        }
        else if (invoiceType
          .equals("1")) {
          csCell.setCellValue("铁运");
        }
        else if (invoiceType
          .equals("3")) {
          csCell.setCellValue("汽运");
        }
        else if (invoiceType
          .equals("2")) {
          csCell.setCellValue("海洋");
        }
        else if (invoiceType
          .equals("5"))
          csCell.setCellValue("联运");
        else {
          csCell.setCellValue(invoiceType);
        }
      }
      k++;

      String invoiceTitleName = tplInvoice.getInvoiceTitleName();
      if (invoiceTitleName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleName);
      }
      k++;

      String invoiceTitleCode = tplInvoice.getInvoiceTitleCode();
      if (invoiceTitleCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleCode);
      }
      k++;

      String invoiceTitleTaxNo = tplInvoice.getInvoiceTitleTaxNo();
      if (invoiceTitleTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleTaxNo);
      }

      k++;
      String userChineseAddress = tplInvoice.getUserChineseAddress();
      if (userChineseAddress != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userChineseAddress);
      }

      k++;
      String userTelephoneNum = tplInvoice.getUserTelephoneNum();
      if (userTelephoneNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userTelephoneNum);
      }

      k++;
      String userBankBranchName = tplInvoice.getUserBankBranchName();
      if (userBankBranchName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userBankBranchName);
      }

      k++;
      String userAccountNum = tplInvoice.getUserAccountNum();
      if (userAccountNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userAccountNum);
      }

      k++;

      String orderNum = tplInvoice.getOrderNum();
      if (orderNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderNum);
      }
      k++;

      Double grossWeight = tplInvoice.getGrossWeight();
      if (grossWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(grossWeight.doubleValue());
      }
      k++;

      Double netWeight = tplInvoice.getNetWeight();
      if (netWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(netWeight.doubleValue());
      }
      k++;

      Long actcount = tplInvoice.getActCount();
      if (actcount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(actcount.longValue());
      }

      k++;
      Double transCharges = tplInvoice.getTransCharges();
      if (transCharges != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(transCharges.doubleValue());
      }
      k++;

      Double taxAmount = tplInvoice.getTaxAmount();
      if (taxAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxAmount.doubleValue());
      }

      Double insurTotalMoney = tplInvoice.getInsurTotalMoney();
      BigDecimal insurTotalMoneyTemp = 
        StringUtil.getBigDecimalNumber(insurTotalMoney);
      Double taxYh = tplInvoice.getTaxYh();
      BigDecimal taxYhTemp = StringUtil.getBigDecimalNumber(taxYh);
      Double totalAmount = tplInvoice.getTotalAmount();
      BigDecimal totalAmountTemp = 
        StringUtil.getBigDecimalNumber(totalAmount);
      k++;

      double transMoney = 0.0D;
      if ("4".equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .subtract(taxYhTemp).doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if ("1"
        .equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if (!"0"
        .equals(invoiceType))
      {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmountTemp.doubleValue());
      }
      k++;

      if (insurTotalMoney != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(insurTotalMoney.doubleValue());
      }

      k++;

      if (taxYh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxYh.doubleValue());
      }

      k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      }

      k++;

      String ssdqgsdm = "";
      if (ssdqgsdm != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(ssdqgsdm);
      }

      k++;

      String orderUserType = tplInvoice.getOrderUserType();
      String orderUserName = "";
      if (orderUserType != null) {
        if (SystemConfigUtil.ORDER_USER_TYPE.containsKey(orderUserType))
          orderUserName = (String)SystemConfigUtil.ORDER_USER_TYPE
            .get(orderUserType);
        else {
          orderUserName = "销售中心";
        }
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      } else {
        orderUserName = "销售中心";

        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      }

      k++;

      String sh = "";
      if (sh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(sh);
      }

      k++;

      String createDate = DateUtils.toString(tplInvoice.getCreateDate(), 
        "yyyy-MM-dd");
      if (createDate != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(createDate);
      }
      k++;

      String consigneeName = tplInvoice.getConsigneeName();
      if (consigneeName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeName);
      }
      k++;

      String consigneeCode = tplInvoice.getConsigneeCode();
      if (consigneeCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeCode);
      }
      k++;

      String consigneeTaxNo = tplInvoice.getConsigneeTaxNo();
      if (consigneeTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeTaxNo);
      }
      k++;

      String shipId = tplInvoice.getShipId();
      if (shipId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipId);
      }
      k++;

      String shipName = tplInvoice.getShipName();
      if (shipName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipName);
      }
      k++;

      String trainNo = tplInvoice.getTrainNo();
      if (trainNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(trainNo);
      }
      k++;

      String goodBillCode = tplInvoice.getGoodBillCode();
      if (goodBillCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(goodBillCode);
      }
      k++;

      String ladingSpotName = tplInvoice.getLadingSpotName();
      if (ladingSpotName != null) {
        HSSFCell csCell0 = row2.createCell((short)k);
        csCell0.setEncoding(1);

        csCell0.setCellValue(ladingSpotName);
      }
      k++;

      String destSpotName = tplInvoice.getDestSpotName();
      if (destSpotName != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);

        csCell1.setCellValue(destSpotName);
      }
      k++;

      Long id = tplInvoice.getId();
      if (id != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);
        csCell1.setCellValue(id.toString());
      }

      List feeList = this.realfeedao.queryRailFeeDetialByInvoiceId(id
        .toString());
      if ((feeList != null) && (feeList.size() > 0)) {
        Object[] items = (Object[])feeList.get(0);

        k++;

        double transportFee = items[0] == null ? 0.0D : 
          Double.parseDouble(items[0].toString());
        HSSFCell transportFeeCell = row2.createCell((short)k);
        transportFeeCell.setEncoding(1);
        transportFeeCell.setCellValue(transportFee);

        k++;

        double insurancePriceFee = items[1] == null ? 0.0D : 
          Double.parseDouble(items[1].toString());
        HSSFCell insurancePriceFeeCell = row2.createCell((short)k);
        insurancePriceFeeCell.setEncoding(1);
        insurancePriceFeeCell.setCellValue(insurancePriceFee);

        k++;

        double billTransFee = items[2] == null ? 0.0D : 
          Double.parseDouble(items[2].toString());
        HSSFCell billTransFeeCell = row2.createCell((short)k);
        billTransFeeCell.setEncoding(1);
        billTransFeeCell.setCellValue(billTransFee);

        k++;

        double conFee = items[3] == null ? 0.0D : 
          Double.parseDouble(items[3].toString());
        HSSFCell conFeeCell = row2.createCell((short)k);
        conFeeCell.setEncoding(1);
        conFeeCell.setCellValue(conFee);

        k++;

        double serviceFee = items[4] == null ? 0.0D : 
          Double.parseDouble(items[4].toString());
        HSSFCell serviceFeeCell = row2.createCell((short)k);
        serviceFeeCell.setEncoding(1);
        serviceFeeCell.setCellValue(serviceFee);

        k++;

        double fixFee = items[5] == null ? 0.0D : 
          Double.parseDouble(items[5].toString());
        HSSFCell fixFeeCell = row2.createCell((short)k);
        fixFeeCell.setEncoding(1);
        fixFeeCell.setCellValue(fixFee);

        k++;

        double ropeFee = items[6] == null ? 0.0D : 
          Double.parseDouble(items[6].toString());
        HSSFCell ropeFeeCell = row2.createCell((short)k);
        ropeFeeCell.setEncoding(1);
        ropeFeeCell.setCellValue(ropeFee);

        k++;

        double saveFee = items[7] == null ? 0.0D : 
          Double.parseDouble(items[7].toString());
        HSSFCell saveFeeCell = row2.createCell((short)k);
        saveFeeCell.setEncoding(1);
        saveFeeCell.setCellValue(saveFee);

        k++;

        double otherFee = items[8] == null ? 0.0D : 
          Double.parseDouble(items[8].toString());
        HSSFCell otherFeeCell = row2.createCell((short)k);
        otherFeeCell.setEncoding(1);
        otherFeeCell.setCellValue(otherFee);
      }

      k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      }

      k++;
      String numWeight = "";

      List listTemp = this.realfeedao.queryNumberAndWeightByInvoiceId(id
        .toString());

      if ((listTemp != null) && (listTemp.size() > 0)) {
        for (int i = 0; i < listTemp.size(); i++) {
          Object[] temp2 = (Object[])listTemp.get(i);

          numWeight = numWeight + temp2[1] + "/" + temp2[0] + "/" + 
            temp2[3];
          if (i < listTemp.size() - 1) {
            numWeight = numWeight + ",";
          }
        }
      }

      if ("".equals(numWeight)) {
        numWeight = "0";
      }

      if (numWeight != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);
        csCell1.setCellValue(numWeight);
      }

      rowNum++;
    }

    HSSFSheet sheet2 = queryDownInvoiceDetailExcel(wb, list);

    return wb;
  }

  public HSSFWorkbook queryDownInvoiceExcelForRail6DT(StockInvoiceMgrSearchModel searchmodel) throws Exception
  {
    List invoiceList = new ArrayList();

    HSSFWorkbook wb = new HSSFWorkbook();

    String[] headers = { "发票代号", "发票号", "发票类型", "发票抬头", 
      "发票抬头代码", "发票抬头单位税号", "发票抬头单位地址", "发票抬头单位联系方式", "发票抬头单位开户行", 
      "发票抬头单位银行账号", "合同号", "毛重", "净重", "件数", "税前金额", "税额", "价税合计", 
      "保险费", "印花税", "费用合计", "所属地区公司代码", "所属地区公司", "所属地区公司税号", "开票日期", 
      "收货单位名称", "收货单位代码", "收货单位税号", "船批号", "船名", "车皮号", "货票编号", 
      "始发站名称", "终到站名称", "发票系统ID", "货票运费总计", "保价费", "货票运费金额", 
      "货票铁建基金", "服务费", "加固费", "绳网费", "铁路护路联防费", "其它费用", "铁路吊装费", 
      "汽运运费", "出入库费", "仓储费", "发票总金额", "码单号/合同号/净重" };

    HSSFSheet sheet = wb.createSheet("invoice");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }

    searchmodel.setPage(null);
    List list = this.invoicedao.queryStockInvoiceMgrListForPage(searchmodel);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }
    int rowNum = 1;
    for (Iterator it = list.iterator(); it.hasNext(); )
    {
      HSSFRow row2 = sheet.createRow(rowNum);

      TplInvoice tplInvoice = (TplInvoice)it.next();

      int k = 0;

      String invoiceId = tplInvoice.getInvoiceId();
      if (invoiceId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceId);
      }
      k++;

      String invoiceNo = tplInvoice.getInvoiceNo();
      if (invoiceNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceNo);
      }
      k++;

      String invoiceType = tplInvoice.getInvoiceType();
      if (invoiceType != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        if (invoiceType.equals("4")) {
          csCell.setCellValue("水运");
        }
        else if (invoiceType
          .equals("0")) {
          csCell.setCellValue("仓储");
        }
        else if (invoiceType
          .equals("1")) {
          csCell.setCellValue("铁运");
        }
        else if (invoiceType
          .equals("3")) {
          csCell.setCellValue("汽运");
        }
        else if (invoiceType
          .equals("2")) {
          csCell.setCellValue("海洋");
        }
        else if (invoiceType
          .equals("5"))
          csCell.setCellValue("联运");
        else {
          csCell.setCellValue(invoiceType);
        }
      }
      k++;

      String invoiceTitleName = tplInvoice.getInvoiceTitleName();
      if (invoiceTitleName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleName);
      }
      k++;

      String invoiceTitleCode = tplInvoice.getInvoiceTitleCode();
      if (invoiceTitleCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleCode);
      }
      k++;

      String invoiceTitleTaxNo = tplInvoice.getInvoiceTitleTaxNo();
      if (invoiceTitleTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceTitleTaxNo);
      }

      k++;
      String userChineseAddress = tplInvoice.getUserChineseAddress();
      if (userChineseAddress != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userChineseAddress);
      }

      k++;
      String userTelephoneNum = tplInvoice.getUserTelephoneNum();
      if (userTelephoneNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userTelephoneNum);
      }

      k++;
      String userBankBranchName = tplInvoice.getUserBankBranchName();
      if (userBankBranchName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userBankBranchName);
      }

      k++;
      String userAccountNum = tplInvoice.getUserAccountNum();
      if (userAccountNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(userAccountNum);
      }

      k++;

      String orderNum = tplInvoice.getOrderNum();
      if (orderNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderNum);
      }
      k++;

      Double grossWeight = tplInvoice.getGrossWeight();
      if (grossWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(grossWeight.doubleValue());
      }
      k++;

      Double netWeight = tplInvoice.getNetWeight();
      if (netWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(netWeight.doubleValue());
      }
      k++;

      Long actcount = tplInvoice.getActCount();
      if (actcount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(actcount.longValue());
      }

      k++;
      Double transCharges = tplInvoice.getTransCharges();
      if (transCharges != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(transCharges.doubleValue());
      }
      k++;

      Double taxAmount = tplInvoice.getTaxAmount();
      if (taxAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxAmount.doubleValue());
      }

      Double insurTotalMoney = tplInvoice.getInsurTotalMoney();
      BigDecimal insurTotalMoneyTemp = 
        StringUtil.getBigDecimalNumber(insurTotalMoney);
      Double taxYh = tplInvoice.getTaxYh();
      BigDecimal taxYhTemp = StringUtil.getBigDecimalNumber(taxYh);
      Double totalAmount = tplInvoice.getTotalAmount();
      BigDecimal totalAmountTemp = 
        StringUtil.getBigDecimalNumber(totalAmount);
      k++;

      double transMoney = 0.0D;
      if ("4".equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .subtract(taxYhTemp).doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if ("1"
        .equals(invoiceType)) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        transMoney = totalAmountTemp.subtract(insurTotalMoneyTemp)
          .doubleValue();
        csCell.setCellValue(transMoney);
      }
      else if (!"0"
        .equals(invoiceType))
      {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmountTemp.doubleValue());
      }
      k++;

      if (insurTotalMoney != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(insurTotalMoney.doubleValue());
      }

      k++;

      if (taxYh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(taxYh.doubleValue());
      }

      k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      }

      k++;

      String ssdqgsdm = "";
      if (ssdqgsdm != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(ssdqgsdm);
      }

      k++;

      String orderUserType = tplInvoice.getOrderUserType();
      String orderUserName = "";
      if (orderUserType != null) {
        if (SystemConfigUtil.ORDER_USER_TYPE.containsKey(orderUserType))
          orderUserName = (String)SystemConfigUtil.ORDER_USER_TYPE
            .get(orderUserType);
        else {
          orderUserName = "销售中心";
        }
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      } else {
        orderUserName = "销售中心";

        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderUserName);
      }

      k++;

      String sh = "";
      if (sh != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(sh);
      }

      k++;

      String createDate = DateUtils.toString(tplInvoice.getCreateDate(), 
        "yyyy-MM-dd");
      if (createDate != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(createDate);
      }
      k++;

      String consigneeName = tplInvoice.getConsigneeName();
      if (consigneeName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeName);
      }
      k++;

      String consigneeCode = tplInvoice.getConsigneeCode();
      if (consigneeCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeCode);
      }
      k++;

      String consigneeTaxNo = tplInvoice.getConsigneeTaxNo();
      if (consigneeTaxNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(consigneeTaxNo);
      }
      k++;

      String shipId = tplInvoice.getShipId();
      if (shipId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipId);
      }
      k++;

      String shipName = tplInvoice.getShipName();
      if (shipName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(shipName);
      }
      k++;

      String trainNo = tplInvoice.getTrainNo();
      if (trainNo != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(trainNo);
      }
      k++;

      String goodBillCode = tplInvoice.getGoodBillCode();
      if (goodBillCode != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);

        csCell.setCellValue(goodBillCode);
      }
      k++;

      String ladingSpotName = tplInvoice.getLadingSpotName();
      if (ladingSpotName != null) {
        HSSFCell csCell0 = row2.createCell((short)k);
        csCell0.setEncoding(1);

        csCell0.setCellValue(ladingSpotName);
      }
      k++;

      String destSpotName = tplInvoice.getDestSpotName();
      if (destSpotName != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);

        csCell1.setCellValue(destSpotName);
      }
      k++;

      Long id = tplInvoice.getId();
      if (id != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);
        csCell1.setCellValue(id.toString());
      }

      List feeList = this.realfeedao.queryRailFeeDetialByInvoiceId(id
        .toString());
      if ((feeList != null) && (feeList.size() > 0)) {
        Object[] items = (Object[])feeList.get(0);

        k++;

        double transportFee = items[0] == null ? 0.0D : 
          Double.parseDouble(items[0].toString());
        HSSFCell transportFeeCell = row2.createCell((short)k);
        transportFeeCell.setEncoding(1);
        transportFeeCell.setCellValue(transportFee);

        k++;

        double insurancePriceFee = items[1] == null ? 0.0D : 
          Double.parseDouble(items[1].toString());
        HSSFCell insurancePriceFeeCell = row2.createCell((short)k);
        insurancePriceFeeCell.setEncoding(1);
        insurancePriceFeeCell.setCellValue(insurancePriceFee);

        k++;

        double billTransFee = items[2] == null ? 0.0D : 
          Double.parseDouble(items[2].toString());
        HSSFCell billTransFeeCell = row2.createCell((short)k);
        billTransFeeCell.setEncoding(1);
        billTransFeeCell.setCellValue(billTransFee);

        k++;

        double conFee = items[3] == null ? 0.0D : 
          Double.parseDouble(items[3].toString());
        HSSFCell conFeeCell = row2.createCell((short)k);
        conFeeCell.setEncoding(1);
        conFeeCell.setCellValue(conFee);

        k++;

        double serviceFee = items[4] == null ? 0.0D : 
          Double.parseDouble(items[4].toString());
        HSSFCell serviceFeeCell = row2.createCell((short)k);
        serviceFeeCell.setEncoding(1);
        serviceFeeCell.setCellValue(serviceFee);

        k++;

        double fixFee = items[5] == null ? 0.0D : 
          Double.parseDouble(items[5].toString());
        HSSFCell fixFeeCell = row2.createCell((short)k);
        fixFeeCell.setEncoding(1);
        fixFeeCell.setCellValue(fixFee);

        k++;

        double ropeFee = items[6] == null ? 0.0D : 
          Double.parseDouble(items[6].toString());
        HSSFCell ropeFeeCell = row2.createCell((short)k);
        ropeFeeCell.setEncoding(1);
        ropeFeeCell.setCellValue(ropeFee);

        k++;

        double saveFee = items[7] == null ? 0.0D : 
          Double.parseDouble(items[7].toString());
        HSSFCell saveFeeCell = row2.createCell((short)k);
        saveFeeCell.setEncoding(1);
        saveFeeCell.setCellValue(saveFee);

        k++;

        double otherFee = items[8] == null ? 0.0D : 
          Double.parseDouble(items[8].toString());
        HSSFCell otherFeeCell = row2.createCell((short)k);
        otherFeeCell.setEncoding(1);
        otherFeeCell.setCellValue(otherFee);
      }

      k++;
      double transportDZFee = 0.0D;
      double transportQYFee = 0.0D;
      double transportCRFee = 0.0D;
      double transportCCFee = 0.0D;
      if (tplInvoice.getGoodBillCode() != null)
      {
        if (SystemConfigUtil.FACTORY_PROVIDER_ID_ST_MAP
          .containsKey(tplInvoice.getProviderId())) {
          List feeList1 = this.realfeedao.queryInvoiceAppleyForMessage(id
            .toString());
          Set invoicFeeItems = new HashSet();
          TplMessageInvoice tplMessageInvoice = new TplMessageInvoice();

          if ((feeList1 == null) || (feeList1.size() <= 0)) break label3851; for (int m = 0; m < feeList1.size(); m++) {
            Object[] object = (Object[])feeList1.get(m);
            TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();

            tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

            tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
              .getInvoiceSysId());

            tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice
              .getInvoiceNo());

            tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice
              .getInvoiceId());

            tplMessageInvoiceFee.setPlanId(object[0] == null ? null : 
              object[0].toString());

            tplMessageInvoiceFee
              .setOrderNum(object[1] == null ? null : 
              object[1].toString());

            String insurId = object[3] == null ? null : object[3]
              .toString();
            tplMessageInvoiceFee.setInsurId(insurId);

            tplMessageInvoiceFee
              .setCompanyCode(object[8] == null ? null : 
              object[8].toString());

            tplMessageInvoiceFee
              .setFeeTypeCode(object[9] == null ? null : 
              object[9].toString());

            tplMessageInvoiceFee
              .setFeeTypeName(object[10] == null ? null : 
              object[10].toString());

            double unitPrice = object[11] == null ? 0.0D : 
              new Double(object[11].toString()).doubleValue();
            unitPrice = StringUtil.round(unitPrice, 2);
            tplMessageInvoiceFee.setUnitPrice(
              StringUtil.getBigDecimalNumber(new Double(unitPrice)));

            tplMessageInvoiceFee.setGrossWeight(
              StringUtil.getBigDecimalNumber(object[5]));

            tplMessageInvoiceFee.setTotalAmount(
              StringUtil.getBigDecimalNumber(object[12]));

            String bill_type = object[17] == null ? null : 
              object[17].toString();

            if ((tplMessageInvoiceFee.getGrossWeight().doubleValue() <= 0.0D) || 
              (tplMessageInvoiceFee.getOrderNum()
              .startsWith("A")) || 
              (tplMessageInvoiceFee.getOrderNum()
              .startsWith("B"))) {
              tplMessageInvoiceFee.setGrossWeight(
                StringUtil.getBigDecimalNumber(object[6]));
              tplMessageInvoiceFee.setTotalAmount(
                StringUtil.getBigDecimalNumber(object[13]));
            }

            String orderType = (String)object[15];
            if (orderType != null) {
              orderType
                .equals("P000");
            }

            String orderNum1 = tplMessageInvoiceFee.getOrderNum();
            if ((orderNum1 != null) && (
              (orderNum1.startsWith("A")) || 
              (orderNum1.startsWith("B"))))
            {
              tplMessageInvoiceFee.setGrossWeight(
                StringUtil.getBigDecimalNumber(object[6]));
              tplMessageInvoiceFee.setTotalAmount(
                StringUtil.getBigDecimalNumber(object[13]));
            }

            if ("10".equals(bill_type))
            {
              if ("1101"
                .equals(tplMessageInvoiceFee
                .getFeeTypeCode())) {
                tplMessageInvoiceFee.setGrossWeight(
                  StringUtil.getBigDecimalNumber(object[5]));
                tplMessageInvoiceFee.setTotalAmount(
                  StringUtil.getBigDecimalNumber(object[16]));
              }

            }

            TplRealFee tplRealFee = (TplRealFee)this.realfeedao
              .queryById(
              Long.valueOf(object[20] == null ? "0" : 
              object[20].toString()), 
              TplRealFee.class);

            setTaxAmountToTplMessageInvoiceFee(
              tplMessageInvoiceFee, tplRealFee);

            invoicFeeItems.add(tplMessageInvoiceFee);
          }

          HashMap feeMap = new HashMap();
          BigDecimal b = new BigDecimal("0");
          Object[] object = invoicFeeItems.toArray();
          for (int d = 0; d < object.length; d++) {
            TplMessageInvoiceFee mdl = (TplMessageInvoiceFee)object[d];
            if (mdl.getFeeTypeName() == null) {
              continue;
            }
            System.out.println(mdl.getFeeTypeName() + "----" + 
              mdl.getTotalAmount());
            if (feeMap.containsKey(mdl.getFeeTypeName())) {
              b = (BigDecimal)feeMap.get(mdl.getFeeTypeName());
              b = b.add(mdl.getTotalAmount());
              feeMap.put(mdl.getFeeTypeName(), b);
            } else {
              feeMap.put(mdl.getFeeTypeName(), mdl
                .getTotalAmount());
            }
          }

          if ((feeMap != null) && (feeMap.size() > 0)) {
            BigDecimal transportDZFeeTemp = (BigDecimal)feeMap
              .get("铁路吊装费");

            if (transportDZFeeTemp != null)
            {
              transportDZFee = 
                Double.parseDouble(transportDZFeeTemp.toString());
            }
            BigDecimal transportQYFeeTemp = (BigDecimal)feeMap
              .get("汽运运费");

            if (transportQYFeeTemp != null) {
              transportQYFee = 
                Double.parseDouble(transportQYFeeTemp.toString());
            }
            BigDecimal transportCRFeeTemp = (BigDecimal)feeMap
              .get("出入库费");
            if (transportCRFeeTemp != null) {
              transportCRFee = 
                Double.parseDouble(transportCRFeeTemp.toString());
            }
            BigDecimal transportCCFeeTemp = (BigDecimal)feeMap
              .get("仓储费");
            if (transportCCFeeTemp != null) {
              transportCCFee = 
                Double.parseDouble(transportCCFeeTemp.toString());
            }

          }

          HSSFCell transportFeeCell = row2.createCell((short)k);
          transportFeeCell.setEncoding(1);
          transportFeeCell.setCellValue(transportDZFee);

          k++;

          HSSFCell insurancePriceFeeCell = row2.createCell((short)k);
          insurancePriceFeeCell.setEncoding(1);
          insurancePriceFeeCell.setCellValue(transportQYFee);

          k++;

          HSSFCell billTransFeeCell = row2.createCell((short)k);
          billTransFeeCell.setEncoding(1);
          billTransFeeCell.setCellValue(transportCRFee);

          k++;

          HSSFCell conFeeCell = row2.createCell((short)k);
          conFeeCell.setEncoding(1);
          conFeeCell.setCellValue(transportCCFee); break label3851;
        }

      }

      HSSFCell transportFeeCell = row2.createCell((short)k);
      transportFeeCell.setEncoding(1);
      transportFeeCell.setCellValue(transportDZFee);

      k++;

      HSSFCell insurancePriceFeeCell = row2.createCell((short)k);
      insurancePriceFeeCell.setEncoding(1);
      insurancePriceFeeCell.setCellValue(transportQYFee);

      k++;

      HSSFCell billTransFeeCell = row2.createCell((short)k);
      billTransFeeCell.setEncoding(1);
      billTransFeeCell.setCellValue(transportCRFee);

      k++;

      HSSFCell conFeeCell = row2.createCell((short)k);
      conFeeCell.setEncoding(1);
      conFeeCell.setCellValue(transportCCFee);

      label3851: k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      }

      k++;
      String numWeight = "";

      List listTemp = this.realfeedao.queryNumberAndWeightByInvoiceId(id
        .toString());

      if ((listTemp != null) && (listTemp.size() > 0)) {
        for (int i = 0; i < listTemp.size(); i++) {
          Object[] temp2 = (Object[])listTemp.get(i);

          numWeight = numWeight + temp2[1] + "/" + temp2[0] + "/" + 
            temp2[3];
          if (i < listTemp.size() - 1) {
            numWeight = numWeight + ",";
          }
        }
      }

      if ("".equals(numWeight)) {
        numWeight = "0";
      }

      if (numWeight != null) {
        HSSFCell csCell1 = row2.createCell((short)k);
        csCell1.setEncoding(1);
        csCell1.setCellValue(numWeight);
      }

      rowNum++;
    }

    HSSFSheet sheet2 = queryDownInvoiceDetailExcel(wb, list);

    return wb;
  }

  public HSSFWorkbook queryDownInvoiceExcelForRail(StockInvoiceMgrSearchModel searchmodel)
    throws Exception
  {
    HSSFWorkbook wb = new HSSFWorkbook();
    String[] headers = { "发票代号", "发票号", "站点名称", "车皮号", "货票号", 
      "发票抬头名称", "税号", "收货单位", "合同号", "品种", "毛重", "净重", "件数", "货票总金额", 
      "货票运费金额", "铁建基金", "保价费", "保险费", "护路联防费", "服务费", "加固费", "网绳费", 
      "发票总金额", "发票生成日期", "所属地区公司", "码单号/合同号/净重", "其他费用", "发票系统ID", 
      "印花税", "购方地址", "电话", "开户行", "开户行账号" };
    String[] headers2 = { "汽运运费", "铁路吊装费", "仓储费", "出入库费" };

    HSSFSheet sheet = wb.createSheet("invoice");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }

    if (searchmodel.getShowFeeFlag()) {
      for (int i = 0; i < headers2.length; i++)
      {
        HSSFCell csCell = row.createCell((short)(headers.length + i));

        csCell.setEncoding(1);

        csCell.setCellValue(headers2[i]);
      }

    }

    searchmodel.setPage(null);
    List list = this.invoicedao.queryInvoiceListForRailExcel(searchmodel);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }
    int rowNum = 1;
    List invoiceList = new ArrayList();
    for (Iterator it = list.iterator(); it.hasNext(); )
    {
      HSSFRow row2 = sheet.createRow(rowNum);
      Object[] temp = (Object[])it.next();
      for (int i = 0; i < temp.length; i++)
      {
        HSSFCell csCell = row2.createCell((short)i);

        csCell.setEncoding(1);
        if (i == 9)
        {
          String orderNum = temp[8] == null ? null : temp[8]
            .toString();

          String productTypeName = temp[9] == null ? "" : temp[9]
            .toString();
          if ((orderNum != null) && (!"".equals(orderNum))) {
            csCell.setEncoding(1);
            csCell.setCellValue(productTypeName);
          }
        } else if (i == 24)
        {
          String orderUserType = temp[24] == null ? null : temp[24]
            .toString();
          if (orderUserType != null)
          {
            csCell.setEncoding(1);
            Object orderUserName = SystemConfigUtil.ORDER_USER_TYPE
              .get(orderUserType);
            csCell.setCellValue(orderUserName == null ? "销售中心" : 
              orderUserName.toString());
          } else {
            csCell.setEncoding(1);
            csCell.setCellValue("销售中心");
          }
        } else if ((10 <= i) && (i <= 22))
        {
          double value = temp[i] == null ? 0.0D : 
            Double.parseDouble(temp[i].toString());
          csCell.setCellValue(value);
        } else if (i == 23) {
          String date = temp[23] == null ? "" : temp[23].toString();
          Date dd = DateUtils.toDate(date, 
            "yyyy-MM-dd");
          String dateTemp = DateUtils.toString(dd, 
            "yyyy-MM-dd");
          csCell.setCellValue(dateTemp);
        }
        else if (temp[i] != null) {
          csCell.setCellValue(String.valueOf(temp[i]));
        }
      }

      String numWeight = "";

      String InvoiceSysId = temp[27] == null ? null : temp[27].toString();
      List listTemp = this.realfeedao
        .queryNumberAndWeightByInvoiceId(InvoiceSysId);

      if ((listTemp != null) && (listTemp.size() > 0)) {
        for (int i = 0; i < listTemp.size(); i++) {
          Object[] temp2 = (Object[])listTemp.get(i);

          numWeight = numWeight + temp2[1] + "/" + temp2[0] + "/" + 
            temp2[3];
          if (i < listTemp.size() - 1) {
            numWeight = numWeight + ",";
          }
        }
      }

      if ("".equals(numWeight)) {
        numWeight = "0";
      }
      short m = (short)(temp.length - 9);
      HSSFCell csCell = row2.createCell(m);

      csCell.setEncoding(1);
      csCell.setCellValue(String.valueOf(numWeight));

      if (searchmodel.getShowFeeFlag()) {
        HashMap feeMap = new HashMap();
        BigDecimal b = new BigDecimal("0");
        Set invoicFeeItems = new HashSet();
        sendInvoiceFeeNoAreaPartDirect(
          new String[] { InvoiceSysId }, 0, 
          new TplMessageInvoice(), invoicFeeItems, 
          new TplInvoice());
        Object[] object = invoicFeeItems.toArray();
        for (int k = 0; k < object.length; k++) {
          TplMessageInvoiceFee mdl = (TplMessageInvoiceFee)object[k];
          if (mdl.getFeeTypeName() == null) {
            continue;
          }
          if (feeMap.containsKey(mdl.getFeeTypeName())) {
            b = (BigDecimal)feeMap.get(mdl.getFeeTypeName());
            b = b.add(mdl.getTotalAmount());
            feeMap.put(mdl.getFeeTypeName(), b);
          } else {
            feeMap.put(mdl.getFeeTypeName(), mdl.getTotalAmount());
          }
        }
        for (int i = 0; i < headers2.length; i++) {
          Iterator its = feeMap.entrySet().iterator();
          while (its.hasNext()) {
            Map.Entry s = (Map.Entry)its.next();
            String feeTypeName = s.getKey().toString();

            csCell = row2.createCell((short)(temp.length + i));
            csCell.setCellValue(0.0D);

            if (headers2[i].equals(feeTypeName)) {
              double fee = new Double(s.getValue().toString())
                .doubleValue();
              fee = StringUtil.round(fee, 2);
              csCell = row2.createCell((short)(temp.length + i));
              csCell.setCellValue(fee);
              break;
            }
          }
        }
      }

      TplInvoice tplInvoice = (TplInvoice)this.realfeedao.queryById(
        Long.valueOf(temp[27].toString()), TplInvoice.class);
      invoiceList.add(tplInvoice);
      rowNum++;
    }
    queryDownInvoiceDetailExcel(wb, invoiceList);

    return wb;
  }

  public HSSFSheet queryDownInsuranceInvoiceDetailExcel(HSSFWorkbook wb, List list)
    throws Exception
  {
    String[] headers = { "作业计划号", "保单号", "合同号", "车皮号", "货票号", 
      "船批号", "船名", "件数", "毛重", "净重", "保险费(RMB)", "收货单位" };

    HSSFSheet sheet = wb.createSheet("insuranceInvoiceDetail");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }
    int rowNum = 1;
    for (int i = 0; i < list.size(); i++) {
      int k = 0;

      HSSFRow row2 = sheet.createRow(rowNum);
      TplInsuranceBill bill = (TplInsuranceBill)list.get(i);

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getTransPlanId());

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getInsurId());

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getOrderNum());

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getTrainNo());

      double grossWeight = 0.0D;
      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getGoodBillCode());

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getShipId());

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      csCell.setCellValue(bill.getShipName());

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      long total_count = bill.getTotalCount() == null ? 0L : bill
        .getTotalCount().longValue();
      csCell.setCellValue(total_count);

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      double gross_weight = bill.getTotalGrossWeight() == null ? 0.0D : 
        bill.getTotalGrossWeight().doubleValue();
      csCell.setCellValue(gross_weight);

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      double net_weight = bill.getTotalNetWeight() == null ? 0.0D : bill
        .getTotalNetWeight().doubleValue();
      csCell.setCellValue(net_weight);

      k++;

      HSSFCell csCell = row2.createCell((short)k);
      csCell.setEncoding(1);

      double total_insuranceMoney = bill
        .getInsuranceTotalNumAdjust() == null ? 
        0.0D : bill
        .getInsuranceTotalNumAdjust().doubleValue();
      csCell.setCellValue(total_insuranceMoney);

      k++;

      if (bill.getConsigneeName() != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(bill.getConsigneeName());
      }

      rowNum++;
    }

    return sheet;
  }

  public HSSFSheet queryDownInvoiceDetailExcel(HSSFWorkbook wb, List list)
    throws Exception
  {
    String[] headers = { "发票号", "作业计划号", "合同号", "品种名称", "毛重", 
      "净重", "件数", "提单号", "单价", "金额", "发票系统ID" };

    HSSFSheet sheet = wb.createSheet("invoiceDetail");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }
    int rowNum = 1;
    for (int m = 0; m < list.size(); m++) {
      TplInvoice tplInvoice = (TplInvoice)list.get(m);
      List listSub = this.realfeedao.queryRealFeeByInvoiceId(tplInvoice
        .getId().toString());
      if ((listSub == null) || (listSub.size() <= 0))
      {
        HSSFRow row2 = sheet.createRow(rowNum);

        HSSFCell csCell = row2.createCell(0);
        csCell.setEncoding(1);

        csCell.setCellValue(tplInvoice.getInvoiceNo());

        rowNum++;
      }
      else
      {
        for (int i = 0; i < listSub.size(); i++) {
          int k = 0;

          HSSFRow row2 = sheet.createRow(rowNum);
          TplRealFee tplRealFee = (TplRealFee)listSub.get(i);

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellValue(tplInvoice.getInvoiceNo());

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellValue(tplRealFee.getPlanId());

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellValue(tplRealFee.getOrderNum());

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellValue(tplRealFee.getProductTypeName());

          double grossWeight = 0.0D;
          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          grossWeight = tplRealFee.getGrossWeight() == null ? 0.0D : 
            tplRealFee.getGrossWeight().doubleValue();
          csCell.setCellValue(grossWeight);

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          double netWeight = tplRealFee.getNetWeight() == null ? 0.0D : 
            tplRealFee.getNetWeight().doubleValue();
          csCell.setCellValue(netWeight);

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          long actCount = tplRealFee.getActCount() == null ? 0L : 
            tplRealFee.getActCount().longValue();
          csCell.setCellValue(actCount);

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellValue(tplRealFee.getPickNum());

          double totalMoney = tplRealFee.getTotalMoney() == null ? 0.0D : 
            tplRealFee.getTotalMoney().doubleValue();
          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          double unit_price = StringUtil.round(0.0D, 2);
          if (grossWeight > 0.0D) {
            double total_insur_money = tplRealFee
              .getTotalInsurMoney() == null ? 
              0.0D : tplRealFee
              .getTotalInsurMoney().doubleValue();

            if ("10".equals(tplInvoice.getInvoiceClassify()))
            {
              unit_price = StringUtil.round(
                (totalMoney - total_insur_money) / 
                grossWeight, 2);
            } else if ("20".equals(tplInvoice.getInvoiceClassify()))
            {
              unit_price = StringUtil.round(totalMoney / 
                grossWeight, 2);
            }
          }

          csCell.setCellValue(unit_price);

          k++;

          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellValue(totalMoney);

          k++;

          Long invoiceSysId = tplRealFee.getInvoiceSysId();
          if (invoiceSysId != null) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);
            csCell.setCellValue(invoiceSysId.toString());
          }

          rowNum++;
        }
      }
    }
    return sheet;
  }

  public HSSFWorkbook queryNumberAndWeightByInvoiceId(String id, String chineseUserName)
    throws Exception
  {
    HSSFWorkbook wb = new HSSFWorkbook();
    String[] headers = { "发票号", "合同号", "金额", "开票日期", "码单号", 
      "毛重", "净重", "件数" };

    HSSFSheet sheet = wb.createSheet("invoice");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }
    TplInvoice tplInvoice = (TplInvoice)this.invoicedao.queryById(
      Long.valueOf(id), TplInvoice.class);

    String invoiceNo = tplInvoice.getInvoiceNo();
    String invoiceId = tplInvoice.getInvoiceId();
    String orderNum = tplInvoice.getOrderNum();
    String InvoiceSysId = tplInvoice.getId().toString();
    Double totalAmount = tplInvoice.getTotalAmount();
    Date invoiceDate = tplInvoice.getInvoiceDate();
    String vehicleCode = "";
    if ("1".equals(tplInvoice
      .getInvoiceType())) {
      vehicleCode = tplInvoice.getTrainNo();
    }
    if ("4".equals(tplInvoice
      .getInvoiceType())) {
      vehicleCode = tplInvoice.getShipId();
    }

    List list = this.realfeedao.queryNumberAndWeightByInvoiceId(InvoiceSysId);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }
    int rowNum = 1;
    for (Iterator it = list.iterator(); it.hasNext(); ) {
      int k = 0;

      HSSFRow row2 = sheet.createRow(rowNum);

      Object[] temp = (Object[])it.next();

      if ((invoiceNo != null) && (!"".equals(invoiceNo.trim()))) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(invoiceNo);
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue("");
      }
      k++;

      if ((temp[0] != null) && (!"".equals(temp[1].toString()))) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(temp[0].toString());
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue("");
      }
      k++;

      if (totalAmount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(totalAmount.doubleValue());
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue("");
      }
      k++;

      if (invoiceDate != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        String dateTemp = DateUtils.toString(invoiceDate, 
          "yyyy-MM-dd");
        csCell.setCellValue(dateTemp);
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue("");
      }
      k++;

      if ((temp[1] != null) && (!"".equals(temp[1].toString()))) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(temp[1].toString());
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue("");
      }
      k++;

      if ((temp[2] != null) && (!"".equals(temp[2].toString()))) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(new Double(temp[2].toString())
          .doubleValue());
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(0.0D);
      }
      k++;

      if ((temp[3] != null) && (!"".equals(temp[3].toString()))) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(new Double(temp[3].toString())
          .doubleValue());
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(0.0D);
      }
      k++;

      if ((temp[4] != null) && (!"".equals(temp[4].toString()))) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(new Double(temp[4].toString())
          .doubleValue());
      } else {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(0.0D);
      }
      rowNum++;
    }
    HSSFRow row3 = sheet.createRow(rowNum);
    List sumList = this.realfeedao.sumNumberAndWeightByInvoiceId(vehicleCode, 
      orderNum, InvoiceSysId);
    Object[] sumObject = (Object[])sumList.iterator().next();

    HSSFCell row3csCell4 = row3.createCell(4);
    row3csCell4.setEncoding(1);
    row3csCell4.setCellValue("汇总");

    HSSFCell row3csCell5 = row3.createCell(5);
    row3csCell5.setEncoding(1);
    if (sumObject[0] != null) {
      row3csCell5.setCellValue(Double.valueOf(sumObject[0].toString())
        .doubleValue());
    }

    HSSFCell row3csCell6 = row3.createCell(6);
    row3csCell6.setEncoding(1);
    if (sumObject[1] != null) {
      row3csCell6.setCellValue(Double.valueOf(sumObject[1].toString())
        .doubleValue());
    }

    HSSFCell row3csCell7 = row3.createCell(7);
    row3csCell7.setEncoding(1);
    if (sumObject[2] != null) {
      row3csCell7.setCellValue(Double.valueOf(sumObject[2].toString())
        .doubleValue());
    }
    rowNum++;
    HSSFRow row4 = sheet.createRow(rowNum);

    HSSFCell row4csCell = row4.createCell(0);
    row4csCell.setEncoding(1);
    row4csCell.setCellValue("承运商");

    HSSFCell row4csCell1 = row4.createCell(1);
    row4csCell1.setEncoding(1);
    row4csCell1.setCellValue(chineseUserName);
    return wb;
  }

  public HSSFWorkbook queryNumberAndWeightByInvoiceIds(String[] ids, String chineseUserName)
    throws Exception
  {
    HSSFWorkbook wb = new HSSFWorkbook();
    String[] headers = { "发票序号", "发票号", "发票金额", "保单发票号", 
      "保单金额", "开票日期", "合同号", "码单号", "毛重", "净重", "件数" };

    HSSFSheet sheet = wb.createSheet("invoice");

    HSSFFont fontStyle = wb.createFont();

    fontStyle.setFontHeightInPoints(12);

    HSSFCellStyle style = wb.createCellStyle();

    style.setBorderBottom(1);
    style.setBorderLeft(1);
    style.setBorderRight(1);
    style.setBorderTop(1);
    style.setFont(fontStyle);

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);

      csCell.setCellStyle(style);
    }

    double grossWeight = 0.0D;

    double netWeight = 0.0D;

    int count = 0;

    double totalMoney = 0.0D;

    double totalInsurMoney = 0.0D;

    double totalAllMoney = 0.0D;
    int rowNum = 1;
    for (int i = 0; i < ids.length; i++)
    {
      TplInvoice tplInvoice = (TplInvoice)this.invoicedao.queryById(
        Long.valueOf(ids[i]), TplInvoice.class);

      String invoiceNo = tplInvoice.getInvoiceNo();
      String invoiceId = tplInvoice.getInvoiceId();
      String InvoiceSysId = tplInvoice.getId().toString();
      Double totalAmount = tplInvoice.getTotalAmount();
      Date invoiceDate = tplInvoice.getInvoiceDate();
      Double insurTotalMoney = tplInvoice.getInsurTotalMoney();
      String tax = tplInvoice.getTaxRate().toString();

      if (totalAmount != null) {
        totalMoney += totalAmount.doubleValue();
      }

      if ((insurTotalMoney != null) && (!tax.equals("0.06")))
      {
        totalInsurMoney = totalInsurMoney + 
          insurTotalMoney.doubleValue();
      }

      if ((totalAmount != null) || (insurTotalMoney != null)) {
        totalAllMoney = totalInsurMoney + totalMoney;
      }
      List list = this.realfeedao
        .queryNumberAndWeightByInvoiceId(InvoiceSysId);

      String orderNum = "";
      for (int m = 0; m < list.size(); m++) {
        int k = 0;

        HSSFRow row2 = sheet.createRow(rowNum);

        if (m == 0)
        {
          if ((invoiceId != null) && (!"".equals(invoiceId.trim()))) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);
            csCell.setCellValue(i + 1);

            csCell.setCellStyle(style);
          }
          k++;

          if ((invoiceNo != null) && (!"".equals(invoiceNo.trim()))) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellStyle(style);
            csCell.setCellValue(invoiceNo);
          } else {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);
            csCell.setCellValue("");

            csCell.setCellStyle(style);
          }
          k++;

          if (totalAmount != null) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellStyle(style);
            csCell.setCellValue(totalAmount.doubleValue());
          } else {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellValue(0.0D);

            csCell.setCellStyle(style);
          }
          k++;

          if ((insurTotalMoney != null) && (!"".equals(insurTotalMoney)) && 
            (insurTotalMoney.doubleValue() != 0.0D)) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellStyle(style);
            csCell.setCellValue(invoiceNo + "-B");
          } else {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellStyle(style);
            csCell.setCellValue("");
          }
          k++;

          if ((insurTotalMoney != null) && (!"".equals(insurTotalMoney))) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellStyle(style);
            csCell.setCellValue(insurTotalMoney.doubleValue());
          } else {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);

            csCell.setCellStyle(style);
            csCell.setCellValue(0.0D);
          }

          k++;

          if (invoiceDate != null) {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);
            String dateTemp = DateUtils.toString(invoiceDate, 
              "yyyy-MM-dd");

            csCell.setCellStyle(style);
            csCell.setCellValue(dateTemp);
          } else {
            HSSFCell csCell = row2.createCell((short)k);
            csCell.setEncoding(1);
            csCell.setCellValue("");

            csCell.setCellStyle(style);
          }
          k++;
        } else {
          k += 6;
        }

        Object[] temp = (Object[])list.get(m);

        if (!orderNum.equals(temp[0])) {
          orderNum = temp[0].toString();
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(orderNum);
        } else {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);
          csCell.setCellStyle(style);
          csCell.setCellValue("");
        }
        k++;

        if ((temp[1] != null) && (!"".equals(temp[1].toString()))) {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(temp[1].toString());
        } else {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);
          csCell.setCellStyle(style);
          csCell.setCellValue("");
        }

        k++;

        if ((temp[2] != null) && (!"".equals(temp[2].toString()))) {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(new Double(temp[2].toString())
            .doubleValue());

          grossWeight = grossWeight + 
            new Double(temp[2].toString()).doubleValue();
        } else {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);
          csCell.setCellValue(0.0D);

          csCell.setCellStyle(style);
        }
        k++;

        if ((temp[3] != null) && (!"".equals(temp[3].toString()))) {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(new Double(temp[3].toString())
            .doubleValue());

          netWeight = netWeight + 
            new Double(temp[3].toString()).doubleValue();
        } else {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(0.0D);
        }
        k++;

        if ((temp[4] != null) && (!"".equals(temp[4].toString()))) {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(new Integer(temp[4].toString())
            .intValue());
          count += new Integer(temp[4].toString()).intValue();
        } else {
          HSSFCell csCell = row2.createCell((short)k);
          csCell.setEncoding(1);

          csCell.setCellStyle(style);
          csCell.setCellValue(0.0D);
        }

        rowNum++;
      }
    }

    HSSFRow row3 = sheet.createRow(rowNum);

    HSSFCell row3csCell1 = row3.createCell(0);
    row3csCell1.setEncoding(1);
    row3csCell1.setCellValue("汇总");

    HSSFCell row3csCell3 = row3.createCell(2);
    row3csCell3.setEncoding(1);
    row3csCell3.setCellValue(totalMoney);

    HSSFCell row3csCell10 = row3.createCell(4);
    row3csCell10.setEncoding(1);
    row3csCell10.setCellValue(totalInsurMoney);

    HSSFCell row3csCell6 = row3.createCell(8);
    row3csCell6.setEncoding(1);
    if (0.0D != grossWeight) {
      row3csCell6.setCellValue(grossWeight);
    }

    HSSFCell row3csCell7 = row3.createCell(9);
    row3csCell7.setEncoding(1);
    if (0.0D != netWeight) {
      row3csCell7.setCellValue(netWeight);
    }

    HSSFCell row3csCell8 = row3.createCell(10);
    row3csCell8.setEncoding(1);
    if (count != 0) {
      row3csCell8.setCellValue(count);
    }

    rowNum++;
    HSSFRow row4 = sheet.createRow(rowNum);

    HSSFCell row4csCell = row4.createCell(0);
    row4csCell.setEncoding(1);
    row4csCell.setCellValue("总计");

    HSSFCell row4csCell1 = row4.createCell(1);
    row4csCell1.setEncoding(1);
    row4csCell1.setCellValue(totalAllMoney);

    rowNum++;
    HSSFRow row5 = sheet.createRow(rowNum);

    HSSFCell row5csCell = row5.createCell(0);
    row5csCell.setEncoding(1);
    row5csCell.setCellValue("承运商");

    HSSFCell row5csCell1 = row5.createCell(1);
    row5csCell1.setEncoding(1);
    row5csCell1.setCellValue(chineseUserName);
    return wb;
  }

  public List queryByAnyString(String boname, String attributename, String attributevalue, String order) throws Exception
  {
    return this.realfeedao.queryByAnyString(boname, attributename, 
      attributevalue, order);
  }

  public void insertInvoice(TplInvoice tplInvoice)
    throws Exception
  {
    this.realfeedao.insert(tplInvoice, TplInvoice.class);
  }

  public void insertInvoiceTitleUser(TplInvoiceTitleUser tplInvoiceTitleUser)
    throws Exception
  {
    this.realfeedao.insert(tplInvoiceTitleUser, TplInvoiceTitleUser.class);
  }

  public TplInvoice queryTplInvoiceById(Long id)
    throws Exception
  {
    return (TplInvoice)this.realfeedao.queryById(id, TplInvoice.class);
  }

  public TplInvoiceArh queryTplInvoicArchById(Long id) throws Exception
  {
    return (TplInvoiceArh)this.realfeearhdao.queryById(id, TplInvoiceArh.class);
  }

  public TplInvoiceTitleUser queryTplInvoiceTitleUserById(Long id)
    throws Exception
  {
    return (TplInvoiceTitleUser)this.realfeedao.queryById(id, 
      TplInvoiceTitleUser.class);
  }

  public void updateTplInvoiceByBO(TplInvoice tplInvoice)
    throws Exception
  {
    this.realfeedao.update(tplInvoice, TplInvoice.class);
  }

  public void updateTplInvoiceTitleUserByBO(TplInvoiceTitleUser tplInvoiceTitleUser)
    throws Exception
  {
    this.realfeedao.update(tplInvoiceTitleUser, TplInvoiceTitleUser.class);
  }

  public List queryInvoiceStatusByIds(String[] ids)
    throws Exception
  {
    return this.invoicetitlechangedao.queryInvoiceStatusByIds(ids);
  }

  public RealFeeDao getRealfeedao()
  {
    return this.realfeedao;
  }

  public void setRealfeedao(RealFeeDao realfeedao)
  {
    this.realfeedao = realfeedao;
  }

  public List queryFeeDetialByRealFeeId(String id)
    throws Exception
  {
    return this.realfeedao.viewFeeDetialByRealFeeId(id);
  }

  public List queryFeeDetialByInvoiceId(String id, String invoiceType)
    throws Exception
  {
    return this.realfeedao.viewFeeDetialByInvoiceId(id, invoiceType);
  }

  public List queryFeeDetialArhByInvoiceId(String id, String invoiceType)
    throws Exception
  {
    return this.realfeearhdao.viewFeeDetialByInvoiceId(id, invoiceType);
  }

  public List queryRealFeeByInvoiceCondition(String invoiceSysId, String billType)
    throws Exception
  {
    return this.realfeedao
      .queryRealFeeByInvoiceCondition(invoiceSysId, billType);
  }

  public List queryRealFeeArhByInvoiceCondition(String invoiceSysId, String billType)
    throws Exception
  {
    return this.realfeearhdao.queryRealFeeByInvoiceCondition(invoiceSysId, 
      billType);
  }

  public List queryRailFeeDetialByInvoiceId(String id) throws Exception {
    return this.realfeedao.queryRailFeeDetialByInvoiceId(id);
  }

  public List queryRailFeeDetialArhByInvoiceId(String id) throws Exception {
    return this.realfeearhdao.queryRailFeeDetialByInvoiceId(id);
  }

  public CodeUtil getCodeUtil()
  {
    return this.codeUtil;
  }

  public void setCodeUtil(CodeUtil codeUtil)
  {
    this.codeUtil = codeUtil;
  }

  public InvoiceDao getInvoicedao()
  {
    return this.invoicedao;
  }

  public GoodBillDao getGoodBillDao() {
    return this.goodBillDao;
  }

  public void setGoodBillDao(GoodBillDao goodBillDao) {
    this.goodBillDao = goodBillDao;
  }

  public void setInvoicedao(InvoiceDao invoicedao)
  {
    this.invoicedao = invoicedao;
  }

  public InvoiceTitleChangeDao getInvoicetitlechangedao()
  {
    return this.invoicetitlechangedao;
  }

  public void setInvoicetitlechangedao(InvoiceTitleChangeDao invoicetitlechangedao)
  {
    this.invoicetitlechangedao = invoicetitlechangedao;
  }

  public MessageService getMessageService()
  {
    return this.messageService;
  }

  public void setMessageService(MessageService messageService)
  {
    this.messageService = messageService;
  }

  public InsuranceBillService getInsuranceBillService()
  {
    return this.insuranceBillService;
  }

  public void setInsuranceBillService(InsuranceBillService insuranceBillService)
  {
    this.insuranceBillService = insuranceBillService;
  }

  public TplGoodBillArh getGoodBillArh(Long invoiceSysId)
    throws Exception
  {
    Long seqId = this.realfeearhdao.getTransSeqByInvoiceId(invoiceSysId);
    if (seqId != null)
    {
      TplGoodBillArh goodBill = this.goodBillArhDao
        .queryGoodBillBySeqId(seqId);
      return goodBill;
    }
    return null;
  }

  public TplGoodBill getGoodBill(Long invoiceSysId) throws Exception
  {
    Long seqId = this.realfeedao.getTransSeqByInvoiceId(invoiceSysId);
    if (seqId != null)
    {
      TplGoodBill goodBill = this.goodBillDao.queryGoodBillBySeqId(seqId);
      return goodBill;
    }
    return null;
  }

  public List queryNumberAndWeightByOrderNumVehicleCode(String orderNum, String vehicleCode, String seqId, String invoiceSysId)
    throws Exception
  {
    return this.realfeedao.queryNumberAndWeightByOrderNumVehicleCode(orderNum, 
      vehicleCode, seqId, invoiceSysId);
  }

  public List queryNumberAndWeightByOrderNumVehicleCode_Arh(String orderNum, String vehicleCode, String seqId, String invoiceSysId)
    throws Exception
  {
    return this.realfeearhdao.queryNumberAndWeightByOrderNumVehicleCode(
      orderNum, vehicleCode, seqId, invoiceSysId);
  }

  public BaseResultModel queryInvoiceTitleUserListForPage(InvoiceTitleUserSearchModel searchModel)
    throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.realfeedao.queryInvoiceTitleUserListForPage(searchModel);
    baseResultModel.setPage(searchModel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public void sendInvoiceFeeNoAreaPartDirectPara(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoice tplInvoice)
    throws Exception
  {
    sendInvoiceFeeNoAreaPartPara(ids, i, tplMessageInvoice, invoicFeeItems, 
      tplInvoice);
  }

  public void sendInvoiceFeeNoAreaPartDirect(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoice tplInvoice)
    throws Exception
  {
    sendInvoiceFeeNoAreaPart(ids, i, tplMessageInvoice, invoicFeeItems, 
      tplInvoice);
  }

  public void sendInvoiceFeeNoAreaPartDirect_Arh(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoiceArh tplInvoice)
    throws Exception
  {
    sendInvoiceFeeNoAreaPart(ids, i, tplMessageInvoice, invoicFeeItems, 
      tplInvoice);
  }

  public PlanFeeDao getPlanFeeDao() {
    return this.planFeeDao;
  }

  public void setPlanFeeDao(PlanFeeDao planFeeDao) {
    this.planFeeDao = planFeeDao;
  }

  public List queryInvoiceList(StockInvoiceMgrSearchModel searchModel)
    throws Exception
  {
    return this.realfeedao.queryInvoiceList(searchModel);
  }

  private void saveGenInvoicePart(GenInvoiceSearchModel genInvoiceSearchModel) throws Exception
  {
    List nextList = genInvoiceSearchModel.getNextIds();
    List realFeeList = this.realfeedao
      .queryTplRealFeeListByIdsAndModel(genInvoiceSearchModel);
    logger.info("厂外定金开票的id个数：" + realFeeList.toString());
    if ((realFeeList != null) && (realFeeList.size() > 0)) {
      genInvoiceSearchModel.setIdsList(realFeeList);
      logger.info("厂外定金开票方式：" + 
        genInvoiceSearchModel.getOutFactoryDingjinGenInvoice());
      if ("1".equals(genInvoiceSearchModel
        .getOutFactoryDingjinGenInvoice())) {
        logger.info("厂外定金按抬头开票");
        saveGenInvoiceTitle(genInvoiceSearchModel);
      } else {
        logger.info("厂外定金按合同开票");
        saveGenInvoiceContract(genInvoiceSearchModel);
      }
      nextList.removeAll(realFeeList);
    }
  }

  private void saveGenInvoicePartEsales(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    List nextList = genInvoiceSearchModel.getNextIds();

    List tiltesList = this.realfeedao
      .queryTplRealFeeTitleList(genInvoiceSearchModel);

    if ((tiltesList == null) || (tiltesList.size() >= 2)) {
      throw new Exception("msg:请选择同一发票抬头进行开票");
    }
    List realFeeList = this.realfeedao
      .queryTplRealFeeListByIdsAndBusinessType(genInvoiceSearchModel);
    if ((realFeeList != null) && (realFeeList.size() > 0)) {
      genInvoiceSearchModel.setIdsList(realFeeList);
      logger.info("电商业务开票，按照抬头开票");
      saveGenInvoiceTitle(genInvoiceSearchModel);
    }
    nextList.removeAll(realFeeList);
  }

  private void outFacDsdfDjGenInvoice(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    genInvoiceSearchModel.setBillType("10");
    genInvoiceSearchModel.setSettleCode("2");
    saveGenInvoicePart(genInvoiceSearchModel);
  }

  private void esalesDsdfGenInvoice(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    genInvoiceSearchModel.setBusinessType("20");
    genInvoiceSearchModel.setSettleCode("2");
    saveGenInvoicePartEsales(genInvoiceSearchModel);
  }

  private void outFacSdDjGenInvoice(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    genInvoiceSearchModel.setBillType("10");
    genInvoiceSearchModel.setSettleCode("1");
    saveGenInvoicePart(genInvoiceSearchModel);
  }

  private void esalsSdGenInvoice(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    genInvoiceSearchModel.setBusinessType("20");
    genInvoiceSearchModel.setSettleCode("1");
    saveGenInvoicePartEsales(genInvoiceSearchModel);
  }

  public TransSeqDao getTransSeqDao() {
    return this.transSeqDao;
  }

  public void setTransSeqDao(TransSeqDao transSeqDao) {
    this.transSeqDao = transSeqDao;
  }

  public RealFeeArhDao getRealfeearhdao() {
    return this.realfeearhdao;
  }

  public void setRealfeearhdao(RealFeeArhDao realfeearhdao) {
    this.realfeearhdao = realfeearhdao;
  }

  public GoodBillArhDao getGoodBillArhDao() {
    return this.goodBillArhDao;
  }

  public void setGoodBillArhDao(GoodBillArhDao goodBillArhDao) {
    this.goodBillArhDao = goodBillArhDao;
  }

  public List queryRedPlanInInvoice(String[] ids) throws Exception
  {
    return this.realfeedao.queryRedPlanInInvoice(ids);
  }

  public int queryInvoiceCreated(String shipIdRadio) throws Exception
  {
    return this.realfeedao.queryInvoiceCreated(shipIdRadio);
  }

  public int queryInvoiceCreated(String shipIdRadio, Object o)
    throws Exception
  {
    return this.realfeedao.queryInvoiceCreated(shipIdRadio, o);
  }

  private void saveAreaGenInvoiceTitle(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    List idsList = genInvoiceSearchModel.getIdsList();
    TplTransSeq tplTransSeq = null;
    if (StringUtil.trimStr(genInvoiceSearchModel.getTransSeq()).length() > 0) {
      tplTransSeq = (TplTransSeq)this.realfeedao.queryById(
        Long.valueOf(genInvoiceSearchModel.getTransSeq()), 
        TplTransSeq.class);
    }

    List title = null;
    if (tplTransSeq != null) {
      if ("10".equals(tplTransSeq.getShipCode()))
        title = this.realfeedao.queryTitlesForGenInvoiceByIdsZC(idsList
          .toArray());
      else
        title = this.realfeedao.queryTitlesForGenInvoiceByIds(idsList
          .toArray());
    }
    else {
      title = this.realfeedao.queryTitlesForGenInvoiceByIds(idsList.toArray());
    }
    if ((title == null) || (title.size() <= 0)) {
      throw new Exception("费用管理模块，生成发票错误，发票抬头代码为空");
    }
    for (int i = 0; i < title.size(); i++) {
      Object[] temp = (Object[])title.get(i);
      String unitId = temp[0] == null ? null : temp[0].toString();
      String settleCode = temp[1] == null ? null : temp[1].toString();
      String invoiceTitleTaxNo = temp[2] == null ? null : temp[2]
        .toString();
      String netWeight = temp[3] == null ? "0" : temp[3].toString();
      String grossWeight = temp[4] == null ? "0" : temp[4].toString();
      String actCount = temp[5] == null ? "0" : temp[5].toString();
      String sumAmount = temp[6] == null ? "0" : temp[6].toString();
      String totalInsurMoney = temp[7] == null ? "0" : temp[7].toString();
      String idStr = temp[8] == null ? "0" : temp[8].toString();

      String transInvoiceSequence = temp[9] == null ? "" : temp[9]
        .toString();

      String taxRateTemp = "0";
      if (temp[10] != null) {
        taxRateTemp = temp[10].toString();
      }
      BigDecimal taxRate = new BigDecimal(taxRateTemp);

      String taxAreaCode = temp[11] == null ? "" : temp[11].toString();
      String invoiceType = temp[12] == null ? "" : temp[12].toString();
      String invDrawbackAmount = temp[13] == null ? "0" : temp[13]
        .toString();

      Map params_map = new HashMap();
      params_map.put("unitId", unitId);
      params_map.put("settleCode", settleCode);
      params_map.put("invoiceTitleTaxNo", invoiceTitleTaxNo);
      params_map.put("idStr", idStr);
      params_map.put("netWeight", netWeight);
      params_map.put("grossWeight", grossWeight);
      params_map.put("actCount", actCount);
      params_map.put("sumAmount", sumAmount);
      params_map.put("totalInsurMoney", totalInsurMoney);
      params_map.put("taxRate", taxRate);
      params_map.put("invoiceType", invoiceType);

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(idStr), TplRealFee.class);
      params_map.put("invDrawbackAmount", invDrawbackAmount);
      params_map.put("transType", tplRealFee.getTransType() == null ? "" : 
        tplRealFee.getTransType().toString());

      TplInvoice tplInvoice = new TplInvoice();

      BigDecimal taxRateZc = tplRealFee.getTaxRate() == null ? 
        new BigDecimal("0") : 
        tplRealFee.getTaxRate();

      DecimalFormat df = new DecimalFormat("#.##");

      String transType = StringUtil.trimStr(tplRealFee.getTransType());
      if (("0.07".equals(df.format(taxRateZc))) && 
        ("2".equals(transType)))
      {
        tplInvoice.setInvoiceId(tplRealFee.getGoodBillCode());
        tplInvoice.setInvoiceNo(tplRealFee.getGoodBillCode());

        tplInvoice
          .setRewriteStatus("1");
        tplInvoice.setInvoiceDate(new Date());
      }

      insertTplInvoiceTitle(genInvoiceSearchModel, params_map, tplInvoice);

      tplInvoice.setTaxAreaCode(tplRealFee.getTaxAreaCode());
      tplInvoice.setTaxAreaName(tplRealFee.getTaxAreaName());
      tplInvoice
        .setUserBankBranchName(tplRealFee.getUserBankBranchName());
      tplInvoice.setUserAccountNum(tplRealFee.getUserAccountNum());
      tplInvoice
        .setUserChineseAddress(tplRealFee.getUserChineseAddress());
      tplInvoice.setUserTelephoneNum(tplRealFee.getUserTelephoneNum());

      insertTplInvoice(tplRealFee, tplInvoice);
      String invoiceSysId = tplInvoice.getId().toString();
      System.out
        .println("============地区公司水运整船开票invoiceSysId==============" + 
        invoiceSysId);

      List list = null;
      if (tplTransSeq != null) {
        if ("10".equals(tplTransSeq.getShipCode()))
          list = this.realfeedao.queryIdsByTitleUitlIdOrderArea(idsList
            .toArray(), invoiceTitleTaxNo, settleCode, "ZC", 
            transInvoiceSequence, taxRate, taxAreaCode, 
            invoiceType);
        else
          list = this.realfeedao.queryIdsByTitleUitlIdOrderArea(idsList
            .toArray(), invoiceTitleTaxNo, settleCode, unitId, 
            transInvoiceSequence, taxRate, taxAreaCode, 
            invoiceType);
      }
      else {
        list = this.realfeedao
          .queryIdsByTitleUitlIdOrderArea(idsList.toArray(), 
          invoiceTitleTaxNo, settleCode, unitId, 
          transInvoiceSequence, taxRate, taxAreaCode, 
          invoiceType);
      }

      this.realfeedao.updateTplRealStatusInvoicIdByIds(list.toArray(), 
        invoiceSysId);

      String shipId = "";
      String trainNo = tplRealFee.getTrainId();

      this.realfeedao.updateTplInsuranceBillInvoicSysIdByIds(list.toArray(), 
        invoiceSysId, shipId, trainNo);
    }
  }

  public String saveGenInvoiceByIdsInvoiceByMonthArea(GenInvoiceSearchModel genInvoiceSearchModel, RealFeeByMonthSearchModel searchmodel)
    throws Exception
  {
    String planType = searchmodel.getPlanType();

    if ("0".equals(planType))
    {
      searchmodel.setTransType("1");
      genInvoiceSearchModel
        .setInvoiceType("3");
    } else if ("1".equals(planType))
    {
      genInvoiceSearchModel
        .setInvoiceType("0");
    } else {
      return null;
    }

    List title = this.realfeedao
      .queryTitlesForGenInvoiceByMonthArea(searchmodel);
    if ((title == null) || (title.size() <= 0)) {
      return new String("地区公司报支发票月结：你所选择的实绩在该月份下没有记录");
    }
    for (int i = 0; i < title.size(); i++) {
      Object[] temp = (Object[])title.get(i);
      String unitId = temp[0] == null ? null : temp[0].toString();
      String settleCode = temp[1] == null ? null : temp[1].toString();
      String invoiceTitleTaxNo = temp[2] == null ? null : temp[2]
        .toString();
      String idStr = temp[8] == null ? "0" : temp[8].toString();

      String netWeight = temp[3] == null ? "0" : temp[3].toString();
      String grossWeight = temp[4] == null ? "0" : temp[4].toString();
      String actCount = temp[5] == null ? "0" : temp[5].toString();
      String sumAmount = temp[6] == null ? "0" : temp[6].toString();
      String totalInsurMoney = temp[7] == null ? "0" : temp[7].toString();

      String transInvoiceSequence = temp[9] == null ? "" : temp[9]
        .toString();

      String taxRateTemp = "0";
      if (temp[10] != null) {
        taxRateTemp = temp[10].toString();
      }
      BigDecimal taxRate = new BigDecimal(taxRateTemp);

      Map params_map = new HashMap();
      params_map.put("unitId", unitId);
      params_map.put("settleCode", settleCode);
      params_map.put("invoiceTitleTaxNo", invoiceTitleTaxNo);
      params_map.put("idStr", idStr);

      params_map.put("netWeight", netWeight);
      params_map.put("grossWeight", grossWeight);
      params_map.put("actCount", actCount);
      params_map.put("sumAmount", sumAmount);
      params_map.put("totalInsurMoney", totalInsurMoney);

      params_map.put("taxRate", taxRate);

      TplRealFee tplRealFee = (TplRealFee)this.realfeedao.queryById(
        Long.valueOf(idStr), TplRealFee.class);
      TplInvoice tplInvoice = new TplInvoice();

      insertTplInvoiceTitle(genInvoiceSearchModel, params_map, tplInvoice);
      insertTplInvoice(tplRealFee, tplInvoice);
      String invoiceSysId = tplInvoice.getId().toString();
      System.out.println("============地区公司报支invoiceSysId================" + 
        invoiceSysId);
      searchmodel.setOwnerAreaPart(unitId);
      searchmodel.setSettleCode(settleCode);
      searchmodel.setInvoiceTitleTaxNo(invoiceTitleTaxNo);

      searchmodel.setTransInvoiceSequence(transInvoiceSequence);
      searchmodel.setTaxRate(taxRate);

      this.realfeedao.updateTplRealStatusInvoicIdByIdsMonthArea(searchmodel, 
        invoiceSysId);
    }

    return null;
  }

  public TplMessageInvoiceFee forRailWayMixedFeeTax(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);

    if ((tplRealFee.getFeeTypeCode() != null) && 
      (!tplRealFee.getFeeTypeCode().equals("")))
    {
      tplMessageInvoiceFee
        .setFeeTypeCode("3203");

      tplMessageInvoiceFee
        .setFeeTypeName("铁路运杂费");
    }
    else {
      tplMessageInvoiceFee
        .setFeeTypeCode("1104");

      tplMessageInvoiceFee
        .setFeeTypeName("铁运吊装费");
    }

    double grossWeight = getWeightByCondition(tplRealFee);
    tplMessageInvoiceFee.setGrossWeight(
      StringUtil.getBigDecimalNumber(new Double(grossWeight)));

    double serviceFee = tplRealFee.getServiceFee() == null ? 0.0D : tplRealFee
      .getServiceFee().doubleValue();
    double ropeFee = tplRealFee.getRopeFee() == null ? 0.0D : tplRealFee
      .getRopeFee().doubleValue();
    double saveFee = tplRealFee.getSaveFee() == null ? 0.0D : tplRealFee
      .getSaveFee().doubleValue();
    double fixFee = tplRealFee.getFixFee() == null ? 0.0D : tplRealFee
      .getFixFee().doubleValue();
    double otherFee = tplRealFee.getOtherFee() == null ? 0.0D : tplRealFee
      .getOtherFee().doubleValue();
    double carry_fee = 0.0D;
    String transInvoiceSequence = StringUtil.trimStr(tplRealFee
      .getTransInvoiceSequence());
    if ((tplRealFee.getUnitId().equals("BGSQ")) && 
      (!transInvoiceSequence.equals("AA"))) {
      carry_fee += tplRealFee.getTotalMoney().doubleValue();
    }
    else if (SystemConfigUtil.FACTORY_PROVIDER_ID_ST_MAP
      .containsKey(tplRealFee.getProviderId()))
    {
      carry_fee = carry_fee + 
        tplRealFee.getTotalMoney().doubleValue();
    }
    else carry_fee = serviceFee + ropeFee + saveFee + fixFee + otherFee;

    carry_fee = StringUtil.round(carry_fee, 2);
    tplMessageInvoiceFee.setTotalAmount(
      StringUtil.getBigDecimalNumber(new Double(carry_fee)));

    setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

    return tplMessageInvoiceFee;
  }

  public TplMessageInvoiceFee forRailWayMixedFee1(TplRealFee tplRealFee, TplMessageInvoice tplMessageInvoice)
    throws Exception
  {
    TplMessageInvoiceFee tplMessageInvoiceFee = generateTplMessageInvoiceFee(
      tplRealFee, tplMessageInvoice);

    tplMessageInvoiceFee
      .setFeeTypeCode("3203");

    tplMessageInvoiceFee
      .setFeeTypeName("铁路运杂费");

    double grossWeight = getWeightByCondition(tplRealFee);
    tplMessageInvoiceFee.setGrossWeight(
      StringUtil.getBigDecimalNumber(new Double(grossWeight)));

    double totalAmount = tplRealFee.getTotalMoney() == null ? 0.0D : 
      tplRealFee.getTotalMoney().doubleValue();

    double billTransFee = tplRealFee.getBillTransFee() == null ? 0.0D : 
      tplRealFee.getBillTransFee().doubleValue();

    double conFee = tplRealFee.getConFee() == null ? 0.0D : tplRealFee
      .getConFee().doubleValue();

    double contractFee = billTransFee + conFee;

    double totalInsurMoney = tplRealFee.getTotalInsurMoney() == null ? 0.0D : 
      tplRealFee.getTotalInsurMoney().doubleValue();

    double other_fee1 = tplRealFee.getOtherFee1() == null ? 0.0D : tplRealFee
      .getOtherFee1().doubleValue();
    double carry_fee = totalAmount;

    carry_fee = StringUtil.round(carry_fee, 2);
    tplMessageInvoiceFee.setTotalAmount(
      StringUtil.getBigDecimalNumber(new Double(carry_fee)));

    setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

    return tplMessageInvoiceFee;
  }

  private void sendInvoiceFeeForPortFee(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoice tplInvoice)
    throws Exception
  {
    String batchOperateId = tplInvoice.getBatchOperateId();

    List listSub = this.realfeearhdao.queryTransSeqByOperateId(batchOperateId);

    if ((listSub != null) && (listSub.size() > 0))
      for (int j = 0; j < listSub.size(); j++) {
        TplTransSeq tplTransSeq = (TplTransSeq)listSub.get(j);

        if ((tplTransSeq.getPortFeeStatus() != null) && 
          ("1".equals(tplTransSeq.getPortFeeStatus()))) {
          this.realfeearhdao.updateTransSeqPortFeeStatus(batchOperateId);
          this.realfeearhdao.updateTransPortFeeStatus(batchOperateId);
        }

        TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
        tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

        tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice
          .getInvoiceNo());

        tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice
          .getInvoiceId());

        tplMessageInvoiceFee.setPlanId(tplTransSeq.getShipId());

        tplMessageInvoiceFee
          .setCompanyCode(tplInvoice.getCompanyCode());

        tplMessageInvoiceFee.setFeeTypeCode("3301");

        tplMessageInvoiceFee.setFeeTypeName("港口建设费");
        tplMessageInvoiceFee.setInvoiceSysId(tplInvoice.getId()
          .toString());

        String totalAmount = Double.toString(tplInvoice
          .getTotalAmount().doubleValue());

        String grossWeight = Double.toString(tplInvoice
          .getGrossWeight().doubleValue());
        String zero = "0";
        if (j == 0) {
          tplMessageInvoiceFee.setTotalAmount(
            StringUtil.getBigDecimalNumber(totalAmount));
          tplMessageInvoiceFee.setGrossWeight(
            StringUtil.getBigDecimalNumber(grossWeight));
        } else {
          tplMessageInvoiceFee.setTotalAmount(
            StringUtil.getBigDecimalNumber(zero));
          tplMessageInvoiceFee.setGrossWeight(
            StringUtil.getBigDecimalNumber(zero));
        }
        tplMessageInvoiceFee.setTaxAmount(tplMessageInvoice
          .getInvTaxAmount());

        invoicFeeItems.add(tplMessageInvoiceFee);
      }
  }

  public void setTaxAmountToTplMessageInvoiceFee(TplMessageInvoiceFee tplMessageInvoiceFee, TplRealFee tplRealFee)
  {
    if (tplRealFee == null) {
      return;
    }

    DecimalFormat df = new DecimalFormat("#.##");
    BigDecimal taxRate = tplRealFee.getTaxRate();

    tplMessageInvoiceFee.setTaxAmount(
      new BigDecimal(setTaxAmountToTplMessageInvoiceFee(
      new Double(tplMessageInvoiceFee.getTotalAmount().doubleValue()), 
      taxRate)));
  }

  public String setTaxAmountToTplMessageInvoiceFee(Double totalAmount, BigDecimal taxRate)
  {
    String resultValue = "0";
    if ((totalAmount != null) && (taxRate != null)) {
      BigDecimal taxRateTmp = taxRate.add(new BigDecimal("1"));
      BigDecimal totalAmountFee = new BigDecimal(totalAmount
        .doubleValue());
      BigDecimal taxResults = totalAmountFee.divide(taxRateTmp, 2)
        .multiply(taxRate);

      DecimalFormat df = new DecimalFormat("#.##");
      resultValue = df.format(taxResults);
    }

    return resultValue;
  }

  public String setTaxAmountToTplInvoiceFeeForGoodBill(Double totalAmount, BigDecimal taxRate)
  {
    String resultValue = "0";
    if ((totalAmount != null) && (taxRate != null)) {
      BigDecimal totalAmountFee = new BigDecimal(totalAmount
        .doubleValue());
      BigDecimal taxResults = totalAmountFee.multiply(taxRate);

      DecimalFormat df = new DecimalFormat("#.##");
      resultValue = df.format(taxResults);
    }

    return resultValue;
  }

  public void updateTplInvoice(StockInvoiceMgrSearchModel stockinvoicemgrsearchmodel)
    throws Exception
  {
    this.realfeedao.updateTplInvoice(stockinvoicemgrsearchmodel);
  }

  public String queryProviderTax(String providerId) throws Exception {
    return this.realfeedao.queryProviderTax(providerId);
  }

  public void createInsurance(String[] ids)
    throws Exception
  {
    for (int i = 0; i < ids.length; i++) {
      TplInvoice tplInvoice = new TplInvoice();

      TplInvoice tplInvoiceSearch = (TplInvoice)this.invoicedao.queryById(
        Long.valueOf(ids[i]), TplInvoice.class);

      tplInvoice.setCreateDate(new Date());
      tplInvoice.setProviderId(tplInvoiceSearch.getProviderId());

      tplInvoice.setInvoiceType("8");
      tplInvoice.setUnitId(tplInvoiceSearch.getUnitId());
      tplInvoice.setUnitName(tplInvoiceSearch.getUnitName());
      tplInvoice.setOrderNum(tplInvoiceSearch.getOrderNum());
      tplInvoice.setGoodBillCode(tplInvoiceSearch.getGoodBillCode());
      tplInvoice.setLadingSpotName(tplInvoiceSearch.getLadingSpotName());
      tplInvoice.setDestSpotName(tplInvoiceSearch.getDestSpotName());
      tplInvoice.setActCount(tplInvoiceSearch.getActCount());
      tplInvoice.setGrossWeight(tplInvoiceSearch.getGrossWeight());
      tplInvoice.setNetWeight(tplInvoiceSearch.getNetWeight());
      tplInvoice.setShipId(tplInvoiceSearch.getShipId());
      tplInvoice.setShipName(tplInvoiceSearch.getShipName());
      tplInvoice.setInvoiceSysId(tplInvoiceSearch.getId());

      String insurTotalMoney = "";

      List result = this.realfeedao.queryInsuranceByInvoiceSysId(Long.valueOf(ids[i]));
      if ((result != null) && (result.size() > 0)) {
        int count = 0;
        for (int a = 0; a < result.size(); a++) {
          TplInsuranceBill bill = (TplInsuranceBill)result.get(a);
          if (bill != null) {
            if ("1".equals(bill.getIsInsuranceM())) {
              insurTotalMoney = bill.getInsuranceTotalNumAdjust() == null ? "0" : bill.getInsuranceTotalNumAdjust().toString();
              count=a;
            }
          }
        }
      }
      else
      {
        List sumInsuranceTotalList = this.realfeedao
          .queryTplInsuranceBillTotalAmountAdjust(ids[i]);
        insurTotalMoney = sumInsuranceTotalList.get(0) == null ? "0" : 
          sumInsuranceTotalList.get(0).toString();
      }
      if ((result != null) && (result.size() > 0)) {
    	  
    	  if(count == 0){
    		  TplInsuranceBill merBill=(TplInsuranceBill)result.get(0);
    		  if (("BGBW".equals(perBill.getUnitId())) && ("2".equals(perBill.getTransType()))) {
	    		  tplInvoice.setInvoiceNo(merBill.getInsurId());
	              tplInvoice.setInvoiceId("0000000000");
    		  }
    	  }else{
    		  TplInsuranceBill merBill=(TplInsuranceBill)result.get(count);
    		  if (("BGBW".equals(merBill.getUnitId())) && ("2".equals(merBill.getTransType()))) {
	    		  tplInvoice.setInvoiceNo(merBill.getInsurId());
	              tplInvoice.setInvoiceId("0000000000");
    		  }
    	  }
//        if (result.size() > 1) {
//          for (int m = 0; m < result.size(); m++) {
//            TplInsuranceBill perBill = (TplInsuranceBill)result.get(m);
//            if (("1".equals(perBill.getIsInsuranceM())) && ("BGBW".equals(perBill.getUnitId())) && ("2".equals(perBill.getTransType()))) {
//              tplInvoice.setInvoiceNo(perBill.getInsurId());
//              tplInvoice.setInvoiceId("0000000000");
//            }
//          }
//        } else {
//          TplInsuranceBill parBill = (TplInsuranceBill)result.get(0);
//          if (("BGBW".equals(parBill.getUnitId())) && ("2".equals(parBill.getTransType()))) {
//            tplInvoice.setInvoiceNo(parBill.getInsurId());
//            tplInvoice.setInvoiceId("0000000000");
//          }
//        }
      }

      if ((insurTotalMoney.equals("0")) || 
        (Double.parseDouble(insurTotalMoney) == 0.0D)) {
        throw new Exception("保险费为0,不能生成保单发票");
      }

      tplInvoice.setTotalAmount(
        new Double(Double.parseDouble(insurTotalMoney)));
      tplInvoice.setInsurTotalMoney(
        new Double(Double.parseDouble(insurTotalMoney)));

      tplInvoice.setInvoiceTitleName(tplInvoiceSearch
        .getInvoiceTitleName());

      tplInvoice.setInvoiceClassify("20");

      tplInvoice.setTaxAreaCode(tplInvoiceSearch.getTaxAreaCode());
      tplInvoice.setTaxAreaName(tplInvoiceSearch.getTaxAreaName());
      tplInvoice.setUserBankBranchName(tplInvoiceSearch
        .getUserBankBranchName());
      tplInvoice.setUserChineseAddress(tplInvoiceSearch
        .getUserChineseAddress());
      tplInvoice.setUserTelephoneNum(tplInvoiceSearch
        .getUserTelephoneNum());
      tplInvoice.setUserAccountNum(tplInvoiceSearch.getUserAccountNum());

      tplInvoice.setCompanyCode(tplInvoiceSearch.getCompanyCode());
      tplInvoice.setSettleType(tplInvoiceSearch.getSettleType());
      tplInvoice.setPayStatus("0");
      tplInvoice.setInvoiceTitleCode(tplInvoiceSearch
        .getInvoiceTitleCode());
      tplInvoice.setInvoiceTitleTaxNo(tplInvoiceSearch
        .getInvoiceTitleTaxNo());
      tplInvoice.setCreditorCode(tplInvoiceSearch.getCreditorCode());
      tplInvoice.setProviderBank(tplInvoiceSearch.getProviderBank());
      tplInvoice
        .setProviderAccount(tplInvoiceSearch.getProviderAccount());

      tplInvoice.setTaxAreaCode(tplInvoiceSearch.getTaxAreaCode());
      tplInvoice.setTaxAreaName(tplInvoiceSearch.getTaxAreaName());
      tplInvoice.setTaxRate(new BigDecimal(0.06D));
      String taxAmount = setTaxAmountToTplMessageInvoiceFee(tplInvoice
        .getTotalAmount(), tplInvoice.getTaxRate());
      tplInvoice.setTaxAmount(new Double(taxAmount));
      tplInvoice.setTransCharges(
        new Double(tplInvoice.getTotalAmount()
        .doubleValue() - 
        tplInvoice.getTaxAmount().doubleValue()));

      tplInvoice.setManuId(tplInvoiceSearch.getManuId());

      tplInvoice.setInvoiceDate(tplInvoiceSearch.getInvoiceDate());

      tplInvoice.setCreateDate(new Date());

      tplInvoice.setConsigneeCode(tplInvoiceSearch.getConsigneeCode());

      tplInvoice.setConsigneeName(tplInvoiceSearch.getConsigneeName());

      tplInvoice.setConsigneeTaxNo(tplInvoiceSearch.getConsigneeTaxNo());

      tplInvoice.setTrainNo(tplInvoiceSearch.getTrainNo());

      tplInvoice.setGoodBillCode(tplInvoiceSearch.getGoodBillCode());

      tplInvoice.setLadingSpot(tplInvoiceSearch.getLadingSpot());

      tplInvoice.setLadingSpotName(tplInvoiceSearch.getLadingSpotName());

      tplInvoice.setDestSpot(tplInvoiceSearch.getDestSpot());

      tplInvoice.setRewriteStatus("1");

      tplInvoice.setCreateId(tplInvoiceSearch.getCreateId());

      tplInvoice.setSaleOrgCode(tplInvoiceSearch.getSaleOrgCode());

      tplInvoice.setPickType(tplInvoiceSearch.getPickType());
      this.realfeedao.insert(tplInvoice, TplInvoice.class);

      String invoiceSysId = tplInvoice.getId().toString();
      System.out.println("========保单发票生成===invoiceSysId=======" + 
        invoiceSysId);

      this.realfeedao.updateTplInsuranceBillInvoicSysIdBByIds(ids[i], 
        invoiceSysId);
    }
  }

  public List queryTplInvoiceByInvoiceIdAndNo(TplInvoice tplInvoice) throws Exception
  {
    return this.realfeedao.queryTplInvoiceByInvoiceIdAndNo(tplInvoice);
  }

  public BaseResultModel queryInsuranceMgrListForPage(StockInvoiceMgrSearchModel stockInvoiceMgrSearchModel)
    throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.invoicedao
      .queryInsuranceMgrListForPage(stockInvoiceMgrSearchModel);
    baseResultModel.setPage(stockInvoiceMgrSearchModel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public BaseResultModel queryTplUnitContranstForPage(TplUnitContrastSearchModel tplUnitContrastSearchModel)
    throws Exception
  {
    BaseResultModel baseResultModel = new BaseResultModel();
    List list = this.invoicedao
      .queryTplUnitContranstForPage(tplUnitContrastSearchModel);
    baseResultModel.setPage(tplUnitContrastSearchModel.getPage());
    baseResultModel.setResults(list);
    return baseResultModel;
  }

  public List querySumInsuranceList(StockInvoiceMgrSearchModel stockInvoiceMgrSearchModel)
    throws Exception
  {
    return this.invoicedao.querySumInsuranceList(stockInvoiceMgrSearchModel);
  }

  public void deleteInvoiceAndUpdateInsuranceBill(String[] ids)
    throws Exception
  {
    this.realfeedao.updateTplBillInvoiceSysIdBNull(ids);

    sendInvioceCancel(ids);

    this.realfeedao.deleteTplInvoice(ids);
  }

  public void updateInsurancePaymentApply(String[] ids) throws Exception
  {
    this.realfeedao.updatePaymentApply(ids);

    sendMessageTplInvoiceForInsurance(ids);
  }

  private void sendMessageTplInvoiceForInsurance(String[] ids)
    throws Exception
  {
    for (int i = 0; i < ids.length; i++)
    {
      TplInvoice tplInvoice = (TplInvoice)this.invoicedao.queryById(
        Long.valueOf(ids[i]), TplInvoice.class);
      if ((tplInvoice.getTotalAmount() == null) || 
        (tplInvoice.getTotalAmount().doubleValue() == 0.0D)) {
        continue;
      }
      String unitId = tplInvoice.getUnitId();

      if (this.messageService.judgeSendMsg(unitId))
      {
        TplMessageInvoice tplMessageInvoice = new TplMessageInvoice();

        tplMessageInvoice.setUnitId(tplInvoice.getUnitId());
        tplMessageInvoice.setUnitName(tplInvoice.getUnitName());
        tplMessageInvoice.setManuId(tplInvoice.getManuId());
        tplMessageInvoice
          .setInvoiceSysId(tplInvoice.getId().toString());
        tplMessageInvoice.setInvoiceId(tplInvoice.getInvoiceId());
        tplMessageInvoice.setInvoiceNo(tplInvoice.getInvoiceNo());
        tplMessageInvoice.setInvoiceType(tplInvoice.getInvoiceType());
        tplMessageInvoice.setInvoiceDate(tplInvoice.getInvoiceDate());
        tplMessageInvoice.setCreditorCode(tplInvoice.getCreditorCode());
        tplMessageInvoice.setCreditorName(tplInvoice.getCreditorName());
        tplMessageInvoice.setProductTypeName(tplInvoice
          .getProductTypeName());
        tplMessageInvoice.setProviderBank(tplInvoice.getProviderBank());
        tplMessageInvoice.setProviderAccount(tplInvoice
          .getProviderAccount());
        tplMessageInvoice.setInvoiceTitleCode(tplInvoice
          .getInvoiceTitleCode());
        tplMessageInvoice.setInvoiceTitleName(tplInvoice
          .getInvoiceTitleName());
        tplMessageInvoice.setInvoiceTitleTaxNo(tplInvoice
          .getInvoiceTitleTaxNo());
        tplMessageInvoice.setConsigneeCode(tplInvoice
          .getConsigneeCode());
        tplMessageInvoice.setConsigneeName(tplInvoice
          .getConsigneeName());
        tplMessageInvoice.setConsigneeTaxNo(tplInvoice
          .getConsigneeTaxNo());
        tplMessageInvoice.setSettleType(tplInvoice.getSettleType());
        tplMessageInvoice.setTaxYh(
          StringUtil.getBigDecimalNumber(tplInvoice.getTaxYh()));
        tplMessageInvoice.setPlanId(tplInvoice.getPlanId());
        tplMessageInvoice.setCompanyCode(tplInvoice.getCompanyCode());
        tplMessageInvoice.setOrderNum(tplInvoice.getOrderNum());
        tplMessageInvoice.setGoodBillCode(tplInvoice.getGoodBillCode());
        tplMessageInvoice.setTrainNo(tplInvoice.getTrainNo());
        tplMessageInvoice.setLadingSpot(tplInvoice.getLadingSpot());
        tplMessageInvoice.setLadingSpotName(tplInvoice
          .getLadingSpotName());
        tplMessageInvoice.setDestSpot(tplInvoice.getDestSpot());
        tplMessageInvoice.setDestSpotName(tplInvoice.getDestSpotName());
        tplMessageInvoice.setPlanId(tplInvoice.getPlanId());
        tplMessageInvoice.setShipId(tplInvoice.getShipId());
        tplMessageInvoice.setShipName(tplInvoice.getShipName());
        tplMessageInvoice.setGrossWeight(
          StringUtil.getBigDecimalNumber(tplInvoice.getGrossWeight()));
        tplMessageInvoice.setNetWeight(
          StringUtil.getBigDecimalNumber(tplInvoice.getNetWeight()));

        double totalAmount = tplInvoice.getTotalAmount() == null ? 0.0D : 
          tplInvoice.getTotalAmount().doubleValue();

        totalAmount = StringUtil.round(totalAmount, 2);

        tplMessageInvoice.setTotalAmount(
          StringUtil.getBigDecimalNumber(new Double(totalAmount)));
        tplMessageInvoice.setPayAmount(
          StringUtil.getBigDecimalNumber(tplInvoice.getPayAmount()));
        tplMessageInvoice.setCreateId(tplInvoice.getCreateId());
        tplMessageInvoice.setInvoiceStatus(tplInvoice
          .getInvoiceStatus());
        tplMessageInvoice.setRewriteStatus(tplInvoice
          .getRewriteStatus());
        tplMessageInvoice.setProviderId(tplInvoice.getProviderId());
        tplMessageInvoice.setCreateDate(tplInvoice.getCreateDate());
        tplMessageInvoice.setModifyDate(tplInvoice.getModifyDate());
        tplMessageInvoice.setModifyId(tplInvoice.getModifyId());
        tplMessageInvoice.setInvoiceType(tplInvoice.getInvoiceType());
        Long invoiceSysId = tplInvoice.getInvoiceSysId();

        if ((invoiceSysId != null) && 
          (!"".equals(tplInvoice.getInvoiceSysId())))
        {
          tplMessageInvoice.setInvGroupId(String.valueOf(tplInvoice
            .getInvoiceSysId()));
          tplMessageInvoice.setTransInvoiceId(
            String.valueOf(tplInvoice.getInvoiceSysId()));
        }

        String invTaxAmount = "0";
        if ((tplInvoice.getTaxAmount() != null) && 
          (!"".equals(tplInvoice.getTaxAmount()))) {
          invTaxAmount = Double.toString(tplInvoice.getTaxAmount()
            .doubleValue());
        }
        tplMessageInvoice.setInvTaxAmount(
          StringUtil.getBigDecimalNumber(invTaxAmount));
        tplMessageInvoice.setIsInsuranceBill("0");
        tplMessageInvoice.setTaxRate(new BigDecimal(0.06D));
        Set invoicFeeItems = new HashSet();

        sendInsuranceFee(ids, i, tplMessageInvoice, invoicFeeItems, 
          tplInvoice);

        tplMessageInvoice.setInvoiceFeeList(invoicFeeItems);

        this.messageService.sendInvoice(tplMessageInvoice);
      }

      List list = this.realfeedao.queryInsuranceBillIdByInvoiceId(tplInvoice
        .getId().toString());
      System.out
        .println("************************************发送保单开始*************************************");
      for (Iterator it = list.iterator(); it.hasNext(); ) {
        String insurId = it.next().toString();
        this.insuranceBillService.sendMessageInsuranceBill(
          Long.valueOf(insurId), 
          "0", unitId);
      }
      System.out
        .println("************************************发送保单结束*************************************");
    }
  }

  private void sendInsuranceFee(String[] ids, int i, TplMessageInvoice tplMessageInvoice, Set invoicFeeItems, TplInvoice tplInvoice)
    throws Exception
  {
    List listSub = this.realfeedao.queryInsuranceAppleyForMessage(ids[i]);

    for (int m = 0; m < listSub.size(); m++)
    {
      TplInsuranceBill tplInsuranceBill = (TplInsuranceBill)listSub
        .get(m);

      TplMessageInvoiceFee tplMessageInvoiceFee = new TplMessageInvoiceFee();
      tplMessageInvoiceFee.setInvoice(tplMessageInvoice);

      tplMessageInvoiceFee.setInvoiceSysId(tplMessageInvoice
        .getInvoiceSysId());

      tplMessageInvoiceFee.setInvoiceNo(tplMessageInvoice.getInvoiceNo());

      tplMessageInvoiceFee.setInvoiceId(tplMessageInvoice.getInvoiceId());

      tplMessageInvoiceFee.setPlanId(tplInsuranceBill.getTransPlanId());

      tplMessageInvoiceFee.setOrderNum(tplInsuranceBill.getOrderNum());

      tplMessageInvoiceFee.setInsurId(tplInsuranceBill.getInsurId());

      tplMessageInvoiceFee.setCompanyCode(tplInsuranceBill
        .getCompanyCode());

      tplMessageInvoiceFee.setFeeTypeCode("3201");

      tplMessageInvoiceFee.setFeeTypeName("保险费");

      double unitPrice = tplInsuranceBill.getUnitPrice() == null ? 0.0D : 
        tplInsuranceBill.getUnitPrice().doubleValue();
      unitPrice = StringUtil.round(unitPrice, 2);
      tplMessageInvoiceFee.setUnitPrice(
        StringUtil.getBigDecimalNumber(new Double(unitPrice)));

      tplMessageInvoiceFee
        .setGrossWeight(
        StringUtil.getBigDecimalNumber(tplInsuranceBill
        .getTotalGrossWeight()));

      tplMessageInvoiceFee.setTotalAmount(
        StringUtil.getBigDecimalNumber(tplInsuranceBill
        .getInsuranceTotalNumAdjust()));

      TplRealFee tplRealFee = new TplRealFee();
      String taxRate = "0.06";
      tplRealFee.setTaxRate(new BigDecimal(taxRate));

      setTaxAmountToTplMessageInvoiceFee(tplMessageInvoiceFee, tplRealFee);

      invoicFeeItems.add(tplMessageInvoiceFee);
    }
  }

  public List queryTplInvoiceVatLimit(String providerId)
    throws Exception
  {
    return this.realfeedao.queryTplInvoiceVatLimit(providerId);
  }

  public List queryInvoiceControl(TplInvoice tplInvoice) throws Exception {
    return this.realfeedao.queryInvoiceControl(tplInvoice);
  }

  public List queryTplInsuranceBill(String id) throws Exception {
    return this.realfeedao.queryTplInsuranceBill(id);
  }

  public void deleteInvoiceAndUpdateRealFeeSearch(String[] ids)
    throws Exception
  {
    String batchOperateId = "";
    List invoiceList = new ArrayList();
    List invoiceListNo = new ArrayList();
    List invoiceList2 = new ArrayList();
    List invoiceList3 = new ArrayList();
    invoiceList = this.realfeedao.queryTplInvoice(ids, null, null);
    if ((invoiceList != null) && (invoiceList.size() > 0)) {
      for (int i = 0; i < invoiceList.size(); i++) {
        TplInvoice TplInvoice = (TplInvoice)invoiceList.get(i);

        if ("6".equals(TplInvoice.getInvoiceType())) {
          invoiceListNo.add(TplInvoice.getBatchOperateId());
        }
      }

    }

    sendInvioceCancel(ids);

    for (int i = 0; i < ids.length; i++) {
      this.realfeedao.updateInvoicePayStatus(ids[i]);
    }

    if ((invoiceListNo != null) && (invoiceListNo.size() > 0))
      for (int j = 0; j < invoiceListNo.size(); j++) {
        batchOperateId = invoiceListNo.get(j).toString();
        invoiceList2 = this.realfeedao.queryTplInvoice(null, batchOperateId, 
          "1");
        invoiceList3 = this.realfeedao.queryTplInvoice(null, batchOperateId, 
          "0");
        if ((invoiceList2 != null) && (invoiceList2.size() > 0)) {
          continue;
        }
        if ((invoiceList3 != null) && (invoiceList3.size() > 0)) {
          this.realfeedao.updateTplTransSeqStatus(batchOperateId, "1");
          this.realfeedao.updateTplProtFeeStatus(batchOperateId, "1");
        } else {
          this.realfeedao.updateTplTransSeqStatus(batchOperateId, "0");
          this.realfeedao.updateTplProtFeeStatus(batchOperateId, "0");
        }
      }
  }

  public List queryInsuranceBillIsCreate(String id)
    throws Exception
  {
    List list = this.realfeedao.queryInsuranceBillIsCreate(id);
    return list;
  }

  public List queryInsuranceBillIsCreate(String id, Object obj)
    throws Exception
  {
    List list = this.realfeedao.queryInsuranceBillIsCreate(id, obj);
    return list;
  }

  public TplTransSeq queryTransSeq(String id) throws Exception {
    TplTransSeq tplTransSeq = this.realfeedao.queryTransSeq(id);
    return tplTransSeq;
  }

  public List queryInvoiceIsCanCreateInvoiceB(TplInvoice tplInvoice) throws Exception
  {
    List list = this.realfeedao.queryInvoiceIsCanCreateInvoiceB(tplInvoice);
    return list;
  }

  public void updateInvoiceDateChange(StockInvoiceMgrSearchModel stockInvoiceMgrSearchModel)
    throws Exception
  {
    this.realfeedao.updateInvoiceDateChange(stockInvoiceMgrSearchModel);
  }

  public List queryShipMByShipId(GenInvoiceSearchModel genInvoiceSearchModel)
    throws Exception
  {
    return this.realfeedao.queryShipMByShipId(genInvoiceSearchModel);
  }

  public TplTransSeq queryTransSeq(Long seqId) throws Exception
  {
    return (TplTransSeq)this.realfeearhdao.queryById(seqId, TplTransSeq.class);
  }

  public void updateTplinsuranceBillByBo(TplRealFee tplRealFee) throws Exception
  {
    this.realfeedao.updateTplinsuranceBillByBo(tplRealFee);
  }

  public Map getWarehouseInfo(String providerId) throws Exception
  {
    Map map = new HashMap();
    List list = this.realfeedao.getWarehouseInfo(providerId);
    if ((list != null) && (list.size() > 0)) {
      for (int i = 0; i < list.size(); i++) {
        TplRProviderWarehouse rpr = (TplRProviderWarehouse)list.get(i);
        map.put(rpr.getWarehouseId(), rpr.getWarehouseName());
      }
    }
    return map;
  }

  public TplTransSeq queryTransSeqByTransNo(TplInvoice tplInvoice)
    throws Exception
  {
    return null;
  }

  public List queryTplRealFeeTrue(Long id)
  {
    return this.realfeedao.queryRealFeeTrueById(id);
  }

  public List queryRealFeeByInvoiceId(String invoiceSysId) throws Exception {
    return this.realfeedao.queryRealFeeByInvoiceId(invoiceSysId);
  }

  public String updateInvoiceSysIdByIds(List ids, Long newInvoiceId)
  {
    return this.realfeedao.updateInvoiceSysIdByIds(ids, newInvoiceId);
  }

  public HSSFWorkbook queryDownRealFeeExcel(RealFeeSearchModel realfeesearchmodel) throws Exception
  {
    List invoiceList = new ArrayList();

    HSSFWorkbook wb = new HSSFWorkbook();
    String[] headers = { "合同号", "毛重", "净重", "件数", "仓库代码", 
      "仓库名称", "存放天数", "保险费(RMB)", "费用类别", "费率(RMB)", "单价(RMB)", 
      "总金额(RMB)" };

    HSSFSheet sheet = wb.createSheet("realFee");

    HSSFRow row = sheet.createRow(0);

    for (int i = 0; i < headers.length; i++)
    {
      HSSFCell csCell = row.createCell((short)i);

      csCell.setEncoding(1);

      csCell.setCellValue(headers[i]);
    }

    realfeesearchmodel.setPage(null);

    List list = this.invoicedao
      .queryTplRealFeeListForDownLoad(realfeesearchmodel);
    if ((list == null) || (list.size() <= 0))
    {
      return wb;
    }

    int rowNum = 1;
    for (Iterator it = list.iterator(); it.hasNext(); )
    {
      HSSFRow row2 = sheet.createRow(rowNum);

      Object[] temp = (Object[])it.next();

      int k = 0;

      String orderNum = temp[0] == null ? "" : temp[0].toString();
      if (orderNum != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(orderNum);
      }
      k++;

      Double grossWeight = temp[1] == null ? new Double(0.0D) : 
        new Double(temp[1].toString());
      if (grossWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(grossWeight.doubleValue());
      }
      k++;

      Double netWeight = temp[2] == null ? new Double(0.0D) : 
        new Double(temp[2].toString());
      if (netWeight != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(netWeight.doubleValue());
      }
      k++;

      Long actcount = temp[3] == null ? new Long(0L) : 
        new Long(temp[3]
        .toString());
      if (actcount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(actcount.longValue());
      }
      k++;

      String wproviderId = temp[4] == null ? "" : temp[4].toString();
      if (wproviderId != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(wproviderId);
      }
      k++;

      String wproviderName = temp[5] == null ? "" : temp[5].toString();
      if (wproviderName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(wproviderName);
      }
      k++;

      Long actCount = temp[6] == null ? new Long(0L) : 
        new Long(temp[6]
        .toString());
      if (actCount != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(actCount.longValue());
      }
      k++;

      Double transCharges = temp[7] == null ? new Double(0.0D) : 
        new Double(temp[7].toString());
      if (transCharges != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(transCharges.doubleValue());
      }
      k++;

      String feeTypeName = temp[8] == null ? "" : temp[8].toString();
      if (feeTypeName != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(feeTypeName);
      }

      k++;

      Double unitPrice = temp[9] == null ? new Double(0.0D) : 
        new Double(temp[9].toString());
      if (unitPrice != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(unitPrice.doubleValue());
      }
      k++;

      Double avgPrice = temp[10] == null ? new Double(0.0D) : 
        new Double(temp[10].toString());
      if (avgPrice != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(avgPrice.doubleValue());
      }
      k++;

      Double unitTotalMoney = temp[11] == null ? new Double(11.0D) : 
        new Double(temp[11].toString());
      if (unitTotalMoney != null) {
        HSSFCell csCell = row2.createCell((short)k);
        csCell.setEncoding(1);
        csCell.setCellValue(unitTotalMoney.doubleValue());
      }
      rowNum++;
    }

    return wb;
  }

  public int getSearchCount(RealFeeSearchModel realfeesearchmodel) {
    int cntCheck = this.invoicedao
      .queryTplRealFeeListForDownLoadCnt(realfeesearchmodel);
    System.out.println("下载数据条数cntCheck===" + cntCheck);
    return cntCheck;
  }

  public String updateInvoiceType(String[] ids) throws Exception {
    for (int i = 0; i < ids.length; i++) {
      String idTemp = ids[i];
      if ((idTemp != null) && (!idTemp.equals(""))) {
        Long id = Long.valueOf(idTemp);
        TplInvoice tplinvoice = (TplInvoice)this.invoicedao.queryById(id, 
          TplInvoice.class);
        String invoiceType = tplinvoice.getInvoiceType();
        if (invoiceType.equals("9"))
          invoiceType = "8";
        else if (invoiceType.equals("8"))
          invoiceType = "9";
        else {
          return "false";
        }
        tplinvoice.setInvoiceType(invoiceType);
        this.invoicedao.update(tplinvoice, tplinvoice.getClass());
      }
    }

    return "true";
  }

  public List queryFeeDetialByInvoiceBill(String id, String invoiceType)
    throws Exception
  {
    return this.realfeedao.queryFeeDetialByInvoiceBill(id, invoiceType);
  }

  public List queryRealFeeListBySeqMId(Long seqMId, Long seqId, String transPlanId, String orderNum, String unitId)
    throws Exception
  {
    return this.realfeedao.queryRealFeeListBySeqMId(seqMId, seqId, transPlanId, 
      orderNum, unitId);
  }

  public List queryRealFeeList(Long seqId, String transPlanId, String orderNum, String unitId)
    throws Exception
  {
    return this.realfeedao
      .queryRealFeeList(seqId, transPlanId, orderNum, unitId);
  }

  public String[] queryIDsOfRealFeeBySeqId(Long seqId) throws Exception {
    List result = this.realfeedao.queryIDsOfRealFeeBySeqId(seqId);
    String[] strArray = new String[result.size()];
    for (int i = 0; i < result.size(); i++) {
      strArray[i] = ((Long)result.get(i));
    }
    return strArray;
  }

  public String[] queryIDsOfRealFeeBySeqMId(Long seqMId) throws Exception {
    List result = this.realfeedao.queryIDsOfRealFeeBySeqMId(seqMId);
    String[] strArray = new String[result.size()];
    for (int i = 0; i < result.size(); i++) {
      strArray[i] = ((Long)result.get(i));
    }
    return strArray;
  }

  public List queryInvoiceIdAndInvoice(String invoiceId, String invoiceNoStr) throws Exception
  {
    List list = this.realfeedao.queryInvoiceIdAndInvoiceNO(invoiceId, 
      invoiceNoStr);
    if ((list != null) && (list.size() > 0)) {
      List temp = new ArrayList();
      temp.add(invoiceId);
      temp.add(invoiceNoStr);
      return temp;
    }
    return null;
  }

  public void updateInvoiceIdAndInvoiceNoBySingle(String[] ids, String invoiceId, String invoiceNo)
    throws Exception
  {
    this.realfeedao.updateInvoiceIdAndInvoiceNO(ids[0], invoiceId, invoiceNo);
  }
}