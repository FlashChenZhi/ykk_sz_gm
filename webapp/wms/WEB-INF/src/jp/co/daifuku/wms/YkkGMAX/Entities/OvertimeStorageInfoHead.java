package jp.co.daifuku.wms.YkkGMAX.Entities;

public class OvertimeStorageInfoHead
{
	private String depositoryType = "";
	
	private String benchmarkDate = "";
	
	private String benchmarkObject = "";
	
	private String orderBy = "";

	public String getBenchmarkDate()
	{
		return benchmarkDate;
	}

	public void setBenchmarkDate(String benchmarkDate)
	{
		this.benchmarkDate = benchmarkDate;
	}

	public String getBenchmarkObject()
	{
		return benchmarkObject;
	}

	public void setBenchmarkObject(String benchmarkObject)
	{
		this.benchmarkObject = benchmarkObject;
	}

	public String getDepositoryType()
	{
		return depositoryType;
	}

	public void setDepositoryType(String depositoryType)
	{
		this.depositoryType = depositoryType;
	}

	public String getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}
}
