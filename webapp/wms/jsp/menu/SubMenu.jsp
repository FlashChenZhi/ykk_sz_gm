<%-- $Id: SubMenu.jsp,v 1.1.1.1 2006/08/17 09:33:20 mori Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page errorPage   = "/jsp/DebugError.jsp" %>
<%@ page import = "java.util.Locale" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "jp.co.daifuku.bluedog.util.DispResources" %>
<%@ page import = "jp.co.daifuku.dbhandler.ResultMap" %>
<%@ page import = "jp.co.daifuku.authentication.DfkUserInfo" %>
<%@ page import = "jp.co.daifuku.authentication.UserInfoHandler" %>
<%
	// no-cache
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private"); 
	response.setDateHeader("Expires", 0);

	//<jp> 入庫、出庫などの種類を判別する。</jp>
	//<en> Determine the types : storage or retrieval.</en>
	String requestid    = (String)request.getAttribute("id");
	String contextpath  = request.getContextPath();
	Locale locale       = request.getLocale();

	// Login User information
	DfkUserInfo     userinfo = (DfkUserInfo)session.getAttribute(jp.co.daifuku.UserInfo.class.getName());

	// Main Menu List Set
	List mainmenu = userinfo.getMainMenu();
	Iterator iterator = mainmenu.iterator();

	ResultMap map = null;
	String menuid = null;
	String menukey = null;
	while (iterator.hasNext())
	{
		map    = (ResultMap)iterator.next();
		menuid = map.getTrimmedString(UserInfoHandler.MAINMENU_MENUID);
		if (requestid.equals(menuid))
		{
			menukey = map.getTrimmedString(UserInfoHandler.MAINMENU_MENURESOURCEKEY);
			break;
		}
	}

	// Function List Set
	List menulist = userinfo.getUserFunction();
	iterator = menulist.iterator();
%>
<%!
	protected String getLineStyle(int argLineNo)
	{
		if (argLineNo % 2 == 0)
		{
			return "#B0ADD3";
		}
		else
		{
			return "#C3C0E0";
		}
	}
%>
<meta content="bluedog" name=generator>
<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Sub menu screen.
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/08/20 N.Sawa     created
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:20 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<html>
<head>
	<link rel="stylesheet" type="text/css" 
		href="<%= contextpath %>/css/common.css" >
	<link rel="stylesheet" type="text/css" 
		href="<%= contextpath %>/css/common_<%= request.getLocale().getLanguage() %>.css" >
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<%-- script --%>
	<script language="javascript" src="<%= contextpath %>/jscript/control.js"></script>
	<script type="text/javascript">
		window.document.onkeydown = ignoreKeyEvent;
		
		var sendable = true;
		function openWindow()
		{
			if (sendable)
			{
				sendable = false;
				window.open(arguments[0], arguments[1]);
			}
		}
		
		</script>
</head>
<body leftmargin="0" marginheight="0" marginwidth="0" topmargin="0" oncontextmenu="return false">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr height="6">
		<td colspan="16" height="6">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr height="6">
			<td colspan="16" height="6" >
			<table bgcolor="#413a8a" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="7"></td>
				<td><span class="lbl-white-001"><%= DispResources.getText(menukey) + DispResources.getText("TLE-9001") %></span></td>
				<td align="right"></td>
				<td width="7"></td>
			</tr>
			<tr height="2"><td colspan="4" height="4"></td></tr>
			</table>
			</td>
		</tr> 
		<tr height="6"><td height="6" background="<%= contextpath %>/img/common/bg_up.gif"></td></tr>
		<tr height="10"><td bgcolor="#dad9ee" height="10"></td></tr>
		</table>
		</td>
	</tr>
<%
	int    buttoncounter  = 0;
	int    lineno         = 0;
	boolean firstflag     = false;
	String bgcolor        = getLineStyle(lineno);
	String tempmainfuncid = "";
	while (iterator.hasNext())
	{
		map    = (ResultMap)iterator.next();
		menuid = map.getTrimmedString(UserInfoHandler.MAINMENU_MENUID);

		if (requestid.equals(menuid))
		{
			String mainfuncid   = map.getTrimmedString(UserInfoHandler.FUNCTION_MAINFUNCTIONID);
			String functionname = DispResources.getText(map.getTrimmedString(UserInfoHandler.FUNCTION_FUNCTIONRESOURCEKEY));
			String buttonvalue  = DispResources.getText(map.getTrimmedString(UserInfoHandler.FUNCTIONMAP_BUTTONRESOURCEKEY));
			String uri          = map.getTrimmedString(UserInfoHandler.FUNCTIONMAP_URI);
			String title        = map.getTrimmedString(UserInfoHandler.FUNCTIONMAP_PAGENAMERESOURCEKEY);
			String functionid   = map.getTrimmedString(UserInfoHandler.FUNCTIONMAP_FUNCTIONID);
			String frame        = map.getTrimmedString(UserInfoHandler.FUNCTIONMAP_FRAMENAME);

			if (!tempmainfuncid.equals(mainfuncid))
			{
				if (firstflag)
				{

					for (int i = 0; i < (4 - buttoncounter); i++)
					{
%>
				<td bgcolor="<%= bgcolor %>"            height="1"></td>
				<td bgcolor="<%= bgcolor %>" width="24" height="1"></td>
<%
					}
%>
				<td bgcolor="<%= bgcolor %>" width="100%" height="1"></td>
				<td bgcolor="#dad9ee"        width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>

				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>

				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
<%
					bgcolor = getLineStyle(lineno);
				}
%>
			<tr>
				<td bgcolor="#dad9ee"        width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= bgcolor %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= bgcolor %>" width="220" nowrap><span class="lbl-black-003"><%= functionname %></span></td>
				<td bgcolor="<%= bgcolor %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= bgcolor %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= bgcolor %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
<%
				firstflag = true;
				buttoncounter = 1;
				tempmainfuncid = mainfuncid;
				lineno++;
			}
			else
			{
				buttoncounter++;
			}
%>
				<td bgcolor="<%= bgcolor %>"><input type="button" class="btn-submenu-001" name="" value="<%= buttonvalue %>" 
					onclick="openWindow('<%= contextpath %>/<%= uri %>.do?PARAM=<%= title %>,<%= functionid %>,<%= menuid %>', '<%= frame %>');"></td>
				<td bgcolor="<%= bgcolor %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
<%
		}
		else
		{
			if (lineno > 0)
			{
				break;
			}
		}
	}

	if (lineno > 0)
	{
		for (int i = 0; i < (4 - buttoncounter); i++)
		{
%>
				<td bgcolor="<%= bgcolor %>"            height="1"></td>
				<td bgcolor="<%= bgcolor %>" width="24" height="1"></td>
<%
		}
%>
				<td bgcolor="<%= bgcolor %>" width="100%" height="1"></td>
				<td bgcolor="#dad9ee"        width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>

				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>

				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
<%
	}
%>
	<tr height="6"><td colspan="16" height="6" ><%@ include file="/jsp/Footer.jsp" %></td></tr>
	</table>
</body>
</html>
