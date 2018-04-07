<%-- $Id: StartInventoryCheck.jsp,v 1.1.1.1 2006/08/17 09:33:15 mori Exp $ --%>
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
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="In_SettingName"/></TD>
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
          <TD noWrap><h:tab id="tab_WorkSet" templateKey="W_WorkSet"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>

  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD>
                        <TABLE>
                          <TBODY>
                          <TR>
                            <TD><h:label id="lbl_WorkerCode" templateKey="W_WorkerCode"/></TD>
                            <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD></TR>
                          <TR>
                            <TD><h:label id="lbl_WareHouse" templateKey="W_WareHouse"/></TD>
                            <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD></TR>
                          <TR>
                            <TD><h:label id="lbl_WorkPlaceStation" templateKey="W_WorkPlaceStation"/></TD>
                            <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD></TR></TBODY></TABLE></TD>
                      <TD>
                        <TABLE>
                          <TBODY>
                          <TR>
                            <TD><h:freetextbox id="txt_WorkerCode" templateKey="W_WorkerCode"/></TD>
                            <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <h:label id="lbl_11" templateKey="W_Password"/></TD>
                            <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                            <TD><h:freetextbox id="txt_Password" templateKey="W_Password"/></TD></TR>
                          <TR>
                            <TD colSpan=4><h:pulldown id="pul_WareHouse" templateKey="W_WareHouse"/></TD></TR></TBODY></TABLE>
                        <TABLE>
                          <TBODY>
                          <TR>
                            <TD><h:linkedpulldown id="pul_WorkPlace" templateKey="W_WorkPlace"/><h:linkedpulldown id="pul_Station" templateKey="W_Station"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee colSpan=3></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7 colSpan=2>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/><h:submitbutton id="btn_PSearchConsignorCode" templateKey="P_Search"/></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_StartLocation" templateKey="W_StartLocation"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:formattextbox id="txt_StartLocation" templateKey="W_F_Location"/><h:submitbutton id="btn_PSearchStartLocation" templateKey="P_Search"/></TD>
                      <TD><h:label id="lbl_20" templateKey="W_FromTo"/></TD>
                      <TD><h:label id="lbl_EndLocation" templateKey="W_EndLocation"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:formattextbox id="txt_EndLocation" templateKey="W_F_Location"/><h:submitbutton id="btn_PSearchEndLocation" templateKey="P_Search"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_StartItemCode" templateKey="W_StartItemCode"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_StartItemCode" templateKey="W_StartItemCode"/><h:submitbutton id="btn_PSearchStartItemCode" templateKey="P_Search"/></TD>
                      <TD><h:label id="lbl_19" templateKey="W_FromTo"/></TD>
                      <TD><h:label id="lbl_EndItemCode" templateKey="W_EndItemCode"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD>&nbsp;<h:freetextbox id="txt_EndItemCode" templateKey="W_EndItemCode"/><h:submitbutton id="btn_PSearchEndItemCode" templateKey="P_Search"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_InventoryCheck" templateKey="W_InventoryCheck"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:checkbox id="chk_CommonUse" templateKey="W_CommonUse"/></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_Setting" templateKey="Setting_ProcessMessage"/></TD>
                <TD>　&nbsp; </TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_Clear" templateKey="Clear"/></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
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
