package jp.co.daifuku.wms.YkkGMAX.Entities;

public class OvertimeStorageInfoEntity
{
	private String dateTime = "";
	
	private String itemCode = "";

	private String itemName1 = "";

	private String itemName2 = "";

	private String itemName3 = "";

	private String color = "";
	
	private String ticketNo = "";
	
	private String bucketNo = "";
	
	private String locationNo = "";
	
	private int instockCount = 0;

	public String getBucketNo()
	{
		return bucketNo;
	}

	public void setBucketNo(String bucketNo)
	{
		this.bucketNo = bucketNo;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public String getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(String dateTime)
	{
		this.dateTime = dateTime;
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

	public String getItemName1()
	{
		return itemName1;
	}

	public void setItemName1(String itemName1)
	{
		this.itemName1 = itemName1;
	}

	public String getItemName2()
	{
		return itemName2;
	}

	public void setItemName2(String itemName2)
	{
		this.itemName2 = itemName2;
	}

	public String getItemName3()
	{
		return itemName3;
	}

	public void setItemName3(String itemName3)
	{
		this.itemName3 = itemName3;
	}

	public String getLocationNo()
	{
		return locationNo;
	}

	public void setLocationNo(String locationNo)
	{
		this.locationNo = locationNo;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}
}
