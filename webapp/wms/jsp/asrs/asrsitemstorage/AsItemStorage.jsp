<%-- $Id: AsItemStorage.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:14 $
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
		if (targetElement == "btn_P_StrgPlanSrch")
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
          <TD noWrap><h:tab id="tab_Set" templateKey="W_Set"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>

  <%-- upper display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_WorkerCode" templateKey="W_WorkerCode"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee colSpan=3><h:freetextbox id="txt_WorkerCode" templateKey="W_WorkerCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_PassWord" templateKey="W_Password"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Password" templateKey="W_Password"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=5>&nbsp;</TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_WareHouse" templateKey="W_WareHouse"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee><h:pulldown id="pul_WareHouse" templateKey="W_WareHouse"/></TD>
                <TD bgColor=#dad9ee><h:label id="lbl_Zone" templateKey="W_Zone"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:linkedpulldown id="pul_Zone" templateKey="W_Zone"/></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=5>&nbsp;&nbsp;</TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_WorkPlace" templateKey="W_WorkPlace"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee><h:linkedpulldown id="pul_WorkPlace" templateKey="W_WorkPlace"/></TD>
                <TD bgColor=#dad9ee><h:label id="lbl_Station" templateKey="W_Station"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:linkedpulldown id="pul_Station" templateKey="W_Station"/></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=5></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/><h:submitbutton id="btn_PSearchConsignorCode" templateKey="P_Search"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>&nbsp;</TD>
                <TD noWrap width=8 bgColor=#b8b7d7 height=3></TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StoragePlanDate" templateKey="W_StoragePlanDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:datetextbox id="txt_StoragePlanDate" templateKey="W_StoragePlanDate"/><h:submitbutton id="btn_PSearchStoragePlanDate" templateKey="P_Search"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD noWrap width=8 bgColor=#b8b7d7 height=3></TD>
                <TD bgColor=#b8b7d7 
              colSpan=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_ItemCode" templateKey="W_ItemCode"/><h:submitbutton id="btn_PSearchItemCode" templateKey="P_Search"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD noWrap width=8 bgColor=#b8b7d7 height=3></TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD width="100%" bgColor=#b8b7d7>&nbsp;</TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CasePieceFlag" templateKey="W_CasePieceFlag"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=4><h:radiobutton id="rdo_CpfAll" templateKey="W_Cpf_All"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_Cpf_Case" templateKey="W_Cpf_Case"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_Cpf_Piece" templateKey="W_Cpf_Piece"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_Cpf_AppointOff" templateKey="W_Cpf_AppointOff"/></TD>
                <TD noWrap width=8 bgColor=#b8b7d7 height=3></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=2></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=4><h:submitbutton id="btn_P_StrgPlanSrch" templateKey="W_P_StrgPlanSrch"/></TD>
                <TD noWrap width=8 bgColor=#b8b7d7 height=3></TD>
                <TD width="100%" bgColor=#b8b7d7 colSpan=2></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=4></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD noWrap width=8 bgColor=#dad9ee height=3></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=4></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD noWrap width=8 bgColor=#dad9ee height=3></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageCaseQty" templateKey="W_StrageCaseQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:numbertextbox id="txt_StrgCaseQty" templateKey="W_StrgCaseQty"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_CaseEntering" templateKey="W_CaseEntering"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:label id="lbl_JavaSetCaseEntering" templateKey="W_JavaSet"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_RestCaseQty" templateKey="W_RestCaseQty"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:label id="lbl_JavaSetZanCaseQty" templateKey="W_JavaSet"/></TD>
                <TD bgColor=#b8b7d7 colSpan=2>&nbsp;</TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StoragePieceQty" templateKey="W_StragePieseQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:numbertextbox id="txt_StrgPieceQty" templateKey="W_StrgPieseQty"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:label id="lbl_JavaSetBundleEntering" templateKey="W_JavaSet"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_RestPieceQty" templateKey="W_RestPieceQty"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:label id="lbl_JavaSetZanPiece" templateKey="W_JavaSet"/> </TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UseByDate" templateKey="W_UseByDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=8><h:freetextbox id="txt_UseByDate" templateKey="W_UseByDate"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Idmschstwoli" templateKey="W_Idmschstwoli"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=8><h:checkbox id="chk_CommonUse" templateKey="W_CommonUse"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Input" templateKey="W_Input"/></TD>
                <TD></TD>
                <TD></TD>
                <TD>&nbsp;&nbsp; </TD>
                <TD></TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_Clear" templateKey="Clear"/>&nbsp;</TD></TR></TBODY></TABLE></TD>
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
                <TD><h:submitbutton id="btn_StorageStart" templateKey="W_StorageStart"/></TD>
                <TD>&nbsp;&nbsp; </TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_AllCancel" templateKey="W_ListClear"/></TD>
                <TD></TD>
                <TD>&nbsp;&nbsp;&nbsp;</TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_ItemStorage" templateKey="W_ItemStorage"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
