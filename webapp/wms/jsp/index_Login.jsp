<!-- $Id: index_Login.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ -->

<!--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
-->

<!--
 * AWC 3.0 Login page
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:14 $
 * @author  $Author: mori $
-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%
	String contextpath  = request.getContextPath();
	String id           = session.getId();
%>
<h:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="Content-Style-Type" content="text/css; charset=utf-8">
	<link rel="stylesheet" type="text/css"
		href="<%= contextpath %>/css/common.css" >
	<link rel="stylesheet" type="text/css"
		href="<%= contextpath %>/css/common_<%= request.getLocale().getLanguage() %>.css" >
	<script>
	<!--
	// the main window object
	var mainWindowObject = 0;
	// the main window target
	var mainWindowName   ="wnd_<%= id %>";

	// show the screen for the Login authentication.
	function showLogin()
	{
		if ( mainWindowObject == 0 )
		{
		}
		else
		{
			mainWindowObject.close();
			mainWindowObject = 0;
		}

		// Open the main window.
		mainWindowObject = 
			window.open( '<%= contextpath %>/login/Login.do', 
						 mainWindowName, 
						 'resizable=yes');
		if( window.name != mainWindowName )
		{
			// The window should close itself.
			window.opener = true;
			window.close();
		}
	}

	// Key operation is invalidated. 
	function getKey()
	{
		// Ctrl + N
		if (window.event.ctrlKey)
		{
			if (window.event.keyCode == '78')
			{
				return false;
			}
		}
	}
	window.document.onkeydown = getKey;
	-->
	</script>
</head>
<body onload="showLogin()" onContextMenu="return false">
</body>
</h:html>
<script language="javascript" src="<%= contextpath %>/jscript/control.js"></script>
<script language="javascript" src="<%= contextpath %>/jscript/user.js"></script>
<script>
windowResize();
</script>