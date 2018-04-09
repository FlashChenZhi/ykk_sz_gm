package jp.co.daifuku.wms.YkkGMAX.Entities;

public class PendingTransferDataCencelOrPrioritizeEntity implements
		RetrievalPlankeySortable
{
	private String whenNextWorkBegin = "";

	private String whenNextWorkBeginTiming = "";

	private String itemCode = "";

	private String itemName1 = "";

	private String itemName2 = "";

	private String itemName3 = "";

	private String color = "";

	private int stockoutTransferQty = 0;

	private int startedBucketCount = 0;

	private int waitingBucketCount = 0;

	private String retrievalNo = "";

	private String mcKey = "";

	private String retrievalPlankey = "";

	public String getRetrievalPlankey()
	{
		return retrievalPlankey;
	}

	public void setRetrievalPlankey(String retrievalPlankey)
	{
		this.retrievalPlankey = retrievalPlankey;
	}

	public String getWhenNextWorkBegin()
	{
		return whenNextWorkBegin;
	}

	public void setWhenNextWorkBegin(String whenNextWorkBegin)
	{
		this.whenNextWorkBegin = whenNextWorkBegin;
	}

	public String getWhenNextWorkBeginTiming()
	{
		return whenNextWorkBeginTiming;
	}

	public void setWhenNextWorkBeginTiming(String whenNextWorkBeginTiming)
	{
		this.whenNextWorkBeginTiming = whenNextWorkBeginTiming;
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

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public int getStockoutTransferQty()
	{
		return stockoutTransferQty;
	}

	public void setStockoutTransferQty(int stockoutTransferQty)
	{
		this.stockoutTransferQty = stockoutTransferQty;
	}

	public int getStartedBucketCount()
	{
		return startedBucketCount;
	}

	public void setStartedBucketCount(int startedBucketCount)
	{
		this.startedBucketCount = startedBucketCount;
	}

	public int getWaitingBucketCount()
	{
		return waitingBucketCount;
	}

	public void setWaitingBucketCount(int waitingBucketCount)
	{
		this.waitingBucketCount = waitingBucketCount;
	}

	public String getRetrievalNo()
	{
		return retrievalNo;
	}

	public void setRetrievalNo(String retrievalNo)
	{
		this.retrievalNo = retrievalNo;
	}

	public String getMcKey()
	{
		return mcKey;
	}

	public void setMcKey(String mcKey)
	{
		this.mcKey = mcKey;
	}

}
