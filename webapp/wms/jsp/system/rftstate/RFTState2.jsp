<%-- $Id: RFTState2.jsp,v 1.1.1.1 2006/08/17 09:33:28 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:28 $
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
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24></TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE>
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
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE>
  <%-- tab --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG height=2 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee><h:label id="lbl_SearchCondition" templateKey="W_SearchCondition"/></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_RftNoLabel" templateKey="W_RftMachine"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee><h:label id="lbl_RftNo" templateKey="W_JavaSet"/></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_TerminalCategoryLabel" templateKey="W_TerminalCategory"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee><h:label id="lbl_TerminalCategory" templateKey="W_JavaSet"/></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_RftStatusLabel" templateKey="W_RftState"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee><h:label id="lbl_RftStatus" templateKey="W_JavaSet"/></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_Worker" templateKey="W_Worker"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee><h:label id="lbl_WorkerCode" templateKey="W_JavaSet"/></TD>
                <TD bgColor=#dad9ee colSpan=4><h:label id="lbl_WorkerName" templateKey="W_JavaSet"/>&nbsp;&nbsp;&nbsp;</TD>
                <TD width="100%" bgColor=#dad9ee><h:submitbutton id="btn_Close" templateKey="Close"/></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee>

  <%-- display --%>
  &nbsp;</TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD>
                <TD width="100%" bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkerCodeLabel" templateKey="W_WorkerCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_WorkerCode" templateKey="W_WorkerCode"/></TD>
                <TD bgColor=#b8b7d7 colSpan=2><h:label id="lbl_Password" templateKey="W_Password"/>&nbsp;</TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_Password" templateKey="W_Password"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WorkOnTheWay" templateKey="W_WorkOnTheWay"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:radiobutton id="rdo_Cancel" templateKey="W_WorkingData_Cancel"/></TD>
                <TD bgColor=#b8b7d7>&nbsp;&nbsp; &nbsp; </TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:radiobutton id="rdo_Commit" templateKey="W_WorkingData_Commit"/>&nbsp;&nbsp;</TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LachAmount" templateKey="W_LackAmount"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:radiobutton id="rdo_Installment" templateKey="W_ShortageQty_Installment"/></TD>
                <TD bgColor=#b8b7d7>&nbsp;&nbsp; &nbsp; </TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:radiobutton id="rdo_Shortage" templateKey="W_ShortageQty_Shortage"/>&nbsp;&nbsp;</TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Submit" templateKey="Setting"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Refresh" templateKey="Reflesh"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="Clear"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>
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
