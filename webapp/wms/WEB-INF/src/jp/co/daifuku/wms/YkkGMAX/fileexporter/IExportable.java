package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.SQLException;

public interface IExportable
{
	String getNativeSQL();

	String generateHead();

	String makeLine(ResultSetProxy resultSetProxy) throws SQLException;

	int getMaxLine();
}
