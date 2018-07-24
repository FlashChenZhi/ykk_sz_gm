<%-- $Id: ExceptionStockoutPopup.jsp,v 1.3 2007/12/13 06:25:11 administrator Exp $ --%>
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
 * @version $Revision: 1.3 $, $Date: 2007/12/13 06:25:11 $
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
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%">
  
  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
            bgColor=#dad9ee border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_StockoutCondition" templateKey="YKK_LBL_StockoutCondition"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="80%" bgColor=#dad9ee><h:freetextbox id="txt_StockoutCondition" templateKey="YKK_TXT_StockoutCondition_RO"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_StockoutRange" templateKey="YKK_LBL-StockoutRange"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="80%" bgColor=#dad9ee><h:freetextbox id="txt_StockoutRange" templateKey="YKK_TXT_StockoutRange_RO"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_OrderBy" templateKey="YKK_LBL_OrderBy"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="80%" bgColor=#dad9ee><h:radiobutton id="rdo_LocationNo" templateKey="YKK_RDO_LocationNoChcked"/><h:radiobutton id="rdo_ItemNo" templateKey="YKK_RDO_ItemNo"/><h:radiobutton id="rdo_StockinTime" templateKey="YKK_RDO_StockinTime"/><h:radiobutton id="rdo_Line1Line2" templateKey="YKK_RDO_Line1Line2"/>&nbsp;&nbsp; <h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_StockoutStation" templateKey="YKK_LBL_StockoutStation"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="80%" bgColor=#dad9ee><h:pulldown id="pul_StockoutStation" templateKey="YKK_PUL_StockoutStation"/>&nbsp;&nbsp; <h:submitbutton id="btn_SelectAll" templateKey="YKK_BTN_SelectAll"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_UnselectAll" templateKey="YKK_BTN_UnselectAll"/></TD>
                <TD width="100%" bgColor=#dad9ee><h:submitbutton id="btn_Set_Up" templateKey="YKK_BTN_Set_NA"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Close_Up" templateKey="YKK_BTN_Close"/></TD></TR></TBODY></TABLE></TD>
          <TD vAlign=bottom align=right>

  <%-- close botton --%>
  <TABLE cellSpacing=0 cellPadding=0 align=right bgColor=#dad9ee 
            border=0>
              <TBODY>
              <TR>
                <TD width=7 height=10></TD>
                <TD width=7 height=10></TD>
                <TD align=right height=10></TD>
                <TD width=7 
      height=10></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>

  <%-- List start --%>   
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#b8b7d7 width="100%">
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_ExceptionStockoutPopup" templateKey="YKK_LST_ExceptionStockoutPopup"/></TD>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR></TBODY></TABLE><BR>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%"><h:submitbutton id="btn_Set_Low" templateKey="YKK_BTN_Set_NA"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Close_Low" templateKey="YKK_BTN_Close"/></TD>
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
