<%-- $Id: Logout.jsp,v 1.2 2007/03/07 07:57:13 suresh Exp $ --%>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * Logout screen.
 * @version $Revision: 1.2 $, $Date: 2007/03/07 07:57:13 $
 * @author  $Author: suresh $
--%>
<%-- encoding --%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private");
	response.setDateHeader("Expires", 0);
	String contextpath  = request.getContextPath();
%>
<html>
	<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<script type="text/javascript">
	// show the screen for the Login authentication.
	function showLogin()
	{
	    // search most parent window.
	    var pWind = window;
	    while( pWind.opener != null && pWind.opener != "undefined" && !isClosed(pWind.opener) && 
	           pWind.opener.opener != null && pWind.opener.opener != "undefined"
	           && !isClosed(pWind.opener.opener) )
	    {
	        pWind = pWind.opener;
	    }
	    if( pWind.opener != null && pWind.opener != "undefined" && !isClosed(pWind.opener))
	    {
	        pWind = window;
	    }
	
	    // Open the main window.
	    var mainWindowObject = pWind.open( '<%= contextpath %>/jsp/index.jsp', '_parent');
	}
	
	function isClosed(windowObject)
	{
	    try
	    {
	        return ( windowObject == null || windowObject.closed );
	    }
	    catch(e)
	    {
	        return true;
	    }
	}
	</script>
	</head>
	<body onLoad="showLogin()" oncontextmenu="return false">
	</body>
</html>
