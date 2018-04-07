<%-- $Id: DefineReportDataShipping.jsp,v 1.1.1.1 2006/08/17 09:33:27 mori Exp $ --%>
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
          <TD noWrap><h:tab id="tab_RptClmSet" templateKey="W_RptClm_SetShp"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_Back" templateKey="Back"/>&nbsp;&nbsp; 
          </TD>
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
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Valid1" templateKey="W_Valid"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_DigitsUseLength1" templateKey="W_DigitsUseLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLength1" templateKey="W_MaxLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Position1" templateKey="W_Position"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Valid2" templateKey="W_Valid"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_DigitsUseLength2" templateKey="W_DigitsUseLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLength2" templateKey="W_MaxLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Position2" templateKey="W_Position"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ShippingPlanDate" templateKey="W_ShippingPlanDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseShpPlanDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpPlanDateLen" templateKey="W_ShpPlanDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetShpPlanDate" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpPlanDatePst" templateKey="W_ShpPlanDatePst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ShpPlanQtyPtl" templateKey="W_ShpPlanQtyPtl"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseShpPlanPtl" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpPlanQtyPtlLen" templateKey="W_ShpPlanQtyPtlLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetShpPlanPtl" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpPlanQtyPtlPst" templateKey="W_ShpPlanQtyPtlPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_OrderDate" templateKey="W_OrderDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseOdd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_OdrgDateLen" templateKey="W_OdrgDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetOdd" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_OdrgDatePst" templateKey="W_OdrgDatePst"/></TD>
                <TD 
                  bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_TCDCFlag" templateKey="W_TCDCFlag"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseTCDC" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TCDCFlagLength" templateKey="W_TCDCFlagLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetTCDC" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TCDCFlagPosition" templateKey="W_TCDCFlagPosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCnsgnrCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdLen" templateKey="W_CnsgnrCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetCnsgnrCd" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdPst" templateKey="W_CnsgnrCdPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_SupplierCode" templateKey="W_SupplierCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseSplCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplCdLen" templateKey="W_SplCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetSplCd" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplCdPst" templateKey="W_SplCdPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCnsgnrNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmLen" templateKey="W_CnsgnrNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetCnsgnrNm" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmPst" templateKey="W_CnsgnrNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_SupplierName" templateKey="W_SupplierName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseSplNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplNmLen" templateKey="W_SplNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetSpl" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplNmPstNm" templateKey="W_SplNmPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerCode" templateKey="W_CustomerCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCustCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdLen" templateKey="W_CustCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetCustCd" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdPst" templateKey="W_CustCdPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstockTicketNo" templateKey="W_InstockTicketNo"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseInstkTkt" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktNoLen" templateKey="W_InstkTktNoLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetInstkTkt" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktNoPst" templateKey="W_InstkTktNoPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCustNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmLen" templateKey="W_CustNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetCustNm" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmPst" templateKey="W_CustNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstkTktLineNo" templateKey="W_InstkTktLineNo"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseInstkTktLine" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktLineNoLen" templateKey="W_InstkTktLineNoLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetInstkTktLine" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktLineNoPst" templateKey="W_InstkTktLineNoPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TicketNo" templateKey="W_TicketNo"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseTktNo" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TicketNoLength" templateKey="W_TicketNoLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetTktNo" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TicketNoPosition" templateKey="W_TicketNoPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ShpQtyPtl" templateKey="W_ShpQtyPtl"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseShpPtl" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpQtyPtlLen" templateKey="W_ShpQtyPtlLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetShpPtl" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpQtyPtlPst" templateKey="W_ShpQtyPtlPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TicketLineNo" templateKey="W_TicketLineNo"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseTktLineNo" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktLineNoLen" templateKey="W_TktLineNoLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetTktLineNo" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktLineNoPst" templateKey="W_TktLineNoPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ShippingResultDate" templateKey="W_ShippingResultDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseShpRslt" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpRsltDateLen" templateKey="W_ShpRsltDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetShpRslt" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpRsltDatePst" templateKey="W_ShpRsltDatePst"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_TktLineNoPstItemCo" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCodeLength" templateKey="W_ItemCodeLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetItemCo" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCodePosition" templateKey="W_ItemCodePosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ResultFlag" templateKey="W_ResultFlag"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseRsltFlg" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ResultFlagLength" templateKey="W_ResultFlagLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetRsltFlg" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ResultFlagPosition" templateKey="W_ResultFlagPosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleItf" templateKey="W_BundleItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseBdlItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfLength" templateKey="W_BundleItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetBdlItf" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfPosition" templateKey="W_BundleItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_UseByDate" templateKey="W_UseByDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseUseByDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_UseByDateLength" templateKey="W_UseByDateLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetUseByDate" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_UseByDatePosition" templateKey="W_UseByDatePosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseItf" templateKey="W_CaseItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCaseItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfLength" templateKey="W_CaseItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetCaseItf" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfPosition" templateKey="W_CaseItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseBdlEtr" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BdlEtrLen" templateKey="W_BdlEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetBdlEtr" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BdlEtrPst" templateKey="W_BdlEtrPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseEntering" templateKey="W_CaseEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCaseEtr" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrLen" templateKey="W_CaseEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetCaseEtr" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrPst" templateKey="W_CaseEtrPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_W_CommonUseItemNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNameLength" templateKey="W_ItemNameLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_JavaSetItemNm" templateKey="W_JavaSet"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNamePosition" templateKey="W_ItemNamePosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Submit" templateKey="Submit"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD>&nbsp;&nbsp;&nbsp; </TD>
                <TD><h:submitbutton id="btn_Clear" templateKey="Clear"/></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD>
	</TR>
	</TBODY>
	</TABLE>
	</TD>
	</TR>
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
    height=8>
  </TD>
  </TR>
  </TBODY>
  </TABLE>
	</TD>
	</TR>
	</TBODY>
	</TABLE></h:page>
</h:html>
