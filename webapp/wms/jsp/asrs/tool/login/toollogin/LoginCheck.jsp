<%-- $Id: LoginCheck.jsp,v 1.1.1.1 2006/08/17 09:33:16 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:16 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
    <h:head>
    </h:head>
    <h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR height=39>
    <TD bgColor=#413a8a colSpan=3 height=39></TD></TR>
  <TR height=251>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
    <TD align=middle width="100%" 
    background=<%=request.getContextPath()%>/img/project/login/background.gif 
    height=251>
      <TABLE cellSpacing=0 cellPadding=1 border=0>
        <TBODY>
        <TR>
          <TD bgColor=#413a8a>
            <TABLE cellSpacing=0 cellPadding=0 bgColor=white border=0>
              <TBODY>
              <TR height=12>
                <TD height=12><IMG height=12 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD>
                <TD colSpan=3></TD></TR>
              <TR>
                <TD width=9><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD noWrap align=middle colSpan=2><h:label id="lbl_Msg01" templateKey="W_In_LoginChkMsg1"/><BR><h:label id="lbl_Msg02" templateKey="LoginChkMsg2"/> </TD>
                <TD width=9><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD></TR>
              <TR>
                <TD width=20><IMG height=25 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0></TD>
                <TD align=middle><h:submitbutton id="btn_LoginWithAnotherUser" templateKey="LoginWithAnotherUser"/><BR>
                <TD></TD></TD>
                <TD width=20><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=9 border=0> 
                </TD></TR>
              <TR>
                <TD align=middle colSpan=3><IMG height=20 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=0 border=0> 
                  <h:linklabel id="llb_LoginUserList" templateKey="W_LoginUserList"/> </TD></TR>
              <TR height=12>
                <TD colSpan=3></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
    <TD width=7 bgColor=#413a8a height=251><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
  <TR height=39>
    <TD bgColor=#413a8a colSpan=5 height=39><IMG height=24 src="<%=request.getContextPath()%>/img/common/logo_menu.gif" width=380 border=0></TD></TR>
  <TR height=7>
    <TD 
    background=<%=request.getContextPath()%>/img/common/menu_bg2.gif 
    colSpan=5 height=7></TD></TR></TBODY></TABLE></h:page>
</h:html>
