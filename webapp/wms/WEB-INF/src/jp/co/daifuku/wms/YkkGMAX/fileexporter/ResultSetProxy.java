package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

public class ResultSetProxy
{

	private ResultSet resultSet;

	public ResultSetProxy(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}

	public String getString(String columnName) throws SQLException
	{
		return StringUtils.isEmpty(resultSet.getString(columnName)) ? StringUtils.EMPTY
				: resultSet.getString(columnName);

	}
}
