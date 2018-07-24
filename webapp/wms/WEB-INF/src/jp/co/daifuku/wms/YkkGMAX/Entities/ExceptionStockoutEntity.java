package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.util.ArrayList;

public class ExceptionStockoutEntity
{
	private String stockoutCondition = "";

	private int stockoutMode = 0;

	private String stockinDate = "";

	private String stockinTime = "";

	private String itemCode = "";

	private int instockCount = 0;

	private String locationNoFrom = "";

	private String locationNoTo = "";

	private String locationNo = "";

	private boolean searchItemCode = false;

	private boolean afterThisLocation = false;

	private int orderMode = 0;

	private String endStation = "";

    private String colorCode = "";

    private String memo = "";
	private ArrayList exceptionStockoutDetailList = new ArrayList();

	public boolean isAfterThisLocation()
	{
		return afterThisLocation;
	}

	public void setAfterThisLocation(boolean afterThisLocation)
	{
		this.afterThisLocation = afterThisLocation;
	}

	public int getInstockCount()
	{
		return instockCount;
	}

	public void setInstockCount(int instockCount)
	{
		this.instockCount = instockCount;
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

	public String getLocationNoFrom()
	{
		return locationNoFrom;
	}

	public void setLocationNoFrom(String locationNoFrom)
	{
		this.locationNoFrom = locationNoFrom;
	}

	public String getLocationNoTo()
	{
		return locationNoTo;
	}

	public void setLocationNoTo(String locationNoTo)
	{
		this.locationNoTo = locationNoTo;
	}

	public boolean isSearchItemCode()
	{
		return searchItemCode;
	}

	public void setSearchItemCode(boolean searchItemCode)
	{
		this.searchItemCode = searchItemCode;
	}

	public String getStockinDate()
	{
		return stockinDate;
	}

	public void setStockinDate(String stockinDate)
	{
		this.stockinDate = stockinDate;
	}

	public String getStockinTime()
	{
		return stockinTime;
	}

	public void setStockinTime(String stockinTime)
	{
		this.stockinTime = stockinTime;
	}

	public int getStockoutMode()
	{
		return stockoutMode;
	}

	public void setStockoutMode(int stockoutMode)
	{
		this.stockoutMode = stockoutMode;
	}

	public ArrayList getExceptionStockoutDetailList()
	{
		return exceptionStockoutDetailList;
	}

	public void setExceptionStockoutDetailList(
			ArrayList exceptionStockoutDetailList)
	{
		this.exceptionStockoutDetailList = exceptionStockoutDetailList;
	}

	public String getStockoutCondition()
	{
		return stockoutCondition;
	}

	public void setStockoutCondition(String stockoutCondition)
	{
		this.stockoutCondition = stockoutCondition;
	}

	public int getOrderMode()
	{
		return orderMode;
	}

	public void setOrderMode(int orderMode)
	{
		this.orderMode = orderMode;
	}

	public String getEndStation()
	{
		return endStation;
	}

	public void setEndStation(String endStation)
	{
		this.endStation = endStation;
	}

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
