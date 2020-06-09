package NewTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionForward;

import baosight.baosteel.uup.client.model.UserSummary;

import com.baosight.baosteel.bli.tpl.base.service.ProcFlagService;
import com.baosight.baosteel.bli.tpl.common.CommonActionUtil;
import com.baosight.baosteel.bli.tpl.common.LogConstants;
import com.baosight.baosteel.bli.tpl.common.StringUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConfigUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConstants;
import com.baosight.baosteel.bli.tpl.model.SearchStockModel;
import com.baosight.baosteel.bli.tpl.model.TplStock;
import com.baosight.baosteel.bli.tpl.model.TplStockIn;
import com.baosight.baosteel.bli.tpl.model.TplStockInItem;
import com.baosight.baosteel.bli.tpl.model.TplStockInPlan;
import com.baosight.baosteel.bli.tpl.stock.searchmodel.StockInPlanParamModel;
import com.baosight.baosteel.bli.tpl.stock.service.StockInManageService;
import com.baosight.baosteel.bli.tpl.stock.service.StockService;
import com.baosight.baosteel.bli.tpl.stock.struts.form.StockInManageForm;
import com.baosight.web.struts.BActionContext;

public class StockInMgrAction {
	/**
	 * �������ύ�嵥
	 * 
	 * @param context
	 *            BActionContext
	 * @return ActionForward ��ת����
	 * @throws Exception
	 */

	private ActionForward insertCommitBill(BActionContext context) {
		String modifyId = null;
		if (SystemConstants.PURVIEW_CONTROL) {
			UserSummary userSummary = (UserSummary) context.getSession()
					.getAttribute(SystemConstants.SESSION_USERSUMMARY_KEY);
			modifyId = userSummary.getName();
		}
		StockInManageService mservice = (StockInManageService) context
				.findService("stockInManageService", StockInManageService.class);
		ProcFlagService procFlagService = (ProcFlagService) context
				.findService("procFlagService", ProcFlagService.class);

		StockService stockService = (StockService) context.findService(
				"stockService", StockService.class);

		StockInManageForm stockinmanageform = (StockInManageForm) context
				.getForm();
		try {
			String[] itemIds = stockinmanageform.getCheckboxs();// �����������id
			// ��������IDû���뵥��
			String planPutinId = context.getRequest().getParameter(
					"planPutinId");
			String pickNum = context.getRequest().getParameter("pickNum");
			String pickType = context.getRequest().getParameter("pickType");
			String manuId = context.getRequest().getParameter("manuId");
			// �����ţ�ҵ���ϵ�ID��û���뵥��
			List packWithoutStack = new ArrayList();
			// �����ţ������ϵ�ID��û���뵥��
			List itemIdWithoutStack = new ArrayList();

			/**
			 * �ᵥ������61�����ƻ���������ʱ����Ҫÿһ��ѡ�е�����У���뵥��
			 * ����뵥�����ڣ���¼���������������뵥������������⡣�����ʾ�û���Щ�������뵥 added by SU Shengjie
			 */
			if (pickType != null
					&& SystemConfigUtil.STOCK_IN_TYPE_AREA_CHUCHANG
							.equals(pickType)) {
				List itemIdsNoStack = mservice.queryItemsWithNoStack(
						planPutinId, pickNum, itemIds, manuId);
				if (itemIdsNoStack != null && itemIdsNoStack.size() > 0) {
					for (int i = 0; i < itemIdsNoStack.size(); i++) {
						TplStockInItem itemsNoStack = (TplStockInItem) itemIdsNoStack
								.get(i);
						packWithoutStack.add(itemsNoStack.getPackId());
						itemIdWithoutStack.add(itemsNoStack.getId().toString());
						throw new Exception("msg:��ѡ���������������뵥��δ���룬������ѡ����Ժ����ԡ�");
					}
				}
			}

			logger.info("--part in stock--insertCommitBill--planPutinId:"
					+ planPutinId + " requested on " + new Date()
					+ " Amt_Items: " + itemIds);
			// �жϸ������Ƿ���⣬��ֹ��������
			for (int i = 0; i < itemIds.length; i++) {
				TplStockInItem tplStockInItem = (TplStockInItem) mservice
						.getTplStockInItemById(Long.valueOf(itemIds[i]));
				Long putinId = tplStockInItem.getPutinId();
				String packId = tplStockInItem.getPackId();
				if (packId.startsWith("WW") || packId.startsWith("WGNK")) {
					context.getRequest().setAttribute("msg",
							"�üƻ���Ӧ����������ϸδ���ɣ����Ժ��������ϵ����Ա!");
					return this.queryInStorePlanMgrList(context);
				}
				if (null != putinId) {
					context.getRequest().setAttribute("msg",
							"Ҫ�����������������Ѿ�����⣬������ѡ��");
					return this.queryInStorePlanMgrList(context);
				}

				if (procFlagService.isTubeProcFlag(new Long(itemIds[i]),
						"packIn")) {
					throw new Exception("msg:�������ͬ����:" + packId + "δ��ˢ�£�������ѡ��!");
				}

				// ---------------------------------------------------------
				TplStockInPlan tplStockInPlan = mservice
						.getTplStockInPlanById(tplStockInItem.getPlanId());
				if (SystemConstants.STOCK_IN_PLAN_EXTEND_WAREHOUSE_FLAG_Y
						.equals(tplStockInPlan.getExtendWarehouseFlag())
						|| SystemConfigUtil.TPL_STOCK_PLAN_SALESNETWORK
								.contains(tplStockInItem.getOrderNum()
										.substring(2, 3))
						|| StringUtil
								.trimStr(tplStockInItem.getPackOrderType())
								.startsWith("QY")) {
					// �ж�ѡ��ļƻ��µ������Ƿ��ڿ���д���

					// ������,�򿴿��״̬
					SearchStockModel stockModel = new SearchStockModel();
					stockModel.setProductClass(tplStockInItem.getOrderNum()
							.substring(0, 1));
					stockModel.setPackId(tplStockInItem.getPackId());
					stockModel.setExtendWarehouseFlag(tplStockInPlan
							.getExtendWarehouseFlag());
					stockModel.setUnitId(tplStockInPlan.getUnitId());
					stockModel.setManuId(tplStockInPlan.getManuId());
					Long stockId = stockService.queryStockList(stockModel);
					pickNum = StringUtil.trimStr(pickNum);
					if (SystemConstants.STOCK_IN_PLAN_EXTEND_WAREHOUSE_FLAG_Y
							.equals(tplStockInPlan.getExtendWarehouseFlag())
							&& !pickNum.equals("")
							&& ((!pickNum.substring(1, 2).equals("F") && SystemConfigUtil
									.judgeBaosteelExtend(tplStockInPlan
											.getUnitId())) || SystemConfigUtil.BAOSTEEL_BGTM
									.equals(tplStockInPlan.getUnitId()))
							&& !pickType.startsWith("7")) {
						// ��������������嵥,�жϳ���ʵ���Ƿ��� modify by llk 2013/1/21
						if (!StringUtil.trimStr(tplStockInItem.getProcFlag())
								.equals("10")) {
							context.getRequest().setAttribute(
									"msg",
									"ҵ���ܼ���������������������"
											+ tplStockInItem.getPackId()
											+ " �޳���ʵ��������ϵ��Դ�ֿ�");
							return this.queryInStorePlanMgrList(context);
						}
					}
					if (null != stockId) {
						TplStock stock = stockService
								.queryTplStockById(stockId);
						// STOCK_FLAG ����־ 10��� 20����
						if (!SystemConstants.TPL_STOCK_FLAG_OUT.equals(stock
								.getStockFlag())) {
							// �˼ƻ���ת��ƻ�,�ÿ�滹δ������,�ʲ�����������
							context.getRequest().setAttribute("msg",
									"ҵ���ܼ�������Դ�ֿ⻹û�������⣬����ϵ��Դ�ֿ�");
							return this.queryInStorePlanMgrList(context);
						}
					} else {

					}
				}

				// ---------------------------------------------------------
				else {
					/*
					 * �ᵥ������30�����ƻ���������ʱ����Ҫдһ�����鷽����
					 * ����ʱ����Ҫ��鱾������������Ӧ��ͬ�ᵥ���µĳ���ƻ������Ƿ���⣬
					 * ���û�г��⣬����ʾ.ҵ���ܼ�������Դ�ֿ⻹û�������⣬����ϵ��Դ�ֿ⣡ add by zhengfei
					 * 20080417
					 */
					boolean flag = mservice
							.queryStockOutItemAndOutByInItem(tplStockInItem);
					if (flag) {
						context.getRequest().setAttribute("msg",
								"ҵ���ܼ�������Դ�ֿ⻹û�������⣬����ϵ��Դ�ֿ�");
						return this.queryInStorePlanMgrList(context);

					}
				}

			}
			HashMap hashmap = new HashMap();
			for (int i = 0; i < itemIds.length; i++) {
				hashmap.put(i + "", itemIds[i]);
			}

			int total = Integer.valueOf(
					context.getRequest().getParameter("total")).intValue();
			// ��������
			String[] idsTotal = new String[total];
			// ��λ
			String[] location = new String[itemIds.length];
			String[] levels = new String[itemIds.length];
			// ����״̬
			String[] stockStatus = new String[itemIds.length];
			List locationList = new ArrayList();
			List stockStatusList = new ArrayList();
			List levelList = new ArrayList();// add by llk ���
			List readyNumList = new ArrayList();
			String wareHouseFlag = StringUtil.trimStr(context.getRequest()
					.getParameter("warehouseType1"));
			String unitId = StringUtil.trimStr(context.getRequest()
					.getParameter("unitId"));
			for (int i = 0; i < idsTotal.length; i++) {
				idsTotal[i] = context.getRequest().getParameter("id_" + i);
				if (hashmap.containsValue(idsTotal[i])) {

					String locationTemp = StringUtil.trimStr(context
							.getRequest().getParameter("location_" + i));
					if ("".equals(locationTemp)
							&& wareHouseFlag.equals("10")
							&& (SystemConfigUtil.judgeBaosteelExtend(unitId) || SystemConfigUtil.BAOSTEEL_BGTM
									.equals(unitId))) {
						context.getRequest().setAttribute("msg",
								"ҵ���ܼ��������������,���������������");
						return this.queryInStorePlanMgrList(context);
					}
					locationList.add(locationTemp);

					String stockStatusTemp = context.getRequest().getParameter(
							"stockStatus_" + i);
					stockStatusList.add(stockStatusTemp);

					// �õ���ͬ��
					String orderNum = StringUtil.trimStr(context.getRequest()
							.getParameter("orderNums_" + i));
					String subOrderNum = "";
					if (!orderNum.equals("")) {
						subOrderNum = orderNum.substring(0, 1);
					}
					if ("J".equals(subOrderNum) && wareHouseFlag.equals("10")
							&& SystemConfigUtil.judgeBaosteelExtend(unitId)) {
						// ȥ�ò��
						String levelNum = StringUtil.trimStr(context
								.getRequest().getParameter("level_" + i));
						levelList.add(levelNum);
					}
					if ("".equals(locationTemp)
							&& (orderNum.substring(2, 3).equals("J") || orderNum
									.substring(2, 3).equals("W"))) {
						context.getRequest().setAttribute("msg",
								"ҵ���ܼ������ֻ�����������������");
						return this.queryInStorePlanMgrList(context);
					}
					String readyNumTemp = StringUtil.trimStr(context
							.getRequest().getParameter("readyNum_" + i));
					readyNumList.add(readyNumTemp);
				}
			}

			for (int i = 0; i < locationList.size(); i++) {
				location[i] = null == locationList.get(i) ? null : locationList
						.get(i).toString();
			}
			for (int i = 0; i < stockStatusList.size(); i++) {
				stockStatus[i] = null == stockStatusList.get(i) ? null
						: stockStatusList.get(i).toString();
			}

			for (int i = 0; i < levelList.size(); i++) {
				levels[i] = null == levelList.get(i) ? null : levelList.get(i)
						.toString();
			}

			String idStr = stockinmanageform.getId();
			if (null == idStr || "".equals(idStr)) {
				throw new Exception("����ϼܴ��󣺼ƻ�idΪ��");
			}

			// ����������������嵥��ʱ��Ҫ��׼���Ų�һ�����������зֲ����ɲ�ͬ���嵥�� add by panyouyong
			if ("10".equals(wareHouseFlag)
					&& SystemConfigUtil.judgeBaosteelExtend(unitId)) {
				List readyNumList2 = new ArrayList();
				for (int i = 0; i < readyNumList.size(); i++) {
					if (!readyNumList2.contains(readyNumList.get(i))) {
						readyNumList2.add(readyNumList.get(i));
					}
				}

				for (int i = 0; i < readyNumList2.size(); i++) {
					List itemIdList2 = new ArrayList();
					List locationList2 = new ArrayList();
					List stockStatusList2 = new ArrayList();
					List levelList2 = new ArrayList();
					for (int j = 0; j < readyNumList.size(); j++) {
						if (readyNumList2.get(i).equals(readyNumList.get(j))) {
							itemIdList2.add(itemIds[j]);
							locationList2.add(location[j]);
							stockStatusList2.add(stockStatus[j]);
							levelList2.add(levels[j]);
						}
					}

					// ��������
					String[] itemIds2 = new String[itemIdList2.size()];
					// ��λ
					String[] location2 = new String[locationList2.size()];
					// ����״̬
					String[] stockStatus2 = new String[stockStatusList2.size()];
					// ���
					String[] levels2 = new String[levelList2.size()];

					for (int j = 0; j < itemIdList2.size(); j++) {
						itemIds2[j] = null == itemIdList2.get(j) ? null
								: itemIdList2.get(j).toString();
					}
					for (int j = 0; j < locationList2.size(); j++) {
						location2[j] = null == locationList2.get(j) ? null
								: locationList2.get(j).toString();
					}
					for (int j = 0; j < stockStatusList2.size(); j++) {
						stockStatus2[j] = null == stockStatusList2.get(j) ? null
								: stockStatusList2.get(j).toString();
					}
					for (int j = 0; j < levelList2.size(); j++) {
						levels2[j] = null == levelList2.get(j) ? null
								: levelList2.get(j).toString();
					}

					StockInPlanParamModel stockInPlanParamModel = new StockInPlanParamModel();
					stockInPlanParamModel.setId(Long.valueOf(idStr));
					stockInPlanParamModel.setCreateDate(stockinmanageform
							.getCreateDate());// ʵ�����ʱ��
					stockInPlanParamModel.setCreateId(stockinmanageform
							.getCreateId());// ��⾭����
					stockInPlanParamModel.setItemIds(itemIds2);
					stockInPlanParamModel.setLocation(location2);
					stockInPlanParamModel.setLevels(levels2);// add by llk
																// 2012/7/31
																// ��������������
					stockInPlanParamModel.setStockStatus(stockStatus2);
					stockInPlanParamModel.setModifyId(modifyId);
					stockInPlanParamModel.setMemo(stockinmanageform.getMemo());

					// ���òֿ�Ȩ���б�
					CommonActionUtil commonAction = new CommonActionUtil();
					stockInPlanParamModel.setWproviderMap(commonAction
							.getLoginUserPrev(context));
					TplStockIn tplStockIn = mservice
							.insertCommitBillDirect(stockInPlanParamModel);
					CommonActionUtil commonActionUtil = new CommonActionUtil();
					commonActionUtil.addActionLog(context,
							LogConstants.WL_STOCK_IN_COMMIT, "�������ύ�嵥,�嵥id��"
									+ tplStockIn.getId());
				}
			} else {
				StockInPlanParamModel stockInPlanParamModel = new StockInPlanParamModel();
				stockInPlanParamModel.setId(Long.valueOf(idStr));
				stockInPlanParamModel.setCreateDate(stockinmanageform
						.getCreateDate());// ʵ�����ʱ��
				stockInPlanParamModel.setCreateId(stockinmanageform
						.getCreateId());// ��⾭����
				stockInPlanParamModel.setItemIds(itemIds);
				stockInPlanParamModel.setLocation(location);
				stockInPlanParamModel.setLevels(levels);// add by llk 2012/7/31
														// ��������������
				stockInPlanParamModel.setStockStatus(stockStatus);
				stockInPlanParamModel.setModifyId(modifyId);
				stockInPlanParamModel.setMemo(stockinmanageform.getMemo());

				// ���òֿ�Ȩ���б�
				CommonActionUtil commonAction = new CommonActionUtil();
				stockInPlanParamModel.setWproviderMap(commonAction
						.getLoginUserPrev(context));
				TplStockIn tplStockIn = mservice
						.insertCommitBillDirect(stockInPlanParamModel);
				CommonActionUtil commonActionUtil = new CommonActionUtil();
				commonActionUtil.addActionLog(context,
						LogConstants.WL_STOCK_IN_COMMIT, "�������ύ�嵥,�嵥id��"
								+ tplStockIn.getId());
			}

			logger.info("--part in stock--insertCommitBill--planPutinId:"
					+ planPutinId + " completed on " + new Date()
					+ " Amt_Items: " + itemIds);
			return this.queryInStorePlanMgrList(context);
		} catch (Exception e) {
			logger.error(e);
			if (e.getMessage().startsWith("msg:")) {
				context.getRequest().setAttribute("msg",
						e.getMessage().substring(4));
			} else {
				context.getRequest().setAttribute("error", e);
			}
			return context.findForward("fail");
		}
	}
}
