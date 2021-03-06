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
          <TD><h:label id="lbl_SettingName" templateKey="YKK_LBL_StockoutStart"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24><h:linkbutton id="btn_Help" templateKey="Help"/> 
          </TD>
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
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#dad9ee>
                <TD width="100%" bgColor=#dad9ee>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:radiobutton id="rdo_OtherProcedure" templateKey="YKK_RDO_OtherProcedure"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Section" templateKey="YKK_TXT_Section"/>&nbsp;&nbsp;<h:label id="lbl_Line" templateKey="YKK_LBL_Line"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_Line" templateKey="YKK_TXT_Line"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                        &nbsp;&nbsp;<h:radiobutton id="rdo_AssemblyLineWorkingProcedu" templateKey="YKK_RDO_AssemblyLineWorkingProcedure"/>&nbsp;&nbsp;&nbsp;<h:label id="lbl_LineDivision" templateKey="YKK_LBL_LineDivision"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:numbertextbox id="txt_LineDivision" templateKey="YKK_TXT_LineDivision"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 
                        <h:radiobutton id="rdo_FinalProcedure" templateKey="YKK_RDO_FinalProcedure"/>&nbsp;&nbsp; </TD>
                      <TD>&nbsp;&nbsp; <h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show"/></TD></TR>
                    <TR>
                      <TD>
                        <TABLE>
                          <TBODY>
                          <TR>
                            <TD><h:label id="lbl_OrderBy" templateKey="YKK_LBL_OrderBy"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:radiobutton id="rdo_ItemNo" templateKey="YKK_RDO_ItemNoChcked"/><h:radiobutton id="rdo_StockinTime" templateKey="YKK_RDO_StockinTime"/></TD>
                            <TD align=right 
                              width="100%">&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_RetrievalNo" templateKey="YKK_LBL_RetrievalNo"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_RetrievalNo" templateKey="YKK_TXT_RetrievalNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_SelectAllUnit" templateKey="YKK_BTN_SelectAllUnit"/>&nbsp;&nbsp; 
                              <h:submitbutton id="btn_SelectAll" templateKey="YKK_BTN_SelectAll"/>&nbsp;&nbsp; 
                              <h:submitbutton id="btn_UnselectAll" templateKey="YKK_BTN_UnselectAll"/>&nbsp;&nbsp; 
                              <h:submitbutton id="btn_Set_Up" templateKey="YKK_BTN_Set"/></TD></TR></TBODY></TABLE></TD>
                      <TD>&nbsp;&nbsp; </TD></TR></TBODY></TABLE></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD width="100%" bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:pager id="pgr_Up" templateKey="YKK_Pager"/></TD></TR>
                    <TR>
                      <TD><h:listcell id="lst_StockoutStart" templateKey="YKK_LST_StockoutStart"/></TD></TR>
                    <TR>
                      <TD><h:pager id="pgr_Low" templateKey="YKK_Pager"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
            <TABLE>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Set_Low" templateKey="YKK_BTN_Set"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_ReleaseStorage" templateKey="YKK_BTN_ReleaseStorage"/></TD>
                <TD align=right width="80%"><h:label id="lbl_DesignateStockoutStation" templateKey="YKK_LBL_DesignateStockoutStation"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:pulldown id="pul_DesignateStockoutStation" templateKey="YKK_PUL_DesignateStockoutStation"/>&nbsp;&nbsp; <h:radiobutton id="rdo_Auto" templateKey="YKK_RDO_Auto"/><h:radiobutton id="rdo_Picking" templateKey="YKK_RDO_Picking"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_SelectedUnitCount" templateKey="YKK_LBL_SelectedUnitCount"/><h:label id="lbl_EqualsMark" templateKey="YKK_LBL_EqualsMark"/><h:numbertextbox id="txt_SelectedUnitCount" templateKey="YKK_TXT_SelectedUnitCount_RO"/>&nbsp;&nbsp; 
                  <h:label id="lbl_TotalStockOutQty" templateKey="YKK_LBL_TotalStockOutQty"/><h:label id="lbl_EqualsMark_2" templateKey="YKK_LBL_EqualsMark"/><h:numbertextbox id="txt_TotalStockOutQty" templateKey="YKK_TXT_TotalStockOutQty"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
