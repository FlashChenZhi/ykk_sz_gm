package jp.co.daifuku.wms.YkkGMAX.Entities;

public class ExternalStockoutDetailEntity implements SystemIdSortable
{
	private String stockinDate = "";
	private String LocationNo = "";
	private String ticketNo = "";
	private String Status = "";
	private int enableToStockoutCount = 0;
	private String remark = "";
	private String originalLocationNo = "";
	private String systemId = "";

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getOriginalLocationNo()
	{
		return originalLocationNo;
	}

	public void setOriginalLocationNo(String originalLocationNo)
	{
		this.originalLocationNo = originalLocationNo;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public int getEnableToStockoutCount()
	{
		return enableToStockoutCount;
	}

	public void setEnableToStockoutCount(int enableToStockoutCount)
	{
		this.enableToStockoutCount = enableToStockoutCount;
	}

	public String getLocationNo()
	{
		return LocationNo;
	}

	public void setLocationNo(String locationNo)
	{
		LocationNo = locationNo;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getStatus()
	{
		return Status;
	}

	public void setStatus(String status)
	{
		Status = status;
	}

	public String getStockinDate()
	{
		return stockinDate;
	}

	public void setStockinDate(String stockinDate)
	{
		this.stockinDate = stockinDate;
	}
}
