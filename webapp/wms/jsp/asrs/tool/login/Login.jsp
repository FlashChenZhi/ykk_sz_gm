<%-- $Id: Login.jsp,v 1.2 2006/11/15 06:28:37 suresh Exp $ --%>
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
 * 2004/10/01 Miyashita  created
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/15 06:28:37 $
 * @author  $Author: suresh $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "jp.co.daifuku.common.text.DisplayText" %>

<h:html>
  <h:head>
  <script>
    window.document.title = 'eWareNavi(R) Ver2.7';
  </script>
  </h:head>
<h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a border=0>
  <TBODY>
  <TR>
    <TD>
  <%-- title --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#413a8a><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#413a8a><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif"  border=0></TD></TR></TBODY></TABLE>

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
          <TD bgColor=#413a8a height=23></TD></TR></TBODY></TABLE>

  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#413a8a border=0>
        <TBODY>
        <TR>
          <TD align=middle width=1001 
          background=<%=request.getContextPath()%>/img/project/login/menu_bg.gif 
          height=251>
            <TABLE cellSpacing=0 cellPadding=1 border=0>
              <TBODY>
              <TR>
                <TD bgColor=#413a8a>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=white 
                  border=0>
                    <TBODY>
                    <TR height=12 width="500">
                      <TD noWrap></TD>
                      <TD></TD>
                      <TD width="100%"><IMG height=12 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=500 border=0></TD></TR>
                    <TR height=12 width="500">
                      <TD noWrap></TD>
                      <TD></TD>
                      <TD width="100%">
                        <CENTER><h:label id="lbl_Production" templateKey="AST_Production"/></CENTER></TD></TR>
                    <TR height=12 width="500">
                      <TD noWrap></TD>
                      <TD></TD>
                      <TD width="100%">
                        <CENTER><h:freetextbox id="txt_ProductionNumber" templateKey="AST_ProductionNumber"/>&nbsp;<h:submitbutton id="btn_ProductionNumber" templateKey="AST_ProductionNumber"/></CENTER></TD></TR>
                    <TR height=12 width="500">
                      <TD noWrap></TD>
                      <TD></TD>
                      <TD width="100%">
                        <CENTER><h:submitbutton id="btn_Next" templateKey="AST_BC_Next"/></CENTER></TD></TR>
                    <TR height=12 width="500">
                      <TD noWrap></TD>
                      <TD></TD>
                      <TD 
          width="100%"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD></TD>
          <TD bgColor=#413a8a></TD>
          <TD></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD>
<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#413a8a height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#413a8a height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_menu.gif" width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 
          background=<%=request.getContextPath()%>/img/common/menu_bg2.gif 
          height=8></TD>
          <TD 
          background=<%=request.getContextPath()%>/img/common/menu_bg2.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
