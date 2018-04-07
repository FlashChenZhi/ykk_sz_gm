package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.math.BigDecimal;

public class IOHistoryInfoEntity
{
	private String time = "";

	private String workType = "";

	private String itemCode = "";

	private String itemName1 = "";

	private String itemName2 = "";

	private String itemName3 = "";

	private String color = "";

	private String ticketNo = "";

	private String bucketNo = "";

	private String locationNo = "";

	private String orderNo = "";

	private BigDecimal measureUnitWeight = new BigDecimal(0);

	private String startStation = "";

	private String endStation = "";

	private String stNo = "";

	private String retrievalNo = "";

	private String userId = "";

	private String userName = "";

	private int workCount = 0;

	private String increaseDecreaseFlag;

    private String productStartDate = "";

    private String line1 = "";

    public String getProductStartDate() {
        return productStartDate;
    }

    public void setProductStartDate(String productStartDate) {
        this.productStartDate = productStartDate;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public BigDecimal getMeasureUnitWeight()
	{
		return measureUnitWeight;
	}

	public void setMeasureUnitWeight(BigDecimal measureUnitWeight)
	{
		this.measureUnitWeight = measureUnitWeight;
	}

	public String getStartStation()
	{
		return startStation;
	}

	public void setStartStation(String startStation)
	{
		this.startStation = startStation;
	}

	public String getEndStation()
	{
		return endStation;
	}

	public void setEndStation(String endStation)
	{
		this.endStation = endStation;
	}

	public String getBucketNo()
	{
		return bucketNo;
	}

	public String getColor()
	{
		return color;
	}

	public String getIncreaseDecreaseFlag()
	{
		return increaseDecreaseFlag;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public String getItemName1()
	{
		return itemName1;
	}

	public String getItemName2()
	{
		return itemName2;
	}

	public String getItemName3()
	{
		return itemName3;
	}

	public String getLocationNo()
	{
		return locationNo;
	}

	public String getRetrievalNo()
	{
		return retrievalNo;
	}

	public String getStNo()
	{
		return stNo;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public String getTime()
	{
		return time;
	}

	public String getUserId()
	{
		return userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public int getWorkCount()
	{
		return workCount;
	}

	public String getWorkType()
	{
		return workType;
	}

	public void setBucketNo(String bucketNo)
	{
		this.bucketNo = bucketNo;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public void setIncreaseDecreaseFlag(String increaseDecreaseFlag)
	{
		this.increaseDecreaseFlag = increaseDecreaseFlag;

	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public void setItemName1(String itemName1)
	{
		this.itemName1 = itemName1;
	}

	public void setItemName2(String itemName2)
	{
		this.itemName2 = itemName2;
	}

	public void setItemName3(String itemName3)
	{
		this.itemName3 = itemName3;
	}

	public void setLocationNo(String locationNo)
	{
		this.locationNo = locationNo;
	}

	public void setRetrievalNo(String retrievalNo)
	{
		this.retrievalNo = retrievalNo;
	}

	public void setStNo(String stNo)
	{
		this.stNo = stNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setWorkCount(int workCount)
	{
		this.workCount = workCount;
	}

	public void setWorkType(String workType)
	{
		this.workType = workType;
	}
}
