<%@ page import = "jp.co.daifuku.bluedog.sheetwriter.SheetWriterUtils" %>
<%@ page import = "java.io.File" %>
<%@ page contentType="text/html;charset=utf-8" errorPage="error.jsp" %>
<%
	String sessionId = request.getSession().getId();
	String file = "";
	String path = "";

	if( sessionId != null && !sessionId.equals("") ) 
	{
		request.setCharacterEncoding("UTF-8");
		file = request.getParameter("file");
		//path = jp.co.daifuku.bluedog.sheetwriter.SheetWriterUtils.getSessionTempDir( request );
		File fileInfo = new File(file);		
		file = fileInfo.getName();
		path = fileInfo.getParent();
	}
%> 
<html> 
<head> 
<script language="javascript" src="../jscript/control.js" ></script>
<script type="text/javascript">

function doOnload()
{
	try
	{
		window.document.onkeydown = doOnkeydown;
		document.onhelp=_onhelp;
	}
	catch( e )
	{
	}

	try
	{
		window.resizeTo(1, 1);
		window.moveTo(-100, -100);
	}
	catch( e )
	{
	}

	try
	{
		if ( window.opener != null)
		{
			window.opener.dialogWindow = null;
			window.opener.focus();
			window.focus();
		}
	}
	catch( e )
	{
	}
}

function _onhelp()
{
	event.returnValue=false;
}

function doOnkeydown()
{
	// click Function key
	//Ignore Function key and shortcut key event
	if( ignoreKeyEvent() == false )
	{
		return false;
	}
		return true;
}

function submitDl()
{
<%
	if ( sessionId != null && !sessionId.equals("")
	    && file != null && !file.equals("") )
	{
%>
	var frm  = document.forms[0];
	frm.target = "_self";
	frm.submit();

<%
	}
	else
	{
%>
	alert("can't download due to session time-out errors.");
	window.close();
<%
	}
%>
}

</script>
</head> 
<body onLoad="doOnload();submitDl()">
<form action="SheetDownLoad.jsp" name="form" method="post">
<input type="hidden" name="file" value="<%= file %>">
<input type="hidden" name="path" value="<%= path %>">
</form>
</body> 
</html> 