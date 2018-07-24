package jp.co.daifuku.wms.YkkGMAX.Entities;

public class TicketNoViewEntity
{
	private String ticketNo = "";
	
	private String itemCode = "";
	
	private String itemName = "";
	
	private String colorCode = "";
	
	private int instockCount = 0;

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

	public String getItemName()
	{
		return itemName;
	}

	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getColorCode()
	{
		return colorCode;
	}

	public void setColorCode(String colorCode)
	{
		this.colorCode = colorCode;
	} 
}
