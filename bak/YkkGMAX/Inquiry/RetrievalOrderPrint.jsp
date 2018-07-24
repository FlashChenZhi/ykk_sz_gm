<%-- $Id: 02_uppertab-only.jsp,v 1.2 2007/03/07 07:43:09 suresh Exp $ --%>
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
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:43:09 $
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
    <TD><%-- title --%>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" 
        bgColor=#413a8a><TBODY>
        <TR>
          <TD height=24 width=7></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" height=24 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          align=right><h:linkbutton id="btn_Help" templateKey="Help"/> 
          </TD>
          <TD height=24 width=7></TD></TR></TBODY></TABLE><%-- message --%>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" 
        bgColor=#413a8a><TBODY>
        <TR>
          <TD height=4 colSpan=4></TD></TR>
        <TR>
          <TD height=23 width=7></TD>
          <TD bgColor=white height=23 width=4></TD>
          <TD bgColor=white height=23><h:message id="message" templateKey="OperationMsg"/></TD>
          <TD height=23 width=7></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE><%-- tab --%>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG border=0 
            src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 
        height=2></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif>
        <TBODY>
        <TR height=28>
          <TD width=7><IMG src="<%=request.getContextPath()%>/img/common/spacer.gif" 
            width=7></TD>
          <TD width=7></TD>
          <TD noWrap><h:tab id="tab" templateKey="YKK_TAB_Inquiry"/></TD>
          <TD height=28 width="90%"></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE><%-- display --%>
      <TABLE style="MARGIN-LEFT: 7px" border=0 cellSpacing=0 cellPadding=0 
      width="98%" bgColor=#b8b7d7>
        <TBODY>
        <TR>
          <TD>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
              <TR height=5 bgColor=#dad9ee>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_DateTimeRange" templateKey="YKK_LBL_DateTimeRange"/><h:label id="lbl_Star1" templateKey="YKK_LBL_Star"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:label id="lbl_DateFrom" templateKey="YKK_LBL_Date"/><h:datetextbox id="txt_DateFrom" templateKey="YKK_TXT_DateFrom"/><h:label id="lbl_TimeFrom" templateKey="YKK_LBL_Time"/><h:timetextbox id="txt_TimeFrom" templateKey="YKK_TXT_TimeFrom"/><h:label id="lbl_to" templateKey="YKK_LBL_to"/><h:label id="lbl_DateTo" templateKey="YKK_LBL_Date"/><h:datetextbox id="txt_DateTo" templateKey="YKK_TXT_DateTo"/><h:label id="lbl_TimeTo" templateKey="YKK_LBL_Time"/><h:timetextbox id="txt_TimeTo" templateKey="YKK_TXT_TimeTo"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_Section" templateKey="YKK_LBL_Section"/><h:label id="lbl_Star2" templateKey="YKK_LBL_Star"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:freetextbox id="txt_Section" templateKey="YKK_TXT_Section"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_Line" templateKey="YKK_LBL_Line"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:freetextbox id="txt_Line" templateKey="YKK_TXT_Line"/></TD></TR>
              <TR>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_WhenNextWorkBegin" templateKey="YKK_LBL_WhenNextWorkBegin"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:datetextbox id="txt_WhenNextWorkBegin" templateKey="YKK_TXT_WhenNextWorkBegin"/><h:label id="lbl_Dynasty" templateKey="YKK-LBL-Dynasty"/></TD></TR>
              <TR>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_ItemNo" templateKey="YKK_LBL_ItemNo"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:freetextbox id="txt_ItemNo" templateKey="YKK_TXT_ItemNo"/><h:submitbutton id="btn_Search_POPUP" templateKey="YKK_BTN_Search_POPUP"/>&nbsp;&nbsp; 
                  <h:freetextbox id="txt_ItemName1" templateKey="YKK_TXT_ItemName1"/></TD></TR>
              <TR>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_ColorCode" templateKey="YKK_LBL_ColorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:freetextbox id="txt_ColorCode" templateKey="YKK_TXT_ColorCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:freetextbox id="txt_ItemName2" templateKey="YKK_TXT_ItemName2"/></TD></TR>
              <TR>
                <TD bgColor=#b8b7d7 noWrap></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7 
                  width="100%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:freetextbox id="txt_ItemName3" templateKey="YKK_TXT_ItemName3"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD bgColor=#b8b7d7 noWrap><h:label id="lbl_SortCondition" templateKey="YKK-LBL-SortCondition"/></TD>
                <TD bgColor=#b8b7d7><IMG 
                  src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 width="100%"><h:radiobutton id="rdo_WhenNextWorkBegin" templateKey="YKK_RDO_WhenNextWorkBegin"/><h:radiobutton id="rdo_ItemNo" templateKey="YKK_RDO_ItemNo"/></TD></TR></TBODY></TABLE><h:submitlabel id="slb_Popup" templateKey="YKK_SLB_SubmitLabelPopup"/></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" border=0 cellSpacing=0 cellPadding=0>
        <TBODY>
        <TR>
          <TD><IMG border=0 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" 
            width=42 height=40></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show_NA"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="YKK_BTN_Clear"/> 
          </TD>
          <TD><IMG border=0 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" 
            width=42 height=40></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD><%-- footer --%>
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
          <TD height=8 background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          width=7></TD>
          <TD height=8 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
