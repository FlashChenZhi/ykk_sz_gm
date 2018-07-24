<%-- $Id: WorkMaintenance.jsp,v 1.1 2007/12/13 06:25:11 administrator Exp $ --%>
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
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:11 $
 * @author  $Author: administrator $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
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
          <TD><h:label id="lbl_SettingName" templateKey="YKK_LBL_WorkMaintenance"/></TD>
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
          <TD noWrap><h:tab id="tab" templateKey="YKK_TAB_Maintenance"/></TD>
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
              <TR bgColor=#dad9ee height=5>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TransferType" templateKey="YKK_LBL_TransferType"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:radiobutton id="rdo_StockinAndStockout" templateKey="YKK_RDO_StockinAndStockoutChecked"/><h:radiobutton id="rdo_Stockin" templateKey="YKK_RDO_Stockin"/><h:radiobutton id="rdo_StockoutSTtoST" templateKey="YKK_RDO_StockoutSTtoST"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Station" templateKey="YKK_LBL_Station"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_Station" templateKey="YKK_PUL_Station"/><h:radiobutton id="rdo_FinalStation" templateKey="YKK_RDO_FinalStationChecked"/><h:radiobutton id="rdo_CurrentStation" templateKey="YKK_RDO_CurrentStation"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show_POPUP"/></TD>
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
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_PickOut" templateKey="YKK_BTN_PickOut"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_WorkEnd" templateKey="YKK_BTN_WorkEnd"/></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_TransferStartStation" templateKey="YKK_LBL_TransferStartStation"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_TransferStartStation" templateKey="YKK_TXT_TransferStartStation_RO"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img\common\icon_arrow.gif" border=0></TD>
                      <TD><h:label id="lbl_TransferDestinationStation" templateKey="YKK_LBL_TransferDestinationStation"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_TransferDestinationStation" templateKey="YKK_TXT_TransferDestinationStation_RO"/><h:submitbutton id="btn_STBreakOffStart" templateKey="YKK_BTN_STBreakOffStart"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_MCKEY" templateKey="YKK_LBL_MCKEY"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_MCKEY" templateKey="YKK_TXT_MCKEY_RO"/></TD>
                      <TD></TD>
                      <TD><h:label id="lbl_LocationNo" templateKey="YKK_LBL_LocationNo"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:formattextbox id="txt_LocationNo" templateKey="YKK_TXT_LocationNo_RO"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_TransferType1" templateKey="YKK_LBL_TransferType"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_TransferType" templateKey="YKK_TXT_TransferType_RO"/></TD>
                      <TD></TD>
                      <TD><h:label id="lbl_OrderDetail" templateKey="YKK_LBL_OrderDetail"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_OrderDetail" templateKey="YKK_TXT_OrderDetail_RO"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_TransferStatus" templateKey="YKK_LBL_TransferStatus"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_TransferStatus" templateKey="YKK_TXT_TransferStatus_RO"/></TD>
                      <TD></TD>
                      <TD><h:label id="lbl_BucketNo" templateKey="YKK_LBL_BucketNo"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_BucketNo" templateKey="YKK_TXT_BucketNo_RO"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_ColorCode" templateKey="YKK_LBL_ColorCode"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_ColorCode" templateKey="YKK_TXT_ColorCode_RO"/></TD>
                      <TD></TD>
                      <TD><h:label id="lbl_TicketNo" templateKey="YKK_LBL_TicketNo"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_TicketNo" templateKey="YKK_TXT_TicketNo_RO"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_ItemCode" templateKey="YKK_LBL_ItemNo"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_ItemCode" templateKey="YKK_TXT_ItemNo_RO"/></TD>
                      <TD></TD>
                      <TD><h:label id="lbl_InstockCount" templateKey="YKK_LBL_InstockCount"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:numbertextbox id="txt_InstockCount" templateKey="YKK_TXT_InstockCount_RO"/></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD></TD>
                      <TD><h:freetextbox id="txt_ItemName" templateKey="YKK_TXT_ItemName_RO"/></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_StockoutRetrievalInfo" templateKey="YKK_LBL_StockoutRetrievalInfo"/></TD></TR>
                    <TR>
                      <TD><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD></TR>
                    <TR>
                      <TD><h:listcell id="lst_WorkMaintenance" templateKey="YKK_LST_WorkMaintenance"/></TD></TR>
                    <TR>
                      <TD><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
