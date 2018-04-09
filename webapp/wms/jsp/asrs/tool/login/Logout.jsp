<%-- $Id: Logout.jsp,v 1.1.1.1 2006/08/17 09:33:16 mori Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
<meta content="bluedog" name=generator>

<%--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
--%>

<%--
 * <jp>メインメニュー画面です。</jp>
 * <jp>メニューアイコンを上下中央位置に配置するように修正 2003/03/18 sawa</jp>
 * <en>Main menu screen</en>
 * <en>Corrected: Changed the location of the menu icons to vertical center.  2003/03/18 sawa</en>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:16 $
 * @author  $Author: mori $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>
<%@ page import = "jp.co.daifuku.common.text.DisplayText" %>
<h:html>
<h:head>
</h:head>
<h:page>
	<body bgcolor="#413A8A" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" onContextMenu="return false">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
			    <span id="message" class="" ></span>
				<td><img src="<%=request.getContextPath()%>/img/project/etc/logo_product_header.gif" border="0"></td>
				<td align="right">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="7"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="7" height="1" border="0"></td>
                            <td><h:submitbutton id="btn_Logout" templateKey="AST_Logout"/></td>
							<td width="7"><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="7" height="1" border="0"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
			                <td height=23></td>
			</tr>
		</table>
	</body>
</h:page>
</h:html>
