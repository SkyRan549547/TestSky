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
	 * 入库管理提交清单
	 * 
	 * @param context
	 *            BActionContext
	 * @return ActionForward 跳转方向
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
			String[] itemIds = stockinmanageform.getCheckboxs();// 入库捆包捆包id
			// 捆包物理ID没有码单的
			String planPutinId = context.getRequest().getParameter(
					"planPutinId");
			String pickNum = context.getRequest().getParameter("pickNum");
			String pickType = context.getRequest().getParameter("pickType");
			String manuId = context.getRequest().getParameter("manuId");
			// 捆包号（业务上的ID）没有码单的
			List packWithoutStack = new ArrayList();
			// 捆包号（物理上的ID）没有码单的
			List itemIdWithoutStack = new ArrayList();

			/**
			 * 提单类型是61的入库计划，在入库的时候，需要每一个选中的捆包校验码单。
			 * 如果码单不存在，记录该捆包。其它有码单的捆包正常入库。最后提示用户哪些捆包无码单 added by SU Shengjie
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
						throw new Exception("msg:您选择的入库捆包出厂码单尚未到齐，请重新选择或稍后重试。");
					}
				}
			}

			logger.info("--part in stock--insertCommitBill--planPutinId:"
					+ planPutinId + " requested on " + new Date()
					+ " Amt_Items: " + itemIds);
			// 判断该捆包是否被入库，防止并发错误
			for (int i = 0; i < itemIds.length; i++) {
				TplStockInItem tplStockInItem = (TplStockInItem) mservice
						.getTplStockInItemById(Long.valueOf(itemIds[i]));
				Long putinId = tplStockInItem.getPutinId();
				String packId = tplStockInItem.getPackId();
				if (packId.startsWith("WW") || packId.startsWith("WGNK")) {
					context.getRequest().setAttribute("msg",
							"该计划对应的虚捆包明细未生成，请稍后操作或联系管理员!");
					return this.queryInStorePlanMgrList(context);
				}
				if (null != putinId) {
					context.getRequest().setAttribute("msg",
							"要入库的捆包中有捆包已经被入库，请重新选择");
					return this.queryInStorePlanMgrList(context);
				}

				if (procFlagService.isTubeProcFlag(new Long(itemIds[i]),
						"packIn")) {
					throw new Exception("msg:管坯类合同捆包:" + packId + "未被刷新，请重新选择!");
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
					// 判断选择的计划下的捆包是否在库存中存在

					// 若存在,则看库存状态
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
						// 产外延伸库生成清单,判断出库实绩是否到齐 modify by llk 2013/1/21
						if (!StringUtil.trimStr(tplStockInItem.getProcFlag())
								.equals("10")) {
							context.getRequest().setAttribute(
									"msg",
									"业务不能继续：厂外延伸库入库捆包"
											+ tplStockInItem.getPackId()
											+ " 无出库实绩，请联系来源仓库");
							return this.queryInStorePlanMgrList(context);
						}
					}
					if (null != stockId) {
						TplStock stock = stockService
								.queryTplStockById(stockId);
						// STOCK_FLAG 库存标志 10入库 20出库
						if (!SystemConstants.TPL_STOCK_FLAG_OUT.equals(stock
								.getStockFlag())) {
							// 此计划是转库计划,该库存还未做出库,故不能做入库操作
							context.getRequest().setAttribute("msg",
									"业务不能继续：来源仓库还没有做出库，请联系来源仓库");
							return this.queryInStorePlanMgrList(context);
						}
					} else {

					}
				}

				// ---------------------------------------------------------
				else {
					/*
					 * 提单类型是30的入库计划，做入库的时候，需要写一个检验方法：
					 * 入库的时候需要检查本次入库的捆包对应相同提单号下的出库计划捆包是否出库，
					 * 如果没有出库，则提示.业务不能继续：来源仓库还没有做出库，请联系来源仓库！ add by zhengfei
					 * 20080417
					 */
					boolean flag = mservice
							.queryStockOutItemAndOutByInItem(tplStockInItem);
					if (flag) {
						context.getRequest().setAttribute("msg",
								"业务不能继续：来源仓库还没有做出库，请联系来源仓库");
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
			// 捆包总数
			String[] idsTotal = new String[total];
			// 库位
			String[] location = new String[itemIds.length];
			String[] levels = new String[itemIds.length];
			// 捆包状态
			String[] stockStatus = new String[itemIds.length];
			List locationList = new ArrayList();
			List stockStatusList = new ArrayList();
			List levelList = new ArrayList();// add by llk 层号
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
								"业务不能继续：厂外延伸库,必须输入库区代码");
						return this.queryInStorePlanMgrList(context);
					}
					locationList.add(locationTemp);

					String stockStatusTemp = context.getRequest().getParameter(
							"stockStatus_" + i);
					stockStatusList.add(stockStatusTemp);

					// 拿到合同号
					String orderNum = StringUtil.trimStr(context.getRequest()
							.getParameter("orderNums_" + i));
					String subOrderNum = "";
					if (!orderNum.equals("")) {
						subOrderNum = orderNum.substring(0, 1);
					}
					if ("J".equals(subOrderNum) && wareHouseFlag.equals("10")
							&& SystemConfigUtil.judgeBaosteelExtend(unitId)) {
						// 去拿层号
						String levelNum = StringUtil.trimStr(context
								.getRequest().getParameter("level_" + i));
						levelList.add(levelNum);
					}
					if ("".equals(locationTemp)
							&& (orderNum.substring(2, 3).equals("J") || orderNum
									.substring(2, 3).equals("W"))) {
						context.getRequest().setAttribute("msg",
								"业务不能继续：现货库必须输入库区代码");
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
				throw new Exception("入库上架错误：计划id为空");
			}

			// 厂外延伸库在生成清单的时候要对准发号不一样的捆包进行分拆，生成不同的清单号 add by panyouyong
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

					// 捆包总数
					String[] itemIds2 = new String[itemIdList2.size()];
					// 库位
					String[] location2 = new String[locationList2.size()];
					// 捆包状态
					String[] stockStatus2 = new String[stockStatusList2.size()];
					// 层号
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
							.getCreateDate());// 实际入库时间
					stockInPlanParamModel.setCreateId(stockinmanageform
							.getCreateId());// 入库经办人
					stockInPlanParamModel.setItemIds(itemIds2);
					stockInPlanParamModel.setLocation(location2);
					stockInPlanParamModel.setLevels(levels2);// add by llk
																// 2012/7/31
																// 宽厚板入库新增层号
					stockInPlanParamModel.setStockStatus(stockStatus2);
					stockInPlanParamModel.setModifyId(modifyId);
					stockInPlanParamModel.setMemo(stockinmanageform.getMemo());

					// 设置仓库权限列表
					CommonActionUtil commonAction = new CommonActionUtil();
					stockInPlanParamModel.setWproviderMap(commonAction
							.getLoginUserPrev(context));
					TplStockIn tplStockIn = mservice
							.insertCommitBillDirect(stockInPlanParamModel);
					CommonActionUtil commonActionUtil = new CommonActionUtil();
					commonActionUtil.addActionLog(context,
							LogConstants.WL_STOCK_IN_COMMIT, "入库管理提交清单,清单id："
									+ tplStockIn.getId());
				}
			} else {
				StockInPlanParamModel stockInPlanParamModel = new StockInPlanParamModel();
				stockInPlanParamModel.setId(Long.valueOf(idStr));
				stockInPlanParamModel.setCreateDate(stockinmanageform
						.getCreateDate());// 实际入库时间
				stockInPlanParamModel.setCreateId(stockinmanageform
						.getCreateId());// 入库经办人
				stockInPlanParamModel.setItemIds(itemIds);
				stockInPlanParamModel.setLocation(location);
				stockInPlanParamModel.setLevels(levels);// add by llk 2012/7/31
														// 宽厚板入库新增层号
				stockInPlanParamModel.setStockStatus(stockStatus);
				stockInPlanParamModel.setModifyId(modifyId);
				stockInPlanParamModel.setMemo(stockinmanageform.getMemo());

				// 设置仓库权限列表
				CommonActionUtil commonAction = new CommonActionUtil();
				stockInPlanParamModel.setWproviderMap(commonAction
						.getLoginUserPrev(context));
				TplStockIn tplStockIn = mservice
						.insertCommitBillDirect(stockInPlanParamModel);
				CommonActionUtil commonActionUtil = new CommonActionUtil();
				commonActionUtil.addActionLog(context,
						LogConstants.WL_STOCK_IN_COMMIT, "入库管理提交清单,清单id："
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
