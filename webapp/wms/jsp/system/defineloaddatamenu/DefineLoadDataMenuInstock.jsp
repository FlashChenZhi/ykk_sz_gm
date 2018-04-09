<%-- $Id: DefineLoadDataMenuInstock.jsp,v 1.1.1.1 2006/08/17 09:33:27 mori Exp $ --%>
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
          height=24><h:linkbutton id="btn_Help" templateKey="Help"/></TD>
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
          <TD noWrap><h:tab id="tab_LodClm_SetInstk" templateKey="W_LodClm_SetInstk"/></TD>
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
                <TD bgColor=#b8b7d7><h:label id="lbl_ValidLeft1" templateKey="W_Valid"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_DigitsUseLength1" templateKey="W_DigitsUseLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLength1" templateKey="W_MaxLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Position1" templateKey="W_Position"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Valid2" templateKey="W_Valid"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_DigitsUseLength2" templateKey="W_DigitsUseLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLength2" templateKey="W_MaxLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_Position2" templateKey="W_Position"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_InstkPlanDateReq" templateKey="W_InstkPlanDateReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NInstockPlanDtReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkPlanDtReqLen" templateKey="W_InstkPnDtReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInstkPlanDtReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkPlanDtReqPst" templateKey="W_InstkPnDtReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_BundleEtr" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEtrLen" templateKey="W_BdlEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleEtr" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEtrPst" templateKey="W_BdlEtrPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_OrderDate" templateKey="W_OrderDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_OrderDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_OrderDateLen" templateKey="W_OdrgDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenOrderDate" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_OrderDatePst" templateKey="W_OdrgDatePst"/></TD>
                <TD 
                  bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_CaseEtrReq" templateKey="W_CaseEtrReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NCaseEtrReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrReqLen" templateKey="W_CaseEtrReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseEtrReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrReqPst" templateKey="W_CaseEtrReqPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdLen" templateKey="W_CnsgnrCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrCd" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdPst" templateKey="W_CnsgnrCdPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NItemNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmLen" templateKey="W_ItemNameLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmPst" templateKey="W_ItemNamePosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmLen" templateKey="W_CnsgnrNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmPst" templateKey="W_CnsgnrNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstkQtyPtlReq" templateKey="W_InstkQtyPtlReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NInstkQtyPtlReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkQtyPtlLenReq" templateKey="W_InstkQtyPtlReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInstkQtyPtlReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkQtyPtlReqPst" templateKey="W_InstkQtyPtlReqPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SplCdReq" templateKey="W_SplCdReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NSupplierCdReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierCdReqLen" templateKey="W_SplCdReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSupplierCdReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierCdReqPst" templateKey="W_SplCdReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_TCDCRequired" templateKey="W_TCDCRequired"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NTCDCFlgReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TCDCFlgReqLen" templateKey="W_TCDCFlgReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenTCDCFlgReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TCDCFlgReqPst" templateKey="W_TCDCFlgReqPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_SplName" templateKey="W_SupplierName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NSupplierNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierNmLen" templateKey="W_SplNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSupplierNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierNmPst" templateKey="W_SplNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_CustomerCodeAst" templateKey="W_CustomerCodeAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CustCdAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdAstLen" templateKey="W_CustCdAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCustCdAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdAstPst" templateKey="W_CustCdAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TicketNoRequired" templateKey="W_TicketNoRequired"/></TD>
                <TD align=middle balign="center" gColor="#b8b7d7"><h:checkbox id="chk_NTktNoReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktNoReqLen" templateKey="W_TktNoReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenTktNoReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktNoReqPst" templateKey="W_TktNoReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CustNmAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmAstLen" templateKey="W_CustNmAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCustNmAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmAstPst" templateKey="W_CustNmAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TktLineNoReq" templateKey="W_TktLineNoReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NTktLineNoReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktLineNoReqLen" templateKey="W_TktLineNoReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenTktLineNoReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktLineNoReqPst" templateKey="W_TktLineNoReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCodeRequired" templateKey="W_ItemCodeRequired"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NItemCdReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdReqLen" templateKey="W_ItemCdReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemCdReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdReqPst" templateKey="W_ItemCdReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_TCRequiredAst" templateKey="W_TCRequired"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleItf" templateKey="W_BundleItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_BundleItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfLen" templateKey="W_BundleItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleItf" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfPst" templateKey="W_BundleItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseItf" templateKey="W_CaseItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CaseItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfLen" templateKey="W_CaseItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseItf" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfPst" templateKey="W_CaseItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Submit" templateKey="Submit"/>&nbsp;&nbsp; 
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
