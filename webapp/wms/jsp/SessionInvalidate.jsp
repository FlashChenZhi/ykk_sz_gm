<!-- $Id: SessionInvalidate.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ -->

<!--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
-->

<%@ page contentType="text/html; charset=utf-8" %>
<%
	// no-cache
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "private"); 
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="Content-Style-Type" content="text/css; charset=utf-8">
	<script>
	function sessionInvalidate()
	{
		<%
			session.invalidate();
		%>
		window.close();
	}
	</script>
</head>
<body onload="sessionInvalidate()" onContextMenu="return false">
</body>
</html>
