package jp.co.daifuku.wms.YkkGMAX.Entities;

public class RetrievalQtyMaintenanceEntity implements RetrievalPlankeySortable
{
	private String itemCode = "";

	private String section = "";

	private String orderBy = "";

	private String startDate = "";

	private String startDateTiming = "";

	private String retrievalNo = "";

	private String color = "";

	private long stockoutNecessaryQty = 0;

	private long wavesRetrievalQty = 0;

	private long managementRetrievalQty = 0;

	private long outQty = 0;

	private int storageQty = 0;

	private String retrievalPlankey = "";

	private long originalManagementRetrievalQty = 0;

	private boolean retrieved = false;

	public String getSection()
	{
		return section;
	}

	public void setSection(String section)
	{
		this.section = section;
	}

	public String getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getStartDateTiming()
	{
		return startDateTiming;
	}

	public void setStartDateTiming(String startDateTiming)
	{
		this.startDateTiming = startDateTiming;
	}

	public String getRetrievalNo()
	{
		return retrievalNo;
	}

	public void setRetrievalNo(String retrievalNo)
	{
		this.retrievalNo = retrievalNo;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public long getStockoutNecessaryQty()
	{
		return stockoutNecessaryQty;
	}

	public void setStockoutNecessaryQty(long stockoutNecessaryQty)
	{
		this.stockoutNecessaryQty = stockoutNecessaryQty;
	}

	public long getWavesRetrievalQty()
	{
		return wavesRetrievalQty;
	}

	public void setWavesRetrievalQty(long wavesRetrievalQty)
	{
		this.wavesRetrievalQty = wavesRetrievalQty;
	}

	public long getManagementRetrievalQty()
	{
		return managementRetrievalQty;
	}

	public void setManagementRetrievalQty(long managementRetrievalQty)
	{
		this.managementRetrievalQty = managementRetrievalQty;
	}

	public long getOutQty()
	{
		return outQty;
	}

	public void setOutQty(long outQty)
	{
		this.outQty = outQty;
	}

	public int getStorageQty()
	{
		return storageQty;
	}

	public void setStorageQty(int storageQty)
	{
		this.storageQty = storageQty;
	}

	public String getRetrievalPlankey()
	{
		return retrievalPlankey;
	}

	public void setRetrievalPlankey(String retrievalPlankey)
	{
		this.retrievalPlankey = retrievalPlankey;
	}

	public boolean isRetrieved()
	{
		return retrieved;
	}

	public void setRetrieved(boolean retrieved)
	{
		this.retrieved = retrieved;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public long getOriginalManagementRetrievalQty()
	{
		return originalManagementRetrievalQty;
	}

	public void setOriginalManagementRetrievalQty(
			long originalManagementRetrievalQty)
	{
		this.originalManagementRetrievalQty = originalManagementRetrievalQty;
	}
}
