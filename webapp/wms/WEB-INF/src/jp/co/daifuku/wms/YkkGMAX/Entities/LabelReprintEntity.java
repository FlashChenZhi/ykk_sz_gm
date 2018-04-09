package jp.co.daifuku.wms.YkkGMAX.Entities;

public class LabelReprintEntity implements LabelKeySortable
{
	private String printTime = "";
	
	private String ticketNo = "";
	
	private String bucketNo = "";
	
	private String itemCode = "";
	
	private String colorCode = "";
	
	private String sectionExternalCode = "";
	
	private String linePrno = "";
	
	private int stockoutCount = 0;
	
	private int stockoutWeight = 0;
	
	private String labelKey = "";

	public String getLabelKey()
	{
		return labelKey;
	}

	public void setLabelKey(String labelKey)
	{
		this.labelKey = labelKey;
	}

	public String getBucketNo()
	{
		return bucketNo;
	}

	public void setBucketNo(String bucketNo)
	{
		this.bucketNo = bucketNo;
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

	public String getLinePrno()
	{
		return linePrno;
	}

	public void setLinePrno(String linePrno)
	{
		this.linePrno = linePrno;
	}

	public String getPrintTime()
	{
		return printTime;
	}

	public void setPrintTime(String printTime)
	{
		this.printTime = printTime;
	}

	public String getSectionExternalCode()
	{
		return sectionExternalCode;
	}

	public void setSectionExternalCode(String sectionExternalCode)
	{
		this.sectionExternalCode = sectionExternalCode;
	}

	public int getStockoutCount()
	{
		return stockoutCount;
	}

	public void setStockoutCount(int stockoutCount)
	{
		this.stockoutCount = stockoutCount;
	}

	public int getStockoutWeight()
	{
		return stockoutWeight;
	}

	public void setStockoutWeight(int stockoutWeight)
	{
		this.stockoutWeight = stockoutWeight;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}
	
}
