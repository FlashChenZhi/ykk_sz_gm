package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Entities.ForcibleRemoveTicketNoEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ForcibleRemoveTicketNoRequestProcessor extends BasicProcessor
{
	private ArrayList list = null;
	private String userid = "";

	public ForcibleRemoveTicketNoRequestProcessor(ArrayList list,String userid)
	{
		this.list = list;
		this.userid = userid;
	}

	protected String getProcedureName()
	{
		return "ticket_no_force_delete_start";
	}

	protected void insertRecorderIntoFngset(Connection conn, String schno)
			throws YKKDBException, YKKSQLException
	{
		for (int i = 0; i < list.size(); i++)
		{
			ForcibleRemoveTicketNoEntity entity = (ForcibleRemoveTicketNoEntity) list.get(i);
			String sqlString = "INSERT INTO FNGSET";
			sqlString += "(syoriflg,schno,ticket_no,userid)";
			sqlString += "VALUES";
			sqlString += "("
					+ StringUtils
							.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
					+ ","
					+ StringUtils.surroundWithSingleQuotes(schno)
					+ ","
					+ StringUtils
							.surroundWithSingleQuotes(entity.getTicketNo())
					+ ","
					+ StringUtils.surroundWithSingleQuotes(userid)
					+ ")";

			DBHandler dbHandler = new DBHandler(conn);
			dbHandler.executeUpdate(sqlString);
		}
	}

}
