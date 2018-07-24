<%-- $Id: Login.jsp,v 1.2 2006/11/15 06:28:38 suresh Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Login Screen for eWareNavi.
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/02/13 N.Sawa     created
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/15 06:28:38 $
 * @author  $Author: suresh $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
    <h:head>
    <script>
      window.document.title = 'eWareNavi(R) Ver2.7';
    </script>
    </h:head>
    <h:page>
<table  cellSpacing=0 cellPadding=0 width="100%" HEIGHT="100%" background="<%=request.getContextPath()%>/img/project/login/background.gif">
<tr valign="top"><td>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR height=38>
    <TD align=right bgColor=#413a8a height=38><IMG src="<%=request.getContextPath()%>/img/project/login/daifuku-logo.gif" border=0>&nbsp;&nbsp;&nbsp;</TD></TR>
  <TR>
    <TD>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/project/login/body-bg.gif 
      border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE height=699 cellSpacing=0 cellPadding=0 width=1011 
            align=center 
            background=<%=request.getContextPath()%>/img/project/login/auth-bg.gif 
            border=0>
              <TBODY>
              <TR>
                <TD colSpan=5 height=200></TD></TR>
              <TR>
                <TD width=330></TD>
                <TD noWrap align=middle colSpan=3 height=30 ALIGN="center">
                  <h:label id="lbl_Message1" templateKey="W_LoginMessage1"/><BR>
                  <h:label id="lbl_Message2" templateKey="W_LoginMessage2"/>
                </TD>
                <TD width=330></TD>
              </TR>
              <TR>
                <TD width=330 height=14></TD>
                <TD noWrap height=14  ALIGN=LEFT><h:label id="lbl_LoginID" templateKey="W_LoginID"/></TD>
                <TD height=14  ALIGN=CENTER><SPAN class=lbl-white-002>: </SPAN></TD>
                <TD width=50 height=14><h:freetextbox id="txt_LoginID" templateKey="W_LoginID"/></TD>
                <TD width=330 height=14></TD></TR>
             <TR>
                <TD width=330 height=14></TD>
                <TD noWrap ALIGN=LEFT>
                <h:label id="lbl_Password" templateKey="Password"/></TD>
                <TD  ALIGN=CENTER><SPAN class=lbl-white-002>: </SPAN></TD>
                <TD width=50 height=14>
                <h:freetextbox id="txt_Password" templateKey="Password"/> </TD>
                <TD width=330 height=14></TD></TR>
              <TR>
                <TD align=middle colSpan=5 height=14>
                <h:submitbutton id="btn_Login" templateKey="Login"/> 
                </TD></TR>
              <TR height=160>
                <TD colSpan=5 height=165>
                </TD></TR>
              </TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
</td></tr></table>

</h:page>
</h:html>
<script>
windowResize();
</script>