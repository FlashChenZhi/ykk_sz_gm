<!-- $Id: Footer.jsp,v 1.1.1.1 2006/08/17 09:33:14 mori Exp $ -->

<!--
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
-->

<!--
 * Footer.jsp
 * <jp>eAWC、eWareNaviで使用します。</jp>
 * <en>This is used in eAWC and in eWareNavi.</en>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:14 $
 * @author  $Author: mori $
-->
<%
	String contextpath2 = request.getContextPath();
%>

<TABLE WIDTH="100%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
	<TR HEIGHT="41">
		<TD BGCOLOR="#DAD9EE" WIDTH="7" HEIGHT="41"><IMG SRC="<%= contextpath2 %>/img/common/spacer.gif" WIDTH="7" HEIGHT="1" BORDER="0"></TD>
		<TD BGCOLOR="#DAD9EE" HEIGHT="41"><IMG SRC="<%= contextpath2 %>/img/common/logo_dp.gif" ALT="" WIDTH="374" HEIGHT="23" BORDER="0"></TD>
	</TR>
	<TR HEIGHT="8">
		<TD WIDTH="7" HEIGHT="8" BACKGROUND="<%= contextpath2 %>/img/common/bg_end.gif"></TD>
		<TD HEIGHT="8" BACKGROUND="<%= contextpath2 %>/img/common/bg_end.gif"></TD>
	</TR>
</TABLE>

