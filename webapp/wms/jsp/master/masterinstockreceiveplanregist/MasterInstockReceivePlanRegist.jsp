<%-- $Id: MasterInstockReceivePlanRegist.jsp,v 1.1.1.1 2006/08/17 09:33:19 mori Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * 
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/02/13 N.Sawa     created
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:19 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
  <script>
	function dynamicAction()
	{
		if (targetElement == "btn_PInstkPlanSrch")
		{
			if (!_isValueInput('txt_ConsignorCode')) 
			{
				document.all('txt_ConsignorCode').focus();
				// 6023004=荷主コードを入力してください。
				_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("6023004") %>',2);
				return true;
			}
		}
		
		return false;
		
	}
  </script>
</h:head>
<h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
  <TBODY>
  <TR>
    <TD>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24><h:linkbutton id="btn_Help" templateKey="Help"/> 
          </TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE>
  <%-- message --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4></TD></TR>
        <TR>
          <TD width=7 height=23></TD>
          <TD width=4 bgColor=white height=23></TD>
          <TD bgColor=white height=23><h:message id="message" templateKey="OperationMsg"/></TD>
          <TD width=7 height=23></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE>
  <%-- tab --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG height=2 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
      border=0>
        <TBODY>
        <TR height=28>
          <TD width=7><IMG src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7></TD>
          <TD width=7></TD>
          <TD noWrap><h:tab id="tab_BscDtlRst" templateKey="W_Bsc_DtlRst"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkerCode" templateKey="W_WorkerCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_WorkerCode" templateKey="W_WorkerCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_Password" templateKey="W_Password"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Password" templateKey="W_Password"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=8 bgColor=#7371ab height=3></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/><h:submitbutton id="btn_PSearchConsignorCode" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorName" templateKey="W_ConsignorName"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_InstockPlanDate" templateKey="W_InstockPlanDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:datetextbox id="txt_InstockPlanDate" templateKey="W_InstockPlanDate"/><h:submitbutton id="btn_PSearchInstockPlanDate" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SupplierCode" templateKey="W_SupplierCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_SupplierCode" templateKey="W_SupplierCode"/><h:submitbutton id="btn_PSearchSupplierCode" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SupplierName" templateKey="W_SupplierName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_SupplierName" templateKey="W_SupplierName"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:submitbutton id="btn_PInstkPlanSrch" templateKey="W_P_InstkPlanSrch"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TCDCFlag" templateKey="W_TCDCFlag"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:radiobutton id="rdo_CrossDCFlagTC" templateKey="W_Instock_CrossDCFlag_TC"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CrossDCFlagCross" templateKey="W_Instock_CrossDCFlag_Cross"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CrossDCFlagDC" templateKey="W_Instock_CrossDCFlag_DC"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerCode" templateKey="W_CustomerCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_CustomerCode" templateKey="W_CustomerCode"/><h:submitbutton id="btn_PSearchCustomerCode" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TicketNo" templateKey="W_TicketNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_TicketNo" templateKey="W_TicketNo"/><h:submitbutton id="btn_PSearchTicketNo" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD noWrap width=8 bgColor=#7371ab height=3></TD>
                <TD noWrap width=3 bgColor=#7371ab 
            height=3></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Next" templateKey="Next"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="Clear"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD>
<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#dad9ee height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#dad9ee height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD>
          <TD 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
