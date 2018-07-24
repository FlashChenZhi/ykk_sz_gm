package jp.co.daifuku.wms.YkkGMAX.Entities;

public class ForbidItemViewEntity
{
	private String itemCode = "";
	private String ticketNoFrom = "";
	private String ticketNoTo = "";
	private String colorCode = "";
	private String stockinDateTimeFrom = "";
	private String stockinDateTimeTo = "";
	public String getStockinDateTimeFrom()
	{
		return stockinDateTimeFrom;
	}
	public void setStockinDateTimeFrom(String stockinDateTimeFrom)
	{
		this.stockinDateTimeFrom = stockinDateTimeFrom;
	}
	public String getStockinDateTimeTo()
	{
		return stockinDateTimeTo;
	}
	public void setStockinDateTimeTo(String stockinDateTimeTo)
	{
		this.stockinDateTimeTo = stockinDateTimeTo;
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
	public String getTicketNoFrom()
	{
		return ticketNoFrom;
	}
	public void setTicketNoFrom(String ticketNoFrom)
	{
		this.ticketNoFrom = ticketNoFrom;
	}
	public String getTicketNoTo()
	{
		return ticketNoTo;
	}
	public void setTicketNoTo(String ticketNoTo)
	{
		this.ticketNoTo = ticketNoTo;
	}
}
