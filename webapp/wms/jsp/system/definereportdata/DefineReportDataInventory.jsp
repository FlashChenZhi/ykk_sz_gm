<%-- $Id: DefineReportDataInventory.jsp,v 1.1.1.1 2006/08/17 09:33:27 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:27 $
 * @author  $Author: mori $
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
          <TD noWrap><h:tab id="tab_RptClmSetInvntry" templateKey="W_RptClm_SetInvntry"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_Back" templateKey="Back"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
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
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Valid" templateKey="W_Valid"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_DigitsUseLength" templateKey="W_DigitsUseLength"/>&nbsp;&nbsp; </TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLen" templateKey="W_MaxLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Position" templateKey="W_Position"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_ConsignorCd" templateKey="W_ConsignorCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdLen" templateKey="W_CnsgnrCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrCd" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdPst" templateKey="W_CnsgnrCdPst"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_ConsignorNm" templateKey="W_ConsignorName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmLen" templateKey="W_CnsgnrNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmPst" templateKey="W_CnsgnrNmPst"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_ItemCd" templateKey="W_ItemCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_ItemCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdLen" templateKey="W_ItemCodeLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemCd" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdPst" templateKey="W_ItemCodePosition"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_ItemNm" templateKey="W_ItemName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_ItemNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmLen" templateKey="W_ItemNameLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmPst" templateKey="W_ItemNamePosition"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_CaseEntering" templateKey="W_CaseEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CaseEtr" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrLen" templateKey="W_CaseEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseEtr" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrPst" templateKey="W_CaseEtrPst"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_SystemStockQty" templateKey="W_SystemStockQty"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_SysStockQtyPtl" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SysStockQtyPtlLen" templateKey="W_SysStockQtyPtlLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSysStockQtyPtl" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SysStockQtyPtlPos" templateKey="W_SysStockQtyPtlPos"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_InvntryRsltQtyPtl" templateKey="W_InvntryRsltQtyPtl"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_InvntRsltQtyPtl" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InvntRsltQtyPtlLen" templateKey="W_InvntRsltQtyPtlLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInvntRsltQtyPtl" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InvntRsltQtyPtlPst" templateKey="W_InvntRsltQtyPtlPos"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_InventoryLct" templateKey="W_InventoryLocation"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_InvntryLct" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InvntryLctLen" templateKey="W_InvntryLctLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInvntryLct" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InvntryLctPst" templateKey="W_InvntryLctPos"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_InvntryRsltDate" templateKey="W_InvntryRsltDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_InvntryRsltDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InvntryRsltDateLen" templateKey="W_InvntryRsltDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInvntryRsltDate" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InvntryRsltDatePst" templateKey="W_InvntryRsltDatePst"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD align=left bgColor=#b8b7d7><h:label id="lbl_UseByDate" templateKey="W_UseByDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_UseByDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_UseByDateLen" templateKey="W_UseByDateLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenUseByDate" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_UseByDatePst" templateKey="W_UseByDatePosition"/></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Submit" templateKey="Submit"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_Clear" templateKey="Clear"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
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
