package jp.co.daifuku.wms.YkkGMAX.Entities;

public class StockoutStartEntity implements SystemIdSortable
{
	private String u = "";

	private String p = "";

	private String subdivision = "";

	private String stockinDateTime = "";

	private String itemCode = "";

	private String itemName1 = "";

	private String itemName2 = "";

	private String itemName3 = "";

	private String color = "";

	private String locationNo = "";

	private String ticketNo = "";

	private int stockoutCount = 0;

	private String originalLocationNo = "";

	private String systemId = "";

	private String mckey = "";

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public int getStockoutCount()
	{
		return stockoutCount;
	}

	public void setStockoutCount(int stockoutCount)
	{
		this.stockoutCount = stockoutCount;
	}

	public String getMckey()
	{
		return mckey;
	}

	public void setMckey(String mckey)
	{
		this.mckey = mckey;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
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

	public String getP()
	{
		return p;
	}

	public void setP(String p)
	{
		this.p = p;
	}

	public String getStockinDateTime()
	{
		return stockinDateTime;
	}

	public void setStockinDateTime(String stockinDateTime)
	{
		this.stockinDateTime = stockinDateTime;
	}

	public String getU()
	{
		return u;
	}

	public void setU(String u)
	{
		this.u = u;
	}

	public String getOriginalLocationNo()
	{
		return originalLocationNo;
	}

	public void setOriginalLocationNo(String originalLocationNo)
	{
		this.originalLocationNo = originalLocationNo;
	}

	public String getSubdivision()
	{
		return subdivision;
	}

	public void setSubDivide(String subdivision)
	{
		this.subdivision = subdivision;
	}
}
