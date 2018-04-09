package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.util.ArrayList;

public class ExternalStockoutEntity implements RetrievalPlankeySortable
{
	private String retrievalPlankey = "";
	private String memo = "";
	private String retrievalNo = "";
	private String externalCode = "";
	private String itemCode = "";
	private String itemName1 = "";
	private String itemName2 = "";
	private String itemName3 = "";
	private String color = "";
	private int stockoutCount = 0;
	private int enableToStockoutCount = 0;
	private int totalCountInstock = 0;
	private String whenNextWorkBegin = "";
	private String whenNextWorkBeginTiming = "";
	private String whenThisWorkFinishInPlan = "";
	private String whenThisWorkFinishInPlanTiming = "";
	private long stockoutNecessaryQty = 0;
	private long wavesRetrievalQty = 0;
	private long managementRetrievalQty = 0;
	private long outQty = 0;
	private ArrayList externalstockoutDetailList = null;
	private boolean retrieved = false;

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

	public int getEnableToStockoutCount()
	{
		return enableToStockoutCount;
	}

	public void setEnableToStockoutCount(int enableToStockoutCount)
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

	public int getStockoutCount()
	{
		return stockoutCount;
	}

	public void setStockoutCount(int stockoutCount)
	{
		this.stockoutCount = stockoutCount;
	}

	public int getTotalCountInstock()
	{
		return totalCountInstock;
	}

	public void setTotalCountInstock(int totalCountInstock)
	{
		this.totalCountInstock = totalCountInstock;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public ArrayList getExternalStockoutDetailList()
	{
		return externalstockoutDetailList;
	}

	public void setExternalStockoutDetailList(
			ArrayList externalstockoutDetailList)
	{
		this.externalstockoutDetailList = externalstockoutDetailList;
	}

	public String getRetrievalPlankey()
	{
		return retrievalPlankey;
	}

	public void setRetrievalPlankey(String retrievalPlankey)
	{
		this.retrievalPlankey = retrievalPlankey;
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

	public ArrayList getExternalstockoutDetailList()
	{
		return externalstockoutDetailList;
	}

	public void setExternalstockoutDetailList(
			ArrayList externalstockoutDetailList)
	{
		this.externalstockoutDetailList = externalstockoutDetailList;
	}

	public String getWhenNextWorkBegin()
	{
		return whenNextWorkBegin;
	}

	public void setWhenNextWorkBegin(String whenNextWorkBegin)
	{
		this.whenNextWorkBegin = whenNextWorkBegin;
	}

	public String getWhenNextWorkBeginTiming()
	{
		return whenNextWorkBeginTiming;
	}

	public void setWhenNextWorkBeginTiming(String whenNextWorkBeginTiming)
	{
		this.whenNextWorkBeginTiming = whenNextWorkBeginTiming;
	}

	public String getWhenThisWorkFinishInPlan()
	{
		return whenThisWorkFinishInPlan;
	}

	public void setWhenThisWorkFinishInPlan(String whenThisWorkFinishInPlan)
	{
		this.whenThisWorkFinishInPlan = whenThisWorkFinishInPlan;
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

	public void setRetrieved(boolean retrieved)
	{
		this.retrieved = retrieved;

	}

	public boolean isRetrieved()
	{
		return retrieved;
	}
}
