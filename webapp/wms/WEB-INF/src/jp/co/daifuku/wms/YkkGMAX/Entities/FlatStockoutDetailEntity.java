package jp.co.daifuku.wms.YkkGMAX.Entities;

public class FlatStockoutDetailEntity implements SystemIdSortable
{
	private String ticketNo = "";

	private int storageCount = 0;

	private String receptionDatetime = "";

	private String systemId = "";

	private int stockoutCount = 0;

	private int storageRemainCount = 0;

	private long stockoutRemainCount = 0;

	private int instockCount = 0;

	public int getInstockCount()
	{
		return instockCount;
	}

	public void setInstockCount(int instockCount)
	{
		this.instockCount = instockCount;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public int getStorageCount()
	{
		return storageCount;
	}

	public void setStorageCount(int storageCount)
	{
		this.storageCount = storageCount;
	}

	public String getReceptionDatetime()
	{
		return receptionDatetime;
	}

	public void setReceptionDatetime(String receptionDatetime)
	{
		this.receptionDatetime = receptionDatetime;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public int getStockoutCount()
	{
		return stockoutCount;
	}

	public void setStockoutCount(int stockoutCount)
	{
		this.stockoutCount = stockoutCount;
	}

	public int getStorageRemainCount()
	{
		return storageRemainCount;
	}

	public void setStorageRemainCount(int storageRemainCount)
	{
		this.storageRemainCount = storageRemainCount;
	}

	public long getStockoutRemainCount()
	{
		return stockoutRemainCount;
	}

	public void setStockoutRemainCount(long stockoutRemainCount)
	{
		this.stockoutRemainCount = stockoutRemainCount;
	}
}
