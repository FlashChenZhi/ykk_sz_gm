<%-- $Id: 08_listbox.jsp,v 1.2 2007/03/07 07:43:05 suresh Exp $ --%>
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
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee>
  <TBODY>
  <TR>
    <TD><%-- header --%>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" 
        bgColor=#413a8a><TBODY>
        <TR>
          <TD><IMG border=0 
            src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif"></TD></TR></TBODY></TABLE><%-- title --%>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR height=1>
          <TD bgColor=#dad9ee height=1 colSpan=3></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a height=4 colSpan=3></TD></TR>
        <TR>
          <TD bgColor=#413a8a height=16 width=7><IMG border=0 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 
            height=1></TD>
          <TD bgColor=white>&nbsp;<h:message id="message" templateKey="OperationMsg"/></TD>
          <TD bgColor=#413a8a height=16 width=7><IMG border=0 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 
            height=1></TD></TR>
        <TR height=2>
          <TD bgColor=#413a8a height=4 colSpan=3></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR>
          <TD width="100%"><IMG border=0 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width="100%" height=10></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR>
          <TD width="100%"><%-- display --%>
            <TABLE style="MARGIN-LEFT: 7px" border=0 cellSpacing=0 cellPadding=0 
            bgColor=#dad9ee>
              <TBODY>
              <TR height=5 bgColor=#dad9ee>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR>
              <TR bgColor=#dad9ee>
                <TD bgColor=#dad9ee noWrap><h:label id="lbl_Section" templateKey="YKK_LBL_Section"/></TD>
                <TD bgColor=#dad9ee noWrap><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"> 
                </TD>
                <TD bgColor=#dad9ee noWrap><h:freetextbox id="txt_Section" templateKey="YKK_TXT_Section"/> </TD>
                <TD bgColor=#dad9ee 
                  noWrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </TD>
                <TD bgColor=#dad9ee noWrap><h:label id="lbl_Line" templateKey="YKK_LBL_Line"/></TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee width="100%"><h:freetextbox id="txt_Line" templateKey="YKK_TXT_Line"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_WhenNextWorkBegin" templateKey="YKK_LBL_WhenNextWorkBegin"/><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:datetextbox id="txt_WhenNextWorkBegin" templateKey="YKK_TXT_WhenNextWorkBegin"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD bgColor=#dad9ee noWrap><h:label id="lbl_ItemNo" templateKey="YKK_LBL_ItemNo"/></TD>
                <TD bgColor=#dad9ee noWrap><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee noWrap><h:freetextbox id="txt_ItemNo" templateKey="YKK_TXT_ItemNo"/></TD>
                <TD bgColor=#dad9ee noWrap></TD>
                <TD bgColor=#dad9ee noWrap><h:label id="lbl_ColorCode" templateKey="YKK_LBL_ColorCode"/></TD>
                <TD bgColor=#dad9ee><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee width="100%"><h:freetextbox id="txt_ColorCode" templateKey="YKK_TXT_ColorCode"/></TD></TR></TBODY></TABLE></TD>
          <TD vAlign=bottom align=right><%-- close botton --%>
            <TABLE border=0 cellSpacing=0 cellPadding=0 bgColor=#dad9ee 
            align=right>
              <TBODY>
              <TR>
                <TD height=10 width=7></TD>
                <TD height=10 width=7></TD>
                <TD height=10 align=right>
                  <TABLE border=0 cellSpacing=0 cellPadding=0 bgColor=#b8b7d7>
                    <TBODY>
                    <TR>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 height=1></TD>
                      <TD></TD>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 height=1></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD><h:submitbutton id="btn_Close_U" templateKey="Close"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 height=1></TD>
                      <TD></TD>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 
                    height=1></TD></TR></TBODY></TABLE></TD>
                <TD height=10 width=7><IMG border=0 
                  src="img/common/spacer.gif" width=7 
            height=1></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR><%-- List start --%>
      <TABLE style="MARGIN-LEFT: 7px" border=0 cellSpacing=0 cellPadding=0 
      bgColor=#dad9ee>
        <TBODY>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_RetrievalOrderPrintPopup1" templateKey="YKK_LST_RetrievalOrderPrintPopup"/></TD>
          <TD bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;</TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:pager id="pgr_D" templateKey="Pager"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_SelectAllUp" templateKey="YKK_BTN_SelectAll"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_UnselectAllUp" templateKey="YKK_BTN_UnselectAll"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Input" templateKey="YKK_BTN_Input"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_CSV_U" templateKey="YKK_BTN_CSV"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:label id="lbl_TicketNo" templateKey="YKK_LBL_TicketNo"/><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_TicketNo" templateKey="YKK_TXT_TicketNo_Enter"/><h:submitbutton id="btn_Input_TicketNo" templateKey="YKK_BTN_Input"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <h:label id="lbl_BucketNo" templateKey="YKK_LBL_BucketNo"/><IMG 
            src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_BucketNo" templateKey="YKK_TXT_BucketNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
          </TD>
          <TD bgColor=#b8b7d7 height=7>&nbsp;</TD></TR>
        <TR>
          <TD bgColor=#b8b7d7></TD>
          <TD 
            bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:label id="lbl_Total" templateKey="YKK-LBL-Total"/><h:label id="lbl_HowMuch" templateKey="YKK-LBL-HowMuch"/><h:label id="lbl_Row" templateKey="YKK-LBL-Row"/><h:label id="lbl_Slash" templateKey="YKK_LBL_Slash"/><h:label id="lbl_HowMuch2" templateKey="YKK-LBL-HowMuch"/><h:label id="lbl_Page" templateKey="YKK-LBL-Page"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:listcell id="lst_RetrievalOrderPrintPopup2" templateKey="YKK_LST_RetrievalOrderPrintPopup"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:submitlabel id="slb_Download" templateKey="W_Download"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR>
        <TR bgColor=#b8b7d7>
          <TD bgColor=#b8b7d7></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_SelectAllDown" templateKey="YKK_BTN_SelectAll"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_UnselectAllDown" templateKey="YKK_BTN_UnselectAll"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Delete" templateKey="YKK_BTN_Delete"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_CSV" templateKey="YKK_BTN_CSV"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Print" templateKey="YKK_BTN_Print"/></TD>
          <TD bgColor=#b8b7d7 height=7></TD></TR></TBODY></TABLE><BR>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR>
          <TD width="100%"></TD>
          <TD align=right><%-- close botton --%>
            <TABLE border=0 cellSpacing=0 cellPadding=0 bgColor=#dad9ee 
            align=right>
              <TBODY>
              <TR>
                <TD height=10 width=7></TD>
                <TD height=10 width=7></TD>
                <TD height=10 align=right>
                  <TABLE border=0 cellSpacing=0 cellPadding=0 bgColor=#b8b7d7>
                    <TBODY>
                    <TR>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 height=1></TD>
                      <TD></TD>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 height=1></TD></TR>
                    <TR>
                      <TD></TD>
                      <TD><h:submitbutton id="btn_Close_D" templateKey="Close"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 height=1></TD>
                      <TD></TD>
                      <TD height=7 width=7><IMG border=0 
                        src="img/common/spacer.gif" width=7 
                    height=1></TD></TR></TBODY></TABLE></TD>
                <TD height=10 width=7><IMG border=0 
                  src="img/common/spacer.gif" width=7 
              height=1></TD></TR></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><%-- footer --%>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR height=41>
          <TD bgColor=#dad9ee height=41 width=7><IMG border=0 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 
            height=1></TD>
          <TD bgColor=#dad9ee height=41><IMG border=0 alt="" 
            src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 
            height=23></TD></TR>
        <TR height=8>
          <TD height=8 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          width=7></TD>
          <TD height=8 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
