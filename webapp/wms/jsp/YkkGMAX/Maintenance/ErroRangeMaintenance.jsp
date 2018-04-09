<%-- $Id: ErroRangeMaintenance.jsp,v 1.1 2007/12/13 06:25:11 administrator Exp $ --%>
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
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:11 $
 * @author  $Author: administrator $
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
          <TD><h:label id="lbl_SettingName" templateKey="YKK_LBL_ErroRangeMaintenance"/></TD>
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
          <TD noWrap><h:tab id="tab" templateKey="YKK_TAB_Maintenance"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- upper display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD></TD>
                <TD></TD>
                <TD></TD></TR>
              <TR bgColor=#dad9ee>
                <TD><h:label id="lbl_InOutDivision" templateKey="YKK_LBL_InOutDivision"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:radiobutton id="rdo_Stockin" templateKey="YKK_RDO_StockinRangeChecked"/><h:radiobutton id="rdo_Stockout" templateKey="YKK_RDO_StockoutRange"/>&nbsp;&nbsp; <h:submitbutton id="btn_TerminalNoBrowse" templateKey="YKK_BTN_FnrangeBrowse_POPUP"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD><h:label id="lbl_TerminalNo" templateKey="YKK_LBL_TerminalNo"/><h:label id="lbl_Star1" templateKey="YKK_LBL_Star"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:freetextbox id="txt_TerminalNo" templateKey="YKK_TXT_TerminalNo"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD><h:label id="lbl_FormerProduceLine1" templateKey="YKK_LBL_FormerProduceLine1"/><h:label id="lbl_Star2" templateKey="YKK_LBL_Star"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:freetextbox id="txt_FormerProduceLine1" templateKey="YKK_TXT_FormerProduceLine1"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD><h:label id="lbl_FormerProduceLine2" templateKey="YKK_LBL_FormerProduceLine2"/><h:label id="lbl_Star3" templateKey="YKK_LBL_Star"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:freetextbox id="txt_FormerProduceLine2" templateKey="YKK_TXT_FormerProduceLine2"/></TD></TR>
              <TR bgColor=#dad9ee>
                <TD><h:label id="lbl_DealDivision" templateKey="YKK_LBL_DealDivision"/></TD>
                <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD><h:submitbutton id="btn_Add" templateKey="YKK_BTN_Add"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_Modify" templateKey="YKK_BTN_Modify"/>&nbsp;&nbsp; 
                  <h:submitbutton id="btn_Delete" templateKey="YKK_BTN_Delete"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TerminalLine1Line2" templateKey="YKK_LBL_TerminalLine1Line2"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_TerminalNo_RO" templateKey="YKK_TXT_TerminalNo_RO"/>&nbsp;<h:freetextbox id="txt_FormerProduceLine1_RO" templateKey="YKK_TXT_FormerProduceLine1_RO"/>&nbsp;<h:freetextbox id="txt_FormerProduceLine2_RO" templateKey="YKK_TXT_FormerProduceLine2_RO"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UnitMaxErro" templateKey="YKK_LBL_UnitMaxErro"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_UnitMaxErro" templateKey="YKK_TXT_UnitMaxErro"/><h:label id="lbl_PercentSign1" templateKey="YKK_LBL_PercentSign"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_UnitMinErro" templateKey="YKK_LBL_UnitMinErro"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_UnitMinErro" templateKey="YKK_TXT_UnitMinErro"/><h:label id="lbl_PercentSign2" templateKey="YKK_LBL_PercentSign"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockinMaxErro" templateKey="YKK_LBL_StockinMaxErro"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_StockinMaxErro" templateKey="YKK_TXT_StockinMaxErro"/><h:label id="lbl_PercentSign3" templateKey="YKK_LBL_PercentSign"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockinMinErro" templateKey="YKK_LBL_StockinMinErro"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_StockinMinErro" templateKey="YKK_TXT_StockinMinErro"/><h:label id="lbl_PercentSign4" templateKey="YKK_LBL_PercentSign"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockoutMaxErro" templateKey="YKK_LBL_StockoutMaxErro"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_StockoutMaxErro" templateKey="YKK_TXT_StockoutMaxErro"/><h:label id="lbl_PercentSign5" templateKey="YKK_LBL_PercentSign"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockoutMinErro" templateKey="YKK_LBL_StockoutMinErro"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_StockoutMinErro" templateKey="YKK_TXT_StockoutMinErro"/><h:label id="lbl_PercentSign6" templateKey="YKK_LBL_PercentSign"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_Set" templateKey="YKK_BTN_Set"/>&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="YKK_BTN_Clear"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE></TD></TR>
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
