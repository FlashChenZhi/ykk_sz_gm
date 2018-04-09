<%-- $Id: ExceptionStockout.jsp,v 1.6 2007/12/30 05:17:37 administrator Exp $ --%>
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
 * @version $Revision: 1.6 $, $Date: 2007/12/30 05:17:37 $
 * @author  $Author: administrator $
--%>
<%--@@SYSTEM_TODO_START@@--%>
<%@ taglib uri="/WEB-INF/control/bluedog-html.tld" prefix ="h"%>
<%--@@SYSTEM_TODO_END@@--%>

<h:html>
<h:head>
  <script>
	function dynamicAction()
	{
		if (targetElement == "btn_Set")
		{
			if(<%= session.getAttribute("STOCKOUT_MODE")%> == "1")
			{

				if (!_isValueInput('txt_StockinDate')) 
				{
					document.all('txt_StockinDate').focus();
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("7000001",jp.co.daifuku.bluedog.util.DispResources.getText("YKK-LBL-StockinDate")) %>',2);
					return true;
				}

			}
			else if(<%= session.getAttribute("STOCKOUT_MODE")%> == "2")
			{

				if (!_isValueInput('txt_InstockCount')) 
				{
					document.all('txt_InstockCount').focus();
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("7000001",jp.co.daifuku.bluedog.util.DispResources.getText("YKK-LBL-InstockCount")) %>',2);
					return true;
				}
			}

			else if(<%= session.getAttribute("STOCKOUT_MODE")%> == "4")
			{

				if (!_isValueInput('txt_ItemNo3')) 
				{
					document.all('txt_ItemNo3').focus();
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("7000001",jp.co.daifuku.bluedog.util.DispResources.getText("YKK-LBL-ItemNo")) %>',2);
					return true;
				}
			}
			else if(<%= session.getAttribute("STOCKOUT_MODE")%> == "5")
			{

				if (!_isValueInput('txt_LocationNo')) 
				{
					document.all('txt_LocationNo').focus();
					_setMessage('<%= jp.co.daifuku.bluedog.util.MessageResources.getText("7000001",jp.co.daifuku.bluedog.util.DispResources.getText("YKK-LBL-LocationNo")) %>',2);
					return true;
				}
			}
		}
		

		return false;
	}
	</script>
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
          <TD><h:label id="lbl_SettingName" templateKey="YKK_LBL-ExceptionStockout"/></TD>
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
          <TD noWrap></TD>
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
                <TD bgColor=#dad9ee></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_StockoutCondition" templateKey="YKK_LBL_StockoutCondition"/></TD>
                <TD noWrap bgColor=#b8b7d7><h:radiobutton id="rdo_OverTimeStorageStockout" templateKey="YKK_RDO_OvertimeStorageStockout"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  &nbsp; </TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD width=100></TD>
                      <TD><h:label id="lbl_StockinDateTime" templateKey="YKK_LBL_StockinDateTime"/></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:label id="lbl_Date" templateKey="YKK_LBL_Date"/><h:datetextbox id="txt_StockinDate" templateKey="YKK_TXT_StockinDate"/>&nbsp;<h:label id="lbl_Time" templateKey="YKK_LBL_Time"/><h:timetextbox id="txt_StockinTime" templateKey="YKK_TXT_StockinTime"/><h:label id="lbl_BeforeThisDate" templateKey="YKK_LBL_BeforeThisDate"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:radiobutton id="rdo_BitsStorageStockout" templateKey="YKK_RDO_BitsStorageStockout"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD width=100></TD>
                      <TD><h:label id="lbl_ItemNo1" templateKey="YKK_LBL_ItemNo"/></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemNo1" templateKey="YKK_TXT_ItemNo"/><h:submitbutton id="btn_ItemBrowse1" templateKey="YKK_BTN_ItemBrowse_POPUP"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD width=100></TD>
                      <TD><h:label id="lbl_InstockCount" templateKey="YKK_LBL_InstockCount"/></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:numbertextbox id="txt_InstockCount" templateKey="YKK_TXT_InstockCount"/><h:label id="lbl_FewerThanThis" templateKey="YKK_LBL_FewerThanThis"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:radiobutton id="rdo_LocationStockout" templateKey="YKK_RDO_LocationStockout"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap align=right bgColor=#b8b7d7><h:radiobutton id="rdo_LocationRange" templateKey="YKK_RDO_LocationRange"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:formattextbox id="txt_LocationNoFrom" templateKey="YKK_TXT_LocationNoFrom"/><h:label id="lbl_to" templateKey="YKK_LBL_to"/><h:formattextbox id="txt_LocationNoTo" templateKey="YKK_TXT_LocationNoTo"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap align=right bgColor=#b8b7d7><h:radiobutton id="rdo_ItemNo2" templateKey="YKK_RDO_ItemCode"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemNo2" templateKey="YKK_TXT_ItemNo"/><h:submitbutton id="btn_ItemBrowse2" templateKey="YKK_BTN_ItemBrowse_POPUP"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap align=right bgColor=#b8b7d7><h:label id="lbl_ColorCode2" templateKey="YKK_LBL_ColorCode"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ColorCode2" templateKey="YKK_TXT_ColorCode"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:radiobutton id="rdo_SearchStockout" templateKey="YKK_RDO_SearchStockout"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD width=100></TD>
                      <TD><h:label id="lbl_ItemNo3" templateKey="YKK_LBL_ItemNo"/></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ItemNo3" templateKey="YKK_TXT_ItemNo"/><h:submitbutton id="btn_ItemBrowse3" templateKey="YKK_BTN_ItemBrowse_POPUP"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD width=100></TD>
                      <TD><h:label id="lbl_ColorCode" templateKey="YKK_LBL_ColorCode"/></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_ColorCode" templateKey="YKK_TXT_ColorCode"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:radiobutton id="rdo_DesignateLocationStockout" templateKey="YKK_RDO_DesignateLocationStockout"/></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD width=100></TD>
                      <TD><h:label id="lbl_LocationNo" templateKey="W_LocationNo"/></TD></TR></TBODY></TABLE></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:formattextbox id="txt_LocationNo" templateKey="YKK_TXT_LocationNo"/><h:checkbox id="chk_AfterThisLocation" templateKey="YKK_CHK_AfterThisLocation"/></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" bgColor=#b8b7d7></TD></TR>
              <TR>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD noWrap bgColor=#b8b7d7><h:label id="lbl_Memo" templateKey="YKK_LBL_Memo"/></TD>
                <TD bgColor=#b8b7d7><IMG src="<%=request.getContextPath()%>/img/common/icon_colon3.gif"></TD>
                <TD width="100%" bgColor=#b8b7d7><h:freetextbox id="txt_Memo" templateKey="YKK_TXT_Memo"/></TD></TR>
              <TR bgColor=#b8b7d7>
                <TD noWrap bgColor=#b8b7d7><h:submitbutton id="btn_Set" templateKey="YKK_BTN_Set_POPUP"/></TD>
                <TD noWrap bgColor=#b8b7d7></TD>
                <TD bgColor=#b8b7d7></TD>
                <TD width="100%" 
        bgColor=#b8b7d7>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE style="MARGIN-LEFT: 7px" cellSpacing=0 cellPadding=0 border=0>
        <TBODY></TBODY></TABLE></TD></TR>
  <TR>
    <TD>
<%-- footer --%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR height=41>
          <TD width=7 bgColor=#dad9ee height=41><IMG height=1 src="<%=request.getContextPath()%>/img/common/spacer.gif" width=7 border=0></TD>
          <TD bgColor=#dad9ee height=41><IMG height=23 alt="" src="<%=request.getContextPath()%>/img/common/logo_dp.gif" width=374 border=0></TD></TR>
        <TR height=8>
          <TD width=7 background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD>
          <TD background=<%=request.getContextPath()%>/img/common/bg_end.gif 
          height=8></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></h:page>
</h:html>
