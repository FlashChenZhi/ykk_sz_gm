package jp.co.daifuku.wms.YkkGMAX.Entities;

public class ExceptionStockoutDetailEntity implements SystemIdSortable
{
	private String stockinDateTime = "";
	
	private String itemCode = "";
	
	private String itemName1 = "";
	
	private String itemName2 = "";
	
	private String itemName3 = "";
	
	private String color = "";

	private String locationNo = "";

	private String ticketNo = "";
	
	private String systemId = "";
	
	private String originalLocationNo = "";
	
	private int instockCount = 0;
	
	private String line1 = "";
	
	private String line2 = "";

    private String memo = "";

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
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

	public String getLine1()
	{
		return line1;
	}

	public void setLine1(String line1)
	{
		this.line1 = line1;
	}

	public String getLine2()
	{
		return line2;
	}

	public void setLine2(String line2)
	{
		this.line2 = line2;
	}

	public String getLocationNo()
	{
		return locationNo;
	}

	public void setLocationNo(String locationNo)
	{
		this.locationNo = locationNo;
	}

	public String getStockinDateTime()
	{
		return stockinDateTime;
	}

	public void setStockinDateTime(String stockinDateTime)
	{
		this.stockinDateTime = stockinDateTime;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

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
}
