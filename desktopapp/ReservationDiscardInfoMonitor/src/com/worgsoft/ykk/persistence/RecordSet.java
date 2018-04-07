package com.worgsoft.ykk.persistence;

import java.util.ArrayList;
import java.util.List;

final public class RecordSet
{
	private int actualCount;
	private final List rowList = new ArrayList();

	public void addRow(RecordSetRow row)
	{
		rowList.add(row);
	}

	public int getActualCount()
	{
		return actualCount;
	}

	public List getRowList()
	{
		return rowList;
	}

	public void setActualCount(int actualCount)
	{
		this.actualCount = actualCount;
	}
}
