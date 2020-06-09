package com.baosight.baosteel.bli.tpl.pad.mobile.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.baosight.baosteel.bli.tpl.common.StringUtil;
import com.baosight.baosteel.bli.tpl.common.SystemConstants;
import com.baosight.baosteel.bli.tpl.model.TplAppEditionManage;
import com.baosight.baosteel.bli.tpl.model.TplCarPosition;
import com.baosight.baosteel.bli.tpl.model.TplKqListItem;
import com.baosight.baosteel.bli.tpl.model.TplMessageTransDisPlan;
import com.baosight.baosteel.bli.tpl.model.TplProviderCarListItem;
import com.baosight.baosteel.bli.tpl.model.TplRDriver;
import com.baosight.baosteel.bli.tpl.model.TplRHeadstock;
import com.baosight.baosteel.bli.tpl.model.TplRTrailer;
import com.baosight.baosteel.bli.tpl.model.TplRVehicleDistribution;
import com.baosight.baosteel.bli.tpl.model.TplSignInRangeManage;
import com.baosight.baosteel.bli.tpl.model.TplTransLoadPlan;
import com.baosight.baosteel.bli.tpl.model.TplTransPack;
import com.baosight.baosteel.bli.tpl.model.TplTransPackVir;
import com.baosight.baosteel.bli.tpl.model.TplTransPlan;
import com.baosight.baosteel.bli.tpl.model.TplTransSeq;
import com.baosight.baosteel.bli.tpl.model.TplTransSeqChose;
import com.baosight.baosteel.bli.tpl.model.TplTransTruckDisPlan;
import com.baosight.baosteel.bli.tpl.model.TplTransTruckSign;
import com.baosight.baosteel.bli.tpl.pad.mobile.dao.MobileDao;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.CarStoreSeqenceSearchModel;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.CarsInfoSearchModel;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.PlanDetailSearchModel;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.TransSignSearchModel;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.ValidetaSearchModel;
import com.baosight.baosteel.bli.tpl.pad.mobile.model.WarehouseSignSearchModel;
import com.baosight.baosteel.bli.tpl.transport.searchmodel.TplCarPlanSearchModel;
import com.baosight.baosteel.bli.tpl.transport.searchmodel.TplTransLoadPlanSearchModel;
import com.baosight.baosteel.bli.tpl.transport.searchmodel.TransPackSearchModel;
import com.baosight.baosteel.bli.tpl.transport.searchmodel.TransPlanSearchModel;
import com.baosight.core.hibernate3.BHibernateDaoSupport;

public class MobileDaoImpl extends BHibernateDaoSupport implements MobileDao {

	public List checkExistsTransSeq(String providerId, String driUCode)
			throws Exception {
		String sql = null;

		sql = "select t.id as id,t.car_id as carId,t.trailer_number as trailerName,";
		sql = sql + " t.plan_arrive_date as planArriveDate, t.plan_arrive_time as planArriveTime";
		sql = sql + " from tpl_trans_seq t where t.trans_type = '1' ";
		sql = sql + " and t.provider_id = '" + providerId + "' ";
		sql = sql + " and t.driver = '" + driUCode + "' ";
		sql = sql + " and t.status != '2' ";// 未完成的车次
		System.out.println("sql----" + sql);
		return this.getBHibernateTemplate().getJdbcTemplate().queryForList(sql);
	}

	public TplRDriver queryDriverBySearchModel(CarsInfoSearchModel carsInfo)
			throws Exception {
		TplRDriver tplRDriver = null;
		String providerId = StringUtil.trimStr(carsInfo.getProviderId());
		String driUCode = StringUtil.trimStr(carsInfo.getDriUCode());
		String username = StringUtil.trimStr(carsInfo.getUsername());
		String idNum = StringUtil.trimStr(carsInfo.getIdNum());

		StringBuffer hql = new StringBuffer();
		hql.append("from TplRDriver t where 1=1 ");
		if (providerId != null && providerId.length() > 0) {
			hql.append(" and t.providerId = '" + providerId + "' ");
		}
		if (driUCode != null && driUCode.length() > 0) {
			hql.append(" and t.driUCode = '" + driUCode + "' ");
		}
		if (username != null && username.length() > 0) {
			hql.append(" and t.username = '" + username + "' ");
		}
		if (idNum != null && idNum.length() > 0) {
			hql.append(" and t.idNumber = '" + idNum + "' ");
		}
		List result = this.queryByHql(hql.toString());

		if (result != null && result.size() > 0) {
			tplRDriver = (TplRDriver) result.get(0);
			return tplRDriver;
		} else {
			return null;
		}
	}

	public void updateIdFlag(String providerId, String driUCode)
			throws Exception {
		String sql = null;
		sql = "update tpl_r_driver t set t.id_flag = '10' ";
		sql = sql + " where t.provider_id = '" + providerId + "'";
		sql = sql + " and t.dri_U_Code = '" + driUCode + "'";

		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public List queryFontInfoList(String providerId, String driUCode)
			throws Exception {
		String sql = null;

		sql = " select distinct t.front_Code as font ";
		sql = sql + " from Tpl_R_Vehicle_Distribution t ";
		sql = sql + " where t.dri_u_code = '" + driUCode + "' ";
		sql = sql + " and t.provider_id = '" + providerId + "' ";
//		sql = sql + " and t.font_status = '0' ";

		sql = sql + " union all ";
		sql = sql + " select h.bus_number as font ";
		sql = sql + " from Tpl_r_Headstock h ";
		sql = sql + " where h.provider_id = '" + providerId + "' ";
		sql = sql + " and h.status = '1' ";
		sql = sql + " and (h.font_status = '0' or h.font_status is null) ";
		sql = sql + " and h.bus_number not in ";
		sql = sql + " (select temp.front_Code ";
		sql = sql + " from Tpl_R_Vehicle_Distribution temp ";
		sql = sql + " where temp.dri_u_code = '" + driUCode + "' ";
		sql = sql + " and temp.provider_id = '" + providerId + "') ";
		System.out.println("sql:" + sql);
		List list = this.getBHibernateTemplate().getJdbcTemplate()
				.queryForList(sql);

		return list;
	}

	public List queryTrailerInfoList(String providerId, String driUCode)
			throws Exception {
		String sql = null;

		sql = " select distinct t.trailer_Name as trailer ";
		sql = sql + " from Tpl_R_Vehicle_Distribution t ";
		sql = sql + " where t.dri_u_code = '" + driUCode + "' ";
		sql = sql + " and t.provider_id = '" + providerId + "' ";

		sql = sql + " union all ";
		sql = sql + " select h.trailer_number as trailer ";
		sql = sql + " from tpl_r_trailer h ";
		sql = sql + " where h.provider_id = '" + providerId + "' ";
		sql = sql + " and h.trailer_number not in ";
		sql = sql + " (select temp.trailer_Name ";
		sql = sql + " from Tpl_R_Vehicle_Distribution temp ";
		sql = sql + " where temp.dri_u_code = '" + driUCode + "' ";
		sql = sql + " and temp.provider_id = '" + providerId + "') ";

		System.out.println("sql:" + sql);
		List list = this.getBHibernateTemplate().getJdbcTemplate()
				.queryForList(sql);

		return list;
	}

	public TplRVehicleDistribution queryVehicleDistributionBySearchModel(
			CarsInfoSearchModel carsInfo) throws Exception {
		TplRVehicleDistribution vehicleDistribution = new TplRVehicleDistribution();
		String providerId = carsInfo.getProviderId();
		String busNumber = carsInfo.getBusNumber();
		String trailerNumber = carsInfo.getTrailerNumber();
		String driUCode = carsInfo.getDriUCode();

		StringBuffer hql = new StringBuffer();
		hql.append("from TplRVehicleDistribution t where 1=1 ");
		hql.append(" and t.providerId = '" + providerId + "' ");
		hql.append(" and t.driUCode = '" + driUCode + "' ");
		hql.append(" and t.frontCode = '" + busNumber + "' ");
		hql.append(" and t.trailerName = '" + trailerNumber + "' ");

		List result = this.queryByHql(hql.toString());

		if (result != null && result.size() > 0) {
			vehicleDistribution = (TplRVehicleDistribution) result.get(0);
			return vehicleDistribution;
		} else {
			return null;
		}
	}

	public TplRHeadstock queryHeadstockBySearchModel(
			CarsInfoSearchModel carsInfo) throws Exception {
		TplRHeadstock headstock = new TplRHeadstock();
		String providerId = carsInfo.getProviderId();
		String busNumber = carsInfo.getBusNumber();

		StringBuffer hql = new StringBuffer();
		hql.append("from TplRHeadstock t where 1=1 ");
		hql.append(" and t.providerId = '" + providerId + "' ");
		hql.append(" and t.busNumber = '" + busNumber + "' ");

		List result = this.queryByHql(hql.toString());

		if (result != null && result.size() > 0) {
			headstock = (TplRHeadstock) result.get(0);
			return headstock;
		} else {
			return null;
		}

	}

	public void updateFontStatusBySearchModel(CarsInfoSearchModel carsInfo,
			String fontStatus) throws Exception {
		String busNumber = StringUtil.trimStr(carsInfo.getBusNumber());
		String userName = StringUtil.trimStr(carsInfo.getUsername());
		String providerId = StringUtil.trimStr(carsInfo.getProviderId());
		String driUCode = StringUtil.trimStr(carsInfo.getDriUCode());
		if ("".equals(busNumber)) {
			return;
		}
		StringBuffer hql = new StringBuffer(
				"update Tpl_R_Headstock t set t.FONT_STATUS='" + fontStatus
						+ "',t.USER_NAME='" + userName
						+ "' where t.BUS_NUMBER='" + busNumber
						+ "' and t.provider_id='" + providerId + "'");
		this.getSession().createSQLQuery(hql.toString()).executeUpdate();
		StringBuffer hql1 = new StringBuffer(
				"update Tpl_R_Vehicle_Distribution t set t.FONT_STATUS='"
						+ fontStatus + "',t.USER_NAME='" + userName
						+ "'  where t.FRONT_CODE='" + busNumber
						+ "' and  t.provider_id='" + providerId + "'");
		this.getSession().createSQLQuery(hql1.toString()).executeUpdate();
		// 更新司机为在岗状态
		StringBuffer hql2 = new StringBuffer(
				"update TPL_R_DRIVER t set t.driver_flag='" + fontStatus
						+ "'  where t.DRI_U_CODE='" + driUCode
						+ "'  and t.provider_id='" + providerId + "'");
		this.getSession().createSQLQuery(hql2.toString()).executeUpdate();

	}

	public List queryDisPlanBySearchModel(PlanDetailSearchModel searchModel)
			throws Exception {
		String driUCode = StringUtil.trimStr(searchModel.getDriUCode());
		String orderCode = StringUtil.trimStr(searchModel.getOrderCode());
		String billId = StringUtil.trimStr(searchModel.getBillId());
		String status = StringUtil.trimStr(searchModel.getStatus());
		String excuteStartDate = StringUtil.trimStr(searchModel.getExcuteStratDate());
		String excuteEndDate = StringUtil.trimStr(searchModel.getExcuteEndDate());
		String ladingSpotName = StringUtil.trimStr(searchModel.getLadingSpotName());
		String pickNum = StringUtil.trimStr(searchModel.getPickNum());
		String systemType = StringUtil.trimStr(searchModel.getSystemType());
		String productType = StringUtil.trimStr(searchModel.getProductType());
		StringBuffer hql = new StringBuffer();
		List result = new ArrayList();

		hql.append("select distinct t.id,t.uploadTime,t.billId,t.ladingSpot,t.ladingSpotName,t.destSpot,t.destSpotName,ttp.planGrossWeight,ttp.planNetWeight,ttp.planCount,");
		hql.append(" ttp.excuteDate,t.status,t.sendFlag,t.urgencyDeg,t.dispatcherName,t.driName,t.busNumber,t.pickNum, ");
		// hql.append(" ttp.actGrossWeight,ttp.actNetWeight,ttp.actCount, substr(pack.orderNum, 1, 1),");
		hql.append(" ttp.actGrossWeight,ttp.actNetWeight,ttp.actCount, ttp.packType,");
		hql.append(" ttp.oriGrossWeight, ttp.oriNetWeight, ttp.oriCount, ttp.oriActGrossWeight, ttp.oriActNetWeight, ttp.oriActCount,");
		// 提货时间有否有效 10-有效 20-无效 add ljh 2016-10-21 16:10:03
		hql.append(" t.providerId , t.validateFlag, t.trackNo ");
		hql.append(" from TplTransTruckDisPlan t,TplTransPlan ttp where t.billId=ttp.billId ");
		hql.append(" and (ttp.planCount >= ttp.actCount or (ttp.packType='20' and ttp.status !='2' ))and ttp.transType = '1'  and t.flag='10'");// 汽运
		hql.append(" and (ttp.batchStatus ='0' or ttp.batchStatus ='1' or ttp.batchStatus is null)");

		if (driUCode.length() > 0) {
			hql.append("and t.driUCode= '" + driUCode + "' ");
		}
		if (billId.length() > 0) {
			hql.append("and t.billId= '" + billId + "' ");
		}
		if (!status.equals("")) {
			hql.append("and t.status= '" + status + "' ");
		} else {
			hql.append("and t.status != '20'");
		}
		if (excuteStartDate.length() > 0 && excuteEndDate.length() > 0) {
			hql.append(" and ttp.excuteDate between to_date('"
					+ excuteStartDate
					+ "000000', 'yyyymmddhh24miss') and to_date('"
					+ excuteEndDate + "235959', 'yyyymmddhh24miss') ");
		}
		if (ladingSpotName.length() > 0) {
			hql.append("and t.ladingSpotName like '%" + ladingSpotName + "%' ");
		}

		if (pickNum.length() > 0) {
			hql.append(" and ttp.pickNum like '%" + pickNum + "%' ");
		}
		
		if ((!"".equals(productType)) && (productType.length() > 0) && (systemType.length() > 0) && (systemType.equals("20"))) {
		      hql.append("and subStr(t.ladingSpot, length(t.ladingSpot)-2,3) in( select distinct g.landingSpotId from TplZgWarehouse g where g.status='1' and g.productType ='" + productType + "')");
		}
		/*if (systemType.length()>0&&systemType.equals("20")){
			//湛江暂时只覆盖13个仓库
			hql.append(" and t.ladingSpot in ('BSZG00Z02','BSZG00Z21','BSZG00Z22','BSZG00Z23','BSZG00Z12','BSZG00Z51','BSZG00Z52','BSZG00Z53','BSZG00Z82','BSZG00Z83','BSZG00FT1','BSZG00FT3','BSZG00FT9')");
		}*/
		
		if (orderCode.length() > 0) {
			if ("10".equals(orderCode)) {
				hql.append(" order by t.urgencyDeg desc");
			} else if ("20".equals(orderCode)) {
				hql.append(" order by t.ladingSpot desc");
			}
		}

		System.out.println("sql " + hql.toString());
		result = this.queryByHql(hql.toString());
		return result;
	}

	public List queryTransPackBySearchModel(PlanDetailSearchModel searchModel)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		String billId = StringUtil.trimStr(searchModel.getBillId()); // 提单号
		String packStatus = StringUtil.trimStr(searchModel.getPackStatus());// 是否装载
		List result = new ArrayList();

		hql.append("select t.packId,t.orderNum,t.productName,t.grossWeight,t.netWeight,t.packStatus from TplTransPack t, TplTransPlan plan ");
		hql.append(" where 1=1 ");
		hql.append(" and (plan.batchStatus ='0' or plan.batchStatus ='1' or plan.batchStatus is null)");
		hql.append(" and t.planId = plan.id");
		hql.append(" and plan.transType = '1'");// 汽运
		hql.append(" and plan.billId = '" + billId + "' ");
		if (!packStatus.equals("")) {
			hql.append(" and t.packStatus = '" + packStatus + "' ");
		} else {
			hql.append(" and t.packStatus = '0' ");
		}
		hql.append(" order by t.packId");

		System.out.println("sql " + hql.toString());
		result = this.queryByHql(hql.toString());
		return result;
	}

	public TplTransTruckDisPlan queryTransTruckDisPlanBySearchModel(
			PlanDetailSearchModel searchModel) throws Exception {
		String billId = StringUtil.trimStr(searchModel.getBillId());
		String driUCode = StringUtil.trimStr(searchModel.getDriUCode());
		Long id = searchModel.getId();
		Long seqId = searchModel.getSeqId();
		StringBuffer hql = new StringBuffer();
		TplTransTruckDisPlan transTruckDisPlan = new TplTransTruckDisPlan();
		List list = new ArrayList();
		hql.append("select t from TplTransTruckDisPlan t where 1=1");
		if (billId != null && !billId.trim().equals("")) {
			hql.append(" and t.billId = '" + billId + "'");
		}
		if (driUCode != null && !driUCode.trim().equals("")) {
			hql.append(" and t.driUCode = '" + driUCode + "'");
		}
		if (id != null) {
			hql.append(" and t.id = " + id);
		}
		if(seqId !=null ){
			hql.append(" and t.trackNo =" + seqId);
		}

		list = this.queryByHql(hql.toString());
		if (list != null && list.size() > 0) {
			transTruckDisPlan = (TplTransTruckDisPlan) list.get(0);
			return transTruckDisPlan;
		} else {
			return null;
		}
	}

	public void updateTransTruckDisPlanBySearchModel(
			CarsInfoSearchModel carsInfo) throws Exception {
		String sql = null;
		String driUCode = StringUtil.trimStr(carsInfo.getDriUCode());
		String providerId = StringUtil.trimStr(carsInfo.getProviderId());
		String busNumber = StringUtil.trimStr(carsInfo.getBusNumber());
		String trailerNumber = StringUtil.trimStr(carsInfo.getTrailerNumber());
		Long seqId = carsInfo.getSeqId();

		sql = " update tpl_trans_truck_dis_plan t ";
		sql = sql + " set t.bus_number = '" + busNumber + "',";
		sql = sql + " t.trailer_number = '" + trailerNumber + "',";
		sql = sql + " t.track_no = " + seqId + ",";
		sql = sql + " t.validate_flag = '00', ";
		sql = sql + " t.delivery_start_time = sysdate ,";
		sql = sql + " t.delivery_end_time = sysdate+1 ";
		sql = sql + " where t.dri_u_code = '" + driUCode + "'";
		sql = sql + " and t.provider_id = '" + providerId + "'";
		sql = sql + " and t.flag = '10'";
		sql = sql + " and t.status != '20'";
//		sql = sql + " and t.validate_flag != '10'";
		System.out.println("update sql " + sql);
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public Long getSeqIdNextVal() throws Exception {
		String sql = new String();

		sql = "select seq_tpl_trans_seq.nextval from dual ";
		Long seqId = Long.valueOf(this.querySingleFiledBySql(sql).toString());
		return seqId;
	}

	public Object querySingleFiledBySql(String sql) throws Exception {

		return this.getBHibernateTemplate().getJdbcTemplate().query(sql,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next())
							return rs.getObject(1);
						return null;
					}
				});
	}

	public List checkTransPackLoad(CarsInfoSearchModel carsInfo)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		Long seqId = carsInfo.getSeqId();
		List packList = carsInfo.getPackList();

		hql.append(" from TplTransPack t where 1=1 ");
		if (seqId != null) {
			hql.append(" and t.seqId = " + seqId);
		}
		if (packList != null && packList.size() > 0) {
			hql.append(" and t.id in (");
			for (int i = 0; i < packList.size(); i++) {
				if (i == packList.size() - 1) {
					hql.append(packList.get(i).toString() + ")");
				} else {
					hql.append(packList.get(i).toString() + ",");
				}
			}
		}

		List list = queryByHql(hql.toString());
		return list;
	}

	public TplTransPack queryTransPackByPackSearchModel(
			TransPackSearchModel packSearchModel) throws Exception {
		StringBuffer hql = new StringBuffer();
		String billId = StringUtil.trimStr(packSearchModel.getBillId());
		String orderNum = StringUtil.trimStr(packSearchModel.getOrderNum());
		String packId = StringUtil.trimStr(packSearchModel.getPackId());
		hql.append(" from TplTransPack t where 1=1 ");
		if (packId.length() > 0) {
			hql.append(" and t.packId = '" + packId + "'");
		}
		if (orderNum.length() > 0) {
			hql.append(" and t.orderNum = '" + orderNum + "'");
		}
		if (billId.length() > 0) {
			hql
					.append(" and exists(select 1 from TplTransPlan p where p.id = t.planId and p.transType = '1' ");
			hql
					.append(" and (p.batchStatus ='0' or p.batchStatus ='1' or p.batchStatus is null)");
			hql.append(" and p.billId = '" + billId + "' )");
		}

		List list = queryByHql(hql.toString());

		if (list.size() > 0) {
			return (TplTransPack) list.get(0);
		} else {
			return null;
		}
	}

	public void updateTransPackForLoad(List packSet, Long disPlanId, Long seqId)
			throws Exception {
		if (packSet.size() > 0) {
			StringBuffer hql = new StringBuffer();
			hql.append("update Tpl_Trans_Pack t set t.control_Id=" + disPlanId);
			hql.append(", t.seq_Id=" + seqId);
			hql.append(",t.pack_Status='1'");
			hql.append(",t.pro_Status='"
					+ SystemConstants.TRANS_PACK_PRO_STATUS_LOADED + "'");// 已装车
			hql.append(" where 1=1 ");
			hql.append(" and t.id in(");
			for (int i = 0; i < packSet.size(); i++) {
				if (i == packSet.size() - 1) {
					hql.append(packSet.get(i).toString() + ")");
				} else {
					hql.append(packSet.get(i).toString() + ",");
				}
			}
			System.out.println(hql.toString());
			this.getSession().createSQLQuery(hql.toString()).executeUpdate();
		}
	}

	public List queryPackSumBySeqId(Long seqId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql
				.append("select sum(t.grossWeight),sum(t.netWeight),sum(t.unitCount),t.planId from TplTransPack t ");
		hql.append(" where t.seqId = " + seqId);
		hql.append(" group by t.planId");

		list = this.queryByHql(hql.toString());

		return list;
	}

	public TplTransPlan queryTransPlanBySearchModel(
			PlanDetailSearchModel searchModel) throws Exception {
		TplTransPlan tplTransPlan = new TplTransPlan();
		String billId = searchModel.getBillId();
		Long id = searchModel.getId();
		StringBuffer hql = new StringBuffer();

		hql.append("from TplTransPlan t where 1=1 ");
		if (billId != null) {
			hql.append(" and t.billId = '" + billId + "' ");
		}
		if (id != null) {
			hql.append(" and t.id = " + id);
		}
		List result = this.queryByHql(hql.toString());

		if (result != null && result.size() > 0) {
			tplTransPlan = (TplTransPlan) result.get(0);
			return tplTransPlan;
		} else {
			return null;
		}
	}

	public TplTransPackVir queryTransPackVirBySearchModel(
			TransPackSearchModel packSearchModel, String storageType)
			throws Exception {
		Long seqId = packSearchModel.getSeqId();
		String packId = packSearchModel.getPackId();
		String billId = packSearchModel.getBillId();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplTransPackVir t where 1 = 1");
		if (seqId != null) {
			hql.append(" and t.seqId = " + seqId);
		}
		if (StringUtil.trimParam(billId).length() > 0) {
			hql.append(" and t.billId = '" + billId + "'");
		}
		if (StringUtil.trimParam(storageType).length() > 0) {
			hql.append(" and t.storageType = '" + storageType + "'");
		}

		if (StringUtil.trimParam(packId).length() > 0) {
			hql.append(" and t.packId = '" + packId + "'");
		}
		System.out.println("queryPackVirByPackId------------" + hql.toString()
				+ "----packId=====" + packId + "----billId---" + billId
				+ "-----storageType---" + storageType + "---seqId---" + seqId);
		List list = this.queryByHql(hql.toString());
		if (list.size() > 0) {
			return (TplTransPackVir) list.get(0);
		} else {
			return null;
		}
	}

	public List queryTransSeqBySearchModel(CarsInfoSearchModel carsInfo)
			throws Exception {
		String driver = carsInfo.getDriUCode();
		String carId = carsInfo.getBusNumber();
		Long id = carsInfo.getSeqId();
		StringBuffer hql = new StringBuffer();
		hql.append("from TplTransSeq t where t.transType='1' and t.driver='"
				+ driver + "' ");
		if (carId.length() > 0) {
			hql.append(" and t.carId = '" + carId + "'");
		}
		if (id != null) {
			hql.append(" and t.id = '" + id + "'");
		}
		hql.append(" and t.status != '2'");

		List list = this.queryByHql(hql.toString());
		return list;
	}

	public List queryTrainListChildWithArgs(TransPackSearchModel packSearchModel)
			throws Exception {

		String seqId = StringUtil
				.trimStr(packSearchModel.getSeqId().toString());// 车次号

		StringBuffer sql = new StringBuffer("");
		sql.append(" select * from (");
		sql
				.append(" select t.pack_Id as packId, t.order_Num as orderNum, t.product_Name as productName, t.gross_Weight as grossWeight, t.net_Weight as netWeight, disPlan.dest_Spot_Name as destSpotName, disPlan.bill_Id as billId, disPlan.lading_Spot_Name as ladingSpotName, disPlan.lading_Spot as ladingSpot, t.control_Id as controlId, '10' as packType , t.unit_Count as unitCount ");
		sql
				.append(" from Tpl_Trans_Pack t, Tpl_Trans_Truck_Dis_Plan disPlan where t.control_Id = disPlan.id");
		sql.append(" and t.sign_Id is null");// 未签收的材料
		if (seqId.length() > 0) {
			sql.append(" and t.seq_Id = " + Long.valueOf(seqId));
		}
		sql.append(" union all");
		sql
				.append(" select t.pack_Id as packId, t.order_Num as orderNum, t.product_Name as productName, t.load_Gross_Weight as grossWeight, t.load_Net_Weight as netWeight, disPlan.dest_Spot_Name as destSpotName, disPlan.bill_Id as billId, disPlan.lading_Spot_Name as ladingSpotName, disPlan.lading_Spot as ladingSpot, t.control_Id as controlId, '20' as packType , t.unit_Count as unitCount ");
		sql
				.append(" from Tpl_Trans_Pack_Vir t, Tpl_Trans_Truck_Dis_Plan disPlan where t.control_Id = disPlan.id");
		sql.append(" and t.sign_Id is null");// 未签收的材料
		sql.append(" and t.load_Unit_Count > 0");
		sql.append(" and t.storage_Type = '10'");// 10 装车 20 到货
		if (seqId.length() > 0) {
			sql.append(" and t.seq_Id = " + Long.valueOf(seqId));
		}
		sql.append(" ) ");
		sql.append(" order by packId");

		List list = this.getBHibernateTemplate().getJdbcTemplate()
				.queryForList(sql.toString());
		System.out.println("queryTrainListChildWithArgs  " + sql);
		return list;

	}

	public List queryTransSignBySearchModel(TransSignSearchModel searchModel)
			throws Exception {
		String carId = StringUtil.trimStr(searchModel.getFrontCode());// 车牌号
		String driUcode = StringUtil.trimStr(searchModel.getDriUCode());// 司机U代码
		String signZt = StringUtil.trimStr(searchModel.getSignZt());
		String signId = StringUtil.trimStr(searchModel.getSignId());

		StringBuffer sql = new StringBuffer();
		sql
				.append("select sign.id, sign.trackNo, s.carId, sign.signDate, sign.memo, sign.signZt, sign.address, sign.netWeight, sign.grossWeight, sign.unitCount");
		sql.append(" from TplTransSeq s, TplTransTruckSign sign where 1 = 1");
		sql.append(" and s.id = sign.trackNo");
		sql.append(" and s.carId = '" + carId + "'");
		sql.append(" and s.driver = '" + driUcode + "'");
		sql.append(" and s.transType = '1'");
		sql.append(" and s.status = '1'");
		if (signId != null && signId.length() > 0) {
			sql.append(" and sign.id = " + signId);
		}
		if (signZt != null && signZt.length() > 0) {
			sql.append(" and sign.signZt = '" + signZt + "'");
		}
		sql.append(" order by s.id, sign.id");

		List list = this.queryByHql(sql.toString());
		return list;
	}

	public List queryTransSignDetailBySearchModel(
			TransSignSearchModel searchModel) throws Exception {
		String signId = StringUtil.trimStr(searchModel.getSignId());
		StringBuffer sql = new StringBuffer();

		sql.append("select * from (");
		sql
				.append(" select t.PACK_ID as packId, t.ORDER_NUM as orderNum, t.PRODUCT_NAME as productName, t.GROSS_WEIGHT as grossWeight, t.NET_WEIGHT as netWeight, plan.start_spot_name as ladingSpotName,t.spec_desc as specDesc,t.shopsign_name as shipsignName");
		sql
				.append(" from TPL_TRANS_PACK t, tpl_trans_truck_sign plan where t.sign_ID = plan.ID  and t.SIGN_ID = '"
						+ signId + "'");
		sql.append(" union all");
		sql
				.append(" select t.PACK_ID as packId, t.ORDER_NUM as orderNum, t.PRODUCT_NAME as productName, t.GROSS_WEIGHT as grossWeight, t.NET_WEIGHT as netWeight, plan.start_spot_name as ladingSpotName,t.spec_desc as specDesc,t.shopsign_name as shipsignName");
		sql
				.append(" from TPL_TRANS_PACK_VIR t, tpl_trans_truck_sign  plan  where 1=1 ");
		// sql.append(" and plan.TRANS_TYPE = '1'");
		sql.append(" and t.SIGN_ID = plan.id");
		sql.append(" and t.SIGN_ID = '" + signId + "'");
		sql.append(" )");
		sql.append(" order by packId");

		List list = this.getBHibernateTemplate().getJdbcTemplate()
				.queryForList(sql.toString());
		System.out.println("queryTrainListChildWithArgs  " + sql);
		return list;
	}

	public void updateTransPackForLoadBack(List packSet, Long seqId)
			throws Exception {

		StringBuffer hql = new StringBuffer();
		hql.append("update Tpl_Trans_Pack t set t.control_Id= null ");
		hql.append(", t.seq_Id=null ");
		hql.append(", t.pack_Status='0' ");
		hql.append(", t.pro_Status='"+ SystemConstants.TRANS_PACK_PRO_STATUS_SENDED + "'");// 派单
		hql.append(" where 1=1 ");
		if (packSet.size() > 0) {
			hql.append(" and t.id in(");
			for (int i = 0; i < packSet.size(); i++) {
				if (i == packSet.size() - 1) {
					hql.append(packSet.get(i).toString() + ")");
				} else {
					hql.append(packSet.get(i).toString() + ",");
				}
			}
		}
		if(seqId!=null){
			hql.append(" and t.seq_Id = "+seqId);
		}
		System.out.println(hql.toString());
		this.getSession().createSQLQuery(hql.toString()).executeUpdate();
	}

	public void updateTplPackSign(List packSet, Long signId) throws Exception {
		if (packSet.size() > 0) {
			StringBuffer hql = new StringBuffer();
			hql.append("update Tpl_Trans_Pack t set t.sign_Id= " + signId);
			hql.append(", t.pro_Status='"
					+ SystemConstants.TRANS_PACK_PRO_STATUS_ARRIVE + "'");//
			hql.append(" where 1=1 ");
			hql.append(" and t.id in(");
			for (int i = 0; i < packSet.size(); i++) {
				if (i == packSet.size() - 1) {
					hql.append(packSet.get(i).toString() + ")");
				} else {
					hql.append(packSet.get(i).toString() + ",");
				}
			}
			System.out.println(hql.toString());
			this.getSession().createSQLQuery(hql.toString()).executeUpdate();

			StringBuffer signHql = new StringBuffer();
			// 更新净重毛重件数
			signHql.append(" update tpl_trans_truck_sign t ");
			signHql
					.append(" set (t.net_weight, t.gross_weight, t.unit_count) = ");
			signHql
					.append(" (select sum(p.net_weight), sum(p.gross_weight), sum(p.unit_count) ");
			signHql.append(" from tpl_trans_pack p ");
			signHql.append(" where p.sign_id = t.id) ");
			signHql.append(" where t.id = " + signId);

			System.out.println(hql.toString());
			this.getSession().createSQLQuery(signHql.toString())
					.executeUpdate();

		}

	}

	public TplTransTruckSign queryTransSignById(Long signId) throws Exception {
		// StringBuffer hql = new StringBuffer();
		// hql.append(" select t from TplTransTruckSign t where 1=1");
		// hql.append(" and t.id = " + signId);
		//		
		// List list = this.queryByHql(hql.toString());
		// if(list.size()>0){
		// TplTransTruckSign sign = (TplTransTruckSign)list.get(0);
		// return sign;
		// }else{
		// return null;
		// }
		//		

		return (TplTransTruckSign) this.queryById(signId,
				TplTransTruckSign.class);
	}

	public void updateTransTruckSign(TplTransTruckSign sign) throws Exception {
		this.update(sign, TplTransTruckSign.class);
	}

	public void updateTransDisPlanforTransLoadZC(
			TransPlanSearchModel transPlanSearchModel) throws Exception {
		StringBuffer hql = new StringBuffer();
		System.out.println("更新作业计划开始....llk dao impl");
		hql.append("update Tpl_Trans_Truck_Dis_Plan t set"
				+ " t.act_Count =  ?, " + " t.act_Gross_Weight =  ?,"
				+ " t.act_Net_Weight =  ? ,t.status=? "
				+ " where t.trans_plan_id = ?");

		String transPlanId = transPlanSearchModel.getTransPlanId();
		Long actUnitCount = transPlanSearchModel.getActCount();
		Double actQuantity = transPlanSearchModel.getActGrossWeight();
		Double actNetWeight = transPlanSearchModel.getActNetWeight();
		Query query = this.getSession().createSQLQuery(hql.toString());
		query.setLong(0, this.filterLongObj(actUnitCount).longValue());
		query.setDouble(1, this.filterDoubleObj(actQuantity).doubleValue());
		query.setDouble(2, this.filterDoubleObj(actNetWeight).doubleValue());
		query.setString(3, transPlanSearchModel.getStatus());
		if (transPlanId == null)
			return;
		query.setString(4, transPlanId);
		query.executeUpdate();
		System.out.println("更新作业计划结束.......");
	}

	private Long filterLongObj(Long arg) throws Exception {
		if (arg != null)
			return arg;
		return new Long(0);
	}

	private Double filterDoubleObj(Double arg) throws Exception {
		if (arg != null)
			return arg;
		return new Double(0);
	}

	public List queryPackSumByPackList(List list, Long seqId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List resultes = new ArrayList();
		hql
				.append("select sum(t.grossWeight),sum(t.netWeight),sum(t.unitCount),t.planId from TplTransPack t ");

		hql.append(" where t.seqId = " + seqId);

		if (list != null && list.size() > 0) {
			hql.append(" and t.id in(");
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					hql.append(list.get(i).toString() + ")");
				} else {
					hql.append(list.get(i).toString() + ",");
				}
			}
		}

		hql.append(" group by t.planId");

		resultes = this.queryByHql(hql.toString());

		return resultes;

	}

	public void updateTransPlanWithTransLoadBack(
			TransPlanSearchModel transPlanSearchModel) throws Exception {
		StringBuffer hql = new StringBuffer();

		hql.append("update TplTransPlan t set"
				+ " t.actCount = t.actCount - ?, "
				+ " t.actGrossWeight = t.actGrossWeight - ?,"
				+ " t.actNetWeight = t.actNetWeight - ?" + " where t.id = ?");

		Long planId = transPlanSearchModel.getId();
		Long actUnitCount = transPlanSearchModel.getActCount();
		Double actQuantity = transPlanSearchModel.getActGrossWeight();
		Double actNetWeight = transPlanSearchModel.getActNetWeight();

		Query query = this.getSession().createQuery(hql.toString());
		query.setLong(0, this.filterLongObj(actUnitCount).longValue());
		query.setDouble(1, this.filterDoubleObj(actQuantity).doubleValue());
		query.setDouble(2, this.filterDoubleObj(actNetWeight).doubleValue());
		query.setLong(3, planId.longValue());
		query.executeUpdate();

	}

	public void updateTransDisPlanWithTransLoadBack(
			TransPlanSearchModel transPlanSearchModel) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("update TplTransTruckDisPlan t set" + " t.actCount =  ?, "
				+ " t.actGrossWeight = ?," + " t.actNetWeight = ? "
				+ " where t.transPlanId = ?");

		// Long planId = transPlanSearchModel.getId();
		Long actUnitCount = transPlanSearchModel.getActCount();
		Double actQuantity = transPlanSearchModel.getActGrossWeight();
		Double actNetWeight = transPlanSearchModel.getActNetWeight();
		String transPlanId = transPlanSearchModel.getTransPlanId();

		Query query = this.getSession().createQuery(hql.toString());
		query.setLong(0, this.filterLongObj(actUnitCount).longValue());
		query.setDouble(1, this.filterDoubleObj(actQuantity).doubleValue());
		query.setDouble(2, this.filterDoubleObj(actNetWeight).doubleValue());
		if (transPlanId == null)
			return;
		query.setString(3, transPlanId);
		query.executeUpdate();

	}

	public void updateTransSeqWithTransLoadBack(
			TransPlanSearchModel transPlanSearchModel) throws Exception {
		StringBuffer hql = new StringBuffer();
		Long transSeqId = transPlanSearchModel.getId();
		Long actUnitCount = transPlanSearchModel.getActCount();
		Double actQuantity = transPlanSearchModel.getActGrossWeight();
		Double actNetWeight = transPlanSearchModel.getActNetWeight();

		
		hql.append("update Tpl_Trans_Seq t set "); 
		hql.append(" t.act_Count = "+actUnitCount);
		hql.append(" ,t.act_Gross_Weight = "+actQuantity);
		hql.append(" ,t.act_Net_Weight = "+actNetWeight);
		if(actUnitCount.equals(new Long(0))){
			hql.append(" ,t.pro_Status = null");
		}
		hql.append(" where t.id = "+transSeqId);


		this.getSession().createSQLQuery(hql.toString()).executeUpdate();
		
	}

	public void updateTransPlanOfStatusWithTransLoad(
			TransPlanSearchModel transPlanSearchModel) throws Exception {
		StringBuffer hql = new StringBuffer();

		hql
				.append("update TplTransPlan t set t.status = ?,t.proStatus=? where t.id = ?");

		String status = transPlanSearchModel.getStatus();
		Long planId = transPlanSearchModel.getId();
		String proStatus = transPlanSearchModel.getProStatus();

		Query query = this.getSession().createQuery(hql.toString());
		query.setString(0, status);
		query.setString(1, proStatus);
		query.setLong(2, planId.longValue());

		System.out.println("计划更新=======" + hql.toString());

		query.executeUpdate();
	}

	public void updateTransDisPlanOfStatusWithTransLoad(
			TransPlanSearchModel transPlanSearchModel) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql
				.append("update TplTransTruckDisPlan t set t.status =? where t.transPlanId=?");
		Query query = this.getSession().createQuery(sql.toString());
		query.setString(0, transPlanSearchModel.getStatus());
		query.setString(1, transPlanSearchModel.getTransPlanId());
		query.executeUpdate();
	}

	public List queryPackSumBySignId(Long signId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql
				.append("select sum(t.grossWeight),sum(t.netWeight),sum(t.unitCount) from TplTransPack t ");
		hql.append(" where t.signId = " + signId);
		// hql.append(" group by t.planId");

		list = this.queryByHql(hql.toString());

		return list;
	}
	
	public List queryTransTruckSignBySeqId(Long seqId,Long signId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append(" from TplTransTruckSign t where t.trackNo = " + seqId);
		hql.append(" and t.id != "+signId);
		list = this.queryByHql(hql.toString());
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public List queryWarehouseRoadnum(String warehouseId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append(" from TplWarehouseRoadnum t where t.warehouseId = '"
				+ warehouseId + "'");

		list = this.queryByHql(hql.toString());

		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public TplRTrailer queryTrailerByTrailerNumber(String trailerNumber,
			String providerId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append(" from TplRTrailer t where t.trailerNumber = '"
				+ trailerNumber + "'");
		hql.append("  and t.providerId = '" + providerId + "'");

		list = this.queryByHql(hql.toString());

		if (list.size() > 0) {
			return (TplRTrailer) list.get(0);
		} else {
			return null;
		}
	}

	public List queryTransDisPlanBySearchModel(String driUCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		
		hql.append(" from TplTransTruckDisPlan t where 1=1 ");
		hql.append(" and t.status != '20' ");
		hql.append(" and t.flag = '10' ");
		hql.append(" and t.driUCode = '"+driUCode+"' ");
//		hql.append(" and t.validateFlag = '00' ");
		hql.append(" and t.createDate >= to_date('20180419','yyyyMMdd')");//只查询18年4月19号以后的派单
		
		list = this.queryByHql(hql.toString());
		return list;
	}

	public List validateTransDisPlanForMG(ValidetaSearchModel searchModel)
			throws Exception {
		String dispatchId = searchModel.getDispatchId();
		String billId = searchModel.getBillId();
		String carId = searchModel.getCarId();
		
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		
		hql.append(" from TplTransTruckDisPlan t where 1=1 ");
		hql.append(" and t.billId = '"+billId+"'");
		hql.append(" and t.busNumber = '"+carId+"'");
		hql.append(" and t.id||t.trackNo = '"+dispatchId+"'");
		System.out.println(hql.toString());
		list = this.queryByHql(hql.toString());
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}

	public TplMessageTransDisPlan searchMessageTransDisPlanByEntrustId(
			String entrustId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();

		hql.append(" from TplMessageTransDisPlan t where 1=1 ");
		hql.append(" and t.entrustId = '" + entrustId + "' ");
		hql.append(" order by t.id desc");
		
		list = this.queryByHql(hql.toString());
		if(list.size()>0){
			return (TplMessageTransDisPlan)list.get(0);
		}else{
			return null;
		}
	}

	public List queryTransDisPlanByString(String str) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		
		hql.append(" from TplTransTruckDisPlan t where 1=1 ");
		hql.append(" and t.id in ("+str+")");
		System.out.println(hql.toString());
		list = this.queryByHql(hql.toString());
		return list;
	}

	public List queryTplCarPlanItem(TplCarPlanSearchModel model)
			throws Exception {
		Long carPlanId = model.getCarPlanId();
		String struts = model.getStruts();
		String seqId = model.getSeqId();
		
		String sql = null;
//		sql = " select t.id as id, t.pack_Id as packId, i.pick_no as pickNum, t.order_Num as orderNum, t.gross_Weight as grossWeight, t.net_Weight as netWeight,";
//        sql = sql + " '0' as selectFlag,";//已选择
//        sql = sql + " i.product_Name as productName, substr(i.LADING_SPOT_ID,-3,3) as ladingSpotId, i.DEST_SPOT_ID as destSpotId,";
//        sql = sql + " i.lading_spot_name as ladingSpotName, i.dest_spot_name as destSpotName";
//        sql = sql + " from Tpl_Trans_Pack t, Tpl_Car_Plan_Item i";
//        sql = sql + " where 1 = 1";
//        sql = sql + " and t.pack_Id = i.pack_No";
//        sql = sql + " and t.order_Num = i.order_Num";
//        sql = sql + " and i.car_Plan_Id = '"+carPlanId+"'";
//        sql = sql + " and i.struts = '"+struts+"'";
//        sql = sql + " union all";
//        sql = sql + " select p.id as id, p.pack_Id as packId, i.pick_num as pickNum, p.order_Num as orderNum, p.gross_Weight as grossWeight, p.net_Weight as netWeight,";
//        sql = sql + " case when p.pack_Id not in (select t.pack_No from Tpl_Car_Plan_Item t where t.struts = '0' and t.car_Plan_Id != '"+carPlanId+"') then '1' else '2' end as selectFlag,";//1未选择 2被他人选
//        sql = sql + " p.product_Name as productName, substr(i.LADING_SPOT,-3,3) as ladingSpotId, i.DEST_SPOT as destSpotId,";
//        sql = sql + " i.lading_spot_name as ladingSpotName, i.dest_spot_name as destSpotName";
//        sql = sql + " from Tpl_Trans_Pack p, Tpl_Trans_Plan i";
//        sql = sql + " where 1 = 1";
//        sql = sql + " and p.plan_Id = i.id";
//        sql = sql + " and (p.since_Status is null or p.since_Status = '0')";
//        sql = sql + " and p.pack_Status = '0'";
//        sql = sql + " and p.seq_Id is null";
//        sql = sql + " and exists (select 1 ";
//        sql = sql + " from Tpl_Trans_Truck_Dis_Plan dis ";
//        sql = sql + " where dis.track_No = '"+seqId+"' ";
//        sql = sql + " and dis.status != '20' ";
//        sql = sql + " and p.trans_Plan_Id = dis.trans_Plan_Id) ";
//        sql = sql + " and not exists (select 1 ";
//        sql = sql + " from Tpl_Car_Plan_Item t";
//        sql = sql + " where t.struts = '"+struts+"'";
//        sql = sql + " and t.car_Plan_Id = '"+carPlanId+"'";
//        sql = sql + " and p.pack_Id = t.pack_No)";
//        sql = sql + " order by selectFlag asc";
		
		sql = " select t.id as id, ";
		sql = sql + " t.pack_Id as packId, ";
		sql = sql + " i.pick_num as pickNum, ";
		sql = sql + " t.order_Num as orderNum, ";
		sql = sql + " t.gross_Weight as grossWeight, ";
		sql = sql + " t.net_Weight as netWeight, ";
		sql = sql + " case ";
		sql = sql + " when t.pack_Id in (select t1.pack_No ";
		sql = sql + " from Tpl_Car_Plan_Item t1 ";
		sql = sql + " where t1.struts = '"+struts+"' ";
		sql = sql + " and t1.car_Plan_Id = "+carPlanId+") then ";
		sql = sql + " '0' ";
		sql = sql + " when t.pack_Id in (select t1.pack_No ";
		sql = sql + " from Tpl_Car_Plan_Item t1 ";
		sql = sql + " where t1.struts = '"+struts+"' ";
		sql = sql + " and t1.car_Plan_Id != "+carPlanId+") then ";
		sql = sql + " '2' ";
		sql = sql + " else ";
		sql = sql + " '1' ";
		sql = sql + " end as selectFlag, ";
		sql = sql + " t.product_Name as productName, ";
		sql = sql + " substr(i.lading_spot, -3, 3) as ladingSpotId, ";
		sql = sql + " i.dest_spot as destSpotId, ";
		sql = sql + " i.lading_spot_name as ladingSpotName, ";
		sql = sql + " nvl(t.memo,i.dest_spot_name) as destSpotName ";
		sql = sql + " from Tpl_Trans_Pack t, tpl_trans_plan i ";
		sql = sql + " where 1 = 1 ";
		sql = sql + " and t.plan_id = i.id ";
		sql = sql + " and (t.since_Status = '0' or t.since_Status is null) ";
		sql = sql + " and ((t.pack_Status = '0' and t.seq_Id is null) or (t.pack_Status = '1' and t.seq_Id = '"+seqId+"')) ";
		sql = sql + " and exists (select 1 ";
		sql = sql + " from Tpl_Trans_Truck_Dis_Plan dis ";
		sql = sql + " where dis.track_No = '"+seqId+"' ";
		sql = sql + " and dis.status != '20' ";
		sql = sql + " and t.trans_Plan_Id = dis.trans_Plan_Id) ";
		sql = sql + " order by selectFlag ";
		
		System.out.println("sql  "+sql);
		List list = this.getBHibernateTemplate().getJdbcTemplate()
		.queryForList(sql.toString());
		return list;
	}

	public void checkTplCarPlanItem(TplCarPlanSearchModel model)
			throws Exception {
		Long carPlanId = model.getCarPlanId();
		String[] packIds = model.getPackIds();
		//置为失效
		String updateSql = "update tpl_car_plan_item t set t.struts = '1' where t.car_plan_id = "
				+ carPlanId;
		System.out.println(updateSql);
		this.getBHibernateTemplate().getJdbcTemplate().execute(updateSql);
		//删除前一次的司机挑选
		String deleteSql = "delete tpl_car_plan_item t where t.struts_flag = '1' and t.car_plan_id = "
				+ carPlanId;
		System.out.println(deleteSql);
		this.getBHibernateTemplate().getJdbcTemplate().execute(deleteSql);
		
		//新增
		for(int i = 0; i<packIds.length ;i++){
			String insertSql = "insert into tpl_car_plan_item ";
			insertSql = insertSql + " select seq_tpl_car_plan_item.nextval, ";
			insertSql = insertSql + " pa.pack_id, ";
			insertSql = insertSql + " pl.pick_num, ";
			insertSql = insertSql + " pa.order_num, ";
			insertSql = insertSql + " pa.gross_weight, ";
			insertSql = insertSql + " pa.net_weight, ";
			insertSql = insertSql + " pa.unit_count, ";
			insertSql = insertSql + " pa.product_name, ";
			insertSql = insertSql + " substr(pl.lading_spot,7,3), ";
			insertSql = insertSql + " pl.lading_spot_name, ";
			insertSql = insertSql + " pl.dest_spot, ";
			insertSql = insertSql + " nvl(pa.memo,pl.dest_spot_name), ";
			insertSql = insertSql + carPlanId + ", ";// 主表id
			insertSql = insertSql + " '0', ";// 有效
			insertSql = insertSql + " '1', ";// 司机挑选
			insertSql = insertSql + " '00' ";
			insertSql = insertSql + " from tpl_Trans_pack pa, tpl_trans_plan pl ";
			insertSql = insertSql + " where pa.plan_id = pl.id ";
			insertSql = insertSql + " and pa.id = " + packIds[i];// 材料id
			
			System.out.println(insertSql);
			this.getBHibernateTemplate().getJdbcTemplate().execute(insertSql);
		}
		
	}

	public List checkTplTransPack(TplCarPlanSearchModel model) throws Exception {
		StringBuffer hql = new StringBuffer();
		String packNo = model.getPackNo();//xxxx,xxxx,xxxx
		hql.append(" from TplTransPack t where t.id in ("+packNo+") ");
		hql.append(" and t.sinceStatus = '1' ");

		List list = this.queryByHql(hql.toString());
		
		return list;
	}

	public List querySumCarPlanIetm(Long carPlanId) throws Exception {
		String sql = null;
		sql = " select sum(t.grossWeight),sum(t.netWeight),sum(t.count) from TplCarPlanItem t ";
		sql = sql + " where t.carPlanId = "+carPlanId;
		sql = sql + " and t.struts = '0' ";
		
		System.out.println("sql  "+sql);
		List list = this.queryByHql(sql);
		
		return list;
	}

	public TplCarPosition queryTplCarPosition(
			CarStoreSeqenceSearchModel searchModel) throws Exception {
		String seqId = searchModel.getSeqId();
		String carPlanId = searchModel.getCarPlanId();
		String storeCode = searchModel.getStoreCode();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplCarPosition t where t.seqId = '"+seqId+"' ");
		hql.append(" and t.carPickNo = '"+carPlanId+"' ");
		hql.append(" and t.storeCode = '"+storeCode+"'");
		hql.append(" order by t.id desc ");

		List list = this.queryByHql(hql.toString());
		if(list.size()>0){
			return (TplCarPosition)list.get(0);
		}else{
			return null;
		}
	}

	public TplKqListItem queryTplKqListItem(
			CarStoreSeqenceSearchModel searchModel) throws Exception {
		String carPlanId = searchModel.getCarPlanId();
		String storeCode = searchModel.getStoreCode();
		String carId = searchModel.getCarId();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select t from TplKqListItem t,TplKqList l where t.kqListId = l.id ");
		hql.append(" and t.carNum = '"+carId+"' ");
		hql.append(" and t.carPickNo = '"+carPlanId+"' ");
		hql.append(" and l.storeCode = '"+storeCode+"' ");

		List list = this.queryByHql(hql.toString());
		if(list.size()>0){
			return (TplKqListItem)list.get(0);
		}else{
			return null;
		}
		
	}

	public List validateTransDisPlanForZG(ValidetaSearchModel searchModel)
			throws Exception {
		String dispatchId = searchModel.getDispatchId();
		String carId = searchModel.getCarId();
		Long id = searchModel.getId();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplCarPlan t where 1=1 ");
		hql.append(" and t.carPickNo||seqId = '"+dispatchId+"' ");
		hql.append(" and t.carNum = '"+carId+"' ");
		hql.append(" and t.seqId = '"+id+"' ");
		
		List list = this.queryByHql(hql.toString());
		
		return list;
	}

	public void updateTplCarPlanValidate(ValidetaSearchModel searchModel)
			throws Exception {
		Long id = searchModel.getId();
		String storehouseId = searchModel.getStorehouseId();
		
		String sql = "";
		sql = "update tpl_car_plan_item t set t.validate_flag = '10' where 1=1";
		sql = sql + " and t.struts = '0' ";
		sql = sql + " and t.lading_spot_id = '"+storehouseId+"'";
		sql = sql + " and t.car_plan_id = " + id;
		
		System.out.println(sql);
		this.getBHibernateTemplate().getJdbcTemplate().execute(sql);
		
	}

	public void reSetTransDisPlan(String seqId) throws Exception {
		
		String sql = "";
		sql = "update tpl_trans_truck_dis_plan t ";
		sql = sql + " set t.track_no = null, t.bus_number = null, t.trailer_number = null ";
		sql = sql + " where t.track_no = " + seqId;
		sql = sql + " and t.status != '20'";
		
		System.out.println(sql);
		this.getBHibernateTemplate().getJdbcTemplate().execute(sql);
		
	}

	public List checkCarPlanItem(TplCarPlanSearchModel model) throws Exception {
		StringBuffer hql = new StringBuffer();
		String packNo = model.getPackNo();// xxxx,xxxx,xxxx
		Long carPlanId = model.getCarPlanId();
		hql.append(" from TplCarPlanItem i where i.packNo in (");
		hql.append(" select t.packId from TplTransPack t where t.id in (" + packNo + "))");
		hql.append(" and i.struts = '0'");
		hql.append(" and i.carPlanId != " + carPlanId);

		List list = this.queryByHql(hql.toString());

		return list;
	}

	public List getWarehouseSignIdStatus(WarehouseSignSearchModel SignInModel) {
		StringBuffer hql = new StringBuffer();
		String warehouseId = SignInModel.getWarehouseId();
		Long seqId = SignInModel.getSeqId();
		
		hql.append(" from TplTransTruckSignIn t where 1=1 ");
		hql.append(" and t.seqId = "+seqId);
		hql.append(" and substr(t.ladingSpot,-3,3) = '"+warehouseId+"'");
		hql.append(" and t.status = '10'");
		List list = this.queryByHql(hql.toString());
		System.out.println("getWarehouseSignIdStatus:::::::::"+hql.toString());
		return list;
	}
	
	public List getWarehouseSignIdStatusDy(WarehouseSignSearchModel SignInModel) {
		StringBuffer hql = new StringBuffer();
		String warehouseId = SignInModel.getWarehouseId();
		Long seqId = SignInModel.getSeqId();
		String loadingPlanNo = SignInModel.getLoadingPlanNo();
		
		hql.append(" from TplTransTruckSignIn t where 1=1 ");
		hql.append(" and t.seqId = "+seqId);
		hql.append(" and substr(t.ladingSpot,-3,3) = '"+warehouseId+"'");
		hql.append(" and t.status = '10'");
		hql.append(" and t.loadingPlanNo = '"+loadingPlanNo+"'");
		
		List list = this.queryByHql(hql.toString());
		System.out.println("getWarehouseSignIdStatusDy:::::::::"+hql.toString());
		return list;
	}

//	public List queryCarPlanBySearchModel(TransportSearchModel searchModel)
//			throws Exception {
//		StringBuffer hql = new StringBuffer();
//		String seqId = StringUtil.trimStr(searchModel.getSeqId());
//		
//		List list = new ArrayList();
//		hql.append(" from TplCarPlan t where 1=1 ");
//		if(seqId!=""&&seqId.length()>0){
//			hql.append(" t.seqId ='"+seqId+"'");
//		}
//		
//		list = this.queryByHql(hql.toString());
//		return list;
//	}
	public void updateTransDisPlanBySeqId(Long seqId) throws Exception {
		String sql = null;
		sql = "update tpl_trans_truck_dis_plan t set t.validate_flag = '00' where t.validate_flag = '10' and t.track_no = "+seqId;

		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List queryPackSumByPlanId(Long planId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append("select sum(t.grossWeight),sum(t.netWeight),sum(t.unitCount) from TplTransPack t ");
		hql.append(" where t.planId = " + planId);
		hql.append(" and t.seqId is not null");
		// hql.append(" group by t.planId");

		list = this.queryByHql(hql.toString());

		return list;
	}

	public List checkPlanItemValidate(Long id,String warehouseId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append("from TplCarPlanItem t where 1=1 ");
		hql.append(" and t.carPlanId = " + id );
		hql.append(" and t.struts = '0'");
		hql.append(" and t.ladingSpotId like '%"+warehouseId+"'");
		hql.append(" and t.validateFlag = '00'");
		
		list = this.queryByHql(hql.toString());
		return list;
	}

	public List queryAllWarehouseCarNum() throws Exception {
		String sql = "";
		sql = sql+ " select t.store_code as storeCode, t.lane_no as laneNo, a.cnt as carCnt ";
		sql = sql+ " from tpl_kq_list t, ";
		sql = sql+ " (select i.kq_list_id as kqListId, count(*) as cnt ";
		sql = sql+ " from tpl_kq_list_item i ";
		sql = sql+ " group by i.kq_list_id) a ";
		sql = sql+ " where t.id = a.kqListId ";
		sql = sql+ " order by t.store_code ,t.lane_no ";

		List list = this.getBHibernateTemplate().getJdbcTemplate().queryForList(sql.toString());
		
		return list;
	}
	
	public List queryTplCarPositionCount(
			CarStoreSeqenceSearchModel carStoreSeqenceSearchModel)
			throws Exception {
		String flag = carStoreSeqenceSearchModel.getFlag();
		String seqId = carStoreSeqenceSearchModel.getSeqId();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplCarPosition t where 1 = 1");
		if(flag!=null && !flag.trim().equals("")){
			hql.append(" and t.seqId = '"+seqId+"'");
		}
		if(seqId!=null && !seqId.trim().equals("")){
			hql.append(" and t.flag = '"+flag+"'");
		}
		return this.queryByHql(hql.toString());
	}
	
	public List queryTplTransPack(Long trackNo) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplTransPack t where 1=1");
		if(trackNo!=null){
			hql.append(" and t.seqId="+trackNo +"and t.signId is null");
		}
		return this.queryByHql(hql.toString());
	}

	public void updateTplTransSign(TplTransTruckSign tplTransTruckSign)
			throws Exception {
		String sql = "";
		sql = " update tpl_trans_truck_sign t set t.unit_count ="+tplTransTruckSign.getUnitCount();
		sql = sql + " ,t.net_weight ="+tplTransTruckSign.getNetWeight();
		sql = sql + " ,t.gross_weight ="+tplTransTruckSign.getGrossWeight();
		sql = sql + " where t.id = "+tplTransTruckSign.getId();

		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public List queryTransSeqByProviderId(String providerId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		
		hql.append(" from TplTransSeq t where 1=1");
		hql.append(" and t.providerId= '" + providerId + "'");
		hql.append(" and t.proStatus = '02' ");
		
		list = this.queryByHql(hql.toString());
		return list;
	}
	
		
	
	/*public List checkPlansLoadPlanValidate(
			TplTransLoadPlanSearchModel tplTransLoadPlanSearchModel)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		Long transLoadPlanId = tplTransLoadPlanSearchModel.getLoadPladId();
		String ladingSpotCode = tplTransLoadPlanSearchModel.getLadingSpotCode();
		String validateFlag = tplTransLoadPlanSearchModel.getValidateFlag();
		hql.append(" from TplTransLoadPlanItem t where 1=1");
		hql.append(" and t.transLoadPlanId = "+transLoadPlanId+"");
		hql.append(" and t.ladingSpotCode = '"+ladingSpotCode+"'");
		hql.append(" and t.validateFlag = '"+validateFlag+"'");
		return this.queryByHql(hql.toString());
	}*/

	public List sumTplTransLoadPlan(PlanDetailSearchModel detailSearchModel)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		Long seqId = detailSearchModel.getSeqId();
		hql.append("select sum(t.planGrossWeight),sum(t.planNetWeight),sum(t.planCount) from TplTransTruckDisPlan t where 1=1");
		if(seqId!=null){
			hql.append(" and t.trackNo = "+seqId);
		}
		return this.queryByHql(hql.toString());
	}

	public List queryTrainListChildWithArgsBs(
			TransPackSearchModel packSearchModel) throws Exception {
		String seqId = StringUtil.trimStr(packSearchModel.getSeqId().toString());// 车次号

		StringBuffer sql = new StringBuffer("");
		sql.append(" select * from (");
		sql
				.append(" select t.pack_Id as packId, t.order_Num as orderNum, t.product_Name as productName, t.gross_Weight as grossWeight, t.net_Weight as netWeight, plan.DEST_SPOT_NAME as destSpotName, plan.bill_Id as billId, plan.LADING_SPOT_NAME as ladingSpotName, plan.LADING_SPOT as ladingSpot, t.control_Id as controlId, '10' as packType , t.unit_Count as unitCount");
		sql
				.append(" from Tpl_Trans_Pack t,TPL_TRANS_PLAN plan where t.TRANS_PLAN_ID = plan.TRANS_PLAN_ID");
		sql.append(" and t.sign_Id is null");// 未签收的材料
		if (seqId.length() > 0) {
			sql.append(" and t.seq_Id = " + Long.valueOf(seqId));
		}
		sql.append(" union all");
		sql
				.append(" select t.pack_Id as packId, t.order_Num as orderNum, t.product_Name as productName, t.load_Gross_Weight as grossWeight, t.load_Net_Weight as netWeight, plan.DEST_SPOT_NAME as destSpotName, plan.bill_Id as billId,  plan.LADING_SPOT_NAME as ladingSpotName,plan.LADING_SPOT as ladingSpot, t.control_Id as controlId, '20' as packType , t.unit_Count as unitCount ");
		sql
				.append(" from Tpl_Trans_Pack_Vir t,TPL_TRANS_PLAN plan where t.TRANS_PLAN_ID = plan.TRANS_PLAN_ID");
		sql.append(" and t.sign_Id is null");// 未签收的材料
		sql.append(" and t.load_Unit_Count > 0");
		sql.append(" and t.storage_Type = '10'");// 10 装车 20 到货
		if (seqId.length() > 0) {
			sql.append(" and t.seq_Id = " + Long.valueOf(seqId));
		}
		sql.append(" ) ");
		sql.append(" order by packId");
		
		List list = this.getBHibernateTemplate().getJdbcTemplate()
				.queryForList(sql.toString());
		System.out.println("queryTrainListChildWithArgs  " + sql);
		return list;
	}
	
	public List queryWarehousePlan(TplTransLoadPlanSearchModel model) throws Exception {
		StringBuffer hql = new StringBuffer();
		Long seqId = model.getSeqId();//车次
		String ladingSpotCode = model.getLadingSpotCode();
		String loadingPlanNo = model.getLoadingPlanNo();
		List argList = new ArrayList();
		hql.append(" select distinct t.loadingPlanNo,t.ladingSpotCode,t.ladingSpotName from TplTransLoadPlan t where 1=1 ");
		if(null !=seqId){
			hql.append(" and t.seqId = ?");
			argList.add(seqId);
		}
		if(null !=ladingSpotCode && !ladingSpotCode.trim().equals("")){
			hql.append(" and t.ladingSpotCode = ?");
			argList.add(ladingSpotCode);
		}
		if(null != loadingPlanNo && !loadingPlanNo.trim().equals("")){
			hql.append(" and t.loadingPlanNo = ?");
			argList.add(loadingPlanNo);
		}
		System.out.println("queryWarehousePlan::::::::::::"+hql.toString());
		return this.queryByHql(hql.toString(), argList.toArray());
	}
	
	public List queryTplRWarehouseGps(String wareHouseCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from TplRWarehouseGps t where 1=1");
		hql.append(" and t.abbreviationCode = '"+wareHouseCode+"'");
		return this.queryByHql(hql.toString());
	}
	
	public void updateTplTransLoadPlan(ValidetaSearchModel searchModel)
	throws Exception {
			String loadingPlanNO = searchModel.getLoadingPlanNO();
			Long seqId = searchModel.getId();
			String ladingSpotId = searchModel.getStorehouseId();
			String sqlTplTransLoadPlan = null;
			String sqlTplTransTruckDisPlan = null;
			if(null !=loadingPlanNO && !loadingPlanNO.trim().equals("")){
				sqlTplTransLoadPlan = "UPDATE TPL_TRANS_LOAD_PLAN t set t.VALIDATE_FLAG = '10' where 1=1";
				sqlTplTransLoadPlan+=" and t.LOADING_PLAN_NO= '"+loadingPlanNO +"'";
				sqlTplTransLoadPlan+=" and t.SEQ_ID = "+seqId;
				sqlTplTransLoadPlan+=" and t.LADING_SPOT_CODE = '"+ladingSpotId+"'";
				this.getSession().createSQLQuery(sqlTplTransLoadPlan).executeUpdate();
				
				sqlTplTransTruckDisPlan = "UPDATE TPL_TRANS_TRUCK_DIS_PLAN t set t.VALIDATE_FLAG = '10' where 1=1";
				sqlTplTransTruckDisPlan+=" and t.BILL_ID= '"+loadingPlanNO +"'";
				sqlTplTransTruckDisPlan+=" and t.TRACK_NO ="+seqId;
				sqlTplTransTruckDisPlan+=" and t.LADING_SPOT= '"+ladingSpotId+"'";
				this.getSession().createSQLQuery(sqlTplTransTruckDisPlan).executeUpdate();
			}
	}
	
	public List queryAllCarList() throws Exception {
		String sql = "";
		sql = sql+ " select t.STOCK_NO as stockNo, a.cnt as carCnt, a.timeWait as maxTimeWait";
		sql = sql+ " from TPL_PROVIDER_CAR_LIST t, ";
		sql = sql+ " (select i.CAR_LIST_ID as carListId, count(*) as cnt ,max(i.TIME_WAIT)	as timeWait";
		sql = sql+ " from TPL_PROVIDER_CAR_LIST_ITEM i where i.STATUS ='1'";
		sql = sql+ " group by i.CAR_LIST_ID) a ";
		sql = sql+ " where t.id = a.carListId ";
		sql = sql+ " order by t.STOCK_NO ";
		return this.getBHibernateTemplate().getJdbcTemplate().queryForList(sql.toString());
	}
	
	public void updateTransLoadPlan(TplTransLoadPlan tplTransLoadPlan ) throws Exception {
		String sql = null;
		sql = "UPDATE TPL_TRANS_LOAD_PLAN t set t.SEQ_ID = "+tplTransLoadPlan.getSeqId()
		+" ,t.STATUS= '"+tplTransLoadPlan.getStatus() +"'"
		+" ,t.VALIDATE_FLAG = '"+tplTransLoadPlan.getValidateFlag()+"'"
		+" ,t.SIGN_IN_STATUS = '"+tplTransLoadPlan.getSignInStatus()+"'"
		+" ,T.UPDATE_DATE=SYSDATE "
		+" where t.LOADING_PLAN_NO ='"+tplTransLoadPlan.getLoadingPlanNo()+"'";
		System.out.println("updateTplTransLoadPlan::::::"+sql);
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public List queryTplProviderCarAllot(CarStoreSeqenceSearchModel searchModel) throws Exception {
		String seqId = searchModel.getSeqId();
		String storeCode= searchModel.getStoreCode();
		String loadingPlanNo = searchModel.getLoadingPlanNo();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.ID,t.PARKING_LOT,t.FLAG,t.LOADING_PLAN_NO FROM TPL_PROVIDER_CAR_ALLOT t where 1=1");
		sql.append(" and t.FLAG != '20'");//20已失效
		sql.append(" and t.SEQ_ID = '"+seqId+"'");
		sql.append(" and substr(t.PARKING_LOT,0,3) = '"+storeCode+"'");
		sql.append(" and t.LOADING_PLAN_NO = '"+loadingPlanNo+"'");
		sql.append(" order by t.id desc ");
		System.out.println("queryTplProviderCarAllot："+sql);
		 return  this.getBHibernateTemplate().getJdbcTemplate().queryForList(sql.toString());
	}
	
	public TplProviderCarListItem queryCarListItem(
			CarStoreSeqenceSearchModel searchModel) throws Exception {
		String loadingPlanNo = searchModel.getLoadingPlanNo();
		String storeCode = searchModel.getStoreCode();

		StringBuffer hql = new StringBuffer();
		hql
				.append(" select t from TplProviderCarListItem t,TplProviderCarList tt where t.carListId = tt.id ");
		hql.append(" and t.status != '0'");//0为失效状态
		hql.append(" and tt.stockNo = '" + storeCode + "' ");
		hql.append(" and t.loadingPlanNo = '" + loadingPlanNo + "' ");
		List list = this.queryByHql(hql.toString());
		if (null != list && list.size() > 0) {
			return (TplProviderCarListItem) list.get(0);
		} else {
			return null;
		}
	}
	
	public List queryDisPlanSearchModelBS(PlanDetailSearchModel searchModel)
			throws Exception {
		String driUCode = StringUtil.trimStr(searchModel.getDriUCode());
		String status = StringUtil.trimStr(searchModel.getStatus());
		String billId = searchModel.getBillId();
		StringBuffer hql = new StringBuffer();
		List result = new ArrayList();
		hql
				.append("select  distinct t.id, t.uploadTime, t.billId, t.planGrossWeight,t.planNetWeight, t.planCount,t.excuteDate,t.status, t.urgencyDeg, t.dispatcherName, t.driName, t.busNumber,t.pickNum, lp.actGrossWeight, lp.actNetWeight, lp.actCount,ttp.packType,");
		hql
				.append("t.providerId,t.validateFlag, t.trackNo,lp.ladingSpotName,lp.destSpotName,lp.startTime,lp.endTime,lp.finalUseId,lp.finalUseName,lp.signInStatus");
		hql
				.append("  from TplTransTruckDisPlan t,TplTransPlan ttp,TplTransLoadPlan lp,TplTransLoadPlanItem lpi where lp.id=lpi.transLoadPlanId and t.transPlanId = lp.loadingPlanNo and  lpi.pickNum=ttp.pickNum");
		hql
				.append(" and (ttp.planCount >= ttp.actCount or (ttp.packType='20' and ttp.status !='2' ))and ttp.transType = '1'  and t.flag='10'");// 汽运
		hql
				.append(" and (ttp.batchStatus ='0' or ttp.batchStatus ='1' or ttp.batchStatus is null)");
		hql.append(" and lp.status !=2");
		if (driUCode.length() > 0) {
			hql.append("and t.driUCode= '" + driUCode + "' ");
		}
		if (!status.equals("")) {
			hql.append("and t.status= '" + status + "' ");
		} else {
			hql.append("and t.status != '20'");
		}
		if(null !=billId && !billId.trim().equals("")){
			hql.append("and t.billId = '"+billId+"'");
		}
		hql.append(" order by t.id");
		System.out.println("查询登陆司机的装载计划，queryDisPlanSearchModelBS：：： " + hql.toString());
		result = this.queryByHql(hql.toString());
		return result;
	}
	public List queryTplPlanAllocationCar(
			CarsInfoSearchModel model) throws Exception {
		Long seqId = model.getSeqId();
		String loadingPlanNo = model.getLoadingPlanNo();
		StringBuffer hql = new StringBuffer();
		hql.append("from TplPlanAllocationCar t where 1=1 and t.revokeStatus = '0'");
		if (seqId != null) {
			hql.append(" and t.seqId = '" + seqId.toString() + "' ");
		}
		if(null != loadingPlanNo && !loadingPlanNo.trim().equals("")){
			hql.append(" and t.loadingPlanNo = '" + loadingPlanNo + "' ");
		}
		System.out.println("查询社会服务商装载计划配车信息，queryTplPlanAllocationCar"+hql.toString());
		return this.queryByHql(hql.toString());
	}

	public void udpateTransLoadPlanStatus(Long seqId) throws Exception {
		if (null != seqId) {
			System.out.println("更新汽运调度计划和装载计划状态");
			StringBuffer hql = new StringBuffer(
					"update TPL_TRANS_LOAD_PLAN t4 set t4.STATUS='"
							+ "2"
							+ "' where t4.SEQ_ID= "
							+ seqId
							+ " and  t4.LOADING_PLAN_NO  in (select distinct t5.LOADING_PLAN_NO from TPL_ACT_LOAD_PLAN t5 where t5.seq_id ='"
							+ seqId
							+ "' and t5.LOADING_PLAN_NO not in"
							+ " (select distinct t.LOADING_PLAN_NO from TPL_ACT_LOAD_PLAN t ,TPL_ACT_LOAD_PLAN_ITEM tt where t.ID =tt.PLAN_ACT_ID and t.seq_id = '"
							+ seqId
							+ "' and tt.pack_id in (select t3.PACK_ID from tpl_trans_pack t3 where t3.pro_Status not in ('60','70') and t3.SEQ_ID="
							+ seqId + ")))");
			System.out.println("更新装载计划状态:::::" + hql);
			this.getSession().createSQLQuery(hql.toString()).executeUpdate();
			StringBuffer hql2 = new StringBuffer(
					"update TPL_TRANS_TRUCK_DIS_PLAN t4 set t4.STATUS='"
							+ "20"
							+ "' where t4.TRACK_NO = '"
							+ seqId
							+ "' and t4.TRANS_PLAN_ID in (select distinct t5.LOADING_PLAN_NO from TPL_ACT_LOAD_PLAN t5 where  t5.seq_id ='"
							+ seqId
							+ "' and t5.LOADING_PLAN_NO not in"
							+ " (select distinct t.LOADING_PLAN_NO from TPL_ACT_LOAD_PLAN t ,TPL_ACT_LOAD_PLAN_ITEM tt where t.ID =tt.PLAN_ACT_ID and t.seq_id = '"
							+ seqId
							+ "' and tt.pack_id in (select t3.PACK_ID from tpl_trans_pack t3 where t3.pro_Status not in ('60','70') and t3.SEQ_ID="
							+ seqId + ")))");
			System.out.println("更新汽运调度计划:::::" + hql2);
			this.getSession().createSQLQuery(hql2.toString()).executeUpdate();
		}
	}

	public List queryTransLoadPlanItem(PlanDetailSearchModel searchModel)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		String billId = searchModel.getBillId();
		System.out.println("查询出装载计划详情，装载计划号为："+billId);
		hql
				.append("select distinct tt.id as id,tt.pickNum,tt.orderNum,tt.planWeight,tt.planCount,t.ladingSpotName,t.destSpotName,t.validateFlag,t3.id from TplTransLoadPlan t,TplTransLoadPlanItem tt,TplTransTruckDisPlan t3 where t.id = tt.transLoadPlanId and t.loadingPlanNo = t3.transPlanId");
		if (null != billId && !billId.trim().equals("")) {
			hql.append(" and t.loadingPlanNo = '" + billId + "'");
		}
		hql.append(" order by tt.id");
		System.out.println("sql " + hql.toString());
		return this.queryByHql(hql.toString());
	}

	public TplSignInRangeManage queryTplSignInRangeManage() throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from TplSignInRangeManage where 1=1");
		System.out.println("查询出签到范围信息，queryTplSignInRangeManage:"+hql.toString());
		List list =  this.queryByHql(hql.toString());
		if(list!=null && list.size()>0){
			return (TplSignInRangeManage)list.get(0);
		}
		return null;
	}

	public List queryAppEdition() throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select max(versionCode) from TplAppEditionManage where 1=1");
		System.out.println("获取最新app版本信息,queryAppEdition::::"+hql.toString());
		return this.queryByHql(hql.toString());
	}

	/*public List queryTplTransPack(Long trackNo) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplTransPack t where 1=1");
		if(trackNo!=null){
			hql.append(" and t.seqId="+trackNo +"and t.signId is null");
		}
		return this.queryByHql(hql.toString());
	}*/

	public List queryTplProviderCarAllotCount(
			CarStoreSeqenceSearchModel carStoreSeqenceSearchModel)
			throws Exception {
		String seqId = carStoreSeqenceSearchModel.getSeqId();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplTransLoadPlan t where 1=1");
		hql.append(" and t.loadingPlanNo not in ( select tt.loadingPlanNo from TplProviderCarAllot tt where tt.seqId= '"+seqId+"' and tt.flag ='10')");
		hql.append(" and t.seqId='"+seqId+"'");
		return this.queryByHql(hql.toString());
	}

	public List queryTplTransLoadPlan(TplTransLoadPlanSearchModel model) throws Exception {
		Long seqId = model.getSeqId();
		String loadingPlanNo = model.getLoadingPlanNo();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplTransLoadPlan t where 1=1");
		hql.append(" and t.seqId ="+seqId);
		hql.append(" and t.loadingPlanNo = '"+loadingPlanNo+"'");
		return this.queryByHql(hql.toString());
	}
	
	public void updateTplTransTruckSignIn(String seqId, String loadingPlanNo)
			throws Exception {
		System.out.println("更签到状态为失效");
		StringBuffer hql = new StringBuffer(
				"update TPL_TRANS_TRUCK_SIGN_IN t set t.STATUS='" + "00"
						+ "' where t.SEQ_ID=" + Long.valueOf(seqId));
		this.getSession().createSQLQuery(hql.toString()).executeUpdate();

		// 更新此车次的服务商车辆车位分配信息为失效
		StringBuffer sqlProviderCarAllot = new StringBuffer(
				"UPDATE TPL_PROVIDER_CAR_ALLOT t set t.FLAG = '20' where t.SEQ_ID= '"
						+ seqId + "'");
		System.out.println("更新此车次的服务商车辆车位分配信息为失效,sqlProviderCarAllot:::"+ sqlProviderCarAllot);
		this.getBHibernateTemplate().getJdbcTemplate().execute(sqlProviderCarAllot.toString());

		// 清空调度计划
		StringBuffer sqlDisPlan = new StringBuffer();
		sqlDisPlan.append("update tpl_trans_truck_dis_plan t");
		sqlDisPlan.append(" set t.track_no = null, t.bus_number = null, t.trailer_number = null ,t.VALIDATE_FLAG= '00',t.STATUS='00'");
		sqlDisPlan.append(" where t.track_no = " + seqId);
		sqlDisPlan.append(" and t.status != '20'");
		System.out.println("清空调度计划::::"+sqlDisPlan);
		this.getBHibernateTemplate().getJdbcTemplate().execute(sqlDisPlan.toString());
		
		// 更新排队信息为失效
		StringBuffer sqlProviderCarList = new StringBuffer();
		sqlProviderCarList.append("UPDATE TPL_PROVIDER_CAR_LIST_ITEM t set t.STATUS ='0' where 1=1");
		sqlProviderCarList.append(" and t.LOADING_PLAN_NO ='"+ loadingPlanNo + "'");
		System.out.println("更新排队信息为失效,sqlProviderCarList:::::"+ sqlProviderCarList);
		this.getBHibernateTemplate().getJdbcTemplate().execute(sqlProviderCarList.toString());
	}

	public TplTransSeqChose queryTplTransSeqChose(TplTransSeqChose tplTransSeqChose) throws Exception {
		String driver = tplTransSeqChose.getDriver();
		List list = new ArrayList();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplTransSeqChose t where 1=1 and t.status = '1'");
		
		if(null != driver && !driver.trim().equals("")){
				hql.append(" and t.driver = '"+driver+"'");
		}
		list =  this.queryByHql(hql.toString());
		if(null !=list && list.size()>0){
			return (TplTransSeqChose) list.get(0);
		}
		return null;
		
	}

	public List queryTplTransParkingLot() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.STORE_CODE as storeCode,t.PARKING_CODE as parkingCode from tpl_trans_Parking_lot t where 1=1 and t.STATUS ='1'");
		return this.getBHibernateTemplate().getJdbcTemplate().queryForList(sql.toString());
	}

	public List queryNotTplTransLoadPlan(Long seqId, String controlId)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select t.loadingPlanNo,tt.id from TplTransLoadPlan t,TplTransTruckDisPlan tt where  t.loadingPlanNo = tt.transPlanId and t.signInStatus='00'");
		hql.append(" and t.seqId = "+seqId);
		hql.append(" and tt.id not in ("+controlId+")");
		System.out.println("sql " + hql.toString());
		return this.queryByHql(hql.toString());
	}

	public List queryWhetherCarAllot(Long seqId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.* FROM TPL_PROVIDER_CAR_ALLOT t where 1=1");
		sql.append(" and t.FLAG = '10'");//10已到位
		sql.append(" and t.SEQ_ID = '"+seqId+"'");
		sql.append(" order by t.id desc ");
		System.out.println("queryTplProviderCarAllot："+sql);
		 return  this.getBHibernateTemplate().getJdbcTemplate().queryForList(sql.toString());
	}

	public List queryTplActLoadPlan(Long seqId,String signInStatus)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT tt FROM TplTransLoadPlan t,TplTransTruckDisPlan tt where 1=1");
		hql.append(" and t.loadingPlanNo = tt.transPlanId");
		hql.append(" and t.seqId ='"+seqId+"'");
		hql.append(" and t.signInStatus ='"+signInStatus+"'");
		hql.append(" and t.loadingPlanNo not in (select t3.loadingPlanNo from TplActLoadPlan t3 where t3.seqId='"+seqId+"')");
		
		System.out.println("queryTplActLoadPlan:::::::::::"+hql);
		return this.queryByHql(hql.toString());
	}

	public List queryTplTransTruckSign(Long seqId) throws Exception {
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		
		hql.append(" from TplTransTruckSign t where 1=1 ");
		hql.append(" and t.trackNo = "+seqId);
		System.out.println(hql.toString());
		list = this.queryByHql(hql.toString());
		return list;
	}

	/*public List queryTplCarPositionCount(
			CarStoreSeqenceSearchModel carStoreSeqenceSearchModel)
			throws Exception {
		String flag = carStoreSeqenceSearchModel.getFlag();
		String seqId = carStoreSeqenceSearchModel.getSeqId();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TplCarPosition t where 1 = 1");
		if(flag!=null && !flag.trim().equals("")){
			hql.append(" and t.seqId = '"+seqId+"'");
		}
		if(seqId!=null && !seqId.trim().equals("")){
			hql.append(" and t.flag = '"+flag+"'");
		}
		return this.queryByHql(hql.toString());
	}*/
	
	/***
	 * 根据landingSpotId判断是否存在
	 * @param landingSpotId
	 * @return
	 * @throws Exception
	 */
	public boolean isContainZGWarehouseCode(String landingSpotId) throws Exception{
		boolean flag =false;
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append(" from TplZgWarehouse t where 1=1");
		hql.append(" and t.landingSpotId= '" + landingSpotId + "'");
		hql.append(" and t.status = '1' ");
		list = this.queryByHql(hql.toString());
		if(null != list && list.size()>0){
			flag =true;
		}
		return flag;
	}
	
	/****
	 * 获取产品形态 add by ranqiguang 20190329
	 * @return
	 * @throws Exception
	 */
	public List getZGWarehouseProductTypes(String systemType) throws Exception{
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		hql.append("select distinct t.productType from TplZgWarehouse t where 1=1");
		if(null!=systemType && !"".equals(systemType)){
			hql.append(" and t.systemType = '"+systemType+"'");
		}
		hql.append(" and t.status = '1' ");
		System.out.println("======getZGWarehouseProductTypes:"+hql.toString());
		list = this.queryByHql(hql.toString());
		return list;
	}
}
