<%-- $Id: MainMenuB.jsp,v 1.1.1.1 2006/08/17 09:33:20 mori Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page errorPage = "/jsp/MenuError.jsp" %>
<%@ page import = "java.util.Locale" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "jp.co.daifuku.bluedog.util.DispResources" %>
<%@ page import = "jp.co.daifuku.dbhandler.ResultMap" %>
<%@ page import = "jp.co.daifuku.authentication.DfkUserInfo" %>
<%@ page import = "jp.co.daifuku.authentication.UserInfoHandler" %>
<%
	// Login User information
	DfkUserInfo     userinfo = (DfkUserInfo)session.getAttribute(jp.co.daifuku.UserInfo.class.getName());
	UserInfoHandler handler = new UserInfoHandler(userinfo);
	// Main Menu List Set
	List mainmenu = userinfo.getMainMenu();

	//Display flag of the Logout button. 
	boolean displayLogoutBtnFlag = userinfo.getDisplayLogoutBtnFlag();

	String contextpath  = request.getContextPath();
	String locale       = request.getLocale().toString();
%>
<meta content="bluedog" name=generator>
<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Main menu screen.
 * History
 * ---------------------------------------------------------------------------------------
 * Date       Author     Comments
 * 2004/08/20 N.Sawa     created
 * 2005/06/20 N.Sawa     for BlueDOG V3.4.6
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:20 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<h:html>
    <h:head>
    <script language="JavaScript" src="<%= contextpath %>/jscript/cookie.js"></script>

    <script language="JavaScript" src="<%= contextpath %>/jscript/common.js"></script>

    <script>
    setContextPath("<%= contextpath %>");
    setMenuCount(<%= mainmenu.size() %>);

    var wSubmitFlag=true;
    function openSubMenu(id)
    {
        var subMenuURL = "<%= contextpath %>/menu/SubMenu.do?id=" + id;
        window.open(subMenuURL, "frame2");
        setTimeout("wSubmitFlag=true;", 500);
    }
    </script>

    <style>
    body {
      background-color : #413A8A;
      background-image : url("<%= contextpath %>/img/project/menu/typeb/bg_img.gif");
    }
    </style>
    </h:head>
    <h:page>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="201"><img src="<%= contextpath %>/img/project/menu/typeb/logo_product.gif" width="246" height="77" border="0"></td>
<%
				for (Iterator iter = mainmenu.iterator(); iter.hasNext();)
				{
					ResultMap map  = (ResultMap)iter.next();
					String menuid  = map.getTrimmedString(UserInfoHandler.MAINMENU_MENUID);
					if (!map.getTrimmedString(UserInfoHandler.MAINMENU_SHOWTYPE).equals("2"))
					{
%>
				<!-- Menu Start ------------------------------------------------>
				<td width="77">
					<img src    = "<%= contextpath %>/img/project/menu/typeb/icon2_<%= menuid %>_0_<%= locale %>.gif" 
				         width  = "77" 
				         height = "77" 
				         border = "0"
					     id     = "menu_<%= menuid %>"
<%
						if (map.getTrimmedString(UserInfoHandler.MAINMENU_SHOWTYPE).equals("0"))
						{
%>
				         style       = "cursor:hand;"
					     onmouseover = "change_ImageB('over',  'menu_<%= menuid %>', '<%= menuid %>','<%= locale %>');"
					     onmousedown = "change_ImageB('down', 'menu_<%= menuid %>', '<%= menuid %>','<%= locale %>');"
					     onmouseout  = "change_ImageB('out',  'menu_<%= menuid %>', '<%= menuid %>','<%= locale %>');"
					     onmouseup   = "change_ImageB('up',  'menu_<%= menuid %>', '<%= menuid %>','<%= locale %>');"
						 onclick     = " if(wSubmitFlag!=true) { event.cancelBubble=true; return; }
									     wSubmitFlag=false; 
									     openSubMenu('<%= menuid %>');
										 change_ImageB('click', 'menu_<%= menuid %>', '<%= menuid %>','<%= locale %>');"
<%
						}
						else
						{
%>		
						 style      = "cursor: not-allowed;" 
<%
						}
%>					     
					></td>
				<td width="1"><img src="<%= contextpath %>/img/common/spacer.gif" width="1" height="1" border="0"></td>
				<!-- Menu End -------------------------------------------------->
<%
					}
				}
%>
			</tr>
			</table>
			</td>
			<td align="right" valign="bottom">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr><td nowrap><div class="lbl-white-003"><%= DispResources.getText("LBL-9005") %>&nbsp;:&nbsp;</div></td>
				<td>
					<table border="0" cellpadding="0" cellspacing="1" bgcolor="#cccad2">
					<tr>
						<td>
						<table border="0" cellspacing="0" cellpadding="0" bgcolor="#413a8a" height="22">
						<tr>
							<td width="8"></td>
							<td nowrap><span class="lbl-white-004"><%= handler.getUserName() %></span></td>
							<td width="8"></td>
						</tr>
						</table>
						</td>
					</tr>
					</table>
				</td>
				<td width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
				<!-- log out button -->
				<td>
				<% 
					if(displayLogoutBtnFlag)
					{
				%>
						<h:submitbutton id="btn_Logout" templateKey="Logout"/>
				<% 
					}
				%>
				</td>
				<td width="7"><img src="<%= contextpath %>/img/common/spacer.gif" width="7" height="1" border="0"></td>
			</tr>
			<tr height="5"><td colspan="5"><img src="<%= contextpath %>/img/common/spacer.gif" width="1" height="5" border="0"></td></tr>
			</table>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</h:page>
	<script>
	function doOnunload()
	{
		if(  self.closed ||
			(event.clientX <= -8000 && event.clientY <= -9000) ||
			(event.clientX >= 31000 && event.clientY >= 32000)
              )
		{
			window.open('<%= contextpath %>/jsp/SessionInvalidate.jsp', '',"resizable=yes,scrollbars=yes");
		}
	}
	</script>
</h:html>
