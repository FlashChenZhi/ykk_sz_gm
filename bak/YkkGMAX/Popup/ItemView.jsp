<%-- $Id: ItemView.jsp,v 1.2 2007/12/25 03:46:15 administrator Exp $ --%>
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
 * @version $Revision: 1.2 $, $Date: 2007/12/25 03:46:15 $
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
  <%-- header --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border=0></TD></TR></TBODY></TABLE>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=1>
          <TD bgColor=#dad9ee colSpan=3 height=1></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a colSpan=3 height=4></TD></TR>
        <TR>
          <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=white>&nbsp;<h:message id="message" templateKey="OperationMsg"/></TD>
          <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a colSpan=3 height=4></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"><IMG height=10 src="<%=request.getContextPath()%>/img/common/spacer.gif" width="100%" border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemCode" templateKey="YKK_LBL_ItemNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemCode" templateKey="YKK_TXT_ItemNo"/>&nbsp;&nbsp; <h:label id="lbl_LooseSearch" templateKey="YKK_LBL_LooseSearch"/><h:radiobutton id="rdo_SameToFront" templateKey="YKK_RDO_SameToFrontChecked"/><h:radiobutton id="rdo_SameToAnywhere" templateKey="YKK_RDO_SameToAnywhere"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ItemName" templateKey="YKK_LBL_ItemName"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemName1" templateKey="YKK_TXT_ItemName1"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemName2" templateKey="YKK_TXT_ItemName2"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemName3" templateKey="YKK_TXT_ItemName3"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show"/> 
          </TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE><BR>

  <%-- List start --%>   
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#b8b7d7 width="100%">
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7>
            <TABLE>
              <TBODY>
              <TR>
                <TD><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD>
                <TD><h:submitbutton id="btn_Close_Up" templateKey="YKK_BTN_Close"/></TD></TR></TBODY></TABLE></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_ItemView" templateKey="YKK_LST_ItemView"/></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7>
            <TABLE>
              <TBODY>
              <TR>
                <TD><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD>
                <TD><h:submitbutton id="btn_Close_Low" templateKey="YKK_BTN_Close"/></TD></TR></TBODY></TABLE></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD></TR></TBODY></TABLE><BR>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"></TD>
          <TD align=right>

  
  <%-- close botton --%>
  <TABLE cellSpacing=0 cellPadding=0 align=right bgColor=#dad9ee 
            border=0>
              <TBODY>
              <TR>
                <TD width=7 height=10></TD>
                <TD width=7 height=10></TD>
                <TD align=right height=10></TD>
                <TD width=7 
        height=10></TD></TR></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

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
