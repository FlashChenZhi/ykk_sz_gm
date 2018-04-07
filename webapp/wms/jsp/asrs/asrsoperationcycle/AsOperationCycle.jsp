<%-- $Id: AsOperationCycle.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ --%>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:14 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
  <script>
	function dynamicAction()
	{
		if (targetElement == "btn_Query")
		{
			from = " ";
			to = " ";
			if (!_isValueInput('txt_DateFrom')) 
			{
				if (_isValueInput('txt_TimeFrom')) 
				{
					document.all('txt_DateFrom').focus();
					// 6023445=開始日付を入力してください。
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("6023445") %>',2);
					return true;
				}
			}
			else
			{
				// 大小チェックを行うため日付のフォーマットを行う
				from = document.all('txt_DateFrom').value;
				if (!_isValueInput('txt_TimeFrom')) 
				{
					from = from + "00:00:00";
				}
				else
				{
					from = from + document.all('txt_TimeFrom').value;
				}
			}

			if (!_isValueInput('txt_DateTo')) 
			{
				if (_isValueInput('txt_TimeTo')) 
				{
					document.all('txt_DateTo').focus();
					// 6023446=終了日付を入力してください。
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("6023446") %>',2);
					return true;
				}
			}
			else
			{
				// 大小チェックを行うため日付のフォーマットを行う
				to = document.all('txt_DateTo').value;
				if (!_isValueInput('txt_TimeTo')) 
				{
					to = to + "23:59:59";
				}
				else
				{
					to = to + document.all('txt_TimeTo').value;
				}
			}
			
			if (validate(from) && validate(to))
			{
				// 大小チェックを行う
				if (!_isCorrectRange(from, to))
				{
					document.all('txt_DateFrom').focus();
					// 6023182=検索開始日時は、検索終了日時より前の日付にしてください。
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("6023182") %>',2);
					return true;
				}
				
			}
		}

		return false;
		
	}
  </script>
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
          <TD noWrap><h:tab id="tab_Query" templateKey="W_Query"/></TD>
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
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_WareHouse" templateKey="W_WareHouse"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:pulldown id="pul_WareHouse" templateKey="W_WareHouse"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StartDateTime" templateKey="W_SrchStrtDateTime"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:datetextbox id="txt_DateFrom" templateKey="W_SerchStartDate"/><h:timetextbox id="txt_TimeFrom" templateKey="W_SearchStartTime"/><h:label id="lbl_FromTo" templateKey="W_FromTo"/><h:label id="lbl_EndDateTime" templateKey="W_EndSearchDateTime"/><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"><h:datetextbox id="txt_DateTo" templateKey="W_SerchEndDate"/><h:timetextbox id="txt_TimeTo" templateKey="W_SearchEndTime"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7>&nbsp;&nbsp;&nbsp; </TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:submitbutton id="btn_Query" templateKey="P_Query"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7 colSpan=4 height=1></TD></TR>
              <TR>
                <TD noWrap bgColor=#dad9ee colSpan=4 height=6></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_MachineNo" templateKey="W_RMNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_JavaSetMachineNo" templateKey="W_JavaSet"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_TotalCarryQty" templateKey="W_TotalCarryQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_JavaSetTotalCarryQty" templateKey="W_JavaSet"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageCarryQty" templateKey="W_StorageCarryQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_JavaSetStorageCarryQty" templateKey="W_JavaSet"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalCarryQty" templateKey="W_RetrievalCarryQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_JavaSetRetrievalCarryQty" templateKey="W_JavaSet"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StorageItemQty" templateKey="W_StorageItemQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_JavaSetStorageItemQty" templateKey="W_JavaSet"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_RetrievalItemQty" templateKey="W_RetrievalItemQty"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_JavaSetRetrievalItemQty" templateKey="W_JavaSet"/></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
                <TD><h:submitbutton id="btn_ItemDetails" templateKey="P_ItemDetails"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h:submitbutton id="btn_Clear" templateKey="Clear"/>&nbsp; 
                </TD>
                <TD></TD>
                <TD></TD>
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
