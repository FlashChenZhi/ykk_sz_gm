<%@ page import="jp.co.daifuku.bluedog.sql.ConnectionManager" %>
<%@ page import="jp.co.daifuku.bluedog.sql.ConnectionRentalList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%
	Map ConnMap = ConnectionManager.getRentalList();
%>

<html>
<body bgColor=#dad9ee>

<h2>= Connection status =</h2>


<table border='1' cellspacing="0px" cellpadding="0px">
<thead>
<tr>
<td>DB name&nbsp;</td>
<td>Active&nbsp;</td>
<td>Idle&nbsp;&nbsp;&nbsp;</td>
<td>Wait&nbsp;&nbsp;&nbsp;</td>
</tr>
</thead>
<tbody>
<%
{
	// Make status information table.
	Map statusMap = ConnectionManager.getConnectionStatus();
	Iterator itr1 = statusMap.keySet().iterator();
	while(itr1.hasNext())
	{
		String key1 = itr1.next().toString();
		if(key1 == null)
		{
			continue;
		}
		Map listmap = (Map)statusMap.get(key1);
		
		out.write("<tr bgcolor=white>");
		out.write("<td>"+key1+"</td>");
		out.write("<td>"+(String)listmap.get("ACTIVE")+"</td>");
		out.write("<td>"+(String)listmap.get("IDLE")+"</td>");
		out.write("<td>"+(String)listmap.get("WAIT")+"</td>");
		out.write("</tr>");
	}
}
%>
</tbody>
</table>
<BR>
<input type="button" id="btn_relaod" onclick="location.href='<%=request.getRequestURI() %>';" value="RELOAD" >
<BR>
<table border='1' cellspacing="0px" cellpadding="0px">
<thead>
<TR bgcolor="skyblue">
<TD>ID&nbsp;</TD>
<TD>DBNAME&nbsp;</TD>
<TD>TYPE&nbsp;</TD>
<TD>CREATE TIME&nbsp;</TD>
<TD>CALLER SCREEN&nbsp;</TD>
<TD>IP&nbsp;</TD>
<TD>USERNAME&nbsp;</TD>
</TR>
</thead>
<tbody>
<%
{
	// Make connection list table.
	Iterator itr1 = ConnMap.keySet().iterator();
	while(itr1.hasNext())
	{
		String key1 = itr1.next().toString();
		if(key1 == null)
		{
			continue;
		}
		Map listMap = (Map)ConnMap.get(key1);
		Iterator itr2 = listMap.keySet().iterator();
		boolean flag = true;
		while(itr2.hasNext())
		{
			out.write("<TR bgcolor=white>");
			String key2 = itr2.next().toString();
			if(key2 == null)
			{
				continue;
			}
			Map paramMap = (Map)listMap.get(key2);

			if(flag)
			{
				String id =key1;
				if(id.length() > 12)
				{
					id = id.subSequence(0,12) + "...";
				}
				out.write("<TD rowspan='"+ listMap.size() +"'>");
				out.write(id);
				out.write("</TD>");
				flag = false;
			}
			
			Date date = (Date)paramMap.get(ConnectionRentalList.KEY_ACQUISITION_TIME);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			String createtime = sdf.format(date);
			
			out.write("<TD>");
			out.write((String)paramMap.get(ConnectionRentalList.KEY_DATABASENAME));
			out.write("</TD>");
			out.write("<TD>");
			out.write((String)paramMap.get(ConnectionRentalList.KEY_EXPIRATION_TYPE));
			out.write("</TD>");
			out.write("<TD>");
			out.write( createtime );
			out.write("</TD>");
			out.write("<TD>");
			out.write((String)paramMap.get(ConnectionRentalList.KEY_CALLER));
			out.write("</TD>");
			out.write("<TD>");
			out.write((String)paramMap.get(ConnectionRentalList.KEY_REMOTE_ADDR));
			out.write("</TD>");
			out.write("<TD>");
			out.write((String)paramMap.get(ConnectionRentalList.KEY_USERNAME));
			out.write("</TD>");
			out.write("</TR>");
		}
	}
}
%>
</tbody>
</table>
</body>
</html>
<script>
	setTimeout('document.all.btn_relaod.click()', '10000');
</script>