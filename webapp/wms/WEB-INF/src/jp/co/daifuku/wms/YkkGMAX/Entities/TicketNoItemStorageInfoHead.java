package jp.co.daifuku.wms.YkkGMAX.Entities;

public class TicketNoItemStorageInfoHead
{
	private String depo = "";
	
	private String division = "";
	
	private String ticketNoFrom = "";
	
	private String ticketNoTo = "";
	
	private String itemCode = "";
	
	private String colorCode = "";

	private String bucketNo = "";
	
	private boolean rangeSet = false;

	public boolean isRangeSet()
	{
		return rangeSet;
	}

	public void setRangeSet(boolean rangeSet)
	{
		this.rangeSet = rangeSet;
	}

	public String getColorCode()
	{
		return colorCode;
	}

	public void setColorCode(String colorCode)
	{
		this.colorCode = colorCode;
	}

	public String getDepo()
	{
		return depo;
	}

	public void setDepo(String depo)
	{
		this.depo = depo;
	}

	public String getDivision()
	{
		return division;
	}

	public void setDivision(String division)
	{
		this.division = division;
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

	public String getBucketNo()
	{
		return bucketNo;
	}

	public void setBucketNo(String bucketNo)
	{
		this.bucketNo = bucketNo;
	}
}
