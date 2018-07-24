<%-- $Id: 10_uppertab-lowertab-listbox.jsp,v 1.2 2007/03/07 07:43:04 suresh Exp $ --%>
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
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:43:04 $
 * @author  $Author: suresh $
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
  <%-- header --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border=0></TD></TR></TBODY></TABLE>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD>&nbsp;<h:label id="lbl_ListName" templateKey="YKK_LBL_FlatStockout"/></TD>
          <TD></TD>
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
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"><IMG height=10 src="img/common/spacer.gif" width="100%" border=0></TD></TR></TBODY></TABLE>


  <%-- close botton --%>

  <%-- upper display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#dad9ee height=5>
          <TD></TD>
          <TD></TD></TR>
        <TR bgColor=#dad9ee>
          <TD width="100%" bgColor=#dad9ee>
            <TABLE>
              <TBODY>
              <TR>
                <TD><h:label id="lbl_ItemNo" templateKey="YKK_LBL_ItemNo"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:freetextbox id="txt_ItemNo" templateKey="YKK_TXT_ItemNo_RO"/></TD>
                <TD><h:freetextbox id="txt_ItemName1" templateKey="YKK_TXT_ItemName1_RO"/></TD></TR>
              <TR>
                <TD><h:label id="lbl_ColorCode" templateKey="YKK_LBL_ColorCode"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:freetextbox id="txt_ColorCode" templateKey="YKK_TXT_ColorCode_RO"/></TD>
                <TD><h:freetextbox id="txt_ItemName2" templateKey="YKK_TXT_ItemName2_RO"/></TD></TR>
              <TR>
                <TD><h:label id="lbl_Section" templateKey="YKK_LBL_Section"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:freetextbox id="txt_Section" templateKey="YKK_TXT_Section_RO"/></TD>
                <TD><h:freetextbox id="txt_ItemName3" templateKey="YKK_TXT_ItemName3_RO"/></TD></TR>
              <TR>
                <TD><h:label id="lbl_WhenNextWorkBegin" templateKey="YKK_LBL_WhenNextWorkBegin"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:datetextbox id="txt_WhenNextWorkBegin" templateKey="YKK_TXT_WhenNextWorkBegin_RO"/></TD>
                <TD><h:label id="lbl_StockoutNecessaryQty" templateKey="YKK_LBL_StockoutNecessaryQty"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_StockoutNecessaryQty" templateKey="YKK_TXT_StockoutNecessaryQty_RO"/><h:label id="lbl_ManagementRetrievalQty" templateKey="YKK_LBL_ManagementRetrievalQty_RO"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_ManagementRetrievalQty" templateKey="YKK_TXT_ManagementRetrievalQty_RO"/><h:label id="lbl_StockoutCount" templateKey="YKK_LBL_StockoutCount"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_StockoutCount" templateKey="YKK_TXT_StockoutCount_RO"/></TD></TR></TBODY></TABLE></TD>
          <TD width="100%" bgColor=#dad9ee></TD></TR>
        <TR bgColor=#dad9ee>
          <TD width="100%" bgColor=#dad9ee></TD>
          <TD width="100%" bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7>
            <TABLE>
              <TBODY>
              <TR>
                <TD>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD>
                      <TD width="100%"></TD>
                      <TD><h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show"/></TD></TR></TBODY></TABLE></TD>
                <TD></TD></TR>
              <TR>
                <TD><h:listcell id="lst_FlatStockoutPopup_Up" templateKey="YKK_LST_FlatStockoutPopup_Up"/></TD>
                <TD></TD></TR>
              <TR>
                <TD>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD>
                      <TD width="100%"></TD>
                      <TD><h:submitbutton id="btn_Input_1" templateKey="YKK_BTN_Input"/></TD></TR></TBODY></TABLE></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD width="100%" bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7></TD>
          <TD width="100%" bgColor=#b8b7d7></TD></TR></TBODY></TABLE>
      <P>
  <%-- lower display --%>
  </P>
      <P>&nbsp;</P>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_TicketNo" templateKey="YKK_LBL_TicketNo"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_TicketNo" templateKey="YKK_TXT_TicketNo"/><h:submitbutton id="btn_Input_2" templateKey="YKK_BTN_Input"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD align=right><h:submitbutton id="btn_Delete" templateKey="YKK_BTN_Delete"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD><h:listcell id="lst_FlatStockoutPopup_Low" templateKey="YKK_LST_FlatStockoutPopup_Low"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD align = "right"><h:submitbutton id="btn_Set" templateKey="YKK_BTN_Set"/>&nbsp;&nbsp; 
                        <h:submitbutton id="btn_Close" templateKey="YKK_BTN_Close"/></TD>
                      <TD></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
  <TR height=15>
    <TD height=15></TD></TR>
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
