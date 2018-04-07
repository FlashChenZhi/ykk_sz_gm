<%-- $Id: StopStationStatusPopup.jsp,v 1.1.1.1 2006/08/17 09:33:15 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:15 $
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
  <%-- header --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD><IMG src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border=0></TD></TR></TBODY></TABLE>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR height=1>
                <TD bgColor=#dad9ee colSpan=3 height=1></TD></TR>
              <TR height=2>
                <TD bgColor=#413a8a colSpan=3 height=4></TD></TR>
              <TR>
                <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
                <TD bgColor=#413a8a>&nbsp;<h:label id="lbl_ListName" templateKey="In_SettingName"/></TD>
                <TD width=7 bgColor=#413a8a height=16><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD></TR>
              <TR height=2>
                <TD bgColor=#413a8a colSpan=3 height=4></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
            border=0>
              <TBODY>
              <TR>
                <TD width=7 height=23></TD>
                <TD width=4 bgColor=white height=23></TD>
                <TD bgColor=white height=23><h:message id="message" templateKey="OperationMsg"/></TD>
                <TD width=7 height=23></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR height=2>
                <TD bgColor=#413a8a colSpan=3 
        height=4></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
  <%-- close botton --%>
  <P>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width="100%">
  
  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
            bgColor=#dad9ee border=0>
              <TBODY>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR></TBODY></TABLE></TD>
          <TD vAlign=bottom align=right>

  <%-- close botton --%>
  <TABLE cellSpacing=0 cellPadding=0 align=right bgColor=#dad9ee 
            border=0>
              <TBODY>
              <TR>
                <TD width=7 height=10></TD>
                <TD width=7 height=10></TD>
                <TD align=right height=10>
                  <TABLE cellSpacing=0 cellPadding=0 bgColor=#b8b7d7 border=0>
                    <TBODY>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD><h:submitbutton id="btn_Close_U" templateKey="Close"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TR></TBODY></TABLE></TD>
                <TD width=7 height=10><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee border=0>
        <TBODY>
        <TR bgColor=#b8b7d7 width="100%">
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_AllCheck" templateKey="W_AllCheck"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_AllCheckClear" templateKey="W_AllCheckClear"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD align=right bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;</TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_StopStationStatus" templateKey="W_StopStationStatus"/></TD>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD align=right bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Setting" templateKey="Setting"/></TD>
                <TD colSpan=5>&nbsp;&nbsp; <h:submitbutton id="btn_Reflesh" templateKey="Reflesh"/></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE><BR>
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
                <TD align=right height=10>
                  <TABLE cellSpacing=0 cellPadding=0 bgColor=#b8b7d7 border=0>
                    <TBODY>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD><h:submitbutton id="btn_Close_D" templateKey="Close"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD>
                      <TD></TD>
                      <TD width=7 height=7><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TR></TBODY></TABLE></TD>
                <TD width=7 height=10><IMG height=1 src="img/common/spacer.gif" width=7 border=0></TD></TR></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></P></TD></TR>
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
