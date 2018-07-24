<%-- $Id: 09_uppertab-listbox.jsp,v 1.2 2007/03/07 07:43:05 suresh Exp $ --%>
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
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:43:05 $
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


  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#dad9ee height=5>
          <TD bgColor=#dad9ee></TD>
          <TD bgColor=#dad9ee></TD></TR>
        <TR bgColor=#dad9ee>
          <TD width="100%" bgColor=#dad9ee></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7>
            <TABLE>
              <TBODY>
              <TR>
                <TD><h:label id="lbl_Section" templateKey="YKK_LBL_Section"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:pulldown id="pul_Section" templateKey="YKK_PUL_Section"/>&nbsp;&nbsp;&nbsp;<h:label id="lbl_RetrievalNo" templateKey="YKK_LBL_RetrievalNo"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_RetrievalNo" templateKey="YKK_TXT_RetrievalNo"/>&nbsp;&nbsp; <h:label id="lbl_OrderNo" templateKey="YKK_LBL_OrderNo"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_OrderNo" templateKey="YKK_TXT_OrderNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show"/></TD>
                <TD>&nbsp;&nbsp; </TD></TR>
              <TR>
                <TD>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_OrderBy" templateKey="YKK_LBL_OrderBy"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:radiobutton id="rdo_ItemNo" templateKey="YKK_RDO_ItemNoChcked"/><h:radiobutton id="rdo_WhenNextWorkBegin" templateKey="YKK_RDO_WhenNextWorkBegin_2"/><h:radiobutton id="rdo_WhenThisWorkFinishInPlan" templateKey="YKK_RDO_WhenThisWorkFinishInPlan"/><h:radiobutton id="rdo_RetrievalNo" templateKey="YKK_RDO_RetrievalNo"/></TD>
                      <TD align=right width="100%"><h:submitbutton id="btn_SelectAll" templateKey="YKK_BTN_SelectAll"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_UnselectAll" templateKey="YKK_BTN_UnselectAll"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Set_Up" templateKey="YKK_BTN_Set_NA"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_TotalNecessaryQty" templateKey="YKK_LBL_TotalNecessaryQty"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_TotalNecessaryQty" templateKey="YKK_TXT_TotalNecessaryQty_RO"/>&nbsp;&nbsp; <h:label id="lbl_TotalOutQty" templateKey="YKK_LBL_TotalOutQty"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_TotalOutQty" templateKey="YKK_TXT_TotalOutQty_RO"/></TD>
                      <TD align=right 
                        width="100%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Close_Up" templateKey="YKK_BTN_Close"/></TD></TR></TBODY></TABLE></TD>
                <TD>&nbsp;&nbsp; </TD></TR></TBODY></TABLE><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_ExternalStockoutView" templateKey="YKK_LST_ExternalStockoutView"/></TD></TR>
        <TR>
          <TD width="100%" bgColor=#b8b7d7><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7><h:submitbutton id="btn_Set_Low" templateKey="YKK_BTN_Set_NA"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Close_Low" templateKey="YKK_BTN_Close"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <h:checkbox id="chk_DisplayFinishedRetrieval" templateKey="YKK_CHK_DisplayFinishedRetrieval"/><LABEL 
            class=lbl-black-001></LABEL></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD width="100%" bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR>
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
