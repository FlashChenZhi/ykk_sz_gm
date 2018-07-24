package jp.co.daifuku.wms.YkkGMAX.Entities;

public class WorkViewEntity
{
	private String transferType = "";
	
	private String status = "";
	
	private String mckey = "";
	
	private String stationNo = "";
	
	private String motoStationNo = "";
	
	private String SakiStationNo = "";
	
	private String locationNo = "";
	
	private String bucketNo = "";
	
	private String itemCode = "";
	
	private String itemName1 = "";
	
	private String itemName2 = "";
	
	private String itemName3 = "";
	
	private int transferCount = 0;
	
	private String dispatchDetail = "";
	
	private int instockCount = 0;
	
	private String ticketNo = "";
	
	private String colorCode = "";
	
	public String getColorCode()
	{
		return colorCode;
	}

	public void setColorCode(String colorCode)
	{
		this.colorCode = colorCode;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getDispatchDetail()
	{
		return dispatchDetail;
	}

	public void setDispatchDetail(String dispatchDetail)
	{
		this.dispatchDetail = dispatchDetail;
	}

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

	public String getMckey()
	{
		return mckey;
	}

	public void setMckey(String mckey)
	{
		this.mckey = mckey;
	}

	public String getMotoStationNo()
	{
		return motoStationNo;
	}

	public void setMotoStationNo(String motoStationNo)
	{
		this.motoStationNo = motoStationNo;
	}

	public String getSakiStationNo()
	{
		return SakiStationNo;
	}

	public void setSakiStationNo(String sakiStationNo)
	{
		SakiStationNo = sakiStationNo;
	}

	public String getStationNo()
	{
		return stationNo;
	}

	public void setStationNo(String stationNo)
	{
		this.stationNo = stationNo;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getTransferCount()
	{
		return transferCount;
	}

	public void setTransferCount(int transferCount)
	{
		this.transferCount = transferCount;
	}

	public String getTransferType()
	{
		return transferType;
	}

	public void setTransferType(String transferType)
	{
		this.transferType = transferType;
	}
}
