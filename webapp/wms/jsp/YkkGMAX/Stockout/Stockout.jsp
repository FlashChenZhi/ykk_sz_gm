<%-- $Id: 03_upper-only.jsp,v 1.2 2007/03/07 07:43:09 suresh Exp $ --%>
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
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
  <TBODY>
  <TR>
    <TD>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="YKK_LBL_StockOut"/></TD>
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
      <TABLE cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
      border=0>
        <TBODY>
        <TR height=28>
          <TD width=7><IMG src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7></TD>
          <TD width=7></TD>
          <TD noWrap></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
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
                <TD bgColor=#dad9ee>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:radiobutton id="rdo_OtherProcedure" templateKey="YKK_RDO_OtherProcedure"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Section" templateKey="YKK_TXT_Section"/>&nbsp;&nbsp;&nbsp;<h:freetextbox id="txt_Section2" templateKey="YKK_TXT_Section"/>&nbsp;&nbsp;&nbsp;<h:freetextbox id="txt_Section3" templateKey="YKK_TXT_Section"/>&nbsp;&nbsp; 
                        <h:freetextbox id="txt_Section4" templateKey="YKK_TXT_Section"/>&nbsp;&nbsp;&nbsp;<h:label id="lbl_Line" templateKey="YKK_LBL_Line"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Line" templateKey="YKK_TXT_Line"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                        &nbsp;<h:radiobutton id="rdo_AssemblyLineWorkingProcedu" templateKey="YKK_RDO_AssemblyLineWorkingProcedure"/>&nbsp;&nbsp;&nbsp;<h:label id="lbl_LineDivision" templateKey="YKK_LBL_LineDivision"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_LineDivision" templateKey="YKK_TXT_LineDivision"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:radiobutton id="rdo_FinalProcedure" templateKey="YKK_RDO_FinalProcedure"/>&nbsp;&nbsp; 
                        &nbsp; </TD>
                      <TD>&nbsp;&nbsp; <h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_WhenNextWorkBegin" templateKey="YKK_LBL_WhenNextWorkBegin"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:datetextbox id="txt_WhenNextWorkBegin" templateKey="YKK_TXT_WhenNextWorkBegin"/><h:pulldown id="pul_AmPm" templateKey="YKK_PUL_AmPm"/></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_OrderBy" templateKey="YKK_LBL_OrderBy"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:radiobutton id="rdo_WhenNextWorkBegin" templateKey="YKK_RDO_WhenNextWorkBegin"/><h:radiobutton id="rdo_WhenThisWorkFinishInPlan" templateKey="YKK_RDO_WhenThisWorkFinishInPlan"/><h:radiobutton id="rdo_ItemNo" templateKey="YKK_RDO_ItemNo"/><h:radiobutton id="rdo_RetrievalNo" templateKey="YKK_RDO_RetrievalNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                        &nbsp; <h:label id="lbl_Subdivision" templateKey="YKK_LBL_Subdivision"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:pulldown id="pul_Subdivision" templateKey="YKK_PUL_Subdivision"/>&nbsp;&nbsp; 
                        <h:submitbutton id="btn_SelectAll" templateKey="YKK_BTN_SelectAll"/>&nbsp;&nbsp; 
                        <h:submitbutton id="btn_UnselectAll" templateKey="YKK_BTN_UnselectAll"/></TD>
                      <TD>&nbsp;&nbsp; <h:submitbutton id="btn_Set_Up" templateKey="YKK_BTN_Set_POPUP"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7><h:listcell id="lst_Stockout" templateKey="YKK_LST_Stockout"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7></TD></TR></TBODY></TABLE><h:submitbutton id="btn_Set_Low" templateKey="YKK_BTN_Set_POPUP"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Delete" templateKey="YKK_BTN_DataDelete"/>&nbsp;&nbsp;&nbsp;<h:freetextbox id="txt_StockOutDeletionPassword" templateKey="YKK_TXT_StockOutDeletionPassword"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <h:checkbox id="chk_DisplayFinishedRetrieval" templateKey="YKK_CHK_DisplayFinishedRetrieval"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            <h:label id="lbl_ShowShortageCondition" templateKey="YKK-LBL-ShowShortageCondition"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:radiobutton id="rdo_ShowAll" templateKey="YKK_RDO_ShowAll"/><h:radiobutton id="rdo_NoShortage" templateKey="YKK_RDO_NoShortage"/><h:radiobutton id="rdo_ShortageOnly" templateKey="YKK_RDO_ShortageOnly"/></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY></TBODY></TABLE></TD></TR>
  <TR>
    <TD>
<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#dad9ee height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#dad9ee height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD>
          <TD background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
