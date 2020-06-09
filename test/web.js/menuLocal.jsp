<%@ page language="java" %>
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<html:html>


<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Menu</title>
  <link href="css/style.css" rel="stylesheet" type="text/css">
 <link rel="stylesheet" type="text/css" href="css/checktree.css" />
 <script type="text/javascript" src="js/checktree.js"></script>
 <script type="text/javascript">
 <!--

 // USAGE NOTES: Create a new CheckTree() object like so, and pass it its own name.
 var checkmenu = new CheckTree('checkmenu');
 // You can create several such tree objects, just give each a unique name.

 // One optional property: whether to count all checkboxes beneath the current level,
 // or just to count the checkboxes immediately beneath the current level (the default).
 //checkmenu.countAllLevels = true;

 //--></script>
<base target="main"/>
</head>

<body>

<form action="javascript:void(0)">
<ul id="tree-checkmenu" class="checktree">
  <!--系统管理Begin-->
 <li id="show-system"> <img src="images/90.gif" border="0" valign="middle"/>基础数据管理<span id="count-system" class="count"></span> 
    <ul id="tree-system">
      
    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/ownerInfo.do?method=list">委托机构信息管理</a></li>	
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/BreedMgr.do?method=list">品种信息管理</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/FinalUser.do?method=search">最终用户信息管理</a></li>
	  <!--<li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/feePlan.do?method=list">费率管理</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/prodPlaceMgr.do">产地信息管理</a></li>-->
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/transStationInq.do">运输站点管理</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplRProviderWarehouse.do?method=queryRProviderWarehouse">仓库信息管理</a></li>	
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/warehouseAutoManage.do?method=query">仓库自动反馈管理</a></li>	
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplTransSeq.do?method=queryTransSeq">船批信息管理</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/MessageLog.do">错误信息管理</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickPromisee.do?method=query">电子提货单打印权限申请</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickChapter.do?method=query">电子提货单专用章授权书</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickLaw.do?method=query">法人代表授权委托书</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickWare.do?method=query">电子提货单仓库服务协议</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickManage.do?method=query">电子提货单资质管理</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/traceMgr.do?method=main">航线航点管理</a></li>
	</ul>
  </li>  

  
   <li id="show-storage"> <img src="images/90.gif" border="0" valign="middle"/>仓储业务管理<span id="count-storage" class="count"></span> 
    <ul id="tree-storage">
	  <li id="show-stockinfo"><img src="images/90.gif" border="0" valign="middle"/>仓库能力预报
      	<ul id="tree-stockinfo">
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/wproviderAbility.do?method=queryRProviderWarehouse" target=main>仓储能力预报</a></li>
	  </ul>
   	  </li>

      <li id="show-in"><img src="images/90.gif" border="0" valign="middle"/>入库管理
      	<ul id="tree-in">
      	      	<li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInPlan.do?method=list" >入库作业计划管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInManage.do?method=listinstoreplanmgr" >入库管理</a></li>
			  <li><span class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInManage.do?method=InStoreList">入库确认</a></span></li>
			  <li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInFollows.do?method=list">入库跟踪</a></li>
			 
			  
        </ul>
   	  </li>
      <li id="show-out"><img src="images/90.gif" border="0" valign="middle"/>出库管理
      	
      	<ul id="tree-out">
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutPlan.do?method=list" >出库作业计划管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutMgr.do?method=list" target=main>出库管理</a> </li>
			  <li><span class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutSure.do?method=list" target=main>出库确认</a></span></li>
			  <li><span class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutSure.do?method=listcheck" class="a" target=main>出门验证</a></span></li>			  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutSure.do?method=listtrack" class="a" target=main>出库跟踪</a></li>
        </ul>
   	  </li>
      <li id="show-stock"><img src="images/90.gif" border="0" valign="middle"/>库存管理
      		<ul id="tree-stock">
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=search" class="a" target=main>库存查询</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=searchStockForMove" class="a" target=main>库位变更</a></li>
			  <li><span class="last"><img src="images/90.gif" alt="a" border="0" valign="middle"/><a href="../stock/stockCheck.do?method=search" class="a" target=main>库存盘点作业管理</a></span></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockAdjust.do?method=queryStockAdjust" class="a" target=main>移库调整</a></li>
			  <!--<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=takeStock" class="a" target=main>厂外延伸库盘库</a></li>
			  <li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=adminSearch" class="a" target=main>库存调整</a></li>-->
			  
			  <li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=stockOtherPage" class="a" target=main>厂外库库位查询</a></li>
	      </ul>
   	  </li>
	  
  </ul>
  </li>   
  
  <li id="show-transport"> <img src="images/90.gif" border="0" valign="middle"/>运输业务管理<span id="count-transport" class="count"></span>      
    <ul id="tree-transport">
      <li id="show-ship"><img src="images/90.gif" border="0" valign="middle"/>水运业务管理
      <span id="count-ship" class="count"></span> 
        <ul id="tree-ship">
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/shipAbility.do?method=queryTransShip"  target=main>水运能力预报</a></li> 
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipPlan.do"  target=main>水运作业计划管理</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplTransSeq.do?method=queryShipManager"  target=main>船批信息管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=listM"  target=main>装港动态管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipResult.do"  target=main>水运费用预处理</a></li>  
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=list"  target=main>装船确认管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipTrace.do?method=queryTransShip">在途动态管理</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipUpload.do"  target=main>到货实绩管理</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipPortFee.do"  target=main>港建费管理</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=listPrint"  target=main>装船编程表管理</a></li>  
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=listTransCurrentPack"  target=main>水运材料船批信息</a></li>
		</ul>
      </li>   
      <li id="show-train"><img src="images/90.gif" border="0" valign="middle"/>铁运业务管理
      <span id="count-train" class="count"></span> 
        <ul id="tree-train">
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooPlan.do?method=queryTransPlan"  target=main>铁运作业计划管理</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooLoad.do?method=queryTransChooSeq"  target=main>铁运装车管理</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooUpload.do?method=queryTransChooUpload"  target=main>铁运到货确认管理</a></li>       
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooResult.do?method=queryTransChooResult"  target=main>铁运车皮管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/goodBillInq.do"  target=main>铁运货票管理</a></li>
		</ul>
      </li>  
      <li id="show-car"><img src="images/90.gif" border="0" valign="middle"/>汽运业务管理
      <span id="count-car" class="count"></span> 
        <ul id="tree-car">
          <li id="show-storeinvoice"><img src="images/90.gif" border="0" valign="middle"/>湛江汽运业务管理
			  <span id="count-storeinvoice" class="count"></span> 
				<ul id="tree-storeinvoice">					
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckPlanBind"  target=main>司机车辆绑定</a></li>
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=transMaterialManageQuery"  target=main>配载材料挑选</a></li> 
		  			<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=queryTransTruckMaterial"  target=main>材料履历查询</a></li> 
		  			<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckDisPlan"  target=main>车辆履历查询</a></li> 
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckSeq.do?method=query"  target=main>车次出厂管理</a></li> 
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlan.do?method=queryDispatch"  target=main>材料流向管理</a></li> 
				</ul>
		  </li> 	
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlan.do?method=queryTransPlan&i=0"  target=main>汽运作业计划管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=chooseFont"  target=main>汽运装车管理</a></li><!-- 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckPlanBind"  target=main>司机车辆绑定</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=transMaterialManageQuery"  target=main>配载材料挑选</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=queryTransTruckMaterial"  target=main>材料履历查询</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckDisPlan"  target=main>车辆履历查询</a></li> 
		  --><li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=queryTransTruckSeq"  target=main>汽运发车管理</a></li> 
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckUpload.do?method=queryTransTruckUpload"  target=main>汽运到货确认管理</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckResult.do?method=queryTransTruckResult&type=load"  target=main>汽运实绩查询</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/TransTruckSignMgr.do?method=transFortruckSignInfo"  target=main>汽运签收管理(调度)</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/TransTruckSignMgr.do?method=querySignInfoListForRun"  target=main>汽运签收管理(司机)</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=searchFirst"  target=main>汽运调度管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=dispatchTrack"  target=main>汽运调度跟踪</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=searchTransTruckStatistics"  target=main>汽运车载流量统计</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/TransTruckSignMgr.do?method=queryAbnormalList"  target=main>汽运疑议材料管理</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckRoadCodeMgr.do?method=queryRoadCodeList"  target=main>车辆路码表查询</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../3pl/map.do?method=map"  target=_blank>地图定位</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=flySearch"  target=main>汽运转授权业务跟踪</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=flyPresentationSearch"  target=main>汽运转授权业务交单</a></li>
			  
		 
		</ul>
      </li> 	
      <li id="show-ecwl"><img src="images/90.gif" border="0" valign="middle"/>二程业务管理
      <span id="count-ecwl" class="count"></span> 
        <ul id="tree-ecwl"> 
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlanMtm.do?method=queryTransPlan&i=0"  target=main>二程汽运计划管理</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoadMtm.do?method=queryShipManager"  target=main>二程船批管理</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipPlanMtmInq.do"  target=main>二程水运计划管理</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooPlanMtm.do?method=queryTransPlan"  target=main>二程铁运计划管理</a></li>   
        </ul>
      </li>
			
    </ul>
  </li>  
  
   <li id="show-invoice"><img src="images/90.gif" border="0" valign="middle"/>费用结算管理
		<ul id="tree-invoice">
		  <li id="show-storeinvoice"><img src="images/90.gif" border="0" valign="middle"/>仓储费用管理
			  <span id="count-storeinvoice" class="count"></span> 
				<ul id="tree-storeinvoice">					
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/stockInvoiceGenerate.do?method=list" target=main>仓储发票生成</a></li>
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/stockInvoiceMgr.do?method=list" target=main>仓储发票管理</a></li>
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/stockInvoiceMgr.do?method=listsearch" target=main>仓储发票查询</a></li>									
				</ul>
			</li> 		
			
			<li id="show-transinvoice"><img src="images/90.gif" border="0" valign="middle"/>运输费用管理
			  <span id="count-transinvoice" class="count"></span> 
				<ul id="tree-transinvoice">					
					<li id="show-insurance"><img src="images/90.gif" border="0" valign="middle"/>保单管理
					  <span id="count-insurance" class="count"></span> 
						<ul id="tree-insurance">
							<li id="show-shipinsurance"><img src="images/90.gif" border="0" valign="middle"/>水运保单管理
							<span id="count-shipinsurance" class="count"></span> 
								<ul id="tree-shipinsurance">
								<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/insurancePickInq.do"  target=main>保单生成</a></li>
								<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/insuranceShipManage.do"  target=main>保单管理</a></li> 		
								</ul>
							</li> 
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/insuranceTrainManage.do" target="main">铁运保单管理</a></li>
						</ul>
					</li>         
					
					<li id="show-invoicedetail"><img src="images/90.gif" border="0" valign="middle"/>发票管理
					  <span id="count-invoicedetail" class="count"></span> 
						<ul id="tree-invoicedetail">
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceGenerate.do?method=list"  target=main>运费发票生成</a></li>										
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceMgr.do?method=list"  target=main>运费发票管理</a></li>			
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceMgr.do?method=listsearch"  target=main>运费发票查询</a></li>
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceGenerate.do?method=shiplist"  target=main>水运整船开票</a></li> 							
					 		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInsuranceMgr.do?method=list"  target=main>保单发票管理</a></li> 							
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInsuranceMgr.do?method=listsearch"  target=main>保单发票查询</a></li> 							
					 		    
						</ul>
					</li> 					
					
					<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/goodBillInq.do" target="main">铁运货票管理</a></li>
				</ul>
			</li> 		
			<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceTitleChange.do?method=list" target="main">发票抬头变更</a></li>
			<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceTitleUser.do?method=list" target="main">发票抬头地区公司管理</a></li>
			<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceGenerate.do?method=settleTrack&op=init" target="main">自行结算跟踪</a></li>
        </ul>
	</li>

    <li id="show-supplier"> <img src="images/90.gif" border="0" valign="middle"/>物流服务资源管理<span id="count-supplier" class="count"></span> 
		<ul id="tree-supplier">
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/feeRate.do" target="main">基本运价查询</a></li>   
			 <!-- <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/transportAgreementInq.do" target="main">运输协议管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/stockAgreementInq.do" target="main">仓储协议管理</a></li>          
			  -->
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/carAction.do?method=list" target="main">车辆资料管理</a></li>  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/shipInq.do?method=list">船籍资料管理</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/shipInq.do?method=queryShip">船舶到港信息管理</a></li> 
			  <!--<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/transStation.do?method=list" target="main">运输站点查询</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/transportInq.do" target="main">运输路线查询</a></li>       			    
			  -->
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/summary.do" target="main">服务商小结报告</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/evaluationInq.do" target="main">服务商评价查询</a></li>
			  
			  <li id="show-providerBaseInfoMgr"><img src="images/90.gif" border="0" valign="middle"/>服务商基本信息管理
				<span id="count-providerBaseInfoMgr" class="count"></span> 
				<ul id="tree-providerBaseInfoMgr">
					<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRProvider.do?method=queryProviderBaseInfo&operate=providerInq"  target=main>服务商基本信息管理</a></li>
					<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRProvider.do?method=queryTplRProvider&operate=providerInq"  target=main>服务商开户申请管理</a></li> 		
				</ul>
			  </li> 
			  			        
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRProvider.do?method=queryTplRProvider&operate=providerMisConfig" target="main">服务商自有系统对接配置管理</a></li>         
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRDocument.do?method=queryTplRDocument" target="main">电子公文管理</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/modifyApply.do?method=queryModifyApplyInfo" target="main">服务资源信息修改申请管理</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/rProductPrice.do?method=list" target="main">基价调整管理</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/esignBsteelComEdocsignLogin.do" target="main">USB_KEY管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/DriverManage.do?method=search" target="main">司机移动终端管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/DriverManage.do?method=shipLineM" target="main">hang xian guanli </a></li>
			  <!--<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/MobileTerminalManage.do?method=search" target="main">车辆移动终端管理</a></li>-->
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/DriverManage.do?method=search" target="main">车辆移动终端管理</a></li>
			  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplHeadstockManage.do?method=search" target="main">车头管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplTrailerManage.do?method=search" target="main">挂车管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplVehDistributionManage.do?method=search" target="main">车辆分配管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplDestinationManage.do?method=search" target="main">目的地管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermit.do?method=query" target="main">车辆进厂证管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermitZj.do?method=query" target="main">湛江进厂证管理</a></li>
			   <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermitZj.do?method=queryTemporary" target="main">湛江临时进厂证管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/infocard.do?method=query" target="main">司机信息卡管理</a></li>
			  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceLimit.do?o=list" target="main">增值税发票限额管理</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceLimit.do?o=number" target="main">税号信息查询</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../useproblem/Viewuseproblem.do"  target=main>承运商系统使用问题</a></li>
		   	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../useproblem/Viewuseproblem.do?method=useProblemTotal"  target=main>承运商系统使用问题汇总</a></li> 
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendContract.do?method=searchList" >合同替换查看</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/tplPMobileQuality.do?method=list" target="main">水运质量授权</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInGoods.do?method=list" >入库实绩发送</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transWarehouseRules.do?method=queryWarehouseRules" >仓库规则管理</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transGarageRules.do?method=queryGarageRules" >车库联动规则管理</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplOverhaulManage.do?method=queryOverhaul" >湛江行车检修信息</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/zGWarehouseAction.do?method=zgWarehuoseQuery" >仓库产品形态信息维护</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermitZj.do?method=queryExamine" >湛江临时进厂证审核单管理</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/warehouseLineUpAction.do?method=query" >库区排队明细信息</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/CarArrivePosition.do?method=query" >未到位车辆查询</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/selfExtractionTrain.do?method=query" >自提转铁运查询</a></li>
		</ul>
    </li>    
    
    <li id="show-run"> <img src="images/90.gif" border="0" valign="middle"/>3PL平台运营管理<span id="count-run" class="count"></span> 
    	<ul id="tree-run">
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMenu.do?method=query" target="main">访问量统计</a></li>  
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPlan.do?method=query" target="main">作业计划统计</a></li> 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statStock.do?method=query" target="main">仓储库存量统计</a></li> 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPick.do?method=query" target="main">电子提货单统计</a></li> 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statInvoice.do?method=list" target="main">发票列表统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statInvoice.do?method=statInvoicecount" target="main">发票支付状态比例统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statInvoice2FeeRate.do?method=statInvoice2FeeRate" target="main">结算率统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statModule.do?method=query" target="main">业务模块数据统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMonth.do?method=query" target="main">月报统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMonthDetail.do?method=query" target="main">月报明细统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPickByDate.do?method=query" target="main">按时间统计(电子提单)</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statDisPick.do?method=query"  target="main">违规操作电子提单</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPlanOnTime.do" target="main">计划准点率完成统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickManage.do?method=query" target="main">电子提货单资质管理</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMonth.do?method=epick" target="main">电子提货单月度统计</a></li> 
    		<!-- 目录名称: 车载终端应用统计
    		 -->
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=searchTransTruckStatistics"  target=main>流量统计</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckRoadCodeMgr.do?method=queryRoadCodeList"  target=main>路码表查询</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=transOnTimeStats"  target=main>在途时间统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=transDriverSeqStats"  target=main>驾驶员产值统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=transSeqStats"  target=main>车次信息统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipStatistics.do?method=shipMTotal"  target=main>水运母船批汇总</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipStatistics.do?method=shipMItem"  target=main>水运母船批明细汇总</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooStatistics.do?method=chooTotal"  target=main>铁运车皮汇总</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooStatistics.do?method=chooItem"  target=main>铁运车皮明细汇总</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transPlanStatistics.do?method=transPlanStatistics"  target=main>汽运计划执行率统计</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transPlanStatistics.do?method=transPlanItemStatistics"  target=main>汽运计划执行率明细统计</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../zxc/providerSystemProblem.do?method=searchProviderSystemProblem"  target=main>承运商系统使用问题</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transProblemStatistics.do?method=providerSystemProblemTotal"  target=main>承运商系统使用问题汇总</a></li>
    		<!-- 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceLimit.do?o=list" target="main">税额上限管理</a></li>
    		 -->
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipUpload.do?method=statistics"  target=main>水运到货实绩统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTrains.do?method=list&operate=timeliness"  target=main>铁总数据及时率统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTrains.do?method=list&operate=accuracy"  target=main>铁总数据准确率统计</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statStock.do?method=queryStockReadyBack"  target=main>预合同准发红冲返厂量统计</a></li>
    	</ul>
    </li>
   <li id="show-maintain"><img src="images/90.gif" border="0" valign="middle" />平台维护管理<span id="count-maintain" class="count"></span>
		<ul id="tree-maintain">
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/planfee.do" target="main">按船批刷新抬头</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/feeUpdateRecode.do?method=query" target="main">按作业计划号刷新费用</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/uupBusiness.do?method=query" target="main">仓库授权管理</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/insuranceGenRule.do?method=query" target="main">铁运保单维护管理</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/deletePlan.do" target="main">计划操作</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/procedure.do" target="main">执行存储过程</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/transFeeInvoice.do" target="main">发票撤销电文发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="./maintain/lackStackingQuery.jsp" target="main">船批离港缺少码单查验</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="./maintain/lackUnitPriceQuery.jsp" target="main">运输作业计划缺少费用单价查验</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/MessageSend.do?method=send" target="main">EDI电文发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInPlan.do?method=list" >入库计划发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInGoods.do?method=list" >入库实绩发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendContract.do?method=list" >合同替换发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendLineout.do?method=list" >线下出库发送</a></li>
			
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendTransPlan.do?method=list" target=main>运输作业计划发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlan.do?method=queryTransPlanList&i=0" target=main>汽运作业计划管理</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInvoice.do?method=list" target=main>运费结算发票发送</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInvoiceRed.do?method=list" target=main>运费结算发票红冲发送</a></li>
		</ul>
	 </li>
	
        <li><img src="images/90.gif" border="0" valign="middle" />退出</li>

  
</ul>
</form>
</body>
</html:html>
 <SCRIPT   LANGUAGE="JavaScript">   
  <!--   
<%--  function   KeyDown(){   --%>
<%--      if   ((window.event.altKey)&&((window.event.keyCode==37)||(window.event.keyCode==39))){     --%>
<%--            event.returnValue=false;   --%>
<%--        }   --%>
<%--      if((event.keyCode==116)||(event.ctrlKey   &&   event.keyCode==82)){   --%>
<%--            event.keyCode=0;   --%>
<%--            event.returnValue=false;   --%>
<%--        }   --%>
<%--      if   ((event.ctrlKey)&&(event.keyCode==78)){         --%>
<%--            event.returnValue=false;   --%>
<%--        }   --%>
<%--      if   ((event.shiftKey)&&(event.keyCode==121)){     --%>
<%--            event.returnValue=false;   --%>
<%--        }   --%>
<%--      if   (window.event.srcElement.tagName   ==   "A"   &&   window.event.shiftKey){     --%>
<%--              window.event.returnValue   =   false;     --%>
<%--        }   --%>
<%--      if   ((window.event.altKey)&&(window.event.keyCode==115)){   --%>
<%--              window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px");   --%>
<%--              return   false;   --%>
<%--      }   --%>
<%--  }   --%>
<%--  document.onkeydown=KeyDown;   --%>
<%--    --%>
<%--  function   Click(){   --%>
<%--  window.event.returnValue=false;   --%>
<%--  }   --%>
<%--  document.oncontextmenu=Click;   --%>
  //-->   
  </SCRIPT>
<script language="javascript" type="text/javascript">
<!--
var funconload = window.onload;
function doOnLoad() {
	if(funconload) {
		funconload();
	}
	parent.document.getElementById('carnoc').style.height =parent.document.getElementById('carnoc').parentNode.parentNode.offsetHeight + 'px';
}
window.onload = doOnLoad;
-->
</script>