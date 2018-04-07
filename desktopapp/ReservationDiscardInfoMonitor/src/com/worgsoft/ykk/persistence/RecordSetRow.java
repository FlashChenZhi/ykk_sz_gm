package com.worgsoft.ykk.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.worgsoft.ykk.persistence.RecordSetColumn;

final public class RecordSetRow
{

	private final List columnList = new ArrayList();

	public void addColumn(RecordSetColumn column)
	{
		columnList.add(column);
	}

	public List getColumnList()
	{
		return columnList;
	}

	public String getValueByColumnName(String name)
	{
		String value = null;
		for (int i = 0; i < columnList.size(); i++)
		{
			RecordSetColumn col = (RecordSetColumn) columnList.get(i);
			if (col.getName().equalsIgnoreCase(name))
			{
				value = col.getValue();
				break;
			}
		}
		return StringUtils.defaultString(value);
	}
}
