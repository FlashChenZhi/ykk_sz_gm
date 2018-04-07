package jp.co.daifuku.wms.YkkGMAX.Entities;

public class LocationViewEntity
{
	private String locationNo = "";
	
	private String locationStatus = "";

	private String manageDivision = "";
	
	private String ticketNo = "";
	
	private String itemCode = "";
	
	private String itemName = "";
	
	private String colorCode = "";
	
	private String bucketNo = "";
	
	private String stockinDate = "";
	
	private String stockinTime = "";
	
	private String memo = "";
	
	private int instockCount = 0;
	
	public int getInstockCount()
	{
		return instockCount;
	}

	public void setInstockCount(int instockCount)
	{
		this.instockCount = instockCount;
	}

	public String getBucketNo()
	{
		return bucketNo;
	}

	public void setBucketNo(String bucketNo)
	{
		this.bucketNo = bucketNo;
	}

	public String getColorCode()
	{
		return colorCode;
	}

	public void setColorCode(String colorCode)
	{
		this.colorCode = colorCode;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public String getItemName()
	{
		return itemName;
	}

	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

	public String getManageDivision()
	{
		return manageDivision;
	}

	public void setManageDivision(String manageDivision)
	{
		this.manageDivision = manageDivision;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
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

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getLocationNo()
	{
		return locationNo;
	}

	public void setLocationNo(String locationNo)
	{
		this.locationNo = locationNo;
	}

	public String getLocationStatus()
	{
		return locationStatus;
	}

	public void setLocationStatus(String locationStatus)
	{
		this.locationStatus = locationStatus;
	}
}
