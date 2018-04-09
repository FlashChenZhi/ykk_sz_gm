<%-- $Id: DefineLoadDataMenuShipping.jsp,v 1.1.1.1 2006/08/17 09:33:27 mori Exp $ --%>
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
          <TD noWrap><h:tab id="tab_LodClm_SetShp" templateKey="W_LodClm_SetShp"/></TD>
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
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ShpPlanDateReq" templateKey="W_ShpPlanDateReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseShpPDateReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpPlanDateReqLen" templateKey="W_ShpPlanDateReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenShpPlanDateReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpPlanDateReqPst" templateKey="W_ShpPlanDateReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseItemName" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmLen" templateKey="W_ItemNameLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemName" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmPst" templateKey="W_ItemNamePosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_OrderDate" templateKey="W_OrderDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseOrderDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_OdrgDateLen" templateKey="W_OdrgDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenOrderDate" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_OdrgDatePst" templateKey="W_OdrgDatePst"/></TD>
                <TD 
                  bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ShpQtyPtlReq" templateKey="W_ShpQtyPtlReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseShpQtyPtlReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpQtyPtlLenReq" templateKey="W_ShpQtyPtlLenReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenShpQtyPtlReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpQtyPtlPstReq" templateKey="W_ShpQtyPtlPstReq"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseConsignorCode" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdLen" templateKey="W_CnsgnrCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenConsignorCode" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdPst" templateKey="W_CnsgnrCdPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_TCDCRequired" templateKey="W_TCDCRequired"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseTCDCRequired" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TCDCFlgReqLen" templateKey="W_TCDCFlgReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenTCDCRequired" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TCDCFlgReqPst" templateKey="W_TCDCFlgReqPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseConsignorName" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmLen" templateKey="W_CnsgnrNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenConsignorName" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmPst" templateKey="W_CnsgnrNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_SupplierCodeAst" templateKey="W_SupplierCodeAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseSplCodeAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplCdAstLen" templateKey="W_SplCdAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSupplierCodeAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplCdAstPst" templateKey="W_SplCdAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustCdReq" templateKey="W_CustCdReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseCustCdReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdReqLen" templateKey="W_CustCdReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCustCdReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdReqPst" templateKey="W_CustCdReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_SupplierName" templateKey="W_SupplierName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseSplNameAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplNmAstLen" templateKey="W_SplNmAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSupplierNameAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SplNmAstPst" templateKey="W_SplNmAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustomerName" templateKey="W_CustomerName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCustNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmLen" templateKey="W_CustNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCustNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmPst" templateKey="W_CustNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstockTicketNoAst" templateKey="W_InstockTicketNoAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseInstkTktNoAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktNoAstLen" templateKey="W_InstkTktNoAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInstockTicketNoAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktNoAstPst" templateKey="W_InstkTktNoAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TicketNoRequired" templateKey="W_TicketNoRequired"/></TD>
                <TD align=middle balign="center" gColor="#b8b7d7"><h:checkbox id="chk_CommonNotUseTicketNoRequir" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktNoReqLen" templateKey="W_TktNoReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenTicketNoRequired" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_TktNoReqPst" templateKey="W_TktNoReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstkTktLineNoAst" templateKey="W_InstkTktLineNoAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseInstkTktLNoAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktLineAstLen" templateKey="W_InstkTktLineAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInstkTktLineNoAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktLineAstPst" templateKey="W_InstkTktLineAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TktLineNoReq" templateKey="W_TktLineNoReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseTktLineNoReq" templateKey="W_CommonNotUse"/></TD>
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
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseItemCodeRequir" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdReqLen" templateKey="W_ItemCdReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemCodeRequired" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdReqPst" templateKey="W_ItemCdReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_CrossTCRequiredAst" templateKey="W_CrossTCRequiredAst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleItf" templateKey="W_BundleItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseBundleItem" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfLength" templateKey="W_BundleItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleItem" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfPosition" templateKey="W_BundleItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseItf" templateKey="W_CaseItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseCaseItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfLength" templateKey="W_CaseItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseItf" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfPosition" templateKey="W_CaseItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonUseBundleEntering" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BdlEtrLen" templateKey="W_BdlEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleEntering" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BdlEtrPst" templateKey="W_BdlEtrPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseEtrReq" templateKey="W_CaseEtrReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CommonNotUseCaseEtrReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrReqLen" templateKey="W_CaseEtrReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseEtrReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrReqPst" templateKey="W_CaseEtrReqPst"/></TD>
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
