package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.util.ArrayList;

public class UnmanagedStockoutEntity
{
	private String stockoutCondition = "";

	private int stockoutMode = 0;

	private String itemCode = "";

	private String locationNo = "";

	private boolean afterThisLocation = false;

	private int orderMode = 0;

	private String endStation = "";

	private ArrayList unmanagedStockoutDetailList = new ArrayList();
	
	public boolean isAfterThisLocation()
	{
		return afterThisLocation;
	}

	public void setAfterThisLocation(boolean afterThisLocation)
	{
		this.afterThisLocation = afterThisLocation;
	}

	public String getEndStation()
	{
		return endStation;
	}

	public void setEndStation(String endStation)
	{
		this.endStation = endStation;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public String getLocationNo()
	{
		return locationNo;
	}

	public void setLocationNo(String locationNo)
	{
		this.locationNo = locationNo;
	}

	public int getOrderMode()
	{
		return orderMode;
	}

	public void setOrderMode(int orderMode)
	{
		this.orderMode = orderMode;
	}

	public String getStockoutCondition()
	{
		return stockoutCondition;
	}

	public void setStockoutCondition(String stockoutCondition)
	{
		this.stockoutCondition = stockoutCondition;
	}

	public int getStockoutMode()
	{
		return stockoutMode;
	}

	public void setStockoutMode(int stockoutMode)
	{
		this.stockoutMode = stockoutMode;
	}

	public ArrayList getUnmanagedStockoutDetailList()
	{
		return unmanagedStockoutDetailList;
	}

	public void setUnmanagedStockoutDetailList(
			ArrayList unmanagedStockoutDetailList)
	{
		this.unmanagedStockoutDetailList = unmanagedStockoutDetailList;
	}
}
