<%-- $Id: DefineReportDataNoPlanStorage.jsp,v 1.1.1.1 2006/08/17 09:33:27 mori Exp $ --%>
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
          <TD noWrap><h:tab id="tab_RptClm_SetNoPlanStrg" templateKey="W_RptClm_SetNoPlanStrg"/></TD>
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
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdLen" templateKey="W_CnsgnrCdLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrCd" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrCdPst" templateKey="W_CnsgnrCdPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_StorageResultDate" templateKey="W_StorageResultDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_StrgRsltDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_StrgRsltDateLen" templateKey="W_StrgRsltDateLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenStrgRsltDate" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_StrgRsltDatePst" templateKey="W_StrgRsltDatePst"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorName" templateKey="W_ConsignorName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CnsgnrNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmLen" templateKey="W_CnsgnrNmLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCnsgnrNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CnsgnrNmPst" templateKey="W_CnsgnrNmPst"/></TD>
                <TD 
                  bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_UseByDate" templateKey="W_UseByDate"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_UseByDate" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_UseByDateLen" templateKey="W_UseByDateLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenUseByDate" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_UseByDatePst" templateKey="W_UseByDatePosition"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="W_ItemCode"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_ItemCd" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdLen" templateKey="W_ItemCodeLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemCd" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemCdPst" templateKey="W_ItemCodePosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
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
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseItf" templateKey="W_CaseItf"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CaseItf" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfLen" templateKey="W_CaseItfLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseItf" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseItfPst" templateKey="W_CaseItfPosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_BundleEntering" templateKey="W_BundleEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_BundleEtr" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEtrLen" templateKey="W_BdlEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenBundleEtr" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_BundleEtrPst" templateKey="W_BdlEtrPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseEntering" templateKey="W_CaseEntering"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CaseEtr" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrLen" templateKey="W_CaseEtrLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseEtr" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseEtrPst" templateKey="W_CaseEtrPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="W_ItemName"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_ItemNm" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmLen" templateKey="W_ItemNameLength"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenItemNm" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_ItemNmPst" templateKey="W_ItemNamePosition"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_PiceStrgLct" templateKey="W_PiceStrgLct"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_PieceStrgLct" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PieceStrgLctLen" templateKey="W_PiceStrgLctLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenPieceStrgLct" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_PieceStrgLctPst" templateKey="W_PiceStrgLctPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CaseStrgLct" templateKey="W_CaseStrgLct"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_CaseStrgLct" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseStrgLctLen" templateKey="W_CaseStrgLctLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenCaseStrgLct" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_CaseStrgLctPst" templateKey="W_CaseStrgLctPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ResultPieceQtyTwoByte" templateKey="W_ResultPieceQtyTwoByte"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_RsltPieceQty" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_RsltPieceQtyLen" templateKey="W_RsltPiceQtyLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenRsltPieceQty" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_RsltPieceQtyPst" templateKey="W_RsltPiceQtyPst"/></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD>
                <TD align=middle bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ResultCaseQtyTwoByte" templateKey="W_ResultCaseQtyTwoByte"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:checkbox id="chk_RsltCaseQty" templateKey="W_CommonUse"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_RsltCaseQtyLen" templateKey="W_RsltCaseQtyLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:label id="lbl_MaxLenRsltCaseQty" templateKey="W_MaxLen"/></TD>
                <TD align=middle bgColor=#b8b7d7><h:numbertextbox id="txt_RsltCaseQtyPst" templateKey="W_RsltCaseQtyPst"/></TD>
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
                <TD><h:submitbutton id="btn_Submit" templateKey="Submit"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD>&nbsp;&nbsp;&nbsp; </TD>
                <TD><h:submitbutton id="btn_Clear" templateKey="Clear"/></TD></TR></TBODY></TABLE></TD>
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
