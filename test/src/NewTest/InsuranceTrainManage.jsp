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
	<title>���˱�������</title>

	<script language="javascript">
	
	var sh =  null;
		function deleteRecord()
		{
			var chb =document.getElementsByName('ids');
			var count = 0;
					
			if(chb.length == 0)
			{
				alert("����û�м�¼��Ϣ�����ܽ���ɾ������");
				return;
			}
			
			for(i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{	
					<%--
					if(insurCode[i].value != ""){
						alert("�����Ѿ���д,����ɾ��!");
						return false ;
					}	
					--%>
					count ++;
				}
			}
			
					
			if(count == 0)
			{
				alert("����ѡ��һ����¼");
				return;
			}
					
			question = confirm("ȷ��Ҫȫ��ɾ����ѡ�е�����?");
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
				alert("����û�м�¼��Ϣ�����ܽ��в���");
				return false ;
			}
				
			for(var i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{
					<%--if(insurCode[i].value != ""){
						alert("ѡ�м�¼�ı��������Ѿ����ڣ����ܻ�д!");
						return false ;
					}	--%>
					count ++;
				}
			}
			
			if(count == 0)
			{
				alert("��ѡ��һ����¼");
				return false ;
			}					
					
			if(insurNoStart[0].value == ''){
		        alert("����������ʼֵΪ�գ�������!");
		        return false ;
		     }
			if(insurNoEnd[0].value == ''){
		       alert("�����������ֵΪ�գ�������!");
		       return false ;
		    }
		     if(insurNoStart[0].value.length>10 || onlyNum(insurNoStart[0].value) == 0){
		       alert("������ֻ��ΪС��10λ�Ĵ�����!");
		       return false ;
		    }
		    if(insurNoEnd[0].value.length>10 || onlyNum(insurNoEnd[0].value) == 0){
		       alert("������ֻ��ΪС��10λ�Ĵ�����!");
		       return false ;
		    }
		        	
		    var temp = insurNoEnd[0].value - insurNoStart[0].value ;
		        	
		    if(temp < 0){
		       alert("�������ʼֵС�ڽ���ֵ������������!");
		       return false ;
		        	}
		     if(temp == 0){
			   	if(count != 1)
				{
					alert("�������ʼֵ�ͽ���ֵ��Χ��ѡ�е���������������������!");
					return false ;
				}
		     }	
		     if(temp > 0){
		        if(count != temp+1)
				{
					alert("�������ʼֵ�ͽ���ֵ��Χ��ѡ�е���������������������!");
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
				alert("��ѡ��һ����¼");
				return false ;
			}	
			
			if (count > 30)
			{
				alert('��һ�����ֻ������30�����˱������ݡ�');
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
			var str="��ֹ�������,"
			with(arguments.callee){
				obj.value=str+"("+(loop++)+"/15)�������";
				if (loop > 15){
					enabledButton();
					obj.value=" �� �� ";
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
				alert("����û�м�¼��Ϣ�����ܽ���ɾ������");
				return;
			}
			
			for(i=0; i < chb.length; i++)
			{
				if(chb[i].checked)
				{	
					if(insuranceTotalNums[i].value == ""){
						alert("���շѲ���Ϊ��,�������޸�!");
						return false ;
					}
					count ++;
				}
			}
			
					
			if(count == 0)
			{
				alert("����ѡ��һ����¼");
				return;
			}
					
			question = confirm("ȷ��Ҫ�޸���ѡ�е�����?");
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
						//alert("��ѡ���������ѻ�д���������ܺϲ���");
						//return false;
					}
					gdBCodes.push(goodBillCode[i].value);
					count ++;
				}
			}
			
			if(count<=1){
				alert("ѡ��ϲ��ı�������������");
				return false;
			}
			if(gdBCodes.length >=2){
				var gdbcode = gdBCodes[0];
				for(a =0; a<gdBCodes.length; a++){
					if(gdbcode != gdBCodes[a]){
						alert("ѡ�񱣵���Ʊ�Ų�һ�£�");
						return false;
					}
				}
			}
			question = confirm("ȷ��Ҫ�ϲ�ѡ�е�����?");
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
			// ���tbody��Ԫ��
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
			if(XMLHttpReq.readyState==4){ //����״̬
				if(XMLHttpReq.status==200){//��Ϣ�ѳɹ����أ���ʼ������Ϣ
					showDetail(XMLHttpReq.responseText);
				} else {
					window.alert("����ʧ��!");
				}
			}
		}
		
		function showDetail(str){
			var tbodyDisv = document.getElementById("tbody" + tbodyid);
			// ���tbody��Ԫ��
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
					���˱�������
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0">
						<tr>
							<td colspan="4" align="center" class="bg_table02">
								��ѯ����
							</td>
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									��ͬ�ţ�
								</div>
							</td>
							<td width="35%" class="bg_table02">
								<div align="left">
									<html:text property="orderNum" styleClass="max-length-20"
										alt="��ͬ��" />
								</div>
							</td>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									״̬��
								</div>
							</td>
							<td class="bg_table02">
								<div align="left">
									<label>
										<html:select property="rewriteStatus"
											styleClass="max-length-2">
											<html:option value="">ȫ��</html:option>
											<html:option value="0">δ��д</html:option>
											<html:option value="1">�ѻ�д</html:option>
										</html:select>
									</label>
								</div>
							</td>
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									��Ƥ�ţ�
								</div>
							</td>
							<td class="bg_table02">
								<div align="left">
									<html:text property="trainNo" styleClass="max-length-20"
										alt="��Ƥ��" />
								</div>
							</td>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									��Ʊ�ţ�
								</div>
							</td>
							<td width="35%" class="bg_table02">
								<div align="left">
									<html:text property="goodBillCode" styleClass="max-length-20"
										alt="��Ʊ��" />
								</div>
							</td>
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									�������룺
								</div>
							</td>
							<td width="35%" class="bg_table02">
								<div align="left">
									<html:text property="insurId" styleClass="max-length-20"
										alt="��������" />
								</div>
							</td>
							<!-- ranqiguang add start 20181107 -->
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									�������ͣ�
								</div>
							</td>
							<td class="bg_table02" align="left" valign="middle" height="2"
								width="30%">
								<html:select property="isInsuranceM">
								<html:option value="">ȫ��</html:option>
								<html:option value="0">�ӱ���</html:option>
								<html:option value="1">�ϲ�����</html:option>
								</html:select>
							</td>
							<!-- ranqiguang add end 20181107 -->
						</tr>
						<tr>
							<td width="15%" align="center" class="bg_table02">
								<div align="right">
									�������ڣ�
								</div>
							</td>
							<td width="35%" class="bg_table02" colspan="3">
								<div align="left">
									<html:text property="operateDate1" alt="������ʼ����"
										readonly="readonly" />
									<baosight:datepicker buttonName="bdatestart1"
										inputFieldName="operateDate1" format="%Y-%m-%d"
										is24Hours="false" showTime="true" />
									��
									<html:text property="operateDate2" alt="���ɽ�������"
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
											<html:submit styleClass="button01" value=" �� ѯ " />
										</logic:equal>
									</logic:notEmpty>
									<logic:notEmpty name="updatePermit">
										<logic:equal name="updatePermit" value="yes">
											<input type="button" name="updInsuranceButton"
												class="button01" value=" �� �� " onclick="updateRecord();" />
										</logic:equal>
									</logic:notEmpty>
									<logic:notEmpty name="delPermit">
										<logic:equal name="delPermit" value="yes">
											<input type="button" name="delInsuranceButton"
												class="button01" value=" ɾ �� " onclick="deleteRecord();" />
										</logic:equal>
									</logic:notEmpty>
							 		<logic:notEmpty name="doMergeInsurancePermit">
										<logic:equal name="doMergeInsurancePermit" value="yes">
											<input type="button" name="doMergeInsuranceButton"
												class="button01" value=" �ϲ�  " onclick="doMergeInsurance();" />
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
								ȫѡ
								<br>
								<input type="checkbox" name="selectAll"
									onclick="selectedAll('selectAll','ids')" />
							</td>
							<td class="bg_table01" align="center">
								������
							</td>
							<td class="bg_table01" align="center">
								��Ʊ��
							</td>
							<td class="bg_table01" align="center">
								��ͬ��
							</td>
							<td class="bg_table01" align="center">
								��ҵ�ƻ���
							</td>
							<td class="bg_table01" align="center">
								����(��)
							</td>
							<td class="bg_table01" align="center">
								ë��(��)
							</td>
							<td class="bg_table01" align="center">
								����(��)
							</td>
							<td class="bg_table01" align="center">
								���շ�(ϵͳֵ)
							</td>
							<!-- ranqiguang add 20181105 -->
							<td class="bg_table01" align="center">
								���շ�(Ʊ��ֵ)
							</td>
							<!-- ranqiguang add 20181105 -->
							<td class="bg_table01" align="center">
								�ջ���λ
							</td>
							<td class="bg_table01" align="center">
								��д״̬
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
											status = "�ѻ�д";
										else
											status = "δ��д";
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
									<input type="button" value="�鿴" class="button01"
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
										&nbsp&nbsp&nbsp&nbsp��������&nbsp
							<input type="text" name="insurIdStart" Class="max-length-20"
											alt="��ʼ��������" />
							&nbsp��&nbsp
							<input type="text" name="insurIdEnd" Class="max-length-20"
											alt="��ֹ��������" />
							&nbsp&nbsp&nbsp&nbsp
							<input type="button" class="button01" value=" �� д "
											onclick="rewriteRecord();">
									</logic:equal>
								</logic:notEmpty>

								<logic:notEmpty name="downloadPermit">
									<logic:equal name="downloadPermit" value="yes">
										<input type="button" class="button01" value=" �� �� "
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
