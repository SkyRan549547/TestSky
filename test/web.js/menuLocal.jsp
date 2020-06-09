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
  <!--ϵͳ����Begin-->
 <li id="show-system"> <img src="images/90.gif" border="0" valign="middle"/>�������ݹ���<span id="count-system" class="count"></span> 
    <ul id="tree-system">
      
    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/ownerInfo.do?method=list">ί�л�����Ϣ����</a></li>	
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/BreedMgr.do?method=list">Ʒ����Ϣ����</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/FinalUser.do?method=search">�����û���Ϣ����</a></li>
	  <!--<li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/feePlan.do?method=list">���ʹ���</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/prodPlaceMgr.do">������Ϣ����</a></li>-->
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/transStationInq.do">����վ�����</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplRProviderWarehouse.do?method=queryRProviderWarehouse">�ֿ���Ϣ����</a></li>	
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/warehouseAutoManage.do?method=query">�ֿ��Զ���������</a></li>	
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplTransSeq.do?method=queryTransSeq">������Ϣ����</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/MessageLog.do">������Ϣ����</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickPromisee.do?method=query">�����������ӡȨ������</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickChapter.do?method=query">���������ר������Ȩ��</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickLaw.do?method=query">���˴�����Ȩί����</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickWare.do?method=query">����������ֿ����Э��</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickManage.do?method=query">������������ʹ���</a></li>
	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/traceMgr.do?method=main">���ߺ������</a></li>
	</ul>
  </li>  

  
   <li id="show-storage"> <img src="images/90.gif" border="0" valign="middle"/>�ִ�ҵ�����<span id="count-storage" class="count"></span> 
    <ul id="tree-storage">
	  <li id="show-stockinfo"><img src="images/90.gif" border="0" valign="middle"/>�ֿ�����Ԥ��
      	<ul id="tree-stockinfo">
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/wproviderAbility.do?method=queryRProviderWarehouse" target=main>�ִ�����Ԥ��</a></li>
	  </ul>
   	  </li>

      <li id="show-in"><img src="images/90.gif" border="0" valign="middle"/>������
      	<ul id="tree-in">
      	      	<li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInPlan.do?method=list" >�����ҵ�ƻ�����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInManage.do?method=listinstoreplanmgr" >������</a></li>
			  <li><span class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInManage.do?method=InStoreList">���ȷ��</a></span></li>
			  <li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockInFollows.do?method=list">������</a></li>
			 
			  
        </ul>
   	  </li>
      <li id="show-out"><img src="images/90.gif" border="0" valign="middle"/>�������
      	
      	<ul id="tree-out">
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutPlan.do?method=list" >������ҵ�ƻ�����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutMgr.do?method=list" target=main>�������</a> </li>
			  <li><span class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutSure.do?method=list" target=main>����ȷ��</a></span></li>
			  <li><span class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutSure.do?method=listcheck" class="a" target=main>������֤</a></span></li>			  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockOutSure.do?method=listtrack" class="a" target=main>�������</a></li>
        </ul>
   	  </li>
      <li id="show-stock"><img src="images/90.gif" border="0" valign="middle"/>������
      		<ul id="tree-stock">
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=search" class="a" target=main>����ѯ</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=searchStockForMove" class="a" target=main>��λ���</a></li>
			  <li><span class="last"><img src="images/90.gif" alt="a" border="0" valign="middle"/><a href="../stock/stockCheck.do?method=search" class="a" target=main>����̵���ҵ����</a></span></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockAdjust.do?method=queryStockAdjust" class="a" target=main>�ƿ����</a></li>
			  <!--<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=takeStock" class="a" target=main>����������̿�</a></li>
			  <li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=adminSearch" class="a" target=main>������</a></li>-->
			  
			  <li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../stock/stockSearch.do?method=stockOtherPage" class="a" target=main>������λ��ѯ</a></li>
	      </ul>
   	  </li>
	  
  </ul>
  </li>   
  
  <li id="show-transport"> <img src="images/90.gif" border="0" valign="middle"/>����ҵ�����<span id="count-transport" class="count"></span>      
    <ul id="tree-transport">
      <li id="show-ship"><img src="images/90.gif" border="0" valign="middle"/>ˮ��ҵ�����
      <span id="count-ship" class="count"></span> 
        <ul id="tree-ship">
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/shipAbility.do?method=queryTransShip"  target=main>ˮ������Ԥ��</a></li> 
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipPlan.do"  target=main>ˮ����ҵ�ƻ�����</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplTransSeq.do?method=queryShipManager"  target=main>������Ϣ����</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=listM"  target=main>װ�۶�̬����</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipResult.do"  target=main>ˮ�˷���Ԥ����</a></li>  
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=list"  target=main>װ��ȷ�Ϲ���</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipTrace.do?method=queryTransShip">��;��̬����</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipUpload.do"  target=main>����ʵ������</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipPortFee.do"  target=main>�۽��ѹ���</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=listPrint"  target=main>װ����̱����</a></li>  
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoad.do?method=listTransCurrentPack"  target=main>ˮ�˲��ϴ�����Ϣ</a></li>
		</ul>
      </li>   
      <li id="show-train"><img src="images/90.gif" border="0" valign="middle"/>����ҵ�����
      <span id="count-train" class="count"></span> 
        <ul id="tree-train">
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooPlan.do?method=queryTransPlan"  target=main>������ҵ�ƻ�����</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooLoad.do?method=queryTransChooSeq"  target=main>����װ������</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooUpload.do?method=queryTransChooUpload"  target=main>���˵���ȷ�Ϲ���</a></li>       
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooResult.do?method=queryTransChooResult"  target=main>���˳�Ƥ����</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/goodBillInq.do"  target=main>���˻�Ʊ����</a></li>
		</ul>
      </li>  
      <li id="show-car"><img src="images/90.gif" border="0" valign="middle"/>����ҵ�����
      <span id="count-car" class="count"></span> 
        <ul id="tree-car">
          <li id="show-storeinvoice"><img src="images/90.gif" border="0" valign="middle"/>տ������ҵ�����
			  <span id="count-storeinvoice" class="count"></span> 
				<ul id="tree-storeinvoice">					
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckPlanBind"  target=main>˾��������</a></li>
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=transMaterialManageQuery"  target=main>���ز�����ѡ</a></li> 
		  			<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=queryTransTruckMaterial"  target=main>����������ѯ</a></li> 
		  			<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckDisPlan"  target=main>����������ѯ</a></li> 
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckSeq.do?method=query"  target=main>���γ�������</a></li> 
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlan.do?method=queryDispatch"  target=main>�����������</a></li> 
				</ul>
		  </li> 	
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlan.do?method=queryTransPlan&i=0"  target=main>������ҵ�ƻ�����</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=chooseFont"  target=main>����װ������</a></li><!-- 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckPlanBind"  target=main>˾��������</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=transMaterialManageQuery"  target=main>���ز�����ѡ</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transMaterialManage.do?method=queryTransTruckMaterial"  target=main>����������ѯ</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=transTruckDisPlan"  target=main>����������ѯ</a></li> 
		  --><li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckLoad.do?method=queryTransTruckSeq"  target=main>���˷�������</a></li> 
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckUpload.do?method=queryTransTruckUpload"  target=main>���˵���ȷ�Ϲ���</a></li> 
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckResult.do?method=queryTransTruckResult&type=load"  target=main>����ʵ����ѯ</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/TransTruckSignMgr.do?method=transFortruckSignInfo"  target=main>����ǩ�չ���(����)</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/TransTruckSignMgr.do?method=querySignInfoListForRun"  target=main>����ǩ�չ���(˾��)</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=searchFirst"  target=main>���˵��ȹ���</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=dispatchTrack"  target=main>���˵��ȸ���</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=searchTransTruckStatistics"  target=main>���˳�������ͳ��</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/TransTruckSignMgr.do?method=queryAbnormalList"  target=main>����������Ϲ���</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckRoadCodeMgr.do?method=queryRoadCodeList"  target=main>����·����ѯ</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../3pl/map.do?method=map"  target=_blank>��ͼ��λ</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=flySearch"  target=main>����ת��Ȩҵ�����</a></li>
		  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckDisPlan.do?method=flyPresentationSearch"  target=main>����ת��Ȩҵ�񽻵�</a></li>
			  
		 
		</ul>
      </li> 	
      <li id="show-ecwl"><img src="images/90.gif" border="0" valign="middle"/>����ҵ�����
      <span id="count-ecwl" class="count"></span> 
        <ul id="tree-ecwl"> 
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlanMtm.do?method=queryTransPlan&i=0"  target=main>�������˼ƻ�����</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipLoadMtm.do?method=queryShipManager"  target=main>���̴�������</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipPlanMtmInq.do"  target=main>����ˮ�˼ƻ�����</a></li>
          <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooPlanMtm.do?method=queryTransPlan"  target=main>�������˼ƻ�����</a></li>   
        </ul>
      </li>
			
    </ul>
  </li>  
  
   <li id="show-invoice"><img src="images/90.gif" border="0" valign="middle"/>���ý������
		<ul id="tree-invoice">
		  <li id="show-storeinvoice"><img src="images/90.gif" border="0" valign="middle"/>�ִ����ù���
			  <span id="count-storeinvoice" class="count"></span> 
				<ul id="tree-storeinvoice">					
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/stockInvoiceGenerate.do?method=list" target=main>�ִ���Ʊ����</a></li>
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/stockInvoiceMgr.do?method=list" target=main>�ִ���Ʊ����</a></li>
					<li class="last"><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/stockInvoiceMgr.do?method=listsearch" target=main>�ִ���Ʊ��ѯ</a></li>									
				</ul>
			</li> 		
			
			<li id="show-transinvoice"><img src="images/90.gif" border="0" valign="middle"/>������ù���
			  <span id="count-transinvoice" class="count"></span> 
				<ul id="tree-transinvoice">					
					<li id="show-insurance"><img src="images/90.gif" border="0" valign="middle"/>��������
					  <span id="count-insurance" class="count"></span> 
						<ul id="tree-insurance">
							<li id="show-shipinsurance"><img src="images/90.gif" border="0" valign="middle"/>ˮ�˱�������
							<span id="count-shipinsurance" class="count"></span> 
								<ul id="tree-shipinsurance">
								<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/insurancePickInq.do"  target=main>��������</a></li>
								<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/insuranceShipManage.do"  target=main>��������</a></li> 		
								</ul>
							</li> 
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/insuranceTrainManage.do" target="main">���˱�������</a></li>
						</ul>
					</li>         
					
					<li id="show-invoicedetail"><img src="images/90.gif" border="0" valign="middle"/>��Ʊ����
					  <span id="count-invoicedetail" class="count"></span> 
						<ul id="tree-invoicedetail">
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceGenerate.do?method=list"  target=main>�˷ѷ�Ʊ����</a></li>										
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceMgr.do?method=list"  target=main>�˷ѷ�Ʊ����</a></li>			
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceMgr.do?method=listsearch"  target=main>�˷ѷ�Ʊ��ѯ</a></li>
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceGenerate.do?method=shiplist"  target=main>ˮ��������Ʊ</a></li> 							
					 		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInsuranceMgr.do?method=list"  target=main>������Ʊ����</a></li> 							
							<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInsuranceMgr.do?method=listsearch"  target=main>������Ʊ��ѯ</a></li> 							
					 		    
						</ul>
					</li> 					
					
					<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/goodBillInq.do" target="main">���˻�Ʊ����</a></li>
				</ul>
			</li> 		
			<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceTitleChange.do?method=list" target="main">��Ʊ̧ͷ���</a></li>
			<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceTitleUser.do?method=list" target="main">��Ʊ̧ͷ������˾����</a></li>
			<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/transFeeInvoiceGenerate.do?method=settleTrack&op=init" target="main">���н������</a></li>
        </ul>
	</li>

    <li id="show-supplier"> <img src="images/90.gif" border="0" valign="middle"/>����������Դ����<span id="count-supplier" class="count"></span> 
		<ul id="tree-supplier">
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/feeRate.do" target="main">�����˼۲�ѯ</a></li>   
			 <!-- <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/transportAgreementInq.do" target="main">����Э�����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/stockAgreementInq.do" target="main">�ִ�Э�����</a></li>          
			  -->
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/carAction.do?method=list" target="main">�������Ϲ���</a></li>  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/shipInq.do?method=list">�������Ϲ���</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/shipInq.do?method=queryShip">����������Ϣ����</a></li> 
			  <!--<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/transStation.do?method=list" target="main">����վ���ѯ</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/transportInq.do" target="main">����·�߲�ѯ</a></li>       			    
			  -->
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/summary.do" target="main">������С�ᱨ��</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/evaluationInq.do" target="main">���������۲�ѯ</a></li>
			  
			  <li id="show-providerBaseInfoMgr"><img src="images/90.gif" border="0" valign="middle"/>�����̻�����Ϣ����
				<span id="count-providerBaseInfoMgr" class="count"></span> 
				<ul id="tree-providerBaseInfoMgr">
					<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRProvider.do?method=queryProviderBaseInfo&operate=providerInq"  target=main>�����̻�����Ϣ����</a></li>
					<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRProvider.do?method=queryTplRProvider&operate=providerInq"  target=main>�����̿����������</a></li> 		
				</ul>
			  </li> 
			  			        
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRProvider.do?method=queryTplRProvider&operate=providerMisConfig" target="main">����������ϵͳ�Խ����ù���</a></li>         
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/tplRDocument.do?method=queryTplRDocument" target="main">���ӹ��Ĺ���</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/modifyApply.do?method=queryModifyApplyInfo" target="main">������Դ��Ϣ�޸��������</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/rProductPrice.do?method=list" target="main">���۵�������</a></li> 
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/esignBsteelComEdocsignLogin.do" target="main">USB_KEY����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/DriverManage.do?method=search" target="main">˾���ƶ��ն˹���</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/DriverManage.do?method=shipLineM" target="main">hang xian guanli </a></li>
			  <!--<li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/MobileTerminalManage.do?method=search" target="main">�����ƶ��ն˹���</a></li>-->
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/DriverManage.do?method=search" target="main">�����ƶ��ն˹���</a></li>
			  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplHeadstockManage.do?method=search" target="main">��ͷ����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplTrailerManage.do?method=search" target="main">�ҳ�����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplVehDistributionManage.do?method=search" target="main">�����������</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/TplDestinationManage.do?method=search" target="main">Ŀ�ĵع���</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermit.do?method=query" target="main">��������֤����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermitZj.do?method=query" target="main">տ������֤����</a></li>
			   <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermitZj.do?method=queryTemporary" target="main">տ����ʱ����֤����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/infocard.do?method=query" target="main">˾����Ϣ������</a></li>
			  
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceLimit.do?o=list" target="main">��ֵ˰��Ʊ�޶����</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceLimit.do?o=number" target="main">˰����Ϣ��ѯ</a></li>
			  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../useproblem/Viewuseproblem.do"  target=main>������ϵͳʹ������</a></li>
		   	  <li><img src="images/90.gif" border="0" valign="middle"/><a href="../useproblem/Viewuseproblem.do?method=useProblemTotal"  target=main>������ϵͳʹ���������</a></li> 
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendContract.do?method=searchList" >��ͬ�滻�鿴</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/tplPMobileQuality.do?method=list" target="main">ˮ��������Ȩ</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInGoods.do?method=list" >���ʵ������</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transWarehouseRules.do?method=queryWarehouseRules" >�ֿ�������</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transGarageRules.do?method=queryGarageRules" >���������������</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/tplOverhaulManage.do?method=queryOverhaul" >տ���г�������Ϣ</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/zGWarehouseAction.do?method=zgWarehuoseQuery" >�ֿ��Ʒ��̬��Ϣά��</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../entry/entryPermitZj.do?method=queryExamine" >տ����ʱ����֤��˵�����</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/warehouseLineUpAction.do?method=query" >�����Ŷ���ϸ��Ϣ</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/CarArrivePosition.do?method=query" >δ��λ������ѯ</a></li>
		      <li><img src="images/90.gif" border="0" valign="middle"/><a href="../supplier/selfExtractionTrain.do?method=query" >����ת���˲�ѯ</a></li>
		</ul>
    </li>    
    
    <li id="show-run"> <img src="images/90.gif" border="0" valign="middle"/>3PLƽ̨��Ӫ����<span id="count-run" class="count"></span> 
    	<ul id="tree-run">
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMenu.do?method=query" target="main">������ͳ��</a></li>  
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPlan.do?method=query" target="main">��ҵ�ƻ�ͳ��</a></li> 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statStock.do?method=query" target="main">�ִ������ͳ��</a></li> 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPick.do?method=query" target="main">���������ͳ��</a></li> 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statInvoice.do?method=list" target="main">��Ʊ�б�ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statInvoice.do?method=statInvoicecount" target="main">��Ʊ֧��״̬����ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statInvoice2FeeRate.do?method=statInvoice2FeeRate" target="main">������ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statModule.do?method=query" target="main">ҵ��ģ������ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMonth.do?method=query" target="main">�±�ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMonthDetail.do?method=query" target="main">�±���ϸͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPickByDate.do?method=query" target="main">��ʱ��ͳ��(�����ᵥ)</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statDisPick.do?method=query"  target="main">Υ����������ᵥ</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statPlanOnTime.do" target="main">�ƻ�׼�������ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../base/epickManage.do?method=query" target="main">������������ʹ���</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statMonth.do?method=epick" target="main">����������¶�ͳ��</a></li> 
    		<!-- Ŀ¼����: �����ն�Ӧ��ͳ��
    		 -->
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=searchTransTruckStatistics"  target=main>����ͳ��</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckRoadCodeMgr.do?method=queryRoadCodeList"  target=main>·����ѯ</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=transOnTimeStats"  target=main>��;ʱ��ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=transDriverSeqStats"  target=main>��ʻԱ��ֵͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckStatistics.do?method=transSeqStats"  target=main>������Ϣͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipStatistics.do?method=shipMTotal"  target=main>ˮ��ĸ��������</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipStatistics.do?method=shipMItem"  target=main>ˮ��ĸ������ϸ����</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooStatistics.do?method=chooTotal"  target=main>���˳�Ƥ����</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transChooStatistics.do?method=chooItem"  target=main>���˳�Ƥ��ϸ����</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transPlanStatistics.do?method=transPlanStatistics"  target=main>���˼ƻ�ִ����ͳ��</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transPlanStatistics.do?method=transPlanItemStatistics"  target=main>���˼ƻ�ִ������ϸͳ��</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../zxc/providerSystemProblem.do?method=searchProviderSystemProblem"  target=main>������ϵͳʹ������</a></li>
		    <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transProblemStatistics.do?method=providerSystemProblemTotal"  target=main>������ϵͳʹ���������</a></li>
    		<!-- 
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../fee/invoiceLimit.do?o=list" target="main">˰�����޹���</a></li>
    		 -->
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transShipUpload.do?method=statistics"  target=main>ˮ�˵���ʵ��ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTrains.do?method=list&operate=timeliness"  target=main>�������ݼ�ʱ��ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTrains.do?method=list&operate=accuracy"  target=main>��������׼ȷ��ͳ��</a></li>
    		<li><img src="images/90.gif" border="0" valign="middle"/><a href="../run/statStock.do?method=queryStockReadyBack"  target=main>Ԥ��ͬ׼����巵����ͳ��</a></li>
    	</ul>
    </li>
   <li id="show-maintain"><img src="images/90.gif" border="0" valign="middle" />ƽ̨ά������<span id="count-maintain" class="count"></span>
		<ul id="tree-maintain">
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/planfee.do" target="main">������ˢ��̧ͷ</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/feeUpdateRecode.do?method=query" target="main">����ҵ�ƻ���ˢ�·���</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/uupBusiness.do?method=query" target="main">�ֿ���Ȩ����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/insuranceGenRule.do?method=query" target="main">���˱���ά������</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/deletePlan.do" target="main">�ƻ�����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/procedure.do" target="main">ִ�д洢����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/transFeeInvoice.do" target="main">��Ʊ�������ķ���</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="./maintain/lackStackingQuery.jsp" target="main">�������ȱ���뵥����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="./maintain/lackUnitPriceQuery.jsp" target="main">������ҵ�ƻ�ȱ�ٷ��õ��۲���</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle" /><a href="../maintain/MessageSend.do?method=send" target="main">EDI���ķ���</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInPlan.do?method=list" >���ƻ�����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInGoods.do?method=list" >���ʵ������</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendContract.do?method=list" >��ͬ�滻����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendLineout.do?method=list" >���³��ⷢ��</a></li>
			
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendTransPlan.do?method=list" target=main>������ҵ�ƻ�����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../transport/transTruckPlan.do?method=queryTransPlanList&i=0" target=main>������ҵ�ƻ�����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInvoice.do?method=list" target=main>�˷ѽ��㷢Ʊ����</a></li>
			 <li><img src="images/90.gif" border="0" valign="middle"/><a href="../maintain/sendInvoiceRed.do?method=list" target=main>�˷ѽ��㷢Ʊ��巢��</a></li>
		</ul>
	 </li>
	
        <li><img src="images/90.gif" border="0" valign="middle" />�˳�</li>

  
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