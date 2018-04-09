package jp.co.daifuku.wms.YkkGMAX.Entities;

import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class StockoutForbidEntity
{
	private String itemCode = "";
	private String ticketNoFrom = "";
	private String ticketNoTo = "";
	private String colorCode = "";
	private String stockInDateFrom = "";
	private String stockInDateTo = "";
	private String stockInTimeFrom = "";
	private String stockInTimeTo = "";

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
		this.ticketNoFrom = StringUtils.IsNullOrEmpty(ticketNoFrom) ? "!!!!!!!!!!" : ticketNoFrom;
	}

	public String getTicketNoTo()
	{
		return ticketNoTo;
	}

	public void setTicketNoTo(String ticketNoTo)
	{
		this.ticketNoTo = StringUtils.IsNullOrEmpty(ticketNoTo) ? "~~~~~~~~~~" : ticketNoTo;
	}

	public void setStockInDateFrom(String stockInDateFrom)
	{
		this.stockInDateFrom = StringUtils.IsNullOrEmpty(stockInDateFrom) ? "19700101" : stockInDateFrom;
	}

	public void setStockInDateTo(String stockInDateTo)
	{
		this.stockInDateTo = StringUtils.IsNullOrEmpty(stockInDateTo) ? "20491231" : stockInDateTo;
	}

	public void setStockInTimeFrom(String stockInTimeFrom)
	{
		this.stockInTimeFrom = StringUtils.IsNullOrEmpty(stockInTimeFrom) ? "000000" : stockInTimeFrom;
	}

	public void setStockInTimeTo(String stockInTimeTo)
	{
		this.stockInTimeTo = StringUtils.IsNullOrEmpty(stockInTimeTo) ? "235959" : stockInTimeTo;;
	}

	public String getStockInDatetimeFrom()
	{
		return stockInDateFrom + stockInTimeFrom;
	}

	public String getStockInDatetimeTo()
	{
		return stockInDateTo + stockInTimeTo;
	}
}
