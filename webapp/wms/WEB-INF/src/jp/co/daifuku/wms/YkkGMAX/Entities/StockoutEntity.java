package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.util.ArrayList;

public class StockoutEntity implements RetrievalPlankeySortable
{
	private String retrievalPlankey = "";

	private String memo = "";

	private String whenNextWorkBegin = "";

	private String whenNextWorkBeginTiming = "";

	private String whenThisWorkFinishInPlan = "";

	private String whenThisWorkFinishInPlanTiming = "";

	private String itemCode = "";

	private String itemName1 = "";

	private String itemName2 = "";

	private String itemName3 = "";

	private String color = "";

	private long stockoutCount = 0;

	private long enableToStockoutCount = 0;

	private long totalCountInstock = 0;

	private String retrievalNo = "";

	private String externalCode = "";

	private ArrayList stockoutDetailList = null;

	private long stockoutNecessaryQty = 0;

	private long wavesRetrievalQty = 0;

	private long managementRetrievalQty = 0;

	private long outQty = 0;
	
	private long planQty = 0;

	private boolean retrieved = false;

    private String itemManageFlag = "";

    private String retrievalStation = "";

	public long getPlanQty()
	{
		return planQty;
	}

	public void setPlanQty(long planQty)
	{
		this.planQty = planQty;
	}

	public boolean isRetrieved()
	{
		return retrieved;
	}

	public void setRetrieved(boolean retrieved)
	{
		this.retrieved = retrieved;
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

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public long getEnableToStockoutCount()
	{
		return enableToStockoutCount;
	}

	public void setEnableToStockoutCount(long enableToStockoutCount)
	{
		this.enableToStockoutCount = enableToStockoutCount;
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

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public long getStockoutCount()
	{
		return stockoutCount;
	}

	public void setStockoutCount(long stockoutCount)
	{
		this.stockoutCount = stockoutCount;
	}

	public long getTotalCountInstock()
	{
		return totalCountInstock;
	}

	public void setTotalCountInstock(long totalCountInstock)
	{
		this.totalCountInstock = totalCountInstock;
	}

	public String getWhenNextWorkBegin()
	{
		return whenNextWorkBegin;
	}

	public void setWhenNextWorkBegin(String whenNextWorkBegin)
	{
		this.whenNextWorkBegin = whenNextWorkBegin;
	}

	public String getWhenThisWorkFinishInPlan()
	{
		return whenThisWorkFinishInPlan;
	}

	public void setWhenThisWorkFinishInPlan(String whenThisWorkFinishInPlan)
	{
		this.whenThisWorkFinishInPlan = whenThisWorkFinishInPlan;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public ArrayList getStockoutDetailList()
	{
		return stockoutDetailList;
	}

	public void setStockoutDetailList(ArrayList stockoutDetailList)
	{
		this.stockoutDetailList = stockoutDetailList;
	}

	public String getRetrievalPlankey()
	{
		return retrievalPlankey;
	}

	public void setRetrievalPlankey(String retrievalPlankey)
	{
		this.retrievalPlankey = retrievalPlankey;
	}

	public String getWhenNextWorkBeginTiming()
	{
		return whenNextWorkBeginTiming;
	}

	public void setWhenNextWorkBeginTiming(String whenNextWorkBeginTiming)
	{
		this.whenNextWorkBeginTiming = whenNextWorkBeginTiming;
	}

	public String getWhenThisWorkFinishInPlanTiming()
	{
		return whenThisWorkFinishInPlanTiming;
	}

	public void setWhenThisWorkFinishInPlanTiming(
			String whenThisWorkFinishInPlanTiming)
	{
		this.whenThisWorkFinishInPlanTiming = whenThisWorkFinishInPlanTiming;
	}

	public String getExternalCode()
	{
		return externalCode;
	}

	public void setExternalCode(String externalCode)
	{
		this.externalCode = externalCode;
	}

	public String getRetrievalNo()
	{
		return retrievalNo;
	}

	public void setRetrievalNo(String retrievalNo)
	{
		this.retrievalNo = retrievalNo;
	}

    public String getItemManageFlag() {
        return itemManageFlag;
    }

    public void setItemManageFlag(String itemManageFlag) {
        this.itemManageFlag = itemManageFlag;
    }

    public String getRetrievalStation() {
        return retrievalStation;
    }

    public void setRetrievalStation(String retrievalStation) {
        this.retrievalStation = retrievalStation;
    }
}
