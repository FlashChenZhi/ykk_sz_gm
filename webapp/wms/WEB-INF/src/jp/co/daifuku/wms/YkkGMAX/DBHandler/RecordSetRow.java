package jp.co.daifuku.wms.YkkGMAX.DBHandler;

import java.util.ArrayList;
import java.util.List;


import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;



public class RecordSetRow {

	private List columnList = new ArrayList();

	public List getColumnList() 
	{
		return columnList;
	}
	
	public void addColumn(RecordSetColumn column) 
	{
		columnList.add(column);
	}
	
	public String getValueByColumnName(String name) throws YKKSQLException 
	{
		String value = null;
		for (int i = 0; i < columnList.size(); i++) 
		{
			RecordSetColumn col = (RecordSetColumn) columnList.get(i);
			if (col.getName().equalsIgnoreCase(name)) 
			{
				value = StringUtils.convertNullToEmpty(col.getValue());
			}
		}
		
		if(value == null)
		{
		    YKKSQLException ex = new YKKSQLException();
		    ex.setResourceKey("7300005");
			throw ex;
			
		}
		return value;
	}
}
