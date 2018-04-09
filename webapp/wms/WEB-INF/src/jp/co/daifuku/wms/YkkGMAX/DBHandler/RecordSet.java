package jp.co.daifuku.wms.YkkGMAX.DBHandler;

import java.util.ArrayList;
import java.util.List;


public class RecordSet 
{
	private int actualCount;
	private int totalCount;
	private List rowList = new ArrayList();

	public void setActualCount(int actualCount) 
	{
		this.actualCount = actualCount;
	}
	
	public int getActualCount()
	{
		return actualCount;
	}

	public void setTotalCount(int totalCount) 
	{
		this.totalCount = totalCount;		
	}
	
	public int getTotalCount()
	{
		return totalCount;
	}

	public void addRow(RecordSetRow row) 
	{
		rowList.add(row);
	}
	
	public List getRowList()
	{
		return rowList;
	}

}
