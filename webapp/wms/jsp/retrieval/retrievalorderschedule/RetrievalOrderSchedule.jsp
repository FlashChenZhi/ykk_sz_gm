<%-- $Id: RetrievalOrderSchedule.jsp,v 1.1.1.1 2006/08/17 09:33:22 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:22 $
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
          <TD noWrap><h:tab id="tab" templateKey="W_RetrievalSetting"/></TD>
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
              <TR bgColor=#dad9ee>
                <TD noWrap bgColor=#dad9ee><h:label id="lbl_WorkerCode" templateKey="W_WorkerCode"/></TD>
                <TD bgColor=#dad9ee><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#dad9ee colSpan=3><h:freetextbox id="txt_WorkerCode" templateKey="W_WorkerCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:label id="lbl_PassWord" templateKey="W_Password"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:freetextbox id="txt_PassWord" templateKey="W_Password"/></TD>
                <TD bgColor=#dad9ee colSpan=2></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_ConsignorCode" templateKey="W_ConsignorCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:freetextbox id="txt_ConsignorCode" templateKey="W_ConsignorCode"/><h:submitbutton id="btn_PS_ConsignorCode" templateKey="P_Search"/></TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalPlanDate" templateKey="W_RetrievalPlanDate"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=3><h:datetextbox id="txt_RtrivlPlanDate" templateKey="W_RtrivlPlanDate"/><h:submitbutton id="btn_PS_RetrievalPlanDate" templateKey="P_Search"/></TD>
                <TD bgColor=#b8b7d7 colSpan=2></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StartOrderNo" templateKey="W_StartOrderNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=4><h:freetextbox id="txt_StartOrderNo" templateKey="W_StartOrderNo"/><h:submitbutton id="btn_PS_StartOrderNo" templateKey="P_Search"/><h:label id="lbl_1" templateKey="W_FromTo"/><h:label id="lbl_EndOrderNo" templateKey="W_EndOrderNo"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7><h:freetextbox id="txt_EndOrderNo" templateKey="W_EndOrderNo"/><h:submitbutton id="btn_PS_EndOrderNo" templateKey="P_Search"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_CasePieceFlag" templateKey="W_CasePieceFlag"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD bgColor=#b8b7d7 colSpan=4><h:radiobutton id="rdo_CpfAll" templateKey="W_Cpf_All"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CpfCase" templateKey="W_Cpf_Case"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CpfPiece" templateKey="W_Cpf_Piece"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <h:radiobutton id="rdo_CpfAppointOff" templateKey="W_Cpf_AppointOff"/></TD>
                <TD bgColor=#b8b7d7></TD>
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
                <TD><h:submitbutton id="btn_View" templateKey="View"/></TD>
                <TD></TD>
                <TD></TD>
                <TD></TD>
                <TD>&nbsp; &nbsp; </TD>
                <TD></TD>
                <TD><h:submitbutton id="btn_Clear" templateKey="Clear"/></TD></TR></TBODY></TABLE></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR>
        <TR height=5>
          <TD height=5></TD>
          <TD height=5></TD>
          <TD height=5></TD></TR></TBODY></TABLE>
      <TABLE>
        <TBODY>
        <TR>
          <TD height=5></TD></TR></TBODY></TABLE>
  <%-- lower display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_urt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7>
            <TABLE cellSpacing=0 cellPadding=0 width=80 border=0>
              <TBODY>
              <TR>
                <TD><h:submitbutton id="btn_RetrievalStart" templateKey="W_RetrievalStart"/></TD>
                <TD>&nbsp;&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_AllCheck" templateKey="W_AllCheck"/>&nbsp; 
                  &nbsp;&nbsp; </TD>
                <TD><h:submitbutton id="btn_AllCheckClear" templateKey="W_AllCheckClear"/></TD>
                <TD>&nbsp; &nbsp;&nbsp;</TD>
                <TD><h:submitbutton id="btn_ListClear" templateKey="W_ListClear"/></TD>
                <TD></TD>
                <TD></TD></TR></TBODY></TABLE></TD>
          <TD bgColor=#b8b7d7><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_ult3.gif" width=42 border=0></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD></TD>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD><h:label id="lbl_Consignor" templateKey="W_Consignor"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD><h:freetextbox id="txt_JavaSetConsignorCode" templateKey="W_R_ConsignorCode"/></TD>
                      <TD><h:freetextbox id="txt_JavaSetConsignorName" templateKey="W_R_ConsignorName"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_RetrievalPlanDateT" templateKey="W_RetrievalPlanDate"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD colSpan=2><h:datetextbox id="txt_JavaSetPlanDate" templateKey="W_R_RtrivlPlanDate"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_Allocate_Choice" templateKey="W_Allocate_Choice"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD colSpan=2><h:checkbox id="chk_Allocate_Place_Hira" templateKey="W_Allocate_Place_Hira"/>　　<h:checkbox id="chk_Allocate_Place_Idm" templateKey="W_Allocate_Place_Idm"/>　　<h:checkbox id="chk_Allocate_Place_Asrs" templateKey="W_Allocate_Place_Asrs"/></TD></TR>
                    <TR>
                      <TD><h:label id="lbl_Keppin_Choice" templateKey="W_Keppin_Choice"/></TD>
                      <TD><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                      <TD colSpan=2><h:checkbox id="chk_Keppin_Chk" templateKey="W_CommonUse"/></TD></TR></TBODY></TABLE></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7><h:listcell id="lst_RetrievalOrderSchedule" templateKey="W_RetrievalOrderSchedule"/></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
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
