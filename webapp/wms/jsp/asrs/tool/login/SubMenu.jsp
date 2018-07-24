<%-- $Id: SubMenu.jsp,v 1.1.1.1 2006/08/17 09:33:16 mori Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * <jp>サブメニュー画面です。</jp>
 * <en>Sub menu screen</en>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:16 $
 * @author  $Author: mori $
--%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private"); 
	response.setDateHeader("Expires", 0);
	System.out.println("CONTEXT[" + request.getContextPath() + "]");
%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "java.util.*" %>
<%@ page import = "jp.co.daifuku.common.text.*" %>
<%@ page import = "jp.co.daifuku.common.*" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css" >
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tool.css" >
	<%-- script --%>
	<script language="javascript" src="<%=request.getContextPath()%>/jscript/control.js"></script>
	<script type="text/javascript">
	    window.document.onkeydown = ignoreKeyEvent;
	function _func_openWindow(htmlname, w, h)
	{
		var sWidth  = screen.availWidth;
		var sHeight = screen.availHeight;
		if( w > sWidth ) { w = sWidth }
		if( h > sHeight ) { h = sHeight }
		var x = (sWidth  - w) / 2;
		var y = (sHeight - h) / 2;
		wWindowObject = window.open( htmlname,'frame3',
		 "resizable=yes, menubar=no, directories=no, scrollbars=yes, status=no, location=no, " + 
		 "left=" + x + ", top=" + y + ", width=" + w + ", height=" + h);
	}
	</script>
</head>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a border=0>
  <TBODY>
  <TR>
    <TD>

	<TABLE WIDTH="100%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
<!--<jp> グループコントローラ設定 </jp>-->
<!--<en> Set the group controller. </en>-->
		<TR HEIGHT="6">
			<TD colspan="16" HEIGHT="6" BACKGROUND="<%=request.getContextPath()%>/img/common/bg_up.gif"></TD>
		</TR>
		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#B0ADD3" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0003") %></SPAN></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>

			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0018") %>" onClick=
			"window.open('/wms/asrs/tool/groupcontroller/GroupController.do?TITLE=TMEN-W0003-1','frame2')"></TD>

			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>

<!--<jp> ロケーション設定 </jp>-->
<!--<en> Set the location. </en>-->

		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#C3C0E0" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0004") %></SPAN></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0019") %>" onClick="javascript:window.open('/wms/asrs/tool/warehouse/WareHouse.do?TITLE=TMEN-W0004-1','frame2');"></TD>


			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0020") %>" onClick="javascript:window.open('/wms/asrs/tool/aisle/Aisle.do?TITLE=TMEN-W0004-2','frame2');"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0021") %>" onClick="javascript:window.open('/wms/asrs/tool/unavailablelocation/UnavailableLocation.do?TITLE=TMEN-W0004-3','frame2');"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#C3C0E0"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>

<!--<jp> ハードゾーン設定 </jp>-->
<!--<en> Set the hard zone. </en>-->

		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#B0ADD3" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0005") %></SPAN></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0022") %>" onClick="javascript:window.open('/wms/asrs/tool/hardzone/HardZone.do?TITLE=TMEN-W0005-1','frame2');"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0038") %>" onClick="javascript:window.open('/wms/asrs/tool/individuallyhardzone/IndividuallyHardZone.do?TITLE=TMEN-W0005-2','frame2');"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0013") %>" onClick="javascript:window.open('/wms/asrs/tool/hardzonelist/HardZoneList.do?TITLE=TMEN-W0005-3','frame2');"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>

<!--<jp> ステーション設定 </jp>-->
<!--<en> Set the station. </en>-->

		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#C3C0E0" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0007") %></SPAN></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0024") %>" onClick=
			"window.open('/wms/asrs/tool/station/Station.do?TITLE=TMEN-W0007-1','frame2')"></TD>


			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0025") %>" onClick="window.open('/wms/asrs/tool/dummystation/DummyStation.do?TITLE=TMEN-W0007-2','frame2')"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0026") %>" onClick="window.open('/wms/asrs/tool/workplace/WorkPlace1.do?TITLE=TMEN-W0007-3','frame2')"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#C3C0E0"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>

<!--<jp> ステーション設定 </jp>-->
<!--<en> Set the station. </en>-->

		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#B0ADD3" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0007") %></SPAN></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>

			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0027") %>" onClick="window.open('/wms/asrs/tool/route/Route.do?TITLE=TMEN-W0007-4','frame2')"></TD>


			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>

<!--<jp> 機器情報設定 </jp>-->
<!--<en> Set the machine information. </en>-->

		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#C3C0E0" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0010") %></SPAN></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0018") %>" onClick="window.open('/wms/asrs/tool/machine/Machine.do?TITLE=TMEN-W0010-1','frame2')"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"></TD>

			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#C3C0E0"></TD>
			<TD BGCOLOR="#C3C0E0" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#C3C0E0"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>

<!--<jp> システム定義ファイル生成 </jp>-->
<!--<en> Generate the system definition file. </en>-->

		<TR>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD NOWRAP BGCOLOR="#B0ADD3" WIDTH="220"><SPAN CLASS="lbl-black-003"><%= DisplayText.getText("TMEN-W0014") %></SPAN></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="6"><IMG SRC="<%=request.getContextPath()%>/img/common/icon_colon2.gif" WIDTH="6" HEIGHT="29" BORDER="0"></TD>

			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0018") %>" onClick="window.open('/wms/asrs/tool/systemdata/SystemData.do?TITLE=TMEN-W0014-1','frame2')"></TD>

			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"><INPUT TYPE="button" CLASS="btn-toolsubmenu-001" NAME="" VALUE="<%= DisplayText.getText("TBTN-W0039") %>" onClick="window.open('/wms/asrs/tool/systemdatatemp/SystemDataTemp.do?TITLE=TMEN-W0014-2','frame2')"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#B0ADD3" WIDTH="24"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="24" HEIGHT="1" BORDER="0"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#B0ADD3"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7"><IMG SRC="<%=request.getContextPath()%>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		</TR>
		<TR HEIGHT="1">
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="6" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="24" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD WIDTH="100%" HEIGHT="1" BGCOLOR="#DAD9EE"></TD>
			<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="1"></TD>
		</TR>
	</TABLE>
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
    height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
</BODY>
</html>

