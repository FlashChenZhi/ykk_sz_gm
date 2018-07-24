package jp.co.daifuku.wms.YkkGMAX.Entities;

public class IOHistoryInfoHead
{
	private String workType = "";

	private String timeFrom = "";

	private String timeTo = "";

	private String item = "";

	private String userId = "";

	private String orderBy = "";

	private String retrievalNo = "";

	private String stockinStation = "";

	private String stockinStationName = "";

	private String stockoutStation = "";

	private String stockoutStationName = "";

	private int bucketCount = 0;

	public String getRetrievalNo()
	{
		return retrievalNo;
	}

	public void setRetrievalNo(String retrievalNo)
	{
		this.retrievalNo = retrievalNo;
	}

	public String getStockinStation()
	{
		return stockinStation;
	}

	public void setStockinStation(String stockinStation)
	{
		this.stockinStation = stockinStation;
	}

	public String getStockoutStation()
	{
		return stockoutStation;
	}

	public void setStockoutStation(String stockoutStation)
	{
		this.stockoutStation = stockoutStation;
	}

	public int getBucketCount()
	{
		return bucketCount;
	}

	public void setBucketCount(int bucketCount)
	{
		this.bucketCount = bucketCount;
	}

	public String getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public String getItem()
	{
		return item;
	}

	public void setItem(String item)
	{
		this.item = item;
	}

	public String getTimeFrom()
	{
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom)
	{
		this.timeFrom = timeFrom;
	}

	public String getTimeTo()
	{
		return timeTo;
	}

	public void setTimeTo(String timeTo)
	{
		this.timeTo = timeTo;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getWorkType()
	{
		return workType;
	}

	public void setWorkType(String workType)
	{
		this.workType = workType;
	}

	public String getStockinStationName()
	{
		return stockinStationName;
	}

	public void setStockinStationName(String stockinStationName)
	{
		this.stockinStationName = stockinStationName;
	}

	public String getStockoutStationName()
	{
		return stockoutStationName;
	}

	public void setStockoutStationName(String stockoutStationName)
	{
		this.stockoutStationName = stockoutStationName;
	}
}
