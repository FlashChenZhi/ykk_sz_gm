<%-- $Id: AutoLogin.jsp,v 1.1.1.1 2006/08/17 09:33:19 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:19 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
    <h:head>
    </h:head>
    <h:page>
    <% if (request.getAttribute("AUTO_LOGIN_SUCCESS") == null) { %>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD bgColor=#413a8a colSpan=5 height=39></TD></TR>
  <TR>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD width="100%" 
    background="<%=request.getContextPath()%>/img/project/login/background.gif " 
    height=5></TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0> 
    </TD></TR>
  <TR>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD align=middle width="100%" 
    background="<%=request.getContextPath()%>/img/project/login/background.gif " 
    height=251>
      <TABLE cellSpacing=0 cellPadding=1 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#413a8a>
            <TABLE cellSpacing=0 cellPadding=0 bgColor=white border=0>
              <TBODY>
              <TR>
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD height=12></TD>
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD></TR>
              <TR>
                <TD></TD>
                <TD width=800 align=center><h:label id="lbl_Message" templateKey="In_ErrorJsp"/></TD>
                <TD></TD></TR>
              <TR>
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD height=12></TD>
                <TD width=9 height=12><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
  <TR>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=#413a8a></TD>
    <TD width="100%" 
    background="<%=request.getContextPath()%>/img/project/login/background.gif " 
    height=5></TD>
    <TD bgColor=#413a8a></TD>
    <TD width=7 bgColor=#413a8a height=5><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0> 
    </TD></TR>
  <TR>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG src="<%=request.getContextPath()%>/img/common/logo_menu.gif" border=0> 
    </TD></TR>
  <TR>
    <TD 
    background=<%=request.getContextPath()%>/img/common/menu_bg2.gif 
    colSpan=5 height=7></TD></TR></TBODY></TABLE>
    <% } %>
    </h:page>
</h:html>
