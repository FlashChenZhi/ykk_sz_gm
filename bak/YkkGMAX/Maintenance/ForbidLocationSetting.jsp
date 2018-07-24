<%-- $Id: ForbidLocationSetting.jsp,v 1.1 2007/12/13 06:25:11 administrator Exp $ --%>
<%@ page contentType="text/html;charset=utf-8" %>
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
 * @version $Revision: 1.1 $, $Date: 2007/12/13 06:25:11 $
 * @author  $Author: administrator $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%@ page import = "java.sql.Connection" %>
<%@ page import="java.text.*" %>
<%@ page import="jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager" %>
<%@ page import="jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre" %>
<%@ page import="java.util.HashMap" %>
<%@ page import = "jp.co.daifuku.common.text.*" %>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
</h:head>
<h:page>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#dad9ee border=0>
  <TBODY>
  <TR>
    <TD>
  <%-- title --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD width=7 height=24></TD>
          <TD><h:label id="lbl_SettingName" templateKey="YKK_LBL_ForbidLocationSetting"/></TD>
          <TD style="PADDING-TOP: 1px" align=right 
          background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
          height=24><h:linkbutton id="btn_Help" templateKey="Help"/> 
          </TD>
          <TD width=7 height=24></TD></TR></TBODY></TABLE>
  <%-- message --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#413a8a 
        border=0><TBODY>
        <TR>
          <TD colSpan=4 height=4></TD></TR>
        <TR>
          <TD width=7 height=23></TD>
          <TD width=4 bgColor=white height=23></TD>
          <TD bgColor=white height=23><h:message id="message" templateKey="OperationMsg"/></TD>
          <TD width=7 height=23></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2></TD></TR></TBODY></TABLE>
  <%-- tab --%>
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=2>
          <TD bgColor=#413a8a height=2><IMG height=2 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=1 border=0></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" 
      background=<%=request.getContextPath()%>/img/control/tab/Tab_BackGround_Spacer.gif 
      border=0>
        <TBODY>
        <TR height=28>
          <TD width=7><IMG src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7></TD>
          <TD width=7></TD>
          <TD noWrap><h:tab id="tab" templateKey="YKK_TAB_Setting"/></TD>
          <TD width="90%" height=28></TD>
          <TD><h:submitbutton id="btn_ToMenu" templateKey="To_Menu"/></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=5>
          <TD width="100%"></TD></TR></TBODY></TABLE>

  <%-- display --%>
  <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 width="98%" 
      bgColor=#b8b7d7 border=0>
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR bgColor=#dad9ee height=5>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD>
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Bank" templateKey="YKK_LBL_Bank"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="60%" bgColor=#b8b7d7><h:pulldown id="pul_Bank" templateKey="YKK_PUL_Bank"/><h:submitbutton id="btn_Show" templateKey="YKK_BTN_Show_NA"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_BayLevel" templateKey="YKK_LBL_BayLevel"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_LocationNo" templateKey="YKK_LBL_LocationNo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="60%" bgColor=#b8b7d7><h:formattextbox id="txt_LocationNoFrom" templateKey="YKK_TXT_LocationNoFrom_NoBank"/><h:label id="lbl_to" templateKey="YKK_LBL_to"/><h:formattextbox id="txt_LocationNoTo" templateKey="YKK_TXT_LocationNoTo_NoBank"/><h:checkbox id="chk_RangeSet" templateKey="YKK_CHK_RangeSet"/></TD>
                <TD bgColor=#b8b7d7><h:label id="lbl_BasePoint" templateKey="YKK_LBL_BasePoint"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:formattextbox id="txt_BasePoint" templateKey="YKK_TXT_BasePoint"/><h:submitbutton id="btn_Show1" templateKey="YKK_BTN_Show_NA"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Status" templateKey="YKK_LBL_Status"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="60%" bgColor=#b8b7d7><h:radiobutton id="rdo_ForbidLocation" templateKey="YKK_RDO_ForbidLocationChecked"/><h:radiobutton id="rdo_UsefulLocation" templateKey="YKK_RDO_UsefulLocation"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_lt3.gif" width=42 border=0></TD>
          <TD bgColor=#b8b7d7><h:submitbutton id="btn_Set" templateKey="YKK_BTN_Set_NA"/>&nbsp;&nbsp; 
            <h:submitbutton id="btn_Clear" templateKey="YKK_BTN_Clear"/></TD>
          <TD><IMG height=40 src="<%=request.getContextPath()%>/img/common/tab_rt3.gif" width=42 border=0></TD></TR></TBODY></TABLE></TD></TR>




<%
Connection conn = null;
try
{
	
	String BankNo = (String)session.getAttribute("BankNo");
	String StartBay = (String)session.getAttribute("StartBay");
	String StartLevel = (String)session.getAttribute("StartLevel");
	session.setAttribute("BankNo", null);
	if(BankNo != null)
	{
%>
<TABLE bgcolor="#DAD9EE" width="100%" cellSpacing="0" cellPadding="0">
<TBODY>
<TR>
<TABLE bgcolor="#DAD9EE" width="100%" cellSpacing="0" cellPadding="0">
<TBODY>
<tr>
    <td>
		<img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="20" height="1" border="0">
	</td>
	
	<td>
	    <table BGCOLOR="#DAD9EE" width = "100%" border="0">
<TBODY>
	        <tr>
	            <td>
	                Bank <%= BankNo%>
	                <BR>
					<div class="shelf-scroll;">
					<TABLE BGCOLOR="#DAD9EE" border="0";overflow-y:scroll> 
<TBODY>
					<tr>
 		  				<td VALIGN="bottom" ROWSPAN="<%= 50 %>" >
 		  				    <IMG SRC="<%=request.getContextPath()%>/img/project/etc/arrow_lvl.gif">
 		  				</td>
 					</tr>
<%
 			int levelLength = 21;
		        int bayLength = 33;
		        int startBayPosition = 0;
		        int startLevelPosition =0;
		        try
		        {
	        		startBayPosition = Integer.parseInt(StartBay);
	        		startLevelPosition = Integer.parseInt(StartLevel);
		        }
	        	catch(Exception ex)
	        	{
	        		startBayPosition = 1;
	        		startLevelPosition = 1;
	        	}
 
	        	String locationSize = "";
	        	String exampleLocationSize  = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	        	String color = "";
	        	String title = "";


		String CLR_EMPTY        = "white";
		String CLR_IN_RESERVED  = "mediumvioletred";
		String CLR_OUT_RESERVED    = "royalblue";		
		String CLR_ABNORMAL     = "red";
	
		String CLR_IN_STOCK  = "palegreen";	
		String CLR_STOCK_OUT    = "darkblue";	
		String CLR_FORBIDDEN     = "yellow";
		String CLR_UNACCESSABLE    = "black";

conn = ConnectionManager.getConnection();
	ASRSInfoCentre centre = new ASRSInfoCentre(conn);

HashMap hm = centre.getLocationStatus(BankNo, String.valueOf(startBayPosition), String.valueOf(startBayPosition + bayLength), String.valueOf(startLevelPosition), String.valueOf(startLevelPosition + levelLength));

	        	for (int levelNo = startLevelPosition + levelLength - 1 ; levelNo >= startLevelPosition ; --levelNo)
	        	{
 	        		DecimalFormat fmt = new DecimalFormat("00");
 	        		String level_scale = fmt.format(levelNo);
%>
		            <TR>
		                <TD><I><FONT ID="SCALE"><%= level_scale %></FONT></I></TD>
<%		
			        for ( int bayNo = startBayPosition ; bayNo < startBayPosition + bayLength; ++bayNo )
			        {
					fmt = new DecimalFormat("00");
				        color = "#DAD9EE";
				        title =  DisplayText.getText("YKK-LBL-LocationNo") + ". " + fmt.format(Integer.parseInt(BankNo)) + "-" + fmt.format(bayNo) + "-" + fmt.format(levelNo);
int status = 0;
try
{
					fmt = new DecimalFormat("000");
					status = Integer.parseInt((String)hm.get(fmt.format(Integer.parseInt(BankNo)) + fmt.format(bayNo) + fmt.format(levelNo)));
}
catch(Exception ex)
{
}



					
if(status == 1)
{
color = "white";
}
else if(status == 2)
{
color = "mediumvioletred";
}
else if(status == 3)
{
color = "royalblue";
}
else if(status == 4)
{
color = "red";
}
else if(status == 5)
{
color = "palegreen";
}
else if(status == 6)
{
color = "darkblue";
}
else if(status == 7)
{
color = "yellow";
}
else if(status == 8)
{
color = "black";
}	
		    


%>
			            <TD BGCOLOR="<%= color %>" title="<%= title %>"><%= locationSize %></TD> 
<%
				    }
%>
                   			                
<%	
 	        	}
 	        	out.write("<TR><TD></TD>");
		        for (int bayNo = startBayPosition ; bayNo < startBayPosition + bayLength; ++bayNo)
		        {
	 		        DecimalFormat fmt = new DecimalFormat("00");
	 		        String bay_scale = fmt.format(bayNo);
			        out.write("<TD width = \"30\"><I><FONT ID=\"SCALE\">" + bay_scale + "</FONT></I></TD>");
		        }
		        out.write("</TR>");
%>
</TBODY>
                    </table>
                    <%= exampleLocationSize %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <img src="<%=request.getContextPath()%>/img/project/etc/arrow_bay.gif">
                    <BR>                    
	            </td>
	        </tr>
<table BGCOLOR="#DAD9EE" width = "100%" border="0"></table>
</TBODY>
	    </table>
	</td>

</tr>
</TBODY>
</TABLE>
</TR>
<TR>
<TABLE BGCOLOR="#DAD9EE" width = "100%">
<TBODY>
	<TR>
	  <TD>
	  <TABLE BGCOLOR="#DAD9EE">
	<TBODY>
	    <TR>
		  <td><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="15" height="1" border="0"></td>
	    
			<TD width = "60"/>

		  <TD BGCOLOR=<%=CLR_EMPTY%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-EmptyLocation") %></TD>
		  <TD width = "100"/>
		  
		
		  <TD BGCOLOR=<%=CLR_IN_RESERVED%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-RetrievalStockinLocation") %></TD>
		  <TD width = "100"/>
	

		  <TD BGCOLOR=<%=CLR_OUT_RESERVED%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-RetrievalStockoutLocation") %></TD>
		  <TD width = "100"/>
		

		<TD BGCOLOR=<%=CLR_ABNORMAL%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-ErroLocation") %></TD>
		  <TD width = "100"/>
		
		</TR>
		<TR>
		  <td><img src="<%=request.getContextPath()%>/img/common/spacer.gif" width="15" height="1" border="0"></td>
	
		<TD width = "60"/>

		  <TD BGCOLOR=<%=CLR_IN_STOCK%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-UsedLocation") %></TD>
		  <TD>&nbsp;&nbsp;</TD>
		

		  <TD BGCOLOR=<%=CLR_STOCK_OUT%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-StockoutingLocation") %></TD>
		  <TD>&nbsp;&nbsp;</TD>
		

		  <TD BGCOLOR=<%=CLR_FORBIDDEN%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-ForbidLocation") %></TD>
		  <TD>&nbsp;&nbsp;</TD>
		

		  <TD BGCOLOR=<%=CLR_UNACCESSABLE%>><%= exampleLocationSize %></TD>
		  <TD><%= DisplayText.getText("YKK-LBL-CannotCallLocation") %></TD>
		  <TD>&nbsp;&nbsp;</TD>
				
		</TR>
	</TBODY>
	  </TABLE>
	  </TD>
	  </TR>
</TBODY>
	  </TABLE>
</TR>
</TBODY>
</TABLE>
<%	
	}
}
finally
	{
		if (conn != null)
		{
			conn.close();
		}
	}	
%>


  <TR>
    <TD>
<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#dad9ee height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#dad9ee height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD>
          <TD 
          background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
