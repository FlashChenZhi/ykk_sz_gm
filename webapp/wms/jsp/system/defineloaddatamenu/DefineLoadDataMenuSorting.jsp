<%-- $Id: DefineLoadDataMenuSorting.jsp,v 1.1.1.1 2006/08/17 09:33:27 mori Exp $ --%>
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
          <TD noWrap><h:tab id="tab_LodClmSetPick" templateKey="W_LodClm_SetPick"/></TD>
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
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_PickPlanDateReq" templateKey="W_PickPlanDateReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NPickPlanDateReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PickPlanDateReqLen" templateKey="W_PickPlanDateReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenPickPlanDateReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PickPlanDateReqPst" templateKey="W_PickPlanDateReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NItemNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmLen" templateKey="W_ItemNameLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmPst" templateKey="W_ItemNamePosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdLen" templateKey="W_CnsgnrCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrCd" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdPst" templateKey="W_CnsgnrCdPst"/></TD>
                <TD 
                  bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_PickingQtyPtlReq" templateKey="W_PickingQtyPtlReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NPickQtyPtlReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PickQtyPtlLenReq" templateKey="W_PickQtyPtlReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenPickQtyPtlReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PickQtyPtlReqPst" templateKey="W_PickQtyPtlReqPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_ConsignorName" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmLen" templateKey="W_CnsgnrNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenConsignorName" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmPst" templateKey="W_CnsgnrNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_PicePickPlaceReq" templateKey="W_PicePickPlaceReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NPiecePickPlaceReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PiecePickPlaceReqLen" templateKey="W_PicePkPlaceReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenPiecePickPlaceReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PiecePickPlaceReqPst" templateKey="W_PicePkPlaceReqPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustCdReq" templateKey="W_CustCdReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NCustCdReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdReqLen" templateKey="W_CustCdReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCustCdReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustCdReqPst" templateKey="W_CustCdReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_CasePickingPlace" templateKey="W_CasePickingPlace"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CasePickPlacee" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CasePickPlaceLen" templateKey="W_CasePickPlaceLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCasePickPlace" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CasePickPlacePst" templateKey="W_CasePickPlacePst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CustNm" templateKey="W_CustomerName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NCustNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmLen" templateKey="W_CustNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCustNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CustNmPst" templateKey="W_CustNmPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_CrossDCRequired" templateKey="W_CrossDCRequired"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NCrossDCFlagReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CrossDCFlagReqLen" templateKey="W_CrsDCFlgReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCrossDCFlagReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CrossDCFlagPst" templateKey="W_CrossDCFlagPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ShippingTicketNo" templateKey="W_ShippingTicketNo"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_ShpTktNo" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpTktNoLen" templateKey="W_ShpTktNoLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenShpTktNo" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpTktNoPst" templateKey="W_ShpTktNoPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_SupplierCodeAst" templateKey="W_SupplierCodeAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_SupplierCdAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierCdAstLen" templateKey="W_SplCdAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSupplierCdAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierCdAstPst" templateKey="W_SplCdAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ShpTktLineNo" templateKey="W_ShpTktLineNo"/></TD>
                <TD align=middle balign="center" gColor="#b8b7d7"><h:checkbox id="chk_ShpTktLineNo" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpTktLineNoLen" templateKey="W_ShpTktLineNoLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenShpTktLineNo" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ShpTktLineNoPst" templateKey="W_ShpTktLineNoPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_SupplierName" templateKey="W_SupplierName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_SupplierNmAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierNmAstLen" templateKey="W_SplNmAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenSupplierNmAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_SupplierNmAstPst" templateKey="W_SplNmAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCodeRequired" templateKey="W_ItemCodeRequired"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NItemCdReq" templateKey="W_CommonNotUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdReqLen" templateKey="W_ItemCdReqLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemCdReq" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdReqPst" templateKey="W_ItemCdReqPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstockTicketNoAst" templateKey="W_InstockTicketNoAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_InstkTktNoAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktNoAstLen" templateKey="W_InstkTktNoAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInstkTktNoAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktNoAstPst" templateKey="W_InstkTktNoAstPst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleItf" templateKey="W_BundleItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_BundleItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfLen" templateKey="W_BundleItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleItf" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleItfPst" templateKey="W_BundleItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_InstkTktLineNoAst" templateKey="W_InstkTktLineNoAst"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_InstkTktLineNoAst" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_InstkTktLineNoAstLen" templateKey="W_InstkTktLineAstLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenInstkTktLineNoAst" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7>&nbsp;<h:numbertextbox id="txt_InstkTktLineNoAstPst" templateKey="W_InstkTktLineAstPst"/>&nbsp;</TD></TR>
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
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_BundleEntering" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEnteringLen" templateKey="W_BdlEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleEntering" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEnteringPst" templateKey="W_BdlEtrPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7 colSpan=5><h:label id="lbl_CrossTCRequiredAst" templateKey="W_CrossTCRequiredAst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseEtrReq" templateKey="W_CaseEtrReq"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_NCaseEtrReq" templateKey="W_CommonNotUse"/></TD>
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
