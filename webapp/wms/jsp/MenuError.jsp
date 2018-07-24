<%-- $Id: MenuError.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ --%>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * <jp>メインメニュー用のエラーページです。</jp>
 * <en>This is the error page for the main menu.</en>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:14 $
 * @author  $Author: mori $
--%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "private"); 
    response.setDateHeader("Expires", 0);
%>
<%-- defined this error page --%>
<%@ page isErrorPage = "true" %>
<%-- encoding --%>
<%@ page contentType="text/html;charset=utf-8"%>
<%-- logic --%>
<%
	String contextpath  = request.getContextPath();
	//<jp> 例外表示</jp>
	//<en> Display the exception.</en>
	exception.printStackTrace();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
	</head>
	<body leftmargin="0" marginheight="0" marginwidth="0" topmargin="0" oncontextmenu="return false"
		<%-- style="{border-bottom : 2px solid #b8b7d7;}" --%> bgcolor="#413a8a">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="<%= contextpath %>/img/project/etc/logo_product_header.gif" border="0"></td>
			<td colspan="5" align="right" bgcolor="#413a8a" height="38"><img 
				src="<%= contextpath %>/img/common/logo_top.gif" width="124" height="27" border="0"></td>
		</tr>
		</table>
	</body>
</html>
