<%-- $Id: SessionTimeout.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ --%>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * <jp>「ログインしていないか、セッションがタイムアウトにより切断されました。」を表示する画面です。</jp>
 * <en>This screen displays the message: "You may not have logged in, or session time out."</en>
 * 
 * <jp>[履歴]</jp>
 * <en>[history]</en>
 * <jp>2003/12/02 update sawa 「ログイン画面へ」がListBox（サブウィンドウ）に表示される場合、Targetはopener.parent.windowに修正した。</jp>
 * <en>2003/12/02 update sawa Corrected: When "To login screen" is displayed on the listBox (sub window), Target will be opener.parent.window.</en>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:14 $
 * @author  $Author: mori $
--%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "private"); 
    response.setDateHeader("Expires", 0);
    String contextpath  = request.getContextPath();
%>
<%-- encoding --%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix="h" %>
<%-- import --%>
<%@ page import = "jp.co.daifuku.bluedog.util.DispResources" %>
<h:html>
	<h:head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" 
			href="<%= contextpath %>/css/common.css" >
		<link rel="stylesheet" type="text/css" 
			href="<%= contextpath %>/css/common_<%= request.getLocale().getLanguage() %>.css" >
	</h:head>
	<script>
	//<jp> ログアウト画面を表示します。</jp>
	//<jp> 「ログイン画面へ」がメインのウィンドウに表示される場合はTargetは"_parent"になります。</jp>
	//<jp> 「ログイン画面へ」がListBox（サブウィンドウ）に表示される場合はTargetはparent.opener.windowになります。</jp>
	//<en> Logout screen is displayed.</en>
	//<en> When "To login screen" is displayed on main window, Target will be "_parent".</en>
	//<en> When "To login screen" is dispalyed on listBox (sub window), Target will be parent.opener.window.</en>
	function dispLoginPage()
	{
		var parentFrameName = "_parent";
		if (opener)
		{
			parentFrameName = parent.opener.window.name;
			window.close();
		}
		window.open('<%= contextpath %>/jsp/login/Logout.jsp', parentFrameName);
	}
	</script>
	<h:page>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr height="39"><td colspan="5" bgcolor="#413a8a" height="39"></td></tr>
	<tr height="251">
		<td bgcolor="#413a8a" width="7" height="251"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
		<td bgcolor="#413a8a" height="251"></td>
		<td align="center" width="1001" height="251" background="<%= contextpath %>/img/project/login/background.gif">
			<table border="0" cellspacing="0" cellpadding="1">
			<tr>
			<td bgcolor="#413a8a">
			<table border="0" cellspacing="0" cellpadding="0" bgcolor="white">
				<tr height="12">
					<td width="9" height="12"></td>
					<td height="12"></td>
					<td width="9" height="12"></td>
				</tr>
				<tr>
					<td width="9"><img src="<%= contextpath %>/img/common/spacer.gif" width="9" height="1" border="0"></td>
					<td nowrap><span class="lbl-black-004"><%= DispResources.getText("MSG-9009") %></span></td>
					<td width="9"><img src="<%= contextpath %>/img/common/spacer.gif" width="9" height="1" border="0"></td>
				</tr>
				<tr>
					<td width="9"><img src="<%= contextpath %>/img/common/spacer.gif" width="9" height="1" border="0"></td>
					<td nowrap align="center"><a href="javascript:dispLoginPage()"><span class="lbl-link-002"><%= DispResources.getText("MSG-9010") %></span></a></td>
					<td width="9"><img src="<%= contextpath %>/img/common/spacer.gif" width="9" height="1" border="0"></td>
				</tr>
				<tr height="12">
					<td width="9" height="12"></td>
					<td height="12"></td>
					<td width="9" height="12"></td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</td>
		<td bgcolor="#413a8a" height="251"></td>
		<td bgcolor="#413a8a" width="7" height="251"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
	</tr>
	<tr><td colspan="5" bgcolor="#413a8a" height="39"><img src="<%= contextpath %>/img/common/logo_menu.gif" width="380" height="24" border="0"></td></tr>
	<tr><td colspan="5" height="7" background="<%= contextpath %>/img/common/menu_bg2.gif"></td></tr>
	</table>
	</h:page>
</h:html>
