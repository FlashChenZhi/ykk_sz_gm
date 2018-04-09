<%-- $Id: SubMenu.jsp,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import = "java.util.Locale" %>
<%@ page import = "jp.co.daifuku.bluedog.util.DispResources" %>
<%@ page import = "jp.co.daifuku.tools.util.ToolParam" %>
<%@ page import = "jp.co.daifuku.tools.display.web.menu.SubMenuBusiness" %>
<%@ page import = "jp.co.daifuku.tools.ToolConstants" %>

<%
	// no-cache
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private"); 
	response.setDateHeader("Expires", 0);

	String contextpath  = request.getContextPath();
	Locale locale       = request.getLocale();
	String product		= (String)request.getParameter("Product");
	String menuType		= (String)request.getParameter("MenuType");
	
	//For SessionTimeout.jsp
	application.setAttribute("MenuToolProductKey", product);
	application.setAttribute("MenuToolMenuTypeKey", menuType);
	
	int line = 0;
%>

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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:06 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<html>
<head>
<title>User/Menu Tool</title>
	<link rel="stylesheet" type="text/css" 
		href="<%= contextpath %>/css/common.css" >
	<link rel="stylesheet" type="text/css" 
		href="<%= contextpath %>/css/common_<%= request.getLocale().getLanguage() %>.css" >
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<%-- script --%>
	<script language="javascript" src="<%= contextpath %>/jscript/control.js"></script>
	<script type="text/javascript">
		window.document.onkeydown = ignoreKeyEvent;
	</script>
</head>
<body leftmargin="0" marginheight="0" marginwidth="0" topmargin="0" oncontextmenu="return false">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr height="6">
		<td colspan="8" height="6">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr height="6">
			<td colspan="7" height="6" >
			<table bgcolor="#413a8a" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="7"></td>
				<td><span class="lbl-white-001"><%= DispResources.getText("TLE-T0022") %></span></td>
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
	<%-- MainMenu --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("MainMenu", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0023") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("MainMenu", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/mainmenu/MainMenu.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
			if( SubMenuBusiness.isDisplayButton("MainMenu.Order", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0004") %>" 
					onclick="window.open('/tools/mainmenuorder/MainMenuOrder.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
		<%-- SubMenu --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("SubMenu", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0024") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("SubMenu", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/function/Function.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
			if(SubMenuBusiness.isDisplayButton("SubMenu.Order", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0004") %>" 
					onclick="window.open('/tools/functionorder/FunctionOrder.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
	<%-- SubMenuButton --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("SubMenuButton", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0025") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("SubMenuButton", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/functionmap/FunctionMap1.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
			if( SubMenuBusiness.isDisplayButton("SubMenuButton.Order", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0004") %>" 
					onclick="window.open('/tools/functionmaporder/FunctionMapOrder.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
		<%-- Role --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("Role", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0026") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("Role", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/role/Role.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
	<%-- RoleMap --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("RoleMap", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0027") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("RoleMap", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/rolemap/RoleMap.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
		<%-- User --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("User", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0028") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("User", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/user/User.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
	<%-- Terminal --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("Terminal", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0029") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("Terminal", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/terminal/Terminal.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
		<%-- System --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("System", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0030") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("System", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/authenticationsystem/AuthenticationSystem.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
	<%-- FinalCheck --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("FinalCheck", menuType))
		{
			line++;
	%>
			<tr>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0031") %></span></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
				<td bgcolor="<%= getBGColor(line) %>" width="100%">
		<%
			if(SubMenuBusiness.isDisplayButton("FinalCheck", menuType))
			{
		%>
				<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0003") %>" 
					onclick="window.open('/tools/finalcheck/FinalCheck.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
				<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
		<%
			}
		%>
				</td>
				<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="1">
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee"              height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="6"    height="1"></td>
				<td bgcolor="#dad9ee" width="24"   height="1"></td>
				<td bgcolor="#dad9ee" width="100%" height="1"></td>
				<td bgcolor="#dad9ee" width="7"    height="1"></td>
			</tr>
	<%
		}
	%>
	<%-- Backup --%>
	<%
		if(SubMenuBusiness.isDisplayFunction("Backup", menuType))
		{
			if((product.equals(jp.co.daifuku.tools.ToolConstants.PRODUCT_AWC) && menuType.equals(jp.co.daifuku.tools.ToolConstants.MENU_CUSTOMER)) || !product.equals(jp.co.daifuku.tools.ToolConstants.PRODUCT_AWC))
			{
				line++;
	%>
				<tr>
					<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
					<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
					<td bgcolor="<%= getBGColor(line) %>" width="220" nowrap><span class="lbl-black-003"><%= DispResources.getText("TLE-T0032") %></span></td>
					<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
					<td bgcolor="<%= getBGColor(line) %>" width="6"><img src="<%= contextpath %>/img/common/icon_colon2.gif" width="6" height="29" border="0"></td>
					<td bgcolor="<%= getBGColor(line) %>" width="24"><img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0"></td>
					<td bgcolor="<%= getBGColor(line) %>" width="100%">
			<%
				if(SubMenuBusiness.isDisplayButton("Backup", menuType))
				{
			%>
					<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0005") %>" 
						onclick="window.open('/tools/backup/BackUp.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
					<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
			<%
				}
				if( SubMenuBusiness.isDisplayButton("Backup.Restore", menuType))
				{
			%>
					<input type="button" class="btn-submenu-001" name="" value="<%= DispResources.getText("BTN-T0006") %>" 
						onclick="window.open('/tools/restore/Restore.do?Product=<%= product %>&MenuType=<%= menuType %>', '_self');">
					<img src="<%= contextpath %>/img/common/spacer.gif" width="24" height="1" border="0">
			<%
				}
			%>
					</td>
					<td bgcolor="#dad9ee" width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				</tr>
				<tr height="1">
					<td bgcolor="#dad9ee" width="7"    height="1"></td>
					<td bgcolor="#dad9ee" width="24"   height="1"></td>
					<td bgcolor="#dad9ee"              height="1"></td>
					<td bgcolor="#dad9ee" width="24"   height="1"></td>
					<td bgcolor="#dad9ee" width="6"    height="1"></td>
					<td bgcolor="#dad9ee" width="24"   height="1"></td>
					<td bgcolor="#dad9ee" width="100%" height="1"></td>
					<td bgcolor="#dad9ee" width="7"    height="1"></td>
				</tr>
	<%
			}
		}	
	%>
	
	
	
	<tr height="6"><td colspan="8" height="6" ><%@ include file="/jsp/Footer.jsp" %></td></tr>
	</table>
</body>
</html>
<%!

	private String getBGColor(int i)
	{
		if(i%2 == 0) return "#C3C0E0";
		else return "#B0ADD3";
	}

%>