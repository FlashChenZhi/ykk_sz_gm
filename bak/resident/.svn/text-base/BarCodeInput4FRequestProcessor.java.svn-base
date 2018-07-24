package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;

import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class BarCodeInput4FRequestProcessor extends BasicProcessor
{

    private String stno = null;
    private String barCode = null;

    public BarCodeInput4FRequestProcessor(String stno, String barCode)
    {
	this.stno = stno;
	this.barCode = barCode;
    }

    protected String getProcedureName()
    {
	return "fourth_floor_barcode_setting";
    }

    protected void insertRecorderIntoFngset(Connection conn, String schno)
	    throws YKKDBException, YKKSQLException
    {
	String sqlString = "INSERT INTO FNGSET";
	sqlString += "(syoriflg,schno,motostno,barcode)";
	sqlString += "VALUES";
	sqlString += "("
		+ StringUtils
			.surroundWithSingleQuotes(DBFlags.SyoriFlg.NOTPROSESSED)
		+ "," + StringUtils.surroundWithSingleQuotes(schno) + ","
		+ StringUtils.surroundWithSingleQuotes(stno) + ","
		+ StringUtils.surroundWithSingleQuotes(barCode) + ")";

	DBHandler dbHandler = new DBHandler(conn);
	dbHandler.executeUpdate(sqlString);

    }
}
