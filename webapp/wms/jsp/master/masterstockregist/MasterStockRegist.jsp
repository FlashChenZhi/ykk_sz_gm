<%-- $Id: MasterStockRegist.jsp,v 1.1.1.1 2006/08/17 09:33:20 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:20 $
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
		if (targetElement == "btn_Pstocksearch")
		{
			if (!_isValueInput('txt_ConsignorCode')) 
			{
				document.all('txt_ConsignorCode').focus();
				// 6023004=׎R[h͂ĂB
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
          <TD noWrap><h:tab id="tab_Regist" templateKey="W_Regist"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- upper display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkerCode" templateKey="W_WorkerCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_WorkerCode" templateKey="W_WorkerCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_Password" templateKey="W_Password"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Password" templateKey="W_Password"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=5></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD></TD>
                <TD></TD>
                <TD colSpan=3></TD>
                <TD noWrap width=8 bgColor=#7371ab height=3></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/><h:submitbutton id="btn_PsearchConsignorCode" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/><h:submitbutton id="btn_PsearchItemCode" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD>&nbsp;&nbsp;<h:submitbutton id="btn_Pstocksearch" templateKey="W_P_StockSearch"/></TD>
                <TD width="100%"></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_ItemName" templateKey="W_ItemName"/></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CasePieceFlag" templateKey="W_CasePieceFlag"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:radiobutton id="rdo_CpfCase" templateKey="W_Cpf_Case"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CpfPiece" templateKey="W_Cpf_Piece"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CpfAppointOff" templateKey="W_Cpf_AppointOff"/></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Location" templateKey="W_Location"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_Location" templateKey="W_Location"/><h:submitbutton id="btn_PsearchLocation" templateKey="P_Search"/></TD>
                <TD></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD></TD>
                <TD></TD>
                <TD colSpan=3></TD>
                <TD noWrap width=8 bgColor=#7371ab height=3></TD>
                <TD noWrap width=3 bgColor=#7371ab height=3></TD>
                <TD width="100%"></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseEntering" templateKey="W_CaseEntering"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEntering" templateKey="W_CaseEntering"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_StockCaseQty" templateKey="W_StockCaseQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_StcCaseQty" templateKey="W_StcCaseQty"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_CaseItf" templateKey="W_CaseItf"/></TD>
                <TD bgColor=#b8b7d7 colSpan=5><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_CaseItf" templateKey="W_CaseItf"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEntering" templateKey="W_BundleEntering"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:label id="lbl_StockPieceQty" templateKey="W_StockPieceQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_StcPieceQty" templateKey="W_StcPieseQty"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_BundleItf" templateKey="W_BundleItf"/></TD>
                <TD bgColor=#b8b7d7 colSpan=5><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_BundleItf" templateKey="W_BundleItf"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageDate" templateKey="W_StorageDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:datetextbox id="txt_StorageDate" templateKey="W_StorageDate"/>&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="btn_StrageTime" templateKey="W_StrageTime"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:timetextbox id="txt_StorageTime" templateKey="W_StorageTime"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; <h:label id="lbl_UseByDate" templateKey="W_UseByDate"/></TD>
                <TD bgColor=#b8b7d7 colSpan=5><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_UseByDate" templateKey="W_UseByDate"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Input" templateKey="W_Input"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="Clear"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE>
      <TABLE>
        <TBODY>
        <TR>
          <TD height=5></TD></TR></TBODY></TABLE>
  <%-- lower display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_urt3.gif" width=42 border=0></TD>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Submit" templateKey="Submit"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ListClear" templateKey="W_ListClear"/></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>&nbsp;</TD></TR>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp; </TD>
                <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_SStockRegist" templateKey="W_S_StockRegist"/></TD></TR></TBODY></TABLE>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
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
