<%@ page language="java"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/inc/tag.inc"%>
<%@ include file="/inc/constants.inc"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

	<%@ include file="/inc/style.inc"%>
	<%@ include file="/inc/script.inc"%>
	<%@ include file="/inc/calendar.inc"%>
	<title>铁运保单管理</title>

	<script language="javascript">
	
	var sh =  null;
		function deleteRecord()
		{
			var chb =document.getElementsByName('ids');
			var count = 0;
					
			if(chb.length == 0)
			{
				alert("现在没有记录信息，不能进行删除操作");
				return;
			}
			
			for(i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{	
					<%--
					if(insurCode[i].value != ""){
						alert("保单已经回写,不能删除!");
						return false ;
					}	
					--%>
					count ++;
				}
			}
			
					
			if(count == 0)
			{
				alert("请先选择一条记录");
				return;
			}
					
			question = confirm("确定要全部删除所选中的项吗?");
			if(question)
			{				
				document.insuranceManageForm.action='../fee/insuranceTrainManage.do?method=delTrainInsurance';
				var dis = submitForm(document.insuranceManageForm);
				if(dis)
					document.forms[0].delInsuranceButton.disabled=true;
			}
			else
			{
				return;
			}
		}		
		
		  function onlyNum(textValue){
			for(var i=0; i<textValue.length; i++){
			   if(textValue.charAt(i)<'0' || textValue.charAt(i)>'9'){
			     return 0;
			   }
		    }
		    return 1;
		  }
		
		
		function rewriteRecord()
		{
			var temp = '';
			var chb =document.getElementsByName('ids');			
			var insurNoStart = document.getElementsByName('insurIdStart');
			var insurNoEnd = document.getElementsByName('insurIdEnd');
			var insurCode = document.getElementsByName('insurCode');
			
			var count = 0;
			if(chb == null || chb.length == 0)
			{
				alert("现在没有记录信息，不能进行操作");
				return false ;
			}
				
			for(var i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{
					<%--if(insurCode[i].value != ""){
						alert("选中记录的保单号码已经存在，不能回写!");
						return false ;
					}	--%>
					count ++;
				}
			}
			
			if(count == 0)
			{
				alert("请选择一条记录");
				return false ;
			}					
					
			if(insurNoStart[0].value == ''){
		        alert("保单号码起始值为空，请输入!");
		        return false ;
		     }
			if(insurNoEnd[0].value == ''){
		       alert("保单号码结束值为空，请输入!");
		       return false ;
		    }
		     if(insurNoStart[0].value.length>10 || onlyNum(insurNoStart[0].value) == 0){
		       alert("保单号只能为小于10位的纯数字!");
		       return false ;
		    }
		    if(insurNoEnd[0].value.length>10 || onlyNum(insurNoEnd[0].value) == 0){
		       alert("保单号只能为小于10位的纯数字!");
		       return false ;
		    }
		        	
		    var temp = insurNoEnd[0].value - insurNoStart[0].value ;
		        	
		    if(temp < 0){
		       alert("输入的起始值小于结束值，请重新输入!");
		       return false ;
		        	}
		     if(temp == 0){
			   	if(count != 1)
				{
					alert("输入的起始值和结束值范围和选中的项数不符，请重新输入!");
					return false ;
				}
		     }	
		     if(temp > 0){
		        if(count != temp+1)
				{
					alert("输入的起始值和结束值范围和选中的项数不符，请重新输入!");
					return false ;
				}
		     }
		        	
			document.forms[0].action='../fee/insuranceTrainManage.do?method=rewriteInsurance';
			submitForm(document.forms[0]);	
		}
		function downLoadInsur(evt)
		{	
		    var chb =document.getElementsByName('ids');	
		    var count=0;
		    for(var i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{
					count ++;
				}
			}
			
			if(count == 0)
			{
				alert("请选择一条记录");
				return false ;
			}	
			
			if (count > 30)
			{
				alert('您一次最多只能下载30条铁运保单数据。');
				return false;
			}				
			oldAction=document.forms[0].action;		
			document.forms[0].action='../fee/insuranceTrainManage.do?method=downLoadInsurance';
			disabledButton();
			MyPeriodicalExecuter(evt);
			submitForm(document.forms[0]);
			document.forms[0].action=oldAction;
			return true;								
		}
		
		function enabledButton(){
			var inputs = document.getElementsByTagName("input");
			for(var i = 0;i<inputs.length;i++){
				if(inputs[i].type.toLowerCase()=="button" || inputs[i].type.toLowerCase()=="submit")
					inputs[i].disabled=false;
			}
		}
		
		function disabledButton(){
			var inputs = document.getElementsByTagName("input");
			for(var i = 0;i<inputs.length;i++){
				if(inputs[i].type.toLowerCase()=="button" || inputs[i].type.toLowerCase()=="submit")
					inputs[i].disabled=true;
			}
		}
		
		function MyPeriodicalExecuter(evt){
			succ.obj=evt;
			succ.loop=0;
			sh=setInterval(succ,1000);
		}
		
		function succ(){
			var str="禁止连续点击,"
			with(arguments.callee){
				obj.value=str+"("+(loop++)+"/15)秒后再试";
				if (loop > 15){
					enabledButton();
					obj.value=" 下 载 ";
					clearInterval(sh);
					return;
				}	
			}
		}
		
		function updateRecord()
		{
			var chb =document.getElementsByName('ids');
			var insuranceTotalNums =document.getElementsByName('insuranceTotalNumAdjusts');
			var count = 0;
					
			if(chb.length == 0)
			{
				alert("现在没有记录信息，不能进行删除操作");
				return;
			}
			
			for(i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{	
					if(insuranceTotalNums[i].value == ""){
						alert("保险费不能为空,请重新修改!");
						return false ;
					}
					count ++;
				}
			}
			
					
			if(count == 0)
			{
				alert("请先选择一条记录");
				return;
			}
					
			question = confirm("确定要修改所选中的项吗?");
			if(question)
			{				
				document.insuranceManageForm.action='../fee/insuranceTrainManage.do?method=updTrainInsurance';
				document.insuranceManageForm.submit();
				document.insuranceManageForm.updInsuranceButton.disabled=true;
				//var dis = submitForm(document.insuranceManageForm);
				//if(dis)
				//	document.forms[0].updInsuranceButton.disabled=true;
				//return;
			}
			else
			{
				return;
			}
		}	
		
		
		function doMergeInsurance()
		{
			
			var chb =document.getElementsByName('ids');
			var goodBillCode = document.getElementsByName('goodBillCodes');
			var insurIds = document.getElementsByName('insurIds');
			var gdBCodes =new Array();
			var count =0;
			for(i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{
					if(null!=insurIds[i].value && "" !=insurIds[i].value)
					{
						//alert("所选保单中有已回写保单，不能合并！");
						//return false;
					}
					gdBCodes.push(goodBillCode[i].value);
					count ++;
				}
			}
			
			if(count<=1){
				alert("选择合并的保单少于两条！");
				return false;
			}
			if(gdBCodes.length >=2){
				var gdbcode = gdBCodes[0];
				for(a =0; a<gdBCodes.length; a++){
					if(gdbcode != gdBCodes[a]){
						alert("选择保单货票号不一致！");
						return false;
					}
				}
			}
			question = confirm("确定要合并选中的项吗?");
			if(question){
				document.insuranceManageForm.action='../fee/insuranceTrainManage.do?method=doMergeInsurance';
				document.insuranceManageForm.submit();
				document.insuranceManageForm.updInsuranceButton.disabled=true;
			}
		}
		
		var tbodyid = null;
		function querySubInsurance(id){
			tbodyid = id;
			var tbodyDisv = document.getElementById("tbody" + tbodyid);
			// 清空tbody下元素
			if(!tbodyDisv.hasChildNodes()){
				sendForAjax("../fee/insuranceTrainManage.do?method=querySubInsurance","&id="+tbodyid,callReturn);
			}else{
				if(tbodyDisv.style.display=='none') {
					tbodyDisv.style.display='block';
				} else {
					tbodyDisv.style.display='none';
				}
			}
		}
		
		function callReturn()
		{	
			if(XMLHttpReq.readyState==4){ //对象状态
				if(XMLHttpReq.status==200){//信息已成功返回，开始处理信息
					showDetail(XMLHttpReq.responseText);
				} else {
					window.alert("调用失败!");
				}
			}
		}
		
		function showDetail(str){
			var tbodyDisv = document.getElementById("tbody" + tbodyid);
			// 清空tbody下元素
			if (tbodyDisv.hasChildNodes()) {
				tbodyDisv.removeChild(tbodyDisv.lastChild);
			}
			var rowArray = str.split("||");
			for(i = 0; i < rowArray.length; i++) {
				var cellArray = rowArray[i].split("$$");
				var row = tbodyDisv.insertRow();
				for (j = 0; j <= cellArray.length; j++) {
					var cell1 = row.insertCell();
					cell1.align="center";
					cell1.height = "2";
					cell1.valign = "middle";
					cell1.className = "bg_table02";
					if(j<13){
						cell1.innerHTML=cellArray[j];
					}else if(j==13){
						cell1.innerHTML="";
					}
				}
			}
			if(tbodyDisv.style.display=='none') {
				tbodyDisv.style.display='block';
			} else {
				tbodyDisv.style.display='none';
			}
		}
	</script>
</head>

<body leftmargin="0" topmargin="0" class="bg_body"
	onload="Validation.autoBind()">
	<html:form
		action="/fee/insuranceTrainManage.do?method=listTrainInsurance"
		method="post" styleClass="required-validate">
		<table width="96%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td class="a_title01" align="center">
					铁运保单管理
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0">
						<tr>
							<td colspan="4" align="center" class="bg_table02">
								查询条件
							</td>
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									合同号：
								</div>
							</td>
							<td width="35%" class="bg_table02">
								<div align="left">
									<html:text property="orderNum" styleClass="max-length-20"
										alt="合同号" />
								</div>
							</td>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									状态：
								</div>
							</td>
							<td class="bg_table02">
								<div align="left">
									<label>
										<html:select property="rewriteStatus"
											styleClass="max-length-2">
											<html:option value="">全部</html:option>
											<html:option value="0">未回写</html:option>
											<html:option value="1">已回写</html:option>
										</html:select>
									</label>
								</div>
							</td>
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									车皮号：
								</div>
							</td>
							<td class="bg_table02">
								<div align="left">
									<html:text property="trainNo" styleClass="max-length-20"
										alt="车皮号" />
								</div>
							</td>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									货票号：
								</div>
							</td>
							<td width="35%" class="bg_table02">
								<div align="left">
									<html:text property="goodBillCode" styleClass="max-length-20"
										alt="货票号" />
								</div>
							</td>
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									保单号码：
								</div>
							</td>
							<td width="35%" class="bg_table02">
								<div align="left">
									<html:text property="insurId" styleClass="max-length-20"
										alt="保单号码" />
								</div>
							</td>
							<!-- ranqiguang add start 20181107 -->
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									保单类型：
								</div>
							</td>
							<td class="bg_table02" align="left" valign="middle" height="2"
								width="30%">
								<html:select property="isInsuranceM">
								<html:option value="">全部</html:option>
								<html:option value="0">子保单</html:option>
								<html:option value="1">合并保单</html:option>
								</html:select>
							</td>
							<!-- ranqiguang add end 20181107 -->
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									生成日期：
								</div>
							</td>
							<td width="35%" class="bg_table02" colspan="3">
								<div align="left">
									<html:text property="operateDate1" alt="生成起始日期"
										readonly="readonly" />
									<baosight:datepicker buttonName="bdatestart1"
										inputFieldName="operateDate1" format="%Y-%m-%d"
										is24Hours="false" showTime="true" />
									～
									<html:text property="operateDate2" alt="生成结束日期"
										readonly="readonly" />
									<baosight:datepicker buttonName="bdatestart2"
										inputFieldName="operateDate2" format="%Y-%m-%d"
										is24Hours="false" showTime="true" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center" class="bg_table03">
								<label>
									<logic:notEmpty name="searchPermit">
										<logic:equal name="searchPermit" value="yes">
											<html:submit styleClass="button01" value=" 查 询 " />
										</logic:equal>
									</logic:notEmpty>
									<logic:notEmpty name="updatePermit">
										<logic:equal name="updatePermit" value="yes">
											<input type="button" name="updInsuranceButton"
												class="button01" value=" 修 改 " onclick="updateRecord();" />
										</logic:equal>
									</logic:notEmpty>
									<logic:notEmpty name="delPermit">
										<logic:equal name="delPermit" value="yes">
											<input type="button" name="delInsuranceButton"
												class="button01" value=" 删 除 " onclick="deleteRecord();" />
										</logic:equal>
									</logic:notEmpty>
							 		<logic:notEmpty name="doMergeInsurancePermit">
										<logic:equal name="doMergeInsurancePermit" value="yes">
											<input type="button" name="doMergeInsuranceButton"
												class="button01" value=" 合并  " onclick="doMergeInsurance();" />
										</logic:equal>
									</logic:notEmpty>
								</label>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="1"
						align="center">
						<tr>
							<td class="bg_table01" align="center">
								全选
								<br>
								<input type="checkbox" name="selectAll"
									onclick="selectedAll('selectAll','ids')" />
							</td>
							<td class="bg_table01" align="center">
								保单号
							</td>
							<td class="bg_table01" align="center">
								货票号
							</td>
							<td class="bg_table01" align="center">
								合同号
							</td>
							<td class="bg_table01" align="center">
								作业计划号
							</td>
							<td class="bg_table01" align="center">
								件数(件)
							</td>
							<td class="bg_table01" align="center">
								毛重(吨)
							</td>
							<td class="bg_table01" align="center">
								净重(吨)
							</td>
							<td class="bg_table01" align="center">
								保险费(系统值)
							</td>
							<!-- ranqiguang add 20181105 -->
							<td class="bg_table01" align="center">
								保险费(票据值)
							</td>
							<!-- ranqiguang add 20181105 -->
							<td class="bg_table01" align="center">
								收货单位
							</td>
							<td class="bg_table01" align="center">
								回写状态
							</td>
							<td class="bg_table01" align="center">&nbsp;
								
							</td>
						</tr>

						<logic:iterate id="element" name="results" property="results">
							<%
							String status = "";
							%>
							<logic:notEmpty name="element" property="rewriteStatus">
								<bean:define id="sts" name="element" property="rewriteStatus" />
								<%
										status = sts.toString();
										if (status.equalsIgnoreCase("1"))
											status = "已回写";
										else
											status = "未回写";
								%>
							</logic:notEmpty>

							<tr>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<html:multibox property="ids">
										<bean:write name="element" property="id" />
									</html:multibox>
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
										<bean:write name="element" property="insurId" />
										<input type="hidden" name="insurCode"
											value="<bean:write name="element" property="insurId"/>">
										<input type="hidden" name="id"
											value="<bean:write name="element" property="id"/>">
									
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
								
									<!-- ranqiguang add start 20181107-->
									<logic:notEmpty name="element" property="isInsuranceM">
										<logic:equal name="element" property="isInsuranceM" value="1">
											<a href="javascript:;" onclick="querySubInsurance('<bean:write name="element" property="id"/>')">
									    		<bean:write name="element" property="goodBillCode" />
											</a>
										</logic:equal>
										<bean:write name="element" property="insurId" />
									</logic:notEmpty>
									<logic:empty name="element" property="isInsuranceM">
									<!-- ranqiguang add end 20181107-->
										<bean:write name="element" property="goodBillCode" />
									</logic:empty>
									<input type="hidden" name="goodBillCodes" value="<bean:write name="element" property="goodBillCode"/>" />
									<input type="hidden" name="insurIds" value="<bean:write name="element" property="insurId"/>" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="orderNum" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="transPlanId" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="totalCount" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="totalGrossWeight" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="totalNetWeight" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="insuranceTotalNum" />
								</td>
								<!--  ranqiguang add 20181105-->
								<td height="2" align="center" valign="middle" class="bg_table02">
									<input name="insuranceTotalNumAdjusts" type="text" size="16" value="<bean:write name="element" property="insuranceTotalNumAdjust"/>" />
								</td>
								<!--  ranqiguang add 20181105-->
								<td height="2" align="center" valign="middle" class="bg_table02">
									<bean:write name="element" property="consigneeName" />
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<%=status%>
								</td>
								<td height="2" align="center" valign="middle" class="bg_table02">
									<input type="button" value="查看" class="button01"
										onclick="window.open('../fee/insuranceTrainManage.do?method=viewTrainInsurance&id=<bean:write name="element" property="id"/>','');">
								</td>
							</tr>
							<tbody id="tbody<bean:write name="element" property="id"/>"
								style="display: none;">
							</tbody>
						</logic:iterate>
						<%@ include file="/3pl/common/sum.inc"%>
						<tr>
							<td colspan="22" class="bg_table03">
								<table width="100%" border="0" cellspacing="1" cellpadding="0">
									<tr>
										<td align="right" class="bg_table03">
											<baosight:page noForm="false" className="results"
												formName="insuranceManageForm" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" class="bg_table03">
								<logic:notEmpty name="rewritePermit">
									<logic:equal name="rewritePermit" value="yes">
										&nbsp&nbsp&nbsp&nbsp保单号码&nbsp
							<input type="text" name="insurIdStart" Class="max-length-20"
											alt="起始保单号码" />
							&nbsp到&nbsp
							<input type="text" name="insurIdEnd" Class="max-length-20"
											alt="终止保单号码" />
							&nbsp&nbsp&nbsp&nbsp
							<input type="button" class="button01" value=" 回 写 "
											onclick="rewriteRecord();">
									</logic:equal>
								</logic:notEmpty>

								<logic:notEmpty name="downloadPermit">
									<logic:equal name="downloadPermit" value="yes">
										<input type="button" class="button01" value=" 下 载 "
											onclick="downLoadInsur(this);">
									</logic:equal>
								</logic:notEmpty>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="msgInfo">
			<%
			String msgInfo = (String) request.getAttribute("msgInfo");
			%>
			<script language="javascript">
				var msgInfo='<%=msgInfo%>';
				alert(msgInfo);
			</script>

		</logic:notEmpty>
	</html:form>
</body>
</html:html>
