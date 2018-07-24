<%-- $Id: Progress.jsp,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $ --%>

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
 * 2004/02/13 N.Sawa     新規作成
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:06 $
 * @author  $Author: mori $
--%>
<%-- @start page directives --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix="h" %>
<%-- @end page directives --%>
<h:html>
    <h:head>
    </h:head>
    <h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR height=39>
    <TD bgColor=#413a8a colSpan=5 height=39></TD></TR>
  <TR height=251>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD bgColor=white height=251></TD>
    <TD align=middle width=1001 
    background=<%=request.getContextPath()%>/img/project/login/menu_bg.gif 
    height=251>
      <TABLE cellSpacing=0 cellPadding=1 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#413a8a>
            <TABLE cellSpacing=0 cellPadding=0 bgColor=white border=0>
              <TBODY>
              <TR height=12>
                <TD vAlign=center align=middle width=45 rowSpan=3><IMG 
                  height=12 
                  src="<%=request.getContextPath()%>/img/common/spacer.gif" 
                  width=1 border=0><IMG height=27 src="<%=request.getContextPath()%>/img/common/icon_progress.gif" width=27 border=0></TD>
                <TD height=12></TD>
                <TD width=9 height=12></TD></TR>
              <TR>
                <TD noWrap><h:label id="msg" templateKey="ProgressMsg"/>&nbsp;</TD>
                <TD width=9><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD></TR>
              <TR height=12>
                <TD height=12>&nbsp;&nbsp;&nbsp;</TD>
                <TD width=9 
    height=12></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
    <TD bgColor=white height=251></TD>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
  <TR height=39>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG height=24 src="<%=request.getContextPath()%>/img/common/logo_menu.gif" width=380 border=0></TD></TR>
  <TR height=7>
    <TD background=<%=request.getContextPath()%>/img/common/menu_bg2.gif 
    colSpan=5 height=7></TD></TR></TBODY></TABLE></h:page>
</h:html>
