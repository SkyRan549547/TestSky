package com.baosight.baosteel.bli.tpl.fee.struts.action;

import com.baosight.baosteel.bli.tpl.common.CommonActionUtil;
import com.baosight.baosteel.bli.tpl.common.LogConstants;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForward;
import com.baosight.web.struts.BAction;
import com.baosight.web.struts.BActionContext;
import com.baosight.web.struts.BaseResultModel;
import com.baosight.baosteel.bli.tpl.model.TplInsuranceBill;
import com.baosight.baosteel.bli.tpl.model.TplPlanFee;
import com.baosight.baosteel.bli.tpl.model.TplTransPlan;
import com.baosight.baosteel.bli.tpl.model.TplTransSeq;
import com.baosight.baosteel.bli.tpl.common.StringUtil;
import com.baosight.baosteel.bli.tpl.common.DateUtils;
import com.baosight.baosteel.bli.tpl.common.SystemConstants;
import com.baosight.baosteel.bli.tpl.common.UserBusiness;
import com.baosight.baosteel.bli.tpl.common.UserOperate;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.InsuranceBillSearchModel;
import com.baosight.baosteel.bli.tpl.fee.searcchmodel.PlanFeeSearchModel;
import com.baosight.baosteel.bli.tpl.fee.service.InsuranceBillService;
import com.baosight.baosteel.bli.tpl.fee.struts.form.InsuranceManageForm;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import baosight.baosteel.uup.client.model.UserSummary;

/**
 * @author Fu Qiang
 * 
 * @struts.action name="insuranceManageForm" path="/fee/insuranceTrainManage"
 *                validate="false" scope="request"
 * @struts.action-forward name="insuranceTrainManagePage"
 *                        path="fee.transport.insurance.train.insuranceTrainManagePage"
 * 
 * @struts.action-forward name="insuranceTrainViewPage"
 *                        path="fee.transport.insurance.train.insuranceTrainViewPage"
 * 
 * @struts.action-forward name="fail" path="3pl.common.error"
 */

public class InsuranceTrainManageAction extends BAction {

	private final static String dateFormat = SystemConstants.dateformat;
	
	protected ActionForward execute(BActionContext context) throws Exception {
		String method = StringUtil.trimStr(context.getRequest().getParameter(
				"method"));
		try{
			if (method.equalsIgnoreCase("listTrainInsurance"))
				return this.listInsuranceTrain(context);
			if (method.equalsIgnoreCase("delTrainInsurance"))
				return this.deleteInsuranceTrain(context);
			if (method.equalsIgnoreCase("viewTrainInsurance"))
				return this.viewInsuranceTrain(context);
			if (method.equalsIgnoreCase("rewriteInsurance"))
				return this.rewriteInsuranceTrain(context);
			if (method.equalsIgnoreCase("downLoadInsurance"))
				return this.downLoadInsuranceShip(context);
			/***ranqiguang add start 20180913***/
			if (method.equalsIgnoreCase("updTrainInsurance"))
				return this.updateInsuranceTrain(context);//保险费修改
			/***ranqiguang add end 20180913***/
			/***ranqiguang add start 20181107*******/
			if (method.equalsIgnoreCase("doMergeInsurance"))
				return this.doMergeInsurance(context);
			if (method.equalsIgnoreCase("querySubInsurance"))
				return this.querySubInsurance(context);
			/***ranqiguang add end 20181107*******/
			return this.listInsuranceTrain(context);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			if (e.getMessage()!=null && e.getMessage().startsWith("msg:")) {
				context.getRequest().setAttribute("msg",
						e.getMessage().substring(4));
			} else {
				context.getRequest().setAttribute("error", e);
			}
			return context.findForward("fail");
		}
	}

	/**
	 * 查询铁运保单列表;
	 * 
	 * @param context
	 * @return
	 */
	private ActionForward listInsuranceTrain(BActionContext context) {
		InsuranceBillService service = (InsuranceBillService) context
				.findService("insuranceBillService", InsuranceBillService.class);
		String providerId = null;
		if (SystemConstants.PURVIEW_CONTROL) {
			UserSummary userSummary = (UserSummary) context.getSession()
					.getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
			providerId = userSummary.getMemberNumber();
		}
		if (SystemConstants.BUTTON_CONTROL) {
			UserSummary userSummary = (UserSummary) context.getSession()
					.getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
			boolean search = userSummary
					.hasPrivilege("WL_FEE_TRANSFEEMGR_INSURANCE_CHOOCHOO_SEARCH");
			boolean del = userSummary
					.hasPrivilege("WL_FEE_TRANSFEEMGR_INSURANCE_CHOOCHOO_DEL");
			boolean rewrite = userSummary
					.hasPrivilege("WL_FEE_TRANSFEEMGR_INSURANCE_CHOOCHOO_REWRITE");
			boolean download = userSummary
					.hasPrivilege("WL_FEE_TRANSFEEMGR_INSURANCE_CHOOCHOO_DOWNLOAD");
			/***ranqigaung add start 20190914****/
			boolean update = userSummary
					.hasPrivilege("WL_FEE_TRANSFEEMGR_INSURANCE_CHOOCHOO_UPD");//增加修改按钮权限
			boolean merge = userSummary
			.hasPrivilege("WL_FEE_TRANSFEEMGR_INSURANCE_CHOOCHOO_UPD");////增加合并按钮权限
			if (update) {
				context.getRequest().setAttribute("updatePermit", "yes");
			} else {
				context.getRequest().setAttribute("updatePermit", "no");
			}
			if (merge) {
				context.getRequest().setAttribute("doMergeInsurancePermit", "yes");
			} else {
				context.getRequest().setAttribute("doMergeInsurancePermit", "no");
			}
			/***ranqigaung add end 20190914****/
			if (search) {
				context.getRequest().setAttribute("searchPermit", "yes");
			} else {
				context.getRequest().setAttribute("searchPermit", "no");
			}
			if (del) {
				context.getRequest().setAttribute("delPermit", "yes");
			} else {
				context.getRequest().setAttribute("delPermit", "no");
			}
			if (rewrite) {
				context.getRequest().setAttribute("rewritePermit", "yes");
			} else {
				context.getRequest().setAttribute("rewritePermit", "no");
			}
			if (download) {
				context.getRequest().setAttribute("downloadPermit", "yes");
			} else {
				context.getRequest().setAttribute("downloadPermit", "no");
			}
		} else {
			context.getRequest().setAttribute("searchPermit", "yes");
			context.getRequest().setAttribute("delPermit", "yes");
			context.getRequest().setAttribute("rewritePermit", "yes");
			context.getRequest().setAttribute("downloadPermit", "yes");
			/***ranqigaung add start 20190914****/
			context.getRequest().setAttribute("doMergeInsurancePermit", "yes");//增加合并按钮权限
			context.getRequest().setAttribute("updatePermit", "yes");//增加修改按钮权限
			/***ranqigaung add end 20190914****/
		}
		InsuranceManageForm form = (InsuranceManageForm) context.getForm();
		InsuranceBillSearchModel searchModel = new InsuranceBillSearchModel();
		BaseResultModel brm = new BaseResultModel();

		searchModel.setOrderNum(StringUtil.trimStr(form.getOrderNum()));
		searchModel.setTrainNo(StringUtil.trimStr(form.getTrainNo()));
		searchModel.setGoodBillCode(StringUtil.trimStr(form.getGoodBillCode()));
		
		//Added by Su Sheng Jie
		UserBusiness userBusiness = CommonActionUtil.getUserBusinessPrev(context, new UserOperate("InsuranceTrainManageAction","listTrainInsurance"));
		searchModel.setUserBusiness(userBusiness);
		
		searchModel.setRewriteStatus(StringUtil
				.trimStr(form.getRewriteStatus()));
		searchModel.setInsurId(StringUtil.trimStr(form.getInsurId()));
		searchModel.setTransType(SystemConstants.TRANS_TYPE_TRAIN);
		searchModel.setInsurStatus(SystemConstants.FEE_INSUR_STATUS_MAKEED);
		searchModel.setProviderId(providerId);
		searchModel.getPage().fillPageWithParameter(context.getRequest());
		try {
			String sOperateDate1 = StringUtil.trimStr(form.getOperateDate1());
			String sOperateDate2 = StringUtil.trimStr(form.getOperateDate2());
			if (sOperateDate1.length() > 0 && sOperateDate2.length() > 0) {
				searchModel.setOperateDate1(DateUtils.toDate(sOperateDate1,
						SystemConstants.dateformat));
				searchModel.setOperateDate2(DateUtils.toDate(sOperateDate2,
						SystemConstants.dateformat));
			}
			brm = service.queryTplInsuranceBillWithArgsByPage(searchModel);
			context.setBean("results", brm, BActionContext.SCOPE_REQUEST);
			
			Map totalInfo=service.queryTotalInsuranceInfo(searchModel);
			Double totalGrossWeight=(Double)totalInfo.get("totalGrossWeight");
			Double totalNetWeight=(Double)totalInfo.get("totalNetWeight");
			Long totalCount=(Long)totalInfo.get("totalCount");
			context.getRequest().setAttribute("totalGrossWeight", totalGrossWeight);
			context.getRequest().setAttribute("totalNetWeight", totalNetWeight);
			context.getRequest().setAttribute("totalCount", totalCount);
		} catch (Exception e) {
			logger.error(e);
			context.getRequest().setAttribute("error", e);
			return context.findForward("fail");
		}
		
		return context.findForward("insuranceTrainManagePage");
	}

	/**
	 * 删除铁运保单;
	 * 
	 * @param context
	 * @return
	 */
	private ActionForward deleteInsuranceTrain(BActionContext context) {
		InsuranceBillService service = (InsuranceBillService) context
				.findService("insuranceBillService", InsuranceBillService.class);
		InsuranceManageForm form = (InsuranceManageForm) context.getForm();
		String[] ids = form.getIds();

		try {
			String msgInfo = service.deleteTplInsuranceBillTrain(ids);

			// Action Log
			CommonActionUtil commonActionUtil = new CommonActionUtil();
			for (int i = 0; i < ids.length; i++) {
				commonActionUtil.addActionLog(context,
						LogConstants.WL_FEE_CHOO_INSURANCE_DEL, "铁运保单删除,保单ID："
								+ ids[i]);
			}
			if (msgInfo != null) {
				context.getRequest().setAttribute("msgInfo", msgInfo);
			}
		} catch (Exception e) {
			logger.error(e);
			context.getRequest().setAttribute("error", e);
			return context.findForward("fail");
		}
		return this.listInsuranceTrain(context);
	}

	/**
	 * 查看铁运保单详情;
	 * 
	 * @param context
	 * @return
	 */
	private ActionForward viewInsuranceTrain(BActionContext context) {
		InsuranceBillService service = (InsuranceBillService) context
				.findService("insuranceBillService", InsuranceBillService.class);
		TplInsuranceBill tplInsuranceBill = new TplInsuranceBill();
		String idTmp = (StringUtil.trimStr(context.getRequest().getParameter(
				"id")));
		if (idTmp.length() > 0) {
			Long id = Long.valueOf(idTmp);
			try {
				tplInsuranceBill = service.queryTplInsuranceBillById(id);
				if(tplInsuranceBill!=null){
					String transPlanId = null == tplInsuranceBill.getTransPlanId()?"":tplInsuranceBill.getTransPlanId();
					TplTransPlan transPlan = service.queryTplTransPlan(transPlanId);
					//获取合同性质
					String orderNum = null== tplInsuranceBill.getOrderNum()?"":tplInsuranceBill.getOrderNum();
					// 取得费用计划
					PlanFeeSearchModel searchModel = new PlanFeeSearchModel();
					searchModel.setPlanId(transPlan.getTransPlanId());
					searchModel.setUnitId(transPlan.getUnitId());
					searchModel.setOrderNum(orderNum);
					List planFeeList = service.queryPlanFeeListWithArgs(searchModel);
					TplPlanFee planFee = null;
					if (null != planFeeList && planFeeList.size() > 0) {
						planFee = (TplPlanFee)planFeeList.get(0);
					}
					
					if (null == planFee.getInsuranceFlag() || "".equals(planFee.getInsuranceFlag())) {// 保单改造前模式，解决历史数据不兼容问题
						double unitPrice=tplInsuranceBill.getUnitPrice().doubleValue();
//						if(tplInsuranceBill.getManuId()!=null && tplInsuranceBill.getManuId().equalsIgnoreCase("BGTM")){
//							unitPrice = unitPrice / 0.0003486;
//						}else{
//							unitPrice = unitPrice / 0.0003486;
//						}	
						
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
						double insurBasePrice = unitPrice / insuranceRate;
						tplInsuranceBill.setIntegrateInsurance(null);
						tplInsuranceBill.setUnitPrice(new Double(StringUtil.round(insurBasePrice,2)));
					} else if("10".equalsIgnoreCase(planFee.getInsuranceFlag())) {// 保单改造后，沿用新的模式
						List list = service.queryProductInfoOfOrder(transPlan.getId(), orderNum, SystemConstants.TRANS_PACK_STATUS_LOADING);
						if (list != null && list.size() > 0) {
							Object[] items = (Object[]) list.get(0);
							tplInsuranceBill.setIntegrateInsurance((Double) items[8]);
							tplInsuranceBill.setUnitPrice((Double) items[13]);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e);
				context.getRequest().setAttribute("error", e);
				return context.findForward("fail");
			}
		}
		context.getRequest().setAttribute("result", tplInsuranceBill);
		return context.findForward("insuranceTrainViewPage");
	}

	/**
	 * 回写铁运保单;
	 * 
	 * @param context
	 * @return
	 */
	private ActionForward rewriteInsuranceTrain(BActionContext context) {
		InsuranceBillService service = (InsuranceBillService) context
				.findService("insuranceBillService", InsuranceBillService.class);
		InsuranceManageForm form = (InsuranceManageForm) context.getForm();
//		String providerId = null;
//		if (SystemConstants.PURVIEW_CONTROL) {
//			UserSummary userSummary = (UserSummary) context.getSession()
//					.getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
//			providerId = userSummary.getMemberNumber();
//		}
		
		String[] ids = form.getIds();
		String insurBillCodeStart = StringUtil.trimStr(form.getInsurIdStart());
		String insurBillCodeEnd = StringUtil.trimStr(form.getInsurIdEnd());

		long start = Long.valueOf(insurBillCodeStart).longValue();
		long end = Long.valueOf(insurBillCodeEnd).longValue();
		long temp = end - start;

		if (ids.length != temp + 1) {
			return context.findForward("fail");
		}

		try {
//			boolean flag=service.getOnlyInsuranceCode(providerId, String.valueOf(start), String.valueOf(end));
//			if(flag){
//				context.getRequest().setAttribute("msgInfo", "保单编号已经被使用,不能再用来回填!");
//			}else{
				service.updateInsurBillCode(ids, insurBillCodeStart);
				//Action Log
				CommonActionUtil commonActionUtil = new CommonActionUtil();
				for (int i = 0; i < ids.length; i++) {
					commonActionUtil.addActionLog(context,
							LogConstants.WL_FEE_CHOO_INSURANCE_RETURN,
							"铁运保单号回写,保单ID：" + ids[i]);
				}
//			}			
		} catch (Exception e) {
			logger.error(e);
			context.getRequest().setAttribute("error", e);
			return context.findForward("fail");
		}
		form.setIds(new String[0]);
		return this.listInsuranceTrain(context);
	}

	/**
	 * 下载铁运保单信息;
	 * 
	 * @param context
	 * @return
	 */
	private ActionForward downLoadInsuranceShip(BActionContext context)throws Exception {
		InsuranceBillService service = (InsuranceBillService) context
				.findService("insuranceBillService", InsuranceBillService.class);
		InsuranceManageForm form = (InsuranceManageForm) context.getForm();
		 String providerId=null;
		 if(SystemConstants.PURVIEW_CONTROL){
		 UserSummary userSummary = (UserSummary) context.getSession()
		 .getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
		 providerId=userSummary.getMemberNumber();
		 }
		 InsuranceBillSearchModel insurBillSearch=new InsuranceBillSearchModel();
		 ServletOutputStream out = null;
		String[] ids = form.getIds();
		if(null==ids||ids.length<=0){
			throw new Exception("msg:请您选择需要下载的铁运保单数据。");
		}
		if (null!=ids&&ids.length>30){
			throw new Exception("msg:您最多只能下载30条铁运保单数据。");
		}
		try {
			insurBillSearch.setIds(ids);
			insurBillSearch.setOrderNum(form.getOrderNum());
			insurBillSearch.setTrainNo(form.getTrainNo());
			insurBillSearch.setGoodBillCode(form.getGoodBillCode());
			insurBillSearch.setRewriteStatus(form.getRewriteStatus());
			insurBillSearch.setInsurId(form.getInsurId());
			String dateData1=StringUtil.trimStr(form.getOperateDate1());
			String dateData2=StringUtil.trimStr(form.getOperateDate2());
			if(dateData1.length()>0 && dateData2.length()>0){
				insurBillSearch.setOperateDate1(DateUtils.toDate(dateData1, dateFormat));
				insurBillSearch.setOperateDate2(DateUtils.toDate(dateData2, dateFormat));
			}			
			insurBillSearch.setProviderId(providerId);
			insurBillSearch.setTransType(SystemConstants.TRANS_TYPE_TRAIN);
			
			HttpServletResponse response = context.getResponse();
			HSSFWorkbook wb = service.getDownLoadInsurances(insurBillSearch);

			Date currentTime = new Date();
			String fileName = "InsuranceBill"
					+ DateUtils.toString(currentTime, "yyyyMMddHHmmss")
					+ ".xls";

			response.setContentType("application/multipart");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);

			out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			out = null;
			CommonActionUtil commonActionUtil = new CommonActionUtil();
			commonActionUtil.addActionLog(context,
						LogConstants.WL_FEE_CHOO_INSURANCE_DOWNLOAD, "铁运保单下载条数："
								+ ids.length);
			return null;

		} catch (Exception e) {
			if (null!=out){
				out.flush();
				out.close();
				out = null;
			}
			e.printStackTrace();
			logger.error(e);
			if (e.getMessage()!=null && e.getMessage().startsWith("msg:")) {
				context.getRequest().setAttribute("msg",
						e.getMessage().substring(4));
			} else {
				context.getRequest().setAttribute("error", e);
			}
			return context.findForward("fail");
		}finally{
			if (null!=out){
				out.flush();
				out.close();
			}
		}
	}
	
	/*******ranqiguang add start 20180913********/
	/***
	 * 修改保险费
	 * @param context
	 * @return
	 */
	private ActionForward updateInsuranceTrain(BActionContext context) {
		InsuranceBillService service = (InsuranceBillService) context
				.findService("insuranceBillService", InsuranceBillService.class);
		InsuranceManageForm form = (InsuranceManageForm) context.getForm();
		String[] idsels = form.getIds();
		String[] insuranceTotalNum = form.getInsuranceTotalNumAdjusts();// 税前金额
//		String[] insuranceTotalNum = context.getRequest().getParameterValues("insuranceTotalNum"); // 税前金额
		String[] ids = context.getRequest().getParameterValues("id");
		try {
			CommonActionUtil commonActionUtil = new CommonActionUtil();
			String msgInfo = null;
			msgInfo = service.updateTplInsuranceBillTrain(idsels,insuranceTotalNum, ids);
			if (msgInfo != null && !"".equals(msgInfo)) {
				context.getRequest().setAttribute("msgInfo", msgInfo);
				return this.listInsuranceTrain(context);
			}
			for(int i=0; i<idsels.length; i++){
				commonActionUtil.addActionLog(context,
						LogConstants.WL_FEE_CHOO_INSURANCE_DEL, "铁运保单修改,保单ID："
								+ idsels[i]);
			}
				
		} catch (Exception e) {
			logger.error(e);
			context.getRequest().setAttribute("error", e);
			return context.findForward("fail");
		}
		return this.listInsuranceTrain(context);
	}
	/*******ranqiguang add end 20180913********/
	/*******ranqiguang add start 20181107********/
	private ActionForward doMergeInsurance(BActionContext context){
		InsuranceManageForm form = (InsuranceManageForm) context.getForm();
		String[] idsels = form.getIds();
		String[] ids = context.getRequest().getParameterValues("id");
		InsuranceBillService service = (InsuranceBillService) context
		.findService("insuranceBillService", InsuranceBillService.class);
		try{
			CommonActionUtil commonActionUtil = new CommonActionUtil();
			String msgInfo = null;
			String createId = null;
			if (SystemConstants.PURVIEW_CONTROL) {
				UserSummary userSummary = (UserSummary) context.getSession()
				.getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
				createId = userSummary.getName();
			}
			msgInfo = service.mergeInsuranceBillTrain(idsels,createId);
			if (msgInfo != null && !"".equals(msgInfo)) {
				context.getRequest().setAttribute("msgInfo", msgInfo);
				return this.listInsuranceTrain(context);
			}
			for(int i=0; i<idsels.length; i++){
				commonActionUtil.addActionLog(context,
						LogConstants.WL_FEE_CHOO_INSURANCE_DEL, "铁运保单修改,保单ID："+ idsels[i]);
			}
		}
		catch(Exception e){
			return context.findForward("fail");
		}
		return this.listInsuranceTrain(context);
	}
	
	
	private ActionForward querySubInsurance(BActionContext context) throws Exception{
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		String id = request.getParameter("id");
		InsuranceBillService service = (InsuranceBillService) context
		.findService("insuranceBillService", InsuranceBillService.class);
		List results =service.getSubInsuranceBill(id);
		
		String result ="";
		if (results != null) {
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				TplInsuranceBill bill = (TplInsuranceBill) iterator.next();
				result += subInsurance(bill);
				result += "||";
			}
		}
		if (results.size() > 0) {
			result = result.substring(0, result.trim().length() - 2);
		}
		response.setContentType("text/html; charset=utf-8");
		System.out.println(result);
		response.getWriter().print(result);
		return null;
	}
	
	private String subInsurance(TplInsuranceBill bill){
		String rewriteStus ="";
		if(null!=bill){
			if("1".equals(bill.getRewriteStatus())){
				rewriteStus="已回写";
			}else{
				rewriteStus="未回写";
			}
			String onclick ="window.open('../fee/insuranceTrainManage.do?method=viewTrainInsurance&id="+bill.getId()+"');";
			String input ="<input class='button01' type='button' value='查看' onclick="+onclick +" />";
			String  res=
			"&nbsp;"
			+ "$$"
			+ StringUtil.replaceBySpace(bill.getInsurId())
			+ "$$"
			+ StringUtil.replaceBySpace(bill.getGoodBillCode()
					+ "")
			+ "$$"
			+ StringUtil.replaceBySpace(bill.getOrderNum())
			+ "$$"
			+ StringUtil.replaceBySpace(bill.getTransPlanId()
					.toString())
			+ "$$"
			+ StringUtil.replaceBySpace(String.valueOf(bill.getTotalCount()))
			+ "$$"
			+ StringUtil.replaceBySpace(String.valueOf(bill.getTotalGrossWeight()))
			+ "$$" + StringUtil.replaceBySpace(String.valueOf(bill.getTotalNetWeight()))
			+ "$$" + StringUtil.replaceBySpace(String.valueOf(bill.getInsuranceTotalNum()))
			+ "$$" + StringUtil.replaceBySpace(String.valueOf(bill.getInsuranceTotalNumAdjust()))
			+ "$$" + StringUtil.replaceBySpace(String.valueOf(bill.getConsigneeName()))
			+ "$$" + StringUtil.replaceBySpace(rewriteStus)
			+ "$$" + StringUtil.replaceBySpace(input);
			return res;
		}
		return "&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;$$&nbsp;";
	}
	/*******ranqiguang add end 20181107********/
}
